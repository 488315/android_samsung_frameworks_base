package com.android.compose.animation.scene.transformation;

import com.android.compose.animation.scene.ElementMatcher;
import com.android.compose.animation.scene.transformation.Transformation;

/* loaded from: classes.dex */
public final class TransformationMatcher {
    public final Transformation.Factory factory;
    public final ElementMatcher matcher;
    public final TransformationRange range;

    public TransformationMatcher(ElementMatcher elementMatcher, Transformation.Factory factory, TransformationRange transformationRange) {
        this.matcher = elementMatcher;
        this.factory = factory;
        this.range = transformationRange;
    }
}
