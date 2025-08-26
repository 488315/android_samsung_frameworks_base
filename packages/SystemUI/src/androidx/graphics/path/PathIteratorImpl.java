package androidx.graphics.path;

import android.graphics.Path;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public abstract class PathIteratorImpl {

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        if ("dalvik".equalsIgnoreCase(System.getProperty("java.vm.name"))) {
            System.loadLibrary("androidx.graphics.path");
        }
    }

    public PathIteratorImpl(Path path, PathIterator$ConicEvaluation pathIterator$ConicEvaluation, float f) {
    }

    public /* synthetic */ PathIteratorImpl(Path path, PathIterator$ConicEvaluation pathIterator$ConicEvaluation, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(path, (i & 2) != 0 ? PathIterator$ConicEvaluation.AsQuadratics : pathIterator$ConicEvaluation, (i & 4) != 0 ? 0.25f : f);
    }
}
