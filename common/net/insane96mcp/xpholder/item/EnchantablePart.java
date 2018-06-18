package net.insane96mcp.xpholder.item;

import java.util.List;

import net.insane96mcp.xpholder.enchantments.EnchantmentHolder;
import net.insane96mcp.xpholder.lib.Translatable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EnchantablePart extends Item {
	
	public EnchantablePart(ResourceLocation name, CreativeTabs tab) {
		setRegistryName(name);
		setCreativeTab(tab);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		if (!stack.isItemEnchanted())
			tooltip.add(I18n.format(Translatable.XpHolder.im_enchantable));
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		if (stack.getItem() instanceof EnchantablePart && enchantment.equals(EnchantmentHolder.ENCHANTMENT)) {
			return true;
		}
		return false;
	}
	
	@Override
	public int getItemEnchantability() {
		return 1;
	}
	
	@Override
	public boolean isEnchantable(ItemStack stack) {
		if (stack.getItem() instanceof EnchantablePart) {
			return true;
		}
		return false;
	}
	
	@Override
	public int getItemStackLimit() {
		return 16;
	}
	
	@Override
	public int getItemStackLimit(ItemStack stack) {
		if (stack.isItemEnchanted()) {
			return 1;
		}
		return 16;
	}
}
