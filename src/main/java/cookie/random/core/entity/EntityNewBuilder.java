package cookie.random.core.entity;

import cookie.random.RandomStuff;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityPathfinder;
import net.minecraft.core.world.World;

public class EntityNewBuilder extends EntityPathfinder {
	private int swingTime = 20;
	private boolean isSwinging = false;
	private EntityWaypoint waypoint;
	private boolean log = true;

	public static final BuilderSchematic SCHEMATIC = new BuilderSchematic();

	public EntityNewBuilder(World world) {
		super(world);
		setSize(0.6F, 1.8F);
	}

	public void swing() {
		if (swingTime <= 0 && !isSwinging) {
			swingTime = 20;
			isSwinging = true;
		} else {
			isSwinging = false;
		}
	}

	@Override
	protected Entity findPlayerToAttack() {
		return (waypoint != null && waypoint.distanceTo(this) < 5.0F) ? waypoint : null;
	}

	private void setWaypoint(int x, int y, int z, int targetBlock) {
		if (entityToAttack != null && entityToAttack instanceof EntityWaypoint) {
			waypoint.remove();
			entityToAttack = null;
		}

		waypoint = new EntityWaypoint(world);
		waypoint.targetBlock = targetBlock;
		waypoint.setPos(x, y, z);
		world.entityJoinedWorld(waypoint);

		if (log) RandomStuff.LOGGER.info("Set build target to block ID {}, at {} {} {}", targetBlock, x, y, z);
	}

	private double getStackHeight(double x, double z) {
		double height = y;

		while (world.getBlock((int) x, (int) (height + 1), (int) z) != null ||
		world.getBlock((int) x, (int) (height + 2), (int) z) != null ||
		world.getBlock((int) x, (int) (height + 3), (int) z) != null) {
			height++;
		}

		return height;
	}

	private double getHighestStack() {
		double stX = Math.floor(x);
		double stY = y;
		double stZ = Math.floor(z);

		if (getStackHeight(stX, stZ) > stY) {

		}

		return 0.0;
	}

	public enum BuilderStates {
		BUILDING,
		DIGGING,
		MOVING,
		ROAMING,
		STUCK
	}
}
