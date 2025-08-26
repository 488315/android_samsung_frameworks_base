package com.android.wm.shell.common.pip;

import android.graphics.Rect;

/* loaded from: classes3.dex */
public class PipSnapAlgorithm {
    public static void applySnapFraction(Rect rect, Rect rect2, float f) {
        if (f < 1.0f) {
            rect.offsetTo(rect2.left + ((int) (f * rect2.width())), rect2.top);
            return;
        }
        if (f < 2.0f) {
            rect.offsetTo(rect2.right, rect2.top + ((int) ((f - 1.0f) * rect2.height())));
        } else if (f < 3.0f) {
            rect.offsetTo(rect2.left + ((int) ((1.0f - (f - 2.0f)) * rect2.width())), rect2.bottom);
        } else {
            rect.offsetTo(rect2.left, rect2.top + ((int) ((1.0f - (f - 3.0f)) * rect2.height())));
        }
    }

    public final float getSnapFraction(int i, Rect rect, Rect rect2) {
        Rect rect3 = new Rect();
        snapRectToClosestEdge(rect, rect2, rect3, i);
        float fWidth = (rect3.left - rect2.left) / rect2.width();
        float fHeight = (rect3.top - rect2.top) / rect2.height();
        int i2 = rect3.top;
        return i2 == rect2.top ? fWidth : rect3.left == rect2.right ? fHeight + 1.0f : i2 == rect2.bottom ? (1.0f - fWidth) + 2.0f : (1.0f - fHeight) + 3.0f;
    }

    public void snapRectToClosestEdge(Rect rect, Rect rect2, Rect rect3, int i) {
        int i2 = rect.left;
        if (i == 1) {
            i2 = rect2.left;
        } else if (i == 2) {
            i2 = rect2.right;
        }
        int iMax = Math.max(rect2.left, Math.min(rect2.right, i2));
        int iMax2 = Math.max(rect2.top, Math.min(rect2.bottom, rect.top));
        rect3.set(rect);
        int iAbs = Math.abs(i2 - rect2.left);
        int iAbs2 = Math.abs(rect.top - rect2.top);
        int iAbs3 = Math.abs(rect2.right - i2);
        int iMin = Math.min(Math.min(iAbs, iAbs3), Math.min(iAbs2, Math.abs(rect2.bottom - rect.top)));
        if (iMin == iAbs) {
            rect3.offsetTo(rect2.left, iMax2);
            return;
        }
        if (iMin == iAbs2) {
            rect3.offsetTo(iMax, rect2.top);
        } else if (iMin == iAbs3) {
            rect3.offsetTo(rect2.right, iMax2);
        } else {
            rect3.offsetTo(iMax, rect2.bottom);
        }
    }

    public static void applySnapFraction(Rect rect, Rect rect2, float f, int i, int i2, Rect rect3, Rect rect4) {
        int iWidth;
        applySnapFraction(rect, rect2, f);
        if (i != 0) {
            if (i == 1) {
                iWidth = (i2 - rect.width()) + rect4.left;
            } else {
                iWidth = (rect3.right - i2) - rect4.right;
            }
            rect.offsetTo(iWidth, rect.top);
        }
    }
}
