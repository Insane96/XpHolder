package net.insane96mcp.xpholder.proxies;

import net.insane96mcp.xpholder.gui.TestGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	@Override
	public void PreInit(FMLPreInitializationEvent event) {
		super.PreInit(event);
	}

	@Override
	public void Init(FMLInitializationEvent event) {
		super.Init(event);
	}

	@Override
	public void PostInit(FMLPostInitializationEvent event) {
		super.PostInit(event);
	}
	
	@Override
	public void openTestGui() {
		Minecraft.getMinecraft().displayGuiScreen(new TestGui());
	}
}