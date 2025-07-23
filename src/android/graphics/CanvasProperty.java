package android.graphics;

import com.android.internal.util.VirtualRefBasePtr;

/* loaded from: classes.dex */
public final class CanvasProperty<T> {
    private VirtualRefBasePtr mProperty;

    private static native long nCreateFloat(float f);

    private static native long nCreatePaint(long j);

    public static CanvasProperty<Float> createFloat(float f) {
        return new CanvasProperty<>(nCreateFloat(f));
    }

    public static CanvasProperty<Paint> createPaint(Paint paint) {
        return new CanvasProperty<>(nCreatePaint(paint.getNativeInstance()));
    }

    private CanvasProperty(long j) {
        this.mProperty = new VirtualRefBasePtr(j);
    }

    public long getNativeContainer() {
        return this.mProperty.get();
    }
}
