package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.capabilities.IPlayerData;
import net.insane96mcp.xpholder.capabilities.PlayerDataProvider;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class DepositMessageHandler implements IMessageHandler<DepositMessage, IMessage> {

	@Override
	public IMessage onMessage(DepositMessage message, MessageContext ctx) {
		World world = ctx.getServerHandler().player.getEntityWorld();
		int xpAmount = message.xpAmount;
		BlockPos pos = message.pos;
		
		//WithdrawResponse response = new WithdrawResponse();
		
		TileEntity tileEntity = world.getTileEntity(pos);
		TileEntityXpHolder xpHolder;
		if (tileEntity instanceof TileEntityXpHolder) {
			xpHolder = (TileEntityXpHolder)tileEntity;
			if (xpHolder.xpHeld >= xpAmount) {
				xpHolder.xpHeld += xpAmount;
			}
		}
		
		return null;
	}

}
