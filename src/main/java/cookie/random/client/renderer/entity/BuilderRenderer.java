package cookie.random.client.renderer.entity;

import cookie.random.core.entity.EntityBuilder;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.model.ModelPlayer;

public class BuilderRenderer extends LivingRenderer<EntityBuilder> {
	public BuilderRenderer() {
		super(new ModelPlayer(0.0F), 0.5F);
	}
}
