package net.insane96mcp.xpholder.lib;

public class Properties {
	
	public static void Init() {
		General.Init();
	}
	
	public static class General{
		public static int maxLevelsHeld;
		public static float xpLostOnDestroy;
		public static float xpCostOnWithdraw;
		
		public static void Init() {
			maxLevelsHeld = Config.LoadIntProperty("general", "max_levels_held", "How many experience levels will an XP Holder contain at maximum?", 100);
			xpLostOnDestroy = Config.LoadFloatProperty("general", "xp_lost_on_destroy", "Percentage of experience lost when an XP Holder is destroyed", 5);
			xpCostOnWithdraw = Config.LoadFloatProperty("general", "xp_cost_on_withdraw", "Percentage of experience consumed when it is withdrawn from an XP Holder", 5);
		}
	}
}
