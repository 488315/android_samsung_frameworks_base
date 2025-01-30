package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3 */
/* loaded from: classes.dex */
public final /* synthetic */ class RunnableC1398x58d1e4b1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ RunnableC1398x58d1e4b1(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite, int i) {
        this.$r8$classId = i;
        this.f$0 = actionsDialogLite;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
                int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                actionsDialogLite.getClass();
                actionsDialogLite.startAnimation(new RunnableC1398x58d1e4b1(actionsDialogLite, 1), false);
                break;
            default:
                GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite2 = this.f$0;
                int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                actionsDialogLite2.setDismissOverride(null);
                actionsDialogLite2.hide();
                actionsDialogLite2.dismiss();
                break;
        }
    }
}
