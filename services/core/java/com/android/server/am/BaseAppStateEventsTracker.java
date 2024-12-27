package com.android.server.am;

import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.PowerExemptionManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.SparseArray;

import java.io.PrintWriter;

public abstract class BaseAppStateEventsTracker extends BaseAppStateTracker {
    public final UidProcessMap mPkgEvents;
    public final ArraySet mTopUids;

    public abstract class BaseAppStateEventsPolicy extends BaseAppStatePolicy {
        public final long mDefaultMaxTrackingDuration;
        public final String mKeyMaxTrackingDuration;
        public volatile long mMaxTrackingDuration;

        public BaseAppStateEventsPolicy(
                BaseAppStateTracker.Injector injector,
                BaseAppStateEventsTracker baseAppStateEventsTracker,
                String str,
                boolean z,
                String str2,
                long j) {
            super(injector, baseAppStateEventsTracker, str, z);
            this.mKeyMaxTrackingDuration = str2;
            this.mDefaultMaxTrackingDuration = j;
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void dump(PrintWriter printWriter, String str) {
            super.dump(printWriter, str);
            if (this.mTrackerEnabled) {
                printWriter.print(str);
                printWriter.print(this.mKeyMaxTrackingDuration);
                printWriter.print('=');
                printWriter.println(this.mMaxTrackingDuration);
            }
        }

        public String getExemptionReasonString(int i, int i2, String str) {
            return PowerExemptionManager.reasonCodeToString(i2);
        }

        public abstract void onMaxTrackingDurationChanged();

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onPropertiesChanged(String str) {
            if (!this.mKeyMaxTrackingDuration.equals(str)) {
                if (this.mKeyTrackerEnabled.equals(str)) {
                    updateTrackerEnabled();
                }
            } else {
                long j =
                        DeviceConfig.getLong(
                                "activity_manager",
                                this.mKeyMaxTrackingDuration,
                                this.mDefaultMaxTrackingDuration);
                if (j != this.mMaxTrackingDuration) {
                    this.mMaxTrackingDuration = j;
                    onMaxTrackingDurationChanged();
                }
            }
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public void onSystemReady() {
            updateTrackerEnabled();
            long j =
                    DeviceConfig.getLong(
                            "activity_manager",
                            this.mKeyMaxTrackingDuration,
                            this.mDefaultMaxTrackingDuration);
            if (j != this.mMaxTrackingDuration) {
                this.mMaxTrackingDuration = j;
                onMaxTrackingDurationChanged();
            }
        }
    }

    public BaseAppStateEventsTracker(
            Context context, AppRestrictionController appRestrictionController) {
        super(context, appRestrictionController);
        this.mPkgEvents = new UidProcessMap();
        this.mTopUids = new ArraySet();
    }

    public abstract BaseAppStateEvents createAppStateEvents(int i, String str);

    public abstract BaseAppStateEvents createAppStateEvents(BaseAppStateEvents baseAppStateEvents);

    @Override // com.android.server.am.BaseAppStateTracker
    public void dump(PrintWriter printWriter, String str) {
        BaseAppStateEventsPolicy baseAppStateEventsPolicy =
                (BaseAppStateEventsPolicy) this.mInjector.mAppStatePolicy;
        synchronized (this.mLock) {
            try {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                SparseArray sparseArray = this.mPkgEvents.mMap;
                for (int size = sparseArray.size() - 1; size >= 0; size--) {
                    int keyAt = sparseArray.keyAt(size);
                    ArrayMap arrayMap = (ArrayMap) sparseArray.valueAt(size);
                    for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                        String str2 = (String) arrayMap.keyAt(size2);
                        BaseAppStateEvents baseAppStateEvents =
                                (BaseAppStateEvents) arrayMap.valueAt(size2);
                        printWriter.print(str);
                        printWriter.print("* ");
                        printWriter.print(str2);
                        printWriter.print('/');
                        printWriter.print(UserHandle.formatUid(keyAt));
                        printWriter.print(" exemption=");
                        printWriter.println(
                                baseAppStateEventsPolicy.getExemptionReasonString(
                                        keyAt, baseAppStateEvents.mExemptReason, str2));
                        dumpEventLocked(printWriter, str, baseAppStateEvents, elapsedRealtime);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        dumpOthers(printWriter, str);
        baseAppStateEventsPolicy.dump(printWriter, str);
    }

    public void dumpEventLocked(
            PrintWriter printWriter, String str, BaseAppStateEvents baseAppStateEvents, long j) {
        baseAppStateEvents.dump(
                printWriter,
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("  ", str),
                j);
    }

    public void dumpOthers(PrintWriter printWriter, String str) {}

    public final BaseAppStateEvents getUidEventsLocked(int i) {
        ArrayMap arrayMap = (ArrayMap) this.mPkgEvents.mMap.get(i);
        BaseAppStateEvents baseAppStateEvents = null;
        if (arrayMap == null) {
            return null;
        }
        for (int size = arrayMap.size() - 1; size >= 0; size--) {
            BaseAppStateEvents baseAppStateEvents2 = (BaseAppStateEvents) arrayMap.valueAt(size);
            if (baseAppStateEvents2 != null) {
                if (baseAppStateEvents == null) {
                    baseAppStateEvents = createAppStateEvents(i, baseAppStateEvents2.mPackageName);
                }
                baseAppStateEvents.add(baseAppStateEvents2);
            }
        }
        return baseAppStateEvents;
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onUidGone(int i) {
        synchronized (this.mLock) {
            this.mTopUids.remove(Integer.valueOf(i));
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public void onUidProcStateChanged(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (this.mPkgEvents.mMap.indexOfKey(i) < 0) {
                    return;
                }
                if (i2 < 4) {
                    this.mTopUids.add(Integer.valueOf(i));
                } else {
                    this.mTopUids.remove(Integer.valueOf(i));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onUidRemoved(int i) {
        synchronized (this.mLock) {
            this.mPkgEvents.mMap.remove(i);
            onUntrackingUidLocked(i);
        }
    }

    public void onUntrackingUidLocked(int i) {}

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                SparseArray sparseArray = this.mPkgEvents.mMap;
                for (int size = sparseArray.size() - 1; size >= 0; size--) {
                    int keyAt = sparseArray.keyAt(size);
                    if (UserHandle.getUserId(keyAt) == i) {
                        sparseArray.removeAt(size);
                        onUntrackingUidLocked(keyAt);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void reset() {
        synchronized (this.mLock) {
            this.mPkgEvents.clear();
            this.mTopUids.clear();
        }
    }

    public final void trim(long j) {
        synchronized (this.mLock) {
            trimLocked(j);
        }
    }

    public void trimLocked(long j) {
        SparseArray sparseArray = this.mPkgEvents.mMap;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            ArrayMap arrayMap = (ArrayMap) sparseArray.valueAt(size);
            for (int size2 = arrayMap.size() - 1; size2 >= 0; size2--) {
                BaseAppStateEvents baseAppStateEvents =
                        (BaseAppStateEvents) arrayMap.valueAt(size2);
                for (int i = 0; i < baseAppStateEvents.mEvents.length; i++) {
                    baseAppStateEvents.trimEvents(i, j);
                }
                if (baseAppStateEvents.isEmpty()) {
                    arrayMap.removeAt(size2);
                }
            }
            if (arrayMap.size() == 0) {
                sparseArray.removeAt(size);
            }
        }
    }
}
