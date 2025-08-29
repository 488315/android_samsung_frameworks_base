package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class LockscreenToGoneTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final SysuiStatusBarStateController statusBarStateController;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public LockscreenToGoneTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.statusBarStateController = sysuiStatusBarStateController;
        FromLockscreenTransitionInteractor.Companion.getClass();
        long j = FromLockscreenTransitionInteractor.TO_GONE_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        SceneKey sceneKey = Scenes.Gone;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GONE, keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, sceneKey)));
        this.transitionAnimation = flowBuilderM;
        Duration.Companion companion2 = Duration.Companion;
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, DurationKt.toDuration(200, DurationUnit.MILLISECONDS), new LockscreenToGoneTransitionViewModel$$ExternalSyntheticLambda0(), 0L, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 204);
        this.deviceEntryParentViewAlpha = flowBuilderM.immediatelyTransitionTo(0.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
