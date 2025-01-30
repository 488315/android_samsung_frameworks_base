package androidx.picker.helper;

import android.graphics.drawable.Drawable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class DrawableHelperKt {
    public static final Drawable newMutateDrawable(Drawable drawable) {
        Drawable.ConstantState constantState;
        Drawable newDrawable;
        if (drawable == null || (constantState = drawable.getConstantState()) == null || (newDrawable = constantState.newDrawable()) == null) {
            return null;
        }
        return newDrawable.mutate();
    }
}
