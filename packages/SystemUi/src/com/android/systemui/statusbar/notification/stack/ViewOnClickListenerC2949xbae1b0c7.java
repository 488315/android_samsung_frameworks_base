package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.systemui.Dependency;
import com.android.systemui.ShelfToolTipManager;
import com.android.systemui.statusbar.notification.NotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter;
import com.android.systemui.statusbar.phone.StatusBarNotificationActivityStarter.C31453;
import com.android.systemui.util.SystemUIAnalytics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController$$ExternalSyntheticLambda8 */
/* loaded from: classes2.dex */
public final /* synthetic */ class ViewOnClickListenerC2949xbae1b0c7 implements View.OnClickListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationStackScrollLayoutController f$0;

    public /* synthetic */ ViewOnClickListenerC2949xbae1b0c7(NotificationStackScrollLayoutController notificationStackScrollLayoutController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationStackScrollLayoutController;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        switch (this.$r8$classId) {
            case 0:
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.f$0;
                NotificationActivityStarter notificationActivityStarter = notificationStackScrollLayoutController.mNotificationActivityStarter;
                if (notificationActivityStarter != null) {
                    notificationStackScrollLayoutController.mView.getClass();
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter = (StatusBarNotificationActivityStarter) notificationActivityStarter;
                    statusBarNotificationActivityStarter.mActivityStarter.dismissKeyguardThenExecute(statusBarNotificationActivityStarter.new C31453(false, view, false), null, false);
                    break;
                }
                break;
            case 1:
                this.f$0.mView.clearNotifications(2, !r4.hasNotifications(1, true));
                break;
            default:
                NotificationActivityStarter notificationActivityStarter2 = this.f$0.mNotificationActivityStarter;
                if (notificationActivityStarter2 != null) {
                    StatusBarNotificationActivityStarter statusBarNotificationActivityStarter2 = (StatusBarNotificationActivityStarter) notificationActivityStarter2;
                    statusBarNotificationActivityStarter2.mActivityStarter.dismissKeyguardThenExecute(statusBarNotificationActivityStarter2.new C31453(false, view, false), null, false);
                    ((ShelfToolTipManager) Dependency.get(ShelfToolTipManager.class)).isTappedNotiSettings = true;
                }
                SystemUIAnalytics.sendEventLog("QPN001", "QPNE0017");
                break;
        }
    }
}
