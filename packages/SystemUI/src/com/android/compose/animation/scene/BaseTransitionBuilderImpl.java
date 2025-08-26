package com.android.compose.animation.scene;

import com.android.compose.animation.scene.transformation.Fade;
import com.android.compose.animation.scene.transformation.Transformation;
import com.android.compose.animation.scene.transformation.TransformationMatcher;
import com.android.compose.animation.scene.transformation.TransformationRange;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseTransitionBuilderImpl implements BaseTransitionBuilder {
    public TransformationRange range;
    public final List transformationMatchers = new ArrayList();

    public final void addTransformation(ElementMatcher elementMatcher, Transformation.Factory factory) {
        List list = this.transformationMatchers;
        TransformationRange transformationRange = this.range;
        if (transformationRange == null) {
            transformationRange = null;
        }
        ((ArrayList) list).add(new TransformationMatcher(elementMatcher, factory, transformationRange));
    }

    public final void fade(ElementMatcher elementMatcher) {
        addTransformation(elementMatcher, Fade.Factory.INSTANCE);
    }
}
