package androidx.graphics.path;

import android.graphics.Path;
import dalvik.annotation.optimization.FastNative;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PathIteratorPreApi34Impl extends PathIteratorImpl {
    public final long internalPathIterator;

    public /* synthetic */ PathIteratorPreApi34Impl(Path path, PathIterator$ConicEvaluation pathIterator$ConicEvaluation, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(path, (i & 2) != 0 ? PathIterator$ConicEvaluation.AsQuadratics : pathIterator$ConicEvaluation, (i & 4) != 0 ? 0.25f : f);
    }

    private final native long createInternalPathIterator(Path path, int i, float f);

    private final native void destroyInternalPathIterator(long j);

    @FastNative
    private final native boolean internalPathIteratorHasNext(long j);

    @FastNative
    private final native int internalPathIteratorNext(long j, float[] fArr, int i);

    @FastNative
    private final native int internalPathIteratorPeek(long j);

    @FastNative
    private final native int internalPathIteratorRawSize(long j);

    @FastNative
    private final native int internalPathIteratorSize(long j);

    public final void finalize() {
        destroyInternalPathIterator(this.internalPathIterator);
    }

    public PathIteratorPreApi34Impl(Path path, PathIterator$ConicEvaluation pathIterator$ConicEvaluation, float f) {
        super(path, pathIterator$ConicEvaluation, f);
        this.internalPathIterator = createInternalPathIterator(path, pathIterator$ConicEvaluation.ordinal(), f);
    }
}
