package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.gui.TestGui;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GetXpHeldMessageHandler implements IMessageHandler<GetXpHeldMessage, IMessage> {

	@Override
	public IMessage onMessage(GetXpHeldMessage message, MessageContext ctx) {
		World world = ctx.getServerHandler().player.getEntityWorld();
		
		BlockPos pos = message.pos;

		GetXpHeldResponse response = new GetXpHeldResponse(pos);
		
		TileEntity tileEntity = world.getTileEntity(pos);
		TileEntityXpHolder xpHolder;
		if (tileEntity instanceof TileEntityXpHolder) {
			xpHolder = (TileEntityXpHolder)tileEntity;
			response.xpHeld = xpHolder.xpHeld;
			response.levelsHeld = xpHolder.levelsHeld;
			response.xpCap = xpHolder.XpBarCap();
			response.currentLevelXp = xpHolder.currentLevelXp;
		}
		
		return response;
	}

}
