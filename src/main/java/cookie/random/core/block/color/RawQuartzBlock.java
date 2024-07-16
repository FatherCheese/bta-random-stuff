package cookie.random.core.block.color;

import cookie.random.core.RSBlocks;
import net.minecraft.core.block.BlockTransparent;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.enums.LightLayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

import java.util.Random;

public class RawQuartzBlock extends BlockTransparent {
	public RawQuartzBlock(String key, int id) {
		super(key, id, Material.glass);
		setTicking(true);
	}

	@Override
	public int getRenderBlockPass() {
		return 1;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		updateTick(world, x, y, z, world.rand);
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int lightLevel = world.getSavedLightValue(LightLayer.Block, x, y, z);
		world.setBlockMetadataWithNotify(x, y, z, Math.max(lightLevel, 0));
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		switch (dropCause) {
			case PICK_BLOCK:
			case SILK_TOUCH:
				return new ItemStack[]{RSBlocks.RAW_QUARTZ.getDefaultStack()};
			default:
				return new ItemStack[]{new ItemStack(Item.quartz, world.rand.nextInt(4) + 1)};
		}
	}
}
