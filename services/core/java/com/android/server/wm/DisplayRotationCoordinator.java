package com.android.server.wm;

import android.util.Slog;
import android.view.Display;

/* loaded from: classes3.dex */
public class DisplayRotationCoordinator {
    public int mDefaultDisplayCurrentRotation;
    public int mDefaultDisplayDefaultRotation;
    Runnable mDefaultDisplayRotationChangedCallback;

    public void onDefaultDisplayRotationChanged(int i) {
        this.mDefaultDisplayCurrentRotation = i;
        Runnable runnable = this.mDefaultDisplayRotationChangedCallback;
        if (runnable != null) {
            runnable.run();
        }
    }

    public void setDefaultDisplayDefaultRotation(int i) {
        this.mDefaultDisplayDefaultRotation = i;
    }

    public int getDefaultDisplayCurrentRotation() {
        return this.mDefaultDisplayCurrentRotation;
    }

    public void setDefaultDisplayRotationChangedCallback(Runnable runnable) {
        if (this.mDefaultDisplayRotationChangedCallback != null) {
            Slog.d("DisplayRotationCoordinator", "Multiple clients unsupported");
            return;
        }
        this.mDefaultDisplayRotationChangedCallback = runnable;
        if (this.mDefaultDisplayCurrentRotation != this.mDefaultDisplayDefaultRotation) {
            runnable.run();
        }
    }

    public void removeDefaultDisplayRotationChangedCallback() {
        this.mDefaultDisplayRotationChangedCallback = null;
    }

    public static boolean isSecondaryInternalDisplay(DisplayContent displayContent) {
        Display display;
        return (displayContent.isDefaultDisplay || (display = displayContent.mDisplay) == null || display.getType() != 1) ? false : true;
    }
}
