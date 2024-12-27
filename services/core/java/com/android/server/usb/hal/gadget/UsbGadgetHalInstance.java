package com.android.server.usb.hal.gadget;

import android.hardware.usb.gadget.V1_0.IUsbGadget;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.sysfwutil.Slog;

import com.android.server.usb.UsbDeviceManager;
import com.android.server.usb.UsbPortManager;

import java.util.NoSuchElementException;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class UsbGadgetHalInstance {
    public static UsbGadgetHal getInstance(UsbDeviceManager usbDeviceManager) {
        boolean z;
        int i = UsbPortManager.COMBO_SOURCE_HOST;
        Slog.println(3, "UsbPortManager", "Querying USB Gadget HAL version");
        try {
            z = ServiceManager.isDeclared(UsbGadgetAidl.USB_GADGET_AIDL_SERVICE);
        } catch (NoSuchElementException e) {
            Set set = UsbDeviceManager.sDenyInterfaces;
            Slog.e("UsbDeviceManager", "connectToProxy: usb gadget Aidl hal service not found.", e);
            z = false;
        }
        if (z) {
            Slog.println(4, "UsbPortManager", "USB Gadget HAL AIDL present");
            return new UsbGadgetAidl(usbDeviceManager);
        }
        try {
            IUsbGadget.getService(true);
        } catch (RemoteException e2) {
            Set set2 = UsbDeviceManager.sDenyInterfaces;
            Slog.e(
                    "UsbDeviceManager",
                    "IUSBGadget hal service present but failed to get service",
                    e2);
        } catch (NoSuchElementException e3) {
            Set set3 = UsbDeviceManager.sDenyInterfaces;
            Slog.e(
                    "UsbDeviceManager",
                    "connectToProxy: usb gadget hidl hal service not found.",
                    e3);
            Slog.println(6, "UsbPortManager", "USB Gadget HAL AIDL/HIDL not present");
            return null;
        }
        Slog.println(4, "UsbPortManager", "USB Gadget HAL HIDL present");
        return new UsbGadgetHidl(usbDeviceManager);
    }
}
