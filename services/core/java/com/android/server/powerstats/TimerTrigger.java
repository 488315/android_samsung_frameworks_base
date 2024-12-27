package com.android.server.powerstats;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.android.server.clipboard.ClipboardService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TimerTrigger extends PowerStatsLogTrigger {
    public final Handler mHandler;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PeriodicTimer implements Runnable, AlarmManager.OnAlarmListener {
        public final int mMsgType;
        public final long mPeriodMs;

        public PeriodicTimer(String str, long j, int i) {
            this.mPeriodMs = j;
            this.mMsgType = i;
        }

        @Override // android.app.AlarmManager.OnAlarmListener
        public final void onAlarm() {
            run();
        }

        @Override // java.lang.Runnable
        public final void run() {
            Flags.alarmBasedPowerstatsLogging();
            TimerTrigger.this.mHandler.postDelayed(this, this.mPeriodMs);
            TimerTrigger timerTrigger = TimerTrigger.this;
            Message.obtain(timerTrigger.mPowerStatsLogger, this.mMsgType).sendToTarget();
        }
    }

    public TimerTrigger(Context context, PowerStatsLogger powerStatsLogger) {
        super(context, powerStatsLogger);
        this.mHandler = context.getMainThreadHandler();
        PeriodicTimer periodicTimer = new PeriodicTimer("PowerStatsLowFreqLog", ClipboardService.DEFAULT_CLIPBOARD_TIMEOUT_MILLIS, 1);
        PeriodicTimer periodicTimer2 = new PeriodicTimer("PowerStatsHighFreqLog", 120000L, 2);
        periodicTimer.run();
        periodicTimer2.run();
    }
}
