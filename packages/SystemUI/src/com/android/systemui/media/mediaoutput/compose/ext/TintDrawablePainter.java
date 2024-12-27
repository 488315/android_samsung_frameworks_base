package com.android.systemui.media.mediaoutput.compose.ext;

import android.graphics.drawable.Drawable;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class TintDrawablePainter extends ConverterPainter {
    public static final Companion Companion = new Companion(null);
    public final Drawable drawable;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
