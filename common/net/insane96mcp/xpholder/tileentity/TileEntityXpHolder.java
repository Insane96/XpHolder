package net.insane96mcp.xpholder.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityXpHolder extends TileEntity{
	public int xpHeld;
	public int levelsHeld;
	public float currentLevelXp;
	
	private boolean isEnder;
	
	public TileEntityXpHolder() { }
	
	public TileEntityXpHolder(boolean isEnder) {
		this.isEnder = isEnder;
	}
	
	private void FixValues() {
	    if (xpHeld < 0)
	    {
	    	xpHeld = 0;
	    }
	    if (levelsHeld < 0)
	    {
	      levelsHeld = 0;
	    }
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		xpHeld = compound.getInteger("xpholder:xpHeld");
		levelsHeld = compound.getInteger("xpHolder:levelsHeld");
		currentLevelXp = compound.getFloat("xpHolder:currentLevelXp");
		isEnder = compound.getBoolean("xpHolder:isEnder");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("xpholder:xpHeld", xpHeld);
		compound.setInteger("xpholder:levelsHeld", levelsHeld);
		compound.setFloat("xpholder:levelsHeld", currentLevelXp);
		compound.setBoolean("xpholder:isEnder", isEnder);
		return compound;
	}
	
	public boolean isEnderHolder() {
		return this.isEnder;
	}
	
	public void AddExperience(int amount) {
		int i = Integer.MAX_VALUE - this.xpHeld;

        if (amount > i)
        {
            amount = i;
        }

        this.currentLevelXp += (float)amount / (float)this.XpBarCap();

        for (this.xpHeld += amount; this.currentLevelXp >= 1.0F; this.currentLevelXp /= (float)this.XpBarCap())
        {
            this.currentLevelXp = (this.currentLevelXp - 1.0F) * (float)this.XpBarCap();
            this.AddExperienceLevel(1);
        }
	}
	
	public void AddExperienceLevel(int amount) {
		this.levelsHeld += amount;

        if (this.levelsHeld < 0)
        {
            this.levelsHeld = 0;
            this.currentLevelXp = 0.0F;
            this.xpHeld = 0;
        }
	}
	
	public int XpBarCap() {
		if (this.levelsHeld >= 30)
        {
            return 112 + (this.levelsHeld - 30) * 9;
        }
        else
        {
            return this.levelsHeld >= 15 ? 37 + (this.levelsHeld - 15) * 5 : 7 + this.levelsHeld * 2;
        }
	}
}
