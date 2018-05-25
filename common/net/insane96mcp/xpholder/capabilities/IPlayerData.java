package net.insane96mcp.xpholder.capabilities;

public interface IPlayerData {
	public int getExperienceStored();
	public void addExperienceStored(int amount);
	public void removeExperienceStored(int amount);
	public void setExperienceStored(int amount);
}
