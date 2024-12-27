package com.android.server.smartclip;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.android.server.LocalServices;
import com.android.server.wm.WindowManagerInternal;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SpenGestureScreenShotManager {

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FutureScreenShot extends FutureTask implements ScreenShot {
        public FutureScreenShot(Callable callable) {
            super(callable);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Host {
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RealScreenShot implements ScreenShot {
        public final Bitmap bitmap;

        public RealScreenShot(int i, int i2, boolean z, Rect rect, int i3, int i4, boolean z2) {
            this.bitmap = ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class)).takeScreenshotToTargetWindowInternal(i, i2, z, rect, i3, i4, z2);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface ScreenShot {
    }
}
