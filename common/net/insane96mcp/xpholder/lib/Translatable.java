package net.insane96mcp.xpholder.lib;

public class Translatable {	
	
	public static class General{
		public static String shiftForMore = "translatable.general.shift_for_more";
	}
	
	public static class XpHolder{
		private static String name = "translatable.xp_holder.";
		public static String on_destroy = name + "on_destroy";
		public static String base_info = name + "base_info";
		public static String withdraw_cost = name + "withdraw_cost";
		public static String withdraw = name + "withdraw";
		public static String deposit = name + "deposit";
		public static String im_enchantable = name + "im_enchantable";
		public static String maxWithdraw = name + "max_withdraw";
		public static String experience = name + "experience";
	}
	
	public static class Upgrade{
		private static String name = "translatable.upgrade.";
		public static String has_bank = name + "has_bank";
		public static String has_pick_up = name + "has_pick_up";
		public static String merge_with_xp_bottle = name + "merge_with_xp_bottle";
	}
}
