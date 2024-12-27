package com.android.server.am;

import android.os.SystemClock;
import android.util.ArrayMap;
import android.util.Pair;
import android.util.SparseArray;
import com.android.server.am.AppBatteryExemptionTracker;
import com.android.server.am.AppBatteryTracker;
import com.android.server.am.AppRestrictionController;
import com.android.server.am.BaseAppStateEventsTracker;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppBatteryExemptionTracker extends BaseAppStateDurationsTracker {
    public UidProcessMap mUidPackageStates;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppBatteryExemptionPolicy extends BaseAppStateEventsTracker.BaseAppStateEventsPolicy {
        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy, com.android.server.am.BaseAppStatePolicy
        public final void dump(PrintWriter printWriter, String str) {
            printWriter.print(str);
            printWriter.println("APP BATTERY EXEMPTION TRACKER POLICY SETTINGS:");
            super.dump(printWriter, "  " + str);
        }

        @Override // com.android.server.am.BaseAppStateEventsTracker.BaseAppStateEventsPolicy
        public final void onMaxTrackingDurationChanged() {
            final AppBatteryExemptionTracker appBatteryExemptionTracker = (AppBatteryExemptionTracker) this.mTracker;
            AppRestrictionController.BgHandler bgHandler = appBatteryExemptionTracker.mBgHandler;
            Objects.requireNonNull(appBatteryExemptionTracker);
            bgHandler.post(new Runnable() { // from class: com.android.server.am.AppBatteryExemptionTracker$AppBatteryExemptionPolicy$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    AppBatteryExemptionTracker appBatteryExemptionTracker2 = AppBatteryExemptionTracker.this;
                    appBatteryExemptionTracker2.getClass();
                    appBatteryExemptionTracker2.trim(Math.max(0L, SystemClock.elapsedRealtime() - ((AppBatteryExemptionTracker.AppBatteryExemptionPolicy) appBatteryExemptionTracker2.mInjector.mAppStatePolicy).mMaxTrackingDuration));
                }
            });
        }

        @Override // com.android.server.am.BaseAppStatePolicy
        public final void onTrackerEnabled(boolean z) {
            AppBatteryExemptionTracker appBatteryExemptionTracker = (AppBatteryExemptionTracker) this.mTracker;
            if (z) {
                appBatteryExemptionTracker.getClass();
                return;
            }
            synchronized (appBatteryExemptionTracker.mLock) {
                appBatteryExemptionTracker.mPkgEvents.clear();
                appBatteryExemptionTracker.mUidPackageStates.clear();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidBatteryStates extends BaseAppStateDurations {
        /* JADX WARN: Removed duplicated region for block: B:46:0x0113  */
        /* JADX WARN: Removed duplicated region for block: B:61:0x017a  */
        @Override // com.android.server.am.BaseAppStateDurations, com.android.server.am.BaseAppStateEvents
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public java.util.LinkedList add(java.util.LinkedList r27, java.util.LinkedList r28) {
            /*
                Method dump skipped, instructions count: 400
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.AppBatteryExemptionTracker.UidBatteryStates.add(java.util.LinkedList, java.util.LinkedList):java.util.LinkedList");
        }

        public final void addEvent(boolean z, long j, AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage, int i) {
            if (z) {
                addEvent(z, new UidStateEventWithBattery(z, j, immutableBatteryUsage, null), i);
                return;
            }
            LinkedList linkedList = this.mEvents[i];
            UidStateEventWithBattery uidStateEventWithBattery = linkedList != null ? (UidStateEventWithBattery) linkedList.peekLast() : null;
            if (uidStateEventWithBattery == null || !uidStateEventWithBattery.mIsStart) {
                return;
            }
            immutableBatteryUsage.getClass();
            AppBatteryTracker.BatteryUsage batteryUsage = new AppBatteryTracker.BatteryUsage(immutableBatteryUsage);
            batteryUsage.subtract(uidStateEventWithBattery.mBatteryUsage);
            addEvent(z, new UidStateEventWithBattery(z, j, new AppBatteryTracker.ImmutableBatteryUsage(batteryUsage), uidStateEventWithBattery), i);
        }

        public final Pair getBatteryUsageSince(int i, long j, long j2) {
            LinkedList linkedList = new LinkedList();
            int i2 = 0;
            while (true) {
                LinkedList[] linkedListArr = this.mEvents;
                if (i2 >= linkedListArr.length) {
                    break;
                }
                if (((1 << i2) & i) != 0) {
                    linkedList = add(linkedList, linkedListArr[i2]);
                }
                i2++;
            }
            if (linkedList == null || linkedList.size() == 0) {
                AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage = AppBatteryTracker.BATTERY_USAGE_NONE;
                return Pair.create(immutableBatteryUsage, immutableBatteryUsage);
            }
            AppBatteryTracker.BatteryUsage batteryUsage = new AppBatteryTracker.BatteryUsage();
            Iterator it = linkedList.iterator();
            UidStateEventWithBattery uidStateEventWithBattery = null;
            while (it.hasNext()) {
                uidStateEventWithBattery = (UidStateEventWithBattery) it.next();
                long j3 = uidStateEventWithBattery.mTimestamp;
                if (j3 >= j && !uidStateEventWithBattery.mIsStart) {
                    batteryUsage.add(uidStateEventWithBattery.getBatteryUsage(j, Math.min(j2, j3)));
                    if (j2 <= uidStateEventWithBattery.mTimestamp) {
                        break;
                    }
                }
            }
            return Pair.create(new AppBatteryTracker.ImmutableBatteryUsage(batteryUsage), uidStateEventWithBattery.mIsStart ? uidStateEventWithBattery.mBatteryUsage : AppBatteryTracker.BATTERY_USAGE_NONE);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidStateEventWithBattery extends BaseAppStateTimeEvents$BaseTimeEvent {
        public AppBatteryTracker.ImmutableBatteryUsage mBatteryUsage;
        public boolean mIsStart;
        public UidStateEventWithBattery mPeer;

        public UidStateEventWithBattery(boolean z, long j, AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage, UidStateEventWithBattery uidStateEventWithBattery) {
            super(j);
            this.mIsStart = z;
            this.mBatteryUsage = immutableBatteryUsage;
            this.mPeer = uidStateEventWithBattery;
            if (uidStateEventWithBattery != null) {
                uidStateEventWithBattery.mPeer = this;
            }
        }

        @Override // com.android.server.am.BaseAppStateTimeEvents$BaseTimeEvent
        public final Object clone() {
            UidStateEventWithBattery uidStateEventWithBattery = new UidStateEventWithBattery();
            uidStateEventWithBattery.mTimestamp = this.mTimestamp;
            uidStateEventWithBattery.mIsStart = this.mIsStart;
            uidStateEventWithBattery.mBatteryUsage = this.mBatteryUsage;
            return uidStateEventWithBattery;
        }

        @Override // com.android.server.am.BaseAppStateTimeEvents$BaseTimeEvent
        public final boolean equals(Object obj) {
            if (obj == null || obj.getClass() != UidStateEventWithBattery.class) {
                return false;
            }
            UidStateEventWithBattery uidStateEventWithBattery = (UidStateEventWithBattery) obj;
            return uidStateEventWithBattery.mIsStart == this.mIsStart && uidStateEventWithBattery.mTimestamp == this.mTimestamp && this.mBatteryUsage.equals(uidStateEventWithBattery.mBatteryUsage);
        }

        public final AppBatteryTracker.ImmutableBatteryUsage getBatteryUsage(long j, long j2) {
            if (this.mIsStart || j >= this.mTimestamp || j2 <= j) {
                return AppBatteryTracker.BATTERY_USAGE_NONE;
            }
            long max = Math.max(j, this.mPeer.mTimestamp);
            long min = Math.min(j2, this.mTimestamp);
            long j3 = this.mTimestamp - this.mPeer.mTimestamp;
            long j4 = min - max;
            if (j3 == 0) {
                return AppBatteryTracker.BATTERY_USAGE_NONE;
            }
            if (j3 == j4) {
                return this.mBatteryUsage;
            }
            AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage = this.mBatteryUsage;
            immutableBatteryUsage.getClass();
            AppBatteryTracker.BatteryUsage batteryUsage = new AppBatteryTracker.BatteryUsage(immutableBatteryUsage);
            batteryUsage.scaleInternal((j4 * 1.0d) / j3);
            return new AppBatteryTracker.ImmutableBatteryUsage(batteryUsage);
        }

        @Override // com.android.server.am.BaseAppStateTimeEvents$BaseTimeEvent
        public final int hashCode() {
            return this.mBatteryUsage.hashCode() + ((Long.hashCode(this.mTimestamp) + (Boolean.hashCode(this.mIsStart) * 31)) * 31);
        }

        public final String toString() {
            return "UidStateEventWithBattery(" + this.mIsStart + ", " + this.mTimestamp + ", " + this.mBatteryUsage + ")";
        }

        @Override // com.android.server.am.BaseAppStateTimeEvents$BaseTimeEvent
        public final void trimTo(long j) {
            if (!this.mIsStart || j < this.mTimestamp) {
                return;
            }
            UidStateEventWithBattery uidStateEventWithBattery = this.mPeer;
            if (uidStateEventWithBattery != null) {
                AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage = uidStateEventWithBattery.mBatteryUsage;
                uidStateEventWithBattery.mBatteryUsage = uidStateEventWithBattery.getBatteryUsage(j, uidStateEventWithBattery.mTimestamp);
                AppBatteryTracker.ImmutableBatteryUsage immutableBatteryUsage2 = this.mBatteryUsage;
                immutableBatteryUsage2.getClass();
                AppBatteryTracker.BatteryUsage batteryUsage = new AppBatteryTracker.BatteryUsage(immutableBatteryUsage2);
                batteryUsage.add(immutableBatteryUsage);
                batteryUsage.subtract(this.mPeer.mBatteryUsage);
                this.mBatteryUsage = new AppBatteryTracker.ImmutableBatteryUsage(batteryUsage);
            }
            this.mTimestamp = j;
        }
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final BaseAppStateEvents createAppStateEvents(int i, String str) {
        return new UidBatteryStates(i, "", 5, (BaseAppStateEventsTracker.BaseAppStateEventsPolicy) this.mInjector.mAppStatePolicy);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker
    public final BaseAppStateEvents createAppStateEvents(BaseAppStateEvents baseAppStateEvents) {
        return new UidBatteryStates((UidBatteryStates) baseAppStateEvents);
    }

    @Override // com.android.server.am.BaseAppStateEventsTracker, com.android.server.am.BaseAppStateTracker
    public final void dump(PrintWriter printWriter, String str) {
        ((AppBatteryExemptionPolicy) this.mInjector.mAppStatePolicy).dump(printWriter, str);
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final int getType() {
        return 2;
    }

    public final void onStateChange(int i, int i2, long j, String str, boolean z) {
        int i3;
        boolean z2;
        if (((AppBatteryExemptionPolicy) this.mInjector.mAppStatePolicy).mTrackerEnabled) {
            AppBatteryTracker.ImmutableBatteryUsage uidBatteryUsage = this.mAppRestrictionController.getUidBatteryUsage(i);
            int numberOfTrailingZeros = Integer.numberOfTrailingZeros(i2);
            synchronized (this.mLock) {
                try {
                    SparseArray sparseArray = this.mUidPackageStates.mMap;
                    ArrayMap arrayMap = (ArrayMap) sparseArray.get(i);
                    if (arrayMap == null) {
                        arrayMap = new ArrayMap();
                        sparseArray.put(i, arrayMap);
                    }
                    int indexOfKey = arrayMap.indexOfKey(str);
                    boolean z3 = false;
                    if (indexOfKey >= 0) {
                        i3 = ((Integer) arrayMap.valueAt(indexOfKey)).intValue();
                    } else {
                        arrayMap.put(str, 0);
                        indexOfKey = arrayMap.indexOfKey(str);
                        i3 = 0;
                    }
                    if (z) {
                        int size = arrayMap.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            if ((((Integer) arrayMap.valueAt(size)).intValue() & i2) != 0) {
                                z3 = true;
                                break;
                            }
                            size--;
                        }
                        arrayMap.setValueAt(indexOfKey, Integer.valueOf(i2 | i3));
                        z2 = !z3;
                    } else {
                        int i4 = i3 & (~i2);
                        arrayMap.setValueAt(indexOfKey, Integer.valueOf(i4));
                        int size2 = arrayMap.size() - 1;
                        while (true) {
                            if (size2 < 0) {
                                z3 = true;
                                break;
                            } else if ((((Integer) arrayMap.valueAt(size2)).intValue() & i2) == 0) {
                                size2--;
                            }
                        }
                        if (i4 == 0) {
                            arrayMap.removeAt(indexOfKey);
                            if (arrayMap.size() == 0) {
                                sparseArray.remove(i);
                            }
                        }
                        z2 = z3;
                    }
                    if (z2) {
                        UidBatteryStates uidBatteryStates = (UidBatteryStates) this.mPkgEvents.get(i, "");
                        if (uidBatteryStates == null) {
                            uidBatteryStates = new UidBatteryStates(i, "", 5, (BaseAppStateEventsTracker.BaseAppStateEventsPolicy) this.mInjector.mAppStatePolicy);
                            this.mPkgEvents.put("", i, uidBatteryStates);
                        }
                        uidBatteryStates.addEvent(z, j, uidBatteryUsage, numberOfTrailingZeros);
                    }
                } finally {
                }
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateTracker
    public final void onSystemReady() {
        super.onSystemReady();
        AppRestrictionController appRestrictionController = this.mAppRestrictionController;
        int size = appRestrictionController.mAppStateTrackers.size();
        for (int i = 0; i < size; i++) {
            BaseAppStateTracker baseAppStateTracker = (BaseAppStateTracker) appRestrictionController.mAppStateTrackers.get(i);
            synchronized (baseAppStateTracker.mLock) {
                baseAppStateTracker.mStateListeners.add(this);
            }
        }
    }

    @Override // com.android.server.am.BaseAppStateDurationsTracker, com.android.server.am.BaseAppStateEventsTracker
    public void reset() {
        super.reset();
        synchronized (this.mLock) {
            this.mUidPackageStates.clear();
        }
    }
}
