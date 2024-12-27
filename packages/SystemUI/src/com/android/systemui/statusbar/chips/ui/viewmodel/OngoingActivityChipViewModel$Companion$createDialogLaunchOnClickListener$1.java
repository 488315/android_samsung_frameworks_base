package com.android.systemui.statusbar.chips.ui.viewmodel;

import android.view.View;
import com.android.systemui.R;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.TransitionAnimator;
import com.android.systemui.animation.ViewDialogTransitionAnimatorController;
import com.android.systemui.statusbar.chips.ui.view.ChipBackgroundContainer;
import com.android.systemui.statusbar.phone.SystemUIDialog;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1 implements View.OnClickListener {
    public final /* synthetic */ SystemUIDialog.Delegate $dialogDelegate;
    public final /* synthetic */ DialogTransitionAnimator $dialogTransitionAnimator;

    public OngoingActivityChipViewModel$Companion$createDialogLaunchOnClickListener$1(SystemUIDialog.Delegate delegate, DialogTransitionAnimator dialogTransitionAnimator) {
        this.$dialogDelegate = delegate;
        this.$dialogTransitionAnimator = dialogTransitionAnimator;
    }

    @Override // android.view.View.OnClickListener
    public final void onClick(View view) {
        SystemUIDialog createDialog = this.$dialogDelegate.createDialog();
        ChipBackgroundContainer chipBackgroundContainer = (ChipBackgroundContainer) view.requireViewById(R.id.ongoing_activity_chip_background);
        DialogTransitionAnimator dialogTransitionAnimator = this.$dialogTransitionAnimator;
        TransitionAnimator.Timings timings = DialogTransitionAnimator.TIMINGS;
        dialogTransitionAnimator.getClass();
        DialogTransitionAnimator.Controller.Companion.getClass();
        ViewDialogTransitionAnimatorController fromView = DialogTransitionAnimator.Controller.Companion.fromView(chipBackgroundContainer, null);
        if (fromView == null) {
            createDialog.show();
        } else {
            dialogTransitionAnimator.show(createDialog, fromView, false);
        }
    }
}
