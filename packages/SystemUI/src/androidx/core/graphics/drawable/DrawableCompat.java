package androidx.core.graphics.drawable;

import android.graphics.drawable.Drawable;

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
