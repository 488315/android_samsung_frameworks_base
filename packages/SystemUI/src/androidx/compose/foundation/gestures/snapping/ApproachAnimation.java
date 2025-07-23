package androidx.compose.foundation.gestures.snapping;

import androidx.compose.animation.core.AnimationVector;
import androidx.compose.foundation.gestures.ScrollScope;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
interface ApproachAnimation<T, V extends AnimationVector> {
    Object approachAnimation(ScrollScope scrollScope, Object obj, Object obj2, Function1 function1, Continuation continuation);
}
