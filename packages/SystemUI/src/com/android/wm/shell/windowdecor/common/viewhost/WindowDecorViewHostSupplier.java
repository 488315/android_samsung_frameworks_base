package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.view.Display;
import android.view.SurfaceControl;

/* loaded from: classes3.dex */
public interface WindowDecorViewHostSupplier {
    WindowDecorViewHost acquire(Context context, Display display);

    void release(WindowDecorViewHost windowDecorViewHost, SurfaceControl.Transaction transaction);
}
