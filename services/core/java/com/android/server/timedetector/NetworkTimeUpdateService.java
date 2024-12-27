package com.android.server.timedetector;

import android.R;
import android.app.AlarmManager;
import android.app.time.UnixEpochTime;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import android.util.LocalLog;
import android.util.Log;
import android.util.NtpTrustedTime;

import com.android.internal.util.DumpUtils;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;

import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.datetime.IDateTimePolicy;
import com.samsung.android.knox.datetime.NtpInfo;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

public final class NetworkTimeUpdateService extends Binder {
    public static boolean mBootCompleted;
    public static Context mMDMContext;
    public final ConnectivityManager mCM;
    public final Context mContext;
    public final EngineImpl mEngine;
    public final Handler mHandler;
    public final NtpTrustedTime mNtpTrustedTime;
    public final AnonymousClass1 mRefreshCallbacks;
    public final PowerManager.WakeLock mWakeLock;
    public final Object mLock = new Object();
    public Network mDefaultNetwork = null;

    /* renamed from: com.android.server.timedetector.NetworkTimeUpdateService$1, reason: invalid class name */
    public final class AnonymousClass1 {
        public final ScheduledRefreshAlarmListener mOnAlarmListener;
        public final /* synthetic */ AlarmManager val$alarmManager;
        public final /* synthetic */ TimeDetectorInternal val$timeDetectorInternal;

        public AnonymousClass1(
                NetworkTimeUpdateService networkTimeUpdateService,
                AlarmManager alarmManager,
                TimeDetectorInternal timeDetectorInternal) {
            this.val$alarmManager = alarmManager;
            this.val$timeDetectorInternal = timeDetectorInternal;
            this.mOnAlarmListener = networkTimeUpdateService.new ScheduledRefreshAlarmListener();
        }
    }

    public final class AutoTimeSettingObserver extends ContentObserver {
        public final Context mContext;

        public AutoTimeSettingObserver(Handler handler, Context context) {
            super(handler);
            Objects.requireNonNull(context);
            this.mContext = context;
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (Settings.Global.getInt(this.mContext.getContentResolver(), "auto_time", 0) != 0) {
                NetworkTimeUpdateService.m972$$Nest$monPollNetworkTime(
                        NetworkTimeUpdateService.this, "automatic time enabled");
            }
        }
    }

    interface Engine {}

    class EngineImpl implements Engine {
        public static boolean mForceNtpSetByMDM;
        public static boolean mNtpSetByMDM;
        public final Supplier mElapsedRealtimeMillisSupplier;
        public Long mLastRefreshAttemptElapsedRealtimeMillis;
        public final LocalLog mLocalDebugLog = new LocalLog(30, false);
        public int mNormalPollingIntervalMillis;
        public final NtpTrustedTime mNtpTrustedTime;
        public int mShortPollingIntervalMillis;
        public int mTryAgainCounter;
        public int mTryAgainTimesMax;

        public EngineImpl(Supplier supplier, int i, int i2, int i3, NtpTrustedTime ntpTrustedTime) {
            Objects.requireNonNull(supplier);
            this.mElapsedRealtimeMillisSupplier = supplier;
            if (i2 > i) {
                throw new IllegalArgumentException(
                        DualAppManagerService$$ExternalSyntheticOutline0.m(
                                i2,
                                i,
                                "shortPollingIntervalMillis (",
                                ") > normalPollingIntervalMillis (",
                                ")"));
            }
            this.mNormalPollingIntervalMillis = i;
            this.mShortPollingIntervalMillis = i2;
            this.mTryAgainTimesMax = i3;
            Objects.requireNonNull(ntpTrustedTime);
            this.mNtpTrustedTime = ntpTrustedTime;
        }

        public static String formatElapsedRealtimeMillis(long j) {
            StringBuilder sb = new StringBuilder();
            sb.append(Duration.ofMillis(j));
            sb.append(" (");
            return AudioConfig$$ExternalSyntheticOutline0.m(sb, j, ")");
        }

        public static void makeNetworkTimeSuggestion(
                NtpTrustedTime.TimeResult timeResult, String str, AnonymousClass1 anonymousClass1) {
            NetworkTimeSuggestion networkTimeSuggestion =
                    new NetworkTimeSuggestion(
                            new UnixEpochTime(
                                    timeResult.getElapsedRealtimeMillis(),
                                    timeResult.getTimeMillis()),
                            timeResult.getUncertaintyMillis());
            networkTimeSuggestion.addDebugInfo(str);
            networkTimeSuggestion.addDebugInfo(timeResult.toString());
            TimeDetectorInternalImpl timeDetectorInternalImpl =
                    (TimeDetectorInternalImpl) anonymousClass1.val$timeDetectorInternal;
            timeDetectorInternalImpl.getClass();
            timeDetectorInternalImpl.mHandler.post(
                    new TimeDetectorInternalImpl$$ExternalSyntheticLambda0(
                            timeDetectorInternalImpl, networkTimeSuggestion, 0));
        }

        public final void logToDebugAndDumpsys(String str) {
            this.mLocalDebugLog.log(str);
        }

        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void refreshAndRescheduleIfRequired(
                android.net.Network r25,
                java.lang.String r26,
                com.android.server.timedetector.NetworkTimeUpdateService.AnonymousClass1 r27) {
            /*
                Method dump skipped, instructions count: 426
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException(
                    "Method not decompiled:"
                        + " com.android.server.timedetector.NetworkTimeUpdateService.EngineImpl.refreshAndRescheduleIfRequired(android.net.Network,"
                        + " java.lang.String,"
                        + " com.android.server.timedetector.NetworkTimeUpdateService$1):void");
        }
    }

    public final class MDMReceiver extends BroadcastReceiver implements Runnable {
        public MDMReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v("NetworkTimeUpdateService", "onReceive + intent " + intent.getAction());
            if (!"com.samsung.android.knox.intent.action.UPDATE_NTP_PARAMETERS_INTERNAL"
                    .equals(action)) {
                if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                    NetworkTimeUpdateService.mBootCompleted = true;
                    return;
                }
                return;
            }
            NetworkTimeUpdateService networkTimeUpdateService = NetworkTimeUpdateService.this;
            EngineImpl engineImpl = networkTimeUpdateService.mEngine;
            Context context2 = networkTimeUpdateService.mContext;
            engineImpl.getClass();
            NtpInfo ntpInfo = null;
            try {
                IDateTimePolicy asInterface =
                        IDateTimePolicy.Stub.asInterface(
                                ServiceManager.getService("date_time_policy"));
                if (asInterface != null) {
                    ntpInfo = asInterface.getNtpInfo(new ContextInfo(Binder.getCallingUid()));
                }
            } catch (RemoteException unused) {
            }
            if (ntpInfo == null || ntpInfo.getServer() == null) {
                EngineImpl.mNtpSetByMDM = false;
                engineImpl.mNormalPollingIntervalMillis =
                        context2.getResources().getInteger(R.integer.config_screenBrightnessDoze);
                engineImpl.mShortPollingIntervalMillis =
                        context2.getResources()
                                .getInteger(R.integer.config_screenBrightnessSettingDefault);
                engineImpl.mTryAgainTimesMax =
                        context2.getResources()
                                .getInteger(R.integer.config_screenBrightnessSettingMaximum);
            } else {
                long pollingInterval = ntpInfo.getPollingInterval();
                if (0 != pollingInterval) {
                    engineImpl.mNormalPollingIntervalMillis = (int) pollingInterval;
                } else {
                    engineImpl.mNormalPollingIntervalMillis =
                            context2.getResources()
                                    .getInteger(R.integer.config_screenBrightnessDoze);
                }
                long pollingInterval2 = ntpInfo.getPollingInterval();
                if (0 != pollingInterval2) {
                    engineImpl.mShortPollingIntervalMillis = (int) pollingInterval2;
                } else {
                    engineImpl.mShortPollingIntervalMillis =
                            context2.getResources()
                                    .getInteger(R.integer.config_screenBrightnessSettingDefault);
                }
                int maxAttempts = ntpInfo.getMaxAttempts();
                if (maxAttempts != 0) {
                    engineImpl.mTryAgainTimesMax = maxAttempts;
                } else {
                    engineImpl.mTryAgainTimesMax =
                            context2.getResources()
                                    .getInteger(R.integer.config_screenBrightnessSettingMaximum);
                }
                EngineImpl.mNtpSetByMDM = true;
                engineImpl.mTryAgainCounter = 0;
            }
            NetworkTimeUpdateService.this.mHandler.post(this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            NetworkTimeUpdateService.m972$$Nest$monPollNetworkTime(
                    NetworkTimeUpdateService.this, "NTP_PARAMETERS_UPDATED");
        }
    }

    public final class NetworkConnectivityCallback extends ConnectivityManager.NetworkCallback {
        public NetworkConnectivityCallback() {}

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onAvailable(Network network) {
            NetworkTimeUpdateService networkTimeUpdateService;
            Log.d(
                    "NetworkTimeUpdateService",
                    String.format("New default network %s; checking time.", network));
            synchronized (NetworkTimeUpdateService.this.mLock) {
                networkTimeUpdateService = NetworkTimeUpdateService.this;
                networkTimeUpdateService.mDefaultNetwork = network;
            }
            NetworkTimeUpdateService.m972$$Nest$monPollNetworkTime(
                    networkTimeUpdateService, "network available");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public final void onLost(Network network) {
            synchronized (NetworkTimeUpdateService.this.mLock) {
                try {
                    if (network.equals(NetworkTimeUpdateService.this.mDefaultNetwork)) {
                        NetworkTimeUpdateService.this.mDefaultNetwork = null;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final class ScheduledRefreshAlarmListener
            implements AlarmManager.OnAlarmListener, Runnable {
        public ScheduledRefreshAlarmListener() {}

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            NetworkTimeUpdateService.this.mHandler.post(this);
        }

        @Override // java.lang.Runnable
        public final void run() {
            NetworkTimeUpdateService.m972$$Nest$monPollNetworkTime(
                    NetworkTimeUpdateService.this, "scheduled refresh");
        }
    }

    /* renamed from: -$$Nest$monPollNetworkTime, reason: not valid java name */
    public static void m972$$Nest$monPollNetworkTime(
            NetworkTimeUpdateService networkTimeUpdateService, String str) {
        Network network;
        synchronized (networkTimeUpdateService.mLock) {
            network = networkTimeUpdateService.mDefaultNetwork;
        }
        networkTimeUpdateService.mWakeLock.acquire();
        try {
            networkTimeUpdateService.mEngine.refreshAndRescheduleIfRequired(
                    network, str, networkTimeUpdateService.mRefreshCallbacks);
        } finally {
            networkTimeUpdateService.mWakeLock.release();
        }
    }

    public NetworkTimeUpdateService(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mCM = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mWakeLock =
                ((PowerManager) context.getSystemService(PowerManager.class))
                        .newWakeLock(1, "NetworkTimeUpdateService");
        NtpTrustedTime ntpTrustedTime = NtpTrustedTime.getInstance(context);
        this.mNtpTrustedTime = ntpTrustedTime;
        this.mEngine =
                new EngineImpl(
                        new NetworkTimeUpdateService$$ExternalSyntheticLambda0(),
                        context.getResources().getInteger(R.integer.config_screenBrightnessDoze),
                        context.getResources()
                                .getInteger(R.integer.config_screenBrightnessSettingDefault),
                        context.getResources()
                                .getInteger(R.integer.config_screenBrightnessSettingMaximum),
                        ntpTrustedTime);
        this.mRefreshCallbacks =
                new AnonymousClass1(
                        this,
                        (AlarmManager) context.getSystemService(AlarmManager.class),
                        (TimeDetectorInternal)
                                LocalServices.getService(TimeDetectorInternal.class));
        HandlerThread handlerThread = new HandlerThread("NetworkTimeUpdateService");
        handlerThread.start();
        this.mHandler = handlerThread.getThreadHandler();
        mMDMContext = context;
    }

    public static boolean isNtpSetByMDM() {
        return EngineImpl.mNtpSetByMDM;
    }

    public static boolean shallForceNtpMdmValues() {
        return EngineImpl.mForceNtpSetByMDM;
    }

    @Override // android.os.Binder
    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "NetworkTimeUpdateService", printWriter)) {
            synchronized (this.mLock) {
                printWriter.println("mDefaultNetwork=" + this.mDefaultNetwork);
            }
            EngineImpl engineImpl = this.mEngine;
            engineImpl.getClass();
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
            indentingPrintWriter.println(
                    "mNormalPollingIntervalMillis=" + engineImpl.mNormalPollingIntervalMillis);
            indentingPrintWriter.println(
                    "mShortPollingIntervalMillis=" + engineImpl.mShortPollingIntervalMillis);
            indentingPrintWriter.println("mTryAgainTimesMax=" + engineImpl.mTryAgainTimesMax);
            synchronized (engineImpl) {
                try {
                    Long l = engineImpl.mLastRefreshAttemptElapsedRealtimeMillis;
                    indentingPrintWriter.println(
                            "mLastRefreshAttemptElapsedRealtimeMillis="
                                    + (l == null
                                            ? "null"
                                            : EngineImpl.formatElapsedRealtimeMillis(
                                                    l.longValue())));
                    indentingPrintWriter.println("mTryAgainCounter=" + engineImpl.mTryAgainCounter);
                } catch (Throwable th) {
                    throw th;
                }
            }
            indentingPrintWriter.println();
            indentingPrintWriter.println("NtpTrustedTime:");
            indentingPrintWriter.increaseIndent();
            engineImpl.mNtpTrustedTime.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("Debug log:");
            indentingPrintWriter.increaseIndent();
            engineImpl.mLocalDebugLog.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("NTP set by MDM: " + EngineImpl.mNtpSetByMDM);
            printWriter.println();
        }
    }

    public final void onShellCommand(
            FileDescriptor fileDescriptor,
            FileDescriptor fileDescriptor2,
            FileDescriptor fileDescriptor3,
            String[] strArr,
            ShellCallback shellCallback,
            ResultReceiver resultReceiver) {
        new NetworkTimeUpdateServiceShellCommand(this)
                .exec(
                        this,
                        fileDescriptor,
                        fileDescriptor2,
                        fileDescriptor3,
                        strArr,
                        shellCallback,
                        resultReceiver);
    }

    public final void setServerConfigForTests(NtpTrustedTime.NtpConfig ntpConfig) {
        this.mContext.enforceCallingPermission(
                "android.permission.SET_TIME", "set NTP server config for tests");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mNtpTrustedTime.setServerConfigForTests(ntpConfig);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void systemRunning() {
        this.mCM.registerDefaultNetworkCallback(new NetworkConnectivityCallback(), this.mHandler);
        this.mContext.registerReceiver(
                new MDMReceiver(),
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        "com.samsung.android.knox.intent.action.UPDATE_NTP_PARAMETERS_INTERNAL",
                        "android.intent.action.BOOT_COMPLETED"));
        this.mContext
                .getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("auto_time"),
                        false,
                        new AutoTimeSettingObserver(this.mHandler, this.mContext));
    }
}
