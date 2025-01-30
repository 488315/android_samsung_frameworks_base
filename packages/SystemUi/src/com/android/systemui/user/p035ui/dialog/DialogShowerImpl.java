package com.android.systemui.user.p035ui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import com.android.systemui.animation.DialogLaunchAnimator;
import com.android.systemui.p016qs.user.UserSwitchDialogController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DialogShowerImpl implements DialogInterface, UserSwitchDialogController.DialogShower {
    public final Dialog animateFrom;
    public final DialogLaunchAnimator dialogLaunchAnimator;

    public DialogShowerImpl(Dialog dialog, DialogLaunchAnimator dialogLaunchAnimator) {
        this.animateFrom = dialog;
        this.dialogLaunchAnimator = dialogLaunchAnimator;
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
