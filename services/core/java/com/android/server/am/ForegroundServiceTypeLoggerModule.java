package com.android.server.am;

import android.content.pm.ApplicationInfo;
import android.util.ArrayMap;
import android.util.IntArray;
import android.util.LongArray;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.util.FrameworkStatsLog;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ForegroundServiceTypeLoggerModule {
  public final SparseArray mUids = new SparseArray();

  public class UidState {
    public final SparseArray mApiClosedCalls;
    public final SparseArray mApiOpenCalls;
    public final SparseArray mFirstFgsTimeStamp;
    public final SparseArray mLastFgsTimeStamp;
    public final SparseIntArray mOpenWithFgsCount;
    public final SparseIntArray mOpenedWithoutFgsCount;
    public final SparseArray mRunningFgs;

    public UidState() {
      this.mApiOpenCalls = new SparseArray();
      this.mApiClosedCalls = new SparseArray();
      this.mOpenedWithoutFgsCount = new SparseIntArray();
      this.mOpenWithFgsCount = new SparseIntArray();
      this.mRunningFgs = new SparseArray();
      this.mLastFgsTimeStamp = new SparseArray();
      this.mFirstFgsTimeStamp = new SparseArray();
    }
  }

  public void logForegroundServiceStart(int i, int i2, ServiceRecord serviceRecord) {
    UidState uidState = (UidState) this.mUids.get(i);
    if (uidState == null) {
      uidState = new UidState();
      this.mUids.put(i, uidState);
    }
    IntArray convertFgsTypeToApiTypes =
        convertFgsTypeToApiTypes(serviceRecord.foregroundServiceType);
    IntArray intArray = new IntArray();
    LongArray longArray = new LongArray();
    int size = convertFgsTypeToApiTypes.size();
    for (int i3 = 0; i3 < size; i3++) {
      int i4 = convertFgsTypeToApiTypes.get(i3);
      int indexOfKey = uidState.mRunningFgs.indexOfKey(i4);
      if (indexOfKey < 0) {
        uidState.mRunningFgs.put(i4, new ArrayMap());
        indexOfKey = uidState.mRunningFgs.indexOfKey(i4);
        uidState.mFirstFgsTimeStamp.put(i4, Long.valueOf(System.currentTimeMillis()));
      }
      ((ArrayMap) uidState.mRunningFgs.valueAt(indexOfKey))
          .put(serviceRecord.getComponentName(), serviceRecord);
      if (uidState.mApiOpenCalls.contains(i4)) {
        uidState.mOpenWithFgsCount.put(i4, uidState.mOpenedWithoutFgsCount.get(i4));
        uidState.mOpenedWithoutFgsCount.put(i4, 0);
        intArray.add(i4);
        FgsApiRecord fgsApiRecord = (FgsApiRecord) uidState.mApiOpenCalls.get(i4);
        longArray.add(fgsApiRecord.mTimeStart);
        fgsApiRecord.mIsAssociatedWithFgs = true;
        fgsApiRecord.mAssociatedFgsRecord = serviceRecord;
        uidState.mApiOpenCalls.remove(i4);
      }
    }
    if (intArray.size() != 0) {
      int size2 = intArray.size();
      for (int i5 = 0; i5 < size2; i5++) {
        logFgsApiEvent(serviceRecord, 4, 1, intArray.get(i5), longArray.get(i5));
      }
    }
  }

  public void logForegroundServiceStop(int i, ServiceRecord serviceRecord) {
    IntArray convertFgsTypeToApiTypes =
        convertFgsTypeToApiTypes(serviceRecord.foregroundServiceType);
    UidState uidState = (UidState) this.mUids.get(i);
    if (uidState == null) {
      Slog.w(
          "ForegroundServiceTypeLoggerModule",
          "FGS stop call being logged with no start call for UID for UID "
              + i
              + " in package "
              + serviceRecord.packageName);
      return;
    }
    ArrayList arrayList = new ArrayList();
    ArrayList arrayList2 = new ArrayList();
    int size = convertFgsTypeToApiTypes.size();
    for (int i2 = 0; i2 < size; i2++) {
      int i3 = convertFgsTypeToApiTypes.get(i2);
      int indexOfKey = uidState.mOpenWithFgsCount.indexOfKey(i3);
      if (indexOfKey < 0) {
        Slog.w(
            "ForegroundServiceTypeLoggerModule",
            "Logger should be tracking FGS types correctly for UID "
                + i
                + " in package "
                + serviceRecord.packageName);
      } else {
        FgsApiRecord fgsApiRecord = (FgsApiRecord) uidState.mApiClosedCalls.get(i3);
        if (fgsApiRecord != null && uidState.mOpenWithFgsCount.valueAt(indexOfKey) == 0) {
          arrayList.add(Integer.valueOf(i3));
          arrayList2.add(Long.valueOf(fgsApiRecord.mTimeStart));
          uidState.mApiClosedCalls.remove(i3);
        }
        ArrayMap arrayMap = (ArrayMap) uidState.mRunningFgs.get(i3);
        if (arrayMap == null) {
          Slog.w(
              "ForegroundServiceTypeLoggerModule",
              "Could not find appropriate running FGS for FGS stop for UID "
                  + i
                  + " in package "
                  + serviceRecord.packageName);
        } else {
          arrayMap.remove(serviceRecord.getComponentName());
          if (arrayMap.size() == 0) {
            uidState.mRunningFgs.remove(i3);
            uidState.mLastFgsTimeStamp.put(i3, Long.valueOf(System.currentTimeMillis()));
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

  public long logForegroundServiceApiEventBegin(int i, int i2, int i3, String str) {
    FgsApiRecord fgsApiRecord = new FgsApiRecord(i2, i3, str, i, System.currentTimeMillis());
    UidState uidState = (UidState) this.mUids.get(i2);
    if (uidState == null) {
      uidState = new UidState();
      this.mUids.put(i2, uidState);
    }
    if (!hasValidActiveFgs(i2, i)) {
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
      return fgsApiRecord.mTimeStart;
    }
    int indexOfKey2 = uidState.mOpenWithFgsCount.indexOfKey(i);
    if (indexOfKey2 < 0) {
      uidState.mOpenWithFgsCount.put(i, 0);
      indexOfKey2 = uidState.mOpenWithFgsCount.indexOfKey(i);
    }
    SparseIntArray sparseIntArray2 = uidState.mOpenWithFgsCount;
    sparseIntArray2.put(i, sparseIntArray2.valueAt(indexOfKey2) + 1);
    ArrayMap arrayMap = (ArrayMap) uidState.mRunningFgs.get(i);
    long j = fgsApiRecord.mTimeStart;
    if (uidState.mOpenWithFgsCount.valueAt(indexOfKey2) == 1) {
      Iterator it = arrayMap.values().iterator();
      while (it.hasNext()) {
        logFgsApiEvent((ServiceRecord) it.next(), 4, 1, i, j);
      }
    }
    return fgsApiRecord.mTimeStart;
  }

  public long logForegroundServiceApiEventEnd(int i, int i2, int i3) {
    UidState uidState = (UidState) this.mUids.get(i2);
    if (uidState == null) {
      Slog.w("ForegroundServiceTypeLoggerModule", "API event end called before start!");
      return -1L;
    }
    int indexOfKey = uidState.mOpenWithFgsCount.indexOfKey(i);
    if (indexOfKey >= 0) {
      if (uidState.mOpenWithFgsCount.get(i) != 0) {
        uidState.mOpenWithFgsCount.put(i, r2.get(i) - 1);
      }
      if (!hasValidActiveFgs(i2, i) && uidState.mOpenWithFgsCount.get(i) == 0) {
        long currentTimeMillis = System.currentTimeMillis();
        logFgsApiEventWithNoFgs(i2, 3, i, currentTimeMillis);
        uidState.mOpenWithFgsCount.removeAt(indexOfKey);
        return currentTimeMillis;
      }
    }
    if (uidState.mOpenedWithoutFgsCount.indexOfKey(i) < 0) {
      uidState.mOpenedWithoutFgsCount.put(i, 0);
    }
    if (uidState.mOpenedWithoutFgsCount.get(i) != 0) {
      uidState.mOpenedWithoutFgsCount.put(i, r11.get(i) - 1);
      return System.currentTimeMillis();
    }
    FgsApiRecord fgsApiRecord = new FgsApiRecord(i2, i3, "", i, System.currentTimeMillis());
    uidState.mApiClosedCalls.put(i, fgsApiRecord);
    return fgsApiRecord.mTimeStart;
  }

  public void logForegroundServiceApiStateChanged(int i, int i2, int i3, int i4) {
    UidState uidState = (UidState) this.mUids.get(i2);
    if (uidState.mRunningFgs.contains(i)) {
      ArrayMap arrayMap = (ArrayMap) uidState.mRunningFgs.get(i);
      long currentTimeMillis = System.currentTimeMillis();
      Iterator it = arrayMap.values().iterator();
      while (it.hasNext()) {
        logFgsApiEvent((ServiceRecord) it.next(), 4, i4, i, currentTimeMillis);
      }
    }
  }

  public final IntArray convertFgsTypeToApiTypes(int i) {
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

  public final boolean hasValidActiveFgs(int i, int i2) {
    UidState uidState = (UidState) this.mUids.get(i);
    if (uidState != null) {
      return uidState.mRunningFgs.contains(i2);
    }
    return false;
  }

  public void logFgsApiEvent(ServiceRecord serviceRecord, int i, int i2, int i3, long j) {
    long j2 = serviceRecord.createRealTime - j;
    long j3 = j - serviceRecord.mFgsExitTime;
    UidState uidState = (UidState) this.mUids.get(serviceRecord.appInfo.uid);
    if (uidState != null) {
      if (uidState.mFirstFgsTimeStamp.contains(i3)) {
        j2 = ((Long) uidState.mFirstFgsTimeStamp.get(i3)).longValue() - j;
      }
      if (uidState.mLastFgsTimeStamp.contains(i3)) {
        j3 = j - ((Long) uidState.mLastFgsTimeStamp.get(i3)).longValue();
      }
    }
    long j4 = j2;
    long j5 = j3;
    int[] iArr = {i3};
    long[] jArr = {j};
    ApplicationInfo applicationInfo = serviceRecord.appInfo;
    int i4 = applicationInfo.uid;
    String str = serviceRecord.shortInstanceName;
    boolean z = serviceRecord.mAllowWhileInUsePermissionInFgs;
    int i5 = serviceRecord.mAllowStartForeground;
    int i6 = applicationInfo.targetSdkVersion;
    int i7 = serviceRecord.mRecentCallingUid;
    ActivityManagerService.FgsTempAllowListItem fgsTempAllowListItem =
        serviceRecord.mInfoTempFgsAllowListReason;
    int i8 = fgsTempAllowListItem != null ? fgsTempAllowListItem.mCallingUid : -1;
    boolean z2 = serviceRecord.mFgsNotificationWasDeferred;
    boolean z3 = serviceRecord.mFgsNotificationShown;
    int i9 = serviceRecord.mStartForegroundCount;
    int hashComponentNameForAtom = ActivityManagerUtils.hashComponentNameForAtom(str);
    boolean z4 = serviceRecord.mFgsHasNotificationPermission;
    int i10 = serviceRecord.foregroundServiceType;
    boolean z5 = serviceRecord.mIsFgsDelegate;
    ForegroundServiceDelegation foregroundServiceDelegation = serviceRecord.mFgsDelegation;
    FrameworkStatsLog.write(
        60,
        i4,
        str,
        i,
        z,
        i5,
        i6,
        i7,
        0,
        i8,
        z2,
        z3,
        0,
        i9,
        hashComponentNameForAtom,
        z4,
        i10,
        0,
        z5,
        foregroundServiceDelegation != null ? foregroundServiceDelegation.mOptions.mClientUid : -1,
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
        j4,
        j5);
  }

  public void logFgsApiEventWithNoFgs(int i, int i2, int i3, long j) {
    UidState uidState = (UidState) this.mUids.get(i);
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
        (uidState == null || !uidState.mLastFgsTimeStamp.contains(i3))
            ? 0L
            : j - ((Long) uidState.mLastFgsTimeStamp.get(i3)).longValue());
  }

  public class FgsApiRecord {
    public ServiceRecord mAssociatedFgsRecord;
    public boolean mIsAssociatedWithFgs;
    public final String mPackageName;
    public final int mPid;
    public final long mTimeStart;
    public int mType;
    public final int mUid;

    public FgsApiRecord(int i, int i2, String str, int i3, long j) {
      this.mUid = i;
      this.mPid = i2;
      this.mPackageName = str;
      this.mType = i3;
      this.mTimeStart = j;
    }
  }
}
