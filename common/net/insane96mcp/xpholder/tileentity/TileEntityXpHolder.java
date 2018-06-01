package net.insane96mcp.xpholder.tileentity;

import net.insane96mcp.xpholder.utils.Experience;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityXpHolder extends TileEntity {

	public Experience experience;

	private boolean isEnder;
	
	public boolean isLocked;
    
	public TileEntityXpHolder() {
		this(false);
	}

	public TileEntityXpHolder(boolean isEnder) {
		experience = new Experience();
		this.isEnder = isEnder;
	}

	private void FixValues() {
		if (experience.xpHeld < 0) {
			experience.xpHeld = 0;
		}
		if (experience.levelsHeld < 0) {
			experience.levelsHeld = 0;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		experience.xpHeld = compound.getInteger("xpholder:xpHeld");
		experience.levelsHeld = compound.getInteger("xpholder:levelsHeld");
		experience.currentLevelXp = compound.getFloat("xpholder:currentLevelXp");
		isEnder = compound.getBoolean("xpholder:isEnder");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("xpholder:xpHeld", experience.xpHeld);
		compound.setInteger("xpholder:levelsHeld", experience.levelsHeld);
		compound.setFloat("xpholder:currentLevelXp", experience.currentLevelXp);
		compound.setBoolean("xpholder:isEnder", isEnder);
		return compound;
	}

	public boolean isEnderHolder() {
		return this.isEnder;
	}

	public void AddExperience(int amount) {
		if (amount < 0) {
			Experience newExp = new Experience();
			newExp = Experience.GetLevelsFromExperience(experience.xpHeld + amount);
			
			experience.xpHeld += amount;
			experience.levelsHeld = newExp.levelsHeld;
			experience.currentLevelXp = newExp.currentLevelXp;

		} else {
			int i = Integer.MAX_VALUE - experience.xpHeld;

			if (amount > i) {
				amount = i;
			}

			experience.currentLevelXp += (float) amount / (float) Experience.XpBarCap(experience.levelsHeld);

			for (experience.xpHeld += amount; experience.currentLevelXp >= 1.0F; experience.currentLevelXp /= (float) Experience
					.XpBarCap(experience.levelsHeld)) {
				experience.currentLevelXp = (experience.currentLevelXp - 1.0F)
						* (float) Experience.XpBarCap(experience.levelsHeld);
				this.experience.levelsHeld++;
			}
		}
	}
}