package com.android.systemui.statusbar.notification.stack;

import android.content.ComponentName;
import android.content.Intent;
import android.view.HapticFeedbackConstants;
import android.view.View;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
            default:
                notificationStackScrollLayout.mShelf.performHapticFeedback(HapticFeedbackConstants.semGetVibrationIndex(27));
                NotiCenterPlugin.INSTANCE.getClass();
                if (!NotiCenterPlugin.isNotiCenterPluginConnected() || !NotiCenterPlugin.noclearEnabled) {
                    notificationStackScrollLayout.clearNotifications(0, true);
                    break;
                } else {
                    notificationStackScrollLayout.clearNotifications(3, true);
                    break;
                }
        }
    }
}
