package com.android.server.wm;

import android.graphics.Rect;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class BlackFrame {
    public final BlackSurface[] mBlackSurfaces;
    public final Rect mInnerRect;
    public final Rect mOuterRect;
    public final Supplier mTransactionFactory;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BlackSurface {
        public final int left;
        public final SurfaceControl surface;
        public final int top;

        public BlackSurface(
                SurfaceControl.Transaction transaction,
                int i,
                int i2,
                int i3,
                int i4,
                DisplayContent displayContent,
                SurfaceControl surfaceControl) {
            this.left = i;
            this.top = i2;
            SurfaceControl build =
                    displayContent
                            .makeOverlay()
                            .setName("BlackSurface")
                            .setColorLayer()
                            .setParent(surfaceControl)
                            .setCallsite("BlackSurface")
                            .build();
            this.surface = build;
            transaction.setWindowCrop(build, i3 - i, i4 - i2);
            transaction.setAlpha(build, 1.0f);
            transaction.setLayer(build, 2010000);
            transaction.setPosition(build, i, i2);
            transaction.show(build);
            if (ProtoLogImpl_54989576.Cache.WM_SHOW_SURFACE_ALLOC_enabled[2]) {
                ProtoLogImpl_54989576.i(
                        ProtoLogGroup.WM_SHOW_SURFACE_ALLOC,
                        -2963535976860666511L,
                        4,
                        null,
                        String.valueOf(build),
                        Long.valueOf(2010000));
            }
        }
    }

    public BlackFrame(
            Supplier supplier,
            SurfaceControl.Transaction transaction,
            Rect rect,
            Rect rect2,
            DisplayContent displayContent,
            SurfaceControl surfaceControl) {
        BlackSurface[] blackSurfaceArr = new BlackSurface[4];
        this.mBlackSurfaces = blackSurfaceArr;
        this.mTransactionFactory = supplier;
        this.mOuterRect = new Rect(rect);
        this.mInnerRect = new Rect(rect2);
        try {
            int i = rect.top;
            int i2 = rect2.top;
            if (i < i2) {
                blackSurfaceArr[0] =
                        new BlackSurface(
                                transaction,
                                rect.left,
                                i,
                                rect2.right,
                                i2,
                                displayContent,
                                surfaceControl);
            }
            int i3 = rect.left;
            int i4 = rect2.left;
            if (i3 < i4) {
                blackSurfaceArr[1] =
                        new BlackSurface(
                                transaction,
                                i3,
                                rect2.top,
                                i4,
                                rect.bottom,
                                displayContent,
                                surfaceControl);
            }
            int i5 = rect.bottom;
            int i6 = rect2.bottom;
            if (i5 > i6) {
                blackSurfaceArr[2] =
                        new BlackSurface(
                                transaction,
                                rect2.left,
                                i6,
                                rect.right,
                                i5,
                                displayContent,
                                surfaceControl);
            }
            int i7 = rect.right;
            int i8 = rect2.right;
            if (i7 > i8) {
                blackSurfaceArr[3] =
                        new BlackSurface(
                                transaction,
                                i8,
                                rect.top,
                                i7,
                                rect2.bottom,
                                displayContent,
                                surfaceControl);
            }
        } catch (Throwable th) {
            kill();
            throw th;
        }
    }

    public final void kill() {
        SurfaceControl.Transaction transaction =
                (SurfaceControl.Transaction) this.mTransactionFactory.get();
        int i = 0;
        while (true) {
            BlackSurface[] blackSurfaceArr = this.mBlackSurfaces;
            if (i >= blackSurfaceArr.length) {
                transaction.apply();
                return;
            }
            BlackSurface blackSurface = blackSurfaceArr[i];
            if (blackSurface != null) {
                if (ProtoLogImpl_54989576.Cache.WM_SHOW_SURFACE_ALLOC_enabled[2]) {
                    ProtoLogImpl_54989576.i(
                            ProtoLogGroup.WM_SHOW_SURFACE_ALLOC,
                            -5633771912572750947L,
                            0,
                            null,
                            String.valueOf(blackSurface.surface));
                }
                transaction.remove(blackSurfaceArr[i].surface);
                blackSurfaceArr[i] = null;
            }
            i++;
        }
    }
}
