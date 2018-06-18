package net.insane96mcp.xpholder.block;

import java.util.List;

import javax.annotation.Nullable;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.init.ModBlocks;
import net.insane96mcp.xpholder.item.ModItems;
import net.insane96mcp.xpholder.lib.Names;
import net.insane96mcp.xpholder.lib.Properties;
import net.insane96mcp.xpholder.lib.Translatable;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockXpHolder extends Block{

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool HAS_BANK_UPGRADE = PropertyBool.create("has_bank_upgrade");
    public static final PropertyBool HAS_PICK_UP_UPGRADE = PropertyBool.create("has_pick_up_upgrade");
	public AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.875, 0.9375);
	
	public BlockXpHolder() {
		super(Material.IRON, MapColor.GREEN);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HAS_BANK_UPGRADE, false).withProperty(HAS_PICK_UP_UPGRADE, false));
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + XpHolder.RESOURCE_PREFIX + Names.XP_HOLDER;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format(Translatable.XpHolder.base_info, Properties.General.maxLevelsHeld));
	}
	
	@Override
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
			List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
	}

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			if (!playerIn.isSneaking())
				XpHolder.proxy.openGui(pos.getX(), pos.getY(), pos.getZ());
			return true;
		}
		
		TileEntityXpHolder tileEntityXpHolder = (TileEntityXpHolder) worldIn.getTileEntity(pos);
		ItemStack heldItemStack = playerIn.getHeldItem(hand);
		Item heldItem = heldItemStack.getItem();
		if (heldItem.equals(ModItems.pickUpUpgrade) && heldItemStack.isItemEnchanted() && playerIn.isSneaking()) {
			if (!tileEntityXpHolder.HasBankUpgrade() && tileEntityXpHolder.AddPickUpUpgrade()) {
				playerIn.getHeldItem(hand).shrink(1);
				playerIn.swingArm(hand);
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.1f, false);
			}
		}
		else if (heldItem.equals(ModItems.bankUpgrade) && heldItemStack.isItemEnchanted() && playerIn.isSneaking()) {
			if (!tileEntityXpHolder.HasPickUpUpgrade() && tileEntityXpHolder.AddBankUpgrade()) {
				playerIn.getHeldItem(hand).shrink(1);
				playerIn.swingArm(hand);
				worldIn.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0f, 1.1f, false);
			}
		}
		else if (playerIn.isSneaking() && playerIn.getHeldItem(hand).isEmpty()) {
			if (tileEntityXpHolder.RemoveBankUpgrade()) {
				ItemStack itemStack = new ItemStack(ModItems.bankUpgrade);
				EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
				worldIn.spawnEntity(item);
				playerIn.swingArm(hand);
			}
			else if (tileEntityXpHolder.RemovePickUpUpgrade()) {
				ItemStack itemStack = new ItemStack(ModItems.pickUpUpgrade);
				EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
				worldIn.spawnEntity(item);
				playerIn.swingArm(hand);
			}
		}
		
		return true;
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntityXpHolder tileEntityXpHolder = (TileEntityXpHolder) worldIn.getTileEntity(pos);
		if (tileEntityXpHolder.experience.xpHeld > 0) {
			int originalXpHeld =  (int) (tileEntityXpHolder.experience.xpHeld * (1f - Properties.General.xpLostOnDestroy / 100f));
			int xpHeld = (int) (tileEntityXpHolder.experience.xpHeld * (1f - Properties.General.xpLostOnDestroy / 100f));
			for (int i = 0; i < 100; i++) {
				int xpOrbValue = (int) Math.ceil(originalXpHeld / 100f);
				if (xpOrbValue <= 0)
					xpOrbValue = 1;
				EntityXPOrb xpOrb = new EntityXPOrb(worldIn, pos.getX(), pos.getY(), pos.getZ(), xpOrbValue);
				xpHeld -= xpOrbValue;
				if (xpHeld <= 0) {
					break;
				}
				worldIn.spawnEntity(xpOrb);
			}
			
		}
		
		if (tileEntityXpHolder.RemoveBankUpgrade()) {
			ItemStack itemStack = new ItemStack(ModItems.bankUpgrade);
			EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
			worldIn.spawnEntity(item);
		}
		else if (tileEntityXpHolder.RemovePickUpUpgrade()) {
			ItemStack itemStack = new ItemStack(ModItems.pickUpUpgrade);
			EntityItem item = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
			worldIn.spawnEntity(item);
		}
		
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);
		if (tileEntity instanceof TileEntityXpHolder) {
			TileEntityXpHolder xpHolder = (TileEntityXpHolder) tileEntity;
			if (xpHolder.experience.xpHeld > 0)
				playerIn.sendStatusMessage(new TextComponentTranslation(Translatable.XpHolder.on_destroy, Properties.General.xpLostOnDestroy), true);
		}
		super.onBlockClicked(worldIn, pos, playerIn);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}
	
	@Override
	public boolean isFullBlock(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public TileEntity createTileEntity(World worldIn, IBlockState meta) {
		return new TileEntityXpHolder(false);
	}

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    public static void setState(boolean hasBankUpgrade, boolean hasPickUpUpgrade, World worldIn, BlockPos pos)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        TileEntity tileentity = worldIn.getTileEntity(pos);

        worldIn.setBlockState(pos, ModBlocks.xpHolderBlock.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)).withProperty(HAS_BANK_UPGRADE, hasBankUpgrade).withProperty(HAS_PICK_UP_UPGRADE, hasPickUpUpgrade), 3);

        if (tileentity != null)
        {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }
    
    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY,
    		float hitZ, int meta, EntityLivingBase placer, EnumHand hand) { 
    	return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(HAS_BANK_UPGRADE, false).withProperty(HAS_PICK_UP_UPGRADE, false);
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
    		ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(HAS_BANK_UPGRADE, false).withProperty(HAS_PICK_UP_UPGRADE, false), 2);
    }
    
    @Override
    public IBlockState getStateFromMeta(int meta) {
    	EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }
    
    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }
    
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
    	return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }
    
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }
    
    public EnumFacing getFacing(IBlockState state) {
    	return (EnumFacing)state.getValue(FACING);
    }
    
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
    	return EnumBlockRenderType.MODEL;
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
    	return new BlockStateContainer(this, new IProperty[] {FACING, HAS_BANK_UPGRADE, HAS_PICK_UP_UPGRADE});
    }
}
