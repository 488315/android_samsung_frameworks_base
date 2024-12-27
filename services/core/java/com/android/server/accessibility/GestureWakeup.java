package com.android.server.accessibility;

import android.app.ActivityManager;
import android.content.Context;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;

import com.samsung.android.cover.CoverManager;
import com.samsung.android.hardware.context.SemContextApproachAttribute;
import com.samsung.android.hardware.context.SemContextManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class GestureWakeup {
    public static GestureWakeup sGesturewakeup;
    public Context mContext;
    public CoverManager mCoverManager;
    public boolean mIsSettingGestureWakeUp;
    public PowerManager mPM;
    public AnonymousClass1 mSemContextListener;
    public SemContextManager mSemContextManager;

    public final boolean StartGestureWakeup() {
        Log.d("GestureWakeup", "StartGestureWakeup()+");
        this.mIsSettingGestureWakeUp = checkSettingCondition(this.mContext);
        this.mPM = (PowerManager) this.mContext.getSystemService("power");
        if (this.mCoverManager == null) {
            this.mCoverManager = new CoverManager(this.mContext);
            Log.d("GestureWakeup", "StartGestureWakeup() create mCoverManager instance");
        }
        int currentUser = ActivityManager.getCurrentUser();
        if (!this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
            Log.e("GestureWakeup", "GestureWakeup: registerApproachListener fail");
            Log.e("GestureWakeup", "StartGestureWakeup() Can't use proximity sensor in sensor hub");
            return false;
        }
        if (this.mSemContextManager == null) {
            Log.d(
                    "GestureWakeup",
                    "registerApproachListener, mSemContextManager is null, create again ");
            this.mSemContextManager =
                    (SemContextManager) this.mContext.getSystemService("scontext");
        }
        this.mSemContextManager.registerListener(
                this.mSemContextListener, 1, new SemContextApproachAttribute(currentUser));
        GestureWakeup$$ExternalSyntheticOutline0.m(
                new StringBuilder("registerApproachListener, currentUser : "),
                currentUser,
                "GestureWakeup");
        Settings.System.putIntForUser(
                this.mContext.getContentResolver(), "air_motion_wake_up", 1, -2);
        Log.d("GestureWakeup", "GestureWakeup start success");
        return true;
    }

    public final void StopGestureWakeup() {
        SemContextManager semContextManager;
        Log.d("GestureWakeup", "StopGestureWakeup()+");
        boolean checkSettingCondition = checkSettingCondition(this.mContext);
        this.mIsSettingGestureWakeUp = checkSettingCondition;
        if (!checkSettingCondition) {
            Log.d("GestureWakeup", "GestureWakeup is aleady stopped");
            return;
        }
        try {
            Settings.System.putIntForUser(
                    this.mContext.getContentResolver(), "air_motion_wake_up", 0, -2);
            if (this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")
                    && (semContextManager = this.mSemContextManager) != null) {
                semContextManager.unregisterListener(this.mSemContextListener, 1);
                Log.d("GestureWakeup", "GestureWakeupService : UnregisterApproachListener");
            }
            this.mSemContextManager = null;
            this.mPM = null;
            Log.d("GestureWakeup", "finish");
        } catch (Throwable th) {
            th.printStackTrace();
        }
        Log.d("GestureWakeup", "GestureWakeup stop success");
    }

    public final boolean checkSettingCondition(Context context) {
        boolean z;
        synchronized (this) {
            this.mIsSettingGestureWakeUp =
                    Settings.System.getIntForUser(
                                    context.getContentResolver(), "air_motion_wake_up", 0, -2)
                            == 1;
            Log.d(
                    "GestureWakeup",
                    "checkSettingCondition() mIsSettingGestureWakeUp = "
                            + this.mIsSettingGestureWakeUp
                            + " User mode : -2");
            z = this.mIsSettingGestureWakeUp;
        }
        return z;
    }
}
