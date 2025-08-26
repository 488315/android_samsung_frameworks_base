package com.android.internal.policy;

import android.graphics.Point;
import android.graphics.Rect;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes5.dex */
public class TaskResizingAlgorithm {
    public static final int CTRL_BOTTOM = 8;
    public static final int CTRL_LEFT = 1;
    public static final int CTRL_NONE = 0;
    public static final int CTRL_RIGHT = 2;
    public static final int CTRL_TOP = 4;
    public static final float MIN_ASPECT = 1.2f;

    @Retention(RetentionPolicy.SOURCE)
    public @interface CtrlType {
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x013a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0144  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0147  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Rect resizeDrag(float f, float f2, float f3, float f4, Rect rect, int i, int i2, int i3, Point point, boolean z, boolean z2) {
        int iMax;
        int iMax2;
        int i4;
        int i5;
        float f5;
        int iMin;
        int iMax3;
        int iMax4;
        int iMin2;
        int iRound = Math.round(f - f3);
        int iRound2 = Math.round(f2 - f4);
        int i6 = rect.left;
        int i7 = rect.top;
        int i8 = rect.right;
        int i9 = rect.bottom;
        int i10 = i8 - i6;
        int i11 = i9 - i7;
        int i12 = i & 1;
        if (i12 != 0) {
            iMax = Math.max(i2, Math.min(i10 - iRound, point.x));
        } else {
            iMax = (i & 2) != 0 ? Math.max(i2, Math.min(iRound + i10, point.x)) : i10;
        }
        int i13 = i & 4;
        if (i13 != 0) {
            iMax2 = Math.max(i3, Math.min(i11 - iRound2, point.y));
        } else {
            iMax2 = (i & 8) != 0 ? Math.max(i3, Math.min(iRound2 + i11, point.y)) : i11;
        }
        float f6 = iMax / iMax2;
        if (z) {
            if (!z2 || f6 >= 1.2f) {
                if (!z2) {
                    f5 = 1.2f;
                    if (f6 > 0.8333333002196431d) {
                    }
                }
                i4 = i9;
            } else {
                f5 = 1.2f;
            }
            if (z2) {
                iMax3 = Math.max(i2, Math.min(point.x, iMax));
                iMin = Math.min(iMax2, Math.round(iMax3 / f5));
                if (iMin < i3) {
                    iMax3 = Math.max(i2, Math.min(point.x, Math.round(i3 * f5)));
                    iMin = i3;
                }
                iMax4 = Math.max(i3, Math.min(point.y, iMax2));
                i4 = i9;
                iMin2 = Math.max(iMax, Math.round(iMax4 * f5));
                if (iMin2 < i2) {
                    iMax4 = Math.max(i3, Math.min(point.y, Math.round(i2 / f5)));
                    iMin2 = i2;
                }
                if ((iMax <= i10 || iMax2 > i11) != (iMax3 * iMin > iMin2 * iMax4)) {
                    iMax = iMax3;
                    iMax2 = iMin;
                } else {
                    iMax = iMin2;
                    iMax2 = iMax4;
                }
            } else {
                i4 = i9;
                int iMax5 = Math.max(i2, Math.min(point.x, iMax));
                int iMax6 = Math.max(iMax2, Math.round(iMax5 * f5));
                if (iMax6 < i3) {
                    iMax5 = Math.max(i2, Math.min(point.x, Math.round(i3 / f5)));
                    iMin = i3;
                } else {
                    iMin = iMax6;
                }
                iMax3 = iMax5;
                iMax4 = Math.max(i3, Math.min(point.y, iMax2));
                iMin2 = Math.min(iMax, Math.round(iMax4 / f5));
                if (iMin2 < i2) {
                    iMax4 = Math.max(i3, Math.min(point.y, Math.round(i2 * f5)));
                    iMin2 = i2;
                }
                if (iMax <= i10) {
                    if ((iMax <= i10 || iMax2 > i11) != (iMax3 * iMin > iMin2 * iMax4)) {
                    }
                }
            }
        } else {
            i4 = i9;
        }
        if (i12 != 0) {
            i6 = i8 - iMax;
        } else {
            i8 = i6 + iMax;
        }
        if (i13 != 0) {
            i7 = i4 - iMax2;
            i5 = i4;
        } else {
            i5 = i7 + iMax2;
        }
        return new Rect(i6, i7, i8, i5);
    }
}
