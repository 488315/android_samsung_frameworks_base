package com.android.systemui.animation;

import android.graphics.drawable.GradientDrawable;
import android.view.ViewGroupOverlay;
import android.view.ViewOverlay;
import com.android.systemui.animation.TransitionAnimator;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class TransitionAnimator$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ TransitionAnimator.Controller f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ ViewGroupOverlay f$2;
    public final /* synthetic */ GradientDrawable f$3;
    public final /* synthetic */ boolean f$4;
    public final /* synthetic */ ViewOverlay f$5;

    public /* synthetic */ TransitionAnimator$$ExternalSyntheticLambda0(TransitionAnimator.Controller controller, boolean z, ViewGroupOverlay viewGroupOverlay, GradientDrawable gradientDrawable, boolean z2, ViewOverlay viewOverlay) {
        this.f$0 = controller;
        this.f$1 = z;
        this.f$2 = viewGroupOverlay;
        this.f$3 = gradientDrawable;
        this.f$4 = z2;
        this.f$5 = viewOverlay;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        ViewGroupOverlay viewGroupOverlay = this.f$2;
        GradientDrawable gradientDrawable = this.f$3;
        ViewOverlay viewOverlay = this.f$5;
        TransitionAnimator.Companion companion = TransitionAnimator.Companion;
        boolean z = this.f$1;
        TransitionAnimator.Controller controller = this.f$0;
        controller.onTransitionAnimationEnd(z);
        viewGroupOverlay.remove(gradientDrawable);
        if (this.f$4 && controller.isLaunching() && viewOverlay != null) {
            viewOverlay.remove(gradientDrawable);
        }
        return Unit.INSTANCE;
    }
}
