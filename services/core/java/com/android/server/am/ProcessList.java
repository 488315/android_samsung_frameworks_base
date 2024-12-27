package com.android.server.am;

import android.app.ActivityManager;
import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.app.IProcessObserver;
import android.app.ProfilerInfo;
import android.app.UidObserver;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ASKSManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ProcessInfo;
import android.graphics.Point;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.net.LocalSocket;
import android.net.NetworkPolicyManager;
import android.os.AppZygote;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.DropBoxManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.os.OomKillRecord;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.provider.DeviceConfig;
import android.system.Os;
import android.system.OsConstants;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.DebugUtils;
import android.util.EventLog;
import android.util.LongArray;
import android.util.LongSparseArray;
import android.util.Pair;
import android.util.PrintWriterPrinter;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import android.util.proto.ProtoUtils;

import com.android.internal.app.ProcessMap;
import com.android.internal.app.procstats.ProcessState;
import com.android.internal.os.Zygote;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.MemInfoReader;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.LocalServices;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.ServiceThread;
import com.android.server.StorageManagerService;
import com.android.server.StorageManagerService$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.pmm.PersonalizedMemoryManager;
import com.android.server.bgslotmanager.BGSlotManager;
import com.android.server.bgslotmanager.BgAppPropManager;
import com.android.server.bgslotmanager.CustomEFKManager;
import com.android.server.bgslotmanager.MemInfoGetter;
import com.android.server.chimera.umr.KernelMemoryProxy$ReclaimerLog;
import com.android.server.compat.CompatChange;
import com.android.server.compat.CompatConfig;
import com.android.server.compat.PlatformCompat;
import com.android.server.net.NetworkManagementService;
import com.android.server.pm.PackageManagerService;
import com.android.server.pm.PackageManagerTracedLock;
import com.android.server.pm.PackageSetting;
import com.android.server.pm.pkg.PackageStateInternal;
import com.android.server.power.stats.PowerStatsUidResolver;
import com.android.server.utils.WatchedSparseIntArray;
import com.android.server.wm.ActivityManagerPerformance;
import com.android.server.wm.ActivityRecord;
import com.android.server.wm.ActivityServiceConnectionsHolder;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.ActivityTaskManagerService;
import com.android.server.wm.SnapshotCache;
import com.android.server.wm.TaskSnapshotCache;
import com.android.server.wm.WindowManagerGlobalLock;
import com.android.server.wm.WindowManagerService;
import com.android.server.wm.WindowProcessController;

import libcore.io.IoUtils;

import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.knoxguard.service.utils.Constants;

import java.com.android.server.am.mars.database.MARsListManager;
import java.io.DataInputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public final class ProcessList {
    static final int NETWORK_STATE_BLOCK = 1;
    static final int NETWORK_STATE_NO_CHANGE = 0;
    static final int NETWORK_STATE_UNBLOCK = 2;
    public ActiveUids mActiveUids;
    public ArrayList mAppDataIsolationAllowlistedApps;
    public long mCachedRestoreLevel;
    public boolean mHaveDisplaySize;
    public ImperceptibleKillRunner mImperceptibleKillRunner;
    public boolean mIsDisplayChanged;
    public ActivityManagerGlobalLock mProcLock;
    public ProcessListSettingsListener mProcessListSettingsListener;
    public LocalSocket mSystemServerSocketForZygote;
    public final long mTotalMemMb;
    public static final int PAGE_SIZE = (int) Os.sysconf(OsConstants._SC_PAGESIZE);
    public static KillHandler sKillHandler = null;
    public static ServiceThread sKillThread = null;
    public static LmkdConnection sLmkdConnection = null;
    public static final int[] sProcStateToProcMem = {
        0, 0, 1, 2, 1, 2, 2, 2, 2, 2, 3, 4, 1, 2, 4, 4, 4, 4, 4, 4
    };
    public static final long[] sFirstAwakePssTimes = {30000, 10000, 20000, 20000, 20000};
    public static final long[] sSameAwakePssTimes = {600000, 60000, 600000, 300000, 600000};
    public static final long[] sFirstAsleepPssTimes = {60000, 20000, 30000, 30000, 60000};
    public static final long[] sSameAsleepPssTimes = {600000, 60000, 600000, 300000, 600000};
    public static final long[] sTestFirstPssTimes = {3000, 3000, 5000, 5000, 5000};
    public static final long[] sTestSamePssTimes = {15000, 10000, 10000, 15000, 15000};
    public ActivityManagerService mService = null;
    public final int[] mOomAdj = {
        0,
        100,
        200,
        FrameworkStatsLog.CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE,
        FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT,
        950
    };
    public final int[] mOomMinFreeLow = {12288, 18432, 24576, 36864, 43008, 49152};
    public final int[] mOomMinFreeHigh = {73728, 92160, 110592, 129024, 147456, 184320};
    public final int[] mOomMinFree = new int[6];
    public int mDisplayWidth = 0;
    public int mDisplayHeight = 0;
    public DynamicHiddenApp mDynamicHiddenApp = null;
    public CustomEFKManager mCustomEFKManager = null;
    public boolean mOomLevelsSet = false;
    public boolean mAppDataIsolationEnabled = false;
    public boolean mVoldAppDataIsolationEnabled = false;
    public final StringBuilder mStringBuilder = new StringBuilder(256);
    volatile long mProcStateSeqCounter = 0;
    public long mProcStartSeqCounter = 0;
    public final LongSparseArray mPendingStarts = new LongSparseArray();
    public final ArrayList mLruProcesses = new ArrayList();
    public int mLruProcessActivityStart = 0;
    public int mLruProcessServiceStart = 0;
    public int mLruSeq = 0;
    public final SparseArray mIsolatedProcesses = new SparseArray();
    public final ProcessMap mAppZygotes = new ProcessMap();
    public final AppStartInfoTracker mAppStartInfoTracker = new AppStartInfoTracker();
    public final SparseArray mSdkSandboxes = new SparseArray();
    public final AppExitInfoTracker mAppExitInfoTracker = new AppExitInfoTracker();
    public final ArrayMap mAppZygoteProcesses = new ArrayMap();
    public final ArraySet mAppsInBackgroundRestricted = new ArraySet();
    public PlatformCompat mPlatformCompat = null;
    public final byte[] mZygoteUnsolicitedMessage = new byte[16];
    public final int[] mZygoteSigChldMessage = new int[3];
    public ActivityManagerPerformance mTaskBooster = null;
    IsolatedUidRange mGlobalIsolatedUids = new IsolatedUidRange(99000, 99999);
    IsolatedUidRangeAllocator mAppIsolatedUidRangeAllocator = new IsolatedUidRangeAllocator(this);
    public final ArrayList mRemovedProcesses = new ArrayList();
    public final ProcessMap mDyingProcesses = new ProcessMap();
    public final RemoteCallbackList mProcessObservers = new RemoteCallbackList();
    public ActivityManagerService.ProcessChangeItem[] mActiveProcessChanges =
            new ActivityManagerService.ProcessChangeItem[5];
    public final ArrayList mPendingProcessChanges = new ArrayList();
    public final ArrayList mAvailProcessChanges = new ArrayList();
    public final Object mProcessChangeLock = new Object();
    public final MyProcessMap mProcessNames = new MyProcessMap();

    /* renamed from: com.android.server.am.ProcessList$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public /* synthetic */ AnonymousClass1() {}

        public void handleOomEvent(OomKillRecord[] oomKillRecordArr) {
            for (OomKillRecord oomKillRecord : oomKillRecordArr) {
                ActivityManagerGlobalLock activityManagerGlobalLock = ProcessList.this.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        ProcessList.this.noteAppKill(
                                oomKillRecord.getPid(), oomKillRecord.getUid(), 3, 30, "oom");
                        oomKillRecord.logKillOccurred();
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            }
        }

        public boolean handleUnsolicitedMessage(DataInputStream dataInputStream, int i) {
            ProcessList processList = ProcessList.this;
            if (i < 4) {
                return false;
            }
            try {
                int readInt = dataInputStream.readInt();
                if (readInt != 6) {
                    if (readInt != 8 || i < 84) {
                        return false;
                    }
                    Pair pair = (Pair) ActiveServices.sNumForegroundServices.get();
                    LmkdStatsReporter.logKillOccurred(
                            dataInputStream,
                            ((Integer) pair.first).intValue(),
                            ((Integer) pair.second).intValue());
                    return true;
                }
                if (i != 12) {
                    return false;
                }
                int readInt2 = dataInputStream.readInt();
                processList
                        .mAppExitInfoTracker
                        .mKillHandler
                        .obtainMessage(4101, readInt2, dataInputStream.readInt())
                        .sendToTarget();
                processList.mService.mKillPolicyManager.calculateLmkdStatus(readInt2);
                PersonalizedMemoryManager.LazyHolder.INSTANCE.onMemoryEvent(
                        processList.mService.mContext,
                        PersonalizedMemoryManager.MemoryEventType.LMKD_KILL);
                return true;
            } catch (IOException unused) {
                Slog.e("ActivityManager", "Invalid buffer data. Failed to log LMK_KILL_OCCURRED");
                return false;
            }
        }

        public boolean onConnect(OutputStream outputStream) {
            Slog.i("ActivityManager", "Connection with lmkd established");
            ProcessList processList = ProcessList.this;
            int[] iArr = processList.mOomAdj;
            try {
                ByteBuffer allocate = ByteBuffer.allocate(4);
                allocate.putInt(3);
                outputStream.write(allocate.array(), 0, allocate.position());
                if (processList.mOomLevelsSet) {
                    ByteBuffer allocate2 = ByteBuffer.allocate(((iArr.length * 2) + 1) * 4);
                    allocate2.putInt(0);
                    for (int i = 0; i < iArr.length; i++) {
                        allocate2.putInt(
                                (processList.mOomMinFree[i] * 1024) / ProcessList.PAGE_SIZE);
                        allocate2.putInt(iArr[i]);
                    }
                    outputStream.write(allocate2.array(), 0, allocate2.position());
                }
                ByteBuffer allocate3 = ByteBuffer.allocate(8);
                allocate3.putInt(5);
                allocate3.putInt(0);
                outputStream.write(allocate3.array(), 0, allocate3.position());
                ByteBuffer allocate4 = ByteBuffer.allocate(8);
                allocate4.putInt(5);
                allocate4.putInt(1);
                outputStream.write(allocate4.array(), 0, allocate4.position());
                return true;
            } catch (IOException unused) {
                return false;
            }
        }
    }

    /* renamed from: com.android.server.am.ProcessList$3, reason: invalid class name */
    public final class AnonymousClass3 implements Comparator {
        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            Pair pair = (Pair) obj;
            Pair pair2 = (Pair) obj2;
            ProcessStateRecord processStateRecord = ((ProcessRecord) pair2.first).mState;
            int i = processStateRecord.mSetAdj;
            ProcessStateRecord processStateRecord2 = ((ProcessRecord) pair.first).mState;
            int i2 = i - processStateRecord2.mSetAdj;
            if (i2 != 0) {
                return i2;
            }
            int i3 = processStateRecord.mSetProcState - processStateRecord2.mSetProcState;
            if (i3 != 0) {
                return i3;
            }
            int intValue = ((Integer) pair2.second).intValue() - ((Integer) pair.second).intValue();
            if (intValue != 0) {
                return intValue;
            }
            return 0;
        }
    }

    public final class ImperceptibleKillRunner extends UidObserver {
        public final ProcStartHandler mHandler;
        public volatile boolean mIdle;
        public IdlenessReceiver mReceiver;
        public boolean mUidObserverEnabled;
        public final SparseArray mWorkItems = new SparseArray();
        public final ProcessMap mLastProcessKillTimes = new ProcessMap();

        public final class IdlenessReceiver extends BroadcastReceiver {
            public IdlenessReceiver() {}

            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context, Intent intent) {
                PowerManager powerManager =
                        (PowerManager)
                                ProcessList.this.mService.mContext.getSystemService(
                                        PowerManager.class);
                String action = intent.getAction();
                action.getClass();
                if (action.equals("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED")) {
                    ImperceptibleKillRunner.this.notifyDeviceIdleness(
                            powerManager.isLightDeviceIdleMode());
                } else if (action.equals("android.os.action.DEVICE_IDLE_MODE_CHANGED")) {
                    ImperceptibleKillRunner.this.notifyDeviceIdleness(
                            powerManager.isDeviceIdleMode());
                }
            }
        }

        public ImperceptibleKillRunner(Looper looper) {
            this.mHandler = new ProcStartHandler(this, looper);
        }

        public final void enqueueLocked(int i, ProcessRecord processRecord, String str) {
            Long l =
                    processRecord.isolated
                            ? null
                            : (Long)
                                    this.mLastProcessKillTimes.get(
                                            processRecord.processName, processRecord.uid);
            if (l == null
                    || SystemClock.uptimeMillis()
                            >= l.longValue() + ActivityManagerConstants.MIN_CRASH_INTERVAL) {
                Bundle bundle = new Bundle();
                bundle.putInt("pid", processRecord.mPid);
                bundle.putInt("uid", processRecord.uid);
                bundle.putLong("timestamp", processRecord.mStartUptime);
                bundle.putString("reason", str);
                bundle.putInt("requester", i);
                List list = (List) this.mWorkItems.get(processRecord.uid);
                if (list == null) {
                    list = new ArrayList();
                    this.mWorkItems.put(processRecord.uid, list);
                }
                list.add(bundle);
                if (this.mReceiver == null) {
                    this.mReceiver = new IdlenessReceiver();
                    IntentFilter intentFilter =
                            new IntentFilter("android.os.action.LIGHT_DEVICE_IDLE_MODE_CHANGED");
                    intentFilter.addAction("android.os.action.DEVICE_IDLE_MODE_CHANGED");
                    ProcessList.this.mService.mContext.registerReceiver(
                            this.mReceiver, intentFilter);
                }
            }
        }

        public final boolean killProcessLocked(
                int i,
                int i2,
                long j,
                String str,
                int i3,
                DropBoxManager dropBoxManager,
                boolean z) {
            ProcessRecord processRecord;
            synchronized (ProcessList.this.mService.mPidsSelfLocked) {
                processRecord = ProcessList.this.mService.mPidsSelfLocked.get(i);
            }
            if (processRecord == null
                    || processRecord.mPid != i
                    || processRecord.uid != i2
                    || processRecord.mStartUptime != j
                    || processRecord.mPkgList.searchEachPackage(
                                    new Function() { // from class:
                                                     // com.android.server.am.ProcessList$ImperceptibleKillRunner$$ExternalSyntheticLambda0
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            if (ProcessList.this.mService.mConstants
                                                    .IMPERCEPTIBLE_KILL_EXEMPT_PACKAGES.contains(
                                                    (String) obj)) {
                                                return Boolean.TRUE;
                                            }
                                            return null;
                                        }
                                    })
                            != null) {
                return true;
            }
            if (ProcessList.this.mService.mConstants.IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES.contains(
                    Integer.valueOf(processRecord.mState.mRepProcState))) {
                return false;
            }
            processRecord.killLocked(13, 15, str, str, true, true);
            if (!processRecord.isolated) {
                this.mLastProcessKillTimes.put(
                        processRecord.processName,
                        processRecord.uid,
                        Long.valueOf(SystemClock.uptimeMillis()));
            }
            if (z) {
                SystemClock.elapsedRealtime();
                StringBuilder sb = new StringBuilder();
                ProcessList.this.mService.appendDropBoxProcessHeaders(
                        processRecord, processRecord.processName, null, sb);
                sb.append("Reason: " + str);
                sb.append("\n");
                sb.append("Requester UID: " + i3);
                sb.append("\n");
                dropBoxManager.addText("imperceptible_app_kill", sb.toString());
            }
            return true;
        }

        public final void notifyDeviceIdleness(boolean z) {
            boolean z2 = this.mIdle != z;
            this.mIdle = z;
            if (z2 && z) {
                ActivityManagerService activityManagerService = ProcessList.this.mService;
                ActivityManagerService.boostPriorityForLockedSection();
                synchronized (activityManagerService) {
                    try {
                        if (this.mWorkItems.size() > 0) {
                            this.mHandler.sendEmptyMessage(0);
                        }
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterLockedSection();
            }
        }

        public final void onUidGone(int i, boolean z) {
            this.mHandler.obtainMessage(1, i, 0).sendToTarget();
        }

        public final void onUidStateChanged(int i, int i2, long j, int i3) {
            this.mHandler.obtainMessage(2, i, i2).sendToTarget();
        }

        public final void registerUidObserverIfNecessaryLocked() {
            if (!this.mUidObserverEnabled && this.mWorkItems.size() > 0) {
                this.mUidObserverEnabled = true;
                ProcessList.this.mService.registerUidObserverForUids(this, 3, -1, "android", null);
            } else if (this.mUidObserverEnabled && this.mWorkItems.size() == 0) {
                this.mUidObserverEnabled = false;
                ProcessList.this.mService.unregisterUidObserver(this);
            }
        }
    }

    public final class IsolatedUidRange {
        public final int mFirstUid;
        public final int mLastUid;
        public int mNextUid;
        public final SparseBooleanArray mUidUsed = new SparseBooleanArray();

        public IsolatedUidRange(int i, int i2) {
            this.mFirstUid = i;
            this.mLastUid = i2;
            this.mNextUid = i;
        }
    }

    public final class IsolatedUidRangeAllocator {
        public final BitSet mAvailableUidRanges;
        public final /* synthetic */ ProcessList this$0;
        public final ProcessMap mAppRanges = new ProcessMap();
        public final int mFirstUid = KnoxCustomManagerService.ONE_UI_VERSION_SEP_VERSION_GAP;
        public final int mNumUidsPerRange = 100;

        public IsolatedUidRangeAllocator(ProcessList processList) {
            BitSet bitSet = new BitSet(90);
            this.mAvailableUidRanges = bitSet;
            bitSet.set(0, 90);
        }
    }

    public final class KillHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i == 4000) {
                Trace.traceBegin(64L, "killProcessGroup");
                KernelMemoryProxy$ReclaimerLog.write("B|killProcessGroup " + message.arg2, false);
                Process.killProcessGroup(message.arg1, message.arg2);
                KernelMemoryProxy$ReclaimerLog.write("E|killProcessGroup", false);
                Trace.traceEnd(64L);
                return;
            }
            if (i != 4001) {
                super.handleMessage(message);
                return;
            }
            final LmkdConnection lmkdConnection = ProcessList.sLmkdConnection;
            synchronized (lmkdConnection.mLmkdSocketLock) {
                try {
                    if (lmkdConnection.mLmkdSocket != null) {
                        return;
                    }
                    LocalSocket openSocket = LmkdConnection.openSocket();
                    if (openSocket == null) {
                        Slog.w(
                                "ActivityManager",
                                "Failed to connect to lowmemorykiller, retry later");
                    } else {
                        try {
                            OutputStream outputStream = openSocket.getOutputStream();
                            InputStream inputStream = openSocket.getInputStream();
                            AnonymousClass1 anonymousClass1 = lmkdConnection.mListener;
                            if (anonymousClass1 == null
                                    || anonymousClass1.onConnect(outputStream)) {
                                lmkdConnection.mLmkdSocket = openSocket;
                                lmkdConnection.mLmkdOutputStream = outputStream;
                                lmkdConnection.mLmkdInputStream = inputStream;
                                lmkdConnection.mMsgQueue.addOnFileDescriptorEventListener(
                                        openSocket.getFileDescriptor(),
                                        5,
                                        new MessageQueue
                                                .OnFileDescriptorEventListener() { // from class:
                                            // com.android.server.am.LmkdConnection.1
                                            public AnonymousClass1() {}

                                            @Override // android.os.MessageQueue.OnFileDescriptorEventListener
                                            public final int onFileDescriptorEvents(
                                                    FileDescriptor fileDescriptor, int i2) {
                                                int i3;
                                                LmkdConnection lmkdConnection2 =
                                                        LmkdConnection.this;
                                                if (lmkdConnection2.mListener == null) {
                                                    return 0;
                                                }
                                                if ((i2 & 1) != 0) {
                                                    ByteBuffer byteBuffer =
                                                            lmkdConnection2.mInputBuf;
                                                    synchronized (lmkdConnection2.mLmkdSocketLock) {
                                                        try {
                                                            try {
                                                                i3 =
                                                                        lmkdConnection2
                                                                                .mLmkdInputStream
                                                                                .read(
                                                                                        byteBuffer
                                                                                                .array(),
                                                                                        0,
                                                                                        byteBuffer
                                                                                                .array()
                                                                                                .length);
                                                            } finally {
                                                            }
                                                        } catch (IOException unused) {
                                                            i3 = -1;
                                                        }
                                                    }
                                                    if (i3 > 0) {
                                                        try {
                                                            lmkdConnection2.mInputData.reset();
                                                            synchronized (
                                                                    lmkdConnection2.mReplyBufLock) {
                                                                try {
                                                                    ByteBuffer byteBuffer2 =
                                                                            lmkdConnection2
                                                                                    .mReplyBuf;
                                                                    if (byteBuffer2 != null) {
                                                                        ProcessList.AnonymousClass1
                                                                                anonymousClass12 =
                                                                                        lmkdConnection2
                                                                                                .mListener;
                                                                        ByteBuffer byteBuffer3 =
                                                                                lmkdConnection2
                                                                                        .mInputBuf;
                                                                        anonymousClass12.getClass();
                                                                        if (i3
                                                                                        == byteBuffer2
                                                                                                .array()
                                                                                                .length
                                                                                && byteBuffer3
                                                                                                .getInt(
                                                                                                        0)
                                                                                        == byteBuffer2
                                                                                                .getInt(
                                                                                                        0)) {
                                                                            lmkdConnection2
                                                                                    .mReplyBuf.put(
                                                                                    lmkdConnection2
                                                                                            .mInputBuf
                                                                                            .array(),
                                                                                    0,
                                                                                    i3);
                                                                            lmkdConnection2
                                                                                    .mReplyBuf
                                                                                    .rewind();
                                                                            lmkdConnection2
                                                                                    .mReplyBufLock
                                                                                    .notifyAll();
                                                                        } else if (!lmkdConnection2
                                                                                .mListener
                                                                                .handleUnsolicitedMessage(
                                                                                        lmkdConnection2
                                                                                                .mInputData,
                                                                                        i3)) {
                                                                            lmkdConnection2
                                                                                            .mReplyBuf =
                                                                                    null;
                                                                            lmkdConnection2
                                                                                    .mReplyBufLock
                                                                                    .notifyAll();
                                                                            Slog.e(
                                                                                    "ActivityManager",
                                                                                    "Received an"
                                                                                        + " unexpected"
                                                                                        + " packet"
                                                                                        + " from"
                                                                                        + " lmkd");
                                                                        }
                                                                    } else if (!lmkdConnection2
                                                                            .mListener
                                                                            .handleUnsolicitedMessage(
                                                                                    lmkdConnection2
                                                                                            .mInputData,
                                                                                    i3)) {
                                                                        Slog.w(
                                                                                "ActivityManager",
                                                                                "Received an"
                                                                                    + " unexpected"
                                                                                    + " packet from"
                                                                                    + " lmkd,"
                                                                                    + " replybuf is"
                                                                                    + " null");
                                                                    }
                                                                } finally {
                                                                }
                                                            }
                                                        } catch (IOException unused2) {
                                                            NandswapManager$$ExternalSyntheticOutline0
                                                                    .m(
                                                                            i3,
                                                                            "Failed to parse lmkd"
                                                                                + " data buffer."
                                                                                + " Size = ",
                                                                            "ActivityManager");
                                                        }
                                                    }
                                                }
                                                if ((i2 & 4) == 0) {
                                                    return 5;
                                                }
                                                synchronized (lmkdConnection2.mLmkdSocketLock) {
                                                    lmkdConnection2.mMsgQueue
                                                            .removeOnFileDescriptorEventListener(
                                                                    lmkdConnection2.mLmkdSocket
                                                                            .getFileDescriptor());
                                                    IoUtils.closeQuietly(
                                                            lmkdConnection2.mLmkdSocket);
                                                    lmkdConnection2.mLmkdSocket = null;
                                                }
                                                synchronized (lmkdConnection2.mReplyBufLock) {
                                                    try {
                                                        if (lmkdConnection2.mReplyBuf != null) {
                                                            lmkdConnection2.mReplyBuf = null;
                                                            lmkdConnection2.mReplyBufLock
                                                                    .notifyAll();
                                                        }
                                                    } finally {
                                                    }
                                                }
                                                lmkdConnection2.mListener.getClass();
                                                Slog.w(
                                                        "ActivityManager",
                                                        "Lost connection to lmkd");
                                                ProcessList.KillHandler killHandler =
                                                        ProcessList.sKillHandler;
                                                killHandler.sendMessageDelayed(
                                                        killHandler.obtainMessage(4001), 1000L);
                                                return 0;
                                            }
                                        });
                                lmkdConnection.mLmkdSocketLock.notifyAll();
                                return;
                            }
                            Slog.w(
                                    "ActivityManager",
                                    "Failed to communicate with lowmemorykiller, retry later");
                            IoUtils.closeQuietly(openSocket);
                        } catch (IOException unused) {
                            IoUtils.closeQuietly(openSocket);
                        }
                    }
                    Slog.i("ActivityManager", "Failed to connect to lmkd, retry after 1000 ms");
                    KillHandler killHandler = ProcessList.sKillHandler;
                    killHandler.sendMessageDelayed(killHandler.obtainMessage(4001), 1000L);
                } finally {
                }
            }
        }
    }

    public final class MyProcessMap extends ProcessMap {
        public MyProcessMap() {}

        public final ProcessRecord put(int i, ProcessRecord processRecord, String str) {
            ProcessRecord processRecord2 = (ProcessRecord) super.put(str, i, processRecord);
            ActivityTaskManagerInternal activityTaskManagerInternal =
                    ProcessList.this.mService.mAtmInternal;
            WindowProcessController windowProcessController =
                    processRecord2.mWindowProcessController;
            ActivityTaskManagerService.LocalService localService =
                    (ActivityTaskManagerService.LocalService) activityTaskManagerInternal;
            synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                ActivityTaskManagerService.this.mProcessNames.put(
                        windowProcessController.mName,
                        windowProcessController.mUid,
                        windowProcessController);
            }
            return processRecord2;
        }

        public final ProcessRecord remove(int i, String str) {
            ProcessRecord processRecord = (ProcessRecord) super.remove(str, i);
            ActivityTaskManagerService.LocalService localService =
                    (ActivityTaskManagerService.LocalService)
                            ProcessList.this.mService.mAtmInternal;
            synchronized (ActivityTaskManagerService.this.mGlobalLockWithoutBoost) {
                try {
                    WindowProcessController windowProcessController =
                            (WindowProcessController)
                                    ActivityTaskManagerService.this.mProcessNames.remove(str, i);
                    if (windowProcessController != null
                            && !ActivityTaskManagerService.this.mStartingProcessActivities
                                    .isEmpty()) {
                        for (int size =
                                        ActivityTaskManagerService.this.mStartingProcessActivities
                                                        .size()
                                                - 1;
                                size >= 0;
                                size--) {
                            ActivityRecord activityRecord =
                                    (ActivityRecord)
                                            ActivityTaskManagerService.this
                                                    .mStartingProcessActivities.get(size);
                            if (i == activityRecord.info.applicationInfo.uid
                                    && str.equals(activityRecord.processName)) {
                                Slog.w(
                                        "ActivityTaskManager",
                                        windowProcessController
                                                + " is removed with pending start "
                                                + activityRecord);
                                ActivityTaskManagerService.this.mStartingProcessActivities.remove(
                                        size);
                                if (activityRecord.isVisibleRequested()) {
                                    activityRecord.finishIfPossible("starting-proc-removed", false);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            return processRecord;
        }
    }

    public final class ProcStartHandler extends Handler {
        public final /* synthetic */ int $r8$classId = 0;
        public final Object mService;

        public ProcStartHandler(ActivityManagerService activityManagerService, Looper looper) {
            super(looper);
            this.mService = activityManagerService;
        }

        public ProcStartHandler(ImperceptibleKillRunner imperceptibleKillRunner, Looper looper) {
            super(looper);
            this.mService = imperceptibleKillRunner;
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            List list;
            switch (this.$r8$classId) {
                case 0:
                    int i = message.what;
                    if (i == 1) {
                        ProcessList processList =
                                ((ActivityManagerService) this.mService).mProcessList;
                        ProcessRecord processRecord = (ProcessRecord) message.obj;
                        int i2 = ProcessList.PAGE_SIZE;
                        processList.getClass();
                        Runnable runnable = processRecord.mSuccessorStartRunnable;
                        if (runnable != null) {
                            processRecord.mSuccessorStartRunnable = null;
                            runnable.run();
                            return;
                        }
                        return;
                    }
                    if (i != 2) {
                        return;
                    }
                    ActivityManagerService activityManagerService =
                            (ActivityManagerService) this.mService;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService) {
                        try {
                            Slog.d(
                                    "ActivityManager_PRED",
                                    "MSG_PROCESS_KILL_TIMEOUT Process = "
                                            + ((ProcessRecord) message.obj));
                            ((ActivityManagerService) this.mService)
                                    .handleProcessStartOrKillTimeoutLocked(
                                            (ProcessRecord) message.obj, true);
                        } finally {
                            ActivityManagerService.resetPriorityAfterLockedSection();
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
                default:
                    int i3 = message.what;
                    if (i3 == 0) {
                        ImperceptibleKillRunner imperceptibleKillRunner =
                                (ImperceptibleKillRunner) this.mService;
                        DropBoxManager dropBoxManager =
                                (DropBoxManager)
                                        ProcessList.this.mService.mContext.getSystemService(
                                                DropBoxManager.class);
                        ActivityManagerService activityManagerService2 = ProcessList.this.mService;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService2) {
                            try {
                                for (int size = imperceptibleKillRunner.mWorkItems.size() - 1;
                                        imperceptibleKillRunner.mIdle && size >= 0;
                                        size--) {
                                    List list2 =
                                            (List) imperceptibleKillRunner.mWorkItems.valueAt(size);
                                    for (int size2 = list2.size() - 1;
                                            imperceptibleKillRunner.mIdle && size2 >= 0;
                                            size2--) {
                                        Bundle bundle = (Bundle) list2.get(size2);
                                        if (imperceptibleKillRunner.killProcessLocked(
                                                bundle.getInt("pid"),
                                                bundle.getInt("uid"),
                                                bundle.getLong("timestamp"),
                                                bundle.getString("reason"),
                                                bundle.getInt("requester"),
                                                dropBoxManager,
                                                false)) {
                                            list2.remove(size2);
                                        }
                                    }
                                    if (list2.size() == 0) {
                                        imperceptibleKillRunner.mWorkItems.removeAt(size);
                                    }
                                }
                                imperceptibleKillRunner.registerUidObserverIfNecessaryLocked();
                            } finally {
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (i3 == 1) {
                        ImperceptibleKillRunner imperceptibleKillRunner2 =
                                (ImperceptibleKillRunner) this.mService;
                        int i4 = message.arg1;
                        ActivityManagerService activityManagerService3 = ProcessList.this.mService;
                        ActivityManagerService.boostPriorityForLockedSection();
                        synchronized (activityManagerService3) {
                            try {
                                imperceptibleKillRunner2.mWorkItems.remove(i4);
                                imperceptibleKillRunner2.registerUidObserverIfNecessaryLocked();
                            } finally {
                                ActivityManagerService.resetPriorityAfterLockedSection();
                            }
                        }
                        ActivityManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                    if (i3 != 2) {
                        return;
                    }
                    ImperceptibleKillRunner imperceptibleKillRunner3 =
                            (ImperceptibleKillRunner) this.mService;
                    int i5 = message.arg1;
                    int i6 = message.arg2;
                    DropBoxManager dropBoxManager2 =
                            (DropBoxManager)
                                    ProcessList.this.mService.mContext.getSystemService(
                                            DropBoxManager.class);
                    boolean z =
                            dropBoxManager2 != null
                                    && dropBoxManager2.isTagEnabled("imperceptible_app_kill");
                    ActivityManagerService activityManagerService4 = ProcessList.this.mService;
                    ActivityManagerService.boostPriorityForLockedSection();
                    synchronized (activityManagerService4) {
                        try {
                            if (imperceptibleKillRunner3.mIdle
                                    && !ProcessList.this.mService.mConstants
                                            .IMPERCEPTIBLE_KILL_EXEMPT_PROC_STATES.contains(
                                            Integer.valueOf(i6))
                                    && (list = (List) imperceptibleKillRunner3.mWorkItems.get(i5))
                                            != null) {
                                for (int size3 = list.size() - 1;
                                        imperceptibleKillRunner3.mIdle && size3 >= 0;
                                        size3--) {
                                    Bundle bundle2 = (Bundle) list.get(size3);
                                    if (imperceptibleKillRunner3.killProcessLocked(
                                            bundle2.getInt("pid"),
                                            bundle2.getInt("uid"),
                                            bundle2.getLong("timestamp"),
                                            bundle2.getString("reason"),
                                            bundle2.getInt("requester"),
                                            dropBoxManager2,
                                            z)) {
                                        list.remove(size3);
                                    }
                                }
                                if (list.size() == 0) {
                                    imperceptibleKillRunner3.mWorkItems.remove(i5);
                                }
                                imperceptibleKillRunner3.registerUidObserverIfNecessaryLocked();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    return;
            }
        }
    }

    public final class ProcStateMemTracker {
        public int mPendingHighestMemState;
        public int mPendingMemState;
        public float mPendingScalingFactor;
        public final int[] mHighestMem = new int[5];
        public final float[] mScalingFactor = new float[5];
        public int mTotalHighestMem = 4;

        public ProcStateMemTracker() {
            for (int i = 0; i < 5; i++) {
                this.mHighestMem[i] = 5;
                this.mScalingFactor[i] = 1.0f;
            }
            this.mPendingMemState = -1;
        }

        public final void dumpLine(PrintWriter printWriter) {
            printWriter.print("best=");
            printWriter.print(this.mTotalHighestMem);
            printWriter.print(" (");
            boolean z = false;
            for (int i = 0; i < 5; i++) {
                int[] iArr = this.mHighestMem;
                if (iArr[i] < 5) {
                    if (z) {
                        printWriter.print(", ");
                    }
                    printWriter.print(i);
                    printWriter.print("=");
                    printWriter.print(iArr[i]);
                    printWriter.print(" ");
                    printWriter.print(this.mScalingFactor[i]);
                    printWriter.print("x");
                    z = true;
                }
            }
            printWriter.print(")");
            if (this.mPendingMemState >= 0) {
                printWriter.print(" / pending state=");
                printWriter.print(this.mPendingMemState);
                printWriter.print(" highest=");
                printWriter.print(this.mPendingHighestMemState);
                printWriter.print(" ");
                printWriter.print(this.mPendingScalingFactor);
                printWriter.print("x");
            }
            printWriter.println();
        }
    }

    public final class ProcessListSettingsListener
            implements DeviceConfig.OnPropertiesChangedListener {
        public final Context mContext;
        public final Object mLock = new Object();
        public boolean mSdkSandboxApplyRestrictionsAudit =
                DeviceConfig.getBoolean(
                        "adservices", "apply_sdk_sandbox_audit_restrictions", false);
        public boolean mSdkSandboxApplyRestrictionsNext =
                DeviceConfig.getBoolean("adservices", "apply_sdk_sandbox_next_restrictions", false);

        public ProcessListSettingsListener(Context context) {
            this.mContext = context;
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onPropertiesChanged(android.provider.DeviceConfig.Properties r8) {
            /*
                r7 = this;
                java.lang.Object r0 = r7.mLock
                monitor-enter(r0)
                java.util.Set r1 = r8.getKeyset()     // Catch: java.lang.Throwable -> L36
                java.util.Iterator r1 = r1.iterator()     // Catch: java.lang.Throwable -> L36
            Lb:
                boolean r2 = r1.hasNext()     // Catch: java.lang.Throwable -> L36
                if (r2 == 0) goto L5d
                java.lang.Object r2 = r1.next()     // Catch: java.lang.Throwable -> L36
                java.lang.String r2 = (java.lang.String) r2     // Catch: java.lang.Throwable -> L36
                if (r2 != 0) goto L1a
                goto Lb
            L1a:
                int r3 = r2.hashCode()     // Catch: java.lang.Throwable -> L36
                r4 = -460166235(0xffffffffe4926ba5, float:-2.160785E22)
                r5 = 1
                r6 = 0
                if (r3 == r4) goto L38
                r4 = 1346273945(0x503e8299, float:1.27849196E10)
                if (r3 == r4) goto L2b
                goto L43
            L2b:
                java.lang.String r3 = "apply_sdk_sandbox_audit_restrictions"
                boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L36
                if (r2 == 0) goto L43
                r2 = r6
                goto L44
            L36:
                r7 = move-exception
                goto L5f
            L38:
                java.lang.String r3 = "apply_sdk_sandbox_next_restrictions"
                boolean r2 = r2.equals(r3)     // Catch: java.lang.Throwable -> L36
                if (r2 == 0) goto L43
                r2 = r5
                goto L44
            L43:
                r2 = -1
            L44:
                if (r2 == 0) goto L53
                if (r2 == r5) goto L49
                goto Lb
            L49:
                java.lang.String r2 = "apply_sdk_sandbox_next_restrictions"
                boolean r2 = r8.getBoolean(r2, r6)     // Catch: java.lang.Throwable -> L36
                r7.mSdkSandboxApplyRestrictionsNext = r2     // Catch: java.lang.Throwable -> L36
                goto Lb
            L53:
                java.lang.String r2 = "apply_sdk_sandbox_audit_restrictions"
                boolean r2 = r8.getBoolean(r2, r6)     // Catch: java.lang.Throwable -> L36
                r7.mSdkSandboxApplyRestrictionsAudit = r2     // Catch: java.lang.Throwable -> L36
                goto Lb
            L5d:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L36
                return
            L5f:
                monitor-exit(r0)     // Catch: java.lang.Throwable -> L36
                throw r7
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.am.ProcessList.ProcessListSettingsListener.onPropertiesChanged(android.provider.DeviceConfig$Properties):void");
        }

        public void unregisterObserver() {
            DeviceConfig.removeOnPropertiesChangedListener(this);
        }
    }

    public ProcessList() {
        MemInfoReader memInfoReader = new MemInfoReader();
        memInfoReader.readMemInfo();
        this.mTotalMemMb = memInfoReader.getTotalSize() / 1048576;
        updateOomLevels(0, 0, false);
    }

    public static void appendRamKb(StringBuilder sb, long j) {
        int i = 0;
        int i2 = 10;
        while (i < 6) {
            if (j < i2) {
                sb.append(' ');
            }
            i++;
            i2 *= 10;
        }
        sb.append(j);
    }

    public static String buildOomTag(
            String str, int i, String str2, String str3, int i2, boolean z) {
        int i3 = i - i2;
        if (i3 == 0) {
            return z ? str2 : str3 == null ? str : str.concat(str3);
        }
        if (i3 >= 10) {
            return ProcessList$$ExternalSyntheticOutline0.m(
                    Preconditions$$ExternalSyntheticOutline0.m(str, "+"), i3);
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(str);
        m.append(z ? "+" : "+ ");
        m.append(Integer.toString(i3));
        return m.toString();
    }

    public static boolean checkFreezerExempt(ProcessRecord processRecord) {
        Set set;
        if (processRecord.mOptRecord.mFreezeExempt) {
            return false;
        }
        MARsListManager mARsListManager = MARsListManager.ListManagerHolder.INSTANCE;
        synchronized (mARsListManager) {
            set = mARsListManager.mGoogleFreezerExemptionList;
        }
        Iterator it = ((HashSet) set).iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str.equals(processRecord.info.packageName)
                    || str.equals(processRecord.info.processName)
                    || str.equals(processRecord.processName)) {
                return true;
            }
        }
        return false;
    }

    public static void checkSlow(long j, String str) {
        long uptimeMillis = SystemClock.uptimeMillis() - j;
        if (uptimeMillis > 50) {
            Slog.w(
                    "ActivityManager",
                    "Slow operation: " + uptimeMillis + "ms so far, now at " + str);
        }
    }

    public static int[] computeGidsForProcess(int i, int i2, int[] iArr, boolean z) {
        ArrayList arrayList = new ArrayList(iArr.length + 5);
        int sharedAppGid = UserHandle.getSharedAppGid(UserHandle.getAppId(i2));
        int cacheAppGid = UserHandle.getCacheAppGid(UserHandle.getAppId(i2));
        int userGid = UserHandle.getUserGid(UserHandle.getUserId(i2));
        for (int i3 : iArr) {
            arrayList.add(Integer.valueOf(i3));
        }
        if (sharedAppGid != -1) {
            arrayList.add(Integer.valueOf(sharedAppGid));
        }
        if (cacheAppGid != -1) {
            arrayList.add(Integer.valueOf(cacheAppGid));
        }
        if (userGid != -1) {
            arrayList.add(Integer.valueOf(userGid));
        }
        if (i == 4 || i == 3) {
            arrayList.add(Integer.valueOf(UserHandle.getUid(UserHandle.getUserId(i2), 1015)));
            arrayList.add(1078);
            arrayList.add(1079);
        }
        if (i == 2) {
            arrayList.add(1079);
        }
        if (i == 3) {
            arrayList.add(1023);
            if (UserHandle.getUserId(i2) == 0) {
                arrayList.add(Integer.valueOf(UserHandle.getUid(95, 1023)));
                arrayList.add(Integer.valueOf(UserHandle.getUserGid(95)));
                arrayList.add(Integer.valueOf(UserHandle.getUid(96, 1023)));
                arrayList.add(Integer.valueOf(UserHandle.getUserGid(96)));
                arrayList.add(Integer.valueOf(UserHandle.getUid(97, 1023)));
                arrayList.add(Integer.valueOf(UserHandle.getUserGid(97)));
                arrayList.add(Integer.valueOf(UserHandle.getUid(98, 1023)));
                arrayList.add(Integer.valueOf(UserHandle.getUserGid(98)));
                arrayList.add(Integer.valueOf(UserHandle.getUid(99, 1023)));
                arrayList.add(Integer.valueOf(UserHandle.getUserGid(99)));
            }
        }
        if (z) {
            arrayList.add(1077);
        }
        int size = arrayList.size();
        int[] iArr2 = new int[size];
        for (int i4 = 0; i4 < size; i4++) {
            iArr2[i4] = ((Integer) arrayList.get(i4)).intValue();
        }
        return iArr2;
    }

    public static void dumpLruEntryLocked(
            PrintWriter printWriter, int i, ProcessRecord processRecord, String str) {
        printWriter.print(str);
        printWriter.print('#');
        if (i < 10) {
            printWriter.print(' ');
        }
        printWriter.print(i);
        printWriter.print(": ");
        boolean z = false;
        printWriter.print(makeOomAdjString(processRecord.mState.mSetAdj, false));
        printWriter.print(' ');
        printWriter.print(ActivityManager.procStateToString(processRecord.mState.mCurProcState));
        printWriter.print(' ');
        ActivityManager.printCapabilitiesSummary(printWriter, processRecord.mState.mCurCapability);
        printWriter.print(' ');
        printWriter.print(processRecord.toShortString());
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        if (processRecord.hasActivitiesOrRecentTasks()
                || processServiceRecord.mHasClientActivities
                || processServiceRecord.mTreatLikeActivity) {
            printWriter.print(" act:");
            boolean z2 = true;
            if (processRecord.mWindowProcessController.mHasActivities) {
                printWriter.print("activities");
                z = true;
            }
            if (processRecord.mWindowProcessController.mHasRecentTasks) {
                if (z) {
                    printWriter.print("|");
                }
                printWriter.print("recents");
                z = true;
            }
            if (processServiceRecord.mHasClientActivities) {
                if (z) {
                    printWriter.print("|");
                }
                printWriter.print("client");
            } else {
                z2 = z;
            }
            if (processServiceRecord.mTreatLikeActivity) {
                if (z2) {
                    printWriter.print("|");
                }
                printWriter.print("treated");
            }
        }
        printWriter.println();
    }

    public static void dumpProcessOomList(
            PrintWriter printWriter,
            ActivityManagerService activityManagerService,
            List list,
            boolean z,
            String str) {
        char c;
        char c2;
        int i;
        ArrayList sortProcessOomList = sortProcessOomList(str, list);
        if (sortProcessOomList.isEmpty()) {
            return;
        }
        long uptimeMillis =
                SystemClock.uptimeMillis() - activityManagerService.mLastPowerCheckUptime;
        int size = sortProcessOomList.size() - 1;
        while (size >= 0) {
            ProcessRecord processRecord =
                    (ProcessRecord) ((Pair) sortProcessOomList.get(size)).first;
            ProcessStateRecord processStateRecord = processRecord.mState;
            ProcessServiceRecord processServiceRecord = processRecord.mServices;
            String makeOomAdjString = makeOomAdjString(processStateRecord.mSetAdj, false);
            switch (processStateRecord.mSetSchedGroup) {
                case -2:
                    c = 'A';
                    break;
                case -1:
                default:
                    c = '?';
                    break;
                case 0:
                    c = 'b';
                    break;
                case 1:
                    c = 'R';
                    break;
                case 2:
                case 6:
                    c = 'F';
                    break;
                case 3:
                    c = 'T';
                    break;
                case 4:
                    c = 'B';
                    break;
                case 5:
                    c = 'M';
                    break;
            }
            char c3 =
                    processStateRecord.mHasForegroundActivities
                            ? 'A'
                            : processServiceRecord.mHasForegroundServices ? 'S' : ' ';
            String procStateToString =
                    ActivityManager.procStateToString(processStateRecord.mCurProcState);
            printWriter.print("    ");
            printWriter.print(processRecord.mPersistent ? "PERS" : "Proc");
            printWriter.print(" #");
            int size2 =
                    (list.size() - 1)
                            - ((Integer) ((Pair) sortProcessOomList.get(size)).second).intValue();
            if (size2 < 10) {
                c2 = ' ';
                printWriter.print(' ');
            } else {
                c2 = ' ';
            }
            printWriter.print(size2);
            printWriter.print(": ");
            printWriter.print(makeOomAdjString);
            printWriter.print(c2);
            printWriter.print(c);
            printWriter.print('/');
            printWriter.print(c3);
            printWriter.print('/');
            printWriter.print(procStateToString);
            printWriter.print(c2);
            ActivityManager.printCapabilitiesSummary(
                    printWriter, processStateRecord.mCurCapability);
            printWriter.print(c2);
            printWriter.print(" t:");
            if (processRecord.mProfile.mTrimMemoryLevel < 10) {
                printWriter.print(c2);
            }
            printWriter.print(processRecord.mProfile.mTrimMemoryLevel);
            printWriter.print(c2);
            printWriter.print(processRecord.toShortString());
            printWriter.print(" (");
            printWriter.print(processStateRecord.mAdjType);
            printWriter.println(')');
            if (processStateRecord.mAdjSource != null || processStateRecord.mAdjTarget != null) {
                printWriter.print("    ");
                printWriter.print("    ");
                Object obj = processStateRecord.mAdjTarget;
                if (obj instanceof ComponentName) {
                    printWriter.print(((ComponentName) obj).flattenToShortString());
                } else if (obj != null) {
                    printWriter.print(obj.toString());
                } else {
                    printWriter.print("{null}");
                }
                printWriter.print("<=");
                Object obj2 = processStateRecord.mAdjSource;
                if (obj2 instanceof ProcessRecord) {
                    printWriter.print("Proc{");
                    printWriter.print(
                            ((ProcessRecord) processStateRecord.mAdjSource).toShortString());
                    printWriter.println("}");
                } else if (obj2 != null) {
                    printWriter.println(obj2.toString());
                } else {
                    printWriter.println("{null}");
                }
            }
            if (z) {
                printWriter.print("    ");
                printWriter.print("    ");
                printWriter.print("oom: max=");
                printWriter.print(processStateRecord.mMaxAdj);
                printWriter.print(" curRaw=");
                printWriter.print(processStateRecord.mCurRawAdj);
                printWriter.print(" setRaw=");
                printWriter.print(processStateRecord.mSetRawAdj);
                printWriter.print(" cur=");
                printWriter.print(processStateRecord.mCurAdj);
                printWriter.print(" set=");
                printWriter.println(processStateRecord.mSetAdj);
                printWriter.print("    ");
                printWriter.print("    ");
                printWriter.print("state: cur=");
                printWriter.print(
                        ActivityManager.procStateToString(processStateRecord.mCurProcState));
                printWriter.print(" set=");
                printWriter.print(
                        ActivityManager.procStateToString(processStateRecord.mSetProcState));
                if (activityManagerService.mAppProfiler.isProfilingPss()) {
                    printWriter.print(" lastPss=");
                    i = size;
                    DebugUtils.printSizeValue(printWriter, processRecord.mProfile.mLastPss * 1024);
                    printWriter.print(" lastSwapPss=");
                    DebugUtils.printSizeValue(
                            printWriter, processRecord.mProfile.mLastSwapPss * 1024);
                    printWriter.print(" lastCachedPss=");
                    DebugUtils.printSizeValue(
                            printWriter, processRecord.mProfile.mLastCachedPss * 1024);
                } else {
                    i = size;
                    printWriter.print(" lastRss=");
                    DebugUtils.printSizeValue(printWriter, processRecord.mProfile.mLastRss * 1024);
                    printWriter.print(" lastCachedRss=");
                    DebugUtils.printSizeValue(
                            printWriter, processRecord.mProfile.mLastCachedRss * 1024);
                }
                printWriter.println();
                printWriter.print("    ");
                printWriter.print("    ");
                printWriter.print("cached=");
                printWriter.print(processStateRecord.isCached());
                printWriter.print(" empty=");
                printWriter.print(processStateRecord.mCurProcState >= 19);
                printWriter.print(" hasAboveClient=");
                printWriter.println(processServiceRecord.mHasAboveClient);
                if (processStateRecord.mSetProcState >= 10) {
                    long j = processRecord.mProfile.mLastCpuTime.get();
                    if (j != 0 && uptimeMillis > 0) {
                        long j2 = processRecord.mProfile.mCurCpuTime.get() - j;
                        printWriter.print("    ");
                        printWriter.print("    ");
                        printWriter.print("run cpu over ");
                        TimeUtils.formatDuration(uptimeMillis, printWriter);
                        printWriter.print(" used ");
                        TimeUtils.formatDuration(j2, printWriter);
                        printWriter.print(" (");
                        printWriter.print((j2 * 100) / uptimeMillis);
                        printWriter.println("%)");
                    }
                }
            } else {
                i = size;
            }
            size = i - 1;
        }
    }

    public static boolean freezePackageCgroup(int i, boolean z) {
        try {
            Process.freezeCgroupUid(i, z);
            return true;
        } catch (RuntimeException e) {
            StringBuilder m =
                    StorageManagerService$$ExternalSyntheticOutline0.m(
                            i, "Unable to ", z ? "freeze" : "unfreeze", " cgroup uid: ", ": ");
            m.append(e);
            Slog.e("ActivityManager", m.toString());
            return false;
        }
    }

    public static final Integer getLmkdKillCount(int i, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate(12);
        ByteBuffer allocate2 = ByteBuffer.allocate(8);
        allocate.putInt(4);
        allocate.putInt(i);
        allocate.putInt(i2);
        allocate2.putInt(4);
        allocate2.rewind();
        if (writeLmkd(allocate, allocate2) && allocate2.getInt() == 4) {
            return new Integer(allocate2.getInt());
        }
        return null;
    }

    public static Map getPackageAppDataInfoMap(
            PackageManagerInternal packageManagerInternal, String[] strArr, int i) {
        ArrayMap arrayMap = new ArrayMap(strArr.length);
        int userId = UserHandle.getUserId(i);
        for (String str : strArr) {
            PackageStateInternal packageStateInternal =
                    packageManagerInternal.getPackageStateInternal(str);
            if (packageStateInternal == null) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m(
                        "Unknown package:", str, "ActivityManager");
            } else {
                String str2 = ((PackageSetting) packageStateInternal).volumeUuid;
                long ceDataInode =
                        packageStateInternal.getUserStateOrDefault(userId).getCeDataInode();
                if (ceDataInode == 0) {
                    Slog.w("ActivityManager", str + " inode == 0 (b/152760674)");
                    return null;
                }
                arrayMap.put(str, Pair.create(str2, Long.valueOf(ceDataInode)));
            }
        }
        return arrayMap;
    }

    public static void killProcessGroup(int i, int i2) {
        KillHandler killHandler = sKillHandler;
        if (killHandler != null) {
            killHandler.sendMessage(killHandler.obtainMessage(4000, i, i2));
        } else {
            Slog.w("ActivityManager", "Asked to kill process group before system bringup!");
            Process.killProcessGroup(i, i2);
        }
    }

    public static String makeOomAdjString(int i, boolean z) {
        return i >= 900
                ? buildOomTag(
                        "cch",
                        i,
                        "cch",
                        "   ",
                        FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT,
                        z)
                : i >= 850
                        ? buildOomTag(
                                "picked ",
                                i,
                                "picked",
                                null,
                                FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED,
                                z)
                        : i >= 800
                                ? buildOomTag("svcb  ", i, "svcb", null, 800, z)
                                : i >= 700
                                        ? buildOomTag("prev  ", i, "prev", null, 700, z)
                                        : i >= 600
                                                ? buildOomTag("home  ", i, "home", null, 600, z)
                                                : i >= 500
                                                        ? buildOomTag(
                                                                "svc   ", i, "svc", null, 500, z)
                                                        : i >= 400
                                                                ? buildOomTag(
                                                                        "hvy   ", i, "hvy", null,
                                                                        400, z)
                                                                : i >= 300
                                                                        ? buildOomTag(
                                                                                "bkup  ", i, "bkup",
                                                                                null, 300, z)
                                                                        : i >= 250
                                                                                ? buildOomTag(
                                                                                        "prcl  ",
                                                                                        i,
                                                                                        "prcl",
                                                                                        null,
                                                                                        FrameworkStatsLog
                                                                                                .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE,
                                                                                        z)
                                                                                : i >= 225
                                                                                        ? buildOomTag(
                                                                                                "prcm "
                                                                                                    + " ",
                                                                                                i,
                                                                                                "prcm",
                                                                                                null,
                                                                                                225,
                                                                                                z)
                                                                                        : i >= 200
                                                                                                ? buildOomTag(
                                                                                                        "prcp "
                                                                                                            + " ",
                                                                                                        i,
                                                                                                        "prcp",
                                                                                                        null,
                                                                                                        200,
                                                                                                        z)
                                                                                                : i
                                                                                                                >= 100
                                                                                                        ? buildOomTag(
                                                                                                                "vis",
                                                                                                                i,
                                                                                                                "vis",
                                                                                                                "   ",
                                                                                                                100,
                                                                                                                z)
                                                                                                        : i
                                                                                                                        >= 0
                                                                                                                ? buildOomTag(
                                                                                                                        "fg ",
                                                                                                                        i,
                                                                                                                        "fg ",
                                                                                                                        "   ",
                                                                                                                        0,
                                                                                                                        z)
                                                                                                                : i
                                                                                                                                >= -700
                                                                                                                        ? buildOomTag(
                                                                                                                                "psvc "
                                                                                                                                    + " ",
                                                                                                                                i,
                                                                                                                                "psvc",
                                                                                                                                null,
                                                                                                                                -700,
                                                                                                                                z)
                                                                                                                        : i
                                                                                                                                        >= -800
                                                                                                                                ? buildOomTag(
                                                                                                                                        "pers "
                                                                                                                                            + " ",
                                                                                                                                        i,
                                                                                                                                        "pers",
                                                                                                                                        null,
                                                                                                                                        -800,
                                                                                                                                        z)
                                                                                                                                : i
                                                                                                                                                >= -900
                                                                                                                                        ? buildOomTag(
                                                                                                                                                "sys  "
                                                                                                                                                    + " ",
                                                                                                                                                i,
                                                                                                                                                "sys",
                                                                                                                                                null,
                                                                                                                                                -900,
                                                                                                                                                z)
                                                                                                                                        : i
                                                                                                                                                        >= -1000
                                                                                                                                                ? buildOomTag(
                                                                                                                                                        "ntv  ",
                                                                                                                                                        i,
                                                                                                                                                        "ntv",
                                                                                                                                                        null,
                                                                                                                                                        -1000,
                                                                                                                                                        z)
                                                                                                                                                : Integer
                                                                                                                                                        .toString(
                                                                                                                                                                i);
    }

    public static int makeProcStateProtoEnum(int i) {
        switch (i) {
            case -1:
                return 999;
            case 0:
                return 1000;
            case 1:
                return 1001;
            case 2:
                return 1002;
            case 3:
                return 1020;
            case 4:
                return 1003;
            case 5:
                return 1004;
            case 6:
                return 1005;
            case 7:
                return 1006;
            case 8:
                return 1007;
            case 9:
                return 1008;
            case 10:
                return 1009;
            case 11:
                return 1010;
            case 12:
                return 1011;
            case 13:
                return 1012;
            case 14:
                return 1013;
            case 15:
                return 1014;
            case 16:
                return 1015;
            case 17:
                return 1016;
            case 18:
                return 1017;
            case 19:
                return 1018;
            case 20:
                return 1019;
            default:
                return 998;
        }
    }

    public static final void remove(int i) {
        if (i <= 0) {
            return;
        }
        ByteBuffer allocate = ByteBuffer.allocate(8);
        allocate.putInt(2);
        allocate.putInt(i);
        writeLmkd(allocate, null);
    }

    public static final void setLmkdParameter(int i, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate(12);
        allocate.putInt(100);
        allocate.putInt(i);
        allocate.putInt(i2);
        writeLmkd(allocate, null);
    }

    public static void setOomAdj(int i, int i2, int i3, int i4) {
        if (i > 0 && i3 != 1001) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (DynamicHiddenApp.LMK_ENABLE_USERSPACE_LMK
                    && DynamicHiddenApp.LMK_ENABLE_REENTRY_LMK) {
                ByteBuffer allocate = ByteBuffer.allocate(20);
                allocate.putInt(101);
                allocate.putInt(i);
                allocate.putInt(i2);
                allocate.putInt(i3);
                allocate.putInt(i4);
                writeLmkd(allocate, null);
            } else {
                ByteBuffer allocate2 = ByteBuffer.allocate(16);
                allocate2.putInt(1);
                allocate2.putInt(i);
                allocate2.putInt(i2);
                allocate2.putInt(i3);
                writeLmkd(allocate2, null);
            }
            long elapsedRealtime2 = SystemClock.elapsedRealtime() - elapsedRealtime;
            if (elapsedRealtime2 > 250) {
                StringBuilder m =
                        SystemServiceManager$$ExternalSyntheticOutline0.m(
                                i, "SLOW OOM ADJ: ", elapsedRealtime2, "ms for pid ");
                m.append(" = ");
                m.append(i3);
                Slog.w("ActivityManager", m.toString());
            }
        }
    }

    public static ArrayList sortProcessOomList(String str, List list) {
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i = 0; i < size; i++) {
            ProcessRecord processRecord = (ProcessRecord) list.get(i);
            if (str == null || processRecord.mPkgList.containsKey(str)) {
                arrayList.add(new Pair((ProcessRecord) list.get(i), Integer.valueOf(i)));
            }
        }
        Collections.sort(arrayList, new AnonymousClass3());
        return arrayList;
    }

    public static boolean writeLmkd(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) {
        boolean z;
        boolean z2;
        boolean z3;
        LmkdConnection lmkdConnection = sLmkdConnection;
        synchronized (lmkdConnection.mLmkdSocketLock) {
            z = false;
            z2 = lmkdConnection.mLmkdSocket != null;
        }
        if (!z2) {
            KillHandler killHandler = sKillHandler;
            killHandler.sendMessage(killHandler.obtainMessage(4001));
            LmkdConnection lmkdConnection2 = sLmkdConnection;
            synchronized (lmkdConnection2.mLmkdSocketLock) {
                if (lmkdConnection2.mLmkdSocket != null) {
                    z3 = true;
                } else {
                    try {
                        lmkdConnection2.mLmkdSocketLock.wait(3000L);
                        z3 = lmkdConnection2.mLmkdSocket != null;
                    } catch (InterruptedException unused) {
                        z3 = false;
                    }
                }
            }
            if (!z3) {
                return false;
            }
        }
        LmkdConnection lmkdConnection3 = sLmkdConnection;
        if (byteBuffer2 == null) {
            return lmkdConnection3.write(byteBuffer);
        }
        synchronized (lmkdConnection3.mReplyBufLock) {
            lmkdConnection3.mReplyBuf = byteBuffer2;
            if (lmkdConnection3.write(byteBuffer)) {
                try {
                    lmkdConnection3.mReplyBufLock.wait();
                    z = lmkdConnection3.mReplyBuf != null;
                } catch (InterruptedException unused2) {
                }
            }
            lmkdConnection3.mReplyBuf = null;
        }
        return z;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void addProcessNameLocked(com.android.server.am.ProcessRecord r14) {
        /*
            Method dump skipped, instructions count: 381
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessList.addProcessNameLocked(com.android.server.am.ProcessRecord):void");
    }

    public final void applyDisplaySize(WindowManagerService windowManagerService) {
        int i;
        String str;
        String str2;
        int i2;
        int i3;
        int i4;
        int i5;
        if (!this.mHaveDisplaySize || this.mIsDisplayChanged) {
            Point point = new Point();
            windowManagerService.getBaseDisplaySize(0, point);
            int i6 = point.x;
            if (i6 == 0 || (i = point.y) == 0) {
                return;
            }
            this.mDisplayWidth = i6;
            this.mDisplayHeight = i;
            boolean z = true;
            updateOomLevels(i6, i, true);
            this.mHaveDisplaySize = true;
            if (this.mDynamicHiddenApp == null) {
                this.mDynamicHiddenApp = DynamicHiddenApp.DhaClassLazyHolder.INSTANCE;
            }
            DynamicHiddenApp dynamicHiddenApp = this.mDynamicHiddenApp;
            int i7 = point.x;
            int i8 = point.y;
            BGSlotManager bGSlotManager = dynamicHiddenApp.mBGSlotManager;
            String str3 = "DynamicHiddenApp_BGSlotManager";
            if (bGSlotManager != null) {
                int slmkPropertyInt = BgAppPropManager.getSlmkPropertyInt("max_snapshot_num", "0");
                MemInfoGetter memInfoGetter = bGSlotManager.mInfo;
                int[][] iArr = memInfoGetter.memoryMBToGBPool;
                int length = iArr.length;
                int i9 = 0;
                while (true) {
                    if (i9 >= length) {
                        str2 = str3;
                        i2 = slmkPropertyInt;
                        i3 = 0;
                        break;
                    }
                    int[] iArr2 = iArr[i9];
                    str2 = str3;
                    i2 = slmkPropertyInt;
                    if (memInfoGetter.mTotalMemMb > iArr2[0]) {
                        i3 = iArr2[1];
                        break;
                    } else {
                        i9++;
                        str3 = str2;
                        slmkPropertyInt = i2;
                    }
                }
                int i10 = i7 * i8;
                int i11 = 5;
                int[][] iArr3 = {
                    new int[] {2, 3, 3, 3},
                    new int[] {3, 5, 3, 3},
                    new int[] {4, 8, 4, 3},
                    new int[] {6, 10, 6, 3},
                    new int[] {8, 10, 8, 4},
                    new int[] {12, 20, 20, 20},
                    new int[] {16, 20, 20, 20}
                };
                if (i10 == 0 || i3 == 0) {
                    str = str2;
                    Slog.i(
                            str,
                            "can not update max task snapshot number, due to resolution or physical"
                                + " memory");
                    StringBuilder sb = new StringBuilder("physical memory: ");
                    sb.append(i3);
                    sb.append(", resolution: ");
                    SystemServiceManager$$ExternalSyntheticOutline0.m(sb, i10, str);
                    i4 = 0;
                } else {
                    str = str2;
                    Slog.i(str, "start update max task snapshot number");
                    i4 = i10 >= 3686400 ? 3 : i10 >= 2073600 ? 2 : 1;
                    int i12 = 0;
                    while (true) {
                        if (i12 >= 7) {
                            z = false;
                            i5 = 0;
                            break;
                        }
                        int[] iArr4 = iArr3[i12];
                        if (i3 == iArr4[0]) {
                            i5 = iArr4[i4];
                            Slog.i(
                                    str,
                                    "update max task snapshot number, physical memory: "
                                            + i3
                                            + ", current resolution : "
                                            + i4);
                            break;
                        }
                        i12++;
                    }
                    if (z) {
                        i11 = i5;
                    } else {
                        Slog.i(
                                str,
                                "can not update max task snapshot number, due to unidentified"
                                    + " physical memory");
                        StringBuilder sb2 = new StringBuilder("physical memory: ");
                        sb2.append(i3);
                        sb2.append(", current resolution: ");
                        SystemServiceManager$$ExternalSyntheticOutline0.m(sb2, i4, str);
                    }
                }
                int i13 = i2 > 0 ? i2 : i11;
                Slog.i(str, "TaskSnapShot : " + i13 + ", CurRes : " + i4);
                SnapshotCache.sMaxSnapshotCache = i13;
                if (i13 <= 3) {
                    TaskSnapshotCache.sSplitModeMaxCacheSize = i13 * 2;
                } else {
                    TaskSnapshotCache.sSplitModeMaxCacheSize = 0;
                }
            } else {
                str = "DynamicHiddenApp_BGSlotManager";
            }
            DynamicHiddenApp dynamicHiddenApp2 = this.mDynamicHiddenApp;
            int i14 = point.x;
            int i15 = point.y;
            BGSlotManager bGSlotManager2 = dynamicHiddenApp2.mBGSlotManager;
            if (bGSlotManager2 != null) {
                if (!BGSlotManager.HRT_MaxCached_Enable || i14 * i15 < 3686400) {
                    bGSlotManager2.BGSlotState &= -3;
                    bGSlotManager2.changeBGSlot();
                    Slog.i(str, "HighResBGSlot Recovered");
                } else {
                    bGSlotManager2.BGSlotState |= 2;
                    bGSlotManager2.changeBGSlot();
                }
            }
            this.mIsDisplayChanged = false;
            KillPolicyManager killPolicyManager = this.mService.mKillPolicyManager;
            if (killPolicyManager != null) {
                int i16 = point.x;
                int i17 = point.y;
                Locale locale = Locale.US;
                killPolicyManager.mDisplaySizeStr = i16 + "x" + i17;
            }
        }
    }

    public final ArrayList collectProcessesLOSP(int i, boolean z, String[] strArr) {
        int i2;
        PackageList packageList;
        if (strArr == null || strArr.length <= i || strArr[i].charAt(0) == '-') {
            return new ArrayList(this.mLruProcesses);
        }
        ArrayList arrayList = new ArrayList();
        try {
            i2 = Integer.parseInt(strArr[i]);
        } catch (NumberFormatException unused) {
            i2 = -1;
        }
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            ProcessRecord processRecord = (ProcessRecord) this.mLruProcesses.get(size);
            int i3 = processRecord.mPid;
            if (i3 > 0 && i3 == i2) {
                arrayList.add(processRecord);
            } else if (z
                    && (packageList = processRecord.mPkgList) != null
                    && packageList.containsKey(strArr[i])) {
                arrayList.add(processRecord);
            } else if (processRecord.processName.equals(strArr[i])) {
                arrayList.add(processRecord);
            }
        }
        if (arrayList.size() <= 0) {
            return null;
        }
        return arrayList;
    }

    public final AppZygote createAppZygoteForProcessIfNeeded(ProcessRecord processRecord) {
        AppZygote appZygote;
        ArrayList arrayList;
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                int i = processRecord.mHostingRecord.mDefiningUid;
                appZygote = (AppZygote) this.mAppZygotes.get(processRecord.info.processName, i);
                if (appZygote == null) {
                    IsolatedUidRangeAllocator isolatedUidRangeAllocator =
                            this.mAppIsolatedUidRangeAllocator;
                    IsolatedUidRange isolatedUidRange =
                            (IsolatedUidRange)
                                    isolatedUidRangeAllocator.mAppRanges.get(
                                            processRecord.info.processName,
                                            processRecord.mHostingRecord.mDefiningUid);
                    int userId = UserHandle.getUserId(i);
                    int uid = UserHandle.getUid(userId, isolatedUidRange.mFirstUid);
                    int uid2 = UserHandle.getUid(userId, isolatedUidRange.mLastUid);
                    ApplicationInfo applicationInfo = new ApplicationInfo(processRecord.info);
                    applicationInfo.packageName = processRecord.mHostingRecord.mDefiningPackageName;
                    applicationInfo.uid = i;
                    AppZygote appZygote2 =
                            new AppZygote(applicationInfo, processRecord.processInfo, i, uid, uid2);
                    this.mAppZygotes.put(processRecord.info.processName, i, appZygote2);
                    arrayList = new ArrayList();
                    this.mAppZygoteProcesses.put(appZygote2, arrayList);
                    appZygote = appZygote2;
                } else {
                    this.mService.mHandler.removeMessages(71, appZygote);
                    arrayList = (ArrayList) this.mAppZygoteProcesses.get(appZygote);
                }
                arrayList.add(processRecord);
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return appZygote;
    }

    public final void dispatchProcessesChanged() {
        int size;
        int i;
        synchronized (this.mProcessChangeLock) {
            try {
                size = this.mPendingProcessChanges.size();
                if (this.mActiveProcessChanges.length < size) {
                    this.mActiveProcessChanges = new ActivityManagerService.ProcessChangeItem[size];
                }
                this.mPendingProcessChanges.toArray(this.mActiveProcessChanges);
                this.mPendingProcessChanges.clear();
            } finally {
            }
        }
        int beginBroadcast = this.mProcessObservers.beginBroadcast();
        while (true) {
            i = 0;
            if (beginBroadcast <= 0) {
                break;
            }
            beginBroadcast--;
            IProcessObserver broadcastItem =
                    this.mProcessObservers.getBroadcastItem(beginBroadcast);
            if (broadcastItem != null) {
                while (i < size) {
                    try {
                        ActivityManagerService.ProcessChangeItem processChangeItem =
                                this.mActiveProcessChanges[i];
                        if ((processChangeItem.changes & 1) != 0) {
                            broadcastItem.onForegroundActivitiesChanged(
                                    processChangeItem.pid,
                                    processChangeItem.uid,
                                    processChangeItem.foregroundActivities);
                        }
                        if ((processChangeItem.changes & 2) != 0) {
                            broadcastItem.onForegroundServicesChanged(
                                    processChangeItem.pid,
                                    processChangeItem.uid,
                                    processChangeItem.foregroundServiceTypes);
                        }
                        i++;
                    } catch (RemoteException unused) {
                    }
                }
            }
        }
        this.mProcessObservers.finishBroadcast();
        synchronized (this.mProcessChangeLock) {
            while (i < size) {
                try {
                    this.mAvailProcessChanges.add(this.mActiveProcessChanges[i]);
                    i++;
                } finally {
                }
            }
        }
    }

    public final void dumpLruLocked(PrintWriter printWriter, String str, String str2) {
        int size = this.mLruProcesses.size();
        String str3 = "  ";
        if (str2 != null) {
            for (int i = size - 1; i >= 0; i--) {
                ProcessRecord processRecord = (ProcessRecord) this.mLruProcesses.get(i);
                if (str == null || processRecord.mPkgList.containsKey(str)) {
                    printWriter.print(str2);
                    printWriter.println("Raw LRU list (dumpsys activity lru):");
                    str3 = str2.concat("  ");
                }
            }
            return;
        }
        printWriter.println("ACTIVITY MANAGER LRU PROCESSES (dumpsys activity lru)");
        boolean z = true;
        int i2 = size - 1;
        boolean z2 = true;
        while (i2 >= this.mLruProcessActivityStart) {
            ProcessRecord processRecord2 = (ProcessRecord) this.mLruProcesses.get(i2);
            if (str == null || processRecord2.mPkgList.containsKey(str)) {
                if (z2) {
                    printWriter.print(str3);
                    printWriter.println("Activities:");
                    z2 = false;
                }
                dumpLruEntryLocked(printWriter, i2, processRecord2, str3);
            }
            i2--;
        }
        boolean z3 = true;
        while (i2 >= this.mLruProcessServiceStart) {
            ProcessRecord processRecord3 = (ProcessRecord) this.mLruProcesses.get(i2);
            if (str == null || processRecord3.mPkgList.containsKey(str)) {
                if (z3) {
                    printWriter.print(str3);
                    printWriter.println("Services:");
                    z3 = false;
                }
                dumpLruEntryLocked(printWriter, i2, processRecord3, str3);
            }
            i2--;
        }
        while (i2 >= 0) {
            ProcessRecord processRecord4 = (ProcessRecord) this.mLruProcesses.get(i2);
            if (str == null || processRecord4.mPkgList.containsKey(str)) {
                if (z) {
                    printWriter.print(str3);
                    printWriter.println("Other:");
                    z = false;
                }
                dumpLruEntryLocked(printWriter, i2, processRecord4, str3);
            }
            i2--;
        }
    }

    public final void dumpOomLocked(PrintWriter printWriter, String str, boolean z) {
        if (this.mLruProcesses.size() > 0) {
            if (z) {
                printWriter.println();
            }
            printWriter.println("  OOM levels:");
            printOomLevel(printWriter, "SYSTEM_ADJ", -900);
            printOomLevel(printWriter, "PERSISTENT_PROC_ADJ", -800);
            printOomLevel(printWriter, "PERSISTENT_SERVICE_ADJ", -700);
            printOomLevel(printWriter, "FOREGROUND_APP_ADJ", 0);
            printOomLevel(printWriter, "VISIBLE_APP_ADJ", 100);
            printOomLevel(printWriter, "PERCEPTIBLE_APP_ADJ", 200);
            printOomLevel(printWriter, "PERCEPTIBLE_MEDIUM_APP_ADJ", 225);
            printOomLevel(
                    printWriter,
                    "PERCEPTIBLE_LOW_APP_ADJ",
                    FrameworkStatsLog
                            .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE);
            printOomLevel(printWriter, "BACKUP_APP_ADJ", 300);
            printOomLevel(printWriter, "HEAVY_WEIGHT_APP_ADJ", 400);
            printOomLevel(printWriter, "SERVICE_ADJ", 500);
            printOomLevel(printWriter, "HOME_APP_ADJ", 600);
            printOomLevel(printWriter, "PREVIOUS_APP_ADJ", 700);
            printOomLevel(printWriter, "SERVICE_B_ADJ", 800);
            printOomLevel(
                    printWriter, "PICKED_ADJ", FrameworkStatsLog.VPN_CONNECTION_STATE_CHANGED);
            printOomLevel(
                    printWriter,
                    "CACHED_APP_MIN_ADJ",
                    FrameworkStatsLog.CAMERA_FEATURE_COMBINATION_QUERY_EVENT);
            printOomLevel(printWriter, "CACHED_APP_MAX_ADJ", 999);
            printWriter.println();
            printWriter.print("  Process OOM control (");
            printWriter.print(this.mLruProcesses.size());
            printWriter.print(" total, non-act at ");
            printWriter.print(this.mLruProcesses.size() - this.mLruProcessActivityStart);
            printWriter.print(", non-svc at ");
            printWriter.print(this.mLruProcesses.size() - this.mLruProcessServiceStart);
            printWriter.println("):");
            dumpProcessOomList(printWriter, this.mService, this.mLruProcesses, true, str);
            z = true;
        }
        synchronized (this.mService.mAppProfiler.mProfilerLock) {
            this.mService.mAppProfiler.dumpProcessesToGc(printWriter, str, z);
        }
        printWriter.println();
        ActivityTaskManagerService.LocalService localService =
                (ActivityTaskManagerService.LocalService) this.mService.mAtmInternal;
        WindowManagerGlobalLock windowManagerGlobalLock =
                ActivityTaskManagerService.this.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                printWriter.println(
                        "  mHomeProcess: " + ActivityTaskManagerService.this.mHomeProcess);
                printWriter.println(
                        "  mPreviousProcess: " + ActivityTaskManagerService.this.mPreviousProcess);
                if (ActivityTaskManagerService.this.mHeavyWeightProcess != null) {
                    printWriter.println(
                            "  mHeavyWeightProcess: "
                                    + ActivityTaskManagerService.this.mHeavyWeightProcess);
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void dumpProcessesLSP(
            FileDescriptor fileDescriptor,
            PrintWriter printWriter,
            String[] strArr,
            int i,
            boolean z,
            String str,
            int i2) {
        boolean z2;
        int i3;
        boolean dumpMemWatchProcessesLPf;
        ProfilerInfo profilerInfo;
        printWriter.println("ACTIVITY MANAGER RUNNING PROCESSES (dumpsys activity processes)");
        boolean z3 = false;
        if (z || str != null) {
            int size = this.mProcessNames.getMap().size();
            z2 = false;
            int i4 = 0;
            for (int i5 = 0; i5 < size; i5++) {
                SparseArray sparseArray = (SparseArray) this.mProcessNames.getMap().valueAt(i5);
                int size2 = sparseArray.size();
                for (int i6 = 0; i6 < size2; i6++) {
                    ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i6);
                    if (str == null || processRecord.mPkgList.containsKey(str)) {
                        if (!z2) {
                            printWriter.println("  All known processes:");
                            z2 = true;
                        }
                        printWriter.print(processRecord.mPersistent ? "  *PERS*" : "  *APP*");
                        printWriter.print(" UID ");
                        printWriter.print(sparseArray.keyAt(i6));
                        printWriter.print(" ");
                        printWriter.println(processRecord);
                        processRecord.dump(printWriter);
                        if (processRecord.mPersistent) {
                            i4++;
                        }
                    }
                }
            }
            i3 = i4;
        } else {
            z2 = false;
            i3 = 0;
        }
        if (this.mIsolatedProcesses.size() > 0) {
            int size3 = this.mIsolatedProcesses.size();
            boolean z4 = false;
            for (int i7 = 0; i7 < size3; i7++) {
                ProcessRecord processRecord2 = (ProcessRecord) this.mIsolatedProcesses.valueAt(i7);
                if (str == null || processRecord2.mPkgList.containsKey(str)) {
                    if (!z4) {
                        if (z2) {
                            printWriter.println();
                        }
                        printWriter.println("  Isolated process list (sorted by uid):");
                        z2 = true;
                        z4 = true;
                    }
                    printWriter.print("    Isolated #");
                    printWriter.print(i7);
                    printWriter.print(": ");
                    printWriter.println(processRecord2);
                }
            }
        }
        ActivityManagerService activityManagerService = this.mService;
        int size4 = activityManagerService.mActiveInstrumentation.size();
        if (size4 > 0) {
            boolean z5 = false;
            for (int i8 = 0; i8 < size4; i8++) {
                ActiveInstrumentation activeInstrumentation =
                        (ActiveInstrumentation)
                                activityManagerService.mActiveInstrumentation.get(i8);
                if (str == null
                        || activeInstrumentation.mClass.getPackageName().equals(str)
                        || activeInstrumentation.mTargetInfo.packageName.equals(str)) {
                    if (!z5) {
                        if (z2) {
                            printWriter.println();
                        }
                        printWriter.println("  Active instrumentation:");
                        z2 = true;
                        z5 = true;
                    }
                    printWriter.print("    Instrumentation #");
                    printWriter.print(i8);
                    printWriter.print(": ");
                    printWriter.println(activeInstrumentation);
                    activeInstrumentation.getClass();
                    printWriter.print("      ");
                    printWriter.print("mClass=");
                    printWriter.print(activeInstrumentation.mClass);
                    printWriter.print(" mFinished=");
                    printWriter.println(activeInstrumentation.mFinished);
                    printWriter.print("      ");
                    printWriter.println("mRunningProcesses:");
                    for (int i9 = 0; i9 < activeInstrumentation.mRunningProcesses.size(); i9++) {
                        printWriter.print("      ");
                        printWriter.print("  #");
                        printWriter.print(i9);
                        printWriter.print(": ");
                        printWriter.println(activeInstrumentation.mRunningProcesses.get(i9));
                    }
                    printWriter.print("      ");
                    printWriter.print("mTargetProcesses=");
                    ProcessList$$ExternalSyntheticOutline0.m(
                            printWriter,
                            Arrays.toString(activeInstrumentation.mTargetProcesses),
                            "      ",
                            "mTargetInfo=");
                    printWriter.println(activeInstrumentation.mTargetInfo);
                    ApplicationInfo applicationInfo = activeInstrumentation.mTargetInfo;
                    if (applicationInfo != null) {
                        applicationInfo.dump(new PrintWriterPrinter(printWriter), "        ", 0);
                    }
                    if (activeInstrumentation.mProfileFile != null) {
                        printWriter.print("      ");
                        printWriter.print("mProfileFile=");
                        printWriter.println(activeInstrumentation.mProfileFile);
                    }
                    if (activeInstrumentation.mWatcher != null) {
                        printWriter.print("      ");
                        printWriter.print("mWatcher=");
                        printWriter.println(activeInstrumentation.mWatcher);
                    }
                    if (activeInstrumentation.mUiAutomationConnection != null) {
                        printWriter.print("      ");
                        printWriter.print("mUiAutomationConnection=");
                        printWriter.println(activeInstrumentation.mUiAutomationConnection);
                    }
                    printWriter.print("mHasBackgroundActivityStartsPermission=");
                    printWriter.println(
                            activeInstrumentation.mHasBackgroundActivityStartsPermission);
                    printWriter.print("mHasBackgroundForegroundServiceStartsPermission=");
                    AppBatteryTracker$AppBatteryPolicy$$ExternalSyntheticOutline0.m(
                            printWriter,
                            "      ",
                            "mArguments=",
                            activeInstrumentation.mHasBackgroundForegroundServiceStartsPermission);
                    printWriter.println(activeInstrumentation.mArguments);
                }
            }
        }
        dumpOomLocked(printWriter, str, z2);
        if (this.mActiveUids.mActiveUids.size() > 0) {
            this.mActiveUids.dump(i2, printWriter, str, "UID states:");
        }
        if (z) {
            this.mService.mUidObserverController.mValidateUids.dump(
                    i2, printWriter, str, "UID validation:");
        }
        printWriter.println();
        dumpLruLocked(printWriter, str, "  ");
        if (this.mLruProcesses.size() > 0) {
            printWriter.println();
            printWriter.print("  Process LRU list (sorted by oom_adj, ");
            printWriter.print(this.mLruProcesses.size());
            printWriter.print(" total, non-act at ");
            printWriter.print(this.mLruProcesses.size() - this.mLruProcessActivityStart);
            printWriter.print(", non-svc at ");
            printWriter.print(this.mLruProcesses.size() - this.mLruProcessServiceStart);
            printWriter.println("):");
            dumpProcessOomList(printWriter, this.mService, this.mLruProcesses, false, str);
        }
        ActivityManagerService activityManagerService2 = this.mService;
        activityManagerService2.getClass();
        if (z || str != null) {
            SparseArray sparseArray2 = new SparseArray();
            synchronized (activityManagerService2.mPidsSelfLocked) {
                try {
                    int size5 =
                            ((SparseArray) activityManagerService2.mPidsSelfLocked.mPidMap).size();
                    boolean z6 = false;
                    for (int i10 = 0; i10 < size5; i10++) {
                        ProcessRecord valueAt =
                                activityManagerService2.mPidsSelfLocked.valueAt(i10);
                        sparseArray2.put(valueAt.mPid, valueAt);
                        if (str == null || valueAt.mPkgList.containsKey(str)) {
                            if (!z6) {
                                printWriter.println();
                                printWriter.println("  PID mappings:");
                                z6 = true;
                            }
                            printWriter.print("    PID #");
                            printWriter.print(
                                    ((SparseArray) activityManagerService2.mPidsSelfLocked.mPidMap)
                                            .keyAt(i10));
                            printWriter.print(": ");
                            printWriter.println(
                                    activityManagerService2.mPidsSelfLocked.valueAt(i10));
                        }
                    }
                } finally {
                }
            }
            SparseArray sparseArray3 = ActivityManagerService.sActiveProcessInfoSelfLocked;
            synchronized (sparseArray3) {
                try {
                    int size6 = sparseArray3.size();
                    boolean z7 = false;
                    for (int i11 = 0; i11 < size6; i11++) {
                        SparseArray sparseArray4 =
                                ActivityManagerService.sActiveProcessInfoSelfLocked;
                        ProcessInfo processInfo = (ProcessInfo) sparseArray4.valueAt(i11);
                        ProcessRecord processRecord3 =
                                (ProcessRecord) sparseArray2.get(sparseArray4.keyAt(i11));
                        if (processRecord3 == null
                                || str == null
                                || processRecord3.mPkgList.containsKey(str)) {
                            if (!z7) {
                                printWriter.println();
                                printWriter.println("  Active process infos:");
                                z7 = true;
                            }
                            printWriter.print("    Pinfo PID #");
                            printWriter.print(sparseArray4.keyAt(i11));
                            printWriter.println(":");
                            printWriter.print("      name=");
                            printWriter.println(processInfo.name);
                            if (processInfo.deniedPermissions != null) {
                                for (int i12 = 0;
                                        i12 < processInfo.deniedPermissions.size();
                                        i12++) {
                                    printWriter.print("      deny: ");
                                    printWriter.println(
                                            (String) processInfo.deniedPermissions.valueAt(i12));
                                }
                            }
                        }
                    }
                } finally {
                }
            }
        }
        if (z) {
            activityManagerService2.mPhantomProcessList.dump(printWriter);
        }
        if (activityManagerService2.mImportantProcesses.size() > 0) {
            synchronized (activityManagerService2.mPidsSelfLocked) {
                try {
                    int size7 = activityManagerService2.mImportantProcesses.size();
                    boolean z8 = false;
                    for (int i13 = 0; i13 < size7; i13++) {
                        ProcessRecord processRecord4 =
                                activityManagerService2.mPidsSelfLocked.get(
                                        ((ActivityManagerService.AnonymousClass14)
                                                        activityManagerService2.mImportantProcesses
                                                                .valueAt(i13))
                                                .pid);
                        if (str == null
                                || (processRecord4 != null
                                        && processRecord4.mPkgList.containsKey(str))) {
                            if (!z8) {
                                printWriter.println();
                                printWriter.println("  Foreground Processes:");
                                z8 = true;
                            }
                            printWriter.print("    PID #");
                            printWriter.print(
                                    activityManagerService2.mImportantProcesses.keyAt(i13));
                            printWriter.print(": ");
                            printWriter.println(
                                    activityManagerService2.mImportantProcesses.valueAt(i13));
                        }
                    }
                } finally {
                }
            }
        }
        if (activityManagerService2.mPersistentStartingProcesses.size() > 0) {
            printWriter.println();
            printWriter.println("  Persisent processes that are starting:");
            ActivityManagerService.dumpProcessList(
                    printWriter,
                    activityManagerService2.mPersistentStartingProcesses,
                    "Starting Norm",
                    "Restarting PERS",
                    str);
        }
        if (activityManagerService2.mProcessList.mRemovedProcesses.size() > 0) {
            printWriter.println();
            printWriter.println("  Processes that are being removed:");
            ActivityManagerService.dumpProcessList(
                    printWriter,
                    activityManagerService2.mProcessList.mRemovedProcesses,
                    "Removed Norm",
                    "Removed PERS",
                    str);
        }
        if (activityManagerService2.mProcessesOnHold.size() > 0) {
            printWriter.println();
            printWriter.println("  Processes that are on old until the system is ready:");
            ActivityManagerService.dumpProcessList(
                    printWriter,
                    activityManagerService2.mProcessesOnHold,
                    "OnHold Norm",
                    "OnHold PERS",
                    str);
        }
        boolean z9 = true;
        boolean dumpForProcesses =
                activityManagerService2.mAtmInternal.dumpForProcesses(
                        printWriter,
                        z,
                        str,
                        i2,
                        activityManagerService2.mAppErrors.dumpLPr(printWriter, str, true),
                        activityManagerService2.mAppProfiler.mTestPssOrRssMode,
                        activityManagerService2.mWakefulness.get());
        if (!z || activityManagerService2.mProcessList.mPendingStarts.size() <= 0) {
            z9 = dumpForProcesses;
        } else {
            if (dumpForProcesses) {
                printWriter.println();
            }
            printWriter.println("  mPendingStarts: ");
            int size8 = activityManagerService2.mProcessList.mPendingStarts.size();
            for (int i14 = 0; i14 < size8; i14++) {
                printWriter.println(
                        "    "
                                + activityManagerService2.mProcessList.mPendingStarts.keyAt(i14)
                                + ": "
                                + activityManagerService2.mProcessList.mPendingStarts.valueAt(i14));
            }
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        new StringBuilder("  mProcessLimit: "),
                                        activityManagerService2.mConstants.MAX_CACHED_PROCESSES,
                                        printWriter,
                                        "  mNumNonCachedProcs: "),
                                activityManagerService2.mOomAdjuster.mNumNonCachedProcs,
                                printWriter,
                                "  mNumCachedHiddenProcs: "),
                        activityManagerService2.mOomAdjuster.mNumCachedHiddenProcs,
                        printWriter,
                        "  mProcessLimitOverride(OverrideMaxCachedProcesses): "),
                activityManagerService2.mConstants.mOverrideMaxCachedProcesses,
                printWriter);
        DynamicHiddenApp dynamicHiddenApp = activityManagerService2.mDynamicHiddenApp;
        if (dynamicHiddenApp != null) {
            dynamicHiddenApp.dumpLMKDParameter(printWriter);
            KillPolicyManager killPolicyManager = activityManagerService2.mKillPolicyManager;
            if (killPolicyManager != null && KillPolicyManager.sPmmEnabledBySpcm) {
                killPolicyManager.dump(printWriter, null);
            }
            if (DynamicHiddenApp.IS_HIGH_CAPACITY_RAM) {
                printWriter.println("  IS_HIGH_CAPACITY_RAM: true");
            }
            if (DynamicHiddenApp.PICKED_ADJ_ENABLE) {
                BGProtectManager bGProtectManager =
                        activityManagerService2.mDynamicHiddenApp.mBGProtectManager;
                if (bGProtectManager.NapProcessSlotDefault != null) {
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("  MLList NAP Process name : "),
                            bGProtectManager.NapProcessSlotDefault.processName,
                            printWriter);
                } else {
                    printWriter.println("  MLList NAP Process name : []");
                }
                ArrayList arrayList = BGProtectManager.dha_MLexcept_map;
                if (arrayList != null) {
                    printWriter.println("  MLList AUF Process name : " + arrayList.toString());
                }
                printWriter.println();
            }
        }
        if (z) {
            activityManagerService2.mUidObserverController.dump(printWriter, str);
            printWriter.println(
                    "  mDeviceIdleAllowlist="
                            + Arrays.toString(activityManagerService2.mDeviceIdleAllowlist));
            printWriter.println(
                    "  mDeviceIdleExceptIdleAllowlist="
                            + Arrays.toString(
                                    activityManagerService2.mDeviceIdleExceptIdleAllowlist));
            printWriter.println(
                    "  mDeviceIdleTempAllowlist="
                            + Arrays.toString(activityManagerService2.mDeviceIdleTempAllowlist));
            if (activityManagerService2.mPendingTempAllowlist.size() > 0) {
                printWriter.println("  mPendingTempAllowlist:");
                int size9 = activityManagerService2.mPendingTempAllowlist.size();
                for (int i15 = 0; i15 < size9; i15++) {
                    ActivityManagerService.PendingTempAllowlist valueAt2 =
                            activityManagerService2.mPendingTempAllowlist.valueAt(i15);
                    printWriter.print("    ");
                    UserHandle.formatUid(printWriter, valueAt2.targetUid);
                    printWriter.print(": ");
                    TimeUtils.formatDuration(valueAt2.duration, printWriter);
                    printWriter.print(" ");
                    printWriter.println(valueAt2.tag);
                    printWriter.print(" ");
                    printWriter.print(valueAt2.type);
                    printWriter.print(" ");
                    printWriter.print(valueAt2.reasonCode);
                    printWriter.print(" ");
                    printWriter.print(valueAt2.callingUid);
                }
            }
            printWriter.println("  mFgsStartTempAllowList:");
            activityManagerService2.mFgsStartTempAllowList.forEach(
                    new ActivityManagerService$$ExternalSyntheticLambda27(
                            printWriter,
                            System.currentTimeMillis(),
                            SystemClock.elapsedRealtime()));
            if (!activityManagerService2.mProcessList.mAppsInBackgroundRestricted.isEmpty()) {
                printWriter.println("  Processes that are in background restricted:");
                int size10 =
                        activityManagerService2.mProcessList.mAppsInBackgroundRestricted.size();
                for (int i16 = 0; i16 < size10; i16++) {
                    printWriter.println(
                            String.format(
                                    "%s #%2d: %s",
                                    "    ",
                                    Integer.valueOf(i16),
                                    ((ProcessRecord)
                                                    activityManagerService2.mProcessList
                                                            .mAppsInBackgroundRestricted.valueAt(
                                                            i16))
                                            .toString()));
                }
            }
        }
        String str2 = activityManagerService2.mDebugApp;
        if ((str2 != null
                        || activityManagerService2.mOrigDebugApp != null
                        || activityManagerService2.mDebugTransient
                        || activityManagerService2.mOrigWaitForDebugger)
                && (str == null
                        || str.equals(str2)
                        || str.equals(activityManagerService2.mOrigDebugApp))) {
            if (z9) {
                printWriter.println();
                z9 = false;
            }
            StringBuilder sb = new StringBuilder("  mDebugApp=");
            sb.append(activityManagerService2.mDebugApp);
            sb.append("/orig=");
            sb.append(activityManagerService2.mOrigDebugApp);
            sb.append(" mDebugTransient=");
            sb.append(activityManagerService2.mDebugTransient);
            sb.append(" mOrigWaitForDebugger=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, activityManagerService2.mOrigWaitForDebugger, printWriter);
        }
        synchronized (activityManagerService2.mAppProfiler.mProfilerLock) {
            dumpMemWatchProcessesLPf =
                    activityManagerService2.mAppProfiler.dumpMemWatchProcessesLPf(printWriter, z9);
        }
        String str3 = activityManagerService2.mTrackAllocationApp;
        if (str3 != null && (str == null || str.equals(str3))) {
            if (dumpMemWatchProcessesLPf) {
                printWriter.println();
                dumpMemWatchProcessesLPf = false;
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("  mTrackAllocationApp="),
                    activityManagerService2.mTrackAllocationApp,
                    printWriter);
        }
        AppProfiler appProfiler = activityManagerService2.mAppProfiler;
        AppProfiler.ProfileData profileData = appProfiler.mProfileData;
        String str4 = profileData.mProfileApp;
        if ((str4 != null
                        || profileData.mProfileProc != null
                        || ((profilerInfo = profileData.mProfilerInfo) != null
                                && (profilerInfo.profileFile != null
                                        || profilerInfo.profileFd != null)))
                && (str == null || str.equals(str4))) {
            if (dumpMemWatchProcessesLPf) {
                printWriter.println();
            } else {
                z3 = dumpMemWatchProcessesLPf;
            }
            printWriter.println(
                    "  mProfileApp="
                            + profileData.mProfileApp
                            + " mProfileProc="
                            + profileData.mProfileProc);
            if (profileData.mProfilerInfo != null) {
                printWriter.println(
                        "  mProfileFile="
                                + profileData.mProfilerInfo.profileFile
                                + " mProfileFd="
                                + profileData.mProfilerInfo.profileFd);
                StringBuilder sb2 = new StringBuilder("  mSamplingInterval=");
                sb2.append(profileData.mProfilerInfo.samplingInterval);
                sb2.append(" mAutoStopProfiler=");
                sb2.append(profileData.mProfilerInfo.autoStopProfiler);
                sb2.append(" mStreamingOutput=");
                sb2.append(profileData.mProfilerInfo.streamingOutput);
                sb2.append(" mClockType=");
                sb2.append(profileData.mProfilerInfo.clockType);
                sb2.append(" mProfilerOutputVersion=");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                sb2,
                                profileData.mProfilerInfo.profilerOutputVersion,
                                printWriter,
                                "  mProfileType="),
                        appProfiler.mProfileType,
                        printWriter);
            }
            dumpMemWatchProcessesLPf = z3;
        }
        String str5 = activityManagerService2.mNativeDebuggingApp;
        if (str5 != null && (str == null || str.equals(str5))) {
            if (dumpMemWatchProcessesLPf) {
                printWriter.println();
            }
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    new StringBuilder("  mNativeDebuggingApp="),
                    activityManagerService2.mNativeDebuggingApp,
                    printWriter);
        }
        if (str == null) {
            if (activityManagerService2.mAlwaysFinishActivities) {
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("  mAlwaysFinishActivities="),
                        activityManagerService2.mAlwaysFinishActivities,
                        printWriter);
            }
            if (activityManagerService2.mAllowSpecifiedFifoScheduling) {
                printWriter.println("  mAllowSpecifiedFifoScheduling=true");
            }
            if (z) {
                printWriter.println("  Total persistent processes: " + i3);
                StringBuilder sb3 = new StringBuilder("  mProcessesReady=");
                sb3.append(activityManagerService2.mProcessesReady);
                sb3.append(" mSystemReady=");
                sb3.append(activityManagerService2.mSystemReady);
                sb3.append(" mBooted=");
                sb3.append(activityManagerService2.mBooted);
                sb3.append(" mFactoryTest=");
                StringBuilder m =
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                sb3,
                                activityManagerService2.mFactoryTest,
                                printWriter,
                                "  mBooting=");
                m.append(activityManagerService2.mBooting);
                m.append(" mCallFinishBooting=");
                m.append(activityManagerService2.mCallFinishBooting);
                m.append(" mBootAnimationComplete=");
                m.append(activityManagerService2.mBootAnimationComplete);
                printWriter.println(m.toString());
                printWriter.print("  mLastPowerCheckUptime=");
                TimeUtils.formatDuration(
                        activityManagerService2.mLastPowerCheckUptime, printWriter);
                printWriter.println("");
                OomAdjuster oomAdjuster = activityManagerService2.mOomAdjuster;
                StringBuilder sb4 = new StringBuilder("  mAdjSeq=");
                sb4.append(oomAdjuster.mAdjSeq);
                sb4.append(" mLruSeq=");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        sb4, oomAdjuster.mProcessList.mLruSeq, printWriter);
                OomAdjuster oomAdjuster2 = activityManagerService2.mOomAdjuster;
                StringBuilder sb5 = new StringBuilder("  mNumNonCachedProcs=");
                sb5.append(oomAdjuster2.mNumNonCachedProcs);
                sb5.append(" (");
                sb5.append(oomAdjuster2.mProcessList.mLruProcesses.size());
                sb5.append(" total) mNumCachedHiddenProcs=");
                sb5.append(oomAdjuster2.mNumCachedHiddenProcs);
                sb5.append(" mNumServiceProcs=");
                sb5.append(oomAdjuster2.mNumServiceProcs);
                sb5.append(" mNewNumServiceProcs=");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        sb5, oomAdjuster2.mNewNumServiceProcs, printWriter);
                AppProfiler appProfiler2 = activityManagerService2.mAppProfiler;
                printWriter.println(
                        "  mAllowLowerMemLevel="
                                + appProfiler2.mAllowLowerMemLevel
                                + " mLastMemoryLevel="
                                + appProfiler2.mLastMemoryLevel
                                + " mLastNumProcesses="
                                + appProfiler2.mLastNumProcesses);
                long uptimeMillis = SystemClock.uptimeMillis();
                printWriter.print("  mLastIdleTime=");
                TimeUtils.formatDuration(
                        uptimeMillis, activityManagerService2.mLastIdleTime, printWriter);
                printWriter.print(" mLowRamSinceLastIdle=");
                TimeUtils.formatDuration(
                        activityManagerService2.mAppProfiler.getLowRamTimeSinceIdleLPr(
                                uptimeMillis),
                        printWriter);
                printWriter.println();
                printWriter.println();
                printWriter.println("  ServiceManager statistics:");
                ServiceManager.sStatLogger.dump(printWriter, "    ");
                printWriter.println();
            }
        }
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("  mForceBackgroundCheck="),
                        activityManagerService2.mForceBackgroundCheck,
                        printWriter,
                        "  CUR_TRIM_EMPTY_PROCESSES:");
        m2.append(activityManagerService2.mConstants.CUR_TRIM_EMPTY_PROCESSES);
        printWriter.print(m2.toString());
        printWriter.print(
                "  CUR_TRIM_CACHED_PROCESSES:"
                        + activityManagerService2.mConstants.CUR_TRIM_CACHED_PROCESSES);
    }

    public final ActivityManagerService.ProcessChangeItem enqueueProcessChangeItemLocked(
            int i, int i2) {
        ActivityManagerService.ProcessChangeItem processChangeItem;
        synchronized (this.mProcessChangeLock) {
            try {
                int size = this.mPendingProcessChanges.size() - 1;
                processChangeItem = null;
                while (size >= 0) {
                    processChangeItem =
                            (ActivityManagerService.ProcessChangeItem)
                                    this.mPendingProcessChanges.get(size);
                    if (processChangeItem.pid != i) {
                        size--;
                    }
                }
                if (size < 0) {
                    int size2 = this.mAvailProcessChanges.size();
                    processChangeItem =
                            size2 > 0
                                    ? (ActivityManagerService.ProcessChangeItem)
                                            this.mAvailProcessChanges.remove(size2 - 1)
                                    : new ActivityManagerService.ProcessChangeItem();
                    processChangeItem.changes = 0;
                    processChangeItem.pid = i;
                    processChangeItem.uid = i2;
                    if (this.mPendingProcessChanges.size() == 0) {
                        processChangeItem.triggered = true;
                    }
                    this.mPendingProcessChanges.add(processChangeItem);
                }
            } finally {
            }
        }
        return processChangeItem;
    }

    public final void fillInProcMemInfoLOSP(
            ProcessRecord processRecord,
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo,
            int i) {
        int i2;
        int i3;
        runningAppProcessInfo.pid = processRecord.mPid;
        runningAppProcessInfo.uid = processRecord.info.uid;
        WindowProcessController windowProcessController = processRecord.mWindowProcessController;
        boolean z = false;
        if (windowProcessController == windowProcessController.mAtm.mHeavyWeightProcess) {
            runningAppProcessInfo.flags |= 1;
        }
        if (processRecord.mPersistent) {
            runningAppProcessInfo.flags |= 2;
        }
        if (processRecord.mWindowProcessController.mHasActivities) {
            runningAppProcessInfo.flags |= 4;
        }
        runningAppProcessInfo.lastTrimLevel = processRecord.mProfile.mTrimMemoryLevel;
        ProcessStateRecord processStateRecord = processRecord.mState;
        int i4 = processStateRecord.mCurAdj;
        int i5 = processStateRecord.mCurProcState;
        int procStateToImportanceForTargetSdk =
                ActivityManager.RunningAppProcessInfo.procStateToImportanceForTargetSdk(i5, i);
        if (procStateToImportanceForTargetSdk == 400) {
            runningAppProcessInfo.lru = i4;
        } else {
            runningAppProcessInfo.lru = 0;
        }
        runningAppProcessInfo.importance = procStateToImportanceForTargetSdk;
        runningAppProcessInfo.importanceReasonCode = processStateRecord.mAdjTypeCode;
        runningAppProcessInfo.processState = i5;
        runningAppProcessInfo.isFocused = processRecord == this.mService.getTopApp();
        runningAppProcessInfo.lastActivityTime = processRecord.mLastActivityTime;
        ProcessProfileRecord processProfileRecord = processRecord.mProfile;
        runningAppProcessInfo.lastPss = processProfileRecord.mLastPss;
        runningAppProcessInfo.lastSwapPss = processProfileRecord.mLastSwapPss;
        ProcessState processState = processProfileRecord.mBaseProcessTracker;
        if (processState != null) {
            long[] totalRunningPss = processState.getTotalRunningPss();
            if (totalRunningPss[0] != 0) {
                runningAppProcessInfo.avgPss = totalRunningPss[2];
                runningAppProcessInfo.minPss = totalRunningPss[1];
                runningAppProcessInfo.maxPss = totalRunningPss[3];
            }
        }
        if (this.mDynamicHiddenApp == null) {
            boolean z2 = DynamicHiddenApp.DEBUG;
            this.mDynamicHiddenApp = DynamicHiddenApp.DhaClassLazyHolder.INSTANCE;
        }
        this.mDynamicHiddenApp.mBGProtectManager.getClass();
        if (!DynamicHiddenApp.sHH_AMSExceptionEnable
                ? !(((i2 = processRecord.dhaKeepEmptyFlag) <= 0 || i2 >= 4)
                        && (!processRecord.isAMSException
                                || processRecord.AMSExceptionFlag
                                        == BGProtectManager.exceptFlag.BROWSERMAIN.getValue()))
                : !(((i3 = processRecord.dhaKeepEmptyFlag) <= 0 || i3 >= 4)
                        && !processRecord.isAMSException)) {
            z = true;
        }
        runningAppProcessInfo.isProtectedInPicked =
                BGProtectManager.isOnlyActCheck(processRecord) ? true : z;
        if (processRecord.mServices.mServices.size() > 0) {
            runningAppProcessInfo.flags |= 8;
        }
    }

    public final ProcessRecord findAppProcessLOSP(IBinder iBinder, String str) {
        MyProcessMap myProcessMap = this.mProcessNames;
        int size = myProcessMap.getMap().size();
        for (int i = 0; i < size; i++) {
            SparseArray sparseArray = (SparseArray) myProcessMap.getMap().valueAt(i);
            int size2 = sparseArray.size();
            for (int i2 = 0; i2 < size2; i2++) {
                ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i2);
                IApplicationThread iApplicationThread = processRecord.mThread;
                if (iApplicationThread != null && iApplicationThread.asBinder() == iBinder) {
                    return processRecord;
                }
            }
        }
        StringBuilder m =
                DumpUtils$$ExternalSyntheticOutline0.m(
                        "Can't find mystery application for ", str, " from pid=");
        m.append(Binder.getCallingPid());
        m.append(" uid=");
        m.append(Binder.getCallingUid());
        m.append(": ");
        m.append(iBinder);
        Slog.w("ActivityManager", m.toString());
        return null;
    }

    public final void forEachLruProcessesLOSP(Consumer consumer, boolean z) {
        if (!z) {
            for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
                consumer.accept((ProcessRecord) this.mLruProcesses.get(size));
            }
            return;
        }
        int size2 = this.mLruProcesses.size();
        for (int i = 0; i < size2; i++) {
            consumer.accept((ProcessRecord) this.mLruProcesses.get(i));
        }
    }

    public int getBlockStateForUid(UidRecord uidRecord) {
        boolean z =
                NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(
                                uidRecord.mCurProcState, uidRecord.mCurCapability)
                        || NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(
                                uidRecord.mCurProcState, uidRecord.mCurCapability);
        boolean z2 =
                NetworkPolicyManager.isProcStateAllowedWhileIdleOrPowerSaveMode(
                                uidRecord.mSetProcState, uidRecord.mSetCapability)
                        || NetworkPolicyManager.isProcStateAllowedWhileOnRestrictBackground(
                                uidRecord.mSetProcState, uidRecord.mSetCapability);
        if (z2 || !z) {
            return (!z2 || z) ? 0 : 2;
        }
        return 1;
    }

    public final ProcessRecord getLRURecordForAppLOSP(IApplicationThread iApplicationThread) {
        if (iApplicationThread == null) {
            return null;
        }
        return getLRURecordForAppLOSP(iApplicationThread.asBinder());
    }

    public final ProcessRecord getLRURecordForAppLOSP(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            ProcessRecord processRecord = (ProcessRecord) this.mLruProcesses.get(size);
            IApplicationThread iApplicationThread = processRecord.mThread;
            if (iApplicationThread != null && iApplicationThread.asBinder() == iBinder) {
                return processRecord;
            }
        }
        return null;
    }

    public final long getMemLevel(int i) {
        int i2;
        int i3 = 0;
        while (true) {
            int[] iArr = this.mOomAdj;
            int length = iArr.length;
            int[] iArr2 = this.mOomMinFree;
            if (i3 >= length) {
                i2 = iArr2[iArr.length - 1];
                break;
            }
            if (i <= iArr[i3]) {
                i2 = iArr2[i3];
                break;
            }
            i3++;
        }
        return i2 * 1024;
    }

    public ProcessListSettingsListener getProcessListSettingsListener() {
        ProcessListSettingsListener processListSettingsListener;
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            try {
                if (this.mProcessListSettingsListener == null) {
                    ProcessListSettingsListener processListSettingsListener2 =
                            new ProcessListSettingsListener(this.mService.mContext);
                    this.mProcessListSettingsListener = processListSettingsListener2;
                    DeviceConfig.addOnPropertiesChangedListener(
                            "adservices",
                            processListSettingsListener2.mContext.getMainExecutor(),
                            processListSettingsListener2);
                }
                processListSettingsListener = this.mProcessListSettingsListener;
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
        return processListSettingsListener;
    }

    public final ProcessRecord getProcessRecordLocked(int i, String str) {
        MyProcessMap myProcessMap = this.mProcessNames;
        if (i == 1000) {
            SparseArray sparseArray = (SparseArray) myProcessMap.getMap().get(str);
            if (sparseArray == null) {
                return null;
            }
            int size = sparseArray.size();
            for (int i2 = 0; i2 < size; i2++) {
                int keyAt = sparseArray.keyAt(i2);
                if (UserHandle.isCore(keyAt) && UserHandle.isSameUser(keyAt, i)) {
                    return (ProcessRecord) sparseArray.valueAt(i2);
                }
            }
        }
        return (ProcessRecord) myProcessMap.get(str, i);
    }

    public final List getRunningAppProcessesLOSP(int i, int i2, int i3, boolean z, boolean z2) {
        ArrayList arrayList = null;
        for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
            ProcessRecord processRecord = (ProcessRecord) this.mLruProcesses.get(size);
            ProcessStateRecord processStateRecord = processRecord.mState;
            ProcessErrorStateRecord processErrorStateRecord = processRecord.mErrorState;
            if ((z || processRecord.userId == i)
                    && ((z2 || processRecord.uid == i2)
                            && processRecord.mThread != null
                            && !processErrorStateRecord.mCrashing
                            && !processErrorStateRecord.mNotResponding)) {
                ActivityManager.RunningAppProcessInfo runningAppProcessInfo =
                        new ActivityManager.RunningAppProcessInfo(
                                processRecord.processName,
                                processRecord.mPid,
                                processRecord.mPkgList.getPackageList());
                ArraySet arraySet = processRecord.mPkgDeps;
                if (arraySet != null) {
                    runningAppProcessInfo.pkgDeps =
                            (String[]) processRecord.mPkgDeps.toArray(new String[arraySet.size()]);
                }
                fillInProcMemInfoLOSP(processRecord, runningAppProcessInfo, i3);
                Object obj = processStateRecord.mAdjSource;
                if (obj instanceof ProcessRecord) {
                    runningAppProcessInfo.importanceReasonPid = ((ProcessRecord) obj).mPid;
                    runningAppProcessInfo.importanceReasonImportance =
                            ActivityManager.RunningAppProcessInfo.procStateToImportance(
                                    processStateRecord.mAdjSourceProcState);
                } else if (obj instanceof ActivityServiceConnectionsHolder) {
                    WindowProcessController windowProcessController =
                            ((ActivityServiceConnectionsHolder) obj).mActivity.app;
                    int i4 = windowProcessController != null ? windowProcessController.mPid : -1;
                    if (i4 != -1) {
                        runningAppProcessInfo.importanceReasonPid = i4;
                    }
                }
                Object obj2 = processStateRecord.mAdjTarget;
                if (obj2 instanceof ComponentName) {
                    runningAppProcessInfo.importanceReasonComponent = (ComponentName) obj2;
                }
                if (arrayList == null) {
                    arrayList = new ArrayList();
                }
                arrayList.add(runningAppProcessInfo);
            }
        }
        return arrayList;
    }

    public final ProcessRecord getSharedIsolatedProcess(int i, String str, String str2) {
        int size = this.mIsolatedProcesses.size();
        for (int i2 = 0; i2 < size; i2++) {
            ProcessRecord processRecord = (ProcessRecord) this.mIsolatedProcesses.valueAt(i2);
            if (processRecord.info.uid == i
                    && processRecord.info.packageName.equals(str2)
                    && processRecord.processName.equals(str)) {
                return processRecord;
            }
        }
        return null;
    }

    public final int getUidProcStateLOSP(int i) {
        UidRecord uidRecord = this.mActiveUids.get(i);
        if (uidRecord == null) {
            return 20;
        }
        return uidRecord.mCurProcState;
    }

    public final int getUidProcessCapabilityLOSP(int i) {
        UidRecord uidRecord = this.mActiveUids.get(i);
        if (uidRecord == null) {
            return 0;
        }
        return uidRecord.mCurCapability;
    }

    public final boolean handlePrecedingAppDiedLocked(ProcessRecord processRecord) {
        if (processRecord.mSuccessor == null) {
            return true;
        }
        if (processRecord.mPersistent
                && !processRecord.mRemoved
                && this.mService.mPersistentStartingProcesses.indexOf(processRecord.mSuccessor)
                        < 0) {
            this.mService.mPersistentStartingProcesses.add(processRecord.mSuccessor);
        }
        processRecord.mSuccessor.mPredecessor = null;
        processRecord.mSuccessor = null;
        this.mService.mProcStartHandler.removeMessages(2, processRecord);
        this.mService.mProcStartHandler.obtainMessage(1, processRecord).sendToTarget();
        return false;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean handleProcessStartedLocked(
            com.android.server.am.ProcessRecord r16, int r17, boolean r18, long r19, boolean r21) {
        /*
            Method dump skipped, instructions count: 1148
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessList.handleProcessStartedLocked(com.android.server.am.ProcessRecord,"
                    + " int, boolean, long, boolean):boolean");
    }

    public void incrementProcStateSeqAndNotifyAppsLOSP(ActiveUids activeUids) {
        int blockStateForUid;
        for (int size = activeUids.mActiveUids.size() - 1; size >= 0; size--) {
            UidRecord valueAt = activeUids.valueAt(size);
            long j = this.mProcStateSeqCounter + 1;
            this.mProcStateSeqCounter = j;
            valueAt.curProcStateSeq = j;
        }
        if (this.mService.mConstants.mNetworkAccessTimeoutMs <= 0) {
            return;
        }
        ArrayList arrayList = null;
        for (int size2 = activeUids.mActiveUids.size() - 1; size2 >= 0; size2--) {
            UidRecord valueAt2 = activeUids.valueAt(size2);
            ActivityManagerService.Injector injector = this.mService.mInjector;
            int i = valueAt2.mUid;
            if (injector.mNmi == null) {
                injector.mNmi =
                        (NetworkManagementService.LocalService)
                                LocalServices.getService(
                                        NetworkManagementService.LocalService.class);
            }
            NetworkManagementService.LocalService localService = injector.mNmi;
            if ((localService != null
                            ? NetworkManagementService.this.isNetworkRestrictedInternal(i)
                            : false)
                    && UserHandle.isApp(valueAt2.mUid)
                    && valueAt2.hasInternetPermission
                    && ((valueAt2.mSetProcState != valueAt2.mCurProcState
                                    || valueAt2.mSetCapability != valueAt2.mCurCapability)
                            && (blockStateForUid = getBlockStateForUid(valueAt2)) != 0)) {
                synchronized (valueAt2.networkStateLock) {
                    if (blockStateForUid == 1) {
                        if (arrayList == null) {
                            try {
                                arrayList = new ArrayList();
                            } finally {
                            }
                        }
                        arrayList.add(Integer.valueOf(valueAt2.mUid));
                    } else if (valueAt2.procStateSeqWaitingForNetwork != 0) {
                        valueAt2.networkStateLock.notifyAll();
                    }
                }
            }
        }
        if (arrayList == null) {
            return;
        }
        for (int size3 = this.mLruProcesses.size() - 1; size3 >= 0; size3--) {
            ProcessRecord processRecord = (ProcessRecord) this.mLruProcesses.get(size3);
            if (arrayList.contains(Integer.valueOf(processRecord.uid))) {
                IApplicationThread iApplicationThread = processRecord.mThread;
                if (!processRecord.mKilledByAm && iApplicationThread != null) {
                    UidRecord uidRecord = this.mActiveUids.get(processRecord.uid);
                    if (uidRecord != null) {
                        try {
                            iApplicationThread.setNetworkBlockSeq(uidRecord.curProcStateSeq);
                        } catch (RemoteException unused) {
                        }
                    }
                }
            }
        }
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void init(
            com.android.server.am.ActivityManagerService r6,
            com.android.server.am.ActiveUids r7,
            com.android.server.compat.PlatformCompat r8) {
        /*
            Method dump skipped, instructions count: 362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessList.init(com.android.server.am.ActivityManagerService,"
                    + " com.android.server.am.ActiveUids,"
                    + " com.android.server.compat.PlatformCompat):void");
    }

    public final void killAllBackgroundProcessesExceptLSP(int i, int i2) {
        ArrayList arrayList = new ArrayList();
        int size = this.mProcessNames.getMap().size();
        for (int i3 = 0; i3 < size; i3++) {
            SparseArray sparseArray = (SparseArray) this.mProcessNames.getMap().valueAt(i3);
            int size2 = sparseArray.size();
            for (int i4 = 0; i4 < size2; i4++) {
                ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i4);
                if (processRecord.mRemoved
                        || ((i < 0 || processRecord.info.targetSdkVersion < i)
                                && (i2 < 0 || processRecord.mState.mSetProcState > i2))) {
                    arrayList.add(processRecord);
                }
            }
        }
        int size3 = arrayList.size();
        for (int i5 = 0; i5 < size3; i5++) {
            removeProcessLocked(
                    (ProcessRecord) arrayList.get(i5),
                    false,
                    true,
                    13,
                    10,
                    "kill all background except",
                    true);
        }
    }

    public final long killAppIfBgRestrictedAndCachedIdleLocked(
            ProcessRecord processRecord, long j) {
        UidRecord uidRecord = processRecord.mUidRecord;
        long j2 = processRecord.mState.mLastCanKillOnBgRestrictedAndIdleTime;
        if (this.mService.mConstants.mKillBgRestrictedAndCachedIdle
                && !processRecord.mKilled
                && processRecord.mThread != null
                && uidRecord != null
                && uidRecord.mIdle
                && processRecord.mState.isCached()) {
            ProcessStateRecord processStateRecord = processRecord.mState;
            if (!processStateRecord.mNoKillOnBgRestrictedAndIdle
                    && processStateRecord.mBackgroundRestricted
                    && j2 != 0) {
                long j3 = j2 + this.mService.mConstants.mKillBgRestrictedAndCachedIdleSettleTimeMs;
                if (j3 > j) {
                    return j3;
                }
                processRecord.killLocked(
                        13,
                        18,
                        "cached idle & background restricted",
                        "cached idle & background restricted",
                        true,
                        true);
                return 0L;
            }
        }
        return 0L;
    }

    public final void killAppZygoteIfNeededLocked(AppZygote appZygote, boolean z) {
        ApplicationInfo appInfo = appZygote.getAppInfo();
        ArrayList arrayList = (ArrayList) this.mAppZygoteProcesses.get(appZygote);
        if (arrayList != null) {
            if (z || arrayList.size() == 0) {
                this.mAppZygotes.remove(appInfo.processName, appInfo.uid);
                this.mAppZygoteProcesses.remove(appZygote);
                IsolatedUidRangeAllocator isolatedUidRangeAllocator =
                        this.mAppIsolatedUidRangeAllocator;
                IsolatedUidRange isolatedUidRange =
                        (IsolatedUidRange)
                                isolatedUidRangeAllocator.mAppRanges.get(
                                        appInfo.processName, appInfo.uid);
                if (isolatedUidRange != null) {
                    isolatedUidRangeAllocator.mAvailableUidRanges.set(
                            (isolatedUidRange.mFirstUid - isolatedUidRangeAllocator.mFirstUid)
                                    / isolatedUidRangeAllocator.mNumUidsPerRange);
                    isolatedUidRangeAllocator.mAppRanges.remove(appInfo.processName, appInfo.uid);
                }
                appZygote.stopZygote();
            }
        }
    }

    public final void killAppZygotesLocked(int i, int i2, String str, boolean z) {
        ArrayList arrayList = new ArrayList();
        for (SparseArray sparseArray : this.mAppZygotes.getMap().values()) {
            for (int i3 = 0; i3 < sparseArray.size(); i3++) {
                int keyAt = sparseArray.keyAt(i3);
                if ((i2 == -1 || UserHandle.getUserId(keyAt) == i2)
                        && (i < 0 || UserHandle.getAppId(keyAt) == i)) {
                    AppZygote appZygote = (AppZygote) sparseArray.valueAt(i3);
                    if (str == null || str.equals(appZygote.getAppInfo().packageName)) {
                        arrayList.add(appZygote);
                    }
                }
            }
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            killAppZygoteIfNeededLocked((AppZygote) it.next(), z);
        }
    }

    public final boolean killPackageProcessesLSP(
            String str,
            int i,
            int i2,
            int i3,
            boolean z,
            boolean z2,
            boolean z3,
            boolean z4,
            boolean z5,
            boolean z6,
            int i4,
            int i5,
            String str2) {
        boolean z7;
        boolean z8;
        boolean z9;
        int freezeBinder;
        boolean containsKey;
        boolean z10;
        boolean z11;
        ArraySet arraySet;
        PackageManagerInternal packageManagerInternal = this.mService.getPackageManagerInternal();
        ArrayList arrayList = new ArrayList();
        int size = this.mProcessNames.getMap().size();
        for (int i6 = 0; i6 < size; i6++) {
            SparseArray sparseArray = (SparseArray) this.mProcessNames.getMap().valueAt(i6);
            int size2 = sparseArray.size();
            for (int i7 = 0; i7 < size2; i7++) {
                ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i7);
                if (!processRecord.mPersistent || z4) {
                    if (!processRecord.mRemoved) {
                        if (processRecord.mState.mSetAdj >= i3) {
                            if (str == null) {
                                if (i2 != -1) {
                                    if (processRecord.userId != i2) {
                                        continue;
                                    }
                                }
                                if (i >= 0 && UserHandle.getAppId(processRecord.uid) != i) {}
                            } else {
                                ArraySet arraySet2 = processRecord.mPkgDeps;
                                boolean z12 = arraySet2 != null && arraySet2.contains(str);
                                if ((z12 || UserHandle.getAppId(processRecord.uid) == i)
                                        && ((i2 == -1 || processRecord.userId == i2)
                                                && ((containsKey =
                                                                processRecord.mPkgList.containsKey(
                                                                        str))
                                                        || z12))) {
                                    if (!containsKey && z12 && !z6 && processRecord.info != null) {
                                        z10 =
                                                packageManagerInternal.isPackageFrozen(
                                                                processRecord.uid,
                                                                processRecord.userId,
                                                                processRecord.info.packageName)
                                                        ? false
                                                        : true;
                                    }
                                }
                            }
                            if (!z3) {
                                return true;
                            }
                            if (z5) {
                                processRecord.mRemoved = true;
                            }
                            arrayList.add(new Pair(processRecord, Boolean.valueOf(z10)));
                        } else {
                            continue;
                        }
                    } else if (z3) {
                        if (!z6
                                && str != null
                                && !processRecord.mPkgList.containsKey(str)
                                && (arraySet = processRecord.mPkgDeps) != null
                                && arraySet.contains(str)
                                && processRecord.info != null) {
                            if (!packageManagerInternal.isPackageFrozen(
                                    processRecord.uid,
                                    processRecord.userId,
                                    processRecord.info.packageName)) {
                                z11 = true;
                                arrayList.add(new Pair(processRecord, Boolean.valueOf(z11)));
                            }
                        }
                        z11 = false;
                        arrayList.add(new Pair(processRecord, Boolean.valueOf(z11)));
                    }
                }
            }
        }
        boolean z13 = i >= 10000 && i <= 19999;
        if (z13) {
            arrayList.sort(new ProcessList$$ExternalSyntheticLambda3());
        }
        int i8 = 0;
        while (i8 < arrayList.size()) {
            int i9 = ((ProcessRecord) ((Pair) arrayList.get(i8)).first).uid;
            int i10 = i8 + 1;
            while (i10 < arrayList.size()
                    && ((ProcessRecord) ((Pair) arrayList.get(i10)).first).uid == i9) {
                i10++;
            }
            List<Pair> subList = arrayList.subList(i8, i10);
            int i11 = ((ProcessRecord) ((Pair) subList.get(0)).first).uid;
            boolean z14 = z13 && UserHandle.getAppId(i11) == i;
            if (z14) {
                int size3 = subList.size();
                int i12 = 0;
                while (i12 < size3) {
                    int i13 = ((ProcessRecord) ((Pair) subList.get(i12)).first).mPid;
                    int i14 = size3;
                    if (i13 > 0) {
                        int i15 = 0;
                        while (true) {
                            z9 = z13;
                            try {
                                freezeBinder = CachedAppOptimizer.freezeBinder(i13, true, 10);
                                if (freezeBinder != (-OsConstants.EAGAIN)) {
                                    break;
                                }
                                int i16 = i15 + 1;
                                if (i15 >= 1) {
                                    break;
                                }
                                z13 = z9;
                                i15 = i16;
                            } catch (RuntimeException e) {
                                Slog.e(
                                        "ActivityManager",
                                        "Unable to freeze binder for " + i13 + ": " + e);
                            }
                        }
                        if (freezeBinder != 0) {
                            Slog.e(
                                    "ActivityManager",
                                    "Unable to freeze binder for " + i13 + ": " + freezeBinder);
                        }
                    } else {
                        z9 = z13;
                    }
                    i12++;
                    size3 = i14;
                    z13 = z9;
                }
                z7 = z13;
                z8 = true;
                freezePackageCgroup(i11, true);
            } else {
                z7 = z13;
                z8 = true;
            }
            for (Pair pair : subList) {
                removeProcessLocked(
                        (ProcessRecord) pair.first,
                        z,
                        (z2 || ((Boolean) pair.second).booleanValue()) ? z8 : false,
                        i4,
                        i5,
                        str2,
                        !z14);
                i11 = i11;
                subList = subList;
                z8 = true;
            }
            int i17 = i11;
            List list = subList;
            killAppZygotesLocked(i, i2, str, false);
            if (z14) {
                freezePackageCgroup(i17, false);
            }
            i8 += list.size();
            z13 = z7;
        }
        this.mService.updateOomAdjLocked(12);
        return arrayList.size() > 0;
    }

    public final void killProcessesWhenImperceptible(int i, String str, int[] iArr) {
        ProcessRecord processRecord;
        if (ArrayUtils.isEmpty(iArr)) {
            return;
        }
        ActivityManagerService activityManagerService = this.mService;
        ActivityManagerService.boostPriorityForLockedSection();
        synchronized (activityManagerService) {
            for (int i2 : iArr) {
                try {
                    synchronized (this.mService.mPidsSelfLocked) {
                        processRecord = this.mService.mPidsSelfLocked.get(i2);
                    }
                    if (processRecord != null) {
                        this.mImperceptibleKillRunner.enqueueLocked(i, processRecord, str);
                    }
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
        }
        ActivityManagerService.resetPriorityAfterLockedSection();
    }

    public final ProcessRecord newProcessRecordLocked(
            ApplicationInfo applicationInfo,
            String str,
            boolean z,
            int i,
            boolean z2,
            int i2,
            String str2,
            HostingRecord hostingRecord) {
        int i3;
        boolean z3;
        IsolatedUidRange isolatedUidRange;
        String str3 = str != null ? str : applicationInfo.processName;
        int userId = UserHandle.getUserId(applicationInfo.uid);
        int i4 = applicationInfo.uid;
        if (z2) {
            i4 = i2;
        }
        if (Process.isSdkSandboxUid(i4) && (!z2 || str2 == null)) {
            Slog.e(
                    "ActivityManager",
                    "Abort creating new sandbox process as required parameters are missing.");
            return null;
        }
        if (z) {
            if (i == 0) {
                if (hostingRecord == null || hostingRecord.mHostingZygote != 2) {
                    isolatedUidRange = this.mGlobalIsolatedUids;
                } else {
                    IsolatedUidRangeAllocator isolatedUidRangeAllocator =
                            this.mAppIsolatedUidRangeAllocator;
                    String str4 = applicationInfo.processName;
                    int i5 = hostingRecord.mDefiningUid;
                    isolatedUidRange =
                            (IsolatedUidRange) isolatedUidRangeAllocator.mAppRanges.get(str4, i5);
                    if (isolatedUidRange == null) {
                        int nextSetBit =
                                isolatedUidRangeAllocator.mAvailableUidRanges.nextSetBit(0);
                        if (nextSetBit < 0) {
                            isolatedUidRange = null;
                        } else {
                            isolatedUidRangeAllocator.mAvailableUidRanges.clear(nextSetBit);
                            int i6 = isolatedUidRangeAllocator.mNumUidsPerRange;
                            int i7 = (nextSetBit * i6) + isolatedUidRangeAllocator.mFirstUid;
                            IsolatedUidRange isolatedUidRange2 =
                                    new IsolatedUidRange(i7, (i6 + i7) - 1);
                            isolatedUidRangeAllocator.mAppRanges.put(str4, i5, isolatedUidRange2);
                            isolatedUidRange = isolatedUidRange2;
                        }
                    }
                }
                if (isolatedUidRange == null) {
                    return null;
                }
                int i8 = (isolatedUidRange.mLastUid - isolatedUidRange.mFirstUid) + 1;
                int i9 = 0;
                while (true) {
                    if (i9 >= i8) {
                        i3 = -1;
                        break;
                    }
                    int i10 = isolatedUidRange.mNextUid;
                    int i11 = isolatedUidRange.mFirstUid;
                    if (i10 < i11 || i10 > isolatedUidRange.mLastUid) {
                        isolatedUidRange.mNextUid = i11;
                    }
                    i3 = UserHandle.getUid(userId, isolatedUidRange.mNextUid);
                    isolatedUidRange.mNextUid++;
                    if (!isolatedUidRange.mUidUsed.get(i3, false)) {
                        isolatedUidRange.mUidUsed.put(i3, true);
                        break;
                    }
                    i9++;
                }
                if (i3 == -1) {
                    return null;
                }
            } else {
                i3 = i;
            }
            AppExitInfoTracker.IsolatedUidRecords isolatedUidRecords =
                    this.mAppExitInfoTracker.mIsolatedUidRecords;
            int i12 = applicationInfo.uid;
            synchronized (AppExitInfoTracker.this.mLock) {
                try {
                    ArraySet arraySet = (ArraySet) isolatedUidRecords.mUidToIsolatedUidMap.get(i12);
                    if (arraySet == null) {
                        arraySet = new ArraySet();
                        isolatedUidRecords.mUidToIsolatedUidMap.put(i12, arraySet);
                    }
                    arraySet.add(Integer.valueOf(i3));
                    isolatedUidRecords.mIsolatedUidToUidMap.put(i3, Integer.valueOf(i12));
                } catch (Throwable th) {
                    throw th;
                }
            }
            PackageManagerInternal packageManagerInternal =
                    this.mService.getPackageManagerInternal();
            int i13 = applicationInfo.uid;
            PackageManagerService.PackageManagerInternalImpl packageManagerInternalImpl =
                    (PackageManagerService.PackageManagerInternalImpl) packageManagerInternal;
            PackageManagerTracedLock packageManagerTracedLock = PackageManagerService.this.mLock;
            boolean z4 = PackageManagerService.DEBUG_COMPRESSION;
            synchronized (packageManagerTracedLock) {
                try {
                    WatchedSparseIntArray watchedSparseIntArray =
                            PackageManagerService.this.mIsolatedOwners;
                    watchedSparseIntArray.mStorage.put(i3, i13);
                    watchedSparseIntArray.dispatchChange(watchedSparseIntArray);
                } catch (Throwable th2) {
                    boolean z5 = PackageManagerService.DEBUG_COMPRESSION;
                    throw th2;
                }
            }
            BatteryStatsService batteryStatsService = this.mService.mBatteryStatsService;
            int i14 = applicationInfo.uid;
            PowerStatsUidResolver powerStatsUidResolver =
                    batteryStatsService.mPowerStatsUidResolver;
            synchronized (powerStatsUidResolver) {
                powerStatsUidResolver.mIsolatedUids.put(i3, i14);
                powerStatsUidResolver.mIsolatedUidRefCounts.put(i3, 1);
            }
            List list = powerStatsUidResolver.mListeners;
            for (int size = list.size() - 1; size >= 0; size--) {
                ((PowerStatsUidResolver.Listener) list.get(size)).onIsolatedUidAdded(i3, i14);
            }
            FrameworkStatsLog.write(43, i14, i3, 1);
            if (this.mService.getCfmsInternalLocked() != null) {
                this.mService.getCfmsInternalLocked().addIsolatedUid(i3, applicationInfo.uid);
            }
        } else {
            i3 = i4;
        }
        ProcessRecord processRecord =
                new ProcessRecord(
                        this.mService,
                        applicationInfo,
                        str3,
                        i3,
                        str2,
                        hostingRecord.mDefiningUid,
                        hostingRecord.mDefiningProcessName);
        ProcessStateRecord processStateRecord = processRecord.mState;
        boolean isStopped = applicationInfo.isStopped();
        if ("activity-on-dex".equals(hostingRecord.mHostingType)
                || "top-activity-on-dex".equals(hostingRecord.mHostingType)) {
            WindowProcessController windowProcessController =
                    processRecord.mWindowProcessController;
            if (windowProcessController.mIsActivityConfigOverrideAllowed) {
                windowProcessController.mAdjustBindAppToDexConfig = true;
            }
        }
        if (isStopped) {
            if (android.app.Flags.useAppInfoNotLaunched()) {
                z3 = !applicationInfo.isNotLaunched();
            } else {
                try {
                    z3 =
                            this.mService
                                    .getPackageManagerInternal()
                                    .wasPackageEverLaunched(
                                            processRecord.userId, processRecord.info.packageName);
                } catch (IllegalArgumentException unused) {
                    z3 = false;
                }
            }
            String str5 = hostingRecord.mHostingType;
            if ("activity".equals(str5)
                    || "next-activity".equals(str5)
                    || "next-top-activity".equals(str5)
                    || "top-activity".equals(str5)
                    || "activity-on-dex".equals(str5)
                    || "top-activity-on-dex".equals(str5)) {
                processRecord.mWindowProcessController.mStoppedState = z3 ? 2 : 1;
            } else if (android.app.Flags.useAppInfoNotLaunched()) {
                processRecord.mWasForceStopped = z3;
            } else {
                processRecord.mWasForceStopped = true;
            }
        }
        if (!z
                && !z2
                && userId == 0
                && (applicationInfo.flags & 9) == 9
                && TextUtils.equals(str3, applicationInfo.processName)) {
            processStateRecord.setCurrentSchedulingGroup(2);
            processStateRecord.mSetSchedGroup = 2;
            processRecord.mPersistent = true;
            processRecord.mWindowProcessController.mPersistent = true;
            processStateRecord.mMaxAdj = -800;
        }
        if (z && i != 0) {
            processStateRecord.mMaxAdj = -700;
        }
        this.mService.mExt.getClass();
        if (!Build.IS_USER
                && Constants.SYSTEMUI_PACKAGE_NAME.equals(processRecord.processName)
                && processRecord.userId == 0
                && (processRecord.info.flags & 9) == 9) {
            processRecord.mPersistent = true;
            processRecord.mWindowProcessController.mPersistent = true;
            processRecord.mState.mMaxAdj = -800;
        }
        ActivityManagerServiceExt activityManagerServiceExt = this.mService.mExt;
        activityManagerServiceExt.getClass();
        if (!processRecord.mPersistent
                && processRecord.userId == 0
                && (processRecord.info.flags & 9) == 9
                && activityManagerServiceExt.persistentApps.contains(processRecord.processName)) {
            Slog.v("ActivityManagerServiceExt", "Set as " + processRecord.processName);
            ProcessStateRecord processStateRecord2 = processRecord.mState;
            processStateRecord2.setCurrentSchedulingGroup(2);
            processStateRecord2.mSetSchedGroup = 2;
            processRecord.mPersistent = true;
            processRecord.mWindowProcessController.mPersistent = true;
            processStateRecord2.mMaxAdj = -800;
        }
        this.mService.parseDexKillProcessTimeout(processRecord);
        addProcessNameLocked(processRecord);
        ActivityManagerServiceExt activityManagerServiceExt2 = this.mService.mExt;
        String str6 = processRecord.info != null ? processRecord.info.packageName : "";
        if (activityManagerServiceExt2.mCanTmoPkgAvoidForceStop
                && "com.tmobile.echolocate".equals(str6)) {
            processStateRecord.mMaxAdj = -800;
        }
        return processRecord;
    }

    public final void noteAppKill(int i, int i2, int i3, int i4, String str) {
        ProcessRecord processRecord;
        ProcessRecord processRecord2;
        synchronized (this.mService.mPidsSelfLocked) {
            processRecord = this.mService.mPidsSelfLocked.get(i);
        }
        if (processRecord != null
                && processRecord.uid == i2
                && !processRecord.isolated
                && processRecord.mDeathRecipient != null) {
            this.mDyingProcesses.put(processRecord.processName, i2, processRecord);
            processRecord.mDyingPid = processRecord.mPid;
        }
        AppExitInfoTracker appExitInfoTracker = this.mAppExitInfoTracker;
        if (appExitInfoTracker.mAppExitInfoLoaded.get()) {
            synchronized (appExitInfoTracker.mService.mPidsSelfLocked) {
                processRecord2 = appExitInfoTracker.mService.mPidsSelfLocked.get(i);
            }
            if (processRecord2 == null) {
                return;
            }
            appExitInfoTracker.scheduleNoteAppKill(processRecord2, i3, i4, str);
        }
    }

    public final void noteAppKill(ProcessRecord processRecord, int i, int i2, String str) {
        if (processRecord.mPid > 0
                && !processRecord.isolated
                && processRecord.mDeathRecipient != null) {
            this.mDyingProcesses.put(processRecord.processName, processRecord.uid, processRecord);
            processRecord.mDyingPid = processRecord.mPid;
        }
        this.mAppExitInfoTracker.scheduleNoteAppKill(processRecord, i, i2, str);
    }

    public final void noteProcessDiedLocked(ProcessRecord processRecord) {
        Watchdog watchdog = Watchdog.getInstance();
        String str = processRecord.processName;
        int i = processRecord.mPid;
        watchdog.getClass();
        if (str.equals(StorageManagerService.sMediaStoreAuthorityProcessName)
                || str.equals("com.android.phone")) {
            Slog.i("Watchdog", "Interesting Java process " + str + " died. Pid " + i);
            synchronized (watchdog.mLock) {
                ((ArrayList) watchdog.mInterestingJavaPids).remove(Integer.valueOf(i));
            }
        }
        if (processRecord.mDeathRecipient == null
                && this.mDyingProcesses.get(processRecord.processName, processRecord.uid)
                        == processRecord) {
            this.mDyingProcesses.remove(processRecord.processName, processRecord.uid);
            processRecord.mDyingPid = 0;
        }
        AppExitInfoTracker appExitInfoTracker = this.mAppExitInfoTracker;
        appExitInfoTracker.getClass();
        if (processRecord.info != null && appExitInfoTracker.mAppExitInfoLoaded.get()) {
            appExitInfoTracker
                    .mKillHandler
                    .obtainMessage(
                            4103,
                            appExitInfoTracker.obtainRawRecord(
                                    processRecord, System.currentTimeMillis()))
                    .sendToTarget();
        }
    }

    public final void onSystemReady() {
        final AppStartInfoTracker appStartInfoTracker = this.mAppStartInfoTracker;
        appStartInfoTracker.getClass();
        boolean appStartInfo = android.app.Flags.appStartInfo();
        appStartInfoTracker.mEnabled = appStartInfo;
        if (appStartInfo) {
            final int i = 0;
            appStartInfoTracker.mService.mContext.registerReceiverForAllUsers(
                    new BroadcastReceiver() { // from class:
                        // com.android.server.am.AppStartInfoTracker.1
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ AppStartInfoTracker this$0;

                        public /* synthetic */ AnonymousClass1(
                                final AppStartInfoTracker appStartInfoTracker2, final int i2) {
                            r2 = i2;
                            r1 = appStartInfoTracker2;
                        }

                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            switch (r2) {
                                case 0:
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    if (intExtra >= 1) {
                                        r1.onUserRemoved(intExtra);
                                        break;
                                    }
                                    break;
                                default:
                                    if (!intent.getBooleanExtra(
                                            "android.intent.extra.REPLACING", false)) {
                                        r1.onPackageRemoved(
                                                intent.getData().getSchemeSpecificPart(),
                                                intent.getIntExtra(
                                                        "android.intent.extra.UID", -10000),
                                                intent.getBooleanExtra(
                                                        "android.intent.extra.REMOVED_FOR_ALL_USERS",
                                                        false));
                                        break;
                                    }
                                    break;
                            }
                        }
                    },
                    BatteryService$$ExternalSyntheticOutline0.m(
                            "android.intent.action.USER_REMOVED"),
                    null,
                    appStartInfoTracker2.mHandler);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
            intentFilter.addDataScheme("package");
            final int i2 = 1;
            appStartInfoTracker2.mService.mContext.registerReceiverForAllUsers(
                    new BroadcastReceiver() { // from class:
                        // com.android.server.am.AppStartInfoTracker.1
                        public final /* synthetic */ int $r8$classId;
                        public final /* synthetic */ AppStartInfoTracker this$0;

                        public /* synthetic */ AnonymousClass1(
                                final AppStartInfoTracker appStartInfoTracker2, final int i22) {
                            r2 = i22;
                            r1 = appStartInfoTracker2;
                        }

                        @Override // android.content.BroadcastReceiver
                        public final void onReceive(Context context, Intent intent) {
                            switch (r2) {
                                case 0:
                                    int intExtra =
                                            intent.getIntExtra(
                                                    "android.intent.extra.user_handle", -1);
                                    if (intExtra >= 1) {
                                        r1.onUserRemoved(intExtra);
                                        break;
                                    }
                                    break;
                                default:
                                    if (!intent.getBooleanExtra(
                                            "android.intent.extra.REPLACING", false)) {
                                        r1.onPackageRemoved(
                                                intent.getData().getSchemeSpecificPart(),
                                                intent.getIntExtra(
                                                        "android.intent.extra.UID", -10000),
                                                intent.getBooleanExtra(
                                                        "android.intent.extra.REMOVED_FOR_ALL_USERS",
                                                        false));
                                        break;
                                    }
                                    break;
                            }
                        }
                    },
                    intentFilter,
                    null,
                    appStartInfoTracker2.mHandler);
            IoThread.getHandler()
                    .post(
                            new AppStartInfoTracker$$ExternalSyntheticLambda4(
                                    appStartInfoTracker2, 1));
        }
        final AppExitInfoTracker appExitInfoTracker = this.mAppExitInfoTracker;
        appExitInfoTracker.getClass();
        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("android.intent.action.USER_REMOVED");
        final int i3 = 0;
        appExitInfoTracker.mService.mContext.registerReceiverForAllUsers(
                new BroadcastReceiver() { // from class: com.android.server.am.AppExitInfoTracker.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i3) {
                            case 0:
                                int intExtra =
                                        intent.getIntExtra("android.intent.extra.user_handle", -1);
                                if (intExtra >= 1) {
                                    appExitInfoTracker.onUserRemoved(intExtra);
                                    break;
                                }
                                break;
                            default:
                                if (!intent.getBooleanExtra(
                                        "android.intent.extra.REPLACING", false)) {
                                    appExitInfoTracker.onPackageRemoved(
                                            intent.getData().getSchemeSpecificPart(),
                                            intent.getIntExtra("android.intent.extra.UID", -10000),
                                            intent.getBooleanExtra(
                                                    "android.intent.extra.REMOVED_FOR_ALL_USERS",
                                                    false));
                                    break;
                                }
                                break;
                        }
                    }
                },
                intentFilter2,
                null,
                appExitInfoTracker.mKillHandler);
        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter3.addDataScheme("package");
        final int i4 = 1;
        appExitInfoTracker.mService.mContext.registerReceiverForAllUsers(
                new BroadcastReceiver() { // from class: com.android.server.am.AppExitInfoTracker.1
                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context, Intent intent) {
                        switch (i4) {
                            case 0:
                                int intExtra =
                                        intent.getIntExtra("android.intent.extra.user_handle", -1);
                                if (intExtra >= 1) {
                                    appExitInfoTracker.onUserRemoved(intExtra);
                                    break;
                                }
                                break;
                            default:
                                if (!intent.getBooleanExtra(
                                        "android.intent.extra.REPLACING", false)) {
                                    appExitInfoTracker.onPackageRemoved(
                                            intent.getData().getSchemeSpecificPart(),
                                            intent.getIntExtra("android.intent.extra.UID", -10000),
                                            intent.getBooleanExtra(
                                                    "android.intent.extra.REMOVED_FOR_ALL_USERS",
                                                    false));
                                    break;
                                }
                                break;
                        }
                    }
                },
                intentFilter3,
                null,
                appExitInfoTracker.mKillHandler);
        IoThread.getHandler()
                .post(new AppExitInfoTracker$$ExternalSyntheticLambda2(appExitInfoTracker, 1));
    }

    public final void printOomLevel(PrintWriter printWriter, String str, int i) {
        printWriter.print("    ");
        if (i >= 0) {
            printWriter.print(' ');
            if (i < 10) {
                printWriter.print(' ');
            }
        } else if (i > -10) {
            printWriter.print(' ');
        }
        printWriter.print(i);
        printWriter.print(": ");
        printWriter.print(str);
        printWriter.print(" (");
        printWriter.print(ActivityManagerService.stringifySize(getMemLevel(i)));
        printWriter.println(")");
    }

    public final void removeLruProcessLocked(ProcessRecord processRecord) {
        int lastIndexOf = this.mLruProcesses.lastIndexOf(processRecord);
        if (lastIndexOf >= 0) {
            ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
            ActivityManagerService.boostPriorityForProcLockedSection();
            synchronized (activityManagerGlobalLock) {
                try {
                    if (!processRecord.mKilled) {
                        if (processRecord.mPersistent) {
                            Slog.w(
                                    "ActivityManager",
                                    "Removing persistent process that hasn't been killed: "
                                            + processRecord);
                        } else {
                            Slog.wtfStack(
                                    "ActivityManager",
                                    "Removing process that hasn't been killed: " + processRecord);
                            if (processRecord.mPid > 0) {
                                KernelMemoryProxy$ReclaimerLog.write(
                                        "B|killProcessQuiet comm="
                                                + processRecord.processName
                                                + "("
                                                + processRecord.mPid
                                                + ")",
                                        false);
                                long j = processRecord.mProfile.mLastPss;
                                Process.killProcessQuiet(processRecord.mPid);
                                killProcessGroup(processRecord.uid, processRecord.mPid);
                                KernelMemoryProxy$ReclaimerLog.write(
                                        "E|killProcessQuiet pss=" + j, false);
                                noteAppKill(processRecord, 13, 16, "hasn't been killed");
                            } else {
                                processRecord.mPendingStart = false;
                            }
                        }
                    }
                    int i = this.mLruProcessActivityStart;
                    if (lastIndexOf < i) {
                        this.mLruProcessActivityStart = i - 1;
                    }
                    int i2 = this.mLruProcessServiceStart;
                    if (lastIndexOf < i2) {
                        this.mLruProcessServiceStart = i2 - 1;
                    }
                    this.mLruProcesses.remove(lastIndexOf);
                } finally {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                }
            }
            ActivityManagerService.resetPriorityAfterProcLockedSection();
        }
        OomAdjuster oomAdjuster = this.mService.mOomAdjuster;
        if (processRecord == null) {
            oomAdjuster.getClass();
            return;
        }
        oomAdjuster.mPendingProcessSet.remove(processRecord);
        PlatformCompatCache platformCompatCache = PlatformCompatCache.getInstance();
        ApplicationInfo applicationInfo = processRecord.info;
        for (int size = platformCompatCache.mCaches.size() - 1; size >= 0; size--) {
            PlatformCompatCache.CacheItem cacheItem =
                    (PlatformCompatCache.CacheItem) platformCompatCache.mCaches.valueAt(size);
            synchronized (cacheItem.mLock) {
                cacheItem.mCache.remove(applicationInfo.packageName);
            }
        }
    }

    public final boolean removeProcessLocked(
            ProcessRecord processRecord,
            boolean z,
            boolean z2,
            int i,
            int i2,
            String str,
            boolean z3) {
        boolean z4;
        boolean z5;
        String str2 = processRecord.processName;
        int i3 = processRecord.uid;
        ASKSManager.removePackageWithPid(processRecord.mPid);
        if (((ProcessRecord) this.mProcessNames.get(str2, i3)) != processRecord) {
            Slog.w("ActivityManager", "Ignoring remove of inactive process: " + processRecord);
            return false;
        }
        removeProcessNameLocked(i3, null, str2);
        this.mService.mAtmInternal.clearHeavyWeightProcessIfEquals(
                processRecord.mWindowProcessController);
        int i4 = processRecord.mPid;
        if ((i4 <= 0 || i4 == ActivityManagerService.MY_PID)
                && !(i4 == 0 && processRecord.mPendingStart)) {
            processRecord.callStack = Debug.getCallers(20);
            this.mRemovedProcesses.add(processRecord);
            return false;
        }
        if (i4 > 0) {
            this.mService.removePidLocked(i4, processRecord);
            processRecord.mBindMountPending = false;
            this.mService.mHandler.removeMessages(20, processRecord);
            this.mService.mBatteryStatsService.noteProcessFinish(
                    processRecord.info.uid, processRecord.processName);
            if (processRecord.isolated) {
                this.mService.mBatteryStatsService.removeIsolatedUid(
                        processRecord.uid, processRecord.info.uid);
                if (this.mService.getCfmsInternalLocked() != null) {
                    this.mService
                            .getCfmsInternalLocked()
                            .removeIsolatedUid(processRecord.uid, processRecord.info.uid);
                }
                this.mService.getPackageManagerInternal().removeIsolatedUid(processRecord.uid);
            }
        }
        if (!processRecord.mPersistent || processRecord.isolated) {
            z4 = false;
            z5 = false;
        } else if (z) {
            z5 = true;
            z4 = false;
        } else {
            z4 = true;
            z5 = false;
        }
        processRecord.killLocked(i, i2, str, str, true, z3);
        this.mService.handleAppDiedLocked(i4, processRecord, z4, z2, false);
        if (z4) {
            removeLruProcessLocked(processRecord);
            this.mService.addAppLocked(processRecord.info, 0);
        }
        return z5;
    }

    public final ProcessRecord removeProcessNameLocked(
            int i, ProcessRecord processRecord, String str) {
        UidRecord uidRecord;
        ProcessRecord processRecord2 = (ProcessRecord) this.mProcessNames.get(str, i);
        ProcessRecord processRecord3 = processRecord != null ? processRecord : processRecord2;
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            if (processRecord == null || processRecord2 == processRecord) {
                try {
                    this.mProcessNames.remove(i, str);
                } catch (Throwable th) {
                    ActivityManagerService.resetPriorityAfterProcLockedSection();
                    throw th;
                }
            }
            if (processRecord != null && processRecord.mIsRemovedName) {
                Slog.d(
                        "ActivityManager",
                        "proc " + processRecord + " already removed. so we skip next process.");
            }
            if (processRecord3 != null
                    && (uidRecord = processRecord3.mUidRecord) != null
                    && (processRecord == null || !processRecord.mIsRemovedName)) {
                uidRecord.mProcRecords.remove(processRecord3);
                processRecord3.mIsRemovedName = true;
                if (uidRecord.mProcRecords.size() == 0) {
                    this.mService.enqueueUidChangeLocked(uidRecord, -1, -2147483647);
                    EventLog.writeEvent(30053, i);
                    this.mActiveUids.remove(i);
                    FgsTempAllowList fgsTempAllowList = this.mService.mFgsStartTempAllowList;
                    int i2 = processRecord3.info.uid;
                    synchronized (fgsTempAllowList.mLock) {
                        fgsTempAllowList.mTempAllowList.remove(i2);
                    }
                    this.mService.noteUidProcessState(i, 20, 0);
                }
                processRecord3.mUidRecord = null;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        this.mIsolatedProcesses.remove(i);
        this.mGlobalIsolatedUids.mUidUsed.delete(i);
        if (processRecord3 != null && processRecord3.appZygote) {
            IsolatedUidRange isolatedUidRange =
                    (IsolatedUidRange)
                            this.mAppIsolatedUidRangeAllocator.mAppRanges.get(
                                    processRecord3.info.processName,
                                    processRecord3.mHostingRecord.mDefiningUid);
            if (isolatedUidRange != null) {
                isolatedUidRange.mUidUsed.delete(processRecord3.uid);
            }
            AppZygote appZygote =
                    (AppZygote)
                            this.mAppZygotes.get(
                                    processRecord3.info.processName,
                                    processRecord3.mHostingRecord.mDefiningUid);
            if (appZygote != null) {
                ArrayList arrayList = (ArrayList) this.mAppZygoteProcesses.get(appZygote);
                arrayList.remove(processRecord3);
                if (arrayList.size() == 0) {
                    this.mService.mHandler.removeMessages(71);
                    if (processRecord3.mRemoved) {
                        killAppZygoteIfNeededLocked(appZygote, false);
                    } else {
                        Message obtainMessage = this.mService.mHandler.obtainMessage(71);
                        obtainMessage.obj = appZygote;
                        this.mService.mHandler.sendMessageDelayed(obtainMessage, 5000L);
                    }
                }
            }
        }
        if (processRecord3 != null && processRecord3.isSdkSandbox) {
            int appUidForSdkSandboxUid = Process.getAppUidForSdkSandboxUid(i);
            ArrayList arrayList2 = (ArrayList) this.mSdkSandboxes.get(appUidForSdkSandboxUid);
            if (arrayList2 != null) {
                arrayList2.remove(processRecord3);
                if (arrayList2.size() == 0) {
                    this.mSdkSandboxes.remove(appUidForSdkSandboxUid);
                }
            }
        }
        this.mAppsInBackgroundRestricted.remove(processRecord3);
        return processRecord2;
    }

    public final void scheduleDispatchProcessDiedLocked(int i, int i2) {
        synchronized (this.mProcessChangeLock) {
            try {
                for (int size = this.mPendingProcessChanges.size() - 1; size >= 0; size--) {
                    ActivityManagerService.ProcessChangeItem processChangeItem =
                            (ActivityManagerService.ProcessChangeItem)
                                    this.mPendingProcessChanges.get(size);
                    if (i > 0 && processChangeItem.pid == i) {
                        this.mPendingProcessChanges.remove(size);
                        this.mAvailProcessChanges.add(processChangeItem);
                    }
                }
                this.mService.mUiHandler.obtainMessage(32, i, i2, null).sendToTarget();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Object searchEachLruProcessesLOSP(Function function, boolean z) {
        if (!z) {
            for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
                Object apply = function.apply((ProcessRecord) this.mLruProcesses.get(size));
                if (apply != null) {
                    return apply;
                }
            }
            return null;
        }
        int size2 = this.mLruProcesses.size();
        for (int i = 0; i < size2; i++) {
            Object apply2 = function.apply((ProcessRecord) this.mLruProcesses.get(i));
            if (apply2 != null) {
                return apply2;
            }
        }
        return null;
    }

    public final void setAllHttpProxy() {
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                for (int size = this.mLruProcesses.size() - 1; size >= 0; size--) {
                    ProcessRecord processRecord = (ProcessRecord) this.mLruProcesses.get(size);
                    IApplicationThread iApplicationThread = processRecord.mThread;
                    if (processRecord.mPid != ActivityManagerService.MY_PID
                            && iApplicationThread != null
                            && !processRecord.isolated) {
                        try {
                            iApplicationThread.updateHttpProxy();
                        } catch (RemoteException unused) {
                            Slog.w(
                                    "ActivityManager",
                                    "Failed to update http proxy for: "
                                            + processRecord.info.processName);
                        }
                    }
                }
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        ActivityThread.updateHttpProxy(this.mService.mContext);
    }

    public void setLruProcessServiceStartLSP(int i) {
        this.mLruProcessServiceStart = i;
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.Process.ProcessStartResult startProcess(
            com.android.server.am.HostingRecord r39,
            com.android.server.am.ProcessRecord r40,
            int r41,
            int[] r42,
            int r43,
            int r44,
            int r45,
            java.lang.String r46,
            java.lang.String r47,
            java.lang.String r48,
            java.lang.String r49,
            long r50) {
        /*
            Method dump skipped, instructions count: 1131
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessList.startProcess(com.android.server.am.HostingRecord,"
                    + " com.android.server.am.ProcessRecord, int, int[], int, int, int,"
                    + " java.lang.String, java.lang.String, java.lang.String, java.lang.String,"
                    + " long):android.os.Process$ProcessStartResult");
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0299, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x029d, code lost:

       r0.printStackTrace();
    */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x029b, code lost:

       r0 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x02a1, code lost:

       r0.printStackTrace();
    */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x02a4, code lost:

       r5 = r16;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.server.am.ProcessRecord startProcessLocked(
            java.lang.String r24,
            android.content.pm.ApplicationInfo r25,
            boolean r26,
            int r27,
            com.android.server.am.HostingRecord r28,
            int r29,
            boolean r30,
            boolean r31,
            int r32,
            boolean r33,
            int r34,
            java.lang.String r35,
            java.lang.String r36,
            java.lang.String r37,
            java.lang.String[] r38,
            java.lang.Runnable r39,
            boolean r40,
            int r41) {
        /*
            Method dump skipped, instructions count: 816
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessList.startProcessLocked(java.lang.String,"
                    + " android.content.pm.ApplicationInfo, boolean, int,"
                    + " com.android.server.am.HostingRecord, int, boolean, boolean, int, boolean,"
                    + " int, java.lang.String, java.lang.String, java.lang.String,"
                    + " java.lang.String[], java.lang.Runnable, boolean,"
                    + " int):com.android.server.am.ProcessRecord");
    }

    public final void startProcessLocked(
            ProcessRecord processRecord, HostingRecord hostingRecord, int i) {
        startProcessLocked(processRecord, hostingRecord, i, false, false, null);
    }

    public final boolean startProcessLocked(
            HostingRecord hostingRecord,
            ProcessRecord processRecord,
            int i,
            int[] iArr,
            int i2,
            int i3,
            int i4,
            String str,
            String str2,
            String str3,
            String str4,
            long j,
            long j2) {
        processRecord.mPendingStart = true;
        processRecord.mRemoved = false;
        ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
        ActivityManagerService.boostPriorityForProcLockedSection();
        synchronized (activityManagerGlobalLock) {
            try {
                processRecord.mKilledByAm = false;
                processRecord.mKilled = false;
            } catch (Throwable th) {
                ActivityManagerService.resetPriorityAfterProcLockedSection();
                throw th;
            }
        }
        ActivityManagerService.resetPriorityAfterProcLockedSection();
        processRecord.mInfant = true;
        processRecord.mTGLCallbackPosted = false;
        if (processRecord.mStartSeq != 0) {
            Slog.wtf(
                    "ActivityManager",
                    "startProcessLocked processName:"
                            + processRecord.processName
                            + " with non-zero startSeq:"
                            + processRecord.mStartSeq);
        }
        if (processRecord.mPid != 0) {
            Slog.wtf(
                    "ActivityManager",
                    "startProcessLocked processName:"
                            + processRecord.processName
                            + " with non-zero pid:"
                            + processRecord.mPid);
        }
        processRecord.mDisabledCompatChanges = null;
        processRecord.mLoggableCompatChanges = null;
        PlatformCompat platformCompat = this.mPlatformCompat;
        if (platformCompat != null) {
            ApplicationInfo applicationInfo = processRecord.info;
            CompatConfig compatConfig = platformCompat.mCompatConfig;
            compatConfig.getClass();
            LongArray longArray = new LongArray();
            for (CompatChange compatChange : compatConfig.mChanges.values()) {
                if (!compatChange.isEnabled(
                        applicationInfo, compatConfig.mAndroidBuildClassifier)) {
                    longArray.add(compatChange.getId());
                }
            }
            long[] array = longArray.toArray();
            Arrays.sort(array);
            processRecord.mDisabledCompatChanges = array;
            PlatformCompat platformCompat2 = this.mPlatformCompat;
            ApplicationInfo applicationInfo2 = processRecord.info;
            CompatConfig compatConfig2 = platformCompat2.mCompatConfig;
            compatConfig2.getClass();
            LongArray longArray2 = new LongArray(compatConfig2.mChanges.size());
            for (CompatChange compatChange2 : compatConfig2.mChanges.values()) {
                long id = compatChange2.getId();
                int i5 = applicationInfo2.targetSdkVersion;
                int enableSinceTargetSdk =
                        (compatChange2.getEnableSinceTargetSdk() != -1
                                        ? compatChange2.getEnableSinceTargetSdk() - 1
                                        : -1)
                                + 1;
                boolean z =
                        enableSinceTargetSdk > 0
                                && (enableSinceTargetSdk == 10000 || enableSinceTargetSdk == i5);
                if (compatChange2.isEnabled(applicationInfo2, compatConfig2.mAndroidBuildClassifier)
                        && z) {
                    longArray2.add(id);
                }
            }
            long[] array2 = longArray2.toArray();
            Arrays.sort(array2);
            processRecord.mLoggableCompatChanges = array2;
        }
        long j3 = this.mProcStartSeqCounter + 1;
        this.mProcStartSeqCounter = j3;
        processRecord.mStartSeq = j3;
        processRecord.mStartUid = i;
        processRecord.mHostingRecord = hostingRecord;
        processRecord.mSeInfo = str;
        processRecord.mStartUptime = j;
        processRecord.mStartElapsedTime = j2;
        boolean z2 =
                (str4 == null && Zygote.getWrapProperty(processRecord.processName) == null)
                        ? false
                        : true;
        processRecord.mUsingWrapper = z2;
        processRecord.mWindowProcessController.mUsingWrapper = z2;
        this.mPendingStarts.put(j3, processRecord);
        ActivityManagerService activityManagerService = this.mService;
        if (activityManagerService.mConstants.FLAG_PROCESS_START_ASYNC) {
            activityManagerService.mProcStartHandler.post(
                    new ProcessList$$ExternalSyntheticLambda4(
                            this, processRecord, iArr, i2, i3, i4, str2, str3, str4, j3, 0));
            return true;
        }
        try {
            Process.ProcessStartResult startProcess =
                    startProcess(
                            hostingRecord,
                            processRecord,
                            i,
                            iArr,
                            i2,
                            i3,
                            i4,
                            str,
                            str2,
                            str3,
                            str4,
                            j);
            handleProcessStartedLocked(
                    processRecord, startProcess.pid, startProcess.usingWrapper, j3, false);
        } catch (RuntimeException e) {
            Slog.e("ActivityManager", "Failure starting process " + processRecord.processName, e);
            processRecord.mPendingStart = false;
            this.mService.forceStopPackageLocked(
                    processRecord.info.packageName,
                    UserHandle.getAppId(processRecord.uid),
                    false,
                    false,
                    true,
                    false,
                    false,
                    processRecord.userId,
                    "start failure");
        }
        return processRecord.mPid > 0;
    }

    /* JADX WARN: Code restructure failed: missing block: B:193:0x0182, code lost:

       if ((r2.mInfo.flags & 16) == 0) goto L59;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean startProcessLocked(
            com.android.server.am.ProcessRecord r25,
            com.android.server.am.HostingRecord r26,
            int r27,
            boolean r28,
            boolean r29,
            java.lang.String r30) {
        /*
            Method dump skipped, instructions count: 1259
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.am.ProcessList.startProcessLocked(com.android.server.am.ProcessRecord,"
                    + " com.android.server.am.HostingRecord, int, boolean, boolean,"
                    + " java.lang.String):boolean");
    }

    public final void updateClientActivitiesOrderingLSP(
            ProcessRecord processRecord, int i, int i2, int i3) {
        int i4;
        boolean z;
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        if (processRecord.hasActivitiesOrRecentTasks()
                || processServiceRecord.mTreatLikeActivity
                || !processServiceRecord.mHasClientActivities) {
            return;
        }
        int i5 = processRecord.info.uid;
        int i6 = processServiceRecord.mConnectionGroup;
        if (i6 > 0) {
            int i7 = processServiceRecord.mConnectionImportance;
            int i8 = i3;
            while (i3 >= i2) {
                ProcessRecord processRecord2 = (ProcessRecord) this.mLruProcesses.get(i3);
                ProcessServiceRecord processServiceRecord2 = processRecord2.mServices;
                int i9 = processServiceRecord2.mConnectionGroup;
                int i10 = processServiceRecord2.mConnectionImportance;
                if (processRecord2.info.uid == i5 && i9 == i6) {
                    if (i3 != i8 || i10 < i7) {
                        int i11 = i;
                        while (true) {
                            if (i11 <= i8) {
                                z = false;
                                break;
                            } else {
                                if (i10
                                        <= ((ProcessRecord) this.mLruProcesses.get(i11))
                                                .mServices
                                                .mConnectionImportance) {
                                    this.mLruProcesses.remove(i3);
                                    this.mLruProcesses.add(i11, processRecord2);
                                    i8--;
                                    z = true;
                                    break;
                                }
                                i11--;
                            }
                        }
                        if (!z) {
                            this.mLruProcesses.remove(i3);
                            this.mLruProcesses.add(i8, processRecord2);
                        }
                    }
                    i8--;
                    i7 = i10;
                }
                i3--;
            }
            i3 = i8;
        }
        int i12 = i3;
        while (i3 >= i2) {
            ProcessRecord processRecord3 = (ProcessRecord) this.mLruProcesses.get(i3);
            ProcessServiceRecord processServiceRecord3 = processRecord3.mServices;
            int i13 = processServiceRecord3.mConnectionGroup;
            if (processRecord3.info.uid != i5) {
                if (i3 < i12) {
                    boolean z2 = false;
                    int i14 = 0;
                    int i15 = 0;
                    while (i3 >= i2) {
                        this.mLruProcesses.remove(i3);
                        this.mLruProcesses.add(i12, processRecord3);
                        i3--;
                        if (i3 < i2) {
                            break;
                        }
                        processRecord3 = (ProcessRecord) this.mLruProcesses.get(i3);
                        if (!processRecord3.hasActivitiesOrRecentTasks()
                                && !processServiceRecord3.mTreatLikeActivity) {
                            if (!processServiceRecord3.mHasClientActivities) {
                                continue;
                            } else if (!z2) {
                                i14 = processRecord3.info.uid;
                                z2 = true;
                                i15 = i13;
                            } else if (i14 == 0
                                    || i14 != processRecord3.info.uid
                                    || i15 == 0
                                    || i15 != i13) {
                                break;
                            }
                            i12--;
                        } else {
                            if (z2) {
                                break;
                            }
                            z2 = true;
                            i12--;
                        }
                    }
                }
                do {
                    i12--;
                    if (i12 < i2) {
                        break;
                    }
                } while (((ProcessRecord) this.mLruProcesses.get(i12)).info.uid != i5);
                if (i12 >= i2) {
                    int i16 =
                            ((ProcessRecord) this.mLruProcesses.get(i12))
                                    .mServices
                                    .mConnectionGroup;
                    do {
                        i12--;
                        if (i12 < i2) {
                            break;
                        }
                        ProcessRecord processRecord4 = (ProcessRecord) this.mLruProcesses.get(i12);
                        i4 = processRecord4.mServices.mConnectionGroup;
                        if (processRecord4.info.uid != i5) {
                            break;
                        }
                    } while (i4 == i16);
                }
                i3 = i12;
            } else {
                i3--;
            }
        }
    }

    public final int updateLruProcessInternalLSP(
            ProcessRecord processRecord,
            long j,
            int i,
            int i2,
            String str,
            Object obj,
            ProcessRecord processRecord2) {
        processRecord.mLastActivityTime = j;
        if (processRecord.hasActivitiesOrRecentTasks()) {
            return i;
        }
        int lastIndexOf = this.mLruProcesses.lastIndexOf(processRecord);
        if (lastIndexOf < 0) {
            Slog.wtf(
                    "ActivityManager",
                    "Adding dependent process "
                            + processRecord
                            + " not on LRU list: "
                            + str
                            + " "
                            + obj
                            + " from "
                            + processRecord2);
            return i;
        }
        if (lastIndexOf >= i) {
            return i;
        }
        int i3 = this.mLruProcessActivityStart;
        if (lastIndexOf >= i3 && i < i3) {
            return i;
        }
        this.mLruProcesses.remove(lastIndexOf);
        if (i > 0) {
            i--;
        }
        this.mLruProcesses.add(i, processRecord);
        processRecord.mLruSeq = i2;
        return i;
    }

    public final void updateLruProcessLSP(
            ProcessRecord processRecord, ProcessRecord processRecord2, boolean z) {
        int i;
        int i2;
        int i3;
        ServiceRecord serviceRecord;
        ProcessRecord processRecord3;
        int i4;
        this.mLruSeq++;
        long uptimeMillis = SystemClock.uptimeMillis();
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        processRecord.mLastActivityTime = uptimeMillis;
        if (z) {
            int size = this.mLruProcesses.size();
            if (processRecord.isActiveLaunch) {
                processRecord.isActiveLaunch = false;
                processRecord.activeLaunchTime = -1L;
            }
            if (this.mDynamicHiddenApp == null) {
                boolean z2 = DynamicHiddenApp.DEBUG;
                this.mDynamicHiddenApp = DynamicHiddenApp.DhaClassLazyHolder.INSTANCE;
            }
            DynamicHiddenApp dynamicHiddenApp = this.mDynamicHiddenApp;
            if (dynamicHiddenApp != null) {
                String str = processRecord.processName;
                int i5 = processRecord.userId;
                dynamicHiddenApp.mBGProtectManager.getClass();
                if (BGProtectManager.appIsPickedProcess(i5, str) > 0) {
                    processRecord.mIpmLaunchType = 1;
                    if (size > 0 && this.mLruProcesses.get(size - 1) == processRecord) {
                        return;
                    }
                }
            }
            processRecord.mIpmLaunchType = -1;
            processRecord.mlLaunchTime = -1L;
            if (size > 0) {
                return;
            }
        } else {
            int i6 = this.mLruProcessServiceStart;
            if (i6 > 0 && this.mLruProcesses.get(i6 - 1) == processRecord) {
                return;
            }
        }
        int lastIndexOf = this.mLruProcesses.lastIndexOf(processRecord);
        if (!processRecord.mPersistent || lastIndexOf < 0) {
            if (lastIndexOf >= 0) {
                int i7 = this.mLruProcessActivityStart;
                if (lastIndexOf < i7) {
                    this.mLruProcessActivityStart = i7 - 1;
                }
                int i8 = this.mLruProcessServiceStart;
                if (lastIndexOf < i8) {
                    this.mLruProcessServiceStart = i8 - 1;
                }
                this.mLruProcesses.remove(lastIndexOf);
            }
            if (z) {
                int size2 = this.mLruProcesses.size();
                i = this.mLruProcessServiceStart;
                if (processRecord.hasActivitiesOrRecentTasks()
                        || processServiceRecord.mTreatLikeActivity
                        || this.mLruProcessActivityStart >= (i4 = size2 - 1)) {
                    this.mLruProcesses.add(processRecord);
                    i2 = this.mLruProcesses.size() - 1;
                } else {
                    while (i4 > this.mLruProcessActivityStart
                            && ((ProcessRecord) this.mLruProcesses.get(i4)).info.uid
                                    != processRecord.info.uid) {
                        i4--;
                    }
                    this.mLruProcesses.add(i4, processRecord);
                    i2 = i4 - 1;
                    int i9 = this.mLruProcessActivityStart;
                    if (i2 < i9) {
                        i2 = i9;
                    }
                    updateClientActivitiesOrderingLSP(processRecord, i4, i9, i2);
                }
            } else {
                int i10 = this.mLruProcessServiceStart;
                if (processRecord2 != null) {
                    int lastIndexOf2 = this.mLruProcesses.lastIndexOf(processRecord2);
                    if (lastIndexOf2 > lastIndexOf) {
                        lastIndexOf = lastIndexOf2;
                    }
                    if (lastIndexOf >= 0 && i10 > lastIndexOf) {
                        i10 = lastIndexOf;
                    }
                }
                this.mLruProcesses.add(i10, processRecord);
                int i11 = i10 - 1;
                this.mLruProcessActivityStart++;
                int i12 = this.mLruProcessServiceStart;
                this.mLruProcessServiceStart = i12 + 1;
                if (i10 > 1) {
                    updateClientActivitiesOrderingLSP(processRecord, i12, 0, i11);
                }
                i = i11;
                i2 = -1;
            }
            processRecord.mLruSeq = this.mLruSeq;
            int size3 = processServiceRecord.mConnections.size() - 1;
            int i13 = i;
            int i14 = i2;
            while (size3 >= 0) {
                ConnectionRecord connectionAt = processServiceRecord.getConnectionAt(size3);
                AppBindRecord appBindRecord = connectionAt.binding;
                if (appBindRecord != null
                        && !connectionAt.serviceDead
                        && (serviceRecord = appBindRecord.service) != null
                        && (processRecord3 = serviceRecord.app) != null
                        && processRecord3.mLruSeq != this.mLruSeq
                        && connectionAt.notHasFlag(1073742128)
                        && !connectionAt.binding.service.app.mPersistent) {
                    ProcessRecord processRecord4 = connectionAt.binding.service.app;
                    if (!processRecord4.mServices.mHasClientActivities) {
                        i3 = size3;
                        i13 =
                                updateLruProcessInternalLSP(
                                        processRecord4,
                                        uptimeMillis,
                                        i13,
                                        this.mLruSeq,
                                        "service connection",
                                        connectionAt,
                                        processRecord);
                    } else if (i14 >= 0) {
                        i3 = size3;
                        i14 =
                                updateLruProcessInternalLSP(
                                        processRecord4,
                                        uptimeMillis,
                                        i14,
                                        this.mLruSeq,
                                        "service connection",
                                        connectionAt,
                                        processRecord);
                    }
                    size3 = i3 - 1;
                }
                i3 = size3;
                size3 = i3 - 1;
            }
            ProcessProviderRecord processProviderRecord = processRecord.mProviders;
            int i15 = i13;
            for (int size4 = processProviderRecord.mConProviders.size() - 1; size4 >= 0; size4--) {
                ContentProviderRecord contentProviderRecord =
                        ((ContentProviderConnection) processProviderRecord.mConProviders.get(size4))
                                .provider;
                ProcessRecord processRecord5 = contentProviderRecord.proc;
                if (processRecord5 != null
                        && processRecord5.mLruSeq != this.mLruSeq
                        && !processRecord5.mPersistent) {
                    i15 =
                            updateLruProcessInternalLSP(
                                    contentProviderRecord.proc,
                                    uptimeMillis,
                                    i15,
                                    this.mLruSeq,
                                    "provider reference",
                                    contentProviderRecord,
                                    processRecord);
                }
            }
        }
    }

    public final void updateLruProcessLocked(
            ProcessRecord processRecord, ProcessRecord processRecord2, boolean z) {
        ProcessServiceRecord processServiceRecord = processRecord.mServices;
        boolean z2 =
                processRecord.hasActivitiesOrRecentTasks()
                        || processServiceRecord.mHasClientActivities
                        || processServiceRecord.mTreatLikeActivity;
        if (z || !z2) {
            if (processRecord.mPid != 0 || processRecord.mPendingStart) {
                ActivityManagerGlobalLock activityManagerGlobalLock = this.mProcLock;
                ActivityManagerService.boostPriorityForProcLockedSection();
                synchronized (activityManagerGlobalLock) {
                    try {
                        updateLruProcessLSP(processRecord, processRecord2, z2);
                    } catch (Throwable th) {
                        ActivityManagerService.resetPriorityAfterProcLockedSection();
                        throw th;
                    }
                }
                ActivityManagerService.resetPriorityAfterProcLockedSection();
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x004d, code lost:

       if (r4 > 1.0f) goto L17;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateOomLevels(int r22, int r23, boolean r24) {
        /*
            Method dump skipped, instructions count: 504
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled: com.android.server.am.ProcessList.updateOomLevels(int, int,"
                    + " boolean):void");
    }

    public String updateSeInfo(ProcessRecord processRecord) {
        boolean z;
        boolean z2;
        String str = "";
        if (processRecord.isSdkSandbox) {
            ProcessListSettingsListener processListSettingsListener =
                    getProcessListSettingsListener();
            synchronized (processListSettingsListener.mLock) {
                z = processListSettingsListener.mSdkSandboxApplyRestrictionsNext;
            }
            if (z) {
                str = ":isSdkSandboxNext";
            } else if (com.android.sdksandbox.flags.Flags.selinuxSdkSandboxAudit()) {
                ProcessListSettingsListener processListSettingsListener2 =
                        getProcessListSettingsListener();
                synchronized (processListSettingsListener2.mLock) {
                    z2 = processListSettingsListener2.mSdkSandboxApplyRestrictionsAudit;
                }
                if (z2) {
                    str = ":isSdkSandboxAudit";
                }
            }
        }
        if (!com.android.sdksandbox.flags.Flags.selinuxInputSelector()) {
            StringBuilder sb = new StringBuilder();
            sb.append(processRecord.info.seInfo);
            return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                    sb,
                    TextUtils.isEmpty(processRecord.info.seInfoUser)
                            ? ""
                            : processRecord.info.seInfoUser,
                    str);
        }
        return processRecord.info.seInfo
                + str
                + TextUtils.emptyIfNull(processRecord.info.seInfoUser);
    }

    public final void writeProcessesToProtoLSP(ProtoOutputStream protoOutputStream, String str) {
        int i;
        ProfilerInfo profilerInfo;
        ActivityManagerService activityManagerService;
        ArrayList arrayList;
        int size = this.mProcessNames.getMap().size();
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            SparseArray sparseArray = (SparseArray) this.mProcessNames.getMap().valueAt(i3);
            int size2 = sparseArray.size();
            for (int i4 = 0; i4 < size2; i4++) {
                ProcessRecord processRecord = (ProcessRecord) sparseArray.valueAt(i4);
                if (str == null || processRecord.mPkgList.containsKey(str)) {
                    processRecord.dumpDebug(
                            protoOutputStream,
                            2246267895809L,
                            this.mLruProcesses.indexOf(processRecord));
                    if (processRecord.mPersistent) {
                        i2++;
                    }
                }
            }
        }
        int size3 = this.mIsolatedProcesses.size();
        for (int i5 = 0; i5 < size3; i5++) {
            ProcessRecord processRecord2 = (ProcessRecord) this.mIsolatedProcesses.valueAt(i5);
            if (str == null || processRecord2.mPkgList.containsKey(str)) {
                processRecord2.dumpDebug(
                        protoOutputStream,
                        2246267895810L,
                        this.mLruProcesses.indexOf(processRecord2));
            }
        }
        int appId = this.mService.getAppId(str);
        this.mActiveUids.dumpProto(protoOutputStream, str, appId, 2246267895812L);
        int i6 = i2;
        if (this.mLruProcesses.size() > 0) {
            long start = protoOutputStream.start(1146756268038L);
            int size4 = this.mLruProcesses.size();
            protoOutputStream.write(1120986464257L, size4);
            protoOutputStream.write(1120986464258L, size4 - this.mLruProcessActivityStart);
            protoOutputStream.write(1120986464259L, size4 - this.mLruProcessServiceStart);
            ActivityManagerService activityManagerService2 = this.mService;
            ArrayList arrayList2 = this.mLruProcesses;
            ArrayList sortProcessOomList = sortProcessOomList(str, arrayList2);
            if (!sortProcessOomList.isEmpty()) {
                long uptimeMillis = SystemClock.uptimeMillis();
                boolean z = true;
                int size5 = sortProcessOomList.size() - 1;
                while (size5 >= 0) {
                    ProcessRecord processRecord3 =
                            (ProcessRecord) ((Pair) sortProcessOomList.get(size5)).first;
                    ProcessStateRecord processStateRecord = processRecord3.mState;
                    ProcessServiceRecord processServiceRecord = processRecord3.mServices;
                    long start2 = protoOutputStream.start(2246267895812L);
                    int i7 = appId;
                    String makeOomAdjString = makeOomAdjString(processStateRecord.mSetAdj, z);
                    long j = start;
                    protoOutputStream.write(1133871366145L, processRecord3.mPersistent);
                    protoOutputStream.write(
                            1120986464258L,
                            (arrayList2.size() - 1)
                                    - ((Integer) ((Pair) sortProcessOomList.get(size5)).second)
                                            .intValue());
                    protoOutputStream.write(1138166333443L, makeOomAdjString);
                    int i8 = processStateRecord.mSetSchedGroup;
                    int i9 = i8 != 0 ? i8 != 2 ? i8 != 3 ? i8 != 4 ? -1 : 3 : 2 : 1 : 0;
                    if (i9 != -1) {
                        protoOutputStream.write(1159641169924L, i9);
                    }
                    if (processStateRecord.mHasForegroundActivities) {
                        protoOutputStream.write(1133871366149L, true);
                    } else if (processServiceRecord.mHasForegroundServices) {
                        protoOutputStream.write(1133871366150L, true);
                    }
                    protoOutputStream.write(
                            1159641169927L,
                            makeProcStateProtoEnum(processStateRecord.mCurProcState));
                    int i10 = size5;
                    protoOutputStream.write(
                            1120986464264L, processRecord3.mProfile.mTrimMemoryLevel);
                    processRecord3.dumpDebug(protoOutputStream, 1146756268041L, -1);
                    protoOutputStream.write(1138166333450L, processStateRecord.mAdjType);
                    if (processStateRecord.mAdjSource != null
                            || processStateRecord.mAdjTarget != null) {
                        Object obj = processStateRecord.mAdjTarget;
                        if (obj instanceof ComponentName) {
                            ((ComponentName) obj).dumpDebug(protoOutputStream, 1146756268043L);
                        } else if (obj != null) {
                            protoOutputStream.write(1138166333452L, obj.toString());
                        }
                        Object obj2 = processStateRecord.mAdjSource;
                        if (obj2 instanceof ProcessRecord) {
                            ((ProcessRecord) obj2).dumpDebug(protoOutputStream, 1146756268045L, -1);
                        } else if (obj2 != null) {
                            protoOutputStream.write(1138166333454L, obj2.toString());
                        }
                    }
                    long start3 = protoOutputStream.start(1146756268047L);
                    protoOutputStream.write(1120986464257L, processStateRecord.mMaxAdj);
                    ArrayList arrayList3 = arrayList2;
                    protoOutputStream.write(1120986464258L, processStateRecord.mCurRawAdj);
                    protoOutputStream.write(1120986464259L, processStateRecord.mSetRawAdj);
                    protoOutputStream.write(1120986464260L, processStateRecord.mCurAdj);
                    protoOutputStream.write(1120986464261L, processStateRecord.mSetAdj);
                    protoOutputStream.write(
                            1159641169927L,
                            makeProcStateProtoEnum(processStateRecord.mCurProcState));
                    protoOutputStream.write(
                            1159641169928L,
                            makeProcStateProtoEnum(processStateRecord.mSetProcState));
                    protoOutputStream.write(
                            1138166333449L,
                            DebugUtils.sizeValueToString(
                                    processRecord3.mProfile.mLastPss * 1024, new StringBuilder()));
                    protoOutputStream.write(
                            1138166333450L,
                            DebugUtils.sizeValueToString(
                                    processRecord3.mProfile.mLastSwapPss * 1024,
                                    new StringBuilder()));
                    protoOutputStream.write(
                            1138166333451L,
                            DebugUtils.sizeValueToString(
                                    processRecord3.mProfile.mLastCachedPss * 1024,
                                    new StringBuilder()));
                    protoOutputStream.write(1133871366156L, processStateRecord.isCached());
                    protoOutputStream.write(1133871366157L, processStateRecord.mCurProcState >= 19);
                    protoOutputStream.write(1133871366158L, processServiceRecord.mHasAboveClient);
                    if (processStateRecord.mSetProcState >= 10) {
                        long j2 = processRecord3.mProfile.mLastCpuTime.get();
                        long j3 = uptimeMillis - activityManagerService2.mLastPowerCheckUptime;
                        if (j2 != 0 && j3 > 0) {
                            long j4 = processRecord3.mProfile.mCurCpuTime.get() - j2;
                            arrayList = arrayList3;
                            long start4 = protoOutputStream.start(1146756268047L);
                            activityManagerService = activityManagerService2;
                            protoOutputStream.write(1112396529665L, j3);
                            protoOutputStream.write(1112396529666L, j4);
                            protoOutputStream.write(1108101562371L, (j4 * 100.0d) / j3);
                            protoOutputStream.end(start4);
                            protoOutputStream.end(start3);
                            protoOutputStream.end(start2);
                            size5 = i10 - 1;
                            arrayList2 = arrayList;
                            appId = i7;
                            start = j;
                            activityManagerService2 = activityManagerService;
                            z = true;
                        }
                    }
                    activityManagerService = activityManagerService2;
                    arrayList = arrayList3;
                    protoOutputStream.end(start3);
                    protoOutputStream.end(start2);
                    size5 = i10 - 1;
                    arrayList2 = arrayList;
                    appId = i7;
                    start = j;
                    activityManagerService2 = activityManagerService;
                    z = true;
                }
            }
            i = appId;
            protoOutputStream.end(start);
        } else {
            i = appId;
        }
        ActivityManagerService activityManagerService3 = this.mService;
        int size6 = activityManagerService3.mActiveInstrumentation.size();
        for (int i11 = 0; i11 < size6; i11++) {
            ActiveInstrumentation activeInstrumentation =
                    (ActiveInstrumentation) activityManagerService3.mActiveInstrumentation.get(i11);
            if (str == null
                    || activeInstrumentation.mClass.getPackageName().equals(str)
                    || activeInstrumentation.mTargetInfo.packageName.equals(str)) {
                activeInstrumentation.getClass();
                long start5 = protoOutputStream.start(2246267895811L);
                activeInstrumentation.mClass.dumpDebug(protoOutputStream, 1146756268033L);
                protoOutputStream.write(1133871366146L, activeInstrumentation.mFinished);
                for (int i12 = 0; i12 < activeInstrumentation.mRunningProcesses.size(); i12++) {
                    ((ProcessRecord) activeInstrumentation.mRunningProcesses.get(i12))
                            .dumpDebug(protoOutputStream, 2246267895811L, -1);
                }
                for (String str2 : activeInstrumentation.mTargetProcesses) {
                    protoOutputStream.write(2237677961220L, str2);
                }
                ApplicationInfo applicationInfo = activeInstrumentation.mTargetInfo;
                if (applicationInfo != null) {
                    applicationInfo.dumpDebug(protoOutputStream, 1146756268037L, 0);
                }
                protoOutputStream.write(1138166333446L, activeInstrumentation.mProfileFile);
                protoOutputStream.write(1138166333447L, activeInstrumentation.mWatcher.toString());
                protoOutputStream.write(
                        1138166333448L, activeInstrumentation.mUiAutomationConnection.toString());
                Bundle bundle = activeInstrumentation.mArguments;
                if (bundle != null) {
                    bundle.dumpDebug(protoOutputStream, 1146756268042L);
                }
                protoOutputStream.end(start5);
            }
        }
        activityManagerService3.mUidObserverController.mValidateUids.dumpProto(
                protoOutputStream, str, i, 2246267895813L);
        if (str != null) {
            synchronized (activityManagerService3.mPidsSelfLocked) {
                try {
                    int size7 =
                            ((SparseArray) activityManagerService3.mPidsSelfLocked.mPidMap).size();
                    for (int i13 = 0; i13 < size7; i13++) {
                        ProcessRecord valueAt =
                                activityManagerService3.mPidsSelfLocked.valueAt(i13);
                        if (valueAt.mPkgList.containsKey(str)) {
                            valueAt.dumpDebug(protoOutputStream, 2246267895815L, -1);
                        }
                    }
                } finally {
                }
            }
        }
        if (activityManagerService3.mImportantProcesses.size() > 0) {
            synchronized (activityManagerService3.mPidsSelfLocked) {
                try {
                    int size8 = activityManagerService3.mImportantProcesses.size();
                    for (int i14 = 0; i14 < size8; i14++) {
                        ActivityManagerService.AnonymousClass14 anonymousClass14 =
                                (ActivityManagerService.AnonymousClass14)
                                        activityManagerService3.mImportantProcesses.valueAt(i14);
                        ProcessRecord processRecord4 =
                                activityManagerService3.mPidsSelfLocked.get(anonymousClass14.pid);
                        if (str == null
                                || (processRecord4 != null
                                        && processRecord4.mPkgList.containsKey(str))) {
                            long start6 = protoOutputStream.start(2246267895816L);
                            protoOutputStream.write(1120986464257L, anonymousClass14.pid);
                            IBinder iBinder = anonymousClass14.token;
                            if (iBinder != null) {
                                protoOutputStream.write(1138166333442L, iBinder.toString());
                            }
                            protoOutputStream.write(1138166333443L, anonymousClass14.reason);
                            protoOutputStream.end(start6);
                        }
                    }
                } finally {
                }
            }
        }
        int size9 = activityManagerService3.mPersistentStartingProcesses.size();
        for (int i15 = 0; i15 < size9; i15++) {
            ProcessRecord processRecord5 =
                    (ProcessRecord) activityManagerService3.mPersistentStartingProcesses.get(i15);
            if (str == null || str.equals(processRecord5.info.packageName)) {
                processRecord5.dumpDebug(protoOutputStream, 2246267895817L, -1);
            }
        }
        int size10 = activityManagerService3.mProcessList.mRemovedProcesses.size();
        for (int i16 = 0; i16 < size10; i16++) {
            ProcessRecord processRecord6 =
                    (ProcessRecord) activityManagerService3.mProcessList.mRemovedProcesses.get(i16);
            if (str == null || str.equals(processRecord6.info.packageName)) {
                processRecord6.dumpDebug(protoOutputStream, 2246267895818L, -1);
            }
        }
        int size11 = activityManagerService3.mProcessesOnHold.size();
        for (int i17 = 0; i17 < size11; i17++) {
            ProcessRecord processRecord7 =
                    (ProcessRecord) activityManagerService3.mProcessesOnHold.get(i17);
            if (str == null || str.equals(processRecord7.info.packageName)) {
                processRecord7.dumpDebug(protoOutputStream, 2246267895819L, -1);
            }
        }
        synchronized (activityManagerService3.mAppProfiler.mProfilerLock) {
            activityManagerService3.mAppProfiler.writeProcessesToGcToProto(protoOutputStream, str);
        }
        activityManagerService3.mAppErrors.dumpDebugLPr(protoOutputStream, str);
        activityManagerService3.mAtmInternal.writeProcessesToProto(
                protoOutputStream,
                str,
                activityManagerService3.mWakefulness.get(),
                activityManagerService3.mAppProfiler.mTestPssOrRssMode);
        if (str == null) {
            activityManagerService3.mUserController.dumpDebug(protoOutputStream);
        }
        activityManagerService3.mUidObserverController.dumpDebug(protoOutputStream, str);
        for (int i18 : activityManagerService3.mDeviceIdleAllowlist) {
            protoOutputStream.write(2220498092056L, i18);
        }
        for (int i19 : activityManagerService3.mDeviceIdleTempAllowlist) {
            protoOutputStream.write(2220498092057L, i19);
        }
        if (activityManagerService3.mPendingTempAllowlist.size() > 0) {
            int size12 = activityManagerService3.mPendingTempAllowlist.size();
            for (int i20 = 0; i20 < size12; i20++) {
                ActivityManagerService.PendingTempAllowlist valueAt2 =
                        activityManagerService3.mPendingTempAllowlist.valueAt(i20);
                valueAt2.getClass();
                long start7 = protoOutputStream.start(2246267895834L);
                protoOutputStream.write(1120986464257L, valueAt2.targetUid);
                protoOutputStream.write(1112396529666L, valueAt2.duration);
                protoOutputStream.write(1138166333443L, valueAt2.tag);
                protoOutputStream.write(1120986464260L, valueAt2.type);
                protoOutputStream.write(1120986464261L, valueAt2.reasonCode);
                protoOutputStream.write(1120986464262L, valueAt2.callingUid);
                protoOutputStream.end(start7);
            }
        }
        String str3 = activityManagerService3.mDebugApp;
        if ((str3 != null
                        || activityManagerService3.mOrigDebugApp != null
                        || activityManagerService3.mDebugTransient
                        || activityManagerService3.mOrigWaitForDebugger)
                && (str == null
                        || str.equals(str3)
                        || str.equals(activityManagerService3.mOrigDebugApp))) {
            long start8 = protoOutputStream.start(1146756268062L);
            protoOutputStream.write(1138166333441L, activityManagerService3.mDebugApp);
            protoOutputStream.write(1138166333442L, activityManagerService3.mOrigDebugApp);
            protoOutputStream.write(1133871366147L, activityManagerService3.mDebugTransient);
            protoOutputStream.write(1133871366148L, activityManagerService3.mOrigWaitForDebugger);
            protoOutputStream.end(start8);
        }
        synchronized (activityManagerService3.mAppProfiler.mProfilerLock) {
            activityManagerService3.mAppProfiler.writeMemWatchProcessToProtoLPf(protoOutputStream);
        }
        String str4 = activityManagerService3.mTrackAllocationApp;
        if (str4 != null && (str == null || str.equals(str4))) {
            protoOutputStream.write(1138166333473L, activityManagerService3.mTrackAllocationApp);
        }
        AppProfiler appProfiler = activityManagerService3.mAppProfiler;
        AppProfiler.ProfileData profileData = appProfiler.mProfileData;
        String str5 = profileData.mProfileApp;
        if ((str5 != null
                        || profileData.mProfileProc != null
                        || ((profilerInfo = profileData.mProfilerInfo) != null
                                && (profilerInfo.profileFile != null
                                        || profilerInfo.profileFd != null)))
                && (str == null || str.equals(str5))) {
            long start9 = protoOutputStream.start(1146756268066L);
            protoOutputStream.write(1138166333441L, profileData.mProfileApp);
            profileData.mProfileProc.dumpDebug(protoOutputStream, 1146756268034L, -1);
            ProfilerInfo profilerInfo2 = profileData.mProfilerInfo;
            if (profilerInfo2 != null) {
                profilerInfo2.dumpDebug(protoOutputStream, 1146756268035L);
                protoOutputStream.write(1120986464260L, appProfiler.mProfileType);
            }
            protoOutputStream.end(start9);
        }
        if (str == null || str.equals(activityManagerService3.mNativeDebuggingApp)) {
            protoOutputStream.write(1138166333475L, activityManagerService3.mNativeDebuggingApp);
        }
        if (str == null) {
            protoOutputStream.write(
                    1133871366180L, activityManagerService3.mAlwaysFinishActivities);
            protoOutputStream.write(1120986464294L, i6);
            protoOutputStream.write(1133871366183L, activityManagerService3.mProcessesReady);
            protoOutputStream.write(1133871366184L, activityManagerService3.mSystemReady);
            protoOutputStream.write(1133871366185L, activityManagerService3.mBooted);
            protoOutputStream.write(1120986464298L, activityManagerService3.mFactoryTest);
            protoOutputStream.write(1133871366187L, activityManagerService3.mBooting);
            protoOutputStream.write(1133871366188L, activityManagerService3.mCallFinishBooting);
            protoOutputStream.write(1133871366189L, activityManagerService3.mBootAnimationComplete);
            protoOutputStream.write(1112396529710L, activityManagerService3.mLastPowerCheckUptime);
            OomAdjuster oomAdjuster = activityManagerService3.mOomAdjuster;
            protoOutputStream.write(1120986464305L, oomAdjuster.mAdjSeq);
            protoOutputStream.write(1120986464306L, oomAdjuster.mProcessList.mLruSeq);
            protoOutputStream.write(1120986464307L, oomAdjuster.mNumNonCachedProcs);
            protoOutputStream.write(1120986464309L, oomAdjuster.mNumServiceProcs);
            protoOutputStream.write(1120986464310L, oomAdjuster.mNewNumServiceProcs);
            AppProfiler appProfiler2 = activityManagerService3.mAppProfiler;
            protoOutputStream.write(1133871366199L, appProfiler2.mAllowLowerMemLevel);
            protoOutputStream.write(1120986464312L, appProfiler2.mLastMemoryLevel);
            protoOutputStream.write(1120986464313L, appProfiler2.mLastNumProcesses);
            long uptimeMillis2 = SystemClock.uptimeMillis();
            ProtoUtils.toDuration(
                    protoOutputStream,
                    1146756268090L,
                    activityManagerService3.mLastIdleTime,
                    uptimeMillis2);
            protoOutputStream.write(
                    1112396529723L,
                    activityManagerService3.mAppProfiler.getLowRamTimeSinceIdleLPr(uptimeMillis2));
        }
    }
}
