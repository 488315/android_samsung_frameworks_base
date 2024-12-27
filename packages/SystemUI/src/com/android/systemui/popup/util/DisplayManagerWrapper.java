package com.android.systemui.popup.util;

import android.content.Context;
import android.hardware.display.DisplayManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class DisplayManagerWrapper {
    private final Context mContext;
    private final DisplayManager mDisplayManager;
    private DisplayManager.DisplayListener mListener;

    public DisplayManagerWrapper(Context context) {
        this.mContext = context;
        this.mDisplayManager = (DisplayManager) context.getSystemService("display");
    }

    public boolean isSubDisplay() {
        return false;
    }

    public void registerDisplayChangedListener(final Runnable runnable) {
        DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() { // from class: com.android.systemui.popup.util.DisplayManagerWrapper.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayChanged(int i) {
                runnable.run();
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public void onDisplayRemoved(int i) {
            }
        };
        this.mListener = displayListener;
        this.mDisplayManager.registerDisplayListener(displayListener, null);
    }

    public void unregisterDisplayChangedListener() {
        DisplayManager.DisplayListener displayListener = this.mListener;
        if (displayListener != null) {
            this.mDisplayManager.unregisterDisplayListener(displayListener);
        }
    }
}
