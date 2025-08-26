package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.os.Trace;
import android.util.Pools;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import com.android.wm.shell.sysui.ShellInit;
import com.samsung.android.rune.CoreRune;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes3.dex */
public final class PooledWindowDecorViewHostSupplier implements WindowDecorViewHostSupplier {
    public final Context context;
    public final CoroutineScope mainScope;
    public int nextDecorViewHostId;
    public final Pools.Pool pool;
    public final int preWarmSize;

    public PooledWindowDecorViewHostSupplier(Context context, CoroutineScope coroutineScope, ShellInit shellInit, int i, int i2) {
        this.context = context;
        this.mainScope = coroutineScope;
        this.preWarmSize = i2;
        this.pool = new Pools.SynchronizedPool(i);
        if (i2 > i) {
            throw new IllegalArgumentException("Pre-warm size should not exceed pool size");
        }
        shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.windowdecor.common.viewhost.PooledWindowDecorViewHostSupplier.2
            @Override // java.lang.Runnable
            public final void run() {
                PooledWindowDecorViewHostSupplier pooledWindowDecorViewHostSupplier = PooledWindowDecorViewHostSupplier.this;
                int i3 = pooledWindowDecorViewHostSupplier.preWarmSize;
                if (i3 <= 0) {
                    return;
                }
                BuildersKt.launch$default(pooledWindowDecorViewHostSupplier.mainScope, null, null, new PooledWindowDecorViewHostSupplier$preWarmViewHosts$1(i3, pooledWindowDecorViewHostSupplier, null), 3);
            }
        }, this);
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier
    public final WindowDecorViewHost acquire(Context context, Display display) {
        View view;
        WindowDecorViewHost windowDecorViewHost = (WindowDecorViewHost) this.pool.acquire();
        if (windowDecorViewHost != null) {
            if (CoreRune.MW_CAPTION_BUG_FIX && (windowDecorViewHost instanceof ReusableWindowDecorViewHost)) {
                ReusableWindowDecorViewHost reusableWindowDecorViewHost = (ReusableWindowDecorViewHost) windowDecorViewHost;
                if (reusableWindowDecorViewHost.displayId == display.getDisplayId()) {
                    SurfaceControlViewHost surfaceControlViewHost = reusableWindowDecorViewHost.viewHostAdapter.viewHost;
                    if (surfaceControlViewHost != null && (view = surfaceControlViewHost.getView()) != null && view.getVisibility() == 8) {
                        view.setVisibility(0);
                    }
                }
            }
            return windowDecorViewHost;
        }
        Trace.beginSection("PooledWindowDecorViewHostSupplier#acquire-newInstance");
        int i = this.nextDecorViewHostId;
        this.nextDecorViewHostId = i + 1;
        ReusableWindowDecorViewHost reusableWindowDecorViewHost2 = new ReusableWindowDecorViewHost(context, this.mainScope, display, i, null, null, 0, 112, null);
        Trace.endSection();
        return reusableWindowDecorViewHost2;
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier
    public final void release(WindowDecorViewHost windowDecorViewHost, SurfaceControl.Transaction transaction) {
        if ((!CoreRune.MW_CAPTION_BUG_FIX || ((windowDecorViewHost instanceof ReusableWindowDecorViewHost) && ((ReusableWindowDecorViewHost) windowDecorViewHost).displayId == this.context.getDisplayId())) && this.pool.release(windowDecorViewHost)) {
            return;
        }
        windowDecorViewHost.release(transaction);
    }
}
