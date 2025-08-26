package com.android.wm.shell.windowdecor;

import android.graphics.Rect;

/* loaded from: classes3.dex */
public interface DragPositioningCallback {
    Rect onDragPositioningEnd(float f, float f2, int i);

    default Rect onDragPositioningEnd(float f, float f2, int i, boolean z) {
        return onDragPositioningEnd(f, f2, i);
    }

    Rect onDragPositioningMove(float f, float f2, int i);

    Rect onDragPositioningStart(int i, float f, float f2, int i2);
}
