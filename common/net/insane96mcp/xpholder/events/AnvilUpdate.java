package net.insane96mcp.xpholder.events;

import java.util.Map;

import net.insane96mcp.xpholder.init.ModBlocks;
import net.insane96mcp.xpholder.item.ModItems;
import net.insane96mcp.xpholder.lib.Properties;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AnvilUpdate {

	@SubscribeEvent
	public static void EventAnvilUpdate(AnvilUpdateEvent event) {
		ItemStack stackLeft = event.getLeft();
		ItemStack stackRight = event.getRight();
		Item itemLeft = stackLeft.getItem();
		Item itemRight = stackRight.getItem();
		if (itemLeft.equals(ModItems.xpHolderTopPart) && itemRight.equals(ModItems.xpHolderBottomPart)
				&& stackLeft.isItemEnchanted() && stackRight.isItemEnchanted()) {
			
			event.setCost(Properties.General.craftingLevelCost);
			event.setOutput(new ItemStack(ModBlocks.xpHolderBlock));
			event.setResult(Result.ALLOW);
		}
	}
}
