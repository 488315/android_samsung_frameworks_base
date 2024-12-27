package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.service.notification.StatusBarNotification;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ NotificationGuts f$1;
    public final /* synthetic */ StatusBarNotification f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ ExpandableNotificationRow f$4;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda1(NotificationGutsManager notificationGutsManager, NotificationGuts notificationGuts, StatusBarNotification statusBarNotification, String str, ExpandableNotificationRow expandableNotificationRow, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationGutsManager;
        this.f$1 = notificationGuts;
        this.f$2 = statusBarNotification;
        this.f$3 = str;
        this.f$4 = expandableNotificationRow;
    }

    public void onClick(NotificationChannel notificationChannel, int i) {
        switch (this.$r8$classId) {
            case 0:
                StatusBarNotification statusBarNotification = this.f$2;
                NotificationGutsManager notificationGutsManager = this.f$0;
                notificationGutsManager.mMetricsLogger.action(205);
                this.f$1.resetFalsingCheck();
                notificationGutsManager.mOnSettingsClickListener.onSettingsClick(statusBarNotification.getKey());
                notificationGutsManager.startAppNotificationSettingsActivity(this.f$3, i, notificationChannel, this.f$4);
                break;
            default:
                StatusBarNotification statusBarNotification2 = this.f$2;
                NotificationGutsManager notificationGutsManager2 = this.f$0;
                notificationGutsManager2.mMetricsLogger.action(205);
                this.f$1.resetFalsingCheck();
                notificationGutsManager2.mOnSettingsClickListener.onSettingsClick(statusBarNotification2.getKey());
                notificationGutsManager2.startAppNotificationSettingsActivity(this.f$3, i, notificationChannel, this.f$4);
                break;
        }
    }
}
