package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.capabilities.IPlayerData;
import net.insane96mcp.xpholder.capabilities.PlayerDataProvider;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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

		System.out.println("xp held: " + xpHeld);
		
		return null;
	}

}
