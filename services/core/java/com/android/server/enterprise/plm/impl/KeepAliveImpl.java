package com.android.server.enterprise.plm.impl;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.os.Process;
import android.util.Log;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.enterprise.plm.IStateDelegate;
import com.android.server.enterprise.plm.common.Utils;
import com.android.server.enterprise.plm.context.ProcessContext;

/* loaded from: classes2.dex */
public abstract class KeepAliveImpl {
    public static final String TAG = "KeepAliveImpl";
    public final Context mContext;
    public final ProcessContext mProcessContext;

    public abstract IBinder getBinder();

    public abstract int getProcessId();

    public abstract boolean isAlive();

    public abstract boolean needToCleanUpOnConditionNotMet();

    public abstract boolean needToKeepAlive(IStateDelegate iStateDelegate);

    public abstract void registerObserver(Handler handler, int i, int i2);

    public abstract boolean start();

    public abstract boolean stop();

    public abstract void unregisterObserver(Handler handler);

    public KeepAliveImpl(Context context, ProcessContext processContext) {
        this.mContext = context;
        this.mProcessContext = processContext;
    }

    public String getDisplayName() {
        return this.mProcessContext.getDisplayName();
    }

    public String getPackageName() {
        return this.mProcessContext.getPackageName();
    }

    public void cleanUpProcess(int i) {
        Process.killProcess(i);
    }

    public boolean hasPackage(String str) {
        return Utils.hasPackage(this.mContext, str);
    }

    public boolean isPackageEnabled(String str) {
        return Utils.getEnabledState(this.mContext, str) == 1;
    }

    public void setPackageEnabled(String str, boolean z) {
        Utils.setEnabledState(this.mContext, str, z);
    }

    public boolean startProcess() {
        String packageName = getPackageName();
        boolean z = false;
        try {
            if (hasPackage(packageName)) {
                boolean start = isPackageEnabled(packageName) ? start() : true;
                setPackageEnabled(packageName, true);
                z = start;
            }
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
        }
        Log.i(TAG, "start process " + packageName + " : " + z);
        return z;
    }

    public boolean stopProcess() {
        String packageName = getPackageName();
        boolean z = false;
        try {
            if (hasPackage(packageName)) {
                int processId = isAlive() ? getProcessId() : -1;
                boolean stop = isPackageEnabled(packageName) ? stop() : true;
                setPackageEnabled(packageName, false);
                if (processId != -1 && needToCleanUpOnConditionNotMet()) {
                    Log.i(TAG, "kill process " + packageName + XmlUtils.STRING_ARRAY_SEPARATOR + processId);
                    cleanUpProcess(processId);
                }
                z = stop;
            }
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
        }
        Log.i(TAG, "stop process " + packageName + " : " + z);
        return z;
    }

    public void changeAdjLevel(boolean z) {
        try {
            ActivityManager activityManager = (ActivityManager) this.mContext.getSystemService("activity");
            IBinder binder = getBinder();
            int processId = getProcessId();
            if (activityManager == null || binder == null || processId == -1) {
                return;
            }
            Log.i(TAG, "set " + getPackageName() + XmlUtils.STRING_ARRAY_SEPARATOR + processId + " important");
            activityManager.semSetProcessImportant(binder, processId, z);
        } catch (Throwable th) {
            Log.e(TAG, th.toString());
        }
    }
}
