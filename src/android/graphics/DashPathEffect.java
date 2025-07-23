package android.graphics;

/* loaded from: classes.dex */
public class DashPathEffect extends PathEffect {
    private static native long nativeCreate(float[] fArr, float f);

    public DashPathEffect(float[] fArr, float f) {
        if (fArr.length < 2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        this.native_instance = nativeCreate(fArr, f);
    }
}
