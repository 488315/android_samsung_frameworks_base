package com.android.server.hdmi;

import android.util.IndentingPrintWriter;

/* loaded from: classes2.dex */
public abstract class HdmiEarcLocalDevice extends HdmiLocalDevice {
    public int mEarcStatus;

    public void disableDevice() {
    }

    public abstract void dump(IndentingPrintWriter indentingPrintWriter);

    public abstract void handleEarcCapabilitiesReported(byte[] bArr);

    public abstract void handleEarcStateChange(int i);

    public HdmiEarcLocalDevice(HdmiControlService hdmiControlService, int i) {
        super(hdmiControlService, i);
    }

    public static HdmiEarcLocalDevice create(HdmiControlService hdmiControlService, int i) {
        if (i != 0) {
            return null;
        }
        return new HdmiEarcLocalDeviceTx(hdmiControlService);
    }
}
