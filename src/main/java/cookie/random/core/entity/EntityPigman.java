package cookie.random.core.entity;

import com.mojang.nbt.CompoundTag;
import com.mojang.nbt.ListTag;
import cookie.random.RandomStuff;
import net.minecraft.core.WeightedRandomLootObject;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityPathfinder;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.tool.ItemToolSword;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

public class EntityPigman extends EntityPathfinder {
	private static final boolean SHOULD_LOG_ACTIONS = true;

	private int angerLevel = 0;
	private int useTimer = 20;
	private int logTimer = 40;
	private int digBuildTime = 10;
	private int checkTimer = 100;
	private boolean currentlyDoingSomething = false;
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
		RandomStuff.LOGGER.info(s);
	}

	private void selectRandomAction() {
		if (angerLevel <= 0 && !currentlyDoingSomething && random.nextInt(20) == 0) {
			int randomAction = random.nextInt(3);
			switch (randomAction) {
				case 0:
					logTimer = 0;
					log("I love wandering!");
					break;
				case 1:
					shouldSearchNearbyItems = true;
					currentlyDoingSomething = true;
					break;
			}
		}
	}

	private void attempt(int attemptTimes, Runnable runnable) {
		if (!dead) {
			for (int i = 0; i < attemptTimes; i++) {
				runnable.run();
			}
		}
	}

	private void putAwayHeldItem() {
		if (heldItem != null) {
			for (int i = 0; i < inventory.length; i++) {
				ItemStack stackInSlot = inventory[i];
				if (stackInSlot != null && stackInSlot.canStackWith(heldItem)) {
					int transferAmount = Math.min(getHeldItem().stackSize, stackInSlot.getMaxStackSize() - stackInSlot.stackSize);
					stackInSlot.stackSize += transferAmount;
					getHeldItem().stackSize -= transferAmount;
					stackInSlot.animationsToGo = 5;
				} else {
					if (heldItem != null) {
						int transferAmount = Math.min(getHeldItem().stackSize, heldItem.getMaxStackSize());
						this.inventory[i] = getHeldItem().copy();
						this.inventory[i].stackSize = transferAmount;
						this.inventory[i].animationsToGo = 5;
						getHeldItem().stackSize -= transferAmount;
					}
				}
			}
		}
	}

	private void searchNearbyItems() {
		log("I'm bored. I wonder if there's anything nearby?");
		if (angerLevel <= 0.0F) {
			attempt(4, () -> {
				List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(this, bb.expand(16, 8, 16));

				if (!entityList.isEmpty()) {
					for (Entity entity : entityList) {
						if (entity instanceof EntityItem) {
							EntityItem item = (EntityItem) entity;
							if (logTimer-- <= 0) {
								log("I see an item!");
								logTimer = 20;
							}

							setTarget(entity);

							if (getTarget().distanceTo(this) < 4.0F) {
								if (heldItem != null) putAwayHeldItem();

								heldItem = new ItemStack(item.item.getItem(), item.item.stackSize, item.item.getMetadata());
								item.remove();

								shouldSearchNearbyItems = false;
								currentlyDoingSomething = false;
							}
						}
					}
				} else log("I don't see anything. Back to wandering.");
			});
		}
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		selectRandomAction();

		if (shouldSearchNearbyItems) searchNearbyItems();

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

		ListTag armorInvList = new ListTag();
		ListTag invList = new ListTag();

		for (int i = 0; i < armorInventory.length; i++) {
			if (armorInventory[i] != null) {
				CompoundTag armorTag = new CompoundTag();
				armorTag.putByte("ArmorSlot", (byte) i);
				armorInventory[i].writeToNBT(armorTag);
				armorInvList.addTag(armorTag);
			}
		}

		for (int i = 0; i < inventory.length; i++) {
			if (inventory[i] != null) {
				CompoundTag itemTag = new CompoundTag();
				itemTag.putByte("InvSlot", (byte) i);
				inventory[i].writeToNBT(itemTag);
				invList.addTag(itemTag);
			}
		}

		if (heldItem != null) {
			CompoundTag heldItemNBT = new CompoundTag();
			getHeldItem().writeToNBT(heldItemNBT);
			tag.putCompound("HeldItem", heldItemNBT);
		}

		tag.put("ArmorItems", armorInvList);
		tag.put("InvItems", invList);
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

		ListTag armorInvList = tag.getList("ArmorItems");
		ListTag invList = tag.getList("InvItems");
		this.armorInventory = new ItemStack[4];
		this.inventory = new ItemStack[36];

		for (int i = 0; i < armorInvList.tagCount(); i++) {
			CompoundTag armorTag = (CompoundTag)armorInvList.tagAt(i);
			int j = armorTag.getByte("ArmorSlot") & 255;
			if (j < this.armorInventory.length) {
				this.armorInventory[j] = ItemStack.readItemStackFromNbt(armorTag);
			}
		}

		for (int i = 0; i < invList.tagCount(); i++) {
			CompoundTag armorTag = (CompoundTag)invList.tagAt(i);
			int j = armorTag.getByte("InvSlot") & 255;
			if (j < this.inventory.length) {
				inventory[j] = ItemStack.readItemStackFromNbt(armorTag);
			}
		}
	}
}
