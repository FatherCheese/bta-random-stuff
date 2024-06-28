package cookie.random.core.entity;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;

public class BuilderInventory {
	public ItemStack item = new ItemStack(Item.toolPickaxeIron);

	public ItemStack getSelected() {
		return this.item;
	}
}
