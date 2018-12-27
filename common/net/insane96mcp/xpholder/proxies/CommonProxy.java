package net.insane96mcp.xpholder.proxies;

import net.insane96mcp.xpholder.block.ModBlocks;
import net.insane96mcp.xpholder.capabilities.IPlayerData;
import net.insane96mcp.xpholder.capabilities.PlayerData;
import net.insane96mcp.xpholder.capabilities.PlayerDataStorage;
import net.insane96mcp.xpholder.item.ModItems;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void PreInit(FMLPreInitializationEvent event) {		
		ModBlocks.Init();
		ModItems.Init();
	}
	
	public void Init(FMLInitializationEvent event) {
		ModBlocks.PostInit();		
		GameRegistry.registerTileEntity(TileEntityXpHolder.class, "XpHolder");
		CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataStorage(), PlayerData.class);
	}
	
	public void PostInit(FMLPostInitializationEvent event) {
		
	}
}
