package com.android.systemui.power.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.UserHandle;
import com.android.systemui.R;
import com.android.systemui.power.PowerUtils;
import com.android.systemui.power.SecBatteryStatsSnapshot;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OptimizationChargingNotification extends PowerUiNotification {
    public String mFinishTime;

    public OptimizationChargingNotification(Context context) {
        super(context);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void dismissNotification() {
        this.mNotificationManager.cancelAsUser("tag_optimization_charging_during_sleep", R.id.notification_power, UserHandle.ALL);
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final Notification.Builder getBuilder() {
        Bundle bundle = new Bundle();
        Context context = this.mContext;
        bundle.putString("android.substName", context.getString(R.string.charging_notice_app_name));
        return getCommonBuilder(context.getString(R.string.battery_protection_noti_title), context.getString(R.string.opt_charging_noti_content, this.mFinishTime), "CHR").setSmallIcon(R.drawable.stat_notify_battery_protection).setGroup("CHARGING").setOnlyAlertOnce(true).setContentIntent(PowerUtils.pendingBroadcast(context, "PNW.batteryInfo")).setOngoing(true).addExtras(bundle).setCategory("sys").addAction(new Notification.Action.Builder((Icon) null, context.getString(R.string.opt_charging_noti_btn_text), PendingIntent.getBroadcastAsUser(context, 0, new Intent("com.samsung.android.sm.ACTION_OPTIMIZED_CHARGING_NOTI_DISMISSED").setPackage("android").setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, UserHandle.OWNER)).build());
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatteryStatsSnapshot secBatteryStatsSnapshot) {
        this.mFinishTime = secBatteryStatsSnapshot.optimizationChargingFinishTime;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        Notification.Builder builder = getBuilder();
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        Context context = this.mContext;
        bigTextStyle.setBigContentTitle(context.getString(R.string.battery_protection_noti_title));
        bigTextStyle.bigText(context.getString(R.string.opt_charging_noti_content, this.mFinishTime));
        builder.setStyle(bigTextStyle);
        this.mNotificationManager.notifyAsUser("tag_optimization_charging_during_sleep", R.id.notification_power, builder.build(), UserHandle.ALL);
    }
}
