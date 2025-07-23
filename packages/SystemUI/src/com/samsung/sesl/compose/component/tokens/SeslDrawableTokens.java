package com.samsung.sesl.compose.component.tokens;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SeslDrawableTokens {
    public static final Companion Companion = new Companion(null);
    public static final Drawable emptyDrawable = new ColorDrawable(0);
    public final Drawable drawable;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static boolean isBitmapOrNinePatch(Drawable drawable) {
            return (drawable instanceof BitmapDrawable) || (drawable instanceof NinePatchDrawable);
        }

        private Companion() {
        }
    }

    public SeslDrawableTokens(Drawable drawable) {
        this.drawable = drawable;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SeslDrawableTokens) && Intrinsics.areEqual(this.drawable, ((SeslDrawableTokens) obj).drawable);
    }

    public final int hashCode() {
        return this.drawable.hashCode();
    }

    public final String toString() {
        return "SeslDrawableTokens(drawable=" + this.drawable + ")";
    }
}
