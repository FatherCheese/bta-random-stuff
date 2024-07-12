package cookie.random.extra.mixin;

import cookie.random.core.entity.EntityPigman;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityPigZombie;
import net.minecraft.core.entity.monster.EntityZombie;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.List;

@Mixin(value = EntityPigZombie.class, remap = false)
public abstract class EntityPigZombieMixin extends EntityZombie {

	public EntityPigZombieMixin(World world) {
		super(world);
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		List<Entity> entityList = world.getEntitiesWithinAABBExcludingEntity(this, bb.expand(32.0, 16.0, 32.0));

		if (!entityList.isEmpty()) {
			for (Entity entity : entityList) {
				if (entity instanceof EntityPigman) entityToAttack = entity;
			}
		}
	}
}
