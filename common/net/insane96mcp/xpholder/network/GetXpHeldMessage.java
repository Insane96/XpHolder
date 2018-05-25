package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class GetXpHeldMessage implements IMessage {

	public GetXpHeldMessage() {}
	
	public BlockPos pos;
	
	public GetXpHeldMessage(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

}
