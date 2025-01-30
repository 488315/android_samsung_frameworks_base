package com.android.server.timedetector;

import android.R;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.time.UnixEpochTime;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.IndentingPrintWriter;
import android.util.LocalLog;
import android.util.Log;
import android.util.NtpTrustedTime;
import com.android.internal.util.DumpUtils;
import com.android.server.LocalServices;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.datetime.IDateTimePolicy;
import com.samsung.android.knox.datetime.NtpInfo;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.time.Duration;
import java.util.Objects;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class NetworkTimeUpdateService extends Binder {
    public static boolean mBootCompleted = false;
    public static Context mMDMContext;
    public final ConnectivityManager mCM;
    public final Context mContext;
    public final Engine mEngine;
    public final Handler mHandler;
    public final NtpTrustedTime mNtpTrustedTime;
    public final Engine.RefreshCallbacks mRefreshCallbacks;
    public final PowerManager.WakeLock mWakeLock;
    public final Object mLock = new Object();
    public Network mDefaultNetwork = null;

    interface Engine {

        public interface RefreshCallbacks {
            void scheduleNextRefresh(long j);

            void submitSuggestion(NetworkTimeSuggestion networkTimeSuggestion);
        }

        void dump(PrintWriter printWriter);

        boolean forceRefreshForTests(Network network, RefreshCallbacks refreshCallbacks);

        void refreshAndRescheduleIfRequired(Network network, String str, RefreshCallbacks refreshCallbacks);

        void updateNTPParametersMDM(Context context);
    }

    public NetworkTimeUpdateService(Context context) {
        Objects.requireNonNull(context);
        this.mContext = context;
        this.mCM = (ConnectivityManager) context.getSystemService(ConnectivityManager.class);
        this.mWakeLock = ((PowerManager) context.getSystemService(PowerManager.class)).newWakeLock(1, "NetworkTimeUpdateService");
        NtpTrustedTime ntpTrustedTime = NtpTrustedTime.getInstance(context);
        this.mNtpTrustedTime = ntpTrustedTime;
        this.mEngine = new EngineImpl(new Supplier() { // from class: com.android.server.timedetector.NetworkTimeUpdateService$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return Long.valueOf(SystemClock.elapsedRealtime());
            }
        }, context.getResources().getInteger(R.integer.config_powerStatsAggregationPeriod), context.getResources().getInteger(R.integer.config_previousVibrationsDumpAggregationTimeMillisLimit), context.getResources().getInteger(R.integer.config_previousVibrationsDumpSizeLimit), ntpTrustedTime);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        final TimeDetectorInternal timeDetectorInternal = (TimeDetectorInternal) LocalServices.getService(TimeDetectorInternal.class);
        final PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, new Intent("com.android.server.timedetector.NetworkTimeUpdateService.action.POLL", (Uri) null).setPackage("android"), 67108864);
        this.mRefreshCallbacks = new Engine.RefreshCallbacks() { // from class: com.android.server.timedetector.NetworkTimeUpdateService.1
            @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks
            public void scheduleNextRefresh(long j) {
                alarmManager.cancel(broadcast);
                alarmManager.set(3, j, broadcast);
            }

            @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine.RefreshCallbacks
            public void submitSuggestion(NetworkTimeSuggestion networkTimeSuggestion) {
                timeDetectorInternal.suggestNetworkTime(networkTimeSuggestion);
            }
        };
        HandlerThread handlerThread = new HandlerThread("NetworkTimeUpdateService");
        handlerThread.start();
        this.mHandler = handlerThread.getThreadHandler();
        mMDMContext = context;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void systemRunning() {
        this.mContext.registerReceiver(new ScheduledRefreshBroadcastReceiver(), new IntentFilter("com.android.server.timedetector.NetworkTimeUpdateService.action.POLL"));
        registerMDMReceiver();
        this.mCM.registerDefaultNetworkCallback(new NetworkConnectivityCallback(), this.mHandler);
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("auto_time"), false, new AutoTimeSettingObserver(this.mHandler, this.mContext));
    }

    public void setServerConfigForTests(NtpTrustedTime.NtpConfig ntpConfig) {
        this.mContext.enforceCallingPermission("android.permission.SET_TIME", "set NTP server config for tests");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mNtpTrustedTime.setServerConfigForTests(ntpConfig);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public boolean forceRefreshForTests() {
        Network network;
        this.mContext.enforceCallingPermission("android.permission.SET_TIME", "force network time refresh");
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            synchronized (this.mLock) {
                network = this.mDefaultNetwork;
            }
            if (network != null) {
                return this.mEngine.forceRefreshForTests(network, this.mRefreshCallbacks);
            }
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void onPollNetworkTime(String str) {
        Network network;
        synchronized (this.mLock) {
            network = this.mDefaultNetwork;
        }
        this.mWakeLock.acquire();
        try {
            this.mEngine.refreshAndRescheduleIfRequired(network, str, this.mRefreshCallbacks);
        } finally {
            this.mWakeLock.release();
        }
    }

    public class ScheduledRefreshBroadcastReceiver extends BroadcastReceiver implements Runnable {
        public ScheduledRefreshBroadcastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            NetworkTimeUpdateService.this.mHandler.post(this);
        }

        @Override // java.lang.Runnable
        public void run() {
            NetworkTimeUpdateService.this.onPollNetworkTime("scheduled refresh");
        }
    }

    public class NetworkConnectivityCallback extends ConnectivityManager.NetworkCallback {
        public NetworkConnectivityCallback() {
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onAvailable(Network network) {
            Log.d("NetworkTimeUpdateService", String.format("New default network %s; checking time.", network));
            synchronized (NetworkTimeUpdateService.this.mLock) {
                NetworkTimeUpdateService.this.mDefaultNetwork = network;
            }
            NetworkTimeUpdateService.this.onPollNetworkTime("network available");
        }

        @Override // android.net.ConnectivityManager.NetworkCallback
        public void onLost(Network network) {
            synchronized (NetworkTimeUpdateService.this.mLock) {
                if (network.equals(NetworkTimeUpdateService.this.mDefaultNetwork)) {
                    NetworkTimeUpdateService.this.mDefaultNetwork = null;
                }
            }
        }
    }

    public class AutoTimeSettingObserver extends ContentObserver {
        public final Context mContext;

        public AutoTimeSettingObserver(Handler handler, Context context) {
            super(handler);
            Objects.requireNonNull(context);
            this.mContext = context;
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            if (isAutomaticTimeEnabled()) {
                NetworkTimeUpdateService.this.onPollNetworkTime("automatic time enabled");
            }
        }

        public final boolean isAutomaticTimeEnabled() {
            return Settings.Global.getInt(this.mContext.getContentResolver(), "auto_time", 0) != 0;
        }
    }

    @Override // android.os.Binder
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "NetworkTimeUpdateService", printWriter)) {
            synchronized (this.mLock) {
                printWriter.println("mDefaultNetwork=" + this.mDefaultNetwork);
            }
            this.mEngine.dump(printWriter);
            printWriter.println();
        }
    }

    public void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new NetworkTimeUpdateServiceShellCommand(this).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public static boolean shallForceNtpMdmValues() {
        return EngineImpl.mForceNtpSetByMDM;
    }

    public static boolean isNtpSetByMDM() {
        return EngineImpl.mNtpSetByMDM;
    }

    public class MDMReceiver extends BroadcastReceiver implements Runnable {
        public MDMReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.v("NetworkTimeUpdateService", "onReceive + intent " + intent.getAction());
            if ("com.samsung.android.knox.intent.action.UPDATE_NTP_PARAMETERS_INTERNAL".equals(action)) {
                NetworkTimeUpdateService.this.mEngine.updateNTPParametersMDM(NetworkTimeUpdateService.this.mContext);
                NetworkTimeUpdateService.this.mHandler.post(this);
            } else if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                NetworkTimeUpdateService.mBootCompleted = true;
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            NetworkTimeUpdateService.this.onPollNetworkTime("NTP_PARAMETERS_UPDATED");
        }
    }

    public void registerMDMReceiver() {
        MDMReceiver mDMReceiver = new MDMReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.samsung.android.knox.intent.action.UPDATE_NTP_PARAMETERS_INTERNAL");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        this.mContext.registerReceiver(mDMReceiver, intentFilter);
    }

    class EngineImpl implements Engine {
        public static boolean mForceNtpSetByMDM = false;
        public static boolean mNtpSetByMDM = false;
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
                throw new IllegalArgumentException(String.format("shortPollingIntervalMillis (%s) > normalPollingIntervalMillis (%s)", Integer.valueOf(i2), Integer.valueOf(i)));
            }
            this.mNormalPollingIntervalMillis = i;
            this.mShortPollingIntervalMillis = i2;
            this.mTryAgainTimesMax = i3;
            Objects.requireNonNull(ntpTrustedTime);
            this.mNtpTrustedTime = ntpTrustedTime;
        }

        @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine
        public boolean forceRefreshForTests(Network network, Engine.RefreshCallbacks refreshCallbacks) {
            boolean tryRefresh = tryRefresh(network);
            logToDebugAndDumpsys("forceRefreshForTests: refreshSuccessful=" + tryRefresh);
            if (tryRefresh) {
                makeNetworkTimeSuggestion(this.mNtpTrustedTime.getCachedTimeResult(), "EngineImpl.forceRefreshForTests()", refreshCallbacks);
            }
            return tryRefresh;
        }

        /* JADX WARN: Removed duplicated region for block: B:47:0x00e0 A[Catch: all -> 0x015f, TryCatch #1 {, blocks: (B:20:0x005f, B:23:0x0079, B:24:0x007c, B:26:0x0080, B:27:0x0083, B:29:0x008a, B:31:0x0092, B:32:0x00a2, B:34:0x00a9, B:35:0x00ab, B:37:0x00b0, B:38:0x00b3, B:40:0x00b7, B:41:0x00bc, B:43:0x00c1, B:44:0x00c5, B:47:0x00e0, B:48:0x00ed, B:49:0x0157, B:56:0x00c7, B:58:0x00cb, B:59:0x00d0, B:60:0x00ba), top: B:19:0x005f }] */
        @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public void refreshAndRescheduleIfRequired(Network network, String str, Engine.RefreshCallbacks refreshCallbacks) {
            boolean z;
            boolean tryRefresh;
            long j;
            long longValue;
            if (network == null) {
                logToDebugAndDumpsys("refreshIfRequiredAndReschedule: reason=" + str + ": No default network available. No refresh attempted and no next attempt scheduled.");
                return;
            }
            NtpTrustedTime.TimeResult cachedTimeResult = this.mNtpTrustedTime.getCachedTimeResult();
            synchronized (this) {
                long longValue2 = ((Long) this.mElapsedRealtimeMillisSupplier.get()).longValue();
                z = calculateTimeResultAgeMillis(cachedTimeResult, longValue2) >= ((long) this.mNormalPollingIntervalMillis) && isRefreshAllowed(longValue2);
            }
            if (z || str.equalsIgnoreCase("NTP_PARAMETERS_UPDATED")) {
                if (mNtpSetByMDM) {
                    mForceNtpSetByMDM = true;
                }
                tryRefresh = tryRefresh(network);
            } else {
                tryRefresh = false;
            }
            synchronized (this) {
                NtpTrustedTime.TimeResult cachedTimeResult2 = this.mNtpTrustedTime.getCachedTimeResult();
                long longValue3 = ((Long) this.mElapsedRealtimeMillisSupplier.get()).longValue();
                long calculateTimeResultAgeMillis = calculateTimeResultAgeMillis(cachedTimeResult2, longValue3);
                if (z) {
                    if (tryRefresh) {
                        this.mTryAgainCounter = 0;
                    } else {
                        int i = this.mTryAgainTimesMax;
                        if (i < 0) {
                            this.mTryAgainCounter = 1;
                        } else {
                            int i2 = this.mTryAgainCounter + 1;
                            this.mTryAgainCounter = i2;
                            if (i2 > i) {
                                this.mTryAgainCounter = 0;
                                if (NetworkTimeUpdateService.mBootCompleted) {
                                    NetworkTimeUpdateService.mMDMContext.sendBroadcast(new Intent("com.samsung.android.knox.intent.action.EVENT_NTP_SERVER_UNREACHABLE"), "com.samsung.android.knox.permission.KNOX_DATE_TIME");
                                }
                            }
                        }
                    }
                }
                int i3 = this.mNormalPollingIntervalMillis;
                if (calculateTimeResultAgeMillis < i3) {
                    this.mTryAgainCounter = 0;
                }
                if (calculateTimeResultAgeMillis < i3) {
                    makeNetworkTimeSuggestion(cachedTimeResult2, str, refreshCallbacks);
                }
                long j2 = this.mTryAgainCounter > 0 ? this.mShortPollingIntervalMillis : this.mNormalPollingIntervalMillis;
                if (calculateTimeResultAgeMillis < j2) {
                    longValue = cachedTimeResult2.getElapsedRealtimeMillis();
                } else {
                    Long l = this.mLastRefreshAttemptElapsedRealtimeMillis;
                    if (l != null) {
                        longValue = l.longValue();
                    } else {
                        Log.w("NetworkTimeUpdateService", "mLastRefreshAttemptElapsedRealtimeMillis unexpectedly missing. Scheduling using currentElapsedRealtimeMillis");
                        logToDebugAndDumpsys("mLastRefreshAttemptElapsedRealtimeMillis unexpectedly missing. Scheduling using currentElapsedRealtimeMillis");
                        j = longValue3 + j2;
                        if (j <= longValue3) {
                            Log.w("NetworkTimeUpdateService", "nextRefreshElapsedRealtimeMillis is a time in the past. Scheduling using currentElapsedRealtimeMillis instead");
                            logToDebugAndDumpsys("nextRefreshElapsedRealtimeMillis is a time in the past. Scheduling using currentElapsedRealtimeMillis instead");
                            j = longValue3 + j2;
                        }
                        refreshCallbacks.scheduleNextRefresh(j);
                        logToDebugAndDumpsys("refreshIfRequiredAndReschedule: network=" + network + ", reason=" + str + ", initialTimeResult=" + cachedTimeResult + ", shouldAttemptRefresh=" + z + ", refreshSuccessful=" + tryRefresh + ", currentElapsedRealtimeMillis=" + formatElapsedRealtimeMillis(longValue3) + ", latestTimeResult=" + cachedTimeResult2 + ", mTryAgainCounter=" + this.mTryAgainCounter + ", refreshAttemptDelayMillis=" + j2 + ", nextRefreshElapsedRealtimeMillis=" + formatElapsedRealtimeMillis(j));
                    }
                }
                j = longValue + j2;
                if (j <= longValue3) {
                }
                refreshCallbacks.scheduleNextRefresh(j);
                logToDebugAndDumpsys("refreshIfRequiredAndReschedule: network=" + network + ", reason=" + str + ", initialTimeResult=" + cachedTimeResult + ", shouldAttemptRefresh=" + z + ", refreshSuccessful=" + tryRefresh + ", currentElapsedRealtimeMillis=" + formatElapsedRealtimeMillis(longValue3) + ", latestTimeResult=" + cachedTimeResult2 + ", mTryAgainCounter=" + this.mTryAgainCounter + ", refreshAttemptDelayMillis=" + j2 + ", nextRefreshElapsedRealtimeMillis=" + formatElapsedRealtimeMillis(j));
            }
            if (mNtpSetByMDM) {
                mForceNtpSetByMDM = false;
            }
        }

        public static String formatElapsedRealtimeMillis(long j) {
            return Duration.ofMillis(j) + " (" + j + ")";
        }

        public static long calculateTimeResultAgeMillis(NtpTrustedTime.TimeResult timeResult, long j) {
            if (timeResult == null) {
                return Long.MAX_VALUE;
            }
            return timeResult.getAgeMillis(j);
        }

        public final boolean isRefreshAllowed(long j) {
            Long l = this.mLastRefreshAttemptElapsedRealtimeMillis;
            return l == null || j >= l.longValue() + ((long) this.mShortPollingIntervalMillis);
        }

        public final boolean tryRefresh(Network network) {
            long longValue = ((Long) this.mElapsedRealtimeMillisSupplier.get()).longValue();
            synchronized (this) {
                this.mLastRefreshAttemptElapsedRealtimeMillis = Long.valueOf(longValue);
            }
            return this.mNtpTrustedTime.forceRefresh(network);
        }

        public final void makeNetworkTimeSuggestion(NtpTrustedTime.TimeResult timeResult, String str, Engine.RefreshCallbacks refreshCallbacks) {
            NetworkTimeSuggestion networkTimeSuggestion = new NetworkTimeSuggestion(new UnixEpochTime(timeResult.getElapsedRealtimeMillis(), timeResult.getTimeMillis()), timeResult.getUncertaintyMillis());
            networkTimeSuggestion.addDebugInfo(str);
            networkTimeSuggestion.addDebugInfo(timeResult.toString());
            refreshCallbacks.submitSuggestion(networkTimeSuggestion);
        }

        @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine
        public void dump(PrintWriter printWriter) {
            IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
            indentingPrintWriter.println("mNormalPollingIntervalMillis=" + this.mNormalPollingIntervalMillis);
            indentingPrintWriter.println("mShortPollingIntervalMillis=" + this.mShortPollingIntervalMillis);
            indentingPrintWriter.println("mTryAgainTimesMax=" + this.mTryAgainTimesMax);
            synchronized (this) {
                Long l = this.mLastRefreshAttemptElapsedRealtimeMillis;
                indentingPrintWriter.println("mLastRefreshAttemptElapsedRealtimeMillis=" + (l == null ? "null" : formatElapsedRealtimeMillis(l.longValue())));
                indentingPrintWriter.println("mTryAgainCounter=" + this.mTryAgainCounter);
            }
            indentingPrintWriter.println();
            indentingPrintWriter.println("NtpTrustedTime:");
            indentingPrintWriter.increaseIndent();
            this.mNtpTrustedTime.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("Debug log:");
            indentingPrintWriter.increaseIndent();
            this.mLocalDebugLog.dump(indentingPrintWriter);
            indentingPrintWriter.decreaseIndent();
            indentingPrintWriter.println();
            indentingPrintWriter.println("NTP set by MDM: " + mNtpSetByMDM);
        }

        public final void logToDebugAndDumpsys(String str) {
            this.mLocalDebugLog.log(str);
        }

        @Override // com.android.server.timedetector.NetworkTimeUpdateService.Engine
        public void updateNTPParametersMDM(Context context) {
            NtpInfo ntpInfo = null;
            try {
                IDateTimePolicy asInterface = IDateTimePolicy.Stub.asInterface(ServiceManager.getService("date_time_policy"));
                if (asInterface != null) {
                    ntpInfo = asInterface.getNtpInfo(new ContextInfo(Binder.getCallingUid()));
                }
            } catch (RemoteException unused) {
            }
            if (ntpInfo != null && ntpInfo.getServer() != null) {
                long pollingInterval = ntpInfo.getPollingInterval();
                if (0 != pollingInterval) {
                    this.mNormalPollingIntervalMillis = (int) pollingInterval;
                } else {
                    this.mNormalPollingIntervalMillis = context.getResources().getInteger(R.integer.config_powerStatsAggregationPeriod);
                }
                long pollingInterval2 = ntpInfo.getPollingInterval();
                if (0 != pollingInterval2) {
                    this.mShortPollingIntervalMillis = (int) pollingInterval2;
                } else {
                    this.mShortPollingIntervalMillis = context.getResources().getInteger(R.integer.config_previousVibrationsDumpAggregationTimeMillisLimit);
                }
                int maxAttempts = ntpInfo.getMaxAttempts();
                if (maxAttempts != 0) {
                    this.mTryAgainTimesMax = maxAttempts;
                } else {
                    this.mTryAgainTimesMax = context.getResources().getInteger(R.integer.config_previousVibrationsDumpSizeLimit);
                }
                mNtpSetByMDM = true;
                this.mTryAgainCounter = 0;
                return;
            }
            mNtpSetByMDM = false;
            this.mNormalPollingIntervalMillis = context.getResources().getInteger(R.integer.config_powerStatsAggregationPeriod);
            this.mShortPollingIntervalMillis = context.getResources().getInteger(R.integer.config_previousVibrationsDumpAggregationTimeMillisLimit);
            this.mTryAgainTimesMax = context.getResources().getInteger(R.integer.config_previousVibrationsDumpSizeLimit);
        }
    }
}
