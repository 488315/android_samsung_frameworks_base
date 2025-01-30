package com.android.server.usb.hal.gadget;

import android.hardware.usb.gadget.IUsbGadget;
import android.hardware.usb.gadget.IUsbGadgetCallback;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.usb.UsbDeviceManager;
import java.util.NoSuchElementException;
import java.util.Objects;

/* loaded from: classes3.dex */
public final class UsbGadgetAidl implements UsbGadgetHal {
    public static final String TAG = "UsbGadgetAidl";
    public static final String USB_GADGET_AIDL_SERVICE = IUsbGadget.DESCRIPTOR + "/default";
    public final UsbDeviceManager mDeviceManager;
    public IUsbGadget mGadgetProxy;
    public final Object mGadgetProxyLock = new Object();
    public final IndentingPrintWriter mPw;
    public UsbGadgetCallback mUsbGadgetCallback;

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public int getGadgetHalVersion() {
        synchronized (this.mGadgetProxyLock) {
            if (this.mGadgetProxy == null) {
                throw new RemoteException("IUsb not initialized yet");
            }
        }
        Slog.i(TAG, "USB Gadget HAL AIDL version: GADGET_HAL_V2_0");
        return 20;
    }

    public final void connectToProxy(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mGadgetProxyLock) {
            if (this.mGadgetProxy != null) {
                return;
            }
            try {
                this.mGadgetProxy = IUsbGadget.Stub.asInterface(ServiceManager.waitForService(USB_GADGET_AIDL_SERVICE));
            } catch (NoSuchElementException e) {
                UsbDeviceManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb gadget hal service not found. Did the service fail to start?", e);
            }
        }
    }

    public static boolean isServicePresent(IndentingPrintWriter indentingPrintWriter) {
        try {
            return ServiceManager.isDeclared(USB_GADGET_AIDL_SERVICE);
        } catch (NoSuchElementException e) {
            UsbDeviceManager.logAndPrintException(indentingPrintWriter, "connectToProxy: usb gadget Aidl hal service not found.", e);
            return false;
        }
    }

    public UsbGadgetAidl(UsbDeviceManager usbDeviceManager, IndentingPrintWriter indentingPrintWriter) {
        Objects.requireNonNull(usbDeviceManager);
        this.mDeviceManager = usbDeviceManager;
        this.mPw = indentingPrintWriter;
        connectToProxy(indentingPrintWriter);
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void getCurrentUsbFunctions(long j) {
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
    public void getUsbSpeed(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.getUsbSpeed(new UsbGadgetCallback(), j);
            }
        } catch (RemoteException e) {
            UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getUsbSpeed, opID:" + j, e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void reset(long j) {
        try {
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.reset(new UsbGadgetCallback(), j);
            }
        } catch (RemoteException e) {
            UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling getUsbSpeed, opID:" + j, e);
        }
    }

    @Override // com.android.server.usb.hal.gadget.UsbGadgetHal
    public void setCurrentUsbFunctions(int i, long j, boolean z, int i2, long j2) {
        try {
            this.mUsbGadgetCallback = new UsbGadgetCallback(null, i, j, z);
            synchronized (this.mGadgetProxyLock) {
                this.mGadgetProxy.setCurrentUsbFunctions(j, this.mUsbGadgetCallback, i2, j2);
            }
        } catch (RemoteException e) {
            UsbDeviceManager.logAndPrintException(this.mPw, "RemoteException while calling setCurrentUsbFunctions: mRequest=" + i + ", mFunctions=" + j + ", mChargingFunctions=" + z + ", timeout=" + i2 + ", opID:" + j2, e);
        }
    }

    public class UsbGadgetCallback extends IUsbGadgetCallback.Stub {
        public boolean mChargingFunctions;
        public long mFunctions;
        public IndentingPrintWriter mPw;
        public int mRequest;

        public String getInterfaceHash() {
            return "cb628c69682659911bca5c1d04042adba7f0de4b";
        }

        public int getInterfaceVersion() {
            return 1;
        }

        public UsbGadgetCallback() {
        }

        public UsbGadgetCallback(IndentingPrintWriter indentingPrintWriter, int i, long j, boolean z) {
            this.mPw = indentingPrintWriter;
            this.mRequest = i;
            this.mFunctions = j;
            this.mChargingFunctions = z;
        }

        public void setCurrentUsbFunctionsCb(long j, int i, long j2) {
            if (i == 0) {
                UsbDeviceManager.logAndPrint(4, this.mPw, "Usb setCurrentUsbFunctionsCb ,functions:" + j + " ,status:" + i + " ,transactionId:" + j2);
            } else {
                UsbDeviceManager.logAndPrint(6, this.mPw, "Usb setCurrentUsbFunctionsCb failed ,functions:" + j + " ,status:" + i + " ,transactionId:" + j2);
            }
            UsbGadgetAidl.this.mDeviceManager.setCurrentUsbFunctionsCb(j, i, this.mRequest, this.mFunctions, this.mChargingFunctions);
        }

        public void getCurrentUsbFunctionsCb(long j, int i, long j2) {
            if (i == 0) {
                UsbDeviceManager.logAndPrint(4, this.mPw, "Usb getCurrentUsbFunctionsCb ,functions:" + j + " ,status:" + i + " ,transactionId:" + j2);
            } else {
                UsbDeviceManager.logAndPrint(6, this.mPw, "Usb getCurrentUsbFunctionsCb failed ,functions:" + j + " ,status:" + i + " ,transactionId:" + j2);
            }
            UsbGadgetAidl.this.mDeviceManager.getCurrentUsbFunctionsCb(j, i);
        }

        public void getUsbSpeedCb(int i, long j) {
            UsbDeviceManager.logAndPrint(4, this.mPw, "getUsbSpeedCb speed:" + i + " ,transactionId:" + j);
            UsbGadgetAidl.this.mDeviceManager.getUsbSpeedCb(i);
        }

        public void resetCb(int i, long j) {
            if (i == 0) {
                UsbDeviceManager.logAndPrint(4, this.mPw, "Usb resetCb status:" + i + " ,transactionId:" + j);
            } else {
                UsbDeviceManager.logAndPrint(6, this.mPw, "Usb resetCb status" + i + " ,transactionId:" + j);
            }
            UsbGadgetAidl.this.mDeviceManager.resetCb(i);
        }
    }
}
