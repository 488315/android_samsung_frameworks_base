package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.ui.graphics.vector.ImageVector;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class ImageVectorConverterPainter extends ConverterPainter {
    public static final Companion Companion = new Companion(null);
    public final ImageVector imageVector;

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
