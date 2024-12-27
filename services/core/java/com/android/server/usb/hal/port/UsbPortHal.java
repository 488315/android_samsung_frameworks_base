package com.android.server.usb.hal.port;

import android.hardware.usb.IUsbOperationInternal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public interface UsbPortHal {
    void enableContaminantPresenceDetection(String str, long j, boolean z);

    void enableLimitPowerTransfer(String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal);

    boolean enableUsbData(String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal);

    void enableUsbDataWhileDocked(String str, long j, IUsbOperationInternal iUsbOperationInternal);

    int getUsbHalVersion();

    void queryPortStatus(long j);

    void resetUsbPort(String str, long j, IUsbOperationInternal iUsbOperationInternal);

    void switchDataRole(int i, String str, long j);

    void switchMode(int i, String str, long j);

    void switchPowerRole(int i, String str, long j);

    void systemReady();
}
