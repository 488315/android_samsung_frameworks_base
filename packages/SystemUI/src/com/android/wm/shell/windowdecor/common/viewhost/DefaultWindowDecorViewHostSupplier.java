package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.view.Display;
import android.view.SurfaceControl;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultWindowDecorViewHostSupplier implements WindowDecorViewHostSupplier {
    public final CoroutineScope mainScope;

    public DefaultWindowDecorViewHostSupplier(CoroutineScope coroutineScope) {
        this.mainScope = coroutineScope;
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier
    public final WindowDecorViewHost acquire(Context context, Display display) {
        return new DefaultWindowDecorViewHost(context, this.mainScope, display, null, 8, null);
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier
    public final void release(WindowDecorViewHost windowDecorViewHost, SurfaceControl.Transaction transaction) {
        windowDecorViewHost.release(transaction);
    }
}
