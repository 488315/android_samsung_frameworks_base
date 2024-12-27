package com.android.systemui.statusbar.phone;

import android.view.View;
import android.view.ViewParent;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayout;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final /* synthetic */ class StatusBarRemoteInputCallback$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ StatusBarRemoteInputCallback$$ExternalSyntheticLambda1(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ((View) obj).callOnClick();
                break;
            default:
                final StatusBarRemoteInputCallback statusBarRemoteInputCallback = (StatusBarRemoteInputCallback) obj;
                View view = statusBarRemoteInputCallback.mPendingWorkRemoteInputView;
                if (view != null) {
                    ViewParent parent = view.getParent();
                    while (!(parent instanceof ExpandableNotificationRow)) {
                        if (parent == null) {
                            break;
                        } else {
                            parent = parent.getParent();
                        }
                    }
                    final ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) parent;
                    ViewParent parent2 = expandableNotificationRow.getParent();
                    if (parent2 instanceof NotificationStackScrollLayout) {
                        final NotificationStackScrollLayout notificationStackScrollLayout = (NotificationStackScrollLayout) parent2;
                        expandableNotificationRow.setUserExpanded(true, true);
                        if (expandableNotificationRow.isChildInGroup()) {
                            ((GroupExpansionManagerImpl) expandableNotificationRow.mGroupExpansionManager).setGroupExpanded(expandableNotificationRow.mEntry, true);
                        }
                        expandableNotificationRow.notifyHeightChanged(false);
                        expandableNotificationRow.post(new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda3
                            @Override // java.lang.Runnable
                            public final void run() {
                                final StatusBarRemoteInputCallback statusBarRemoteInputCallback2 = StatusBarRemoteInputCallback.this;
                                final NotificationStackScrollLayout notificationStackScrollLayout2 = notificationStackScrollLayout;
                                ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
                                statusBarRemoteInputCallback2.getClass();
                                Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda4
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        StatusBarRemoteInputCallback statusBarRemoteInputCallback3 = StatusBarRemoteInputCallback.this;
                                        NotificationStackScrollLayout notificationStackScrollLayout3 = notificationStackScrollLayout2;
                                        statusBarRemoteInputCallback3.mPendingWorkRemoteInputView.callOnClick();
                                        statusBarRemoteInputCallback3.mPendingWorkRemoteInputView = null;
                                        notificationStackScrollLayout3.mFinishScrollingCallback = null;
                                    }
                                };
                                if (notificationStackScrollLayout2.scrollTo(expandableNotificationRow2)) {
                                    notificationStackScrollLayout2.mFinishScrollingCallback = runnable;
                                } else {
                                    runnable.run();
                                }
                            }
                        });
                        break;
                    }
                }
                break;
        }
    }
}
