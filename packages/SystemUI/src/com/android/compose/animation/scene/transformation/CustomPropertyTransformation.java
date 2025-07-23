package com.android.compose.animation.scene.transformation;

import com.android.compose.animation.scene.ContentKey;
import com.android.compose.animation.scene.ElementKey;
import com.android.compose.animation.scene.PropertyTransformationScopeImpl;
import com.android.compose.animation.scene.content.state.TransitionState;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface CustomPropertyTransformation extends PropertyTransformation {
    Object transform(PropertyTransformationScopeImpl propertyTransformationScopeImpl, ContentKey contentKey, ElementKey elementKey, TransitionState.Transition transition, CoroutineScope coroutineScope);
}
