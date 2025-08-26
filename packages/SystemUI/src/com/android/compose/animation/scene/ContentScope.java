package com.android.compose.animation.scene;

import androidx.compose.runtime.Composer;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.ui.Modifier;

/* loaded from: classes.dex */
public interface ContentScope {
    void Element(ElementKey elementKey, Modifier modifier, ComposableLambdaImpl composableLambdaImpl, Composer composer, int i);

    Modifier element(Modifier modifier, ElementKey elementKey);

    MutableSceneTransitionLayoutState getLayoutState();
}
