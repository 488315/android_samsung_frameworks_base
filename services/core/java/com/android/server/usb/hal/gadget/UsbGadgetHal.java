package com.android.server.usb.hal.gadget;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface UsbGadgetHal {
    void getCurrentUsbFunctions(long j);

    int getGadgetHalVersion();

    void getUsbSpeed(long j);

    void reset(long j);

    void setCurrentUsbFunctions(int i, long j, long j2, boolean z);
}
