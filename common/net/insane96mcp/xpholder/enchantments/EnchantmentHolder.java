package net.insane96mcp.xpholder.enchantments;

import io.netty.handler.codec.AsciiHeadersEncoder.NewlineType;
import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.item.EnchantablePart;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
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
		return 32;
	}
	
	@Override
	public int getMinEnchantability(int enchantmentLevel) {
		return 28;
	}
	
	@Override
	public boolean isAllowedOnBooks() {
		return false;
	}
}
