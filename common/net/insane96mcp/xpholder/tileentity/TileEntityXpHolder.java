package net.insane96mcp.xpholder.tileentity;

import net.insane96mcp.xpholder.lib.Properties;
import net.insane96mcp.xpholder.lib.Properties.EnumMendingBehaviour;
import net.insane96mcp.xpholder.utils.Experience;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityXpHolder extends TileEntity {

	public int experience;

	private boolean isEnder;
    
	public TileEntityXpHolder() {
		this(false);
	}

	public TileEntityXpHolder(boolean isEnder) {
		experience = 0;
		this.isEnder = isEnder;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		experience = compound.getInteger("xpholder:xpHeld");
		isEnder = compound.getBoolean("xpholder:isEnder");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("xpholder:xpHeld", experience);
		compound.setBoolean("xpholder:isEnder", isEnder);
		return compound;
	}

	public boolean isEnderHolder() {
		return this.isEnder;
	}

	public void AddExperience(int amount, World world, EntityPlayer player, BlockPos pos) {
		if (world.isRemote)
			return;
		
		int multiplier = 1 + Experience.GetLevelsFromExperience(player.experienceTotal).levels / 10;
		amount *= multiplier;
		
		if (experience + amount > Experience.GetExperienceFromLevel(Properties.config.maxLevelsHeld))
			amount = Experience.GetExperienceFromLevel(Properties.config.maxLevelsHeld) - experience;
		
		if (player.experienceTotal < amount)
			amount = player.experienceTotal;
		
		if (amount <= 0)
			return;
		
		Experience finalExperience = Experience.GetLevelsFromExperience(this.experience + amount);
		int xpToAdd = 0;
		if (finalExperience.levels > Properties.config.maxLevelsHeld) {
			int xpDiff = Experience.GetExperienceFromLevel(Properties.config.maxLevelsHeld);
			xpToAdd = xpDiff - this.experience;
		}
		else {
			xpToAdd = amount;
		}
		this.experience += amount;
		
		Experience playerXp = Experience.GetLevelsFromExperience(player.experienceTotal - xpToAdd);
		player.experience = playerXp.currentLevelXp;
		player.experienceLevel = playerXp.levels;
		player.experienceTotal = playerXp.xp;
		
		EntityXPOrb xpOrb = new EntityXPOrb(world, pos.getX() + .5, pos.getY() + 0.9, pos.getZ() + .5, 0);
		xpOrb.xpOrbAge = 5999;
		xpOrb.motionX = 0;
		xpOrb.motionY = -1;
		xpOrb.motionZ = 0;
		world.spawnEntity(xpOrb);
		world.playSound(null, pos, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 0.2f, (float)Math.random() * 0.5f + 0.75f);
		
		this.markDirty();
	}
	
	public void RemoveExperience(int amount, World world, EntityPlayer player) {
		if (world.isRemote)
			return;
		
		if (this.experience <= 0)
			return;

		int multiplier = 1 + Experience.GetLevelsFromExperience(this.experience).levels / 10;
		amount *= multiplier;
		
		if (this.experience < amount)
			amount = this.experience;
		
		if (amount <= 0)
			return;
		
		this.experience -= amount;
		
		//Vanilla Mending
		if (Properties.config.mendingBehaviour.equals(EnumMendingBehaviour.NORMAL)) {
			EntityXPOrb xpOrb = new EntityXPOrb(world, player.posX, player.posY, player.posZ, amount);
			xpOrb.xpOrbAge = 5800;
			world.spawnEntity(xpOrb);
		}
		//Vanilla Mending / 2
		else if (Properties.config.mendingBehaviour.equals(EnumMendingBehaviour.NERFED)) {
			ItemStack itemStack = EnchantmentHelper.getEnchantedItem(Enchantments.MENDING, player);

            if (!itemStack.isEmpty() && itemStack.isItemDamaged())
            {
                int i = (int) Math.min(amount, itemStack.getItemDamage());
                amount -= i;
                itemStack.setItemDamage(itemStack.getItemDamage() - i);
            }

            if (amount > 0)
            {
                player.addExperience(amount);
            }
			world.playSound(null, new BlockPos(player), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.35F + 0.9F);
		}
		//No Mending repair
		else if (Properties.config.mendingBehaviour.equals(EnumMendingBehaviour.DISABLED)){
			player.addExperience(amount);
			world.playSound(null, new BlockPos(player), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.35F + 0.9F);
        }
		
		this.markDirty();
	}
}