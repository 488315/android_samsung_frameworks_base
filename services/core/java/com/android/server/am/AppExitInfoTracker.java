package com.android.server.am;

import android.app.ActivityManager;
import android.app.ApplicationExitInfo;
import android.app.IAppTraceRetriever;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.Process;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Pair;
import android.util.Pools;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoInputStream;

import com.android.internal.app.ProcessMap;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ExplicitHealthCheckController$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;

import com.samsung.android.rune.CoreRune;
import com.sec.tmodiagnostics.DeviceReportingSecurityChecker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.zip.GZIPOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppExitInfoTracker {
    static final String APP_EXIT_INFO_FILE = "procexitinfo";
    public static final long APP_EXIT_INFO_PERSIST_INTERVAL = TimeUnit.MINUTES.toMillis(30);
    public static final long APP_EXIT_INFO_STATSD_LOG_DEBOUNCE = TimeUnit.SECONDS.toMillis(15);
    static final String APP_EXIT_STORE_DIR = "procexitstore";
    public int mAppExitInfoHistoryListSize;
    public KillHandler mKillHandler;
    File mProcExitInfoFile;
    File mProcExitStoreDir;
    public ActivityManagerService mService;
    public final Object mLock = new Object();
    public AppExitInfoTracker$$ExternalSyntheticLambda2 mAppExitInfoPersistTask = null;
    public long mLastAppExitInfoPersistTimestamp = 0;
    AtomicBoolean mAppExitInfoLoaded = new AtomicBoolean();
    public final ArrayList mTmpInfoList = new ArrayList();
    public final ArrayList mTmpInfoList2 = new ArrayList();
    public final IsolatedUidRecords mIsolatedUidRecords = new IsolatedUidRecords();
    public final AppExitInfoExternalSource mAppExitInfoSourceZygote =
            new AppExitInfoExternalSource(null);
    public final AppExitInfoExternalSource mAppExitInfoSourceLmkd =
            new AppExitInfoExternalSource(3);
    public final SparseArray mActiveAppStateSummary = new SparseArray();
    public final SparseArray mActiveAppTraces = new SparseArray();
    public final AppTraceRetriever mAppTraceRetriever = new AppTraceRetriever();
    public final ProcessMap mData = new ProcessMap();
    public final Pools.SynchronizedPool mRawRecordsPool = new Pools.SynchronizedPool(8);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppExitInfoContainer {
        public final int mMaxCapacity;
        public int mUid;
        public final SparseArray mInfos = new SparseArray();
        public final SparseArray mRecoverableCrashes = new SparseArray();

        public AppExitInfoContainer(int i) {
            this.mMaxCapacity = i;
        }

        public static void destroyLocked(SparseArray sparseArray) {
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                ApplicationExitInfo applicationExitInfo =
                        (ApplicationExitInfo) sparseArray.valueAt(size);
                File traceFile = applicationExitInfo.getTraceFile();
                if (traceFile != null) {
                    traceFile.delete();
                }
                applicationExitInfo.setTraceFile(null);
                applicationExitInfo.setAppTraceRetriever(null);
            }
        }

        public final void addInfoLocked(
                SparseArray sparseArray, ApplicationExitInfo applicationExitInfo) {
            int size = sparseArray.size();
            if (size >= this.mMaxCapacity) {
                int i = -1;
                long j = Long.MAX_VALUE;
                for (int i2 = 0; i2 < size; i2++) {
                    ApplicationExitInfo applicationExitInfo2 =
                            (ApplicationExitInfo) sparseArray.valueAt(i2);
                    if (applicationExitInfo2.getTimestamp() < j) {
                        j = applicationExitInfo2.getTimestamp();
                        i = i2;
                    }
                }
                if (i >= 0) {
                    File traceFile = ((ApplicationExitInfo) sparseArray.valueAt(i)).getTraceFile();
                    if (traceFile != null) {
                        traceFile.delete();
                    }
                    sparseArray.removeAt(i);
                }
            }
            int packageUid = applicationExitInfo.getPackageUid();
            if (Process.isSdkSandboxUid(applicationExitInfo.getRealUid())) {
                packageUid = applicationExitInfo.getRealUid();
            }
            int pid = applicationExitInfo.getPid();
            byte[] processStateSummary = applicationExitInfo.getProcessStateSummary();
            AppExitInfoTracker appExitInfoTracker = AppExitInfoTracker.this;
            if (processStateSummary == null) {
                applicationExitInfo.setProcessStateSummary(
                        (byte[])
                                AppExitInfoTracker.m172$$Nest$smfindAndRemoveFromSparse2dArray(
                                        packageUid,
                                        pid,
                                        appExitInfoTracker.mActiveAppStateSummary));
            }
            if (applicationExitInfo.getTraceFile() == null) {
                applicationExitInfo.setTraceFile(
                        (File)
                                AppExitInfoTracker.m172$$Nest$smfindAndRemoveFromSparse2dArray(
                                        packageUid, pid, appExitInfoTracker.mActiveAppTraces));
            }
            applicationExitInfo.setAppTraceRetriever(appExitInfoTracker.mAppTraceRetriever);
            sparseArray.append(pid, applicationExitInfo);
        }

        public final void getExitInfoLocked(int i, int i2, ArrayList arrayList) {
            SparseArray sparseArray = this.mInfos;
            if (i > 0) {
                ApplicationExitInfo applicationExitInfo = (ApplicationExitInfo) sparseArray.get(i);
                if (applicationExitInfo != null) {
                    arrayList.add(applicationExitInfo);
                    return;
                }
                return;
            }
            int size = sparseArray.size();
            int i3 = 0;
            if (i2 <= 0 || size <= i2) {
                while (i3 < size) {
                    arrayList.add((ApplicationExitInfo) sparseArray.valueAt(i3));
                    i3++;
                }
                Collections.sort(arrayList, new AppExitInfoTracker$$ExternalSyntheticLambda1(1));
                return;
            }
            if (i2 == 1) {
                ApplicationExitInfo applicationExitInfo2 =
                        (ApplicationExitInfo) sparseArray.valueAt(0);
                for (int i4 = 1; i4 < size; i4++) {
                    ApplicationExitInfo applicationExitInfo3 =
                            (ApplicationExitInfo) sparseArray.valueAt(i4);
                    if (applicationExitInfo2.getTimestamp() < applicationExitInfo3.getTimestamp()) {
                        applicationExitInfo2 = applicationExitInfo3;
                    }
                }
                arrayList.add(applicationExitInfo2);
                return;
            }
            ArrayList arrayList2 = AppExitInfoTracker.this.mTmpInfoList2;
            arrayList2.clear();
            for (int i5 = 0; i5 < size; i5++) {
                arrayList2.add((ApplicationExitInfo) sparseArray.valueAt(i5));
            }
            Collections.sort(arrayList2, new AppExitInfoTracker$$ExternalSyntheticLambda1(2));
            while (i3 < i2) {
                arrayList.add((ApplicationExitInfo) arrayList2.get(i3));
                i3++;
            }
            arrayList2.clear();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AppExitInfoExternalSource {
        public final SparseArray mData = new SparseArray();
        public final Integer mPresetReason;

        public AppExitInfoExternalSource(Integer num) {
            this.mPresetReason = num;
        }

        public final void addLocked(int i, int i2, Object obj) {
            Integer uidByIsolatedUid =
                    AppExitInfoTracker.this.mIsolatedUidRecords.getUidByIsolatedUid(i2);
            if (uidByIsolatedUid != null) {
                i2 = uidByIsolatedUid.intValue();
            }
            SparseArray sparseArray = (SparseArray) this.mData.get(i2);
            if (sparseArray == null) {
                sparseArray = new SparseArray();
                this.mData.put(i2, sparseArray);
            }
            sparseArray.put(i, new Pair(Long.valueOf(System.currentTimeMillis()), obj));
        }

        public final void onProcDied(final int i, int i2, final Integer num) {
            AppExitInfoTracker appExitInfoTracker = AppExitInfoTracker.this;
            if (appExitInfoTracker.mService == null) {
                return;
            }
            synchronized (appExitInfoTracker.mLock) {
                final AppExitInfoTracker appExitInfoTracker2 = AppExitInfoTracker.this;
                final Integer num2 = this.mPresetReason;
                Integer uidByIsolatedUid =
                        appExitInfoTracker2.mIsolatedUidRecords.getUidByIsolatedUid(i2);
                final int intValue = uidByIsolatedUid != null ? uidByIsolatedUid.intValue() : i2;
                final ArrayList arrayList = appExitInfoTracker2.mTmpInfoList;
                arrayList.clear();
                appExitInfoTracker2.forEachPackageLocked(
                        new BiFunction() { // from class:
                                           // com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda15
                            @Override // java.util.function.BiFunction
                            public final Object apply(Object obj, Object obj2) {
                                AppExitInfoTracker appExitInfoTracker3 = AppExitInfoTracker.this;
                                int i3 = intValue;
                                ArrayList arrayList2 = arrayList;
                                int i4 = i;
                                Integer num3 = num;
                                Integer num4 = num2;
                                appExitInfoTracker3.getClass();
                                AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer =
                                        (AppExitInfoTracker.AppExitInfoContainer)
                                                ((SparseArray) obj2).get(i3);
                                if (appExitInfoContainer == null) {
                                    return 0;
                                }
                                arrayList2.clear();
                                appExitInfoContainer.getExitInfoLocked(i4, 1, arrayList2);
                                if (arrayList2.size() == 0) {
                                    return 0;
                                }
                                ApplicationExitInfo applicationExitInfo =
                                        (ApplicationExitInfo) arrayList2.get(0);
                                if (applicationExitInfo.getRealUid() != i3) {
                                    arrayList2.clear();
                                    return 0;
                                }
                                appExitInfoTracker3.updateExistingExitInfoRecordLocked(
                                        applicationExitInfo, num3, num4);
                                return 2;
                            }
                        });
                if (arrayList.size() <= 0) {
                    addLocked(i, i2, num);
                }
            }
        }

        public Pair remove(int i, int i2) {
            Pair pair;
            synchronized (AppExitInfoTracker.this.mLock) {
                try {
                    Integer uidByIsolatedUid =
                            AppExitInfoTracker.this.mIsolatedUidRecords.getUidByIsolatedUid(i2);
                    if (uidByIsolatedUid != null) {
                        i2 = uidByIsolatedUid.intValue();
                    }
                    SparseArray sparseArray = (SparseArray) this.mData.get(i2);
                    if (sparseArray == null || (pair = (Pair) sparseArray.get(i)) == null) {
                        return null;
                    }
                    sparseArray.remove(i);
                    return AppExitInfoTracker.this.isFresh(((Long) pair.first).longValue())
                            ? pair
                            : null;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removeByUidLocked(int i, boolean z) {
            Integer uidByIsolatedUid;
            if (UserHandle.isIsolated(i)
                    && (uidByIsolatedUid =
                                    AppExitInfoTracker.this.mIsolatedUidRecords.getUidByIsolatedUid(
                                            i))
                            != null) {
                i = uidByIsolatedUid.intValue();
            }
            if (!z) {
                this.mData.remove(i);
                return;
            }
            int appId = UserHandle.getAppId(i);
            for (int size = this.mData.size() - 1; size >= 0; size--) {
                if (UserHandle.getAppId(this.mData.keyAt(size)) == appId) {
                    this.mData.removeAt(size);
                }
            }
        }

        public final void removeByUserId(int i) {
            if (i == -2) {
                i = AppExitInfoTracker.this.mService.mUserController.getCurrentUserId();
            }
            synchronized (AppExitInfoTracker.this.mLock) {
                try {
                    if (i == -1) {
                        this.mData.clear();
                        return;
                    }
                    for (int size = this.mData.size() - 1; size >= 0; size--) {
                        if (UserHandle.getUserId(this.mData.keyAt(size)) == i) {
                            this.mData.removeAt(size);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class AppTraceRetriever extends IAppTraceRetriever.Stub {
        public AppTraceRetriever() {}

        public final ParcelFileDescriptor getTraceFileDescriptor(String str, int i, int i2) {
            AppExitInfoTracker.this.mService.getClass();
            ActivityManagerService.enforceNotIsolatedCaller("getTraceFileDescriptor");
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("Invalid package name");
            }
            int callingPid = Binder.getCallingPid();
            int callingUid = Binder.getCallingUid();
            int userId = UserHandle.getUserId(i);
            AppExitInfoTracker.this.mService.mUserController.handleIncomingUser(
                    callingPid, callingUid, userId, true, 0, "getTraceFileDescriptor", null);
            int enforceDumpPermissionForPackage =
                    AppExitInfoTracker.this.mService.enforceDumpPermissionForPackage(
                            userId, callingUid, str, "getTraceFileDescriptor");
            if (enforceDumpPermissionForPackage == -1) {
                return null;
            }
            synchronized (AppExitInfoTracker.this.mLock) {
                try {
                    ApplicationExitInfo exitInfoLocked =
                            AppExitInfoTracker.this.getExitInfoLocked(
                                    enforceDumpPermissionForPackage, i2, str);
                    if (exitInfoLocked == null) {
                        return null;
                    }
                    File traceFile = exitInfoLocked.getTraceFile();
                    if (traceFile == null) {
                        return null;
                    }
                    long clearCallingIdentity = Binder.clearCallingIdentity();
                    try {
                        return ParcelFileDescriptor.open(traceFile, 268435456);
                    } catch (FileNotFoundException unused) {
                        return null;
                    } finally {
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IsolatedUidRecords {
        public final SparseArray mUidToIsolatedUidMap = new SparseArray();
        public final SparseArray mIsolatedUidToUidMap = new SparseArray();

        public IsolatedUidRecords() {}

        public final Integer getUidByIsolatedUid(int i) {
            Integer num;
            if (!UserHandle.isIsolated(i)) {
                return Integer.valueOf(i);
            }
            synchronized (AppExitInfoTracker.this.mLock) {
                num = (Integer) this.mIsolatedUidToUidMap.get(i);
            }
            return num;
        }

        public void removeAppUid(int i, boolean z) {
            synchronized (AppExitInfoTracker.this.mLock) {
                try {
                    if (z) {
                        int appId = UserHandle.getAppId(i);
                        for (int size = this.mUidToIsolatedUidMap.size() - 1; size >= 0; size--) {
                            int keyAt = this.mUidToIsolatedUidMap.keyAt(size);
                            if (appId == UserHandle.getAppId(keyAt)) {
                                removeAppUidLocked(keyAt);
                            }
                            this.mUidToIsolatedUidMap.removeAt(size);
                        }
                    } else {
                        removeAppUidLocked(i);
                        this.mUidToIsolatedUidMap.remove(i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void removeAppUidLocked(int i) {
            ArraySet arraySet = (ArraySet) this.mUidToIsolatedUidMap.get(i);
            if (arraySet != null) {
                for (int size = arraySet.size() - 1; size >= 0; size--) {
                    this.mIsolatedUidToUidMap.remove(
                            ((Integer) arraySet.removeAt(size)).intValue());
                }
            }
        }

        public final void removeIsolatedUid(int i, int i2) {
            synchronized (AppExitInfoTracker.this.mLock) {
                try {
                    int indexOfKey = this.mUidToIsolatedUidMap.indexOfKey(i2);
                    if (indexOfKey >= 0) {
                        ArraySet arraySet =
                                (ArraySet) this.mUidToIsolatedUidMap.valueAt(indexOfKey);
                        arraySet.remove(Integer.valueOf(i));
                        if (arraySet.isEmpty()) {
                            this.mUidToIsolatedUidMap.removeAt(indexOfKey);
                        }
                    }
                    this.mIsolatedUidToUidMap.remove(i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class KillHandler extends Handler {
        public KillHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            switch (message.what) {
                case 4101:
                    AppExitInfoTracker.this.mAppExitInfoSourceLmkd.onProcDied(
                            message.arg1, message.arg2, null);
                    return;
                case 4102:
                    AppExitInfoTracker.this.mAppExitInfoSourceZygote.onProcDied(
                            message.arg1, message.arg2, (Integer) message.obj);
                    return;
                case 4103:
                    ApplicationExitInfo applicationExitInfo = (ApplicationExitInfo) message.obj;
                    synchronized (AppExitInfoTracker.this.mLock) {
                        AppExitInfoTracker.this.handleNoteProcessDiedLocked(applicationExitInfo);
                    }
                    AppExitInfoTracker.this.recycleRawRecord(applicationExitInfo);
                    return;
                case 4104:
                    ApplicationExitInfo applicationExitInfo2 = (ApplicationExitInfo) message.obj;
                    synchronized (AppExitInfoTracker.this.mLock) {
                        AppExitInfoTracker.this.handleNoteAppKillLocked(applicationExitInfo2);
                    }
                    AppExitInfoTracker.this.recycleRawRecord(applicationExitInfo2);
                    return;
                case 4105:
                    synchronized (AppExitInfoTracker.this.mLock) {
                        AppExitInfoTracker appExitInfoTracker = AppExitInfoTracker.this;
                        ApplicationExitInfo applicationExitInfo3 =
                                (ApplicationExitInfo) message.obj;
                        appExitInfoTracker.getClass();
                        AppExitInfoTracker.performLogToStatsdLocked(applicationExitInfo3);
                    }
                    return;
                case 4106:
                    ApplicationExitInfo applicationExitInfo4 = (ApplicationExitInfo) message.obj;
                    synchronized (AppExitInfoTracker.this.mLock) {
                        AppExitInfoTracker.this.handleNoteAppRecoverableCrashLocked(
                                applicationExitInfo4);
                    }
                    AppExitInfoTracker.this.recycleRawRecord(applicationExitInfo4);
                    return;
                default:
                    super.handleMessage(message);
                    return;
            }
        }
    }

    /* renamed from: -$$Nest$smfindAndRemoveFromSparse2dArray, reason: not valid java name */
    public static Object m172$$Nest$smfindAndRemoveFromSparse2dArray(
            int i, int i2, SparseArray sparseArray) {
        SparseArray sparseArray2;
        int indexOfKey;
        int indexOfKey2 = sparseArray.indexOfKey(i);
        Object obj = null;
        if (indexOfKey2 >= 0
                && (sparseArray2 = (SparseArray) sparseArray.valueAt(indexOfKey2)) != null
                && (indexOfKey = sparseArray2.indexOfKey(i2)) >= 0) {
            obj = sparseArray2.valueAt(indexOfKey);
            sparseArray2.removeAt(indexOfKey);
            if (sparseArray2.size() == 0) {
                sparseArray.removeAt(indexOfKey2);
            }
        }
        return obj;
    }

    public static void dumpHistoryProcessExitInfoLocked(
            PrintWriter printWriter,
            String str,
            SparseArray sparseArray,
            SimpleDateFormat simpleDateFormat) {
        printWriter.println("  package: " + str);
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            printWriter.println("    Historical Process Exit for uid=" + sparseArray.keyAt(i));
            AppExitInfoContainer appExitInfoContainer =
                    (AppExitInfoContainer) sparseArray.valueAt(i);
            appExitInfoContainer.getClass();
            ArrayList arrayList = new ArrayList();
            for (int size2 = appExitInfoContainer.mInfos.size() - 1; size2 >= 0; size2--) {
                arrayList.add((ApplicationExitInfo) appExitInfoContainer.mInfos.valueAt(size2));
            }
            for (int size3 = appExitInfoContainer.mRecoverableCrashes.size() - 1;
                    size3 >= 0;
                    size3--) {
                arrayList.add(
                        (ApplicationExitInfo)
                                appExitInfoContainer.mRecoverableCrashes.valueAt(size3));
            }
            Collections.sort(arrayList, new AppExitInfoTracker$$ExternalSyntheticLambda1(3));
            int size4 = arrayList.size();
            for (int i2 = 0; i2 < size4; i2++) {
                ((ApplicationExitInfo) arrayList.get(i2))
                        .dump(
                                printWriter,
                                "        ",
                                VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "#"),
                                simpleDateFormat);
            }
        }
    }

    public static void performLogToStatsdLocked(ApplicationExitInfo applicationExitInfo) {
        if (applicationExitInfo.isLoggedInStatsd()) {
            return;
        }
        applicationExitInfo.setLoggedInStatsd(true);
        String packageName = applicationExitInfo.getPackageName();
        String processName = applicationExitInfo.getProcessName();
        if (TextUtils.equals(packageName, processName)) {
            processName = null;
        } else if (processName != null
                && packageName != null
                && processName.startsWith(packageName)) {
            processName = processName.substring(packageName.length());
        }
        FrameworkStatsLog.write(
                FrameworkStatsLog.APP_PROCESS_DIED,
                applicationExitInfo.getPackageUid(),
                processName,
                applicationExitInfo.getReason(),
                applicationExitInfo.getSubReason(),
                applicationExitInfo.getImportance(),
                (int) applicationExitInfo.getPss(),
                (int) applicationExitInfo.getRss(),
                applicationExitInfo.hasForegroundServices());
    }

    public static void putToSparse2dArray(
            SparseArray sparseArray,
            int i,
            int i2,
            Object obj,
            Supplier supplier,
            AppExitInfoTracker$$ExternalSyntheticLambda5
                    appExitInfoTracker$$ExternalSyntheticLambda5) {
        SparseArray sparseArray2;
        int indexOfKey = sparseArray.indexOfKey(i);
        if (indexOfKey < 0) {
            sparseArray2 = (SparseArray) supplier.get();
            sparseArray.put(i, sparseArray2);
        } else {
            sparseArray2 = (SparseArray) sparseArray.valueAt(indexOfKey);
        }
        int indexOfKey2 = sparseArray2.indexOfKey(i2);
        if (indexOfKey2 < 0) {
            sparseArray2.put(i2, obj);
            return;
        }
        if (appExitInfoTracker$$ExternalSyntheticLambda5 != null) {
            appExitInfoTracker$$ExternalSyntheticLambda5.accept(sparseArray2.valueAt(indexOfKey2));
        }
        sparseArray2.setValueAt(indexOfKey2, obj);
    }

    public static void removeFromSparse2dArray(
            SparseArray sparseArray,
            Predicate predicate,
            AppExitInfoTracker$$ExternalSyntheticLambda5
                    appExitInfoTracker$$ExternalSyntheticLambda5) {
        SparseArray sparseArray2;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            if (predicate.test(Integer.valueOf(sparseArray.keyAt(size)))
                    && (sparseArray2 = (SparseArray) sparseArray.valueAt(size)) != null) {
                for (int size2 = sparseArray2.size() - 1; size2 >= 0; size2--) {
                    if (appExitInfoTracker$$ExternalSyntheticLambda5 != null) {
                        appExitInfoTracker$$ExternalSyntheticLambda5.accept(
                                sparseArray2.valueAt(size2));
                    }
                    sparseArray2.removeAt(size2);
                }
                if (sparseArray2.size() == 0) {
                    sparseArray.removeAt(size);
                }
            }
        }
    }

    public final void addExitInfoInnerLocked(
            String str, int i, ApplicationExitInfo applicationExitInfo, boolean z) {
        AppExitInfoContainer appExitInfoContainer = (AppExitInfoContainer) this.mData.get(str, i);
        if (appExitInfoContainer == null) {
            appExitInfoContainer = new AppExitInfoContainer(this.mAppExitInfoHistoryListSize);
            if (UserHandle.isIsolated(applicationExitInfo.getRealUid())) {
                Integer uidByIsolatedUid =
                        this.mIsolatedUidRecords.getUidByIsolatedUid(
                                applicationExitInfo.getRealUid());
                if (uidByIsolatedUid != null) {
                    appExitInfoContainer.mUid = uidByIsolatedUid.intValue();
                }
            } else {
                appExitInfoContainer.mUid = applicationExitInfo.getRealUid();
            }
            this.mData.put(str, i, appExitInfoContainer);
        }
        if (z) {
            appExitInfoContainer.addInfoLocked(
                    appExitInfoContainer.mRecoverableCrashes, applicationExitInfo);
        } else {
            appExitInfoContainer.addInfoLocked(appExitInfoContainer.mInfos, applicationExitInfo);
        }
    }

    public final ApplicationExitInfo addExitInfoLocked(
            ApplicationExitInfo applicationExitInfo, boolean z) {
        Integer uidByIsolatedUid;
        if (!this.mAppExitInfoLoaded.get()) {
            Slog.w(
                    "ActivityManager",
                    "Skipping saving the exit info due to ongoing loading from storage");
            return null;
        }
        ApplicationExitInfo applicationExitInfo2 = new ApplicationExitInfo(applicationExitInfo);
        String[] packageList = applicationExitInfo.getPackageList();
        int realUid = applicationExitInfo.getRealUid();
        if (UserHandle.isIsolated(realUid)
                && (uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(realUid))
                        != null) {
            realUid = uidByIsolatedUid.intValue();
        }
        for (String str : packageList) {
            addExitInfoInnerLocked(str, realUid, applicationExitInfo2, z);
        }
        if (Process.isSdkSandboxUid(realUid)) {
            for (String str2 : packageList) {
                addExitInfoInnerLocked(
                        str2, applicationExitInfo.getPackageUid(), applicationExitInfo2, z);
            }
        }
        schedulePersistProcessExitInfo(false);
        return applicationExitInfo2;
    }

    public void clearProcessExitInfo(boolean z) {
        File file;
        synchronized (this.mLock) {
            try {
                if (this.mAppExitInfoPersistTask != null) {
                    IoThread.getHandler().removeCallbacks(this.mAppExitInfoPersistTask);
                    this.mAppExitInfoPersistTask = null;
                }
                if (z && (file = this.mProcExitInfoFile) != null) {
                    file.delete();
                }
                this.mData.getMap().clear();
                this.mActiveAppStateSummary.clear();
                this.mActiveAppTraces.clear();
                pruneAnrTracesIfNecessaryLocked();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpHistoryProcessExitInfo(final PrintWriter printWriter, String str) {
        printWriter.println("ACTIVITY MANAGER PROCESS EXIT INFO (dumpsys activity exit-info)");
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        synchronized (this.mLock) {
            try {
                printWriter.println(
                        "Last Timestamp of Persistence Into Persistent Storage: "
                                + simpleDateFormat.format(
                                        new Date(this.mLastAppExitInfoPersistTimestamp)));
                if (TextUtils.isEmpty(str)) {
                    forEachPackageLocked(
                            new BiFunction() { // from class:
                                               // com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda12
                                @Override // java.util.function.BiFunction
                                public final Object apply(Object obj, Object obj2) {
                                    AppExitInfoTracker appExitInfoTracker = AppExitInfoTracker.this;
                                    PrintWriter printWriter2 = printWriter;
                                    SimpleDateFormat simpleDateFormat2 = simpleDateFormat;
                                    appExitInfoTracker.getClass();
                                    AppExitInfoTracker.dumpHistoryProcessExitInfoLocked(
                                            printWriter2,
                                            (String) obj,
                                            (SparseArray) obj2,
                                            simpleDateFormat2);
                                    return 0;
                                }
                            });
                } else {
                    SparseArray sparseArray = (SparseArray) this.mData.getMap().get(str);
                    if (sparseArray != null) {
                        dumpHistoryProcessExitInfoLocked(
                                printWriter, str, sparseArray, simpleDateFormat);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void forEachPackageLocked(BiFunction biFunction) {
        ArrayMap map = this.mData.getMap();
        int size = map.size() - 1;
        while (size >= 0) {
            int intValue =
                    ((Integer)
                                    biFunction.apply(
                                            (String) map.keyAt(size),
                                            (SparseArray) map.valueAt(size)))
                            .intValue();
            if (intValue == 1) {
                SparseArray sparseArray = (SparseArray) map.valueAt(size);
                for (int size2 = sparseArray.size() - 1; size2 >= 0; size2--) {
                    AppExitInfoContainer appExitInfoContainer =
                            (AppExitInfoContainer) sparseArray.valueAt(size2);
                    AppExitInfoContainer.destroyLocked(appExitInfoContainer.mInfos);
                    AppExitInfoContainer.destroyLocked(appExitInfoContainer.mRecoverableCrashes);
                }
                map.removeAt(size);
            } else if (intValue == 2) {
                size = 0;
            }
            size--;
        }
    }

    public void getExitInfo(String str, final int i, final int i2, int i3, ArrayList arrayList) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                try {
                    if (TextUtils.isEmpty(str)) {
                        final ArrayList arrayList2 = this.mTmpInfoList2;
                        arrayList2.clear();
                        forEachPackageLocked(
                                new BiFunction() { // from class:
                                                   // com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda0
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        AppExitInfoTracker appExitInfoTracker =
                                                AppExitInfoTracker.this;
                                        int i4 = i;
                                        ArrayList arrayList3 = arrayList2;
                                        int i5 = i2;
                                        appExitInfoTracker.getClass();
                                        AppExitInfoTracker.AppExitInfoContainer
                                                appExitInfoContainer =
                                                        (AppExitInfoTracker.AppExitInfoContainer)
                                                                ((SparseArray) obj2).get(i4);
                                        if (appExitInfoContainer != null) {
                                            appExitInfoTracker.mTmpInfoList.clear();
                                            ArrayList arrayList4 = appExitInfoTracker.mTmpInfoList;
                                            if (arrayList4 == null) {
                                                arrayList4 = new ArrayList();
                                            }
                                            for (int size = appExitInfoContainer.mInfos.size() - 1;
                                                    size >= 0;
                                                    size--) {
                                                if (i5 == 0
                                                        || i5
                                                                == appExitInfoContainer.mInfos
                                                                        .keyAt(size)) {
                                                    arrayList4.add(
                                                            (ApplicationExitInfo)
                                                                    appExitInfoContainer.mInfos
                                                                            .valueAt(size));
                                                }
                                            }
                                            for (int size2 =
                                                            appExitInfoContainer.mRecoverableCrashes
                                                                            .size()
                                                                    - 1;
                                                    size2 >= 0;
                                                    size2--) {
                                                if (i5 == 0
                                                        || i5
                                                                == appExitInfoContainer
                                                                        .mRecoverableCrashes.keyAt(
                                                                        size2)) {
                                                    arrayList4.add(
                                                            (ApplicationExitInfo)
                                                                    appExitInfoContainer
                                                                            .mRecoverableCrashes
                                                                            .valueAt(size2));
                                                }
                                            }
                                            arrayList3.addAll(arrayList4);
                                        }
                                        return 0;
                                    }
                                });
                        Collections.sort(
                                arrayList2, new AppExitInfoTracker$$ExternalSyntheticLambda1(0));
                        int size = arrayList2.size();
                        if (i3 > 0) {
                            size = Math.min(size, i3);
                        }
                        for (int i4 = 0; i4 < size; i4++) {
                            arrayList.add((ApplicationExitInfo) arrayList2.get(i4));
                        }
                        arrayList2.clear();
                    } else {
                        AppExitInfoContainer appExitInfoContainer =
                                (AppExitInfoContainer) this.mData.get(str, i);
                        if (appExitInfoContainer != null) {
                            appExitInfoContainer.getExitInfoLocked(i2, i3, arrayList);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final ApplicationExitInfo getExitInfoLocked(int i, int i2, String str) {
        ArrayList arrayList = this.mTmpInfoList;
        arrayList.clear();
        getExitInfo(str, i, i2, 1, arrayList);
        ApplicationExitInfo applicationExitInfo =
                arrayList.size() > 0 ? (ApplicationExitInfo) arrayList.get(0) : null;
        arrayList.clear();
        return applicationExitInfo;
    }

    public byte[] getProcessStateSummary(int i, int i2) {
        synchronized (this.mLock) {
            try {
                Integer uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(i);
                if (uidByIsolatedUid != null) {
                    i = uidByIsolatedUid.intValue();
                }
                int indexOfKey = this.mActiveAppStateSummary.indexOfKey(i);
                if (indexOfKey < 0) {
                    return null;
                }
                return (byte[])
                        ((SparseArray) this.mActiveAppStateSummary.valueAt(indexOfKey)).get(i2);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void handleLogAnrTrace(int i, int i2, String[] strArr, File file, long j, long j2) {
        ApplicationExitInfo applicationExitInfo;
        if (!file.exists() || ArrayUtils.isEmpty(strArr)) {
            return;
        }
        long length = file.length();
        long j3 = j2 - j;
        if (j >= length || j2 > length || j3 <= 0) {
            return;
        }
        File file2 = new File(this.mProcExitStoreDir, file.getName() + ".gz");
        try {
            BufferedInputStream bufferedInputStream =
                    new BufferedInputStream(new FileInputStream(file));
            try {
                GZIPOutputStream gZIPOutputStream =
                        new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(file2)));
                try {
                    byte[] bArr = new byte[8192];
                    bufferedInputStream.skip(j);
                    while (j3 > 0) {
                        int read = bufferedInputStream.read(bArr, 0, (int) Math.min(8192, j3));
                        if (read >= 0) {
                            gZIPOutputStream.write(bArr, 0, read);
                            j3 -= read;
                        }
                    }
                    gZIPOutputStream.close();
                    bufferedInputStream.close();
                    if (j3 == 0 && file2.exists()) {
                        synchronized (this.mLock) {
                            try {
                                Integer uidByIsolatedUid =
                                        this.mIsolatedUidRecords.getUidByIsolatedUid(i2);
                                int intValue =
                                        uidByIsolatedUid != null ? uidByIsolatedUid.intValue() : i2;
                                boolean z = true;
                                for (String str : strArr) {
                                    AppExitInfoContainer appExitInfoContainer =
                                            (AppExitInfoContainer) this.mData.get(str, intValue);
                                    if (appExitInfoContainer != null
                                            && (applicationExitInfo =
                                                            (ApplicationExitInfo)
                                                                    appExitInfoContainer.mInfos.get(
                                                                            i))
                                                    != null) {
                                        applicationExitInfo.setTraceFile(file2);
                                        applicationExitInfo.setAppTraceRetriever(
                                                AppExitInfoTracker.this.mAppTraceRetriever);
                                        z = false;
                                    }
                                }
                                if (z) {
                                    putToSparse2dArray(
                                            this.mActiveAppTraces,
                                            intValue,
                                            i,
                                            file2,
                                            new AppExitInfoTracker$$ExternalSyntheticLambda7(),
                                            new AppExitInfoTracker$$ExternalSyntheticLambda5(1));
                                }
                            } finally {
                            }
                        }
                    }
                } finally {
                }
            } finally {
            }
        } catch (IOException unused) {
        }
    }

    public void handleNoteAppKillLocked(ApplicationExitInfo applicationExitInfo) {
        ApplicationExitInfo exitInfoLocked =
                getExitInfoLocked(
                        applicationExitInfo.getPackageUid(),
                        applicationExitInfo.getPid(),
                        applicationExitInfo.getPackageName());
        final int i = 0;
        if (exitInfoLocked == null) {
            exitInfoLocked = addExitInfoLocked(applicationExitInfo, false);
        } else {
            exitInfoLocked.setReason(applicationExitInfo.getReason());
            exitInfoLocked.setSubReason(applicationExitInfo.getSubReason());
            exitInfoLocked.setStatus(0);
            exitInfoLocked.setTimestamp(System.currentTimeMillis());
            exitInfoLocked.setDescription(applicationExitInfo.getDescription());
        }
        if (CoreRune.MNO_TMO_DEVICE_REPORTING && DeviceReportingSecurityChecker.getStatus()) {
            final String packageName = applicationExitInfo.getPackageName();
            final int pid = applicationExitInfo.getPid();
            int reason = applicationExitInfo.getReason();
            boolean z = AppStateBroadcaster.DEBUG;
            if (reason == 4 || reason == 5) {
                i = 3;
            } else if (reason == 6 || reason == 7) {
                i = 2;
            } else if (reason == 10 || reason == 11) {
                i = 1;
            }
            Slog.d("AppStateBroadcaster", "TMO killProcesses");
            Handler handler = AppStateBroadcaster.mObjHandler;
            if (handler != null) {
                handler.post(
                        new Runnable() { // from class:
                            // com.android.server.am.AppStateBroadcaster$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                boolean z2;
                                int i2;
                                boolean z3;
                                String str = packageName;
                                int i3 = pid;
                                int i4 = i;
                                boolean z4 = AppStateBroadcaster.DEBUG;
                                if (z4) {
                                    StringBuilder m =
                                            StorageManagerService$$ExternalSyntheticOutline0.m(
                                                    i3, "sendApplicationStop(", str, ", ", ", ");
                                    m.append(i4);
                                    m.append(")");
                                    AppStateBroadcaster.logOriginFunction(m.toString());
                                }
                                if (!AppStateBroadcaster.mIsBroadcastEnabled
                                        || TextUtils.isEmpty(str)
                                        || i4 < 0) {
                                    return;
                                }
                                String[] strArr = AppStateBroadcaster.APP_TERM_REASONS;
                                if (i4 < strArr.length) {
                                    String str2 = "";
                                    String stripPackageName =
                                            AppStateBroadcaster.stripPackageName(str);
                                    HashMap hashMap = AppStateBroadcaster.sKnownRunningPackages;
                                    synchronized (hashMap) {
                                        if (z4) {
                                            try {
                                                Slog.d(
                                                        "AppStateBroadcaster",
                                                        "packageStopped for "
                                                                + str
                                                                + " with processId="
                                                                + i3
                                                                + " stopReason="
                                                                + i4);
                                            } catch (Throwable th) {
                                                throw th;
                                            }
                                        }
                                        ApplicationState applicationState =
                                                (ApplicationState) hashMap.get(stripPackageName);
                                        z2 = true;
                                        if (applicationState != null) {
                                            if (z4) {
                                                Slog.d(
                                                        "AppStateBroadcaster",
                                                        "Removing process "
                                                                + i3
                                                                + ", packageName "
                                                                + str
                                                                + " from rootPackage "
                                                                + stripPackageName);
                                            }
                                            applicationState.mStopReason =
                                                    Math.max(i4, applicationState.mStopReason);
                                            applicationState.mProcessIds.remove(
                                                    Integer.valueOf(i3));
                                            if (applicationState.mProcessIds.isEmpty()) {
                                                if (z4) {
                                                    Slog.d(
                                                            "AppStateBroadcaster",
                                                            "Removing "
                                                                    + stripPackageName
                                                                    + " from running packages");
                                                }
                                                String str3 = AppStateBroadcaster.mLastFocusAppName;
                                                if (str3 == null || !str.equals(str3)) {
                                                    z3 = false;
                                                } else {
                                                    AppStateBroadcaster.mLastFocusAppName = null;
                                                    z3 = true;
                                                }
                                                hashMap.remove(stripPackageName);
                                                str2 = strArr[applicationState.mStopReason];
                                            } else if (i4 < 2) {
                                                hashMap.remove(stripPackageName);
                                                str2 = strArr[applicationState.mStopReason];
                                            } else {
                                                z3 = false;
                                                z2 = false;
                                            }
                                        } else {
                                            if (z4) {
                                                Slog.d(
                                                        "AppStateBroadcaster",
                                                        "packageStopped appState is null; send app"
                                                            + " exit with stopReason="
                                                                + i4);
                                            }
                                            hashMap.remove(stripPackageName);
                                            str2 = strArr[i4];
                                        }
                                        z3 = false;
                                    }
                                    if (z3) {
                                        AppStateBroadcaster.sendApplicationFocusLoss(str);
                                    }
                                    if (z2) {
                                        if (z4) {
                                            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                                                    " packageName : ",
                                                    stripPackageName,
                                                    " termReason : ",
                                                    str2,
                                                    "AppStateBroadcaster");
                                        }
                                        if (AppStateBroadcaster.mContext == null
                                                || stripPackageName == null
                                                || str2 == null) {
                                            if (z4) {
                                                Slog.d(
                                                        "AppStateBroadcaster",
                                                        "Can't send broadcast mContext is null");
                                                return;
                                            }
                                            return;
                                        }
                                        for (String str4 : AppStateBroadcaster.sPackages) {
                                            Intent m2 =
                                                    ExplicitHealthCheckController$$ExternalSyntheticOutline0
                                                            .m(
                                                                    "diagandroid.app.ApplicationState",
                                                                    str4);
                                            m2.putExtra(
                                                    "ApplicationPackageName",
                                                    AppStateBroadcaster.stripPackageName(
                                                            stripPackageName));
                                            m2.putExtra("ApplicationState", "EXITED");
                                            m2.putExtra("ApplicationTermReason", str2);
                                            m2.putExtra(
                                                    "oemIntentTimestamp",
                                                    System.currentTimeMillis());
                                            AppStateBroadcaster.mContext.sendBroadcastAsUser(
                                                    m2,
                                                    UserHandle.ALL,
                                                    "diagandroid.app.receiveDetailedApplicationState");
                                        }
                                    }
                                }
                            }
                        });
            }
        }
        scheduleLogToStatsdLocked(exitInfoLocked, true);
    }

    public void handleNoteAppRecoverableCrashLocked(ApplicationExitInfo applicationExitInfo) {
        addExitInfoLocked(applicationExitInfo, true);
    }

    public void handleNoteProcessDiedLocked(ApplicationExitInfo applicationExitInfo) {
        int intValue;
        if (applicationExitInfo != null) {
            ApplicationExitInfo exitInfoLocked =
                    getExitInfoLocked(
                            applicationExitInfo.getPackageUid(),
                            applicationExitInfo.getPid(),
                            applicationExitInfo.getPackageName());
            Pair remove =
                    this.mAppExitInfoSourceZygote.remove(
                            applicationExitInfo.getPid(), applicationExitInfo.getRealUid());
            Pair remove2 =
                    this.mAppExitInfoSourceLmkd.remove(
                            applicationExitInfo.getPid(), applicationExitInfo.getRealUid());
            int realUid = applicationExitInfo.getRealUid();
            IsolatedUidRecords isolatedUidRecords = this.mIsolatedUidRecords;
            isolatedUidRecords.getClass();
            if (UserHandle.isIsolated(realUid)
                    && (intValue =
                                    ((Integer)
                                                    isolatedUidRecords.mIsolatedUidToUidMap.get(
                                                            realUid, -1))
                                            .intValue())
                            != -1) {
                isolatedUidRecords.mIsolatedUidToUidMap.remove(realUid);
                ArraySet arraySet =
                        (ArraySet) isolatedUidRecords.mUidToIsolatedUidMap.get(intValue);
                if (arraySet != null) {
                    arraySet.remove(Integer.valueOf(realUid));
                }
            }
            if (exitInfoLocked == null) {
                exitInfoLocked = addExitInfoLocked(applicationExitInfo, false);
            }
            if (remove2 != null) {
                updateExistingExitInfoRecordLocked(exitInfoLocked, null, 3);
            } else if (remove != null) {
                updateExistingExitInfoRecordLocked(exitInfoLocked, (Integer) remove.second, null);
            } else {
                scheduleLogToStatsdLocked(exitInfoLocked, false);
            }
        }
    }

    public boolean isFresh(long j) {
        return j + 300000 >= System.currentTimeMillis();
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x004d, code lost:

       if (r0 != null) goto L56;
    */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x004f, code lost:

       r0.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0069, code lost:

       if (r0 == null) goto L35;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void loadExistingProcessExitInfo() {
        /*
            r6 = this;
            java.io.File r0 = r6.mProcExitInfoFile
            boolean r0 = r0.canRead()
            r1 = 1
            if (r0 != 0) goto Lf
            java.util.concurrent.atomic.AtomicBoolean r6 = r6.mAppExitInfoLoaded
            r6.set(r1)
            return
        Lf:
            r0 = 0
            android.util.AtomicFile r2 = new android.util.AtomicFile     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            java.io.File r3 = r6.mProcExitInfoFile     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            java.io.FileInputStream r0 = r2.openRead()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            android.util.proto.ProtoInputStream r2 = new android.util.proto.ProtoInputStream     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            int r3 = r2.nextField()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
        L24:
            r4 = -1
            if (r3 == r4) goto L4d
            if (r3 == r1) goto L36
            r4 = 2
            if (r3 == r4) goto L2d
            goto L45
        L2d:
            long r3 = (long) r3     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            r6.loadPackagesFromProto(r2, r3)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            goto L45
        L32:
            r6 = move-exception
            goto L7c
        L34:
            r2 = move-exception
            goto L53
        L36:
            java.lang.Object r3 = r6.mLock     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            monitor-enter(r3)     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            r4 = 1112396529665(0x10300000001, double:5.495969098605E-312)
            long r4 = r2.readLong(r4)     // Catch: java.lang.Throwable -> L4a
            r6.mLastAppExitInfoPersistTimestamp = r4     // Catch: java.lang.Throwable -> L4a
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4a
        L45:
            int r3 = r2.nextField()     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
            goto L24
        L4a:
            r2 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4a
            throw r2     // Catch: java.lang.Throwable -> L32 java.lang.Exception -> L34
        L4d:
            if (r0 == 0) goto L6c
        L4f:
            r0.close()     // Catch: java.io.IOException -> L6c
            goto L6c
        L53:
            java.lang.String r3 = "ActivityManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L32
            r4.<init>()     // Catch: java.lang.Throwable -> L32
            java.lang.String r5 = "Error in loading historical app exit info from persistent storage: "
            r4.append(r5)     // Catch: java.lang.Throwable -> L32
            r4.append(r2)     // Catch: java.lang.Throwable -> L32
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L32
            android.util.Slog.w(r3, r2)     // Catch: java.lang.Throwable -> L32
            if (r0 == 0) goto L6c
            goto L4f
        L6c:
            java.lang.Object r2 = r6.mLock
            monitor-enter(r2)
            r6.pruneAnrTracesIfNecessaryLocked()     // Catch: java.lang.Throwable -> L79
            java.util.concurrent.atomic.AtomicBoolean r6 = r6.mAppExitInfoLoaded     // Catch: java.lang.Throwable -> L79
            r6.set(r1)     // Catch: java.lang.Throwable -> L79
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L79
            return
        L79:
            r6 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L79
            throw r6
        L7c:
            if (r0 == 0) goto L81
            r0.close()     // Catch: java.io.IOException -> L81
        L81:
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppExitInfoTracker.loadExistingProcessExitInfo():void");
    }

    public final void loadPackagesFromProto(ProtoInputStream protoInputStream, long j) {
        long start = protoInputStream.start(j);
        String str = "";
        int nextField = protoInputStream.nextField();
        while (nextField != -1) {
            if (nextField == 1) {
                str = protoInputStream.readString(1138166333441L);
            } else if (nextField != 2) {
                continue;
            } else {
                AppExitInfoContainer appExitInfoContainer =
                        new AppExitInfoContainer(this.mAppExitInfoHistoryListSize);
                long start2 = protoInputStream.start(2246267895810L);
                int nextField2 = protoInputStream.nextField();
                while (nextField2 != -1) {
                    if (nextField2 == 1) {
                        appExitInfoContainer.mUid = protoInputStream.readInt(1120986464257L);
                    } else if (nextField2 == 2) {
                        ApplicationExitInfo applicationExitInfo = new ApplicationExitInfo();
                        applicationExitInfo.readFromProto(protoInputStream, 2246267895810L);
                        appExitInfoContainer.mInfos.put(
                                applicationExitInfo.getPid(), applicationExitInfo);
                    } else if (nextField2 == 3) {
                        ApplicationExitInfo applicationExitInfo2 = new ApplicationExitInfo();
                        applicationExitInfo2.readFromProto(protoInputStream, 2246267895811L);
                        appExitInfoContainer.mRecoverableCrashes.put(
                                applicationExitInfo2.getPid(), applicationExitInfo2);
                    }
                    nextField2 = protoInputStream.nextField();
                }
                protoInputStream.end(start2);
                int i = appExitInfoContainer.mUid;
                synchronized (this.mLock) {
                    this.mData.put(str, i, appExitInfoContainer);
                }
            }
            nextField = protoInputStream.nextField();
        }
        protoInputStream.end(start);
    }

    public ApplicationExitInfo obtainRawRecord(ProcessRecord processRecord, long j) {
        ApplicationExitInfo applicationExitInfo =
                (ApplicationExitInfo) this.mRawRecordsPool.acquire();
        if (applicationExitInfo == null) {
            applicationExitInfo = new ApplicationExitInfo();
        }
        ActivityManagerProcLock activityManagerProcLock = this.mService.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerProcLock) {
            try {
                int i =
                        processRecord.mHostingRecord != null
                                ? processRecord.mHostingRecord.mDefiningUid
                                : 0;
                applicationExitInfo.setPid(processRecord.mPid);
                applicationExitInfo.setRealUid(processRecord.uid);
                applicationExitInfo.setPackageUid(processRecord.info.uid);
                if (i <= 0) {
                    i = processRecord.info.uid;
                }
                applicationExitInfo.setDefiningUid(i);
                applicationExitInfo.setProcessName(processRecord.processName);
                applicationExitInfo.setConnectionGroup(processRecord.mServices.mConnectionGroup);
                applicationExitInfo.setPackageName(processRecord.info.packageName);
                applicationExitInfo.setPackageList(processRecord.mPkgList.getPackageList());
                applicationExitInfo.setReason(0);
                applicationExitInfo.setSubReason(0);
                applicationExitInfo.setStatus(0);
                applicationExitInfo.setImportance(
                        ActivityManager.RunningAppProcessInfo.procStateToImportance(
                                processRecord.mState.mRepProcState));
                applicationExitInfo.setPss(processRecord.mProfile.mLastPss);
                applicationExitInfo.setRss(processRecord.mProfile.mLastRss);
                applicationExitInfo.setTimestamp(j);
                applicationExitInfo.setHasForegroundServices(
                        processRecord.mServices.mRepHasForegroundServices);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        return applicationExitInfo;
    }

    public void onPackageRemoved(String str, int i, boolean z) {
        if (str != null) {
            boolean isEmpty = TextUtils.isEmpty(this.mService.mPackageManagerInt.getNameForUid(i));
            synchronized (this.mLock) {
                if (isEmpty) {
                    try {
                        this.mAppExitInfoSourceZygote.removeByUidLocked(i, z);
                        this.mAppExitInfoSourceLmkd.removeByUidLocked(i, z);
                        this.mIsolatedUidRecords.removeAppUid(i, z);
                    } finally {
                    }
                }
                removePackageLocked(i, z ? -1 : UserHandle.getUserId(i), str, isEmpty);
                schedulePersistProcessExitInfo(true);
            }
        }
    }

    public void onUserRemoved(int i) {
        this.mAppExitInfoSourceZygote.removeByUserId(i);
        this.mAppExitInfoSourceLmkd.removeByUserId(i);
        IsolatedUidRecords isolatedUidRecords = this.mIsolatedUidRecords;
        int currentUserId =
                i == -2 ? AppExitInfoTracker.this.mService.mUserController.getCurrentUserId() : i;
        synchronized (AppExitInfoTracker.this.mLock) {
            try {
                if (currentUserId == -1) {
                    isolatedUidRecords.mIsolatedUidToUidMap.clear();
                    isolatedUidRecords.mUidToIsolatedUidMap.clear();
                } else {
                    for (int size = isolatedUidRecords.mIsolatedUidToUidMap.size() - 1;
                            size >= 0;
                            size--) {
                        isolatedUidRecords.mIsolatedUidToUidMap.keyAt(size);
                        int intValue =
                                ((Integer) isolatedUidRecords.mIsolatedUidToUidMap.valueAt(size))
                                        .intValue();
                        if (UserHandle.getUserId(intValue) == currentUserId) {
                            isolatedUidRecords.mIsolatedUidToUidMap.removeAt(size);
                            isolatedUidRecords.mUidToIsolatedUidMap.remove(intValue);
                        }
                    }
                }
            } finally {
            }
        }
        synchronized (this.mLock) {
            removeByUserIdLocked(i);
            schedulePersistProcessExitInfo(true);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0053 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void persistProcessExitInfo() {
        /*
            r9 = this;
            android.util.AtomicFile r0 = new android.util.AtomicFile
            java.io.File r1 = r9.mProcExitInfoFile
            r0.<init>(r1)
            long r1 = java.lang.System.currentTimeMillis()
            r3 = 0
            java.io.FileOutputStream r4 = r0.startWrite()     // Catch: java.io.IOException -> L38
            android.util.proto.ProtoOutputStream r5 = new android.util.proto.ProtoOutputStream     // Catch: java.io.IOException -> L33
            r5.<init>(r4)     // Catch: java.io.IOException -> L33
            r6 = 1112396529665(0x10300000001, double:5.495969098605E-312)
            r5.write(r6, r1)     // Catch: java.io.IOException -> L33
            java.lang.Object r6 = r9.mLock     // Catch: java.io.IOException -> L33
            monitor-enter(r6)     // Catch: java.io.IOException -> L33
            com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda8 r7 = new com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda8     // Catch: java.lang.Throwable -> L35
            r8 = 0
            r7.<init>(r8, r5)     // Catch: java.lang.Throwable -> L35
            r9.forEachPackageLocked(r7)     // Catch: java.lang.Throwable -> L35
            r9.mLastAppExitInfoPersistTimestamp = r1     // Catch: java.lang.Throwable -> L35
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L35
            r5.flush()     // Catch: java.io.IOException -> L33
            r0.finishWrite(r4)     // Catch: java.io.IOException -> L33
            goto L50
        L33:
            r1 = move-exception
            goto L3a
        L35:
            r1 = move-exception
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L35
            throw r1     // Catch: java.io.IOException -> L33
        L38:
            r1 = move-exception
            r4 = r3
        L3a:
            java.lang.String r2 = "ActivityManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Unable to write historical app exit info into persistent storage: "
            r5.<init>(r6)
            r5.append(r1)
            java.lang.String r1 = r5.toString()
            android.util.Slog.w(r2, r1)
            r0.failWrite(r4)
        L50:
            java.lang.Object r0 = r9.mLock
            monitor-enter(r0)
            r9.mAppExitInfoPersistTask = r3     // Catch: java.lang.Throwable -> L57
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L57
            return
        L57:
            r9 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L57
            throw r9
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppExitInfoTracker.persistProcessExitInfo():void");
    }

    public final void pruneAnrTracesIfNecessaryLocked() {
        final ArraySet arraySet = new ArraySet();
        if (ArrayUtils.isEmpty(
                this.mProcExitStoreDir.listFiles(
                        new FileFilter() { // from class:
                                           // com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda9
                            @Override // java.io.FileFilter
                            public final boolean accept(File file) {
                                ArraySet arraySet2 = arraySet;
                                String name = file.getName();
                                boolean z = name.startsWith("anr_") && name.endsWith(".gz");
                                if (z) {
                                    arraySet2.add(name);
                                }
                                return z;
                            }
                        }))) {
            return;
        }
        forEachPackageLocked(new AppExitInfoTracker$$ExternalSyntheticLambda8(1, arraySet));
        SparseArray sparseArray = this.mActiveAppTraces;
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            SparseArray sparseArray2 = (SparseArray) sparseArray.valueAt(size);
            if (sparseArray2 != null) {
                for (int size2 = sparseArray2.size() - 1; size2 >= 0; size2--) {
                    arraySet.remove(((File) sparseArray2.valueAt(size2)).getName());
                }
            }
        }
        for (int size3 = arraySet.size() - 1; size3 >= 0; size3--) {
            new File(this.mProcExitStoreDir, (String) arraySet.valueAt(size3)).delete();
        }
    }

    public void recycleRawRecord(ApplicationExitInfo applicationExitInfo) {
        applicationExitInfo.setProcessName(null);
        applicationExitInfo.setDescription(null);
        applicationExitInfo.setPackageList(null);
        this.mRawRecordsPool.release(applicationExitInfo);
    }

    public final void removeByUserIdLocked(final int i) {
        if (i == -1) {
            this.mData.getMap().clear();
            this.mActiveAppStateSummary.clear();
            this.mActiveAppTraces.clear();
            pruneAnrTracesIfNecessaryLocked();
            return;
        }
        final int i2 = 0;
        removeFromSparse2dArray(
                this.mActiveAppStateSummary,
                new Predicate() { // from class:
                                  // com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i3 = i2;
                        int i4 = i;
                        Integer num = (Integer) obj;
                        switch (i3) {
                            case 0:
                                if (UserHandle.getUserId(num.intValue()) == i4) {}
                                break;
                            default:
                                if (UserHandle.getUserId(num.intValue()) == i4) {}
                                break;
                        }
                        return false;
                    }
                },
                null);
        final int i3 = 1;
        removeFromSparse2dArray(
                this.mActiveAppTraces,
                new Predicate() { // from class:
                                  // com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda3
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        int i32 = i3;
                        int i4 = i;
                        Integer num = (Integer) obj;
                        switch (i32) {
                            case 0:
                                if (UserHandle.getUserId(num.intValue()) == i4) {}
                                break;
                            default:
                                if (UserHandle.getUserId(num.intValue()) == i4) {}
                                break;
                        }
                        return false;
                    }
                },
                new AppExitInfoTracker$$ExternalSyntheticLambda5(0));
        forEachPackageLocked(
                new BiFunction() { // from class:
                                   // com.android.server.am.AppExitInfoTracker$$ExternalSyntheticLambda6
                    @Override // java.util.function.BiFunction
                    public final Object apply(Object obj, Object obj2) {
                        int i4 = i;
                        SparseArray sparseArray = (SparseArray) obj2;
                        int size = sparseArray.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            if (UserHandle.getUserId(sparseArray.keyAt(size)) == i4) {
                                AppExitInfoTracker.AppExitInfoContainer appExitInfoContainer =
                                        (AppExitInfoTracker.AppExitInfoContainer)
                                                sparseArray.valueAt(size);
                                AppExitInfoTracker.AppExitInfoContainer.destroyLocked(
                                        appExitInfoContainer.mInfos);
                                AppExitInfoTracker.AppExitInfoContainer.destroyLocked(
                                        appExitInfoContainer.mRecoverableCrashes);
                                sparseArray.removeAt(size);
                                break;
                            }
                            size--;
                        }
                        return Integer.valueOf(sparseArray.size() != 0 ? 0 : 1);
                    }
                });
    }

    public final void removePackageLocked(int i, int i2, String str, boolean z) {
        if (z) {
            this.mActiveAppStateSummary.remove(i);
            int indexOfKey = this.mActiveAppTraces.indexOfKey(i);
            if (indexOfKey >= 0) {
                SparseArray sparseArray = (SparseArray) this.mActiveAppTraces.valueAt(indexOfKey);
                for (int size = sparseArray.size() - 1; size >= 0; size--) {
                    ((File) sparseArray.valueAt(size)).delete();
                }
                this.mActiveAppTraces.removeAt(indexOfKey);
            }
        }
        ArrayMap map = this.mData.getMap();
        SparseArray sparseArray2 = (SparseArray) map.get(str);
        if (sparseArray2 == null) {
            return;
        }
        if (i2 == -1) {
            for (int size2 = sparseArray2.size() - 1; size2 >= 0; size2--) {
                AppExitInfoContainer appExitInfoContainer =
                        (AppExitInfoContainer) sparseArray2.valueAt(size2);
                AppExitInfoContainer.destroyLocked(appExitInfoContainer.mInfos);
                AppExitInfoContainer.destroyLocked(appExitInfoContainer.mRecoverableCrashes);
            }
            this.mData.getMap().remove(str);
            return;
        }
        int size3 = sparseArray2.size() - 1;
        while (true) {
            if (size3 < 0) {
                break;
            }
            if (UserHandle.getUserId(sparseArray2.keyAt(size3)) == i2) {
                AppExitInfoContainer appExitInfoContainer2 =
                        (AppExitInfoContainer) sparseArray2.valueAt(size3);
                AppExitInfoContainer.destroyLocked(appExitInfoContainer2.mInfos);
                AppExitInfoContainer.destroyLocked(appExitInfoContainer2.mRecoverableCrashes);
                sparseArray2.removeAt(size3);
                break;
            }
            size3--;
        }
        if (sparseArray2.size() == 0) {
            map.remove(str);
        }
    }

    public final void scheduleLogToStatsdLocked(
            ApplicationExitInfo applicationExitInfo, boolean z) {
        if (applicationExitInfo.isLoggedInStatsd()) {
            return;
        }
        if (z) {
            this.mKillHandler.removeMessages(4105, applicationExitInfo);
            performLogToStatsdLocked(applicationExitInfo);
        } else {
            if (this.mKillHandler.hasMessages(4105, applicationExitInfo)) {
                return;
            }
            KillHandler killHandler = this.mKillHandler;
            killHandler.sendMessageDelayed(
                    killHandler.obtainMessage(4105, applicationExitInfo),
                    APP_EXIT_INFO_STATSD_LOG_DEBOUNCE);
        }
    }

    public final void scheduleNoteAppKill(ProcessRecord processRecord, int i, int i2, String str) {
        if (!this.mAppExitInfoLoaded.get() || processRecord == null || processRecord.info == null) {
            return;
        }
        ApplicationExitInfo obtainRawRecord =
                obtainRawRecord(processRecord, System.currentTimeMillis());
        obtainRawRecord.setReason(i);
        obtainRawRecord.setSubReason(i2);
        obtainRawRecord.setDescription(str);
        this.mKillHandler.obtainMessage(4104, obtainRawRecord).sendToTarget();
    }

    public void schedulePersistProcessExitInfo(boolean z) {
        synchronized (this.mLock) {
            try {
                AppExitInfoTracker$$ExternalSyntheticLambda2
                        appExitInfoTracker$$ExternalSyntheticLambda2 = this.mAppExitInfoPersistTask;
                if (appExitInfoTracker$$ExternalSyntheticLambda2 == null || z) {
                    if (appExitInfoTracker$$ExternalSyntheticLambda2 != null) {
                        IoThread.getHandler().removeCallbacks(this.mAppExitInfoPersistTask);
                    }
                    this.mAppExitInfoPersistTask =
                            new AppExitInfoTracker$$ExternalSyntheticLambda2(this, 0);
                    IoThread.getHandler()
                            .postDelayed(
                                    this.mAppExitInfoPersistTask,
                                    z ? 0L : APP_EXIT_INFO_PERSIST_INTERVAL);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void setProcessStateSummary(int i, int i2, byte[] bArr) {
        synchronized (this.mLock) {
            try {
                Integer uidByIsolatedUid = this.mIsolatedUidRecords.getUidByIsolatedUid(i);
                if (uidByIsolatedUid != null) {
                    i = uidByIsolatedUid.intValue();
                }
                putToSparse2dArray(
                        this.mActiveAppStateSummary,
                        i,
                        i2,
                        bArr,
                        new AppExitInfoTracker$$ExternalSyntheticLambda7(),
                        null);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x006c, code lost:

       if (r6.intValue() == 3) goto L26;
    */
    /* JADX WARN: Removed duplicated region for block: B:11:0x0060  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateExistingExitInfoRecordLocked(
            android.app.ApplicationExitInfo r4, java.lang.Integer r5, java.lang.Integer r6) {
        /*
            r3 = this;
            if (r4 == 0) goto L73
            long r0 = r4.getTimestamp()
            boolean r0 = r3.isFresh(r0)
            if (r0 != 0) goto Ld
            goto L73
        Ld:
            r0 = 1
            if (r5 == 0) goto L5d
            int r1 = r5.intValue()
            boolean r1 = android.system.OsConstants.WIFEXITED(r1)
            if (r1 == 0) goto L2a
            r4.setReason(r0)
            int r5 = r5.intValue()
            int r5 = android.system.OsConstants.WEXITSTATUS(r5)
            r4.setStatus(r5)
        L28:
            r5 = r0
            goto L5e
        L2a:
            int r1 = r5.intValue()
            boolean r1 = android.system.OsConstants.WIFSIGNALED(r1)
            if (r1 == 0) goto L5d
            int r1 = r4.getReason()
            if (r1 != 0) goto L4a
            r1 = 2
            r4.setReason(r1)
            int r5 = r5.intValue()
            int r5 = android.system.OsConstants.WTERMSIG(r5)
            r4.setStatus(r5)
            goto L5d
        L4a:
            int r1 = r4.getReason()
            r2 = 5
            if (r1 != r2) goto L5d
            int r5 = r5.intValue()
            int r5 = android.system.OsConstants.WTERMSIG(r5)
            r4.setStatus(r5)
            goto L28
        L5d:
            r5 = 0
        L5e:
            if (r6 == 0) goto L6f
            int r1 = r6.intValue()
            r4.setReason(r1)
            int r6 = r6.intValue()
            r1 = 3
            if (r6 != r1) goto L6f
            goto L70
        L6f:
            r0 = r5
        L70:
            r3.scheduleLogToStatsdLocked(r4, r0)
        L73:
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppExitInfoTracker.updateExistingExitInfoRecordLocked(android.app.ApplicationExitInfo,"
                    + " java.lang.Integer, java.lang.Integer):void");
    }
}
