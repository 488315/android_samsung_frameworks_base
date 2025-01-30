package com.android.systemui.unfold.util;

import com.android.systemui.unfold.updates.RotationChangeProvider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
