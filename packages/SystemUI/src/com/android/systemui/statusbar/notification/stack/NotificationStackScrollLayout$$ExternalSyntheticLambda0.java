package com.android.systemui.statusbar.notification.stack;

import android.view.View;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationStackScrollLayout$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        NotificationChildrenContainer notificationChildrenContainer;
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                boolean z = NotificationStackScrollLayout.DEBUG_DISABLE_SHOW_NEW_NOTIF_ONLY;
                ((View) obj).requestFocus();
                break;
            default:
                ExpandableNotificationRow expandableNotificationRow = (ExpandableNotificationRow) obj;
                expandableNotificationRow.mGroupExpansionChanging = false;
                expandableNotificationRow.updateBackgroundForGroupState();
                if (expandableNotificationRow.isInsignificant() && expandableNotificationRow.mIsSummaryWithChildren && (notificationChildrenContainer = expandableNotificationRow.mChildrenContainer) != null) {
                    notificationChildrenContainer.updateHeaderVisibility(false, true);
                    break;
                }
                break;
        }
    }

    public /* synthetic */ NotificationStackScrollLayout$$ExternalSyntheticLambda0(ExpandableNotificationRow expandableNotificationRow) {
        this.f$0 = expandableNotificationRow;
    }
}
