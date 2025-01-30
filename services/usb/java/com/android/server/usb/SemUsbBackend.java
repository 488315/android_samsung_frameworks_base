package com.android.server.usb;

import android.content.Context;
import android.content.IntentFilter;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.os.Binder;
import android.os.SystemProperties;
import android.os.UserManager;
import android.util.sysfwutil.Slog;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes3.dex */
public class SemUsbBackend {
    public static final AtomicInteger sUsbOperationCount = new AtomicInteger();
    public final Context mContext;
    public UsbDeviceManager mDeviceManager;
    public boolean mIsUnlocked;
    public UsbPort mPort;
    public UsbPortManager mPortManager;
    public UsbPortStatus mPortStatus;
    public final boolean mRestricted;
    public UserManager mUserManager;

    public final int modeToPower(int i) {
        return (i & 1) == 1 ? 1 : 2;
    }

    public SemUsbBackend(Context context, UsbPortManager usbPortManager, UsbDeviceManager usbDeviceManager) {
        this.mContext = context;
        this.mIsUnlocked = context.registerReceiver(null, new IntentFilter("android.hardware.usb.action.USB_STATE")).getBooleanExtra("unlocked", false);
        UserManager userManager = UserManager.get(context);
        this.mUserManager = userManager;
        this.mRestricted = userManager.hasUserRestriction("no_usb_file_transfer");
        this.mPortManager = usbPortManager;
        this.mDeviceManager = usbDeviceManager;
        updateUsbPort();
    }

    public void updateUsbPort() {
        UsbPort[] ports = this.mPortManager.getPorts();
        if (ports != null) {
            int length = ports.length;
            for (int i = 0; i < length; i++) {
                UsbPortStatus portStatus = this.mPortManager.getPortStatus(ports[i].getId());
                Slog.d("SemUsbBackend", "updateUsbPort() status = " + portStatus);
                if (portStatus != null) {
                    Slog.d("SemUsbBackend", "updateUsbPort() status.isConnected() = " + portStatus.isConnected());
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

    public int getCurrentMode() {
        if (this.mPort != null && this.mPortStatus.getCurrentPowerRole() == 1) {
            Slog.d("SemUsbBackend", "USB connection getCurrentMode : 1");
            return 1;
        }
        Slog.d("SemUsbBackend", "USB connection getCurrentMode : " + (getUsbDataMode() | 0));
        return getUsbDataMode() | 0;
    }

    public int getUsbDataMode() {
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

    public final void setUsbFunction(int i) {
        int incrementAndGet = sUsbOperationCount.incrementAndGet() + Binder.getCallingUid();
        Slog.d("SemUsbBackend", "USB connection setUsbFunction : " + i + ", operationId : " + incrementAndGet);
        if (i == 2) {
            Slog.d("SemUsbBackend", "USB connection setUsbFunction : + UsbManager.USB_FUNCTION_MTP");
            this.mDeviceManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("mtp"), incrementAndGet);
            return;
        }
        if (i == 4) {
            Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_PTP");
            this.mDeviceManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("ptp"), incrementAndGet);
            return;
        }
        if (i == 6) {
            Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_MIDI");
            this.mDeviceManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("midi"), incrementAndGet);
        } else if (i == 8) {
            Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_MASS_STORAGE");
            this.mDeviceManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("mass_storage"), incrementAndGet);
        } else if (i == 10) {
            Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_MTP_GADGET");
            this.mDeviceManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("mtp,conn_gadget"), incrementAndGet);
        } else {
            Slog.d("SemUsbBackend", "USB connection setUsbFunction :  + UsbManager.USB_FUNCTION_SEC_CHARGING");
            this.mDeviceManager.setCurrentFunctions(UsbManager.usbFunctionsFromString("sec_charging"), incrementAndGet);
        }
    }

    public void setMode(int i) {
        int i2;
        int i3;
        UsbDeviceManager usbDeviceManager = this.mDeviceManager;
        if (usbDeviceManager != null) {
            usbDeviceManager.rmSetNextUsbModeToDefault();
        } else {
            Slog.d("SemUsbBackend", "setMode : mDeviceManager is null");
        }
        if (this.mPort != null) {
            i2 = modeToPower(i);
            i3 = ((i & 14) == 0 && this.mPortStatus.isRoleCombinationSupported(i2, 1)) ? 1 : 2;
            Slog.d("SemUsbBackend", "setMode : powerRole - " + i2 + " /dataRole - " + i3);
            this.mPortManager.setPortRolesLegacy(this.mPort.getId(), i2, i3, null);
        } else {
            i2 = 0;
            i3 = 0;
        }
        if (i2 != 1 || i3 != 1) {
            Slog.d("SemUsbBackend", "USB connection setMode : " + i);
            setUsbFunction(i & 14);
            return;
        }
        Slog.d("SemUsbBackend", "USB connection setMode : " + i + " - skip!!");
    }

    public final boolean isFunctionEnabled(String str) {
        return containsFunction(SystemProperties.get("sys.usb.config"), str);
    }

    public final boolean containsFunction(String str, String str2) {
        int indexOf = str.indexOf(str2);
        if (indexOf < 0) {
            return false;
        }
        if (indexOf > 0 && str.charAt(indexOf - 1) != ',') {
            return false;
        }
        int length = indexOf + str2.length();
        return length >= str.length() || str.charAt(length) == ',';
    }
}
