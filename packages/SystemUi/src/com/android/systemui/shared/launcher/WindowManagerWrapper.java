package com.android.systemui.shared.launcher;

import android.view.InsetsController;
import android.view.animation.Interpolator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WindowManagerWrapper {
    public static final WindowManagerWrapper sInstance;

    static {
        Interpolator interpolator = InsetsController.RESIZE_INTERPOLATOR;
        sInstance = new WindowManagerWrapper();
    }
}
