package com.android.compose.animation.scene;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface ContentScope {
    void Element(ElementKey elementKey, Modifier modifier, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i);

    Modifier element(Modifier modifier, ElementKey elementKey);

    MutableSceneTransitionLayoutState getLayoutState();
}
