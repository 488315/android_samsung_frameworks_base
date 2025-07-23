package androidx.compose.ui.platform;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class NestedScrollInteropConnectionKt {
    public static final int composeToViewOffset(float f) {
        return ((int) (f >= 0.0f ? Math.ceil(f) : Math.floor(f))) * (-1);
    }
}
