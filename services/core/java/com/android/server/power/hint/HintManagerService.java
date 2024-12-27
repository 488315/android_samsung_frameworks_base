package com.android.server.power.hint;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.StatsManager;
import android.app.UidObserver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.power.ChannelConfig;
import android.hardware.power.IPower;
import android.hardware.power.SessionConfig;
import android.hardware.power.WorkDuration;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IHintManager;
import android.os.IHintSession;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Slog;
import android.util.SparseIntArray;

import com.android.internal.util.ConcurrentUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.Preconditions;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.FgThread;
import com.android.server.LocalServices;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.SystemService;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;

import com.samsung.android.knoxguard.service.utils.Constants;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class HintManagerService extends SystemService {
    static final int CLEAN_UP_UID_DELAY_MILLIS = 1000;
    public final ArrayMap mActiveSessions;
    public final ActivityManagerInternal mAmInternal;
    public final ArrayMap mChannelMap;
    public final Object mChannelMapLock;
    public final CleanUpHandler mCleanUpHandler;
    public final AtomicBoolean mConfigCreationSupport;
    public final Context mContext;
    final long mHintSessionPreferredRate;
    public final Object mLock;
    public final NativeWrapper mNativeWrapper;
    public final Map mNonIsolatedTids;
    public final Object mNonIsolatedTidsLock;
    public final PackageManager mPackageManager;
    public final IPower mPowerHal;
    public final int mPowerHalVersion;
    final IHintManager.Stub mService;
    public int mSystemUiUid;
    final MyUidObserver mUidObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class AppHintSession extends IHintSession.Stub implements IBinder.DeathRecipient {
        public static final /* synthetic */ int $r8$clinit = 0;
        public long mHalSessionPtr;
        public int[] mNewThreadIds;
        public final int mPid;
        public final boolean mShouldForcePauseBySPL;
        public long mTargetDurationNanos;
        public int[] mThreadIds;
        public final IBinder mToken;
        public final int mUid;
        public boolean mUpdateAllowedByProcState = true;
        public boolean mPowerEfficient = false;
        public boolean mShouldForcePause = false;

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m833$$Nest$mdump(
                AppHintSession appHintSession, PrintWriter printWriter) {
            synchronized (appHintSession) {
                try {
                    printWriter.println("    SessionPID: " + appHintSession.mPid);
                    printWriter.println("    SessionUID: " + appHintSession.mUid);
                    printWriter.println(
                            "    SessionTIDs: " + Arrays.toString(appHintSession.mThreadIds));
                    printWriter.println(
                            "    SessionTargetDurationNanos: "
                                    + appHintSession.mTargetDurationNanos);
                    printWriter.println(
                            "    SessionAllowedByProcState: "
                                    + appHintSession.mUpdateAllowedByProcState);
                    printWriter.println(
                            "    SessionForcePaused: " + appHintSession.mShouldForcePause);
                    printWriter.println(
                            "    PowerEfficient: "
                                    .concat(appHintSession.mPowerEfficient ? "true" : "false"));
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public AppHintSession(int i, int i2, int[] iArr, IBinder iBinder, long j, long j2) {
            this.mShouldForcePauseBySPL = false;
            this.mUid = i;
            this.mPid = i2;
            this.mToken = iBinder;
            this.mThreadIds = iArr;
            this.mHalSessionPtr = j;
            this.mTargetDurationNanos = j2;
            updateHintAllowedByProcState(HintManagerService.this.mUidObserver.isUidForeground(i));
            try {
                iBinder.linkToDeath(this, 0);
                int i3 = HintManagerService.this.mSystemUiUid;
                if (i3 <= 0 || i3 != i) {
                    return;
                }
                Slog.d("HintManagerService", "SystemUI make new AppHintSession but force pause");
                this.mShouldForcePauseBySPL = true;
                pause();
            } catch (RemoteException e) {
                HintManagerService.this.mNativeWrapper.halCloseHintSession(this.mHalSessionPtr);
                throw new IllegalStateException("Client already dead", e);
            }
        }

        public static void validateWorkDuration(WorkDuration workDuration) {
            if (workDuration.durationNanos <= 0) {
                throw new IllegalArgumentException(
                        TextUtils.formatSimple(
                                "Actual total duration (%d) should be greater than 0",
                                new Object[] {Long.valueOf(workDuration.durationNanos)}));
            }
            if (workDuration.workPeriodStartTimestampNanos < 0) {
                throw new IllegalArgumentException(
                        TextUtils.formatSimple(
                                "Work period start timestamp (%d) should be greater than 0",
                                new Object[] {
                                    Long.valueOf(workDuration.workPeriodStartTimestampNanos)
                                }));
            }
            long j = workDuration.cpuDurationNanos;
            if (j < 0) {
                throw new IllegalArgumentException(
                        TextUtils.formatSimple(
                                "Actual CPU duration (%d) should be greater than or equal to 0",
                                new Object[] {Long.valueOf(workDuration.cpuDurationNanos)}));
            }
            long j2 = workDuration.gpuDurationNanos;
            if (j2 < 0) {
                throw new IllegalArgumentException(
                        TextUtils.formatSimple(
                                "Actual GPU duration (%d) should greater than or equal to 0",
                                new Object[] {Long.valueOf(workDuration.gpuDurationNanos)}));
            }
            if (j + j2 <= 0) {
                throw new IllegalArgumentException(
                        TextUtils.formatSimple(
                                "The actual CPU duration (%d) and the actual GPU duration (%d)"
                                    + " should not both be 0",
                                new Object[] {
                                    Long.valueOf(workDuration.cpuDurationNanos),
                                    Long.valueOf(workDuration.gpuDurationNanos)
                                }));
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            close();
        }

        public final void close() {
            synchronized (this) {
                try {
                    long j = this.mHalSessionPtr;
                    if (j == 0) {
                        return;
                    }
                    HintManagerService.this.mNativeWrapper.halCloseHintSession(j);
                    this.mHalSessionPtr = 0L;
                    try {
                        this.mToken.unlinkToDeath(this, 0);
                    } catch (NoSuchElementException unused) {
                        Slogf.d(
                                "HintManagerService",
                                "Death link does not exist for session with UID " + this.mUid);
                    }
                    synchronized (HintManagerService.this.mLock) {
                        try {
                            ArrayMap arrayMap =
                                    (ArrayMap)
                                            HintManagerService.this.mActiveSessions.get(
                                                    Integer.valueOf(this.mUid));
                            if (arrayMap == null) {
                                Slogf.w(
                                        "HintManagerService",
                                        "UID %d is not present in active session map",
                                        Integer.valueOf(this.mUid));
                                return;
                            }
                            ArraySet arraySet = (ArraySet) arrayMap.get(this.mToken);
                            if (arraySet == null) {
                                Slogf.w(
                                        "HintManagerService",
                                        "Token %s is not present in token map",
                                        this.mToken.toString());
                                return;
                            }
                            arraySet.remove(this);
                            if (arraySet.isEmpty()) {
                                arrayMap.remove(this.mToken);
                            }
                            if (arrayMap.isEmpty()) {
                                HintManagerService.this.mActiveSessions.remove(
                                        Integer.valueOf(this.mUid));
                            }
                            Flags.powerhintThreadCleanup();
                            synchronized (HintManagerService.this.mNonIsolatedTidsLock) {
                                try {
                                    for (int i : getTidsInternal()) {
                                        if (((HashMap) HintManagerService.this.mNonIsolatedTids)
                                                .containsKey(Integer.valueOf(i))) {
                                            ((Set)
                                                            ((HashMap)
                                                                            HintManagerService.this
                                                                                    .mNonIsolatedTids)
                                                                    .get(Integer.valueOf(i)))
                                                    .remove(Long.valueOf(this.mHalSessionPtr));
                                            if (((Set)
                                                            ((HashMap)
                                                                            HintManagerService.this
                                                                                    .mNonIsolatedTids)
                                                                    .get(Integer.valueOf(i)))
                                                    .isEmpty()) {
                                                ((HashMap) HintManagerService.this.mNonIsolatedTids)
                                                        .remove(Integer.valueOf(i));
                                            }
                                        }
                                    }
                                } finally {
                                }
                            }
                        } finally {
                        }
                    }
                } finally {
                }
            }
        }

        public int[] getTidsInternal() {
            int[] copyOf;
            synchronized (this) {
                try {
                    int[] iArr = this.mNewThreadIds;
                    if (iArr != null) {
                        copyOf = Arrays.copyOf(iArr, iArr.length);
                    } else {
                        int[] iArr2 = this.mThreadIds;
                        copyOf = Arrays.copyOf(iArr2, iArr2.length);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return copyOf;
        }

        public final boolean isHintAllowed() {
            return (this.mHalSessionPtr == 0
                            || !this.mUpdateAllowedByProcState
                            || this.mShouldForcePause
                            || this.mShouldForcePauseBySPL)
                    ? false
                    : true;
        }

        public final void pause() {
            synchronized (this) {
                try {
                    long j = this.mHalSessionPtr;
                    if (j == 0) {
                        return;
                    }
                    HintManagerService.this.mNativeWrapper.halPauseHintSession(j);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void reportActualWorkDuration(long[] jArr, long[] jArr2) {
            synchronized (this) {
                try {
                    if (isHintAllowed()) {
                        boolean z = true;
                        Preconditions.checkArgument(
                                jArr.length != 0, "the count of hint durations shouldn't be 0.");
                        if (jArr.length != jArr2.length) {
                            z = false;
                        }
                        Preconditions.checkArgument(
                                z, "The length of durations and timestamps should be the same.");
                        for (int i = 0; i < jArr.length; i++) {
                            if (jArr[i] <= 0) {
                                throw new IllegalArgumentException(
                                        String.format(
                                                "durations[%d]=%d should be greater than 0",
                                                Integer.valueOf(i), Long.valueOf(jArr[i])));
                            }
                        }
                        HintManagerService.this.mNativeWrapper.halReportActualWorkDuration(
                                this.mHalSessionPtr, jArr, jArr2);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void reportActualWorkDuration2(WorkDuration[] workDurationArr) {
            synchronized (this) {
                try {
                    if (isHintAllowed()) {
                        Preconditions.checkArgument(
                                workDurationArr.length != 0,
                                "the count of work durations shouldn't be 0.");
                        for (WorkDuration workDuration : workDurationArr) {
                            validateWorkDuration(workDuration);
                        }
                        HintManagerService.this.mNativeWrapper.halReportActualWorkDuration(
                                this.mHalSessionPtr, workDurationArr);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void resume() {
            synchronized (this) {
                try {
                    long j = this.mHalSessionPtr;
                    if (j == 0) {
                        return;
                    }
                    HintManagerService.this.mNativeWrapper.halResumeHintSession(j);
                    int[] iArr = this.mNewThreadIds;
                    if (iArr != null) {
                        HintManagerService.this.mNativeWrapper.halSetThreads(
                                this.mHalSessionPtr, iArr);
                        this.mThreadIds = this.mNewThreadIds;
                        this.mNewThreadIds = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void sendHint(int i) {
            synchronized (this) {
                try {
                    if (isHintAllowed()) {
                        Preconditions.checkArgument(
                                i >= 0, "the hint ID value should be greater than zero.");
                        HintManagerService.this.mNativeWrapper.halSendHint(this.mHalSessionPtr, i);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setMode(int i, boolean z) {
            synchronized (this) {
                try {
                    if (isHintAllowed()) {
                        Preconditions.checkArgument(
                                i >= 0, "the mode Id value should be greater than zero.");
                        if (i == 0) {
                            this.mPowerEfficient = z;
                        }
                        HintManagerService.this.mNativeWrapper.halSetMode(
                                this.mHalSessionPtr, i, z);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void setThreadsInternal(int[] iArr, boolean z) {
            long clearCallingIdentity;
            if (iArr.length == 0) {
                throw new IllegalArgumentException("Thread id list can't be empty.");
            }
            synchronized (this) {
                try {
                    if (this.mHalSessionPtr == 0) {
                        return;
                    }
                    if (!this.mUpdateAllowedByProcState) {
                        Slogf.v("HintManagerService", "update hint not allowed, storing tids.");
                        this.mNewThreadIds = iArr;
                        this.mShouldForcePause = false;
                        return;
                    }
                    if (z) {
                        int callingUid = Binder.getCallingUid();
                        int threadGroupLeader =
                                Process.getThreadGroupLeader(Binder.getCallingPid());
                        Flags.powerhintThreadCleanup();
                        IntArray intArray = new IntArray();
                        clearCallingIdentity = Binder.clearCallingIdentity();
                        Integer m831$$Nest$mcheckTidValid =
                                HintManagerService.m831$$Nest$mcheckTidValid(
                                        HintManagerService.this,
                                        callingUid,
                                        threadGroupLeader,
                                        iArr,
                                        intArray);
                        if (m831$$Nest$mcheckTidValid != null) {
                            String m832$$Nest$mformatTidCheckErrMsg =
                                    HintManagerService.m832$$Nest$mformatTidCheckErrMsg(
                                            HintManagerService.this,
                                            callingUid,
                                            iArr,
                                            m831$$Nest$mcheckTidValid);
                            Slogf.w("HintManagerService", m832$$Nest$mformatTidCheckErrMsg);
                            throw new SecurityException(m832$$Nest$mformatTidCheckErrMsg);
                        }
                        Flags.powerhintThreadCleanup();
                        synchronized (HintManagerService.this.mNonIsolatedTidsLock) {
                            try {
                                for (int size = intArray.size() - 1; size >= 0; size--) {
                                    ((HashMap) HintManagerService.this.mNonIsolatedTids)
                                            .putIfAbsent(
                                                    Integer.valueOf(intArray.get(size)),
                                                    new ArraySet());
                                    ((Set)
                                                    ((HashMap)
                                                                    HintManagerService.this
                                                                            .mNonIsolatedTids)
                                                            .get(
                                                                    Integer.valueOf(
                                                                            intArray.get(size))))
                                            .add(Long.valueOf(this.mHalSessionPtr));
                                }
                            } finally {
                            }
                        }
                        Binder.restoreCallingIdentity(clearCallingIdentity);
                    }
                    HintManagerService.this.mNativeWrapper.halSetThreads(this.mHalSessionPtr, iArr);
                    this.mThreadIds = iArr;
                    this.mNewThreadIds = null;
                    if (this.mShouldForcePause) {
                        if (!this.mShouldForcePauseBySPL) {
                            resume();
                        }
                        this.mShouldForcePause = false;
                    }
                } catch (Throwable th) {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                    throw th;
                } finally {
                }
            }
        }

        public boolean updateHintAllowedByProcState(boolean z) {
            synchronized (this) {
                if (z) {
                    try {
                        if (!this.mUpdateAllowedByProcState
                                && !this.mShouldForcePause
                                && !this.mShouldForcePauseBySPL) {
                            resume();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                if (!z && this.mUpdateAllowedByProcState) {
                    pause();
                }
                this.mUpdateAllowedByProcState = z;
            }
            return z;
        }

        public final void updateTargetWorkDuration(long j) {
            synchronized (this) {
                try {
                    if (isHintAllowed()) {
                        Preconditions.checkArgument(
                                j > 0, "Expected the target duration to be greater than 0.");
                        HintManagerService.this.mNativeWrapper.halUpdateTargetWorkDuration(
                                this.mHalSessionPtr, j);
                        this.mTargetDurationNanos = j;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class BinderService extends IHintManager.Stub {
        public BinderService() {}

        public final void closeSessionChannel() {
            if (HintManagerService.this.mPowerHalVersion < 5
                    || !com.android.internal.hidden_from_bootclasspath.android.os.Flags
                            .adpfUseFmqChannel()) {
                return;
            }
            HintManagerService.this.removeChannelItem(
                    Integer.valueOf(Process.getThreadGroupLeader(Binder.getCallingPid())),
                    Integer.valueOf(Binder.getCallingUid()));
        }

        /* JADX WARN: Removed duplicated region for block: B:26:0x013b A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:60:0x00dc A[EXC_TOP_SPLITTER, SYNTHETIC] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final android.os.IHintSession createHintSessionWithConfig(
                android.os.IBinder r29,
                int[] r30,
                long r31,
                int r33,
                android.hardware.power.SessionConfig r34) {
            /*
                Method dump skipped, instructions count: 526
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.power.hint.HintManagerService.BinderService.createHintSessionWithConfig(android.os.IBinder,"
                        + " int[], long, int,"
                        + " android.hardware.power.SessionConfig):android.os.IHintSession");
        }

        public final void dump(
                FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
            if (DumpUtils.checkDumpPermission(
                    HintManagerService.this.getContext(), "HintManagerService", printWriter)) {
                StringBuilder m =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                new StringBuilder("HintSessionPreferredRate: "),
                                HintManagerService.this.mHintSessionPreferredRate,
                                printWriter,
                                "HAL Support: ");
                m.append(HintManagerService.this.mHintSessionPreferredRate != -1);
                printWriter.println(m.toString());
                printWriter.println("Active Sessions:");
                synchronized (HintManagerService.this.mLock) {
                    for (int i = 0; i < HintManagerService.this.mActiveSessions.size(); i++) {
                        try {
                            printWriter.println(
                                    "Uid "
                                            + ((Integer)
                                                            HintManagerService.this.mActiveSessions
                                                                    .keyAt(i))
                                                    .toString()
                                            + ":");
                            ArrayMap arrayMap =
                                    (ArrayMap) HintManagerService.this.mActiveSessions.valueAt(i);
                            for (int i2 = 0; i2 < arrayMap.size(); i2++) {
                                ArraySet arraySet = (ArraySet) arrayMap.valueAt(i2);
                                for (int i3 = 0; i3 < arraySet.size(); i3++) {
                                    printWriter.println("  Session:");
                                    AppHintSession.m833$$Nest$mdump(
                                            (AppHintSession) arraySet.valueAt(i3), printWriter);
                                }
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            }
        }

        public final long getHintSessionPreferredRate() {
            return HintManagerService.this.mHintSessionPreferredRate;
        }

        public final int[] getHintSessionThreadIds(IHintSession iHintSession) {
            int[] copyOf;
            AppHintSession appHintSession = (AppHintSession) iHintSession;
            synchronized (appHintSession) {
                int[] iArr = appHintSession.mThreadIds;
                copyOf = Arrays.copyOf(iArr, iArr.length);
            }
            return copyOf;
        }

        public final ChannelConfig getSessionChannel(IBinder iBinder) {
            ChannelItem channelItem;
            if (HintManagerService.this.mPowerHalVersion < 5
                    || !com.android.internal.hidden_from_bootclasspath.android.os.Flags
                            .adpfUseFmqChannel()) {
                return null;
            }
            Objects.requireNonNull(iBinder);
            int threadGroupLeader = Process.getThreadGroupLeader(Binder.getCallingPid());
            int callingUid = Binder.getCallingUid();
            HintManagerService hintManagerService = HintManagerService.this;
            synchronized (hintManagerService.mChannelMapLock) {
                try {
                    if (!hintManagerService.mChannelMap.containsKey(Integer.valueOf(callingUid))) {
                        hintManagerService.mChannelMap.put(
                                Integer.valueOf(callingUid), new TreeMap());
                    }
                    TreeMap treeMap =
                            (TreeMap)
                                    hintManagerService.mChannelMap.get(Integer.valueOf(callingUid));
                    if (!treeMap.containsKey(Integer.valueOf(threadGroupLeader))) {
                        ChannelItem channelItem2 =
                                hintManagerService
                                .new ChannelItem(threadGroupLeader, callingUid, iBinder);
                        channelItem2.openChannel();
                        treeMap.put(Integer.valueOf(threadGroupLeader), channelItem2);
                    }
                    channelItem = (ChannelItem) treeMap.get(Integer.valueOf(threadGroupLeader));
                } catch (Throwable th) {
                    throw th;
                }
            }
            return channelItem.mConfig;
        }

        public final void setHintSessionThreads(IHintSession iHintSession, int[] iArr) {
            ((AppHintSession) iHintSession).setThreadsInternal(iArr, true);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ChannelItem implements IBinder.DeathRecipient {
        public final int mTgid;
        public final IBinder mToken;
        public final int mUid;
        public boolean mLinked = false;
        public ChannelConfig mConfig = null;

        public ChannelItem(int i, int i2, IBinder iBinder) {
            this.mTgid = i;
            this.mUid = i2;
            this.mToken = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            HintManagerService.this.removeChannelItem(
                    Integer.valueOf(this.mTgid), Integer.valueOf(this.mUid));
        }

        public final void closeChannel() {
            if (this.mLinked) {
                this.mToken.unlinkToDeath(this, 0);
                this.mLinked = false;
            }
            if (this.mConfig != null) {
                try {
                    HintManagerService.this.mPowerHal.closeSessionChannel(this.mTgid, this.mUid);
                    this.mConfig = null;
                } catch (RemoteException e) {
                    throw new IllegalStateException("Failed to close session channel!", e);
                }
            }
        }

        public final void openChannel() {
            if (!this.mLinked) {
                try {
                    this.mToken.linkToDeath(this, 0);
                    this.mLinked = true;
                } catch (RemoteException e) {
                    throw new IllegalStateException("Client already dead", e);
                }
            }
            if (this.mConfig == null) {
                try {
                    this.mConfig =
                            HintManagerService.this.mPowerHal.getSessionChannel(
                                    this.mTgid, this.mUid);
                } catch (RemoteException e2) {
                    HintManagerService.this.removeChannelItem(
                            Integer.valueOf(this.mTgid), Integer.valueOf(this.mUid));
                    throw new IllegalStateException("Failed to create session channel!", e2);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class CleanUpHandler extends Handler {
        public CleanUpHandler(Looper looper) {
            super(looper);
        }

        public final int cleanUpSession(
                AppHintSession appHintSession, SparseIntArray sparseIntArray, int[] iArr) {
            boolean z;
            boolean z2;
            boolean containsKey;
            synchronized (appHintSession) {
                z = appHintSession.mHalSessionPtr == 0;
            }
            if (!z) {
                synchronized (appHintSession) {
                    z2 = appHintSession.mShouldForcePause;
                }
                if (!z2) {
                    int i = appHintSession.mPid;
                    int[] tidsInternal = appHintSession.getTidsInternal();
                    if (iArr.length == 1) {
                        iArr[0] = iArr[0] + tidsInternal.length;
                    }
                    IntArray intArray = new IntArray(tidsInternal.length);
                    for (int i2 : tidsInternal) {
                        if (sparseIntArray.get(i2, 0) == 0) {
                            synchronized (HintManagerService.this.mNonIsolatedTidsLock) {
                                containsKey =
                                        ((HashMap) HintManagerService.this.mNonIsolatedTids)
                                                .containsKey(Integer.valueOf(i2));
                            }
                            if (containsKey) {
                                try {
                                    Process.checkTid(i, i2);
                                } catch (NoSuchElementException unused) {
                                    sparseIntArray.put(i2, 2);
                                } catch (Exception e) {
                                    StringBuilder m =
                                            ArrayUtils$$ExternalSyntheticOutline0.m(
                                                    i2,
                                                    i,
                                                    "Unexpected exception when checking TID ",
                                                    " under PID ",
                                                    "(isolated: ");
                                    m.append(!containsKey);
                                    m.append(")");
                                    Slog.w("HintManagerService", m.toString(), e);
                                    intArray.add(i2);
                                }
                            } else {
                                Process.checkPid(i2);
                            }
                            sparseIntArray.put(i2, 1);
                            intArray.add(i2);
                        } else if (sparseIntArray.get(i2) == 1) {
                            intArray.add(i2);
                        }
                    }
                    int length = tidsInternal.length - intArray.size();
                    if (length > 0) {
                        synchronized (appHintSession) {
                            try {
                                int[] tidsInternal2 = appHintSession.getTidsInternal();
                                if (tidsInternal2.length != tidsInternal.length) {
                                    Slog.d(
                                            "HintManagerService",
                                            "Skipped cleaning up the session as new tids are"
                                                + " added");
                                    return length;
                                }
                                Arrays.sort(tidsInternal2);
                                Arrays.sort(tidsInternal);
                                if (!Arrays.equals(tidsInternal2, tidsInternal)) {
                                    Slog.d(
                                            "HintManagerService",
                                            "Skipped cleaning up the session as new tids are"
                                                + " updated");
                                    return length;
                                }
                                Slog.d(
                                        "HintManagerService",
                                        "Cleaned up "
                                                + length
                                                + " invalid tids for session "
                                                + appHintSession.mHalSessionPtr
                                                + " with UID "
                                                + appHintSession.mUid
                                                + "\n\tbefore: "
                                                + Arrays.toString(tidsInternal)
                                                + "\n\tafter: "
                                                + intArray);
                                int[] array = intArray.toArray();
                                if (array.length == 0) {
                                    appHintSession.mShouldForcePause = true;
                                    if (appHintSession.mUpdateAllowedByProcState) {
                                        appHintSession.pause();
                                    }
                                } else {
                                    appHintSession.setThreadsInternal(array, false);
                                }
                            } finally {
                            }
                        }
                    }
                    return length;
                }
            }
            return 0;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 3) {
                if (hasEqualMessages(i, message.obj)) {
                    removeEqualMessages(message.what, message.obj);
                    sendMessageDelayed(obtainMessage(message.what, message.obj), 1000L);
                    Slog.d("HintManagerService", "Duplicate messages for " + message.obj);
                    return;
                }
                Slog.d("HintManagerService", "Starts cleaning for " + message.obj);
                Integer num = (Integer) message.obj;
                int intValue = num.intValue();
                boolean isUidForeground =
                        HintManagerService.this.mUidObserver.isUidForeground(intValue);
                synchronized (HintManagerService.this.mLock) {
                    try {
                        ArrayMap arrayMap =
                                (ArrayMap) HintManagerService.this.mActiveSessions.get(num);
                        if (arrayMap != null && !arrayMap.isEmpty()) {
                            ArrayList arrayList = new ArrayList(arrayMap.size());
                            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                                ArraySet arraySet = (ArraySet) arrayMap.valueAt(size);
                                for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                                    arrayList.add((AppHintSession) arraySet.valueAt(size2));
                                }
                            }
                            int size3 = arrayList.size();
                            long[] jArr = new long[size3];
                            int size4 = arrayList.size();
                            int[] iArr = new int[size4];
                            SparseIntArray sparseIntArray = new SparseIntArray();
                            int[] iArr2 = new int[1];
                            for (int size5 = arrayList.size() - 1; size5 >= 0; size5--) {
                                AppHintSession appHintSession =
                                        (AppHintSession) arrayList.get(size5);
                                long nanoTime = System.nanoTime();
                                try {
                                    int cleanUpSession =
                                            cleanUpSession(appHintSession, sparseIntArray, iArr2);
                                    long nanoTime2 = System.nanoTime() - nanoTime;
                                    iArr[size5] = cleanUpSession;
                                    jArr[size5] = nanoTime2;
                                } catch (Exception unused) {
                                    StringBuilder sb =
                                            new StringBuilder("Failed to clean up session ");
                                    sb.append(appHintSession.mHalSessionPtr);
                                    sb.append(" for UID ");
                                    VaultKeeperService$$ExternalSyntheticOutline0.m(
                                            sb, appHintSession.mUid, "HintManagerService");
                                }
                            }
                            int size6 = arrayList.size();
                            int i2 = iArr2[0];
                            int i3 = 0;
                            int i4 = Integer.MIN_VALUE;
                            for (int i5 = 0; i5 < size4; i5++) {
                                int i6 = iArr[i5];
                                i3 += i6;
                                i4 = Math.max(i4, i6);
                            }
                            if (i3 > 0) {
                                Arrays.sort(jArr);
                                long j = 0;
                                for (int i7 = 0; i7 < size3; i7++) {
                                    j += jArr[i7];
                                }
                                TimeUnit timeUnit = TimeUnit.NANOSECONDS;
                                int micros = (int) timeUnit.toMicros(j);
                                int micros2 = (int) timeUnit.toMicros(jArr[size3 - 1]);
                                int micros3 = (int) timeUnit.toMicros(jArr[0]);
                                int micros4 = (int) timeUnit.toMicros(j / size3);
                                int micros5 = (int) timeUnit.toMicros(jArr[(int) (size3 * 0.9d)]);
                                FrameworkStatsLog.write(
                                        FrameworkStatsLog.ADPF_HINT_SESSION_TID_CLEANUP,
                                        intValue,
                                        micros,
                                        micros2,
                                        i2,
                                        i3,
                                        i4,
                                        size6,
                                        isUidForeground);
                                StringBuilder m =
                                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                                intValue,
                                                micros,
                                                "Invalid tid found for UID",
                                                " in ",
                                                "us:\n\tcount( session: ");
                                ServiceKeeper$$ExternalSyntheticOutline0.m(
                                        size6, i2, " totalTid: ", " maxInvalidTid: ", m);
                                ServiceKeeper$$ExternalSyntheticOutline0.m(
                                        i4,
                                        i3,
                                        " totalInvalidTid: ",
                                        ")\n\ttime per session( min: ",
                                        m);
                                ServiceKeeper$$ExternalSyntheticOutline0.m(
                                        micros3, micros2, "us max: ", "us avg: ", m);
                                ServiceKeeper$$ExternalSyntheticOutline0.m(
                                        micros4, micros5, "us 90%: ", "us)\n\tisForeground: ", m);
                                m.append(isUidForeground);
                                Slog.w("HintManagerService", m.toString());
                            }
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    class Injector {}

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class MyUidObserver extends UidObserver {
        public final SparseIntArray mProcStatesCache = new SparseIntArray();

        public MyUidObserver() {}

        public final boolean isUidForeground(int i) {
            boolean z;
            synchronized (HintManagerService.this.mLock) {
                z = this.mProcStatesCache.get(i, 6) <= 6;
            }
            return z;
        }

        public final void onUidGone(final int i, boolean z) {
            FgThread.getHandler()
                    .post(
                            new Runnable() { // from class:
                                             // com.android.server.power.hint.HintManagerService$MyUidObserver$$ExternalSyntheticLambda1
                                @Override // java.lang.Runnable
                                public final void run() {
                                    HintManagerService.MyUidObserver myUidObserver =
                                            HintManagerService.MyUidObserver.this;
                                    int i2 = i;
                                    synchronized (HintManagerService.this.mLock) {
                                        try {
                                            myUidObserver.mProcStatesCache.delete(i2);
                                            ArrayMap arrayMap =
                                                    (ArrayMap)
                                                            HintManagerService.this.mActiveSessions
                                                                    .get(Integer.valueOf(i2));
                                            if (arrayMap == null) {
                                                return;
                                            }
                                            Slog.d("HintManagerService", "Uid gone for " + i2);
                                            for (int size = arrayMap.size() + (-1);
                                                    size >= 0;
                                                    size--) {
                                                ArraySet arraySet =
                                                        (ArraySet) arrayMap.valueAt(size);
                                                for (int size2 = arraySet.size() - 1;
                                                        size2 >= 0;
                                                        size2--) {
                                                    ((HintManagerService.AppHintSession)
                                                                    arraySet.valueAt(size2))
                                                            .close();
                                                }
                                            }
                                            synchronized (HintManagerService.this.mChannelMapLock) {
                                                try {
                                                    TreeMap treeMap =
                                                            (TreeMap)
                                                                    HintManagerService.this
                                                                            .mChannelMap.get(
                                                                            Integer.valueOf(i2));
                                                    if (treeMap != null) {
                                                        Iterator it = treeMap.entrySet().iterator();
                                                        while (it.hasNext()) {
                                                            ((HintManagerService.ChannelItem)
                                                                            ((Map.Entry) it.next())
                                                                                    .getValue())
                                                                    .closeChannel();
                                                        }
                                                        HintManagerService.this.mChannelMap.remove(
                                                                Integer.valueOf(i2));
                                                    }
                                                } finally {
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            });
        }

        public final void onUidStateChanged(final int i, final int i2, long j, int i3) {
            FgThread.getHandler()
                    .post(
                            new Runnable() { // from class:
                                             // com.android.server.power.hint.HintManagerService$MyUidObserver$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    HintManagerService.MyUidObserver myUidObserver =
                                            HintManagerService.MyUidObserver.this;
                                    int i4 = i;
                                    int i5 = i2;
                                    synchronized (HintManagerService.this.mLock) {
                                        try {
                                            boolean z = false;
                                            if (HintManagerService.this.mPowerHalVersion >= 4) {
                                                Flags.powerhintThreadCleanup();
                                                if (myUidObserver.mProcStatesCache.get(
                                                                        i4, Integer.MAX_VALUE)
                                                                <= 6
                                                        && i5 > 6) {
                                                    z = true;
                                                }
                                            }
                                            myUidObserver.mProcStatesCache.put(i4, i5);
                                            ArrayMap arrayMap =
                                                    (ArrayMap)
                                                            HintManagerService.this.mActiveSessions
                                                                    .get(Integer.valueOf(i4));
                                            if (arrayMap == null) {
                                                return;
                                            }
                                            if (z) {
                                                Flags.powerhintThreadCleanup();
                                                HintManagerService.this.mCleanUpHandler
                                                        .sendMessageDelayed(
                                                                HintManagerService.this
                                                                        .mCleanUpHandler
                                                                        .obtainMessage(
                                                                                3,
                                                                                Integer.valueOf(
                                                                                        i4)),
                                                                1000L);
                                                Slog.d(
                                                        "HintManagerService",
                                                        "Sent cleanup message for uid " + i4);
                                            }
                                            boolean isUidForeground =
                                                    myUidObserver.isUidForeground(i4);
                                            for (int size = arrayMap.size() - 1;
                                                    size >= 0;
                                                    size--) {
                                                ArraySet arraySet =
                                                        (ArraySet) arrayMap.valueAt(size);
                                                for (int size2 = arraySet.size() - 1;
                                                        size2 >= 0;
                                                        size2--) {
                                                    ((HintManagerService.AppHintSession)
                                                                    arraySet.valueAt(size2))
                                                            .updateHintAllowedByProcState(
                                                                    isUidForeground);
                                                }
                                            }
                                        } finally {
                                        }
                                    }
                                }
                            });
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class NativeWrapper {
        private static native void nativeCloseHintSession(long j);

        private static native long nativeCreateHintSession(int i, int i2, int[] iArr, long j);

        private static native long nativeCreateHintSessionWithConfig(
                int i, int i2, int[] iArr, long j, int i3, SessionConfig sessionConfig);

        private static native long nativeGetHintSessionPreferredRate();

        private native void nativeInit();

        private static native void nativePauseHintSession(long j);

        private static native void nativeReportActualWorkDuration(
                long j, long[] jArr, long[] jArr2);

        private static native void nativeReportActualWorkDuration(
                long j, WorkDuration[] workDurationArr);

        private static native void nativeResumeHintSession(long j);

        private static native void nativeSendHint(long j, int i);

        private static native void nativeSetMode(long j, int i, boolean z);

        private static native void nativeSetThreads(long j, int[] iArr);

        private static native void nativeUpdateTargetWorkDuration(long j, long j2);

        public final void halCloseHintSession(long j) {
            nativeCloseHintSession(j);
        }

        public final long halCreateHintSession(int i, int i2, int[] iArr, long j) {
            return nativeCreateHintSession(i, i2, iArr, j);
        }

        public final long halCreateHintSessionWithConfig(
                int i, int i2, int[] iArr, long j, int i3, SessionConfig sessionConfig) {
            return nativeCreateHintSessionWithConfig(i, i2, iArr, j, i3, sessionConfig);
        }

        public final long halGetHintSessionPreferredRate() {
            return nativeGetHintSessionPreferredRate();
        }

        public final void halInit() {
            nativeInit();
        }

        public final void halPauseHintSession(long j) {
            nativePauseHintSession(j);
        }

        public final void halReportActualWorkDuration(long j, long[] jArr, long[] jArr2) {
            nativeReportActualWorkDuration(j, jArr, jArr2);
        }

        public final void halReportActualWorkDuration(long j, WorkDuration[] workDurationArr) {
            nativeReportActualWorkDuration(j, workDurationArr);
        }

        public final void halResumeHintSession(long j) {
            nativeResumeHintSession(j);
        }

        public final void halSendHint(long j, int i) {
            nativeSendHint(j, i);
        }

        public final void halSetMode(long j, int i, boolean z) {
            nativeSetMode(j, i, z);
        }

        public final void halSetThreads(long j, int[] iArr) {
            nativeSetThreads(j, iArr);
        }

        public final void halUpdateTargetWorkDuration(long j, long j2) {
            nativeUpdateTargetWorkDuration(j, j2);
        }
    }

    /* renamed from: -$$Nest$mcheckTidValid, reason: not valid java name */
    public static Integer m831$$Nest$mcheckTidValid(
            HintManagerService hintManagerService, int i, int i2, int[] iArr, IntArray intArray) {
        hintManagerService.getClass();
        List list = null;
        for (int i3 = 0; i3 < iArr.length; i3++) {
            int i4 = iArr[i3];
            long[] jArr = new long[2];
            Process.readProcLines(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(i4, "/proc/", "/status"),
                    new String[] {"Uid:", "Tgid:"},
                    jArr);
            int i5 = (int) jArr[0];
            int i6 = (int) jArr[1];
            if (i6 == i2) {
                intArray.add(i4);
            } else {
                if (i5 != i) {
                    if ((list != null
                                    || (i != 1000
                                            && (list =
                                                            hintManagerService.mAmInternal
                                                                    .getIsolatedProcesses(i))
                                                    != null))
                            && list.contains(Integer.valueOf(i6))) {}
                    return Integer.valueOf(i4);
                }
                continue;
            }
        }
        return null;
    }

    /* renamed from: -$$Nest$mformatTidCheckErrMsg, reason: not valid java name */
    public static String m832$$Nest$mformatTidCheckErrMsg(
            HintManagerService hintManagerService, int i, int[] iArr, Integer num) {
        hintManagerService.getClass();
        return "Tid"
                + num
                + " from list "
                + Arrays.toString(iArr)
                + " doesn't belong to the calling application "
                + i;
    }

    public HintManagerService(Context context) {
        this(context, new Injector());
    }

    public HintManagerService(Context context, Injector injector) {
        super(context);
        this.mLock = new Object();
        this.mChannelMapLock = new Object();
        this.mNonIsolatedTidsLock = new Object();
        this.mConfigCreationSupport = new AtomicBoolean(true);
        this.mService = new BinderService();
        this.mSystemUiUid = -1;
        this.mContext = context;
        Flags.powerhintThreadCleanup();
        ServiceThread serviceThread = new ServiceThread(19, "HintManagerService", true);
        serviceThread.start();
        this.mCleanUpHandler = new CleanUpHandler(serviceThread.getLooper());
        this.mNonIsolatedTids = new HashMap();
        Flags.adpfSessionTag();
        this.mPackageManager = context.getPackageManager();
        this.mActiveSessions = new ArrayMap();
        this.mChannelMap = new ArrayMap();
        injector.getClass();
        NativeWrapper nativeWrapper = new NativeWrapper();
        this.mNativeWrapper = nativeWrapper;
        nativeWrapper.halInit();
        this.mHintSessionPreferredRate = nativeWrapper.halGetHintSessionPreferredRate();
        this.mUidObserver = new MyUidObserver();
        ActivityManagerInternal activityManagerInternal =
                (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        Objects.requireNonNull(activityManagerInternal);
        this.mAmInternal = activityManagerInternal;
        IPower asInterface =
                IPower.Stub.asInterface(
                        ServiceManager.waitForDeclaredService(IPower.DESCRIPTOR + "/default"));
        this.mPowerHal = asInterface;
        this.mPowerHalVersion = 0;
        if (asInterface != null) {
            try {
                this.mPowerHalVersion = asInterface.getInterfaceVersion();
            } catch (RemoteException e) {
                throw new IllegalStateException("Could not contact PowerHAL!", e);
            }
        }
    }

    public IHintManager.Stub getBinderServiceInstance() {
        return this.mService;
    }

    public Boolean hasChannel(int i, int i2) {
        synchronized (this.mChannelMapLock) {
            try {
                TreeMap treeMap = (TreeMap) this.mChannelMap.get(Integer.valueOf(i2));
                if (treeMap != null) {
                    return Boolean.valueOf(((ChannelItem) treeMap.get(Integer.valueOf(i))) != null);
                }
                return Boolean.FALSE;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            Slogf.v("HintManagerService", "Initializing HintManager service...");
            try {
                ActivityManager.getService()
                        .registerUidObserver(this.mUidObserver, 3, -1, (String) null);
            } catch (RemoteException unused) {
            }
        }
        if (i == 1000) {
            ((StatsManager) this.mContext.getSystemService(StatsManager.class))
                    .setPullAtomCallback(
                            FrameworkStatsLog.ADPF_SYSTEM_COMPONENT_INFO,
                            (StatsManager.PullAtomMetadata) null,
                            ConcurrentUtils.DIRECT_EXECUTOR,
                            new StatsManager
                                    .StatsPullAtomCallback() { // from class:
                                                               // com.android.server.power.hint.HintManagerService$$ExternalSyntheticLambda0
                                public final int onPullAtom(int i2, List list) {
                                    HintManagerService.this.getClass();
                                    if (i2 == 10173) {
                                        list.add(
                                                FrameworkStatsLog.buildStatsEvent(
                                                        FrameworkStatsLog
                                                                .ADPF_SYSTEM_COMPONENT_INFO,
                                                        SystemProperties.getBoolean(
                                                                "debug.sf.enable_adpf_cpu_hint",
                                                                false),
                                                        SystemProperties.getBoolean(
                                                                "debug.hwui.use_hint_manager",
                                                                false)));
                                    }
                                    return 0;
                                }
                            });
            PackageManager packageManager = this.mContext.getPackageManager();
            if (packageManager != null) {
                try {
                    this.mSystemUiUid =
                            packageManager.getPackageUid(Constants.SYSTEMUI_PACKAGE_NAME, 0);
                } catch (PackageManager.NameNotFoundException unused2) {
                    Slog.d("HintManagerService", "com.android.systemui is not found in pm");
                }
            }
            if (this.mSystemUiUid > 0) {
                try {
                    synchronized (this.mLock) {
                        try {
                            ArrayMap arrayMap =
                                    (ArrayMap)
                                            this.mActiveSessions.get(
                                                    Integer.valueOf(this.mSystemUiUid));
                            if (arrayMap == null) {
                                return;
                            }
                            Slog.d("HintManagerService", "SystemUI ADPF Force Pause");
                            for (int size = arrayMap.size() - 1; size >= 0; size--) {
                                ArraySet arraySet = (ArraySet) arrayMap.valueAt(size);
                                for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                                    AppHintSession appHintSession =
                                            (AppHintSession) arraySet.valueAt(size2);
                                    int i2 = AppHintSession.$r8$clinit;
                                    appHintSession.pause();
                                }
                            }
                        } finally {
                        }
                    }
                } catch (Exception unused3) {
                    Slog.d("HintManagerService", "com.android.systemui exception ADPF Force Pause");
                }
            }
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        publishBinderService("performance_hint", this.mService);
    }

    public final void removeChannelItem(Integer num, Integer num2) {
        synchronized (this.mChannelMapLock) {
            try {
                TreeMap treeMap = (TreeMap) this.mChannelMap.get(num2);
                if (treeMap != null) {
                    ChannelItem channelItem = (ChannelItem) treeMap.get(num);
                    if (channelItem != null) {
                        channelItem.closeChannel();
                        treeMap.remove(num);
                    }
                    if (treeMap.isEmpty()) {
                        this.mChannelMap.remove(num2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
