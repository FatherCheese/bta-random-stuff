package cookie.random.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

public class EntityWaypoint extends Entity {
	public int targetBlock;

	public EntityWaypoint(World world) {
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
