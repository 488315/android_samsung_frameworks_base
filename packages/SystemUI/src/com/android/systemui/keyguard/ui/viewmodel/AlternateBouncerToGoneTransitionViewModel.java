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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GONE, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, sceneKey)));
        this.transitionAnimation = m;
        this.showAllNotifications = bouncerToGoneFlows.m2605showAllNotificationsVtjQ1oo(j, keyguardState);
        ComposeBouncerFlags.INSTANCE.getClass();
        this.scrimAlpha = bouncerToGoneFlows.m2604createScrimAlphaFlowKLykuaI(j, keyguardState, new BouncerToGoneFlows$scrimAlpha$2(bouncerToGoneFlows.primaryBouncerInteractor));
        this.deviceEntryParentViewAlpha = m.immediatelyTransitionTo(0.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
