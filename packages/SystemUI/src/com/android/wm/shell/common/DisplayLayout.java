package com.android.wm.shell.common;

import android.R;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.InsetsState;
import android.view.WindowInsets;
import com.android.internal.policy.SystemBarUtils;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class DisplayLayout {
    public DisplayCutout mCutout;
    public int mDensityDpi;
    public RectF mGlobalBoundsDp;
    public int mHeight;
    public int mRotation;
    public int mUiMode;
    public int mWidth;
    public final Rect mNonDecorInsets = new Rect();
    public final Rect mStableInsets = new Rect();
    public boolean mHasNavigationBar = false;
    public boolean mHasStatusBar = false;
    public int mNavBarFrameHeight = 0;
    public int mTaskbarFrameHeight = 0;
    public boolean mAllowSeamlessRotationDespiteNavBarMoving = false;
    public boolean mNavigationBarCanMove = false;
    public boolean mReverseDefaultRotation = false;
    public InsetsState mInsetsState = new InsetsState();
    public final Rect mStableInsetsIgnoringCutout = new Rect();
    public final Rect mImmersiveStableInsets = new Rect();
    public final Rect mNaviStarStableInsets = new Rect();

    public DisplayLayout() {
        new Rect();
    }

    public static int navigationBarPosition(Resources resources, int i, int i2, int i3) {
        if (!CoreRune.FW_NAVBAR_MOVABLE_POLICY && i != i2 && resources.getBoolean(R.bool.config_safe_media_volume_enabled) && i > i2) {
            return i3 == 1 ? 2 : 1;
        }
        return 4;
    }

    public final float density() {
        return this.mDensityDpi * 0.00625f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DisplayLayout)) {
            return false;
        }
        DisplayLayout displayLayout = (DisplayLayout) obj;
        return this.mUiMode == displayLayout.mUiMode && this.mWidth == displayLayout.mWidth && this.mHeight == displayLayout.mHeight && Objects.equals(this.mGlobalBoundsDp, displayLayout.mGlobalBoundsDp) && Objects.equals(this.mCutout, displayLayout.mCutout) && this.mRotation == displayLayout.mRotation && this.mDensityDpi == displayLayout.mDensityDpi && Objects.equals(this.mNonDecorInsets, displayLayout.mNonDecorInsets) && Objects.equals(this.mStableInsets, displayLayout.mStableInsets) && this.mHasNavigationBar == displayLayout.mHasNavigationBar && this.mHasStatusBar == displayLayout.mHasStatusBar && this.mAllowSeamlessRotationDespiteNavBarMoving == displayLayout.mAllowSeamlessRotationDespiteNavBarMoving && this.mNavigationBarCanMove == displayLayout.mNavigationBarCanMove && this.mReverseDefaultRotation == displayLayout.mReverseDefaultRotation && this.mNavBarFrameHeight == displayLayout.mNavBarFrameHeight && this.mTaskbarFrameHeight == displayLayout.mTaskbarFrameHeight && Objects.equals(this.mInsetsState, displayLayout.mInsetsState);
    }

    public final void getDisplayBounds(Rect rect) {
        rect.set(0, 0, this.mWidth, this.mHeight);
    }

    public final void getStableBounds(Rect rect, boolean z) {
        rect.set(0, 0, this.mWidth, this.mHeight);
        rect.inset(stableInsets(z));
    }

    public final PointF globalDpToLocalPx(Number number, Number number2) {
        return new PointF(((number.floatValue() - this.mGlobalBoundsDp.left) * this.mDensityDpi) / 160.0f, ((number2.floatValue() - this.mGlobalBoundsDp.top) * this.mDensityDpi) / 160.0f);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mUiMode), Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), this.mGlobalBoundsDp, this.mCutout, Integer.valueOf(this.mRotation), Integer.valueOf(this.mDensityDpi), this.mNonDecorInsets, this.mStableInsets, Boolean.valueOf(this.mHasNavigationBar), Boolean.valueOf(this.mHasStatusBar), Integer.valueOf(this.mNavBarFrameHeight), Integer.valueOf(this.mTaskbarFrameHeight), Boolean.valueOf(this.mAllowSeamlessRotationDespiteNavBarMoving), Boolean.valueOf(this.mNavigationBarCanMove), Boolean.valueOf(this.mReverseDefaultRotation), this.mInsetsState);
    }

    public final void init(DisplayInfo displayInfo, Resources resources, boolean z, boolean z2) {
        this.mUiMode = resources.getConfiguration().uiMode;
        this.mWidth = displayInfo.logicalWidth;
        this.mHeight = displayInfo.logicalHeight;
        this.mRotation = displayInfo.rotation;
        this.mCutout = displayInfo.displayCutout;
        this.mDensityDpi = displayInfo.logicalDensityDpi;
        this.mGlobalBoundsDp = new RectF(0.0f, 0.0f, pxToDp(Integer.valueOf(this.mWidth)), pxToDp(Integer.valueOf(this.mHeight)));
        this.mHasNavigationBar = z;
        this.mHasStatusBar = z2;
        this.mAllowSeamlessRotationDespiteNavBarMoving = resources.getBoolean(R.bool.config_allowTheaterModeWakeFromLidSwitch);
        this.mNavigationBarCanMove = resources.getBoolean(R.bool.config_safe_media_volume_enabled);
        this.mReverseDefaultRotation = resources.getBoolean(R.bool.config_stopSystemPackagesByDefault);
        recalcInsets(resources);
    }

    public final boolean isLandscape() {
        return this.mWidth > this.mHeight;
    }

    public final PointF localPxToGlobalDp(Number number, Number number2) {
        return new PointF(pxToDp(number) + this.mGlobalBoundsDp.left, pxToDp(number2) + this.mGlobalBoundsDp.top);
    }

    public final float pxToDp(Number number) {
        return (number.floatValue() * 160.0f) / this.mDensityDpi;
    }

    public void recalcInsets(Resources resources) {
        int statusBarHeight;
        int dimensionPixelSize;
        int i = this.mRotation;
        int i2 = this.mWidth;
        int i3 = this.mHeight;
        DisplayCutout displayCutout = this.mCutout;
        InsetsState insetsState = this.mInsetsState;
        int i4 = this.mUiMode;
        Rect rect = this.mNonDecorInsets;
        boolean z = this.mHasNavigationBar;
        rect.setEmpty();
        if (z) {
            Insets calculateInsets = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars(), false);
            int navigationBarPosition = navigationBarPosition(resources, i2, i3, i);
            boolean z2 = i2 > i3;
            if ((i4 & 15) == 3) {
                if (navigationBarPosition == 4) {
                    dimensionPixelSize = resources.getDimensionPixelSize(z2 ? R.dimen.slice_icon_size : R.dimen.select_dialog_drawable_padding_start_material);
                } else {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.snooze_and_bubble_gone_padding_end);
                }
            } else if (navigationBarPosition == 4) {
                dimensionPixelSize = resources.getDimensionPixelSize(z2 ? R.dimen.select_dialog_padding_start_material : R.dimen.seekbar_track_progress_height_material);
            } else {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.slice_shortcut_size);
            }
            if (navigationBarPosition == 4) {
                rect.bottom = Math.max(calculateInsets.bottom, dimensionPixelSize);
            } else if (navigationBarPosition == 2) {
                rect.right = Math.max(calculateInsets.right, dimensionPixelSize);
            } else if (navigationBarPosition == 1) {
                rect.left = Math.max(calculateInsets.left, dimensionPixelSize);
            }
        } else {
            rect.set(insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars(), false).toRect());
        }
        if (displayCutout != null) {
            rect.left = displayCutout.getSafeInsetLeft() + rect.left;
            rect.top = displayCutout.getSafeInsetTop() + rect.top;
            rect.right = displayCutout.getSafeInsetRight() + rect.right;
            rect.bottom = displayCutout.getSafeInsetBottom() + rect.bottom;
        }
        this.mStableInsets.set(this.mNonDecorInsets);
        boolean z3 = this.mHasStatusBar;
        if (z3) {
            Rect rect2 = this.mStableInsets;
            DisplayCutout displayCutout2 = this.mCutout;
            if (z3) {
                if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
                    statusBarHeight = SystemBarUtils.getStatusBarHeight(resources, displayCutout2);
                } else if (isLandscape()) {
                    statusBarHeight = resources.getDimensionPixelSize(17106380);
                } else {
                    statusBarHeight = Math.max(displayCutout2 == null ? 0 : displayCutout2.getSafeInsetTop(), resources.getDimensionPixelSize(17106381));
                }
                rect2.top = Math.max(rect2.top, statusBarHeight);
            }
        }
        this.mNavBarFrameHeight = resources.getDimensionPixelSize(this.mWidth > this.mHeight ? R.dimen.secondary_waterfall_display_right_edge_size : R.dimen.secondary_waterfall_display_left_edge_size);
        this.mTaskbarFrameHeight = SystemBarUtils.getTaskbarHeight(resources);
        this.mImmersiveStableInsets.setEmpty();
        Rect rect3 = this.mNaviStarStableInsets;
        Rect rect4 = this.mStableInsets;
        rect3.set(rect4.left, rect4.top, rect4.right, 0);
        if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT || this.mCutout == null) {
            return;
        }
        this.mStableInsetsIgnoringCutout.set(this.mStableInsets);
        if (this.mCutout.getSafeInsetLeft() > 0) {
            this.mStableInsetsIgnoringCutout.left -= this.mCutout.getSafeInsetLeft();
        }
        if (this.mCutout.getSafeInsetRight() > 0) {
            this.mStableInsetsIgnoringCutout.right -= this.mCutout.getSafeInsetRight();
        }
        if (this.mHasNavigationBar && navigationBarPosition(resources, this.mWidth, this.mHeight, this.mRotation) == 4 && this.mCutout.getSafeInsetBottom() > 0) {
            this.mStableInsetsIgnoringCutout.bottom -= this.mCutout.getSafeInsetBottom();
        }
    }

    public final void rotateTo(Resources resources, int i) {
        int i2 = this.mWidth;
        int i3 = this.mHeight;
        int i4 = this.mRotation;
        boolean z = (((i - i4) + 4) % 4) % 2 != 0;
        this.mRotation = i;
        if (z) {
            this.mWidth = i3;
            this.mHeight = i2;
        }
        DisplayCutout displayCutout = this.mCutout;
        if (displayCutout != null) {
            this.mCutout = displayCutout.getRotated(i2, i3, i4, i);
        }
        recalcInsets(resources);
    }

    public final void set(DisplayLayout displayLayout) {
        this.mUiMode = displayLayout.mUiMode;
        this.mWidth = displayLayout.mWidth;
        this.mHeight = displayLayout.mHeight;
        this.mGlobalBoundsDp = displayLayout.mGlobalBoundsDp;
        this.mCutout = displayLayout.mCutout;
        this.mRotation = displayLayout.mRotation;
        this.mDensityDpi = displayLayout.mDensityDpi;
        this.mHasNavigationBar = displayLayout.mHasNavigationBar;
        this.mHasStatusBar = displayLayout.mHasStatusBar;
        this.mAllowSeamlessRotationDespiteNavBarMoving = displayLayout.mAllowSeamlessRotationDespiteNavBarMoving;
        this.mNavigationBarCanMove = displayLayout.mNavigationBarCanMove;
        this.mReverseDefaultRotation = displayLayout.mReverseDefaultRotation;
        this.mNavBarFrameHeight = displayLayout.mNavBarFrameHeight;
        this.mTaskbarFrameHeight = displayLayout.mTaskbarFrameHeight;
        this.mNonDecorInsets.set(displayLayout.mNonDecorInsets);
        this.mStableInsets.set(displayLayout.mStableInsets);
        this.mInsetsState.set(displayLayout.mInsetsState, true);
        this.mImmersiveStableInsets.set(displayLayout.mImmersiveStableInsets);
        Rect rect = this.mNaviStarStableInsets;
        Rect rect2 = this.mStableInsets;
        rect.set(rect2.left, rect2.top, rect2.right, 0);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT) {
            this.mStableInsetsIgnoringCutout.set(displayLayout.mStableInsetsIgnoringCutout);
        }
    }

    public final Rect stableInsets(boolean z) {
        if (!z) {
            return this.mStableInsets;
        }
        Rect rect = new Rect(MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED ? this.mImmersiveStableInsets : MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED ? this.mNaviStarStableInsets : (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT || this.mCutout == null) ? this.mStableInsets : this.mStableInsetsIgnoringCutout);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY) {
            rect.top = 0;
        }
        return rect;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder(128);
        sb.append(super.toString());
        sb.append("\n{ mWidth=");
        sb.append(this.mWidth);
        sb.append(", mHeight=");
        sb.append(this.mHeight);
        sb.append(", mRotation=");
        sb.append(this.mRotation);
        sb.append(", mNonDecorInsets=");
        sb.append(this.mNonDecorInsets);
        sb.append(", mStableInsets=");
        sb.append(this.mStableInsets);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT) {
            sb.append(", mStableInsetsWithoutCutout=");
            sb.append(this.mStableInsetsIgnoringCutout);
        }
        sb.append(", mHasNavigationBar=" + this.mHasNavigationBar);
        sb.append(", mImmersiveStableInsets=");
        sb.append(this.mImmersiveStableInsets);
        sb.append(", mNaviStarStableInsets=");
        sb.append(this.mNaviStarStableInsets);
        sb.append(" }");
        return sb.toString();
    }

    public DisplayLayout(DisplayInfo displayInfo, Resources resources, boolean z, boolean z2) {
        new Rect();
        init(displayInfo, resources, z, z2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00a0, code lost:
    
        if (r4 != false) goto L6;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public DisplayLayout(android.content.Context r7, android.view.Display r8) {
        /*
            r6 = this;
            r6.<init>()
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r6.mNonDecorInsets = r0
            android.graphics.Rect r0 = new android.graphics.Rect
            r0.<init>()
            r6.mStableInsets = r0
            r0 = 0
            r6.mHasNavigationBar = r0
            r6.mHasStatusBar = r0
            r6.mNavBarFrameHeight = r0
            r6.mTaskbarFrameHeight = r0
            r6.mAllowSeamlessRotationDespiteNavBarMoving = r0
            r6.mNavigationBarCanMove = r0
            r6.mReverseDefaultRotation = r0
            android.view.InsetsState r1 = new android.view.InsetsState
            r1.<init>()
            r6.mInsetsState = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r6.mStableInsetsIgnoringCutout = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r6.mImmersiveStableInsets = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            r6.mNaviStarStableInsets = r1
            android.graphics.Rect r1 = new android.graphics.Rect
            r1.<init>()
            int r1 = r8.getDisplayId()
            android.view.DisplayInfo r2 = new android.view.DisplayInfo
            r2.<init>()
            r8.getDisplayInfo(r2)
            android.content.res.Resources r8 = r7.getResources()
            r3 = 1
            if (r1 != 0) goto L7b
            java.lang.String r4 = "qemu.hw.mainkeys"
            java.lang.String r4 = android.os.SystemProperties.get(r4)
            java.lang.String r5 = "1"
            boolean r5 = r5.equals(r4)
            if (r5 == 0) goto L65
        L63:
            r7 = r0
            goto La3
        L65:
            java.lang.String r5 = "0"
            boolean r4 = r5.equals(r4)
            if (r4 == 0) goto L6f
        L6d:
            r7 = r3
            goto La3
        L6f:
            android.content.res.Resources r7 = r7.getResources()
            r4 = 17891914(0x111024a, float:2.6633936E-38)
            boolean r7 = r7.getBoolean(r4)
            goto La3
        L7b:
            int r4 = r2.type
            r5 = 5
            if (r4 != r5) goto L88
            int r4 = r2.ownerUid
            r5 = 1000(0x3e8, float:1.401E-42)
            if (r4 == r5) goto L88
            r4 = r3
            goto L89
        L88:
            r4 = r0
        L89:
            android.content.ContentResolver r7 = r7.getContentResolver()
            java.lang.String r5 = "force_desktop_mode_on_external_displays"
            int r7 = android.provider.Settings.Global.getInt(r7, r5, r0)
            if (r7 == 0) goto L97
            r7 = r3
            goto L98
        L97:
            r7 = r0
        L98:
            int r5 = r2.flags
            r5 = r5 & 64
            if (r5 != 0) goto L6d
            if (r7 == 0) goto L63
            if (r4 != 0) goto L63
            goto L6d
        La3:
            if (r1 != 0) goto La6
            r0 = r3
        La6:
            r6.init(r2, r8, r7, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.DisplayLayout.<init>(android.content.Context, android.view.Display):void");
    }

    public DisplayLayout(DisplayLayout displayLayout) {
        new Rect();
        set(displayLayout);
    }
}
