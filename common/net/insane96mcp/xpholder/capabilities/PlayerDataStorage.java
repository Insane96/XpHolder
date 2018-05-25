package net.insane96mcp.xpholder.capabilities;

import net.insane96mcp.xpholder.XpHolder;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlayerDataStorage implements IStorage<IPlayerData> {

	@Override
	public NBTBase writeNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side) {
		NBTTagCompound tags = new NBTTagCompound();
		tags.setInteger(XpHolder.MOD_ID + ":experienceStored", instance.getExperienceStored());
		return tags;
	}

	@Override
	public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound nbtTagCompound = (NBTTagCompound)nbt;
		instance.setExperienceStored(nbtTagCompound.getInteger(XpHolder.MOD_ID + ":experienceStored"));
	}

}
