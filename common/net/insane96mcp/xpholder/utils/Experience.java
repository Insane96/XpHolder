package net.insane96mcp.xpholder.utils;

import net.minecraft.entity.player.EntityPlayer;

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
			return level * level + 6 * level;
		}
		else if (level > 16 && level <= 31) {
			return (int) (2.5f * level * level - 40.5f * level + 360);
		}
		else if (level > 31){
			return (int) (4.5f * level * level - 162.5f * level + 2220);
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return String.format("Experience{XpHeld: %d, LevelsHeld: %d, CurrentLevelXp: %f}", this.xp, this.levels, this.currentLevelXp);
	}

	public static void FixPlayerExperience(EntityPlayer player) {
		int xp = GetExperienceFromLevel(player.experienceLevel);
		int xpBarCap = XpBarCap(player.experienceLevel);
		float xpBarValue = (float) Math.floor(xpBarCap * player.experience);
		xp += xpBarValue;
		Experience experience = Experience.GetLevelsFromExperience(xp);
		//System.out.println(GetExperienceFromLevel(player.experienceLevel) + " " + xpBarValue + " " + xp + " " + player.experienceTotal);
		if (experience.xp - player.experienceTotal > -2 && experience.xp - player.experienceTotal < 2) {
			//System.out.println("No Need To Fix");
			return;
		}
		player.experienceTotal = xp;
	}
}
