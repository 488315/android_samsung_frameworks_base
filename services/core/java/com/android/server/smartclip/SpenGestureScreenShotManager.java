package com.android.server.smartclip;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.android.server.LocalServices;
import com.android.server.wm.WindowManagerInternal;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public final class SpenGestureScreenShotManager {

    public final class FutureScreenShot extends FutureTask implements ScreenShot {
        public FutureScreenShot(Callable callable) {
            super(callable);
        }
    }

    public final class Host {}

    public final class RealScreenShot implements ScreenShot {
        public final Bitmap bitmap;

        public RealScreenShot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
            this.bitmap =
                    ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class))
                            .takeScreenshotToTargetWindowInternal(i, i2, z, rect, i3, i4, z2);
        }
    }

    public interface ScreenShot {}
}
