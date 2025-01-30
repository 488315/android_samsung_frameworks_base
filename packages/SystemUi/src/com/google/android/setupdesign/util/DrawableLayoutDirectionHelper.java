package com.google.android.setupdesign.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import com.google.android.setupcompat.internal.TemplateLayout;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DrawableLayoutDirectionHelper {
    public static InsetDrawable createRelativeInsetDrawable(Drawable drawable, int i, int i2, TemplateLayout templateLayout) {
        return templateLayout.getLayoutDirection() == 1 ? new InsetDrawable(drawable, i2, 0, i, 0) : new InsetDrawable(drawable, i, 0, i2, 0);
    }
}
