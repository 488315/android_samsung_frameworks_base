package com.android.server.am;

import android.content.pm.ServiceInfo;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.wm.BackgroundLaunchProcessController;
import com.android.server.wm.WindowProcessController;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProcessServiceRecord {
    public boolean mAllowlistManager;
    public final ProcessRecord mApp;
    public int mConnectionGroup;
    public int mConnectionImportance;
    public ServiceRecord mConnectionService;
    public boolean mExecServicesFg;
    public int mFgServiceTypes;
    public boolean mHasAboveClient;
    public boolean mHasClientActivities;
    public boolean mHasForegroundServices;
    public boolean mHasTopStartedAlmostPerceptibleServices;
    public boolean mHasTypeNoneFgs;
    public long mLastTopStartedAlmostPerceptibleBindRequestUptimeMs;
    public boolean mRepHasForegroundServices;
    public boolean mScheduleServiceTimeoutPending;
    public ArraySet mSdkSandboxConnections;
    public final ActivityManagerService mService;
    public boolean mTreatLikeActivity;
    public final ArraySet mServices = new ArraySet();
    public final ArraySet mExecutingServices = new ArraySet();
    public final ArraySet mConnections = new ArraySet();
    public ArraySet mBoundClientUids = new ArraySet();

    public ProcessServiceRecord(ProcessRecord processRecord) {
        this.mApp = processRecord;
        this.mService = processRecord.mService;
    }

    public static boolean isAlmostPerceptible(ServiceRecord serviceRecord) {
        if (serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs <= 0) {
            return false;
        }
        ArrayMap arrayMap = serviceRecord.connections;
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) arrayMap.valueAt(size);
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                if (((ConnectionRecord) arrayList.get(size2))
                        .hasFlag(EndpointMonitorConst.FLAG_TRACING_NETWORK_EVENT_ABNORMAL_PKT)) {
                    return true;
                }
            }
        }
        return false;
    }

    public final void clearBoundClientUids() {
        this.mBoundClientUids.clear();
        BackgroundLaunchProcessController backgroundLaunchProcessController =
                this.mApp.mWindowProcessController.mBgLaunchController;
        synchronized (backgroundLaunchProcessController) {
            try {
                IntArray intArray = backgroundLaunchProcessController.mBalOptInBoundClientUids;
                if (intArray == null) {
                    backgroundLaunchProcessController.mBalOptInBoundClientUids = new IntArray();
                } else {
                    intArray.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ConnectionRecord getConnectionAt(int i) {
        return (ConnectionRecord) this.mConnections.valueAt(i);
    }

    public final ServiceRecord getRunningServiceAt(int i) {
        return (ServiceRecord) this.mServices.valueAt(i);
    }

    public final boolean hasNonShortForegroundServices() {
        if (this.mHasForegroundServices) {
            return this.mHasTypeNoneFgs || this.mFgServiceTypes != 2048;
        }
        return false;
    }

    public final int modifyRawOomAdj(int i) {
        if (!this.mHasAboveClient || i < 0) {
            return i;
        }
        int i2 = 100;
        if (i >= 100) {
            i2 = 200;
            if (i >= 200) {
                i2 =
                        FrameworkStatsLog
                                .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE;
                if (i >= 250) {
                    i2 = FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT;
                    if (i >= 900) {
                        return i < 999 ? i + 1 : i;
                    }
                }
            }
        }
        return i2;
    }

    public final void scheduleServiceTimeoutIfNeededLocked() {
        Flags.serviceBindingOomAdjPolicy();
        if (!this.mScheduleServiceTimeoutPending || this.mExecutingServices.size() <= 0) {
            return;
        }
        this.mService.mServices.scheduleServiceTimeoutLocked(this.mApp);
        long uptimeMillis = SystemClock.uptimeMillis();
        int size = this.mExecutingServices.size();
        for (int i = 0; i < size; i++) {
            ((ServiceRecord) this.mExecutingServices.valueAt(i)).executingStart = uptimeMillis;
        }
    }

    public final boolean startService(ServiceRecord serviceRecord) {
        ServiceInfo serviceInfo;
        if (serviceRecord == null) {
            return false;
        }
        boolean add = this.mServices.add(serviceRecord);
        if (add && (serviceInfo = serviceRecord.serviceInfo) != null) {
            WindowProcessController windowProcessController = this.mApp.mWindowProcessController;
            windowProcessController.getClass();
            String str = serviceInfo.permission;
            if (str != null) {
                switch (str) {
                    case "android.permission.BIND_INPUT_METHOD":
                        windowProcessController.mHasImeService = true;
                    case "android.permission.BIND_VOICE_INTERACTION":
                    case "android.permission.BIND_ACCESSIBILITY_SERVICE":
                        windowProcessController.mIsActivityConfigOverrideAllowed = false;
                        windowProcessController.unregisterActivityConfigurationListener();
                        break;
                }
            }
            updateHostingComonentTypeForBindingsLocked();
        }
        long j = serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs;
        if (j > 0) {
            this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs =
                    Math.max(this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs, j);
            if (!this.mHasTopStartedAlmostPerceptibleServices) {
                this.mHasTopStartedAlmostPerceptibleServices = isAlmostPerceptible(serviceRecord);
            }
        }
        return add;
    }

    public final void stopService(ServiceRecord serviceRecord) {
        boolean remove = this.mServices.remove(serviceRecord);
        if (serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs > 0) {
            updateHasTopStartedAlmostPerceptibleServices();
        }
        if (remove) {
            updateHostingComonentTypeForBindingsLocked();
        }
    }

    public final void updateBoundClientUids() {
        clearBoundClientUids();
        if (this.mServices.isEmpty()) {
            return;
        }
        ArraySet arraySet = new ArraySet();
        int size = this.mServices.size();
        WindowProcessController windowProcessController = this.mApp.mWindowProcessController;
        for (int i = 0; i < size; i++) {
            ArrayMap arrayMap = ((ServiceRecord) this.mServices.valueAt(i)).connections;
            int size2 = arrayMap.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ArrayList arrayList = (ArrayList) arrayMap.valueAt(i2);
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    ConnectionRecord connectionRecord = (ConnectionRecord) arrayList.get(i3);
                    arraySet.add(Integer.valueOf(connectionRecord.clientUid));
                    windowProcessController.addBoundClientUid(
                            connectionRecord.clientUid,
                            connectionRecord.clientPackageName,
                            connectionRecord.flags);
                }
            }
        }
        this.mBoundClientUids = arraySet;
    }

    public final void updateHasTopStartedAlmostPerceptibleServices() {
        this.mHasTopStartedAlmostPerceptibleServices = false;
        this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs = 0L;
        for (int size = this.mServices.size() - 1; size >= 0; size--) {
            ServiceRecord serviceRecord = (ServiceRecord) this.mServices.valueAt(size);
            this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs =
                    Math.max(
                            this.mLastTopStartedAlmostPerceptibleBindRequestUptimeMs,
                            serviceRecord.lastTopAlmostPerceptibleBindRequestUptimeMs);
            if (!this.mHasTopStartedAlmostPerceptibleServices
                    && isAlmostPerceptible(serviceRecord)) {
                this.mHasTopStartedAlmostPerceptibleServices = true;
            }
        }
    }

    public final void updateHostingComonentTypeForBindingsLocked() {
        int size = this.mServices.size();
        while (true) {
            size--;
            ProcessRecord processRecord = this.mApp;
            if (size < 0) {
                processRecord.mProfile.clearHostingComponentType(512);
                return;
            }
            ServiceRecord runningServiceAt = getRunningServiceAt(size);
            if (runningServiceAt != null && !runningServiceAt.connections.isEmpty()) {
                processRecord.mProfile.addHostingComponentType(512);
                return;
            }
        }
    }
}
