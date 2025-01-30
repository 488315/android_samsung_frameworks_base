package com.android.wm.shell.common;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.Rect;
import android.os.SystemProperties;
import android.provider.Settings;
import android.support.v4.media.AbstractC0000x2c234b15;
import android.util.RotationUtils;
import android.util.Size;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayInfo;
import android.view.InsetsState;
import android.view.WindowInsets;
import com.android.internal.policy.SystemBarUtils;
import com.samsung.android.multiwindow.MultiWindowCoreState;
import com.samsung.android.rune.CoreRune;
import com.sec.ims.configuration.DATA;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisplayLayout {
    public DisplayCutout mCutout;
    public int mDensityDpi;
    public int mHeight;
    public int mRotation;
    public int mUiMode;
    public int mWidth;
    public final Rect mNonDecorInsets = new Rect();
    public final Rect mStableInsets = new Rect();
    public boolean mHasNavigationBar = false;
    public boolean mHasStatusBar = false;
    public int mNavBarFrameHeight = 0;
    public boolean mAllowSeamlessRotationDespiteNavBarMoving = false;
    public boolean mNavigationBarCanMove = false;
    public boolean mReverseDefaultRotation = false;
    public InsetsState mInsetsState = new InsetsState();
    public final Rect mStableInsetsIgnoringCutout = new Rect();
    public final Rect mImmersiveStableInsets = new Rect();
    public final Rect mNaviStarStableInsets = new Rect();
    public final Rect mTempRect = new Rect();

    public DisplayLayout() {
    }

    public static DisplayCutout computeSafeInsets(DisplayCutout displayCutout, int i, int i2) {
        if (displayCutout == DisplayCutout.NO_CUTOUT) {
            return null;
        }
        Size size = new Size(i, i2);
        if (size.getWidth() != size.getHeight()) {
            return displayCutout.replaceSafeInsets(new Rect(Math.max(displayCutout.getWaterfallInsets().left, findCutoutInsetForSide(size, displayCutout.getBoundingRectLeft(), 3)), Math.max(displayCutout.getWaterfallInsets().top, findCutoutInsetForSide(size, displayCutout.getBoundingRectTop(), 48)), Math.max(displayCutout.getWaterfallInsets().right, findCutoutInsetForSide(size, displayCutout.getBoundingRectRight(), 5)), Math.max(displayCutout.getWaterfallInsets().bottom, findCutoutInsetForSide(size, displayCutout.getBoundingRectBottom(), 80))));
        }
        throw new UnsupportedOperationException("not implemented: display=" + size + " cutout=" + displayCutout);
    }

    public static int findCutoutInsetForSide(Size size, Rect rect, int i) {
        if (rect.isEmpty()) {
            return 0;
        }
        if (i == 3) {
            return Math.max(0, rect.right);
        }
        if (i == 5) {
            return Math.max(0, size.getWidth() - rect.left);
        }
        if (i == 48) {
            return Math.max(0, rect.bottom);
        }
        if (i == 80) {
            return Math.max(0, size.getHeight() - rect.top);
        }
        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("unknown gravity: ", i));
    }

    public static int navigationBarPosition(Resources resources, int i, int i2, int i3) {
        if (!(CoreRune.FW_NAVBAR_MOVABLE_POLICY ? false : i != i2 && resources.getBoolean(R.bool.config_letterboxIsEducationEnabled)) || i <= i2) {
            return 4;
        }
        return i3 == 1 ? 2 : 1;
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
        return this.mUiMode == displayLayout.mUiMode && this.mWidth == displayLayout.mWidth && this.mHeight == displayLayout.mHeight && Objects.equals(this.mCutout, displayLayout.mCutout) && this.mRotation == displayLayout.mRotation && this.mDensityDpi == displayLayout.mDensityDpi && Objects.equals(this.mNonDecorInsets, displayLayout.mNonDecorInsets) && Objects.equals(this.mStableInsets, displayLayout.mStableInsets) && this.mHasNavigationBar == displayLayout.mHasNavigationBar && this.mHasStatusBar == displayLayout.mHasStatusBar && this.mAllowSeamlessRotationDespiteNavBarMoving == displayLayout.mAllowSeamlessRotationDespiteNavBarMoving && this.mNavigationBarCanMove == displayLayout.mNavigationBarCanMove && this.mReverseDefaultRotation == displayLayout.mReverseDefaultRotation && this.mNavBarFrameHeight == displayLayout.mNavBarFrameHeight && Objects.equals(this.mInsetsState, displayLayout.mInsetsState);
    }

    public final void getDisplayBounds(Rect rect) {
        rect.set(0, 0, this.mWidth, this.mHeight);
    }

    public final void getStableBounds(Rect rect, boolean z) {
        rect.set(0, 0, this.mWidth, this.mHeight);
        rect.inset(stableInsets(z));
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mUiMode), Integer.valueOf(this.mWidth), Integer.valueOf(this.mHeight), this.mCutout, Integer.valueOf(this.mRotation), Integer.valueOf(this.mDensityDpi), this.mNonDecorInsets, this.mStableInsets, Boolean.valueOf(this.mHasNavigationBar), Boolean.valueOf(this.mHasStatusBar), Integer.valueOf(this.mNavBarFrameHeight), Boolean.valueOf(this.mAllowSeamlessRotationDespiteNavBarMoving), Boolean.valueOf(this.mNavigationBarCanMove), Boolean.valueOf(this.mReverseDefaultRotation), this.mInsetsState);
    }

    public final void init(DisplayInfo displayInfo, Resources resources, boolean z, boolean z2) {
        this.mUiMode = resources.getConfiguration().uiMode;
        this.mWidth = displayInfo.logicalWidth;
        this.mHeight = displayInfo.logicalHeight;
        this.mRotation = displayInfo.rotation;
        this.mCutout = displayInfo.displayCutout;
        this.mDensityDpi = displayInfo.logicalDensityDpi;
        this.mHasNavigationBar = z;
        this.mHasStatusBar = z2;
        this.mAllowSeamlessRotationDespiteNavBarMoving = resources.getBoolean(R.bool.config_allowSeamlessRotationDespiteNavBarMoving);
        this.mNavigationBarCanMove = resources.getBoolean(R.bool.config_letterboxIsEducationEnabled);
        this.mReverseDefaultRotation = resources.getBoolean(R.bool.config_notificationHeaderClickableForExpand);
        recalcInsets(resources);
    }

    public void recalcInsets(Resources resources) {
        int dimensionPixelSize;
        int i = this.mRotation;
        int i2 = this.mWidth;
        int i3 = this.mHeight;
        DisplayCutout displayCutout = this.mCutout;
        InsetsState insetsState = this.mInsetsState;
        int i4 = this.mUiMode;
        boolean z = this.mHasNavigationBar;
        Rect rect = this.mNonDecorInsets;
        rect.setEmpty();
        Rect rect2 = new Rect();
        Insets calculateInsets = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars(), false);
        if (z) {
            Insets calculateInsets2 = insetsState.calculateInsets(insetsState.getDisplayFrame(), WindowInsets.Type.navigationBars(), false);
            int navigationBarPosition = navigationBarPosition(resources, i2, i3, i);
            boolean z2 = i2 > i3;
            if ((i4 & 15) == 3) {
                if (navigationBarPosition == 4) {
                    dimensionPixelSize = resources.getDimensionPixelSize(z2 ? R.dimen.notification_expand_button_icon_padding : R.dimen.notification_custom_view_max_image_width);
                } else {
                    dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_grayscale_icon_max_size);
                }
            } else if (navigationBarPosition == 4) {
                dimensionPixelSize = resources.getDimensionPixelSize(z2 ? R.dimen.notification_custom_view_max_image_width_low_ram : R.dimen.notification_custom_view_max_image_height_low_ram);
            } else {
                dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.notification_feedback_size);
            }
            if (navigationBarPosition == 4) {
                rect.bottom = Math.max(calculateInsets2.bottom, dimensionPixelSize);
            } else if (navigationBarPosition == 2) {
                rect.right = Math.max(calculateInsets2.right, dimensionPixelSize);
            } else if (navigationBarPosition == 1) {
                rect.left = Math.max(calculateInsets2.left, dimensionPixelSize);
            }
        } else if (!calculateInsets.toRect().equals(rect2)) {
            int i5 = calculateInsets.bottom;
            if (i5 != 0) {
                rect.bottom = i5;
            } else {
                int i6 = calculateInsets.right;
                if (i6 != 0) {
                    rect.right = i6;
                } else {
                    int i7 = calculateInsets.left;
                    if (i7 != 0) {
                        rect.left = i7;
                    }
                }
            }
        }
        if (displayCutout != null) {
            rect.left = displayCutout.getSafeInsetLeft() + rect.left;
            rect.top = displayCutout.getSafeInsetTop() + rect.top;
            rect.right = displayCutout.getSafeInsetRight() + rect.right;
            rect.bottom = displayCutout.getSafeInsetBottom() + rect.bottom;
        }
        Rect rect3 = this.mStableInsets;
        rect3.set(rect);
        if (this.mHasStatusBar && !resources.getConfiguration().isDexMode()) {
            DisplayCutout displayCutout2 = this.mCutout;
            if (this.mHasStatusBar) {
                rect3.top = Math.max(rect3.top, SystemBarUtils.getStatusBarHeight(resources, displayCutout2));
            }
        }
        this.mNavBarFrameHeight = resources.getDimensionPixelSize(this.mWidth > this.mHeight ? R.dimen.notification_content_margin_start : R.dimen.notification_content_margin_end);
        this.mImmersiveStableInsets.setEmpty();
        this.mNaviStarStableInsets.set(rect3.left, rect3.top, rect3.right, 0);
        if (!CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT || this.mCutout == null) {
            return;
        }
        Rect rect4 = this.mStableInsetsIgnoringCutout;
        rect4.set(rect3);
        if (this.mCutout.getSafeInsetLeft() > 0) {
            rect4.left -= this.mCutout.getSafeInsetLeft();
        }
        if (this.mCutout.getSafeInsetRight() > 0) {
            rect4.right -= this.mCutout.getSafeInsetRight();
        }
        if (this.mHasNavigationBar && navigationBarPosition(resources, this.mWidth, this.mHeight, this.mRotation) == 4 && this.mCutout.getSafeInsetBottom() > 0) {
            rect4.bottom -= this.mCutout.getSafeInsetBottom();
        }
    }

    public final void rotateTo(int i, Resources resources) {
        DisplayCutout displayCutout;
        int i2 = ((i - this.mRotation) + 4) % 4;
        boolean z = i2 % 2 != 0;
        int i3 = this.mWidth;
        int i4 = this.mHeight;
        this.mRotation = i;
        if (z) {
            this.mWidth = i4;
            this.mHeight = i3;
        }
        DisplayCutout displayCutout2 = this.mCutout;
        if (displayCutout2 != null && !displayCutout2.isEmpty()) {
            DisplayCutout displayCutout3 = this.mCutout;
            if (displayCutout3 == null || displayCutout3 == DisplayCutout.NO_CUTOUT) {
                displayCutout = null;
            } else if (i2 == 0) {
                displayCutout = computeSafeInsets(displayCutout3, i3, i4);
            } else {
                Insets rotateInsets = RotationUtils.rotateInsets(displayCutout3.getWaterfallInsets(), i2);
                boolean z2 = i2 == 1 || i2 == 3;
                Rect[] boundingRectsAll = displayCutout3.getBoundingRectsAll();
                Rect[] rectArr = new Rect[boundingRectsAll.length];
                Rect rect = new Rect(0, 0, i3, i4);
                for (int i5 = 0; i5 < boundingRectsAll.length; i5++) {
                    Rect rect2 = new Rect(boundingRectsAll[i5]);
                    if (!rect2.isEmpty()) {
                        RotationUtils.rotateBounds(rect2, rect, i2);
                    }
                    int i6 = i5 - i2;
                    if (i6 < 0) {
                        i6 += 4;
                    }
                    rectArr[i6] = rect2;
                }
                DisplayCutout.CutoutPathParserInfo cutoutPathParserInfo = displayCutout3.getCutoutPathParserInfo();
                displayCutout = computeSafeInsets(DisplayCutout.constructDisplayCutout(rectArr, rotateInsets, new DisplayCutout.CutoutPathParserInfo(cutoutPathParserInfo.getDisplayWidth(), cutoutPathParserInfo.getDisplayHeight(), cutoutPathParserInfo.getPhysicalDisplayWidth(), cutoutPathParserInfo.getPhysicalDisplayHeight(), cutoutPathParserInfo.getDensity(), cutoutPathParserInfo.getCutoutSpec(), i, cutoutPathParserInfo.getScale(), cutoutPathParserInfo.getPhysicalPixelDisplaySizeRatio())), z2 ? i4 : i3, z2 ? i3 : i4);
            }
            this.mCutout = displayCutout;
        }
        recalcInsets(resources);
    }

    public final void set(DisplayLayout displayLayout) {
        this.mUiMode = displayLayout.mUiMode;
        this.mWidth = displayLayout.mWidth;
        this.mHeight = displayLayout.mHeight;
        this.mCutout = displayLayout.mCutout;
        this.mRotation = displayLayout.mRotation;
        this.mDensityDpi = displayLayout.mDensityDpi;
        this.mHasNavigationBar = displayLayout.mHasNavigationBar;
        this.mHasStatusBar = displayLayout.mHasStatusBar;
        this.mAllowSeamlessRotationDespiteNavBarMoving = displayLayout.mAllowSeamlessRotationDespiteNavBarMoving;
        this.mNavigationBarCanMove = displayLayout.mNavigationBarCanMove;
        this.mReverseDefaultRotation = displayLayout.mReverseDefaultRotation;
        this.mNavBarFrameHeight = displayLayout.mNavBarFrameHeight;
        this.mNonDecorInsets.set(displayLayout.mNonDecorInsets);
        Rect rect = displayLayout.mStableInsets;
        Rect rect2 = this.mStableInsets;
        rect2.set(rect);
        this.mInsetsState.set(displayLayout.mInsetsState, true);
        this.mImmersiveStableInsets.set(displayLayout.mImmersiveStableInsets);
        this.mNaviStarStableInsets.set(rect2.left, rect2.top, rect2.right, 0);
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT) {
            this.mStableInsetsIgnoringCutout.set(displayLayout.mStableInsetsIgnoringCutout);
        }
    }

    public final Rect stableInsets(boolean z) {
        Rect rect = this.mTempRect;
        if (z && MultiWindowCoreState.MW_SPLIT_IMMERSIVE_MODE_ENABLED) {
            rect.set(this.mImmersiveStableInsets);
            return rect;
        }
        if (z && MultiWindowCoreState.MW_NAVISTAR_SPLIT_IMMERSIVE_MODE_ENABLED) {
            rect.set(this.mNaviStarStableInsets);
            return rect;
        }
        if (CoreRune.MW_MULTI_SPLIT_BOUNDS_POLICY_IGNORING_CUTOUT && z && this.mCutout != null) {
            rect.set(this.mStableInsetsIgnoringCutout);
            return rect;
        }
        rect.set(this.mStableInsets);
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
        init(displayInfo, resources, z, z2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x009e, code lost:
    
        if (r4 != false) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DisplayLayout(Context context, Display display) {
        boolean z;
        int displayId = display.getDisplayId();
        DisplayInfo displayInfo = new DisplayInfo();
        display.getDisplayInfo(displayInfo);
        Resources resources = context.getResources();
        if (displayId == 0) {
            String str = SystemProperties.get("qemu.hw.mainkeys");
            if (!"1".equals(str)) {
                if (!DATA.DM_FIELD_INDEX.PCSCF_DOMAIN.equals(str)) {
                    z = context.getResources().getBoolean(R.bool.config_predictShowStartingSurface);
                }
                z = true;
            }
            z = false;
        } else {
            boolean z2 = displayInfo.type == 5 && displayInfo.ownerUid != 1000;
            boolean z3 = Settings.Global.getInt(context.getContentResolver(), "force_desktop_mode_on_external_displays", 0) != 0;
            if ((displayInfo.flags & 64) == 0) {
                if (z3) {
                }
                z = false;
            }
            z = true;
        }
        init(displayInfo, resources, z, displayId == 0);
    }

    public DisplayLayout(DisplayLayout displayLayout) {
        set(displayLayout);
    }
}
