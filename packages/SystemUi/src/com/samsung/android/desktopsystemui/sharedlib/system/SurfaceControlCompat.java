package com.samsung.android.desktopsystemui.sharedlib.system;

import android.view.SurfaceControl;
import android.view.View;
import android.view.ViewRootImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SurfaceControlCompat {
    final SurfaceControl mSurfaceControl;

    public SurfaceControlCompat(SurfaceControl surfaceControl) {
        this.mSurfaceControl = surfaceControl;
    }

    public SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    public boolean isValid() {
        SurfaceControl surfaceControl = this.mSurfaceControl;
        return surfaceControl != null && surfaceControl.isValid();
    }

    public SurfaceControlCompat(View view) {
        ViewRootImpl viewRootImpl = view.getViewRootImpl();
        this.mSurfaceControl = viewRootImpl != null ? viewRootImpl.getSurfaceControl() : null;
    }
}
