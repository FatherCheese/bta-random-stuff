package cookie.random.core;

import cookie.random.client.renderer.block.BlockColorCoral;
import cookie.random.core.block.BlockCoral;
import cookie.random.core.block.color.RawQuartzBlock;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import turniplabs.halplibe.helper.BlockBuilder;

import static cookie.random.RandomStuff.MOD_ID;

public class RSBlocks {

	public static Block CORAL;
	public static Block RAW_QUARTZ;

	public static void initializeBlocks() {
		CORAL = new BlockBuilder(MOD_ID)
			.setTextures("random:block/coral")
			.setBlockColor(block -> new BlockColorCoral())
			.build(new BlockCoral("coral", 1100));

		RAW_QUARTZ = new BlockBuilder(MOD_ID)
			.setTextures("random:block/raw_quartz")
			.setTags(BlockTags.MINEABLE_BY_PICKAXE)
			.build(new RawQuartzBlock("raw_quartz", 1101))
			.withLightBlock(3);
	}
}
