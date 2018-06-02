package net.insane96mcp.xpholder.network;

import net.insane96mcp.xpholder.lib.Properties;
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
					System.out.println(xpHolder.experience.xpHeld);
					
					int levelsAfterWithdraw = xpHolder.experience.levelsHeld - Properties.General.levelCostOnWithdraw;
					int xp = (int) (Experience.GetExperienceFromLevel((int) levelsAfterWithdraw) + Experience.XpBarCap(xpHolder.experience.levelsHeld) * xpHolder.experience.currentLevelXp);
					System.out.println(xp);
					xpHolder.experience.xpHeld = xp;
					xpHolder.experience.levelsHeld = levelsAfterWithdraw;
					if (xpHolder.experience.levelsHeld < 0)
						xpHolder.experience.levelsHeld = 0;
					
					player.addExperience(xpAmount);
					
					xpHolder.markDirty();
				}
			}
		});
		
		
		
		return null;
	}

}
