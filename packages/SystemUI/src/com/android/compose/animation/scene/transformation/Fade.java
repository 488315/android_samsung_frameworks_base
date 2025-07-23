package com.android.compose.animation.scene.transformation;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.PropertyTransformationScopeImpl;
import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.PropertyTransformation;
import com.android.compose.animation.scene.transformation.Transformation;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Fade implements InterpolatedPropertyTransformation {
    public static final Fade INSTANCE = new Fade();
    public static final PropertyTransformation.Property.Alpha property = PropertyTransformation.Property.Alpha.INSTANCE;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Factory implements Transformation.Factory {
        public static final Factory INSTANCE = new Factory();

        private Factory() {
        }

        @Override // com.android.compose.animation.scene.transformation.Transformation.Factory
        public final Transformation create() {
            return Fade.INSTANCE;
        }
    }

    private Fade() {
    }

    @Override // com.android.compose.animation.scene.transformation.PropertyTransformation
    public final PropertyTransformation.Property getProperty() {
        return property;
    }

    @Override // com.android.compose.animation.scene.transformation.InterpolatedPropertyTransformation
    public final /* bridge */ /* synthetic */ Object transform(PropertyTransformationScopeImpl propertyTransformationScopeImpl, ContentKey contentKey, ElementKey elementKey, TransitionState.Transition transition, Object obj) {
        ((Number) obj).floatValue();
        return Float.valueOf(0.0f);
    }
}
