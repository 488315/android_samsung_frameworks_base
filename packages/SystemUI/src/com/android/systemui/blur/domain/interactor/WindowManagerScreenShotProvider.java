package com.android.systemui.blur.domain.interactor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import com.android.systemui.blur.di.ScreenShotBitmapProvider;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.samsung.android.view.SemWindowManager;
import kotlin.Pair;

/* loaded from: classes.dex */
public final class WindowManagerScreenShotProvider implements ScreenShotBitmapProvider {
    public final Context context;
    public final DisplayLifecycle displayLifecycle;
    public final Rect rect = new Rect(0, 0, 0, 0);
    public final Point sizePoint = new Point();
    public final int targetWindowType = 2000;

    public WindowManagerScreenShotProvider(Context context, DisplayLifecycle displayLifecycle) {
        this.context = context;
        this.displayLifecycle = displayLifecycle;
    }

    @Override // com.android.systemui.blur.di.ScreenShotBitmapProvider
    public final Bitmap getScreenShot() {
        Pair pair;
        this.context.getDisplay().getRealSize(this.sizePoint);
        Point point = this.sizePoint;
        int i = point.x;
        ScreenShotBitmapProvider.Companion.getClass();
        int i2 = ScreenShotBitmapProvider.Companion.RESIZE_SCALE;
        point.x = i / i2;
        this.sizePoint.y /= i2;
        int displayId = this.displayLifecycle.getDisplay(0).getDisplayId();
        if (this.context.getResources().getConfiguration().orientation == 1) {
            Point point2 = this.sizePoint;
            Double dValueOf = Double.valueOf(Math.min(point2.x, point2.y));
            Point point3 = this.sizePoint;
            pair = new Pair(dValueOf, Double.valueOf(Math.max(point3.x, point3.y)));
        } else {
            Point point4 = this.sizePoint;
            Double dValueOf2 = Double.valueOf(Math.max(point4.x, point4.y));
            Point point5 = this.sizePoint;
            pair = new Pair(dValueOf2, Double.valueOf(Math.min(point5.x, point5.y)));
        }
        return SemWindowManager.getInstance().screenshot(displayId, this.targetWindowType, false, this.rect, (int) ((Number) pair.component1()).doubleValue(), (int) ((Number) pair.component2()).doubleValue(), false, 0, true);
    }
}
