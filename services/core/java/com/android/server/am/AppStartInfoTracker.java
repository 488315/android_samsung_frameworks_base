package com.android.server.am;

import android.app.ApplicationStartInfo;
import android.app.IApplicationStartInfoCompleteListener;
import android.content.Intent;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.icu.text.SimpleDateFormat;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import android.util.SparseArray;
import android.util.proto.ProtoInputStream;

import com.android.internal.app.ProcessMap;
import com.android.server.IoThread;
import com.android.server.wm.WindowProcessController;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiFunction;

public final class AppStartInfoTracker {
    static final String APP_START_INFO_FILE = "procstartinfo";
    static final int APP_START_INFO_HISTORY_LIST_SIZE = 16;
    public static final long APP_START_INFO_PERSIST_INTERVAL = TimeUnit.MINUTES.toMillis(30);
    static final String APP_START_STORE_DIR = "procstartstore";
    static final int MAX_IN_PROGRESS_RECORDS = 5;
    int mAppStartInfoHistoryListSize;
    public Handler mHandler;
    File mProcStartInfoFile;
    File mProcStartStoreDir;
    ActivityManagerService mService;
    final Object mLock = new Object();
    boolean mEnabled = false;
    public AppStartInfoTracker$$ExternalSyntheticLambda4 mAppStartInfoPersistTask = null;
    public long mLastAppStartInfoPersistTimestamp = 0;
    AtomicBoolean mAppStartInfoLoaded = new AtomicBoolean();
    public final ArrayList mTmpStartInfoList = new ArrayList();
    final ArrayMap mInProgressRecords = new ArrayMap();
    final ArrayList mTemporaryInProgressIndexes = new ArrayList();
    public final SparseArray mCallbacks = new SparseArray();
    public final ProcessMap mData = new ProcessMap();

    public final class AppStartInfoContainer {
        public final int mMaxCapacity;
        public int mUid;
        public boolean mMonitoringModeEnabled = false;
        public final ArrayList mInfos = new ArrayList();

        public AppStartInfoContainer(int i) {
            this.mMaxCapacity = i;
        }

        public static long calculateAverage(List list) {
            return (long)
                    list.stream()
                            .mapToDouble(
                                    new AppStartInfoTracker$AppStartInfoContainer$$ExternalSyntheticLambda2())
                            .average()
                            .orElse(0.0d);
        }

        public final void addTimestampToStartLocked(int i, long j) {
            ApplicationStartInfo applicationStartInfo;
            int startupState;
            if (this.mInfos.isEmpty()
                    || (startupState =
                                    (applicationStartInfo =
                                                    (ApplicationStartInfo) this.mInfos.get(0))
                                            .getStartupState())
                            == 1) {
                return;
            }
            Map startupTimestamps = applicationStartInfo.getStartupTimestamps();
            if (startupState == 2 && i != 5) {
                if (i == 6) {
                    Long l = (Long) startupTimestamps.get(4);
                    if (l == null || j > l.longValue()) {
                        return;
                    }
                } else if (i != 7) {
                    return;
                }
            }
            if (startupTimestamps.get(Integer.valueOf(i)) != null) {
                return;
            }
            applicationStartInfo.addStartupTimestamp(i, j);
            if (i == 4 && android.app.Flags.appStartInfoTimestamps()) {
                applicationStartInfo.setStartupState(2);
                AppStartInfoTracker.this.checkCompletenessAndCallback(applicationStartInfo);
            }
        }
    }

    public final class ApplicationStartInfoCompleteCallback implements IBinder.DeathRecipient {
        public final IApplicationStartInfoCompleteListener mCallback;
        public final int mUid;

        public ApplicationStartInfoCompleteCallback(
                IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener,
                int i) {
            this.mCallback = iApplicationStartInfoCompleteListener;
            this.mUid = i;
            try {
                iApplicationStartInfoCompleteListener.asBinder().linkToDeath(this, 0);
            } catch (RemoteException unused) {
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            AppStartInfoTracker.this.removeStartInfoCompleteListener(
                    this.mCallback, this.mUid, false);
        }
    }

    public static void addBaseFieldsFromProcessRecord(
            ApplicationStartInfo applicationStartInfo, ProcessRecord processRecord) {
        if (processRecord == null) {
            return;
        }
        boolean z = false;
        int i =
                processRecord.mHostingRecord != null
                        ? processRecord.mHostingRecord.mDefiningUid
                        : 0;
        applicationStartInfo.setPid(processRecord.mPid);
        applicationStartInfo.setRealUid(processRecord.uid);
        applicationStartInfo.setPackageUid(processRecord.info.uid);
        if (i <= 0) {
            i = processRecord.info.uid;
        }
        applicationStartInfo.setDefiningUid(i);
        applicationStartInfo.setProcessName(processRecord.processName);
        applicationStartInfo.setPackageName(processRecord.info.packageName);
        if (com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags.stayStopped()) {
            WindowProcessController windowProcessController =
                    processRecord.mWindowProcessController;
            if (processRecord.mWasForceStopped
                    || (windowProcessController != null
                            && windowProcessController.mStoppedState == 2)) {
                z = true;
            }
            applicationStartInfo.setForceStopped(z);
        }
    }

    public static void dumpHistoryProcessStartInfoLocked(
            PrintWriter printWriter,
            String str,
            SparseArray sparseArray,
            SimpleDateFormat simpleDateFormat) {
        printWriter.println("  package: " + str);
        int size = sparseArray.size();
        for (int i = 0; i < size; i++) {
            printWriter.println("    Historical Process Start for userId=" + sparseArray.keyAt(i));
            AppStartInfoContainer appStartInfoContainer =
                    (AppStartInfoContainer) sparseArray.valueAt(i);
            if (appStartInfoContainer.mMonitoringModeEnabled) {
                ArrayList arrayList = new ArrayList();
                ArrayList arrayList2 = new ArrayList();
                ArrayList arrayList3 = new ArrayList();
                for (int i2 = 0; i2 < appStartInfoContainer.mInfos.size(); i2++) {
                    ApplicationStartInfo applicationStartInfo =
                            (ApplicationStartInfo) appStartInfoContainer.mInfos.get(i2);
                    Map startupTimestamps = applicationStartInfo.getStartupTimestamps();
                    if (startupTimestamps.containsKey(0) && startupTimestamps.containsKey(4)) {
                        long longValue =
                                ((Long) startupTimestamps.get(4)).longValue()
                                        - ((Long) startupTimestamps.get(0)).longValue();
                        int startType = applicationStartInfo.getStartType();
                        if (startType == 1) {
                            arrayList.add(Long.valueOf(longValue));
                        } else if (startType == 2) {
                            arrayList2.add(Long.valueOf(longValue));
                        } else if (startType == 3) {
                            arrayList3.add(Long.valueOf(longValue));
                        }
                    }
                }
                StringBuilder sb =
                        new StringBuilder("        Average Start Time in ns for Cold Starts: ");
                sb.append(
                        arrayList.isEmpty()
                                ? "No records"
                                : Long.valueOf(AppStartInfoContainer.calculateAverage(arrayList)));
                printWriter.println(sb.toString());
                StringBuilder sb2 =
                        new StringBuilder("        Average Start Time in ns for Warm Starts: ");
                sb2.append(
                        arrayList2.isEmpty()
                                ? "No records"
                                : Long.valueOf(AppStartInfoContainer.calculateAverage(arrayList2)));
                printWriter.println(sb2.toString());
                StringBuilder sb3 =
                        new StringBuilder("        Average Start Time in ns for Hot Starts: ");
                sb3.append(
                        arrayList3.isEmpty()
                                ? "No records"
                                : Long.valueOf(AppStartInfoContainer.calculateAverage(arrayList3)));
                printWriter.println(sb3.toString());
            }
            int size2 = appStartInfoContainer.mInfos.size();
            for (int i3 = 0; i3 < size2; i3++) {
                ((ApplicationStartInfo) appStartInfoContainer.mInfos.get(i3))
                        .dump(
                                printWriter,
                                "        ",
                                VibrationParam$1$$ExternalSyntheticOutline0.m(i3, "#"),
                                simpleDateFormat);
            }
        }
    }

    public static long getStartTimestamp(ApplicationStartInfo applicationStartInfo) {
        if (applicationStartInfo.getStartupTimestamps() == null
                || !applicationStartInfo.getStartupTimestamps().containsKey(0)) {
            return -1L;
        }
        return ((Long) applicationStartInfo.getStartupTimestamps().get(0)).longValue();
    }

    public final ApplicationStartInfo addStartInfoLocked(
            ApplicationStartInfo applicationStartInfo) {
        if (!this.mAppStartInfoLoaded.get()) {
            Slog.w(
                    "ActivityManager",
                    "Skipping saving the start info due to ongoing loading from storage");
            return null;
        }
        ApplicationStartInfo applicationStartInfo2 = new ApplicationStartInfo(applicationStartInfo);
        AppStartInfoContainer appStartInfoContainer =
                (AppStartInfoContainer)
                        this.mData.get(
                                applicationStartInfo.getPackageName(),
                                applicationStartInfo.getRealUid());
        if (appStartInfoContainer == null) {
            appStartInfoContainer = new AppStartInfoContainer(this.mAppStartInfoHistoryListSize);
            appStartInfoContainer.mUid = applicationStartInfo2.getRealUid();
            this.mData.put(
                    applicationStartInfo.getPackageName(),
                    applicationStartInfo.getRealUid(),
                    appStartInfoContainer);
        }
        int size = appStartInfoContainer.mInfos.size();
        if (size
                >= (appStartInfoContainer.mMonitoringModeEnabled
                        ? 100
                        : appStartInfoContainer.mMaxCapacity)) {
            int i = -1;
            long j = Long.MAX_VALUE;
            for (int i2 = 0; i2 < size; i2++) {
                ApplicationStartInfo applicationStartInfo3 =
                        (ApplicationStartInfo) appStartInfoContainer.mInfos.get(i2);
                if (getStartTimestamp(applicationStartInfo3) < j) {
                    j = getStartTimestamp(applicationStartInfo3);
                    i = i2;
                }
            }
            if (i >= 0) {
                appStartInfoContainer.mInfos.remove(i);
            }
        }
        appStartInfoContainer.mInfos.add(applicationStartInfo2);
        Collections.sort(
                appStartInfoContainer.mInfos, new AppStartInfoTracker$$ExternalSyntheticLambda1(2));
        schedulePersistProcessStartInfo(false);
        return applicationStartInfo2;
    }

    public final void addTimestampToStart(int i, int i2, long j, String str) {
        if (this.mEnabled) {
            synchronized (this.mLock) {
                try {
                    AppStartInfoContainer appStartInfoContainer =
                            (AppStartInfoContainer) this.mData.get(str, i);
                    if (appStartInfoContainer == null) {
                        return;
                    }
                    appStartInfoContainer.addTimestampToStartLocked(i2, j);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void checkCompletenessAndCallback(ApplicationStartInfo applicationStartInfo) {
        synchronized (this.mLock) {
            try {
                if (applicationStartInfo.getStartupState() == 2) {
                    List list = (List) this.mCallbacks.get(applicationStartInfo.getRealUid());
                    if (list == null) {
                        return;
                    }
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (list.get(i) != null) {
                            ApplicationStartInfoCompleteCallback
                                    applicationStartInfoCompleteCallback =
                                            (ApplicationStartInfoCompleteCallback) list.get(i);
                            applicationStartInfoCompleteCallback.getClass();
                            try {
                                applicationStartInfoCompleteCallback.mCallback
                                        .onApplicationStartInfoComplete(applicationStartInfo);
                            } catch (RemoteException unused) {
                            }
                        }
                    }
                    this.mCallbacks.remove(applicationStartInfo.getRealUid());
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void clearProcessStartInfo(boolean z) {
        File file;
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (this.mAppStartInfoPersistTask != null) {
                        IoThread.getHandler().removeCallbacks(this.mAppStartInfoPersistTask);
                        this.mAppStartInfoPersistTask = null;
                    }
                    if (z && (file = this.mProcStartInfoFile) != null) {
                        file.delete();
                    }
                    this.mData.getMap().clear();
                    this.mInProgressRecords.clear();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void dumpHistoryProcessStartInfo(final PrintWriter printWriter, String str) {
        if (this.mEnabled) {
            printWriter.println("ACTIVITY MANAGER LRU PROCESSES (dumpsys activity start-info)");
            final SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            synchronized (this.mLock) {
                try {
                    printWriter.println(
                            "Last Timestamp of Persistence Into Persistent Storage: "
                                    + simpleDateFormat.format(
                                            new Date(this.mLastAppStartInfoPersistTimestamp)));
                    if (TextUtils.isEmpty(str)) {
                        forEachPackageLocked(
                                new BiFunction() { // from class:
                                                   // com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda6
                                    @Override // java.util.function.BiFunction
                                    public final Object apply(Object obj, Object obj2) {
                                        AppStartInfoTracker appStartInfoTracker =
                                                AppStartInfoTracker.this;
                                        PrintWriter printWriter2 = printWriter;
                                        SimpleDateFormat simpleDateFormat2 = simpleDateFormat;
                                        appStartInfoTracker.getClass();
                                        AppStartInfoTracker.dumpHistoryProcessStartInfoLocked(
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
                            dumpHistoryProcessStartInfoLocked(
                                    printWriter, str, sparseArray, simpleDateFormat);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final boolean forEachPackageLocked(BiFunction biFunction) {
        ArrayMap map = this.mData.getMap();
        for (int size = map.size() - 1; size >= 0; size--) {
            int intValue =
                    ((Integer)
                                    biFunction.apply(
                                            (String) map.keyAt(size),
                                            (SparseArray) map.valueAt(size)))
                            .intValue();
            if (intValue != 1) {
                if (intValue != 2) {
                    if (intValue == 3) {
                        map.removeAt(size);
                    }
                }
                return false;
            }
            map.removeAt(size);
        }
        return true;
    }

    public final void getStartInfo(final int i, int i2, String str, ArrayList arrayList) {
        if (this.mEnabled) {
            if (i2 == 0) {
                i2 = 16;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (this.mLock) {
                    try {
                        int i3 = 0;
                        if (TextUtils.isEmpty(str)) {
                            final ArrayList arrayList2 = this.mTmpStartInfoList;
                            arrayList2.clear();
                            forEachPackageLocked(
                                    new BiFunction() { // from class:
                                                       // com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda0
                                        @Override // java.util.function.BiFunction
                                        public final Object apply(Object obj, Object obj2) {
                                            int i4 = i;
                                            ArrayList arrayList3 = arrayList2;
                                            AppStartInfoTracker.AppStartInfoContainer
                                                    appStartInfoContainer =
                                                            (AppStartInfoTracker
                                                                            .AppStartInfoContainer)
                                                                    ((SparseArray) obj2).get(i4);
                                            if (appStartInfoContainer != null) {
                                                arrayList3.addAll(appStartInfoContainer.mInfos);
                                            }
                                            return 0;
                                        }
                                    });
                            Collections.sort(
                                    arrayList2,
                                    new AppStartInfoTracker$$ExternalSyntheticLambda1(0));
                            int size = arrayList2.size();
                            if (i2 > 0) {
                                size = Math.min(size, i2);
                            }
                            while (i3 < size) {
                                arrayList.add((ApplicationStartInfo) arrayList2.get(i3));
                                i3++;
                            }
                            arrayList2.clear();
                        } else {
                            AppStartInfoContainer appStartInfoContainer =
                                    (AppStartInfoContainer) this.mData.get(str, i);
                            if (appStartInfoContainer != null) {
                                if (appStartInfoContainer.mInfos.size() > i2) {
                                    i3 = appStartInfoContainer.mInfos.size() - i2;
                                }
                                arrayList.addAll(i3, appStartInfoContainer.mInfos);
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
    }

    public final void handleProcessBackupStart(long j, ProcessRecord processRecord, boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    ApplicationStartInfo applicationStartInfo = new ApplicationStartInfo();
                    addBaseFieldsFromProcessRecord(applicationStartInfo, processRecord);
                    applicationStartInfo.setStartupState(0);
                    applicationStartInfo.addStartupTimestamp(0, j);
                    applicationStartInfo.setStartType(z ? 1 : 2);
                    applicationStartInfo.setReason(1);
                    addStartInfoLocked(applicationStartInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void handleProcessContentProviderStart(ProcessRecord processRecord, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    ApplicationStartInfo applicationStartInfo = new ApplicationStartInfo();
                    addBaseFieldsFromProcessRecord(applicationStartInfo, processRecord);
                    applicationStartInfo.setStartupState(0);
                    applicationStartInfo.addStartupTimestamp(0, j);
                    applicationStartInfo.setStartType(1);
                    applicationStartInfo.setReason(4);
                    addStartInfoLocked(applicationStartInfo);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0052, code lost:

       if (r0 != null) goto L48;
    */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0054, code lost:

       r0.close();
    */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x006e, code lost:

       if (r0 == null) goto L38;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void loadExistingProcessStartInfo() {
        /*
            r6 = this;
            boolean r0 = r6.mEnabled
            if (r0 != 0) goto L5
            return
        L5:
            java.io.File r0 = r6.mProcStartInfoFile
            boolean r0 = r0.canRead()
            r1 = 1
            if (r0 != 0) goto L14
            java.util.concurrent.atomic.AtomicBoolean r6 = r6.mAppStartInfoLoaded
            r6.set(r1)
            return
        L14:
            r0 = 0
            android.util.AtomicFile r2 = new android.util.AtomicFile     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            java.io.File r3 = r6.mProcStartInfoFile     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            r2.<init>(r3)     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            java.io.FileInputStream r0 = r2.openRead()     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            android.util.proto.ProtoInputStream r2 = new android.util.proto.ProtoInputStream     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            r2.<init>(r0)     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            int r3 = r2.nextField()     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
        L29:
            r4 = -1
            if (r3 == r4) goto L52
            if (r3 == r1) goto L3b
            r4 = 2
            if (r3 == r4) goto L32
            goto L4a
        L32:
            long r3 = (long) r3     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            r6.loadPackagesFromProto(r2, r3)     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            goto L4a
        L37:
            r6 = move-exception
            goto L77
        L39:
            r2 = move-exception
            goto L58
        L3b:
            java.lang.Object r3 = r6.mLock     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            monitor-enter(r3)     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39
            r4 = 1112396529665(0x10300000001, double:5.495969098605E-312)
            long r4 = r2.readLong(r4)     // Catch: java.lang.Throwable -> L4f
            r6.mLastAppStartInfoPersistTimestamp = r4     // Catch: java.lang.Throwable -> L4f
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4f
        L4a:
            int r3 = r2.nextField()     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39 java.lang.Throwable -> L39 java.lang.Throwable -> L39 java.lang.Throwable -> L39
            goto L29
        L4f:
            r2 = move-exception
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L4f
            throw r2     // Catch: java.lang.Throwable -> L37 java.lang.Throwable -> L39 java.lang.Throwable -> L39 java.lang.Throwable -> L39 java.lang.Throwable -> L39
        L52:
            if (r0 == 0) goto L71
        L54:
            r0.close()     // Catch: java.io.IOException -> L71
            goto L71
        L58:
            java.lang.String r3 = "ActivityManager"
            java.lang.StringBuilder r4 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L37
            r4.<init>()     // Catch: java.lang.Throwable -> L37
            java.lang.String r5 = "Error in loading historical app start info from persistent storage: "
            r4.append(r5)     // Catch: java.lang.Throwable -> L37
            r4.append(r2)     // Catch: java.lang.Throwable -> L37
            java.lang.String r2 = r4.toString()     // Catch: java.lang.Throwable -> L37
            android.util.Slog.w(r3, r2)     // Catch: java.lang.Throwable -> L37
            if (r0 == 0) goto L71
            goto L54
        L71:
            java.util.concurrent.atomic.AtomicBoolean r6 = r6.mAppStartInfoLoaded
            r6.set(r1)
            return
        L77:
            if (r0 == 0) goto L7c
            r0.close()     // Catch: java.io.IOException -> L7c
        L7c:
            throw r6
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppStartInfoTracker.loadExistingProcessStartInfo():void");
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
                AppStartInfoContainer appStartInfoContainer =
                        new AppStartInfoContainer(this.mAppStartInfoHistoryListSize);
                long start2 = protoInputStream.start(2246267895810L);
                int nextField2 = protoInputStream.nextField();
                while (nextField2 != -1) {
                    if (nextField2 == 1) {
                        appStartInfoContainer.mUid = protoInputStream.readInt(1120986464257L);
                    } else if (nextField2 == 2) {
                        ApplicationStartInfo applicationStartInfo = new ApplicationStartInfo();
                        applicationStartInfo.readFromProto(protoInputStream, 2246267895810L);
                        appStartInfoContainer.mInfos.add(applicationStartInfo);
                    } else if (nextField2 == 3) {
                        appStartInfoContainer.mMonitoringModeEnabled =
                                protoInputStream.readBoolean(1133871366147L);
                    }
                    nextField2 = protoInputStream.nextField();
                }
                protoInputStream.end(start2);
                int i = appStartInfoContainer.mUid;
                synchronized (this.mLock) {
                    this.mData.put(str, i, appStartInfoContainer);
                }
            }
            nextField = protoInputStream.nextField();
        }
        protoInputStream.end(start);
    }

    public final void maybeTrimInProgressRecordsLocked() {
        if (this.mInProgressRecords.size() <= 5) {
            return;
        }
        this.mTemporaryInProgressIndexes.clear();
        for (int i = 0; i < this.mInProgressRecords.size(); i++) {
            this.mTemporaryInProgressIndexes.add(i, Integer.valueOf(i));
        }
        Collections.sort(
                this.mTemporaryInProgressIndexes,
                new Comparator() { // from class:
                                   // com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda8
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        AppStartInfoTracker appStartInfoTracker = AppStartInfoTracker.this;
                        return Long.compare(
                                ((Long)
                                                appStartInfoTracker.mInProgressRecords.keyAt(
                                                        ((Integer) obj).intValue()))
                                        .longValue(),
                                ((Long)
                                                appStartInfoTracker.mInProgressRecords.keyAt(
                                                        ((Integer) obj2).intValue()))
                                        .longValue());
                    }
                });
        if (this.mTemporaryInProgressIndexes.size() == 6) {
            this.mInProgressRecords.removeAt(
                    ((Integer) this.mTemporaryInProgressIndexes.get(0)).intValue());
        } else {
            ArrayList arrayList = this.mTemporaryInProgressIndexes;
            arrayList
                    .subList(arrayList.size() - 5, this.mTemporaryInProgressIndexes.size())
                    .clear();
            Collections.sort(this.mTemporaryInProgressIndexes);
            for (int size = this.mTemporaryInProgressIndexes.size() - 1; size >= 0; size--) {
                this.mInProgressRecords.removeAt(
                        ((Integer) this.mTemporaryInProgressIndexes.get(size)).intValue());
            }
        }
        this.mTemporaryInProgressIndexes.clear();
    }

    public final void onActivityLaunched(long j, long j2, ProcessRecord processRecord) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    int indexOfKey = this.mInProgressRecords.indexOfKey(Long.valueOf(j));
                    if (indexOfKey < 0) {
                        return;
                    }
                    ApplicationStartInfo applicationStartInfo =
                            (ApplicationStartInfo) this.mInProgressRecords.valueAt(indexOfKey);
                    if (applicationStartInfo != null && processRecord != null) {
                        applicationStartInfo.setStartType((int) j2);
                        addBaseFieldsFromProcessRecord(applicationStartInfo, processRecord);
                        ApplicationStartInfo addStartInfoLocked =
                                addStartInfoLocked(applicationStartInfo);
                        if (addStartInfoLocked == null) {
                            this.mInProgressRecords.removeAt(indexOfKey);
                        } else {
                            this.mInProgressRecords.setValueAt(indexOfKey, addStartInfoLocked);
                        }
                        return;
                    }
                    this.mInProgressRecords.removeAt(indexOfKey);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onIntentStarted(Intent intent, long j) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    ApplicationStartInfo applicationStartInfo = new ApplicationStartInfo();
                    applicationStartInfo.setStartupState(0);
                    applicationStartInfo.setIntent(intent);
                    applicationStartInfo.setStartType(0);
                    applicationStartInfo.addStartupTimestamp(0, j);
                    if (intent == null
                            || intent.getCategories() == null
                            || !intent.getCategories()
                                    .contains("android.intent.category.LAUNCHER")) {
                        applicationStartInfo.setReason(11);
                    } else {
                        applicationStartInfo.setReason(6);
                    }
                    this.mInProgressRecords.put(Long.valueOf(j), applicationStartInfo);
                    maybeTrimInProgressRecordsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void onPackageRemoved(String str, int i, boolean z) {
        if (this.mEnabled && str != null) {
            TextUtils.isEmpty(this.mService.mPackageManagerInt.getNameForUid(i));
            synchronized (this.mLock) {
                removePackageLocked(z ? -1 : UserHandle.getUserId(i), str);
                schedulePersistProcessStartInfo(true);
            }
        }
    }

    public void onUserRemoved(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    if (i == -1) {
                        this.mData.getMap().clear();
                    } else {
                        forEachPackageLocked(new AppStartInfoTracker$$ExternalSyntheticLambda3(i));
                    }
                    schedulePersistProcessStartInfo(true);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void persistProcessStartInfo() {
        /*
            r8 = this;
            boolean r0 = r8.mEnabled
            if (r0 != 0) goto L5
            return
        L5:
            android.util.AtomicFile r0 = new android.util.AtomicFile
            java.io.File r1 = r8.mProcStartInfoFile
            r0.<init>(r1)
            long r1 = java.lang.System.currentTimeMillis()
            r3 = 0
            java.io.FileOutputStream r4 = r0.startWrite()     // Catch: java.io.IOException -> L47
            android.util.proto.ProtoOutputStream r5 = new android.util.proto.ProtoOutputStream     // Catch: java.io.IOException -> L3f
            r5.<init>(r4)     // Catch: java.io.IOException -> L3f
            r6 = 1112396529665(0x10300000001, double:5.495969098605E-312)
            r5.write(r6, r1)     // Catch: java.io.IOException -> L3f
            java.lang.Object r6 = r8.mLock     // Catch: java.io.IOException -> L3f
            monitor-enter(r6)     // Catch: java.io.IOException -> L3f
            com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda2 r7 = new com.android.server.am.AppStartInfoTracker$$ExternalSyntheticLambda2     // Catch: java.lang.Throwable -> L33
            r7.<init>()     // Catch: java.lang.Throwable -> L33
            boolean r7 = r8.forEachPackageLocked(r7)     // Catch: java.lang.Throwable -> L33
            if (r7 == 0) goto L35
            r8.mLastAppStartInfoPersistTimestamp = r1     // Catch: java.lang.Throwable -> L33
            goto L35
        L33:
            r1 = move-exception
            goto L45
        L35:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L33
            if (r7 == 0) goto L41
            r5.flush()     // Catch: java.io.IOException -> L3f
            r0.finishWrite(r4)     // Catch: java.io.IOException -> L3f
            goto L5f
        L3f:
            r1 = move-exception
            goto L49
        L41:
            r0.failWrite(r4)     // Catch: java.io.IOException -> L3f
            goto L5f
        L45:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> L33
            throw r1     // Catch: java.io.IOException -> L3f
        L47:
            r1 = move-exception
            r4 = r3
        L49:
            java.lang.String r2 = "ActivityManager"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            java.lang.String r6 = "Unable to write historical app start info into persistent storage: "
            r5.<init>(r6)
            r5.append(r1)
            java.lang.String r1 = r5.toString()
            android.util.Slog.w(r2, r1)
            r0.failWrite(r4)
        L5f:
            java.lang.Object r0 = r8.mLock
            monitor-enter(r0)
            r8.mAppStartInfoPersistTask = r3     // Catch: java.lang.Throwable -> L66
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L66
            return
        L66:
            r8 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L66
            throw r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.AppStartInfoTracker.persistProcessStartInfo():void");
    }

    public final void removePackageLocked(int i, String str) {
        ArrayMap map = this.mData.getMap();
        SparseArray sparseArray = (SparseArray) map.get(str);
        if (sparseArray == null) {
            return;
        }
        if (i == -1) {
            this.mData.getMap().remove(str);
            return;
        }
        int size = sparseArray.size() - 1;
        while (true) {
            if (size < 0) {
                break;
            }
            if (UserHandle.getUserId(sparseArray.keyAt(size)) == i) {
                sparseArray.removeAt(size);
                break;
            }
            size--;
        }
        if (sparseArray.size() == 0) {
            map.remove(str);
        }
    }

    public final void removeStartInfoCompleteListener(
            IApplicationStartInfoCompleteListener iApplicationStartInfoCompleteListener,
            int i,
            boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    ArrayList arrayList = (ArrayList) this.mCallbacks.get(i);
                    if (arrayList == null) {
                        return;
                    }
                    int size = arrayList.size();
                    int i2 = 0;
                    while (true) {
                        if (i2 >= size) {
                            break;
                        }
                        ApplicationStartInfoCompleteCallback applicationStartInfoCompleteCallback =
                                (ApplicationStartInfoCompleteCallback) arrayList.get(i2);
                        IApplicationStartInfoCompleteListener
                                iApplicationStartInfoCompleteListener2 =
                                        applicationStartInfoCompleteCallback.mCallback;
                        if (iApplicationStartInfoCompleteListener2
                                != iApplicationStartInfoCompleteListener) {
                            i2++;
                        } else if (z) {
                            iApplicationStartInfoCompleteListener2
                                    .asBinder()
                                    .unlinkToDeath(applicationStartInfoCompleteCallback, 0);
                        }
                    }
                    if (i2 < size) {
                        arrayList.remove(i2);
                    }
                    if (arrayList.isEmpty()) {
                        this.mCallbacks.remove(i);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void schedulePersistProcessStartInfo(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mEnabled) {
                    AppStartInfoTracker$$ExternalSyntheticLambda4
                            appStartInfoTracker$$ExternalSyntheticLambda4 =
                                    this.mAppStartInfoPersistTask;
                    if (appStartInfoTracker$$ExternalSyntheticLambda4 == null || z) {
                        if (appStartInfoTracker$$ExternalSyntheticLambda4 != null) {
                            IoThread.getHandler().removeCallbacks(this.mAppStartInfoPersistTask);
                        }
                        this.mAppStartInfoPersistTask =
                                new AppStartInfoTracker$$ExternalSyntheticLambda4(this, 0);
                        IoThread.getHandler()
                                .postDelayed(
                                        this.mAppStartInfoPersistTask,
                                        z ? 0L : APP_START_INFO_PERSIST_INTERVAL);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
