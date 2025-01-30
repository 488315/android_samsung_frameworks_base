package com.android.p038wm.shell.transition;

import android.graphics.ColorSpace;
import android.graphics.GraphicBuffer;
import android.hardware.HardwareBuffer;
import android.view.SurfaceControl;
import android.view.SurfaceSession;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WindowThumbnail {
    public SurfaceControl mSurfaceControl;

    private WindowThumbnail() {
    }

    public static WindowThumbnail createAndAttach(SurfaceSession surfaceSession, SurfaceControl surfaceControl, HardwareBuffer hardwareBuffer, SurfaceControl.Transaction transaction) {
        WindowThumbnail windowThumbnail = new WindowThumbnail();
        windowThumbnail.mSurfaceControl = new SurfaceControl.Builder(surfaceSession).setParent(surfaceControl).setName("WindowThumanil : " + surfaceControl.toString()).setCallsite("WindowThumanil").setFormat(-3).build();
        transaction.setBuffer(windowThumbnail.mSurfaceControl, GraphicBuffer.createFromHardwareBuffer(hardwareBuffer));
        transaction.setColorSpace(windowThumbnail.mSurfaceControl, ColorSpace.get(ColorSpace.Named.SRGB));
        transaction.setLayer(windowThumbnail.mSurfaceControl, Integer.MAX_VALUE);
        transaction.show(windowThumbnail.mSurfaceControl);
        transaction.apply();
        return windowThumbnail;
    }
}
