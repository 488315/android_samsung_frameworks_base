package com.android.compose.animation.scene;

import com.android.compose.animation.scene.transformation.TransformationWithRange;

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
