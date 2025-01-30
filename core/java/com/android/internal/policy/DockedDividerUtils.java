package com.android.internal.policy;

import android.content.res.Resources;
import android.graphics.Rect;
import com.android.internal.C4337R;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes5.dex */
public class DockedDividerUtils {
    public static final float INVALID_DIVIDER_RATIO = 0.0f;

    public static void calculateBoundsForPosition(int position, int dockSide, Rect outRect, int displayWidth, int displayHeight, int dividerSize) {
        calculateBoundsForPosition(position, dockSide, outRect, displayWidth, displayHeight, dividerSize, null);
    }

    public static void calculateBoundsForPosition(int position, int dockSide, Rect outRect, int displayWidth, int displayHeight, int dividerSize, Rect stableInsets) {
        boolean z = false;
        outRect.set(0, 0, displayWidth, displayHeight);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY && stableInsets != null) {
            outRect.inset(stableInsets);
        }
        switch (dockSide) {
            case 1:
                outRect.right = position;
                break;
            case 2:
                outRect.bottom = position;
                break;
            case 3:
                outRect.left = position + dividerSize;
                break;
            case 4:
                outRect.top = position + dividerSize;
                break;
        }
        if (dockSide == 1 || dockSide == 2) {
            z = true;
        }
        sanitizeStackBounds(outRect, z);
    }

    public static void calculateBoundsForCellWithPosition(int position, int dockSide, Rect outRect, Rect splitArea, int dividerSize) {
        outRect.set(splitArea);
        switch (dockSide) {
            case 1:
                outRect.right = position;
                break;
            case 2:
                outRect.bottom = position;
                break;
            case 3:
                outRect.left = position + dividerSize;
                break;
            case 4:
                outRect.top = position + dividerSize;
                break;
        }
        boolean z = true;
        if (dockSide != 1 && dockSide != 2) {
            z = false;
        }
        sanitizeStackBounds(outRect, z);
        outRect.intersect(splitArea);
    }

    public static void sanitizeStackBounds(Rect bounds, boolean topLeft) {
        if (topLeft) {
            if (bounds.left >= bounds.right) {
                bounds.left = bounds.right - 1;
            }
            if (bounds.top >= bounds.bottom) {
                bounds.top = bounds.bottom - 1;
                return;
            }
            return;
        }
        if (bounds.right <= bounds.left) {
            bounds.right = bounds.left + 1;
        }
        if (bounds.bottom <= bounds.top) {
            bounds.bottom = bounds.top + 1;
        }
    }

    public static int calculatePositionForBounds(Rect bounds, int dockSide, int dividerSize) {
        switch (dockSide) {
            case 1:
                return bounds.right;
            case 2:
                return bounds.bottom;
            case 3:
                return bounds.left - dividerSize;
            case 4:
                return bounds.top - dividerSize;
            default:
                return 0;
        }
    }

    public static int calculateMiddlePosition(boolean isHorizontalDivision, Rect insets, int displayWidth, int displayHeight, int dividerSize) {
        int end;
        int start = isHorizontalDivision ? insets.top : insets.left;
        if (isHorizontalDivision) {
            end = displayHeight - insets.bottom;
        } else {
            end = displayWidth - insets.right;
        }
        return (((end - start) / 2) + start) - (dividerSize / 2);
    }

    public static int invertDockSide(int dockSide) {
        switch (dockSide) {
            case 1:
                return 3;
            case 2:
                return 4;
            case 3:
                return 1;
            case 4:
                return 2;
            default:
                return -1;
        }
    }

    public static int getDividerInsets(Resources res) {
        return res.getDimensionPixelSize(C4337R.dimen.docked_stack_divider_insets);
    }

    public static int getDividerSize(Resources res, int dividerInsets) {
        int windowWidth = res.getDimensionPixelSize(C4337R.dimen.docked_stack_divider_thickness);
        return windowWidth - (dividerInsets * 2);
    }

    public static int getDockSide(int displayWidth, int displayHeight) {
        return displayWidth > displayHeight ? 1 : 2;
    }

    public static int getDockSide(Rect main, Rect display) {
        if (main.bottom == display.bottom && main.left == display.left && main.right < display.right) {
            return 1;
        }
        if (main.top == display.top && main.left == display.left && main.bottom < display.bottom) {
            return 2;
        }
        if (main.top == display.top && main.right == display.right && display.left < main.left) {
            return 3;
        }
        if (main.bottom == display.bottom && main.right == display.right && display.top < main.top) {
            return 4;
        }
        return -1;
    }

    public static boolean isHorizontalDivision(int dockSide) {
        return dockSide == 2 || dockSide == 4;
    }

    public static float calculateSplitRatio(int dividerSize, Rect main, Rect splitArea, Rect applyInsets) {
        int dockSide = getDockSide(main, splitArea);
        Rect stableBounds = new Rect(splitArea);
        if (applyInsets != null) {
            stableBounds.inset(applyInsets);
        }
        int positionInDisplay = calculatePositionForBounds(main, dockSide, dividerSize) - (isHorizontalDivision(dockSide) ? stableBounds.top : stableBounds.left);
        return positionInDisplay / ((isHorizontalDivision(dockSide) ? stableBounds.height() : splitArea.width()) - dividerSize);
    }
}
