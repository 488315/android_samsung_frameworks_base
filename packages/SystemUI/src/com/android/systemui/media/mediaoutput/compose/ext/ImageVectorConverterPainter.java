package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.ui.graphics.vector.ImageVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ImageVectorConverterPainter extends ConverterPainter {
    public static final Companion Companion = new Companion(null);
    public final ImageVector imageVector;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
