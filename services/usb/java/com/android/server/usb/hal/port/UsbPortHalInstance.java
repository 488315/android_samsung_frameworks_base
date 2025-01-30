package com.android.server.usb.hal.port;

import com.android.internal.util.IndentingPrintWriter;
import com.android.server.usb.UsbPortManager;

/* loaded from: classes3.dex */
public abstract class UsbPortHalInstance {
    public static UsbPortHal getInstance(UsbPortManager usbPortManager, IndentingPrintWriter indentingPrintWriter) {
        UsbPortManager.logAndPrint(3, null, "Querying USB HAL version");
        if (UsbPortAidl.isServicePresent(null)) {
            UsbPortManager.logAndPrint(4, null, "USB HAL AIDL present");
            return new UsbPortAidl(usbPortManager, indentingPrintWriter);
        }
        if (!UsbPortHidl.isServicePresent(null)) {
            return null;
        }
        UsbPortManager.logAndPrint(4, null, "USB HAL HIDL present");
        return new UsbPortHidl(usbPortManager, indentingPrintWriter);
    }
}
