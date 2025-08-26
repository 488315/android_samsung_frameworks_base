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
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class SurfaceControlViewHostAdapter {
    public final Context context;
    public final Display display;
    public final SurfaceControl rootSurface;
    public final Function4 surfaceControlViewHostFactory;
    public SurfaceControlViewHost viewHost;
    public WindowDecorWindowlessWindowManager wwm;

    public SurfaceControlViewHostAdapter(Context context, Display display, Function4 function4) {
        this.context = context;
        this.display = display;
        this.surfaceControlViewHostFactory = function4;
        this.rootSurface = new SurfaceControl.Builder().setName("SurfaceControlViewHostAdapter surface").setContainerLayer().setCallsite("SurfaceControlViewHostAdapter#init").build();
    }

    public final void prepareViewHost(Configuration configuration, Region region) {
        if (this.wwm == null) {
            this.wwm = new WindowDecorWindowlessWindowManager(configuration, this.rootSurface);
        }
        if (this.viewHost == null) {
            Context context = this.context;
            Display display = this.display;
            WindowDecorWindowlessWindowManager windowDecorWindowlessWindowManager = this.wwm;
            if (windowDecorWindowlessWindowManager == null) {
                throw new IllegalStateException("Expected non-null windowless window manager");
            }
            this.viewHost = (SurfaceControlViewHost) this.surfaceControlViewHostFactory.invoke(context, display, windowDecorWindowlessWindowManager, "SurfaceControlViewHostAdapter#prepareViewHost");
        }
        WindowDecorWindowlessWindowManager windowDecorWindowlessWindowManager2 = this.wwm;
        if (windowDecorWindowlessWindowManager2 == null) {
            throw new IllegalStateException("Expected non-null windowless window manager");
        }
        windowDecorWindowlessWindowManager2.setConfiguration(configuration);
        WindowDecorWindowlessWindowManager windowDecorWindowlessWindowManager3 = this.wwm;
        if (windowDecorWindowlessWindowManager3 == null) {
            throw new IllegalStateException("Expected non-null windowless window manager");
        }
        windowDecorWindowlessWindowManager3.setTouchRegion(requireViewHost(), region);
    }

    public final SurfaceControlViewHost requireViewHost() {
        SurfaceControlViewHost surfaceControlViewHost = this.viewHost;
        if (surfaceControlViewHost != null) {
            return surfaceControlViewHost;
        }
        throw new IllegalStateException("Expected non-null view host");
    }

    public final void updateView(View view, WindowManager.LayoutParams layoutParams) {
        if (requireViewHost().getView() == null) {
            Trace.beginSection("SurfaceControlViewHostAdapter#updateView-setView");
            requireViewHost().setView(view, layoutParams);
            Trace.endSection();
        } else {
            if (!Intrinsics.areEqual(requireViewHost().getView(), view)) {
                throw new IllegalStateException("Changing view is not allowed");
            }
            Trace.beginSection("SurfaceControlViewHostAdapter#updateView-relayout");
            requireViewHost().relayout(layoutParams);
            Trace.endSection();
        }
    }

    public /* synthetic */ SurfaceControlViewHostAdapter(Context context, Display display, Function4 function4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, display, (i & 4) != 0 ? new SurfaceControlViewHostAdapter$$ExternalSyntheticLambda0() : function4);
    }

    public static /* synthetic */ void getViewHost$annotations() {
    }
}
