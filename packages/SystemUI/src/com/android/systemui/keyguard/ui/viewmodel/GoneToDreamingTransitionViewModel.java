package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.domain.interactor.FromGoneTransitionInteractor;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.scene.shared.model.Scenes;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class GoneToDreamingTransitionViewModel {
    public final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 lockscreenAlpha;

    public GoneToDreamingTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        FromGoneTransitionInteractor.Companion.getClass();
        long j = FromGoneTransitionInteractor.TO_DREAMING_DURATION;
        Edge.Companion companion = Edge.Companion;
        SceneKey sceneKey = Scenes.Gone;
        KeyguardState keyguardState = KeyguardState.DREAMING;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m = AlternateBouncerToGoneTransitionViewModel$$ExternalSyntheticOutline0.m(KeyguardState.GONE, keyguardState, keyguardTransitionAnimationFlow.m2598setupVtjQ1oo(j, new Edge.ContentToState(sceneKey, keyguardState)));
        Duration.Companion companion2 = Duration.Companion;
        this.lockscreenAlpha = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(m, DurationKt.toDuration(IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, DurationUnit.MILLISECONDS), new GoneToDreamingTransitionViewModel$$ExternalSyntheticLambda0(), 0L, null, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_setDexForegroundModePackageList);
    }
}
