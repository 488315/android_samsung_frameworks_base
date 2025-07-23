package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.modifiers.AnimatedBackgroundKt$$ExternalSyntheticLambda0;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$$ExternalSyntheticOutline0;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.util.kotlin.FlowKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockscreenToAodTransitionViewModel implements DeviceEntryIconTransition {
    public final ChannelLimitedFlowMerge deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final SafeFlow lockscreenAlphaOnFold;
    public final PowerInteractor powerInteractor;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimationOnFold;

    public LockscreenToAodTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, PowerInteractor powerInteractor, ShadeDependentFlows shadeDependentFlows, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        this.powerInteractor = powerInteractor;
        FromLockscreenTransitionInteractor.Companion.getClass();
        long j = FromLockscreenTransitionInteractor.TO_AOD_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.LOCKSCREEN;
        KeyguardState keyguardState2 = KeyguardState.AOD;
        KeyguardTransitionAnimationFlow.FlowBuilder m2598setupVtjQ1oo = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, KeyguardInteractor$$ExternalSyntheticOutline0.m(companion, keyguardState, keyguardState2));
        this.transitionAnimation = m2598setupVtjQ1oo;
        KeyguardTransitionAnimationFlow.FlowBuilder m2598setupVtjQ1oo2 = keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(FromLockscreenTransitionInteractor.TO_AOD_FOLD_DURATION, new Edge.StateToState(keyguardState, keyguardState2));
        this.transitionAnimationOnFold = m2598setupVtjQ1oo2;
        KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 immediatelyTransitionTo = m2598setupVtjQ1oo.immediatelyTransitionTo(0.0f);
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        final int i = 0;
        this.deviceEntryBackgroundViewAlpha = shadeDependentFlows.transitionFlow(immediatelyTransitionTo, KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(300, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Float f = (Float) obj;
                switch (i) {
                    case 0:
                        return Float.valueOf(1 - f.floatValue());
                    default:
                        return Float.valueOf(1 - f.floatValue());
                }
            }
        }, 0L, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 204));
        final int i2 = 1;
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                    case 0:
                        return Float.valueOf(1 - f.floatValue());
                    default:
                        return Float.valueOf(1 - f.floatValue());
                }
            }
        }, 0L, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 204);
        this.lockscreenAlphaOnFold = new SafeFlow(new LockscreenToAodTransitionViewModel$special$$inlined$transform$1(FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo2, DurationKt.toDuration(500, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda6(), DurationKt.toDuration(VolteConstants.ErrorCode.BUSY_EVERYWHERE, durationUnit), null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_removeDexShortcut), powerInteractor.detailedWakefulness, LockscreenToAodTransitionViewModel$lockscreenAlphaOnFold$4.INSTANCE), null));
        new SafeFlow(new LockscreenToAodTransitionViewModel$special$$inlined$transform$2(FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m2598setupVtjQ1oo2, DurationKt.toDuration(1100, durationUnit), new DozingToGoneTransitionViewModel$$ExternalSyntheticLambda0(), 0L, null, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), null, null, 220), powerInteractor.detailedWakefulness, LockscreenToAodTransitionViewModel$notificationAlphaOnFold$5.INSTANCE), null));
        this.deviceEntryParentViewAlpha = kotlinx.coroutines.flow.FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new LockscreenToAodTransitionViewModel$special$$inlined$flatMapLatest$1(null, shadeDependentFlows, this));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
