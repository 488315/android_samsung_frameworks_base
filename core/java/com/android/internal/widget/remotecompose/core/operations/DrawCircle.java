package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;

/* loaded from: classes5.dex */
public class DrawCircle extends DrawBase3 {
    public static final DrawBase3.Companion COMPANION =
            new DrawBase3.Companion(
                    46) { // from class:
                          // com.android.internal.widget.remotecompose.core.operations.DrawCircle.1
                @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase3.Companion
                public Operation construct(float x1, float y1, float x2) {
                    return new DrawCircle(x1, y1, x2);
                }
            };

    public DrawCircle(float left, float top, float right) {
        super(left, top, right);
        this.mName = "DrawCircle";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawCircle(this.mV1, this.mV2, this.mV3);
    }
}
