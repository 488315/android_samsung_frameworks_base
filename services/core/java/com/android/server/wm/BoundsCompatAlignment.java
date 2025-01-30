package com.android.server.wm;

import android.graphics.Rect;
import android.view.DisplayInfo;
import android.view.Gravity;
import com.samsung.android.rune.CoreRune;

/* compiled from: BoundsCompatController.java */
/* loaded from: classes3.dex */
public class BoundsCompatAlignment {
    public int mAlignment = 17;
    public DisplayContent mDisplayContent;

    public static int getCenterOffset(int i, int i2) {
        return (int) (((i - i2) + 1) * 0.5f);
    }

    public void setDisplayContent(DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
    }

    public void setAlignment(int i) {
        this.mAlignment = i;
    }

    public int getAlignment() {
        return this.mAlignment;
    }

    public boolean isTopVertical() {
        return (this.mAlignment & 112) == 48;
    }

    public boolean isCenterVertical() {
        return (this.mAlignment & 112) == 16;
    }

    public int getHorizontalOffset(Rect rect, Rect rect2) {
        return getHorizontalOffset(rect, rect2, this.mAlignment);
    }

    public int getHorizontalOffset(Rect rect, Rect rect2, int i) {
        int i2 = i & 7;
        if (i2 == 3) {
            return 0;
        }
        if (i2 == 5) {
            return rect.right - rect2.width();
        }
        return getCenterOffset(rect.width(), rect2.width());
    }

    public int getVerticalOffset(Rect rect, Rect rect2) {
        return getVerticalOffset(rect, rect2, this.mAlignment);
    }

    public final int getVerticalOffset(Rect rect, Rect rect2, int i) {
        int i2 = i & 112;
        if (i2 != 48) {
            if (i2 == 80) {
                return rect.bottom - rect2.height();
            }
            return getCenterOffset(rect.height(), rect2.height());
        }
        DisplayContent displayContent = this.mDisplayContent;
        if (displayContent == null) {
            return rect.top;
        }
        DisplayInfo displayInfo = displayContent.getDisplayInfo();
        int i3 = this.mDisplayContent.getDisplayPolicy().getDecorInsetsInfo(displayInfo.rotation, displayInfo.logicalWidth, displayInfo.logicalHeight).mConfigInsets.top;
        return rect2.height() + i3 <= rect.bottom ? i3 : rect.top;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        int alignment = getAlignment();
        sb.append("BoundsCompatAlignment:");
        sb.append(" Alignment=0x");
        sb.append(Integer.toHexString(alignment));
        if (CoreRune.SAFE_DEBUG) {
            sb.append("(");
            sb.append(Gravity.toString(alignment));
            sb.append(")");
        }
        return sb.toString();
    }
}
