package cookie.random.core.block;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;

public class CogEntity extends TileEntity {
	public boolean isClockwise = true;
	public float currentRotation;
	public float oldRotation;
	public float rotationTarget;
	private float currentRotationSpeed;

	public void updateNeighbourTile() {
		for (int blockEntityX = x - 1; blockEntityX <= x + 1; blockEntityX++) {
			if (blockEntityX != x) {
				TileEntity blockEntityXs = worldObj.getBlockTileEntity(blockEntityX, y, z);
				if (blockEntityXs instanceof CogEntity) {
					if (currentRotationSpeed >= 0) {
						((CogEntity) blockEntityXs).rotationTarget = rotationTarget;
						((CogEntity) blockEntityXs).currentRotationSpeed = currentRotationSpeed;
					}
				}
			}
		}
	}

	@Override
	public void tick() {
		oldRotation = currentRotation %= 360;

		if (rotationTarget > 0 && currentRotationSpeed < rotationTarget) {
			currentRotationSpeed += 0.25f;
		} else if (rotationTarget <= 0 && currentRotationSpeed > 0) {
			currentRotationSpeed -= 0.25f;
		}

		if (currentRotationSpeed > 0) {
			currentRotation -= (isClockwise ? 0.25f : -0.25f) * currentRotationSpeed;
		}

		updateNeighbourTile();

		System.out.printf("Entity at %d %d %d is %f%n", x, y, z, currentRotation);
	}

	@Override
	public void writeToNBT(CompoundTag tag) {
		super.writeToNBT(tag);
		tag.putBoolean("IsClockwise", isClockwise);
		tag.putFloat("CurrentRotation", currentRotation);
	}

	@Override
	public void readFromNBT(CompoundTag tag) {
		super.readFromNBT(tag);
		isClockwise = tag.getBoolean("IsClockwise");
		currentRotation = tag.getFloat("CurrentRotation");
	}
}
