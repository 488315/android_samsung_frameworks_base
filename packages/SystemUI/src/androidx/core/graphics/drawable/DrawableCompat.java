package androidx.core.graphics.drawable;

import android.graphics.drawable.Drawable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DrawableCompat {
    private DrawableCompat() {
    }

    public static void setTint(Drawable drawable, int i) {
        drawable.setTint(i);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static Drawable unwrap(Drawable drawable) {
        return drawable instanceof WrappedDrawable ? ((WrappedDrawableApi14) ((WrappedDrawable) drawable)).mDrawable : drawable;
    }
}
