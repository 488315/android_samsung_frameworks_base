package com.android.server.ibs.sleepmode;

import android.util.LocalLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SleepModeLogger {
    public boolean mIsUsed;
    public LocalLog mSleepModeLog;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class SleepModeLoggerHolder {
        public static final SleepModeLogger INSTANCE;

        static {
            SleepModeLogger sleepModeLogger = new SleepModeLogger();
            sleepModeLogger.mIsUsed = false;
            sleepModeLogger.mSleepModeLog = new LocalLog(3000);
            INSTANCE = sleepModeLogger;
        }
    }

    public final void add(String str) {
        if (!this.mIsUsed) {
            this.mIsUsed = true;
        }
        this.mSleepModeLog.log(str);
    }
}
