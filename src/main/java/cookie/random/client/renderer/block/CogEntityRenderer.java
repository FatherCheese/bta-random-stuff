package cookie.random.client.renderer.block;

import cookie.random.RandomStuff;
import cookie.random.client.renderer.entity.CogModel;
import cookie.random.core.block.CogEntity;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.client.render.tessellator.Tessellator;
import net.minecraft.client.render.tileentity.TileEntityRenderer;
import net.minecraft.core.block.Block;
import net.minecraft.core.util.helper.MathHelper;
import org.lwjgl.opengl.GL11;
import org.useless.dragonfly.helper.ModelHelper;

import static cookie.random.RandomStuff.MOD_ID;

public class CogEntityRenderer extends TileEntityRenderer<CogEntity> {
	private final ModelBase model = ModelHelper.getOrCreateEntityModel(MOD_ID,
		"entity/small_cog.json",
		CogModel.class);

	@Override
	public void doRender(Tessellator tessellator, CogEntity blockEntity, double x, double y, double z, float partialTick) {
		float scale = 0.0625f;

		GL11.glPushMatrix();
		GL11.glDisable(2884);
		GL11.glTranslated(x + 0.5, y + 0.5f, z + 0.5);

		try {
			GL11.glEnable(32826);
			GL11.glRotatef(90, 0, 0, 0.5f);
			this.loadTexture("/assets/random/textures/entity/small_cog/1.png");
			GL11.glEnable(3008);
			GL11.glDisable(32826);
		} catch (Exception e) {
			RandomStuff.LOGGER.error(e.getMessage(), e);
		}

		float rotVisual = MathHelper.lerp(blockEntity.oldRotation, blockEntity.currentRotation, partialTick);


		Block block = blockEntity.worldObj.getBlock(blockEntity.x, blockEntity.y, blockEntity.z);
		if (block != null) {
			int meta = blockEntity.worldObj.getBlockMetadata(blockEntity.x, blockEntity.y, blockEntity.z);

			if (meta == 0 || meta == 1) {
				GL11.glRotatef(90, 0, 1, 0);

				GL11.glRotatef(rotVisual, 0, 0, 1);

			} else if (meta == 2 || meta == 3) {

			} else if (meta == 4 || meta == 5) {
				GL11.glRotatef(90, 1, 0, 0);
			}
		}

		GL11.glTranslatef(0, -16 * scale, 0);
		model.render(0, 0, 1, 0, 0, scale);

		GL11.glEnable(2884);
		GL11.glPopMatrix();
	}
}
