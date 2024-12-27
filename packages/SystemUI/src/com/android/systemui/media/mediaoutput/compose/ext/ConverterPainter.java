package com.android.systemui.media.mediaoutput.compose.ext;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.Painter;

public abstract class ConverterPainter extends Painter {
    public final long intrinsicSize;

    public ConverterPainter() {
        Size.Companion.getClass();
        this.intrinsicSize = Size.Unspecified;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* renamed from: getIntrinsicSize-NH-jbRc */
    public final long mo488getIntrinsicSizeNHjbRc() {
        return this.intrinsicSize;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
    }
}
