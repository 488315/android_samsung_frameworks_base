package com.samsung.android.server.continuity.common;

import android.os.Handler;
import android.util.Log;

import com.samsung.android.server.continuity.autoswitch.AutoSwitchSettingHelper$$ExternalSyntheticLambda0;

import java.util.concurrent.ThreadPoolExecutor;

public abstract class ExecutorUtil {
    public static ThreadPoolExecutor sExecutorIO;
    public static Handler sHandler;

    public final class ThrowExceptionRunnable implements Runnable {
        public final Runnable mTask;

        public ThrowExceptionRunnable(
                AutoSwitchSettingHelper$$ExternalSyntheticLambda0
                        autoSwitchSettingHelper$$ExternalSyntheticLambda0) {
            this.mTask = autoSwitchSettingHelper$$ExternalSyntheticLambda0;
        }

        @Override // java.lang.Runnable
        public final void run() {
            try {
                this.mTask.run();
            } catch (Throwable th) {
                Log.w(
                        "[MCF_DS_SYS]_ExecutorUtil",
                        "run - Error in running "
                                + this.mTask.toString()
                                + " on "
                                + Thread.currentThread().getName()
                                + ", "
                                + th.getMessage());
                th.printStackTrace();
            }
        }
    }
}
