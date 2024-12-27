package com.android.server.wm;

import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;

import java.util.ArrayList;

public final class TrustedOverlayHost {
    public final ArrayList mOverlays = new ArrayList();
    public SurfaceControl mSurfaceControl;
    public final WindowManagerService mWmService;

    public TrustedOverlayHost(WindowManagerService windowManagerService) {
        this.mWmService = windowManagerService;
    }

    public final void release() {
        for (int size = this.mOverlays.size() - 1; size >= 0; size--) {
            SurfaceControlViewHost.SurfacePackage surfacePackage =
                    (SurfaceControlViewHost.SurfacePackage) this.mOverlays.get(size);
            try {
                surfacePackage.getRemoteInterface().onDispatchDetachedFromWindow();
            } catch (Exception unused) {
            }
            surfacePackage.release();
        }
        this.mOverlays.clear();
        ((SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get())
                .remove(this.mSurfaceControl)
                .apply();
        this.mSurfaceControl = null;
    }

    public final boolean removeOverlay(SurfaceControlViewHost.SurfacePackage surfacePackage) {
        SurfaceControl.Transaction transaction =
                (SurfaceControl.Transaction) this.mWmService.mTransactionFactory.get();
        for (int size = this.mOverlays.size() - 1; size >= 0; size--) {
            SurfaceControlViewHost.SurfacePackage surfacePackage2 =
                    (SurfaceControlViewHost.SurfacePackage) this.mOverlays.get(size);
            if (surfacePackage2
                    .getSurfaceControl()
                    .isSameSurface(surfacePackage.getSurfaceControl())) {
                this.mOverlays.remove(size);
                transaction.reparent(surfacePackage2.getSurfaceControl(), null);
                surfacePackage2.release();
            }
        }
        transaction.apply();
        return this.mOverlays.size() > 0;
    }
}
