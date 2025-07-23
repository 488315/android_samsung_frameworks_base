package com.android.wm.shell.windowdecor;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface DragPositioningCallback {
    Rect onDragPositioningEnd(float f, float f2, int i);

    Rect onDragPositioningMove(float f, float f2, int i);

    Rect onDragPositioningStart(int i, float f, float f2, int i2);
}
