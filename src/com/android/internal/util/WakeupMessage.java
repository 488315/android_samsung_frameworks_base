package com.android.internal.util;

import android.app.AlarmManager;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/* loaded from: classes4.dex */
public class WakeupMessage implements AlarmManager.OnAlarmListener {
    private final AlarmManager mAlarmManager;
    protected final int mArg1;
    protected final int mArg2;
    protected final int mCmd;
    protected final String mCmdName;
    protected final Handler mHandler;
    protected final Object mObj;
    private final Runnable mRunnable;
    private boolean mScheduled;

    public WakeupMessage(Context context, Handler handler, String str, int i, int i2, int i3, Object obj) {
        this.mAlarmManager = getAlarmManager(context);
        this.mHandler = handler;
        this.mCmdName = str;
        this.mCmd = i;
        this.mArg1 = i2;
        this.mArg2 = i3;
        this.mObj = obj;
        this.mRunnable = null;
    }

    public WakeupMessage(Context context, Handler handler, String str, int i, int i2) {
        this(context, handler, str, i, i2, 0, null);
    }

    public WakeupMessage(Context context, Handler handler, String str, int i, int i2, int i3) {
        this(context, handler, str, i, i2, i3, null);
    }

    public WakeupMessage(Context context, Handler handler, String str, int i) {
        this(context, handler, str, i, 0, 0, null);
    }

    public WakeupMessage(Context context, Handler handler, String str, Runnable runnable) {
        this.mAlarmManager = getAlarmManager(context);
        this.mHandler = handler;
        this.mCmdName = str;
        this.mCmd = 0;
        this.mArg1 = 0;
        this.mArg2 = 0;
        this.mObj = null;
        this.mRunnable = runnable;
    }

    private static AlarmManager getAlarmManager(Context context) {
        return (AlarmManager) context.getSystemService("alarm");
    }

    public synchronized void schedule(long j) throws Throwable {
        try {
            try {
                this.mAlarmManager.setExact(2, j, this.mCmdName, this, this.mHandler);
                this.mScheduled = true;
            } catch (Throwable th) {
                th = th;
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public synchronized void cancel() {
        if (this.mScheduled) {
            this.mAlarmManager.cancel(this);
            this.mScheduled = false;
        }
    }

    @Override // android.app.AlarmManager.OnAlarmListener
    public void onAlarm() {
        boolean z;
        Message messageObtain;
        synchronized (this) {
            z = this.mScheduled;
            this.mScheduled = false;
        }
        if (z) {
            Runnable runnable = this.mRunnable;
            if (runnable == null) {
                messageObtain = this.mHandler.obtainMessage(this.mCmd, this.mArg1, this.mArg2, this.mObj);
            } else {
                messageObtain = Message.obtain(this.mHandler, runnable);
            }
            this.mHandler.dispatchMessage(messageObtain);
            messageObtain.recycle();
        }
    }
}
