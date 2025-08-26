package com.android.systemui.user.ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.qs.user.UserSwitchDialogController$DialogShower;

/* loaded from: classes3.dex */
public final class DialogShowerImpl implements DialogInterface, UserSwitchDialogController$DialogShower {
    public final Dialog animateFrom;

    public DialogShowerImpl(Dialog dialog, DialogTransitionAnimator dialogTransitionAnimator) {
        this.animateFrom = dialog;
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
