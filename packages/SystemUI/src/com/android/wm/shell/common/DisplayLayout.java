package com.android.wm.shell.common;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.SystemProperties;
import android.provider.Settings;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.InsetsState;
import android.view.WindowInsets;
import com.android.internal.policy.SystemBarUtils;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import java.util.Objects;

/* loaded from: classes3.dex */
public class DisplayLayout {
    public int mCaptionInsets;
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

    public final void getStableBoundsByInsetsVisibility(Rect rect) {
        InsetsState insetsState = this.mInsetsState;
        Insets insetsCalculateInsets = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars(), true);
        rect.set(0, 0, this.mWidth, this.mHeight);
        rect.inset(insetsCalculateInsets);
    }

    public final PointF globalDpToLocalPx(Number number, Number number2) {
        return new PointF(((number.floatValue() - this.mGlobalBoundsDp.left) * this.mDensityDpi) / 160.0f, ((number2.floatValue() - this.mGlobalBoundsDp.top) * this.mDensityDpi) / 160.0f);
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mUiMode), Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), this.mGlobalBoundsDp, this.mCutout, Integer.valueOf(this.mRotation), Integer.valueOf(this.mDensityDpi), this.mNonDecorInsets, this.mStableInsets, Boolean.valueOf(this.mHasNavigationBar), Boolean.valueOf(this.mHasStatusBar), Integer.valueOf(this.mNavBarFrameHeight), Integer.valueOf(this.mTaskbarFrameHeight), Boolean.valueOf(this.mAllowSeamlessRotationDespiteNavBarMoving), Boolean.valueOf(this.mNavigationBarCanMove), Boolean.valueOf(this.mReverseDefaultRotation), this.mInsetsState);
    }

    public final void init(DisplayInfo displayInfo, Resources resources, boolean z, boolean z2) throws Resources.NotFoundException {
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

    public void recalcInsets(Resources resources) throws Resources.NotFoundException {
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
            Insets insetsCalculateInsets = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars(), false);
            int iNavigationBarPosition = navigationBarPosition(resources, i2, i3, i);
            boolean z2 = i2 > i3;
            if ((i4 & 15) == 3) {
                if (iNavigationBarPosition == 4) {
                    dimensionPixelSize = resources.getDimensionPixelSize(z2 ? R.dimen.slice_padding : R.dimen.select_dialog_padding_start_material);
                } else {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.spot_shadow_alpha);
                }
            } else if (iNavigationBarPosition == 4) {
                dimensionPixelSize = resources.getDimensionPixelSize(z2 ? R.dimen.slice_icon_size : R.dimen.select_dialog_drawable_padding_start_material);
            } else {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.snooze_and_bubble_gone_padding_end);
            }
            if (iNavigationBarPosition == 4) {
                rect.bottom = Math.max(insetsCalculateInsets.bottom, dimensionPixelSize);
            } else if (iNavigationBarPosition == 2) {
                rect.right = Math.max(insetsCalculateInsets.right, dimensionPixelSize);
            } else if (iNavigationBarPosition == 1) {
                rect.left = Math.max(insetsCalculateInsets.left, dimensionPixelSize);
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
                    statusBarHeight = resources.getDimensionPixelSize(17106381);
                } else {
                    statusBarHeight = Math.max(displayCutout2 == null ? 0 : displayCutout2.getSafeInsetTop(), resources.getDimensionPixelSize(17106382));
                }
                rect2.top = Math.max(rect2.top, statusBarHeight);
            }
        }
        this.mNavBarFrameHeight = resources.getDimensionPixelSize(this.mWidth > this.mHeight ? R.dimen.secondary_waterfall_display_top_edge_size : R.dimen.secondary_waterfall_display_right_edge_size);
        this.mTaskbarFrameHeight = SystemBarUtils.getTaskbarHeight(resources);
        this.mImmersiveStableInsets.setEmpty();
        Rect rect3 = this.mNaviStarStableInsets;
        Rect rect4 = this.mStableInsets;
        rect3.set(rect4.left, rect4.top, rect4.right, 0);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT && this.mCutout != null) {
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
        this.mCaptionInsets = resources.getDimensionPixelSize(SystemBarUtils.getDesktopViewAppHeaderHeightId());
    }

    public final void rotateTo(Resources resources, int i) throws Resources.NotFoundException {
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

    public DisplayLayout(DisplayInfo displayInfo, Resources resources, boolean z, boolean z2) throws Resources.NotFoundException {
        new Rect();
        init(displayInfo, resources, z, z2);
    }

    /* JADX WARN: Removed duplicated region for block: B:6:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x006d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DisplayLayout(Context context, Display display) throws Resources.NotFoundException {
        boolean z;
        new Rect();
        int displayId = display.getDisplayId();
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        Resources resources = context.getResources();
        if (displayId == 0) {
            String str = SystemProperties.get("qemu.hw.mainkeys");
            if ("1".equals(str)) {
                z = false;
            } else {
                z = "0".equals(str) ? true : context.getResources().getBoolean(R.bool.config_swipeDisambiguation);
            }
        } else {
            boolean z2 = displayInfo.type == 5 && displayInfo.ownerUid != 1000;
            boolean z3 = Settings.Global.getInt(context.getContentResolver(), "force_desktop_mode_on_external_displays", 0) != 0;
            if ((displayInfo.flags & 64) != 0 || (z3 && !z2)) {
            }
        }
        init(displayInfo, resources, z, displayId == 0);
    }

    public DisplayLayout(DisplayLayout displayLayout) {
        new Rect();
        set(displayLayout);
    }
}
