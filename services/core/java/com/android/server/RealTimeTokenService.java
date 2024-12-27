package com.android.server;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.util.Slog;

import java.util.Timer;
import java.util.TimerTask;

public final class RealTimeTokenService extends IRealTimeTokenService.Stub {
    public static Context mContext;
    public long bootingTime;
    public long currentTime;
    public long elapsedTime;
    public final AnonymousClass2 mBootCompletedReceiver;
    public final AnonymousClass2 mNetWorkReceiver;
    public final AnonymousClass2 mTimeChangedReceiver;
    public Timer mTimerObserve;
    public AnonymousClass1 mTimerTask;

    /* renamed from: -$$Nest$misNetworkAvailable, reason: not valid java name */
    public static boolean m78$$Nest$misNetworkAvailable(
            RealTimeTokenService realTimeTokenService, Context context) {
        realTimeTokenService.getClass();
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo =
                connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    static {
        System.loadLibrary("rtts_jni");
    }

    public RealTimeTokenService(Context context) {
        final int i = 0;
        BroadcastReceiver broadcastReceiver =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.RealTimeTokenService.2
                    public final /* synthetic */ RealTimeTokenService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i) {
                            case 0:
                                Slog.i(
                                        "RealTimeTokenService",
                                        "Broadcast NetWork received:" + intent.getAction());
                                try {
                                    if (RealTimeTokenService.m78$$Nest$misNetworkAvailable(
                                                    this.this$0, context2)
                                            || this.this$0.getActiveTokenNumber() <= 0) {
                                        Slog.i("RealTimeTokenService", "Stop RTTS Time Observer");
                                        try {
                                            Timer timer = this.this$0.mTimerObserve;
                                            if (timer != null) {
                                                timer.cancel();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        this.this$0.mTimerObserve = null;
                                        break;
                                    } else {
                                        Slog.i("RealTimeTokenService", "Start RTTS Time Observer");
                                        RealTimeTokenService realTimeTokenService = this.this$0;
                                        realTimeTokenService.getClass();
                                        realTimeTokenService.currentTime =
                                                System.currentTimeMillis();
                                        realTimeTokenService.elapsedTime =
                                                SystemClock.elapsedRealtime();
                                        try {
                                            final RealTimeTokenService realTimeTokenService2 =
                                                    this.this$0;
                                            if (realTimeTokenService2.mTimerObserve == null) {
                                                realTimeTokenService2.mTimerTask =
                                                        new TimerTask() { // from class:
                                                            // com.android.server.RealTimeTokenService.1
                                                            @Override // java.util.TimerTask,
                                                            // java.lang.Runnable
                                                            public final void run() {
                                                                Slog.i(
                                                                        "RealTimeTokenService",
                                                                        "Time up: Check all"
                                                                            + " tokens");
                                                                RealTimeTokenService.this
                                                                        .checkAllTokenExpiry();
                                                            }
                                                        };
                                                this.this$0.mTimerObserve = new Timer();
                                                RealTimeTokenService realTimeTokenService3 =
                                                        this.this$0;
                                                realTimeTokenService3.mTimerObserve.schedule(
                                                        realTimeTokenService3.mTimerTask,
                                                        0L,
                                                        21600000L);
                                                break;
                                            }
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                            return;
                                        }
                                    }
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                    return;
                                }
                                e3.printStackTrace();
                                break;
                            case 1:
                                intent.getAction();
                                Slog.i("RealTimeTokenService", "Time has changed");
                                RealTimeTokenService realTimeTokenService4 = this.this$0;
                                Context context3 = RealTimeTokenService.mContext;
                                realTimeTokenService4.getClass();
                                realTimeTokenService4.currentTime = System.currentTimeMillis();
                                realTimeTokenService4.elapsedTime = SystemClock.elapsedRealtime();
                                RealTimeTokenService realTimeTokenService5 = this.this$0;
                                long j =
                                        (realTimeTokenService5.bootingTime
                                                        + realTimeTokenService5.elapsedTime)
                                                / 1000;
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "setUserTime: ", j, "|");
                                m.append(this.this$0.bootingTime);
                                m.append("|");
                                m.append(this.this$0.elapsedTime);
                                Slog.i("RealTimeTokenService", m.toString());
                                int userTime = this.this$0.setUserTime(j);
                                if (userTime < 0) {
                                    NandswapManager$$ExternalSyntheticOutline0.m(
                                            userTime,
                                            "setUserTime failed: ",
                                            "RealTimeTokenService");
                                    break;
                                }
                                break;
                            default:
                                Slog.i("RealTimeTokenService", "Boot Completed Receiver");
                                RealTimeTokenService realTimeTokenService6 = this.this$0;
                                Context context4 = RealTimeTokenService.mContext;
                                realTimeTokenService6.getClass();
                                realTimeTokenService6.currentTime = System.currentTimeMillis();
                                realTimeTokenService6.elapsedTime = SystemClock.elapsedRealtime();
                                RealTimeTokenService realTimeTokenService7 = this.this$0;
                                realTimeTokenService7.bootingTime =
                                        realTimeTokenService7.currentTime
                                                - realTimeTokenService7.elapsedTime;
                                realTimeTokenService7.initTokenStorage();
                                if (!RealTimeTokenService.m78$$Nest$misNetworkAvailable(
                                                this.this$0, context2)
                                        && this.this$0.getActiveTokenNumber() > 0) {
                                    try {
                                        Timer timer2 = this.this$0.mTimerObserve;
                                        if (timer2 != null) {
                                            timer2.cancel();
                                            this.this$0.mTimerObserve = null;
                                        }
                                        final RealTimeTokenService realTimeTokenService8 =
                                                this.this$0;
                                        realTimeTokenService8.getClass();
                                        realTimeTokenService8.mTimerTask =
                                                new TimerTask() { // from class:
                                                    // com.android.server.RealTimeTokenService.1
                                                    @Override // java.util.TimerTask,
                                                    // java.lang.Runnable
                                                    public final void run() {
                                                        Slog.i(
                                                                "RealTimeTokenService",
                                                                "Time up: Check all tokens");
                                                        RealTimeTokenService.this
                                                                .checkAllTokenExpiry();
                                                    }
                                                };
                                        this.this$0.mTimerObserve = new Timer();
                                        RealTimeTokenService realTimeTokenService9 = this.this$0;
                                        realTimeTokenService9.mTimerObserve.schedule(
                                                realTimeTokenService9.mTimerTask, 0L, 21600000L);
                                        break;
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i2 = 1;
        BroadcastReceiver broadcastReceiver2 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.RealTimeTokenService.2
                    public final /* synthetic */ RealTimeTokenService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i2) {
                            case 0:
                                Slog.i(
                                        "RealTimeTokenService",
                                        "Broadcast NetWork received:" + intent.getAction());
                                try {
                                    if (RealTimeTokenService.m78$$Nest$misNetworkAvailable(
                                                    this.this$0, context2)
                                            || this.this$0.getActiveTokenNumber() <= 0) {
                                        Slog.i("RealTimeTokenService", "Stop RTTS Time Observer");
                                        try {
                                            Timer timer = this.this$0.mTimerObserve;
                                            if (timer != null) {
                                                timer.cancel();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        this.this$0.mTimerObserve = null;
                                        break;
                                    } else {
                                        Slog.i("RealTimeTokenService", "Start RTTS Time Observer");
                                        RealTimeTokenService realTimeTokenService = this.this$0;
                                        realTimeTokenService.getClass();
                                        realTimeTokenService.currentTime =
                                                System.currentTimeMillis();
                                        realTimeTokenService.elapsedTime =
                                                SystemClock.elapsedRealtime();
                                        try {
                                            final RealTimeTokenService realTimeTokenService2 =
                                                    this.this$0;
                                            if (realTimeTokenService2.mTimerObserve == null) {
                                                realTimeTokenService2.mTimerTask =
                                                        new TimerTask() { // from class:
                                                            // com.android.server.RealTimeTokenService.1
                                                            @Override // java.util.TimerTask,
                                                            // java.lang.Runnable
                                                            public final void run() {
                                                                Slog.i(
                                                                        "RealTimeTokenService",
                                                                        "Time up: Check all"
                                                                            + " tokens");
                                                                RealTimeTokenService.this
                                                                        .checkAllTokenExpiry();
                                                            }
                                                        };
                                                this.this$0.mTimerObserve = new Timer();
                                                RealTimeTokenService realTimeTokenService3 =
                                                        this.this$0;
                                                realTimeTokenService3.mTimerObserve.schedule(
                                                        realTimeTokenService3.mTimerTask,
                                                        0L,
                                                        21600000L);
                                                break;
                                            }
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                            return;
                                        }
                                    }
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                    return;
                                }
                                e3.printStackTrace();
                                break;
                            case 1:
                                intent.getAction();
                                Slog.i("RealTimeTokenService", "Time has changed");
                                RealTimeTokenService realTimeTokenService4 = this.this$0;
                                Context context3 = RealTimeTokenService.mContext;
                                realTimeTokenService4.getClass();
                                realTimeTokenService4.currentTime = System.currentTimeMillis();
                                realTimeTokenService4.elapsedTime = SystemClock.elapsedRealtime();
                                RealTimeTokenService realTimeTokenService5 = this.this$0;
                                long j =
                                        (realTimeTokenService5.bootingTime
                                                        + realTimeTokenService5.elapsedTime)
                                                / 1000;
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "setUserTime: ", j, "|");
                                m.append(this.this$0.bootingTime);
                                m.append("|");
                                m.append(this.this$0.elapsedTime);
                                Slog.i("RealTimeTokenService", m.toString());
                                int userTime = this.this$0.setUserTime(j);
                                if (userTime < 0) {
                                    NandswapManager$$ExternalSyntheticOutline0.m(
                                            userTime,
                                            "setUserTime failed: ",
                                            "RealTimeTokenService");
                                    break;
                                }
                                break;
                            default:
                                Slog.i("RealTimeTokenService", "Boot Completed Receiver");
                                RealTimeTokenService realTimeTokenService6 = this.this$0;
                                Context context4 = RealTimeTokenService.mContext;
                                realTimeTokenService6.getClass();
                                realTimeTokenService6.currentTime = System.currentTimeMillis();
                                realTimeTokenService6.elapsedTime = SystemClock.elapsedRealtime();
                                RealTimeTokenService realTimeTokenService7 = this.this$0;
                                realTimeTokenService7.bootingTime =
                                        realTimeTokenService7.currentTime
                                                - realTimeTokenService7.elapsedTime;
                                realTimeTokenService7.initTokenStorage();
                                if (!RealTimeTokenService.m78$$Nest$misNetworkAvailable(
                                                this.this$0, context2)
                                        && this.this$0.getActiveTokenNumber() > 0) {
                                    try {
                                        Timer timer2 = this.this$0.mTimerObserve;
                                        if (timer2 != null) {
                                            timer2.cancel();
                                            this.this$0.mTimerObserve = null;
                                        }
                                        final RealTimeTokenService realTimeTokenService8 =
                                                this.this$0;
                                        realTimeTokenService8.getClass();
                                        realTimeTokenService8.mTimerTask =
                                                new TimerTask() { // from class:
                                                    // com.android.server.RealTimeTokenService.1
                                                    @Override // java.util.TimerTask,
                                                    // java.lang.Runnable
                                                    public final void run() {
                                                        Slog.i(
                                                                "RealTimeTokenService",
                                                                "Time up: Check all tokens");
                                                        RealTimeTokenService.this
                                                                .checkAllTokenExpiry();
                                                    }
                                                };
                                        this.this$0.mTimerObserve = new Timer();
                                        RealTimeTokenService realTimeTokenService9 = this.this$0;
                                        realTimeTokenService9.mTimerObserve.schedule(
                                                realTimeTokenService9.mTimerTask, 0L, 21600000L);
                                        break;
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                break;
                        }
                    }
                };
        final int i3 = 2;
        BroadcastReceiver broadcastReceiver3 =
                new BroadcastReceiver(
                        this) { // from class: com.android.server.RealTimeTokenService.2
                    public final /* synthetic */ RealTimeTokenService this$0;

                    {
                        this.this$0 = this;
                    }

                    @Override // android.content.BroadcastReceiver
                    public final void onReceive(Context context2, Intent intent) {
                        switch (i3) {
                            case 0:
                                Slog.i(
                                        "RealTimeTokenService",
                                        "Broadcast NetWork received:" + intent.getAction());
                                try {
                                    if (RealTimeTokenService.m78$$Nest$misNetworkAvailable(
                                                    this.this$0, context2)
                                            || this.this$0.getActiveTokenNumber() <= 0) {
                                        Slog.i("RealTimeTokenService", "Stop RTTS Time Observer");
                                        try {
                                            Timer timer = this.this$0.mTimerObserve;
                                            if (timer != null) {
                                                timer.cancel();
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        this.this$0.mTimerObserve = null;
                                        break;
                                    } else {
                                        Slog.i("RealTimeTokenService", "Start RTTS Time Observer");
                                        RealTimeTokenService realTimeTokenService = this.this$0;
                                        realTimeTokenService.getClass();
                                        realTimeTokenService.currentTime =
                                                System.currentTimeMillis();
                                        realTimeTokenService.elapsedTime =
                                                SystemClock.elapsedRealtime();
                                        try {
                                            final RealTimeTokenService realTimeTokenService2 =
                                                    this.this$0;
                                            if (realTimeTokenService2.mTimerObserve == null) {
                                                realTimeTokenService2.mTimerTask =
                                                        new TimerTask() { // from class:
                                                            // com.android.server.RealTimeTokenService.1
                                                            @Override // java.util.TimerTask,
                                                            // java.lang.Runnable
                                                            public final void run() {
                                                                Slog.i(
                                                                        "RealTimeTokenService",
                                                                        "Time up: Check all"
                                                                            + " tokens");
                                                                RealTimeTokenService.this
                                                                        .checkAllTokenExpiry();
                                                            }
                                                        };
                                                this.this$0.mTimerObserve = new Timer();
                                                RealTimeTokenService realTimeTokenService3 =
                                                        this.this$0;
                                                realTimeTokenService3.mTimerObserve.schedule(
                                                        realTimeTokenService3.mTimerTask,
                                                        0L,
                                                        21600000L);
                                                break;
                                            }
                                        } catch (Exception e2) {
                                            e2.printStackTrace();
                                            return;
                                        }
                                    }
                                } catch (Exception e3) {
                                    e3.printStackTrace();
                                    return;
                                }
                                e3.printStackTrace();
                                break;
                            case 1:
                                intent.getAction();
                                Slog.i("RealTimeTokenService", "Time has changed");
                                RealTimeTokenService realTimeTokenService4 = this.this$0;
                                Context context3 = RealTimeTokenService.mContext;
                                realTimeTokenService4.getClass();
                                realTimeTokenService4.currentTime = System.currentTimeMillis();
                                realTimeTokenService4.elapsedTime = SystemClock.elapsedRealtime();
                                RealTimeTokenService realTimeTokenService5 = this.this$0;
                                long j =
                                        (realTimeTokenService5.bootingTime
                                                        + realTimeTokenService5.elapsedTime)
                                                / 1000;
                                StringBuilder m =
                                        BatteryService$$ExternalSyntheticOutline0.m(
                                                "setUserTime: ", j, "|");
                                m.append(this.this$0.bootingTime);
                                m.append("|");
                                m.append(this.this$0.elapsedTime);
                                Slog.i("RealTimeTokenService", m.toString());
                                int userTime = this.this$0.setUserTime(j);
                                if (userTime < 0) {
                                    NandswapManager$$ExternalSyntheticOutline0.m(
                                            userTime,
                                            "setUserTime failed: ",
                                            "RealTimeTokenService");
                                    break;
                                }
                                break;
                            default:
                                Slog.i("RealTimeTokenService", "Boot Completed Receiver");
                                RealTimeTokenService realTimeTokenService6 = this.this$0;
                                Context context4 = RealTimeTokenService.mContext;
                                realTimeTokenService6.getClass();
                                realTimeTokenService6.currentTime = System.currentTimeMillis();
                                realTimeTokenService6.elapsedTime = SystemClock.elapsedRealtime();
                                RealTimeTokenService realTimeTokenService7 = this.this$0;
                                realTimeTokenService7.bootingTime =
                                        realTimeTokenService7.currentTime
                                                - realTimeTokenService7.elapsedTime;
                                realTimeTokenService7.initTokenStorage();
                                if (!RealTimeTokenService.m78$$Nest$misNetworkAvailable(
                                                this.this$0, context2)
                                        && this.this$0.getActiveTokenNumber() > 0) {
                                    try {
                                        Timer timer2 = this.this$0.mTimerObserve;
                                        if (timer2 != null) {
                                            timer2.cancel();
                                            this.this$0.mTimerObserve = null;
                                        }
                                        final RealTimeTokenService realTimeTokenService8 =
                                                this.this$0;
                                        realTimeTokenService8.getClass();
                                        realTimeTokenService8.mTimerTask =
                                                new TimerTask() { // from class:
                                                    // com.android.server.RealTimeTokenService.1
                                                    @Override // java.util.TimerTask,
                                                    // java.lang.Runnable
                                                    public final void run() {
                                                        Slog.i(
                                                                "RealTimeTokenService",
                                                                "Time up: Check all tokens");
                                                        RealTimeTokenService.this
                                                                .checkAllTokenExpiry();
                                                    }
                                                };
                                        this.this$0.mTimerObserve = new Timer();
                                        RealTimeTokenService realTimeTokenService9 = this.this$0;
                                        realTimeTokenService9.mTimerObserve.schedule(
                                                realTimeTokenService9.mTimerTask, 0L, 21600000L);
                                        break;
                                    } catch (Exception e4) {
                                        e4.printStackTrace();
                                    }
                                }
                                break;
                        }
                    }
                };
        mContext = context;
        try {
            Slog.i("RealTimeTokenService", "register for NetWork Broadcasts");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            mContext.registerReceiver(broadcastReceiver, intentFilter);
            Slog.i("RealTimeTokenService", "register for Boot Completed Broadcasts");
            IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction("android.intent.action.BOOT_COMPLETED");
            mContext.registerReceiver(broadcastReceiver3, intentFilter2);
            Slog.i("RealTimeTokenService", "register for TimeChange Broadcasts");
            IntentFilter intentFilter3 = new IntentFilter();
            intentFilter3.addAction("android.intent.action.TIME_SET");
            intentFilter3.addAction("android.intent.action.TIMEZONE_CHANGED");
            intentFilter3.addAction("android.intent.action.DATE_CHANGED");
            mContext.registerReceiver(broadcastReceiver2, intentFilter3);
        } catch (Throwable th) {
            th.printStackTrace();
        }
    }

    public native int checkAllTokenExpiry();

    public native int checkTokenExpiry(long j);

    public final int checkTokenInfoExpiry(long j) {
        Slog.i("RealTimeTokenService", "IRealTimeTokenService - checkTokenExpiry");
        return checkTokenExpiry(j);
    }

    public native int getActiveTokenNumber();

    public native int initTokenStorage();

    public native int registerToken(long j, long j2);

    public final int registerTokenInfo(long j, long j2) {
        Slog.i("RealTimeTokenService", "IRealTimeTokenService - registerToken");
        return registerToken(j, j2);
    }

    public native int setUserTime(long j);

    public native int unregisterAllToken();

    public final int unregisterAllTokenInfo() {
        Slog.i("RealTimeTokenService", "IRealTimeTokenService - unregisterAllToken");
        return unregisterAllToken();
    }

    public native int unregisterToken(long j);

    public final int unregisterTokenInfo(long j) {
        Slog.i("RealTimeTokenService", "IRealTimeTokenService - unregisterToken");
        return unregisterToken(j);
    }
}
