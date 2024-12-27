package com.android.server.hdmi;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
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
