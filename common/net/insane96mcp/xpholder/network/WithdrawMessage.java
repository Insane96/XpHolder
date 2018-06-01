package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class WithdrawMessage implements IMessage {

	public WithdrawMessage() {}
	
	public float xpAmountPercentage;
	public BlockPos pos;
	
	public WithdrawMessage(float xpAmountPercentage, BlockPos pos) {
		this.xpAmountPercentage = xpAmountPercentage;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.xpAmountPercentage = buf.readFloat();
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(xpAmountPercentage);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

}
