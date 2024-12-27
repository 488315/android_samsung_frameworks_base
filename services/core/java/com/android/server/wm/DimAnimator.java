package com.android.server.wm;

import android.content.Context;
import android.view.SurfaceControl;

public final class DimAnimator {
    public final WindowContainer mContainer;
    public final Context mContext;
    SurfaceControl mDimAnimationLayer;
    public int mTransitType = 0;

    public DimAnimator(WindowContainer windowContainer) {
        this.mContainer = windowContainer;
        this.mContext = windowContainer.mWmService.mContext;
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0058, code lost:

       if (r5 != false) goto L39;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean canCreateDimAnimationLayer(
            int r4,
            boolean r5,
            android.view.WindowManager.LayoutParams r6,
            android.window.TransitionInfo.Change r7) {
        /*
            r3 = this;
            com.android.server.wm.WindowContainer r3 = r3.mContainer
            com.android.server.wm.WindowManagerService r0 = r3.mWmService
            float r0 = r0.getTransitionAnimationScaleLocked()
            r1 = 0
            int r0 = (r0 > r1 ? 1 : (r0 == r1 ? 0 : -1))
            r2 = 0
            if (r0 > 0) goto Lf
            return r2
        Lf:
            com.android.server.wm.ActivityRecord r0 = r3.asActivityRecord()
            if (r0 != 0) goto L1c
            com.android.server.wm.Task r0 = r3.asTask()
            if (r0 != 0) goto L1c
            return r2
        L1c:
            if (r7 == 0) goto L26
            int r7 = r7.getMode()
            r0 = 6
            if (r7 != r0) goto L26
            return r2
        L26:
            if (r6 == 0) goto L3e
            float r7 = r6.dimAmount
            int r0 = (r7 > r1 ? 1 : (r7 == r1 ? 0 : -1))
            if (r0 <= 0) goto L35
            r0 = 1065353216(0x3f800000, float:1.0)
            int r7 = (r7 > r0 ? 1 : (r7 == r0 ? 0 : -1))
            if (r7 >= 0) goto L35
            return r2
        L35:
            int r6 = r6.windowAnimations
            boolean r6 = com.android.internal.policy.TransitionAnimation.isDefaultPackageAnimRes(r6)
            if (r6 != 0) goto L3e
            return r2
        L3e:
            com.android.server.wm.DisplayContent r6 = r3.getDisplayContent()
            boolean r6 = r6.isDefaultDisplay
            if (r6 != 0) goto L47
            return r2
        L47:
            r6 = 1
            if (r4 == r6) goto L58
            r7 = 2
            if (r4 == r7) goto L54
            r7 = 3
            if (r4 == r7) goto L58
            r7 = 4
            if (r4 == r7) goto L54
            return r2
        L54:
            if (r5 != 0) goto L57
            goto L5a
        L57:
            return r2
        L58:
            if (r5 == 0) goto L74
        L5a:
            boolean r4 = r3.inMultiWindowMode()
            if (r4 != 0) goto L74
            boolean r4 = r3.fillsParent()
            if (r4 != 0) goto L67
            goto L74
        L67:
            com.android.server.wm.DimAnimator$$ExternalSyntheticLambda0 r4 = new com.android.server.wm.DimAnimator$$ExternalSyntheticLambda0
            r4.<init>()
            com.android.server.wm.ActivityRecord r3 = r3.getActivity(r4)
            if (r3 == 0) goto L73
            return r2
        L73:
            return r6
        L74:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.DimAnimator.canCreateDimAnimationLayer(int, boolean,"
                    + " android.view.WindowManager$LayoutParams,"
                    + " android.window.TransitionInfo$Change):boolean");
    }

    public void createDimAnimationLayer(int i) {
        finishDimAnimation(0);
        if (this.mDimAnimationLayer == null) {
            this.mTransitType = i;
            WindowContainer windowContainer = this.mContainer;
            this.mDimAnimationLayer =
                    windowContainer
                            .makeChildSurface(null)
                            .setName("DimAnimationLayer for " + windowContainer.getName())
                            .setColorLayer()
                            .setParent(windowContainer.getDisplayContent().getSurfaceControl())
                            .setCallsite("WindowContainer#createAnimatingDimLayer")
                            .build();
        }
    }

    public final void finishDimAnimation(int i) {
        SurfaceControl surfaceControl = this.mDimAnimationLayer;
        if (surfaceControl == null || !surfaceControl.isValid()) {
            return;
        }
        WindowContainer windowContainer = this.mContainer;
        windowContainer.mWmService.mSurfaceAnimationRunner.onAnimationCancelled(
                this.mDimAnimationLayer);
        windowContainer.getSyncTransaction().reparent(this.mDimAnimationLayer, null);
        this.mDimAnimationLayer = null;
        this.mTransitType = 0;
        windowContainer.scheduleAnimation();
        if ((i & 2) != 0) {
            for (WindowContainer parent = windowContainer.getParent();
                    parent != null;
                    parent = parent.getParent()) {
                parent.mDimAnimator.finishDimAnimation(2);
            }
        }
    }
}
