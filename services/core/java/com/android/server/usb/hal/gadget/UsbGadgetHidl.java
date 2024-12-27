package com.android.server.usb.hal.gadget;

import android.hardware.usb.gadget.V1_0.IUsbGadget;
import android.hardware.usb.gadget.V1_2.IUsbGadgetCallback;
import android.hidl.manager.V1_0.IServiceManager;
import android.hidl.manager.V1_0.IServiceNotification;
import android.os.IHwBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.sysfwutil.Slog;

import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.UserspaceRebootLogger$$ExternalSyntheticOutline0;
import com.android.server.usb.UsbDeviceManager;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

public final class UsbGadgetHidl implements UsbGadgetHal {
    public final UsbDeviceManager mDeviceManager;
    public IUsbGadget mGadgetProxy;
    public final Object mGadgetProxyLock = new Object();
    public final IndentingPrintWriter mPw;
    public UsbGadgetCallback mUsbGadgetCallback;

    public final class DeathRecipient implements IHwBinder.DeathRecipient {
        public final IndentingPrintWriter mPw = null;

        public DeathRecipient() {}

        public final void serviceDied(long j) {
            if (j == 2000) {
                UsbDeviceManager.logAndPrint(
                        6, this.mPw, "Usb Gadget hal service died cookie: " + j);
                synchronized (UsbGadgetHidl.this.mGadgetProxyLock) {
                    UsbGadgetHidl.this.mGadgetProxy = null;
                }
            }
        }
    }

    public final class ServiceNotification extends IServiceNotification.Stub {
        public ServiceNotification() {}

        @Override // android.hidl.manager.V1_0.IServiceNotification
        public final void onRegistration(String str, String str2, boolean z) {
            UsbDeviceManager.logAndPrint(
                    4,
                    UsbGadgetHidl.this.mPw,
                    BootReceiver$$ExternalSyntheticOutline0.m(
                            "Usb gadget hal service started ", str, " ", str2));
            UsbGadgetHidl.this.connectToProxy$1();
        }
    }

    public final class UsbGadgetCallback extends IUsbGadgetCallback.Stub {
        public final boolean mChargingFunctions;
        public final long mFunctions;
        public final int mRequest;

        public UsbGadgetCallback() {}

        public UsbGadgetCallback(int i, long j, boolean z) {
            this.mRequest = i;
            this.mFunctions = j;
            this.mChargingFunctions = z;
        }

        public final void getCurrentUsbFunctionsCb(long j, int i) {
            UsbDeviceManager usbDeviceManager = UsbGadgetHidl.this.mDeviceManager;
            usbDeviceManager.getClass();
            Long valueOf = Long.valueOf(j);
            int i2 = i == 2 ? 1 : 0;
            UsbDeviceManager.UsbHandler usbHandler = usbDeviceManager.mHandler;
            usbHandler.removeMessages(16);
            Message obtain = Message.obtain(usbHandler, 16);
            obtain.obj = valueOf;
            obtain.arg1 = i2;
            usbHandler.sendMessage(obtain);
        }

        public final void getUsbSpeedCb(int i) {
            UsbGadgetHidl.this.mDeviceManager.mHandler.getUsbSpeedCb(i);
        }

        public final void setCurrentUsbFunctionsCb(long j, int i) {
            UsbDeviceManager usbDeviceManager = UsbGadgetHidl.this.mDeviceManager;
            int i2 = this.mRequest;
            usbDeviceManager.mHandler.setCurrentUsbFunctionsCb(
                    i, j, this.mFunctions, i2, this.mChargingFunctions);
        }
    }

    public UsbGadgetHidl(UsbDeviceManager usbDeviceManager) {
        Objects.requireNonNull(usbDeviceManager);
        this.mDeviceManager = usbDeviceManager;
        this.mPw = null;
        try {
            if (!IServiceManager.getService()
                    .registerForNotifications(
                            "android.hardware.usb.gadget@1.0::IUsbGadget",
                            "",
                            new ServiceNotification())) {
                Slog.println(
                        6, "UsbDeviceManager", "Failed to register service start notification");
            }
            connectToProxy$1();
        } catch (RemoteException e) {
            Slog.e("UsbDeviceManager", "Failed to register service start notification", e);
        }
    }

    public final void connectToProxy$1() {
        synchronized (this.mGadgetProxyLock) {
            if (this.mGadgetProxy != null) {
                return;
            }
            try {
                IUsbGadget service = IUsbGadget.getService();
                this.mGadgetProxy = service;
                service.linkToDeath(new DeathRecipient(), 2000L);
            } catch (RemoteException e) {
                Set set = UsbDeviceManager.sDenyInterfaces;
                Slog.e(
                        "UsbDeviceManager",
                        "connectToProxy: usb gadget hal service not responding",
                        e);
            } catch (NoSuchElementException e2) {
                Set set2 = UsbDeviceManager.sDenyInterfaces;
                Slog.e(
                        "UsbDeviceManager",
                        "connectToProxy: usb gadget hal service not found. Did the service fail to"
                            + " start?",
                        e2);
            }
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final void getCurrentUsbFunctions(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.getCurrentUsbFunctions(new UsbGadgetCallback());
            }
        } catch (RemoteException e) {
            UsbDeviceManager.logAndPrintException(
                    this.mPw, "RemoteException while calling getCurrentUsbFunctions", e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final int getGadgetHalVersion() {
        int i;
        synchronized (this.mGadgetProxyLock) {
            try {
                IUsbGadget iUsbGadget = this.mGadgetProxy;
                if (iUsbGadget == null) {
                    throw new RemoteException("IUsbGadget not initialized yet");
                }
                i =
                        android.hardware.usb.gadget.V1_2.IUsbGadget.castFrom(iUsbGadget) != null
                                ? 12
                                : android.hardware.usb.gadget.V1_1.IUsbGadget.castFrom(
                                                        this.mGadgetProxy)
                                                != null
                                        ? 11
                                        : 10;
                UsbDeviceManager.logAndPrint(4, this.mPw, "USB Gadget HAL HIDL version: " + i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final void getUsbSpeed(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                try {
                    if (android.hardware.usb.gadget.V1_2.IUsbGadget.castFrom(this.mGadgetProxy)
                            != null) {
                        android.hardware.usb.gadget.V1_2.IUsbGadget.castFrom(this.mGadgetProxy)
                                .getUsbSpeed(new UsbGadgetCallback());
                    }
                } finally {
                }
            }
        } catch (RemoteException e) {
            UsbDeviceManager.logAndPrintException(this.mPw, "get UsbSpeed failed", e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final void reset(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                try {
                    if (android.hardware.usb.gadget.V1_1.IUsbGadget.castFrom(this.mGadgetProxy)
                            != null) {
                        android.hardware.usb.gadget.V1_1.IUsbGadget.castFrom(this.mGadgetProxy)
                                .reset();
                    }
                } finally {
                }
            }
        } catch (RemoteException e) {
            UsbDeviceManager.logAndPrintException(
                    this.mPw, "RemoteException while calling reset", e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final void setCurrentUsbFunctions(int i, long j, long j2, boolean z) {
        try {
            this.mUsbGadgetCallback = new UsbGadgetCallback(i, j, z);
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.setCurrentUsbFunctions(j, this.mUsbGadgetCallback, 2500);
            }
        } catch (RemoteException e) {
            IndentingPrintWriter indentingPrintWriter = this.mPw;
            StringBuilder m =
                    UserspaceRebootLogger$$ExternalSyntheticOutline0.m(
                            i,
                            "RemoteException while calling setCurrentUsbFunctions mRequest = ",
                            j,
                            ", mFunctions = ");
            m.append(", timeout = 2500, mChargingFunctions = ");
            m.append(z);
            m.append(", operationId =");
            m.append(j2);
            UsbDeviceManager.logAndPrintException(indentingPrintWriter, m.toString(), e);
        }
    }
}
