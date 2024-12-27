package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;

public class MatrixTranslate extends DrawBase2 {
    public static final DrawBase2.Companion COMPANION =
            new DrawBase2.Companion(
                    127) { // from class:
                           // com.android.internal.widget.remotecompose.core.operations.MatrixTranslate.1
                @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase2.Companion
                public Operation construct(float x1, float y1) {
                    return new MatrixTranslate(x1, y1);
                }
            };

    public MatrixTranslate(float translateX, float translateY) {
        super(translateX, translateY);
        this.mName = "MatrixTranslate";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.matrixTranslate(this.mV1, this.mV2);
    }
}
