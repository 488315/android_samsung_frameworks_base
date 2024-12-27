package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryUdfpsInteractor;
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GoneToDozingTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryBackgroundViewAlpha;
    public final ChannelFlowTransformLatest deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 notificationAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public GoneToDozingTransitionViewModel(DeviceEntryUdfpsInteractor deviceEntryUdfpsInteractor, KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromGoneTransitionInteractor.Companion.getClass();
        long j = FromGoneTransitionInteractor.TO_DOZING_DURATION;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.DOZING;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GONE, keyguardState, keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(j, new Edge.SceneToState(sceneKey, keyguardState)));
        this.transitionAnimation = m;
        Duration.Companion companion2 = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDozingTransitionViewModel$lockscreenAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final /* bridge */ /* synthetic */ Object invoke(Object obj) {
                ((Number) obj).floatValue();
                return Float.valueOf(0.0f);
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDozingTransitionViewModel$lockscreenAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(1.0f);
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDozingTransitionViewModel$lockscreenAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(1.0f);
            }
        }, null, null, 204);
        KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDozingTransitionViewModel$notificationAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(1.0f - ((Number) obj).floatValue());
            }
        }, 0L, null, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDozingTransitionViewModel$notificationAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(1.0f);
            }
        }, null, null, 220);
        this.deviceEntryBackgroundViewAlpha = m.immediatelyTransitionTo(0.0f);
        this.deviceEntryParentViewAlpha = FlowKt.transformLatest(deviceEntryUdfpsInteractor.isUdfpsEnrolledAndEnabled, new GoneToDozingTransitionViewModel$special$$inlined$flatMapLatest$1(null, this));
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
