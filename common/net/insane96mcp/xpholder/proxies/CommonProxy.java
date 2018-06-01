package net.insane96mcp.xpholder.proxies;

import net.insane96mcp.xpholder.events.RenderGameOverlay;
import net.insane96mcp.xpholder.init.ModBlocks;
import net.insane96mcp.xpholder.lib.Config;
import net.insane96mcp.xpholder.lib.Properties;
import net.insane96mcp.xpholder.network.PacketHandler;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void PreInit(FMLPreInitializationEvent event) {
		Config.config = new Configuration(event.getSuggestedConfigurationFile());
		Config.SyncConfig();
		Properties.Init();
		
		ModBlocks.Init();
	}
	
	public void Init(FMLInitializationEvent event) {
		ModBlocks.PostInit();
		MinecraftForge.EVENT_BUS.register(RenderGameOverlay.class);
		PacketHandler.Init();
		
		GameRegistry.registerTileEntity(TileEntityXpHolder.class, "XpHolder");
	}
	
	public void PostInit(FMLPostInitializationEvent event) {
		Config.SaveConfig();
	}
	
	public void openGui(int x, int y, int z) {
		
	}
}
