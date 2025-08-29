package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import dagger.Lazy;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class PrimaryBouncerToGoneTransitionViewModel implements PrimaryBouncerTransition {
    public final BlurConfig blurConfig;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 bouncerAlpha;
    public boolean leaveShadeOpen;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationBlurRadius;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final ChannelFlowTransformLatest scrimAlpha;
    public final Flow showAllNotifications;
    public final SysuiStatusBarStateController statusBarStateController;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;
    public boolean willRunDismissFromKeyguard;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public PrimaryBouncerToGoneTransitionViewModel(BlurConfig blurConfig, SysuiStatusBarStateController sysuiStatusBarStateController, PrimaryBouncerInteractor primaryBouncerInteractor, Lazy lazy, BouncerToGoneFlows bouncerToGoneFlows, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        final int i = 0;
        final int i2 = 1;
        this.blurConfig = blurConfig;
        this.statusBarStateController = sysuiStatusBarStateController;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        long j = FromPrimaryBouncerTransitionInteractor.TO_GONE_DURATION;
        Edge.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2613setupVtjQ1oo = keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, Edge.INVALID);
        KeyguardState keyguardState = KeyguardState.PRIMARY_BOUNCER;
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GONE, flowBuilderM2613setupVtjQ1oo);
        this.transitionAnimation = flowBuilderM;
        this.showAllNotifications = bouncerToGoneFlows.m2620showAllNotificationsVtjQ1oo(j, keyguardState);
        ComposeBouncerFlags composeBouncerFlags = ComposeBouncerFlags.INSTANCE;
        composeBouncerFlags.getClass();
        final PrimaryBouncerToGoneTransitionViewModel$bouncerAlpha$2 primaryBouncerToGoneTransitionViewModel$bouncerAlpha$2 = new PrimaryBouncerToGoneTransitionViewModel$bouncerAlpha$2(primaryBouncerInteractor);
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        final int i3 = 2;
        this.bouncerAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(this.transitionAnimation, FromPrimaryBouncerTransitionInteractor.TO_GONE_SHORT_DURATION, new Function1(this) { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i3) {
                    case 0:
                        f.floatValue();
                        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.f$0;
                        return (primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard || primaryBouncerToGoneTransitionViewModel.leaveShadeOpen) ? Float.valueOf(1.0f) : Float.valueOf(0.0f);
                    case 1:
                        float fFloatValue = f.floatValue();
                        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel2 = this.f$0;
                        boolean z = primaryBouncerToGoneTransitionViewModel2.leaveShadeOpen;
                        BlurConfig blurConfig2 = primaryBouncerToGoneTransitionViewModel2.blurConfig;
                        return Float.valueOf(MathUtils.lerp(blurConfig2.maxBlurRadiusPx, blurConfig2.minBlurRadiusPx, fFloatValue));
                    default:
                        return this.f$0.willRunDismissFromKeyguard ? Float.valueOf(0.0f) : Float.valueOf(1.0f - f.floatValue());
                }
            }
        }, 0L, new Function0(this) { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda1
            public final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i2) {
                    case 0:
                        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.f$0;
                        primaryBouncerToGoneTransitionViewModel.leaveShadeOpen = ((StatusBarStateControllerImpl) primaryBouncerToGoneTransitionViewModel.statusBarStateController).mLeaveOpenOnKeyguardHide;
                        primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard = ((Boolean) primaryBouncerToGoneTransitionViewModel$bouncerAlpha$2.invoke()).booleanValue();
                        break;
                    default:
                        this.f$0.willRunDismissFromKeyguard = ((Boolean) primaryBouncerToGoneTransitionViewModel$bouncerAlpha$2.invoke()).booleanValue();
                        break;
                }
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        final PrimaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2 primaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2 = new PrimaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2(primaryBouncerInteractor);
        Duration.Companion companion = Duration.Companion;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(this.transitionAnimation, DurationKt.toDuration(50, DurationUnit.MILLISECONDS), new Function1(this) { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i) {
                    case 0:
                        f.floatValue();
                        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.f$0;
                        return (primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard || primaryBouncerToGoneTransitionViewModel.leaveShadeOpen) ? Float.valueOf(1.0f) : Float.valueOf(0.0f);
                    case 1:
                        float fFloatValue = f.floatValue();
                        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel2 = this.f$0;
                        boolean z = primaryBouncerToGoneTransitionViewModel2.leaveShadeOpen;
                        BlurConfig blurConfig2 = primaryBouncerToGoneTransitionViewModel2.blurConfig;
                        return Float.valueOf(MathUtils.lerp(blurConfig2.maxBlurRadiusPx, blurConfig2.minBlurRadiusPx, fFloatValue));
                    default:
                        return this.f$0.willRunDismissFromKeyguard ? Float.valueOf(0.0f) : Float.valueOf(1.0f - f.floatValue());
                }
            }
        }, 0L, new Function0(this) { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda1
            public final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                switch (i) {
                    case 0:
                        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.f$0;
                        primaryBouncerToGoneTransitionViewModel.leaveShadeOpen = ((StatusBarStateControllerImpl) primaryBouncerToGoneTransitionViewModel.statusBarStateController).mLeaveOpenOnKeyguardHide;
                        primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard = ((Boolean) primaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2.invoke()).booleanValue();
                        break;
                    default:
                        this.f$0.willRunDismissFromKeyguard = ((Boolean) primaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2.invoke()).booleanValue();
                        break;
                }
                return Unit.INSTANCE;
            }
        }, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, IKnoxCustomManager.Stub.TRANSACTION_getWifiState);
        this.windowBlurRadius = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM, FromPrimaryBouncerTransitionInteractor.TO_GONE_SHORT_DURATION, new Function1(this) { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda0
            public final /* synthetic */ PrimaryBouncerToGoneTransitionViewModel f$0;

            {
                this.f$0 = this;
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                    case 0:
                        f.floatValue();
                        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.f$0;
                        return (primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard || primaryBouncerToGoneTransitionViewModel.leaveShadeOpen) ? Float.valueOf(1.0f) : Float.valueOf(0.0f);
                    case 1:
                        float fFloatValue = f.floatValue();
                        PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel2 = this.f$0;
                        boolean z = primaryBouncerToGoneTransitionViewModel2.leaveShadeOpen;
                        BlurConfig blurConfig2 = primaryBouncerToGoneTransitionViewModel2.blurConfig;
                        return Float.valueOf(MathUtils.lerp(blurConfig2.maxBlurRadiusPx, blurConfig2.minBlurRadiusPx, fFloatValue));
                    default:
                        return this.f$0.willRunDismissFromKeyguard ? Float.valueOf(0.0f) : Float.valueOf(1.0f - f.floatValue());
                }
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = this.f$0;
                primaryBouncerToGoneTransitionViewModel.leaveShadeOpen = ((StatusBarStateControllerImpl) primaryBouncerToGoneTransitionViewModel.statusBarStateController).mLeaveOpenOnKeyguardHide;
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        this.notificationBlurRadius = flowBuilderM.immediatelyTransitionTo(0.0f);
        composeBouncerFlags.getClass();
        this.scrimAlpha = bouncerToGoneFlows.m2619createScrimAlphaFlowKLykuaI(j, keyguardState, new BouncerToGoneFlows$scrimAlpha$2(bouncerToGoneFlows.primaryBouncerInteractor));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition
    public final Flow getNotificationBlurRadius() {
        return this.notificationBlurRadius;
    }
}
