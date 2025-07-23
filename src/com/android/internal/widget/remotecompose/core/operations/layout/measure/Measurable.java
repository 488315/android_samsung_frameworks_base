package com.android.internal.widget.remotecompose.core.operations.layout.measure;

import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;

/* loaded from: classes6.dex */
public interface Measurable {
    void animatingBounds(RemoteContext remoteContext);

    void layout(RemoteContext remoteContext, MeasurePass measurePass);

    void measure(PaintContext paintContext, float f, float f2, float f3, float f4, MeasurePass measurePass);

    boolean needsMeasure();
}
