package com.samsung.android.desktopsystemui.sharedlib.system;

import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.TransitionInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface RemoteTransitionRunner {
    void startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, Runnable runnable);

    default void mergeAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, IBinder iBinder2, Runnable runnable) {
    }
}
