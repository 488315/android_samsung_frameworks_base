package com.android.systemui.globalactions;

import android.app.Dialog;
import com.android.systemui.MultiListLayout;
import com.android.systemui.globalactions.GlobalActionsDialogLite;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* renamed from: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0 */
/* loaded from: classes.dex */
public final /* synthetic */ class C1395x58d1e4ae {
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
