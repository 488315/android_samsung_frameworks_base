package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.view.Display;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface WindowDecorViewHostSupplier {
    WindowDecorViewHost acquire(Context context, Display display);

    void release(WindowDecorViewHost windowDecorViewHost, SurfaceControl.Transaction transaction);
}
