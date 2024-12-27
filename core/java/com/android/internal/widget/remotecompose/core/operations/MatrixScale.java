package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.operations.DrawBase4;

/* loaded from: classes5.dex */
public class MatrixScale extends DrawBase4 {
    public static final DrawBase4.Companion COMPANION = new DrawBase4.Companion(126) { // from class: com.android.internal.widget.remotecompose.core.operations.MatrixScale.1
        @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Companion
        public Operation construct(float scaleX, float scaleY, float centerX, float centerY) {
            return new MatrixScale(scaleX, scaleY, centerX, centerY);
        }
    };

    public MatrixScale(float scaleX, float scaleY, float centerX, float centerY) {
        super(scaleX, scaleY, centerX, centerY);
        this.mName = "MatrixScale";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.matrixScale(this.mX1, this.mY1, this.mX2, this.mY2);
    }
}
