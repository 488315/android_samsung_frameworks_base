package com.samsung.sesl.compose.component.tokens;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SeslDrawableTokens implements SeslDrawableSchemeKeyTokens {
    public static final Companion Companion = new Companion(null);
    public static final Drawable emptyDrawable = new ColorDrawable(0);
    public final Drawable drawable;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
