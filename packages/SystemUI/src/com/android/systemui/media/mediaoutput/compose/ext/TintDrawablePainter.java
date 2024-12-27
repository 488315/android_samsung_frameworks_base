package com.android.systemui.media.mediaoutput.compose.ext;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class TintDrawablePainter extends ConverterPainter {
    public static final Companion Companion = new Companion(null);
    public final Drawable drawable;

    public final class Companion {
        private Companion() {
        }

        public static TintDrawablePainter toConverter(Drawable drawable) {
            return new TintDrawablePainter(drawable, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ TintDrawablePainter(Drawable drawable, DefaultConstructorMarker defaultConstructorMarker) {
        this(drawable);
    }

    private TintDrawablePainter(Drawable drawable) {
        this.drawable = drawable;
    }
}
