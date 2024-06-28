package cookie.random.core.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.WorldSource;

public class BlockCoral extends Block {
	public BlockCoral(String key, int id) {
		super(key, id, Material.coral);
		float var3 = 0.0625F;
		setBlockBounds(0.0F - var3, 0.0F - var3, 0.0F - var3, 1.0F + var3, 1.0F + var3, 1.0F + var3);
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public boolean getIsBlockSolid(WorldSource blockAccess, int x, int y, int z, Side side) {
		return false;
	}
}
