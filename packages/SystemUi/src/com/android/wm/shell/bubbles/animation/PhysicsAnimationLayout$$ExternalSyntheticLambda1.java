package com.android.wm.shell.bubbles.animation;

import android.view.View;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class PhysicsAnimationLayout$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ PhysicsAnimationLayout f$0;
    public final /* synthetic */ View f$1;

    public /* synthetic */ PhysicsAnimationLayout$$ExternalSyntheticLambda1(PhysicsAnimationLayout physicsAnimationLayout, View view) {
        this.f$0 = physicsAnimationLayout;
        this.f$1 = view;
    }

    @Override // java.lang.Runnable
    public final void run() {
        PhysicsAnimationLayout physicsAnimationLayout = this.f$0;
        View view = this.f$1;
        physicsAnimationLayout.cancelAnimationsOnView(view);
        physicsAnimationLayout.removeTransientView(view);
    }
}
