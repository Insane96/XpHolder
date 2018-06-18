package net.insane96mcp.xpholder.item;

import java.util.ArrayList;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.lib.Names;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ModItems {

	public static Item obsidianContainer;
	
	public static EnchantablePart xpHolderTopPart;
	public static EnchantablePart xpHolderBottomPart;

	public static Upgrade pickUpUpgrade;
	public static Upgrade bankUpgrade;
	
	public static ArrayList<Item> ITEMS = new ArrayList<Item>();
	
	public static void Init() {
		obsidianContainer = new Item();
		obsidianContainer.setCreativeTab(CreativeTabs.MATERIALS);
		obsidianContainer.setRegistryName(new ResourceLocation(XpHolder.MOD_ID, Names.OBSIDIAN_CONTAINER));
		obsidianContainer.setUnlocalizedName(XpHolder.RESOURCE_PREFIX + Names.OBSIDIAN_CONTAINER);
		ITEMS.add(obsidianContainer);
		
		xpHolderTopPart = new EnchantablePart(new ResourceLocation(XpHolder.MOD_ID, Names.XP_HOLDER_TOP_PART), CreativeTabs.MISC);
		xpHolderTopPart.setUnlocalizedName(XpHolder.RESOURCE_PREFIX + Names.XP_HOLDER_TOP_PART);
		ITEMS.add(xpHolderTopPart);
		
		xpHolderBottomPart = new EnchantablePart(new ResourceLocation(XpHolder.MOD_ID, Names.XP_HOLDER_BOTTOM_PART), CreativeTabs.MISC);
		xpHolderBottomPart.setUnlocalizedName(XpHolder.RESOURCE_PREFIX + Names.XP_HOLDER_BOTTOM_PART);
		ITEMS.add(xpHolderBottomPart);
		
		pickUpUpgrade = new Upgrade(new ResourceLocation(XpHolder.MOD_ID, Names.PICK_UP_UPGRADE), CreativeTabs.MISC);
		pickUpUpgrade.setUnlocalizedName(XpHolder.RESOURCE_PREFIX + Names.PICK_UP_UPGRADE);
		ITEMS.add(pickUpUpgrade);
		
		bankUpgrade = new Upgrade(new ResourceLocation(XpHolder.MOD_ID, Names.BANK_UPGRADE), CreativeTabs.MISC);
		bankUpgrade.setUnlocalizedName(XpHolder.RESOURCE_PREFIX + Names.BANK_UPGRADE);
		ITEMS.add(bankUpgrade);
	}
}
