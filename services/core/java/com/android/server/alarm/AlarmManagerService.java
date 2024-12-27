package com.android.server.alarm;

import android.app.ActivityManager;
import android.app.ActivityManagerInternal;
import android.app.ActivityOptions;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.BroadcastOptions;
import android.app.IActivityManager;
import android.app.IAlarmCompleteListener;
import android.app.IAlarmListener;
import android.app.IAlarmManager;
import android.app.PendingIntent;
import android.app.StatsManager;
import android.app.compat.CompatChanges;
import android.app.usage.UsageStatsManagerInternal;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.PermissionChecker;
import android.content.pm.PackageManagerInternal;
import android.content.pm.UserPackage;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.BatteryManager;
import android.os.BatteryStatsInternal;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerExecutor;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.ShellCommand;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.WorkSource;
import android.provider.DeviceConfig;
import android.provider.Settings;
import android.system.Os;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.EventLog;
import android.util.IndentingPrintWriter;
import android.util.IntArray;
import android.util.LongArrayQueue;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.util.SparseLongArray;
import android.util.TimeUtils;
import android.util.proto.ProtoOutputStream;
import com.android.internal.app.IAppOpsCallback;
import com.android.internal.app.IAppOpsService;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.LocalLog;
import com.android.internal.util.RingBuffer;
import com.android.internal.util.jobs.ConcurrentUtils;
import com.android.internal.util.jobs.DumpUtils;
import com.android.internal.util.jobs.StatLogger;
import com.android.server.AppSchedulingModuleThread;
import com.android.server.AppStateTracker;
import com.android.server.AppStateTrackerImpl;
import com.android.server.AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleInternal;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DssController$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.SystemClockTime;
import com.android.server.SystemService;
import com.android.server.SystemServiceManager;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.SystemTimeZone;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.Alarm;
import com.android.server.alarm.AlarmManagerService;
import com.android.server.alarm.AlarmStore;
import com.android.server.alarm.GmsAlarmManager.SmartManagerSettingsObserver;
import com.android.server.alarm.SamsungAlarmManagerService;
import com.android.server.am.FreecessController;
import com.android.server.am.FreecessPkgStatus;
import com.android.server.am.MARsPackageInfo;
import com.android.server.am.MARsPolicyManager;
import com.android.server.am.mars.MARsFreezeStateRecord;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.clipboard.ClipboardService;
import com.android.server.pm.PackageManagerShellCommandDataLoader;
import com.android.server.pm.permission.PermissionManagerService;
import com.android.server.pm.pkg.AndroidPackage;
import com.android.server.usage.AppStandbyInternal;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.analytics.activation.ActivationMonitor;
import com.samsung.android.knox.datetime.IDateTimePolicy;
import dalvik.annotation.optimization.NeverCompile;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.zone.ZoneOffsetTransition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class AlarmManagerService extends SystemService {
    public static final Intent NEXT_ALARM_CLOCK_CHANGED_INTENT = new Intent("android.app.action.NEXT_ALARM_CLOCK_CHANGED").addFlags(553648128);
    public static PackageManagerInternal pmInternalForMARs;
    public ActivityManagerInternal mActivityManagerInternal;
    public final ActivityOptions mActivityOptsRestrictBal;
    public final SparseArray mAdditionHistory;
    public final AlarmManagerService$$ExternalSyntheticLambda3 mAlarmClockUpdater;
    public final AlarmManagerService$$ExternalSyntheticLambda4 mAlarmDispatchComparator;
    public LazyAlarmStore mAlarmStore;
    public final SparseIntArray mAlarmsPerUid;
    public AppWakeupHistory mAllowWhileIdleCompatHistory;
    public AppWakeupHistory mAllowWhileIdleHistory;
    public AppOpsManager mAppOps;
    boolean mAppStandbyParole;
    public AppStateTrackerImpl mAppStateTracker;
    public final AppSyncWrapper mAppSync;
    public AppWakeupHistory mAppWakeupHistory;
    public final Intent mBackgroundIntent;
    public BatteryStatsInternal mBatteryStatsInternal;
    public final BroadcastOptions mBroadcastOptsRestrictBal;
    public int mBroadcastRefCount;
    public final SparseArray mBroadcastStats;
    public ClockReceiver mClockReceiver;
    public Constants mConstants;
    public PendingIntent mDateChangeSender;
    public final SparseArray mDeliveryHistory;
    public final DeliveryTracker mDeliveryTracker;
    volatile Set mExactAlarmCandidates;
    public final RingBuffer mExpirationHistory;
    public final AnonymousClass7 mForceAppStandbyListener;
    public GmsAlarmManager mGmsManager;
    public AlarmHandler mHandler;
    public final SparseArray mHandlerSparseAlarmClockArray;
    public final ArrayList mInFlight;
    public final ArrayList mInFlightListeners;
    public final Injector mInjector;
    public boolean mInteractive;
    public final RingBuffer mInvalidExpirationHistory;
    public long mLastAlarmDeliveryTime;
    SparseIntArray mLastOpScheduleExactAlarm;
    public final SparseLongArray mLastPriorityAlarmDispatch;
    public long mLastTickReceived;
    public long mLastTickSet;
    public long mLastTimeChangeClockTime;
    public long mLastTimeChangeRealtime;
    public long mLastTrigger;
    public long mLastWakeup;
    public int mListenerCount;
    public AnonymousClass1 mListenerDeathRecipient;
    public int mListenerFinishCount;
    public DeviceIdleInternal mLocalDeviceIdleController;
    public volatile PermissionManagerService.PermissionManagerServiceInternalImpl mLocalPermissionManager;
    public final Object mLock;
    public final LocalLog mLog;
    public long mMaxDelayTime;
    public MetricsHelper mMetricsHelper;
    public final SparseArray mNextAlarmClockForUser;
    public boolean mNextAlarmClockMayChange;
    public long mNextNonWakeUpSetAt;
    public long mNextNonWakeup;
    public long mNextNonWakeupDeliveryTime;
    public int mNextTickHistory;
    public Alarm mNextWakeFromIdle;
    public long mNextWakeUpSetAt;
    public long mNextWakeup;
    public long mNonInteractiveStartTime;
    public long mNonInteractiveTime;
    public int mNumDelayedAlarms;
    public int mNumTimeChanged;
    public final BroadcastOptions mOptsTimeBroadcast;
    public final BroadcastOptions mOptsWithFgs;
    public final BroadcastOptions mOptsWithFgsForAlarmClock;
    public final BroadcastOptions mOptsWithoutFgs;
    public PackageManagerInternal mPackageManagerInternal;
    public final SparseArray mPendingBackgroundAlarms;
    public Alarm mPendingIdleUntil;
    public final SparseArray mPendingMARsRestrictedAlarms;
    public final ArrayList mPendingNonWakeupAlarms;
    public final SparseBooleanArray mPendingSendNextAlarmClockChangedForUser;
    public final SparseArray mRemovalHistory;
    public final SparseArray mRemoveFailedHistory;
    public final SamsungAlarmManagerService mSamsungAlarmManagerService;
    public int mSendCount;
    public int mSendFinishCount;
    public final AnonymousClass4 mService;
    public long mSetKernelNonWakeup;
    public long mSetKernelWakeup;
    public long mStartCurrentDelayTime;
    public final StatLogger mStatLogger;
    public int mSystemUiUid;
    public TemporaryQuotaReserve mTemporaryQuotaReserve;
    public final long[] mTickHistory;
    public Intent mTimeTickIntent;
    public Bundle mTimeTickOptions;
    public AnonymousClass2 mTimeTickTrigger;
    public final SparseArray mTmpSparseAlarmClockArray;
    public long mTotalDelayTime;
    public UsageStatsManagerInternal mUsageStatsManagerInternal;
    public boolean mUseFrozenStateToDropListenerAlarms;
    public PowerManager.WakeLock mWakeLock;
    public final RingBuffer mWakeupAlarmHistory;

    /* renamed from: com.android.server.alarm.AlarmManagerService$2, reason: invalid class name */
    public final class AnonymousClass2 extends IAlarmListener.Stub {
        public AnonymousClass2() {
        }

        public final void doAlarm(final IAlarmCompleteListener iAlarmCompleteListener) {
            AlarmManagerService.this.mHandler.post(new Runnable() { // from class: com.android.server.alarm.AlarmManagerService$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ?? r0 = AlarmManagerService.AnonymousClass2.this;
                    IAlarmCompleteListener iAlarmCompleteListener2 = iAlarmCompleteListener;
                    Context context = AlarmManagerService.this.getContext();
                    AlarmManagerService alarmManagerService = AlarmManagerService.this;
                    context.sendBroadcastAsUser(alarmManagerService.mTimeTickIntent, UserHandle.ALL, null, alarmManagerService.mTimeTickOptions);
                    try {
                        iAlarmCompleteListener2.alarmComplete((IBinder) r0);
                    } catch (RemoteException unused) {
                    }
                }
            });
            synchronized (AlarmManagerService.this.mLock) {
                AlarmManagerService alarmManagerService = AlarmManagerService.this;
                alarmManagerService.mInjector.getClass();
                alarmManagerService.mLastTickReceived = System.currentTimeMillis();
            }
            AlarmManagerService.this.mClockReceiver.scheduleTimeTickEvent();
        }
    }

    /* renamed from: com.android.server.alarm.AlarmManagerService$5, reason: invalid class name */
    public final class AnonymousClass5 implements Comparator {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AlarmManagerService this$0;

        public /* synthetic */ AnonymousClass5(AlarmManagerService alarmManagerService, int i) {
            this.$r8$classId = i;
            this.this$0 = alarmManagerService;
        }

        @Override // java.util.Comparator
        public final int compare(Object obj, Object obj2) {
            switch (this.$r8$classId) {
                case 0:
                    long j = ((FilterStats) obj).aggregateTime;
                    long j2 = ((FilterStats) obj2).aggregateTime;
                    if (j >= j2) {
                        if (j > j2) {
                        }
                    }
                    break;
                default:
                    long j3 = ((FilterStats) obj).aggregateTime;
                    long j4 = ((FilterStats) obj2).aggregateTime;
                    if (j3 >= j4) {
                        if (j3 > j4) {
                        }
                    }
                    break;
            }
            return 0;
        }
    }

    public final class AddedAlarm {
        public final boolean mAlarmClock;
        public final int mFlags;
        public final String mHash;
        public final long mInterval;
        public final long mOrigWhen;
        public final String mTag;
        public final int mType;
        public final long mWhenAddedElapsed;
        public final long mWhenAddedRtc;
        public final long mWindowLength;

        public AddedAlarm(Alarm alarm, long j, long j2) {
            StringBuilder sb;
            int hashCode;
            this.mTag = alarm.statsTag;
            this.mType = alarm.type;
            this.mFlags = alarm.flags;
            this.mAlarmClock = alarm.alarmClock != null;
            if (alarm.operation != null) {
                sb = new StringBuilder("PI:");
                hashCode = alarm.operation.hashCode();
            } else {
                sb = new StringBuilder("L:");
                hashCode = alarm.listener.asBinder().hashCode();
            }
            sb.append(Integer.toHexString(hashCode));
            this.mHash = sb.toString();
            this.mOrigWhen = alarm.origWhen;
            this.mWindowLength = alarm.windowLength;
            this.mInterval = alarm.repeatInterval;
            this.mWhenAddedRtc = j;
            this.mWhenAddedElapsed = j2;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.print("[tag", this.mTag);
            int i = this.mType;
            indentingPrintWriter.print("T", Integer.valueOf(i));
            indentingPrintWriter.print("F", Integer.valueOf(this.mFlags));
            indentingPrintWriter.print("AC", Boolean.valueOf(this.mAlarmClock));
            indentingPrintWriter.print("H", this.mHash);
            boolean isRtc = AlarmManagerService.isRtc(i);
            long j = this.mOrigWhen;
            if (isRtc) {
                indentingPrintWriter.print("OW=");
                indentingPrintWriter.print(simpleDateFormat.format(new Date(j)) + " ");
            } else {
                indentingPrintWriter.print("OW", Long.valueOf(j));
            }
            long j2 = this.mWindowLength;
            if (j2 != 0) {
                indentingPrintWriter.print("WL", Long.valueOf(j2));
            }
            long j3 = this.mInterval;
            if (j3 != 0) {
                indentingPrintWriter.print("RI", Long.valueOf(j3));
            }
            indentingPrintWriter.print("elapsed", Long.valueOf(this.mWhenAddedElapsed));
            indentingPrintWriter.print("rtc=");
            indentingPrintWriter.print(simpleDateFormat.format(new Date(this.mWhenAddedRtc)));
            indentingPrintWriter.println("]");
        }
    }

    public class AlarmHandler extends Handler {
        public AlarmHandler() {
            super(Looper.myLooper());
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = 0;
            int i2 = message.what;
            if (i2 == 11) {
                AlarmManagerService.this.refreshExactAlarmCandidates();
                return;
            }
            if (i2 == 21) {
                synchronized (AlarmManagerService.this.mLock) {
                    AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                }
                return;
            }
            switch (i2) {
                case 1:
                    ArrayList arrayList = new ArrayList();
                    synchronized (AlarmManagerService.this.mLock) {
                        AlarmManagerService.this.mInjector.getClass();
                        AlarmManagerService.this.triggerAlarmsLocked(SystemClock.elapsedRealtime(), arrayList);
                        AlarmManagerService.this.updateNextAlarmClockLocked();
                    }
                    while (i < arrayList.size()) {
                        Alarm alarm = (Alarm) arrayList.get(i);
                        try {
                            AlarmManagerService alarmManagerService = AlarmManagerService.this;
                            alarmManagerService.getClass();
                            Bundle bundle = alarm.mIdleOptions;
                            if (bundle == null) {
                                bundle = alarm.operation.isActivity() ? alarmManagerService.mActivityOptsRestrictBal.toBundle() : alarmManagerService.mBroadcastOptsRestrictBal.toBundle();
                            }
                            alarm.operation.send(null, 0, null, null, null, null, bundle);
                        } catch (PendingIntent.CanceledException unused) {
                            if (alarm.repeatInterval > 0) {
                                AlarmManagerService.this.removeImpl(alarm.operation, null);
                            }
                        }
                        AlarmManagerService.this.decrementAlarmCount(alarm.uid);
                        i++;
                    }
                    return;
                case 2:
                    AlarmManagerService.m152$$Nest$msendNextAlarmClockChanged(AlarmManagerService.this);
                    return;
                case 3:
                    AlarmManagerService.this.mDeliveryTracker.alarmTimedOut((IBinder) message.obj);
                    return;
                case 4:
                    DeviceIdleInternal deviceIdleInternal = AlarmManagerService.this.mLocalDeviceIdleController;
                    if (deviceIdleInternal != null) {
                        deviceIdleInternal.setAlarmsActive(message.arg1 != 0);
                        return;
                    }
                    return;
                case 5:
                    break;
                case 6:
                    synchronized (AlarmManagerService.this.mLock) {
                        try {
                            AlarmManagerService.this.mAppStandbyParole = ((Boolean) message.obj).booleanValue();
                            if (AlarmManagerService.this.reorderAlarmsBasedOnStandbyBuckets(null)) {
                                AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                                AlarmManagerService.this.updateNextAlarmClockLocked();
                            }
                        } finally {
                        }
                    }
                    return;
                case 7:
                    PendingIntent pendingIntent = (PendingIntent) message.obj;
                    synchronized (AlarmManagerService.this.mLock) {
                        AlarmManagerService.this.removeLocked(pendingIntent, null, 4);
                    }
                    return;
                case 8:
                    AlarmManagerService.this.removeExactAlarmsOnPermissionRevoked(message.arg1, (String) message.obj, true);
                    return;
                default:
                    switch (i2) {
                        case 13:
                            String str = (String) message.obj;
                            int i3 = message.arg1;
                            if (AlarmManagerService.this.hasScheduleExactAlarmInternal(i3, str) || AlarmManagerService.this.hasUseExactAlarmInternal(i3, str)) {
                                return;
                            }
                            AlarmManagerService.this.removeExactAlarmsOnPermissionRevoked(i3, str, false);
                            return;
                        case 14:
                            break;
                        case 15:
                            int intValue = ((Integer) message.obj).intValue();
                            AlarmManagerService alarmManagerService2 = AlarmManagerService.this;
                            int[] iArr = {intValue};
                            Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                            synchronized (alarmManagerService2.mLock) {
                                alarmManagerService2.removeAlarmsInternalLocked(6, new AlarmManagerService$$ExternalSyntheticLambda6(i, iArr));
                            }
                            return;
                        default:
                            return;
                    }
            }
            synchronized (AlarmManagerService.this.mLock) {
                try {
                    ArraySet arraySet = new ArraySet();
                    arraySet.add(UserPackage.of(message.arg1, (String) message.obj));
                    if (AlarmManagerService.this.reorderAlarmsBasedOnStandbyBuckets(arraySet)) {
                        AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                        AlarmManagerService.this.updateNextAlarmClockLocked();
                    }
                } finally {
                }
            }
        }
    }

    public final class AlarmThread extends Thread {
        public int mFalseWakeups;
        public int mWtfThreshold;

        public AlarmThread() {
            super("AlarmManager");
            this.mFalseWakeups = 0;
            this.mWtfThreshold = 100;
        }

        /* JADX WARN: Code restructure failed: missing block: B:8:0x0068, code lost:
        
            if (r8 > (-1000)) goto L11;
         */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 738
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.AlarmThread.run():void");
        }
    }

    public final class AppStandbyTracker extends AppStandbyInternal.AppIdleStateChangeListener {
        public AppStandbyTracker() {
        }

        public final void onAppIdleStateChanged(String str, int i, boolean z, int i2, int i3) {
            AlarmManagerService.this.mHandler.obtainMessage(5, i, -1, str).sendToTarget();
        }

        public final void triggerTemporaryQuotaBump(String str, int i) {
            AlarmManagerService alarmManagerService;
            int i2;
            int packageUid;
            synchronized (AlarmManagerService.this.mLock) {
                alarmManagerService = AlarmManagerService.this;
                i2 = alarmManagerService.mConstants.TEMPORARY_QUOTA_BUMP;
            }
            if (i2 > 0 && (packageUid = alarmManagerService.mPackageManagerInternal.getPackageUid(str, 0L, i)) >= 0 && !UserHandle.isCore(packageUid)) {
                synchronized (AlarmManagerService.this.mLock) {
                    AlarmManagerService alarmManagerService2 = AlarmManagerService.this;
                    TemporaryQuotaReserve temporaryQuotaReserve = alarmManagerService2.mTemporaryQuotaReserve;
                    alarmManagerService2.mInjector.getClass();
                    long elapsedRealtime = SystemClock.elapsedRealtime();
                    temporaryQuotaReserve.getClass();
                    if (i2 > 0) {
                        UserPackage of = UserPackage.of(i, str);
                        TemporaryQuotaReserve.QuotaInfo quotaInfo = (TemporaryQuotaReserve.QuotaInfo) temporaryQuotaReserve.mQuotaBuffer.get(of);
                        if (quotaInfo == null) {
                            quotaInfo = new TemporaryQuotaReserve.QuotaInfo();
                            temporaryQuotaReserve.mQuotaBuffer.put(of, quotaInfo);
                        }
                        quotaInfo.remainingQuota = i2;
                        quotaInfo.expirationTime = elapsedRealtime + temporaryQuotaReserve.mMaxDuration;
                    }
                }
                AlarmManagerService.this.mHandler.obtainMessage(14, i, -1, str).sendToTarget();
            }
        }
    }

    class AppWakeupHistory {
        public final ArrayMap mPackageHistory = new ArrayMap();
        public final long mWindowSize = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;

        public final void dump(IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mPackageHistory.size(); i++) {
                UserPackage userPackage = (UserPackage) this.mPackageHistory.keyAt(i);
                LongArrayQueue longArrayQueue = (LongArrayQueue) this.mPackageHistory.valueAt(i);
                indentingPrintWriter.print(userPackage.packageName);
                indentingPrintWriter.print(", u");
                indentingPrintWriter.print(userPackage.userId);
                indentingPrintWriter.print(": ");
                int max = Math.max(0, longArrayQueue.size() - 100);
                for (int size = longArrayQueue.size() - 1; size >= max; size--) {
                    TimeUtils.formatDuration(longArrayQueue.get(size), j, indentingPrintWriter);
                    indentingPrintWriter.print(", ");
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        }

        public final long getNthLastWakeupForPackage(int i, int i2, String str) {
            int size;
            LongArrayQueue longArrayQueue = (LongArrayQueue) this.mPackageHistory.get(UserPackage.of(i, str));
            if (longArrayQueue != null && (size = longArrayQueue.size() - i2) >= 0) {
                return longArrayQueue.get(size);
            }
            return 0L;
        }

        public final int getTotalWakeupsInWindow(int i, String str) {
            LongArrayQueue longArrayQueue = (LongArrayQueue) this.mPackageHistory.get(UserPackage.of(i, str));
            if (longArrayQueue == null) {
                return 0;
            }
            return longArrayQueue.size();
        }

        public final void recordAlarmForPackage(int i, String str, long j) {
            UserPackage of = UserPackage.of(i, str);
            LongArrayQueue longArrayQueue = (LongArrayQueue) this.mPackageHistory.get(of);
            if (longArrayQueue == null) {
                longArrayQueue = new LongArrayQueue();
                this.mPackageHistory.put(of, longArrayQueue);
            }
            if (longArrayQueue.size() == 0 || longArrayQueue.peekLast() < j) {
                longArrayQueue.addLast(j);
            }
            while (longArrayQueue.peekFirst() + this.mWindowSize < longArrayQueue.peekLast()) {
                longArrayQueue.removeFirst();
            }
        }

        public final void removeForUser(int i) {
            for (int size = this.mPackageHistory.size() - 1; size >= 0; size--) {
                if (((UserPackage) this.mPackageHistory.keyAt(size)).userId == i) {
                    this.mPackageHistory.removeAt(size);
                }
            }
        }
    }

    public final class BroadcastStats {
        public long aggregateTime;
        public int count;
        public final ArrayMap filterStats = new ArrayMap();
        public final String mPackageName;
        public final int mUid;
        public int nesting;
        public int numWakeup;
        public long startTime;

        public BroadcastStats(int i, String str) {
            this.mUid = i;
            this.mPackageName = str;
        }

        public final void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1120986464257L, this.mUid);
            protoOutputStream.write(1138166333442L, this.mPackageName);
            protoOutputStream.write(1112396529667L, this.aggregateTime);
            protoOutputStream.write(1120986464260L, this.count);
            protoOutputStream.write(1120986464261L, this.numWakeup);
            protoOutputStream.write(1112396529670L, this.startTime);
            protoOutputStream.write(1120986464263L, this.nesting);
            protoOutputStream.end(start);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("BroadcastStats{uid=");
            sb.append(this.mUid);
            sb.append(", packageName=");
            sb.append(this.mPackageName);
            sb.append(", aggregateTime=");
            sb.append(this.aggregateTime);
            sb.append(", count=");
            sb.append(this.count);
            sb.append(", numWakeup=");
            sb.append(this.numWakeup);
            sb.append(", startTime=");
            sb.append(this.startTime);
            sb.append(", nesting=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.nesting, sb, "}");
        }
    }

    class ChargingReceiver extends BroadcastReceiver {
        public ChargingReceiver() {
            AlarmManagerService.this.getContext().registerReceiver(this, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.os.action.CHARGING", "android.os.action.DISCHARGING"));
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            boolean equals = "android.os.action.CHARGING".equals(intent.getAction());
            AlarmManagerService.this.mHandler.removeMessages(6);
            AlarmManagerService.this.mHandler.obtainMessage(6, Boolean.valueOf(equals)).sendToTarget();
        }
    }

    class ClockReceiver extends BroadcastReceiver {
        public ClockReceiver() {
            AlarmManagerService.this.getContext().registerReceiver(this, BatteryService$$ExternalSyntheticOutline0.m("android.intent.action.DATE_CHANGED"));
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.intent.action.DATE_CHANGED")) {
                scheduleDateChangedEvent();
            }
        }

        public final void scheduleDateChangedEvent() {
            Calendar calendar = Calendar.getInstance();
            AlarmManagerService.this.mInjector.getClass();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(11, 0);
            calendar.set(12, 0);
            calendar.set(13, 0);
            calendar.set(14, 0);
            calendar.add(5, 1);
            AlarmManagerService.this.setImpl(1, calendar.getTimeInMillis(), 0L, 0L, AlarmManagerService.this.mDateChangeSender, null, null, 1, null, null, Process.myUid(), "android", null, 1);
        }

        public final void scheduleTimeTickEvent() {
            AlarmManagerService.this.mInjector.getClass();
            long currentTimeMillis = System.currentTimeMillis();
            long j = (((currentTimeMillis / 60000) + 1) * 60000) - currentTimeMillis;
            AlarmManagerService alarmManagerService = AlarmManagerService.this;
            int i = (alarmManagerService.mConstants.TIME_TICK_ALLOWED_WHILE_IDLE ? 8 : 0) | 1;
            alarmManagerService.mInjector.getClass();
            alarmManagerService.setImpl(3, SystemClock.elapsedRealtime() + j, 0L, 0L, null, AlarmManagerService.this.mTimeTickTrigger, "TIME_TICK", i, null, null, Process.myUid(), "android", null, 1);
            synchronized (AlarmManagerService.this.mLock) {
                AlarmManagerService.this.mLastTickSet = currentTimeMillis;
            }
        }
    }

    final class Constants implements DeviceConfig.OnPropertiesChangedListener {
        static final String KEY_ALLOW_WHILE_IDLE_COMPAT_QUOTA = "allow_while_idle_compat_quota";
        static final String KEY_ALLOW_WHILE_IDLE_COMPAT_WINDOW = "allow_while_idle_compat_window";
        static final String KEY_ALLOW_WHILE_IDLE_QUOTA = "allow_while_idle_quota";
        static final String KEY_ALLOW_WHILE_IDLE_WHITELIST_DURATION = "allow_while_idle_whitelist_duration";
        static final String KEY_ALLOW_WHILE_IDLE_WINDOW = "allow_while_idle_window";
        static final String KEY_CACHED_LISTENER_REMOVAL_DELAY = "cached_listener_removal_delay";
        static final String KEY_LISTENER_TIMEOUT = "listener_timeout";
        static final String KEY_MAX_ALARMS_PER_UID = "max_alarms_per_uid";
        static final String KEY_MAX_DEVICE_IDLE_FUZZ = "max_device_idle_fuzz";
        static final String KEY_MAX_INTERVAL = "max_interval";
        static final String KEY_MIN_DEVICE_IDLE_FUZZ = "min_device_idle_fuzz";
        static final String KEY_MIN_FUTURITY = "min_futurity";
        static final String KEY_MIN_INTERVAL = "min_interval";
        static final String KEY_MIN_WINDOW = "min_window";
        static final String KEY_PRIORITY_ALARM_DELAY = "priority_alarm_delay";
        static final String KEY_TEMPORARY_QUOTA_BUMP = "temporary_quota_bump";
        final String[] KEYS_APP_STANDBY_QUOTAS = {"standby_quota_active", "standby_quota_working", "standby_quota_frequent", "standby_quota_rare", "standby_quota_never"};
        public final int[] DEFAULT_APP_STANDBY_QUOTAS = {720, 10, 2, 1, 0};
        public long MIN_FUTURITY = 5000;
        public long MIN_INTERVAL = 60000;
        public long MAX_INTERVAL = 31536000000L;
        public long MIN_WINDOW = 600000;
        public long ALLOW_WHILE_IDLE_WHITELIST_DURATION = 10000;
        public long LISTENER_TIMEOUT = 5000;
        public int MAX_ALARMS_PER_UID = 500;
        public long APP_STANDBY_WINDOW = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public final int[] APP_STANDBY_QUOTAS = new int[5];
        public int APP_STANDBY_RESTRICTED_QUOTA = 1;
        public long APP_STANDBY_RESTRICTED_WINDOW = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
        public boolean TIME_TICK_ALLOWED_WHILE_IDLE = true;
        public int ALLOW_WHILE_IDLE_QUOTA = 72;
        public int ALLOW_WHILE_IDLE_COMPAT_QUOTA = 7;
        public long ALLOW_WHILE_IDLE_COMPAT_WINDOW = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public long ALLOW_WHILE_IDLE_WINDOW = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
        public long PRIORITY_ALARM_DELAY = 540000;
        public long MIN_DEVICE_IDLE_FUZZ = 120000;
        public long MAX_DEVICE_IDLE_FUZZ = 900000;
        public int TEMPORARY_QUOTA_BUMP = 0;
        public boolean DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF = true;
        public long CACHED_LISTENER_REMOVAL_DELAY = 10000;
        public long mLastAllowWhileIdleWhitelistDuration = -1;
        public int mVersion = 0;

        public Constants() {
            int i = 0;
            updateAllowWhileIdleWhitelistDurationLocked();
            while (true) {
                int[] iArr = this.APP_STANDBY_QUOTAS;
                if (i >= iArr.length) {
                    return;
                }
                iArr[i] = this.DEFAULT_APP_STANDBY_QUOTAS[i];
                i++;
            }
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("Settings:");
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("version", Integer.valueOf(this.mVersion));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MIN_FUTURITY);
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.MIN_FUTURITY, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MIN_INTERVAL);
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.MIN_INTERVAL, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MAX_INTERVAL);
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.MAX_INTERVAL, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MIN_WINDOW);
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.MIN_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_LISTENER_TIMEOUT);
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.LISTENER_TIMEOUT, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_QUOTA, Integer.valueOf(this.ALLOW_WHILE_IDLE_QUOTA));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_WINDOW);
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.ALLOW_WHILE_IDLE_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_COMPAT_QUOTA, Integer.valueOf(this.ALLOW_WHILE_IDLE_COMPAT_QUOTA));
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_COMPAT_WINDOW);
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.ALLOW_WHILE_IDLE_COMPAT_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_ALLOW_WHILE_IDLE_WHITELIST_DURATION);
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.ALLOW_WHILE_IDLE_WHITELIST_DURATION, indentingPrintWriter);
            indentingPrintWriter.println();
            indentingPrintWriter.print(KEY_MAX_ALARMS_PER_UID, Integer.valueOf(this.MAX_ALARMS_PER_UID));
            indentingPrintWriter.println();
            indentingPrintWriter.print("app_standby_window");
            indentingPrintWriter.print("=");
            TimeUtils.formatDuration(this.APP_STANDBY_WINDOW, indentingPrintWriter);
            indentingPrintWriter.println();
            int i = 0;
            while (true) {
                String[] strArr = this.KEYS_APP_STANDBY_QUOTAS;
                if (i >= strArr.length) {
                    indentingPrintWriter.print("standby_quota_restricted", Integer.valueOf(this.APP_STANDBY_RESTRICTED_QUOTA));
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("app_standby_restricted_window");
                    indentingPrintWriter.print("=");
                    TimeUtils.formatDuration(this.APP_STANDBY_RESTRICTED_WINDOW, indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("time_tick_allowed_while_idle", Boolean.valueOf(this.TIME_TICK_ALLOWED_WHILE_IDLE));
                    indentingPrintWriter.println();
                    indentingPrintWriter.print(KEY_PRIORITY_ALARM_DELAY);
                    indentingPrintWriter.print("=");
                    TimeUtils.formatDuration(this.PRIORITY_ALARM_DELAY, indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.print(KEY_MIN_DEVICE_IDLE_FUZZ);
                    indentingPrintWriter.print("=");
                    TimeUtils.formatDuration(this.MIN_DEVICE_IDLE_FUZZ, indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.print(KEY_MAX_DEVICE_IDLE_FUZZ);
                    indentingPrintWriter.print("=");
                    TimeUtils.formatDuration(this.MAX_DEVICE_IDLE_FUZZ, indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.print(KEY_TEMPORARY_QUOTA_BUMP, Integer.valueOf(this.TEMPORARY_QUOTA_BUMP));
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("delay_nonwakeup_alarms_while_screen_off", Boolean.valueOf(this.DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF));
                    indentingPrintWriter.println();
                    indentingPrintWriter.print(KEY_CACHED_LISTENER_REMOVAL_DELAY);
                    indentingPrintWriter.print("=");
                    TimeUtils.formatDuration(this.CACHED_LISTENER_REMOVAL_DELAY, indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.decreaseIndent();
                    return;
                }
                indentingPrintWriter.print(strArr[i], Integer.valueOf(this.APP_STANDBY_QUOTAS[i]));
                indentingPrintWriter.println();
                i++;
            }
        }

        public final void onPropertiesChanged(DeviceConfig.Properties properties) {
            char c;
            synchronized (AlarmManagerService.this.mLock) {
                try {
                    this.mVersion++;
                    boolean z = false;
                    boolean z2 = false;
                    for (String str : properties.getKeyset()) {
                        if (str != null) {
                            switch (str.hashCode()) {
                                case -1577286106:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_COMPAT_WINDOW)) {
                                        c = 7;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1490947714:
                                    if (str.equals(KEY_MIN_DEVICE_IDLE_FUZZ)) {
                                        c = 15;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1293038119:
                                    if (str.equals(KEY_MIN_FUTURITY)) {
                                        c = 0;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -1251256606:
                                    if (str.equals(KEY_CACHED_LISTENER_REMOVAL_DELAY)) {
                                        c = 19;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -975718548:
                                    if (str.equals(KEY_MAX_ALARMS_PER_UID)) {
                                        c = '\n';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -880907612:
                                    if (str.equals("app_standby_restricted_window")) {
                                        c = '\f';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -618440710:
                                    if (str.equals(KEY_PRIORITY_ALARM_DELAY)) {
                                        c = 14;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -591494837:
                                    if (str.equals(KEY_TEMPORARY_QUOTA_BUMP)) {
                                        c = 17;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -577593775:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_QUOTA)) {
                                        c = 3;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -564889801:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_WINDOW)) {
                                        c = 6;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -410928980:
                                    if (str.equals(KEY_MAX_DEVICE_IDLE_FUZZ)) {
                                        c = 16;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -392965507:
                                    if (str.equals(KEY_MIN_WINDOW)) {
                                        c = 4;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case -147388512:
                                    if (str.equals("app_standby_window")) {
                                        c = 11;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 932562134:
                                    if (str.equals(KEY_LISTENER_TIMEOUT)) {
                                        c = '\t';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1139967827:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_WHITELIST_DURATION)) {
                                        c = '\b';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1213697417:
                                    if (str.equals("time_tick_allowed_while_idle")) {
                                        c = '\r';
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1528643904:
                                    if (str.equals(KEY_MAX_INTERVAL)) {
                                        c = 2;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 1883600258:
                                    if (str.equals(KEY_ALLOW_WHILE_IDLE_COMPAT_QUOTA)) {
                                        c = 5;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2003069970:
                                    if (str.equals(KEY_MIN_INTERVAL)) {
                                        c = 1;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                case 2099862680:
                                    if (str.equals("delay_nonwakeup_alarms_while_screen_off")) {
                                        c = 18;
                                        break;
                                    }
                                    c = 65535;
                                    break;
                                default:
                                    c = 65535;
                                    break;
                            }
                            switch (c) {
                                case 0:
                                    this.MIN_FUTURITY = properties.getLong(KEY_MIN_FUTURITY, 5000L);
                                    break;
                                case 1:
                                    this.MIN_INTERVAL = properties.getLong(KEY_MIN_INTERVAL, 60000L);
                                    break;
                                case 2:
                                    this.MAX_INTERVAL = properties.getLong(KEY_MAX_INTERVAL, 31536000000L);
                                    break;
                                case 3:
                                    int i = properties.getInt(KEY_ALLOW_WHILE_IDLE_QUOTA, 72);
                                    this.ALLOW_WHILE_IDLE_QUOTA = i;
                                    if (i <= 0) {
                                        Slog.w("AlarmManager", "Must have positive allow_while_idle quota");
                                        this.ALLOW_WHILE_IDLE_QUOTA = 1;
                                        break;
                                    } else {
                                        break;
                                    }
                                case 4:
                                    this.MIN_WINDOW = properties.getLong(KEY_MIN_WINDOW, 600000L);
                                    break;
                                case 5:
                                    int i2 = properties.getInt(KEY_ALLOW_WHILE_IDLE_COMPAT_QUOTA, 7);
                                    this.ALLOW_WHILE_IDLE_COMPAT_QUOTA = i2;
                                    if (i2 <= 0) {
                                        Slog.w("AlarmManager", "Must have positive allow_while_idle_compat quota");
                                        this.ALLOW_WHILE_IDLE_COMPAT_QUOTA = 1;
                                        break;
                                    } else {
                                        break;
                                    }
                                case 6:
                                    long j = properties.getLong(KEY_ALLOW_WHILE_IDLE_WINDOW, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                                    this.ALLOW_WHILE_IDLE_WINDOW = j;
                                    if (j > ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                                        Slog.w("AlarmManager", "Cannot have allow_while_idle_window > 3600000");
                                        this.ALLOW_WHILE_IDLE_WINDOW = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
                                        break;
                                    } else if (j != ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                                        Slog.w("AlarmManager", "Using a non-default allow_while_idle_window = " + this.ALLOW_WHILE_IDLE_WINDOW);
                                        break;
                                    } else {
                                        break;
                                    }
                                case 7:
                                    long j2 = properties.getLong(KEY_ALLOW_WHILE_IDLE_COMPAT_WINDOW, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
                                    this.ALLOW_WHILE_IDLE_COMPAT_WINDOW = j2;
                                    if (j2 > ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                                        Slog.w("AlarmManager", "Cannot have allow_while_idle_compat_window > 3600000");
                                        this.ALLOW_WHILE_IDLE_COMPAT_WINDOW = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
                                        break;
                                    } else if (j2 != ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                                        Slog.w("AlarmManager", "Using a non-default allow_while_idle_compat_window = " + this.ALLOW_WHILE_IDLE_COMPAT_WINDOW);
                                        break;
                                    } else {
                                        break;
                                    }
                                case '\b':
                                    this.ALLOW_WHILE_IDLE_WHITELIST_DURATION = properties.getLong(KEY_ALLOW_WHILE_IDLE_WHITELIST_DURATION, 10000L);
                                    updateAllowWhileIdleWhitelistDurationLocked();
                                    break;
                                case '\t':
                                    this.LISTENER_TIMEOUT = properties.getLong(KEY_LISTENER_TIMEOUT, 5000L);
                                    break;
                                case '\n':
                                    int i3 = properties.getInt(KEY_MAX_ALARMS_PER_UID, 500);
                                    this.MAX_ALARMS_PER_UID = i3;
                                    if (i3 < 500) {
                                        Slog.w("AlarmManager", "Cannot set max_alarms_per_uid lower than 500");
                                        this.MAX_ALARMS_PER_UID = 500;
                                        break;
                                    } else {
                                        break;
                                    }
                                case 11:
                                case '\f':
                                    updateStandbyWindowsLocked();
                                    break;
                                case '\r':
                                    this.TIME_TICK_ALLOWED_WHILE_IDLE = properties.getBoolean("time_tick_allowed_while_idle", true);
                                    break;
                                case 14:
                                    this.PRIORITY_ALARM_DELAY = properties.getLong(KEY_PRIORITY_ALARM_DELAY, 540000L);
                                    break;
                                case 15:
                                case 16:
                                    if (z) {
                                        break;
                                    } else {
                                        updateDeviceIdleFuzzBoundaries();
                                        z = true;
                                        break;
                                    }
                                case 17:
                                    this.TEMPORARY_QUOTA_BUMP = properties.getInt(KEY_TEMPORARY_QUOTA_BUMP, 0);
                                    break;
                                case 18:
                                    this.DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF = properties.getBoolean("delay_nonwakeup_alarms_while_screen_off", true);
                                    break;
                                case 19:
                                    this.CACHED_LISTENER_REMOVAL_DELAY = properties.getLong(KEY_CACHED_LISTENER_REMOVAL_DELAY, 10000L);
                                    break;
                                default:
                                    if (str.startsWith("standby_quota_") && !z2) {
                                        updateStandbyQuotasLocked();
                                        z2 = true;
                                        break;
                                    }
                                    break;
                            }
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void updateAllowWhileIdleWhitelistDurationLocked() {
            long j = this.mLastAllowWhileIdleWhitelistDuration;
            long j2 = this.ALLOW_WHILE_IDLE_WHITELIST_DURATION;
            if (j != j2) {
                this.mLastAllowWhileIdleWhitelistDuration = j2;
                AlarmManagerService.this.mOptsWithFgs.setTemporaryAppAllowlist(j2, 0, FrameworkStatsLog.APP_BACKGROUND_RESTRICTIONS_INFO__EXEMPTION_REASON__REASON_ALARM_MANAGER_WHILE_IDLE, "");
                AlarmManagerService.this.mOptsWithFgsForAlarmClock.setTemporaryAppAllowlist(this.ALLOW_WHILE_IDLE_WHITELIST_DURATION, 0, 301, "");
                AlarmManagerService.this.mOptsWithoutFgs.setTemporaryAppAllowlist(this.ALLOW_WHILE_IDLE_WHITELIST_DURATION, 1, -1, "");
            }
        }

        public final void updateDeviceIdleFuzzBoundaries() {
            DeviceConfig.Properties properties = DeviceConfig.getProperties("alarm_manager", new String[]{KEY_MIN_DEVICE_IDLE_FUZZ, KEY_MAX_DEVICE_IDLE_FUZZ});
            this.MIN_DEVICE_IDLE_FUZZ = properties.getLong(KEY_MIN_DEVICE_IDLE_FUZZ, 120000L);
            long j = properties.getLong(KEY_MAX_DEVICE_IDLE_FUZZ, 900000L);
            this.MAX_DEVICE_IDLE_FUZZ = j;
            if (j < this.MIN_DEVICE_IDLE_FUZZ) {
                Slog.w("AlarmManager", "max_device_idle_fuzz cannot be smaller than min_device_idle_fuzz! Increasing to " + this.MIN_DEVICE_IDLE_FUZZ);
                this.MAX_DEVICE_IDLE_FUZZ = this.MIN_DEVICE_IDLE_FUZZ;
            }
        }

        public final void updateStandbyQuotasLocked() {
            DeviceConfig.Properties properties = DeviceConfig.getProperties("alarm_manager", this.KEYS_APP_STANDBY_QUOTAS);
            this.APP_STANDBY_QUOTAS[0] = properties.getInt(this.KEYS_APP_STANDBY_QUOTAS[0], this.DEFAULT_APP_STANDBY_QUOTAS[0]);
            int i = 1;
            while (true) {
                String[] strArr = this.KEYS_APP_STANDBY_QUOTAS;
                if (i >= strArr.length) {
                    this.APP_STANDBY_RESTRICTED_QUOTA = Math.max(1, DeviceConfig.getInt("alarm_manager", "standby_quota_restricted", 1));
                    return;
                } else {
                    int[] iArr = this.APP_STANDBY_QUOTAS;
                    iArr[i] = properties.getInt(strArr[i], Math.min(iArr[i - 1], this.DEFAULT_APP_STANDBY_QUOTAS[i]));
                    i++;
                }
            }
        }

        public final void updateStandbyWindowsLocked() {
            DeviceConfig.Properties properties = DeviceConfig.getProperties("alarm_manager", new String[]{"app_standby_window", "app_standby_restricted_window"});
            long j = properties.getLong("app_standby_window", ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS);
            this.APP_STANDBY_WINDOW = j;
            if (j > ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                Slog.w("AlarmManager", "Cannot exceed the app_standby_window size of 3600000");
                this.APP_STANDBY_WINDOW = ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
            } else if (j < ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS) {
                Slog.w("AlarmManager", "Using a non-default app_standby_window of " + this.APP_STANDBY_WINDOW);
            }
            this.APP_STANDBY_RESTRICTED_WINDOW = Math.max(this.APP_STANDBY_WINDOW, properties.getLong("app_standby_restricted_window", BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS));
        }
    }

    public final class DeliveredAlarm {
        public final String mHash;
        public final long mOrigWhen;
        public final String mTag;
        public final long mWhenDeliveredElapsed;
        public final long mWhenDeliveredRtc;

        public DeliveredAlarm(Alarm alarm, long j, long j2) {
            StringBuilder sb;
            int hashCode;
            this.mTag = alarm.statsTag;
            this.mOrigWhen = alarm.origWhen;
            if (alarm.operation != null) {
                sb = new StringBuilder("PI:");
                hashCode = alarm.operation.hashCode();
            } else {
                sb = new StringBuilder("L:");
                hashCode = alarm.listener.asBinder().hashCode();
            }
            sb.append(Integer.toHexString(hashCode));
            this.mHash = sb.toString();
            this.mWhenDeliveredRtc = j;
            this.mWhenDeliveredElapsed = j2;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.print("[tag", this.mTag);
            indentingPrintWriter.print("origWhen", Long.valueOf(this.mOrigWhen));
            indentingPrintWriter.print("H", this.mHash);
            indentingPrintWriter.print("elapsed", Long.valueOf(this.mWhenDeliveredElapsed));
            indentingPrintWriter.print("rtc=");
            indentingPrintWriter.print(simpleDateFormat.format(new Date(this.mWhenDeliveredRtc)));
            indentingPrintWriter.println("]");
        }
    }

    public final class DeliveryTracker extends IAlarmCompleteListener.Stub implements PendingIntent.OnFinished {
        public DeliveryTracker() {
        }

        public final void alarmComplete(IBinder iBinder) {
            if (iBinder == null) {
                AlarmManagerService.this.mLog.w("Invalid alarmComplete: uid=" + Binder.getCallingUid() + " pid=" + Binder.getCallingPid());
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                synchronized (AlarmManagerService.this.mLock) {
                    try {
                        AlarmManagerService.this.mHandler.removeMessages(3, iBinder);
                        InFlight removeLocked = removeLocked(iBinder);
                        if (removeLocked != null) {
                            updateTrackingLocked(removeLocked);
                            AlarmManagerService.this.mListenerFinishCount++;
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }

        public final void alarmTimedOut(IBinder iBinder) {
            synchronized (AlarmManagerService.this.mLock) {
                try {
                    InFlight removeLocked = removeLocked(iBinder);
                    if (removeLocked != null) {
                        updateTrackingLocked(removeLocked);
                        AlarmManagerService.this.mListenerFinishCount++;
                    } else {
                        AlarmManagerService.this.mLog.w("Spurious timeout of listener " + iBinder);
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
        public final void deliverLocked(final com.android.server.alarm.Alarm r21, long r22) {
            /*
                Method dump skipped, instructions count: 663
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.DeliveryTracker.deliverLocked(com.android.server.alarm.Alarm, long):void");
        }

        @Override // android.app.PendingIntent.OnFinished
        public final void onSendFinished(PendingIntent pendingIntent, Intent intent, int i, String str, Bundle bundle) {
            synchronized (AlarmManagerService.this.mLock) {
                AlarmManagerService.this.mSendFinishCount++;
                updateTrackingLocked(removeLocked(pendingIntent, intent));
            }
        }

        public final InFlight removeLocked(PendingIntent pendingIntent, Intent intent) {
            for (int i = 0; i < AlarmManagerService.this.mInFlight.size(); i++) {
                if (((InFlight) AlarmManagerService.this.mInFlight.get(i)).mPendingIntent == pendingIntent) {
                    if (pendingIntent.isBroadcast()) {
                        AlarmManagerService alarmManagerService = AlarmManagerService.this;
                        if (alarmManagerService.mInFlightListeners.size() > 0) {
                            DssController$$ExternalSyntheticThrowCCEIfNotNull0.m(alarmManagerService.mInFlightListeners.get(0));
                            throw null;
                        }
                    }
                    return (InFlight) AlarmManagerService.this.mInFlight.remove(i);
                }
            }
            AlarmManagerService.this.mLog.w("No in-flight alarm for " + pendingIntent + " " + intent);
            return null;
        }

        public final InFlight removeLocked(IBinder iBinder) {
            for (int i = 0; i < AlarmManagerService.this.mInFlight.size(); i++) {
                if (((InFlight) AlarmManagerService.this.mInFlight.get(i)).mListener == iBinder) {
                    return (InFlight) AlarmManagerService.this.mInFlight.remove(i);
                }
            }
            AlarmManagerService.this.mLog.w("No in-flight alarm for listener " + iBinder);
            return null;
        }

        public final void updateTrackingLocked(InFlight inFlight) {
            if (inFlight != null) {
                AlarmManagerService.this.mInjector.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                BroadcastStats broadcastStats = inFlight.mBroadcastStats;
                int i = broadcastStats.nesting - 1;
                broadcastStats.nesting = i;
                if (i <= 0) {
                    broadcastStats.nesting = 0;
                    broadcastStats.aggregateTime = (elapsedRealtime - broadcastStats.startTime) + broadcastStats.aggregateTime;
                }
                FilterStats filterStats = inFlight.mFilterStats;
                int i2 = filterStats.nesting - 1;
                filterStats.nesting = i2;
                if (i2 <= 0) {
                    filterStats.nesting = 0;
                    filterStats.aggregateTime = (elapsedRealtime - filterStats.startTime) + filterStats.aggregateTime;
                }
                AlarmManagerService.this.mActivityManagerInternal.noteAlarmFinish(inFlight.mPendingIntent, inFlight.mWorkSource, inFlight.mUid, inFlight.mTag);
            }
            AlarmManagerService alarmManagerService = AlarmManagerService.this;
            int i3 = alarmManagerService.mBroadcastRefCount - 1;
            alarmManagerService.mBroadcastRefCount = i3;
            if (i3 != 0) {
                if (alarmManagerService.mInFlight.size() > 0) {
                    InFlight inFlight2 = (InFlight) AlarmManagerService.this.mInFlight.get(0);
                    AlarmManagerService.this.setWakelockWorkSource(inFlight2.mWorkSource, inFlight2.mCreatorUid, inFlight2.mTag, false);
                    return;
                } else {
                    AlarmManagerService.this.mLog.w("Alarm wakelock still held but sent queue empty");
                    AlarmManagerService.this.mWakeLock.setWorkSource(null);
                    return;
                }
            }
            alarmManagerService.mHandler.obtainMessage(4, 0, 0).sendToTarget();
            AlarmManagerService.this.mWakeLock.release();
            if (AlarmManagerService.this.mInFlight.size() > 0) {
                AlarmManagerService.this.mLog.w("Finished all dispatches with " + AlarmManagerService.this.mInFlight.size() + " remaining inflights");
                for (int i4 = 0; i4 < AlarmManagerService.this.mInFlight.size(); i4++) {
                    LocalLog localLog = AlarmManagerService.this.mLog;
                    StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i4, "  Remaining #", ": ");
                    m.append(AlarmManagerService.this.mInFlight.get(i4));
                    localLog.w(m.toString());
                }
                AlarmManagerService.this.mInFlight.clear();
            }
        }
    }

    public final class ExpiredRecord {
        public final long mDiff;
        public final long mFlags;
        public final long mLastElapsed;
        public final long mLastRtc;
        public final long mNonWakeup;
        public final long mWakeup;
        public final long mWhenExpiredElapsed;
        public final long mWhenExpiredRtc;

        public ExpiredRecord(long j, long j2, long j3, long j4, long j5, long j6, long j7) {
            this.mFlags = j;
            this.mWakeup = j2;
            this.mNonWakeup = j3;
            this.mWhenExpiredRtc = j4;
            this.mWhenExpiredElapsed = j5;
            this.mLastRtc = j6;
            this.mLastElapsed = j7;
            this.mDiff = ((j4 - j6) - j5) + j7;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, SimpleDateFormat simpleDateFormat) {
            long j = this.mFlags;
            indentingPrintWriter.print("[flag", Long.valueOf(j));
            long j2 = j & 65536;
            long j3 = this.mWhenExpiredElapsed;
            long j4 = this.mWhenExpiredRtc;
            if (j2 != 0) {
                long j5 = this.mLastRtc;
                if (j5 == 0) {
                    indentingPrintWriter.print("diff", "last 0");
                } else {
                    indentingPrintWriter.print("diff", Long.valueOf(((j4 - j5) - j3) + this.mLastElapsed));
                }
            }
            indentingPrintWriter.print("wakeup", Long.valueOf(this.mWakeup));
            indentingPrintWriter.print("non-wakeup", Long.valueOf(this.mNonWakeup));
            indentingPrintWriter.print("elapsed", Long.valueOf(j3));
            indentingPrintWriter.print("rtc=");
            indentingPrintWriter.print(simpleDateFormat.format(new Date(j4)));
            indentingPrintWriter.println("]");
        }
    }

    public final class FilterStats {
        public long aggregateTime;
        public int count;
        public long lastTime;
        public final BroadcastStats mBroadcastStats;
        public final String mTag;
        public int nesting;
        public int numWakeup;
        public long startTime;

        public FilterStats(BroadcastStats broadcastStats, String str) {
            this.mBroadcastStats = broadcastStats;
            this.mTag = str;
        }

        public final void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
            long start = protoOutputStream.start(j);
            protoOutputStream.write(1138166333441L, this.mTag);
            protoOutputStream.write(1112396529666L, this.lastTime);
            protoOutputStream.write(1112396529667L, this.aggregateTime);
            protoOutputStream.write(1120986464260L, this.count);
            protoOutputStream.write(1120986464261L, this.numWakeup);
            protoOutputStream.write(1112396529670L, this.startTime);
            protoOutputStream.write(1120986464263L, this.nesting);
            protoOutputStream.end(start);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("FilterStats{tag=");
            sb.append(this.mTag);
            sb.append(", lastTime=");
            sb.append(this.lastTime);
            sb.append(", aggregateTime=");
            sb.append(this.aggregateTime);
            sb.append(", count=");
            sb.append(this.count);
            sb.append(", numWakeup=");
            sb.append(this.numWakeup);
            sb.append(", startTime=");
            sb.append(this.startTime);
            sb.append(", nesting=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.nesting, sb, "}");
        }
    }

    public final class InFlight {
        public final int mAlarmType;
        public final BroadcastStats mBroadcastStats;
        public final int mCreatorUid;
        public final FilterStats mFilterStats;
        public final IBinder mListener;
        public final PendingIntent mPendingIntent;
        public final int mPriorityClass;
        public final String mTag;
        public final int mUid;
        public final long mWhenElapsed;
        public final WorkSource mWorkSource;

        public InFlight(AlarmManagerService alarmManagerService, Alarm alarm, long j) {
            BroadcastStats statsLocked;
            this.mPendingIntent = alarm.operation;
            this.mWhenElapsed = j;
            IAlarmListener iAlarmListener = alarm.listener;
            this.mListener = iAlarmListener != null ? iAlarmListener.asBinder() : null;
            this.mWorkSource = alarm.workSource;
            int i = alarm.uid;
            this.mUid = i;
            this.mCreatorUid = alarm.creatorUid;
            String str = alarm.statsTag;
            this.mTag = str;
            PendingIntent pendingIntent = alarm.operation;
            if (pendingIntent != null) {
                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                alarmManagerService.getClass();
                statsLocked = alarmManagerService.getStatsLocked(pendingIntent.getCreatorUid(), pendingIntent.getCreatorPackage());
            } else {
                Intent intent2 = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                statsLocked = alarmManagerService.getStatsLocked(i, alarm.packageName);
            }
            this.mBroadcastStats = statsLocked;
            FilterStats filterStats = (FilterStats) statsLocked.filterStats.get(str);
            if (filterStats == null) {
                filterStats = new FilterStats(statsLocked, str);
                statsLocked.filterStats.put(str, filterStats);
            }
            filterStats.lastTime = j;
            this.mFilterStats = filterStats;
            this.mAlarmType = alarm.type;
            this.mPriorityClass = alarm.priorityClass;
        }

        public final void dumpDebug(ProtoOutputStream protoOutputStream) {
            long start = protoOutputStream.start(2246267895842L);
            protoOutputStream.write(1120986464257L, this.mUid);
            protoOutputStream.write(1138166333442L, this.mTag);
            protoOutputStream.write(1112396529667L, this.mWhenElapsed);
            protoOutputStream.write(1159641169924L, this.mAlarmType);
            PendingIntent pendingIntent = this.mPendingIntent;
            if (pendingIntent != null) {
                pendingIntent.dumpDebug(protoOutputStream, 1146756268037L);
            }
            BroadcastStats broadcastStats = this.mBroadcastStats;
            if (broadcastStats != null) {
                broadcastStats.dumpDebug(protoOutputStream, 1146756268038L);
            }
            FilterStats filterStats = this.mFilterStats;
            if (filterStats != null) {
                filterStats.dumpDebug(protoOutputStream, 1146756268039L);
            }
            WorkSource workSource = this.mWorkSource;
            if (workSource != null) {
                workSource.dumpDebug(protoOutputStream, 1146756268040L);
            }
            protoOutputStream.end(start);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("InFlight{pendingIntent=");
            sb.append(this.mPendingIntent);
            sb.append(", when=");
            sb.append(this.mWhenElapsed);
            sb.append(", workSource=");
            sb.append(this.mWorkSource);
            sb.append(", uid=");
            sb.append(this.mUid);
            sb.append(", creatorUid=");
            sb.append(this.mCreatorUid);
            sb.append(", tag=");
            sb.append(this.mTag);
            sb.append(", broadcastStats=");
            sb.append(this.mBroadcastStats);
            sb.append(", filterStats=");
            sb.append(this.mFilterStats);
            sb.append(", alarmType=");
            sb.append(this.mAlarmType);
            sb.append(", priorityClass=");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.mPriorityClass, sb, "}");
        }
    }

    public class Injector {
        public Context mContext;
        public long mNativeData;

        public static void initializeTimeIfRequired() {
            android.util.LocalLog localLog = SystemClockTime.sTimeDebugLog;
            long max = Long.max(SystemProperties.getLong("ro.build.date.utc", -1L) * 1000, Long.max(Environment.getRootDirectory().lastModified(), Build.TIME));
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < max) {
                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Current time only ", currentTimeMillis, ", advancing to build time ");
                m.append(max);
                String sb = m.toString();
                Slog.i("SystemClockTime", sb);
                SystemClockTime.setTimeAndConfidence(0, sb, max);
            }
        }
    }

    public final class LocalService {
        public LocalService() {
        }
    }

    public abstract class RemoveFailedRequest {
    }

    public final class RemovedAlarm {
        public final Alarm.Snapshot mAlarmSnapshot;
        public final String mHash;
        public final int mRemoveReason;
        public final long mWhenRemovedElapsed;
        public final long mWhenRemovedRtc;

        public RemovedAlarm(Alarm alarm, int i, long j, long j2) {
            StringBuilder sb;
            int hashCode;
            this.mAlarmSnapshot = new Alarm.Snapshot(alarm);
            this.mRemoveReason = i;
            this.mWhenRemovedRtc = j;
            this.mWhenRemovedElapsed = j2;
            if (alarm.operation != null) {
                sb = new StringBuilder("PI:");
                hashCode = alarm.operation.hashCode();
            } else {
                sb = new StringBuilder("L:");
                hashCode = alarm.listener.asBinder().hashCode();
            }
            sb.append(Integer.toHexString(hashCode));
            this.mHash = sb.toString();
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, long j, SimpleDateFormat simpleDateFormat) {
            String str;
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.print("H", this.mHash);
            int i = this.mRemoveReason;
            switch (i) {
                case 1:
                    str = "alarm_cancelled";
                    break;
                case 2:
                    str = "exact_alarm_permission_revoked";
                    break;
                case 3:
                    str = "data_cleared";
                    break;
                case 4:
                    str = "pi_cancelled";
                    break;
                case 5:
                    str = "listener_binder_died";
                    break;
                case 6:
                    str = "listener_cached";
                    break;
                case 7:
                    str = "uninstall_receiver";
                    break;
                default:
                    str = VibrationParam$1$$ExternalSyntheticOutline0.m(i, "unknown:");
                    break;
            }
            indentingPrintWriter.print("Reason", str);
            long j2 = this.mWhenRemovedElapsed;
            indentingPrintWriter.print("elapsed", Long.valueOf(j2));
            TimeUtils.formatDuration(j2, j, indentingPrintWriter);
            indentingPrintWriter.print(" rtc=");
            indentingPrintWriter.print(simpleDateFormat.format(new Date(this.mWhenRemovedRtc)));
            indentingPrintWriter.println();
            indentingPrintWriter.println("Snapshot:");
            indentingPrintWriter.increaseIndent();
            Alarm.Snapshot snapshot = this.mAlarmSnapshot;
            indentingPrintWriter.print("type", Alarm.typeToString(snapshot.mType));
            indentingPrintWriter.print("tag", snapshot.mTag);
            indentingPrintWriter.println();
            indentingPrintWriter.print("policyWhenElapsed:");
            for (int i2 = 0; i2 < 5; i2++) {
                indentingPrintWriter.print(" " + Alarm.policyIndexToString(i2) + "=");
                TimeUtils.formatDuration(snapshot.mPolicyWhenElapsed[i2], j, indentingPrintWriter);
            }
            indentingPrintWriter.println();
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final class ShellCmd extends ShellCommand {
        public ShellCmd() {
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final int onCommand(java.lang.String r8) {
            /*
                r7 = this;
                if (r8 != 0) goto L7
                int r7 = r7.handleDefaultCommands(r8)
                return r7
            L7:
                java.io.PrintWriter r0 = r7.getOutPrintWriter()
                r1 = -1
                int r2 = r8.hashCode()     // Catch: java.lang.Exception -> L2e
                r3 = -2120488796(0xffffffff819be8a4, float:-5.727183E-38)
                r4 = 2
                r5 = 1
                r6 = 0
                if (r2 == r3) goto L3b
                r3 = 1369384280(0x519f2558, float:8.5440791E10)
                if (r2 == r3) goto L30
                r3 = 2023087364(0x7895dd04, float:2.4316718E34)
                if (r2 == r3) goto L23
                goto L46
            L23:
                java.lang.String r2 = "set-timezone"
                boolean r2 = r8.equals(r2)     // Catch: java.lang.Exception -> L2e
                if (r2 == 0) goto L46
                r2 = r5
                goto L47
            L2e:
                r7 = move-exception
                goto L8a
            L30:
                java.lang.String r2 = "set-time"
                boolean r2 = r8.equals(r2)     // Catch: java.lang.Exception -> L2e
                if (r2 == 0) goto L46
                r2 = r6
                goto L47
            L3b:
                java.lang.String r2 = "get-config-version"
                boolean r2 = r8.equals(r2)     // Catch: java.lang.Exception -> L2e
                if (r2 == 0) goto L46
                r2 = r4
                goto L47
            L46:
                r2 = r1
            L47:
                if (r2 == 0) goto L72
                if (r2 == r5) goto L62
                if (r2 == r4) goto L52
                int r7 = r7.handleDefaultCommands(r8)     // Catch: java.lang.Exception -> L2e
                return r7
            L52:
                com.android.server.alarm.AlarmManagerService r7 = com.android.server.alarm.AlarmManagerService.this     // Catch: java.lang.Exception -> L2e
                com.android.server.alarm.AlarmManagerService$4 r7 = r7.mService     // Catch: java.lang.Exception -> L2e
                android.app.IAlarmManager r7 = android.app.IAlarmManager.Stub.asInterface(r7)     // Catch: java.lang.Exception -> L2e
                int r7 = r7.getConfigVersion()     // Catch: java.lang.Exception -> L2e
                r0.println(r7)     // Catch: java.lang.Exception -> L2e
                return r6
            L62:
                java.lang.String r8 = r7.getNextArgRequired()     // Catch: java.lang.Exception -> L2e
                com.android.server.alarm.AlarmManagerService r7 = com.android.server.alarm.AlarmManagerService.this     // Catch: java.lang.Exception -> L2e
                com.android.server.alarm.AlarmManagerService$4 r7 = r7.mService     // Catch: java.lang.Exception -> L2e
                android.app.IAlarmManager r7 = android.app.IAlarmManager.Stub.asInterface(r7)     // Catch: java.lang.Exception -> L2e
                r7.setTimeZone(r8)     // Catch: java.lang.Exception -> L2e
                return r6
            L72:
                java.lang.String r8 = r7.getNextArgRequired()     // Catch: java.lang.Exception -> L2e
                long r2 = java.lang.Long.parseLong(r8)     // Catch: java.lang.Exception -> L2e
                com.android.server.alarm.AlarmManagerService r7 = com.android.server.alarm.AlarmManagerService.this     // Catch: java.lang.Exception -> L2e
                com.android.server.alarm.AlarmManagerService$4 r7 = r7.mService     // Catch: java.lang.Exception -> L2e
                android.app.IAlarmManager r7 = android.app.IAlarmManager.Stub.asInterface(r7)     // Catch: java.lang.Exception -> L2e
                boolean r7 = r7.setTime(r2)     // Catch: java.lang.Exception -> L2e
                if (r7 == 0) goto L89
                r1 = r6
            L89:
                return r1
            L8a:
                r0.println(r7)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.ShellCmd.onCommand(java.lang.String):int");
        }

        public final void onHelp() {
            PrintWriter outPrintWriter = getOutPrintWriter();
            outPrintWriter.println("Alarm manager service (alarm) commands:");
            outPrintWriter.println("  help");
            outPrintWriter.println("    Print this help text.");
            outPrintWriter.println("  set-time TIME");
            BatteryService$$ExternalSyntheticOutline0.m(outPrintWriter, "    Set the system clock time to TIME where TIME is milliseconds", "    since the Epoch.", "  set-timezone TZ", "    Set the system timezone to TZ where TZ is an Olson id.");
            outPrintWriter.println("  get-config-version");
            outPrintWriter.println("    Returns an integer denoting the version of device_config keys the service is sync'ed to. As long as this returns the same version, the values of the config are guaranteed to remain the same.");
        }
    }

    public final class TemporaryQuotaReserve {
        public final ArrayMap mQuotaBuffer = new ArrayMap();
        public final long mMaxDuration = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;

        public final class QuotaInfo {
            public long expirationTime;
            public long lastUsage;
            public int remainingQuota;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, long j) {
            indentingPrintWriter.increaseIndent();
            for (int i = 0; i < this.mQuotaBuffer.size(); i++) {
                UserPackage userPackage = (UserPackage) this.mQuotaBuffer.keyAt(i);
                QuotaInfo quotaInfo = (QuotaInfo) this.mQuotaBuffer.valueAt(i);
                indentingPrintWriter.print(userPackage.packageName);
                indentingPrintWriter.print(", u");
                indentingPrintWriter.print(userPackage.userId);
                indentingPrintWriter.print(": ");
                if (quotaInfo == null) {
                    indentingPrintWriter.print("--");
                } else {
                    indentingPrintWriter.print("quota: ");
                    indentingPrintWriter.print(quotaInfo.remainingQuota);
                    indentingPrintWriter.print(", expiration: ");
                    TimeUtils.formatDuration(quotaInfo.expirationTime, j, indentingPrintWriter);
                    indentingPrintWriter.print(" last used: ");
                    TimeUtils.formatDuration(quotaInfo.lastUsage, j, indentingPrintWriter);
                }
                indentingPrintWriter.println();
            }
            indentingPrintWriter.decreaseIndent();
        }
    }

    public final class UninstallReceiver extends BroadcastReceiver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AlarmManagerService this$0;

        public UninstallReceiver(AlarmManagerService alarmManagerService, int i) {
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = alarmManagerService;
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.SCREEN_OFF");
                    intentFilter.addAction("android.intent.action.SCREEN_ON");
                    intentFilter.setPriority(1000);
                    alarmManagerService.getContext().registerReceiver(this, intentFilter);
                    break;
                default:
                    this.this$0 = alarmManagerService;
                    IntentFilter intentFilter2 = new IntentFilter();
                    intentFilter2.addAction("android.intent.action.PACKAGE_REMOVED");
                    intentFilter2.addAction("android.intent.action.PACKAGE_ADDED");
                    intentFilter2.addAction("android.intent.action.PACKAGE_RESTARTED");
                    intentFilter2.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
                    intentFilter2.addDataScheme("package");
                    alarmManagerService.getContext().registerReceiverForAllUsers(this, intentFilter2, null, null);
                    IntentFilter intentFilter3 = new IntentFilter();
                    intentFilter3.addAction("android.intent.action.EXTERNAL_APPLICATIONS_UNAVAILABLE");
                    intentFilter3.addAction("android.intent.action.USER_STOPPED");
                    intentFilter3.addAction("android.intent.action.UID_REMOVED");
                    alarmManagerService.getContext().registerReceiverForAllUsers(this, intentFilter3, null, null);
                    break;
            }
        }

        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r10, android.content.Intent r11) {
            /*
                Method dump skipped, instructions count: 642
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.UninstallReceiver.onReceive(android.content.Context, android.content.Intent):void");
        }
    }

    public final class WakeupRecord {
        public final String mTag;
        public final int mUid;
        public final long mWakeupRtc;

        public WakeupRecord(long j, int i, String str) {
            this.mWakeupRtc = j;
            this.mUid = i;
            this.mTag = str;
        }

        public final void dump(IndentingPrintWriter indentingPrintWriter, SimpleDateFormat simpleDateFormat) {
            indentingPrintWriter.print("rtc", simpleDateFormat.format(new Date(this.mWakeupRtc)));
            indentingPrintWriter.print("uid", Integer.valueOf(this.mUid));
            indentingPrintWriter.print("tag", this.mTag);
            indentingPrintWriter.println();
        }
    }

    /* renamed from: -$$Nest$mdeliverHistory, reason: not valid java name */
    public static void m149$$Nest$mdeliverHistory(AlarmManagerService alarmManagerService, Alarm alarm) {
        alarmManagerService.mInjector.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        SparseArray sparseArray = alarmManagerService.mDeliveryHistory;
        int i = alarm.uid;
        RingBuffer ringBuffer = (RingBuffer) sparseArray.get(i);
        if (ringBuffer == null) {
            ringBuffer = (i == 1000 || "com.sec.android.app.clockpackage".equals(alarm.sourcePackage)) ? new RingBuffer(DeliveredAlarm.class, 50) : i == alarmManagerService.mSystemUiUid ? new RingBuffer(DeliveredAlarm.class, 30) : new RingBuffer(DeliveredAlarm.class, 10);
            alarmManagerService.mDeliveryHistory.put(i, ringBuffer);
        }
        ringBuffer.append(new DeliveredAlarm(alarm, currentTimeMillis, elapsedRealtime));
        if (!alarm.wakeup || alarmManagerService.mInteractive) {
            return;
        }
        alarmManagerService.mWakeupAlarmHistory.append(alarmManagerService.new WakeupRecord(currentTimeMillis, alarm.uid, alarm.statsTag));
    }

    /* renamed from: -$$Nest$mfiltAlarmsForFreeCess, reason: not valid java name */
    public static void m150$$Nest$mfiltAlarmsForFreeCess(AlarmManagerService alarmManagerService, ArrayList arrayList) {
        PendingIntent pendingIntent;
        alarmManagerService.getClass();
        for (int size = arrayList.size() - 1; size >= 0; size--) {
            Alarm alarm = (Alarm) arrayList.get(size);
            if (alarm != null && (pendingIntent = alarm.operation) != null) {
                boolean z = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                FreecessController freecessController = FreecessController.FreecessControllerHolder.INSTANCE;
                if (freecessController.isFreezedPackage(UserHandle.getUserId(alarm.operation.getCreatorUid()), pendingIntent.getCreatorPackage())) {
                    MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE.getClass();
                    if (MARsPolicyManager.isChinaPolicyEnabled() || !freecessController.FREECESS_ENHANCEMENT) {
                        int creatorUid = alarm.operation.getCreatorUid();
                        MARsPolicyManager.Lock lock = MARsPolicyManager.MARsLock;
                        synchronized (lock) {
                            try {
                                ArrayList arrayList2 = freecessController.mMonitorFreezedList;
                                if (arrayList2 == null || !arrayList2.contains(Integer.valueOf(creatorUid))) {
                                    String creatorPackage = alarm.operation.getCreatorPackage();
                                    int userId = UserHandle.getUserId(alarm.operation.getCreatorUid());
                                    synchronized (lock) {
                                        try {
                                            FreecessPkgStatus frozenPackageStatus = freecessController.getFrozenPackageStatus(userId, creatorPackage);
                                            if (frozenPackageStatus != null) {
                                                MARsFreezeStateRecord mARsFreezeStateRecord = frozenPackageStatus.freezedRecord;
                                                if (mARsFreezeStateRecord.isFrozen) {
                                                    if (!mARsFreezeStateRecord.isLcdOnFreezed) {
                                                        if (mARsFreezeStateRecord.isOLAFFreezed) {
                                                        }
                                                    }
                                                }
                                            }
                                            Slog.v("AlarmManager", "MARs: filtAlarmsForFreeCess ---pkgName = " + alarm.operation.getTargetPackage() + ", Uid = " + alarm.operation.getCreatorUid());
                                            arrayList.remove(size);
                                        } finally {
                                        }
                                    }
                                }
                            } finally {
                            }
                        }
                    }
                    freecessController.unFreezePackage(UserHandle.getUserId(alarm.operation.getCreatorUid()), alarm.operation.getCreatorPackage(), "Alarm");
                } else {
                    continue;
                }
            }
        }
    }

    /* renamed from: -$$Nest$mlogAlarmBatchDelivered, reason: not valid java name */
    public static void m151$$Nest$mlogAlarmBatchDelivered(AlarmManagerService alarmManagerService, int i, int i2, SparseIntArray sparseIntArray, SparseIntArray sparseIntArray2) {
        alarmManagerService.getClass();
        int[] iArr = new int[sparseIntArray.size()];
        int[] iArr2 = new int[sparseIntArray.size()];
        int[] iArr3 = new int[sparseIntArray.size()];
        for (int i3 = 0; i3 < sparseIntArray.size(); i3++) {
            iArr[i3] = sparseIntArray.keyAt(i3);
            iArr2[i3] = sparseIntArray.valueAt(i3);
            iArr3[i3] = sparseIntArray2.get(iArr[i3], 0);
        }
        FrameworkStatsLog.write(FrameworkStatsLog.ALARM_BATCH_DELIVERED, i, i2, iArr, iArr2, iArr3);
    }

    /* renamed from: -$$Nest$msendNextAlarmClockChanged, reason: not valid java name */
    public static void m152$$Nest$msendNextAlarmClockChanged(AlarmManagerService alarmManagerService) {
        int i;
        SparseArray sparseArray = alarmManagerService.mHandlerSparseAlarmClockArray;
        sparseArray.clear();
        synchronized (alarmManagerService.mLock) {
            try {
                int size = alarmManagerService.mPendingSendNextAlarmClockChangedForUser.size();
                for (int i2 = 0; i2 < size; i2++) {
                    int keyAt = alarmManagerService.mPendingSendNextAlarmClockChangedForUser.keyAt(i2);
                    sparseArray.append(keyAt, (AlarmManager.AlarmClockInfo) alarmManagerService.mNextAlarmClockForUser.get(keyAt));
                }
                alarmManagerService.mPendingSendNextAlarmClockChangedForUser.clear();
            } catch (Throwable th) {
                throw th;
            }
        }
        int size2 = sparseArray.size();
        for (i = 0; i < size2; i++) {
            int keyAt2 = sparseArray.keyAt(i);
            AlarmManager.AlarmClockInfo alarmClockInfo = (AlarmManager.AlarmClockInfo) sparseArray.valueAt(i);
            Settings.System.putStringForUser(alarmManagerService.getContext().getContentResolver(), "next_alarm_formatted", alarmClockInfo == null ? "" : DateFormat.format(DateFormat.getBestDateTimePattern(Locale.getDefault(), DateFormat.is24HourFormat(alarmManagerService.getContext(), keyAt2) ? "EHm" : "Ehma"), alarmClockInfo.getTriggerTime()).toString(), keyAt2);
            alarmManagerService.getContext().sendBroadcastAsUser(NEXT_ALARM_CLOCK_CHANGED_INTENT, new UserHandle(keyAt2));
        }
    }

    /* renamed from: -$$Nest$smgetNextAlarm, reason: not valid java name */
    public static /* bridge */ /* synthetic */ long m153$$Nest$smgetNextAlarm(int i, long j) {
        return getNextAlarm(j, i);
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public AlarmManagerService(android.content.Context r2) {
        /*
            r1 = this;
            com.android.server.alarm.AlarmManagerService$Injector r0 = new com.android.server.alarm.AlarmManagerService$Injector
            r0.<init>()
            r0.mContext = r2
            r1.<init>(r2, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.<init>(android.content.Context):void");
    }

    public AlarmManagerService(Context context, Injector injector) {
        super(context);
        this.mBackgroundIntent = new Intent().addFlags(4);
        this.mLog = new LocalLog("AlarmManager");
        this.mLock = new Object();
        this.mExactAlarmCandidates = Collections.emptySet();
        this.mLastOpScheduleExactAlarm = new SparseIntArray();
        this.mPendingBackgroundAlarms = new SparseArray();
        this.mPendingMARsRestrictedAlarms = new SparseArray();
        this.mTickHistory = new long[10];
        this.mBroadcastRefCount = 0;
        this.mAlarmsPerUid = new SparseIntArray();
        this.mPendingNonWakeupAlarms = new ArrayList();
        this.mInFlight = new ArrayList();
        this.mInFlightListeners = new ArrayList();
        this.mLastPriorityAlarmDispatch = new SparseLongArray();
        this.mRemovalHistory = new SparseArray();
        this.mRemoveFailedHistory = new SparseArray();
        this.mAdditionHistory = new SparseArray();
        this.mDeliveryHistory = new SparseArray();
        this.mExpirationHistory = new RingBuffer(ExpiredRecord.class, 50);
        this.mInvalidExpirationHistory = new RingBuffer(ExpiredRecord.class, 50);
        this.mWakeupAlarmHistory = new RingBuffer(WakeupRecord.class, 200);
        this.mDeliveryTracker = new DeliveryTracker();
        this.mInteractive = true;
        new ArrayList();
        this.mStatLogger = new StatLogger("Alarm manager stats", new String[]{"REORDER_ALARMS_FOR_STANDBY", "HAS_SCHEDULE_EXACT_ALARM"});
        BroadcastOptions makeBasic = BroadcastOptions.makeBasic();
        makeBasic.setAlarmBroadcast(true);
        this.mOptsWithFgs = makeBasic;
        BroadcastOptions makeBasic2 = BroadcastOptions.makeBasic();
        makeBasic2.setAlarmBroadcast(true);
        this.mOptsWithFgsForAlarmClock = makeBasic2;
        BroadcastOptions makeBasic3 = BroadcastOptions.makeBasic();
        makeBasic3.setAlarmBroadcast(true);
        this.mOptsWithoutFgs = makeBasic3;
        BroadcastOptions makeBasic4 = BroadcastOptions.makeBasic();
        makeBasic4.setAlarmBroadcast(true);
        this.mOptsTimeBroadcast = makeBasic4;
        this.mActivityOptsRestrictBal = ActivityOptions.makeBasic();
        BroadcastOptions makeBasic5 = BroadcastOptions.makeBasic();
        makeBasic5.setAlarmBroadcast(true);
        this.mBroadcastOptsRestrictBal = makeBasic5;
        this.mNextAlarmClockForUser = new SparseArray();
        this.mTmpSparseAlarmClockArray = new SparseArray();
        this.mPendingSendNextAlarmClockChangedForUser = new SparseBooleanArray();
        this.mAlarmClockUpdater = new AlarmManagerService$$ExternalSyntheticLambda3(this, 0);
        this.mHandlerSparseAlarmClockArray = new SparseArray();
        this.mAlarmDispatchComparator = new Comparator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda4
            /* JADX WARN: Code restructure failed: missing block: B:25:0x0043, code lost:
            
                if (r0 != false) goto L16;
             */
            /* JADX WARN: Code restructure failed: missing block: B:9:0x0022, code lost:
            
                if (r0 != false) goto L16;
             */
            @Override // java.util.Comparator
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final int compare(java.lang.Object r8, java.lang.Object r9) {
                /*
                    r7 = this;
                    com.android.server.alarm.AlarmManagerService r7 = com.android.server.alarm.AlarmManagerService.this
                    com.android.server.alarm.Alarm r8 = (com.android.server.alarm.Alarm) r8
                    com.android.server.alarm.Alarm r9 = (com.android.server.alarm.Alarm) r9
                    android.content.Intent r0 = com.android.server.alarm.AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT
                    r7.getClass()
                    int r0 = r8.flags
                    r0 = r0 & 16
                    r1 = 0
                    r2 = 1
                    if (r0 == 0) goto L15
                    r0 = r2
                    goto L16
                L15:
                    r0 = r1
                L16:
                    int r3 = r9.flags
                    r3 = r3 & 16
                    if (r3 == 0) goto L1e
                    r3 = r2
                    goto L1f
                L1e:
                    r3 = r1
                L1f:
                    r4 = -1
                    if (r0 == r3) goto L25
                    if (r0 == 0) goto L2f
                    goto L2b
                L25:
                    int r0 = r8.priorityClass
                    int r3 = r9.priorityClass
                    if (r0 >= r3) goto L2d
                L2b:
                    r1 = r4
                    goto L56
                L2d:
                    if (r0 <= r3) goto L31
                L2f:
                    r1 = r2
                    goto L56
                L31:
                    android.app.IAlarmListener r0 = r8.listener
                    com.android.server.alarm.AlarmManagerService$2 r7 = r7.mTimeTickTrigger
                    if (r0 != r7) goto L39
                    r0 = r2
                    goto L3a
                L39:
                    r0 = r1
                L3a:
                    android.app.IAlarmListener r3 = r9.listener
                    if (r3 != r7) goto L40
                    r7 = r2
                    goto L41
                L40:
                    r7 = r1
                L41:
                    if (r0 == r7) goto L46
                    if (r0 == 0) goto L2f
                    goto L2b
                L46:
                    long[] r7 = r8.mPolicyWhenElapsed
                    r7 = r7[r1]
                    long[] r9 = r9.mPolicyWhenElapsed
                    r5 = r9[r1]
                    int r7 = (r7 > r5 ? 1 : (r7 == r5 ? 0 : -1))
                    if (r7 >= 0) goto L53
                    goto L2b
                L53:
                    if (r7 <= 0) goto L56
                    goto L2f
                L56:
                    return r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda4.compare(java.lang.Object, java.lang.Object):int");
            }
        };
        this.mPendingIdleUntil = null;
        this.mNextWakeFromIdle = null;
        this.mBroadcastStats = new SparseArray();
        this.mNumDelayedAlarms = 0;
        this.mTotalDelayTime = 0L;
        this.mMaxDelayTime = 0L;
        this.mService = new IAlarmManager.Stub() { // from class: com.android.server.alarm.AlarmManagerService.4
            public final boolean canScheduleExactAlarms(String str) {
                AlarmManagerService.this.mInjector.getClass();
                int callingUid = Binder.getCallingUid();
                int userId = UserHandle.getUserId(callingUid);
                int packageUid = AlarmManagerService.this.mPackageManagerInternal.getPackageUid(str, 0L, userId);
                if (callingUid == packageUid) {
                    return !AlarmManagerService.isExactAlarmChangeEnabled(userId, str) || AlarmManagerService.this.isExemptFromExactAlarmPermissionNoLock(packageUid) || AlarmManagerService.this.hasScheduleExactAlarmInternal(packageUid, str) || AlarmManagerService.this.hasUseExactAlarmInternal(packageUid, str);
                }
                throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Uid ", " cannot query canScheduleExactAlarms for package ", str));
            }

            public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
                if (DumpUtils.checkDumpAndUsageStatsPermission(AlarmManagerService.this.getContext(), "AlarmManager", printWriter)) {
                    if (strArr.length > 0) {
                        int i = 0;
                        if ("--proto".equals(strArr[0])) {
                            AlarmManagerService alarmManagerService = AlarmManagerService.this;
                            alarmManagerService.getClass();
                            ProtoOutputStream protoOutputStream = new ProtoOutputStream(fileDescriptor);
                            synchronized (alarmManagerService.mLock) {
                                try {
                                    alarmManagerService.mInjector.getClass();
                                    long currentTimeMillis = System.currentTimeMillis();
                                    alarmManagerService.mInjector.getClass();
                                    long elapsedRealtime = SystemClock.elapsedRealtime();
                                    protoOutputStream.write(1112396529665L, currentTimeMillis);
                                    protoOutputStream.write(1112396529666L, elapsedRealtime);
                                    protoOutputStream.write(1112396529667L, alarmManagerService.mLastTimeChangeClockTime);
                                    protoOutputStream.write(1112396529668L, alarmManagerService.mLastTimeChangeRealtime);
                                    Constants constants = alarmManagerService.mConstants;
                                    constants.getClass();
                                    long start = protoOutputStream.start(1146756268037L);
                                    protoOutputStream.write(1112396529665L, constants.MIN_FUTURITY);
                                    protoOutputStream.write(1112396529666L, constants.MIN_INTERVAL);
                                    protoOutputStream.write(1112396529671L, constants.MAX_INTERVAL);
                                    protoOutputStream.write(1112396529667L, constants.LISTENER_TIMEOUT);
                                    protoOutputStream.write(1112396529670L, constants.ALLOW_WHILE_IDLE_WHITELIST_DURATION);
                                    protoOutputStream.end(start);
                                    AppStateTrackerImpl appStateTrackerImpl = alarmManagerService.mAppStateTracker;
                                    if (appStateTrackerImpl != null) {
                                        appStateTrackerImpl.dumpProto(protoOutputStream, 1146756268038L);
                                    }
                                    protoOutputStream.write(1133871366151L, alarmManagerService.mInteractive);
                                    if (!alarmManagerService.mInteractive) {
                                        protoOutputStream.write(1112396529672L, elapsedRealtime - alarmManagerService.mNonInteractiveStartTime);
                                        protoOutputStream.write(1112396529673L, alarmManagerService.currentNonWakeupFuzzLocked(elapsedRealtime));
                                        protoOutputStream.write(1112396529674L, elapsedRealtime - alarmManagerService.mLastAlarmDeliveryTime);
                                        protoOutputStream.write(1112396529675L, elapsedRealtime - alarmManagerService.mNextNonWakeupDeliveryTime);
                                    }
                                    protoOutputStream.write(1112396529676L, alarmManagerService.mNextNonWakeup - elapsedRealtime);
                                    protoOutputStream.write(1112396529677L, alarmManagerService.mNextWakeup - elapsedRealtime);
                                    protoOutputStream.write(1112396529678L, elapsedRealtime - alarmManagerService.mLastWakeup);
                                    protoOutputStream.write(1112396529679L, elapsedRealtime - alarmManagerService.mNextWakeUpSetAt);
                                    protoOutputStream.write(1112396529680L, alarmManagerService.mNumTimeChanged);
                                    TreeSet treeSet = new TreeSet();
                                    int size = alarmManagerService.mNextAlarmClockForUser.size();
                                    for (int i2 = 0; i2 < size; i2++) {
                                        treeSet.add(Integer.valueOf(alarmManagerService.mNextAlarmClockForUser.keyAt(i2)));
                                    }
                                    int size2 = alarmManagerService.mPendingSendNextAlarmClockChangedForUser.size();
                                    for (int i3 = 0; i3 < size2; i3++) {
                                        treeSet.add(Integer.valueOf(alarmManagerService.mPendingSendNextAlarmClockChangedForUser.keyAt(i3)));
                                    }
                                    Iterator it = treeSet.iterator();
                                    while (it.hasNext()) {
                                        int intValue = ((Integer) it.next()).intValue();
                                        AlarmManager.AlarmClockInfo alarmClockInfo = (AlarmManager.AlarmClockInfo) alarmManagerService.mNextAlarmClockForUser.get(intValue);
                                        long triggerTime = alarmClockInfo != null ? alarmClockInfo.getTriggerTime() : 0L;
                                        boolean z = alarmManagerService.mPendingSendNextAlarmClockChangedForUser.get(intValue);
                                        long start2 = protoOutputStream.start(2246267895826L);
                                        protoOutputStream.write(1120986464257L, intValue);
                                        protoOutputStream.write(1133871366146L, z);
                                        protoOutputStream.write(1112396529667L, triggerTime);
                                        protoOutputStream.end(start2);
                                    }
                                    Iterator it2 = alarmManagerService.mAlarmStore.mAlarms.iterator();
                                    while (it2.hasNext()) {
                                        ((Alarm) it2.next()).dumpDebug(protoOutputStream, 2246267895850L, elapsedRealtime);
                                    }
                                    for (int i4 = 0; i4 < alarmManagerService.mPendingBackgroundAlarms.size(); i4++) {
                                        ArrayList arrayList = (ArrayList) alarmManagerService.mPendingBackgroundAlarms.valueAt(i4);
                                        if (arrayList != null) {
                                            Iterator it3 = arrayList.iterator();
                                            while (it3.hasNext()) {
                                                ((Alarm) it3.next()).dumpDebug(protoOutputStream, 2246267895828L, elapsedRealtime);
                                            }
                                        }
                                    }
                                    Alarm alarm = alarmManagerService.mPendingIdleUntil;
                                    if (alarm != null) {
                                        alarm.dumpDebug(protoOutputStream, 1146756268053L, elapsedRealtime);
                                    }
                                    Alarm alarm2 = alarmManagerService.mNextWakeFromIdle;
                                    if (alarm2 != null) {
                                        alarm2.dumpDebug(protoOutputStream, 1146756268055L, elapsedRealtime);
                                    }
                                    Iterator it4 = alarmManagerService.mPendingNonWakeupAlarms.iterator();
                                    while (it4.hasNext()) {
                                        ((Alarm) it4.next()).dumpDebug(protoOutputStream, 2246267895832L, elapsedRealtime);
                                    }
                                    protoOutputStream.write(1120986464281L, alarmManagerService.mNumDelayedAlarms);
                                    protoOutputStream.write(1112396529690L, alarmManagerService.mTotalDelayTime);
                                    protoOutputStream.write(1112396529691L, alarmManagerService.mMaxDelayTime);
                                    protoOutputStream.write(1112396529692L, alarmManagerService.mNonInteractiveTime);
                                    protoOutputStream.write(1120986464285L, alarmManagerService.mBroadcastRefCount);
                                    protoOutputStream.write(1120986464286L, alarmManagerService.mSendCount);
                                    protoOutputStream.write(1120986464287L, alarmManagerService.mSendFinishCount);
                                    protoOutputStream.write(1120986464288L, alarmManagerService.mListenerCount);
                                    protoOutputStream.write(1120986464289L, alarmManagerService.mListenerFinishCount);
                                    Iterator it5 = alarmManagerService.mInFlight.iterator();
                                    while (it5.hasNext()) {
                                        ((InFlight) it5.next()).dumpDebug(protoOutputStream);
                                    }
                                    alarmManagerService.mLog.dumpDebug(protoOutputStream, 1146756268069L);
                                    FilterStats[] filterStatsArr = new FilterStats[10];
                                    AnonymousClass5 anonymousClass5 = new AnonymousClass5(alarmManagerService, 1);
                                    int i5 = 0;
                                    int i6 = 0;
                                    while (i5 < alarmManagerService.mBroadcastStats.size()) {
                                        ArrayMap arrayMap = (ArrayMap) alarmManagerService.mBroadcastStats.valueAt(i5);
                                        int i7 = i;
                                        while (i7 < arrayMap.size()) {
                                            BroadcastStats broadcastStats = (BroadcastStats) arrayMap.valueAt(i7);
                                            int i8 = i;
                                            while (i8 < broadcastStats.filterStats.size()) {
                                                FilterStats filterStats = (FilterStats) broadcastStats.filterStats.valueAt(i8);
                                                if (i6 > 0) {
                                                    i = Arrays.binarySearch(filterStatsArr, i, i6, filterStats, anonymousClass5);
                                                }
                                                if (i < 0) {
                                                    i = (-i) - 1;
                                                }
                                                if (i < 10) {
                                                    int i9 = 9 - i;
                                                    if (i9 > 0) {
                                                        System.arraycopy(filterStatsArr, i, filterStatsArr, i + 1, i9);
                                                    }
                                                    filterStatsArr[i] = filterStats;
                                                    if (i6 < 10) {
                                                        i6++;
                                                    }
                                                }
                                                i8++;
                                                i = 0;
                                            }
                                            i7++;
                                            i = 0;
                                        }
                                        i5++;
                                        i = 0;
                                    }
                                    for (int i10 = 0; i10 < i6; i10++) {
                                        long start3 = protoOutputStream.start(2246267895846L);
                                        FilterStats filterStats2 = filterStatsArr[i10];
                                        protoOutputStream.write(1120986464257L, filterStats2.mBroadcastStats.mUid);
                                        protoOutputStream.write(1138166333442L, filterStats2.mBroadcastStats.mPackageName);
                                        filterStats2.dumpDebug(protoOutputStream, 1146756268035L);
                                        protoOutputStream.end(start3);
                                    }
                                    ArrayList arrayList2 = new ArrayList();
                                    for (int i11 = 0; i11 < alarmManagerService.mBroadcastStats.size(); i11++) {
                                        ArrayMap arrayMap2 = (ArrayMap) alarmManagerService.mBroadcastStats.valueAt(i11);
                                        for (int i12 = 0; i12 < arrayMap2.size(); i12++) {
                                            long start4 = protoOutputStream.start(2246267895847L);
                                            BroadcastStats broadcastStats2 = (BroadcastStats) arrayMap2.valueAt(i12);
                                            broadcastStats2.dumpDebug(protoOutputStream, 1146756268033L);
                                            arrayList2.clear();
                                            for (int i13 = 0; i13 < broadcastStats2.filterStats.size(); i13++) {
                                                arrayList2.add((FilterStats) broadcastStats2.filterStats.valueAt(i13));
                                            }
                                            Collections.sort(arrayList2, anonymousClass5);
                                            Iterator it6 = arrayList2.iterator();
                                            while (it6.hasNext()) {
                                                ((FilterStats) it6.next()).dumpDebug(protoOutputStream, 2246267895810L);
                                            }
                                            protoOutputStream.end(start4);
                                        }
                                    }
                                } catch (Throwable th) {
                                    throw th;
                                }
                            }
                            protoOutputStream.flush();
                            return;
                        }
                    }
                    AlarmManagerService.this.dumpImpl(new IndentingPrintWriter(printWriter, "  "));
                }
            }

            public final int getConfigVersion() {
                int i;
                getConfigVersion_enforcePermission();
                Constants constants = AlarmManagerService.this.mConstants;
                synchronized (AlarmManagerService.this.mLock) {
                    i = constants.mVersion;
                }
                return i;
            }

            public final AlarmManager.AlarmClockInfo getNextAlarmClock(int i) {
                AlarmManager.AlarmClockInfo alarmClockInfo;
                int handleIncomingUser = AlarmManagerService.this.mActivityManagerInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 0, "getNextAlarmClock", (String) null);
                AlarmManagerService alarmManagerService = AlarmManagerService.this;
                synchronized (alarmManagerService.mLock) {
                    alarmClockInfo = (AlarmManager.AlarmClockInfo) alarmManagerService.mNextAlarmClockForUser.get(handleIncomingUser);
                }
                return alarmClockInfo;
            }

            public final List getNextAlarmClocks(int i) {
                ArrayList arrayList;
                int callingUid = Binder.getCallingUid();
                AlarmManagerService alarmManagerService = AlarmManagerService.this;
                if (callingUid != alarmManagerService.mSystemUiUid) {
                    String str = "Permission Denial : getNextAlarmClocks from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid();
                    Slog.w("AlarmManager", str);
                    throw new SecurityException(str);
                }
                int handleIncomingUser = alarmManagerService.mActivityManagerInternal.handleIncomingUser(Binder.getCallingPid(), Binder.getCallingUid(), i, false, 0, "getNextAlarmClock", (String) null);
                AlarmManagerService alarmManagerService2 = AlarmManagerService.this;
                synchronized (alarmManagerService2.mLock) {
                    try {
                        arrayList = new ArrayList();
                        LazyAlarmStore lazyAlarmStore = alarmManagerService2.mAlarmStore;
                        lazyAlarmStore.getClass();
                        ArrayList arrayList2 = new ArrayList(lazyAlarmStore.mAlarms);
                        Collections.reverse(arrayList2);
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            Alarm alarm = (Alarm) it.next();
                            if (alarm.alarmClock != null && UserHandle.getUserId(alarm.uid) == handleIncomingUser) {
                                arrayList.add(alarm.alarmClock);
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return arrayList;
            }

            public final long getNextWakeFromIdleTime() {
                long j;
                AlarmManagerService alarmManagerService = AlarmManagerService.this;
                synchronized (alarmManagerService.mLock) {
                    Alarm alarm = alarmManagerService.mNextWakeFromIdle;
                    j = alarm != null ? alarm.mWhenElapsed : Long.MAX_VALUE;
                }
                return j;
            }

            public final boolean hasScheduleExactAlarm(String str, int i) {
                AlarmManagerService.this.mInjector.getClass();
                int callingUid = Binder.getCallingUid();
                if (UserHandle.getUserId(callingUid) != i) {
                    AlarmManagerService.this.getContext().enforceCallingOrSelfPermission("android.permission.INTERACT_ACROSS_USERS_FULL", "hasScheduleExactAlarm");
                }
                int packageUid = AlarmManagerService.this.mPackageManagerInternal.getPackageUid(str, 0L, i);
                if (callingUid != packageUid && !UserHandle.isCore(callingUid)) {
                    throw new SecurityException(AccessibilityManagerService$$ExternalSyntheticOutline0.m(callingUid, "Uid ", " cannot query hasScheduleExactAlarm for package ", str));
                }
                if (packageUid > 0) {
                    return AlarmManagerService.this.hasScheduleExactAlarmInternal(packageUid, str);
                }
                return false;
            }

            public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
                AlarmManagerService.this.new ShellCmd().exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
            }

            public final void remove(PendingIntent pendingIntent, IAlarmListener iAlarmListener) {
                if (pendingIntent == null && iAlarmListener == null) {
                    Slog.w("AlarmManager", "remove() with no intent or listener");
                    return;
                }
                synchronized (AlarmManagerService.this.mLock) {
                    AlarmManagerService.this.removeLocked(pendingIntent, iAlarmListener, 1);
                }
            }

            public final void removeAll(String str) {
                AlarmManagerService.this.mInjector.getClass();
                int callingUid = Binder.getCallingUid();
                if (callingUid == 1000) {
                    Slog.wtfStack("AlarmManager", "Attempt to remove all alarms from the system uid package: " + str);
                } else {
                    if (callingUid != AlarmManagerService.this.mPackageManagerInternal.getPackageUid(str, 0L, UserHandle.getUserId(callingUid))) {
                        throw new SecurityException(SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(callingUid, "Package ", str, " does not belong to the calling uid "));
                    }
                    synchronized (AlarmManagerService.this.mLock) {
                        AlarmManagerService.this.removeAlarmsInternalLocked(1, new AlarmManagerService$$ExternalSyntheticLambda11(callingUid, 1, str));
                    }
                }
            }

            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void set(java.lang.String r23, int r24, long r25, long r27, long r29, int r31, android.app.PendingIntent r32, android.app.IAlarmListener r33, java.lang.String r34, android.os.WorkSource r35, android.app.AlarmManager.AlarmClockInfo r36) {
                /*
                    Method dump skipped, instructions count: 704
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.AnonymousClass4.set(java.lang.String, int, long, long, long, int, android.app.PendingIntent, android.app.IAlarmListener, java.lang.String, android.os.WorkSource, android.app.AlarmManager$AlarmClockInfo):void");
            }

            public final void setAutoPowerUp(String str) {
                long j = AlarmManagerService.this.mInjector.mNativeData;
                if (j != 0) {
                    AlarmManagerService.setBootAlarm(j, str);
                }
            }

            public final boolean setTime(long j) {
                setTime_enforcePermission();
                if (IAlarmManager.Stub.getCallingUid() >= 10000 && IAlarmManager.Stub.getCallingUid() <= 19999) {
                    try {
                        IDateTimePolicy asInterface = IDateTimePolicy.Stub.asInterface(ServiceManager.getService("date_time_policy"));
                        if (asInterface != null && !asInterface.isDateTimeChangeEnabled(new ContextInfo(IAlarmManager.Stub.getCallingUid()))) {
                            Slog.w("AlarmManager", "Not setting time since EDM doesn't allow date & time change.");
                            return false;
                        }
                    } catch (RemoteException unused) {
                    }
                }
                AlarmManagerService alarmManagerService = AlarmManagerService.this;
                synchronized (alarmManagerService.mLock) {
                    alarmManagerService.mInjector.getClass();
                    SystemClockTime.setTimeAndConfidence(100, "AlarmManager.setTime() called", j);
                }
                return true;
            }

            public final void setTimeZone(String str) {
                setTimeZone_enforcePermission();
                if (IAlarmManager.Stub.getCallingUid() >= 10000 && IAlarmManager.Stub.getCallingUid() <= 19999) {
                    try {
                        IDateTimePolicy asInterface = IDateTimePolicy.Stub.asInterface(ServiceManager.getService("date_time_policy"));
                        if (asInterface != null) {
                            if (!asInterface.isDateTimeChangeEnabled(new ContextInfo(IAlarmManager.Stub.getCallingUid()))) {
                                return;
                            }
                        }
                    } catch (RemoteException unused) {
                    }
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    AlarmManagerService.this.setTimeZoneImpl(100, str, "AlarmManager.setTimeZone() called");
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            }
        };
        this.mForceAppStandbyListener = new AppStateTrackerImpl.Listener() { // from class: com.android.server.alarm.AlarmManagerService.7
            @Override // com.android.server.AppStateTrackerImpl.Listener
            public final void handleUidCachedChanged(int i, boolean z) {
                long j;
                if (!AlarmManagerService.this.mUseFrozenStateToDropListenerAlarms && CompatChanges.isChangeEnabled(265195908L, i)) {
                    synchronized (AlarmManagerService.this.mLock) {
                        j = AlarmManagerService.this.mConstants.CACHED_LISTENER_REMOVAL_DELAY;
                    }
                    Integer valueOf = Integer.valueOf(i);
                    if (!z || AlarmManagerService.this.mHandler.hasEqualMessages(15, valueOf)) {
                        AlarmManagerService.this.mHandler.removeEqualMessages(15, valueOf);
                    } else {
                        AlarmHandler alarmHandler = AlarmManagerService.this.mHandler;
                        alarmHandler.sendMessageDelayed(alarmHandler.obtainMessage(15, valueOf), j);
                    }
                }
            }

            @Override // com.android.server.AppStateTrackerImpl.Listener
            public final void removeAlarmsForUid(int i) {
                synchronized (AlarmManagerService.this.mLock) {
                    AlarmManagerService alarmManagerService = AlarmManagerService.this;
                    alarmManagerService.getClass();
                    if (i != 1000) {
                        alarmManagerService.removeAlarmsInternalLocked(0, new AlarmManagerService$$ExternalSyntheticLambda11(i, 2, alarmManagerService));
                    }
                }
            }

            @Override // com.android.server.AppStateTrackerImpl.Listener
            public final void unblockAlarmsForUid(int i) {
                synchronized (AlarmManagerService.this.mLock) {
                    AlarmManagerService.this.sendPendingBackgroundAlarmsLocked(i, null);
                }
            }

            @Override // com.android.server.AppStateTrackerImpl.Listener
            public final void unblockAlarmsForUidPackage(int i, String str) {
                synchronized (AlarmManagerService.this.mLock) {
                    AlarmManagerService.this.sendPendingBackgroundAlarmsLocked(i, str);
                }
            }

            @Override // com.android.server.AppStateTrackerImpl.Listener
            public final void unblockAllUnrestrictedAlarms() {
                synchronized (AlarmManagerService.this.mLock) {
                    AlarmManagerService alarmManagerService = AlarmManagerService.this;
                    alarmManagerService.getClass();
                    ArrayList arrayList = new ArrayList();
                    AlarmManagerService.findAllUnrestrictedPendingBackgroundAlarmsLockedInner(alarmManagerService.mPendingBackgroundAlarms, arrayList, new AlarmManagerService$$ExternalSyntheticLambda6(1, alarmManagerService));
                    if (arrayList.size() > 0) {
                        alarmManagerService.mInjector.getClass();
                        alarmManagerService.deliverPendingBackgroundAlarmsLocked(SystemClock.elapsedRealtime(), arrayList);
                    }
                }
            }

            @Override // com.android.server.AppStateTrackerImpl.Listener
            public final void updateAlarmsForUid(int i) {
                boolean adjustDeliveryTimeBasedOnBatterySaver;
                synchronized (AlarmManagerService.this.mLock) {
                    try {
                        LazyAlarmStore lazyAlarmStore = AlarmManagerService.this.mAlarmStore;
                        Iterator it = lazyAlarmStore.mAlarms.iterator();
                        boolean z = false;
                        while (it.hasNext()) {
                            Alarm alarm = (Alarm) it.next();
                            if (alarm.creatorUid != i) {
                                adjustDeliveryTimeBasedOnBatterySaver = false;
                            } else {
                                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                                adjustDeliveryTimeBasedOnBatterySaver = AlarmManagerService.this.adjustDeliveryTimeBasedOnBatterySaver(alarm);
                            }
                            z |= adjustDeliveryTimeBasedOnBatterySaver;
                        }
                        if (z) {
                            Collections.sort(lazyAlarmStore.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                        }
                        if (z) {
                            AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }

            @Override // com.android.server.AppStateTrackerImpl.Listener
            public final void updateAllAlarms() {
                synchronized (AlarmManagerService.this.mLock) {
                    try {
                        LazyAlarmStore lazyAlarmStore = AlarmManagerService.this.mAlarmStore;
                        Iterator it = lazyAlarmStore.mAlarms.iterator();
                        boolean z = false;
                        while (it.hasNext()) {
                            Alarm alarm = (Alarm) it.next();
                            Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                            z |= AlarmManagerService.this.adjustDeliveryTimeBasedOnBatterySaver(alarm);
                        }
                        if (z) {
                            Collections.sort(lazyAlarmStore.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                        }
                        if (z) {
                            AlarmManagerService.this.rescheduleKernelAlarmsLocked();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };
        this.mSendCount = 0;
        this.mSendFinishCount = 0;
        this.mListenerCount = 0;
        this.mListenerFinishCount = 0;
        this.mInjector = injector;
        this.mSamsungAlarmManagerService = new SamsungAlarmManagerService(context);
        int i = AppSyncInfo.$r8$clinit;
        this.mAppSync = ActivationMonitor.CHINA_COUNTRY_CODE.equalsIgnoreCase(SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY)) ? new AppSyncInfo(context) : new DummyAppSync();
    }

    public static long addClampPositive(long j, long j2) {
        long j3 = j + j2;
        return j3 >= 0 ? j3 : (j < 0 || j2 < 0) ? 0L : Long.MAX_VALUE;
    }

    public static void calculateDeliveryPriorities(ArrayList arrayList) {
        int size = arrayList.size();
        ArraySet arraySet = new ArraySet(4);
        for (int i = 0; i < size; i++) {
            Alarm alarm = (Alarm) arrayList.get(i);
            if (alarm.wakeup) {
                arraySet.add(UserPackage.of(UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage));
            }
        }
        for (int i2 = 0; i2 < size; i2++) {
            Alarm alarm2 = (Alarm) arrayList.get(i2);
            int i3 = alarm2.creatorUid;
            String str = alarm2.sourcePackage;
            if (i3 == 1000 && "android".equals(str)) {
                alarm2.priorityClass = 0;
            } else if (arraySet.contains(UserPackage.of(UserHandle.getUserId(alarm2.creatorUid), str))) {
                alarm2.priorityClass = 1;
            } else {
                alarm2.priorityClass = 2;
            }
        }
    }

    private static native void close(long j);

    public static final void dumpAlarmList(IndentingPrintWriter indentingPrintWriter, ArrayList arrayList, long j, SimpleDateFormat simpleDateFormat) {
        int size = arrayList.size();
        for (int i = size - 1; i >= 0; i--) {
            Alarm alarm = (Alarm) arrayList.get(i);
            indentingPrintWriter.print(Alarm.typeToString(alarm.type));
            indentingPrintWriter.print(" #");
            indentingPrintWriter.print(size - i);
            indentingPrintWriter.print(": ");
            indentingPrintWriter.println(alarm);
            indentingPrintWriter.increaseIndent();
            alarm.dump(indentingPrintWriter, j, simpleDateFormat);
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static void findAllUnrestrictedPendingBackgroundAlarmsLockedInner(SparseArray sparseArray, ArrayList arrayList, Predicate predicate) {
        for (int size = sparseArray.size() - 1; size >= 0; size--) {
            ArrayList arrayList2 = (ArrayList) sparseArray.valueAt(size);
            for (int size2 = arrayList2.size() - 1; size2 >= 0; size2--) {
                Alarm alarm = (Alarm) arrayList2.get(size2);
                if (!predicate.test(alarm)) {
                    arrayList.add(alarm);
                    arrayList2.remove(size2);
                }
            }
            if (arrayList2.size() == 0) {
                sparseArray.removeAt(size);
            }
        }
    }

    private static native long getNextAlarm(long j, int i);

    public static void increment(int i, SparseIntArray sparseIntArray) {
        int indexOfKey = sparseIntArray.indexOfKey(i);
        if (indexOfKey >= 0) {
            sparseIntArray.setValueAt(indexOfKey, sparseIntArray.valueAt(indexOfKey) + 1);
        } else {
            sparseIntArray.put(i, 1);
        }
    }

    private static native long init();

    public static boolean isAllowedWhileIdleRestricted(Alarm alarm) {
        return (alarm.flags & 36) != 0;
    }

    public static boolean isExactAlarmChangeEnabled(int i, String str) {
        return CompatChanges.isChangeEnabled(171306433L, str, UserHandle.of(i));
    }

    public static boolean isExemptFromAppStandby(Alarm alarm) {
        return (alarm.alarmClock == null && !UserHandle.isCore(alarm.creatorUid) && (alarm.flags & 12) == 0) ? false : true;
    }

    public static boolean isMARsRestricted(Alarm alarm) {
        boolean z;
        PackageManagerInternal packageManagerInternal;
        PendingIntent pendingIntent = alarm.operation;
        if (pendingIntent != null) {
            int creatorUid = pendingIntent.getCreatorUid();
            if (alarm.alarmClock == null && (alarm.windowLength != 0 || alarm.exactAllowReason == -1 || ((packageManagerInternal = pmInternalForMARs) != null && packageManagerInternal.getUidTargetSdkVersion(creatorUid) < 31))) {
                boolean z2 = MARsPolicyManager.MARs_ENABLE;
                MARsPolicyManager mARsPolicyManager = MARsPolicyManager.MARsPolicyManagerHolder.INSTANCE;
                String creatorPackage = alarm.operation.getCreatorPackage();
                int userId = UserHandle.getUserId(creatorUid);
                mARsPolicyManager.getClass();
                synchronized (MARsPolicyManager.MARsLock) {
                    try {
                        MARsPackageInfo mARsPackageInfo = MARsPolicyManager.getMARsPackageInfo(mARsPolicyManager.mMARsTargetPackages, creatorPackage, userId);
                        if (mARsPackageInfo != null) {
                            if (!MARsPolicyManager.isChinaPolicyEnabled() || mARsPackageInfo.isFASEnabled) {
                                boolean z3 = (mARsPackageInfo.optionFlag & 2) != 0;
                                if (!z3) {
                                    boolean z4 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE.matchFreezeState(userId, creatorPackage)) {
                                        z = true;
                                    }
                                }
                            } else {
                                if ((mARsPackageInfo.optionFlag & 8) != 0) {
                                    boolean z5 = FreecessController.IS_MINIMIZE_OLAF_LOCK;
                                    if (FreecessController.FreecessControllerHolder.INSTANCE.matchFreezeState(userId, creatorPackage)) {
                                        z = true;
                                    }
                                }
                                z = false;
                            }
                        }
                        z = false;
                    } finally {
                    }
                }
                if (z) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isRtc(int i) {
        return i == 1 || i == 0;
    }

    public static long maxTriggerTime(long j, long j2, long j3) {
        if (j3 == 0) {
            j3 = j2 - j;
        }
        long addClampPositive = addClampPositive(j2, (long) ((j3 >= 10000 ? j3 : 0L) * 0.75d));
        return j3 == 0 ? Math.min(addClampPositive, addClampPositive(j2, ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS)) : addClampPositive;
    }

    private static native int set(long j, int i, long j2, long j3);

    public static native void setBootAlarm(long j, String str);

    public static native int waitForAlarm(long j);

    public final boolean adjustDeliveryTimeBasedOnBatterySaver(Alarm alarm) {
        int i;
        long j;
        AppWakeupHistory appWakeupHistory;
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (alarm.alarmClock != null) {
            return false;
        }
        PendingIntent pendingIntent = alarm.operation;
        if (pendingIntent != null && (pendingIntent.isActivity() || alarm.operation.isForegroundService())) {
            return false;
        }
        int i2 = alarm.creatorUid;
        if (UserHandle.isCore(i2)) {
            return false;
        }
        AppStateTrackerImpl appStateTrackerImpl = this.mAppStateTracker;
        if (appStateTrackerImpl != null) {
            String str = alarm.sourcePackage;
            if (appStateTrackerImpl.areAlarmsRestrictedByBatterySaver(i2, str)) {
                int i3 = alarm.flags;
                if ((i3 & 8) == 0) {
                    if (isAllowedWhileIdleRestricted(alarm)) {
                        int userId = UserHandle.getUserId(i2);
                        if ((i3 & 4) != 0) {
                            Constants constants = this.mConstants;
                            i = constants.ALLOW_WHILE_IDLE_QUOTA;
                            j = constants.ALLOW_WHILE_IDLE_WINDOW;
                            appWakeupHistory = this.mAllowWhileIdleHistory;
                        } else {
                            Constants constants2 = this.mConstants;
                            i = constants2.ALLOW_WHILE_IDLE_COMPAT_QUOTA;
                            j = constants2.ALLOW_WHILE_IDLE_COMPAT_WINDOW;
                            appWakeupHistory = this.mAllowWhileIdleCompatHistory;
                        }
                        if (appWakeupHistory.getTotalWakeupsInWindow(userId, str) >= i) {
                            elapsedRealtime = appWakeupHistory.getNthLastWakeupForPackage(userId, i, str) + j;
                        }
                    } else if ((i3 & 64) != 0) {
                        long j2 = this.mLastPriorityAlarmDispatch.get(i2, 0L);
                        if (j2 != 0) {
                            elapsedRealtime = this.mConstants.PRIORITY_ALARM_DELAY + j2;
                        }
                    } else {
                        elapsedRealtime += 31536000000L;
                    }
                }
                return alarm.setPolicyElapsed(3, elapsedRealtime);
            }
        }
        return alarm.setPolicyElapsed(3, elapsedRealtime);
    }

    public final boolean adjustDeliveryTimeBasedOnBucketLocked(Alarm alarm) {
        long j;
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (isExemptFromAppStandby(alarm) || this.mAppStandbyParole) {
            return alarm.setPolicyElapsed(1, elapsedRealtime);
        }
        String str = alarm.sourcePackage;
        int userId = UserHandle.getUserId(alarm.creatorUid);
        int appStandbyBucket = this.mUsageStatsManagerInternal.getAppStandbyBucket(userId, str, elapsedRealtime);
        int totalWakeupsInWindow = this.mAppWakeupHistory.getTotalWakeupsInWindow(userId, str);
        if (appStandbyBucket != 45) {
            int quotaForBucketLocked = getQuotaForBucketLocked(appStandbyBucket);
            if (totalWakeupsInWindow >= quotaForBucketLocked) {
                TemporaryQuotaReserve temporaryQuotaReserve = this.mTemporaryQuotaReserve;
                temporaryQuotaReserve.getClass();
                TemporaryQuotaReserve.QuotaInfo quotaInfo = (TemporaryQuotaReserve.QuotaInfo) temporaryQuotaReserve.mQuotaBuffer.get(UserPackage.of(userId, str));
                if (quotaInfo != null && quotaInfo.remainingQuota > 0 && elapsedRealtime <= quotaInfo.expirationTime) {
                    alarm.mUsingReserveQuota = true;
                    return alarm.setPolicyElapsed(1, elapsedRealtime);
                }
                if (quotaForBucketLocked <= 0) {
                    j = 31536000000L;
                } else {
                    elapsedRealtime = this.mAppWakeupHistory.getNthLastWakeupForPackage(userId, quotaForBucketLocked, str);
                    j = this.mConstants.APP_STANDBY_WINDOW;
                }
                return alarm.setPolicyElapsed(1, elapsedRealtime + j);
            }
        } else if (totalWakeupsInWindow > 0) {
            long nthLastWakeupForPackage = this.mAppWakeupHistory.getNthLastWakeupForPackage(userId, this.mConstants.APP_STANDBY_RESTRICTED_QUOTA, str);
            long j2 = elapsedRealtime - nthLastWakeupForPackage;
            long j3 = this.mConstants.APP_STANDBY_RESTRICTED_WINDOW;
            if (j2 < j3) {
                return alarm.setPolicyElapsed(1, nthLastWakeupForPackage + j3);
            }
        }
        alarm.mUsingReserveQuota = false;
        return alarm.setPolicyElapsed(1, elapsedRealtime);
    }

    public final boolean adjustDeliveryTimeBasedOnDeviceIdle(Alarm alarm) {
        int i;
        long j;
        AppWakeupHistory appWakeupHistory;
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        Alarm alarm2 = this.mPendingIdleUntil;
        if (alarm2 == null || alarm2 == alarm) {
            return alarm.setPolicyElapsed(2, elapsedRealtime);
        }
        if ((alarm.flags & 10) == 0) {
            boolean isAllowedWhileIdleRestricted = isAllowedWhileIdleRestricted(alarm);
            int i2 = alarm.flags;
            int i3 = alarm.creatorUid;
            if (isAllowedWhileIdleRestricted) {
                int userId = UserHandle.getUserId(i3);
                if ((i2 & 4) != 0) {
                    Constants constants = this.mConstants;
                    i = constants.ALLOW_WHILE_IDLE_QUOTA;
                    j = constants.ALLOW_WHILE_IDLE_WINDOW;
                    appWakeupHistory = this.mAllowWhileIdleHistory;
                } else {
                    Constants constants2 = this.mConstants;
                    i = constants2.ALLOW_WHILE_IDLE_COMPAT_QUOTA;
                    j = constants2.ALLOW_WHILE_IDLE_COMPAT_WINDOW;
                    appWakeupHistory = this.mAllowWhileIdleCompatHistory;
                }
                String str = alarm.sourcePackage;
                if (appWakeupHistory.getTotalWakeupsInWindow(userId, str) >= i) {
                    elapsedRealtime = Math.min(appWakeupHistory.getNthLastWakeupForPackage(userId, i, str) + j, this.mPendingIdleUntil.mWhenElapsed);
                }
            } else if ((i2 & 64) != 0) {
                long j2 = this.mLastPriorityAlarmDispatch.get(i3, 0L);
                if (j2 != 0) {
                    elapsedRealtime = this.mConstants.PRIORITY_ALARM_DELAY + j2;
                }
                elapsedRealtime = Math.min(elapsedRealtime, this.mPendingIdleUntil.mWhenElapsed);
            } else {
                elapsedRealtime = this.mPendingIdleUntil.mWhenElapsed;
            }
        }
        return alarm.setPolicyElapsed(2, elapsedRealtime);
    }

    public final boolean adjustIdleUntilTime(Alarm alarm) {
        if ((alarm.flags & 16) == 0) {
            return false;
        }
        boolean policyElapsed = alarm.setPolicyElapsed(0, convertToElapsed(alarm.type, alarm.origWhen));
        Alarm alarm2 = this.mNextWakeFromIdle;
        if (alarm2 == null) {
            return policyElapsed;
        }
        long j = alarm2.mWhenElapsed;
        if (alarm.mWhenElapsed < j - this.mConstants.MIN_DEVICE_IDLE_FUZZ) {
            return policyElapsed;
        }
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j2 = j - elapsedRealtime;
        if (j2 <= this.mConstants.MIN_DEVICE_IDLE_FUZZ) {
            alarm.setPolicyElapsed(0, elapsedRealtime);
            return true;
        }
        alarm.setPolicyElapsed(0, j - ThreadLocalRandom.current().nextLong(this.mConstants.MIN_DEVICE_IDLE_FUZZ, Math.min(this.mConstants.MAX_DEVICE_IDLE_FUZZ, j2) + 1));
        return true;
    }

    public final boolean checkAllowNonWakeupDelayLocked(long j) {
        if (this.mConstants.DELAY_NONWAKEUP_ALARMS_WHILE_SCREEN_OFF && !this.mInteractive && this.mLastAlarmDeliveryTime > 0) {
            return (this.mPendingNonWakeupAlarms.size() <= 0 || this.mNextNonWakeupDeliveryTime >= j) && j - this.mLastAlarmDeliveryTime <= currentNonWakeupFuzzLocked(j);
        }
        return false;
    }

    public final long convertToElapsed(int i, long j) {
        if (!isRtc(i)) {
            return j;
        }
        Injector injector = this.mInjector;
        injector.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        injector.getClass();
        return j - (currentTimeMillis - SystemClock.elapsedRealtime());
    }

    public final long currentNonWakeupFuzzLocked(long j) {
        long j2 = j - this.mNonInteractiveStartTime;
        if (j2 < 300000) {
            return 120000L;
        }
        if (j2 < 1800000) {
            return 900000L;
        }
        return ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS;
    }

    public final void decrementAlarmCount(int i) {
        int i2;
        int indexOfKey = this.mAlarmsPerUid.indexOfKey(i);
        if (indexOfKey >= 0) {
            i2 = this.mAlarmsPerUid.valueAt(indexOfKey);
            if (i2 > 1) {
                this.mAlarmsPerUid.setValueAt(indexOfKey, i2 - 1);
            } else {
                this.mAlarmsPerUid.removeAt(indexOfKey);
            }
        } else {
            i2 = 0;
        }
        if (i2 < 1) {
            Slog.wtf("AlarmManager", "Attempt to decrement existing alarm count " + i2 + " by 1 for uid " + i);
        }
    }

    public final void deliverAlarmsLocked(long j, ArrayList arrayList) {
        this.mLastAlarmDeliveryTime = j;
        for (int i = 0; i < arrayList.size(); i++) {
            Alarm alarm = (Alarm) arrayList.get(i);
            boolean z = alarm.wakeup;
            int i2 = alarm.uid;
            String str = alarm.packageName;
            if (z) {
                Trace.traceBegin(131072L, "Dispatch wakeup alarm to " + str);
            } else {
                Trace.traceBegin(131072L, "Dispatch non-wakeup alarm to " + str);
            }
            try {
                this.mActivityManagerInternal.noteAlarmStart(alarm.operation, alarm.workSource, i2, alarm.statsTag);
                this.mDeliveryTracker.deliverLocked(alarm, j);
            } catch (RuntimeException e) {
                Slog.w("AlarmManager", "Failure sending alarm.", e);
            }
            Trace.traceEnd(131072L);
            decrementAlarmCount(i2);
        }
    }

    public final void deliverPendingBackgroundAlarmsLocked(long j, ArrayList arrayList) {
        int i;
        int i2;
        long j2 = j;
        ArrayList arrayList2 = arrayList;
        int size = arrayList.size();
        boolean z = false;
        int i3 = 0;
        while (i3 < size) {
            Alarm alarm = (Alarm) arrayList2.get(i3);
            boolean z2 = alarm.wakeup ? true : z;
            alarm.count = 1;
            long j3 = alarm.repeatInterval;
            if (j3 > 0) {
                long j4 = alarm.mPolicyWhenElapsed[0];
                int i4 = (int) (((j2 - j4) / j3) + 1);
                alarm.count = i4;
                long j5 = i4 * j3;
                long j6 = j4 + j5;
                long maxTriggerTime = maxTriggerTime(j, j6, j3);
                long j7 = alarm.origWhen + j5;
                long j8 = maxTriggerTime - j6;
                PendingIntent pendingIntent = alarm.operation;
                i2 = i3;
                WorkSource workSource = alarm.workSource;
                i = size;
                setImplLocked(alarm.type, alarm.flags, alarm.uid, -1, j7, j6, j8, alarm.repeatInterval, alarm.alarmClock, null, pendingIntent, null, workSource, null, alarm.packageName);
            } else {
                i = size;
                i2 = i3;
            }
            i3 = i2 + 1;
            j2 = j;
            arrayList2 = arrayList;
            z = z2;
            size = i;
        }
        if (!z && checkAllowNonWakeupDelayLocked(j)) {
            if (this.mPendingNonWakeupAlarms.size() == 0) {
                this.mStartCurrentDelayTime = j;
                this.mNextNonWakeupDeliveryTime = ((currentNonWakeupFuzzLocked(j) * 3) / 2) + j;
            }
            this.mPendingNonWakeupAlarms.addAll(arrayList);
            this.mNumDelayedAlarms = arrayList.size() + this.mNumDelayedAlarms;
            return;
        }
        if (this.mPendingNonWakeupAlarms.size() > 0) {
            arrayList.addAll(this.mPendingNonWakeupAlarms);
            long j9 = j - this.mStartCurrentDelayTime;
            this.mTotalDelayTime += j9;
            if (this.mMaxDelayTime < j9) {
                this.mMaxDelayTime = j9;
            }
            this.mPendingNonWakeupAlarms.clear();
        }
        calculateDeliveryPriorities(arrayList);
        Collections.sort(arrayList, this.mAlarmDispatchComparator);
        deliverAlarmsLocked(j, arrayList);
    }

    @NeverCompile
    public final void dumpImpl(IndentingPrintWriter indentingPrintWriter) {
        long j;
        boolean z;
        BroadcastStats broadcastStats;
        long j2;
        synchronized (this.mLock) {
            try {
                indentingPrintWriter.println("Current Alarm Manager state:");
                indentingPrintWriter.increaseIndent();
                this.mConstants.dump(indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Feature Flags:");
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("com.android.server.alarm.use_frozen_state_to_drop_listener_alarms", Boolean.valueOf(this.mUseFrozenStateToDropListenerAlarms));
                indentingPrintWriter.println();
                indentingPrintWriter.print("com.android.server.alarm.start_user_before_scheduled_alarms", Boolean.FALSE);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.println();
                indentingPrintWriter.println("App Standby Parole: " + this.mAppStandbyParole);
                indentingPrintWriter.println();
                AppStateTrackerImpl appStateTrackerImpl = this.mAppStateTracker;
                if (appStateTrackerImpl != null) {
                    appStateTrackerImpl.dump(indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                this.mInjector.getClass();
                long elapsedRealtime = SystemClock.elapsedRealtime();
                long uptimeMillis = SystemClock.uptimeMillis();
                this.mInjector.getClass();
                long currentTimeMillis = System.currentTimeMillis();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                indentingPrintWriter.print("nowRTC=");
                indentingPrintWriter.print(currentTimeMillis);
                indentingPrintWriter.print("=");
                indentingPrintWriter.print(simpleDateFormat.format(new Date(currentTimeMillis)));
                indentingPrintWriter.print(" nowELAPSED=");
                indentingPrintWriter.print(elapsedRealtime);
                indentingPrintWriter.println();
                indentingPrintWriter.print("mLastTimeChangeClockTime=");
                indentingPrintWriter.print(this.mLastTimeChangeClockTime);
                indentingPrintWriter.print("=");
                indentingPrintWriter.println(simpleDateFormat.format(new Date(this.mLastTimeChangeClockTime)));
                indentingPrintWriter.print("mLastTimeChangeRealtime=");
                indentingPrintWriter.println(this.mLastTimeChangeRealtime);
                indentingPrintWriter.print("mLastTickReceived=");
                indentingPrintWriter.println(simpleDateFormat.format(new Date(this.mLastTickReceived)));
                indentingPrintWriter.print("mLastTickSet=");
                indentingPrintWriter.println(simpleDateFormat.format(new Date(this.mLastTickSet)));
                indentingPrintWriter.println();
                indentingPrintWriter.println("Recent TIME_TICK history:");
                indentingPrintWriter.increaseIndent();
                int i = this.mNextTickHistory;
                do {
                    i--;
                    if (i < 0) {
                        i = 9;
                    }
                    long j3 = this.mTickHistory[i];
                    indentingPrintWriter.println(j3 > 0 ? simpleDateFormat.format(new Date(currentTimeMillis - (elapsedRealtime - j3))) : PackageManagerShellCommandDataLoader.STDIN_PATH);
                } while (i != this.mNextTickHistory);
                indentingPrintWriter.decreaseIndent();
                SystemServiceManager systemServiceManager = (SystemServiceManager) LocalServices.getService(SystemServiceManager.class);
                if (systemServiceManager != null) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("RuntimeStarted=");
                    j = currentTimeMillis;
                    indentingPrintWriter.print(simpleDateFormat.format(new Date((currentTimeMillis - elapsedRealtime) + systemServiceManager.mRuntimeStartElapsedTime)));
                    if (systemServiceManager.mRuntimeRestarted) {
                        indentingPrintWriter.print("  (Runtime restarted)");
                    }
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Runtime uptime (elapsed): ");
                    TimeUtils.formatDuration(elapsedRealtime, systemServiceManager.mRuntimeStartElapsedTime, indentingPrintWriter);
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Runtime uptime (uptime): ");
                    TimeUtils.formatDuration(uptimeMillis, systemServiceManager.getRuntimeStartUptime(), indentingPrintWriter);
                    indentingPrintWriter.println();
                } else {
                    j = currentTimeMillis;
                }
                indentingPrintWriter.println();
                if (!this.mInteractive) {
                    indentingPrintWriter.print("Time since non-interactive: ");
                    TimeUtils.formatDuration(elapsedRealtime - this.mNonInteractiveStartTime, indentingPrintWriter);
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.print("Max wakeup delay: ");
                TimeUtils.formatDuration(currentNonWakeupFuzzLocked(elapsedRealtime), indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Time since last dispatch: ");
                TimeUtils.formatDuration(elapsedRealtime - this.mLastAlarmDeliveryTime, indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Next non-wakeup delivery time: ");
                TimeUtils.formatDuration(this.mNextNonWakeupDeliveryTime, elapsedRealtime, indentingPrintWriter);
                indentingPrintWriter.println();
                long j4 = j - elapsedRealtime;
                long j5 = this.mNextWakeup + j4;
                long j6 = this.mNextNonWakeup + j4;
                indentingPrintWriter.print("Next non-wakeup alarm: ");
                TimeUtils.formatDuration(this.mNextNonWakeup, elapsedRealtime, indentingPrintWriter);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.print(this.mNextNonWakeup);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.println(simpleDateFormat.format(new Date(j6)));
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("set at ");
                TimeUtils.formatDuration(this.mNextNonWakeUpSetAt, elapsedRealtime, indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Next wakeup alarm: ");
                TimeUtils.formatDuration(this.mNextWakeup, elapsedRealtime, indentingPrintWriter);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.print(this.mNextWakeup);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.println(simpleDateFormat.format(new Date(j5)));
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("set at ");
                TimeUtils.formatDuration(this.mNextWakeUpSetAt, elapsedRealtime, indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Next kernel non-wakeup alarm: ");
                TimeUtils.formatDuration(m153$$Nest$smgetNextAlarm(3, this.mInjector.mNativeData), indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Next kernel wakeup alarm: ");
                TimeUtils.formatDuration(m153$$Nest$smgetNextAlarm(2, this.mInjector.mNativeData), indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Last wakeup: ");
                TimeUtils.formatDuration(this.mLastWakeup, elapsedRealtime, indentingPrintWriter);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.println(this.mLastWakeup);
                indentingPrintWriter.print("Last trigger: ");
                TimeUtils.formatDuration(this.mLastTrigger, elapsedRealtime, indentingPrintWriter);
                indentingPrintWriter.print(" = ");
                indentingPrintWriter.println(this.mLastTrigger);
                indentingPrintWriter.print("Num time change events: ");
                indentingPrintWriter.println(this.mNumTimeChanged);
                indentingPrintWriter.println();
                indentingPrintWriter.println("App ids requesting SCHEDULE_EXACT_ALARM: " + this.mExactAlarmCandidates);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Last OP_SCHEDULE_EXACT_ALARM: [");
                int i2 = 0;
                for (int i3 = 0; i3 < this.mLastOpScheduleExactAlarm.size(); i3++) {
                    if (i3 > 0) {
                        indentingPrintWriter.print(", ");
                    }
                    UserHandle.formatUid(indentingPrintWriter, this.mLastOpScheduleExactAlarm.keyAt(i3));
                    indentingPrintWriter.print(":" + AppOpsManager.modeToName(this.mLastOpScheduleExactAlarm.valueAt(i3)));
                }
                indentingPrintWriter.println("]");
                indentingPrintWriter.println();
                indentingPrintWriter.println("Next alarm clock information: ");
                indentingPrintWriter.increaseIndent();
                TreeSet treeSet = new TreeSet();
                for (int i4 = 0; i4 < this.mNextAlarmClockForUser.size(); i4++) {
                    treeSet.add(Integer.valueOf(this.mNextAlarmClockForUser.keyAt(i4)));
                }
                for (int i5 = 0; i5 < this.mPendingSendNextAlarmClockChangedForUser.size(); i5++) {
                    treeSet.add(Integer.valueOf(this.mPendingSendNextAlarmClockChangedForUser.keyAt(i5)));
                }
                Iterator it = treeSet.iterator();
                while (it.hasNext()) {
                    int intValue = ((Integer) it.next()).intValue();
                    AlarmManager.AlarmClockInfo alarmClockInfo = (AlarmManager.AlarmClockInfo) this.mNextAlarmClockForUser.get(intValue);
                    long triggerTime = alarmClockInfo != null ? alarmClockInfo.getTriggerTime() : 0L;
                    boolean z2 = this.mPendingSendNextAlarmClockChangedForUser.get(intValue);
                    indentingPrintWriter.print("user:");
                    indentingPrintWriter.print(intValue);
                    indentingPrintWriter.print(" pendingSend:");
                    indentingPrintWriter.print(z2);
                    indentingPrintWriter.print(" time:");
                    indentingPrintWriter.print(triggerTime);
                    if (triggerTime > 0) {
                        indentingPrintWriter.print(" = ");
                        indentingPrintWriter.print(simpleDateFormat.format(new Date(triggerTime)));
                        indentingPrintWriter.print(" = ");
                        j2 = j;
                        TimeUtils.formatDuration(triggerTime, j2, indentingPrintWriter);
                    } else {
                        j2 = j;
                    }
                    indentingPrintWriter.println();
                    j = j2;
                }
                indentingPrintWriter.decreaseIndent();
                if (this.mAlarmStore.mAlarms.size() > 0) {
                    indentingPrintWriter.println();
                    this.mAlarmStore.dump(indentingPrintWriter, elapsedRealtime, simpleDateFormat);
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Pending user blocked background alarms: ");
                indentingPrintWriter.increaseIndent();
                int i6 = 0;
                boolean z3 = false;
                while (true) {
                    z = true;
                    if (i6 >= this.mPendingBackgroundAlarms.size()) {
                        break;
                    }
                    ArrayList arrayList = (ArrayList) this.mPendingBackgroundAlarms.valueAt(i6);
                    if (arrayList != null && arrayList.size() > 0) {
                        dumpAlarmList(indentingPrintWriter, arrayList, elapsedRealtime, simpleDateFormat);
                        z3 = true;
                    }
                    i6++;
                }
                if (!z3) {
                    indentingPrintWriter.println("none");
                }
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Pending alarms per uid: [");
                for (int i7 = 0; i7 < this.mAlarmsPerUid.size(); i7++) {
                    if (i7 > 0) {
                        indentingPrintWriter.print(", ");
                    }
                    UserHandle.formatUid(indentingPrintWriter, this.mAlarmsPerUid.keyAt(i7));
                    indentingPrintWriter.print(":");
                    indentingPrintWriter.print(this.mAlarmsPerUid.valueAt(i7));
                }
                indentingPrintWriter.println("]");
                indentingPrintWriter.println();
                indentingPrintWriter.println("App Alarm history:");
                this.mAppWakeupHistory.dump(indentingPrintWriter, elapsedRealtime);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Temporary Quota Reserves:");
                this.mTemporaryQuotaReserve.dump(indentingPrintWriter, elapsedRealtime);
                if (this.mPendingIdleUntil != null) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.println("Idle mode state:");
                    indentingPrintWriter.increaseIndent();
                    indentingPrintWriter.print("Idling until: ");
                    Alarm alarm = this.mPendingIdleUntil;
                    if (alarm != null) {
                        indentingPrintWriter.println(alarm);
                        this.mPendingIdleUntil.dump(indentingPrintWriter, elapsedRealtime, simpleDateFormat);
                    } else {
                        indentingPrintWriter.println("null");
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                if (this.mNextWakeFromIdle != null) {
                    indentingPrintWriter.println();
                    indentingPrintWriter.print("Next wake from idle: ");
                    indentingPrintWriter.println(this.mNextWakeFromIdle);
                    indentingPrintWriter.increaseIndent();
                    this.mNextWakeFromIdle.dump(indentingPrintWriter, elapsedRealtime, simpleDateFormat);
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.print("Past-due non-wakeup alarms: ");
                if (this.mPendingNonWakeupAlarms.size() > 0) {
                    indentingPrintWriter.println(this.mPendingNonWakeupAlarms.size());
                    indentingPrintWriter.increaseIndent();
                    dumpAlarmList(indentingPrintWriter, this.mPendingNonWakeupAlarms, elapsedRealtime, simpleDateFormat);
                    indentingPrintWriter.decreaseIndent();
                } else {
                    indentingPrintWriter.println("(none)");
                }
                indentingPrintWriter.increaseIndent();
                indentingPrintWriter.print("Number of delayed alarms: ");
                indentingPrintWriter.print(this.mNumDelayedAlarms);
                indentingPrintWriter.print(", total delay time: ");
                TimeUtils.formatDuration(this.mTotalDelayTime, indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.print("Max delay time: ");
                TimeUtils.formatDuration(this.mMaxDelayTime, indentingPrintWriter);
                indentingPrintWriter.print(", max non-interactive time: ");
                TimeUtils.formatDuration(this.mNonInteractiveTime, indentingPrintWriter);
                indentingPrintWriter.println();
                indentingPrintWriter.decreaseIndent();
                indentingPrintWriter.println();
                indentingPrintWriter.print("Broadcast ref count: ");
                indentingPrintWriter.println(this.mBroadcastRefCount);
                indentingPrintWriter.print("PendingIntent send count: ");
                indentingPrintWriter.println(this.mSendCount);
                indentingPrintWriter.print("PendingIntent finish count: ");
                indentingPrintWriter.println(this.mSendFinishCount);
                indentingPrintWriter.print("Listener send count: ");
                indentingPrintWriter.println(this.mListenerCount);
                indentingPrintWriter.print("Listener finish count: ");
                indentingPrintWriter.println(this.mListenerFinishCount);
                indentingPrintWriter.println();
                if (this.mInFlight.size() > 0) {
                    indentingPrintWriter.println("Outstanding deliveries:");
                    indentingPrintWriter.increaseIndent();
                    for (int i8 = 0; i8 < this.mInFlight.size(); i8++) {
                        indentingPrintWriter.print("#");
                        indentingPrintWriter.print(i8);
                        indentingPrintWriter.print(": ");
                        indentingPrintWriter.println(this.mInFlight.get(i8));
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println("Allow while idle history:");
                this.mAllowWhileIdleHistory.dump(indentingPrintWriter, elapsedRealtime);
                indentingPrintWriter.println();
                indentingPrintWriter.println("Allow while idle compat history:");
                this.mAllowWhileIdleCompatHistory.dump(indentingPrintWriter, elapsedRealtime);
                indentingPrintWriter.println();
                if (this.mLastPriorityAlarmDispatch.size() > 0) {
                    indentingPrintWriter.println("Last priority alarm dispatches:");
                    indentingPrintWriter.increaseIndent();
                    for (int i9 = 0; i9 < this.mLastPriorityAlarmDispatch.size(); i9++) {
                        indentingPrintWriter.print("UID: ");
                        UserHandle.formatUid(indentingPrintWriter, this.mLastPriorityAlarmDispatch.keyAt(i9));
                        indentingPrintWriter.print(": ");
                        TimeUtils.formatDuration(this.mLastPriorityAlarmDispatch.valueAt(i9), elapsedRealtime, indentingPrintWriter);
                        indentingPrintWriter.println();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                if (this.mRemovalHistory.size() > 0) {
                    indentingPrintWriter.println("Removal history:");
                    indentingPrintWriter.increaseIndent();
                    for (int i10 = 0; i10 < this.mRemovalHistory.size(); i10++) {
                        UserHandle.formatUid(indentingPrintWriter, this.mRemovalHistory.keyAt(i10));
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        RemovedAlarm[] removedAlarmArr = (RemovedAlarm[]) ((RingBuffer) this.mRemovalHistory.valueAt(i10)).toArray();
                        for (int length = removedAlarmArr.length - 1; length >= 0; length += -1) {
                            indentingPrintWriter.print("#" + (removedAlarmArr.length - length) + ": ");
                            removedAlarmArr[length].dump(indentingPrintWriter, elapsedRealtime, simpleDateFormat);
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (this.mRemoveFailedHistory.size() > 0) {
                    indentingPrintWriter.println("Remove Failed history: ");
                    indentingPrintWriter.increaseIndent();
                    for (int i11 = 0; i11 < this.mRemoveFailedHistory.size(); i11++) {
                        UserHandle.formatUid(indentingPrintWriter, this.mRemoveFailedHistory.keyAt(i11));
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        RemoveFailedRequest[] removeFailedRequestArr = (RemoveFailedRequest[]) ((RingBuffer) this.mRemoveFailedHistory.valueAt(i11)).toArray();
                        if (removeFailedRequestArr.length > 0) {
                            RemoveFailedRequest removeFailedRequest = removeFailedRequestArr[0];
                            throw null;
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (this.mAdditionHistory.size() > 0) {
                    indentingPrintWriter.println("Addition history: ");
                    indentingPrintWriter.increaseIndent();
                    for (int i12 = 0; i12 < this.mAdditionHistory.size(); i12++) {
                        UserHandle.formatUid(indentingPrintWriter, this.mAdditionHistory.keyAt(i12));
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        for (AddedAlarm addedAlarm : (AddedAlarm[]) ((RingBuffer) this.mAdditionHistory.valueAt(i12)).toArray()) {
                            addedAlarm.dump(indentingPrintWriter, simpleDateFormat);
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (this.mDeliveryHistory.size() > 0) {
                    indentingPrintWriter.println("Delivery history: ");
                    indentingPrintWriter.increaseIndent();
                    for (int i13 = 0; i13 < this.mDeliveryHistory.size(); i13++) {
                        UserHandle.formatUid(indentingPrintWriter, this.mDeliveryHistory.keyAt(i13));
                        indentingPrintWriter.println(":");
                        indentingPrintWriter.increaseIndent();
                        for (DeliveredAlarm deliveredAlarm : (DeliveredAlarm[]) ((RingBuffer) this.mDeliveryHistory.valueAt(i13)).toArray()) {
                            deliveredAlarm.dump(indentingPrintWriter, simpleDateFormat);
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (this.mExpirationHistory.size() > 0) {
                    indentingPrintWriter.println("Expiration history: ");
                    indentingPrintWriter.increaseIndent();
                    for (ExpiredRecord expiredRecord : (ExpiredRecord[]) this.mExpirationHistory.toArray()) {
                        expiredRecord.dump(indentingPrintWriter, simpleDateFormat);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (this.mInvalidExpirationHistory.size() > 0) {
                    indentingPrintWriter.print("Invalid Expiration history: [");
                    ExpiredRecord[] expiredRecordArr = (ExpiredRecord[]) this.mInvalidExpirationHistory.toArray();
                    for (int i14 = 0; i14 < this.mInvalidExpirationHistory.size(); i14++) {
                        ExpiredRecord expiredRecord2 = expiredRecordArr[i14];
                        if (i14 > 0) {
                            indentingPrintWriter.print(", ");
                        }
                        indentingPrintWriter.print(expiredRecord2.mWhenExpiredElapsed);
                    }
                    indentingPrintWriter.println("]");
                    indentingPrintWriter.println();
                }
                if (this.mWakeupAlarmHistory.size() > 0) {
                    indentingPrintWriter.println("Wakeup Alarm history(screen off): ");
                    indentingPrintWriter.increaseIndent();
                    for (WakeupRecord wakeupRecord : (WakeupRecord[]) this.mWakeupAlarmHistory.toArray()) {
                        wakeupRecord.dump(indentingPrintWriter, simpleDateFormat);
                    }
                    indentingPrintWriter.decreaseIndent();
                    indentingPrintWriter.println();
                }
                if (this.mLog.dump(indentingPrintWriter, "Recent problems:")) {
                    indentingPrintWriter.println();
                }
                char c = '\n';
                FilterStats[] filterStatsArr = new FilterStats[10];
                AnonymousClass5 anonymousClass5 = new AnonymousClass5(this, 0);
                int i15 = 0;
                int i16 = 0;
                while (i15 < this.mBroadcastStats.size()) {
                    ArrayMap arrayMap = (ArrayMap) this.mBroadcastStats.valueAt(i15);
                    int i17 = i2;
                    while (i17 < arrayMap.size()) {
                        BroadcastStats broadcastStats2 = (BroadcastStats) arrayMap.valueAt(i17);
                        int i18 = i2;
                        while (i18 < broadcastStats2.filterStats.size()) {
                            FilterStats filterStats = (FilterStats) broadcastStats2.filterStats.valueAt(i18);
                            if (i16 > 0) {
                                i2 = Arrays.binarySearch(filterStatsArr, i2, i16, filterStats, anonymousClass5);
                            }
                            if (i2 < 0) {
                                i2 = (-i2) - 1;
                            }
                            ArrayMap arrayMap2 = arrayMap;
                            if (i2 < 10) {
                                int i19 = 9 - i2;
                                if (i19 > 0) {
                                    broadcastStats = broadcastStats2;
                                    System.arraycopy(filterStatsArr, i2, filterStatsArr, i2 + 1, i19);
                                } else {
                                    broadcastStats = broadcastStats2;
                                }
                                filterStatsArr[i2] = filterStats;
                                if (i16 < 10) {
                                    i16++;
                                }
                            } else {
                                broadcastStats = broadcastStats2;
                            }
                            i18++;
                            arrayMap = arrayMap2;
                            broadcastStats2 = broadcastStats;
                            i2 = 0;
                        }
                        i17++;
                        c = '\n';
                        z = true;
                        i2 = 0;
                    }
                    i15++;
                    i2 = 0;
                }
                if (i16 > 0) {
                    indentingPrintWriter.println("Top Alarms:");
                    indentingPrintWriter.increaseIndent();
                    for (int i20 = 0; i20 < i16; i20++) {
                        FilterStats filterStats2 = filterStatsArr[i20];
                        if (filterStats2.nesting > 0) {
                            indentingPrintWriter.print("*ACTIVE* ");
                        }
                        TimeUtils.formatDuration(filterStats2.aggregateTime, indentingPrintWriter);
                        indentingPrintWriter.print(" running, ");
                        indentingPrintWriter.print(filterStats2.numWakeup);
                        indentingPrintWriter.print(" wakeups, ");
                        indentingPrintWriter.print(filterStats2.count);
                        indentingPrintWriter.print(" alarms: ");
                        UserHandle.formatUid(indentingPrintWriter, filterStats2.mBroadcastStats.mUid);
                        indentingPrintWriter.print(":");
                        indentingPrintWriter.print(filterStats2.mBroadcastStats.mPackageName);
                        indentingPrintWriter.println();
                        indentingPrintWriter.increaseIndent();
                        indentingPrintWriter.print(filterStats2.mTag);
                        indentingPrintWriter.println();
                        indentingPrintWriter.decreaseIndent();
                    }
                    indentingPrintWriter.decreaseIndent();
                }
                indentingPrintWriter.println();
                indentingPrintWriter.println("Alarm Stats:");
                ArrayList arrayList2 = new ArrayList();
                for (int i21 = 0; i21 < this.mBroadcastStats.size(); i21++) {
                    ArrayMap arrayMap3 = (ArrayMap) this.mBroadcastStats.valueAt(i21);
                    for (int i22 = 0; i22 < arrayMap3.size(); i22++) {
                        BroadcastStats broadcastStats3 = (BroadcastStats) arrayMap3.valueAt(i22);
                        if (broadcastStats3.nesting > 0) {
                            indentingPrintWriter.print("*ACTIVE* ");
                        }
                        UserHandle.formatUid(indentingPrintWriter, broadcastStats3.mUid);
                        indentingPrintWriter.print(":");
                        indentingPrintWriter.print(broadcastStats3.mPackageName);
                        indentingPrintWriter.print(" ");
                        TimeUtils.formatDuration(broadcastStats3.aggregateTime, indentingPrintWriter);
                        indentingPrintWriter.print(" running, ");
                        indentingPrintWriter.print(broadcastStats3.numWakeup);
                        indentingPrintWriter.println(" wakeups:");
                        arrayList2.clear();
                        for (int i23 = 0; i23 < broadcastStats3.filterStats.size(); i23++) {
                            arrayList2.add((FilterStats) broadcastStats3.filterStats.valueAt(i23));
                        }
                        Collections.sort(arrayList2, anonymousClass5);
                        indentingPrintWriter.increaseIndent();
                        for (int i24 = 0; i24 < arrayList2.size(); i24++) {
                            FilterStats filterStats3 = (FilterStats) arrayList2.get(i24);
                            if (filterStats3.nesting > 0) {
                                indentingPrintWriter.print("*ACTIVE* ");
                            }
                            TimeUtils.formatDuration(filterStats3.aggregateTime, indentingPrintWriter);
                            indentingPrintWriter.print(" ");
                            indentingPrintWriter.print(filterStats3.numWakeup);
                            indentingPrintWriter.print(" wakes ");
                            indentingPrintWriter.print(filterStats3.count);
                            indentingPrintWriter.print(" alarms, last ");
                            TimeUtils.formatDuration(filterStats3.lastTime, elapsedRealtime, indentingPrintWriter);
                            indentingPrintWriter.println(":");
                            indentingPrintWriter.increaseIndent();
                            indentingPrintWriter.print(filterStats3.mTag);
                            indentingPrintWriter.println();
                            indentingPrintWriter.decreaseIndent();
                        }
                        indentingPrintWriter.decreaseIndent();
                    }
                }
                indentingPrintWriter.println();
                this.mStatLogger.dump(indentingPrintWriter);
                GmsAlarmManager gmsAlarmManager = this.mGmsManager;
                if (gmsAlarmManager != null) {
                    gmsAlarmManager.doDump(indentingPrintWriter);
                }
                AppSyncWrapper appSyncWrapper = this.mAppSync;
                if (appSyncWrapper != null) {
                    appSyncWrapper.dump(indentingPrintWriter);
                } else {
                    indentingPrintWriter.println("<AppSync Disabled>");
                    indentingPrintWriter.println();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void finalize() {
        try {
            close(this.mInjector.mNativeData);
        } finally {
            super.finalize();
        }
    }

    public int getQuotaForBucketLocked(int i) {
        return this.mConstants.APP_STANDBY_QUOTAS[i <= 10 ? (char) 0 : i <= 20 ? (char) 1 : i <= 30 ? (char) 2 : i < 50 ? (char) 3 : (char) 4];
    }

    public final BroadcastStats getStatsLocked(int i, String str) {
        ArrayMap arrayMap = (ArrayMap) this.mBroadcastStats.get(i);
        if (arrayMap == null) {
            arrayMap = new ArrayMap();
            this.mBroadcastStats.put(i, arrayMap);
        }
        BroadcastStats broadcastStats = (BroadcastStats) arrayMap.get(str);
        if (broadcastStats != null) {
            return broadcastStats;
        }
        BroadcastStats broadcastStats2 = new BroadcastStats(i, str);
        arrayMap.put(str, broadcastStats2);
        return broadcastStats2;
    }

    public final boolean hasScheduleExactAlarmInternal(int i, String str) {
        int checkOpNoThrow;
        long time = this.mStatLogger.getTime();
        boolean z = false;
        if (this.mExactAlarmCandidates.contains(Integer.valueOf(UserHandle.getAppId(i))) && isExactAlarmChangeEnabled(UserHandle.getUserId(i), str) && (!CompatChanges.isChangeEnabled(226439802L, str, UserHandle.of(UserHandle.getUserId(i))) ? (checkOpNoThrow = this.mAppOps.checkOpNoThrow(107, i, str)) == 3 || checkOpNoThrow == 0 : PermissionChecker.checkPermissionForPreflight(getContext(), "android.permission.SCHEDULE_EXACT_ALARM", -1, i, str) == 0)) {
            z = true;
        }
        this.mStatLogger.logDurationStat(1, time);
        return z;
    }

    public final boolean hasUseExactAlarmInternal(int i, String str) {
        return CompatChanges.isChangeEnabled(218533173L, str, UserHandle.of(UserHandle.getUserId(i))) && PermissionChecker.checkPermissionForPreflight(getContext(), "android.permission.USE_EXACT_ALARM", -1, i, str) == 0;
    }

    public final void interactiveStateChangedLocked(boolean z) {
        if (this.mInteractive != z) {
            this.mInteractive = z;
            this.mInjector.getClass();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (!z) {
                this.mNonInteractiveStartTime = elapsedRealtime;
                return;
            }
            if (this.mPendingNonWakeupAlarms.size() > 0) {
                long j = elapsedRealtime - this.mStartCurrentDelayTime;
                this.mTotalDelayTime += j;
                if (this.mMaxDelayTime < j) {
                    this.mMaxDelayTime = j;
                }
                deliverAlarmsLocked(elapsedRealtime, new ArrayList(this.mPendingNonWakeupAlarms));
                this.mPendingNonWakeupAlarms.clear();
            }
            long j2 = this.mNonInteractiveStartTime;
            if (j2 > 0) {
                long j3 = elapsedRealtime - j2;
                if (j3 > this.mNonInteractiveTime) {
                    this.mNonInteractiveTime = j3;
                }
            }
            this.mHandler.post(new AlarmManagerService$$ExternalSyntheticLambda3(this, 1));
        }
    }

    public final boolean isBackgroundRestricted(Alarm alarm) {
        AppStateTrackerImpl appStateTrackerImpl;
        if (alarm.alarmClock != null) {
            return false;
        }
        PendingIntent pendingIntent = alarm.operation;
        if (pendingIntent != null && pendingIntent.isActivity()) {
            return false;
        }
        int i = alarm.creatorUid;
        return (UserHandle.isCore(i) || (appStateTrackerImpl = this.mAppStateTracker) == null || !appStateTrackerImpl.areAlarmsRestricted(i, alarm.sourcePackage)) ? false : true;
    }

    public final boolean isExemptFromExactAlarmPermissionNoLock(int i) {
        DeviceIdleInternal deviceIdleInternal;
        if (Build.IS_DEBUGGABLE && Thread.holdsLock(this.mLock)) {
            Slog.wtfStack("AlarmManager", "Alarm lock held while calling into DeviceIdleController");
        }
        return UserHandle.isSameApp(this.mSystemUiUid, i) || UserHandle.isCore(i) || (deviceIdleInternal = this.mLocalDeviceIdleController) == null || deviceIdleInternal.isAppOnWhitelist(UserHandle.getAppId(i));
    }

    public final boolean lookForPackageLocked(int i, String str) {
        LazyAlarmStore lazyAlarmStore = this.mAlarmStore;
        lazyAlarmStore.getClass();
        ArrayList arrayList = new ArrayList(lazyAlarmStore.mAlarms);
        Collections.reverse(arrayList);
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Alarm alarm = (Alarm) it.next();
            if (str.equals(alarm.sourcePackage) && alarm.creatorUid == i) {
                return true;
            }
        }
        ArrayList arrayList2 = (ArrayList) this.mPendingBackgroundAlarms.get(i);
        if (arrayList2 != null) {
            Iterator it2 = arrayList2.iterator();
            while (it2.hasNext()) {
                if (str.equals(((Alarm) it2.next()).sourcePackage)) {
                    return true;
                }
            }
        }
        Iterator it3 = this.mPendingNonWakeupAlarms.iterator();
        while (it3.hasNext()) {
            Alarm alarm2 = (Alarm) it3.next();
            if (str.equals(alarm2.sourcePackage) && alarm2.creatorUid == i) {
                return true;
            }
        }
        return false;
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 500) {
            synchronized (this.mLock) {
                Constants constants = this.mConstants;
                AlarmManagerService.this.mInjector.getClass();
                DeviceConfig.addOnPropertiesChangedListener("alarm_manager", AppSchedulingModuleThread.getExecutor(), constants);
                constants.onPropertiesChanged(DeviceConfig.getProperties("alarm_manager", new String[0]));
                this.mAppOps = (AppOpsManager) getContext().getSystemService("appops");
                this.mLocalDeviceIdleController = (DeviceIdleInternal) LocalServices.getService(DeviceIdleInternal.class);
                GmsAlarmManager gmsAlarmManager = this.mGmsManager;
                if (gmsAlarmManager != null && gmsAlarmManager.isHongKongMode && gmsAlarmManager.mGmsPkgUid != -1) {
                    gmsAlarmManager.mObserver = gmsAlarmManager.new SmartManagerSettingsObserver(gmsAlarmManager.mHandler);
                    gmsAlarmManager.mContext.getContentResolver().registerContentObserver(GmsAlarmManager.SMART_MGR_SETTINGS_URI, false, gmsAlarmManager.mObserver);
                    gmsAlarmManager.getSettingsValueFromDB();
                }
                this.mUsageStatsManagerInternal = (UsageStatsManagerInternal) LocalServices.getService(UsageStatsManagerInternal.class);
                AppStateTrackerImpl appStateTrackerImpl = (AppStateTrackerImpl) LocalServices.getService(AppStateTracker.class);
                this.mAppStateTracker = appStateTrackerImpl;
                appStateTrackerImpl.addListener(this.mForceAppStandbyListener);
                this.mAppStandbyParole = ((BatteryManager) getContext().getSystemService(BatteryManager.class)).isCharging();
                this.mClockReceiver.scheduleTimeTickEvent();
                this.mClockReceiver.scheduleDateChangedEvent();
            }
            this.mInjector.getClass();
            try {
                IAppOpsService.Stub.asInterface(ServiceManager.getService("appops")).startWatchingMode(107, (String) null, new IAppOpsCallback.Stub() { // from class: com.android.server.alarm.AlarmManagerService.3
                    /* JADX WARN: Code restructure failed: missing block: B:40:0x009d, code lost:
                    
                        if (r8 == 0) goto L47;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:41:0x00a0, code lost:
                    
                        r3 = false;
                     */
                    /* JADX WARN: Code restructure failed: missing block: B:50:0x00ac, code lost:
                    
                        if (r8 != 0) goto L39;
                     */
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final void opChanged(int r8, int r9, java.lang.String r10, java.lang.String r11) {
                        /*
                            Method dump skipped, instructions count: 255
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.AnonymousClass3.opChanged(int, int, java.lang.String, java.lang.String):void");
                    }
                });
            } catch (RemoteException unused) {
            }
            this.mLocalPermissionManager = (PermissionManagerService.PermissionManagerServiceInternalImpl) LocalServices.getService(PermissionManagerService.PermissionManagerServiceInternalImpl.class);
            refreshExactAlarmCandidates();
            ((AppStandbyInternal) LocalServices.getService(AppStandbyInternal.class)).addListener(new AppStandbyTracker());
            this.mBatteryStatsInternal = (BatteryStatsInternal) LocalServices.getService(BatteryStatsInternal.class);
            final MetricsHelper metricsHelper = this.mMetricsHelper;
            final ?? r1 = new Supplier() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda0
                @Override // java.util.function.Supplier
                public final Object get() {
                    return AlarmManagerService.this.mAlarmStore;
                }
            };
            ((StatsManager) metricsHelper.mContext.getSystemService(StatsManager.class)).setPullAtomCallback(FrameworkStatsLog.PENDING_ALARM_INFO, (StatsManager.PullAtomMetadata) null, ConcurrentUtils.DIRECT_EXECUTOR, new StatsManager.StatsPullAtomCallback() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda0
                public final int onPullAtom(int i2, List list) {
                    MetricsHelper metricsHelper2 = MetricsHelper.this;
                    Supplier supplier = r1;
                    metricsHelper2.getClass();
                    if (i2 != 10106) {
                        throw new UnsupportedOperationException(VibrationParam$1$$ExternalSyntheticOutline0.m(i2, "Unknown tag"));
                    }
                    final long elapsedRealtime = SystemClock.elapsedRealtime();
                    synchronized (metricsHelper2.mLock) {
                        AlarmStore alarmStore = (AlarmStore) supplier.get();
                        int size = ((LazyAlarmStore) alarmStore).mAlarms.size();
                        final int i3 = 0;
                        LazyAlarmStore lazyAlarmStore = (LazyAlarmStore) alarmStore;
                        int count = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i3) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i4 = 5;
                        int count2 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i4) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i5 = 6;
                        int count3 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i5) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i6 = 7;
                        int count4 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i6) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i7 = 8;
                        int count5 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i7) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i8 = 9;
                        int count6 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i8) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i9 = 10;
                        int count7 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i9) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i10 = 1;
                        int count8 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i10) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        int count9 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda11
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                return ((Alarm) obj).mPolicyWhenElapsed[0] > elapsedRealtime + 31536000000L;
                            }
                        });
                        final int i11 = 2;
                        int count10 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i11) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i12 = 3;
                        int count11 = lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i12) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        });
                        final int i13 = 4;
                        list.add(FrameworkStatsLog.buildStatsEvent(i2, size, count, count2, count3, count4, count5, count6, count7, count8, count9, count10, count11, lazyAlarmStore.getCount(new Predicate() { // from class: com.android.server.alarm.MetricsHelper$$ExternalSyntheticLambda1
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                Alarm alarm = (Alarm) obj;
                                switch (i13) {
                                    case 0:
                                        return alarm.windowLength == 0;
                                    case 1:
                                        return alarm.listener != null;
                                    case 2:
                                        return alarm.repeatInterval != 0;
                                    case 3:
                                        return alarm.alarmClock != null;
                                    case 4:
                                        return AlarmManagerService.isRtc(alarm.type);
                                    case 5:
                                        return alarm.wakeup;
                                    case 6:
                                        return (alarm.flags & 4) != 0;
                                    case 7:
                                        return (alarm.flags & 64) != 0;
                                    case 8:
                                        PendingIntent pendingIntent = alarm.operation;
                                        return pendingIntent != null && pendingIntent.isForegroundService();
                                    case 9:
                                        PendingIntent pendingIntent2 = alarm.operation;
                                        return pendingIntent2 != null && pendingIntent2.isActivity();
                                    default:
                                        PendingIntent pendingIntent3 = alarm.operation;
                                        return pendingIntent3 != null && pendingIntent3.isService();
                                }
                            }
                        })));
                    }
                    return 0;
                }
            });
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
        Injector injector = this.mInjector;
        injector.getClass();
        System.loadLibrary("alarm_jni");
        injector.mNativeData = init();
        this.mHandler = new AlarmHandler();
        this.mOptsWithFgs.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mOptsWithFgsForAlarmClock.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mOptsWithoutFgs.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mOptsTimeBroadcast.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mActivityOptsRestrictBal.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mBroadcastOptsRestrictBal.setPendingIntentBackgroundActivityLaunchAllowed(false);
        this.mMetricsHelper = new MetricsHelper(getContext(), this.mLock);
        this.mActivityManagerInternal = (ActivityManagerInternal) LocalServices.getService(ActivityManagerInternal.class);
        Flags.useFrozenStateToDropListenerAlarms();
        this.mUseFrozenStateToDropListenerAlarms = true;
        Flags.startUserBeforeScheduledAlarms();
        if (this.mUseFrozenStateToDropListenerAlarms) {
            ((ActivityManager) getContext().getSystemService(ActivityManager.class)).registerUidFrozenStateChangedCallback(new HandlerExecutor(this.mHandler), new ActivityManager.UidFrozenStateChangedCallback() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda1
                public final void onUidFrozenStateChanged(int[] iArr, int[] iArr2) {
                    AlarmManagerService alarmManagerService = AlarmManagerService.this;
                    Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                    alarmManagerService.getClass();
                    int length = iArr2.length;
                    if (iArr.length != length) {
                        Slog.wtf("AlarmManager", "Got different length arrays in frozen state callback! uids.length: " + iArr.length + " frozenStates.length: " + length);
                        return;
                    }
                    IntArray intArray = new IntArray();
                    for (int i = 0; i < length; i++) {
                        if (iArr2[i] == 1 && CompatChanges.isChangeEnabled(265195908L, iArr[i])) {
                            intArray.add(iArr[i]);
                        }
                    }
                    if (intArray.size() > 0) {
                        int[] array = intArray.toArray();
                        synchronized (alarmManagerService.mLock) {
                            alarmManagerService.removeAlarmsInternalLocked(6, new AlarmManagerService$$ExternalSyntheticLambda6(0, array));
                        }
                    }
                }
            });
        }
        this.mListenerDeathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.alarm.AlarmManagerService.1
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
            }

            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied(IBinder iBinder) {
                IAlarmListener asInterface = IAlarmListener.Stub.asInterface(iBinder);
                synchronized (AlarmManagerService.this.mLock) {
                    AlarmManagerService.this.removeLocked(null, asInterface, 5);
                }
            }
        };
        synchronized (this.mLock) {
            try {
                this.mConstants = new Constants();
                LazyAlarmStore lazyAlarmStore = new LazyAlarmStore();
                this.mAlarmStore = lazyAlarmStore;
                lazyAlarmStore.mOnAlarmClockRemoved = this.mAlarmClockUpdater;
                this.mAppWakeupHistory = new AppWakeupHistory();
                this.mAllowWhileIdleHistory = new AppWakeupHistory();
                this.mAllowWhileIdleCompatHistory = new AppWakeupHistory();
                this.mTemporaryQuotaReserve = new TemporaryQuotaReserve();
                this.mNextNonWakeup = 0L;
                this.mNextWakeup = 0L;
                this.mInjector.getClass();
                Injector.initializeTimeIfRequired();
                PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                this.mPackageManagerInternal = packageManagerInternal;
                pmInternalForMARs = packageManagerInternal;
                this.mInjector.getClass();
                int packageUid = packageManagerInternal.getPackageUid(packageManagerInternal.getSystemUiServiceComponent().getPackageName(), 1048576L, 0);
                this.mSystemUiUid = packageUid;
                if (packageUid <= 0) {
                    Slog.wtf("AlarmManager", "SysUI package not found!");
                }
                this.mWakeLock = ((PowerManager) this.mInjector.mContext.getSystemService("power")).newWakeLock(1, "*alarm*");
                this.mTimeTickIntent = new Intent("android.intent.action.TIME_TICK").addFlags(1344274432);
                this.mTimeTickOptions = BroadcastOptions.makeBasic().setDeliveryGroupPolicy(1).setDeferralPolicy(2).toBundle();
                this.mTimeTickTrigger = new AnonymousClass2();
                Intent intent = new Intent("android.intent.action.DATE_CHANGED");
                intent.addFlags(538968064);
                this.mDateChangeSender = PendingIntent.getBroadcastAsUser(getContext(), 0, intent, 67108864, UserHandle.ALL);
                this.mInjector.getClass();
                this.mClockReceiver = new ClockReceiver();
                new ChargingReceiver();
                new UninstallReceiver(this, 1);
                new UninstallReceiver(this, 0);
                String str = SystemProperties.get(ActivationMonitor.COUNTRY_CODE_PROPERTY);
                if ("CHINA".equalsIgnoreCase(str) || "HONG KONG".equalsIgnoreCase(str)) {
                    GmsAlarmManager gmsAlarmManager = new GmsAlarmManager(getContext());
                    this.mGmsManager = gmsAlarmManager;
                    gmsAlarmManager.init(this);
                }
                if (this.mInjector.mNativeData != 0) {
                    new AlarmThread().start();
                } else {
                    Slog.w("AlarmManager", "Failed to open alarm driver. Falling back to a handler.");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        publishLocalService(LocalService.class, new LocalService());
        publishBinderService("alarm", this.mService);
    }

    @Override // com.android.server.SystemService
    public final void onUserStarting(SystemService.TargetUser targetUser) {
        super.onUserStarting(targetUser);
        final int userIdentifier = targetUser.getUserIdentifier();
        this.mHandler.post(new Runnable() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AlarmManagerService alarmManagerService = AlarmManagerService.this;
                int i = userIdentifier;
                Iterator it = alarmManagerService.mExactAlarmCandidates.iterator();
                while (it.hasNext()) {
                    int uid = UserHandle.getUid(i, ((Integer) it.next()).intValue());
                    AndroidPackage androidPackage = alarmManagerService.mPackageManagerInternal.getPackage(uid);
                    if (androidPackage != null) {
                        int checkOpNoThrow = alarmManagerService.mAppOps.checkOpNoThrow(107, uid, androidPackage.getPackageName());
                        synchronized (alarmManagerService.mLock) {
                            alarmManagerService.mLastOpScheduleExactAlarm.put(uid, checkOpNoThrow);
                        }
                    }
                }
            }
        });
    }

    public final void reevaluateRtcAlarms() {
        Alarm alarm;
        synchronized (this.mLock) {
            try {
                LazyAlarmStore lazyAlarmStore = this.mAlarmStore;
                Iterator it = lazyAlarmStore.mAlarms.iterator();
                boolean z = false;
                boolean z2 = false;
                while (it.hasNext()) {
                    Alarm alarm2 = (Alarm) it.next();
                    z2 |= !isRtc(alarm2.type) ? false : alarm2.setPolicyElapsed(0, convertToElapsed(alarm2.type, alarm2.origWhen));
                }
                if (z2) {
                    Collections.sort(lazyAlarmStore.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                }
                if (z2 && this.mPendingIdleUntil != null && (alarm = this.mNextWakeFromIdle) != null && isRtc(alarm.type)) {
                    LazyAlarmStore lazyAlarmStore2 = this.mAlarmStore;
                    Iterator it2 = lazyAlarmStore2.mAlarms.iterator();
                    boolean z3 = false;
                    while (it2.hasNext()) {
                        Alarm alarm3 = (Alarm) it2.next();
                        z3 |= alarm3 == this.mPendingIdleUntil && adjustIdleUntilTime(alarm3);
                    }
                    if (z3) {
                        Collections.sort(lazyAlarmStore2.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                    }
                    if (z3) {
                        LazyAlarmStore lazyAlarmStore3 = this.mAlarmStore;
                        Iterator it3 = lazyAlarmStore3.mAlarms.iterator();
                        while (it3.hasNext()) {
                            z |= adjustDeliveryTimeBasedOnDeviceIdle((Alarm) it3.next());
                        }
                        if (z) {
                            Collections.sort(lazyAlarmStore3.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                        }
                    }
                }
                if (z2) {
                    rescheduleKernelAlarmsLocked();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void refreshExactAlarmCandidates() {
        String[] appOpPermissionPackages = PermissionManagerService.this.mPermissionManagerServiceImpl.getAppOpPermissionPackages("android.permission.SCHEDULE_EXACT_ALARM");
        ArraySet arraySet = new ArraySet(appOpPermissionPackages.length);
        for (String str : appOpPermissionPackages) {
            int packageUid = this.mPackageManagerInternal.getPackageUid(str, 4194304L, 0);
            if (packageUid > 0) {
                arraySet.add(Integer.valueOf(UserHandle.getAppId(packageUid)));
            }
        }
        this.mExactAlarmCandidates = Collections.unmodifiableSet(arraySet);
    }

    public final void removeAlarmsInternalLocked(int i, Predicate predicate) {
        boolean z;
        boolean z2;
        this.mInjector.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ArrayList remove = this.mAlarmStore.remove(predicate);
        int i2 = 1;
        boolean z3 = !remove.isEmpty();
        for (int size = this.mPendingBackgroundAlarms.size() - 1; size >= 0; size--) {
            ArrayList arrayList = (ArrayList) this.mPendingBackgroundAlarms.valueAt(size);
            for (int size2 = arrayList.size() - 1; size2 >= 0; size2--) {
                if (predicate.test((Alarm) arrayList.get(size2))) {
                    remove.add((Alarm) arrayList.remove(size2));
                }
            }
            if (arrayList.size() == 0) {
                this.mPendingBackgroundAlarms.removeAt(size);
            }
        }
        for (int size3 = this.mPendingMARsRestrictedAlarms.size() - 1; size3 >= 0; size3--) {
            ArrayList arrayList2 = (ArrayList) this.mPendingMARsRestrictedAlarms.valueAt(size3);
            for (int size4 = arrayList2.size() - 1; size4 >= 0; size4--) {
                if (predicate.test((Alarm) arrayList2.get(size4))) {
                    remove.add((Alarm) arrayList2.remove(size4));
                }
            }
            if (arrayList2.size() == 0) {
                this.mPendingMARsRestrictedAlarms.removeAt(size3);
            }
        }
        for (int size5 = this.mPendingNonWakeupAlarms.size() - 1; size5 >= 0; size5--) {
            if (predicate.test((Alarm) this.mPendingNonWakeupAlarms.get(size5))) {
                remove.add((Alarm) this.mPendingNonWakeupAlarms.remove(size5));
            }
        }
        Iterator it = remove.iterator();
        while (true) {
            z = false;
            if (!it.hasNext()) {
                break;
            }
            Alarm alarm = (Alarm) it.next();
            if (alarm.alarmClock != null) {
                SamsungAlarmManagerService.SamsungAlarmHandler samsungAlarmHandler = this.mSamsungAlarmManagerService.mHandler;
                samsungAlarmHandler.removeMessages(i2);
                samsungAlarmHandler.sendEmptyMessage(i2);
            }
            int i3 = alarm.uid;
            decrementAlarmCount(i3);
            IAlarmListener iAlarmListener = alarm.listener;
            if (iAlarmListener != null) {
                iAlarmListener.asBinder().unlinkToDeath(this.mListenerDeathRecipient, 0);
            }
            if (i != 0) {
                RingBuffer ringBuffer = (RingBuffer) this.mRemovalHistory.get(i3);
                if (ringBuffer == null) {
                    ringBuffer = (i3 == 1000 || "com.sec.android.app.clockpackage".equals(alarm.sourcePackage)) ? new RingBuffer(RemovedAlarm.class, 50) : i3 == this.mSystemUiUid ? new RingBuffer(RemovedAlarm.class, 30) : new RingBuffer(RemovedAlarm.class, 10);
                    this.mRemovalHistory.put(i3, ringBuffer);
                }
                ringBuffer.append(new RemovedAlarm(alarm, i, currentTimeMillis, elapsedRealtime));
                it = it;
                i2 = 1;
            }
        }
        if (z3) {
            Alarm alarm2 = this.mPendingIdleUntil;
            if (alarm2 == null || !predicate.test(alarm2)) {
                z2 = false;
            } else {
                this.mPendingIdleUntil = null;
                z2 = true;
            }
            Alarm alarm3 = this.mNextWakeFromIdle;
            if (alarm3 != null && predicate.test(alarm3)) {
                this.mNextWakeFromIdle = this.mAlarmStore.getNextWakeFromIdleAlarm();
                if (this.mPendingIdleUntil != null) {
                    LazyAlarmStore lazyAlarmStore = this.mAlarmStore;
                    Iterator it2 = lazyAlarmStore.mAlarms.iterator();
                    boolean z4 = false;
                    while (it2.hasNext()) {
                        Alarm alarm4 = (Alarm) it2.next();
                        z4 |= alarm4 == this.mPendingIdleUntil && adjustIdleUntilTime(alarm4);
                    }
                    if (z4) {
                        Collections.sort(lazyAlarmStore.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                    }
                    z2 |= z4;
                }
            }
            if (z2) {
                LazyAlarmStore lazyAlarmStore2 = this.mAlarmStore;
                Iterator it3 = lazyAlarmStore2.mAlarms.iterator();
                while (it3.hasNext()) {
                    z |= adjustDeliveryTimeBasedOnDeviceIdle((Alarm) it3.next());
                }
                if (z) {
                    Collections.sort(lazyAlarmStore2.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                }
            }
            rescheduleKernelAlarmsLocked();
            updateNextAlarmClockLocked();
        }
    }

    public final void removeExactAlarmsOnPermissionRevoked(int i, String str, boolean z) {
        if (isExemptFromExactAlarmPermissionNoLock(i) || !isExactAlarmChangeEnabled(UserHandle.getUserId(i), str)) {
            return;
        }
        Slog.w("AlarmManager", AppStateTrackerImpl$MyHandler$$ExternalSyntheticOutline0.m(i, "Package ", str, ", uid ", " lost permission to set exact alarms!"));
        AlarmManagerService$$ExternalSyntheticLambda11 alarmManagerService$$ExternalSyntheticLambda11 = new AlarmManagerService$$ExternalSyntheticLambda11(i, str);
        synchronized (this.mLock) {
            removeAlarmsInternalLocked(2, alarmManagerService$$ExternalSyntheticLambda11);
        }
        if (z) {
            int appId = UserHandle.getAppId(i);
            int userId = UserHandle.getUserId(i);
            ConcurrentHashMap concurrentHashMap = PermissionManagerService.sRunningAttributionSources;
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                IActivityManager service = ActivityManager.getService();
                if (service != null) {
                    try {
                        service.killUidForPermissionChange(appId, userId, "schedule_exact_alarm revoked");
                    } catch (RemoteException unused) {
                    }
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public final void removeImpl(PendingIntent pendingIntent, IAlarmListener iAlarmListener) {
        synchronized (this.mLock) {
            removeLocked(pendingIntent, iAlarmListener, 0);
        }
    }

    public final void removeLocked(PendingIntent pendingIntent, IAlarmListener iAlarmListener, int i) {
        if (pendingIntent == null && iAlarmListener == null) {
            return;
        }
        removeAlarmsInternalLocked(i, new AlarmManagerService$$ExternalSyntheticLambda8(pendingIntent, iAlarmListener));
    }

    public final void removeUserLocked(int i) {
        if (i == 0) {
            Slog.w("AlarmManager", "Ignoring attempt to remove system-user state!");
            return;
        }
        removeAlarmsInternalLocked(0, new AlarmManagerService$$ExternalSyntheticLambda9(i, 1));
        for (int size = this.mLastPriorityAlarmDispatch.size() - 1; size >= 0; size--) {
            if (UserHandle.getUserId(this.mLastPriorityAlarmDispatch.keyAt(size)) == i) {
                this.mLastPriorityAlarmDispatch.removeAt(size);
            }
        }
        for (int size2 = this.mRemovalHistory.size() - 1; size2 >= 0; size2--) {
            if (UserHandle.getUserId(this.mRemovalHistory.keyAt(size2)) == i) {
                this.mRemovalHistory.removeAt(size2);
            }
        }
        for (int size3 = this.mLastOpScheduleExactAlarm.size() - 1; size3 >= 0; size3--) {
            if (UserHandle.getUserId(this.mLastOpScheduleExactAlarm.keyAt(size3)) == i) {
                this.mLastOpScheduleExactAlarm.removeAt(size3);
            }
        }
    }

    public final boolean reorderAlarmsBasedOnStandbyBuckets(final ArraySet arraySet) {
        StatLogger statLogger = this.mStatLogger;
        long time = statLogger.getTime();
        boolean updateAlarmDeliveries = this.mAlarmStore.updateAlarmDeliveries(new AlarmStore.AlarmDeliveryCalculator() { // from class: com.android.server.alarm.AlarmManagerService$$ExternalSyntheticLambda10
            @Override // com.android.server.alarm.AlarmStore.AlarmDeliveryCalculator
            public final boolean updateAlarmDelivery(Alarm alarm) {
                ArraySet arraySet2 = arraySet;
                Intent intent = AlarmManagerService.NEXT_ALARM_CLOCK_CHANGED_INTENT;
                AlarmManagerService alarmManagerService = AlarmManagerService.this;
                alarmManagerService.getClass();
                UserPackage of = UserPackage.of(UserHandle.getUserId(alarm.creatorUid), alarm.sourcePackage);
                if (arraySet2 == null || arraySet2.contains(of)) {
                    return alarmManagerService.adjustDeliveryTimeBasedOnBucketLocked(alarm);
                }
                return false;
            }
        });
        statLogger.logDurationStat(0, time);
        return updateAlarmDeliveries;
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00a8, code lost:
    
        if (r13 != r11) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void rescheduleKernelAlarmsLocked() {
        /*
            Method dump skipped, instructions count: 224
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.rescheduleKernelAlarmsLocked():void");
    }

    public final void sendPendingBackgroundAlarmsLocked(int i, String str) {
        ArrayList arrayList = (ArrayList) this.mPendingBackgroundAlarms.get(i);
        if (arrayList == null || arrayList.size() == 0) {
            return;
        }
        if (str != null) {
            ArrayList arrayList2 = new ArrayList();
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                if (str.equals(((Alarm) arrayList.get(size)).sourcePackage)) {
                    arrayList2.add((Alarm) arrayList.remove(size));
                }
            }
            if (arrayList.size() == 0) {
                this.mPendingBackgroundAlarms.remove(i);
            }
            arrayList = arrayList2;
        } else {
            this.mPendingBackgroundAlarms.remove(i);
        }
        this.mInjector.getClass();
        deliverPendingBackgroundAlarmsLocked(SystemClock.elapsedRealtime(), arrayList);
    }

    public final void setImpl(int i, long j, long j2, long j3, PendingIntent pendingIntent, IAlarmListener iAlarmListener, String str, int i2, WorkSource workSource, AlarmManager.AlarmClockInfo alarmClockInfo, int i3, String str2, Bundle bundle, int i4) {
        long j4;
        long min;
        long j5;
        String str3;
        long j6;
        long j7 = j3;
        if ((pendingIntent == null && iAlarmListener == null) || (pendingIntent != null && iAlarmListener != null)) {
            Slog.w("AlarmManager", "Alarms must either supply a PendingIntent or an AlarmReceiver");
            return;
        }
        if (iAlarmListener != null) {
            try {
                iAlarmListener.asBinder().linkToDeath(this.mListenerDeathRecipient, 0);
            } catch (RemoteException unused) {
                HeimdAllFsService$$ExternalSyntheticOutline0.m("Dropping unreachable alarm listener ", str, "AlarmManager");
                return;
            }
        }
        Constants constants = this.mConstants;
        long j8 = constants.MIN_INTERVAL;
        if (j7 <= 0 || j7 >= j8) {
            if (j7 > constants.MAX_INTERVAL) {
                Slog.w("AlarmManager", "Suspiciously long interval " + j7 + " millis; clamping");
                j7 = this.mConstants.MAX_INTERVAL;
            }
            j8 = j7;
        } else {
            StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m("Suspiciously short interval ", j7, " millis; expanding to ");
            m.append(j8 / 1000);
            m.append(" seconds");
            Slog.w("AlarmManager", m.toString());
        }
        if (i < 0 || i > 3) {
            throw new IllegalArgumentException(VibrationParam$1$$ExternalSyntheticOutline0.m(i, "Invalid alarm type "));
        }
        if (j < 0) {
            long callingPid = Binder.getCallingPid();
            StringBuilder m2 = SystemServiceManager$$ExternalSyntheticOutline0.m(i3, "Invalid alarm trigger time! ", j, " from uid=");
            m2.append(" pid=");
            m2.append(callingPid);
            Slog.w("AlarmManager", m2.toString());
            j4 = 0;
        } else {
            j4 = j;
        }
        this.mInjector.getClass();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long max = Math.max((UserHandle.isCore(i3) ? 0L : this.mConstants.MIN_FUTURITY) + elapsedRealtime, convertToElapsed(i, j4));
        if (j2 == 0) {
            j5 = j8;
            str3 = str2;
            j6 = j2;
        } else {
            if (j2 < 0) {
                j5 = j8;
                min = maxTriggerTime(elapsedRealtime, max, j5) - max;
            } else {
                min = Math.min((long) ((max - elapsedRealtime) * 0.75d), this.mConstants.MIN_WINDOW);
                if (j2 > BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
                    Slog.w("AlarmManager", "Window length " + j2 + "ms too long; limiting to 1 day");
                    j5 = j8;
                    min = BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS;
                } else {
                    if ((i2 & 64) != 0 || j2 >= min || isExemptFromExactAlarmPermissionNoLock(i3)) {
                        j5 = j8;
                        str3 = str2;
                    } else {
                        j5 = j8;
                        str3 = str2;
                        if (CompatChanges.isChangeEnabled(185199076L, str3, UserHandle.getUserHandleForUid(i3))) {
                            StringBuilder m3 = BatteryService$$ExternalSyntheticOutline0.m("Window length ", j2, "ms too short; expanding to ");
                            m3.append(min);
                            m3.append("ms.");
                            Slog.w("AlarmManager", m3.toString());
                            j6 = min;
                        }
                    }
                    min = j2;
                    j6 = min;
                }
            }
            str3 = str2;
            j6 = min;
        }
        synchronized (this.mLock) {
            try {
                try {
                    if (this.mAlarmsPerUid.get(i3, 0) >= this.mConstants.MAX_ALARMS_PER_UID) {
                        String str4 = "Maximum limit of concurrent alarms " + this.mConstants.MAX_ALARMS_PER_UID + " reached for uid: " + UserHandle.formatUid(i3) + ", callingPackage: " + str3;
                        Slog.w("AlarmManager", str4);
                        if (i3 != 1000) {
                            throw new IllegalStateException(str4);
                        }
                        EventLog.writeEvent(1397638484, "234441463", -1, str4);
                    }
                    setImplLocked(i, i2, i3, i4, j4, max, j6, j5, alarmClockInfo, iAlarmListener, pendingIntent, bundle, workSource, str, str2);
                } catch (Throwable th) {
                    th = th;
                    throw th;
                }
            } catch (Throwable th2) {
                th = th2;
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:99:0x0202, code lost:
    
        if (r8.toString().contains("com.google.android.gms.checkin.EventLogServiceReceiver") != false) goto L115;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setImplLocked(int r25, int r26, int r27, int r28, long r29, long r31, long r33, long r35, android.app.AlarmManager.AlarmClockInfo r37, android.app.IAlarmListener r38, android.app.PendingIntent r39, android.os.Bundle r40, android.os.WorkSource r41, java.lang.String r42, java.lang.String r43) {
        /*
            Method dump skipped, instructions count: 659
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.alarm.AlarmManagerService.setImplLocked(int, int, int, int, long, long, long, long, android.app.AlarmManager$AlarmClockInfo, android.app.IAlarmListener, android.app.PendingIntent, android.os.Bundle, android.os.WorkSource, java.lang.String, java.lang.String):void");
    }

    public final void setImplLocked(Alarm alarm) {
        Alarm alarm2;
        int i = alarm.flags;
        boolean z = false;
        if ((i & 16) != 0) {
            adjustIdleUntilTime(alarm);
            Alarm alarm3 = this.mPendingIdleUntil;
            if (alarm3 != alarm && alarm3 != null) {
                Slog.wtfStack("AlarmManager", "setImplLocked: idle until changed from " + this.mPendingIdleUntil + " to " + alarm);
                LazyAlarmStore lazyAlarmStore = this.mAlarmStore;
                Alarm alarm4 = this.mPendingIdleUntil;
                Objects.requireNonNull(alarm4);
                lazyAlarmStore.remove(new AlarmManagerService$$ExternalSyntheticLambda6(3, alarm4));
            }
            this.mPendingIdleUntil = alarm;
            LazyAlarmStore lazyAlarmStore2 = this.mAlarmStore;
            Iterator it = lazyAlarmStore2.mAlarms.iterator();
            boolean z2 = false;
            while (it.hasNext()) {
                z2 |= adjustDeliveryTimeBasedOnDeviceIdle((Alarm) it.next());
            }
            if (z2) {
                Collections.sort(lazyAlarmStore2.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
            }
        } else if (this.mPendingIdleUntil != null) {
            adjustDeliveryTimeBasedOnDeviceIdle(alarm);
        }
        if ((i & 2) != 0 && ((alarm2 = this.mNextWakeFromIdle) == null || alarm2.mWhenElapsed > alarm.mWhenElapsed)) {
            this.mNextWakeFromIdle = alarm;
            if (this.mPendingIdleUntil != null) {
                LazyAlarmStore lazyAlarmStore3 = this.mAlarmStore;
                Iterator it2 = lazyAlarmStore3.mAlarms.iterator();
                boolean z3 = false;
                while (it2.hasNext()) {
                    Alarm alarm5 = (Alarm) it2.next();
                    z3 |= alarm5 == this.mPendingIdleUntil && adjustIdleUntilTime(alarm5);
                }
                if (z3) {
                    Collections.sort(lazyAlarmStore3.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                }
                if (z3) {
                    LazyAlarmStore lazyAlarmStore4 = this.mAlarmStore;
                    Iterator it3 = lazyAlarmStore4.mAlarms.iterator();
                    while (it3.hasNext()) {
                        z |= adjustDeliveryTimeBasedOnDeviceIdle((Alarm) it3.next());
                    }
                    if (z) {
                        Collections.sort(lazyAlarmStore4.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                    }
                }
            }
        }
        if (alarm.alarmClock != null) {
            SamsungAlarmManagerService.SamsungAlarmHandler samsungAlarmHandler = this.mSamsungAlarmManagerService.mHandler;
            samsungAlarmHandler.removeMessages(1);
            samsungAlarmHandler.sendEmptyMessage(1);
            this.mNextAlarmClockMayChange = true;
        }
        adjustDeliveryTimeBasedOnBatterySaver(alarm);
        adjustDeliveryTimeBasedOnBucketLocked(alarm);
        this.mInjector.getClass();
        long currentTimeMillis = System.currentTimeMillis();
        long elapsedRealtime = SystemClock.elapsedRealtime();
        SparseArray sparseArray = this.mAdditionHistory;
        int i2 = alarm.uid;
        RingBuffer ringBuffer = (RingBuffer) sparseArray.get(i2);
        if (ringBuffer == null) {
            ringBuffer = (i2 == 1000 || "com.sec.android.app.clockpackage".equals(alarm.sourcePackage)) ? new RingBuffer(AddedAlarm.class, 50) : i2 == this.mSystemUiUid ? new RingBuffer(AddedAlarm.class, 30) : new RingBuffer(AddedAlarm.class, 10);
            this.mAdditionHistory.put(i2, ringBuffer);
        }
        ringBuffer.append(new AddedAlarm(alarm, currentTimeMillis, elapsedRealtime));
        LazyAlarmStore lazyAlarmStore5 = this.mAlarmStore;
        int binarySearch = Collections.binarySearch(lazyAlarmStore5.mAlarms, alarm, LazyAlarmStore.sDecreasingTimeOrder);
        if (binarySearch < 0) {
            binarySearch = (-1) - binarySearch;
        }
        lazyAlarmStore5.mAlarms.add(binarySearch, alarm);
        rescheduleKernelAlarmsLocked();
        updateNextAlarmClockLocked();
    }

    public final void setLocked(int i, long j) {
        long j2;
        long j3;
        long j4 = this.mInjector.mNativeData;
        if (j4 == 0) {
            Message obtain = Message.obtain();
            obtain.what = 1;
            this.mHandler.removeMessages(1);
            this.mHandler.sendMessageAtTime(obtain, j);
            return;
        }
        if (j < 0) {
            j3 = 0;
            j2 = 0;
        } else {
            j2 = 1000000 * (j % 1000);
            j3 = j / 1000;
        }
        int i2 = set(j4, i, j3, j2);
        if (i2 != 0) {
            StringBuilder m = SystemServiceManager$$ExternalSyntheticOutline0.m(i, "Unable to set kernel alarm, now=", SystemClock.elapsedRealtime(), " type=");
            BootReceiver$$ExternalSyntheticOutline0.m(m, " @ (", j3, ",");
            m.append(j2);
            m.append("), ret = ");
            m.append(i2);
            m.append(" = ");
            m.append(Os.strerror(i2));
            Slog.wtf("AlarmManager", m.toString());
        }
    }

    public final void setTimeZoneImpl(int i, String str, String str2) {
        boolean timeZoneId;
        if (TextUtils.isEmpty(str)) {
            return;
        }
        TimeZone timeZone = TimeZone.getTimeZone(str);
        synchronized (this) {
            try {
                timeZoneId = SystemTimeZone.setTimeZoneId(i, str, str2);
                this.mInjector.getClass();
                SystemProperties.set("persist.sys.time.offset", String.valueOf(timeZone.getOffset(System.currentTimeMillis())));
                ZoneOffsetTransition nextTransition = timeZone.toZoneId().getRules().nextTransition(Instant.now());
                if (nextTransition != null) {
                    TimeUnit timeUnit = TimeUnit.SECONDS;
                    long millis = timeUnit.toMillis(nextTransition.getOffsetAfter().getTotalSeconds() - nextTransition.getOffsetBefore().getTotalSeconds());
                    SystemProperties.set("persist.sys.time.dst_transition", String.valueOf(timeUnit.toMillis(nextTransition.toEpochSecond())));
                    SystemProperties.set("persist.sys.time.dst_offset", String.valueOf(millis));
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        TimeZone.setDefault(null);
        if (timeZoneId) {
            this.mClockReceiver.scheduleDateChangedEvent();
            Intent m = BatteryService$$ExternalSyntheticOutline0.m(622854144, "android.intent.action.TIMEZONE_CHANGED");
            m.putExtra("time-zone", timeZone.getID());
            this.mOptsTimeBroadcast.setTemporaryAppAllowlist(this.mActivityManagerInternal.getBootTimeTempAllowListDuration(), 0, 204, "");
            this.mOptsTimeBroadcast.setDeliveryGroupPolicy(1);
            getContext().sendBroadcastAsUser(m, UserHandle.ALL, null, this.mOptsTimeBroadcast.toBundle());
        }
    }

    public final void setWakelockWorkSource(WorkSource workSource, int i, String str, boolean z) {
        try {
            PowerManager.WakeLock wakeLock = this.mWakeLock;
            if (!z) {
                str = null;
            }
            wakeLock.setHistoryTag(str);
        } catch (Exception unused) {
        }
        if (workSource != null) {
            this.mWakeLock.setWorkSource(workSource);
            return;
        }
        if (i >= 0) {
            this.mWakeLock.setWorkSource(new WorkSource(i));
            return;
        }
        this.mWakeLock.setWorkSource(null);
    }

    public final int triggerAlarmsLocked(long j, ArrayList arrayList) {
        Alarm alarm;
        int i;
        AlarmManagerService alarmManagerService = this;
        ArrayList arrayList2 = arrayList;
        LazyAlarmStore lazyAlarmStore = alarmManagerService.mAlarmStore;
        lazyAlarmStore.getClass();
        ArrayList arrayList3 = new ArrayList();
        int i2 = 1;
        boolean z = false;
        boolean z2 = false;
        for (int size = lazyAlarmStore.mAlarms.size() - 1; size >= 0; size--) {
            Alarm alarm2 = (Alarm) lazyAlarmStore.mAlarms.get(size);
            if (alarm2.mWhenElapsed > j) {
                break;
            }
            lazyAlarmStore.mAlarms.remove(size);
            arrayList3.add(alarm2);
            if (alarm2.wakeup && alarm2.mMaxWhenElapsed <= j + 500) {
                z = true;
            }
            if ((alarm2.flags & 1) != 0) {
                z2 = true;
            }
        }
        ArrayList arrayList4 = new ArrayList();
        for (int size2 = arrayList3.size() - 1; size2 >= 0; size2--) {
            Alarm alarm3 = (Alarm) arrayList3.get(size2);
            if ((z || !alarm3.wakeup) && (!z2 || (alarm3.flags & 1) != 0)) {
                arrayList3.remove(size2);
                arrayList4.add(alarm3);
            }
        }
        lazyAlarmStore.mAlarms.addAll(arrayList3);
        Collections.sort(lazyAlarmStore.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
        Iterator it = arrayList4.iterator();
        int i3 = 0;
        while (it.hasNext()) {
            Alarm alarm4 = (Alarm) it.next();
            boolean isBackgroundRestricted = alarmManagerService.isBackgroundRestricted(alarm4);
            int i4 = alarm4.creatorUid;
            if (isBackgroundRestricted) {
                ArrayList arrayList5 = (ArrayList) alarmManagerService.mPendingBackgroundAlarms.get(i4);
                if (arrayList5 == null) {
                    arrayList5 = new ArrayList();
                    alarmManagerService.mPendingBackgroundAlarms.put(i4, arrayList5);
                }
                arrayList5.add(alarm4);
            } else if (isMARsRestricted(alarm4)) {
                Slog.d("AlarmManager", "Deferring alarm " + alarm4 + " due to freecess. [uid " + i4 + "]");
                ArrayList arrayList6 = (ArrayList) alarmManagerService.mPendingMARsRestrictedAlarms.get(i4);
                if (arrayList6 == null) {
                    arrayList6 = new ArrayList();
                    alarmManagerService.mPendingMARsRestrictedAlarms.put(i4, arrayList6);
                }
                arrayList6.add(alarm4);
            } else {
                alarm4.count = i2;
                arrayList2.add(alarm4);
                if ((alarm4.flags & 2) != 0) {
                    EventLog.writeEvent(34002, Integer.valueOf(alarmManagerService.mPendingIdleUntil != null ? i2 : 0), alarm4.statsTag);
                }
                if (alarmManagerService.mPendingIdleUntil == alarm4) {
                    alarmManagerService.mPendingIdleUntil = null;
                    LazyAlarmStore lazyAlarmStore2 = alarmManagerService.mAlarmStore;
                    Iterator it2 = lazyAlarmStore2.mAlarms.iterator();
                    boolean z3 = false;
                    while (it2.hasNext()) {
                        z3 |= alarmManagerService.adjustDeliveryTimeBasedOnDeviceIdle((Alarm) it2.next());
                    }
                    if (z3) {
                        Collections.sort(lazyAlarmStore2.mAlarms, LazyAlarmStore.sDecreasingTimeOrder);
                    }
                }
                if (alarmManagerService.mNextWakeFromIdle == alarm4) {
                    alarmManagerService.mNextWakeFromIdle = alarmManagerService.mAlarmStore.getNextWakeFromIdleAlarm();
                }
                long j2 = alarm4.repeatInterval;
                if (j2 > 0) {
                    long j3 = alarm4.count;
                    long j4 = alarm4.mPolicyWhenElapsed[0];
                    int i5 = (int) (((j - j4) / j2) + j3);
                    alarm4.count = i5;
                    long j5 = i5 * j2;
                    long j6 = j4 + j5;
                    long maxTriggerTime = maxTriggerTime(j, j6, j2);
                    long j7 = alarm4.origWhen + j5;
                    long j8 = maxTriggerTime - j6;
                    PendingIntent pendingIntent = alarm4.operation;
                    WorkSource workSource = alarm4.workSource;
                    setImplLocked(alarm4.type, alarm4.flags, alarm4.uid, -1, j7, j6, j8, alarm4.repeatInterval, alarm4.alarmClock, null, pendingIntent, null, workSource, null, alarm4.packageName);
                    alarm = alarm4;
                } else {
                    alarm = alarm4;
                }
                if (alarm.wakeup) {
                    i3++;
                }
                if (alarm.alarmClock != null) {
                    alarmManagerService = this;
                    SamsungAlarmManagerService.SamsungAlarmHandler samsungAlarmHandler = alarmManagerService.mSamsungAlarmManagerService.mHandler;
                    i = 1;
                    samsungAlarmHandler.removeMessages(1);
                    samsungAlarmHandler.sendEmptyMessage(1);
                    alarmManagerService.mNextAlarmClockMayChange = true;
                } else {
                    i = 1;
                    alarmManagerService = this;
                }
                arrayList2 = arrayList;
                i2 = i;
            }
        }
        calculateDeliveryPriorities(arrayList);
        Collections.sort(arrayList, alarmManagerService.mAlarmDispatchComparator);
        return i3;
    }

    public final void updateNextAlarmClockLocked() {
        if (this.mNextAlarmClockMayChange) {
            this.mNextAlarmClockMayChange = false;
            SparseArray sparseArray = this.mTmpSparseAlarmClockArray;
            sparseArray.clear();
            LazyAlarmStore lazyAlarmStore = this.mAlarmStore;
            lazyAlarmStore.getClass();
            ArrayList arrayList = new ArrayList(lazyAlarmStore.mAlarms);
            Collections.reverse(arrayList);
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                Alarm alarm = (Alarm) it.next();
                if (alarm.alarmClock != null) {
                    int userId = UserHandle.getUserId(alarm.uid);
                    AlarmManager.AlarmClockInfo alarmClockInfo = (AlarmManager.AlarmClockInfo) this.mNextAlarmClockForUser.get(userId);
                    if (sparseArray.get(userId) == null) {
                        sparseArray.put(userId, alarm.alarmClock);
                    } else if (alarm.alarmClock.equals(alarmClockInfo) && alarmClockInfo.getTriggerTime() <= ((AlarmManager.AlarmClockInfo) sparseArray.get(userId)).getTriggerTime()) {
                        sparseArray.put(userId, alarmClockInfo);
                    }
                }
            }
            int size = sparseArray.size();
            for (int i = 0; i < size; i++) {
                AlarmManager.AlarmClockInfo alarmClockInfo2 = (AlarmManager.AlarmClockInfo) sparseArray.valueAt(i);
                int keyAt = sparseArray.keyAt(i);
                if (!alarmClockInfo2.equals((AlarmManager.AlarmClockInfo) this.mNextAlarmClockForUser.get(keyAt))) {
                    updateNextAlarmInfoForUserLocked(keyAt, alarmClockInfo2);
                }
            }
            for (int size2 = this.mNextAlarmClockForUser.size() - 1; size2 >= 0; size2--) {
                int keyAt2 = this.mNextAlarmClockForUser.keyAt(size2);
                if (sparseArray.get(keyAt2) == null) {
                    updateNextAlarmInfoForUserLocked(keyAt2, null);
                }
            }
        }
    }

    public final void updateNextAlarmInfoForUserLocked(int i, AlarmManager.AlarmClockInfo alarmClockInfo) {
        if (alarmClockInfo != null) {
            this.mNextAlarmClockForUser.put(i, alarmClockInfo);
        } else {
            this.mNextAlarmClockForUser.remove(i);
        }
        this.mPendingSendNextAlarmClockChangedForUser.put(i, true);
        this.mHandler.removeMessages(2);
        this.mHandler.sendEmptyMessage(2);
    }
}
