package com.android.server.power.batterysaver;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.EventLog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class BatterySaverStateMachine$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BatterySaverStateMachine f$0;

    public /* synthetic */ BatterySaverStateMachine$$ExternalSyntheticLambda0(BatterySaverStateMachine batterySaverStateMachine, int i) {
        this.$r8$classId = i;
        this.f$0 = batterySaverStateMachine;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BatterySaverStateMachine batterySaverStateMachine = this.f$0;
                NotificationManager notificationManager = (NotificationManager) batterySaverStateMachine.mContext.getSystemService(NotificationManager.class);
                batterySaverStateMachine.ensureNotificationChannelExists(notificationManager, "dynamic_mode_notification", R.string.indeterminate_progress_37);
                Bundle bundle = new Bundle(1);
                bundle.putString(":settings:fragment_args_key", "battery_saver_schedule");
                Resources resources = batterySaverStateMachine.mContext.getResources();
                PendingIntent activity = PendingIntent.getActivity(batterySaverStateMachine.mContext, 0, new Intent("android.settings.BATTERY_SAVER_SETTINGS").setFlags(268468224).putExtra(":settings:show_fragment_args", bundle), 201326592);
                String string = resources.getString(R.string.indeterminate_progress_41);
                String string2 = resources.getString(R.string.indeterminate_progress_39);
                notificationManager.notifyAsUser("BatterySaverStateMachine", 1992, new Notification.Builder(batterySaverStateMachine.mContext, "dynamic_mode_notification").setSmallIcon(R.drawable.ic_chevron_start).setContentTitle(string).setContentText(string2).setContentIntent(activity).setStyle(new Notification.BigTextStyle().bigText(string2)).setOnlyAlertOnce(true).setAutoCancel(true).setTimeoutAfter(0L).build(), UserHandle.ALL);
                return;
            case 1:
                BatterySaverStateMachine batterySaverStateMachine2 = this.f$0;
                NotificationManager notificationManager2 = (NotificationManager) batterySaverStateMachine2.mContext.getSystemService(NotificationManager.class);
                batterySaverStateMachine2.ensureNotificationChannelExists(notificationManager2, "battery_saver_channel", R.string.config_defaultNetworkRecommendationProviderPackage);
                notificationManager2.notifyAsUser("BatterySaverStateMachine", 1993, batterySaverStateMachine2.buildNotification(R.string.config_defaultNetworkScorerPackageName, R.string.config_defaultNearbyFastPairSettingsDevicesComponent, BatterySaverStateMachine.STICKY_DISABLED_NOTIFY_TIMEOUT_MS, "battery_saver_channel"), UserHandle.ALL);
                return;
            case 2:
                EventLog.writeEvent(27392, this.f$0.mSettingBatterySaverTriggerThreshold);
                return;
            case 3:
                BatterySaverStateMachine batterySaverStateMachine3 = this.f$0;
                NotificationManager notificationManager3 = (NotificationManager) batterySaverStateMachine3.mContext.getSystemService(NotificationManager.class);
                batterySaverStateMachine3.ensureNotificationChannelExists(notificationManager3, "dynamic_mode_notification", R.string.indeterminate_progress_37);
                notificationManager3.notifyAsUser("BatterySaverStateMachine", 1992, batterySaverStateMachine3.buildNotification(R.string.indeterminate_progress_40, R.string.indeterminate_progress_38, 0L, "dynamic_mode_notification"), UserHandle.ALL);
                return;
            default:
                BatterySaverStateMachine batterySaverStateMachine4 = this.f$0;
                ContentResolver contentResolver = batterySaverStateMachine4.mContext.getContentResolver();
                contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_sticky"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_trigger_level"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("automatic_power_save_mode"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("dynamic_power_savings_enabled"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("dynamic_power_savings_disable_threshold"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_sticky_auto_disable_enabled"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power_sticky_auto_disable_level"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.System.getUriFor("emergency_mode"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                contentResolver.registerContentObserver(Settings.System.getUriFor("ultra_powersaving_mode"), false, batterySaverStateMachine4.mSettingsObserver, 0);
                synchronized (batterySaverStateMachine4.mLock) {
                    try {
                        if (batterySaverStateMachine4.getGlobalSetting("low_power_sticky", 0) != 0) {
                            batterySaverStateMachine4.mState = 5;
                        }
                        batterySaverStateMachine4.mBootCompleted = true;
                        batterySaverStateMachine4.refreshSettingsLocked();
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }
}
