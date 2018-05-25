package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.capabilities.IPlayerData;
import net.insane96mcp.xpholder.capabilities.PlayerDataProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WithdrawResponseHandler implements IMessageHandler<WithdrawResponse, IMessage> {

	@Override
	public IMessage onMessage(WithdrawResponse message, MessageContext ctx) {
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		boolean allowWithdraw = message.allowWithdraw;    	

		System.out.println("is withdraw allowed: " + allowWithdraw);
		
		return null;
	}

}
