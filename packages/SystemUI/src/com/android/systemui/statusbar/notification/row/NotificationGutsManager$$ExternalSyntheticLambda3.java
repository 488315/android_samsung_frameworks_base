package com.android.systemui.statusbar.notification.row;

import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.row.GutContentInitializer;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;

public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda3 implements GutContentInitializer.OnSettingsClickListener {
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ NotificationGuts f$1;
    public final /* synthetic */ StatusBarNotification f$2;
    public final /* synthetic */ ExpandableNotificationRow f$3;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda3(NotificationGutsManager notificationGutsManager, NotificationGuts notificationGuts, StatusBarNotification statusBarNotification, ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = notificationGutsManager;
        this.f$1 = notificationGuts;
        this.f$2 = statusBarNotification;
        this.f$3 = expandableNotificationRow;
    }

    @Override // com.android.systemui.statusbar.notification.row.GutContentInitializer.OnSettingsClickListener
    public void onClick() {
        StatusBarNotification statusBarNotification = this.f$2;
        NotificationGutsManager notificationGutsManager = this.f$0;
        notificationGutsManager.mMetricsLogger.action(205);
        this.f$1.resetFalsingCheck();
        notificationGutsManager.mOnSettingsClickListener.onSettingsClick(statusBarNotification.getKey());
        NotificationSAUtil.sendTypeLog(SystemUIAnalytics.EID_QPNE_GO_TO_SETTINGS_FROM_GUTS, this.f$3.mEntry);
    }
}
