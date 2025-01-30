package com.samsung.android.allshare;

import com.samsung.android.allshare.Device;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public abstract class DeviceFinder {

    public interface IDeviceFinderEventListener {
        void onDeviceAdded(Device.DeviceType deviceType, Device device, ERROR error);

        void onDeviceRemoved(Device.DeviceType deviceType, Device device, ERROR error);
    }

    public abstract Device getDevice(String str, Device.DeviceType deviceType);

    public abstract ArrayList<Device> getDevices(Device.DeviceDomain deviceDomain, Device.DeviceType deviceType);

    public abstract ArrayList<Device> getDevices(Device.DeviceType deviceType);

    public abstract ArrayList<Device> getDevices(Device.DeviceType deviceType, String str);

    public abstract void refresh();

    public abstract void refresh(Device.DeviceType deviceType);

    public abstract void registerSearchTarget(ArrayList<Device.DeviceType> arrayList);

    public abstract void setDeviceFinderEventListener(Device.DeviceType deviceType, IDeviceFinderEventListener iDeviceFinderEventListener);

    public abstract void unregisterSearchTarget(ArrayList<Device.DeviceType> arrayList);

    protected DeviceFinder() {
    }
}
