package com.android.server.wallpaper;

import android.app.WallpaperManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;

import com.samsung.android.wallpaper.Rune;
import com.samsung.android.wallpaper.utils.WhichChecker;
import com.samsung.server.wallpaper.SemWallpaperManagerService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class WallpaperCropper {
    static final int ADD = 1;
    static final int BALANCE = 3;
    static final float MAX_PARALLAX = 1.0f;
    static final int REMOVE = 2;
    public final WallpaperDisplayHelper mWallpaperDisplayHelper;

    public interface WallpaperCropUtils {}

    public WallpaperCropper(
            WallpaperDisplayHelper wallpaperDisplayHelper,
            SemWallpaperManagerService semWallpaperManagerService) {
        this.mWallpaperDisplayHelper = wallpaperDisplayHelper;
    }

    public static Rect getAdjustedCrop(
            Rect rect, Point point, Point point2, boolean z, boolean z2, int i) {
        int sqrt;
        Rect rect2 = new Rect(rect);
        float width = rect.width() / rect.height();
        float f = point2.x / point2.y;
        if (width == f) {
            return rect;
        }
        if (width <= f) {
            if (i == 2) {
                sqrt = 0;
            } else if (i == 1) {
                sqrt = (int) ((rect.height() * f) - rect.width());
            } else {
                sqrt = (int) (Math.sqrt(rect.height() * rect.width() * f) + (-rect.width()));
            }
            if (point.x - rect.width() >= sqrt) {
                int i2 = sqrt / 2;
                int i3 = (sqrt % 2) + i2;
                int i4 = rect.left;
                if (i4 < i2) {
                    i3 += i2 - i4;
                    i2 = i4;
                } else {
                    int i5 = point.x;
                    int i6 = rect.right;
                    if (i5 - i6 < i3) {
                        i2 += i3 - (i5 - i6);
                        i3 = i5 - i6;
                    }
                }
                rect2.left -= i2;
                rect2.right += i3;
            } else {
                rect2.left = 0;
                rect2.right = point.x;
            }
            int height = (int) (rect.height() - (rect2.width() / f));
            int i7 = height / 2;
            rect2.top = (height % 2) + i7 + rect2.top;
            rect2.bottom -= i7;
        } else {
            if (!z) {
                int i8 = point.y - rect.bottom;
                int height2 = rect.height() + i8;
                int i9 = rect.left;
                Rect rect3 = new Rect(i8, i9, height2, rect.width() + i9);
                Point point3 = new Point(point.y, point.x);
                Rect adjustedCrop =
                        getAdjustedCrop(rect3, point3, new Point(point2.y, point2.x), false, z2, i);
                int i10 = adjustedCrop.top;
                int height3 = adjustedCrop.height() + i10;
                int i11 = point3.x - adjustedCrop.right;
                return new Rect(i10, i11, height3, adjustedCrop.width() + i11);
            }
            if ((width / f) - MAX_PARALLAX > MAX_PARALLAX) {
                int ceil = (int) Math.ceil((r1 - MAX_PARALLAX) * f * rect.height());
                if (z2) {
                    rect2.left += ceil;
                } else {
                    rect2.right -= ceil;
                }
            }
        }
        return rect2;
    }

    public static List getOriginalCropHints(WallpaperData wallpaperData, List list) {
        ArrayList arrayList = new ArrayList();
        Iterator it = ((ArrayList) list).iterator();
        while (it.hasNext()) {
            Rect rect = new Rect((Rect) it.next());
            rect.scale(wallpaperData.mSampleSize);
            Rect rect2 = wallpaperData.cropHint;
            rect.offset(rect2.left, rect2.top);
            arrayList.add(rect);
        }
        return arrayList;
    }

    public static SparseArray getRelativeCropHints(WallpaperData wallpaperData) {
        SparseArray sparseArray = new SparseArray();
        for (int i = 0; i < wallpaperData.mCropHints.size(); i++) {
            Rect rect = new Rect((Rect) wallpaperData.mCropHints.valueAt(i));
            Rect rect2 = wallpaperData.cropHint;
            rect.offset(-rect2.left, -rect2.top);
            rect.scale(MAX_PARALLAX / wallpaperData.mSampleSize);
            sparseArray.put(wallpaperData.mCropHints.keyAt(i), rect);
        }
        return sparseArray;
    }

    public static Rect getTotalCrop(SparseArray sparseArray) {
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        int i3 = Integer.MAX_VALUE;
        int i4 = Integer.MIN_VALUE;
        for (int i5 = 0; i5 < sparseArray.size(); i5++) {
            Rect rect = (Rect) sparseArray.valueAt(i5);
            i = Math.min(i, rect.left);
            i3 = Math.min(i3, rect.top);
            i4 = Math.max(i4, rect.right);
            i2 = Math.max(i2, rect.bottom);
        }
        return new Rect(i, i3, i4, i2);
    }

    public static Rect noParallax(Rect rect, Point point, Point point2, boolean z) {
        if (point == null) {
            return rect;
        }
        Rect adjustedCrop = getAdjustedCrop(rect, point2, point, true, z, 1);
        int width =
                (int)
                        ((adjustedCrop.width()
                                        - (adjustedCrop.height()
                                                * ((point.x * MAX_PARALLAX) / point.y)))
                                + 0.5f);
        if (z) {
            adjustedCrop.left += width;
        } else {
            adjustedCrop.right -= width;
        }
        return adjustedCrop;
    }

    /* JADX WARN: Code restructure failed: missing block: B:163:0x05b8, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x05b9, code lost:

       r18 = r4;
       r13 = r5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x05b3, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x05b4, code lost:

       r18 = r4;
       r13 = r5;
    */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x05dc, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x05dd, code lost:

       r18 = r4;
       r13 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x05d7, code lost:

       r0 = th;
    */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x05d8, code lost:

       r18 = r4;
       r13 = null;
    */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x038c, code lost:

       r0 = e;
    */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x038d, code lost:

       r17 = r2;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void generateCrop(com.android.server.wallpaper.WallpaperData r21) {
        /*
            Method dump skipped, instructions count: 1584
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wallpaper.WallpaperCropper.generateCrop(com.android.server.wallpaper.WallpaperData):void");
    }

    public final Rect getCrop(Point point, Point point2, SparseArray sparseArray, boolean z) {
        Rect rect;
        int orientation = WallpaperManager.getOrientation(point);
        int i = 0;
        WallpaperDisplayHelper wallpaperDisplayHelper = this.mWallpaperDisplayHelper;
        if (sparseArray == null || sparseArray.size() == 0) {
            Rect rect2 = new Rect(0, 0, point2.x, point2.y);
            if (Rune.SUPPORT_SUB_DISPLAY_MODE
                    && !Rune.SUPPORT_COVER_DISPLAY_WATCHFACE
                    && (orientation == 0 || orientation == 1)) {
                i = 1;
            }
            int unfoldedOrientation = wallpaperDisplayHelper.getUnfoldedOrientation(orientation);
            if (i == 0 && unfoldedOrientation != -1) {
                SparseArray sparseArray2 = new SparseArray();
                sparseArray2.put(unfoldedOrientation, rect2);
                return getCrop(point, point2, sparseArray2, z);
            }
            if (i != 0) {
                SparseArray sparseArray3 = new SparseArray();
                sparseArray3.put(orientation, rect2);
                Slog.w(
                        "WallpaperCropper",
                        "getCrop: foldedOrientation, newSuggestedCrops = "
                                + sparseArray3
                                + " , orientation = "
                                + orientation);
                return getCrop(point, point2, sparseArray3, z);
            }
            if (!wallpaperDisplayHelper.mIsLargeScreen
                    || wallpaperDisplayHelper.mIsFoldable
                    || point.x >= point.y) {
                rect = rect2;
            } else {
                Point point3 = new Point(point.y, point.x);
                Rect noParallax =
                        noParallax(getCrop(point3, point2, sparseArray, z), point3, point2, z);
                rect = getAdjustedCrop(noParallax, point2, point, false, z, 1);
                if (z) {
                    rect.left = noParallax.left;
                } else {
                    rect.right = noParallax.right;
                }
            }
            return getAdjustedCrop(rect, point2, point, true, z, 1);
        }
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
            Rect rect3 = (Rect) sparseArray.valueAt(i2);
            if (rect3 == null
                    || rect3.left < 0
                    || rect3.top < 0
                    || rect3.right > point2.x
                    || rect3.bottom > point2.y) {
                Slog.w(
                        "WallpaperCropper",
                        "invalid crop: " + rect3 + " for bitmap size: " + point2);
                return getCrop(point, point2, new SparseArray(), z);
            }
        }
        Rect rect4 = (Rect) sparseArray.get(orientation);
        if (rect4 != null) {
            return getAdjustedCrop(rect4, point2, point, true, z, 1);
        }
        SparseArray sparseArray4 = wallpaperDisplayHelper.mDefaultDisplaySizes;
        int rotatedOrientation = WallpaperManager.getRotatedOrientation(orientation);
        Rect rect5 = (Rect) sparseArray.get(rotatedOrientation);
        Point point4 = (Point) sparseArray4.get(rotatedOrientation);
        if (rect5 != null) {
            return getAdjustedCrop(
                    noParallax(rect5, point4, point2, z), point2, point, false, z, 3);
        }
        int unfoldedOrientation2 = wallpaperDisplayHelper.getUnfoldedOrientation(orientation);
        Rect rect6 = (Rect) sparseArray.get(unfoldedOrientation2);
        Point point5 = (Point) sparseArray4.get(unfoldedOrientation2);
        if (rect6 != null) {
            Rect noParallax2 = noParallax(rect6, point5, point2, z);
            Rect adjustedCrop = getAdjustedCrop(noParallax2, point2, point, false, z, 2);
            if (adjustedCrop.width() >= noParallax2.width()) {
                return adjustedCrop;
            }
            if (z) {
                adjustedCrop.left = Math.min(adjustedCrop.left, noParallax2.left);
            } else {
                adjustedCrop.right = Math.max(adjustedCrop.right, noParallax2.right);
            }
            return getAdjustedCrop(adjustedCrop, point2, point, true, z, 1);
        }
        int foldedOrientation = wallpaperDisplayHelper.getFoldedOrientation(orientation);
        Rect rect7 = (Rect) sparseArray.get(foldedOrientation);
        Point point6 = (Point) sparseArray4.get(foldedOrientation);
        if (rect7 != null) {
            Rect noParallax3 = noParallax(rect7, point6, point2, z);
            Slog.i(
                    "WallpaperCropper",
                    "getCrop: suggestedCrop = " + rect7 + " , orientation = " + orientation);
            return getAdjustedCrop(noParallax3, point2, point, false, z, 1);
        }
        Point point7 = (Point) sparseArray4.get(rotatedOrientation);
        if (point7 != null) {
            int[] iArr = {
                wallpaperDisplayHelper.getFoldedOrientation(rotatedOrientation),
                wallpaperDisplayHelper.getUnfoldedOrientation(rotatedOrientation)
            };
            while (i < 2) {
                if (((Rect) sparseArray.get(iArr[i])) != null) {
                    Rect crop = getCrop(point7, point2, sparseArray, z);
                    SparseArray sparseArray5 = new SparseArray();
                    sparseArray5.put(rotatedOrientation, crop);
                    return getCrop(point, point2, sparseArray5, z);
                }
                i++;
            }
        }
        Slog.w(
                "WallpaperCropper",
                "Could not find a proper default crop for display: "
                        + point
                        + ", bitmap size: "
                        + point2
                        + ", suggested crops: "
                        + sparseArray
                        + ", orientation: "
                        + orientation
                        + ", rtl: "
                        + z
                        + ", defaultDisplaySizes: "
                        + sparseArray4);
        return getCrop(point, point2, new SparseArray(), z);
    }

    public final SparseArray getDefaultCrops(int i, SparseArray sparseArray, Point point) {
        SparseArray sparseArray2;
        Rect rect = (Rect) sparseArray.get(-1);
        int i2 = 0;
        if (rect != null) {
            Rect rect2 = new Rect(0, 0, point.x, point.y);
            if (sparseArray.size() == 1 && rect2.contains(rect)) {
                SparseArray defaultCrops =
                        getDefaultCrops(
                                i, new SparseArray(), new Point(rect.width(), rect.height()));
                while (i2 < defaultCrops.size()) {
                    ((Rect) defaultCrops.valueAt(i2)).offset(rect.left, rect.top);
                    i2++;
                }
                return defaultCrops;
            }
            Slog.w(
                    "WallpaperCropper",
                    "Couldn't get default crops from suggested crops "
                            + sparseArray
                            + " for bitmap of size "
                            + point
                            + "; ignoring suggested crops");
            return getDefaultCrops(i, new SparseArray(), point);
        }
        boolean z = Rune.SUPPORT_SUB_DISPLAY_MODE;
        WallpaperDisplayHelper wallpaperDisplayHelper = this.mWallpaperDisplayHelper;
        if (!z || Rune.SUPPORT_COVER_DISPLAY_WATCHFACE) {
            sparseArray2 = wallpaperDisplayHelper.mDefaultDisplaySizes;
        } else if (wallpaperDisplayHelper.mDefaultDisplaySizes.size() < 4) {
            sparseArray2 = wallpaperDisplayHelper.mDefaultDisplaySizes;
        } else {
            SparseArray sparseArray3 = new SparseArray();
            if (WhichChecker.isSubDisplay(i)) {
                sparseArray3.put(0, (Point) wallpaperDisplayHelper.mDefaultDisplaySizes.get(0));
                sparseArray3.put(1, (Point) wallpaperDisplayHelper.mDefaultDisplaySizes.get(1));
            } else {
                sparseArray3.put(2, (Point) wallpaperDisplayHelper.mDefaultDisplaySizes.get(2));
                sparseArray3.put(3, (Point) wallpaperDisplayHelper.mDefaultDisplaySizes.get(3));
            }
            Slog.w(
                    "WallpaperDisplayHelper",
                    "getDefaultDisplaySizes which = " + i + " , sizes = " + sparseArray3);
            sparseArray2 = sparseArray3;
        }
        boolean z2 = TextUtils.getLayoutDirectionFromLocale(Locale.getDefault()) == 1;
        SparseArray sparseArray4 = new SparseArray();
        for (int i3 = 0; i3 < sparseArray2.size(); i3++) {
            int keyAt = sparseArray2.keyAt(i3);
            Point point2 = (Point) sparseArray2.valueAt(i3);
            if (((Rect) sparseArray.get(keyAt)) != null) {
                sparseArray4.put(keyAt, getCrop(point2, point, sparseArray, z2));
            }
        }
        SparseArray clone = sparseArray4.clone();
        while (i2 < sparseArray2.size()) {
            int keyAt2 = sparseArray2.keyAt(i2);
            if (!clone.contains(keyAt2)) {
                clone.put(
                        keyAt2, getCrop((Point) sparseArray2.valueAt(i2), point, sparseArray4, z2));
            }
            i2++;
        }
        return clone;
    }
}
