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

public class DepositMessageHandler implements IMessageHandler<DepositMessage, IMessage> {

	@Override
	public IMessage onMessage(DepositMessage message, MessageContext ctx) {
		IThreadListener iThreadListener = (WorldServer)ctx.getServerHandler().player.world;
		iThreadListener.addScheduledTask(new Runnable() {
			
			@Override
			public void run() {
				EntityPlayerMP player = ctx.getServerHandler().player;
				World world = player.getEntityWorld();
				float xpAmountPercentage = message.xpAmountPercentage;
				if (xpAmountPercentage <= 0f || xpAmountPercentage > 100f)
					return;
				
				BlockPos pos = message.pos;
				
				TileEntity tileEntity = world.getTileEntity(pos);
				TileEntityXpHolder xpHolder;
				if (tileEntity instanceof TileEntityXpHolder) {
					xpHolder = (TileEntityXpHolder)tileEntity;
					
					int xpAmount = (int) Math.ceil(player.experienceTotal * (xpAmountPercentage / 100f));
					Experience finalExperience = Experience.GetLevelsFromExperience(xpHolder.experience.xpHeld + xpAmount);
					int xpToAdd = 0;
					if (finalExperience.levelsHeld > Properties.General.maxLevelsHeld) {
						int xpDiff = Experience.GetExperienceFromLevel(Properties.General.maxLevelsHeld);
						xpToAdd = xpDiff - xpHolder.experience.xpHeld;
					}
					else {
						xpToAdd = xpAmount;
					}
					xpHolder.AddExperience(xpToAdd);
					
					Experience playerXp = Experience.GetLevelsFromExperience(player.experienceTotal - xpToAdd);
					player.experience = playerXp.currentLevelXp;
					player.experienceLevel = playerXp.levelsHeld;
					player.experienceTotal = playerXp.xpHeld;
					
					xpHolder.markDirty();
				}
			}
		});
		
		return null;
	}

}
