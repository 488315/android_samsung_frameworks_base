package com.android.wm.shell.freeform;

import android.graphics.Rect;

/* loaded from: classes3.dex */
public interface FreeformContainerCallback {
    void onItemAdded(FreeformContainerItem freeformContainerItem);

    void onItemRemoved(FreeformContainerItem freeformContainerItem);

    void onRotationChanged(int i, int i2, Rect rect);

    void onViewDestroyed();
}
