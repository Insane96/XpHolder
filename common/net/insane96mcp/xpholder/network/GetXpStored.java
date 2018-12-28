package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.insane96mcp.xpholder.block.ModBlocks;
import net.insane96mcp.xpholder.tileentity.TileEntityXpHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class GetXpStored implements IMessage {

	public GetXpStored() {}
	
	public int xpStored;
	public BlockPos pos;
	
	public GetXpStored(int xpStored, BlockPos pos) {
		this.xpStored = xpStored;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.xpStored = buf.readInt();
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.xpStored);
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
	}

	public static class Handler implements IMessageHandler<GetXpStored, IMessage> {

		@Override
		public IMessage onMessage(GetXpStored message, MessageContext ctx) {
			IThreadListener iThreadListener = Minecraft.getMinecraft();
			iThreadListener.addScheduledTask(new Runnable() {
				@Override
				public void run() {
					EntityPlayerSP player = Minecraft.getMinecraft().player;
					if (player.world.getBlockState(message.pos).getBlock().equals(ModBlocks.xpHolderBlock)) {
						TileEntityXpHolder xpHolder = (TileEntityXpHolder) player.world.getTileEntity(message.pos);
						xpHolder.experience = message.xpStored;
					}
				}
			});
			
			return null;
		}

	}
	
}