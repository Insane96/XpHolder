package net.insane96mcp.xpholder.events;

import net.insane96mcp.xpholder.XpHolder;
import net.insane96mcp.xpholder.block.ModBlocks;
import net.insane96mcp.xpholder.network.GetXpStored;
import net.insane96mcp.xpholder.network.PacketHandler;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = XpHolder.MOD_ID)
public class PlayerTick {

	@SubscribeEvent
	public static void EventLivingUpdate(LivingUpdateEvent event) {
		EntityLivingBase entity = event.getEntityLiving();
		if (!(entity instanceof EntityPlayerMP))
			return;
		
		EntityPlayerMP player = (EntityPlayerMP) entity;
		RayTraceResult rayTraceResult = player.rayTrace(player.getAttributeMap().getAttributeInstance(player.REACH_DISTANCE).getAttributeValue(), 1.0f);
		if (rayTraceResult == null)
			return;
		BlockPos pos = rayTraceResult.getBlockPos();
		
		if (player.world.getBlockState(pos).getBlock().equals(ModBlocks.xpHolderBlock)) {
			TileEntityXpHolder xpHolder = (TileEntityXpHolder) player.world.getTileEntity(pos);
			PacketHandler.SendToClient(new GetXpStored(xpHolder.experience, pos), player);
			/*ITextComponent textComponent = new TextComponentTranslation(Translatable.XpHolder.levelsStored, Experience.GetLevelsFromExperience(xpHolder.experience).levels);
			SPacketTitle title = new SPacketTitle(SPacketTitle.Type.ACTIONBAR, textComponent);
			player.connection.sendPacket(title);*/
		}
	}
}
