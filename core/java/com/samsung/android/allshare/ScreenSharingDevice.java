package com.samsung.android.allshare;

import android.net.Uri;
import com.samsung.android.allshare.Device;

/* loaded from: classes3.dex */
public abstract class ScreenSharingDevice extends Device {

    public interface IScreenSharingActionResponseListner {
        void onConnectScreenSharingM2TV(String str, String str2, String str3);

        void onConnectScreenSharingTV2M(String str, String str2, String str3, String str4);
    }

    public interface IScreenSharingEventListener {
        void onEventReceived(String str, String str2, ERROR error);
    }

    public abstract void connectScreenSharingM2TV(String str, String str2, String str3, int i);

    public abstract void connectScreenSharingTV2M(String str, String str2, String str3);

    public abstract ERROR setEventListener(IScreenSharingEventListener iScreenSharingEventListener);

    public abstract void setResponseListener(IScreenSharingActionResponseListner iScreenSharingActionResponseListner);

    protected ScreenSharingDevice() {
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceDomain getDeviceDomain() {
        return null;
    }

    @Override // com.samsung.android.allshare.Device
    public Device.DeviceType getDeviceType() {
        return null;
    }

    @Override // com.samsung.android.allshare.Device
    public String getID() {
        return null;
    }

    @Override // com.samsung.android.allshare.Device
    public String getIPAddress() {
        return null;
    }

    @Override // com.samsung.android.allshare.Device
    public Uri getIcon() {
        return null;
    }

    @Override // com.samsung.android.allshare.Device
    public String getModelName() {
        return null;
    }

    @Override // com.samsung.android.allshare.Device
    public String getName() {
        return null;
    }
}
