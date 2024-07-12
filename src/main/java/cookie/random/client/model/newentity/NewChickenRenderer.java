package cookie.random.client.model.newentity;

import cookie.random.RandomStuff;
import cookie.random.core.newentity.NewMob;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import org.lwjgl.opengl.GL11;

public class NewChickenRenderer extends EntityRenderer<NewMob> {
	private final ModelBase model;

	public NewChickenRenderer(ModelBase model) {
		this.model = model;
	}

	protected void translateModel(NewMob entity, double x, double y, double z) {
		GL11.glTranslatef((float)x, (float)y, (float)z);
	}

	public void loadEntityTexture(NewMob entity) {
		if (!Minecraft.getMinecraft(this).gameSettings.mobVariants.value)
			this.loadTexture(entity.getEntityTexture());
	}

	@Override
	public void doRender(Tessellator tessellator, NewMob entity, double x, double y, double z, float yaw, float partialTick) {
		GL11.glPushMatrix();
		GL11.glDisable(2884);

		try {
			this.translateModel(entity, x, y, z);
			float scale = 0.0625F;
			GL11.glEnable(32826);
			GL11.glScalef(-1.0F, -1.0F, 1.0F);

			GL11.glTranslatef(0.0F, -24.0F * scale - 0.0078125F, 0.0F);

			this.loadEntityTexture(entity);
			GL11.glEnable(3008);
			model.render(1, 1, 1, 1, 1, scale);
		} catch (Exception e) {
			RandomStuff.LOGGER.error(e.getMessage(), e);
		}

		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}
}
