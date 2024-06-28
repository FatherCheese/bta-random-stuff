package cookie.random.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

public class BuilderWaypoint extends Entity {
	public int targetBlock;

	public BuilderWaypoint(World world) {
		super(world);
	}

	@Override
	protected void init() {

	}

	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	public boolean isAlive() {
		return true;
	}
}
