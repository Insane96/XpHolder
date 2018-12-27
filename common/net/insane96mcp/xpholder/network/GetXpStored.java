package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.insane96mcp.xpholder.lib.Strings.Translatable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.resources.I18n;
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
					FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
					String text = I18n.format(Translatable.XpHolder.levelsStored, message.xpStored);
					//System.out.println(message.xpStored + " " + message.pos);
					EntityRenderer.drawNameplate(fontRenderer, text, message.pos.getX(), message.pos.getY() + 1, message.pos.getZ(), 0, player.getPitchYaw().x, player.getPitchYaw().y, false, false);
				}
			});
			
			return null;
		}

	}
	
}