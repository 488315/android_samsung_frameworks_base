package android.graphics;

/* loaded from: classes.dex */
public class EmbossMaskFilter extends MaskFilter {
    private static native long nativeConstructor(float[] fArr, float f, float f2, float f3);

    @Deprecated
    public EmbossMaskFilter(float[] fArr, float f, float f2, float f3) {
        if (fArr.length < 3) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.native_instance = nativeConstructor(fArr, f, f2, f3);
    }
}
