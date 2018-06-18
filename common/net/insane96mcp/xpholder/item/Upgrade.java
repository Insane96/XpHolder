package net.insane96mcp.xpholder.item;

import java.util.List;

import net.insane96mcp.xpholder.enchantments.EnchantmentHolder;
import net.insane96mcp.xpholder.lib.Translatable;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Upgrade extends Item {
	public Upgrade(ResourceLocation name, CreativeTabs tab) {
		setRegistryName(name);
		setCreativeTab(tab);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(I18n.format(Translatable.Upgrade.merge_with_xp_bottle));
	}
	
	@Override
	public int getItemStackLimit() {
		return 1;
	}

	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
		return stack.isItemEnchanted();
	}
}
