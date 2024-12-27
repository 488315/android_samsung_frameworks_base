package com.android.systemui.statusbar.phone.ongoingactivity;

import com.samsung.android.view.SemWindowManager;

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
