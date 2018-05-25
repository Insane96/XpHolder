package net.insane96mcp.xpholder.gui;

import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	public static final int XP_HOLDER_GUI_ID = 0;
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == XP_HOLDER_GUI_ID)
			return new TileEntityXpHolder();
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == XP_HOLDER_GUI_ID)
			return new TestGui(x, y, z);
		
		return null;
	}
}
