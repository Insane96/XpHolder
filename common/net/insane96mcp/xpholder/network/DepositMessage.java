package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class DepositMessage implements IMessage {

	public DepositMessage() {}
	
	public int xpAmount;
	public BlockPos pos;
	
	public DepositMessage(int xpAmount, BlockPos pos) {
		this.xpAmount = xpAmount;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.xpAmount = buf.readInt();
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xpAmount);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

}
