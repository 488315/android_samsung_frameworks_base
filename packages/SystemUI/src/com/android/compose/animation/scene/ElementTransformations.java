package com.android.compose.animation.scene;

import com.android.compose.animation.scene.transformation.TransformationWithRange;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ElementTransformations {
    public final TransformationWithRange alpha;
    public final TransformationWithRange drawScale;
    public final TransformationWithRange offset;
    public final TransformationWithRange shared;
    public final TransformationWithRange size;

    public ElementTransformations(TransformationWithRange transformationWithRange, TransformationWithRange transformationWithRange2, TransformationWithRange transformationWithRange3, TransformationWithRange transformationWithRange4, TransformationWithRange transformationWithRange5) {
        this.shared = transformationWithRange;
        this.offset = transformationWithRange2;
        this.size = transformationWithRange3;
        this.drawScale = transformationWithRange4;
        this.alpha = transformationWithRange5;
    }
}
