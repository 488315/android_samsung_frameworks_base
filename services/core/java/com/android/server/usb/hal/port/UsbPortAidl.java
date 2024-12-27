package com.android.server.usb.hal.port;

import android.hardware.usb.AltModeData;
import android.hardware.usb.DisplayPortAltModeInfo;
import android.hardware.usb.IUsb;
import android.hardware.usb.IUsbCallback;
import android.hardware.usb.IUsbOperationInternal;
import android.hardware.usb.PortRole;
import android.hardware.usb.PortStatus;
import android.hardware.usb.UsbPort;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.IntArray;
import android.util.LongSparseArray;
import android.util.sysfwutil.Slog;

import com.android.internal.hidden_from_bootclasspath.android.hardware.usb.flags.Flags;
import com.android.internal.util.IndentingPrintWriter;
import com.android.server.usb.UsbPortManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbPortAidl implements UsbPortHal {
    public static final LongSparseArray sCallbacks = new LongSparseArray();
    public IBinder mBinder;
    public final HALCallback mHALCallback;
    public final Object mLock = new Object();
    public IUsb mProxy;
    public final IndentingPrintWriter mPw;
    public boolean mSystemReady;
    public long mTransactionId;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HALCallback extends Binder implements IUsbCallback {
        public UsbPortManager mPortManager;
        public IndentingPrintWriter mPw;
        public UsbPortAidl mUsbPortAidl;
        public int preDataRole;

        @Override // android.os.IInterface
        public final IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public final boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            PortStatus portStatus;
            DisplayPortAltModeInfo displayPortAltModeInfo;
            HALCallback hALCallback;
            int i3;
            HALCallback hALCallback2 = this;
            String str = IUsbCallback.DESCRIPTOR;
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(str);
            }
            if (i == 1598968902) {
                parcel2.writeString(str);
                return true;
            }
            if (i == 16777215) {
                parcel2.writeNoException();
                parcel2.writeInt(3);
                return true;
            }
            if (i == 16777214) {
                parcel2.writeNoException();
                parcel2.writeString("7fe46e9531884739d925b8caeee9dba5c411e228");
                return true;
            }
            switch (i) {
                case 1:
                    PortStatus[] portStatusArr =
                            (PortStatus[]) parcel.createTypedArray(PortStatus.CREATOR);
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    if (hALCallback2.mUsbPortAidl.mSystemReady) {
                        if (readInt != 0) {
                            UsbPortManager.logAndPrint(
                                    6, hALCallback2.mPw, "port status enquiry failed");
                        } else {
                            ArrayList arrayList = new ArrayList();
                            int length = portStatusArr.length;
                            int i4 = 0;
                            while (i4 < length) {
                                PortStatus portStatus2 = portStatusArr[i4];
                                String str2 = portStatus2.portName;
                                int i5 = 0;
                                for (byte b : portStatus2.supportedModes) {
                                    i5 |= hALCallback2.toPortMode(b);
                                }
                                int i6 = 0;
                                for (byte b2 : portStatus2.supportedContaminantProtectionModes) {
                                    i6 |= hALCallback2.toContaminantProtectionStatus(b2);
                                }
                                int portMode = hALCallback2.toPortMode(portStatus2.currentMode);
                                boolean z = portStatus2.canChangeMode;
                                byte b3 = portStatus2.currentPowerRole;
                                boolean z2 = portStatus2.canChangePowerRole;
                                byte b4 = portStatus2.currentDataRole;
                                boolean z3 = portStatus2.canChangeDataRole;
                                boolean z4 =
                                        portStatus2.supportsEnableContaminantPresenceProtection;
                                int contaminantProtectionStatus =
                                        hALCallback2.toContaminantProtectionStatus(
                                                portStatus2.contaminantProtectionStatus);
                                boolean z5 = portStatus2.supportsEnableContaminantPresenceDetection;
                                PortStatus[] portStatusArr2 = portStatusArr;
                                byte b5 = portStatus2.contaminantDetectionStatus;
                                int i7 = length;
                                ArrayList arrayList2 = arrayList;
                                int i8 = i4;
                                int i9 = 0;
                                for (byte b6 : portStatus2.usbDataStatus) {
                                    switch (b6) {
                                        case 1:
                                            i9 |= 1;
                                            break;
                                        case 2:
                                            i9 |= 2;
                                            break;
                                        case 3:
                                            i9 |= 4;
                                            break;
                                        case 4:
                                            i9 |= 200;
                                            break;
                                        case 5:
                                            i9 |= 16;
                                            break;
                                        case 6:
                                            i9 |= 32;
                                            break;
                                        case 7:
                                            i9 |= 72;
                                            break;
                                        case 8:
                                            i9 |= 136;
                                            break;
                                    }
                                }
                                UsbPortManager.logAndPrint(
                                        4, hALCallback2.mPw, "AIDL UsbDataStatus:" + i9);
                                boolean z6 = portStatus2.powerTransferLimited;
                                byte b7 = portStatus2.powerBrickStatus;
                                boolean z7 = portStatus2.supportsComplianceWarnings;
                                int[] iArr = portStatus2.complianceWarnings;
                                Objects.requireNonNull(iArr);
                                IntArray intArray = new IntArray();
                                Arrays.sort(iArr);
                                int length2 = iArr.length;
                                int i10 = 0;
                                while (i10 < length2) {
                                    int i11 = length2;
                                    int i12 = iArr[i10];
                                    int[] iArr2 = iArr;
                                    int i13 = i9;
                                    if (intArray.indexOf(i12) == -1 && i12 >= 1) {
                                        if (Flags.enableUsbDataComplianceWarning()) {
                                            if (i12 > 9) {
                                                intArray.add(1);
                                            } else {
                                                intArray.add(i12);
                                            }
                                        } else if (i12 > 4) {
                                            intArray.add(1);
                                        } else {
                                            intArray.add(i12);
                                        }
                                    }
                                    i10++;
                                    length2 = i11;
                                    iArr = iArr2;
                                    i9 = i13;
                                }
                                int i14 = i9;
                                int[] array = intArray.toArray();
                                int i15 = portStatus2.plugOrientation;
                                int i16 = 0;
                                for (AltModeData altModeData : portStatus2.supportedAltModes) {
                                    if (altModeData._tag == 0) {
                                        i16 = 1;
                                    }
                                }
                                AltModeData[] altModeDataArr = portStatus2.supportedAltModes;
                                int length3 = altModeDataArr.length;
                                int i17 = 0;
                                while (true) {
                                    if (i17 < length3) {
                                        AltModeData altModeData2 = altModeDataArr[i17];
                                        AltModeData[] altModeDataArr2 = altModeDataArr;
                                        if (altModeData2._tag == 0) {
                                            AltModeData.DisplayPortAltModeData
                                                    displayPortAltModeData =
                                                            altModeData2
                                                                    .getDisplayPortAltModeData();
                                            int i18 = displayPortAltModeData.partnerSinkStatus;
                                            int i19 = displayPortAltModeData.cableStatus;
                                            portStatus = portStatus2;
                                            switch (displayPortAltModeData.pinAssignment) {
                                                case 1:
                                                case 3:
                                                case 5:
                                                    i3 = 4;
                                                    break;
                                                case 2:
                                                case 4:
                                                case 6:
                                                    i3 = 2;
                                                    break;
                                                default:
                                                    i3 = 0;
                                                    break;
                                            }
                                            displayPortAltModeInfo =
                                                    new DisplayPortAltModeInfo(
                                                            i18,
                                                            i19,
                                                            i3,
                                                            displayPortAltModeData.hpd,
                                                            displayPortAltModeData
                                                                    .linkTrainingStatus);
                                        } else {
                                            i17++;
                                            altModeDataArr = altModeDataArr2;
                                        }
                                    } else {
                                        portStatus = portStatus2;
                                        displayPortAltModeInfo = null;
                                    }
                                }
                                arrayList2.add(
                                        new RawPortInfo(
                                                str2,
                                                i5,
                                                i6,
                                                portMode,
                                                z,
                                                b3,
                                                z2,
                                                b4,
                                                z3,
                                                z4,
                                                contaminantProtectionStatus,
                                                z5,
                                                b5,
                                                i14,
                                                z6,
                                                b7,
                                                z7,
                                                array,
                                                i15,
                                                i16,
                                                displayPortAltModeInfo));
                                PortStatus portStatus3 = portStatus;
                                byte b8 = portStatus3.currentDataRole;
                                if (b8 == 1) {
                                    hALCallback = this;
                                    int i20 = hALCallback.preDataRole;
                                    if (i20 == 0 || i20 == 2) {
                                        hALCallback.mPortManager.startBoost();
                                        hALCallback.preDataRole = b8;
                                        UsbPortManager.logAndPrint(
                                                4,
                                                hALCallback.mPw,
                                                "ClientCallback AIDL V1: " + portStatus3.portName);
                                        arrayList = arrayList2;
                                        portStatusArr = portStatusArr2;
                                        i4 = i8 + 1;
                                        hALCallback2 = hALCallback;
                                        length = i7;
                                    }
                                } else {
                                    hALCallback = this;
                                }
                                if (hALCallback.preDataRole == 1 && (b8 == 0 || b8 == 2)) {
                                    hALCallback.mPortManager.stopBoost();
                                }
                                hALCallback.preDataRole = b8;
                                UsbPortManager.logAndPrint(
                                        4,
                                        hALCallback.mPw,
                                        "ClientCallback AIDL V1: " + portStatus3.portName);
                                arrayList = arrayList2;
                                portStatusArr = portStatusArr2;
                                i4 = i8 + 1;
                                hALCallback2 = hALCallback;
                                length = i7;
                            }
                            hALCallback2.mPortManager.updatePorts(arrayList);
                        }
                    }
                    return true;
                case 2:
                    String readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    if (readInt2 == 0) {
                        UsbPortManager.logAndPrint(
                                4,
                                hALCallback2.mPw,
                                readString + " role switch successful. opID:" + readLong);
                        break;
                    } else {
                        UsbPortManager.logAndPrint(
                                6,
                                hALCallback2.mPw,
                                readString
                                        + " role switch failed. err:"
                                        + readInt2
                                        + "opID:"
                                        + readLong);
                        break;
                    }
                case 3:
                    String readString2 = parcel.readString();
                    boolean readBoolean = parcel.readBoolean();
                    int readInt3 = parcel.readInt();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    if (readInt3 == 0) {
                        UsbPortManager.logAndPrint(
                                4,
                                hALCallback2.mPw,
                                "notifyEnableUsbDataStatus:"
                                        + readString2
                                        + ": opID:"
                                        + readLong2
                                        + " enable:"
                                        + readBoolean);
                    } else {
                        UsbPortManager.logAndPrint(
                                6,
                                hALCallback2.mPw,
                                readString2
                                        + "notifyEnableUsbDataStatus: opID:"
                                        + readLong2
                                        + " failed. err:"
                                        + readInt3);
                    }
                    try {
                        ((IUsbOperationInternal) UsbPortAidl.sCallbacks.get(readLong2))
                                .onOperationComplete(readInt3 == 0 ? 0 : 1);
                        break;
                    } catch (RemoteException e) {
                        UsbPortManager.logAndPrintException(
                                hALCallback2.mPw,
                                "notifyEnableUsbDataStatus: Failed to call onOperationComplete",
                                e);
                        break;
                    }
                case 4:
                    String readString3 = parcel.readString();
                    int readInt4 = parcel.readInt();
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    if (readInt4 == 0) {
                        UsbPortManager.logAndPrint(
                                4,
                                hALCallback2.mPw,
                                readString3 + ": opID:" + readLong3 + " successful");
                    } else {
                        UsbPortManager.logAndPrint(
                                6,
                                hALCallback2.mPw,
                                readString3
                                        + "notifyEnableUsbDataWhileDockedStatus: opID:"
                                        + readLong3
                                        + " failed. err:"
                                        + readInt4);
                    }
                    try {
                        LongSparseArray longSparseArray = UsbPortAidl.sCallbacks;
                        if (((IUsbOperationInternal) longSparseArray.get(readLong3)) != null) {
                            ((IUsbOperationInternal) longSparseArray.get(readLong3))
                                    .onOperationComplete(readInt4 == 0 ? 0 : 1);
                            break;
                        }
                    } catch (RemoteException e2) {
                        UsbPortManager.logAndPrintException(
                                hALCallback2.mPw,
                                "notifyEnableUsbDataWhileDockedStatus: Failed to call"
                                    + " onOperationComplete",
                                e2);
                        break;
                    }
                    break;
                case 5:
                    String readString4 = parcel.readString();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt5 = parcel.readInt();
                    long readLong4 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    if (readInt5 == 0) {
                        UsbPortManager.logAndPrint(
                                4,
                                hALCallback2.mPw,
                                "notifyContaminantEnabledStatus:"
                                        + readString4
                                        + ": opID:"
                                        + readLong4
                                        + " enable:"
                                        + readBoolean2);
                        break;
                    } else {
                        UsbPortManager.logAndPrint(
                                6,
                                hALCallback2.mPw,
                                readString4
                                        + "notifyContaminantEnabledStatus: opID:"
                                        + readLong4
                                        + " failed. err:"
                                        + readInt5);
                        break;
                    }
                case 6:
                    String readString5 = parcel.readString();
                    int readInt6 = parcel.readInt();
                    long readLong5 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    if (readInt6 == 0) {
                        UsbPortManager.logAndPrint(
                                4,
                                hALCallback2.mPw,
                                readString5 + ": opID:" + readLong5 + " successful");
                        break;
                    } else {
                        UsbPortManager.logAndPrint(
                                6,
                                hALCallback2.mPw,
                                readString5 + ": opID:" + readLong5 + " failed. err:" + readInt6);
                        break;
                    }
                case 7:
                    String readString6 = parcel.readString();
                    parcel.readBoolean();
                    int readInt7 = parcel.readInt();
                    long readLong6 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    if (readInt7 == 0) {
                        UsbPortManager.logAndPrint(
                                4,
                                hALCallback2.mPw,
                                readString6 + ": opID:" + readLong6 + " successful");
                    } else {
                        UsbPortManager.logAndPrint(
                                6,
                                hALCallback2.mPw,
                                readString6
                                        + "notifyLimitPowerTransferStatus: opID:"
                                        + readLong6
                                        + " failed. err:"
                                        + readInt7);
                    }
                    try {
                        LongSparseArray longSparseArray2 = UsbPortAidl.sCallbacks;
                        if (((IUsbOperationInternal) longSparseArray2.get(readLong6)) != null) {
                            ((IUsbOperationInternal) longSparseArray2.get(readLong6))
                                    .onOperationComplete(readInt7 == 0 ? 0 : 1);
                            break;
                        }
                    } catch (RemoteException e3) {
                        UsbPortManager.logAndPrintException(
                                hALCallback2.mPw,
                                "enableLimitPowerTransfer: Failed to call onOperationComplete",
                                e3);
                        break;
                    }
                    break;
                case 8:
                    String readString7 = parcel.readString();
                    int readInt8 = parcel.readInt();
                    long readLong7 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    if (readInt8 == 0) {
                        UsbPortManager.logAndPrint(
                                4,
                                hALCallback2.mPw,
                                "notifyResetUsbPortStatus:" + readString7 + ": opID:" + readLong7);
                    } else {
                        UsbPortManager.logAndPrint(
                                6,
                                hALCallback2.mPw,
                                readString7
                                        + "notifyEnableUsbDataStatus: opID:"
                                        + readLong7
                                        + " failed. err:"
                                        + readInt8);
                    }
                    try {
                        ((IUsbOperationInternal) UsbPortAidl.sCallbacks.get(readLong7))
                                .onOperationComplete(readInt8 == 0 ? 0 : 1);
                        break;
                    } catch (RemoteException e4) {
                        UsbPortManager.logAndPrintException(
                                hALCallback2.mPw,
                                "notifyResetUsbPortStatus: Failed to call onOperationComplete",
                                e4);
                        break;
                    }
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        public final int toContaminantProtectionStatus(byte b) {
            if (b == 0) {
                return 0;
            }
            int i = 1;
            if (b != 1) {
                i = 2;
                if (b != 2) {
                    if (b == 3) {
                        return 4;
                    }
                    if (b == 4) {
                        return 8;
                    }
                    UsbPortManager.logAndPrint(
                            6, this.mPw, "Unrecognized aidlContaminantProtection:" + ((int) b));
                    return 0;
                }
            }
            return i;
        }

        public final int toPortMode(byte b) {
            if (b == 0) {
                return 0;
            }
            int i = 1;
            if (b != 1) {
                i = 2;
                if (b != 2) {
                    i = 3;
                    if (b != 3) {
                        i = 4;
                        if (b != 4) {
                            if (b == 5) {
                                return 8;
                            }
                            UsbPortManager.logAndPrint(
                                    6, this.mPw, "Unrecognized aidlPortMode:" + ((int) b));
                            return 0;
                        }
                    }
                }
            }
            return i;
        }
    }

    public UsbPortAidl(UsbPortManager usbPortManager) {
        Objects.requireNonNull(usbPortManager);
        this.mPw = null;
        HALCallback hALCallback = new HALCallback();
        hALCallback.markVintfStability();
        hALCallback.attachInterface(hALCallback, IUsbCallback.DESCRIPTOR);
        hALCallback.preDataRole = 0;
        hALCallback.mPw = null;
        hALCallback.mPortManager = usbPortManager;
        hALCallback.mUsbPortAidl = this;
        this.mHALCallback = hALCallback;
        connectToProxy();
    }

    public final void connectToProxy() {
        synchronized (this.mLock) {
            if (this.mProxy != null) {
                return;
            }
            try {
                IBinder waitForService =
                        ServiceManager.waitForService("android.hardware.usb.IUsb/default");
                this.mBinder = waitForService;
                this.mProxy = IUsb.Stub.asInterface(waitForService);
                this.mBinder.linkToDeath(
                        new IBinder
                                .DeathRecipient() { // from class:
                                                    // com.android.server.usb.hal.port.UsbPortAidl$$ExternalSyntheticLambda0
                            @Override // android.os.IBinder.DeathRecipient
                            public final void binderDied() {
                                UsbPortAidl usbPortAidl = UsbPortAidl.this;
                                UsbPortManager.logAndPrint(
                                        6, usbPortAidl.mPw, "Usb AIDL hal service died");
                                synchronized (usbPortAidl.mLock) {
                                    usbPortAidl.mProxy = null;
                                }
                                usbPortAidl.connectToProxy();
                            }
                        },
                        0);
                ((IUsb.Stub.Proxy) this.mProxy).setCallback(this.mHALCallback);
                IUsb iUsb = this.mProxy;
                long j = this.mTransactionId + 1;
                this.mTransactionId = j;
                ((IUsb.Stub.Proxy) iUsb).queryPortStatus(j);
            } catch (RemoteException e) {
                int i = UsbPortManager.COMBO_SOURCE_HOST;
                Slog.e("UsbPortManager", "connectToProxy: usb hal service not responding", e);
            } catch (NoSuchElementException e2) {
                int i2 = UsbPortManager.COMBO_SOURCE_HOST;
                Slog.e(
                        "UsbPortManager",
                        "connectToProxy: usb hal service not found. Did the service fail to start?",
                        e2);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void enableContaminantPresenceDetection(String str, long j, boolean z) {
        synchronized (this.mLock) {
            IUsb iUsb = this.mProxy;
            if (iUsb == null) {
                UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID: " + j);
                return;
            }
            try {
                ((IUsb.Stub.Proxy) iUsb).enableContaminantPresenceDetection(str, j, z);
            } catch (RemoteException e) {
                UsbPortManager.logAndPrintException(
                        this.mPw, "Failed to set contaminant detection. opID:" + j, e);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void enableLimitPowerTransfer(
            String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal) {
        LongSparseArray longSparseArray;
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            try {
            } catch (RemoteException e) {
                UsbPortManager.logAndPrintException(
                        this.mPw,
                        "enableLimitPowerTransfer: Failed to call onOperationComplete portID="
                                + str
                                + " opID:"
                                + j,
                        e);
            }
            if (this.mProxy == null) {
                UsbPortManager.logAndPrint(
                        6, this.mPw, "enableLimitPowerTransfer: Proxy is null. Retry !opID:" + j);
                iUsbOperationInternal.onOperationComplete(1);
                return;
            }
            long j2 = j;
            while (true) {
                longSparseArray = sCallbacks;
                if (longSparseArray.get(j2) == null) {
                    break;
                } else {
                    j2 = ThreadLocalRandom.current().nextInt();
                }
            }
            if (j2 != j) {
                UsbPortManager.logAndPrint(
                        4,
                        this.mPw,
                        "enableUsbData: operationID exists ! opID:" + j + " key:" + j2);
            }
            try {
                longSparseArray.put(j2, iUsbOperationInternal);
                ((IUsb.Stub.Proxy) this.mProxy).limitPowerTransfer(str, j2, z);
            } catch (RemoteException e2) {
                UsbPortManager.logAndPrintException(
                        this.mPw,
                        "enableLimitPowerTransfer: Failed while invoking AIDL HAL portID="
                                + str
                                + " opID:"
                                + j,
                        e2);
                if (iUsbOperationInternal != null) {
                    iUsbOperationInternal.onOperationComplete(1);
                }
                sCallbacks.remove(j2);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final boolean enableUsbData(
            String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal) {
        long j2;
        LongSparseArray longSparseArray;
        Objects.requireNonNull(str);
        Objects.requireNonNull(iUsbOperationInternal);
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    UsbPortManager.logAndPrint(
                            6, this.mPw, "enableUsbData: Proxy is null. Retry !opID:" + j);
                    iUsbOperationInternal.onOperationComplete(1);
                    return false;
                }
                j2 = j;
                while (true) {
                    try {
                        longSparseArray = sCallbacks;
                        if (longSparseArray.get(j2) == null) {
                            break;
                        }
                        j2 = ThreadLocalRandom.current().nextInt();
                    } catch (RemoteException e) {
                        e = e;
                        UsbPortManager.logAndPrintException(
                                this.mPw,
                                "enableUsbData: Failed to call onOperationComplete portID="
                                        + str
                                        + "opID:"
                                        + j,
                                e);
                        sCallbacks.remove(j2);
                        return false;
                    }
                }
                if (j2 != j) {
                    UsbPortManager.logAndPrint(
                            4,
                            this.mPw,
                            "enableUsbData: operationID exists ! opID:" + j + " key:" + j2);
                }
                try {
                    longSparseArray.put(j2, iUsbOperationInternal);
                    ((IUsb.Stub.Proxy) this.mProxy).enableUsbData(str, j2, z);
                    return true;
                } catch (RemoteException e2) {
                    UsbPortManager.logAndPrintException(
                            this.mPw,
                            "enableUsbData: Failed to invoke enableUsbData: portID="
                                    + str
                                    + "opID:"
                                    + j,
                            e2);
                    iUsbOperationInternal.onOperationComplete(1);
                    sCallbacks.remove(j2);
                    return false;
                }
            } catch (RemoteException e3) {
                e = e3;
                j2 = j;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void enableUsbDataWhileDocked(
            String str, long j, IUsbOperationInternal iUsbOperationInternal) {
        LongSparseArray longSparseArray;
        Objects.requireNonNull(str);
        synchronized (this.mLock) {
            try {
            } catch (RemoteException e) {
                UsbPortManager.logAndPrintException(
                        this.mPw,
                        "enableUsbDataWhileDocked: Failed to call onOperationComplete portID="
                                + str
                                + " opID:"
                                + j,
                        e);
            }
            if (this.mProxy == null) {
                UsbPortManager.logAndPrint(
                        6, this.mPw, "enableUsbDataWhileDocked: Proxy is null. Retry !opID:" + j);
                iUsbOperationInternal.onOperationComplete(1);
                return;
            }
            long j2 = j;
            while (true) {
                longSparseArray = sCallbacks;
                if (longSparseArray.get(j2) == null) {
                    break;
                } else {
                    j2 = ThreadLocalRandom.current().nextInt();
                }
            }
            if (j2 != j) {
                UsbPortManager.logAndPrint(
                        4,
                        this.mPw,
                        "enableUsbDataWhileDocked: operationID exists ! opID:" + j + " key:" + j2);
            }
            try {
                longSparseArray.put(j2, iUsbOperationInternal);
                ((IUsb.Stub.Proxy) this.mProxy).enableUsbDataWhileDocked(j2, str);
            } catch (RemoteException e2) {
                UsbPortManager.logAndPrintException(
                        this.mPw,
                        "enableUsbDataWhileDocked: error while invoking halportID="
                                + str
                                + " opID:"
                                + j,
                        e2);
                if (iUsbOperationInternal != null) {
                    iUsbOperationInternal.onOperationComplete(1);
                }
                sCallbacks.remove(j2);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final int getUsbHalVersion() {
        synchronized (this.mLock) {
            if (this.mProxy == null) {
                throw new RemoteException("IUsb not initialized yet");
            }
        }
        int i = UsbPortManager.COMBO_SOURCE_HOST;
        Slog.println(4, "UsbPortManager", "USB HAL AIDL version: USB_HAL_V2_0");
        return 20;
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void queryPortStatus(long j) {
        synchronized (this.mLock) {
            IUsb iUsb = this.mProxy;
            if (iUsb == null) {
                UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID:" + j);
                return;
            }
            try {
                ((IUsb.Stub.Proxy) iUsb).queryPortStatus(j);
            } catch (RemoteException e) {
                int i = UsbPortManager.COMBO_SOURCE_HOST;
                Slog.e("UsbPortManager", "ServiceStart: Failed to query port status. opID:" + j, e);
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void resetUsbPort(
            String str, long j, IUsbOperationInternal iUsbOperationInternal) {
        long j2;
        LongSparseArray longSparseArray;
        Objects.requireNonNull(str);
        Objects.requireNonNull(iUsbOperationInternal);
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    UsbPortManager.logAndPrint(
                            6, this.mPw, "resetUsbPort: Proxy is null. Retry !opID:" + j);
                    iUsbOperationInternal.onOperationComplete(1);
                }
                j2 = j;
                while (true) {
                    try {
                        longSparseArray = sCallbacks;
                        if (longSparseArray.get(j2) == null) {
                            break;
                        } else {
                            j2 = ThreadLocalRandom.current().nextInt();
                        }
                    } catch (RemoteException e) {
                        e = e;
                        UsbPortManager.logAndPrintException(
                                this.mPw,
                                "resetUsbPort: Failed to call onOperationComplete portID="
                                        + str
                                        + "opID:"
                                        + j,
                                e);
                        sCallbacks.remove(j2);
                    }
                }
                if (j2 != j) {
                    UsbPortManager.logAndPrint(
                            4,
                            this.mPw,
                            "resetUsbPort: operationID exists ! opID:" + j + " key:" + j2);
                }
                try {
                    longSparseArray.put(j2, iUsbOperationInternal);
                    IUsb iUsb = this.mProxy;
                    if (iUsb != null) {
                        ((IUsb.Stub.Proxy) iUsb).resetUsbPort(j2, str);
                    }
                } catch (RemoteException e2) {
                    UsbPortManager.logAndPrintException(
                            this.mPw,
                            "resetUsbPort: Failed to resetUsbPort: portID=" + str + "opId:" + j,
                            e2);
                    iUsbOperationInternal.onOperationComplete(1);
                    sCallbacks.remove(j2);
                }
            } catch (RemoteException e3) {
                e = e3;
                j2 = j;
            }
        }
    }

    @Override // com.android.server.usb.hal.port.UsbPortHal
    public final void switchDataRole(int i, String str, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mProxy == null) {
                    UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID:" + j);
                    return;
                }
                PortRole portRole = new PortRole();
                Byte valueOf = Byte.valueOf((byte) i);
                portRole._tag = 1;
                portRole._value = valueOf;
                try {
                    ((IUsb.Stub.Proxy) this.mProxy).switchRole(str, portRole, j);
                } catch (RemoteException e) {
                    UsbPortManager.logAndPrintException(
                            this.mPw,
                            "Failed to set the USB data role: portId="
                                    + str
                                    + ", newDataRole="
                                    + UsbPort.dataRoleToString(i)
                                    + "opID:"
                                    + j,
                            e);
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
                if (this.mProxy == null) {
                    UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID:" + j);
                    return;
                }
                PortRole portRole = new PortRole();
                Byte valueOf = Byte.valueOf((byte) i);
                portRole._tag = 2;
                portRole._value = valueOf;
                try {
                    ((IUsb.Stub.Proxy) this.mProxy).switchRole(str, portRole, j);
                } catch (RemoteException e) {
                    UsbPortManager.logAndPrintException(
                            this.mPw,
                            "Failed to set the USB port mode: portId="
                                    + str
                                    + ", newMode="
                                    + UsbPort.modeToString(i)
                                    + "opID:"
                                    + j,
                            e);
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
                if (this.mProxy == null) {
                    UsbPortManager.logAndPrint(6, this.mPw, "Proxy is null. Retry ! opID:" + j);
                    return;
                }
                PortRole portRole = new PortRole();
                Byte valueOf = Byte.valueOf((byte) i);
                portRole._tag = 0;
                portRole._value = valueOf;
                try {
                    ((IUsb.Stub.Proxy) this.mProxy).switchRole(str, portRole, j);
                } catch (RemoteException e) {
                    UsbPortManager.logAndPrintException(
                            this.mPw,
                            "Failed to set the USB power role: portId="
                                    + str
                                    + ", newPowerRole="
                                    + UsbPort.powerRoleToString(i)
                                    + "opID:"
                                    + j,
                            e);
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
