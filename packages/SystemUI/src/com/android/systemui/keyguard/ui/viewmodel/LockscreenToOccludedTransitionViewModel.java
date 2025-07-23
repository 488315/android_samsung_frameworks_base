package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractorImpl;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockscreenToOccludedTransitionViewModel implements DeviceEntryIconTransition {
    public final ChannelLimitedFlowMerge deviceEntryParentViewAlpha;
    public final ChannelLimitedFlowMerge lockscreenAlpha;
    public final ChannelLimitedFlowMerge shortcutsAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public LockscreenToOccludedTransitionViewModel(ShadeDependentFlows shadeDependentFlows, ConfigurationInteractor configurationInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromLockscreenTransitionInteractor.Companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m2598setupVtjQ1oo = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(FromLockscreenTransitionInteractor.TO_OCCLUDED_DURATION, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.OCCLUDED));
        this.transitionAnimation = m2598setupVtjQ1oo;
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        final int i = 0;
        this.lockscreenAlpha = shadeDependentFlows.transitionFlow(m2598setupVtjQ1oo.immediatelyTransitionTo(0.0f), KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Float f = (Float) obj;
                switch (i) {
                }
                return Float.valueOf(1.0f - f.floatValue());
            }
        }, 0L, null, null, null, null, "LOCKSCREEN->OCCLUDED: lockscreenAlpha", 124));
        final int i2 = 1;
        this.shortcutsAlpha = shadeDependentFlows.transitionFlow(m2598setupVtjQ1oo.immediatelyTransitionTo(0.0f), KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                }
                return Float.valueOf(1.0f - f.floatValue());
            }
        }, 0L, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 204));
        FlowKt.transformLatest(((ConfigurationInteractorImpl) configurationInteractor).dimensionPixelSize(R.dimen.lockscreen_to_occluded_transition_lockscreen_translation_y), new LockscreenToOccludedTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
        long duration = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit);
        final int i3 = 2;
        this.deviceEntryParentViewAlpha = shadeDependentFlows.transitionFlow(m2598setupVtjQ1oo.immediatelyTransitionTo(0.0f), KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, duration, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToOccludedTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Float f = (Float) obj;
                switch (i3) {
                }
                return Float.valueOf(1.0f - f.floatValue());
            }
        }, 0L, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getAppsButtonState));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
