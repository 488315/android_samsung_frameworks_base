package android.graphics;

/* loaded from: classes.dex */
public class PathDashPathEffect extends PathEffect {
    private static native long nativeCreate(long j, float f, float f2, int i);

    public enum Style {
        TRANSLATE(0),
        ROTATE(1),
        MORPH(2);

        int native_style;

        Style(int i) {
            this.native_style = i;
        }
    }

    public PathDashPathEffect(Path path, float f, float f2, Style style) {
        this.native_instance = nativeCreate(path.readOnlyNI(), f, f2, style.native_style);
    }
}
