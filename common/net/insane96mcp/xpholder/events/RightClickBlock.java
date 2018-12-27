package net.insane96mcp.xpholder.events;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = XpHolder.MOD_ID)
public class RightClickBlock {

	@SubscribeEvent
	public static void EventRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		World world = event.getWorld();
		Block block = world.getBlockState(event.getPos()).getBlock();
		if (block.equals(ModBlocks.xpHolderBlock)) {
			event.setUseBlock(Result.ALLOW);
		}
	}
}
