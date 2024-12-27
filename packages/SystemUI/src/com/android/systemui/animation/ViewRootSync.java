package com.android.systemui.animation;

import android.view.View;
import android.window.SurfaceSyncGroup;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

public final class ViewRootSync {
    public static final ViewRootSync INSTANCE = new ViewRootSync();

    private ViewRootSync() {
    }

    public static void synchronizeNextDraw(View view, View view2, final Function0 function0) {
        if (!view.isAttachedToWindow() || view.getViewRootImpl() == null || !view2.isAttachedToWindow() || view2.getViewRootImpl() == null || Intrinsics.areEqual(view.getViewRootImpl(), view2.getViewRootImpl())) {
            function0.invoke();
            return;
        }
        SurfaceSyncGroup surfaceSyncGroup = new SurfaceSyncGroup("SysUIAnimation");
        surfaceSyncGroup.addSyncCompleteCallback(view.getContext().getMainExecutor(), new Runnable() { // from class: com.android.systemui.animation.ViewRootSync$synchronizeNextDraw$1
            @Override // java.lang.Runnable
            public final void run() {
                Function0.this.invoke();
            }
        });
        surfaceSyncGroup.add(view.getRootSurfaceControl(), (Runnable) null);
        surfaceSyncGroup.add(view2.getRootSurfaceControl(), (Runnable) null);
        surfaceSyncGroup.markSyncReady();
    }
}
