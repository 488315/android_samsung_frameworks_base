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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProximityManager {
    public Context mContext;
    public Handler mHandler;
    public String mPackageName;
    public ProximityReceiver proximityReceiver;
    public SharedPreferences sharedPreferences;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ProximityReceiver extends BroadcastReceiver {
        public ProximityReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("ProximityManager", "onReceive(), action=" + action);
            if (ProximityManager.this.mPackageName != null) {
                if ("android.intent.action.USER_PRESENT".equals(action)) {
                    ProximityManager.m854$$Nest$msendIntent(
                            ProximityManager.this,
                            context,
                            "com.samsung.android.mdx.proximity.ACTION_USER_PRESENT");
                } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                    ProximityManager.m854$$Nest$msendIntent(
                            ProximityManager.this,
                            context,
                            "com.samsung.android.mdx.proximity.ACTION_SCREEN_OFF");
                }
            }
            ProximityManager proximityManager = ProximityManager.this;
            if (proximityManager.isValid()) {
                return;
            }
            proximityManager.disableSendingUserPresentIntent();
        }
    }

    /* renamed from: -$$Nest$msendIntent, reason: not valid java name */
    public static void m854$$Nest$msendIntent(
            ProximityManager proximityManager, Context context, String str) {
        proximityManager.getClass();
        Intent intent = new Intent();
        intent.setAction(str);
        intent.addFlags(32);
        for (ResolveInfo resolveInfo :
                context.getPackageManager().queryBroadcastReceivers(intent, 0)) {
            if (resolveInfo.activityInfo.packageName.equals(proximityManager.mPackageName)) {
                ActivityInfo activityInfo = resolveInfo.activityInfo;
                intent.setComponent(new ComponentName(activityInfo.packageName, activityInfo.name));
                context.sendBroadcast(intent);
            }
        }
    }

    public final synchronized void disableSendingUserPresentIntent() {
        Log.d("ProximityManager", "unregisterProximityReceiver()");
        ensureInitSharedPreference();
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putBoolean("ltw_proximity_enabled", false);
        edit.commit();
        ProximityReceiver proximityReceiver = this.proximityReceiver;
        if (proximityReceiver != null) {
            try {
                ProximityManager.this.mContext.unregisterReceiver(proximityReceiver);
            } catch (IllegalArgumentException e) {
                Log.d("ProximityManager", e.getMessage());
            }
            this.proximityReceiver = null;
        }
    }

    public final void enableSendingUserPresentIntent(String str) {
        Log.d("ProximityManager", "registerProximityReceiver()");
        ensureInitSharedPreference();
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putString("target_package_name", str);
        edit.commit();
        this.mPackageName = str;
        ensureInitSharedPreference();
        SharedPreferences.Editor edit2 = this.sharedPreferences.edit();
        edit2.putBoolean("ltw_proximity_enabled", true);
        edit2.commit();
        if (isValid()) {
            registerBroadcastReceiver();
        }
    }

    public final void ensureInitSharedPreference() {
        if (this.sharedPreferences == null) {
            this.sharedPreferences = this.mContext.getSharedPreferences("remote_app_mode_prefs", 0);
        }
    }

    public final long getExpiredTime() {
        ensureInitSharedPreference();
        return this.sharedPreferences.getLong("ltw_proximity_expired_time", 0L);
    }

    public final boolean isValid() {
        ensureInitSharedPreference();
        if (this.sharedPreferences.getBoolean("ltw_proximity_enabled", false)
                && getExpiredTime() != 0) {
            return getExpiredTime() == -1 || System.currentTimeMillis() < getExpiredTime();
        }
        return false;
    }

    public final synchronized void registerBroadcastReceiver() {
        if (this.proximityReceiver == null) {
            ProximityReceiver proximityReceiver = new ProximityReceiver();
            this.proximityReceiver = proximityReceiver;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.mContext.registerReceiverAsUser(
                    proximityReceiver, UserHandle.ALL, intentFilter, null, this.mHandler);
        }
    }
}
