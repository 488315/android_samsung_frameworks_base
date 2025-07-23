package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.SharedElementTransformation;
import com.android.compose.animation.scene.transformation.TransformationWithRange;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class SharedElementKt {
    public static final boolean isSharedElementEnabled(ElementKey elementKey, TransitionState.Transition transition) {
        SharedElementTransformation sharedElementTransformation;
        TransformationWithRange sharedElementTransformation2 = sharedElementTransformation(elementKey, transition);
        if (sharedElementTransformation2 == null || (sharedElementTransformation = (SharedElementTransformation) sharedElementTransformation2.transformation) == null) {
            return true;
        }
        return sharedElementTransformation.enabled;
    }

    public static final TransformationWithRange sharedElementTransformation(ElementKey elementKey, TransitionState.Transition transition) {
        TransformationSpecImpl transformationSpecImpl = transition.transformationSpec;
        ElementTransformations transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transformationSpecImpl.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(transition.fromContent, elementKey);
        TransformationWithRange transformationWithRange = transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout != null ? transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.shared : null;
        ElementTransformations transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 = transformationSpecImpl.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(transition.toContent, elementKey);
        TransformationWithRange transformationWithRange2 = transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 != null ? transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2.shared : null;
        if (Intrinsics.areEqual(transformationWithRange, transformationWithRange2)) {
            return transformationWithRange;
        }
        throw new IllegalStateException(("Different sharedElement() transformations matched " + elementKey + " (from=" + transformationWithRange + " to=" + transformationWithRange2 + ")").toString());
    }
}
