package com.android.systemui.globalactions;

import android.app.Dialog;
import com.android.systemui.MultiListLayout;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

public final /* synthetic */ class GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0 {
    public final /* synthetic */ GlobalActionsDialogLite.ActionsDialogLite f$0;

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
