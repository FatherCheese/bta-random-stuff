package cookie.random.core.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import cookie.random.RandomStuff;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityPathfinder;
import net.minecraft.core.entity.monster.EntityPigZombie;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EntityPigman extends EntityPathfinder {
	private static final boolean SHOULD_LOG_ACTIONS = true;

	private int angerLevel = 0;
	private int useTimer = 20;
	private int timerUntilDoingSomething;
	private int logTimer = 40;
	private int digBuildTime = 10;
	private int checkTimer = 100;
	private ItemStack[] armorInventory = new ItemStack[4];
	private ItemStack[] inventory = new ItemStack[36];

	public List<WeightedRandomLootObject> burningMobDrops = new ArrayList<>();
	public ItemStack heldItem;
	public boolean swinging = false;
	public boolean shouldSwing = false;
	public int swingTime = 0;
	public boolean shouldSearchNearbyItems = false;
	public boolean shouldForage = false;
	public boolean shouldFindAnimals = false;

	public EntityPigman(World world) {
		super(world);
		setHealthRaw(20);
		heldItem = null;
		mobDrops.add(new WeightedRandomLootObject(Item.foodPorkchopRaw.getDefaultStack(), 1, 2));
		mobDrops.add(new WeightedRandomLootObject(heldItem));
		burningMobDrops.add(new WeightedRandomLootObject(Item.foodPorkchopCooked.getDefaultStack(), 1, 2));

		timerUntilDoingSomething = 200 + random.nextInt(200);
	}

	@Override
	public int getMaxHealth() {
		return 100;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/random/textures/entity/pigman/0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/random/textures/entity/pigman/0.png";
	}

	@Override
	public String getLivingSound() {
		return "mob.pig";
	}

	@Override
	protected String getHurtSound() {
		return "mob.pig";
	}

	@Override
	protected String getDeathSound() {
		return "mob.pigdeath";
	}

	public void setHeldItem(ItemStack heldItem) {
		this.heldItem = heldItem;
	}

	@Override
	public ItemStack getHeldItem() {
		return heldItem;
	}

	private void log(String s) {
		timerUntilDoingSomething = 40;
		RandomStuff.LOGGER.info(s);
	}

	private void findRandomItem() {
		log("I'm bored, I'll check if there's a random item nearby!");
		if (angerLevel <= 0 && !dead) {
			List<Entity> nearbyItems = this.world
				.getEntitiesWithinAABBExcludingEntity(
					this, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0)
				);

			if (!nearbyItems.isEmpty() && getHeldItem() == null) {
				for (Entity entity : nearbyItems) {
					if (entity instanceof EntityItem) {
						EntityItem item = (EntityItem) entity;

						if (item.item.stackSize > 0) setTarget(item);

						if (bb.intersectsWith(item.bb)) {
							setHeldItem(item.item);
							item.remove();
							timerUntilDoingSomething = 400 + random.nextInt(200);
						}
					}
				}
			}
		}
	}

	private void findAndEatFood() {
		if (getHealth() < 20) {
			if (logTimer-- <= 0) {
				log("I don't feel so good! I'll try to find something to eat...");
				logTimer = 40;
			}
			List<Entity> nearbyItems = this.world
				.getEntitiesWithinAABB(
					EntityItem.class, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0)
				);
			if (!nearbyItems.isEmpty() && getHeldItem() == null) {
				for (Entity entity : nearbyItems) {

					if (entity instanceof EntityItem) {
						if (((EntityItem) entity).item.getItem() instanceof ItemFood) {
							EntityItem item = (EntityItem) entity;

							if (item.item.stackSize > 0) {
								setTarget(item);
							}

							if (bb.intersectsWith(item.bb)) {
								setHeldItem(item.item);
								item.remove();
							}
						}
					}
				}
			}

			if (heldItem != null && heldItem.getItem() instanceof ItemFood) {
				ItemFood food = (ItemFood) heldItem.getItem();

				if (logTimer-- <= 0) {
					logTimer = 40;
					log("I found some food! Now I'll try to eat it.");
				}

				if (useTimer-- <= 0) {
					useTimer = 20;
					heal(food.getHealAmount());
					setHeldItem(null);

					if (food == Item.foodPorkchopRaw || food == Item.foodPorkchopCooked) {
						world.createExplosion(this, x, y, z, 1);
						hurt(null, 42, DamageType.GENERIC);
					}
				}
			}
		}
	}

	private void findAndEquipArmor() {
		if (heldItem != null && heldItem.getItem() instanceof ItemArmor) {
			if (useTimer-- <= 0) {
				if (getArmorInSlot(((ItemArmor) heldItem.getItem()).armorPiece) == null) {
					armorInventory[((ItemArmor) heldItem.getItem()).armorPiece] = heldItem;
					setHealthRaw(getHealth() + 2 * (((ItemArmor) heldItem.getItem()).material.renderIndex + 1));
				} else {
					EntityItem currentItem = new EntityItem(world, x, y, z, heldItem);
					currentItem.setPos(x, y, z);
					world.entityJoinedWorld(currentItem);
					setHeldItem(null);
				}

				setHeldItem(null);
				useTimer = 20;
			}
		}
	}

	private void checkForZombiePigs() {
		if (checkTimer-- <= 0) {
			checkTimer = 20;
			List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(this, bb.expand(32, 16, 32));

			if (!entityList.isEmpty()) {
				for (Entity entity : entityList) {
					if (entity instanceof EntityPigZombie) {
						EntityPigZombie zombie = (EntityPigZombie) entity;

						if (armorInventory != null) roamRandomPath();
						else entityToAttack = zombie;
					}
				}
			}
		}
	}

	private void checkForAnimals() {

	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();

		findAndEatFood();
		findAndEquipArmor();
		checkForZombiePigs();

		if (swinging) {
			swingTime++;
			if (swingTime >= 8) {
				swingTime = 0;
				swinging = false;
			}
		} else {
			swingTime = 0;
		}
		swingProgress = (float) swingTime / 8.0F;

		if (angerLevel > 0 && entityToAttack != null) {
			if (getHeldItem() != null && !(heldItem.getItem() instanceof ItemToolSword)) {
				EntityItem currentItem = new EntityItem(world, x, y, z, heldItem);
				currentItem.setPos(x, y, z);
				world.entityJoinedWorld(currentItem);
				setHeldItem(null);
			}

			if (!dead && !(heldItem.getItem() instanceof ItemToolSword)) setHeldItem(Item.toolSwordWood.getDefaultStack());

			shouldSwing = !(entityToAttack.distanceTo(this) > 4.0);
			pathToEntity = world.getPathToEntity(this, entityToAttack, 16.0F);
		}

		if (entityToAttack != null) {
			if (!entityToAttack.isAlive()) {
				shouldSwing = false;
				entityToAttack = null;
				angerLevel = 0;
			}
		}
	}

	public void swing() {
		swingTime = -1;
		swinging = true;
	}

	@Override
	public void onLivingUpdate() {
		if (!swinging && shouldSwing) {
			swing();
		}
		super.onLivingUpdate();
	}

	private void becomeAngryAt(Entity entity) {
		entityToAttack = entity;
		angerLevel = 400 + random.nextInt(400);
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		if (super.hurt(attacker, damage, type)) {
			if (passenger != attacker && vehicle != attacker) {
				if (attacker != this) {

					becomeAngryAt(attacker);

					List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(this, bb.expand(32, 16, 32));

					if (!entityList.isEmpty()) {
						for (Entity entity : entityList) {
							if (entity instanceof EntityPigman) {
								EntityPigman pigmen = (EntityPigman) entity;

								pigmen.becomeAngryAt(attacker);
								pigmen.entityToAttack = attacker;
							}
						}
					}
				}
			}

			return true;
		}

		return false;
	}

	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (attackTime <= 0 && distance <= 3.0F && entity.bb.maxY > bb.minY && entity.bb.minY < bb.maxY) {
			if (!(entityToAttack instanceof EntityItem) && entityToAttack.isAlive()) {
				attackTime = 20;
				swing();

				if (getHeldItem() != null) entity.hurt(this, heldItem.getDamageVsEntity(entity), DamageType.COMBAT);
				else entity.hurt(this, 1, DamageType.COMBAT);
			}
		}
	}

	@Override
	protected boolean canDespawn() {
		return false;
	}

	@Override
	protected List<WeightedRandomLootObject> getMobDrops() {
		return remainingFireTicks > 0 ? burningMobDrops : mobDrops;
	}

	public ItemStack getArmorInSlot(int i) {
		return armorInventory[i];
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("AngerLevel", angerLevel);

		ListTag nbtTagList = new ListTag();

		for (int i = 0; i < this.armorInventory.length; i++) {
			if (this.armorInventory[i] != null) {
				CompoundTag armorTag = new CompoundTag();
				armorTag.putByte("Slot", (byte) i);
				this.armorInventory[i].writeToNBT(armorTag);
				nbtTagList.addTag(armorTag);
			}
		}

		if (heldItem != null) {
			CompoundTag heldItemNBT = new CompoundTag();
			getHeldItem().writeToNBT(heldItemNBT);
			tag.putCompound("HeldItem", heldItemNBT);
		}

		tag.put("Items", nbtTagList);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		angerLevel = tag.getInteger("AngerLevel");

		CompoundTag heldItemStack = tag.getCompound("HeldItem");
		if (heldItemStack != null) {
			ItemStack stack = ItemStack.readItemStackFromNbt(heldItemStack);
			if (stack != null) setHeldItem(stack);
		}

		ListTag nbtTagList = tag.getList("Items");
		this.armorInventory = new ItemStack[4];

		for (int i = 0; i < nbtTagList.tagCount(); i++) {
			CompoundTag armorTag = (CompoundTag)nbtTagList.tagAt(i);
			int j = armorTag.getByte("Slot") & 255;
			if (j < this.armorInventory.length) {
				this.armorInventory[j] = ItemStack.readItemStackFromNbt(armorTag);
			}
		}
	}
}
