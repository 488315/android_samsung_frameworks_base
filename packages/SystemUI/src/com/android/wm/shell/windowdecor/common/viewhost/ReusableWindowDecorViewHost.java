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
import android.widget.FrameLayout;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ReusableWindowDecorViewHost implements WindowDecorViewHost {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public StandaloneCoroutine currentUpdateJob;
    public final int displayId;
    public final int id;
    public final CoroutineScope mainScope;
    public final FrameLayout rootView;
    public final SurfaceControlViewHostAdapter viewHostAdapter;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public ReusableWindowDecorViewHost(Context context, CoroutineScope coroutineScope, Display display, int i, SurfaceControlViewHostAdapter surfaceControlViewHostAdapter, FrameLayout frameLayout, int i2) {
        this.context = context;
        this.mainScope = coroutineScope;
        this.id = i;
        this.viewHostAdapter = surfaceControlViewHostAdapter;
        this.rootView = frameLayout;
        this.displayId = i2;
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
        Trace.beginSection("ReusableWindowDecorViewHost#updateView");
        StandaloneCoroutine standaloneCoroutine = this.currentUpdateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.currentUpdateJob = null;
        updateViewHost$1(view, layoutParams, configuration, region, transaction);
        Trace.endSection();
    }

    @Override // com.android.wm.shell.windowdecor.common.viewhost.WindowDecorViewHost
    public final void updateViewAsync(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region) {
        Trace.beginSection("ReusableWindowDecorViewHost#updateViewAsync");
        StandaloneCoroutine standaloneCoroutine = this.currentUpdateJob;
        if (standaloneCoroutine != null) {
            standaloneCoroutine.cancel(null);
        }
        this.currentUpdateJob = null;
        this.currentUpdateJob = BuildersKt.launch$default(this.mainScope, null, null, new ReusableWindowDecorViewHost$updateViewAsync$1(this, view, layoutParams, configuration, region, null), 3);
        Trace.endSection();
    }

    public final void updateViewHost$1(View view, WindowManager.LayoutParams layoutParams, Configuration configuration, Region region, SurfaceControl.Transaction transaction) {
        Trace.beginSection("ReusableWindowDecorViewHost#updateViewHost");
        SurfaceControlViewHostAdapter surfaceControlViewHostAdapter = this.viewHostAdapter;
        surfaceControlViewHostAdapter.prepareViewHost(configuration, region);
        if (transaction != null) {
            surfaceControlViewHostAdapter.requireViewHost().getRootSurfaceControl().applyTransactionOnDraw(transaction);
        }
        if (!Intrinsics.areEqual(view.getParent(), this.rootView)) {
            this.rootView.removeAllViews();
            this.rootView.addView(view);
        }
        surfaceControlViewHostAdapter.updateView(this.rootView, layoutParams);
        Trace.endSection();
    }

    public /* synthetic */ ReusableWindowDecorViewHost(Context context, CoroutineScope coroutineScope, Display display, int i, SurfaceControlViewHostAdapter surfaceControlViewHostAdapter, FrameLayout frameLayout, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, coroutineScope, display, i, (i3 & 16) != 0 ? new SurfaceControlViewHostAdapter(context, display, null, 4, null) : surfaceControlViewHostAdapter, (i3 & 32) != 0 ? new FrameLayout(context) : frameLayout, (i3 & 64) != 0 ? display.getDisplayId() : i2);
    }
}
