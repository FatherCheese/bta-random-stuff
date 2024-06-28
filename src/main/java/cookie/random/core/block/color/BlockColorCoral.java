package cookie.random.core.block.color;

import net.minecraft.client.render.block.color.BlockColorCustom;
import net.minecraft.client.render.colorizer.Colorizer;
import net.minecraft.core.world.WorldSource;

public class BlockColorCoral extends BlockColorCustom {
	public BlockColorCoral() {
		super(new Colorizer("coral"));
	}

	@Override
	public int getFallbackColor(int meta) {
		return 0xFFFFFF;
	}

	@Override
	public int getWorldColor(WorldSource world, int x, int y, int z) {
		return x * x * 3187961
			+ x * 987243
			+ y * y * 43297126
			+ y * 987121
			+ z * z * 927469861
			+ z * 1861
			& 16777215;
	}
}
