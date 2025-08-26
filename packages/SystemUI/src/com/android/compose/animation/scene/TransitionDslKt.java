package com.android.compose.animation.scene;

import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public abstract class TransitionDslKt {
    public static final HighestZIndexContentPicker DefaultElementContentPicker = HighestZIndexContentPicker.INSTANCE;

    public static final SceneTransitions transitions(Function1 function1) {
        SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl = new SceneTransitionsBuilderImpl();
        function1.mo781invoke(sceneTransitionsBuilderImpl);
        return new SceneTransitions(sceneTransitionsBuilderImpl.transitionSpecs, sceneTransitionsBuilderImpl.interruptionHandler);
    }
}
