package net.insane96mcp.xpholder.capabilities;

public class PlayerData implements IPlayerData{

	private int experienceStored;

	@Override
	public int getExperienceStored() {
		return experienceStored;
	}

	@Override
	public void addExperienceStored(int amount) {
		experienceStored += amount;
	}

	@Override
	public void removeExperienceStored(int amount) {
		experienceStored -= amount;
	}

	@Override
	public void setExperienceStored(int amount) {
		experienceStored = amount;
	}
		

}
