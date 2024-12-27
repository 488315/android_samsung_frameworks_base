package com.android.systemui.media.mediaoutput.compose.ext;

import kotlin.jvm.internal.DefaultConstructorMarker;

public final class DrawableResourceConverterPainter extends ConverterPainter {
    public static final Companion Companion = new Companion(null);
    public final int resId;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public /* synthetic */ DrawableResourceConverterPainter(int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(i);
    }

    private DrawableResourceConverterPainter(int i) {
        this.resId = i;
    }
}
