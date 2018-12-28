package net.insane96mcp.xpholder.lib;

import net.insane96mcp.xpholder.XpHolder;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Name;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

@Config(modid = XpHolder.MOD_ID)
public class Properties {
	
	public static ConfigOptions config = new ConfigOptions();
	
	public static class ConfigOptions{
		@Name("Max Levels Held")
		@Comment("How many experience levels will an XP Holder contain at maximum?")
		public int maxLevelsHeld = 50;
		@Name("Xp Lost on Destroy")
		@Comment("Percentage of experience lost when an XP Holder is destroyed")
		public float xpLostOnDestroy = 10;
		@Name("Crafting Level Cost")
		@Comment("How much costs combining top and bottom part of the Xp Holder")
		public int craftingLevelCost = 10;
		@Name("Mending Behaviour")
		@Comment("How will Mending work when getting Experience from an XP Holder.\n"
				+ "Valid Values are NORMAL, NERFED, DISABLED\n"
				+ "NORMAL: like vanilla when picking experience\n"
				+ "NERFED: like vanilla but requires more experience to repair a tool durability\n"
				+ "DISABLED: picking up Experience from an XP Holder will not repair anything with Mending")
		public EnumMendingBehaviour mendingBehaviour = EnumMendingBehaviour.NERFED;
	}
	
	public enum EnumMendingBehaviour{
		NORMAL, 
		NERFED, 
		DISABLED
	}

	
	@Mod.EventBusSubscriber(modid = XpHolder.MOD_ID)
	private static class EventHandler{
		@SubscribeEvent
	    public static void onConfigChangedEvent(OnConfigChangedEvent event)
	    {
	        if (event.getModID().equals(XpHolder.MOD_ID))
	        {
	            ConfigManager.sync(XpHolder.MOD_ID, Type.INSTANCE);
	        }
	    }
	    
		@SubscribeEvent
	    public static void onPlayerLoggedIn(PlayerLoggedInEvent event) {
	    	if (event.player.world.isRemote)
	    		return;
	    	
	    	
	    }
	}
}
