package androidx.compose.ui.graphics;

/* loaded from: classes.dex */
public final class AndroidPathMeasure implements PathMeasure {
    public final android.graphics.PathMeasure internalPathMeasure;

    public AndroidPathMeasure(android.graphics.PathMeasure pathMeasure) {
        this.internalPathMeasure = pathMeasure;
    }

    public final boolean getSegment(float f, float f2, AndroidPath androidPath) {
        android.graphics.PathMeasure pathMeasure = this.internalPathMeasure;
        if (androidPath != null) {
            return pathMeasure.getSegment(f, f2, androidPath.internalPath, true);
        }
        throw new UnsupportedOperationException("Unable to obtain android.graphics.Path");
    }
}
