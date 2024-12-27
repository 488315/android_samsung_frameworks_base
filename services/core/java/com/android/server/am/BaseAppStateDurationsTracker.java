package com.android.server.am;

import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.SystemClock;
import android.util.SparseArray;
import com.android.server.am.BaseAppStateEventsTracker;
import java.io.PrintWriter;
import java.util.LinkedList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BaseAppStateDurationsTracker extends BaseAppStateEventsTracker {
    public final SparseArray mUidStateDurations;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class SimplePackageDurations extends BaseAppStateDurations {
        public SimplePackageDurations(int i, String str, BaseAppStateEventsTracker.BaseAppStateEventsPolicy baseAppStateEventsPolicy) {
            super(i, str, 1, baseAppStateEventsPolicy);
            this.mEvents[0] = new LinkedList();
        }

        @Override // com.android.server.am.BaseAppStateEvents
        public final String formatEventTypeLabel(int i) {
            return "";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidStateDurations extends SimplePackageDurations {
    }

    public BaseAppStateDurationsTracker(Context context, AppRestrictionController appRestrictionController) {
        super(context, appRestrictionController);
        this.mUidStateDurations = new SparseArray();
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final void dumpEventLocked(PrintWriter printWriter, String str, BaseAppStateEvents baseAppStateEvents, long j) {
        BaseAppStateDurations baseAppStateDurations = (BaseAppStateDurations) baseAppStateEvents;
        UidStateDurations uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(baseAppStateDurations.mUid);
        printWriter.print("  " + str);
        printWriter.println("(bg only)");
        if (uidStateDurations == null || uidStateDurations.isEmpty()) {
            baseAppStateDurations.dump(printWriter, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("    ", str), j);
            return;
        }
        BaseAppStateDurations baseAppStateDurations2 = (BaseAppStateDurations) createAppStateEvents(baseAppStateDurations);
        LinkedList[] linkedListArr = uidStateDurations.mEvents;
        if (linkedListArr.length > 0 && linkedListArr[0] != null) {
            int i = 0;
            while (true) {
                LinkedList[] linkedListArr2 = baseAppStateDurations2.mEvents;
                if (i >= linkedListArr2.length) {
                    break;
                }
                LinkedList linkedList = linkedListArr2[i];
                if (linkedList != null) {
                    linkedListArr2[i] = BaseAppStateDurations.subtract(linkedList, uidStateDurations.mEvents[0]);
                }
                i++;
            }
        }
        baseAppStateDurations2.dump(printWriter, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("    ", str), j);
        printWriter.print("  " + str);
        printWriter.println("(fg + bg)");
        baseAppStateDurations.dump(printWriter, "    " + str, j);
    }

    public final long getTotalDurationsSince(int i, int i2, long j, long j2) {
        synchronized (this.mLock) {
            try {
                BaseAppStateDurations baseAppStateDurations = (BaseAppStateDurations) getUidEventsLocked(i);
                if (baseAppStateDurations == null) {
                    return 0L;
                }
                UidStateDurations uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(i);
                if (uidStateDurations != null && !uidStateDurations.isEmpty()) {
                    baseAppStateDurations.subtract(uidStateDurations, i2);
                }
                return baseAppStateDurations.getTotalDurationsSince(i2, j, j2);
            } finally {
            }
        }
    }

    public final long getTotalDurationsSince(int i, int i2, String str, long j, long j2) {
        synchronized (this.mLock) {
            try {
                BaseAppStateDurations baseAppStateDurations = (BaseAppStateDurations) this.mPkgEvents.get(i, str);
                if (baseAppStateDurations == null) {
                    return 0L;
                }
                UidStateDurations uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(i);
                if (uidStateDurations == null || uidStateDurations.isEmpty()) {
                    return baseAppStateDurations.getTotalDurationsSince(i2, j, j2);
                }
                BaseAppStateDurations baseAppStateDurations2 = (BaseAppStateDurations) createAppStateEvents(baseAppStateDurations);
                baseAppStateDurations2.subtract(uidStateDurations, i2);
                return baseAppStateDurations2.getTotalDurationsSince(i2, j, j2);
            } finally {
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    public final void onUidGone(int i) {
        onUidProcStateChanged(i, 20);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    public final void onUidProcStateChanged(int i, int i2) {
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
                UidStateDurations uidStateDurations = (UidStateDurations) this.mUidStateDurations.get(i);
                if (uidStateDurations == null) {
                    uidStateDurations = new UidStateDurations(i, "", (BaseAppStateEventsTracker.BaseAppStateEventsPolicy) this.mInjector.mAppStatePolicy);
                    this.mUidStateDurations.put(i, uidStateDurations);
                }
                uidStateDurations.addEvent(i2 < 4, new BaseAppStateTimeEvents$BaseTimeEvent(SystemClock.elapsedRealtime()), 0);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final void onUntrackingUidLocked(int i) {
        this.mUidStateDurations.remove(i);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public void reset() {
        super.reset();
        synchronized (this.mLock) {
            this.mUidStateDurations.clear();
        }
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final void trimLocked(long j) {
        super.trimLocked(j);
        for (int size = this.mUidStateDurations.size() - 1; size >= 0; size--) {
            UidStateDurations uidStateDurations = (UidStateDurations) this.mUidStateDurations.valueAt(size);
            for (int i = 0; i < uidStateDurations.mEvents.length; i++) {
                uidStateDurations.trimEvents(i, j);
            }
            if (uidStateDurations.isEmpty()) {
                this.mUidStateDurations.removeAt(size);
            }
        }
    }
}
