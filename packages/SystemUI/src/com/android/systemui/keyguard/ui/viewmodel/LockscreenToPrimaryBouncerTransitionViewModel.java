package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.compose.animation.scene.OverlayKey;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.BlurConfig;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition;
import com.android.systemui.scene.shared.model.Overlays;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockscreenToPrimaryBouncerTransitionViewModel implements DeviceEntryIconTransition, PrimaryBouncerTransition {
    public final LockscreenToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0 alphaForAnimationStep;
    public final BlurConfig blurConfig;
    public final ChannelLimitedFlowMerge deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationAlpha;
    public final EmptyFlow notificationBlurRadius;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final ChannelLimitedFlowMerge windowBlurRadius;

    /* JADX WARN: Type inference failed for: r10v0, types: [com.android.systemui.keyguard.ui.viewmodel.LockscreenToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0, kotlin.jvm.functions.Function1] */
    public LockscreenToPrimaryBouncerTransitionViewModel(BlurConfig blurConfig, ShadeDependentFlows shadeDependentFlows, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        this.blurConfig = blurConfig;
        FromLockscreenTransitionInteractor.Companion.getClass();
        long j = FromLockscreenTransitionInteractor.TO_PRIMARY_BOUNCER_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        OverlayKey overlayKey = Overlays.Bouncer;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.PRIMARY_BOUNCER, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.StateToContent(keyguardState, overlayKey)));
        final int i = 0;
        ?? r10 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Float f = (Float) obj;
                switch (i) {
                }
                return Float.valueOf(1.0f - f.floatValue());
            }
        };
        this.alphaForAnimationStep = r10;
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, DurationKt.toDuration(200, durationUnit), r10, 0L, null, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 220);
        this.shortcutsAlpha = m2599sharedFlow74qcysc$default;
        this.lockscreenAlpha = m2599sharedFlow74qcysc$default;
        this.notificationAlpha = m2599sharedFlow74qcysc$default;
        EmptyFlow emptyFlow = EmptyFlow.INSTANCE;
        this.notificationBlurRadius = emptyFlow;
        long duration = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit);
        final int i2 = 1;
        this.deviceEntryParentViewAlpha = shadeDependentFlows.transitionFlow(m.immediatelyTransitionTo(0.0f), KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, duration, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                }
                return Float.valueOf(1.0f - f.floatValue());
            }
        }, 0L, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 204));
        this.windowBlurRadius = shadeDependentFlows.transitionFlow(emptyFlow, KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToPrimaryBouncerTransitionViewModel$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                float floatValue = ((Float) obj).floatValue();
                BlurConfig blurConfig2 = LockscreenToPrimaryBouncerTransitionViewModel.this.blurConfig;
                return Float.valueOf(MathUtils.lerp(blurConfig2.minBlurRadiusPx, blurConfig2.maxBlurRadiusPx, floatValue));
            }
        }, 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.PrimaryBouncerTransition
    public final Flow getNotificationBlurRadius() {
        return this.notificationBlurRadius;
    }
}
