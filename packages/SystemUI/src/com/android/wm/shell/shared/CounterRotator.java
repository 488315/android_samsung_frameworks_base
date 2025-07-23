package com.android.wm.shell.shared;

import android.graphics.Point;
import android.util.RotationUtils;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class CounterRotator {
    public SurfaceControl mSurface = null;

    public final void setup(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, int i, float f, float f2) {
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
