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
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes2.dex */
public final class LockscreenToDozingTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 shortcutsAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public LockscreenToDozingTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromLockscreenTransitionInteractor.Companion.getClass();
        long j = FromLockscreenTransitionInteractor.TO_DOZING_DURATION;
        KeyguardTransitionAnimationFlow.FlowBuilder flowBuilderM2613setupVtjQ1oo = keyguardTransitionAnimationFlow.m2613setupVtjQ1oo(j, KeyguardInteractor$$ExternalSyntheticOutline0.m(Edge.Companion, KeyguardState.LOCKSCREEN, KeyguardState.DOZING));
        this.transitionAnimation = flowBuilderM2613setupVtjQ1oo;
        this.lockscreenAlpha = flowBuilderM2613setupVtjQ1oo.immediatelyTransitionTo(1.0f);
        Duration.Companion companion = Duration.Companion;
        final int i = 0;
        this.shortcutsAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDozingTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i) {
                    case 0:
                        return Float.valueOf(1 - f.floatValue());
                    default:
                        f.getClass();
                        return null;
                }
            }
        }, 0L, null, new AnimatedBackgroundKt$$ExternalSyntheticLambda0(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 204);
        final int i2 = 1;
        this.deviceEntryBackgroundViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2614sharedFlow74qcysc$default(flowBuilderM2613setupVtjQ1oo, j, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToDozingTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                Float f = (Float) obj;
                switch (i2) {
                    case 0:
                        return Float.valueOf(1 - f.floatValue());
                    default:
                        f.getClass();
                        return null;
                }
            }
        }, 0L, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), null, null, 204);
        this.deviceEntryParentViewAlpha = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new LockscreenToDozingTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
