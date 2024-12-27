package com.android.server.usb.hal.gadget;

public interface UsbGadgetHal {
    void getCurrentUsbFunctions(long j);

    int getGadgetHalVersion();

    void getUsbSpeed(long j);

    void reset(long j);

    void setCurrentUsbFunctions(int i, long j, long j2, boolean z);
}
