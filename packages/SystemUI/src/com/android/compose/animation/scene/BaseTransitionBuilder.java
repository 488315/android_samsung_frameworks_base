package com.android.compose.animation.scene;

import com.android.compose.animation.scene.transformation.EdgeTranslate;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface BaseTransitionBuilder {
    static void translate$default(BaseTransitionBuilder baseTransitionBuilder, ElementKey elementKey, Edge edge) {
        BaseTransitionBuilderImpl baseTransitionBuilderImpl = (BaseTransitionBuilderImpl) baseTransitionBuilder;
        baseTransitionBuilderImpl.getClass();
        baseTransitionBuilderImpl.addTransformation(elementKey, new EdgeTranslate.Factory(edge, true));
    }
}
