package net.insane96mcp.xpholder.lib;

public class Strings {
	public static class Names {
		public static final String XP_HOLDER = "xp_holder";
		public static final String XP_HOLDER_TOP_PART = "xp_holder_top_part";
		public static final String XP_HOLDER_BOTTOM_PART = "xp_holder_bottom_part";
		public static final String OBSIDIAN_CONTAINER = "obsidian_container";
	}
	
	public static class Translatable {	
		
		public static class General{
			public static String shiftForMore = "translatable.general.shift_for_more";
		}
		
		public static class XpHolder{
			private static String name = "xp_holder.";
			public static String on_destroy = name + "on_destroy";
			public static String base_info = name + "base_info";
			public static String levelsStored = name + "levels_stored";
			public static String im_enchantable = name + "im_enchantable";
		}
		
		public static class Upgrade{
			private static String name = "translatable.upgrade.";
			public static String has_bank = name + "has_bank";
			public static String has_pick_up = name + "has_pick_up";
			public static String merge_with_xp_bottle = name + "merge_with_xp_bottle";
		}
	}
}
