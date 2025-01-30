package com.android.server.wm;

import android.app.ActivityOptions;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.TypedValue;
import com.samsung.android.rune.CoreRune;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public abstract class PopOverBoundsCalculator {
    public static Rect getBounds(final ActivityRecord activityRecord) {
        int rotation;
        ActivityOptions options = activityRecord.mPopOverState.getOptions();
        if (options == null) {
            return activityRecord.getBounds();
        }
        final DisplayContent displayContent = activityRecord.mDisplayContent;
        ActivityRecord activity = activityRecord.getTask().getActivity(new Predicate() { // from class: com.android.server.wm.PopOverBoundsCalculator$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$getBounds$0;
                lambda$getBounds$0 = PopOverBoundsCalculator.lambda$getBounds$0(ActivityRecord.this, displayContent, (ActivityRecord) obj);
                return lambda$getBounds$0;
            }
        });
        if (activity != null && activity.getFixedRotationTransformDisplayInfo() != null) {
            rotation = activity.getFixedRotationTransformDisplayInfo().rotation;
        } else {
            rotation = displayContent.getRotation();
        }
        boolean isAnyPortrait = displayContent.getDisplayRotation().isAnyPortrait(rotation);
        float f = displayContent.getDisplayMetrics().density;
        Rect availableArea = getAvailableArea(activityRecord, displayContent, rotation, f, activity);
        int requestedWidth = getRequestedWidth(options, isAnyPortrait ? 1 : 0, f);
        int requestedHeight = getRequestedHeight(options, isAnyPortrait ? 1 : 0, f);
        int i = options.mPopOverAnchorPosition[isAnyPortrait ? 1 : 0];
        Point point = options.mPopOverAnchorMarginDp[isAnyPortrait ? 1 : 0];
        int i2 = (int) (point.x * f);
        int i3 = (int) (point.y * f);
        Rect rect = new Rect();
        applyWidth(i, requestedWidth, availableArea, i2, rect);
        applyHeight(i, requestedHeight, availableArea, i3, rect);
        rect.intersect(availableArea);
        return rect;
    }

    public static /* synthetic */ boolean lambda$getBounds$0(ActivityRecord activityRecord, DisplayContent displayContent, ActivityRecord activityRecord2) {
        return activityRecord2 != activityRecord && displayContent.isFixedRotationLaunchingApp(activityRecord2) && activityRecord2.inFullscreenWindowingMode() && !activityRecord2.mPopOverState.isActivated();
    }

    public static Rect getAvailableArea(ActivityRecord activityRecord, DisplayContent displayContent, int i, float f, ActivityRecord activityRecord2) {
        Rect rect;
        if (activityRecord2 != null) {
            rect = activityRecord2.getBounds();
        } else {
            rect = new Rect(activityRecord.getTask().getBounds());
        }
        applyInsets(displayContent, activityRecord, i, rect);
        applyDefaultMargin(f, rect);
        return rect;
    }

    public static void applyInsets(DisplayContent displayContent, ActivityRecord activityRecord, int i, Rect rect) {
        Rect rect2 = new Rect();
        boolean z = true;
        boolean z2 = i == 1 || i == 3;
        Rect rect3 = new Rect(displayContent.getDisplayPolicy().getDecorInsetsInfo(i, z2 ? displayContent.mBaseDisplayHeight : displayContent.mBaseDisplayWidth, z2 ? displayContent.mBaseDisplayWidth : displayContent.mBaseDisplayHeight).mConfigInsets);
        rect2.set(0, rect3.top, 0, rect3.bottom);
        Task task = activityRecord.getTask();
        if (task.isDesktopModeEnabled()) {
            if (task.isNewDexMode() && task.inFullscreenWindowingMode()) {
                rect.top += rect2.top;
            }
            if (!task.isActivityTypeStandard() && !task.inFreeformWindowingMode()) {
                z = false;
            }
            if (z) {
                if (task.inFullscreenWindowingMode()) {
                    rect.top += getPopOverShadow(displayContent.getDisplayPolicy().getContext().getResources());
                }
                rect.top += task.getCaptionHeight();
            }
            rect.bottom -= task.inFullscreenWindowingMode() ? rect2.bottom : 0;
            return;
        }
        if (task.inFreeformWindowingMode()) {
            rect.top += task.getCaptionHeight();
            return;
        }
        if (task.inMultiWindowMode()) {
            return;
        }
        Rect safeInsets = displayContent.calculateDisplayCutoutForRotation(i, activityRecord.isConfigurationNeededInUdcCutout()).getSafeInsets();
        if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT && displayContent.mIsOverlappingWithCutoutAsDefault) {
            safeInsets.setEmpty();
        }
        rect.top += Math.max(rect2.top, safeInsets.top);
        rect.bottom -= rect2.bottom + safeInsets.bottom;
        rect.left += Math.max(rect2.left, safeInsets.left);
        rect.right -= Math.max(rect2.right, safeInsets.right);
    }

    public static void applyDefaultMargin(float f, Rect rect) {
        int i = (int) (12.0f * f);
        rect.top += i;
        rect.bottom -= (int) (f * 16.0f);
        rect.left += i;
        rect.right -= i;
    }

    public static int getRequestedWidth(ActivityOptions activityOptions, int i, float f) {
        return (int) (activityOptions.mPopOverWidthDp[i] * f);
    }

    public static int getRequestedHeight(ActivityOptions activityOptions, int i, float f) {
        return (int) (activityOptions.mPopOverHeightDp[i] * f);
    }

    public static void applyWidth(int i, int i2, Rect rect, int i3, Rect rect2) {
        int i4 = i & 112;
        if (i4 == 16) {
            int i5 = rect.left + i3;
            rect2.left = i5;
            int i6 = i5 + i2;
            rect2.right = i6;
            int i7 = rect.right;
            if (i7 < i6) {
                rect2.left = i7 - i2;
                rect2.right = i7;
                return;
            }
            return;
        }
        if (i4 != 32) {
            if (i4 != 64) {
                return;
            }
            int width = (rect.left + (rect.width() / 2)) - (i2 / 2);
            rect2.left = width;
            rect2.right = width + i2;
            return;
        }
        int i8 = rect.right - i3;
        rect2.right = i8;
        int i9 = i8 - i2;
        rect2.left = i9;
        int i10 = rect.left;
        if (i9 < i10) {
            rect2.right = i2 + i10;
            rect2.left = i10;
        }
    }

    public static void applyHeight(int i, int i2, Rect rect, int i3, Rect rect2) {
        int i4 = i & 7;
        if (i4 == 1) {
            int i5 = rect.top + i3;
            rect2.top = i5;
            int i6 = i5 + i2;
            rect2.bottom = i6;
            int i7 = rect.bottom;
            if (i7 < i6) {
                rect2.top = i7 - i2;
                rect2.bottom = i7;
                return;
            }
            return;
        }
        if (i4 != 2) {
            if (i4 != 4) {
                return;
            }
            int height = (rect.top + (rect.height() / 2)) - (i2 / 2);
            rect2.top = height;
            rect2.bottom = height + i2;
            return;
        }
        int i8 = rect.bottom - i3;
        rect2.bottom = i8;
        int i9 = i8 - i2;
        rect2.top = i9;
        int i10 = rect.top;
        if (i9 < i10) {
            rect2.bottom = i2 + i10;
            rect2.top = i10;
        }
    }

    public static int getPopOverShadow(Resources resources) {
        return (int) TypedValue.applyDimension(1, 32.0f, resources.getDisplayMetrics());
    }
}
