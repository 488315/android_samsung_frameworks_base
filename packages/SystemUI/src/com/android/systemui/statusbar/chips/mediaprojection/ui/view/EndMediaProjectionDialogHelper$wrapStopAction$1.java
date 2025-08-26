package com.android.systemui.statusbar.chips.mediaprojection.ui.view;

import android.content.DialogInterface;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final class EndMediaProjectionDialogHelper$wrapStopAction$1 implements DialogInterface.OnClickListener {
    public final /* synthetic */ Function0 $stopAction;
    public final /* synthetic */ EndMediaProjectionDialogHelper this$0;

    public EndMediaProjectionDialogHelper$wrapStopAction$1(EndMediaProjectionDialogHelper endMediaProjectionDialogHelper, Function0 function0) {
        this.this$0 = endMediaProjectionDialogHelper;
        this.$stopAction = function0;
    }

    @Override // android.content.DialogInterface.OnClickListener
    public final void onClick(DialogInterface dialogInterface, int i) {
        this.this$0.dialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
        this.$stopAction.invoke();
    }
}
