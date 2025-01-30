package androidx.core.graphics.drawable;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DrawableCompat {
    private DrawableCompat() {
    }

    public static void setTint(int i, Drawable drawable) {
        drawable.setTint(i);
    }

    public static void setTintList(Drawable drawable, ColorStateList colorStateList) {
        drawable.setTintList(colorStateList);
    }

    public static void setTintMode(Drawable drawable, PorterDuff.Mode mode) {
        drawable.setTintMode(mode);
    }
}
