package com.android.server.wm;

import android.graphics.Point;
import android.graphics.Rect;
import com.android.server.wm.DisplayPolicy;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AppCompatDisplayInsets {
    public final int mHeight;
    public final boolean mIsFloating;
    public final boolean mIsInFixedOrientationOrAspectRatioLetterbox;
    public final boolean mIsRotationCompatMode;
    public final int mOriginalRequestedOrientation;
    public final int mWidth;
    public final Rect[] mNonDecorInsets = new Rect[4];
    public final Rect[] mStableInsets = new Rect[4];

    public AppCompatDisplayInsets(DisplayContent displayContent, ActivityRecord activityRecord, Rect rect, boolean z) {
        boolean z2 = CoreRune.MT_APP_COMPAT_ROTATION_COMPAT_MODE;
        boolean z3 = z2 && activityRecord.mAtmService.mMultiTaskingAppCompatController.mOrientationPolicy.shouldCreateAppCompatDisplayInsetsForRotationCompat(activityRecord);
        this.mIsRotationCompatMode = z3;
        activityRecord.mAtmService.mMultiTaskingAppCompatController.mSizeCompatModePolicy.getClass();
        Rect rect2 = z2 && z3 ? null : rect;
        int i = displayContent.mDisplayRotation.mRotation;
        boolean tasksAreFloating = activityRecord.getWindowConfiguration().tasksAreFloating();
        this.mIsFloating = tasksAreFloating;
        this.mOriginalRequestedOrientation = activityRecord.getRequestedConfigurationOrientation();
        if (tasksAreFloating) {
            Rect bounds = activityRecord.getWindowConfiguration().getBounds();
            this.mWidth = bounds.width();
            this.mHeight = bounds.height();
            Rect rect3 = new Rect();
            for (int i2 = 0; i2 < 4; i2++) {
                this.mNonDecorInsets[i2] = rect3;
                this.mStableInsets[i2] = rect3;
            }
            this.mIsInFixedOrientationOrAspectRatioLetterbox = false;
            return;
        }
        Task task = activityRecord.task;
        boolean z4 = rect2 != null;
        this.mIsInFixedOrientationOrAspectRatioLetterbox = z4;
        rect2 = z4 ? rect2 : task != null ? task.getBounds() : displayContent.getBounds();
        int rotation = (activityRecord.hasFixedRotationTransform() && z4) ? activityRecord.getWindowConfiguration().getRotation() : displayContent.getConfiguration().windowConfiguration.getRotation();
        boolean z5 = rotation == 1 || rotation == 3;
        int width = rect2.width();
        int height = rect2.height();
        Point point = z5 ? new Point(height, width) : new Point(width, height);
        this.mWidth = point.x;
        this.mHeight = point.y;
        Rect rect4 = rect2.equals(displayContent.getBounds()) ? null : new Rect();
        DisplayPolicy displayPolicy = displayContent.mDisplayPolicy;
        int i3 = 0;
        while (i3 < 4) {
            this.mNonDecorInsets[i3] = new Rect();
            this.mStableInsets[i3] = new Rect();
            boolean z6 = i3 == 1 || i3 == 3;
            int i4 = z6 ? displayContent.mBaseDisplayHeight : displayContent.mBaseDisplayWidth;
            int i5 = z6 ? displayContent.mBaseDisplayWidth : displayContent.mBaseDisplayHeight;
            DisplayPolicy.DecorInsets.Info decorInsetsInfo = displayPolicy.getDecorInsetsInfo(i3, i4, i5);
            if (z) {
                this.mStableInsets[i3].set(decorInsetsInfo.mOverrideConfigInsets);
                this.mNonDecorInsets[i3].set(decorInsetsInfo.mOverrideNonDecorInsets);
            } else {
                this.mStableInsets[i3].set(decorInsetsInfo.mConfigInsets);
                this.mNonDecorInsets[i3].set(decorInsetsInfo.mNonDecorInsets);
            }
            if (rect4 != null) {
                rect4.set(rect2);
                displayContent.rotateBounds(rotation, rect4, i3);
                updateInsetsForBounds(rect4, i4, i5, this.mNonDecorInsets[i3]);
                updateInsetsForBounds(rect4, i4, i5, this.mStableInsets[i3]);
            }
            i3++;
        }
    }

    public static void updateInsetsForBounds(Rect rect, int i, int i2, Rect rect2) {
        rect2.left = Math.max(0, rect2.left - rect.left);
        rect2.top = Math.max(0, rect2.top - rect.top);
        rect2.right = Math.max(0, (rect.right - i) + rect2.right);
        rect2.bottom = Math.max(0, (rect.bottom - i2) + rect2.bottom);
    }

    public final void getBoundsByRotation(int i, Rect rect) {
        boolean z = true;
        if (i != 1 && i != 3) {
            z = false;
        }
        int i2 = this.mWidth;
        int i3 = this.mHeight;
        int i4 = z ? i3 : i2;
        if (!z) {
            i2 = i3;
        }
        rect.set(0, 0, i4, i2);
    }
}
