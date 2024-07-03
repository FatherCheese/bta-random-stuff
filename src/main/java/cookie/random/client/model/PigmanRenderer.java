package cookie.random.client.model;

import cookie.random.core.entity.EntityPigman;
import net.minecraft.client.render.block.model.BlockModel;
import net.minecraft.client.render.entity.LivingRenderer;
import net.minecraft.client.render.item.model.ItemModelDispatcher;
import net.minecraft.client.render.model.ModelBiped;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemArmor;
import net.minecraft.core.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class PigmanRenderer extends LivingRenderer<EntityPigman> {
	private final PigmanModelVanilla model;
	private final PigmanModelVanilla modelArmor;
	private final PigmanModelVanilla modelArmorLeggings;

	public PigmanRenderer(PigmanModelVanilla model, float shadowSize) {
		super(model, shadowSize);
		this.model = model;
		this.modelArmor = new PigmanModelVanilla(1.0F);
		this.modelArmorLeggings = new PigmanModelVanilla(0.5F);
	}

	private void hideArmorPiece(int piece) {
		switch (piece) {
			case 0:
				modelArmor.bipedHead.showModel = false;
				modelArmor.bipedHeadOverlay.showModel = false;
				break;
			case 1:
				modelArmor.bipedBody.showModel = false;
				modelArmor.bipedLeftArm.showModel = false;
				modelArmor.bipedRightArm.showModel = false;
				break;
			case 2:
				modelArmorLeggings.bipedBody.showModel = false;
				modelArmorLeggings.bipedLeftLeg.showModel = false;
				modelArmorLeggings.bipedRightLeg.showModel = false;
				break;
			case 3:
				modelArmor.bipedLeftLeg.showModel = false;
				modelArmor.bipedRightLeg.showModel = false;
				break;
		}
	}

	private boolean setArmorModel(EntityPigman entity, int renderPass, float partialTick) {
		ItemStack armorStack = entity.getArmorInSlot(3 - renderPass);
		if (armorStack != null) {
			Item item = armorStack.getItem();

			if (item instanceof ItemArmor) {
				ItemArmor itemarmor = (ItemArmor) item;
				this.loadTexture(
					String.format(
						"/assets/random/textures/armor/pigman/%s_%d.png",
						itemarmor.material.identifier.value,
						renderPass != 1 ? 1 : 2
					)
				);
				ModelBiped modelbiped = renderPass != 1 ? this.modelArmor : this.modelArmorLeggings;
				modelbiped.bipedHead.showModel = renderPass == 3;
				modelbiped.bipedHeadOverlay.showModel = renderPass == 3;
				modelbiped.bipedBody.showModel = renderPass == 2 || renderPass == 1;
				modelbiped.bipedLeftArm.showModel = renderPass == 2;
				modelbiped.bipedRightArm.showModel = renderPass == 2;
				modelbiped.bipedLeftLeg.showModel = renderPass == 0 || renderPass == 1;
				modelbiped.bipedRightLeg.showModel = renderPass == 0 || renderPass == 1;
				this.setRenderPassModel(modelbiped);
				return true;
			}
		}

		return false;
	}

	@Override
	public void renderEquippedItems(EntityPigman entity, float partialTick) {
		ItemStack stack = entity.getHeldItem();
		if (stack != null) {
			GL11.glPushMatrix();
			model.bipedRightArm.postRender(0.0625F);
			BlockModel.setRenderBlocks(this.renderDispatcher.itemRenderer.renderBlocksInstance);
			ItemModelDispatcher.getInstance()
				.getDispatch(stack)
				.renderItemThirdPerson(Tessellator.instance, this.renderDispatcher.itemRenderer, entity, stack, true);
			GL11.glPopMatrix();
		}
	}

	@Override
	protected boolean shouldRenderPass(EntityPigman entity, int renderPass, float partialTick) {
		super.shouldRenderPass(entity, renderPass, partialTick);
		return setArmorModel(entity, renderPass, partialTick);
	}
}
