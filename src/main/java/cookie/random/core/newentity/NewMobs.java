package cookie.random.core.newentity;

import cookie.random.core.newentity.component.SpawnsItemComponent;
import net.minecraft.core.item.Item;

public class NewMobs {
	public static NewMob CHICKEN;

	public static void initializeNewMobs() {
		CHICKEN = new NewMob(null, "new_chicken");
		CHICKEN.addComponent(new SpawnsItemComponent(100, Item.eggChicken.getDefaultStack()));
	}
}
