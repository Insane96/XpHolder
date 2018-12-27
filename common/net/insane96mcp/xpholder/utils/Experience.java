package net.insane96mcp.xpholder.utils;

public class Experience{
	public int xp;
	public int levels;
	public float currentLevelXp;
	
	public Experience() {
		this.xp = 0;
		this.levels = 0;
		this.currentLevelXp = 0;
	}
	
	public Experience(int xpHeld, int levelsHeld, float currentLevelXp) {
		this.xp = xpHeld;
		this.levels = levelsHeld;
		this.currentLevelXp = currentLevelXp;
	}
	
	public static int XpBarCap(int levelsHeld) {
		if (levelsHeld >= 30)
        {
            return 112 + (levelsHeld - 30) * 9;
        }
        else
        {
            return levelsHeld >= 15 ? 37 + (levelsHeld - 15) * 5 : 7 + levelsHeld * 2;
        }
	}
	
	public static Experience GetLevelsFromExperience(int amount) {
		Experience experience = new Experience();
		
		int maxInt = Integer.MAX_VALUE;

        if (amount > maxInt)
        {
            amount = maxInt;
        }

        experience.currentLevelXp += (float)amount / (float)XpBarCap(experience.levels);

        for (experience.xp += amount; experience.currentLevelXp >= 1.0F; experience.currentLevelXp /= (float)XpBarCap(experience.levels))
        {
        	experience.currentLevelXp = (experience.currentLevelXp - 1.0F) * (float)XpBarCap(experience.levels);
        	experience.levels++;
        }
        
		return experience;
	}
	
	public static int GetExperienceFromLevel(int level) {
		if (level > 0 && level <= 16) {
			return level * level + 6 * level + 1;
		}
		else if (level > 16 && level <= 31) {
			return (int) (2.5 * level * level - 40.5 * level + 361);
		}
		else if (level > 31){
			return (int) (4.5 * level * level - 162.5 * level + 2221);
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return String.format("XpHeld: %d, LevelsHeld: %d, CurrentLevelXp: %f", this.xp, this.levels, this.currentLevelXp);
	}
}
