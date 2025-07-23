package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class DozingToDreamingTransitionViewModel {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;

    public DozingToDreamingTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromDozingTransitionInteractor.Companion.getClass();
        this.lockscreenAlpha = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(FromDozingTransitionInteractor.TO_DREAMING_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.DOZING, KeyguardState.DREAMING)).immediatelyTransitionTo(0.0f);
    }
}
