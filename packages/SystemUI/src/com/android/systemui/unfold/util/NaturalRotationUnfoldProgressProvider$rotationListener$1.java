package com.android.systemui.unfold.util;

import com.android.systemui.unfold.updates.RotationChangeProvider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NaturalRotationUnfoldProgressProvider$rotationListener$1 implements RotationChangeProvider.RotationListener {
    public final /* synthetic */ NaturalRotationUnfoldProgressProvider this$0;

    public NaturalRotationUnfoldProgressProvider$rotationListener$1(NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider) {
        this.this$0 = naturalRotationUnfoldProgressProvider;
    }

    @Override // com.android.systemui.unfold.updates.RotationChangeProvider.RotationListener
    public final void onRotationChanged(int i) {
        boolean z = i == 0 || i == 2;
        NaturalRotationUnfoldProgressProvider naturalRotationUnfoldProgressProvider = this.this$0;
        if (naturalRotationUnfoldProgressProvider.isNaturalRotation != z) {
            naturalRotationUnfoldProgressProvider.isNaturalRotation = z;
            naturalRotationUnfoldProgressProvider.scopedUnfoldTransitionProgressProvider.setReadyToHandleTransition(z);
        }
    }
}
