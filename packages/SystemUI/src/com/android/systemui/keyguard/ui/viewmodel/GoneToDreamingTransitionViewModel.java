package com.android.systemui.keyguard.ui.viewmodel;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.scene.shared.model.Scenes;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

public final class GoneToDreamingTransitionViewModel {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    public GoneToDreamingTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromGoneTransitionInteractor.Companion.getClass();
        long j = FromGoneTransitionInteractor.TO_DREAMING_DURATION;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.DREAMING;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GONE, keyguardState, keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(j, new Edge.SceneToState(sceneKey, keyguardState)));
        this.transitionAnimation = m;
        Duration.Companion companion2 = Duration.Companion;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(m, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, DurationUnit.MILLISECONDS), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDreamingTransitionViewModel$lockscreenAlpha$1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(1.0f - ((Number) obj).floatValue());
            }
        }, 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
    }

    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenTranslationY(final int i) {
        Duration.Companion companion = Duration.Companion;
        long duration = DurationKt.toDuration(500, DurationUnit.MILLISECONDS);
        Interpolator interpolator = Interpolators.EMPHASIZED_ACCELERATE;
        Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDreamingTransitionViewModel$lockscreenTranslationY$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Float.valueOf(((Number) obj).floatValue() * i);
            }
        };
        GoneToDreamingTransitionViewModel$lockscreenTranslationY$2 goneToDreamingTransitionViewModel$lockscreenTranslationY$2 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDreamingTransitionViewModel$lockscreenTranslationY$2
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        };
        GoneToDreamingTransitionViewModel$lockscreenTranslationY$3 goneToDreamingTransitionViewModel$lockscreenTranslationY$3 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToDreamingTransitionViewModel$lockscreenTranslationY$3
            @Override // kotlin.jvm.functions.Function0
            public final /* bridge */ /* synthetic */ Object invoke() {
                return Float.valueOf(0.0f);
            }
        };
        Intrinsics.checkNotNull(interpolator);
        return KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(this.transitionAnimation, duration, function1, 0L, null, goneToDreamingTransitionViewModel$lockscreenTranslationY$2, goneToDreamingTransitionViewModel$lockscreenTranslationY$3, interpolator, null, 140);
    }
}
