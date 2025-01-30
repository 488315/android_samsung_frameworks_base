package com.android.systemui.statusbar.notification.row;

import android.app.NotificationChannel;
import android.os.RemoteException;
import android.service.notification.StatusBarNotification;
import com.android.systemui.statusbar.notification.row.NotificationInfo;
import com.android.systemui.statusbar.phone.StatusBarNotificationPresenter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationGutsManager$$ExternalSyntheticLambda2 implements NotificationInfo.OnSettingsClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationGutsManager f$0;
    public final /* synthetic */ NotificationGuts f$1;
    public final /* synthetic */ StatusBarNotification f$2;
    public final /* synthetic */ String f$3;
    public final /* synthetic */ ExpandableNotificationRow f$4;

    public /* synthetic */ NotificationGutsManager$$ExternalSyntheticLambda2(NotificationGutsManager notificationGutsManager, NotificationGuts notificationGuts, StatusBarNotification statusBarNotification, String str, ExpandableNotificationRow expandableNotificationRow, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationGutsManager;
        this.f$1 = notificationGuts;
        this.f$2 = statusBarNotification;
        this.f$3 = str;
        this.f$4 = expandableNotificationRow;
    }

    public final void onClick(int i, NotificationChannel notificationChannel) {
        int i2 = this.$r8$classId;
        ExpandableNotificationRow expandableNotificationRow = this.f$4;
        String str = this.f$3;
        StatusBarNotification statusBarNotification = this.f$2;
        NotificationGuts notificationGuts = this.f$1;
        NotificationGutsManager notificationGutsManager = this.f$0;
        switch (i2) {
            case 0:
                notificationGutsManager.mMetricsLogger.action(205);
                notificationGuts.resetFalsingCheck();
                StatusBarNotificationPresenter.C31573 c31573 = notificationGutsManager.mOnSettingsClickListener;
                String key = statusBarNotification.getKey();
                c31573.getClass();
                try {
                    StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(key);
                } catch (RemoteException unused) {
                }
                notificationGutsManager.startAppNotificationSettingsActivity(str, i, notificationChannel, expandableNotificationRow);
                break;
            case 1:
                notificationGutsManager.mMetricsLogger.action(205);
                notificationGuts.resetFalsingCheck();
                StatusBarNotificationPresenter.C31573 c315732 = notificationGutsManager.mOnSettingsClickListener;
                String key2 = statusBarNotification.getKey();
                c315732.getClass();
                try {
                    StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(key2);
                } catch (RemoteException unused2) {
                }
                notificationGutsManager.startAppNotificationSettingsActivity(str, i, notificationChannel, expandableNotificationRow);
                break;
            default:
                notificationGutsManager.mMetricsLogger.action(205);
                notificationGuts.resetFalsingCheck();
                StatusBarNotificationPresenter.C31573 c315733 = notificationGutsManager.mOnSettingsClickListener;
                String key3 = statusBarNotification.getKey();
                c315733.getClass();
                try {
                    StatusBarNotificationPresenter.this.mBarService.onNotificationSettingsViewed(key3);
                } catch (RemoteException unused3) {
                }
                notificationGutsManager.startAppNotificationSettingsActivity(str, i, notificationChannel, expandableNotificationRow);
                break;
        }
    }
}
