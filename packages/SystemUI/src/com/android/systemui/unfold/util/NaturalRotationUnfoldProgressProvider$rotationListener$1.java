package com.android.systemui.unfold.util;

import com.android.systemui.unfold.updates.RotationChangeProvider;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
