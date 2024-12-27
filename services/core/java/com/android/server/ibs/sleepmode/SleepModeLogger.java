package com.android.server.ibs.sleepmode;

import android.util.LocalLog;

public final class SleepModeLogger {
    public boolean mIsUsed;
    public LocalLog mSleepModeLog;

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
