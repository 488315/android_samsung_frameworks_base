package com.android.systemui.media.mediaoutput.compose.ext;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class TintDrawablePainter extends ConverterPainter {
    public static final Companion Companion = new Companion(null);
    public final Drawable drawable;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static TintDrawablePainter toConverter(Drawable drawable) {
            return new TintDrawablePainter(drawable, null);
        }

        private Companion() {
        }
    }

    public /* synthetic */ TintDrawablePainter(Drawable drawable, DefaultConstructorMarker defaultConstructorMarker) {
        this(drawable);
    }

    private TintDrawablePainter(Drawable drawable) {
        this.drawable = drawable;
    }
}
