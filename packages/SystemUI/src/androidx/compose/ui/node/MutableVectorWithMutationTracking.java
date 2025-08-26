package androidx.compose.ui.node;

import androidx.compose.runtime.collection.MutableVector;
import kotlin.jvm.functions.Function0;

/* loaded from: classes.dex */
public final class MutableVectorWithMutationTracking<T> {
    public final Function0 onVectorMutated;
    public final MutableVector vector;

    public MutableVectorWithMutationTracking(MutableVector<T> mutableVector, Function0 function0) {
        this.vector = mutableVector;
        this.onVectorMutated = function0;
    }
}
