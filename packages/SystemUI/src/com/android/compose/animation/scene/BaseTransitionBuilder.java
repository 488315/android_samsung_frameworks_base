package com.android.compose.animation.scene;

import com.android.compose.animation.scene.transformation.EdgeTranslate;

/* loaded from: classes.dex */
public interface BaseTransitionBuilder {
    static void translate$default(BaseTransitionBuilder baseTransitionBuilder, ElementKey elementKey, Edge edge) {
        BaseTransitionBuilderImpl baseTransitionBuilderImpl = (BaseTransitionBuilderImpl) baseTransitionBuilder;
        baseTransitionBuilderImpl.getClass();
        baseTransitionBuilderImpl.addTransformation(elementKey, new EdgeTranslate.Factory(edge, true));
    }
}
