package com.android.systemui.keyguard.ui.viewmodel;

import com.android.compose.animation.scene.SceneKey;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.scene.shared.model.Scenes;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class OccludedToGoneTransitionViewModel {
    public static final long DEFAULT_DURATION;
    public final KeyguardTransitionAnimationFlow.FlowBuilder transitionAnimation;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
        Duration.Companion companion = Duration.Companion;
        DEFAULT_DURATION = DurationKt.toDuration(300, DurationUnit.MILLISECONDS);
    }

    public OccludedToGoneTransitionViewModel(KeyguardTransitionAnimationFlow keyguardTransitionAnimationFlow) {
        Edge.Companion companion = Edge.Companion;
        KeyguardState keyguardState = KeyguardState.OCCLUDED;
        SceneKey sceneKey = Scenes.Gone;
        companion.getClass();
        KeyguardTransitionAnimationFlow.FlowBuilder m1961setupVtjQ1oo = keyguardTransitionAnimationFlow.m1961setupVtjQ1oo(DEFAULT_DURATION, new Edge.StateToScene(keyguardState, sceneKey));
        KeyguardState keyguardState2 = KeyguardState.GONE;
        companion.getClass();
        m1961setupVtjQ1oo.setupWithoutSceneContainer(new Edge.StateToState(keyguardState, keyguardState2));
    }
}
