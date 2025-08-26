package com.android.compose.animation.scene;

import com.android.compose.animation.scene.content.state.TransitionState;
import com.android.compose.animation.scene.transformation.SharedElementTransformation;
import com.android.compose.animation.scene.transformation.TransformationWithRange;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public abstract class SharedElementKt {
    public static final boolean isSharedElementEnabled(ElementKey elementKey, TransitionState.Transition transition) {
        SharedElementTransformation sharedElementTransformation;
        TransformationWithRange transformationWithRangeSharedElementTransformation = sharedElementTransformation(elementKey, transition);
        if (transformationWithRangeSharedElementTransformation == null || (sharedElementTransformation = (SharedElementTransformation) transformationWithRangeSharedElementTransformation.transformation) == null) {
            return true;
        }
        return sharedElementTransformation.enabled;
    }

    public static final TransformationWithRange sharedElementTransformation(ElementKey elementKey, TransitionState.Transition transition) {
        TransformationSpecImpl transformationSpecImpl = transition.transformationSpec;
        ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout = transformationSpecImpl.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(transition.fromContent, elementKey);
        TransformationWithRange transformationWithRange = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout.shared : null;
        ElementTransformations elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 = transformationSpecImpl.transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(transition.toContent, elementKey);
        TransformationWithRange transformationWithRange2 = elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2 != null ? elementTransformationsTransformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout2.shared : null;
        if (Intrinsics.areEqual(transformationWithRange, transformationWithRange2)) {
            return transformationWithRange;
        }
        throw new IllegalStateException(("Different sharedElement() transformations matched " + elementKey + " (from=" + transformationWithRange + " to=" + transformationWithRange2 + ")").toString());
    }
}
