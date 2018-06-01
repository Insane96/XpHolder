package net.insane96mcp.xpholder.events;

import net.insane96mcp.xpholder.gui.XpHolderGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class RenderGameOverlay {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void EventRenderGameOverlay(RenderGameOverlayEvent.Pre event) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		ElementType type = event.getType();
		
		if (Minecraft.getMinecraft().currentScreen instanceof XpHolderGui) { 
			XpHolderGui currentGui = (XpHolderGui) Minecraft.getMinecraft().currentScreen;
			
			event.setCanceled(currentGui.ShouldBarHide(type));
		}
	}
}
