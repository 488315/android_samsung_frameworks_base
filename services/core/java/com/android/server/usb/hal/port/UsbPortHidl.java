package com.android.server.usb.hal.port;

import android.hardware.usb.IUsbOperationInternal;
import android.hardware.usb.UsbPort;
import android.hardware.usb.V1_0.IUsb;
import android.hardware.usb.V1_0.IUsbCallback;
import android.hardware.usb.V1_0.PortRole;
import android.hardware.usb.V1_0.PortStatus;
import android.hardware.usb.V1_1.IUsb;
import android.hardware.usb.V1_1.PortStatus_1_1;
import android.hardware.usb.V1_3.IUsb$Proxy;
import android.hidl.base.V1_0.DebugInfo;
import android.hidl.base.V1_0.IBase;
import android.hidl.manager.V1_0.IServiceManager;
import android.hidl.manager.V1_0.IServiceNotification;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import android.util.sysfwutil.Slog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.usb.UsbPortManager;
import com.att.iqi.lib.metrics.hw.HwConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbPortHidl implements UsbPortHal {
    public static int sUsbDataStatus;
    public final HALCallback mHALCallback;
    public final Object mLock = new Object();
    public IUsb mProxy;
    public final IndentingPrintWriter mPw;
    public boolean mSystemReady;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeathRecipient implements IHwBinder.DeathRecipient {
        public final IndentingPrintWriter pw = null;

        public DeathRecipient() {
        }

        public final void serviceDied(long j) {
            if (j == 1000) {
                UsbPortManager.logAndPrint(6, this.pw, "Usb hal service died cookie: " + j);
                synchronized (UsbPortHidl.this.mLock) {
                    UsbPortHidl.this.mProxy = null;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HALCallback extends HwBinder implements IUsbCallback {
        public UsbPortManager mPortManager;
        public IndentingPrintWriter mPw;
        public UsbPortHidl mUsbPortHidl;
        public int preDataRole;

        @Override // android.hidl.base.V1_0.IBase
        public final IHwBinder asBinder() {
            return this;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void debug(NativeHandle nativeHandle, ArrayList arrayList) {
        }

        @Override // android.hidl.base.V1_0.IBase
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList getHashChain() {
            return new ArrayList(Arrays.asList(new byte[]{70, -103, 108, -46, -95, -58, 98, 97, -89, 90, 31, 110, -54, -38, 119, -18, -75, -122, 30, -78, 100, -6, 57, -71, -106, 84, -113, -32, -89, -14, 45, -45}, new byte[]{19, -91, Byte.MIN_VALUE, -29, 90, -16, 18, 112, -95, -23, 119, 65, 119, -59, 29, -75, 29, -122, 114, -26, 19, -101, -96, 8, 81, -26, 84, -26, -118, 77, 125, -1}, new byte[]{75, -25, -120, 30, 65, 27, -92, 39, -124, -65, 91, 115, 84, -63, 74, -32, -49, 22, HwConstants.IQ_CONFIG_POS_NETWORK_ENABLED, 4, -45, -108, 51, -86, -20, -86, -80, -47, -98, -87, -109, 84}, new byte[]{-20, Byte.MAX_VALUE, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override // android.hidl.base.V1_0.IBase
        public final ArrayList interfaceChain() {
            return new ArrayList(Arrays.asList("android.hardware.usb@1.2::IUsbCallback", "android.hardware.usb@1.1::IUsbCallback", "android.hardware.usb@1.0::IUsbCallback", IBase.kInterfaceName));
        }

        @Override // android.hidl.base.V1_0.IBase
        public final String interfaceDescriptor() {
            return "android.hardware.usb@1.2::IUsbCallback";
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long j) {
            return true;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public final void onTransact(int i, HwParcel hwParcel, HwParcel hwParcel2, int i2) {
            int i3;
            int i4;
            HALCallback hALCallback;
            HALCallback hALCallback2 = this;
            if (i == 1) {
                hwParcel.enforceInterface("android.hardware.usb@1.0::IUsbCallback");
                ArrayList arrayList = new ArrayList();
                HwBlob readBuffer = hwParcel.readBuffer(16L);
                int int32 = readBuffer.getInt32(8L);
                HwBlob readEmbeddedBuffer = hwParcel.readEmbeddedBuffer(int32 * 40, readBuffer.handle(), 0L, true);
                arrayList.clear();
                for (int i5 = 0; i5 < int32; i5++) {
                    PortStatus portStatus = new PortStatus();
                    portStatus.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer, i5 * 40);
                    arrayList.add(portStatus);
                }
                int readInt32 = hwParcel.readInt32();
                if (hALCallback2.mUsbPortHidl.mSystemReady) {
                    if (readInt32 != 0) {
                        UsbPortManager.logAndPrint(6, hALCallback2.mPw, "port status enquiry failed");
                        return;
                    }
                    ArrayList arrayList2 = new ArrayList();
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        PortStatus portStatus2 = (PortStatus) it.next();
                        arrayList2.add(new RawPortInfo(portStatus2.portName, portStatus2.supportedModes, 0, portStatus2.currentMode, portStatus2.canChangeMode, portStatus2.currentPowerRole, portStatus2.canChangePowerRole, portStatus2.currentDataRole, portStatus2.canChangeDataRole, false, 0, false, 0, UsbPortHidl.sUsbDataStatus, false, 0, false, new int[0], 0, 0, null));
                        UsbPortManager.logAndPrint(4, hALCallback2.mPw, "ClientCallback V1_0: " + portStatus2.portName);
                    }
                    hALCallback2.mPortManager.updatePorts(arrayList2);
                    return;
                }
                return;
            }
            if (i == 2) {
                hwParcel.enforceInterface("android.hardware.usb@1.0::IUsbCallback");
                String readString = hwParcel.readString();
                HwBlob readBuffer2 = hwParcel.readBuffer(8L);
                readBuffer2.getInt32(0L);
                readBuffer2.getInt32(4L);
                if (hwParcel.readInt32() == 0) {
                    UsbPortManager.logAndPrint(4, hALCallback2.mPw, readString + " role switch successful");
                    return;
                }
                UsbPortManager.logAndPrint(6, hALCallback2.mPw, readString + " role switch failed");
                return;
            }
            if (i == 3) {
                hwParcel.enforceInterface("android.hardware.usb@1.1::IUsbCallback");
                ArrayList arrayList3 = new ArrayList();
                HwBlob readBuffer3 = hwParcel.readBuffer(16L);
                int int322 = readBuffer3.getInt32(8L);
                HwBlob readEmbeddedBuffer2 = hwParcel.readEmbeddedBuffer(int322 * 48, readBuffer3.handle(), 0L, true);
                arrayList3.clear();
                for (int i6 = 0; i6 < int322; i6++) {
                    PortStatus_1_1 portStatus_1_1 = new PortStatus_1_1();
                    long j = i6 * 48;
                    portStatus_1_1.status.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer2, j);
                    portStatus_1_1.supportedModes = readEmbeddedBuffer2.getInt32(j + 40);
                    portStatus_1_1.currentMode = readEmbeddedBuffer2.getInt32(j + 44);
                    arrayList3.add(portStatus_1_1);
                }
                int readInt322 = hwParcel.readInt32();
                if (hALCallback2.mUsbPortHidl.mSystemReady) {
                    if (readInt322 != 0) {
                        UsbPortManager.logAndPrint(6, hALCallback2.mPw, "port status enquiry failed");
                        return;
                    }
                    ArrayList arrayList4 = new ArrayList();
                    int size = arrayList3.size();
                    int i7 = 0;
                    while (i7 < size) {
                        PortStatus_1_1 portStatus_1_12 = (PortStatus_1_1) arrayList3.get(i7);
                        PortStatus portStatus3 = portStatus_1_12.status;
                        ArrayList arrayList5 = arrayList3;
                        arrayList4.add(new RawPortInfo(portStatus3.portName, portStatus_1_12.supportedModes, 0, portStatus_1_12.currentMode, portStatus3.canChangeMode, portStatus3.currentPowerRole, portStatus3.canChangePowerRole, portStatus3.currentDataRole, portStatus3.canChangeDataRole, false, 0, false, 0, UsbPortHidl.sUsbDataStatus, false, 0, false, new int[0], 0, 0, null));
                        PortStatus portStatus4 = portStatus_1_12.status;
                        int i8 = portStatus4.currentDataRole;
                        if (i8 == 1) {
                            int i9 = hALCallback2.preDataRole;
                            i3 = 2;
                            if (i9 == 0 || i9 == 2) {
                                hALCallback2.mPortManager.startBoost();
                                hALCallback2.preDataRole = i8;
                                UsbPortManager.logAndPrint(4, hALCallback2.mPw, "ClientCallback V1_1: " + portStatus4.portName);
                                i7++;
                                arrayList3 = arrayList5;
                            }
                        } else {
                            i3 = 2;
                        }
                        if (hALCallback2.preDataRole == 1 && (i8 == 0 || i8 == i3)) {
                            hALCallback2.mPortManager.stopBoost();
                        }
                        hALCallback2.preDataRole = i8;
                        UsbPortManager.logAndPrint(4, hALCallback2.mPw, "ClientCallback V1_1: " + portStatus4.portName);
                        i7++;
                        arrayList3 = arrayList5;
                    }
                    hALCallback2.mPortManager.updatePorts(arrayList4);
                    return;
                }
                return;
            }
            if (i != 4) {
                switch (i) {
                    case 256067662:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        ArrayList interfaceChain = interfaceChain();
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeStringVector(interfaceChain);
                        hwParcel2.send();
                        return;
                    case 256131655:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        hwParcel.readNativeHandle();
                        hwParcel.readStringVector();
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 256136003:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        hwParcel2.writeStatus(0);
                        hwParcel2.writeString("android.hardware.usb@1.2::IUsbCallback");
                        hwParcel2.send();
                        return;
                    case 256398152:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        ArrayList hashChain = getHashChain();
                        hwParcel2.writeStatus(0);
                        HwBlob hwBlob = new HwBlob(16);
                        int size2 = hashChain.size();
                        hwBlob.putInt32(8L, size2);
                        hwBlob.putBool(12L, false);
                        HwBlob hwBlob2 = new HwBlob(size2 * 32);
                        for (int i10 = 0; i10 < size2; i10++) {
                            long j2 = i10 * 32;
                            byte[] bArr = (byte[]) hashChain.get(i10);
                            if (bArr == null || bArr.length != 32) {
                                throw new IllegalArgumentException("Array element is not of the expected length");
                            }
                            hwBlob2.putInt8Array(j2, bArr);
                        }
                        hwBlob.putBlob(0L, hwBlob2);
                        hwParcel2.writeBuffer(hwBlob);
                        hwParcel2.send();
                        return;
                    case 256462420:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        return;
                    case 256921159:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        hwParcel2.writeStatus(0);
                        hwParcel2.send();
                        return;
                    case 257049926:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        DebugInfo debugInfo = getDebugInfo();
                        hwParcel2.writeStatus(0);
                        debugInfo.writeToParcel(hwParcel2);
                        hwParcel2.send();
                        return;
                    case 257120595:
                        hwParcel.enforceInterface(IBase.kInterfaceName);
                        HwBinder.enableInstrumentation();
                        return;
                    default:
                        return;
                }
            }
            hwParcel.enforceInterface("android.hardware.usb@1.2::IUsbCallback");
            ArrayList arrayList6 = new ArrayList();
            HwBlob readBuffer4 = hwParcel.readBuffer(16L);
            int int323 = readBuffer4.getInt32(8L);
            HwBlob readEmbeddedBuffer3 = hwParcel.readEmbeddedBuffer(int323 * 72, readBuffer4.handle(), 0L, true);
            arrayList6.clear();
            for (int i11 = 0; i11 < int323; i11++) {
                android.hardware.usb.V1_2.PortStatus portStatus5 = new android.hardware.usb.V1_2.PortStatus();
                PortStatus_1_1 portStatus_1_13 = new PortStatus_1_1();
                portStatus5.status_1_1 = portStatus_1_13;
                portStatus5.supportsEnableContaminantPresenceProtection = false;
                portStatus5.contaminantProtectionStatus = 0;
                portStatus5.supportsEnableContaminantPresenceDetection = false;
                portStatus5.contaminantDetectionStatus = 0;
                long j3 = i11 * 72;
                portStatus_1_13.status.readEmbeddedFromParcel(hwParcel, readEmbeddedBuffer3, j3);
                portStatus_1_13.supportedModes = readEmbeddedBuffer3.getInt32(j3 + 40);
                portStatus_1_13.currentMode = readEmbeddedBuffer3.getInt32(j3 + 44);
                portStatus5.supportedContaminantProtectionModes = readEmbeddedBuffer3.getInt32(48 + j3);
                portStatus5.supportsEnableContaminantPresenceProtection = readEmbeddedBuffer3.getBool(52 + j3);
                portStatus5.contaminantProtectionStatus = readEmbeddedBuffer3.getInt32(56 + j3);
                portStatus5.supportsEnableContaminantPresenceDetection = readEmbeddedBuffer3.getBool(60 + j3);
                portStatus5.contaminantDetectionStatus = readEmbeddedBuffer3.getInt32(j3 + 64);
                arrayList6.add(portStatus5);
            }
            int readInt323 = hwParcel.readInt32();
            if (hALCallback2.mUsbPortHidl.mSystemReady) {
                if (readInt323 != 0) {
                    UsbPortManager.logAndPrint(6, hALCallback2.mPw, "port status enquiry failed");
                    return;
                }
                ArrayList arrayList7 = new ArrayList();
                int size3 = arrayList6.size();
                int i12 = 0;
                while (i12 < size3) {
                    android.hardware.usb.V1_2.PortStatus portStatus6 = (android.hardware.usb.V1_2.PortStatus) arrayList6.get(i12);
                    PortStatus_1_1 portStatus_1_14 = portStatus6.status_1_1;
                    PortStatus portStatus7 = portStatus_1_14.status;
                    int i13 = size3;
                    ArrayList arrayList8 = arrayList6;
                    int i14 = i12;
                    ArrayList arrayList9 = arrayList7;
                    arrayList9.add(new RawPortInfo(portStatus7.portName, portStatus_1_14.supportedModes, portStatus6.supportedContaminantProtectionModes, portStatus_1_14.currentMode, portStatus7.canChangeMode, portStatus7.currentPowerRole, portStatus7.canChangePowerRole, portStatus7.currentDataRole, portStatus7.canChangeDataRole, portStatus6.supportsEnableContaminantPresenceProtection, portStatus6.contaminantProtectionStatus, portStatus6.supportsEnableContaminantPresenceDetection, portStatus6.contaminantDetectionStatus, UsbPortHidl.sUsbDataStatus, false, 0, false, new int[0], 0, 0, null));
                    PortStatus_1_1 portStatus_1_15 = portStatus6.status_1_1;
                    int i15 = portStatus_1_15.status.currentDataRole;
                    if (i15 == 1) {
                        hALCallback = this;
                        int i16 = hALCallback.preDataRole;
                        i4 = 2;
                        if (i16 == 0 || i16 == 2) {
                            hALCallback.mPortManager.startBoost();
                            hALCallback.preDataRole = i15;
                            UsbPortManager.logAndPrint(4, hALCallback.mPw, "ClientCallback V1_2: " + portStatus_1_15.status.portName);
                            i12 = i14 + 1;
                            size3 = i13;
                            arrayList6 = arrayList8;
                            arrayList7 = arrayList9;
                            hALCallback2 = hALCallback;
                        }
                    } else {
                        i4 = 2;
                        hALCallback = this;
                    }
                    if (hALCallback.preDataRole == 1 && (i15 == 0 || i15 == i4)) {
                        hALCallback.mPortManager.stopBoost();
                    }
                    hALCallback.preDataRole = i15;
                    UsbPortManager.logAndPrint(4, hALCallback.mPw, "ClientCallback V1_2: " + portStatus_1_15.status.portName);
                    i12 = i14 + 1;
                    size3 = i13;
                    arrayList6 = arrayList8;
                    arrayList7 = arrayList9;
                    hALCallback2 = hALCallback;
                }
                hALCallback2.mPortManager.updatePorts(arrayList7);
            }
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void ping() {
        }

        public final IHwInterface queryLocalInterface(String str) {
            if ("android.hardware.usb@1.2::IUsbCallback".equals(str)) {
                return this;
            }
            return null;
        }

        @Override // android.hidl.base.V1_0.IBase
        public final void setHALInstrumentation() {
        }

        public final String toString() {
            return "android.hardware.usb@1.2::IUsbCallback@Stub";
        }

        @Override // android.hidl.base.V1_0.IBase
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ServiceNotification extends IServiceNotification.Stub {
        public ServiceNotification() {
        }

        @Override // android.hidl.manager.V1_0.IServiceNotification
        public final void onRegistration(String str, String str2, boolean z) {
            String m = BootReceiver$$ExternalSyntheticOutline0.m("Usb hal service started ", str, " ", str2);
            int i = UsbPortManager.COMBO_SOURCE_HOST;
            Slog.println(4, "UsbPortManager", m);
            UsbPortHidl.this.connectToProxy$1();
        }
    }

    public UsbPortHidl(UsbPortManager usbPortManager) {
        Objects.requireNonNull(usbPortManager);
        this.mPw = null;
        HALCallback hALCallback = new HALCallback();
        hALCallback.preDataRole = 0;
        hALCallback.mPw = null;
        hALCallback.mPortManager = usbPortManager;
        hALCallback.mUsbPortHidl = this;
        this.mHALCallback = hALCallback;
        try {
            if (!IServiceManager.getService().registerForNotifications("android.hardware.usb@1.0::IUsb", "", new ServiceNotification())) {
                Slog.println(6, "UsbPortManager", "Failed to register service start notification");
            }
            connectToProxy$1();
        } catch (RemoteException e) {
            Slog.e("UsbPortManager", "Failed to register service start notification", e);
        }
    }

    public final void connectToProxy$1() {
        synchronized (this.mLock) {
            if (this.mProxy != null) {
                return;
            }
            try {
                IUsb asInterface = IUsb.asInterface(HwBinder.getService("android.hardware.usb@1.0::IUsb", "default"));
                this.mProxy = asInterface;
                asInterface.linkToDeath(new DeathRecipient(), 1000L);
                this.mProxy.setCallback(this.mHALCallback);
                this.mProxy.queryPortStatus();
            } catch (RemoteException e) {
                int i = UsbPortManager.COMBO_SOURCE_HOST;
                Slog.e("UsbPortManager", "connectToProxy: usb hal service not responding", e);
            } catch (NoSuchElementException e2) {
                int i2 = UsbPortManager.COMBO_SOURCE_HOST;
                Slog.e("UsbPortManager", "connectToProxy: usb hal service not found. Did the service fail to start?", e2);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void enableContaminantPresenceDetection(String str, long j, boolean z) {
        synchronized (this.mLock) {
            IUsb iUsb = this.mProxy;
            if (iUsb == null) {
                UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                return;
            }
            try {
                try {
                    android.hardware.usb.V1_2.IUsb.castFrom((IHwInterface) iUsb).enableContaminantPresenceDetection(str, z);
                } catch (ClassCastException e) {
                    UsbPortManager.logAndPrintException(this.mPw, "Method only applicable to V1.2 or above implementation", e);
                }
            } catch (RemoteException e2) {
                UsbPortManager.logAndPrintException(this.mPw, "Failed to set contaminant detection", e2);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void enableLimitPowerTransfer(String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal) {
        try {
            iUsbOperationInternal.onOperationComplete(2);
        } catch (RemoteException e) {
            UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete", e);
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final boolean enableUsbData(String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal) {
        boolean enableUsbDataSignal;
        try {
            if (getUsbHalVersion() != 13) {
                try {
                    iUsbOperationInternal.onOperationComplete(2);
                } catch (RemoteException e) {
                    UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e);
                }
                return false;
            }
            synchronized (this.mLock) {
                try {
                    enableUsbDataSignal = IUsb$Proxy.castFrom((IHwInterface) this.mProxy).enableUsbDataSignal(z);
                    Slog.d("UsbPortHidl", "enableUsbData success[" + enableUsbDataSignal + "] from USB HAL, while enable[" + z + "]");
                } catch (RemoteException e2) {
                    UsbPortManager.logAndPrintException(this.mPw, "Failed enableUsbData: opId:" + j + " portId=" + str, e2);
                    try {
                        iUsbOperationInternal.onOperationComplete(1);
                    } catch (RemoteException e3) {
                        UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e3);
                    }
                    return false;
                }
            }
            if (enableUsbDataSignal) {
                sUsbDataStatus = z ? 0 : 16;
            }
            try {
                iUsbOperationInternal.onOperationComplete(!enableUsbDataSignal ? 1 : 0);
            } catch (RemoteException e4) {
                UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e4);
            }
            return false;
        } catch (RemoteException e5) {
            UsbPortManager.logAndPrintException(this.mPw, "Failed to query USB HAL version. opID:" + j + " portId:" + str, e5);
            return false;
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void enableUsbDataWhileDocked(String str, long j, IUsbOperationInternal iUsbOperationInternal) {
        try {
            iUsbOperationInternal.onOperationComplete(2);
        } catch (RemoteException e) {
            UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete", e);
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final int getUsbHalVersion() {
        IHwBinder asBinder;
        int i;
        synchronized (this.mLock) {
            try {
                IUsb iUsb = this.mProxy;
                if (iUsb == null) {
                    throw new RemoteException("IUsb not initialized yet");
                }
                if (IUsb$Proxy.castFrom((IHwInterface) iUsb) != null) {
                    i = 13;
                } else if (android.hardware.usb.V1_2.IUsb.castFrom((IHwInterface) this.mProxy) != null) {
                    i = 12;
                } else {
                    IUsb iUsb2 = this.mProxy;
                    android.hardware.usb.V1_1.IUsb iUsb3 = null;
                    if (iUsb2 != null && (asBinder = iUsb2.asBinder()) != null) {
                        IHwInterface queryLocalInterface = asBinder.queryLocalInterface("android.hardware.usb@1.1::IUsb");
                        if (queryLocalInterface == null || !(queryLocalInterface instanceof android.hardware.usb.V1_1.IUsb)) {
                            IUsb.Proxy proxy = new IUsb.Proxy();
                            proxy.mRemote = asBinder;
                            try {
                                Iterator it = proxy.interfaceChain().iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    if (((String) it.next()).equals("android.hardware.usb@1.1::IUsb")) {
                                        iUsb3 = proxy;
                                        break;
                                    }
                                }
                            } catch (RemoteException unused) {
                            }
                        } else {
                            iUsb3 = (android.hardware.usb.V1_1.IUsb) queryLocalInterface;
                        }
                    }
                    i = iUsb3 != null ? 11 : 10;
                }
                int i2 = UsbPortManager.COMBO_SOURCE_HOST;
                Slog.println(4, "UsbPortManager", "USB HAL HIDL version: " + i);
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void queryPortStatus(long j) {
        synchronized (this.mLock) {
            android.hardware.usb.V1_0.IUsb iUsb = this.mProxy;
            if (iUsb == null) {
                UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                return;
            }
            try {
                iUsb.queryPortStatus();
            } catch (RemoteException e) {
                int i = UsbPortManager.COMBO_SOURCE_HOST;
                Slog.e("UsbPortManager", "ServiceStart: Failed to query port status", e);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void resetUsbPort(String str, long j, IUsbOperationInternal iUsbOperationInternal) {
        try {
            iUsbOperationInternal.onOperationComplete(2);
        } catch (RemoteException e) {
            UsbPortManager.logAndPrintException(this.mPw, "Failed to call onOperationComplete. opID:" + j + " portId:" + str, e);
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void switchDataRole(int i, String str, long j) {
        synchronized (this.mLock) {
            try {
                android.hardware.usb.V1_0.IUsb iUsb = this.mProxy;
                if (iUsb == null) {
                    UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                    return;
                }
                PortRole portRole = new PortRole();
                portRole.type = 0;
                portRole.role = i;
                try {
                    iUsb.switchRole(str, portRole);
                } catch (RemoteException e) {
                    UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB data role: portId=" + str + ", newDataRole=" + UsbPort.dataRoleToString(portRole.role), e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void switchMode(int i, String str, long j) {
        synchronized (this.mLock) {
            try {
                android.hardware.usb.V1_0.IUsb iUsb = this.mProxy;
                if (iUsb == null) {
                    UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                    return;
                }
                PortRole portRole = new PortRole();
                portRole.type = 2;
                portRole.role = i;
                try {
                    iUsb.switchRole(str, portRole);
                } catch (RemoteException e) {
                    UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB port mode: portId=" + str + ", newMode=" + UsbPort.modeToString(portRole.role), e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void switchPowerRole(int i, String str, long j) {
        synchronized (this.mLock) {
            try {
                android.hardware.usb.V1_0.IUsb iUsb = this.mProxy;
                if (iUsb == null) {
                    UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry !");
                    return;
                }
                PortRole portRole = new PortRole();
                portRole.type = 1;
                portRole.role = i;
                try {
                    iUsb.switchRole(str, portRole);
                } catch (RemoteException e) {
                    UsbPortManager.logAndPrintException(this.mPw, "Failed to set the USB power role: portId=" + str + ", newPowerRole=" + UsbPort.powerRoleToString(portRole.role), e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void systemReady() {
        this.mSystemReady = true;
    }
}
