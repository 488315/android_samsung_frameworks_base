package com.android.server.os;

import android.app.ApplicationExitInfo;
import android.app.IParcelFileDescriptorRetriever;
import android.content.Context;
import android.os.FileObserver;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.util.Slog;
import android.util.SparseArray;

import com.android.internal.util.FrameworkStatsLog;
import com.android.server.ServiceThread;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class NativeTombstoneManager {
    public static final File TOMBSTONE_DIR = new File("/data/tombstones");
    public final Context mContext;
    public final Handler mHandler;
    public final TombstoneWatcher mWatcher;
    public final ReentrantLock mTmpFileLock = new ReentrantLock();
    public final Object mLock = new Object();
    public final SparseArray mTombstones = new SparseArray();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TombstoneFile {
        public int mAppId;
        public String mCrashReason;
        public final ParcelFileDescriptor mPfd;
        public int mPid;
        public String mProcessName;
        public boolean mPurged = false;
        public final ParcelFileDescriptorRetriever mRetriever = new ParcelFileDescriptorRetriever();
        public long mTimestampMs;
        public int mUid;
        public int mUserId;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ParcelFileDescriptorRetriever
                extends IParcelFileDescriptorRetriever.Stub {
            public ParcelFileDescriptorRetriever() {}

            public final ParcelFileDescriptor getPfd() {
                if (TombstoneFile.this.mPurged) {
                    return null;
                }
                try {
                    return ParcelFileDescriptor.open(
                            new File("/proc/self/fd/" + TombstoneFile.this.mPfd.getFd()),
                            268435456);
                } catch (FileNotFoundException e) {
                    File file = NativeTombstoneManager.TOMBSTONE_DIR;
                    Slog.e(
                            "NativeTombstoneManager",
                            "failed to reopen file descriptor as read-only",
                            e);
                    return null;
                }
            }
        }

        public TombstoneFile(ParcelFileDescriptor parcelFileDescriptor) {
            this.mPfd = parcelFileDescriptor;
        }

        public final boolean matches(Optional optional, Optional optional2) {
            if (this.mPurged) {
                return false;
            }
            if (!optional.isPresent() || ((Integer) optional.get()).intValue() == this.mUserId) {
                return !optional2.isPresent()
                        || ((Integer) optional2.get()).intValue() == this.mAppId;
            }
            return false;
        }

        public final ApplicationExitInfo toAppExitInfo() {
            ApplicationExitInfo applicationExitInfo = new ApplicationExitInfo();
            applicationExitInfo.setPid(this.mPid);
            applicationExitInfo.setRealUid(this.mUid);
            applicationExitInfo.setPackageUid(this.mUid);
            applicationExitInfo.setDefiningUid(this.mUid);
            applicationExitInfo.setProcessName(this.mProcessName);
            applicationExitInfo.setReason(5);
            applicationExitInfo.setStatus(0);
            applicationExitInfo.setImportance(1000);
            applicationExitInfo.setPackageName("");
            applicationExitInfo.setProcessStateSummary(null);
            applicationExitInfo.setPss(0L);
            applicationExitInfo.setRss(0L);
            applicationExitInfo.setTimestamp(this.mTimestampMs);
            applicationExitInfo.setDescription(this.mCrashReason);
            applicationExitInfo.setSubReason(0);
            applicationExitInfo.setNativeTombstoneRetriever(this.mRetriever);
            return applicationExitInfo;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TombstoneWatcher extends FileObserver {
        public TombstoneWatcher() {
            super(
                    NativeTombstoneManager.TOMBSTONE_DIR,
                    FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
        }

        @Override // android.os.FileObserver
        public final void onEvent(int i, final String str) {
            if (str != null) {
                NativeTombstoneManager.this.mHandler.post(
                        new Runnable() { // from class:
                                         // com.android.server.os.NativeTombstoneManager$TombstoneWatcher$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                NativeTombstoneManager.TombstoneWatcher tombstoneWatcher =
                                        NativeTombstoneManager.TombstoneWatcher.this;
                                String str2 = str;
                                tombstoneWatcher.getClass();
                                if (str2.endsWith(".tmp")) {
                                    return;
                                }
                                NativeTombstoneManager.this.handleTombstone(
                                        new File(NativeTombstoneManager.TOMBSTONE_DIR, str2));
                            }
                        });
            } else {
                File file = NativeTombstoneManager.TOMBSTONE_DIR;
                Slog.w("NativeTombstoneManager", "path is null at TombstoneWatcher.onEvent()");
            }
        }
    }

    public NativeTombstoneManager(Context context) {
        this.mContext = context;
        ServiceThread serviceThread =
                new ServiceThread(10, "NativeTombstoneManager:tombstoneWatcher", true);
        serviceThread.start();
        this.mHandler = serviceThread.getThreadHandler();
        TombstoneWatcher tombstoneWatcher = new TombstoneWatcher();
        this.mWatcher = tombstoneWatcher;
        tombstoneWatcher.startWatching();
    }

    public final void collectTombstones(
            final ArrayList arrayList, int i, final int i2, final int i3) {
        final CompletableFuture completableFuture = new CompletableFuture();
        if (UserHandle.isApp(i)) {
            final int userId = UserHandle.getUserId(i);
            final int appId = UserHandle.getAppId(i);
            this.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.os.NativeTombstoneManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            boolean z;
                            NativeTombstoneManager nativeTombstoneManager;
                            int i4;
                            NativeTombstoneManager nativeTombstoneManager2 =
                                    NativeTombstoneManager.this;
                            int i5 = userId;
                            int i6 = appId;
                            int i7 = i2;
                            ArrayList arrayList2 = arrayList;
                            int i8 = i3;
                            CompletableFuture completableFuture2 = completableFuture;
                            synchronized (nativeTombstoneManager2.mLock) {
                                try {
                                    int size = nativeTombstoneManager2.mTombstones.size();
                                    int i9 = 0;
                                    z = false;
                                    while (i9 < size) {
                                        NativeTombstoneManager.TombstoneFile tombstoneFile =
                                                (NativeTombstoneManager.TombstoneFile)
                                                        nativeTombstoneManager2.mTombstones.valueAt(
                                                                i9);
                                        if (tombstoneFile.matches(
                                                        Optional.of(Integer.valueOf(i5)),
                                                        Optional.of(Integer.valueOf(i6)))
                                                && (i7 == 0 || tombstoneFile.mPid == i7)) {
                                            int size2 = arrayList2.size();
                                            int i10 = 0;
                                            while (true) {
                                                if (i10 < size2) {
                                                    ApplicationExitInfo applicationExitInfo =
                                                            (ApplicationExitInfo)
                                                                    arrayList2.get(i10);
                                                    nativeTombstoneManager =
                                                            nativeTombstoneManager2;
                                                    if (applicationExitInfo.getReason() == 5
                                                            && applicationExitInfo.getPid()
                                                                    == tombstoneFile.mPid
                                                            && applicationExitInfo.getRealUid()
                                                                    == tombstoneFile.mUid) {
                                                        i4 = i5;
                                                        if (Math.abs(
                                                                        applicationExitInfo
                                                                                        .getTimestamp()
                                                                                - tombstoneFile
                                                                                        .mTimestampMs)
                                                                <= 10000) {
                                                            applicationExitInfo
                                                                    .setNativeTombstoneRetriever(
                                                                            tombstoneFile
                                                                                    .mRetriever);
                                                            break;
                                                        } else {
                                                            i10++;
                                                            i5 = i4;
                                                            nativeTombstoneManager2 =
                                                                    nativeTombstoneManager;
                                                        }
                                                    }
                                                    i4 = i5;
                                                    i10++;
                                                    i5 = i4;
                                                    nativeTombstoneManager2 =
                                                            nativeTombstoneManager;
                                                } else {
                                                    nativeTombstoneManager =
                                                            nativeTombstoneManager2;
                                                    i4 = i5;
                                                    if (arrayList2.size() < i8) {
                                                        arrayList2.add(
                                                                tombstoneFile.toAppExitInfo());
                                                        z = true;
                                                    }
                                                }
                                            }
                                        } else {
                                            nativeTombstoneManager = nativeTombstoneManager2;
                                            i4 = i5;
                                        }
                                        i9++;
                                        i5 = i4;
                                        nativeTombstoneManager2 = nativeTombstoneManager;
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            if (z) {
                                Collections.sort(
                                        arrayList2,
                                        new NativeTombstoneManager$$ExternalSyntheticLambda3());
                            }
                            completableFuture2.complete(null);
                        }
                    });
            try {
                completableFuture.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x01e9  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x026a  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0287  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0273  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void handleTombstone(java.io.File r20) {
        /*
            Method dump skipped, instructions count: 775
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.os.NativeTombstoneManager.handleTombstone(java.io.File):void");
    }
}
