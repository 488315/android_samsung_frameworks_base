package com.android.wm.shell.transition;

import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.hardware.HardwareBuffer;
import android.view.SurfaceControl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class WindowThumbnail {
    public SurfaceControl mSurfaceControl;

    private WindowThumbnail() {
    }

    public static WindowThumbnail createAndAttach(SurfaceControl surfaceControl, HardwareBuffer hardwareBuffer, SurfaceControl.Transaction transaction) {
        WindowThumbnail windowThumbnail = new WindowThumbnail();
        windowThumbnail.mSurfaceControl = new SurfaceControl.Builder().setParent(surfaceControl).setName("WindowThumanil : " + surfaceControl.toString()).setCallsite("WindowThumanil").setFormat(-3).build();
        transaction.setBuffer(windowThumbnail.mSurfaceControl, GraphicBuffer.createFromHardwareBuffer(hardwareBuffer));
        transaction.setColorSpace(windowThumbnail.mSurfaceControl, ColorSpace.get(ColorSpace.Named.SRGB));
        transaction.setLayer(windowThumbnail.mSurfaceControl, Integer.MAX_VALUE);
        transaction.show(windowThumbnail.mSurfaceControl);
        transaction.apply();
        return windowThumbnail;
    }
}
