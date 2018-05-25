package net.insane96mcp.xpholder.init;

import java.util.ArrayList;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.block.BlockXpHolder;
import net.insane96mcp.xpholder.lib.Names;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;

public class ModBlocks {
	
	public static BlockXpHolder xpHolderBlock;
	
	public static ArrayList<Block> BLOCKS = new ArrayList<Block>();
	
	public static void Init() {
		ResourceLocation location = new ResourceLocation(XpHolder.MOD_ID, Names.XP_HOLDER);
		xpHolderBlock = new BlockXpHolder();
		xpHolderBlock.setRegistryName(location);
		xpHolderBlock.setCreativeTab(CreativeTabs.DECORATIONS);
		xpHolderBlock.setHardness(7.5f);
		xpHolderBlock.setResistance(12f);
		xpHolderBlock.setHarvestLevel("pickaxe", 1);
		BLOCKS.add(xpHolderBlock);
	}
	
	public static void PostInit() {
		
	}
}
