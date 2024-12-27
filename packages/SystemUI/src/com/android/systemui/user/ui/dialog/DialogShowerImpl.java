package com.android.systemui.user.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.user.UserSwitchDialogController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
