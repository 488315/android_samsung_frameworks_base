package com.android.systemui.scene.ui.composable;

import com.android.compose.animation.scene.SceneTransitions;
import com.android.compose.animation.scene.TransitionDslKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ConstantSceneContainerTransitionsBuilder implements SceneContainerTransitionsBuilder {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SceneTransitions transitions;

    static {
        SceneTransitions.Companion companion = SceneTransitions.Companion;
    }

    public ConstantSceneContainerTransitionsBuilder() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public ConstantSceneContainerTransitionsBuilder(SceneTransitions sceneTransitions) {
        this.transitions = sceneTransitions;
    }

    public /* synthetic */ ConstantSceneContainerTransitionsBuilder(SceneTransitions sceneTransitions, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? TransitionDslKt.transitions(new ConstantSceneContainerTransitionsBuilder$$ExternalSyntheticLambda0()) : sceneTransitions);
    }
}
