package com.android.systemui.user.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.user.UserSwitchDialogController;

public final class DialogShowerImpl implements DialogInterface, UserSwitchDialogController.DialogShower {
    public final Dialog animateFrom;
    public final DialogTransitionAnimator dialogTransitionAnimator;

    public DialogShowerImpl(Dialog dialog, DialogTransitionAnimator dialogTransitionAnimator) {
        this.animateFrom = dialog;
        this.dialogTransitionAnimator = dialogTransitionAnimator;
    }

    @Override // android.content.DialogInterface
    public final void cancel() {
        this.animateFrom.cancel();
    }

    @Override // android.content.DialogInterface
    public final void dismiss() {
        this.animateFrom.dismiss();
    }
}
