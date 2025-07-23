package com.android.compose.animation.scene.transformation;

import com.android.compose.animation.scene.ElementMatcher;
import com.android.compose.animation.scene.transformation.Transformation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
