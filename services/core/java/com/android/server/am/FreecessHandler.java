package com.android.server.am;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.location.ILocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManagerInternal;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0;
import com.android.server.PinnerService$$ExternalSyntheticOutline0;
import com.android.server.SystemUpdateManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.am.FreecessController;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.ServiceRecord;
import com.android.server.am.mars.MARsDebugConfig;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.filter.filter.BlueToothConnectedFilter;
import com.android.server.am.mars.filter.filter.WidgetPkgFilter;
import com.android.server.am.mars.netlink.NetlinkSocket;
import com.android.server.am.mars.netlink.StructFreeCessMsg;
import com.android.server.am.mars.util.UidStateMgr;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.knox.analytics.service.EventQueue;
import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FreecessHandler {
    public static int mScreenOnQuickFreezeCheckDelay = 6000;
    public static int mScreenOnQuickFreezeDelayInterval = 5000;
    public long lastDelayedTime;
    public BluetoothAdapter mBluetoothAdapter;
    public BluetoothHandler mBluetoothHandler;
    public FreecessThread mFreecessThread;
    public HandleAmsLockHandler mHandleAmsLockHandler;
    public MainHandler mMainHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BluetoothHandler extends Handler {
        public BluetoothHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            Bundle data = message.getData();
            if (data != null) {
                FreecessHandler freecessHandler = FreecessHandler.this;
                if (freecessHandler.mBluetoothAdapter == null) {
                    return;
                }
                int i = data.getInt("uid", -1);
                BlueToothConnectedFilter blueToothConnectedFilter = BlueToothConnectedFilter.BlueToothConnectedFilterHolder.INSTANCE;
                Integer valueOf = Integer.valueOf(i);
                List list = blueToothConnectedFilter.mBTTargetList;
                if (list == null || !list.contains(valueOf)) {
                    return;
                }
                int i2 = message.what;
                if (i2 == 26) {
                    if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d("FreecessHandler", "handle FREECESS_FREEZE_BT_SCAN_MSG....");
                    }
                    freecessHandler.mBluetoothAdapter.onFreeze(i);
                } else {
                    if (i2 != 27) {
                        throw new IllegalStateException("Unexpected value: " + message.what);
                    }
                    if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d("FreecessHandler", "handle FREECESS_UNFREEZE_BT_SCAN_MSG....");
                    }
                    freecessHandler.mBluetoothAdapter.onUnFreeze(i);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class FreecessHandlerHolder {
        public static final FreecessHandler INSTANCE;

        static {
            FreecessHandler freecessHandler = new FreecessHandler();
            freecessHandler.lastDelayedTime = 0L;
            INSTANCE = freecessHandler;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FreecessThread extends HandlerThread {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ FreecessHandler this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public FreecessThread(int i) {
            super("FreecessHandler", 0);
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = FreecessHandlerHolder.INSTANCE;
                    super("Freecess_BT", 0);
                    break;
                case 2:
                    this.this$0 = FreecessHandlerHolder.INSTANCE;
                    super("Freecess_HAMSL", 0);
                    break;
                default:
                    this.this$0 = FreecessHandlerHolder.INSTANCE;
                    break;
            }
        }

        @Override // android.os.HandlerThread
        public final void onLooperPrepared() {
            switch (this.$r8$classId) {
                case 0:
                    this.this$0.mMainHandler = new MainHandler(getLooper());
                    break;
                case 1:
                    this.this$0.mBluetoothHandler = this.this$0.new BluetoothHandler(getLooper());
                    break;
                default:
                    this.this$0.mHandleAmsLockHandler = new HandleAmsLockHandler(getLooper());
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HandleAmsLockHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            ArrayList<Integer> integerArrayList;
            ApplicationInfo applicationInfo;
            ArrayList arrayList;
            Bundle data = message.getData();
            int i = message.what;
            if (i == 29) {
                if (data == null || (integerArrayList = data.getIntegerArrayList("pids")) == null) {
                    return;
                }
                boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                freecessController.getClass();
                Iterator<Integer> it = integerArrayList.iterator();
                while (it.hasNext()) {
                    ProcessRecord processRecordFromPidLocked = freecessController.mAm.getProcessRecordFromPidLocked(it.next().intValue());
                    if (processRecordFromPidLocked != null) {
                        ActivityManagerService activityManagerService = freecessController.mAm;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService) {
                            try {
                                Handler handler = freecessController.mAm.mBroadcastQueue.mLocalHandler;
                                handler.removeMessages(6, processRecordFromPidLocked);
                                handler.obtainMessage(6, processRecordFromPidLocked).sendToTarget();
                            } finally {
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                    }
                }
                return;
            }
            switch (i) {
                case 32:
                    if (data != null) {
                        int i2 = data.getInt("uid");
                        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController freecessController2 = FreecessController.FreecessControllerHolder.INSTANCE;
                        ActivityManagerService activityManagerService2 = freecessController2.mAm;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService2) {
                            try {
                                for (int size = freecessController2.mAm.mServices.mRestartingServices.size() - 1; size >= 0; size--) {
                                    ServiceRecord serviceRecord = (ServiceRecord) freecessController2.mAm.mServices.mRestartingServices.get(size);
                                    if (serviceRecord != null && (applicationInfo = serviceRecord.appInfo) != null && applicationInfo.uid == i2 && freecessController2.mAm.mHandler.hasCallbacks(serviceRecord.restarter)) {
                                        freecessController2.mAm.mHandler.removeCallbacks(serviceRecord.restarter);
                                        freecessController2.mAm.pendingScheduleServiceRestart(serviceRecord.appInfo.uid, serviceRecord);
                                    }
                                }
                            } finally {
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    return;
                case 33:
                    ServiceRecord serviceRecord2 = (ServiceRecord) message.obj;
                    boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    ((ArrayList) FreecessController.FreecessControllerHolder.INSTANCE.mPendingRemoveConnectionMap.computeIfAbsent(Integer.valueOf(serviceRecord2.appInfo.uid), new FreecessController$$ExternalSyntheticLambda6(0))).add(serviceRecord2);
                    return;
                case 34:
                    if (data != null) {
                        int i3 = data.getInt("uid");
                        boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        FreecessController freecessController3 = FreecessController.FreecessControllerHolder.INSTANCE;
                        if (freecessController3.mPendingRemoveConnectionMap.get(Integer.valueOf(i3)) == null || (arrayList = (ArrayList) freecessController3.mPendingRemoveConnectionMap.remove(Integer.valueOf(i3))) == null || arrayList.isEmpty()) {
                            return;
                        }
                        Iterator it2 = arrayList.iterator();
                        while (it2.hasNext()) {
                            ServiceRecord serviceRecord3 = (ServiceRecord) it2.next();
                            ActiveServices activeServices = freecessController3.mAm.mServices;
                            activeServices.getClass();
                            Slog.d("ActivityManager", "unpendingRemoveConnection: s=" + serviceRecord3);
                            ActivityManagerService activityManagerService3 = activeServices.mAm;
                            ActivityManagerService.boostPriorityForLockedSection();
                            synchronized (activityManagerService3) {
                                try {
                                    ArrayList arrayList2 = new ArrayList();
                                    arrayList2.addAll(serviceRecord3.pendingRemoveConnections);
                                    serviceRecord3.pendingRemoveConnections.clear();
                                    Iterator it3 = arrayList2.iterator();
                                    while (it3.hasNext()) {
                                        ServiceRecord.removeConnectionItem removeconnectionitem = (ServiceRecord.removeConnectionItem) it3.next();
                                        activeServices.removeConnectionLocked(removeconnectionitem.c, removeconnectionitem.skipApp, removeconnectionitem.skipAct, removeconnectionitem.enqueueOomAdj);
                                    }
                                } finally {
                                }
                            }
                            ActivityManagerService.resetPriorityAfterLockedSection();
                        }
                        return;
                    }
                    return;
                default:
                    throw new IllegalStateException("Unexpected value: " + message.what);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainHandler extends Handler {
        public Bundle extras;
        public final FreecessController mFreecessController;

        public MainHandler(Looper looper) {
            super(looper);
            boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
            this.mFreecessController = FreecessController.FreecessControllerHolder.INSTANCE;
        }

        public final void handleFreecessResetAllState() {
            String string;
            Bundle bundle = this.extras;
            if (bundle == null || (string = bundle.getString("reason", null)) == null) {
                return;
            }
            FreecessController freecessController = this.mFreecessController;
            freecessController.getClass();
            Slog.w("FreecessController", "!@*** unFreezeAllPackages for watchdog : Start reset all state unfreezing!!! - MARs FW Side (reason: " + string + ")");
            if ("SoftReset".equals(string)) {
                ((PowerManager) freecessController.mContext.getSystemService("power")).newWakeLock(1, string).acquire(2000L);
            }
            freecessController.unFreezePackage(string);
            if (freecessController.sendFreecessMsg2kernel(3, -1, 2, -1) > 0) {
                synchronized (MARsPolicyManager.MARsLock) {
                    try {
                        ArrayList arrayList = freecessController.mMonitorFreezedList;
                        if (arrayList != null) {
                            arrayList.clear();
                        }
                    } finally {
                    }
                }
            } else {
                Slog.e("FreecessController", "cleanPacketMonitoredUids Exception");
            }
            if (freecessController.mIsScreenOnFreecessEnabled) {
                synchronized (MARsPolicyManager.MARsLock) {
                    try {
                        SparseArray sparseArray = freecessController.mFreecessManagedPackages.mUidMap;
                        for (int i = 0; i < sparseArray.size(); i++) {
                            FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) sparseArray.valueAt(i);
                            if (freecessPkgStatus != null) {
                                freecessPkgStatus.freezedState = 1;
                            }
                        }
                    } finally {
                    }
                }
            }
            PinnerService$$ExternalSyntheticOutline0.m("!@*** unFreezeAllPackages for watchdog : End reset all state unfreezing!!! - MARs FW Side (reason: ", string, ")", "FreecessController");
        }

        public final void handleFreecessResetState() {
            Bundle bundle = this.extras;
            if (bundle != null) {
                String string = bundle.getString("packageName", null);
                int i = this.extras.getInt("userId", -1);
                if (string == null || i == -1) {
                    return;
                }
                FreecessController freecessController = this.mFreecessController;
                freecessController.getClass();
                synchronized (MARsPolicyManager.MARsLock) {
                    try {
                        FreecessPkgStatus packageStatus = freecessController.getPackageStatus(i, string);
                        if (packageStatus == null) {
                            return;
                        }
                        if (packageStatus.freezedState != 2 && (!freecessController.mScreenOn || !packageStatus.freezedRecord.isLcdOffFreezed)) {
                            packageStatus.freezedState = 1;
                            FreecessHandler freecessHandler = FreecessHandlerHolder.INSTANCE;
                            freecessHandler.removeBgTriggerMsgByObj(1, packageStatus.name);
                            freecessHandler.removeBgTriggerMsgByObj(3, packageStatus.name);
                            freecessHandler.removeBgTriggerMsgByObj(28, packageStatus.name);
                        }
                        if (packageStatus.freezedRecord.isFrozen) {
                            freecessController.unFreezePackage(packageStatus.userId, packageStatus.name, "UidActive");
                        } else {
                            packageStatus.freezedState = 1;
                        }
                        FreecessHandlerHolder.INSTANCE.removeBgTriggerMsgByObj(4, packageStatus.name);
                    } finally {
                    }
                }
            }
        }

        public final void handleFreecessSettlementPackage() {
            Bundle bundle = this.extras;
            if (bundle == null) {
                return;
            }
            String string = bundle.getString("packageName", null);
            int i = this.extras.getInt("userId", -1);
            int i2 = this.extras.getInt("uid", -1);
            int i3 = this.extras.getInt("packetMonitorFlag", -1);
            int i4 = this.extras.getInt("disableWakelockFlag", -1);
            boolean z = this.extras.getBoolean("isLcdOnTrigger", false);
            if (this.extras.getBoolean("unrestrictJobs", false)) {
                boolean z2 = MARsPolicyManager.MARs_ENABLE;
                MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.restrictJobsByUid(i2, false);
            }
            if (i3 == 0) {
                FreecessController freecessController = this.mFreecessController;
                if (freecessController.sendFreecessMsg2kernel(3, -1, 1, i2) > 0) {
                    synchronized (MARsPolicyManager.MARsLock) {
                        try {
                            ArrayList arrayList = freecessController.mMonitorFreezedList;
                            if (arrayList != null && arrayList.contains(Integer.valueOf(i2))) {
                                freecessController.mMonitorFreezedList.remove(Integer.valueOf(i2));
                            }
                        } finally {
                        }
                    }
                } else {
                    Slog.e("FreecessController", "deletePacketMonitoredUid Exception");
                }
            } else if (i3 == 1) {
                FreecessController freecessController2 = this.mFreecessController;
                if (freecessController2.sendFreecessMsg2kernel(3, -1, 0, i2) > 0) {
                    synchronized (MARsPolicyManager.MARsLock) {
                        try {
                            ArrayList arrayList2 = freecessController2.mMonitorFreezedList;
                            if (arrayList2 != null && !arrayList2.contains(Integer.valueOf(i2))) {
                                freecessController2.mMonitorFreezedList.add(Integer.valueOf(i2));
                            }
                        } finally {
                        }
                    }
                } else {
                    freecessController2.unFreezePackage(i, string, "RegException");
                }
            }
            if (i4 != -1) {
                FreecessController freecessController3 = this.mFreecessController;
                boolean z3 = i4 == 1;
                if (freecessController3.mLocalPowerManager == null) {
                    freecessController3.mLocalPowerManager = (PowerManagerInternal) LocalServices.getService(PowerManagerInternal.class);
                }
                int wakeLockEnableDisable = freecessController3.mLocalPowerManager.setWakeLockEnableDisable(i2, z3);
                if (wakeLockEnableDisable == 1) {
                    StringBuilder sb = new StringBuilder();
                    DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(sb, z3 ? "disable" : "enable", " frozen app (", string, ",");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb, i2, ") wakelock.", "FreecessController");
                } else if (wakeLockEnableDisable == 2) {
                    if (z) {
                        if (freecessController3.mCalmModeEnabled) {
                            return;
                        }
                        freecessController3.unFreezePackage(UserHandle.getUserId(i2), string, "Wakelock");
                    } else {
                        if (UidStateMgr.UidStateMgrHolder.INSTANCE.isUidIdle(i2)) {
                            return;
                        }
                        freecessController3.unFreezePackage(UserHandle.getUserId(i2), string, "Wakelock");
                    }
                }
            }
        }

        public final void handleFreezeStateChanged() {
            Bundle bundle = this.extras;
            if (bundle != null) {
                boolean z = bundle.getBoolean("enabled", false);
                int i = this.extras.getInt("uid", -1);
                FreecessController freecessController = this.mFreecessController;
                if (freecessController.mAlarmManagerInternal == null) {
                    freecessController.mAlarmManagerInternal = (AlarmManagerService.LocalService) LocalServices.getService(AlarmManagerService.LocalService.class);
                }
                AlarmManagerService.LocalService localService = freecessController.mAlarmManagerInternal;
                AlarmManagerService.this.mHandler.removeMessages(21);
                AlarmManagerService.this.mHandler.sendEmptyMessageDelayed(21, 200L);
                if (!z) {
                    synchronized (AlarmManagerService.this.mLock) {
                        AlarmManagerService alarmManagerService = AlarmManagerService.this;
                        ArrayList arrayList = (ArrayList) alarmManagerService.mPendingMARsRestrictedAlarms.get(i);
                        if (arrayList != null && arrayList.size() != 0) {
                            AnyMotionDetector$$ExternalSyntheticOutline0.m(i, "Sending alarms blocked by MARsFreecess for uid ", "AlarmManager");
                            alarmManagerService.mPendingMARsRestrictedAlarms.remove(i);
                            alarmManagerService.mInjector.getClass();
                            alarmManagerService.deliverPendingBackgroundAlarmsLocked(SystemClock.elapsedRealtime(), arrayList);
                        }
                    }
                }
                if (freecessController.FREECESS_LRS_ENABLED) {
                    try {
                        if (freecessController.mLocationManager == null) {
                            freecessController.mLocationManager = ILocationManager.Stub.asInterface(ServiceManager.getService("location"));
                        }
                        ILocationManager iLocationManager = freecessController.mLocationManager;
                        if (iLocationManager != null) {
                            iLocationManager.onFreezeStateChanged(z, i);
                        }
                    } catch (RemoteException e) {
                        StringBuilder m = AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "Error occurred while setLRs(", ", ", "): ", z);
                        m.append(e);
                        Slog.e("FreecessController", m.toString());
                    }
                }
            }
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            WindowManagerService windowManagerService;
            Bundle data = message.getData();
            this.extras = data;
            String str = null;
            switch (message.what) {
                case 1:
                    if (MARsDebugConfig.DEBUG_ENG) {
                        Slog.d("FreecessHandler", "handle FREECESS_LCD_ON_QUICK_FREEZE_MSG....");
                    }
                    if (this.extras != null) {
                        FreecessController freecessController = this.mFreecessController;
                        freecessController.updateRunningLocationPackages();
                        ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE.getUidListUsingAudio();
                        freecessController.triggerLcdOnFreeze(this.extras.getInt("uid", -1), this.extras.getString("packageName", null), this.extras.getString("reason", null));
                        return;
                    }
                    return;
                case 2:
                    if (data != null) {
                        this.mFreecessController.lcdOnFreezedStateChange(1, this.extras.getInt("userId", -1), this.extras.getString("reason", ""), data.getString("packageName", ""));
                        return;
                    }
                    return;
                case 3:
                    if (data != null) {
                        this.mFreecessController.lcdOnFreezedStateChange(2, this.extras.getInt("userId", -1), this.extras.getString("reason", ""), data.getString("packageName", ""));
                        return;
                    }
                    return;
                case 4:
                    if (data != null) {
                        this.mFreecessController.lcdOnFreezePackage(this.extras.getInt("uid", -1), data.getString("packageName", ""));
                        return;
                    }
                    return;
                case 5:
                    handleFreecessResetState();
                    return;
                case 6:
                    handleFreecessResetAllState();
                    return;
                case 7:
                    handleFreecessSettlementPackage();
                    return;
                case 8:
                    if (data != null) {
                        int i = data.getInt("uid", -1);
                        FreecessController freecessController2 = this.mFreecessController;
                        if (freecessController2.sendFreecessMsg2kernel(4, i, 0, 0) < 0) {
                            freecessController2.unFreezePackage("FBException");
                            return;
                        }
                        return;
                    }
                    return;
                case 9:
                    if (data != null) {
                        boolean z = data.getBoolean("enterFlag", false);
                        String string = this.extras.getString("packageName", null);
                        int i2 = this.extras.getInt("uid", -1);
                        FreecessController freecessController3 = this.mFreecessController;
                        if (!z) {
                            freecessController3.unFreezeForOLAF("Force");
                            freecessController3.restrictJobsByOlaf(-1, false);
                            return;
                        }
                        freecessController3.restrictJobsByOlaf(i2, true);
                        freecessController3.mFreecessOlafUpdate.set(true);
                        freecessController3.mOlafTargetPkg = string;
                        freecessController3.mOlafTargetUserId = UserHandle.getUserId(i2);
                        freecessController3.triggerOLAF(i2, string);
                        freecessController3.mFreecessOlafUpdate.set(false);
                        freecessController3.mOlafTargetPkg = null;
                        freecessController3.mOlafTargetUserId = -10;
                        return;
                    }
                    return;
                case 10:
                    this.mFreecessController.restrictJobsByOlaf(-1, false);
                    this.mFreecessController.unFreezeForOLAF("timeout");
                    return;
                case 11:
                    if (data != null) {
                        int i3 = data.getInt("type", -1);
                        FreecessController freecessController4 = this.mFreecessController;
                        freecessController4.mIsDumpstateWorking = false;
                        freecessController4.mIsSmartSwitchWorking = false;
                        freecessController4.setFreecessEnableForSpecificReason(i3, true);
                        return;
                    }
                    return;
                case 12:
                    this.mFreecessController.readSysfs();
                    return;
                case 13:
                    handleUnfreezeActivePackages();
                    return;
                case 14:
                    handleFreezeStateChanged();
                    return;
                case 15:
                    if (data != null) {
                        String string2 = data.getString("packageName", "");
                        int i4 = this.extras.getInt("uid", -1);
                        FreecessController freecessController5 = this.mFreecessController;
                        FreecessPkgStatus packageStatus = freecessController5.getPackageStatus(i4);
                        if (packageStatus == null || !packageStatus.freezedRecord.isFrozen) {
                            DeviceIdleController$$ExternalSyntheticOutline0.m(i4, "postMonitoringFrozenProcs: u=", " is not frozen, return.", "FreecessController");
                            return;
                        }
                        ArrayList pidList = freecessController5.mMapFrozenUidPidList.getPidList(Integer.valueOf(i4));
                        StringBuilder sb = new StringBuilder();
                        if (freecessController5.canUidBeFrozen(pidList, new FreecessController$$ExternalSyntheticLambda1(1, sb))) {
                            return;
                        }
                        String sb2 = sb.toString();
                        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("unfreeze ", string2, "(");
                        int i5 = packageStatus.uid;
                        m.append(i5);
                        m.append(")in postMonitoringFrozenProcs. reason : ");
                        m.append(sb2);
                        Slog.d("FreecessController", m.toString());
                        freecessController5.unFreezePackage(i5, sb2);
                        return;
                    }
                    return;
                case 16:
                    if (data != null) {
                        this.mFreecessController.deleteRemovedPackage(this.extras.getInt("uid", -1), data.getString("packageName", ""));
                        return;
                    }
                    return;
                case 17:
                    if (data != null) {
                        data.getString("packageName", "");
                        this.mFreecessController.unFreezeSpecialPackage(this.extras.getInt("uid", -1), this.extras.getString("reason", ""));
                        return;
                    }
                    return;
                case 18:
                case 19:
                case 20:
                case 26:
                case 27:
                case 29:
                default:
                    throw new IllegalStateException("Unexpected value: " + message.what);
                case 21:
                    if (data != null) {
                        this.mFreecessController.triggerCalmMode(data.getStringArrayList("list"));
                        return;
                    }
                    return;
                case 22:
                    handleRepeatCalmMode();
                    return;
                case 23:
                    this.mFreecessController.cancelCalmMode();
                    return;
                case 24:
                    FreecessController freecessController6 = this.mFreecessController;
                    ActivityManagerService activityManagerService = freecessController6.mAm;
                    if (activityManagerService != null && (windowManagerService = activityManagerService.mWindowManager) != null) {
                        str = windowManagerService.getRequestFocusPkg();
                    }
                    if (freecessController6.isFreezedPackage(freecessController6.mContext.getUserId(), str)) {
                        freecessController6.unFreezePackage(freecessController6.mContext.getUserId(), str, "has Focus");
                        return;
                    }
                    return;
                case 25:
                    if (data != null) {
                        String string3 = data.getString("packageName", null);
                        int i6 = this.extras.getInt("userId", -1);
                        this.extras.getString("reason", "");
                        if (string3 == null || i6 == -1) {
                            return;
                        }
                        boolean z2 = MARsPolicyManager.MARs_ENABLE;
                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.reportStatusWithMARs(i6, string3, false);
                        return;
                    }
                    return;
                case 28:
                    if (data != null) {
                        String string4 = data.getString("packageName", null);
                        int i7 = this.extras.getInt("uid", -1);
                        int i8 = this.extras.getInt("userId", -1);
                        String string5 = this.extras.getString("reason", null);
                        if (string4 == null || i7 == -1 || i8 == -1 || string5 == null) {
                            return;
                        }
                        boolean z3 = this.extras.getBoolean("isDelay", false);
                        FreecessController freecessController7 = this.mFreecessController;
                        freecessController7.getClass();
                        try {
                            FreecessPkgStatus packageStatus2 = freecessController7.getPackageStatus(i8, string4);
                            if (packageStatus2 == null) {
                                return;
                            }
                            String str2 = packageStatus2.name;
                            if (freecessController7.mAm != null && freecessController7.mIsQuickFreezeEnabled && !UidStateMgr.UidStateMgrHolder.INSTANCE.isUidIdle(packageStatus2.uid) && !z3) {
                                if (MARsDebugConfig.DEBUG_ENG) {
                                    Slog.d("FreecessController", "Try to make pkg idle for fz: (" + str2 + ", " + i8 + ").");
                                }
                                freecessController7.mAm.makePackageIdle(str2, i8);
                            }
                            if (freecessController7.mIsQuickFreezeEnabled) {
                                if (freecessController7.mAm.checkProcDiedOrComponentExecutingForFreeze(freecessController7.getAllRunningPackagePids(i7, true), new ArrayList()) == 2) {
                                    if (MARsDebugConfig.DEBUG_ENG) {
                                        Slog.d("FreecessController", "<" + i7 + ", " + str2 + "> is still ES after making idle, scheduling retry.");
                                    }
                                    FreecessHandlerHolder.INSTANCE.sendMakePkgIdleMsg(packageStatus2.name, i7, true, i8, string5);
                                    return;
                                }
                            }
                            if (freecessController7.getAllRunningPackagePids(i7, true).isEmpty()) {
                                return;
                            }
                            if (MARsDebugConfig.DEBUG_ENG) {
                                Slog.d("FreecessController", "<" + i7 + ", " + string4 + "> Service stopped, continue.");
                            }
                            FreecessHandlerHolder.INSTANCE.sendChangeToFrozenMsg(i8, str2, string5);
                            return;
                        } catch (Exception e) {
                            BootReceiver$$ExternalSyntheticOutline0.m(e, "makePkgIdleForLcdOn Exception : ", "FreecessController");
                            return;
                        }
                    }
                    return;
                case 30:
                    if (data != null) {
                        int i9 = data.getInt("uid", -1);
                        String string6 = this.extras.getString("packageName", null);
                        ActiveMusicRecordFilter activeMusicRecordFilter = ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE;
                        if (activeMusicRecordFilter.mAudioManager == null) {
                            return;
                        }
                        synchronized (activeMusicRecordFilter.mSilentAudioDetectionMap) {
                            try {
                                if (!activeMusicRecordFilter.mSilentAudioDetectionMap.containsKey(Integer.valueOf(i9))) {
                                    ArrayMap arrayMap = activeMusicRecordFilter.mActiveMusicRecordPkgs;
                                    if ((arrayMap != null && !arrayMap.isEmpty() && activeMusicRecordFilter.mActiveMusicRecordPkgs.containsKey(Integer.valueOf(i9))) || activeMusicRecordFilter.isUsingAudio(i9, string6)) {
                                        synchronized (activeMusicRecordFilter.mSilentAudioDetectionMap) {
                                            try {
                                                if (MARsDebugConfig.DEBUG_MARs) {
                                                    Slog.d("ActiveMusicRecordFilter", " startCheckSilentAudio :" + i9);
                                                }
                                                if (activeMusicRecordFilter.mSlientAudioApp.contains(Integer.valueOf(i9))) {
                                                    activeMusicRecordFilter.mSlientAudioApp.remove(Integer.valueOf(i9));
                                                }
                                                activeMusicRecordFilter.mSilentAudioDetectionMap.put(Integer.valueOf(i9), Long.valueOf(SystemClock.elapsedRealtime()));
                                                if (MARsDebugConfig.DEBUG_MARs) {
                                                    Slog.d("ActiveMusicRecordFilter", "put " + i9 + " to check at : " + activeMusicRecordFilter.mSilentAudioDetectionMap.get(Integer.valueOf(i9)));
                                                }
                                            } finally {
                                            }
                                        }
                                        activeMusicRecordFilter.mAudioManager.setParameters(activeMusicRecordFilter.SILENT_AUDIO_PREX + i9);
                                        FreecessHandlerHolder.INSTANCE.sendSlientAudioDeactivated(i9, string6, 15000L);
                                    }
                                }
                            } finally {
                            }
                        }
                        return;
                    }
                    return;
                case 31:
                    if (data != null) {
                        int i10 = data.getInt("uid", -1);
                        this.extras.getString("packageName", null);
                        this.extras.getLong("delay", 0L);
                        ActiveMusicRecordFilter activeMusicRecordFilter2 = ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE;
                        if (activeMusicRecordFilter2.mAudioManager == null) {
                            return;
                        }
                        try {
                            SystemClock.elapsedRealtime();
                            synchronized (activeMusicRecordFilter2.mSilentAudioDetectionMap) {
                                try {
                                    if (activeMusicRecordFilter2.mSlientAudioApp.contains(Integer.valueOf(i10))) {
                                        activeMusicRecordFilter2.mSlientAudioApp.remove(Integer.valueOf(i10));
                                    }
                                    if (activeMusicRecordFilter2.mSilentAudioDetectionMap.containsKey(Integer.valueOf(i10))) {
                                        long longValue = ((Long) activeMusicRecordFilter2.mSilentAudioDetectionMap.remove(Integer.valueOf(i10))).longValue();
                                        String parameters = activeMusicRecordFilter2.mAudioManager.getParameters(activeMusicRecordFilter2.SILENT_AUDIO_PREX + i10);
                                        if (MARsDebugConfig.DEBUG_MARs) {
                                            Slog.d("ActiveMusicRecordFilter", " endCheckSilentAudio result :" + parameters + " check time:" + (SystemClock.elapsedRealtime() - longValue));
                                        }
                                        synchronized (activeMusicRecordFilter2.mSilentAudioDetectionMap) {
                                            try {
                                                if ("true".equals(parameters)) {
                                                    if (SystemClock.elapsedRealtime() - longValue >= 15000) {
                                                        activeMusicRecordFilter2.mSlientAudioApp.add(Integer.valueOf(i10));
                                                        boolean z4 = MARsPolicyManager.MARs_ENABLE;
                                                        MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.addDebugInfoToHistory("DEV", "SILENT:" + i10);
                                                    }
                                                } else if (activeMusicRecordFilter2.mSlientAudioApp.contains(Integer.valueOf(i10))) {
                                                    activeMusicRecordFilter2.mSlientAudioApp.remove(Integer.valueOf(i10));
                                                }
                                            } finally {
                                            }
                                        }
                                        if (MARsDebugConfig.DEBUG_MARs) {
                                            Slog.d("ActiveMusicRecordFilter", "mSlientAudioApp has " + activeMusicRecordFilter2.mSlientAudioApp.toString());
                                            return;
                                        }
                                        return;
                                    }
                                    return;
                                } finally {
                                }
                            }
                        } catch (Exception e2) {
                            PackageWatchdog$BootThreshold$$ExternalSyntheticOutline0.m(e2, " endCheckSilentAudio exception :", "ActiveMusicRecordFilter");
                            return;
                        }
                    }
                    return;
            }
        }

        public final void handleRepeatCalmMode() {
            Bundle bundle = this.extras;
            if (bundle != null) {
                String string = bundle.getString("packageName", null);
                int i = this.extras.getInt("userId", -1);
                String string2 = this.extras.getString("reason", null);
                FreecessController freecessController = this.mFreecessController;
                freecessController.getClass();
                synchronized (MARsPolicyManager.MARsLock) {
                    try {
                        FreecessPkgStatus packageStatus = freecessController.getPackageStatus(i, string);
                        if (packageStatus != null && !freecessController.freezeForCalmMode(packageStatus, string2)) {
                            FreecessHandlerHolder.INSTANCE.sendCalmModeRepeatMsg(i, string, string2);
                        }
                    } finally {
                    }
                }
            }
        }

        public final void handleUnfreezeActivePackages() {
            String string;
            Bundle bundle = this.extras;
            if (bundle == null || (string = bundle.getString("reason", null)) == null) {
                return;
            }
            FreecessController freecessController = this.mFreecessController;
            freecessController.getClass();
            if ("screenOn-widget".equals(string)) {
                synchronized (MARsPolicyManager.MARsLock) {
                    try {
                        SparseArray clone = freecessController.mFreezedPackages.mUidMap.clone();
                        for (int i = 0; i < clone.size(); i++) {
                            FreecessPkgStatus freecessPkgStatus = (FreecessPkgStatus) clone.valueAt(i);
                            if (WidgetPkgFilter.WidgetPkgFilterHolder.INSTANCE.filter(freecessPkgStatus.userId, freecessPkgStatus.uid, 4, freecessPkgStatus.name) > 0) {
                                freecessController.unFreezeAction(freecessPkgStatus, string, false);
                            }
                        }
                    } finally {
                    }
                }
                return;
            }
            synchronized (MARsPolicyManager.MARsLock) {
                try {
                    SparseArray clone2 = freecessController.mFreezedPackages.mUidMap.clone();
                    for (int i2 = 0; i2 < clone2.size(); i2++) {
                        FreecessPkgStatus freecessPkgStatus2 = (FreecessPkgStatus) clone2.valueAt(i2);
                        if (freecessPkgStatus2.freezedRecord.isLcdOffFreezed && !UidStateMgr.UidStateMgrHolder.INSTANCE.isUidIdle(freecessPkgStatus2.uid)) {
                            freecessController.unFreezeAction(freecessPkgStatus2, string, false);
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class NetLinkReceiverThread extends Thread {
        public final /* synthetic */ FreecessHandler this$0 = FreecessHandlerHolder.INSTANCE;

        public NetLinkReceiverThread() {
            super("Freecess_NLRCT");
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x010e  */
        /* JADX WARN: Removed duplicated region for block: B:35:0x011d A[EDGE_INSN: B:35:0x011d->B:36:0x011d BREAK  A[LOOP:0: B:2:0x0007->B:31:0x0007], SYNTHETIC] */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 314
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.FreecessHandler.NetLinkReceiverThread.run():void");
        }
    }

    public static void receiveNetLinkInformationContinously() {
        while (true) {
            boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
            FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
            freecessController.getClass();
            try {
                FileDescriptor fileDescriptor = freecessController.mSendRecvNetLinkFd;
                if (fileDescriptor != null) {
                    StructFreeCessMsg parse = StructFreeCessMsg.parse(NetlinkSocket.recvMessage(fileDescriptor, FreecessController.DEFAULT_RECV_BUFSIZE, 5000000000L));
                    if (MARsDebugConfig.DEBUG_NETLINK) {
                        Slog.d("FreecessController", "RecvNetlink type:" + parse.type + " mod:" + parse.mod + " src_portid:" + parse.src_portid + " dst_portid:" + parse.dst_portid + " version:" + parse.version + " target_uid:" + parse.target_uid + " flag:" + parse.flag + " code:" + parse.code + " cmd:" + parse.cmd + " uid:" + parse.uid);
                    }
                    freecessController.kernelFreecessReport(parse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void init(Context context, HandlerThread handlerThread, HandlerThread handlerThread2) {
        this.mBluetoothHandler = new BluetoothHandler(handlerThread2.getLooper());
        this.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        this.mFreecessThread = null;
        this.mMainHandler = new MainHandler(handlerThread.getLooper());
    }

    public final void removeBgTriggerMsg() {
        if (this.mMainHandler == null) {
            return;
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d("FreecessHandler", "removeBgTriggerMsg....");
        }
        this.mMainHandler.removeMessages(2);
        this.mMainHandler.removeMessages(3);
        this.mMainHandler.removeMessages(28);
        this.mMainHandler.removeMessages(1);
        this.mMainHandler.removeMessages(4);
    }

    public final void removeBgTriggerMsgByObj(int i, Object obj) {
        if (this.mMainHandler == null) {
            return;
        }
        if (MARsDebugConfig.DEBUG_ENG) {
            Slog.d("FreecessHandler", "removeBgTriggerMsgByObj....what:" + i + ",obj:" + obj);
        }
        this.mMainHandler.removeMessages(i, obj);
    }

    public final void sendCalmModeRepeatMsg(int i, String str, String str2) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "packageName", str, "userId");
        m.putString("reason", str2);
        Message obtainMessage = this.mMainHandler.obtainMessage(22, str);
        obtainMessage.setData(m);
        this.mMainHandler.sendMessageDelayed(obtainMessage, 2000L);
    }

    public final void sendChangeToFrozenMsg(int i, String str, String str2) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "packageName", str, "userId");
        m.putString("reason", str2);
        Message obtainMessage = this.mMainHandler.obtainMessage(3, str);
        obtainMessage.setData(m);
        if (this.mMainHandler.hasMessages(3, str)) {
            return;
        }
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendFreecessSettlementMsg(int i, int i2, boolean z, String str, int i3, boolean z2, boolean z3, int i4) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        Message obtainMessage = mainHandler.obtainMessage(7);
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "packageName", str, "userId");
        m.putInt("uid", i2);
        m.putInt("packetMonitorFlag", i3);
        m.putInt("disableWakelockFlag", i4);
        m.putBoolean("isLcdOnTrigger", z2);
        m.putBoolean("unrestrictJobs", z3);
        obtainMessage.setData(m);
        this.mMainHandler.sendMessage(obtainMessage);
        if (z) {
            Message obtainMessage2 = this.mMainHandler.obtainMessage(8);
            Bundle bundle = new Bundle();
            bundle.putInt("uid", i2);
            obtainMessage2.setData(bundle);
            this.mMainHandler.sendMessageDelayed(obtainMessage2, 2000L);
        }
    }

    public final void sendMakePkgIdleMsg(String str, int i, boolean z, int i2, String str2) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "packageName", str, "uid");
        m.putInt("userId", i2);
        m.putString("reason", str2);
        m.putBoolean("isDelay", z);
        Message obtainMessage = this.mMainHandler.obtainMessage(28, str);
        obtainMessage.setData(m);
        if (this.mMainHandler.hasMessages(28, str)) {
            if (MARsDebugConfig.DEBUG_ENG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m("packageName: ", str, " already hasMessage", "FreecessHandler");
            }
        } else if (z) {
            this.mMainHandler.sendMessageDelayed(obtainMessage, 2000L);
        } else {
            this.mMainHandler.sendMessage(obtainMessage);
        }
    }

    public final void sendOLAFMsg(int i, String str, boolean z) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        mainHandler.removeMessages(9);
        Bundle bundle = new Bundle();
        bundle.putBoolean("enterFlag", z);
        bundle.putString("packageName", str);
        bundle.putInt("uid", i);
        Message obtainMessage = this.mMainHandler.obtainMessage(9);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessageAtFrontOfQueue(obtainMessage);
    }

    public final void sendOLAFTimeOutMsg(long j) {
        int i;
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        Message obtainMessage = mainHandler.obtainMessage(10);
        if (j != 0) {
            i = (int) (j - SystemClock.uptimeMillis());
            if (i > 5000) {
                i = 5000;
            }
            if (i <= 0) {
                this.mMainHandler.sendMessage(obtainMessage);
                return;
            }
        } else {
            i = 3000;
        }
        this.mMainHandler.sendMessageDelayed(obtainMessage, i);
    }

    public final void sendOnFreezeStateChanged(int i, boolean z) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean("enabled", z);
        bundle.putInt("uid", i);
        Message obtainMessage = this.mMainHandler.obtainMessage(14);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendReportToBroadcastQueueMsg(ArrayList arrayList) {
        if (this.mHandleAmsLockHandler == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("pids", arrayList);
        Message obtainMessage = this.mHandleAmsLockHandler.obtainMessage(29);
        obtainMessage.setData(bundle);
        this.mHandleAmsLockHandler.sendMessage(obtainMessage);
    }

    public final void sendResetAllStateMsg(String str) {
        if (this.mMainHandler == null) {
            return;
        }
        boolean equals = "Watchdog_HALF".equals(str);
        if (equals) {
            boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
            final FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
            Slog.w("FreecessController", "!@*** unFreezeAllPackages for watchdog : Start thread for preaction unfreezing!!! cntFail(FZ/UFZ/UFZ_P) : " + freecessController.cntFailFreeze + "/" + freecessController.cntFailUnfreeze);
            final long uptimeMillis = SystemClock.uptimeMillis();
            new Thread() { // from class: com.android.server.am.FreecessController.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super("MARsWatchdogUnfreezer");
                }

                @Override // java.lang.Thread, java.lang.Runnable
                public final void run() {
                    try {
                        if (!FreecessController.this.mFrozenPidListSelfLocked.isEmpty()) {
                            Iterator it = new ArrayList(FreecessController.this.mFrozenPidListSelfLocked).iterator();
                            while (it.hasNext()) {
                                Integer num = (Integer) it.next();
                                if (SystemClock.uptimeMillis() - uptimeMillis >= 5000) {
                                    break;
                                } else if (num != null) {
                                    FreecessController.this.releaseFreezedAppPid(num.intValue());
                                }
                            }
                        }
                    } catch (Exception e) {
                        boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                        BootReceiver$$ExternalSyntheticOutline0.m(e, "Error occurred while handleResetAllPreAction: ", "FreecessController");
                    }
                    boolean z3 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                    Slog.w("FreecessController", "!@*** unFreezeAllPackages for watchdog : End thread for preaction unfreezing!!!");
                }
            }.start();
        }
        this.mMainHandler.removeMessages(6);
        Bundle bundle = new Bundle();
        bundle.putString("reason", str);
        Message obtainMessage = this.mMainHandler.obtainMessage(6);
        obtainMessage.setData(bundle);
        if (equals) {
            this.mMainHandler.sendMessageDelayed(obtainMessage, 5000L);
        } else {
            this.mMainHandler.sendMessage(obtainMessage);
        }
    }

    public final void sendSetFreecessEnableDelayedMsg(int i) {
        if (this.mMainHandler == null) {
            return;
        }
        int i2 = i == 2 ? 660000 : EventQueue.LOG_WAIT_TIME;
        long currentTimeMillis = System.currentTimeMillis();
        long j = i2;
        if (this.lastDelayedTime - currentTimeMillis < j) {
            this.lastDelayedTime = currentTimeMillis + j;
            this.mMainHandler.removeMessages(11);
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            Message obtainMessage = this.mMainHandler.obtainMessage(11);
            obtainMessage.setData(bundle);
            this.mMainHandler.sendMessageDelayed(obtainMessage, j);
        }
    }

    public final void sendSlientAudioActiveTrigger(int i, String str) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null || mainHandler.hasMessages(30, str)) {
            return;
        }
        Message obtainMessage = this.mMainHandler.obtainMessage(30, str);
        Bundle bundle = new Bundle();
        bundle.putString("packageName", str);
        bundle.putInt("uid", i);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendSlientAudioDeactivated(int i, String str, long j) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        if (mainHandler.hasMessages(31, str)) {
            if (j != 0) {
                return;
            } else {
                this.mMainHandler.removeMessages(31, str);
            }
        }
        Message obtainMessage = this.mMainHandler.obtainMessage(31, str);
        Bundle m = FreecessController$$ExternalSyntheticOutline0.m(i, "packageName", str, "uid");
        m.putLong("delay", j);
        obtainMessage.setData(m);
        this.mMainHandler.sendMessageDelayed(obtainMessage, j);
    }

    public final void sendUidLcdOnQuickFzMsg(int i, String str, String str2, boolean z) {
        if (this.mMainHandler == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("uid", i);
        bundle.putString("packageName", str);
        if (str2 != null) {
            bundle.putString("reason", str2);
        }
        Message obtainMessage = this.mMainHandler.obtainMessage(1, str);
        obtainMessage.setData(bundle);
        if (this.mMainHandler.hasMessages(1, str)) {
            if (MARsDebugConfig.DEBUG_ENG) {
                DeviceIdleController$$ExternalSyntheticOutline0.m("packageName: ", str, " already hasMessage", "FreecessHandler");
            }
        } else {
            if (!z) {
                this.mMainHandler.sendMessage(obtainMessage);
                return;
            }
            boolean z2 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
            if (FreecessController.FreecessControllerHolder.INSTANCE.mIsQuickFreezeEnabled) {
                this.mMainHandler.sendMessageDelayed(obtainMessage, mScreenOnQuickFreezeCheckDelay);
            } else {
                this.mMainHandler.sendMessageDelayed(obtainMessage, 60000L);
            }
        }
    }

    public final void sendUnfreezeActivePackagesMsg(String str) {
        MainHandler mainHandler = this.mMainHandler;
        if (mainHandler == null) {
            return;
        }
        mainHandler.removeMessages(13);
        Bundle bundle = new Bundle();
        bundle.putString("reason", str);
        Message obtainMessage = this.mMainHandler.obtainMessage(13);
        obtainMessage.setData(bundle);
        this.mMainHandler.sendMessage(obtainMessage);
    }

    public final void sendUpdateBTMsg(int i, int i2) {
        if (this.mBluetoothHandler.hasMessages(i)) {
            return;
        }
        Bundle m = SystemUpdateManagerService$$ExternalSyntheticOutline0.m(i2, "uid");
        Message obtainMessage = this.mBluetoothHandler.obtainMessage(i);
        obtainMessage.setData(m);
        this.mBluetoothHandler.sendMessage(obtainMessage);
    }
}
