package net.insane96mcp.xpholder.events;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.block.ModBlocks;
import net.insane96mcp.xpholder.item.ModItems;
import net.insane96mcp.xpholder.lib.Properties;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = XpHolder.MOD_ID)
public class AnvilUpdate {

	@SubscribeEvent
	public static void EventAnvilUpdate(AnvilUpdateEvent event) {
		ItemStack stackLeft = event.getLeft();
		ItemStack stackRight = event.getRight();
		Item itemLeft = stackLeft.getItem();
		Item itemRight = stackRight.getItem();
		
		mergeParts(stackLeft, stackRight, itemLeft, itemRight, event);
	}
	
	private static void mergeParts(ItemStack stackLeft, ItemStack stackRight, Item itemLeft, Item itemRight, AnvilUpdateEvent event) {
		if (itemLeft.equals(ModItems.xpHolderTopPart) && itemRight.equals(ModItems.xpHolderBottomPart)
			&& stackLeft.isItemEnchanted() && stackRight.isItemEnchanted()) {
			
			event.setCost(Properties.config.craftingLevelCost);
			event.setOutput(new ItemStack(ModBlocks.xpHolderBlock));
			event.setResult(Result.ALLOW);
		}
	}
}
