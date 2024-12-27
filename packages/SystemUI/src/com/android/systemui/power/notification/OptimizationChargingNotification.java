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
import com.android.systemui.power.SecBatterySnapshot;
import com.android.systemui.util.NotificationChannels;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        bundle.putString("android.substName", this.mContext.getString(R.string.charging_notice_app_name));
        return getCommonBuilder(NotificationChannels.CHARGING, this.mContext.getString(R.string.battery_protection_noti_title), this.mContext.getString(R.string.opt_charging_noti_content, this.mFinishTime)).setSmallIcon(R.drawable.stat_notify_battery_protection).setGroup("CHARGING").setOnlyAlertOnce(true).setContentIntent(PowerUtils.pendingBroadcast(this.mContext, "PNW.batteryInfo")).setOngoing(true).addExtras(bundle).setCategory("sys").addAction(new Notification.Action.Builder((Icon) null, this.mContext.getString(R.string.opt_charging_noti_btn_text), PendingIntent.getBroadcastAsUser(this.mContext, 0, new Intent("com.samsung.android.sm.ACTION_OPTIMIZED_CHARGING_NOTI_DISMISSED").setPackage("android").setFlags(QuickStepContract.SYSUI_STATE_NAV_BAR_VIS_GONE), QuickStepContract.SYSUI_STATE_REQUESTED_RECENT_KEY, UserHandle.OWNER)).build());
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void setInformation(SecBatterySnapshot secBatterySnapshot) {
        this.mFinishTime = secBatterySnapshot.optimizationChargingFinishTime;
    }

    @Override // com.android.systemui.power.notification.PowerUiNotification
    public final void showNotification() {
        Notification.Builder builder = getBuilder();
        Notification.BigTextStyle bigTextStyle = new Notification.BigTextStyle();
        bigTextStyle.setBigContentTitle(this.mContext.getString(R.string.battery_protection_noti_title));
        bigTextStyle.bigText(this.mContext.getString(R.string.opt_charging_noti_content, this.mFinishTime));
        builder.setStyle(bigTextStyle);
        this.mNotificationManager.notifyAsUser("tag_optimization_charging_during_sleep", R.id.notification_power, builder.build(), UserHandle.ALL);
    }
}
