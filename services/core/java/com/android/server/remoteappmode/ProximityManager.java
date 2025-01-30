package com.android.server.remoteappmode;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.UserHandle;

/* loaded from: classes3.dex */
public class ProximityManager {
  public static final String TAG = "ProximityManager";
  public Context mContext;
  public Handler mHandler;
  public String mPackageName;
  public ProximityReceiver proximityReceiver = null;
  public SharedPreferences sharedPreferences;

  public ProximityManager(Context context, Handler handler) {
    this.mContext = context;
    this.mHandler = handler;
  }

  public void register() {
    if (isValid()) {
      this.mPackageName = getTargetPackageName();
      registerBroadcastReceiver();
    }
  }

  public final boolean isValid() {
    if (getProximityEnabled() && getExpiredTime() != 0) {
      return getExpiredTime() == -1 || System.currentTimeMillis() < getExpiredTime();
    }
    return false;
  }

  public void enableSendingUserPresentIntent(String str) {
    Log.m81d(TAG, "registerProximityReceiver()");
    setTargetPackageName(str);
    this.mPackageName = str;
    setProximityEnabled(true);
    if (isValid()) {
      registerBroadcastReceiver();
    }
  }

  public final synchronized void registerBroadcastReceiver() {
    if (this.proximityReceiver == null) {
      ProximityReceiver proximityReceiver = new ProximityReceiver();
      this.proximityReceiver = proximityReceiver;
      proximityReceiver.register();
    }
  }

  public synchronized void disableSendingUserPresentIntent() {
    Log.m81d(TAG, "unregisterProximityReceiver()");
    setProximityEnabled(false);
    ProximityReceiver proximityReceiver = this.proximityReceiver;
    if (proximityReceiver != null) {
      proximityReceiver.unregister();
      this.proximityReceiver = null;
    }
  }

  public synchronized boolean isSendingUserPresentEnabled() {
    return this.proximityReceiver != null;
  }

  public final void ensureInitSharedPreference() {
    if (this.sharedPreferences == null) {
      this.sharedPreferences = this.mContext.getSharedPreferences("remote_app_mode_prefs", 0);
    }
  }

  public long getExpiredTime() {
    ensureInitSharedPreference();
    return this.sharedPreferences.getLong("ltw_proximity_expired_time", 0L);
  }

  public void setExpiredTime(long j) {
    ensureInitSharedPreference();
    SharedPreferences.Editor edit = this.sharedPreferences.edit();
    edit.putLong("ltw_proximity_expired_time", j);
    edit.commit();
  }

  public final String getTargetPackageName() {
    ensureInitSharedPreference();
    return this.sharedPreferences.getString("target_package_name", null);
  }

  public final void setTargetPackageName(String str) {
    ensureInitSharedPreference();
    SharedPreferences.Editor edit = this.sharedPreferences.edit();
    edit.putString("target_package_name", str);
    edit.commit();
  }

  public final boolean getProximityEnabled() {
    ensureInitSharedPreference();
    return this.sharedPreferences.getBoolean("ltw_proximity_enabled", false);
  }

  public final void setProximityEnabled(boolean z) {
    ensureInitSharedPreference();
    SharedPreferences.Editor edit = this.sharedPreferences.edit();
    edit.putBoolean("ltw_proximity_enabled", z);
    edit.commit();
  }

  public final void unregisterReceiverWhenActivationTimeEnds() {
    if (isValid()) {
      return;
    }
    disableSendingUserPresentIntent();
  }

  public class ProximityReceiver extends BroadcastReceiver {
    public ProximityReceiver() {}

    public void register() {
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction("android.intent.action.USER_PRESENT");
      intentFilter.addAction("android.intent.action.SCREEN_OFF");
      ProximityManager proximityManager = ProximityManager.this;
      proximityManager.mContext.registerReceiverAsUser(
          this, UserHandle.ALL, intentFilter, null, proximityManager.mHandler);
    }

    public void unregister() {
      try {
        ProximityManager.this.mContext.unregisterReceiver(this);
      } catch (IllegalArgumentException e) {
        Log.m81d(ProximityManager.TAG, e.getMessage());
      }
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
      String action = intent.getAction();
      Log.m81d(ProximityManager.TAG, "onReceive(), action=" + action);
      if (ProximityManager.this.mPackageName != null) {
        if ("android.intent.action.USER_PRESENT".equals(action)) {
          ProximityManager.this.sendIntent(
              context, "com.samsung.android.mdx.proximity.ACTION_USER_PRESENT");
        } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
          ProximityManager.this.sendIntent(
              context, "com.samsung.android.mdx.proximity.ACTION_SCREEN_OFF");
        }
      }
      ProximityManager.this.unregisterReceiverWhenActivationTimeEnds();
    }
  }

  public final void sendIntent(Context context, String str) {
    Intent intent = new Intent();
    intent.setAction(str);
    intent.addFlags(32);
    for (ResolveInfo resolveInfo : context.getPackageManager().queryBroadcastReceivers(intent, 0)) {
      if (resolveInfo.activityInfo.packageName.equals(this.mPackageName)) {
        ActivityInfo activityInfo = resolveInfo.activityInfo;
        intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
        context.sendBroadcast(intent);
      }
    }
  }
}
