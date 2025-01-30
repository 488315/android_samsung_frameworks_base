package com.android.systemui.statusbar.notification.stack;

import android.content.Intent;
import android.view.View;
import com.android.systemui.noticenter.NotiCenterPlugin;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda0 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationStackScrollLayout f$0;

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda0(NotificationStackScrollLayout notificationStackScrollLayout, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationStackScrollLayout;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                NotificationStackScrollLayout notificationStackScrollLayout = this.f$0;
                notificationStackScrollLayout.mActivityStarter.startActivity(notificationStackScrollLayout.mController.isHistoryEnabled() ? new Intent("android.settings.NOTIFICATION_HISTORY") : new Intent("android.settings.NOTIFICATION_SETTINGS"), true, true, QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT);
                break;
            default:
                NotificationStackScrollLayout notificationStackScrollLayout2 = this.f$0;
                int i = NotificationStackScrollLayout.$r8$clinit;
                notificationStackScrollLayout2.getClass();
                NotiCenterPlugin.INSTANCE.getClass();
                if (!NotiCenterPlugin.isNotiCenterPluginConnected() || !NotiCenterPlugin.noclearEnabled) {
                    notificationStackScrollLayout2.clearNotifications(0, true);
                    break;
                } else {
                    notificationStackScrollLayout2.clearNotifications(3, true);
                    break;
                }
                break;
        }
    }
}
