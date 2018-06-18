package net.insane96mcp.xpholder.events;

import java.util.Map;

import net.insane96mcp.xpholder.enchantments.EnchantmentHolder;
import net.insane96mcp.xpholder.init.ModBlocks;
import net.insane96mcp.xpholder.item.ModItems;
import net.insane96mcp.xpholder.lib.Properties;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
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
		
		mergeParts(stackLeft, stackRight, itemLeft, itemRight, event);
		enchantUpgrade(stackLeft, stackRight, itemLeft, itemRight, event);
	}
	
	private static void mergeParts(ItemStack stackLeft, ItemStack stackRight, Item itemLeft, Item itemRight, AnvilUpdateEvent event) {
		if (itemLeft.equals(ModItems.xpHolderTopPart) && itemRight.equals(ModItems.xpHolderBottomPart)
			&& stackLeft.isItemEnchanted() && stackRight.isItemEnchanted()) {
			
			event.setCost(Properties.General.craftingLevelCost);
			event.setOutput(new ItemStack(ModBlocks.xpHolderBlock));
			event.setResult(Result.ALLOW);
		}
	}
	
	private static void enchantUpgrade(ItemStack stackLeft, ItemStack stackRight, Item itemLeft, Item itemRight, AnvilUpdateEvent event) {
		if ((itemLeft.equals(ModItems.pickUpUpgrade) || itemLeft.equals(ModItems.bankUpgrade)) && itemRight.equals(Items.EXPERIENCE_BOTTLE) && stackRight.getCount() == 1) {

			event.setCost(15);//Properties.Upgrade.enchantCost);
			ItemStack output = new ItemStack(itemLeft);
			output.addEnchantment(EnchantmentHolder.ENCHANTMENT, 1);
			event.setOutput(output);
			event.setResult(Result.ALLOW);
		}
	}
}
