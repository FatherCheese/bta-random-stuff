package cookie.random.core.block;

import cookie.random.client.renderer.block.BlockColorCoral;
import net.minecraft.core.block.Block;
import turniplabs.halplibe.helper.BlockBuilder;

import static cookie.random.RandomStuff.MOD_ID;

public class RSBlocks {

	public static Block CORAL;

	public static void initializeBlocks() {
		CORAL = new BlockBuilder(MOD_ID)
			.setTextures("random:block/coral")
			.setBlockColor(block -> new BlockColorCoral())
			.build(new BlockCoral("coral", 1100));
	}
}
