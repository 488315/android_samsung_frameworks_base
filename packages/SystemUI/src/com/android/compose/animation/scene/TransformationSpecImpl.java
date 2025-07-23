package com.android.compose.animation.scene;

import androidx.compose.animation.core.AnimationSpec;
import com.android.compose.animation.scene.transformation.PropertyTransformation;
import com.android.compose.animation.scene.transformation.SharedElementTransformation;
import com.android.compose.animation.scene.transformation.Transformation;
import com.android.compose.animation.scene.transformation.TransformationMatcher;
import com.android.compose.animation.scene.transformation.TransformationRange;
import com.android.compose.animation.scene.transformation.TransformationWithRange;
import com.android.systemui.bixby2.controller.mediacontrol.MoveFromCurrentPositionController;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.NoWhenBranchMatchedException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TransformationSpecImpl implements TransformationSpec {
    public final Map cache = new LinkedHashMap();
    public final UserActionDistance distance;
    public final AnimationSpec progressSpec;
    public final List transformationMatchers;

    public TransformationSpecImpl(AnimationSpec<Float> animationSpec, UserActionDistance userActionDistance, List<TransformationMatcher> list) {
        this.progressSpec = animationSpec;
        this.distance = userActionDistance;
        this.transformationMatchers = list;
    }

    public static void throwIfNotNull(TransformationWithRange transformationWithRange, ElementKey elementKey, String str) {
        if (transformationWithRange == null) {
            return;
        }
        throw new IllegalStateException((elementKey + " has multiple " + str + " transformations").toString());
    }

    public final ElementTransformations transformations$frameworks__base__packages__SystemUI__compose__scene__android_common__PlatformComposeSceneTransitionLayout(ContentKey contentKey, ElementKey elementKey) {
        LinkedHashMap linkedHashMap = (LinkedHashMap) this.cache;
        Object obj = linkedHashMap.get(elementKey);
        if (obj == null) {
            obj = new LinkedHashMap();
            linkedHashMap.put(elementKey, obj);
        }
        Map map = (Map) obj;
        Object obj2 = map.get(contentKey);
        if (obj2 == null) {
            List list = this.transformationMatchers;
            int size = list.size();
            TransformationWithRange transformationWithRange = null;
            TransformationWithRange transformationWithRange2 = null;
            TransformationWithRange transformationWithRange3 = null;
            TransformationWithRange transformationWithRange4 = null;
            TransformationWithRange transformationWithRange5 = null;
            for (int i = 0; i < size; i++) {
                TransformationMatcher transformationMatcher = (TransformationMatcher) list.get(i);
                if (transformationMatcher.matcher.matches(contentKey, elementKey)) {
                    Transformation create = transformationMatcher.factory.create();
                    boolean z = create instanceof SharedElementTransformation;
                    TransformationRange transformationRange = transformationMatcher.range;
                    if (z) {
                        throwIfNotNull(transformationWithRange, elementKey, "shared");
                        transformationWithRange = new TransformationWithRange(create, transformationRange);
                    } else {
                        if (!(create instanceof PropertyTransformation)) {
                            throw new NoWhenBranchMatchedException();
                        }
                        PropertyTransformation propertyTransformation = (PropertyTransformation) create;
                        PropertyTransformation.Property property = propertyTransformation.getProperty();
                        if (property instanceof PropertyTransformation.Property.Offset) {
                            throwIfNotNull(transformationWithRange2, elementKey, MoveFromCurrentPositionController.OFFSET);
                            transformationWithRange2 = new TransformationWithRange(propertyTransformation, transformationRange);
                        } else if (property instanceof PropertyTransformation.Property.Size) {
                            throwIfNotNull(transformationWithRange3, elementKey, "size");
                            transformationWithRange3 = new TransformationWithRange(propertyTransformation, transformationRange);
                        } else if (property instanceof PropertyTransformation.Property.Scale) {
                            throwIfNotNull(transformationWithRange4, elementKey, "drawScale");
                            transformationWithRange4 = new TransformationWithRange(propertyTransformation, transformationRange);
                        } else {
                            if (!(property instanceof PropertyTransformation.Property.Alpha)) {
                                throw new NoWhenBranchMatchedException();
                            }
                            throwIfNotNull(transformationWithRange5, elementKey, "alpha");
                            transformationWithRange5 = new TransformationWithRange(propertyTransformation, transformationRange);
                        }
                    }
                }
            }
            obj2 = (transformationWithRange == null && transformationWithRange2 == null && transformationWithRange3 == null && transformationWithRange4 == null && transformationWithRange5 == null) ? null : new ElementTransformations(transformationWithRange, transformationWithRange2, transformationWithRange3, transformationWithRange4, transformationWithRange5);
            map.put(contentKey, obj2);
        }
        return (ElementTransformations) obj2;
    }
}
