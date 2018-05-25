package net.insane96mcp.xpholder.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileEntityXpHolder extends TileEntity{
	public int xpHeld;
	public int levelsHeld;
	
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
		isEnder = compound.getBoolean("xpHolder:isEnder");
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("xpholder:xpHeld", xpHeld);
		compound.setInteger("xpholder:levelsHeld", levelsHeld);
		compound.setBoolean("xpholder:isEnder", isEnder);
		return compound;
	}
	
	public boolean isEnderHolder() {
		return this.isEnder;
	}
}
