package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DepositMessageHandler implements IMessageHandler<DepositMessage, IMessage> {

	@Override
	public IMessage onMessage(DepositMessage message, MessageContext ctx) {
		IThreadListener iThreadListener = (WorldServer)ctx.getServerHandler().player.world;
		iThreadListener.addScheduledTask(new Runnable() {
			
			@Override
			public void run() {
				World world = ctx.getServerHandler().player.getEntityWorld();
				int xpAmount = message.xpAmount;
				BlockPos pos = message.pos;
				
				TileEntity tileEntity = world.getTileEntity(pos);
				TileEntityXpHolder xpHolder;
				if (tileEntity instanceof TileEntityXpHolder) {
					xpHolder = (TileEntityXpHolder)tileEntity;
					xpHolder.AddExperience(xpAmount);

					System.out.println(xpHolder.xpHeld);
				}
			}
		});
		
		return null;
	}

}
