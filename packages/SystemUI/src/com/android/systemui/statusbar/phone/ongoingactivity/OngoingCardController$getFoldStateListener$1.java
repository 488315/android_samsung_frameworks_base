package com.android.systemui.statusbar.phone.ongoingactivity;

import com.samsung.android.view.SemWindowManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class OngoingCardController$getFoldStateListener$1 implements SemWindowManager.FoldStateListener {
    public final /* synthetic */ OngoingCardController this$0;

    public OngoingCardController$getFoldStateListener$1(OngoingCardController ongoingCardController) {
        this.this$0 = ongoingCardController;
    }

    public final void onFoldStateChanged(boolean z) {
        if (this.this$0.mCardStackView.isReadyCollapseAnimation()) {
            this.this$0.collapseAnimation();
        }
    }

    public final void onTableModeChanged(boolean z) {
    }
}
