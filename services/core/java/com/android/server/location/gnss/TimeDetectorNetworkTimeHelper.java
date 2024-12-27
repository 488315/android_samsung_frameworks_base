package com.android.server.location.gnss;

import android.app.time.UnixEpochTime;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.LocalLog;
import android.util.Log;
import com.android.server.LocalServices;
import com.android.server.backup.BackupManagerConstants;
import com.android.server.location.gnss.NetworkTimeHelper;
import com.android.server.timedetector.NetworkTimeSuggestion;
import com.android.server.timedetector.TimeDetectorInternal;
import com.android.server.timedetector.TimeDetectorInternalImpl;
import com.android.server.timedetector.TimeDetectorStrategyImpl;
import com.android.server.timezonedetector.StateChangeListener;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class TimeDetectorNetworkTimeHelper extends NetworkTimeHelper {
    public static final boolean DEBUG = Log.isLoggable("TDNetworkTimeHelper", 3);
    static final int MAX_NETWORK_TIME_AGE_MILLIS = 86400000;
    public final LocalLog mDumpLog = new LocalLog(10, false);
    public final EnvironmentImpl mEnvironment;
    public final NetworkTimeHelper.InjectTimeCallback mInjectTimeCallback;
    public boolean mNetworkTimeInjected;
    public boolean mPeriodicTimeInjectionEnabled;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EnvironmentImpl {
        public final Handler mHandler;
        public final Object mScheduledRunnableToken = new Object();
        public final Object mImmediateRunnableToken = new Object();
        public final TimeDetectorInternal mTimeDetectorInternal = (TimeDetectorInternal) LocalServices.getService(TimeDetectorInternal.class);

        public EnvironmentImpl(Looper looper) {
            this.mHandler = new Handler(looper);
        }

        public final void requestImmediateTimeQueryCallback(final TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper, final String str) {
            synchronized (this) {
                this.mHandler.removeCallbacksAndMessages(this.mImmediateRunnableToken);
                this.mHandler.postDelayed(new Runnable() { // from class: com.android.server.location.gnss.TimeDetectorNetworkTimeHelper$EnvironmentImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        TimeDetectorNetworkTimeHelper.this.queryAndInjectNetworkTime(str);
                    }
                }, this.mImmediateRunnableToken, 0L);
            }
        }
    }

    public TimeDetectorNetworkTimeHelper(EnvironmentImpl environmentImpl, GnssLocationProviderSec gnssLocationProviderSec) {
        this.mInjectTimeCallback = gnssLocationProviderSec;
        this.mEnvironment = environmentImpl;
        StateChangeListener stateChangeListener = new StateChangeListener() { // from class: com.android.server.location.gnss.TimeDetectorNetworkTimeHelper$$ExternalSyntheticLambda0
            @Override // com.android.server.timezonedetector.StateChangeListener
            public final void onChange() {
                TimeDetectorNetworkTimeHelper timeDetectorNetworkTimeHelper = TimeDetectorNetworkTimeHelper.this;
                timeDetectorNetworkTimeHelper.mEnvironment.requestImmediateTimeQueryCallback(timeDetectorNetworkTimeHelper, "onNetworkTimeAvailable");
            }
        };
        TimeDetectorInternalImpl timeDetectorInternalImpl = (TimeDetectorInternalImpl) environmentImpl.mTimeDetectorInternal;
        timeDetectorInternalImpl.getClass();
        TimeDetectorStrategyImpl timeDetectorStrategyImpl = (TimeDetectorStrategyImpl) timeDetectorInternalImpl.mTimeDetectorStrategy;
        synchronized (timeDetectorStrategyImpl) {
            timeDetectorStrategyImpl.mNetworkTimeUpdateListeners.add(stateChangeListener);
        }
    }

    public final void maybeInjectNetworkTime(NetworkTimeSuggestion networkTimeSuggestion, String str) {
        long elapsedRealtime;
        if (networkTimeSuggestion == null) {
            elapsedRealtime = Long.MAX_VALUE;
        } else {
            long elapsedRealtimeMillis = networkTimeSuggestion.mUnixEpochTime.getElapsedRealtimeMillis();
            this.mEnvironment.getClass();
            elapsedRealtime = SystemClock.elapsedRealtime() - elapsedRealtimeMillis;
        }
        boolean z = DEBUG;
        if (elapsedRealtime > BackupManagerConstants.DEFAULT_FULL_BACKUP_INTERVAL_MILLISECONDS) {
            String str2 = "maybeInjectNetworkTime: Not injecting latest network time latestNetworkTime=" + networkTimeSuggestion + " reason=" + str;
            this.mDumpLog.log(str2);
            if (z) {
                Log.d("TDNetworkTimeHelper", str2);
                return;
            }
            return;
        }
        UnixEpochTime unixEpochTime = networkTimeSuggestion.mUnixEpochTime;
        long unixEpochTimeMillis = unixEpochTime.getUnixEpochTimeMillis();
        String str3 = "maybeInjectNetworkTime: Injecting latest network time latestNetworkTime=" + networkTimeSuggestion + " reason=" + str + " System time offset millis=" + (unixEpochTimeMillis - System.currentTimeMillis());
        this.mDumpLog.log(str3);
        if (z) {
            Log.d("TDNetworkTimeHelper", str3);
        }
        ((GnssLocationProvider) this.mInjectTimeCallback).mGnssNative.injectTime(unixEpochTimeMillis, unixEpochTime.getElapsedRealtimeMillis(), networkTimeSuggestion.mUncertaintyMillis);
        this.mNetworkTimeInjected = true;
    }

    public final synchronized void queryAndInjectNetworkTime(String str) {
        NetworkTimeSuggestion latestNetworkSuggestion = ((TimeDetectorStrategyImpl) ((TimeDetectorInternalImpl) this.mEnvironment.mTimeDetectorInternal).mTimeDetectorStrategy).getLatestNetworkSuggestion();
        maybeInjectNetworkTime(latestNetworkSuggestion, str);
        EnvironmentImpl environmentImpl = this.mEnvironment;
        synchronized (environmentImpl) {
            environmentImpl.mHandler.removeCallbacksAndMessages(environmentImpl.mScheduledRunnableToken);
        }
        if (this.mPeriodicTimeInjectionEnabled) {
            String str2 = "queryAndInjectNtpTime: Scheduling periodic query reason=" + str + " latestNetworkTime=" + latestNetworkSuggestion + " maxDelayMillis=86400000";
            this.mDumpLog.log(str2);
            if (DEBUG) {
                Log.d("TDNetworkTimeHelper", str2);
            }
            EnvironmentImpl environmentImpl2 = this.mEnvironment;
            long j = MAX_NETWORK_TIME_AGE_MILLIS;
            synchronized (environmentImpl2) {
                try {
                    synchronized (environmentImpl2) {
                        environmentImpl2.mHandler.removeCallbacksAndMessages(environmentImpl2.mScheduledRunnableToken);
                    }
                } finally {
                }
            }
            environmentImpl2.mHandler.postDelayed(new Runnable() { // from class: com.android.server.location.gnss.TimeDetectorNetworkTimeHelper$EnvironmentImpl$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    TimeDetectorNetworkTimeHelper.this.queryAndInjectNetworkTime("delayedTimeQueryCallback");
                }
            }, environmentImpl2.mScheduledRunnableToken, j);
        }
    }
}
