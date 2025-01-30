package com.android.systemui.accessibility;

import android.hardware.display.DisplayManager;
import android.util.SparseArray;
import android.view.Display;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class DisplayIdIndexSupplier {
    public final DisplayManager mDisplayManager;
    public final SparseArray mSparseArray = new SparseArray();

    public DisplayIdIndexSupplier(DisplayManager displayManager) {
        this.mDisplayManager = displayManager;
    }

    public abstract Object createInstance(Display display);

    public final Object get(int i) {
        SparseArray sparseArray = this.mSparseArray;
        Object obj = sparseArray.get(i);
        if (obj != null) {
            return obj;
        }
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            return null;
        }
        Object createInstance = createInstance(display);
        sparseArray.put(i, createInstance);
        return createInstance;
    }
}
