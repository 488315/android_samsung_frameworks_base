package com.android.systemui.globalactions;

import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite, int i) {
        this.$r8$classId = i;
        this.f$0 = actionsDialogLite;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
        switch (i) {
            case 0:
                int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                actionsDialogLite.getClass();
                actionsDialogLite.startAnimation(false, new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4(actionsDialogLite, 1));
                break;
            default:
                int i3 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                actionsDialogLite.setDismissOverride(null);
                actionsDialogLite.hide();
                actionsDialogLite.dismiss();
                break;
        }
    }
}
