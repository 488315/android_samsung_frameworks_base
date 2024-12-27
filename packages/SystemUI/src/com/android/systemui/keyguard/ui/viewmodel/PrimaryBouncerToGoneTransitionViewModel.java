package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.Flags;
import com.android.systemui.bouncer.domain.interactor.PrimaryBouncerInteractor;
import com.android.systemui.keyguard.domain.interactor.FromPrimaryBouncerTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.scene.shared.model.Scenes;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PrimaryBouncerToGoneTransitionViewModel {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 bouncerAlpha;
    public boolean leaveShadeOpen;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationAlpha;
    public final PrimaryBouncerInteractor primaryBouncerInteractor;
    public final ChannelFlowTransformLatest scrimAlpha;
    public final Flow showAllNotifications;
    public final SysuiStatusBarStateController statusBarStateController;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;
    public boolean willRunDismissFromKeyguard;

    public PrimaryBouncerToGoneTransitionViewModel(SysuiStatusBarStateController sysuiStatusBarStateController, PrimaryBouncerInteractor primaryBouncerInteractor, Lazy lazy, BouncerToGoneFlows bouncerToGoneFlows, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        this.statusBarStateController = sysuiStatusBarStateController;
        this.primaryBouncerInteractor = primaryBouncerInteractor;
        FromPrimaryBouncerTransitionInteractor.Companion.getClass();
        long j = FromPrimaryBouncerTransitionInteractor.TO_GONE_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.PRIMARY_BOUNCER;
        SceneKey sceneKey = Scenes.Gone;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GONE, keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(j, new Edge.StateToScene(keyguardState, sceneKey)));
        this.transitionAnimation = m;
        bouncerToGoneFlows.m1968showAllNotificationsVtjQ1oo(j, keyguardState);
        Duration.Companion companion2 = Duration.Companion;
        this.notificationAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m, DurationKt.toDuration(200, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$notificationAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                float floatValue = ((Number) obj).floatValue();
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = PrimaryBouncerToGoneTransitionViewModel.this;
                float f = 1.0f;
                if (!primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard && !primaryBouncerToGoneTransitionViewModel.leaveShadeOpen) {
                    f = 1.0f - floatValue;
                }
                return Float.valueOf(f);
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$notificationAlpha$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = PrimaryBouncerToGoneTransitionViewModel.this;
                primaryBouncerToGoneTransitionViewModel.leaveShadeOpen = ((StatusBarStateControllerImpl) primaryBouncerToGoneTransitionViewModel.statusBarStateController).mLeaveOpenOnKeyguardHide;
                primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard = primaryBouncerToGoneTransitionViewModel.primaryBouncerInteractor.willRunDismissFromKeyguard();
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        Flags.sceneContainer();
        final PrimaryBouncerToGoneTransitionViewModel$bouncerAlpha$2 primaryBouncerToGoneTransitionViewModel$bouncerAlpha$2 = new PrimaryBouncerToGoneTransitionViewModel$bouncerAlpha$2(primaryBouncerInteractor);
        Duration.Companion companion3 = Duration.Companion;
        this.bouncerAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(this.transitionAnimation, DurationKt.toDuration(200, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$createBouncerAlphaFlow$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(PrimaryBouncerToGoneTransitionViewModel.this.willRunDismissFromKeyguard ? 0.0f : 1.0f - ((Number) obj).floatValue());
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$createBouncerAlphaFlow$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PrimaryBouncerToGoneTransitionViewModel.this.willRunDismissFromKeyguard = ((Boolean) primaryBouncerToGoneTransitionViewModel$bouncerAlpha$2.invoke()).booleanValue();
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode);
        Flags.sceneContainer();
        final PrimaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2 primaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2 = new PrimaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2(primaryBouncerInteractor);
        Duration.Companion companion4 = Duration.Companion;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(this.transitionAnimation, DurationKt.toDuration(50, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$createLockscreenAlpha$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                ((Number) obj).floatValue();
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = PrimaryBouncerToGoneTransitionViewModel.this;
                return Float.valueOf((primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard || primaryBouncerToGoneTransitionViewModel.leaveShadeOpen) ? 1.0f : 0.0f);
            }
        }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$createLockscreenAlpha$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                PrimaryBouncerToGoneTransitionViewModel primaryBouncerToGoneTransitionViewModel = PrimaryBouncerToGoneTransitionViewModel.this;
                primaryBouncerToGoneTransitionViewModel.leaveShadeOpen = ((StatusBarStateControllerImpl) primaryBouncerToGoneTransitionViewModel.statusBarStateController).mLeaveOpenOnKeyguardHide;
                primaryBouncerToGoneTransitionViewModel.willRunDismissFromKeyguard = ((Boolean) primaryBouncerToGoneTransitionViewModel$lockscreenAlpha$2.invoke()).booleanValue();
                return Unit.INSTANCE;
            }
        }, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.PrimaryBouncerToGoneTransitionViewModel$createLockscreenAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, null, null, IKnoxCustomManager.Stub.TRANSACTION_getWifiState);
        Flags.sceneContainer();
        this.scrimAlpha = bouncerToGoneFlows.m1967createScrimAlphaFlowKLykuaI(j, keyguardState, new BouncerToGoneFlows$scrimAlpha$2(bouncerToGoneFlows.primaryBouncerInteractor));
    }
}
