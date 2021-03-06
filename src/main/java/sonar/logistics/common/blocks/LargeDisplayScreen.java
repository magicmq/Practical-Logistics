package sonar.logistics.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sonar.logistics.common.tileentity.TileEntityLargeScreen;

public class LargeDisplayScreen extends AbstractScreen {

	@Override
	public float height() {
		return 0.0F;
	}

	@Override
	public float width() {
		return 1.0F;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int i) {
		return new TileEntityLargeScreen();
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
	}

	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {

		int l = world.getBlockMetadata(x, y, z);
		float f = 0.0F;
		float f1 = 1 - height();
		float f2 = 0.0F;
		float f3 = width();
		float f4 = 0.080F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

		if (l == 2) {
			this.setBlockBounds(f2, f, 1.0F - f4, f3, f1, 1.0F);
		}

		if (l == 3) {
			this.setBlockBounds(f2, f, 0.0F, f3, f1, f4);
		}

		if (l == 4) {
			this.setBlockBounds(1.0F - f4, f, f2, 1.0F, f1, f3);
		}

		if (l == 5) {
			this.setBlockBounds(0.0F, f, f2, f4, f1, f3);
		}

	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		setDefaultDirection(world, x, y, z);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack itemstack) {
		int l = MathHelper.floor_double(player.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;

		if (l == 0) {
			world.setBlockMetadataWithNotify(x, y, z, 2, 2);
		}

		if (l == 1) {
			world.setBlockMetadataWithNotify(x, y, z, 5, 2);
		}

		if (l == 2) {
			world.setBlockMetadataWithNotify(x, y, z, 3, 2);
		}

		if (l == 3) {
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}

}
