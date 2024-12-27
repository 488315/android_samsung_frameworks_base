package com.android.server.usb.hal.port;

import android.hardware.usb.IUsbOperationInternal;

public interface UsbPortHal {
    void enableContaminantPresenceDetection(String str, long j, boolean z);

    void enableLimitPowerTransfer(
            String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal);

    boolean enableUsbData(
            String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal);

    void enableUsbDataWhileDocked(String str, long j, IUsbOperationInternal iUsbOperationInternal);

    int getUsbHalVersion();

    void queryPortStatus(long j);

    void resetUsbPort(String str, long j, IUsbOperationInternal iUsbOperationInternal);

    void switchDataRole(int i, String str, long j);

    void switchMode(int i, String str, long j);

    void switchPowerRole(int i, String str, long j);

    void systemReady();
}
