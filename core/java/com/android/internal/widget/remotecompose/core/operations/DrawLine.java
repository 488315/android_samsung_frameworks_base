package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;

public class DrawLine extends DrawBase4 {
    public static final DrawBase4.Companion COMPANION =
            new DrawBase4.Companion(
                    47) { // from class:
                          // com.android.internal.widget.remotecompose.core.operations.DrawLine.1
                @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Companion
                public Operation construct(float x1, float y1, float x2, float y2) {
                    return new DrawLine(x1, y1, x2, y2);
                }
            };

    public DrawLine(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
        this.mName = "DrawLine";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawLine(this.mX1, this.mY1, this.mX2, this.mY2);
    }
}
