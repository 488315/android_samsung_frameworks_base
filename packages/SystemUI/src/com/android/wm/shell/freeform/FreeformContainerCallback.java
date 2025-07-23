package com.android.wm.shell.freeform;

import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface FreeformContainerCallback {
    void onItemAdded(FreeformContainerItem freeformContainerItem);

    void onItemRemoved(FreeformContainerItem freeformContainerItem);

    void onRotationChanged(int i, int i2, Rect rect);

    void onViewDestroyed();
}
