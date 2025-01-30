package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.util.Slog;
import com.android.server.IRealTimeTokenService;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public final class RealTimeTokenService extends IRealTimeTokenService.Stub {
    public static Context mContext;
    public long bootingTime;
    public long currentTime;
    public long elapsedTime;
    public Timer mTimerObserve;
    public TimerTask mTimerTask;
    public final BroadcastReceiver mNetWorkReceiver = new BroadcastReceiver() { // from class: com.android.server.RealTimeTokenService.2
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.i("RealTimeTokenService", "Broadcast NetWork received:" + intent.getAction());
            try {
                if (!RealTimeTokenService.this.isNetworkAvailable(context) && RealTimeTokenService.this.getActiveTokenNumber() > 0) {
                    Slog.i("RealTimeTokenService", "Start RTTS Time Observer");
                    RealTimeTokenService.this.getSystemTime();
                    try {
                        if (RealTimeTokenService.this.mTimerObserve == null) {
                            RealTimeTokenService.this.makeTimerTask();
                            RealTimeTokenService.this.mTimerObserve = new Timer();
                            RealTimeTokenService.this.mTimerObserve.schedule(RealTimeTokenService.this.mTimerTask, 0L, 21600000L);
                            return;
                        }
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                Slog.i("RealTimeTokenService", "Stop RTTS Time Observer");
                try {
                    if (RealTimeTokenService.this.mTimerObserve != null) {
                        RealTimeTokenService.this.mTimerObserve.cancel();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
                RealTimeTokenService.this.mTimerObserve = null;
                return;
            } catch (Exception e3) {
                e3.printStackTrace();
            }
            e3.printStackTrace();
        }
    };
    public final BroadcastReceiver mTimeChangedReceiver = new BroadcastReceiver() { // from class: com.android.server.RealTimeTokenService.3
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            intent.getAction();
            Slog.i("RealTimeTokenService", "Time has changed");
            RealTimeTokenService.this.getSystemTime();
            long j = (RealTimeTokenService.this.bootingTime + RealTimeTokenService.this.elapsedTime) / 1000;
            Slog.i("RealTimeTokenService", "setUserTime: " + j + "|" + RealTimeTokenService.this.bootingTime + "|" + RealTimeTokenService.this.elapsedTime);
            int userTime = RealTimeTokenService.this.setUserTime(j);
            if (userTime < 0) {
                Slog.e("RealTimeTokenService", "setUserTime failed: " + userTime);
            }
        }
    };
    public final BroadcastReceiver mBootCompletedReceiver = new BroadcastReceiver() { // from class: com.android.server.RealTimeTokenService.4
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Slog.i("RealTimeTokenService", "Boot Completed Receiver");
            RealTimeTokenService.this.getSystemTime();
            RealTimeTokenService realTimeTokenService = RealTimeTokenService.this;
            realTimeTokenService.bootingTime = realTimeTokenService.currentTime - RealTimeTokenService.this.elapsedTime;
            RealTimeTokenService.this.initTokenStorage();
            if (RealTimeTokenService.this.isNetworkAvailable(context) || RealTimeTokenService.this.getActiveTokenNumber() <= 0) {
                return;
            }
            try {
                if (RealTimeTokenService.this.mTimerObserve != null) {
                    RealTimeTokenService.this.mTimerObserve.cancel();
                    RealTimeTokenService.this.mTimerObserve = null;
                }
                RealTimeTokenService.this.makeTimerTask();
                RealTimeTokenService.this.mTimerObserve = new Timer();
                RealTimeTokenService.this.mTimerObserve.schedule(RealTimeTokenService.this.mTimerTask, 0L, 21600000L);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public native int checkAllTokenExpiry();

    public native int checkTokenExpiry(long j);

    public native int getActiveTokenNumber();

    public native int initTokenStorage();

    public native int registerToken(long j, long j2);

    public native int setUserTime(long j);

    public native int unregisterAllToken();

    public native int unregisterToken(long j);

    static {
        System.loadLibrary("rtts_jni");
    }

    public RealTimeTokenService(Context context) {
        setContext(context);
        try {
            registerForNetWorkBroadcasts();
            registerForBootCompleteds();
            registerForTimeChangeBroadcasts();
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public static void setContext(Context context) {
        mContext = context;
    }

    public int registerTokenInfo(long j, long j2) {
        Slog.i("RealTimeTokenService", "IRealTimeTokenService - registerToken");
        return registerToken(j, j2);
    }

    public int checkTokenInfoExpiry(long j) {
        Slog.i("RealTimeTokenService", "IRealTimeTokenService - checkTokenExpiry");
        return checkTokenExpiry(j);
    }

    public int unregisterTokenInfo(long j) {
        Slog.i("RealTimeTokenService", "IRealTimeTokenService - unregisterToken");
        return unregisterToken(j);
    }

    public int unregisterAllTokenInfo() {
        Slog.i("RealTimeTokenService", "IRealTimeTokenService - unregisterAllToken");
        return unregisterAllToken();
    }

    public final void getSystemTime() {
        this.currentTime = System.currentTimeMillis();
        this.elapsedTime = SystemClock.elapsedRealtime();
    }

    public void makeTimerTask() {
        this.mTimerTask = new TimerTask() { // from class: com.android.server.RealTimeTokenService.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                Slog.i("RealTimeTokenService", "Time up: Check all tokens");
                RealTimeTokenService.this.checkAllTokenExpiry();
            }
        };
    }

    public final boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public final void registerForNetWorkBroadcasts() {
        Slog.i("RealTimeTokenService", "register for NetWork Broadcasts");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        mContext.registerReceiver(this.mNetWorkReceiver, intentFilter);
    }

    public final void registerForBootCompleteds() {
        Slog.i("RealTimeTokenService", "register for Boot Completed Broadcasts");
        IntentFilter intentFilter = new IntentFilter("noConnectivity");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        mContext.registerReceiver(this.mBootCompletedReceiver, intentFilter);
    }

    public final void registerForTimeChangeBroadcasts() {
        Slog.i("RealTimeTokenService", "register for TimeChange Broadcasts");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("android.intent.action.DATE_CHANGED");
        mContext.registerReceiver(this.mTimeChangedReceiver, intentFilter);
    }
}
