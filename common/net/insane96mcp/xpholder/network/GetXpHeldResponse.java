package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class GetXpHeldResponse implements IMessage {

	public GetXpHeldResponse() {}
	
	public int xpHeld;
	public BlockPos pos;
	
	public GetXpHeldResponse(int xpHeld, BlockPos pos) {
		this.xpHeld = xpHeld;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.xpHeld = buf.readInt();
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.xpHeld);
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
	}

}
