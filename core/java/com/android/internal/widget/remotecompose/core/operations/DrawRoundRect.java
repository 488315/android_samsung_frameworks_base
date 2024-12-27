package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.operations.DrawBase6;

/* loaded from: classes5.dex */
public class DrawRoundRect extends DrawBase6 {
    public static final DrawBase6.Companion COMPANION = new DrawBase6.Companion(51) { // from class: com.android.internal.widget.remotecompose.core.operations.DrawRoundRect.1
        @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase6.Companion
        public Operation construct(float v1, float v2, float v3, float v4, float v5, float v6) {
            return new DrawRoundRect(v1, v2, v3, v4, v5, v6);
        }
    };

    public DrawRoundRect(float v1, float v2, float v3, float v4, float v5, float v6) {
        super(v1, v2, v3, v4, v5, v6);
        this.mName = "ClipRect";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.drawRoundRect(this.mV1, this.mV2, this.mV3, this.mV4, this.mV5, this.mV6);
    }
}
