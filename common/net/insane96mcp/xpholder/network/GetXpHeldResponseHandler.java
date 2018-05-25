package net.insane96mcp.xpholder.network;

import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;

import net.insane96mcp.xpholder.capabilities.IPlayerData;
import net.insane96mcp.xpholder.capabilities.PlayerDataProvider;
import net.insane96mcp.xpholder.gui.TestGui;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GetXpHeldResponseHandler implements IMessageHandler<GetXpHeldResponse, IMessage> {

	@Override
	public IMessage onMessage(GetXpHeldResponse message, MessageContext ctx) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		int xpHeld = message.xpHeld;
		GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
		if (guiScreen instanceof TestGui) {
			TestGui testGui = (TestGui) guiScreen;
			testGui.xpHeld = message.xpHeld;
			testGui.levelsHeld = message.levelsHeld;
			testGui.xpCap = message.xpCap;
			testGui.currentLevelXp = message.currentLevelXp;
		}

		
		return null;
	}

}
