package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class WithdrawResponse implements IMessage {

	public WithdrawResponse() {}
	
	public boolean allowWithdraw;
	
	public WithdrawResponse(boolean allowWithdraw) {
		this.allowWithdraw = allowWithdraw;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.allowWithdraw = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(allowWithdraw);
	}

}
