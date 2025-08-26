package com.android.systemui.shade;

import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController;
import java.util.function.Consumer;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda43 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Dumpable f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda43(Dumpable dumpable, int i) {
        this.$r8$classId = i;
        this.f$0 = dumpable;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        Dumpable dumpable = this.f$0;
        switch (i) {
            case 0:
                ((NotificationStackScrollLayoutController) dumpable).mView.mAmbientState.mTrackedHeadsUpRow = (ExpandableNotificationRow) obj;
                break;
            default:
                ((Boolean) obj).getClass();
                ((KeyguardSecBottomAreaViewController) dumpable).updateIndicationPosition();
                break;
        }
    }
}
