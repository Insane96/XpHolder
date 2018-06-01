package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.gui.XpHolderGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GetXpHeldResponseHandler implements IMessageHandler<GetXpHeldResponse, IMessage> {

	@Override
	public IMessage onMessage(GetXpHeldResponse message, MessageContext ctx) {
		IThreadListener iThreadListener = Minecraft.getMinecraft();
		iThreadListener.addScheduledTask(new Runnable() {
			@Override
			public void run() {
				EntityPlayerSP player = Minecraft.getMinecraft().player;
				int xpHeld = message.xpHeld;
				GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
				if (guiScreen instanceof XpHolderGui) {
					XpHolderGui testGui = (XpHolderGui) guiScreen;
					testGui.xpHeld = message.xpHeld;
					testGui.levelsHeld = message.levelsHeld;
					testGui.xpCap = message.xpCap;
					testGui.currentLevelXp = message.currentLevelXp;
				}
			}
		});
		
		return null;
	}

}
