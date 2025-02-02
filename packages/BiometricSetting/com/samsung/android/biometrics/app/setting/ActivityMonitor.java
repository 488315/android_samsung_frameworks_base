package com.samsung.android.biometrics.app.setting;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/* loaded from: classes.dex */
public final class ActivityMonitor implements Application.ActivityLifecycleCallbacks {
    private static ActivityMonitor sInstance;
    private Activity mCurrentActivity;

    private ActivityMonitor() {
    }

    public static ActivityMonitor get() {
        if (sInstance == null) {
            synchronized (ActivityMonitor.class) {
                if (sInstance == null) {
                    sInstance = new ActivityMonitor();
                }
            }
        }
        return sInstance;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityDestroyed(Activity activity) {
        Log.i("BSS_ActivityMonitor", "onActivityDestroyed: " + activity);
        if (this.mCurrentActivity == activity) {
            this.mCurrentActivity = null;
        }
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStarted(Activity activity) {
        Log.i("BSS_ActivityMonitor", "onActivityStarted: " + activity);
        this.mCurrentActivity = activity;
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPaused(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityResumed(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityStopped(Activity activity) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivityPreCreated(Activity activity, Bundle bundle) {
    }

    @Override // android.app.Application.ActivityLifecycleCallbacks
    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
    }
}
