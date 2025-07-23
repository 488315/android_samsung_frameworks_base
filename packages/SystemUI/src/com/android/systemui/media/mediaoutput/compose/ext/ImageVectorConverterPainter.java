package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.ui.graphics.vector.ImageVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class ImageVectorConverterPainter extends ConverterPainter {
    public static final Companion Companion = new Companion(null);
    public final ImageVector imageVector;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static ImageVectorConverterPainter toConverter(ImageVector imageVector) {
            return new ImageVectorConverterPainter(imageVector, null);
        }

        private Companion() {
        }
    }

    public /* synthetic */ ImageVectorConverterPainter(ImageVector imageVector, DefaultConstructorMarker defaultConstructorMarker) {
        this(imageVector);
    }

    private ImageVectorConverterPainter(ImageVector imageVector) {
        this.imageVector = imageVector;
    }
}
