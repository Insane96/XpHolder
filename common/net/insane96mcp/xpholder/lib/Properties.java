package net.insane96mcp.xpholder.lib;

public class Properties {
	
	public static void Init() {
		General.Init();
	}
	
	public static class General{
		public static int vulcaniteBlockTimeOnFire;
		
		public static void Init() {
			vulcaniteBlockTimeOnFire = Config.LoadIntProperty("general", "vulcanite_block_seconds_on_fire", "How much time will the Vulcanite Block set on fire mobs that are standing on it", 3);
		}
	}
}
