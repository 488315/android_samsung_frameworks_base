package com.android.server.wm;

import android.app.ActivityOptions;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.TypedValue;

import com.samsung.android.rune.CoreRune;

import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class PopOverBoundsCalculator {
    /* JADX WARN: Multi-variable type inference failed */
    public static Rect getBounds(final ActivityRecord activityRecord) {
        ActivityOptions activityOptions = activityRecord.mPopOverState.mOptions;
        if (activityOptions == null) {
            return activityRecord.getBounds();
        }
        final DisplayContent displayContent = activityRecord.mDisplayContent;
        ActivityRecord activity =
                activityRecord.task.getActivity(
                        new Predicate() { // from class:
                                          // com.android.server.wm.PopOverBoundsCalculator$$ExternalSyntheticLambda0
                            @Override // java.util.function.Predicate
                            public final boolean test(Object obj) {
                                ActivityRecord activityRecord2 = (ActivityRecord) obj;
                                return activityRecord2 != ActivityRecord.this
                                        && displayContent.isFixedRotationLaunchingApp(
                                                activityRecord2)
                                        && activityRecord2.inFullscreenWindowingMode()
                                        && !activityRecord2.mPopOverState.mIsActivated;
                            }
                        });
        int i =
                (activity == null || activity.getFixedRotationTransformDisplayInfo() == null)
                        ? displayContent.mDisplayRotation.mRotation
                        : activity.getFixedRotationTransformDisplayInfo().rotation;
        boolean isAnyPortrait = displayContent.mDisplayRotation.isAnyPortrait(i);
        float f = displayContent.mDisplayMetrics.density;
        Rect bounds =
                activity != null ? activity.getBounds() : new Rect(activityRecord.task.getBounds());
        Rect rect = new Rect();
        byte b = i == 1 || i == 3;
        Rect rect2 =
                new Rect(
                        displayContent.mDisplayPolicy.getDecorInsetsInfo(
                                        i,
                                        b != false
                                                ? displayContent.mBaseDisplayHeight
                                                : displayContent.mBaseDisplayWidth,
                                        b != false
                                                ? displayContent.mBaseDisplayWidth
                                                : displayContent.mBaseDisplayHeight)
                                .mOverrideConfigInsets);
        rect.set(0, rect2.top, 0, rect2.bottom);
        Task task = activityRecord.task;
        if (task.isDesktopModeEnabled()) {
            if (task.isNewDexMode() && task.inFullscreenWindowingMode()) {
                bounds.top += rect.top;
            }
            if (task.isActivityTypeStandard() || task.inFreeformWindowingMode()) {
                if (task.inFullscreenWindowingMode()) {
                    bounds.top =
                            ((int)
                                            TypedValue.applyDimension(
                                                    1,
                                                    32.0f,
                                                    displayContent
                                                            .mDisplayPolicy
                                                            .getContext()
                                                            .getResources()
                                                            .getDisplayMetrics()))
                                    + bounds.top;
                }
                bounds.top = task.getCaptionHeight() + bounds.top;
            }
            bounds.bottom -= task.inFullscreenWindowingMode() ? rect.bottom : 0;
        } else if (task.inFreeformWindowingMode()) {
            bounds.top = task.getCaptionHeight() + bounds.top;
        } else if (!task.inMultiWindowMode()) {
            Rect safeInsets =
                    displayContent
                            .calculateDisplayCutoutForRotation(
                                    i, activityRecord.isConfigurationNeededInUdcCutout())
                            .getSafeInsets();
            if (CoreRune.FW_OVERLAPPING_WITH_CUTOUT_AS_DEFAULT
                    && displayContent.mIsOverlappingWithCutoutAsDefault) {
                safeInsets.setEmpty();
            }
            bounds.top = Math.max(rect.top, safeInsets.top) + bounds.top;
            bounds.bottom -= rect.bottom + safeInsets.bottom;
            bounds.left = Math.max(rect.left, safeInsets.left) + bounds.left;
            bounds.right -= Math.max(rect.right, safeInsets.right);
        }
        int i2 = (int) (12.0f * f);
        bounds.top += i2;
        bounds.bottom -= (int) (16.0f * f);
        bounds.left += i2;
        bounds.right -= i2;
        int i3 = (int) (activityOptions.mPopOverWidthDp[isAnyPortrait ? 1 : 0] * f);
        int i4 = (int) (activityOptions.mPopOverHeightDp[isAnyPortrait ? 1 : 0] * f);
        int i5 = activityOptions.mPopOverAnchorPosition[isAnyPortrait ? 1 : 0];
        Point point = activityOptions.mPopOverAnchorMarginDp[isAnyPortrait ? 1 : 0];
        int i6 = (int) (point.x * f);
        int i7 = (int) (point.y * f);
        Rect rect3 = new Rect();
        int i8 = i5 & 112;
        if (i8 == 16) {
            int i9 = bounds.left + i6;
            rect3.left = i9;
            int i10 = i9 + i3;
            rect3.right = i10;
            int i11 = bounds.right;
            if (i11 < i10) {
                rect3.left = i11 - i3;
                rect3.right = i11;
            }
        } else if (i8 == 32) {
            int i12 = bounds.right - i6;
            rect3.right = i12;
            int i13 = i12 - i3;
            rect3.left = i13;
            int i14 = bounds.left;
            if (i13 < i14) {
                rect3.right = i3 + i14;
                rect3.left = i14;
            }
        } else if (i8 == 64) {
            int width = ((bounds.width() / 2) + bounds.left) - (i3 / 2);
            rect3.left = width;
            rect3.right = width + i3;
        }
        int i15 = i5 & 7;
        if (i15 == 1) {
            int i16 = bounds.top + i7;
            rect3.top = i16;
            int i17 = i16 + i4;
            rect3.bottom = i17;
            int i18 = bounds.bottom;
            if (i18 < i17) {
                rect3.top = i18 - i4;
                rect3.bottom = i18;
            }
        } else if (i15 == 2) {
            int i19 = bounds.bottom - i7;
            rect3.bottom = i19;
            int i20 = i19 - i4;
            rect3.top = i20;
            int i21 = bounds.top;
            if (i20 < i21) {
                rect3.bottom = i4 + i21;
                rect3.top = i21;
            }
        } else if (i15 == 4) {
            int height = ((bounds.height() / 2) + bounds.top) - (i4 / 2);
            rect3.top = height;
            rect3.bottom = height + i4;
        }
        rect3.intersect(bounds);
        return rect3;
    }
}
