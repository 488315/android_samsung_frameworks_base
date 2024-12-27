package com.android.server.usb;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.hardware.usb.DisplayPortAltModeInfo;
import android.hardware.usb.IDisplayPortAltModeInfoListener;
import android.hardware.usb.IUsbOperationInternal;
import android.hardware.usb.ParcelableUsbPort;
import android.hardware.usb.UsbManager;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.hardware.usb.V1_0.IUsb;
import android.os.Bundle;
import android.os.Handler;
import android.os.HwBinder;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.LongSparseArray;
import android.util.sysfwutil.Slog;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.usb.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.IndentingPrintWriter;
import com.android.internal.util.dump.DualDumpOutputStream;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.usb.UsbService;
import com.android.server.usb.hal.port.RawPortInfo;
import com.android.server.usb.hal.port.UsbPortAidl;
import com.android.server.usb.hal.port.UsbPortHal;
import com.android.server.usb.hal.port.UsbPortHidl;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.os.SemDvfsManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class UsbPortManager implements IBinder.DeathRecipient {
    public final Context mContext;
    public int mIsPortContaminatedNotificationId;
    public NotificationManager mNotificationManager;
    public long mTransactionId;
    public final UsbPortHal mUsbPortHal;
    public final SemDvfsManager usbPortBooster;
    public static final int COMBO_SOURCE_HOST = UsbPort.combineRolesAsBit(1, 1);
    public static final int COMBO_SOURCE_DEVICE = UsbPort.combineRolesAsBit(1, 2);
    public static final int COMBO_SINK_HOST = UsbPort.combineRolesAsBit(2, 1);
    public static final int COMBO_SINK_DEVICE = UsbPort.combineRolesAsBit(2, 2);
    public final Object mLock = new Object();
    public final ArrayMap mPorts = new ArrayMap();
    public final ArrayMap mSimulatedPorts = new ArrayMap();
    public final ArrayMap mConnected = new ArrayMap();
    public final ArrayMap mContaminantStatus = new ArrayMap();
    public final Object mDisplayPortListenerLock = new Object();
    public final ArrayMap mDisplayPortListeners = new ArrayMap();
    public final AnonymousClass1 mHandler = new Handler(FgThread.get().getLooper()) { // from class: com.android.server.usb.UsbPortManager.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                UsbPortManager usbPortManager = UsbPortManager.this;
                usbPortManager.mNotificationManager = (NotificationManager) usbPortManager.mContext.getSystemService("notification");
                return;
            }
            ArrayList parcelableArrayList = message.getData().getParcelableArrayList("port_info", RawPortInfo.class);
            synchronized (UsbPortManager.this.mLock) {
                UsbPortManager.this.updatePortsLocked(null, parcelableArrayList);
            }
        }
    };

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PortInfo {
        public boolean mCanChangeDataRole;
        public boolean mCanChangeMode;
        public boolean mCanChangePowerRole;
        public long mConnectedAtMillis;
        public int mDisposition;
        public long mLastConnectDurationMillis;
        public final UsbPort mUsbPort;
        public UsbPortStatus mUsbPortStatus;
        public int mComplianceWarningChange = 0;
        public int mDisplayPortAltModeChange = 0;

        public PortInfo(UsbManager usbManager, String str, int i, int i2, boolean z, boolean z2, boolean z3, int i3) {
            this.mUsbPort = new UsbPort(usbManager, str, i, i2, z, z2, z3, i3);
        }

        public final void dump(DualDumpOutputStream dualDumpOutputStream) {
            long start = dualDumpOutputStream.start("usb_ports", 2246267895810L);
            DumpUtils.writePort(dualDumpOutputStream, "port", 1146756268033L, this.mUsbPort);
            DumpUtils.writePortStatus(dualDumpOutputStream, Constants.JSON_CLIENT_DATA_STATUS, 1146756268034L, this.mUsbPortStatus);
            dualDumpOutputStream.write("can_change_mode", 1133871366147L, this.mCanChangeMode);
            dualDumpOutputStream.write("can_change_power_role", 1133871366148L, this.mCanChangePowerRole);
            dualDumpOutputStream.write("can_change_data_role", 1133871366149L, this.mCanChangeDataRole);
            dualDumpOutputStream.write("connected_at_millis", 1112396529670L, this.mConnectedAtMillis);
            dualDumpOutputStream.write("last_connect_duration_millis", 1112396529671L, this.mLastConnectDurationMillis);
            dualDumpOutputStream.end(start);
        }

        /* JADX WARN: Removed duplicated region for block: B:39:0x0141  */
        /* JADX WARN: Removed duplicated region for block: B:46:0x0158  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x010a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean setStatus(int r18, boolean r19, int r20, boolean r21, int r22, boolean r23, int r24, int r25, int r26, int r27, boolean r28, int r29, int[] r30, int r31, android.hardware.usb.DisplayPortAltModeInfo r32) {
            /*
                Method dump skipped, instructions count: 362
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.usb.UsbPortManager.PortInfo.setStatus(int, boolean, int, boolean, int, boolean, int, int, int, int, boolean, int, int[], int, android.hardware.usb.DisplayPortAltModeInfo):boolean");
        }

        public final String toString() {
            return "port=" + this.mUsbPort + ", status=" + this.mUsbPortStatus + ", canChangeMode=" + this.mCanChangeMode + ", canChangePowerRole=" + this.mCanChangePowerRole + ", canChangeDataRole=" + this.mCanChangeDataRole + ", connectedAtMillis=" + this.mConnectedAtMillis + ", lastConnectDurationMillis=" + this.mLastConnectDurationMillis;
        }
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.usb.UsbPortManager$1] */
    public UsbPortManager(Context context) {
        boolean z;
        UsbPortHal usbPortHal = null;
        this.usbPortBooster = null;
        this.mContext = context;
        if (this.usbPortBooster == null) {
            this.usbPortBooster = SemDvfsManager.createInstance(context, "USB_PORT_MANAGER_BOOSTER");
        }
        if (this.usbPortBooster != null) {
            Slog.d("UsbPortManager", "To boost, setHint");
            this.usbPortBooster.setHint(1150);
        }
        Slog.println(3, "UsbPortManager", "Querying USB HAL version");
        LongSparseArray longSparseArray = UsbPortAidl.sCallbacks;
        try {
            z = ServiceManager.isDeclared("android.hardware.usb.IUsb/default");
        } catch (NoSuchElementException e) {
            Slog.e("UsbPortManager", "connectToProxy: usb Aidl hal service not found.", e);
            z = false;
        }
        if (z) {
            Slog.println(4, "UsbPortManager", "USB HAL AIDL present");
            usbPortHal = new UsbPortAidl(this);
        } else {
            try {
                IUsb.asInterface(HwBinder.getService("android.hardware.usb@1.0::IUsb", "default", true));
            } catch (RemoteException e2) {
                Slog.e("UsbPortManager", "IUSB hal service present but failed to get service", e2);
            } catch (NoSuchElementException e3) {
                Slog.e("UsbPortManager", "connectToProxy: usb hidl hal service not found.", e3);
            }
            Slog.println(4, "UsbPortManager", "USB HAL HIDL present");
            usbPortHal = new UsbPortHidl(this);
        }
        this.mUsbPortHal = usbPortHal;
        Slog.println(3, "UsbPortManager", "getInstance done");
    }

    public static void logAndPrint(int i, IndentingPrintWriter indentingPrintWriter, String str) {
        Slog.println(i, "UsbPortManager", str);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str);
        }
    }

    public static void logAndPrintException(IndentingPrintWriter indentingPrintWriter, String str, Exception exc) {
        Slog.e("UsbPortManager", str, exc);
        if (indentingPrintWriter != null) {
            indentingPrintWriter.println(str + exc);
        }
    }

    public final void addOrUpdatePortLocked(String str, int i, int i2, int i3, boolean z, int i4, boolean z2, int i5, boolean z3, boolean z4, int i6, boolean z5, int i7, int i8, boolean z6, int i9, boolean z7, int[] iArr, int i10, int i11, DisplayPortAltModeInfo displayPortAltModeInfo, IndentingPrintWriter indentingPrintWriter) {
        int i12;
        boolean z8;
        String str2;
        int i13;
        int i14;
        int combineRolesAsBit;
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("addOrUpdatePortLocked()++ : portId=", str, " supportedModes=");
        m.append(UsbPort.modeToString(i));
        m.append(" currentMode=");
        m.append(UsbPort.modeToString(i3));
        m.append(" canChangeMode=");
        m.append(z);
        m.append(" currentPowerRole=");
        m.append(UsbPort.powerRoleToString(i4));
        m.append(" canChangePowerRole=");
        m.append(z2);
        m.append(" currentDataRole=");
        m.append(UsbPort.dataRoleToString(i5));
        m.append(" canChangeDataRole=");
        m.append(z3);
        Slog.d("UsbPortManager", m.toString());
        if ((i & 3) == 3) {
            i12 = i3;
            z8 = z;
        } else if (i3 == 0 || i3 == i) {
            i12 = i3;
            z8 = false;
        } else {
            logAndPrint(5, indentingPrintWriter, "Ignoring inconsistent current mode from USB port driver: supportedModes=" + UsbPort.modeToString(i) + ", currentMode=" + UsbPort.modeToString(i3));
            i12 = 0;
            z8 = false;
        }
        int combineRolesAsBit2 = UsbPort.combineRolesAsBit(i4, i5);
        if (i12 != 0 && i4 != 0 && i5 != 0) {
            if (z2 && z3) {
                i13 = COMBO_SOURCE_HOST | COMBO_SOURCE_DEVICE | COMBO_SINK_HOST;
                i14 = COMBO_SINK_DEVICE;
            } else {
                if (z2) {
                    combineRolesAsBit2 |= UsbPort.combineRolesAsBit(1, i5);
                    combineRolesAsBit = UsbPort.combineRolesAsBit(2, i5);
                } else if (z3) {
                    combineRolesAsBit2 |= UsbPort.combineRolesAsBit(i4, 1);
                    combineRolesAsBit = UsbPort.combineRolesAsBit(i4, 2);
                } else if (z8) {
                    i13 = COMBO_SOURCE_HOST;
                    i14 = COMBO_SINK_DEVICE;
                }
                combineRolesAsBit2 |= combineRolesAsBit;
            }
            combineRolesAsBit = i13 | i14;
            combineRolesAsBit2 |= combineRolesAsBit;
        }
        int i15 = combineRolesAsBit2;
        Slog.d("UsbPortManager", "supportedRoleCombinations=" + i15);
        PortInfo portInfo = (PortInfo) this.mPorts.get(str);
        if (portInfo == null) {
            PortInfo portInfo2 = new PortInfo((UsbManager) this.mContext.getSystemService(UsbManager.class), str, i, i2, z4, z5, z7, i11);
            portInfo2.setStatus(i12, z8, i4, z2, i5, z3, i15, i6, i7, i8, z6, i9, iArr, i10, displayPortAltModeInfo);
            this.mPorts.put(str, portInfo2);
            Slog.d("UsbPortManager", "mPorts(" + str + ") put size=" + this.mPorts.size());
            str2 = "UsbPortManager";
        } else {
            if (i != portInfo.mUsbPort.getSupportedModes()) {
                logAndPrint(5, indentingPrintWriter, "Ignoring inconsistent list of supported modes from USB port driver (should be immutable): previous=" + UsbPort.modeToString(portInfo.mUsbPort.getSupportedModes()) + ", current=" + UsbPort.modeToString(i));
            }
            Slog.d("UsbPortManager", "supportedModes=" + UsbPort.modeToString(i));
            if (z4 != portInfo.mUsbPort.supportsEnableContaminantPresenceProtection()) {
                logAndPrint(5, indentingPrintWriter, "Ignoring inconsistent supportsEnableContaminantPresenceProtectionUSB port driver (should be immutable): previous=" + portInfo.mUsbPort.supportsEnableContaminantPresenceProtection() + ", current=" + z4);
            }
            if (z5 != portInfo.mUsbPort.supportsEnableContaminantPresenceDetection()) {
                logAndPrint(5, indentingPrintWriter, "Ignoring inconsistent supportsEnableContaminantPresenceDetection USB port driver (should be immutable): previous=" + portInfo.mUsbPort.supportsEnableContaminantPresenceDetection() + ", current=" + z5);
            }
            if (portInfo.setStatus(i12, z8, i4, z2, i5, z3, i15, i6, i7, i8, z6, i9, iArr, i10, displayPortAltModeInfo)) {
                portInfo.mDisposition = 1;
                str2 = "UsbPortManager";
                Slog.d(str2, "addOrUpdatePortLocked() mPorts DISPOSITION_REMOVED -> DISPOSITION_CHANGED");
            } else {
                str2 = "UsbPortManager";
                portInfo.mDisposition = 2;
            }
        }
        Slog.d(str2, "addOrUpdatePortLocked()--");
    }

    public final void addSimulatedPort(String str, int i, boolean z, boolean z2, IndentingPrintWriter indentingPrintWriter) {
        DisplayPortAltModeInfo displayPortAltModeInfo = z2 ? new DisplayPortAltModeInfo() : null;
        synchronized (this.mLock) {
            try {
            } catch (Throwable th) {
                th = th;
            }
            try {
                if (this.mSimulatedPorts.containsKey(str)) {
                    indentingPrintWriter.println("Port with same name already exists.  Please remove it first.");
                    return;
                }
                indentingPrintWriter.println("Adding simulated port: portId=" + str + ", supportedModes=" + UsbPort.modeToString(i));
                this.mSimulatedPorts.put(str, new RawPortInfo(str, i, 0, 0, false, 0, false, 0, false, false, 0, false, 0, 0, false, 0, z, new int[0], 0, z2 ? 1 : 0, displayPortAltModeInfo));
                updatePortsLocked(indentingPrintWriter, null);
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied() {
        Slog.wtf("UsbPortManager", "binderDied() called unexpectedly");
    }

    @Override // android.os.IBinder.DeathRecipient
    public final void binderDied(IBinder iBinder) {
        synchronized (this.mDisplayPortListenerLock) {
            this.mDisplayPortListeners.remove(iBinder);
            Slog.d("UsbPortManager", "DisplayPortEventDispatcherListener died at " + iBinder);
        }
    }

    public final void connectSimulatedPort(String str, int i, boolean z, int i2, boolean z2, int i3, boolean z3, IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Cannot connect simulated port which does not exist.");
                    return;
                }
                if (i != 0 && i2 != 0 && i3 != 0) {
                    if ((rawPortInfo.supportedModes & i) == 0) {
                        indentingPrintWriter.println("Simulated port does not support mode: " + UsbPort.modeToString(i));
                        return;
                    }
                    indentingPrintWriter.println("Connecting simulated port: portId=" + str + ", mode=" + UsbPort.modeToString(i) + ", canChangeMode=" + z + ", powerRole=" + UsbPort.powerRoleToString(i2) + ", canChangePowerRole=" + z2 + ", dataRole=" + UsbPort.dataRoleToString(i3) + ", canChangeDataRole=" + z3);
                    rawPortInfo.currentMode = i;
                    rawPortInfo.canChangeMode = z;
                    rawPortInfo.currentPowerRole = i2;
                    rawPortInfo.canChangePowerRole = z2;
                    rawPortInfo.currentDataRole = i3;
                    rawPortInfo.canChangeDataRole = z3;
                    updatePortsLocked(indentingPrintWriter, null);
                    return;
                }
                indentingPrintWriter.println("Cannot connect simulated port in null mode, power role, or data role.");
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void disconnectSimulatedPort(String str, IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Cannot disconnect simulated port which does not exist.");
                    return;
                }
                indentingPrintWriter.println("Disconnecting simulated port: portId=" + str);
                rawPortInfo.currentMode = 0;
                rawPortInfo.canChangeMode = false;
                rawPortInfo.currentPowerRole = 0;
                rawPortInfo.canChangePowerRole = false;
                rawPortInfo.currentDataRole = 0;
                rawPortInfo.canChangeDataRole = false;
                updatePortsLocked(indentingPrintWriter, null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dump(DualDumpOutputStream dualDumpOutputStream, String str, long j) {
        long start = dualDumpOutputStream.start(str, j);
        synchronized (this.mLock) {
            try {
                dualDumpOutputStream.write("is_simulation_active", 1133871366145L, !this.mSimulatedPorts.isEmpty());
                Iterator it = this.mPorts.values().iterator();
                while (it.hasNext()) {
                    ((PortInfo) it.next()).dump(dualDumpOutputStream);
                }
                UsbPortHal usbPortHal = this.mUsbPortHal;
                int i = -2;
                if (usbPortHal != null) {
                    try {
                        i = usbPortHal.getUsbHalVersion();
                    } catch (RemoteException unused) {
                    }
                }
                dualDumpOutputStream.write("usb_hal_version", 1159641169924L, i);
            } catch (Throwable th) {
                throw th;
            }
        }
        dualDumpOutputStream.end(start);
    }

    public final void enableContaminantDetection(String str, boolean z, IndentingPrintWriter indentingPrintWriter) {
        PortInfo portInfo = (PortInfo) this.mPorts.get(str);
        if (portInfo == null) {
            if (indentingPrintWriter != null) {
                indentingPrintWriter.println("No such USB port: " + str);
                return;
            }
            return;
        }
        if (portInfo.mUsbPort.supportsEnableContaminantPresenceDetection()) {
            if (!z || portInfo.mUsbPortStatus.getContaminantDetectionStatus() == 1) {
                if ((z || portInfo.mUsbPortStatus.getContaminantDetectionStatus() != 1) && portInfo.mUsbPortStatus.getContaminantDetectionStatus() != 0) {
                    try {
                        UsbPortHal usbPortHal = this.mUsbPortHal;
                        long j = this.mTransactionId + 1;
                        this.mTransactionId = j;
                        usbPortHal.enableContaminantPresenceDetection(str, j, z);
                    } catch (Exception e) {
                        logAndPrintException(indentingPrintWriter, "Failed to set contaminant detection", e);
                    }
                }
            }
        }
    }

    public final void enableLimitPowerTransfer(String str, boolean z, long j, IUsbOperationInternal iUsbOperationInternal, IndentingPrintWriter indentingPrintWriter) {
        Objects.requireNonNull(str);
        if (((PortInfo) this.mPorts.get(str)) == null) {
            logAndPrint(6, indentingPrintWriter, "enableLimitPowerTransfer: No such port: " + str + " opId:" + j);
            if (iUsbOperationInternal != null) {
                try {
                    iUsbOperationInternal.onOperationComplete(3);
                    return;
                } catch (RemoteException e) {
                    logAndPrintException(indentingPrintWriter, "enableLimitPowerTransfer: Failed to call OperationComplete. opId:" + j, e);
                    return;
                }
            }
            return;
        }
        try {
            this.mUsbPortHal.enableLimitPowerTransfer(str, z, j, iUsbOperationInternal);
        } catch (Exception e2) {
            try {
                logAndPrintException(indentingPrintWriter, "enableLimitPowerTransfer: Failed to limit power transfer. opId:" + j, e2);
                if (iUsbOperationInternal != null) {
                    iUsbOperationInternal.onOperationComplete(1);
                }
            } catch (RemoteException e3) {
                logAndPrintException(indentingPrintWriter, "enableLimitPowerTransfer:Failed to call onOperationComplete. opId:" + j, e3);
            }
        }
    }

    public final boolean enableUsbData(String str, boolean z, int i, IUsbOperationInternal iUsbOperationInternal) {
        Objects.requireNonNull(iUsbOperationInternal);
        Objects.requireNonNull(str);
        if (((PortInfo) this.mPorts.get(str)) == null) {
            Slog.println(6, "UsbPortManager", SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "enableUsbData: No such port: ", str, " opId:"));
            try {
                iUsbOperationInternal.onOperationComplete(3);
            } catch (RemoteException e) {
                Slog.e("UsbPortManager", "enableUsbData: Failed to call OperationComplete. opId:" + i, e);
            }
            return false;
        }
        try {
            return this.mUsbPortHal.enableUsbData(str, z, i, iUsbOperationInternal);
        } catch (Exception e2) {
            try {
                Slog.e("UsbPortManager", "enableUsbData: Failed to invoke enableUsbData. opId:" + i, e2);
                iUsbOperationInternal.onOperationComplete(1);
            } catch (RemoteException e3) {
                Slog.e("UsbPortManager", "enableUsbData: Failed to call onOperationComplete. opId:" + i, e3);
            }
            return false;
        }
    }

    public final void enableUsbDataWhileDocked(String str, long j, IUsbOperationInternal iUsbOperationInternal) {
        Objects.requireNonNull(str);
        if (((PortInfo) this.mPorts.get(str)) == null) {
            Slog.println(6, "UsbPortManager", "enableUsbDataWhileDocked: No such port: " + str + " opId:" + j);
            if (iUsbOperationInternal != null) {
                try {
                    iUsbOperationInternal.onOperationComplete(3);
                    return;
                } catch (RemoteException e) {
                    Slog.e("UsbPortManager", "enableUsbDataWhileDocked: Failed to call OperationComplete. opId:" + j, e);
                    return;
                }
            }
            return;
        }
        try {
            this.mUsbPortHal.enableUsbDataWhileDocked(str, j, iUsbOperationInternal);
        } catch (Exception e2) {
            try {
                Slog.e("UsbPortManager", "enableUsbDataWhileDocked: Failed to limit power transfer. opId:" + j, e2);
                if (iUsbOperationInternal != null) {
                    iUsbOperationInternal.onOperationComplete(1);
                }
            } catch (RemoteException e3) {
                Slog.e("UsbPortManager", "enableUsbDataWhileDocked:Failed to call onOperationComplete. opId:" + j, e3);
            }
        }
    }

    public final UsbPortStatus getPortStatus(String str) {
        UsbPortStatus usbPortStatus;
        synchronized (this.mLock) {
            try {
                PortInfo portInfo = (PortInfo) this.mPorts.get(str);
                usbPortStatus = portInfo != null ? portInfo.mUsbPortStatus : null;
            } catch (Throwable th) {
                throw th;
            }
        }
        return usbPortStatus;
    }

    public final UsbPort[] getPorts() {
        UsbPort[] usbPortArr;
        synchronized (this.mLock) {
            try {
                int size = this.mPorts.size();
                usbPortArr = new UsbPort[size];
                for (int i = 0; i < size; i++) {
                    usbPortArr[i] = ((PortInfo) this.mPorts.valueAt(i)).mUsbPort;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return usbPortArr;
    }

    public final void handlePortLocked(PortInfo portInfo) {
        PortInfo portInfo2;
        int i;
        int i2;
        Intent m = BatteryService$$ExternalSyntheticOutline0.m(285212672, "android.hardware.usb.action.USB_PORT_CHANGED");
        m.putExtra("port", (Parcelable) ParcelableUsbPort.of(portInfo.mUsbPort));
        m.putExtra("portStatus", (Parcelable) portInfo.mUsbPortStatus);
        post(new UsbPortManager$$ExternalSyntheticLambda0(this, m, 0));
        if (portInfo.mUsbPortStatus == null) {
            if (this.mConnected.containsKey(portInfo.mUsbPort.getId())) {
                if (((Boolean) this.mConnected.get(portInfo.mUsbPort.getId())).booleanValue()) {
                    FrameworkStatsLog.write(70, 0, portInfo.mUsbPort.getId(), portInfo.mLastConnectDurationMillis);
                }
                this.mConnected.remove(portInfo.mUsbPort.getId());
            }
            if (this.mContaminantStatus.containsKey(portInfo.mUsbPort.getId())) {
                if (((Integer) this.mContaminantStatus.get(portInfo.mUsbPort.getId())).intValue() == 3) {
                    FrameworkStatsLog.write(146, portInfo.mUsbPort.getId(), 3);
                }
                this.mContaminantStatus.remove(portInfo.mUsbPort.getId());
            }
        } else {
            if (!this.mConnected.containsKey(portInfo.mUsbPort.getId()) || ((Boolean) this.mConnected.get(portInfo.mUsbPort.getId())).booleanValue() != portInfo.mUsbPortStatus.isConnected()) {
                this.mConnected.put(portInfo.mUsbPort.getId(), Boolean.valueOf(portInfo.mUsbPortStatus.isConnected()));
                FrameworkStatsLog.write(70, portInfo.mUsbPortStatus.isConnected() ? 1 : 0, portInfo.mUsbPort.getId(), portInfo.mLastConnectDurationMillis);
            }
            if (!this.mContaminantStatus.containsKey(portInfo.mUsbPort.getId()) || ((Integer) this.mContaminantStatus.get(portInfo.mUsbPort.getId())).intValue() != portInfo.mUsbPortStatus.getContaminantDetectionStatus()) {
                this.mContaminantStatus.put(portInfo.mUsbPort.getId(), Integer.valueOf(portInfo.mUsbPortStatus.getContaminantDetectionStatus()));
                String id = portInfo.mUsbPort.getId();
                int contaminantDetectionStatus = portInfo.mUsbPortStatus.getContaminantDetectionStatus();
                FrameworkStatsLog.write(146, id, contaminantDetectionStatus != 0 ? contaminantDetectionStatus != 1 ? contaminantDetectionStatus != 2 ? contaminantDetectionStatus != 3 ? 0 : 4 : 3 : 2 : 1);
            }
        }
        Resources resources = this.mContext.getResources();
        int i3 = 2;
        for (PortInfo portInfo3 : this.mPorts.values()) {
            int contaminantDetectionStatus2 = portInfo3.mUsbPortStatus.getContaminantDetectionStatus();
            if (contaminantDetectionStatus2 == 3 || contaminantDetectionStatus2 == 1) {
                portInfo2 = portInfo3;
                i3 = contaminantDetectionStatus2;
                break;
            }
            i3 = contaminantDetectionStatus2;
        }
        portInfo2 = null;
        if (i3 == 3 && (i2 = this.mIsPortContaminatedNotificationId) != 52) {
            if (i2 == 53) {
                this.mNotificationManager.cancelAsUser(null, i2, UserHandle.ALL);
            }
            this.mIsPortContaminatedNotificationId = 52;
            CharSequence text = resources.getText(17043337);
            String str = SystemNotificationChannels.ALERTS;
            CharSequence text2 = resources.getText(17043336);
            Intent intent = new Intent();
            intent.addFlags(268435456);
            intent.setComponent(ComponentName.unflattenFromString(resources.getString(R.string.face_dialog_default_subtitle)));
            intent.putExtra("port", (Parcelable) ParcelableUsbPort.of(portInfo2.mUsbPort));
            intent.putExtra("portStatus", (Parcelable) portInfo2.mUsbPortStatus);
            this.mNotificationManager.notifyAsUser(null, this.mIsPortContaminatedNotificationId, new Notification.Builder(this.mContext, str).setOngoing(true).setTicker(text).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setContentIntent(PendingIntent.getActivityAsUser(this.mContext, 0, intent, 67108864, null, UserHandle.CURRENT)).setContentTitle(text).setContentText(text2).setVisibility(1).setSmallIcon(R.drawable.stat_sys_warning).setStyle(new Notification.BigTextStyle().bigText(text2)).build(), UserHandle.ALL);
            return;
        }
        if (i3 == 3 || (i = this.mIsPortContaminatedNotificationId) != 52) {
            return;
        }
        NotificationManager notificationManager = this.mNotificationManager;
        UserHandle userHandle = UserHandle.ALL;
        notificationManager.cancelAsUser(null, i, userHandle);
        this.mIsPortContaminatedNotificationId = 0;
        if (i3 == 2) {
            this.mIsPortContaminatedNotificationId = 53;
            CharSequence text3 = resources.getText(17043339);
            String str2 = SystemNotificationChannels.ALERTS;
            CharSequence text4 = resources.getText(17043338);
            this.mNotificationManager.notifyAsUser(null, this.mIsPortContaminatedNotificationId, new Notification.Builder(this.mContext, str2).setSmallIcon(R.drawable.jog_tab_bar_left_end_confirm_green).setTicker(text3).setColor(this.mContext.getColor(R.color.system_notification_accent_color)).setContentTitle(text3).setContentText(text4).setVisibility(1).setStyle(new Notification.BigTextStyle().bigText(text4)).build(), userHandle);
        }
    }

    public final boolean registerForDisplayPortEvents(IDisplayPortAltModeInfoListener iDisplayPortAltModeInfoListener) {
        synchronized (this.mDisplayPortListenerLock) {
            try {
                if (this.mDisplayPortListeners.containsKey(iDisplayPortAltModeInfoListener.asBinder())) {
                    return false;
                }
                try {
                    iDisplayPortAltModeInfoListener.asBinder().linkToDeath(this, 0);
                    this.mDisplayPortListeners.put(iDisplayPortAltModeInfoListener.asBinder(), iDisplayPortAltModeInfoListener);
                    return true;
                } catch (RemoteException e) {
                    Slog.e("UsbPortManager", "Caught RemoteException in registerForDisplayPortEvents: ", e);
                    return false;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeSimulatedPort(String str, IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                int indexOfKey = this.mSimulatedPorts.indexOfKey(str);
                if (indexOfKey < 0) {
                    indentingPrintWriter.println("Cannot remove simulated port which does not exist.");
                    return;
                }
                indentingPrintWriter.println("Disconnecting simulated port: portId=" + str);
                this.mSimulatedPorts.removeAt(indexOfKey);
                updatePortsLocked(indentingPrintWriter, null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void resetSimulation(IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Removing all simulated ports and ending simulation.");
                if (!this.mSimulatedPorts.isEmpty()) {
                    this.mSimulatedPorts.clear();
                    updatePortsLocked(indentingPrintWriter, null);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void resetUsbPort(String str, int i, IUsbOperationInternal iUsbOperationInternal) {
        synchronized (this.mLock) {
            Objects.requireNonNull(iUsbOperationInternal);
            Objects.requireNonNull(str);
            if (((PortInfo) this.mPorts.get(str)) == null) {
                Slog.println(6, "UsbPortManager", "resetUsbPort: No such port: " + str + " opId:" + i);
                try {
                    iUsbOperationInternal.onOperationComplete(3);
                } catch (RemoteException e) {
                    Slog.e("UsbPortManager", "resetUsbPort: Failed to call OperationComplete. opId:" + i, e);
                }
            }
            try {
                this.mUsbPortHal.resetUsbPort(str, i, iUsbOperationInternal);
            } catch (Exception e2) {
                try {
                    logAndPrintException(null, "reseetUsbPort: Failed to resetUsbPort. opId:" + i, e2);
                    iUsbOperationInternal.onOperationComplete(1);
                } catch (RemoteException e3) {
                    Slog.e("UsbPortManager", "resetUsbPort: Failed to call onOperationComplete. opId:" + i, e3);
                }
            }
        }
    }

    public final void setPortRoles(String str, int i, int i2, IndentingPrintWriter indentingPrintWriter, UsbService.AnonymousClass2 anonymousClass2) {
        PortInfo portInfo;
        int i3;
        synchronized (this.mLock) {
            try {
                portInfo = (PortInfo) this.mPorts.get(str);
            } catch (Throwable th) {
                throw th;
            }
            if (portInfo == null) {
                if (indentingPrintWriter != null) {
                    indentingPrintWriter.println("No such USB port: " + str);
                }
                return;
            }
            if (!portInfo.mUsbPortStatus.isRoleCombinationSupported(i, i2)) {
                logAndPrint(6, indentingPrintWriter, "Attempted to set USB port into unsupported role combination: portId=" + str + ", newPowerRole=" + UsbPort.powerRoleToString(i) + ", newDataRole=" + UsbPort.dataRoleToString(i2));
                return;
            }
            int currentDataRole = portInfo.mUsbPortStatus.getCurrentDataRole();
            int currentPowerRole = portInfo.mUsbPortStatus.getCurrentPowerRole();
            if (currentDataRole == i2 && currentPowerRole == i) {
                if (indentingPrintWriter != null) {
                    indentingPrintWriter.println("No change.");
                }
                return;
            }
            boolean z = portInfo.mCanChangeMode;
            boolean z2 = portInfo.mCanChangePowerRole;
            boolean z3 = portInfo.mCanChangeDataRole;
            int currentMode = portInfo.mUsbPortStatus.getCurrentMode();
            if ((z2 || currentPowerRole == i) && (z3 || currentDataRole == i2)) {
                i3 = currentMode;
            } else if (z && i == 1 && i2 == 1) {
                i3 = 2;
            } else {
                if (!z || i != 2 || i2 != 2) {
                    logAndPrint(6, indentingPrintWriter, "Found mismatch in supported USB role combinations while attempting to change role: " + portInfo + ", newPowerRole=" + UsbPort.powerRoleToString(i) + ", newDataRole=" + UsbPort.dataRoleToString(i2));
                    return;
                }
                i3 = 1;
            }
            logAndPrint(4, indentingPrintWriter, "Setting USB port mode and role: portId=" + str + ", currentMode=" + UsbPort.modeToString(currentMode) + ", currentPowerRole=" + UsbPort.powerRoleToString(currentPowerRole) + ", currentDataRole=" + UsbPort.dataRoleToString(currentDataRole) + ", newMode=" + UsbPort.modeToString(i3) + ", newPowerRole=" + UsbPort.powerRoleToString(i) + ", newDataRole=" + UsbPort.dataRoleToString(i2));
            RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
            if (rawPortInfo != null) {
                rawPortInfo.currentMode = i3;
                rawPortInfo.currentPowerRole = i;
                rawPortInfo.currentDataRole = i2;
                updatePortsLocked(indentingPrintWriter, null);
            } else {
                UsbPortHal usbPortHal = this.mUsbPortHal;
                if (usbPortHal != null) {
                    if (currentMode != i3) {
                        logAndPrint(6, indentingPrintWriter, "Trying to set the USB port mode: portId=" + str + ", newMode=" + UsbPort.modeToString(i3));
                        if (anonymousClass2 != null) {
                            try {
                                anonymousClass2.run();
                            } catch (Exception e) {
                                logAndPrintException(indentingPrintWriter, "Failed to set the USB port mode: portId=" + str + ", newMode=" + UsbPort.modeToString(i3), e);
                            }
                        }
                        UsbPortHal usbPortHal2 = this.mUsbPortHal;
                        if (i3 != 1) {
                            r8 = 2;
                        }
                        long j = this.mTransactionId + 1;
                        this.mTransactionId = j;
                        usbPortHal2.switchMode(r8, str, j);
                    } else {
                        if (currentPowerRole != i) {
                            int i4 = i == 2 ? 2 : 1;
                            try {
                                long j2 = this.mTransactionId + 1;
                                this.mTransactionId = j2;
                                usbPortHal.switchPowerRole(i4, str, j2);
                            } catch (Exception e2) {
                                logAndPrintException(indentingPrintWriter, "Failed to set the USB port power role: portId=" + str + ", newPowerRole=" + UsbPort.powerRoleToString(i), e2);
                                return;
                            }
                        }
                        if (currentDataRole != i2) {
                            if (anonymousClass2 != null) {
                                try {
                                    anonymousClass2.run();
                                } catch (Exception e3) {
                                    logAndPrintException(indentingPrintWriter, "Failed to set the USB port data role: portId=" + str + ", newDataRole=" + UsbPort.dataRoleToString(i2), e3);
                                }
                            }
                            UsbPortHal usbPortHal3 = this.mUsbPortHal;
                            r8 = i2 == 2 ? 2 : 1;
                            long j3 = this.mTransactionId + 1;
                            this.mTransactionId = j3;
                            usbPortHal3.switchDataRole(r8, str, j3);
                        }
                    }
                    throw th;
                }
            }
        }
    }

    public final void simulateComplianceWarnings(String str, String str2, IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Simulated port not found");
                    return;
                }
                IntArray intArray = new IntArray();
                for (String str3 : str2.split("[, ]")) {
                    if (str3.length() > 0) {
                        intArray.add(Integer.parseInt(str3));
                    }
                }
                indentingPrintWriter.println("Simulating Compliance Warnings: portId=" + str + " Warnings=" + str2);
                rawPortInfo.complianceWarnings = intArray.toArray();
                updatePortsLocked(indentingPrintWriter, null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void simulateContaminantStatus(String str, boolean z, IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Simulated port not found.");
                    return;
                }
                indentingPrintWriter.println("Simulating wet port: portId=" + str + ", wet=" + z);
                rawPortInfo.contaminantDetectionStatus = z ? 3 : 2;
                updatePortsLocked(indentingPrintWriter, null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void simulateDisplayPortAltModeInfo(String str, int i, int i2, int i3, boolean z, int i4, IndentingPrintWriter indentingPrintWriter) {
        synchronized (this.mLock) {
            try {
                RawPortInfo rawPortInfo = (RawPortInfo) this.mSimulatedPorts.get(str);
                if (rawPortInfo == null) {
                    indentingPrintWriter.println("Simulated port not found");
                    return;
                }
                DisplayPortAltModeInfo displayPortAltModeInfo = new DisplayPortAltModeInfo(i, i2, i3, z, i4);
                rawPortInfo.displayPortAltModeInfo = displayPortAltModeInfo;
                indentingPrintWriter.println("Simulating DisplayPort Info: " + displayPortAltModeInfo);
                updatePortsLocked(indentingPrintWriter, null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void startBoost() {
        if (this.usbPortBooster != null) {
            Slog.d("UsbPortManager", "To boost, Acquire");
            this.usbPortBooster.acquire();
        }
    }

    public final void stopBoost() {
        if (this.usbPortBooster != null) {
            Slog.d("UsbPortManager", "To boost, Release");
            this.usbPortBooster.release();
        }
    }

    public final void updatePorts(ArrayList arrayList) {
        Message obtainMessage = obtainMessage();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("port_info", arrayList);
        obtainMessage.what = 1;
        obtainMessage.setData(bundle);
        sendMessage(obtainMessage);
    }

    public final void updatePortsLocked(IndentingPrintWriter indentingPrintWriter, ArrayList arrayList) {
        IndentingPrintWriter indentingPrintWriter2;
        int i;
        UsbPortManager usbPortManager = this;
        Slog.d("UsbPortManager", "updatePortsLocked()++");
        int size = usbPortManager.mPorts.size();
        while (true) {
            int i2 = size - 1;
            if (size <= 0) {
                break;
            }
            ((PortInfo) usbPortManager.mPorts.valueAt(i2)).mDisposition = 3;
            size = i2;
        }
        if (usbPortManager.mSimulatedPorts.isEmpty()) {
            for (Iterator it = arrayList.iterator(); it.hasNext(); it = it) {
                RawPortInfo rawPortInfo = (RawPortInfo) it.next();
                addOrUpdatePortLocked(rawPortInfo.portId, rawPortInfo.supportedModes, rawPortInfo.supportedContaminantProtectionModes, rawPortInfo.currentMode, rawPortInfo.canChangeMode, rawPortInfo.currentPowerRole, rawPortInfo.canChangePowerRole, rawPortInfo.currentDataRole, rawPortInfo.canChangeDataRole, rawPortInfo.supportsEnableContaminantPresenceProtection, rawPortInfo.contaminantProtectionStatus, rawPortInfo.supportsEnableContaminantPresenceDetection, rawPortInfo.contaminantDetectionStatus, rawPortInfo.usbDataStatus, rawPortInfo.powerTransferLimited, rawPortInfo.powerBrickConnectionStatus, rawPortInfo.supportsComplianceWarnings, rawPortInfo.complianceWarnings, rawPortInfo.plugState, rawPortInfo.supportedAltModes, rawPortInfo.displayPortAltModeInfo, indentingPrintWriter);
            }
        } else {
            int i3 = 0;
            for (int size2 = usbPortManager.mSimulatedPorts.size(); i3 < size2; size2 = size2) {
                RawPortInfo rawPortInfo2 = (RawPortInfo) usbPortManager.mSimulatedPorts.valueAt(i3);
                usbPortManager = this;
                usbPortManager.addOrUpdatePortLocked(rawPortInfo2.portId, rawPortInfo2.supportedModes, rawPortInfo2.supportedContaminantProtectionModes, rawPortInfo2.currentMode, rawPortInfo2.canChangeMode, rawPortInfo2.currentPowerRole, rawPortInfo2.canChangePowerRole, rawPortInfo2.currentDataRole, rawPortInfo2.canChangeDataRole, rawPortInfo2.supportsEnableContaminantPresenceProtection, rawPortInfo2.contaminantProtectionStatus, rawPortInfo2.supportsEnableContaminantPresenceDetection, rawPortInfo2.contaminantDetectionStatus, rawPortInfo2.usbDataStatus, rawPortInfo2.powerTransferLimited, rawPortInfo2.powerBrickConnectionStatus, rawPortInfo2.supportsComplianceWarnings, rawPortInfo2.complianceWarnings, rawPortInfo2.plugState, rawPortInfo2.supportedAltModes, rawPortInfo2.displayPortAltModeInfo, indentingPrintWriter);
                i3++;
            }
        }
        Slog.d("UsbPortManager", "mPorts size=" + this.mPorts.size());
        int size3 = this.mPorts.size();
        while (true) {
            int i4 = size3 - 1;
            if (size3 <= 0) {
                Slog.d("UsbPortManager", "updatePortsLocked()--");
                return;
            }
            PortInfo portInfo = (PortInfo) this.mPorts.valueAt(i4);
            int i5 = portInfo.mDisposition;
            if (i5 == 0) {
                indentingPrintWriter2 = indentingPrintWriter;
                i = 3;
                logAndPrint(4, indentingPrintWriter2, "USB port added: " + portInfo);
                handlePortLocked(portInfo);
                portInfo.mDisposition = 2;
                Slog.d("UsbPortManager", "mPorts(" + i4 + ") DISPOSITION_ADDED -> DISPOSITION_READY");
            } else if (i5 != 1) {
                i = 3;
                if (i5 != 3) {
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i4, "mPorts(", ") default: mDisposition=");
                    m.append(portInfo.mDisposition);
                    Slog.d("UsbPortManager", m.toString());
                    indentingPrintWriter2 = indentingPrintWriter;
                } else {
                    StringBuilder m2 = BatteryService$$ExternalSyntheticOutline0.m(i4, "mPorts(", ") DISPOSITION_REMOVED size=");
                    m2.append(this.mPorts.size());
                    Slog.d("UsbPortManager", m2.toString());
                    this.mPorts.removeAt(i4);
                    portInfo.mUsbPortStatus = null;
                    indentingPrintWriter2 = indentingPrintWriter;
                    logAndPrint(4, indentingPrintWriter2, "USB port removed: " + portInfo);
                    handlePortLocked(portInfo);
                }
            } else {
                indentingPrintWriter2 = indentingPrintWriter;
                i = 3;
                logAndPrint(4, indentingPrintWriter2, "USB port changed: " + portInfo);
                if (this.mConnected.containsKey(portInfo.mUsbPort.getId()) && ((Boolean) this.mConnected.get(portInfo.mUsbPort.getId())).booleanValue() && !portInfo.mUsbPortStatus.isConnected() && portInfo.mUsbPortStatus.getContaminantDetectionStatus() == 1) {
                    enableContaminantDetection(portInfo.mUsbPort.getId(), true, indentingPrintWriter2);
                }
                if (this.mConnected.containsKey(portInfo.mUsbPort.getId()) && ((Boolean) this.mConnected.get(portInfo.mUsbPort.getId())).booleanValue() && !portInfo.mUsbPortStatus.isConnected() && portInfo.mUsbPortStatus.isPowerTransferLimited()) {
                    String id = portInfo.mUsbPort.getId();
                    long j = this.mTransactionId + 1;
                    this.mTransactionId = j;
                    enableLimitPowerTransfer(id, false, j, null, indentingPrintWriter);
                }
                handlePortLocked(portInfo);
                portInfo.mDisposition = 2;
                Slog.d("UsbPortManager", "mPorts(" + i4 + ") DISPOSITION_CHANGED -> DISPOSITION_READY");
            }
            if (portInfo.mComplianceWarningChange == 1) {
                logAndPrint(4, indentingPrintWriter2, "USB port compliance warning changed: " + portInfo);
                UsbPortStatus usbPortStatus = portInfo.mUsbPortStatus;
                if (usbPortStatus != null && usbPortStatus.getComplianceWarnings().length != 0) {
                    String id2 = portInfo.mUsbPort.getId();
                    int[] complianceWarnings = portInfo.mUsbPortStatus.getComplianceWarnings();
                    IntArray intArray = new IntArray();
                    for (int i6 : complianceWarnings) {
                        switch (i6) {
                            case 1:
                                intArray.add(4);
                                break;
                            case 2:
                                intArray.add(1);
                                break;
                            case 3:
                                intArray.add(2);
                                break;
                            case 4:
                                intArray.add(i);
                                break;
                            case 5:
                                intArray.add(5);
                                break;
                            case 6:
                                intArray.add(6);
                                break;
                            case 7:
                                intArray.add(7);
                                break;
                            case 8:
                                intArray.add(8);
                                break;
                            case 9:
                                intArray.add(9);
                                break;
                        }
                    }
                    FrameworkStatsLog.write(FrameworkStatsLog.USB_COMPLIANCE_WARNINGS_REPORTED, id2, intArray.toArray());
                }
                if (portInfo.mComplianceWarningChange != 0) {
                    Intent m3 = BatteryService$$ExternalSyntheticOutline0.m(285212672, "android.hardware.usb.action.USB_PORT_COMPLIANCE_CHANGED");
                    m3.putExtra("port", (Parcelable) ParcelableUsbPort.of(portInfo.mUsbPort));
                    m3.putExtra("portStatus", (Parcelable) portInfo.mUsbPortStatus);
                    post(new UsbPortManager$$ExternalSyntheticLambda0(this, m3, 1));
                }
            }
            if (portInfo.mDisplayPortAltModeChange == 1) {
                logAndPrint(4, indentingPrintWriter2, "USB port DisplayPort Alt Mode Status Changed: " + portInfo);
                String id3 = portInfo.mUsbPort.getId();
                synchronized (this.mDisplayPortListenerLock) {
                    Iterator it2 = this.mDisplayPortListeners.values().iterator();
                    while (it2.hasNext()) {
                        try {
                            ((IDisplayPortAltModeInfoListener) it2.next()).onDisplayPortAltModeInfoChanged(id3, portInfo.mUsbPortStatus.getDisplayPortAltModeInfo());
                        } catch (RemoteException e) {
                            logAndPrintException(indentingPrintWriter2, "Caught RemoteException at sendDpAltModeCallbackLocked", e);
                        }
                    }
                }
            }
            size3 = i4;
        }
    }
}
