package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.keyguard.domain.interactor.FromDozingTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class DozingToLockscreenTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;

    public DozingToLockscreenTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromDozingTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2615setupVtjQ1oo = keyguardTransitionAnimationFlow.m2615setupVtjQ1oo(FromDozingTransitionInteractor.TO_LOCKSCREEN_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.DOZING, KeyguardState.LOCKSCREEN));
        Duration.Companion companion = Duration.Companion;
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2616sharedFlow74qcysc$default(flowBuilderM2615setupVtjQ1oo, DurationKt.toDuration(150, DurationUnit.MILLISECONDS), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), 0L, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
        this.lockscreenAlpha = flowBuilderM2615setupVtjQ1oo.immediatelyTransitionTo(1.0f);
        this.deviceEntryBackgroundViewAlpha = flowBuilderM2615setupVtjQ1oo.immediatelyTransitionTo(1.0f);
        this.deviceEntryParentViewAlpha = flowBuilderM2615setupVtjQ1oo.immediatelyTransitionTo(1.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
