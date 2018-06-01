package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.lib.Properties;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class WithdrawMessageHandler implements IMessageHandler<WithdrawMessage, IMessage> {

	@Override
	public IMessage onMessage(WithdrawMessage message, MessageContext ctx) {
		IThreadListener iThreadListener = (WorldServer) ctx.getServerHandler().player.world;
		iThreadListener.addScheduledTask(new Runnable() {
			
			@Override
			public void run() {
				EntityPlayerMP player = ctx.getServerHandler().player;
				World world = player.getEntityWorld();
				
				float xpAmountPercentage = message.xpAmountPercentage;
				if (xpAmountPercentage <= 0f)
					return;

				BlockPos pos = message.pos;
				
				TileEntity tileEntity = world.getTileEntity(pos);
				TileEntityXpHolder xpHolder;
				if (tileEntity instanceof TileEntityXpHolder) {
					xpHolder = (TileEntityXpHolder)tileEntity;

					int xpAmount = (int) (xpHolder.experience.xpHeld * (xpAmountPercentage / 100f));
					xpHolder.AddExperience(-xpAmount);
					xpAmount -= (Properties.General.xpCostOnWithdraw / 100) * xpAmount;
					player.addExperience(xpAmount);
					
					xpHolder.markDirty();
				}
			}
		});
		
		
		
		return null;
	}

}
