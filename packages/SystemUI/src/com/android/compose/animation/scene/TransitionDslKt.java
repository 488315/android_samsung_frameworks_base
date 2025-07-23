package com.android.compose.animation.scene;

import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class TransitionDslKt {
    public static final HighestZIndexContentPicker DefaultElementContentPicker = HighestZIndexContentPicker.INSTANCE;

    public static final SceneTransitions transitions(Function1 function1) {
        SceneTransitionsBuilderImpl sceneTransitionsBuilderImpl = new SceneTransitionsBuilderImpl();
        function1.mo779invoke(sceneTransitionsBuilderImpl);
        return new SceneTransitions(sceneTransitionsBuilderImpl.transitionSpecs, sceneTransitionsBuilderImpl.interruptionHandler);
    }
}
