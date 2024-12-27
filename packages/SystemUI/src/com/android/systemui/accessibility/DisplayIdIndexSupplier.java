package com.android.systemui.accessibility;

import android.hardware.display.DisplayManager;
import android.util.SparseArray;
import android.view.Display;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class DisplayIdIndexSupplier {
    public final DisplayManager mDisplayManager;
    public final SparseArray mSparseArray = new SparseArray();

    public DisplayIdIndexSupplier(DisplayManager displayManager) {
        this.mDisplayManager = displayManager;
    }

    public abstract Object createInstance(Display display);

    public final Object get(int i) {
        Object obj = this.mSparseArray.get(i);
        if (obj != null) {
            return obj;
        }
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            return null;
        }
        Object createInstance = createInstance(display);
        this.mSparseArray.put(i, createInstance);
        return createInstance;
    }
}
