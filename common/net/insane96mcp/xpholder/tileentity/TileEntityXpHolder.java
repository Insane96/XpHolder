package net.insane96mcp.xpholder.tileentity;

import java.util.List;

import javax.annotation.Nullable;

import net.insane96mcp.xpholder.block.BlockXpHolder;
import net.insane96mcp.xpholder.lib.Properties;
import net.insane96mcp.xpholder.utils.Experience;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class TileEntityXpHolder extends TileEntity implements ITickable {

	public Experience experience;

	private boolean isEnder;
	
	public boolean isLocked;
	
	private int xpAtMaxLevel;
	
	private boolean hasBankUpgrade;
	private int nextGrow;
	
	private boolean hasPickUpUpgrade;
    
	public TileEntityXpHolder() {
		this(false);
	}

	public TileEntityXpHolder(boolean isEnder) {
		experience = new Experience();
		this.isEnder = isEnder;
		hasBankUpgrade = false;
		nextGrow = -1;
		hasPickUpUpgrade = false;
		
		xpAtMaxLevel = Experience.GetExperienceFromLevel(Properties.General.maxLevelsHeld);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		experience.xpHeld = compound.getInteger("xpholder:xpHeld");
		experience.levelsHeld = compound.getInteger("xpholder:levelsHeld");
		experience.currentLevelXp = compound.getFloat("xpholder:currentLevelXp");
		isEnder = compound.getBoolean("xpholder:isEnder");
		hasBankUpgrade = compound.getBoolean("xpholder:hasBankUpgrade");
		nextGrow = compound.getInteger("xpholder:nextGrow");
		hasPickUpUpgrade = compound.getBoolean("xpholder:hasPickUpUpgrade");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("xpholder:xpHeld", experience.xpHeld);
		compound.setInteger("xpholder:levelsHeld", experience.levelsHeld);
		compound.setFloat("xpholder:currentLevelXp", experience.currentLevelXp);
		compound.setBoolean("xpholder:isEnder", isEnder);
		compound.setBoolean("xpholder:hasBankUpgrade", hasBankUpgrade);
		compound.setInteger("xpholder:nextGrow", nextGrow);
		compound.setBoolean("xpholder:hasPickUpUpgrade", hasPickUpUpgrade);
		return compound;
	}

	@Override
	public void update() {
		//if (world.isRemote)
			//return;
		System.out.println(world.isRemote + " " + hasPickUpUpgrade + " " + world.getTotalWorldTime());
		
		if (hasBankUpgrade) {
			nextGrow--;
			if (nextGrow <= 0) {
				nextGrow = 10 * 20; //Properties.BankUpgrade.growTime * 20;
				growExperience();
			}
		}

		if (hasPickUpUpgrade) {
			pickUpExperience();
		}
	}

	public boolean IsEnderHolder() {
		return this.isEnder;
	}

	/* Returns true when successfully adds the upgrade */
	public boolean AddBankUpgrade() {
		if (!hasBankUpgrade) {
			hasBankUpgrade = true;
			nextGrow = 60 * 20; //Properties.BankUpgrade.growTime * 20;
			this.sendUpdates();
			return true;
		}
		return false;
	}

	/* Returns true when successfully removes the upgrade */
	public boolean RemoveBankUpgrade() {
		if (hasBankUpgrade) {
			hasBankUpgrade = false;
			nextGrow = -1;
			this.sendUpdates();
			return true;
		}
		return false;
	}
	
	public boolean HasBankUpgrade() {
		return hasBankUpgrade;
	}
	
	private void growExperience() {
		if (!HasEnoughExperienceForBank())
			return;
		
		if (experience.xpHeld >= xpAtMaxLevel) {
			world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0f, 0.65f, false);
			return;
		}
		
		int bonusExperience = (int) (experience.xpHeld * 0.01f /*Properties.BankUpgrade.growBonus*/);
		if (experience.xpHeld > 0 && bonusExperience == 0) {
			bonusExperience = 1;
		}
		Experience experienceAfterGrow = Experience.GetLevelsFromExperience(experience.xpHeld + bonusExperience);
		if (experienceAfterGrow.levelsHeld >= 100) {
			bonusExperience = xpAtMaxLevel - experience.xpHeld;
		}
		AddExperience(bonusExperience);
		world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0f, 0.75f, false);
	}
	
	public boolean HasEnoughExperienceForBank() {
		return experience.levelsHeld >= 0 /*Properties.BankUpgrade.minLevelGrow*/;
	}
	
	/* Returns true when successfully adds the upgrade */
	public boolean AddPickUpUpgrade() {
		if (!hasPickUpUpgrade) {
			hasPickUpUpgrade = true;
			this.sendUpdates();
			return true;
		}
		return false;
	}
	
	/* Returns true when successfully removes the upgrade */
	public boolean RemovePickUpUpgrade() {
		if (hasPickUpUpgrade) {
			hasPickUpUpgrade = false;
			this.sendUpdates();
			return true;
		}
		return false;
	}
	
	public boolean HasPickUpUpgrade() {
		return hasPickUpUpgrade;
	}
	
	private void pickUpExperience(){
		if (experience.xpHeld >= xpAtMaxLevel) {
			return;
		}
		BlockPos pos1 = pos.add(-5, -5, -5/*-Properties.PickUpUpgrade.radius*/);
		BlockPos pos2 = pos.add(5, 5, 5/*Properties.PickUpUpgrade.radius*/);
		AxisAlignedBB radius = new AxisAlignedBB(pos1, pos2);
		List<EntityXPOrb> orbsInRadius = world.getEntitiesWithinAABB(EntityXPOrb.class, radius);
		if (orbsInRadius.size() <= 0)
			return;
		
		EntityXPOrb entityXPOrb = orbsInRadius.get(0);
		double d1 = (pos.getX() - entityXPOrb.posX) / 8.0d;
        double d2 = (pos.getY() - entityXPOrb.posY) / 8.0d;
        double d3 = (pos.getZ() - entityXPOrb.posZ) / 8.0d;
        double d4 = Math.sqrt(d1 * d1 + d2 * d2 + d3 * d3);
        double d5 = 1.0D - d4;

        if (d5 > 0.0D)
        {
            d5 = d5 * d5;
            entityXPOrb.motionX += d1 / d4 * d5 * 0.1d;
            entityXPOrb.motionY += d2 / d4 * d5 * 0.1d;
            entityXPOrb.motionZ += d3 / d4 * d5 * 0.1d;
        }
        
        //System.out.println(world.isRemote + " " + entityXPOrb.motionX + " " + entityXPOrb.motionY + " " + entityXPOrb.motionZ);
        
        if (!world.isRemote)
        	return;
        
        Vec3d dPos = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        
        if (dPos.addVector(.5d, 0d, .5d).distanceTo(new Vec3d(entityXPOrb.posX, entityXPOrb.posY, entityXPOrb.posZ)) <= 1.05d) {
        	Experience experienceAfterGrow = Experience.GetLevelsFromExperience(experience.xpHeld + entityXPOrb.xpValue);
    		if (experienceAfterGrow.levelsHeld >= 100) {
    			entityXPOrb.xpValue -= xpAtMaxLevel - experience.xpHeld;
    			AddExperience(xpAtMaxLevel - experience.xpHeld);
    		}
    		else {
    			AddExperience(entityXPOrb.xpValue);
    			world.removeEntity(entityXPOrb);
    			orbsInRadius.remove(entityXPOrb);
    		}
    		world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.BLOCKS, 1.0f, MathHelper.nextFloat(world.rand, 0.85f, 1.15f), false);
        }
	}

	public void AddExperience(int amount) {
		if (amount < 0) {
			Experience newExp = new Experience();
			newExp = Experience.GetLevelsFromExperience(experience.xpHeld + amount);
			
			experience.xpHeld += amount;
			experience.levelsHeld = newExp.levelsHeld;
			experience.currentLevelXp = newExp.currentLevelXp;

		} 
		else {
			int i = Integer.MAX_VALUE - experience.xpHeld;

			if (amount > i) {
				amount = i;
			}

			experience.currentLevelXp += (float) amount / (float) Experience.XpBarCap(experience.levelsHeld);

			for (experience.xpHeld += amount; experience.currentLevelXp >= 1.0F; experience.currentLevelXp /= (float) Experience
					.XpBarCap(experience.levelsHeld)) {
				experience.currentLevelXp = (experience.currentLevelXp - 1.0F)
						* (float) Experience.XpBarCap(experience.levelsHeld);
				this.experience.levelsHeld++;
			}
		}
		this.sendUpdates();
	}
	
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}
	
	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		super.onDataPacket(net, pkt);
		handleUpdateTag(pkt.getNbtCompound());
	}
	
	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		super.handleUpdateTag(tag);
		sendUpdates();
	}

	private void sendUpdates() {
		world.markBlockRangeForRenderUpdate(pos, pos);
		world.notifyBlockUpdate(pos, getState(), getState(), 3);
		world.scheduleBlockUpdate(pos,this.getBlockType(),0,0);
		System.out.println(world.isRemote + " " + hasBankUpgrade + " " + hasPickUpUpgrade);
		markDirty();
		BlockXpHolder.setState(hasBankUpgrade, hasPickUpUpgrade, world, pos);
	}
	
	private IBlockState getState() {
		return world.getBlockState(pos);
	}
}