package com.android.systemui.statusbar.notification.row;

import android.metrics.LogMaker;
import com.android.systemui.statusbar.notification.stack.NotificationListContainer;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class SecNotificationSnooze$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ SecNotificationSnooze$$ExternalSyntheticLambda2(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                AtomicReference atomicReference = (AtomicReference) this.f$0;
                LogMaker logMaker = SecNotificationSnooze.OPTIONS_OPEN_LOG;
                atomicReference.set(((NotificationStackScrollLayoutController) obj).mNotificationListContainer);
                break;
            case 1:
                SecNotificationSnooze secNotificationSnooze = (SecNotificationSnooze) this.f$0;
                LogMaker logMaker2 = SecNotificationSnooze.OPTIONS_OPEN_LOG;
                secNotificationSnooze.getClass();
                secNotificationSnooze.mSnoozeOptionManager.mSnoozeListener = NotificationStackScrollLayoutController.this.mSwipeHelper;
                break;
            default:
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) this.f$0;
                LogMaker logMaker3 = SecNotificationSnooze.OPTIONS_OPEN_LOG;
                ((NotificationStackScrollLayoutController.NotificationListContainerImpl) ((NotificationListContainer) obj)).onHeightChanged(expandableNotificationRow, expandableNotificationRow.isShown());
                break;
        }
    }
}
