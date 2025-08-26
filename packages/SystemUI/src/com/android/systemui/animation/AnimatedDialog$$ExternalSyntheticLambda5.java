package com.android.systemui.animation;

import android.view.ViewGroup;
import com.android.systemui.animation.AnimatedDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class AnimatedDialog$$ExternalSyntheticLambda5 implements Function0 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ AnimatedDialog f$0;
    public final /* synthetic */ AnimatedDialog$$ExternalSyntheticLambda1 f$1;

    public /* synthetic */ AnimatedDialog$$ExternalSyntheticLambda5(AnimatedDialog$$ExternalSyntheticLambda1 animatedDialog$$ExternalSyntheticLambda1, AnimatedDialog animatedDialog) {
        this.f$1 = animatedDialog$$ExternalSyntheticLambda1;
        this.f$0 = animatedDialog;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                AnimatedDialog animatedDialog = this.f$0;
                ViewGroup viewGroup = animatedDialog.dialogContentWithBackground;
                viewGroup.getClass();
                viewGroup.setVisibility(4);
                AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener = animatedDialog.backgroundLayoutListener;
                if (animatedBoundsLayoutListener != null) {
                    viewGroup.removeOnLayoutChangeListener(animatedBoundsLayoutListener);
                }
                animatedDialog.controller.stopDrawingInOverlay();
                animatedDialog.synchronizeNextDraw(new AnimatedDialog$$ExternalSyntheticLambda5(this.f$1, animatedDialog));
                break;
            default:
                this.f$1.mo781invoke(Boolean.TRUE);
                AnimatedDialog animatedDialog2 = this.f$0;
                animatedDialog2.onDialogDismissed.mo781invoke(animatedDialog2);
                break;
        }
        return Unit.INSTANCE;
    }

    public /* synthetic */ AnimatedDialog$$ExternalSyntheticLambda5(AnimatedDialog animatedDialog, AnimatedDialog$$ExternalSyntheticLambda1 animatedDialog$$ExternalSyntheticLambda1) {
        this.f$0 = animatedDialog;
        this.f$1 = animatedDialog$$ExternalSyntheticLambda1;
    }
}
