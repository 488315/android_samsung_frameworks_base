package com.samsung.android.desktopsystemui.sharedlib.system;

import android.app.AppGlobals;
import android.app.UiModeManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class UiModeManagerWrapper {
    private static final UiModeManagerWrapper sInstance = new UiModeManagerWrapper();
    private static final UiModeManager mUiModeManager = (UiModeManager) AppGlobals.getInitialApplication().getSystemService(UiModeManager.class);

    private UiModeManagerWrapper() {
    }

    public static UiModeManagerWrapper getInstance() {
        return sInstance;
    }

    public void setNightModeActivated(boolean z) {
        mUiModeManager.setNightModeActivated(z);
    }
}
