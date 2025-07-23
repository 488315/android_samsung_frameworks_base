package com.samsung.sesl.compose.phone.resources;

import androidx.compose.ui.geometry.Size;
import androidx.compose.ui.graphics.drawscope.DrawScope;
import androidx.compose.ui.graphics.painter.Painter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class EmptyPainter extends Painter {
    public static final EmptyPainter INSTANCE = new EmptyPainter();

    private EmptyPainter() {
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    /* renamed from: getIntrinsicSize-NH-jbRc */
    public final long mo561getIntrinsicSizeNHjbRc() {
        Size.Companion.getClass();
        return Size.Unspecified;
    }

    @Override // androidx.compose.ui.graphics.painter.Painter
    public final void onDraw(DrawScope drawScope) {
    }
}
