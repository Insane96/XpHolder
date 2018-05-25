package net.insane96mcp.xpholder.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import scala.inline;

public class GetXpHeldResponse implements IMessage {

	public GetXpHeldResponse() {}
	
	public int xpHeld;
	public int levelsHeld;
	public int xpCap;
	public float currentLevelXp;
	public BlockPos pos;
	
	public GetXpHeldResponse(BlockPos pos) {
		this.xpHeld = 0;
		this.levelsHeld = 0;
		this.xpCap = 0;
		this.currentLevelXp = 0;
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.xpHeld = buf.readInt();
		this.levelsHeld = buf.readInt();
		this.xpCap = buf.readInt();
		this.currentLevelXp = buf.readFloat();
		this.pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(this.xpHeld);
		buf.writeInt(this.levelsHeld);
		buf.writeInt(this.xpCap);
		buf.writeFloat(this.currentLevelXp);
		buf.writeInt(this.pos.getX());
		buf.writeInt(this.pos.getY());
		buf.writeInt(this.pos.getZ());
	}

}
