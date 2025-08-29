package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.keyguard.domain.interactor.FromAlternateBouncerTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.scene.shared.model.Scenes;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class AlternateBouncerToGoneTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final ChannelFlowTransformLatest scrimAlpha;
    public final Flow showAllNotifications;
    public final SysuiStatusBarStateController statusBarStateController;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public AlternateBouncerToGoneTransitionViewModel(BouncerToGoneFlows bouncerToGoneFlows, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, SysuiStatusBarStateController sysuiStatusBarStateController) {
        this.statusBarStateController = sysuiStatusBarStateController;
        FromAlternateBouncerTransitionInteractor.Companion.getClass();
        long j = FromAlternateBouncerTransitionInteractor.TO_GONE_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.ALTERNATE_BOUNCER;
        SceneKey sceneKey = Scenes.Gone;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GONE, keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, sceneKey)));
        this.transitionAnimation = flowBuilderM;
        this.showAllNotifications = bouncerToGoneFlows.m2620showAllNotificationsVtjQ1oo(j, keyguardState);
        ComposeBouncerFlags.INSTANCE.getClass();
        this.scrimAlpha = bouncerToGoneFlows.m2619createScrimAlphaFlowKLykuaI(j, keyguardState, new BouncerToGoneFlows$scrimAlpha$2(bouncerToGoneFlows.primaryBouncerInteractor));
        this.deviceEntryParentViewAlpha = flowBuilderM.immediatelyTransitionTo(0.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
