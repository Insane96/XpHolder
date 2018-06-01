package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.insane96mcp.xpholder.utils.Experience;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GetXpHeldMessageHandler implements IMessageHandler<GetXpHeldMessage, IMessage> {

	@Override
	public IMessage onMessage(GetXpHeldMessage message, MessageContext ctx) {
		IThreadListener iThreadListener = (WorldServer)ctx.getServerHandler().player.world;
		iThreadListener.addScheduledTask(new Runnable() {
			
			@Override
			public void run() {
				EntityPlayerMP playerMP = ctx.getServerHandler().player;
				World world = ctx.getServerHandler().player.getEntityWorld();
				
				BlockPos pos = message.pos;

				GetXpHeldResponse response = new GetXpHeldResponse(pos);
				
				TileEntity tileEntity = world.getTileEntity(pos);
				TileEntityXpHolder xpHolder;
				if (tileEntity instanceof TileEntityXpHolder) {
					xpHolder = (TileEntityXpHolder)tileEntity;
					response.xpHeld = xpHolder.experience.xpHeld;
					response.levelsHeld = xpHolder.experience.levelsHeld;
					response.xpCap = Experience.XpBarCap(xpHolder.experience.levelsHeld);
					response.currentLevelXp = xpHolder.experience.currentLevelXp;
					
					
					if (playerMP.experienceTotal != Experience.GetExperienceFromLevel(playerMP.experienceLevel) + playerMP.experience * Experience.XpBarCap(playerMP.experienceLevel))
						playerMP.experienceTotal = (int) (Experience.GetExperienceFromLevel(playerMP.experienceLevel) + playerMP.experience * Experience.XpBarCap(playerMP.experienceLevel));
				}
				
				PacketHandler.SendToClient(response, playerMP);
			}
		});
		
		return null;
	}

}
