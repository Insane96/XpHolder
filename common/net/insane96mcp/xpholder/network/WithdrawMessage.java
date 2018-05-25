package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class WithdrawMessage implements IMessage {

	public WithdrawMessage() {}
	
	public int xpAmount;
	public int xpLevels;
	public BlockPos pos;
	
	public WithdrawMessage(int xpAmount, int xpLevels, BlockPos pos) {
		this.xpAmount = xpAmount;
		this.xpLevels = xpLevels;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.xpAmount = buf.readInt();
		this.xpLevels = buf.readInt();
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(xpAmount);
		buf.writeInt(xpLevels);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

}
