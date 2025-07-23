package com.android.systemui.shade;

import com.android.systemui.Dumpable;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;
import com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda36 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Dumpable f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda36(Dumpable dumpable, int i) {
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
