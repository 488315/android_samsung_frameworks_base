package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.scene.shared.model.Scenes;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class GoneToLockscreenTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;

    public GoneToLockscreenTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromGoneTransitionInteractor.Companion.getClass();
        long j = FromGoneTransitionInteractor.TO_LOCKSCREEN_DURATION;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GONE, keyguardState, keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, new Edge.ContentToState(sceneKey, keyguardState)));
        Duration.Companion companion2 = Duration.Companion;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, DurationUnit.MILLISECONDS), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), 0L, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState);
        this.shortcutsAlpha = keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default;
        this.lockscreenAlpha = keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2614sharedFlow74qcysc$default;
        this.deviceEntryBackgroundViewAlpha = flowBuilderM.immediatelyTransitionTo(1.0f);
        this.deviceEntryParentViewAlpha = flowBuilderM.immediatelyTransitionTo(1.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
