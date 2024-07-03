package cookie.random.client.model;

import cookie.random.core.entity.EntityBuilder;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.model.ModelPlayer;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class BuilderRenderer extends LivingRenderer<EntityBuilder> {
	private final ModelPlayer modelBipedMain = (ModelPlayer)this.mainModel;

	public BuilderRenderer() {
		super(new ModelPlayer(0.0F), 0.5F);
	}

	@Override
	protected void renderEquippedItems(EntityBuilder entity, float f) {
		ItemStack stack = entity.getHeldItem();
		if (stack != null) {
			GL11.glPushMatrix();
			modelBipedMain.bipedRightArm.postRender(0.0625F);
			BlockModel.setRenderBlocks(this.renderDispatcher.itemRenderer.renderBlocksInstance);
			ItemModelDispatcher.getInstance()
				.getDispatch(stack)
				.renderItemThirdPerson(Tessellator.instance, this.renderDispatcher.itemRenderer, entity, stack, true);
			GL11.glPopMatrix();
		}
	}
}
