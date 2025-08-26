package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.view.Display;
import android.view.SurfaceControl;
import kotlinx.coroutines.CoroutineScope;

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
