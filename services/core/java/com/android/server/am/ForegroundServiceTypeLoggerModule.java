package com.android.server.am;

import android.os.Trace;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.LongArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.BrailleDisplayConnection$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ForegroundServiceTypeLoggerModule {
    public final SparseArray mUids = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FgsApiRecord {
        public final long mTimeStart;

        public FgsApiRecord(long j) {
            this.mTimeStart = j;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class UidState {
        public final SparseArray mApiOpenCalls = new SparseArray();
        public final SparseArray mApiClosedCalls = new SparseArray();
        public final SparseIntArray mOpenedWithoutFgsCount = new SparseIntArray();
        public final SparseIntArray mOpenWithFgsCount = new SparseIntArray();
        public final SparseArray mRunningFgs = new SparseArray();
        public final SparseArray mLastFgsTimeStamp = new SparseArray();
        public final SparseArray mFirstFgsTimeStamp = new SparseArray();
    }

    public static IntArray convertFgsTypeToApiTypes(int i) {
        IntArray intArray = new IntArray();
        if ((i & 64) == 64) {
            intArray.add(1);
        }
        if ((i & 16) == 16) {
            intArray.add(2);
            intArray.add(8);
            intArray.add(9);
        }
        if ((i & 8) == 8) {
            intArray.add(3);
        }
        if ((i & 2) == 2) {
            intArray.add(5);
            intArray.add(4);
        }
        if ((i & 128) == 128) {
            intArray.add(6);
        }
        if ((i & 4) == 4) {
            intArray.add(7);
        }
        return intArray;
    }

    public void logFgsApiEvent(ServiceRecord serviceRecord, int i, int i2, int i3, long j) {
        UidState uidState = (UidState) this.mUids.get(serviceRecord.appInfo.uid);
        if (uidState == null) {
            return;
        }
        long longValue =
                uidState.mFirstFgsTimeStamp.contains(i3)
                        ? ((Long) uidState.mFirstFgsTimeStamp.get(i3)).longValue() - j
                        : 0L;
        long longValue2 =
                uidState.mLastFgsTimeStamp.contains(i3)
                        ? j - ((Long) uidState.mLastFgsTimeStamp.get(i3)).longValue()
                        : 0L;
        int[] iArr = {i3};
        long[] jArr = {j};
        int i4 = serviceRecord.appInfo.uid;
        String str = serviceRecord.shortInstanceName;
        boolean isFgsAllowedWiu_forCapabilities = serviceRecord.isFgsAllowedWiu_forCapabilities();
        int fgsAllowStart = serviceRecord.getFgsAllowStart();
        int i5 = serviceRecord.appInfo.targetSdkVersion;
        int i6 = serviceRecord.mRecentCallingUid;
        ActivityManagerService.FgsTempAllowListItem fgsTempAllowListItem =
                serviceRecord.mInfoTempFgsAllowListReason;
        int i7 = fgsTempAllowListItem != null ? fgsTempAllowListItem.mCallingUid : -1;
        boolean z = serviceRecord.mFgsNotificationWasDeferred;
        boolean z2 = serviceRecord.mFgsNotificationShown;
        int i8 = serviceRecord.mStartForegroundCount;
        boolean z3 = serviceRecord.mFgsHasNotificationPermission;
        int i9 = serviceRecord.foregroundServiceType;
        boolean z4 = serviceRecord.mIsFgsDelegate;
        ForegroundServiceDelegation foregroundServiceDelegation = serviceRecord.mFgsDelegation;
        FrameworkStatsLog.write(
                60,
                i4,
                str,
                i,
                isFgsAllowedWiu_forCapabilities,
                fgsAllowStart,
                i5,
                i6,
                0,
                i7,
                z,
                z2,
                0,
                i8,
                0,
                z3,
                i9,
                0,
                z4,
                foregroundServiceDelegation != null
                        ? foregroundServiceDelegation.mOptions.mClientUid
                        : -1,
                foregroundServiceDelegation != null
                        ? foregroundServiceDelegation.mOptions.mDelegationService
                        : 0,
                i2,
                iArr,
                jArr,
                -1,
                0,
                -1,
                0,
                longValue,
                longValue2,
                serviceRecord.mAllowWiu_noBinding,
                serviceRecord.mAllowWiu_inBindService,
                serviceRecord.mAllowWiu_byBindings,
                serviceRecord.mAllowStart_noBinding,
                serviceRecord.mAllowStart_inBindService,
                serviceRecord.mAllowStart_byBindings,
                0,
                false);
    }

    public void logFgsApiEventWithNoFgs(int i, int i2, int i3, long j) {
        UidState uidState = (UidState) this.mUids.get(i);
        if (uidState == null) {
            return;
        }
        FrameworkStatsLog.write(
                60,
                i,
                null,
                4,
                false,
                0,
                0,
                i,
                0,
                0,
                false,
                false,
                0,
                0,
                0,
                false,
                0,
                0,
                false,
                0,
                0,
                i2,
                new int[] {i3},
                new long[] {j},
                -1,
                0,
                -1,
                0,
                0L,
                uidState.mLastFgsTimeStamp.contains(i3)
                        ? j - ((Long) uidState.mLastFgsTimeStamp.get(i3)).longValue()
                        : 0L,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                false);
    }

    public final void logForegroundServiceApiEventBegin(int i, int i2, int i3) {
        long currentTimeMillis = System.currentTimeMillis();
        FgsApiRecord fgsApiRecord = new FgsApiRecord(currentTimeMillis);
        UidState uidState = (UidState) this.mUids.get(i2);
        if (uidState == null) {
            uidState = new UidState();
            this.mUids.put(i2, uidState);
        }
        UidState uidState2 = (UidState) this.mUids.get(i2);
        if (!(uidState2 != null ? uidState2.mRunningFgs.contains(i) : false)) {
            int indexOfKey = uidState.mOpenedWithoutFgsCount.indexOfKey(i);
            if (indexOfKey < 0) {
                uidState.mOpenedWithoutFgsCount.put(i, 0);
                indexOfKey = uidState.mOpenedWithoutFgsCount.indexOfKey(i);
            }
            if (!uidState.mApiOpenCalls.contains(i)
                    || uidState.mOpenedWithoutFgsCount.valueAt(indexOfKey) == 0) {
                uidState.mApiOpenCalls.put(i, fgsApiRecord);
            }
            SparseIntArray sparseIntArray = uidState.mOpenedWithoutFgsCount;
            sparseIntArray.put(i, sparseIntArray.get(i) + 1);
            return;
        }
        int indexOfKey2 = uidState.mOpenWithFgsCount.indexOfKey(i);
        if (indexOfKey2 < 0) {
            uidState.mOpenWithFgsCount.put(i, 0);
            indexOfKey2 = uidState.mOpenWithFgsCount.indexOfKey(i);
        }
        SparseIntArray sparseIntArray2 = uidState.mOpenWithFgsCount;
        sparseIntArray2.put(i, sparseIntArray2.valueAt(indexOfKey2) + 1);
        ArrayMap arrayMap = (ArrayMap) uidState.mRunningFgs.get(i);
        if (uidState.mOpenWithFgsCount.valueAt(indexOfKey2) == 1) {
            Iterator it = arrayMap.values().iterator();
            while (it.hasNext()) {
                logFgsApiEvent((ServiceRecord) it.next(), 4, 1, i, currentTimeMillis);
            }
        }
    }

    public final void logForegroundServiceApiEventEnd(int i, int i2, int i3) {
        UidState uidState = (UidState) this.mUids.get(i2);
        if (uidState == null) {
            Slog.w("ForegroundServiceTypeLoggerModule", "API event end called before start!");
            return;
        }
        int indexOfKey = uidState.mOpenWithFgsCount.indexOfKey(i);
        if (indexOfKey >= 0) {
            if (uidState.mOpenWithFgsCount.get(i) != 0) {
                uidState.mOpenWithFgsCount.put(i, r2.get(i) - 1);
            }
            UidState uidState2 = (UidState) this.mUids.get(i2);
            if (!(uidState2 != null ? uidState2.mRunningFgs.contains(i) : false)
                    && uidState.mOpenWithFgsCount.get(i) == 0) {
                logFgsApiEventWithNoFgs(i2, 3, i, System.currentTimeMillis());
                uidState.mOpenWithFgsCount.removeAt(indexOfKey);
                return;
            }
        }
        if (uidState.mOpenedWithoutFgsCount.indexOfKey(i) < 0) {
            uidState.mOpenedWithoutFgsCount.put(i, 0);
        }
        int i4 = uidState.mOpenedWithoutFgsCount.get(i);
        if (i4 == 0) {
            uidState.mApiClosedCalls.put(i, new FgsApiRecord(System.currentTimeMillis()));
            return;
        }
        int i5 = i4 - 1;
        if (i5 == 0) {
            uidState.mApiOpenCalls.remove(i);
        }
        uidState.mOpenedWithoutFgsCount.put(i, i5);
        System.currentTimeMillis();
    }

    public final void logForegroundServiceApiStateChanged(int i, int i2, int i3) {
        UidState uidState = (UidState) this.mUids.get(i2);
        if (uidState.mRunningFgs.contains(i)) {
            ArrayMap arrayMap = (ArrayMap) uidState.mRunningFgs.get(i);
            long currentTimeMillis = System.currentTimeMillis();
            Iterator it = arrayMap.values().iterator();
            while (it.hasNext()) {
                logFgsApiEvent((ServiceRecord) it.next(), 4, i3, i, currentTimeMillis);
            }
        }
    }

    public final void logForegroundServiceStart(int i, ServiceRecord serviceRecord) {
        if (serviceRecord.name != null) {
            Trace.asyncTraceForTrackBegin(
                    64L,
                    serviceRecord.name.flattenToString() + ":" + i,
                    "foregroundService",
                    serviceRecord.foregroundServiceType);
        }
        UidState uidState = (UidState) this.mUids.get(i);
        if (uidState == null) {
            uidState = new UidState();
            this.mUids.put(i, uidState);
        }
        IntArray convertFgsTypeToApiTypes =
                convertFgsTypeToApiTypes(serviceRecord.foregroundServiceType);
        if (convertFgsTypeToApiTypes.size() == 0) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    i,
                    "Foreground service start for UID: ",
                    " does not have any types",
                    "ForegroundServiceTypeLoggerModule");
        }
        IntArray intArray = new IntArray();
        LongArray longArray = new LongArray();
        int size = convertFgsTypeToApiTypes.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = convertFgsTypeToApiTypes.get(i2);
            int indexOfKey = uidState.mRunningFgs.indexOfKey(i3);
            if (indexOfKey < 0) {
                uidState.mRunningFgs.put(i3, new ArrayMap());
                indexOfKey = uidState.mRunningFgs.indexOfKey(i3);
                uidState.mFirstFgsTimeStamp.put(i3, Long.valueOf(System.currentTimeMillis()));
            }
            ((ArrayMap) uidState.mRunningFgs.valueAt(indexOfKey))
                    .put(serviceRecord.name, serviceRecord);
            if (uidState.mApiOpenCalls.contains(i3)) {
                uidState.mOpenWithFgsCount.put(i3, uidState.mOpenedWithoutFgsCount.get(i3));
                uidState.mOpenedWithoutFgsCount.put(i3, 0);
                intArray.add(i3);
                longArray.add(((FgsApiRecord) uidState.mApiOpenCalls.get(i3)).mTimeStart);
                uidState.mApiOpenCalls.remove(i3);
            }
        }
        if (intArray.size() != 0) {
            int size2 = intArray.size();
            for (int i4 = 0; i4 < size2; i4++) {
                logFgsApiEvent(serviceRecord, 4, 1, intArray.get(i4), longArray.get(i4));
            }
        }
    }

    public final void logForegroundServiceStop(int i, ServiceRecord serviceRecord) {
        if (serviceRecord.name != null) {
            Trace.asyncTraceForTrackEnd(
                    64L, serviceRecord.name.flattenToString() + ":" + i, serviceRecord.hashCode());
        }
        IntArray convertFgsTypeToApiTypes =
                convertFgsTypeToApiTypes(serviceRecord.foregroundServiceType);
        UidState uidState = (UidState) this.mUids.get(i);
        if (convertFgsTypeToApiTypes.size() == 0) {
            BrailleDisplayConnection$$ExternalSyntheticOutline0.m(
                    i,
                    "FGS stop call for: ",
                    " has no types!",
                    "ForegroundServiceTypeLoggerModule");
        }
        if (uidState == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i,
                            "FGS stop call being logged with no start call for UID for UID ",
                            " in package "),
                    serviceRecord.packageName,
                    "ForegroundServiceTypeLoggerModule");
            return;
        }
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        int size = convertFgsTypeToApiTypes.size();
        for (int i2 = 0; i2 < size; i2++) {
            int i3 = convertFgsTypeToApiTypes.get(i2);
            ArrayMap arrayMap = (ArrayMap) uidState.mRunningFgs.get(i3);
            if (arrayMap == null) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i,
                                "Could not find appropriate running FGS for FGS stop for UID ",
                                " in package "),
                        serviceRecord.packageName,
                        "ForegroundServiceTypeLoggerModule");
            } else {
                arrayMap.remove(serviceRecord.name);
                if (arrayMap.size() == 0) {
                    uidState.mRunningFgs.remove(i3);
                    uidState.mLastFgsTimeStamp.put(i3, Long.valueOf(System.currentTimeMillis()));
                }
                int indexOfKey = uidState.mOpenWithFgsCount.indexOfKey(i3);
                if (indexOfKey < 0) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    i,
                                    "Logger should be tracking FGS types correctly for UID ",
                                    " in package "),
                            serviceRecord.packageName,
                            "ForegroundServiceTypeLoggerModule");
                } else {
                    FgsApiRecord fgsApiRecord = (FgsApiRecord) uidState.mApiClosedCalls.get(i3);
                    if (fgsApiRecord != null
                            && uidState.mOpenWithFgsCount.valueAt(indexOfKey) == 0) {
                        arrayList.add(Integer.valueOf(i3));
                        arrayList2.add(Long.valueOf(fgsApiRecord.mTimeStart));
                        uidState.mApiClosedCalls.remove(i3);
                    }
                }
            }
        }
        if (arrayList.isEmpty()) {
            return;
        }
        for (int i4 = 0; i4 < arrayList.size(); i4++) {
            logFgsApiEvent(
                    serviceRecord,
                    4,
                    2,
                    ((Integer) arrayList.get(i4)).intValue(),
                    ((Long) arrayList2.get(i4)).longValue());
        }
    }
}
