package com.android.server.usb.hal.gadget;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.hardware.usb.gadget.IUsbGadget;
import android.hardware.usb.gadget.IUsbGadgetCallback;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.sysfwutil.Slog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.UserspaceRebootLogger$$ExternalSyntheticOutline0;
import com.android.server.usb.UsbDeviceManager;
import java.util.NoSuchElementException;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbGadgetAidl implements UsbGadgetHal {
    public static final String USB_GADGET_AIDL_SERVICE = AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder(), IUsbGadget.DESCRIPTOR, "/default");
    public final UsbDeviceManager mDeviceManager;
    public final IUsbGadget mGadgetProxy;
    public final Object mGadgetProxyLock;
    public final IndentingPrintWriter mPw;
    public UsbGadgetCallback mUsbGadgetCallback;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UsbGadgetCallback extends IUsbGadgetCallback.Stub {
        public final boolean mChargingFunctions;
        public final long mFunctions;
        public final IndentingPrintWriter mPw;
        public final int mRequest;

        public UsbGadgetCallback() {
        }

        public UsbGadgetCallback(int i, long j, boolean z) {
            this.mPw = null;
            this.mRequest = i;
            this.mFunctions = j;
            this.mChargingFunctions = z;
        }

        public final void getCurrentUsbFunctionsCb(long j, int i, long j2) {
            if (i == 0) {
                IndentingPrintWriter indentingPrintWriter = this.mPw;
                StringBuilder m = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "Usb getCurrentUsbFunctionsCb ,functions:", j, " ,status:");
                m.append(" ,transactionId:");
                m.append(j2);
                UsbDeviceManager.logAndPrint(4, indentingPrintWriter, m.toString());
            } else {
                IndentingPrintWriter indentingPrintWriter2 = this.mPw;
                StringBuilder m2 = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "Usb getCurrentUsbFunctionsCb failed ,functions:", j, " ,status:");
                m2.append(" ,transactionId:");
                m2.append(j2);
                UsbDeviceManager.logAndPrint(6, indentingPrintWriter2, m2.toString());
            }
            UsbDeviceManager usbDeviceManager = UsbGadgetAidl.this.mDeviceManager;
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

        public final String getInterfaceHash() {
            return "cb628c69682659911bca5c1d04042adba7f0de4b";
        }

        public final int getInterfaceVersion() {
            return 1;
        }

        public final void getUsbSpeedCb(int i, long j) {
            UsbDeviceManager.logAndPrint(4, this.mPw, "getUsbSpeedCb speed:" + i + " ,transactionId:" + j);
            UsbGadgetAidl.this.mDeviceManager.mHandler.getUsbSpeedCb(i);
        }

        public final void resetCb(int i, long j) {
            if (i == 0) {
                UsbDeviceManager.logAndPrint(4, this.mPw, "Usb resetCb status:" + i + " ,transactionId:" + j);
            } else {
                UsbDeviceManager.logAndPrint(6, this.mPw, "Usb resetCb status" + i + " ,transactionId:" + j);
            }
            UsbGadgetAidl.this.mDeviceManager.mHandler.resetCb(i);
        }

        public final void setCurrentUsbFunctionsCb(long j, int i, long j2) {
            if (i == 0) {
                IndentingPrintWriter indentingPrintWriter = this.mPw;
                StringBuilder m = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "Usb setCurrentUsbFunctionsCb ,functions:", j, " ,status:");
                m.append(" ,transactionId:");
                m.append(j2);
                UsbDeviceManager.logAndPrint(4, indentingPrintWriter, m.toString());
            } else {
                IndentingPrintWriter indentingPrintWriter2 = this.mPw;
                StringBuilder m2 = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "Usb setCurrentUsbFunctionsCb failed ,functions:", j, " ,status:");
                m2.append(" ,transactionId:");
                m2.append(j2);
                UsbDeviceManager.logAndPrint(6, indentingPrintWriter2, m2.toString());
            }
            UsbDeviceManager usbDeviceManager = UsbGadgetAidl.this.mDeviceManager;
            int i2 = this.mRequest;
            usbDeviceManager.mHandler.setCurrentUsbFunctionsCb(i, j, this.mFunctions, i2, this.mChargingFunctions);
        }
    }

    public UsbGadgetAidl(UsbDeviceManager usbDeviceManager) {
        Object obj = new Object();
        this.mGadgetProxyLock = obj;
        Objects.requireNonNull(usbDeviceManager);
        this.mDeviceManager = usbDeviceManager;
        this.mPw = null;
        synchronized (obj) {
            if (this.mGadgetProxy != null) {
                return;
            }
            try {
                this.mGadgetProxy = IUsbGadget.Stub.asInterface(ServiceManager.waitForService(USB_GADGET_AIDL_SERVICE));
            } catch (NoSuchElementException e) {
                Slog.e("UsbDeviceManager", "connectToProxy: usb gadget hal service not found. Did the service fail to start?", e);
            }
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final void getCurrentUsbFunctions(long j) {
        synchronized (this.mGadgetProxyLock) {
            try {
                try {
                    this.mGadgetProxy.getCurrentUsbFunctions(new UsbGadgetCallback(), j);
                } catch (RemoteException e) {
                    UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getCurrentUsbFunctions, opID:" + j, e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final int getGadgetHalVersion() {
        synchronized (this.mGadgetProxyLock) {
            if (this.mGadgetProxy == null) {
                throw new RemoteException("IUsb not initialized yet");
            }
        }
        android.util.Slog.i("UsbGadgetAidl", "USB Gadget HAL AIDL version: GADGET_HAL_V2_0");
        return 20;
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final void getUsbSpeed(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.getUsbSpeed(new UsbGadgetCallback(), j);
            }
        } catch (RemoteException e) {
            UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getUsbSpeed, opID:" + j, e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final void reset(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.reset(new UsbGadgetCallback(), j);
            }
        } catch (RemoteException e) {
            UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getUsbSpeed, opID:" + j, e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public final void setCurrentUsbFunctions(int i, long j, long j2, boolean z) {
        try {
            this.mUsbGadgetCallback = new UsbGadgetCallback(i, j, z);
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.setCurrentUsbFunctions(j, this.mUsbGadgetCallback, 2500, j2);
            }
        } catch (RemoteException e) {
            IndentingPrintWriter indentingPrintWriter = this.mPw;
            StringBuilder m = UserspaceRebootLogger$$ExternalSyntheticOutline0.m(i, "RemoteException while calling setCurrentUsbFunctions: mRequest=", j, ", mFunctions=");
            m.append(", mChargingFunctions=");
            m.append(z);
            m.append(", timeout=2500, opID:");
            m.append(j2);
            UsbDeviceManager.logAndPrintException(indentingPrintWriter, m.toString(), e);
        }
    }
}
