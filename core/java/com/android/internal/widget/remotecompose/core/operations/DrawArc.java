package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;

public class DrawArc extends DrawBase6 {
    public static final DrawBase6.Companion COMPANION =
            new DrawBase6.Companion(
                    52) { // from class:
                          // com.android.internal.widget.remotecompose.core.operations.DrawArc.1
                @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase6.Companion
                public Operation construct(
                        float v1, float v2, float v3, float v4, float v5, float v6) {
                    return new DrawArc(v1, v2, v3, v4, v5, v6);
                }
            };

    public DrawArc(float v1, float v2, float v3, float v4, float v5, float v6) {
        super(v1, v2, v3, v4, v5, v6);
        this.mName = "DrawArc";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawArc(this.mV1, this.mV2, this.mV3, this.mV4, this.mV5, this.mV6);
    }
}
