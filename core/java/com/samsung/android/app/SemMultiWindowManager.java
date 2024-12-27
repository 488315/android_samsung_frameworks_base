package com.samsung.android.app;

import android.content.pm.ResolveInfo;
import com.samsung.android.multiwindow.MultiWindowManager;

/* loaded from: classes5.dex */
public class SemMultiWindowManager {
    public static final int MODE_FREEFORM = 1;
    public static final int MODE_NONE = 0;
    public static final int MODE_PICTURE_IN_PICTURE = 4;
    public static final int MODE_SPLIT_SCREEN = 2;
    private final MultiWindowManager mMultiWindowManager = new MultiWindowManager();

    public void setMultiWindowEnabled(String key, boolean enabled) {
        this.mMultiWindowManager.setMultiWindowEnabled(key, "SEM_API", enabled);
    }

    public int getMode() {
        return this.mMultiWindowManager.getMultiWindowModeStates(0);
    }

    public int getSupportedModes(ResolveInfo rInfo) {
        return this.mMultiWindowManager.getSupportedMultiWindowModes(rInfo);
    }
}
