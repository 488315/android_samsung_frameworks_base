package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;

public class MatrixRotate extends DrawBase3 {
    public static final DrawBase3.Companion COMPANION =
            new DrawBase3.Companion(
                    129) { // from class:
                           // com.android.internal.widget.remotecompose.core.operations.MatrixRotate.1
                @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase3.Companion
                public Operation construct(float rotate, float pivotX, float pivotY) {
                    return new MatrixRotate(rotate, pivotX, pivotY);
                }
            };

    public MatrixRotate(float rotate, float pivotX, float pivotY) {
        super(rotate, pivotX, pivotY);
        this.mName = "MatrixRotate";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.matrixRotate(this.mV1, this.mV2, this.mV3);
    }
}
