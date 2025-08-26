package androidx.compose.ui.platform;

/* loaded from: classes.dex */
public abstract class NestedScrollInteropConnectionKt {
    public static final int composeToViewOffset(float f) {
        return ((int) (f >= 0.0f ? Math.ceil(f) : Math.floor(f))) * (-1);
    }
}
