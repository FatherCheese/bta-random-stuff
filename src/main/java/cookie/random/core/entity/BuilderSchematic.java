package cookie.random.core.entity;

import net.minecraft.core.block.Block;

public class BuilderSchematic {
	public int[][][] start;
	public int[][][] target;

	public BuilderSchematic() {
		this.start = new int[][][]{
			{{0}, {0}, {0}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {0}, {0}},
			{{0}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.obsidian.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.cobbleStone.id}, {0}, {0}, {20}, {20}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {Block.obsidian.id}, {Block.blockIron.id}, {Block.obsidian.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.cobbleStone.id}, {0}, {0}, {0}, {20}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {Block.blockIron.id}, {0}, {0}, {0}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {0}, {0}, {20}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {Block.oreDiamondStone.id}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.oreDiamondStone.id}, {Block.oreDiamondStone.id}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.oreDiamondStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.oreDiamondStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}}
		};
		this.target = new int[][][]{
			{{0}, {0}, {0}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {0}, {0}},
			{{0}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.obsidian.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.cobbleStone.id}, {20}, {20}, {20}, {20}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {Block.obsidian.id}, {Block.blockIron.id}, {Block.obsidian.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.cobbleStone.id}, {20}, {20}, {20}, {20}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.obsidian.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {20}, {20}, {20}, {20}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {Block.oreDiamondStone.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {20}, {20}, {20}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.oreDiamondStone.id}, {Block.oreDiamondStone.id}, {Block.oreDiamondStone.id}, {Block.blockIron.id}, {Block.blockIron.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {0}, {0}, {20}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.oreDiamondStone.id}, {Block.oreDiamondStone.id}, {Block.oreDiamondStone.id}, {0}, {Block.blockIron.id}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {20}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.oreDiamondStone.id}, {Block.oreDiamondStone.id}, {0}, {0}, {0}, {Block.cobbleStone.id}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.oreDiamondStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {Block.cobbleStone.id}},
			{{Block.cobbleStone.id}, {Block.blockIron.id}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}},
			{{0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}, {0}}
		};
	}
}
