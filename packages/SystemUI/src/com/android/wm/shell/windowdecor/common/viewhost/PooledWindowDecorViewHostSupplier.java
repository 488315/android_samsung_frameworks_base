package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.util.Pools;
import android.view.SurfaceControl;
import com.android.wm.shell.sysui.ShellInit;
import com.samsung.android.rune.CoreRune;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:8:0x001d, code lost:
    
        if (((com.android.wm.shell.windowdecor.common.viewhost.ReusableWindowDecorViewHost) r0).displayId == r13.getDisplayId()) goto L10;
     */
    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost acquire(android.content.Context r12, android.view.Display r13) {
        /*
            r11 = this;
            android.util.Pools$Pool r0 = r11.pool
            android.util.Pools$SynchronizedPool r0 = (android.util.Pools.SynchronizedPool) r0
            java.lang.Object r0 = r0.acquire()
            com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost r0 = (com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost) r0
            if (r0 == 0) goto L20
            boolean r1 = com.samsung.android.rune.CoreRune.MW_CAPTION_BUG_FIX
            if (r1 == 0) goto L1f
            boolean r1 = r0 instanceof com.android.wm.shell.windowdecor.common.viewhost.ReusableWindowDecorViewHost
            if (r1 == 0) goto L1f
            r1 = r0
            com.android.wm.shell.windowdecor.common.viewhost.ReusableWindowDecorViewHost r1 = (com.android.wm.shell.windowdecor.common.viewhost.ReusableWindowDecorViewHost) r1
            int r2 = r13.getDisplayId()
            int r1 = r1.displayId
            if (r1 != r2) goto L20
        L1f:
            return r0
        L20:
            java.lang.String r0 = "PooledWindowDecorViewHostSupplier#acquire-newInstance"
            android.os.Trace.beginSection(r0)
            com.android.wm.shell.windowdecor.common.viewhost.ReusableWindowDecorViewHost r1 = new com.android.wm.shell.windowdecor.common.viewhost.ReusableWindowDecorViewHost
            int r5 = r11.nextDecorViewHostId
            int r0 = r5 + 1
            r11.nextDecorViewHostId = r0
            r7 = 0
            r8 = 0
            kotlinx.coroutines.CoroutineScope r3 = r11.mainScope
            r6 = 0
            r9 = 112(0x70, float:1.57E-43)
            r10 = 0
            r2 = r12
            r4 = r13
            r1.<init>(r2, r3, r4, r5, r6, r7, r8, r9, r10)
            android.os.Trace.endSection()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.common.viewhost.PooledWindowDecorViewHostSupplier.acquire(android.content.Context, android.view.Display):com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost");
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHostSupplier
    public final void release(WindowDecorViewHost windowDecorViewHost, SurfaceControl.Transaction transaction) {
        if ((!CoreRune.MW_CAPTION_BUG_FIX || ((windowDecorViewHost instanceof ReusableWindowDecorViewHost) && ((ReusableWindowDecorViewHost) windowDecorViewHost).displayId == this.context.getDisplayId())) && this.pool.release(windowDecorViewHost)) {
            return;
        }
        windowDecorViewHost.release(transaction);
    }
}
