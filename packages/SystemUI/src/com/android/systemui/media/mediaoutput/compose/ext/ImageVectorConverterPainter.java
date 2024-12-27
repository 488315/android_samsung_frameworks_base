package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.ui.graphics.vector.ImageVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class ImageVectorConverterPainter extends ConverterPainter {
    public static final Companion Companion = new Companion(null);
    public final ImageVector imageVector;

    public final class Companion {
        private Companion() {
        }

        public static ImageVectorConverterPainter toConverter(ImageVector imageVector) {
            return new ImageVectorConverterPainter(imageVector, null);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ ImageVectorConverterPainter(ImageVector imageVector, DefaultConstructorMarker defaultConstructorMarker) {
        this(imageVector);
    }

    private ImageVectorConverterPainter(ImageVector imageVector) {
        this.imageVector = imageVector;
    }
}
