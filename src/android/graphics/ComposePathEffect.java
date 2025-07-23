package android.graphics;

/* loaded from: classes.dex */
public class ComposePathEffect extends PathEffect {
    private static native long nativeCreate(long j, long j2);

    public ComposePathEffect(PathEffect pathEffect, PathEffect pathEffect2) {
        this.native_instance = nativeCreate(pathEffect.native_instance, pathEffect2.native_instance);
    }
}
