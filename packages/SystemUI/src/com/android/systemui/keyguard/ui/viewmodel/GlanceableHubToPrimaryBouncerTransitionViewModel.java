package com.android.systemui.keyguard.ui.viewmodel;

import com.android.systemui.communal.domain.interactor.CommunalSceneInteractor;
import com.android.systemui.communal.domain.interactor.CommunalSettingsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromGlanceableHubTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import kotlinx.coroutines.flow.Flow;

/* loaded from: classes2.dex */
public final class GlanceableHubToPrimaryBouncerTransitionViewModel implements PrimaryBouncerTransition {
    public final BlurConfig blurConfig;
    public final CommunalSettingsInteractor communalSettingsInteractor;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationBlurRadius;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public GlanceableHubToPrimaryBouncerTransitionViewModel(BlurConfig blurConfig, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, CommunalSettingsInteractor communalSettingsInteractor, CommunalSceneInteractor communalSceneInteractor, KeyguardStateController keyguardStateController) {
        this.blurConfig = blurConfig;
        this.communalSettingsInteractor = communalSettingsInteractor;
        FromGlanceableHubTransitionInteractor.Companion.getClass();
        long j = FromGlanceableHubTransitionInteractor.TO_BOUNCER_DURATION;
        Edge.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GLANCEABLE_HUB, KeyguardState.PRIMARY_BOUNCER, keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, Edge.INVALID));
        this.transitionAnimation = flowBuilderM;
        this.windowBlurRadius = flowBuilderM.immediatelyTransitionTo(blurConfig.maxBlurRadiusPx);
        this.notificationBlurRadius = flowBuilderM.immediatelyTransitionTo(0.0f);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition
    public final Flow getNotificationBlurRadius() {
        return this.notificationBlurRadius;
    }
}
