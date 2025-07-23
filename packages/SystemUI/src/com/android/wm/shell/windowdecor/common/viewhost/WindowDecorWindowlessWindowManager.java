package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.res.Configuration;
import android.graphics.Region;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.WindowlessWindowManager;
import android.window.InputTransferToken;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WindowDecorWindowlessWindowManager extends WindowlessWindowManager {
    public WindowDecorWindowlessWindowManager(Configuration configuration, SurfaceControl surfaceControl) {
        super(configuration, surfaceControl, (InputTransferToken) null);
    }

    public final void setTouchRegion(SurfaceControlViewHost surfaceControlViewHost, Region region) {
        setTouchRegion(surfaceControlViewHost.getWindowToken().asBinder(), region);
    }
}
