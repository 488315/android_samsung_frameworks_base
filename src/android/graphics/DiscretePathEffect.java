package android.graphics;

/* loaded from: classes.dex */
public class DiscretePathEffect extends PathEffect {
    private static native long nativeCreate(float f, float f2);

    public DiscretePathEffect(float f, float f2) {
        this.native_instance = nativeCreate(f, f2);
    }
}
