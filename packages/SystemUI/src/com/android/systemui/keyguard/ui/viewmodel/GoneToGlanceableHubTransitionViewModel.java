package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class GoneToGlanceableHubTransitionViewModel implements DeviceEntryIconTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;

    public GoneToGlanceableHubTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromGoneTransitionInteractor.Companion.getClass();
        long j = FromGoneTransitionInteractor.TO_GLANCEABLE_HUB_DURATION;
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.GONE;
        SceneKey sceneKey = Scenes.Communal;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GLANCEABLE_HUB, keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(j, new Edge.StateToScene(keyguardState, sceneKey)));
        Duration.Companion companion2 = Duration.Companion;
        this.deviceEntryParentViewAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m, DurationKt.toDuration(167, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToGlanceableHubTransitionViewModel$deviceEntryParentViewAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue());
            }
        }, 0L, null, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToGlanceableHubTransitionViewModel$deviceEntryParentViewAlpha$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        }, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToGlanceableHubTransitionViewModel$deviceEntryParentViewAlpha$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(1.0f);
            }
        }, null, null, 204);
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }
}
