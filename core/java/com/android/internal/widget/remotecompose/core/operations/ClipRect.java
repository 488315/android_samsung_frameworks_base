package com.android.internal.widget.remotecompose.core.operations;

import com.android.internal.widget.remotecompose.core.Operation;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.operations.DrawBase4;

/* loaded from: classes5.dex */
public class ClipRect extends DrawBase4 {
    public static final DrawBase4.Companion COMPANION = new DrawBase4.Companion(39) { // from class: com.android.internal.widget.remotecompose.core.operations.ClipRect.1
        @Override // com.android.internal.widget.remotecompose.core.operations.DrawBase4.Companion
        public Operation construct(float x1, float y1, float x2, float y2) {
            return new ClipRect(x1, y1, x2, y2);
        }
    };

    public ClipRect(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
        this.mName = "ClipRect";
    }

    @Override // com.android.internal.widget.remotecompose.core.PaintOperation
    public void paint(PaintContext context) {
        context.clipRect(this.mX1, this.mY1, this.mX2, this.mY2);
    }
}
