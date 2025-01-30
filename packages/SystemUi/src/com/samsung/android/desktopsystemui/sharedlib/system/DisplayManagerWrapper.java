package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.hardware.display.DisplayManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DisplayManagerWrapper {
    private static final DisplayManagerWrapper sInstance = new DisplayManagerWrapper();
    private static final DisplayManager mDisplayManager = (DisplayManager) AppGlobals.getInitialApplication().getSystemService("display");

    private DisplayManagerWrapper() {
    }

    public static DisplayManagerWrapper getInstance() {
        return sInstance;
    }

    public void setBrightness(int i, float f) {
        mDisplayManager.setBrightness(i, f);
    }

    public void setTempBrightness(int i, float f) {
        mDisplayManager.setTemporaryBrightness(i, f);
    }
}
