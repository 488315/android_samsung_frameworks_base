package com.android.server.wallpaper;

import android.app.WallpaperManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Binder;
import android.os.Debug;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.view.Display;
import android.view.WindowManager;
import android.view.WindowMetrics;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.WindowManagerInternal;
import com.android.window.flags.Flags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WallpaperDisplayHelper {
    public final DisplayManager mDisplayManager;
    public final boolean mIsFoldable;
    public final WindowManagerInternal mWindowManagerInternal;
    public final SparseArray mDisplayDatas = new SparseArray();
    public final SparseArray mDefaultDisplaySizes = new SparseArray();
    public final List mFoldableOrientationPairs = new ArrayList();
    public final boolean mIsLargeScreen = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DisplayData {
        public final int mDisplayId;
        public int mWidth = -1;
        public int mHeight = -1;
        public final Rect mPadding = new Rect(0, 0, 0, 0);

        public DisplayData(int i) {
            this.mDisplayId = i;
        }
    }

    public WallpaperDisplayHelper(
            DisplayManager displayManager,
            WindowManager windowManager,
            WindowManagerInternal windowManagerInternal,
            boolean z) {
        this.mDisplayManager = displayManager;
        this.mWindowManagerInternal = windowManagerInternal;
        this.mIsFoldable = z;
        if (Flags.multiCrop()) {
            Set<WindowMetrics> possibleMaximumWindowMetrics =
                    windowManager.getPossibleMaximumWindowMetrics(0);
            boolean z2 = z && possibleMaximumWindowMetrics.size() == 2;
            int i = -1;
            float f = 0.0f;
            for (WindowMetrics windowMetrics : possibleMaximumWindowMetrics) {
                Rect bounds = windowMetrics.getBounds();
                Point point = new Point(bounds.width(), bounds.height());
                for (Point point2 : List.of(point, new Point(point.y, point.x))) {
                    int orientation = WallpaperManager.getOrientation(point2);
                    Point point3 = (Point) this.mDefaultDisplaySizes.get(orientation);
                    if (point3 == null || point3.x * point3.y < point2.x * point2.y) {
                        this.mDefaultDisplaySizes.put(orientation, point2);
                    }
                }
                this.mIsLargeScreen |= ((float) point.x) / windowMetrics.getDensity() >= 600.0f;
                if (z2) {
                    int orientation2 = WallpaperManager.getOrientation(point);
                    float density =
                            (point.x * point.y)
                                    / (windowMetrics.getDensity() * windowMetrics.getDensity());
                    if (f <= FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        i = orientation2;
                        f = density;
                    } else {
                        Pair pair =
                                density > f
                                        ? new Pair(
                                                Integer.valueOf(i), Integer.valueOf(orientation2))
                                        : new Pair(
                                                Integer.valueOf(orientation2), Integer.valueOf(i));
                        Pair pair2 =
                                new Pair(
                                        Integer.valueOf(
                                                WallpaperManager.getRotatedOrientation(
                                                        ((Integer) pair.first).intValue())),
                                        Integer.valueOf(
                                                WallpaperManager.getRotatedOrientation(
                                                        ((Integer) pair.second).intValue())));
                        ((ArrayList) this.mFoldableOrientationPairs).add(pair);
                        ((ArrayList) this.mFoldableOrientationPairs).add(pair2);
                    }
                }
            }
        }
    }

    public final void ensureSaneWallpaperDisplaySize(DisplayData displayData, int i) {
        int maximumSizeDimension = getMaximumSizeDimension(i);
        if (displayData.mWidth < maximumSizeDimension) {
            displayData.mWidth = maximumSizeDimension;
        }
        if (displayData.mHeight < maximumSizeDimension) {
            displayData.mHeight = maximumSizeDimension;
        }
    }

    public final DisplayData getDisplayDataOrCreate(int i) {
        DisplayData displayData = (DisplayData) this.mDisplayDatas.get(i);
        if (displayData != null) {
            return displayData;
        }
        DisplayData displayData2 = new DisplayData(i);
        ensureSaneWallpaperDisplaySize(displayData2, i);
        this.mDisplayDatas.append(i, displayData2);
        return displayData2;
    }

    public final int getFoldedOrientation(int i) {
        Iterator it = ((ArrayList) this.mFoldableOrientationPairs).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((Integer) pair.second).equals(Integer.valueOf(i))) {
                return ((Integer) pair.first).intValue();
            }
        }
        return -1;
    }

    public final int getMaximumSizeDimension(int i) {
        Display display = this.mDisplayManager.getDisplay(i);
        if (display == null) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(i, "Invalid displayId=", " ");
            m.append(Debug.getCallers(4));
            Slog.w("WallpaperDisplayHelper", m.toString());
            display = this.mDisplayManager.getDisplay(0);
        }
        return display.getMaximumSizeDimension();
    }

    public final int getUnfoldedOrientation(int i) {
        Iterator it = ((ArrayList) this.mFoldableOrientationPairs).iterator();
        while (it.hasNext()) {
            Pair pair = (Pair) it.next();
            if (((Integer) pair.first).equals(Integer.valueOf(i))) {
                return ((Integer) pair.second).intValue();
            }
        }
        return -1;
    }

    public final boolean isUsableDisplay(Display display, int i) {
        if (display == null || !display.hasAccess(i)) {
            return false;
        }
        int displayId = display.getDisplayId();
        if (displayId == 0) {
            return true;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mWindowManagerInternal.isHomeSupportedOnDisplay(displayId);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
