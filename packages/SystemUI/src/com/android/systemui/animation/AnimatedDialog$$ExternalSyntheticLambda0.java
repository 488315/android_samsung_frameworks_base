package com.android.systemui.animation;

import android.view.ViewGroup;
import android.view.Window;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.systemui.animation.AnimatedDialog;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final /* synthetic */ class AnimatedDialog$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AnimatedDialog f$0;

    public /* synthetic */ AnimatedDialog$$ExternalSyntheticLambda0(AnimatedDialog animatedDialog, int i) {
        this.$r8$classId = i;
        this.f$0 = animatedDialog;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                Window window = this.f$0.dialog.getWindow();
                window.getClass();
                break;
            case 1:
                AnimatedDialog animatedDialog = this.f$0;
                animatedDialog.isSourceDrawnInDialog = true;
                animatedDialog.maybeStartLaunchAnimation();
                break;
            case 2:
                AnimatedDialog animatedDialog2 = this.f$0;
                animatedDialog2.isLaunching = false;
                if (animatedDialog2.dismissRequested) {
                    animatedDialog2.dialog.dismiss();
                }
                AnimatedDialog.AnimatedBoundsLayoutListener animatedBoundsLayoutListener = animatedDialog2.backgroundLayoutListener;
                if (animatedBoundsLayoutListener != null) {
                    ViewGroup viewGroup = animatedDialog2.dialogContentWithBackground;
                    viewGroup.getClass();
                    viewGroup.addOnLayoutChangeListener(animatedBoundsLayoutListener);
                }
                if (animatedDialog2.hasInstrumentedJank) {
                    InteractionJankMonitor interactionJankMonitor = animatedDialog2.interactionJankMonitor;
                    DialogCuj cuj = animatedDialog2.controller.getCuj();
                    cuj.getClass();
                    interactionJankMonitor.end(cuj.cujType);
                }
                break;
            default:
                Window window2 = this.f$0.dialog.getWindow();
                if (window2 != null) {
                    window2.clearFlags(2);
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
