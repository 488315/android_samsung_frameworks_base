package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import com.android.systemui.keyguard.dagger.GlanceableHubBlurComponent;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition;
import com.android.systemui.keyguard.ui.transitions.GlanceableHubTransition;
import com.android.systemui.scene.shared.model.Scenes;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AodToGlanceableHubTransitionViewModel implements DeviceEntryIconTransition, GlanceableHubTransition {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 deviceEntryParentViewAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 windowBlurRadius;

    public AodToGlanceableHubTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow, GlanceableHubBlurComponent.Factory factory) {
        Duration.Companion companion = Duration.Companion;
        long duration = DurationKt.toDuration(1000, DurationUnit.MILLISECONDS);
        Edge.Companion companion2 = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.AOD;
        SceneKey sceneKey = Scenes.Communal;
        companion2.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(keyguardState, KeyguardState.GLANCEABLE_HUB, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(duration, new Edge.StateToContent(keyguardState, sceneKey)));
        this.transitionAnimation = m;
        this.deviceEntryParentViewAlpha = m.immediatelyTransitionTo(1.0f);
        this.windowBlurRadius = ((DaggerReferenceGlobalRootComponent.GlanceableHubBlurComponentImpl) factory.create(m)).getBlurProvider().enterBlurRadius;
    }

    @Override // com.android.systemui.keyguard.ui.transitions.DeviceEntryIconTransition
    public final Flow getDeviceEntryParentViewAlpha() {
        return this.deviceEntryParentViewAlpha;
    }

    public final Flow lockscreenAlpha(final ViewStateAccessor viewStateAccessor) {
        final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
        Duration.Companion companion = Duration.Companion;
        DurationUnit durationUnit = DurationUnit.MILLISECONDS;
        long duration = DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, durationUnit);
        long duration2 = DurationKt.toDuration(0, durationUnit);
        return KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(this.transitionAnimation, duration, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((Float) obj).floatValue()));
            }
        }, duration2, new Function0(viewStateAccessor) { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToGlanceableHubTransitionViewModel$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Ref$FloatRef.this.element = 0.0f;
                return Unit.INSTANCE;
            }
        }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp);
    }
}
