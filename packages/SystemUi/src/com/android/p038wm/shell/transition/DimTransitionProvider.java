package com.android.p038wm.shell.transition;

import android.view.SurfaceControl;
import android.view.SurfaceSession;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DimTransitionProvider {
    public static SurfaceControl attachDimTransitionSurface(SurfaceSession surfaceSession, SurfaceControl surfaceControl, SurfaceControl surfaceControl2, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        SurfaceControl build = new SurfaceControl.Builder(surfaceSession).setParent(surfaceControl).setColorLayer().setName("DimTransitionLayer for " + surfaceControl2).setCallsite("DimTransitionProvider#attachDimTransitionSurface").build();
        transaction.setRelativeLayer(build, surfaceControl2, -1);
        transaction.setAlpha(build, 0.0f);
        transaction.show(build);
        if (build.isValid()) {
            transaction2.reparent(build, null);
        }
        return build;
    }
}
