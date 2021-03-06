package sonar.logistics.info.providers.tile;

import java.util.List;

import mekanism.api.IEvaporationSolar;
import mekanism.api.IHeatTransfer;
import mekanism.api.energy.IStrictEnergyAcceptor;
import mekanism.api.energy.IStrictEnergyStorage;
import mekanism.api.lasers.ILaserReceptor;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import sonar.logistics.api.info.ILogicInfo;
import sonar.logistics.api.info.LogicInfo;
import sonar.logistics.api.providers.TileProvider;
import cpw.mods.fml.common.Loader;

public class MekanismGeneralProvider extends TileProvider {

	public static String name = "Mekanism-General";
	public String[] categories = new String[] { "Mekanism General", "Mekanism Energy" };
	public String[] subcategories = new String[] { "Temperature", "Can See Sun", "Can Laser Dig", "Stored", "Max Stored", "Can Connect" };

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean canProvideInfo(World world, int x, int y, int z, ForgeDirection dir) {
		TileEntity target = world.getTileEntity(x, y, z);
		return target != null && (target instanceof IHeatTransfer || target instanceof IEvaporationSolar || target instanceof ILaserReceptor || target instanceof IStrictEnergyStorage);
	}

	@Override
	public void getTileInfo(List<ILogicInfo> infoList, World world, int x, int y, int z, ForgeDirection dir) {
		int id = this.getID();
		TileEntity target = world.getTileEntity(x, y, z);
		if (target instanceof IHeatTransfer) {
			IHeatTransfer block = (IHeatTransfer) target;
			infoList.add(new LogicInfo(id, 0, 0, (int) block.getTemp()).addSuffix("degrees"));
		}
		if (target instanceof IEvaporationSolar) {
			IEvaporationSolar block = (IEvaporationSolar) target;
			infoList.add(new LogicInfo(id, 0, 1, block.seesSun()));
		}
		if (target instanceof ILaserReceptor) {
			ILaserReceptor block = (ILaserReceptor) target;
			infoList.add(new LogicInfo(id, 0, 2, block.canLasersDig()));
		}
		if (target instanceof IStrictEnergyStorage) {
			IStrictEnergyStorage block = (IStrictEnergyStorage) target;
			infoList.add(new LogicInfo(id, 1, 3, (long) block.getEnergy()).addSuffix("J"));;
			infoList.add(new LogicInfo(id, 1, 4, (long) block.getMaxEnergy()).addSuffix("J"));
		}
		if (target instanceof IStrictEnergyAcceptor) {
			IStrictEnergyAcceptor block = (IStrictEnergyAcceptor) target;
			infoList.add(new LogicInfo(id, 1, 5, block.canReceiveEnergy(dir)));
		}
	}

	@Override
	public String getCategory(int id) {
		return categories[id];
	}

	@Override
	public String getSubCategory(int id) {
		return subcategories[id];
	}

	public boolean isLoadable() {
		return Loader.isModLoaded("Mekanism");
	}

}
