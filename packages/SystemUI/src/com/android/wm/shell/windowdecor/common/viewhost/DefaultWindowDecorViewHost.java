package com.android.wm.shell.windowdecor.common.viewhost;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Region;
import android.os.Trace;
import android.view.Display;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.view.View;
import android.view.WindowManager;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DefaultWindowDecorViewHost implements WindowDecorViewHost {
    public StandaloneCoroutine currentUpdateJob;
    public final CoroutineScope mainScope;
    public final SurfaceControlViewHostAdapter viewHostAdapter;

    public DefaultWindowDecorViewHost(Context context, CoroutineScope coroutineScope, Display display, SurfaceControlViewHostAdapter surfaceControlViewHostAdapter) {
        this.mainScope = coroutineScope;
        this.viewHostAdapter = surfaceControlViewHostAdapter;
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final SurfaceControl getSurfaceControl() {
        return this.viewHostAdapter.rootSurface;
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final void release(SurfaceControl.Transaction transaction) {
        StandaloneCoroutine standaloneCoroutine = this.currentUpdateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.currentUpdateJob = null;
        SurfaceControlViewHostAdapter surfaceControlViewHostAdapter = this.viewHostAdapter;
        SurfaceControlViewHost surfaceControlViewHost = surfaceControlViewHostAdapter.viewHost;
        if (surfaceControlViewHost != null) {
            surfaceControlViewHost.release();
        }
        transaction.remove(surfaceControlViewHostAdapter.rootSurface);
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final void updateView(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region, SurfaceControl.Transaction transaction) {
        Trace.beginSection("DefaultWindowDecorViewHost#updateView");
        StandaloneCoroutine standaloneCoroutine = this.currentUpdateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.currentUpdateJob = null;
        Trace.beginSection("DefaultWindowDecorViewHost#updateViewHost");
        SurfaceControlViewHostAdapter surfaceControlViewHostAdapter = this.viewHostAdapter;
        surfaceControlViewHostAdapter.prepareViewHost(configuration, region);
        if (transaction != null) {
            surfaceControlViewHostAdapter.requireViewHost().getRootSurfaceControl().applyTransactionOnDraw(transaction);
        }
        surfaceControlViewHostAdapter.updateView(view, layoutParams);
        Trace.endSection();
        Trace.endSection();
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final void updateViewAsync(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region) {
        Trace.beginSection("DefaultWindowDecorViewHost#updateViewAsync");
        StandaloneCoroutine standaloneCoroutine = this.currentUpdateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.currentUpdateJob = null;
        this.currentUpdateJob = BuildersKt.launch$default(this.mainScope, null, null, new DefaultWindowDecorViewHost$updateViewAsync$1(this, view, layoutParams, configuration, region, null), 3);
        Trace.endSection();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public /* synthetic */ DefaultWindowDecorViewHost(android.content.Context r7, kotlinx.coroutines.CoroutineScope r8, android.view.Display r9, com.android.wm.shell.windowdecor.common.viewhost.SurfaceControlViewHostAdapter r10, int r11, kotlin.jvm.internal.DefaultConstructorMarker r12) {
        /*
            r6 = this;
            r11 = r11 & 8
            if (r11 == 0) goto L10
            com.android.wm.shell.windowdecor.common.viewhost.SurfaceControlViewHostAdapter r0 = new com.android.wm.shell.windowdecor.common.viewhost.SurfaceControlViewHostAdapter
            r4 = 4
            r5 = 0
            r3 = 0
            r1 = r7
            r2 = r9
            r0.<init>(r1, r2, r3, r4, r5)
            r10 = r0
            goto L12
        L10:
            r1 = r7
            r2 = r9
        L12:
            r6.<init>(r1, r8, r2, r10)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.windowdecor.common.viewhost.DefaultWindowDecorViewHost.<init>(android.content.Context, kotlinx.coroutines.CoroutineScope, android.view.Display, com.android.wm.shell.windowdecor.common.viewhost.SurfaceControlViewHostAdapter, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
    }
}
