package net.insane96mcp.xpholder.lib;

public class Properties {
	
	public static void Init() {
		General.Init();
	}
	
	public static class General{
		public static int maxLevelsHeld;
		public static float xpLostOnDestroy;
		public static int levelCostOnWithdraw;
		public static int craftingLevelCost;
		
		public static void Init() {
			maxLevelsHeld = Config.LoadIntProperty("general", "max_levels_held", "How many experience levels will an XP Holder contain at maximum?", 100);
			xpLostOnDestroy = Config.LoadFloatProperty("general", "xp_lost_on_destroy", "Percentage of experience lost when an XP Holder is destroyed", 5);
			levelCostOnWithdraw = Config.LoadIntProperty("general", "xp_cost_on_withdraw", "Levels consumed in the XP Holder when player withdrawn experience", 1);
			craftingLevelCost = Config.LoadIntProperty("general", "crafting_level_cost", "How much costs combining top and bottom part of the Xp Holder", 15);
		}
	}
}
