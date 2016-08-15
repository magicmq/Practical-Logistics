package sonar.logistics.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import sonar.core.helpers.NBTHelper.SyncType;
import sonar.core.network.sync.SyncPart;
import sonar.logistics.Logistics;
import sonar.logistics.api.info.monitor.IMonitorInfo;
import sonar.logistics.api.info.monitor.MonitorHandler;

public class SyncMonitoredType<T extends IMonitorInfo> extends SyncPart {

	public String handlerID;
	public MonitorHandler<T> handler;
	public IMonitorInfo<T> info;

	public SyncMonitoredType(String handlerID, int id) {
		super(id);
		this.handlerID = handlerID;
	}

	public void setInfo(IMonitorInfo<T> info) {
		this.info = info;
		this.setChanged(true);
	}

	public T getMonitoredInfo() {
		return (T) info;
	}

	@Override
	public void writeToBuf(ByteBuf buf) {
		if (info != null) {
			buf.writeBoolean(true);
			ByteBufUtils.writeTag(buf, handler().writeInfo((T) info, new NBTTagCompound(), SyncType.SAVE));
		} else {
			buf.writeBoolean(false);
		}
	}

	@Override
	public void readFromBuf(ByteBuf buf) {
		if (buf.readBoolean()) {
			info = handler().readInfo(ByteBufUtils.readTag(buf), SyncType.SAVE);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt, SyncType type) {
		if (info != null) {
			nbt.setTag("info", handler().writeInfo((T) info, new NBTTagCompound(), type));
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt, SyncType type) {
		if (nbt.hasKey("info")) {
			info = handler().readInfo(nbt.getCompoundTag("info"), type);
		}
	}

	public MonitorHandler handler() {
		return handler == null ? handler = Logistics.monitorHandlers.getRegisteredObject(handlerID) : handler;

	}

}