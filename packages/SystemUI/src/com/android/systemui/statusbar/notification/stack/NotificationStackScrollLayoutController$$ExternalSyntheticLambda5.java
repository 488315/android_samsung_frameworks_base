package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.ShelfToolTipManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.notification.footer.shared.FooterViewRefactor;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationStackScrollLayoutController$$ExternalSyntheticLambda5 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ NotificationStackScrollLayoutController$$ExternalSyntheticLambda5(NotificationStackScrollLayoutController notificationStackScrollLayoutController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationStackScrollLayoutController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        int i = this.$r8$classId;
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.f$0;
        switch (i) {
            case 0:
                notificationStackScrollLayoutController.getClass();
                FooterViewRefactor.assertInLegacyMode();
                FooterViewRefactor.assertInLegacyMode();
                notificationStackScrollLayoutController.mView.clearNotifications(2, true ^ notificationStackScrollLayoutController.hasNotifications(1, true));
                break;
            case 1:
                NotificationActivityStarter notificationActivityStarter = notificationStackScrollLayoutController.mNotificationActivityStarter;
                if (notificationActivityStarter != null) {
                    NotificationStackScrollLayout notificationStackScrollLayout = notificationStackScrollLayoutController.mView;
                    notificationStackScrollLayout.getClass();
                    FooterViewRefactor.assertInLegacyMode();
                    if (notificationStackScrollLayout.mFooterView != null) {
                        FooterViewRefactor.assertInLegacyMode();
                    }
                    ((StatusBarNotificationActivityStarter) notificationActivityStarter).startHistoryIntent(view, false);
                    break;
                }
                break;
            default:
                NotificationActivityStarter notificationActivityStarter2 = notificationStackScrollLayoutController.mNotificationActivityStarter;
                if (notificationActivityStarter2 != null) {
                    ((StatusBarNotificationActivityStarter) notificationActivityStarter2).startHistoryIntent(view, false);
                    ((ShelfToolTipManager) Dependency.sDependency.getDependencyInner(ShelfToolTipManager.class)).isTappedNotiSettings = true;
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_NOTIFICATION_SETTINGS_ON_FOOTER);
                break;
        }
    }
}
