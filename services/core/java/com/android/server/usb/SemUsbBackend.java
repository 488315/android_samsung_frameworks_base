package com.android.server.usb;

import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.os.SystemProperties;
import android.util.sysfwutil.Slog;

import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SemUsbBackend {
    public static final AtomicInteger sUsbOperationCount = new AtomicInteger();
    public UsbDeviceManager mDeviceManager;
    public boolean mIsUnlocked;
    public UsbPort mPort;
    public UsbPortManager mPortManager;
    public UsbPortStatus mPortStatus;

    public static boolean isFunctionEnabled(String str) {
        String str2 = SystemProperties.get("sys.usb.config");
        int indexOf = str2.indexOf(str);
        if (indexOf < 0) {
            return false;
        }
        if (indexOf > 0 && str2.charAt(indexOf - 1) != ',') {
            return false;
        }
        int length = str.length() + indexOf;
        return length >= str2.length() || str2.charAt(length) == ',';
    }

    public final int getUsbDataMode() {
        if (!this.mIsUnlocked) {
            Slog.d("SemUsbBackend", "USB connection getUsbDataMode : None");
            return 0;
        }
        if (isFunctionEnabled("mtp")) {
            Slog.d("SemUsbBackend", "USB connection getUsbDataMode : mtp");
            return 2;
        }
        if (isFunctionEnabled("ptp")) {
            Slog.d("SemUsbBackend", "USB connection getUsbDataMode : ptp");
            return 4;
        }
        if (isFunctionEnabled("midi")) {
            Slog.d("SemUsbBackend", "USB connection getUsbDataMode : midi");
            return 6;
        }
        if (isFunctionEnabled("mass_storage")) {
            Slog.d("SemUsbBackend", "USB connection getUsbDataMode : mass_storage");
            return 8;
        }
        if (isFunctionEnabled("sec_charging")) {
            Slog.d("SemUsbBackend", "USB connection getUsbDataMode : sec_charging");
            return 0;
        }
        if (!isFunctionEnabled("mtp,conn_gadget")) {
            return 0;
        }
        Slog.d("SemUsbBackend", "USB connection getUsbDataMode : mtp,conn_gadget");
        return 10;
    }

    public final void updateUsbPort() {
        UsbPortManager usbPortManager = this.mPortManager;
        UsbPort[] ports = usbPortManager.getPorts();
        int length = ports.length;
        for (int i = 0; i < length; i++) {
            UsbPortStatus portStatus = usbPortManager.getPortStatus(ports[i].getId());
            Slog.d("SemUsbBackend", "updateUsbPort() status = " + portStatus);
            if (portStatus != null) {
                Slog.d(
                        "SemUsbBackend",
                        "updateUsbPort() status.isConnected() = " + portStatus.isConnected());
                if (portStatus.isConnected()) {
                    this.mPort = ports[i];
                    this.mPortStatus = portStatus;
                    Slog.d("SemUsbBackend", "updateUsbPort() mPort = " + this.mPort);
                    return;
                }
            }
            Slog.d("SemUsbBackend", "updateUsbPort() mPort = " + this.mPort);
        }
    }
}
