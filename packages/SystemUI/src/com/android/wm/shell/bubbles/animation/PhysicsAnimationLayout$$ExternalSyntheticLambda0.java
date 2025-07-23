package com.android.wm.shell.bubbles.animation;

import android.view.View;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PhysicsAnimationLayout$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ PhysicsAnimationLayout f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ PhysicsAnimationLayout$$ExternalSyntheticLambda0(PhysicsAnimationLayout physicsAnimationLayout, View view) {
        this.f$0 = physicsAnimationLayout;
        this.f$1 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PhysicsAnimationLayout physicsAnimationLayout = this.f$0;
        View view = this.f$1;
        int i = PhysicsAnimationLayout.$r8$clinit;
        physicsAnimationLayout.cancelAnimationsOnView(view);
        physicsAnimationLayout.removeTransientView(view);
    }
}
