package cookie.random.core.newentity.component;

import net.minecraft.core.item.ItemStack;

public class SpawnsItemComponent extends Component {
	private int timer;
	private final ItemStack stack;

	public SpawnsItemComponent(int timer, ItemStack stack) {
		this.timer = timer;
		this.stack = stack;
	}

	@Override
	public void onInit() {
		timer = baseObject.world.rand.nextInt(timer) + timer;
	}

	@Override
	public void tick() {
		if (timer-- <= 0) {
			timer = baseObject.world.rand.nextInt(timer) + timer;
			baseObject.world.dropItem((int) baseObject.x, (int) baseObject.y, (int) baseObject.z, stack);
		}
	}
}
