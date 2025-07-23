package com.android.systemui.globalactions;

import android.app.Dialog;
import com.android.systemui.MultiListLayout;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0 {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

    public /* synthetic */ GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0(GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite) {
        this.f$0 = actionsDialogLite;
    }

    public final void onRotate() {
        GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
        actionsDialogLite.mOnRefreshCallback.run();
        GlobalActionsPopupMenu globalActionsPopupMenu = actionsDialogLite.mOverflowPopup;
        if (globalActionsPopupMenu != null) {
            globalActionsPopupMenu.dismiss();
        }
        Dialog dialog = actionsDialogLite.mPowerOptionsDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        MultiListLayout multiListLayout = actionsDialogLite.mGlobalActionsLayout;
        if (multiListLayout.mAdapter == null) {
            throw new IllegalStateException("mAdapter must be set before calling updateList");
        }
        multiListLayout.onUpdateList();
    }
}
