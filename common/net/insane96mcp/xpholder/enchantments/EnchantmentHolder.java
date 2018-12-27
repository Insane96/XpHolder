package net.insane96mcp.xpholder.enchantments;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.item.EnchantablePart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;

public class EnchantmentHolder extends Enchantment {

	public static EnchantmentHolder ENCHANTMENT = new EnchantmentHolder();
	
	protected EnchantmentHolder(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots) {
		super(rarityIn, typeIn, slots);
		this.setName("holder");
		this.setRegistryName(XpHolder.MOD_ID + ":holder");
	}
	
	public EnchantmentHolder() {
		this(Rarity.COMMON, EnumEnchantmentType.ALL, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
	}
	
	@Override
	public boolean canApply(ItemStack stack) {
		if (stack.getItem() instanceof EnchantablePart)
			return true;
		return false;
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack) {
		if (stack.getItem() instanceof EnchantablePart)
			return true;
		return false;
	}
	
	@Override
	public int getMaxLevel() {
		return 1;
	}
	
	@Override
	public int getMinLevel() {
		return 1;
	}
	
	@Override
	public int getMaxEnchantability(int enchantmentLevel) {
		return 17;
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 13;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
}
