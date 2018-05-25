package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.XpHolder;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(XpHolder.MOD_ID);
	private static int discriminator = 0;
	
	public static void Init() {
		INSTANCE.registerMessage(WithdrawMessageHandler.class, WithdrawMessage.class, discriminator++, Side.SERVER);
		INSTANCE.registerMessage(WithdrawResponseHandler.class, WithdrawResponse.class, discriminator++, Side.CLIENT);
		INSTANCE.registerMessage(GetXpHeldMessageHandler.class, GetXpHeldMessage.class, discriminator, Side.SERVER);
		INSTANCE.registerMessage(GetXpHeldResponseHandler.class, GetXpHeldResponse.class, discriminator, Side.CLIENT);
		
	}
	
	public static void SendToServer(IMessage message) {
		INSTANCE.sendToServer(message);
	}
	
	public static void SendToClient(IMessage message, EntityPlayerMP player) {
		INSTANCE.sendTo(message, player);
	}
}
