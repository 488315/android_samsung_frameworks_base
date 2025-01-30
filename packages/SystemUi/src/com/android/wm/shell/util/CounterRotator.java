package com.android.wm.shell.util;

import android.graphics.Point;
import android.util.RotationUtils;
import android.view.SurfaceControl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class CounterRotator {
    public SurfaceControl mSurface = null;

    public final void setup(float f, float f2, int i, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        if (i == 0) {
            return;
        }
        SurfaceControl build = new SurfaceControl.Builder().setName("Transition Unrotate").setContainerLayer().setParent(surfaceControl).build();
        this.mSurface = build;
        RotationUtils.rotateSurface(transaction, build, i);
        Point point = new Point(0, 0);
        if (i % 2 != 0) {
            f2 = f;
            f = f2;
        }
        RotationUtils.rotatePoint(point, i, (int) f, (int) f2);
        transaction.setPosition(this.mSurface, point.x, point.y);
        transaction.show(this.mSurface);
    }
}
