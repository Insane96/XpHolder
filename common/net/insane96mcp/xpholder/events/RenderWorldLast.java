package net.insane96mcp.xpholder.events;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.block.ModBlocks;
import net.insane96mcp.xpholder.lib.Strings.Translatable;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.insane96mcp.xpholder.utils.Experience;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = XpHolder.MOD_ID)
public class RenderWorldLast {
	@SubscribeEvent
	public static void EventRenderWorldLast(RenderWorldLastEvent event) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		RayTraceResult rayTraceResult = player.rayTrace(player.getAttributeMap().getAttributeInstance(player.REACH_DISTANCE).getAttributeValue(), 1.0f);
		if (rayTraceResult == null)
			return;
		BlockPos pos = rayTraceResult.getBlockPos();
		if (!player.world.getBlockState(pos).getBlock().equals(ModBlocks.xpHolderBlock))
			return;
		TileEntityXpHolder xpHolder = (TileEntityXpHolder) player.world.getTileEntity(pos);
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		String text = I18n.format(Translatable.XpHolder.levelsStored, Experience.GetLevelsFromExperience(xpHolder.experience).levels);
		//System.out.println(text + " " + rayTraceResult.getBlockPos().getX());
		EntityRenderer.drawNameplate(fontRenderer, text, rayTraceResult.getBlockPos().getX(), rayTraceResult.getBlockPos().getY() + 1, rayTraceResult.getBlockPos().getZ(), 0, player.getPitchYaw().x, player.getPitchYaw().y, false, false);
		
	}
}
