package cookie.random.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.Minecraft;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityPathfinder;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.util.phys.Vec3d;
import net.minecraft.core.world.World;
import net.minecraft.core.world.pathfinder.Path;

public class EntityBuilder extends EntityPathfinder {
	private static final int MAX_TURN = 30;
	private static final int BUILD_TIME = 10;
	private static final float BUILD_RANGE = 3.0F;
	private static final int MAX_STUCK_COUNT = 20;
	private static final int PUSH_COOLDOWN = 50;
	public static final int SWING_DURATION = 8;
	public Minecraft minecraft;
	public boolean swinging = false;
	public boolean shouldSwing = false;
	public int swingTime = 0;
	public EntityBuilder.State state = EntityBuilder.State.ROAMING;
	public BuilderWaypoint buildTarget;
	public static final BuilderSchematic schema = new BuilderSchematic();
	public static final int xSchemaOrigin = 113;
	public static final int ySchemaOrigin = 72;
	public static final int zSchemaOrigin = 139;
	public int xold;
	public int zold;
	public int xlaststuck;
	public int zlaststuck = 0;
	public int stuckCount = 0;
	public int pushStep = 50;
	public int age = 0;
	public static boolean initialized = false;
	private int buildStep = 0;
	private final boolean verbose = false;
	public BuilderInventory inventory = new BuilderInventory();
	private Path path;

	public EntityBuilder(World world) {
		super(world);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
	}

	public void swing() {
		swingTime = -1;
		swinging = true;
	}

	@Override
	protected Entity findPlayerToAttack() {
		return state == State.MOVING && this.buildTarget != null && !(this.buildTarget.distanceTo(this) < 2.0F) ? this.buildTarget : null;
	}

	public void setBuildTarget(int x, int y, int z, int blockID) {
		if (this.buildTarget != null && this.buildTarget instanceof BuilderWaypoint) {
			buildTarget.remove();
			buildTarget = null;
		}


		BuilderWaypoint waypoint = new BuilderWaypoint(world);
		waypoint.targetBlock = blockID;
		waypoint.setPos(x, y, z);
		this.buildTarget = waypoint;
		this.entityToAttack = this.buildTarget;
		if (this.verbose) {
			System.out.println("Build target set to " + blockID + " at " + x + ", " + y + ", " + z);
		}

		int var6 = (int)Math.floor(this.bb.minY);
		if (buildTarget.y < (double)(var6 - 1)) {
			Vec3d vec3d = Vec3d.createVector(this.x, this.y, this.z).subtract(Vec3d.createVector(this.buildTarget.x, this.buildTarget.y, this.buildTarget.z)).normalize();
			push(vec3d.xCoord * 0.6F, 0.4F, vec3d.zCoord * 0.6F);
		}
	}


	private int getStackHeight(int x, int z) {
		int y = 0;

		while (
			world.getBlockId(x, y + 1, z) != 0
				|| world.getBlockId(x, y + 2, z) != 0
				|| world.getBlockId(x, y + 3, z) != 0
		) {
			y++;
		}

		return y;
	}

	private int getHeighestNeighborStack() {
		int mathX = (int)Math.floor(this.x);
		int mathZ = (int)Math.floor(this.z);
		int var3 = 0;
		if (this.getStackHeight(mathX + 1, mathZ) > var3) {
			var3 = this.getStackHeight(mathX + 1, mathZ);
		}

		if (this.getStackHeight(mathX, mathZ + 1) > var3) {
			var3 = this.getStackHeight(mathX, mathZ + 1);
		}

		if (this.getStackHeight(mathX - 1, mathZ + 1) > var3) {
			var3 = this.getStackHeight(mathX - 1, mathZ + 1);
		}

		if (this.getStackHeight(mathX - 1, mathZ) > var3) {
			var3 = this.getStackHeight(mathX - 1, mathZ);
		}

		if (this.getStackHeight(mathX - 1, mathZ - 1) > var3) {
			var3 = this.getStackHeight(mathX - 1, mathZ - 1);
		}

		if (this.getStackHeight(mathX + 1, mathZ + 1) > var3) {
			var3 = this.getStackHeight(mathX + 1, mathZ + 1);
		}

		if (this.getStackHeight(mathX, mathZ - 1) > var3) {
			var3 = this.getStackHeight(mathX, mathZ - 1);
		}

		if (this.getStackHeight(mathX + 1, mathZ - 1) > var3) {
			var3 = this.getStackHeight(mathX + 1, mathZ - 1);
		}

		return var3;
	}


	public void spawnBuilder() {
		EntityBuilder builder = new EntityBuilder(minecraft.theWorld);
		builder.minecraft = this.minecraft;
		double var2 = 119.0 + Math.random() * 10.0;
		double var4 = 70.0 + Math.random() * 10.0;
		double var6 = 170.0 + Math.random() * 10.0;
		builder.moveTo(var2, var4, var6, 1263.59F, 4.0F);
		if (builder.getCanSpawnHere()) {
			this.minecraft.theWorld.entityJoinedWorld(builder);
		} else {
			this.spawnBuilder();
		}
	}


	@Override
	public void updatePlayerActionState() {
		this.age++;
		if (!initialized) {
			initialized = true;

			for (int var1 = schema.start[0].length - 1; var1 > 0; var1--) {
				for (int var2 = 0; var2 < schema.start.length; var2++) {
					for (int var3 = 0; var3 < schema.start[var2][var1].length; var3++) {
						if (schema.start[var2][var1][var3] != 0) {
							world.setBlockWithNotify(113 + var2, 72 + var1, 139 + var3, schema.start[var2][var1][var3]);
							int var4 = 0;

							while (this.world.getBlockId(113 + var2, 72 + var1 - var4 - 1, 139 + var3) == 0) {
								var4++;
								this.world.setBlockWithNotify(113 + var2, 72 + var1 - var4, 139 + var3, 5);
							}
						}
					}
				}
			}
		}


		this.heartsHalvesLife = 20;
		if (this.pushStep < 50) {
			this.pushStep--;
			if (this.pushStep <= 0) {
				this.pushStep = 50;
			}
		}


		float var24 = 40.0F;
		this.isJumping = false;
		int var25 = (int)Math.floor(this.x);
		int var26 = (int)Math.floor(this.bb.minY);
		int var27 = (int)Math.floor(this.z);
		if (this.age > 3000 && this.random.nextInt(500) == 0) {
			this.world.createExplosion(this, var25, var26, var27, 1.0F);
			this.remove();
			if (world.isNewWorld) {
				this.spawnBuilder();
				this.spawnBuilder();
				this.spawnBuilder();
				this.spawnBuilder();
			}
		}

		if (this.world.getBlockId(var25, var26, var27) != 0 || this.buildTarget != null && this.buildTarget.x == (double)var25 && this.buildTarget.z == (double)var27) {
			this.isJumping = true;
		}


		if (this.pushStep == 50 && this.buildTarget != null && this.random.nextInt(300) == 0) {
			Vec3d var28 = Vec3d.createVector(this.x, this.bb.minY, this.z).subtract(Vec3d.createVector(this.buildTarget.x, this.buildTarget.y, this.buildTarget.z)).normalize();
			this.push(var28.xCoord * 0.5, 0.4F, var28.zCoord * 0.5);
			this.pushStep--;
		}


		if (this.state == State.MOVING) {
			if (this.entityToAttack == null) {
				this.entityToAttack = this.findPlayerToAttack();
			} else if (!this.entityToAttack.isAlive()) {
				this.entityToAttack = null;
			} else {
				float distanceTo = this.entityToAttack.distanceTo(this);
				if (this.canEntityBeSeen(this.entityToAttack)) {
					this.attackBlockedEntity(this.entityToAttack, distanceTo);
				}
			}

			if (this.entityToAttack != null) {
				path = world.getPathToEntity(this, entityToAttack, var24);
			}
		}

		if (this.state == State.MOVING || this.state == State.BUILDING) {
			if (Math.floor(this.x) == this.xold && Math.floor(this.z) == this.zold) {
				this.stuckCount++;
			} else {
				this.stuckCount = 0;
			}

			this.xold = (int) Math.floor(this.x);
			this.zold = (int) Math.floor(this.z);
			if (this.state == State.MOVING && this.path == null || this.stuckCount >= 20) {
				this.state = State.STUCK;
				return;
			}
		}

		if (this.state == State.STUCK) {
			if (this.verbose) {
				System.out.println(System.currentTimeMillis() + " - I think I'm stuck... thinking about solution.");
			}

			if (this.verbose) {
				System.out.println("Checking if heighest neighobur stack (" + this.getHeighestNeighborStack() + ") is > " + var26);
			}

			if (this.getHeighestNeighborStack() > var26) {
				if (this.verbose) {
					System.out.println("Building ourselves up.");
				}

				if (this.world.getBlockId(var25, var26 + 1, var27) != 0) {
					this.world.setBlockWithNotify(var25, var26 + 1, var27, 0);
				}

				if (this.world.getBlockId(var25, var26 + 2, var27) != 0) {
					this.world.setBlockWithNotify(var25, var26 + 2, var27, 0);
				}

				if (this.world.getBlockId(var25, var26 + 3, var27) != 0) {
					this.world.setBlockWithNotify(var25, var26 + 3, var27, 0);
				}

				if (this.pushStep == 50) {
					this.pushStep--;
					this.push(Math.random() * 0.6 - 0.3, 0.4, Math.random() * 0.6 - 0.3);
				}

				this.isJumping = true;
				this.world.setBlockWithNotify(var25, var26, var27, 5);
				this.stuckCount = 0;
				this.state = State.MOVING;
				return;
			}

			if (this.pushStep == 50) {
				if (this.verbose) {
					System.out.println("Nudging ourselves");
				}

				this.pushStep--;
				this.push(Math.random() * 0.6 - 0.3, 0.5, Math.random() * 0.6 - 0.3);
				this.isJumping = true;
				this.stuckCount = 0;
				this.state = State.MOVING;
				return;
			}
		}

		if (this.state == State.MOVING) {
			if (this.entityToAttack == null || this.buildTarget == null) {
				this.entityToAttack = null;
				this.buildTarget = null;
				this.state = State.ROAMING;
				return;
			}

			if (this.entityToAttack.distanceTo(this) <= 3.0F) {
				this.state = State.BUILDING;
				this.faceEntity(this.buildTarget, 30.0F, 30.0F);
				this.entityToAttack = null;
				return;
			}
		} else if (this.state == State.ROAMING) {
			if (this.buildTarget != null && this.buildTarget.distanceTo(this) > 8.0F) {
				this.state = State.MOVING;
				return;
			}

			for (int var30 = 0; var30 < schema.target[0].length; var30++) {
				for (int var6 = 0; var6 < schema.target.length; var6++) {
					for (int var7 = 0; var7 < schema.target[var6][var30].length; var7++) {
						int var8 = this.world.getBlockId(113 + var6, 72 + var30, 139 + var7);
						if (schema.target[var6][var30][var7] != 0 && var8 != schema.target[var6][var30][var7]) {
							int var9 = 0;

							while (this.world.getBlockId(113 + var6, 72 + var30 - var9 - 1, 139 + var7) == 0) {
								var9++;
							}

							if (var9 > 0) {
								if (this.verbose) {
									System.out
										.println(
											"I should build wood foundation "
												+ var9
												+ " steps under target block, at "
												+ (139 + var7)
												+ " -- Foundation: "
												+ (113 + var6)
												+ ", "
												+ (72 + var30 - var9)
												+ ", "
												+ (139 + var7)
												+ "."
										);
								}

								this.setBuildTarget(113 + var6, 72 + var30 - var9, 139 + var7, 5);
							} else {
								if (this.verbose) {
									System.out.println("I should build " + schema.target[var6][var30][var7] + " at " + (113 + var6) + ", " + (72 + var30) + ", " + (139 + var7));
								}

								this.setBuildTarget(113 + var6, 72 + var30, 139 + var7, schema.target[var6][var30][var7]);
							}

							this.state = State.BUILDING;
							if (this.buildTarget != null) {
								this.faceEntity(this.buildTarget, 30.0F, 30.0F);
							}

							return;
						}
					}
				}
			}

			for (int var31 = schema.target[0].length - 1; var31 > 0; var31--) {
				for (int var37 = 0; var37 < schema.target.length; var37++) {
					for (int var42 = 0; var42 < schema.target[var37][var31].length; var42++) {
						int var47 = this.world.getBlockId(113 + var37, 72 + var31, 139 + var42);
						if (schema.target[var37][var31][var42] == 0 && var47 != schema.target[var37][var31][var42]) {
							if (this.verbose) {
								System.out.println("I should build " + schema.target[var37][var31][var42] + " at " + (113 + var37) + ", " + (72 + var31) + ", " + (139 + var42));
							}

							this.setBuildTarget(113 + var37, 72 + var31, 139 + var42, schema.target[var37][var31][var42]);
							this.state = State.BUILDING;
							if (this.buildTarget != null) {
								this.faceEntity(this.buildTarget, 30.0F, 30.0F);
							}

							return;
						}
					}
				}
			}

			if (this.verbose) {
				System.out.println("Looks like schematic is complete, let's see if there's any excess wood to destroy");
			}

			byte var32 = 15;

			for (int var38 = schema.target[0].length + var32; var38 >= -(schema.target[0].length + var32); var38--) {
				for (int var43 = -var32; var43 < schema.target.length + var32; var43++) {
					for (int var48 = -var32; var48 < schema.target[0][0].length + var32; var48++) {
						int var53 = this.world.getBlockId(113 + var43, 72 + var38, 139 + var48);
						if (var53 == 5) {
							int var10 = 0;

							while (this.world.getBlockId(113 + var43, 72 + var38 - var10 - 1, 139 + var48) == 0) {
								var10++;
							}

							if (var10 > 0) {
								this.setBuildTarget(113 + var43, 72 + var38 - var10, 139 + var48, 5);
							} else {
								this.setBuildTarget(113 + var43, 72 + var38, 139 + var48, 0);
							}

							this.state = State.BUILDING;
							if (this.buildTarget != null) {
								this.faceEntity(this.buildTarget, 30.0F, 30.0F);
							}

							return;
						}
					}
				}
			}

			this.entityToAttack = null;
			this.buildTarget = null;
			if (this.verbose) {
				System.out.println("Forever roaming");
			}
		} else if (this.state == State.BUILDING) {
			if (this.buildTarget == null) {
				this.state = State.ROAMING;
				return;
			}

			if (this.buildTarget.distanceTo(this) > 3.0F) {
				this.shouldSwing = false;
				this.onGround = false;
				this.state = State.MOVING;
				return;
			}

			this.onGround = true;
			this.shouldSwing = true;
			this.isJumping = false;
			if (this.buildStep < 10) {
				this.buildStep++;
			} else {
				this.buildStep = 0;
				if (this.buildStep == 0 && this.verbose) {
					System.out
						.println("Building at " + (int)Math.floor(this.buildTarget.x) + ", " + (int)Math.floor(this.buildTarget.y) + ", " + (int)Math.floor(this.buildTarget.z));
				}

				int var33 = (int)Math.floor(this.buildTarget.x);
				int var39 = (int)Math.floor(this.buildTarget.y);
				int var44 = (int)Math.floor(this.buildTarget.z);
				int var49 = this.world.getBlockId(var33, var39, var44);
				if (var49 != 0 && this.buildTarget.targetBlock != var49) {
					if (this.verbose) {
						System.out.println("This tile is something it shouldn't be, destroying it...");
					}

					if (this.buildTarget.targetBlock != 0) {
						Block.blocksList[var49].onBlockRemoved(world, var33, var39, var44, world.getBlockMetadata(var33, var39, var44));
					}

					this.world.setBlockWithNotify(var33, var39, var44, 0);
					if (this.buildTarget.targetBlock == 0) {
						this.stuckCount = 0;
						this.shouldSwing = false;
						this.onGround = false;
						this.buildTarget = null;
						this.state = State.ROAMING;
					}

					return;
				}

				if (this.buildTarget.targetBlock != 0 && !this.world.canBlockBePlacedAt(this.buildTarget.targetBlock, var33, var39, var44, false, Side.NONE)) {
					if (this.verbose) {
						System.out.println("May not place here, jumping");
					}

					if (this.pushStep == 50) {
						this.pushStep--;
						this.push(0.0, 0.8F, 0.0);
					}

					this.isJumping = true;
				} else {
					if (this.verbose) {
						System.out.println("Placing block");
					}

					this.world.setBlockWithNotify(var33, var39, var44, this.buildTarget.targetBlock);
				}

				var49 = this.world.getBlockId(var33, var39, var44);
				if (this.buildTarget.targetBlock == var49) {
					if (this.verbose) {
						System.out.println("Done building, going to roaming.");
					}

					this.stuckCount = 0;
					this.shouldSwing = false;
					this.onGround = false;
					this.buildTarget = null;
					this.state = State.ROAMING;
					return;
				}
			}
		}

		if (this.swinging) {
			this.swingTime++;
			if (this.swingTime == 8) {
				this.swingTime = 0;
				this.swinging = false;
			}
		} else {
			this.swingTime = 0;
		}

		this.swingProgress = (float)this.swingTime / 8.0F;
		if (this.entityToAttack == null) {
			this.entityToAttack = this.findPlayerToAttack();
			if (this.entityToAttack != null) {
				this.path = this.world.getPathToEntity(this, this.entityToAttack, var24);
			}
		} else if (!this.entityToAttack.isAlive()) {
			this.entityToAttack = null;
		} else {
			float var34 = this.entityToAttack.distanceTo(this);
			if (this.canEntityBeSeen(this.entityToAttack)) {
				this.attackBlockedEntity(this.entityToAttack, var34);
			}
		}

		if (this.onGround) {
			this.moveStrafing = 0.0F;
			this.moveForward = 0.0F;
			this.isJumping = false;
		} else {
			if (this.onGround || this.entityToAttack == null || this.path != null && this.random.nextInt(20) != 0) {
				if (this.path == null && this.random.nextInt(80) == 0 || this.random.nextInt(80) == 0) {
					boolean var35 = false;
					int var40 = -1;
					int var45 = -1;
					int var51 = -1;
					float var54 = -99999.0F;

					for (int var56 = 0; var56 < 10; var56++) {
						int var11 = (int) Math.floor(this.x + (double)this.random.nextInt(13) - 6.0);
						int var12 = (int) Math.floor(this.y + (double)this.random.nextInt(7) - 3.0);
						int var13 = (int) Math.floor(this.z + (double)this.random.nextInt(13) - 6.0);
						float var14 = this.getBlockPathWeight(var11, var12, var13);
						if (var14 > var54) {
							var54 = var14;
							var40 = var11;
							var45 = var12;
							var51 = var13;
							var35 = true;
						}
					}

					if (var35) {
						this.path = this.world.getEntityPathToXYZ(this, var40, var45, var51, 10.0F);
					}
				}
			} else {
				this.path = this.world.getPathToEntity(this, this.entityToAttack, var24);
			}

			int var36 = (int) Math.floor(this.bb.minY);
			boolean var41 = this.isInWater();
			boolean var46 = this.isInLava();
			this.xRot = 0.0F;
			if (this.path != null && this.random.nextInt(100) != 0) {
				Vec3d var52 = this.path.getPos(this);
				double var55 = (double)(this.bbWidth * 2.0F);

				while (var52 != null && var52.squareDistanceTo(this.x, var52.yCoord, this.z) < var55 * var55) {
					this.path.next();
					if (this.path.isDone()) {
						var52 = null;
						this.path = null;
					} else {
						var52 = this.path.getPos(this);
					}
				}

				this.isJumping = false;
				if (var52 != null) {
					double var57 = var52.xCoord - this.x;
					double var58 = var52.zCoord - this.z;
					double var15 = var52.yCoord - (double)var36;
					float var17 = (float)(Math.atan2(var58, var57) * 180.0 / (float) Math.PI) - 90.0F;
					float var18 = var17 - this.yRot;
					this.moveForward = this.moveSpeed;

					while (var18 < -180.0F) {
						var18 += 360.0F;
					}

					while (var18 >= 180.0F) {
						var18 -= 360.0F;
					}

					if (var18 > 30.0F) {
						var18 = 30.0F;
					}

					if (var18 < -30.0F) {
						var18 = -30.0F;
					}

					this.yRot += var18;
					if (this.onGround && this.entityToAttack != null) {
						double var19 = this.entityToAttack.x - this.x;
						double var21 = this.entityToAttack.z - this.z;
						float var23 = this.yRot;
						this.yRot = (float)(Math.atan2(var21, var19) * 180.0 / (float) Math.PI) - 90.0F;
						var18 = (var23 - this.yRot + 90.0F) * (float) Math.PI / 180.0F;
						this.moveStrafing = (float) (-Math.sin(var18) * this.moveForward * 1.0F);
						this.moveForward = (float) (Math.cos(var18) * this.moveForward * 1.0F);
					}

					if (var15 > 0.0) {
						this.isJumping = true;
					}
				}

				if (this.entityToAttack != null) {
					this.faceEntity(this.entityToAttack, 30.0F, 30.0F);
				}

				if (this.horizontalCollision) {
					this.isJumping = true;
				}

				if (this.random.nextFloat() < 0.8F && (var41 || var46)) {
					this.isJumping = true;
				}
			} else {
				super.updatePlayerActionState();
				this.path = null;
			}
		}
	}

	@Override
	public ItemStack getHeldItem() {
		return inventory.getSelected();
	}

	@Override
	public void onLivingUpdate() {
		if (!swinging && shouldSwing) {
			this.swing();
		}

		super.onLivingUpdate();
	}

	@Override
	protected float getBlockPathWeight(int x, int y, int z) {
		return 10.0F;
	}

	public enum State {
		BUILDING,
		MOVING,
		ROAMING,
		STUCK
	}
}
