package net.insane96mcp.xpholder.block;

import java.util.List;

import javax.annotation.Nullable;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.gui.GuiHandler;
import net.insane96mcp.xpholder.lib.Names;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockXpHolder extends BlockContainer{

	public BlockXpHolder() {
		super(Material.IRON);
		
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + XpHolder.RESOURCE_PREFIX + Names.XP_HOLDER;
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		playerIn.openGui(XpHolder.instance, GuiHandler.XP_HOLDER_GUI_ID, worldIn, pos.getX(), pos.getY(), pos.getZ());
		//XpHolder.proxy.openTestGui();
		
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityXpHolder(false);
	}
}
