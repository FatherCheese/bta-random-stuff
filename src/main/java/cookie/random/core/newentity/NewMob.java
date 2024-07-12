package cookie.random.core.newentity;

import com.mojang.nbt.CompoundTag;
import cookie.random.RandomStuff;
import cookie.random.core.newentity.component.Component;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.world.World;

import java.util.ArrayList;
import java.util.List;

public class NewMob extends Entity {
	public final String name;
	private final List<Component> componentList;

	public NewMob(World world, String name) {
		super(world);
		this.name = name;
		componentList = new ArrayList<>();
	}

	public <T extends Component> T getComponent(Class<T> componentClass) {
		for (Component c : componentList) {
			if (componentClass.isAssignableFrom(c.getClass())) {
				try {
					return componentClass.cast(c);
				} catch (ClassCastException e) {
					RandomStuff.LOGGER.error(e.getMessage(), e);
					assert false : "Error casting NewMob component!";
				}
			}
		}

		return null;
	}

	public <T extends Component> void removeComponent(Class<T> componentClass) {
		for (int i = 0; i < componentList.size(); i++) {
			Component c = componentList.get(i);
			if (componentClass.isAssignableFrom(c.getClass())) {
				componentList.remove(i);
				return;
			}
		}
	}

	public void addComponent(Component c) {
		componentList.add(c);
		c.baseObject = this;
	}

	public void tick() {
		for (int i = 0; i < componentList.size(); i++) {
			componentList.get(i).tick();
		}
	}

	public void init() {
		for (int i = 0; i < componentList.size(); i++) {
			componentList.get(i).onInit();
		}
	}


	@Override
	public void readAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	public void addAdditionalSaveData(CompoundTag compoundTag) {

	}
}
