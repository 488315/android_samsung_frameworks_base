package com.android.server.hdmi;

public abstract class HdmiLocalDevice {
    public final int mDeviceType;
    public final Object mLock;
    public final HdmiControlService mService;

    public HdmiLocalDevice(HdmiControlService hdmiControlService, int i) {
        this.mService = hdmiControlService;
        this.mDeviceType = i;
        this.mLock = hdmiControlService.mLock;
    }
}
