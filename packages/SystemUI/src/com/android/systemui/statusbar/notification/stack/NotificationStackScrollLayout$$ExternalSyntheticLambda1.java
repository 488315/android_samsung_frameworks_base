package com.android.systemui.statusbar.notification.stack;

import android.content.ComponentName;
import android.content.Intent;
import android.view.HapticFeedbackConstants;
import android.view.View;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda1 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationStackScrollLayout f$0;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda1(NotificationStackScrollLayout notificationStackScrollLayout, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationStackScrollLayout;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
        switch (i) {
            case 0:
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                notificationStackScrollLayout.getClass();
                notificationStackScrollLayout.mActivityStarter.startActivity(new Intent().setComponent(new ComponentName(KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG, "com.android.settings.Settings$ZenModeSettingsActivity")).setAction("android.intent.action.MAIN").setFlags(268468224), true, true);
                break;
            case 1:
                notificationStackScrollLayout.mShelf.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(27));
                NotiCenterPlugin.INSTANCE.getClass();
                if (!NotiCenterPlugin.isNotiCenterPluginConnected() || !NotiCenterPlugin.noclearEnabled) {
                    notificationStackScrollLayout.clearNotifications(0, true);
                    break;
                } else {
                    notificationStackScrollLayout.clearNotifications(3, true);
                    break;
                }
            default:
                notificationStackScrollLayout.mActivityStarter.startActivity(notificationStackScrollLayout.mController.isHistoryEnabled() ? new Intent("android.settings.NOTIFICATION_HISTORY") : new Intent("android.settings.NOTIFICATION_SETTINGS"), true, true, 536870912);
                break;
        }
    }
}
