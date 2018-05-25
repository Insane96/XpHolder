package net.insane96mcp.xpholder;

import java.util.Random;

import net.insane96mcp.xpholder.proxies.CommonProxy;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = XpHolder.MOD_ID, name = XpHolder.MOD_NAME, version = XpHolder.VERSION, acceptedMinecraftVersions = XpHolder.MINECRAFT_VERSIONS)
public class XpHolder{
	
	public static final String MOD_ID = "xpholder";
	public static final String MOD_NAME = "XpHolder";
	public static final String VERSION = "1.0.0";
	public static final String RESOURCE_PREFIX = MOD_ID.toLowerCase() + ":";
	public static final String MINECRAFT_VERSIONS = "[1.12,1.12.2]";
	
	public static Configuration config;

	public static Random random = new Random();
	
	@Instance(MOD_ID)
	public static XpHolder instance;
	
	@SidedProxy(clientSide = "net.insane96mcp.xpholder.proxies.ClientProxy", serverSide = "net.insane96mcp.xpholder.proxies.ServerProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void PreInit(FMLPreInitializationEvent event) {
		proxy.PreInit(event);
	}
	
	@EventHandler
	public void Init(FMLInitializationEvent event) {
		proxy.Init(event);
	}
	
	@EventHandler
	public void PostInit(FMLPostInitializationEvent event) {
		proxy.PostInit(event);
	}
}
