package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.res.Configuration;
import android.graphics.Region;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.WindowlessWindowManager;
import android.window.InputTransferToken;

/* loaded from: classes3.dex */
public final class WindowDecorWindowlessWindowManager extends WindowlessWindowManager {
    public WindowDecorWindowlessWindowManager(Configuration configuration, SurfaceControl surfaceControl) {
        super(configuration, surfaceControl, (InputTransferToken) null);
    }

    public final void setTouchRegion(SurfaceControlViewHost surfaceControlViewHost, Region region) {
        setTouchRegion(surfaceControlViewHost.getWindowToken().asBinder(), region);
    }
}
