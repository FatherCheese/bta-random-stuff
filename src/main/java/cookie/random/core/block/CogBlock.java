package cookie.random.core.block;

import cookie.random.core.RSBlocks;
import net.minecraft.core.block.BlockTileEntity;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.enums.EnumDropCause;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.Direction;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

import java.util.Random;

public class CogBlock extends BlockTileEntity {
	public CogBlock(String key, int id) {
		super(key, id, Material.decoration);
		setTicking(true);
	}

	@Override
	public int tickRate() {
		return 2;
	}

	@Override
	protected TileEntity getNewBlockEntity() {
		return new CogEntity();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public boolean isSolidRender() {
		return false;
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		TileEntity blockEntity = world.getBlockTileEntity(x, y, z);

		if (blockEntity instanceof CogEntity) {
			if (world.isBlockGettingPowered(x, y, z)) {
				((CogEntity) blockEntity).rotationTarget = 10;
			} else {
				((CogEntity) blockEntity).rotationTarget = 0;
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		world.scheduleBlockUpdate(x, y, z, id, tickRate());
	}

	@Override
	public ItemStack[] getBreakResult(World world, EnumDropCause dropCause, int x, int y, int z, int meta, TileEntity tileEntity) {
		return new ItemStack[]{RSBlocks.COG.getDefaultStack()};
	}

	public void breakBlock(World world, int x, int y, int z) {
		dropBlockWithCause(world, EnumDropCause.WORLD, x, y, z, 0, null);
		world.setBlockWithNotify(x, y, z, 0);
	}

	public boolean neighbourIsClockwise(CogEntity blockEntity) {
		return blockEntity != null && blockEntity.isClockwise;
	}

	@Override
	public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
		Direction placementDirection = entity.getPlacementDirection(side).getOpposite();
		world.setBlockMetadataWithNotify(x, y, z, placementDirection.getId());

		TileEntity thisBlockEntity = world.getBlockTileEntity(x, y, z);

		if (thisBlockEntity instanceof CogEntity) {
			TileEntity blockEntityXPos = world.getBlockTileEntity(x + 1, y, z);
			TileEntity blockEntityXNeg = world.getBlockTileEntity(x - 1, y, z);
			TileEntity blockEntityZPos = world.getBlockTileEntity(x, y, z + 1);
			TileEntity blockEntityZNeg = world.getBlockTileEntity(x, y, z - 1);

			if (blockEntityXPos instanceof CogEntity) {
				if (((CogEntity) blockEntityXPos).isClockwise) {
					((CogEntity) thisBlockEntity).isClockwise = false;
					((CogEntity) thisBlockEntity).currentRotation -= ((CogEntity) blockEntityXPos).currentRotation + 22.5f;
				}
			}

			if (blockEntityXNeg instanceof CogEntity) {
				if (((CogEntity) blockEntityXNeg).isClockwise) {
					((CogEntity) thisBlockEntity).isClockwise = false;
					((CogEntity) thisBlockEntity).currentRotation -= ((CogEntity) blockEntityXNeg).currentRotation + 22.5f;
				}
			}

			if (blockEntityXPos instanceof CogEntity && blockEntityXNeg instanceof CogEntity) {
				if ((neighbourIsClockwise((CogEntity) blockEntityXPos) && !neighbourIsClockwise((CogEntity) blockEntityXNeg)) ||
					(!neighbourIsClockwise((CogEntity) blockEntityXPos) && neighbourIsClockwise((CogEntity) blockEntityXNeg))) {
					breakBlock(world, x, y, z);
				}
			}
		}
	}
}
