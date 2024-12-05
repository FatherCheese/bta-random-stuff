package cookie.random.core;

import cookie.random.client.renderer.block.CogEntityRenderer;
import cookie.random.core.block.CogBlock;
import cookie.random.core.block.CogEntity;
import net.minecraft.client.render.block.model.BlockModelEmpty;
import net.minecraft.core.block.Block;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.EntityHelper;

import static cookie.random.RandomStuff.MOD_ID;

public class RSBlocks {

	public static final Block COG = new BlockBuilder(MOD_ID)
		.setBlockModel(BlockModelEmpty::new)
		.build(new CogBlock("small_cog", 1400));

	public static void initializeBlocks() {
		EntityHelper.createSpecialTileEntity(
			CogEntity.class,
			"small_cog",
			CogEntityRenderer::new
		);
	}
}
