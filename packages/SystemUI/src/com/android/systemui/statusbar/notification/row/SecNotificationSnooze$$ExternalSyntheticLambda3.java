package com.android.systemui.statusbar.notification.row;

import android.metrics.LogMaker;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public final /* synthetic */ class SecNotificationSnooze$$ExternalSyntheticLambda3 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecNotificationSnooze$$ExternalSyntheticLambda3(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                LogMaker logMaker = SecNotificationSnooze.OPTIONS_OPEN_LOG;
                ((AtomicReference) obj2).set(((NotificationStackScrollLayoutController) obj).mNotificationListContainer);
                break;
            case 1:
                SecNotificationSnooze secNotificationSnooze = (SecNotificationSnooze) obj2;
                LogMaker logMaker2 = SecNotificationSnooze.OPTIONS_OPEN_LOG;
                secNotificationSnooze.getClass();
                secNotificationSnooze.mSnoozeOptionManager.mSnoozeListener = NotificationStackScrollLayoutController.this.mSwipeHelper;
                break;
            default:
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) obj2;
                LogMaker logMaker3 = SecNotificationSnooze.OPTIONS_OPEN_LOG;
                ((NotificationStackScrollLayoutController.NotificationListContainerImpl) ((NotificationListContainer) obj)).onHeightChanged(expandableNotificationRow, expandableNotificationRow.isShown());
                break;
        }
    }
}
