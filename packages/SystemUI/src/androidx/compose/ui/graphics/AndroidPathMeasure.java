package androidx.compose.ui.graphics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
