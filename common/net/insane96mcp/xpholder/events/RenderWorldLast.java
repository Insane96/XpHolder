package net.insane96mcp.xpholder.events;

import net.insane96mcp.xpholder.XpHolder;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = XpHolder.MOD_ID)
public class RenderWorldLast {
	@SubscribeEvent
	public static void EventRenderWorldLast(RenderWorldLastEvent event) {
		/*EntityPlayerSP player = Minecraft.getMinecraft().player;
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		String text = I18n.format(Translatable.XpHolder.levelsStored, 199);
		EntityRenderer.drawNameplate(fontRenderer, text, x, y, z, verticalShift, viewerYaw, viewerPitch, isThirdPersonFrontal, isSneaking);*/
	}
}
