package com.android.server.am;

import android.content.pm.ApplicationInfo;
import android.os.SystemClock;
import android.util.ArraySet;
import com.android.internal.os.TimeoutRecord;
import com.android.server.wm.WindowProcessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AnrHelper {
    public static final long CONSECUTIVE_ANR_TIME_MS;
    public static final long EXPIRED_REPORT_TIME_MS = TimeUnit.SECONDS.toMillis(10);
    public static final long SELF_ONLY_AFTER_BOOT_MS;
    public static final AnrHelper$$ExternalSyntheticLambda0 sDefaultThreadFactory;
    public static final AnrHelper$$ExternalSyntheticLambda0 sMainProcessDumpThreadFactory;
    public final ArrayList mAnrRecords;
    public final ExecutorService mAuxiliaryTaskExecutor;
    public final ExecutorService mEarlyDumpExecutor;
    public long mLastAnrTimeMs;
    public int mProcessingPid;
    public final AtomicBoolean mRunning;
    public final ActivityManagerService mService;
    public final Set mTempDumpedPids;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnrConsumerThread extends Thread {
        public AnrConsumerThread() {
            super("AnrConsumer");
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0091, code lost:
        
            r4 = android.os.SystemClock.uptimeMillis();
            r6 = r4 - r1.mTimestamp;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x009d, code lost:
        
            if (r6 > com.android.server.am.AnrHelper.EXPIRED_REPORT_TIME_MS) goto L30;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x00a3, code lost:
        
            if (r4 >= com.android.server.am.AnrHelper.SELF_ONLY_AFTER_BOOT_MS) goto L63;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x00a6, code lost:
        
            r1.mTimeoutRecord.mLatencyTracker.anrProcessingStarted();
            r1.mApp.mErrorState.appNotResponding(r1.mActivityShortComponentName, r1.mAppInfo, r1.mParentShortComponentName, r1.mParentProcess, r1.mAboveSystem, r1.mTimeoutRecord, r1.this$0.mAuxiliaryTaskExecutor, r3, r1.mIsContinuousAnr, r1.mFirstPidFilePromise);
         */
        /* JADX WARN: Code restructure failed: missing block: B:27:0x00d0, code lost:
        
            r1.mTimeoutRecord.mLatencyTracker.anrProcessingEnded();
            r8 = android.os.SystemClock.uptimeMillis();
            r2 = new java.lang.StringBuilder("Completed ANR of ");
            r2.append(r1.mApp.processName);
            r2.append(" in ");
            r2.append(r8 - r4);
            r2.append("ms, latency ");
            r2.append(r6);
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x00fd, code lost:
        
            if (r3 == false) goto L35;
         */
        /* JADX WARN: Code restructure failed: missing block: B:29:0x00ff, code lost:
        
            r1 = "ms (expired, only dump ANR app)";
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0103, code lost:
        
            r1 = "ms";
         */
        /* JADX WARN: Code restructure failed: missing block: B:35:0x010b, code lost:
        
            r0 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x010c, code lost:
        
            r1.mTimeoutRecord.mLatencyTracker.anrProcessingEnded();
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x0113, code lost:
        
            throw r0;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00a5, code lost:
        
            r3 = true;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 332
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.AnrHelper.AnrConsumerThread.run():void");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AnrRecord {
        public final boolean mAboveSystem;
        public final String mActivityShortComponentName;
        public final ProcessRecord mApp;
        public final ApplicationInfo mAppInfo;
        public final Future mFirstPidFilePromise;
        public final boolean mIsContinuousAnr;
        public final WindowProcessController mParentProcess;
        public final String mParentShortComponentName;
        public final int mPid;
        public final TimeoutRecord mTimeoutRecord;
        public final long mTimestamp = SystemClock.uptimeMillis();

        public AnrRecord(ProcessRecord processRecord, String str, ApplicationInfo applicationInfo, String str2, WindowProcessController windowProcessController, boolean z, TimeoutRecord timeoutRecord, boolean z2, Future future) {
            this.mApp = processRecord;
            this.mPid = processRecord.mPid;
            this.mActivityShortComponentName = str;
            this.mParentShortComponentName = str2;
            this.mTimeoutRecord = timeoutRecord;
            this.mAppInfo = applicationInfo;
            this.mParentProcess = windowProcessController;
            this.mAboveSystem = z;
            this.mIsContinuousAnr = z2;
            this.mFirstPidFilePromise = future;
        }
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.am.AnrHelper$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.server.am.AnrHelper$$ExternalSyntheticLambda0] */
    static {
        TimeUnit timeUnit = TimeUnit.MINUTES;
        CONSECUTIVE_ANR_TIME_MS = timeUnit.toMillis(2L);
        SELF_ONLY_AFTER_BOOT_MS = timeUnit.toMillis(10L);
        final int i = 0;
        sDefaultThreadFactory = new ThreadFactory() { // from class: com.android.server.am.AnrHelper$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                switch (i) {
                    case 0:
                        return new Thread(runnable, "AnrAuxiliaryTaskExecutor");
                    default:
                        return new Thread(runnable, "AnrMainProcessDumpThread");
                }
            }
        };
        final int i2 = 1;
        sMainProcessDumpThreadFactory = new ThreadFactory() { // from class: com.android.server.am.AnrHelper$$ExternalSyntheticLambda0
            @Override // java.util.concurrent.ThreadFactory
            public final Thread newThread(Runnable runnable) {
                switch (i2) {
                    case 0:
                        return new Thread(runnable, "AnrAuxiliaryTaskExecutor");
                    default:
                        return new Thread(runnable, "AnrMainProcessDumpThread");
                }
            }
        };
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AnrHelper(com.android.server.am.ActivityManagerService r13) {
        /*
            r12 = this;
            java.util.concurrent.ThreadPoolExecutor r8 = new java.util.concurrent.ThreadPoolExecutor
            java.util.concurrent.TimeUnit r9 = java.util.concurrent.TimeUnit.SECONDS
            java.util.concurrent.LinkedBlockingQueue r6 = new java.util.concurrent.LinkedBlockingQueue
            r6.<init>()
            r3 = 10
            r2 = 1
            com.android.server.am.AnrHelper$$ExternalSyntheticLambda0 r7 = com.android.server.am.AnrHelper.sDefaultThreadFactory
            r0 = r8
            r1 = r2
            r5 = r9
            r0.<init>(r1, r2, r3, r5, r6, r7)
            r10 = 1
            r8.allowCoreThreadTimeOut(r10)
            java.util.concurrent.ThreadPoolExecutor r11 = new java.util.concurrent.ThreadPoolExecutor
            java.util.concurrent.LinkedBlockingQueue r6 = new java.util.concurrent.LinkedBlockingQueue
            r6.<init>()
            r3 = 10
            r2 = 2
            com.android.server.am.AnrHelper$$ExternalSyntheticLambda0 r7 = com.android.server.am.AnrHelper.sMainProcessDumpThreadFactory
            r0 = r11
            r1 = r2
            r5 = r9
            r0.<init>(r1, r2, r3, r5, r6, r7)
            r11.allowCoreThreadTimeOut(r10)
            r12.<init>(r13, r8, r11)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.AnrHelper.<init>(com.android.server.am.ActivityManagerService):void");
    }

    public AnrHelper(ActivityManagerService activityManagerService, ExecutorService executorService, ExecutorService executorService2) {
        this.mAnrRecords = new ArrayList();
        this.mTempDumpedPids = Collections.synchronizedSet(new ArraySet());
        this.mRunning = new AtomicBoolean(false);
        this.mLastAnrTimeMs = 0L;
        this.mProcessingPid = -1;
        this.mService = activityManagerService;
        this.mAuxiliaryTaskExecutor = executorService;
        this.mEarlyDumpExecutor = executorService2;
    }

    public final void appNotResponding(ProcessRecord processRecord, TimeoutRecord timeoutRecord) {
        appNotResponding(processRecord, null, null, null, null, false, timeoutRecord, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:43:0x012e A[Catch: all -> 0x011e, TRY_LEAVE, TryCatch #3 {all -> 0x011e, blocks: (B:52:0x010d, B:54:0x0113, B:41:0x0124, B:43:0x012e), top: B:51:0x010d }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void appNotResponding(final com.android.server.am.ProcessRecord r18, java.lang.String r19, final android.content.pm.ApplicationInfo r20, java.lang.String r21, com.android.server.wm.WindowProcessController r22, boolean r23, final com.android.internal.os.TimeoutRecord r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 335
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.am.AnrHelper.appNotResponding(com.android.server.am.ProcessRecord, java.lang.String, android.content.pm.ApplicationInfo, java.lang.String, com.android.server.wm.WindowProcessController, boolean, com.android.internal.os.TimeoutRecord, boolean):void");
    }
}
