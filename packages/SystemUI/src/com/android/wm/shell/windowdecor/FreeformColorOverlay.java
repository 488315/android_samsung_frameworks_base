package com.android.wm.shell.windowdecor;

import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.SurfaceSession;

/* loaded from: classes3.dex */
public class FreeformColorOverlay {
    public final Rect mCropRect;
    public SurfaceControl mLeash;
    public final Object mLock;
    public final SurfaceControl.Transaction mTransaction;

    public FreeformColorOverlay() {
        Object obj = new Object();
        this.mLock = obj;
        this.mTransaction = new SurfaceControl.Transaction();
        this.mCropRect = new Rect();
        synchronized (obj) {
            this.mLeash = new SurfaceControl.Builder(new SurfaceSession()).setCallsite("FreeformColorOverlay").setName("FreeformColorOverlay").setContainerLayer().setHidden(true).build();
        }
    }

    public final boolean isLeashValidLocked() {
        SurfaceControl surfaceControl = this.mLeash;
        return surfaceControl != null && surfaceControl.isValid();
    }
}
