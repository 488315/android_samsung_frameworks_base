package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;

/* loaded from: classes5.dex */
public class DrawRect extends DrawBase4 {
    public static final DrawBase4.Companion COMPANION =
            new DrawBase4.Companion(
                    42) { // from class:
                          // com.android.internal.widget.remotecompose.core.operations.DrawRect.1
                @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Companion
                public Operation construct(float x1, float y1, float x2, float y2) {
                    return new DrawRect(x1, y1, x2, y2);
                }
            };

    public DrawRect(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
        this.mName = "DrawRect";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawRect(this.mX1, this.mY1, this.mX2, this.mY2);
    }
}
