package com.android.server.accessibility;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.samsung.android.cover.CoverManager;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.context.SemContextApproach;
import com.samsung.android.hardware.context.SemContextApproachAttribute;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;

/* loaded from: classes.dex */
public class GestureWakeup {
  public static GestureWakeup sGesturewakeup;
  public Context mContext;
  public PowerManager mPM;
  public boolean mIsSettingGestureWakeUp = false;
  public CoverManager mCoverManager = null;
  public SemContextManager mSemContextManager = null;
  public SemContextListener mSemContextListener =
      new SemContextListener() { // from class: com.android.server.accessibility.GestureWakeup.1
        public void onSemContextChanged(SemContextEvent semContextEvent) {
          SemContextApproach approachContext = semContextEvent.getApproachContext();
          int currentUser = ActivityManager.getCurrentUser();
          int userID = approachContext.getUserID();
          if (semContextEvent.semContext.getType() == 1) {
            StringBuilder sb = new StringBuilder();
            sb.append("onSContextChanged() Approach, userId : ");
            sb.append(userID);
            sb.append(" , currentUser : ");
            sb.append(currentUser);
            sb.append(", setting : ");
            GestureWakeup gestureWakeup = GestureWakeup.this;
            sb.append(gestureWakeup.checkSettingCondition(gestureWakeup.mContext));
            Log.secD("GestureWakeup", sb.toString());
            GestureWakeup gestureWakeup2 = GestureWakeup.this;
            if (gestureWakeup2.checkSettingCondition(gestureWakeup2.mContext)
                && userID == currentUser) {
              GestureWakeup.this.launchGestureWakeup();
            }
          }
        }
      };

  public static synchronized GestureWakeup getInstance(Context context) {
    GestureWakeup gestureWakeup;
    synchronized (GestureWakeup.class) {
      if (sGesturewakeup == null) {
        sGesturewakeup = new GestureWakeup(context);
      }
      gestureWakeup = sGesturewakeup;
    }
    return gestureWakeup;
  }

  public GestureWakeup(Context context) {
    this.mContext = context;
  }

  public boolean StartGestureWakeup() {
    Log.d("GestureWakeup", "StartGestureWakeup()+");
    this.mIsSettingGestureWakeUp = checkSettingCondition(this.mContext);
    this.mPM = (PowerManager) this.mContext.getSystemService("power");
    if (this.mCoverManager == null) {
      this.mCoverManager = new CoverManager(this.mContext);
      Log.d("GestureWakeup", "StartGestureWakeup() create mCoverManager instance");
    }
    if (!registerApproachListener()) {
      Log.e("GestureWakeup", "StartGestureWakeup() Can't use proximity sensor in sensor hub");
      return false;
    }
    Settings.System.putIntForUser(this.mContext.getContentResolver(), "air_motion_wake_up", 1, -2);
    Log.d("GestureWakeup", "GestureWakeup start success");
    return true;
  }

  public boolean StopGestureWakeup() {
    Log.d("GestureWakeup", "StopGestureWakeup()+");
    boolean checkSettingCondition = checkSettingCondition(this.mContext);
    this.mIsSettingGestureWakeUp = checkSettingCondition;
    if (checkSettingCondition) {
      finishGestureWakeup();
      Log.d("GestureWakeup", "GestureWakeup stop success");
      return true;
    }
    Log.d("GestureWakeup", "GestureWakeup is aleady stopped");
    return false;
  }

  public final void finishGestureWakeup() {
    try {
      Settings.System.putIntForUser(
          this.mContext.getContentResolver(), "air_motion_wake_up", 0, -2);
      UnregisterApproachListener();
      this.mSemContextManager = null;
      this.mPM = null;
      Log.d("GestureWakeup", "finish");
    } catch (Throwable th) {
      th.printStackTrace();
    }
  }

  public final void launchGestureWakeup() {
    Log.d("GestureWakeup", "launchGestureWakeup()+");
    if (((SemDesktopModeManager) this.mContext.getSystemService("desktopmode")) != null
        && SemDesktopModeManager.isDesktopMode()) {
      Log.d("GestureWakeup", "launchGestureWakeup() :: No action in Desktop mode+");
      return;
    }
    TelephonyManager telephonyManager = (TelephonyManager) this.mContext.getSystemService("phone");
    if (telephonyManager.getCallState() == 2 || telephonyManager.getCallState() == 1) {
      Log.d(
          "GestureWakeup",
          "CALL_STATE_OFFHOOK or CALL_STATE_RINGING : " + telephonyManager.getCallState());
      return;
    }
    CoverState coverState = this.mCoverManager.getCoverState();
    if (coverState != null) {
      try {
        if (!coverState.getSwitchState()) {
          Log.d("GestureWakeup", "..Cover is closed ..");
          return;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    if (this.mPM == null) {
      this.mPM = (PowerManager) this.mContext.getSystemService("power");
    }
    this.mPM.semWakeUp(SystemClock.uptimeMillis(), 7, "WAKE_UP_REASON_GESTURE");
    insertLog(this.mContext, "com.samsung.android.app.airwakeupview", "ACC3");
  }

  public final boolean registerApproachListener() {
    int currentUser = ActivityManager.getCurrentUser();
    if (this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")) {
      if (this.mSemContextManager == null) {
        Log.d(
            "GestureWakeup", "registerApproachListener, mSemContextManager is null, create again ");
        this.mSemContextManager = (SemContextManager) this.mContext.getSystemService("scontext");
      }
      this.mSemContextManager.registerListener(
          this.mSemContextListener, 1, new SemContextApproachAttribute(currentUser));
      Log.d("GestureWakeup", "registerApproachListener, currentUser : " + currentUser);
      return true;
    }
    Log.e("GestureWakeup", "GestureWakeup: registerApproachListener fail");
    return false;
  }

  public final void UnregisterApproachListener() {
    SemContextManager semContextManager;
    if (!this.mContext.getPackageManager().hasSystemFeature("com.sec.feature.sensorhub")
        || (semContextManager = this.mSemContextManager) == null) {
      return;
    }
    semContextManager.unregisterListener(this.mSemContextListener, 1);
    Log.secD("GestureWakeup", "GestureWakeupService : UnregisterApproachListener");
  }

  public boolean checkSettingCondition(Context context) {
    boolean z;
    synchronized (this) {
      this.mIsSettingGestureWakeUp =
          Settings.System.getIntForUser(context.getContentResolver(), "air_motion_wake_up", 0, -2)
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

  public static void insertLog(Context context, String str, String str2) {
    if (SemFloatingFeature.getInstance()
        .getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")) {
      ContentValues contentValues = new ContentValues();
      contentValues.put("app_id", str);
      contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, str2);
      Intent intent = new Intent();
      intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
      intent.putExtra("data", contentValues);
      intent.setPackage("com.samsung.android.providers.context");
      context.sendBroadcast(intent);
    }
  }
}
