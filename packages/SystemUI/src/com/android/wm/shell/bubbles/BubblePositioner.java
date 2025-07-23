package com.android.wm.shell.bubbles;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.UserHandle;
import android.util.Log;
import android.view.WindowManager;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.wm.shell.QpShellRune;
import com.android.wm.shell.bubbles.BubbleStackView;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.bubbles.BubbleBarLocation;
import com.android.wm.shell.shared.bubbles.BubbleDropTargetBoundsProvider;
import com.android.wm.shell.shared.bubbles.DeviceConfig;
import com.samsung.android.knox.EnterpriseContainerCallback;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class BubblePositioner implements BubbleDropTargetBoundsProvider {
    public int mBarExpViewDropTargetPaddingBottom;
    public int mBarExpViewDropTargetPaddingHorizontal;
    public int mBarExpViewDropTargetPaddingTop;
    public BubbleBarLocation mBubbleBarLocation;
    public int mBubbleBarTopOnScreen;
    public int mBubbleElevation;
    public int mBubbleOffscreenAmount;
    public int mBubbleOverflowLandscapeHeight;
    public int mBubbleOverflowMarginBottom;
    public int mBubblePaddingTop;
    public int mBubbleSize;
    public final Context mContext;
    public int mDefaultMaxBubbles;
    public DeviceConfig mDeviceConfig;
    public int mExpandedViewBubbleBarWidth;
    public int mExpandedViewLargeScreenInsetClosestEdge;
    public int mExpandedViewLargeScreenInsetFurthestEdge;
    public int mExpandedViewLargeScreenWidth;
    public int mExpandedViewMinHeight;
    public int mExpandedViewPadding;
    public int mImeHeight;
    public boolean mImeVisible;
    public Insets mInsets;
    public int mManageButtonHeight;
    public int mManageButtonHeightIncludingMargins;
    public int mMaxBubbles;
    public int mMinimumFlyoutWidthLargeScreen;
    public int mOverflowHeight;
    public int mOverflowWidth;
    public int mPointerHeight;
    public int mPointerMargin;
    public int mPointerOverlap;
    public int mPointerWidth;
    public Rect mPositionRect;
    public PointF mRestingStackPosition;
    public int mRotation;
    public Rect mScreenRect;
    public int mSpacingBetweenBubbles;
    public int mStackOffset;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public enum StackPinnedEdge {
        LEFT,
        RIGHT
    }

    public BubblePositioner(Context context, WindowManager windowManager) {
        this(context, DeviceConfig.create(context, windowManager));
    }

    public final RectF getAllowableStackPositionRegion(int i) {
        RectF rectF = new RectF(this.mPositionRect);
        int i2 = this.mImeVisible ? this.mImeHeight : 0;
        float f = i > 1 ? this.mBubblePaddingTop + this.mStackOffset : this.mBubblePaddingTop;
        rectF.left = rectF.left - (-this.mBubbleOffscreenAmount);
        rectF.top += this.mBubblePaddingTop;
        float f2 = rectF.right;
        int i3 = this.mBubbleSize;
        rectF.right = f2 + (r3 - i3);
        rectF.bottom -= (i2 + f) + i3;
        return rectF;
    }

    public final void getBubbleBarExpandedViewBounds(Rect rect, boolean z, boolean z2) {
        int i = this.mExpandedViewPadding;
        int i2 = z2 ? this.mOverflowWidth : this.mExpandedViewBubbleBarWidth;
        int expandedViewHeightForBubbleBar = getExpandedViewHeightForBubbleBar(z2);
        rect.set(0, 0, i2, expandedViewHeightForBubbleBar);
        rect.offsetTo(z ? this.mInsets.left + i : (this.mPositionRect.right - i2) - i, (this.mBubbleBarTopOnScreen - this.mExpandedViewPadding) - expandedViewHeightForBubbleBar);
    }

    public final Rect getBubbleBarExpandedViewDropTargetBounds(boolean z) {
        Rect rect = new Rect();
        getBubbleBarExpandedViewBounds(rect, z, false);
        rect.inset(z ? 0 : this.mBarExpViewDropTargetPaddingHorizontal, this.mBarExpViewDropTargetPaddingTop, z ? this.mBarExpViewDropTargetPaddingHorizontal : 0, this.mBarExpViewDropTargetPaddingBottom);
        return rect;
    }

    public final float getBubbleRowStart(BubbleStackView.StackViewState stackViewState) {
        int i = stackViewState.numberOfBubbles;
        float f = ((i - 1) * this.mSpacingBetweenBubbles) + (this.mBubbleSize * i);
        DeviceConfig deviceConfig = this.mDeviceConfig;
        if ((!deviceConfig.isLargeScreen || deviceConfig.isSmallTablet || deviceConfig.isLandscape) ? false : true) {
            float expandedViewHeightForLargeScreen = getExpandedViewHeightForLargeScreen();
            int i2 = this.mScreenRect.bottom;
            Insets insets = this.mInsets;
            return ((((i2 - Math.max(insets.bottom, insets.top)) - this.mManageButtonHeight) - this.mPointerWidth) - (expandedViewHeightForLargeScreen / 2.0f)) - (f / 2.0f);
        }
        float centerY = showBubblesVertically() ? this.mPositionRect.centerY() : this.mPositionRect.centerX();
        if (showBubblesVertically() && this.mImeVisible) {
            centerY = this.mPositionRect.centerY() - this.mPositionRect.bottom;
        }
        return centerY - (f / 2.0f);
    }

    public final PointF getDefaultStartPosition(boolean z) {
        return getStartPosition(z ? this.mDeviceConfig.isRtl : !this.mDeviceConfig.isRtl ? StackPinnedEdge.LEFT : StackPinnedEdge.RIGHT);
    }

    public final PointF getExpandedBubbleXY(int i, BubbleStackView.StackViewState stackViewState) {
        float f;
        float f2;
        int i2;
        int i3;
        boolean showBubblesVertically = showBubblesVertically();
        if (!showBubblesVertically && this.mDeviceConfig.isRtl) {
            i = (stackViewState.numberOfBubbles - 1) - i;
        }
        float f3 = (this.mBubbleSize + this.mSpacingBetweenBubbles) * i;
        float bubbleRowStart = getBubbleRowStart(stackViewState);
        if (showBubblesVertically) {
            int i4 = this.mExpandedViewLargeScreenInsetClosestEdge;
            f2 = stackViewState.numberOfBubbles == 2 ? bubbleRowStart + f3 + (this.mSpacingBetweenBubbles * 2) : (bubbleRowStart + f3) - (this.mBubbleSize / 4);
            boolean z = this.mDeviceConfig.isLargeScreen;
            int i5 = z ? (i4 - this.mExpandedViewPadding) - this.mBubbleSize : this.mPositionRect.left;
            int i6 = z ? (this.mPositionRect.right - i4) + this.mExpandedViewPadding : this.mPositionRect.right - this.mBubbleSize;
            if (QpShellRune.NOTI_BUBBLE_STYLE_TABLET) {
                i2 = ((getTabletSidePadding() + this.mPositionRect.left) - this.mBubbleSize) - this.mBubblePaddingTop;
                i3 = ((getTabletSidePadding() + this.mPositionRect.right) - this.mBubbleSize) - this.mBubblePaddingTop;
            } else {
                int i7 = -(this.mBubbleOffscreenAmount / 2);
                int i8 = i6 + i7;
                i2 = i5 - i7;
                i3 = i8;
            }
            f = stackViewState.onLeft ? i2 : i3;
        } else {
            float f4 = this.mPositionRect.top + this.mBubblePaddingTop;
            if (stackViewState.numberOfBubbles == 2) {
                bubbleRowStart = bubbleRowStart + f3 + (this.mBubbleSize / 2);
                f3 = this.mSpacingBetweenBubbles / 2;
            }
            f = f3 + bubbleRowStart;
            f2 = f4;
        }
        if (!showBubblesVertically || !this.mImeVisible) {
            return new PointF(f, f2);
        }
        float f5 = this.mPositionRect.top + this.mExpandedViewPadding;
        if (showBubblesVertically()) {
            int i9 = (this.mImeVisible ? this.mImeHeight : 0) + this.mInsets.bottom;
            int i10 = this.mSpacingBetweenBubbles;
            float f6 = this.mScreenRect.bottom - ((i10 * 2) + i9);
            int i11 = stackViewState.numberOfBubbles;
            float f7 = ((i11 - 1) * i10) + (this.mBubbleSize * i11);
            float bubbleRowStart2 = getBubbleRowStart(stackViewState);
            float f8 = f7 + bubbleRowStart2;
            if (f8 > f6) {
                float f9 = bubbleRowStart2 - (f8 - f6);
                float max = Math.max(f9, f5);
                if (f9 < f5) {
                    int i12 = stackViewState.numberOfBubbles;
                    float f10 = ((i12 - 2) * this.mSpacingBetweenBubbles) + ((i12 - 1) * this.mBubbleSize);
                    float centerY = showBubblesVertically() ? this.mPositionRect.centerY() : this.mPositionRect.centerX();
                    float f11 = f10 / 2.0f;
                    bubbleRowStart2 = (centerY - f11) - ((centerY + f11) - f6);
                } else {
                    bubbleRowStart2 = max;
                }
            }
            int i13 = stackViewState.selectedIndex;
            int i14 = this.mBubbleSize + this.mSpacingBetweenBubbles;
            if ((i13 * i14) + bubbleRowStart2 >= f5) {
                f5 = bubbleRowStart2;
            }
            f5 += i14 * i;
        }
        return new PointF(f, f5);
    }

    public final int[] getExpandedViewContainerPadding(boolean z, boolean z2) {
        int i = this.mPointerHeight - this.mPointerOverlap;
        int width = (z2 && this.mDeviceConfig.isLargeScreen) ? (this.mScreenRect.width() - this.mExpandedViewLargeScreenInsetClosestEdge) - this.mOverflowWidth : this.mExpandedViewLargeScreenInsetFurthestEdge;
        int[] iArr = new int[4];
        if (this.mDeviceConfig.isLargeScreen) {
            iArr[0] = z ? this.mExpandedViewLargeScreenInsetClosestEdge - i : width;
            iArr[1] = 0;
            if (!z) {
                width = this.mExpandedViewLargeScreenInsetClosestEdge - i;
            }
            iArr[2] = width;
            iArr[3] = z2 ? this.mExpandedViewPadding : 0;
            return iArr;
        }
        Insets insets = this.mInsets;
        int i2 = insets.left;
        int i3 = this.mExpandedViewPadding;
        int i4 = i2 + i3;
        int i5 = insets.right + i3;
        if (showBubblesVertically()) {
            if (z) {
                i4 += this.mBubbleSize - i;
            } else {
                i5 += this.mBubbleSize - i;
            }
        }
        iArr[0] = i4;
        iArr[1] = showBubblesVertically() ? 0 : this.mPointerMargin;
        iArr[2] = i5;
        iArr[3] = 0;
        if (QpShellRune.NOTI_BUBBLE_STYLE_TABLET) {
            iArr[0] = getTabletSidePadding();
            iArr[1] = this.mPointerMargin;
            iArr[2] = getTabletSidePadding();
            iArr[3] = z2 ? this.mExpandedViewPadding : 0;
        }
        return iArr;
    }

    public final float getExpandedViewHeight(BubbleViewProvider bubbleViewProvider) {
        float f;
        int i = 0;
        boolean z = bubbleViewProvider == null || "Overflow".equals(bubbleViewProvider.getKey());
        if (z && showBubblesVertically() && !this.mDeviceConfig.isLargeScreen && !QpShellRune.NOTI_BUBBLE_STYLE_TABLET) {
            return this.mBubbleOverflowLandscapeHeight;
        }
        if (z) {
            f = this.mOverflowHeight;
        } else {
            Bubble bubble = (Bubble) bubbleViewProvider;
            Context context = this.mContext;
            int i2 = bubble.mDesiredHeightResId;
            if (i2 != 0) {
                String str = bubble.mPackageName;
                int identifier = bubble.mUser.getIdentifier();
                if (str != null) {
                    if (identifier == -1) {
                        identifier = 0;
                    }
                    try {
                        i = context.createContextAsUser(UserHandle.of(identifier), 0).getPackageManager().getResourcesForApplication(str).getDimensionPixelSize(i2);
                    } catch (PackageManager.NameNotFoundException unused) {
                    } catch (Resources.NotFoundException e) {
                        Log.e("Bubble", "Couldn't find desired height res id", e);
                    }
                }
                f = i;
            } else {
                f = bubble.mDesiredHeight * context.getResources().getDisplayMetrics().density;
            }
        }
        float max = Math.max(f, this.mExpandedViewMinHeight);
        if (max > getMaxExpandedViewHeight(z)) {
            return -1.0f;
        }
        return max;
    }

    public final int getExpandedViewHeightForBubbleBar(boolean z) {
        if (z) {
            return this.mOverflowHeight;
        }
        return ((Math.min(this.mScreenRect.width(), this.mScreenRect.height()) - (this.mScreenRect.height() - (this.mBubbleBarTopOnScreen - this.mExpandedViewPadding))) - this.mInsets.top) - this.mExpandedViewPadding;
    }

    public final int getExpandedViewHeightForLargeScreen() {
        Insets insets = this.mInsets;
        return (((Math.min(this.mScreenRect.height(), this.mScreenRect.width()) - (Math.max(insets.top, insets.bottom) * 2)) - this.mManageButtonHeight) - this.mPointerWidth) - (this.mExpandedViewPadding * 2);
    }

    public final float getExpandedViewY(BubbleViewProvider bubbleViewProvider, float f) {
        boolean z = bubbleViewProvider == null || "Overflow".equals(bubbleViewProvider.getKey());
        float expandedViewHeight = getExpandedViewHeight(bubbleViewProvider);
        int expandedViewYTopAligned = getExpandedViewYTopAligned();
        int i = z ? this.mBubbleOverflowMarginBottom : this.mManageButtonHeightIncludingMargins;
        DeviceConfig deviceConfig = this.mDeviceConfig;
        if (((!deviceConfig.isLargeScreen || deviceConfig.isSmallTablet || deviceConfig.isLandscape) ? false : true) && expandedViewHeight == -1.0f) {
            return ((this.mPositionRect.bottom - i) - getExpandedViewHeightForLargeScreen()) - this.mPointerWidth;
        }
        if (!showBubblesVertically() || expandedViewHeight == -1.0f) {
            return expandedViewYTopAligned;
        }
        float pointerPosition = getPointerPosition(f);
        float f2 = expandedViewHeight / 2.0f;
        float f3 = pointerPosition + f2 + i;
        float f4 = pointerPosition - f2;
        Rect rect = this.mPositionRect;
        float f5 = rect.top;
        return (f4 <= f5 || ((float) rect.bottom) <= f3) ? f4 <= f5 ? expandedViewYTopAligned : ((rect.bottom - i) - expandedViewHeight) - this.mPointerWidth : (pointerPosition - this.mPointerWidth) - f2;
    }

    public final int getExpandedViewYTopAligned() {
        int i;
        int i2;
        int i3 = this.mPositionRect.top;
        if (showBubblesVertically()) {
            i = i3 - this.mPointerWidth;
            i2 = this.mExpandedViewPadding;
        } else {
            i = i3 + this.mBubbleSize;
            i2 = this.mPointerMargin;
        }
        return i + i2;
    }

    public final int getMaxExpandedViewHeight(boolean z) {
        DeviceConfig deviceConfig = this.mDeviceConfig;
        if (deviceConfig.isLargeScreen && !deviceConfig.isSmallTablet && !z) {
            return getExpandedViewHeightForLargeScreen();
        }
        int expandedViewYTopAligned = getExpandedViewYTopAligned() - this.mInsets.top;
        int i = showBubblesVertically() ? 0 : this.mPointerHeight;
        return (((this.mPositionRect.height() - expandedViewYTopAligned) - i) - (showBubblesVertically() ? this.mPointerWidth : this.mPointerHeight + this.mPointerMargin)) - (z ? this.mExpandedViewPadding : this.mManageButtonHeightIncludingMargins);
    }

    public final float getPointerPosition(float f) {
        return showBubblesVertically() ? (this.mBubbleSize / 2.0f) + f : ((Math.round(this.mBubbleSize * 0.92f) / 2.0f) + f) - this.mPointerWidth;
    }

    public final PointF getRestingPosition() {
        PointF pointF = this.mRestingStackPosition;
        return pointF == null ? getDefaultStartPosition(false) : pointF;
    }

    public final PointF getStartPosition(StackPinnedEdge stackPinnedEdge) {
        RectF allowableStackPositionRegion = getAllowableStackPositionRegion(1);
        if (this.mDeviceConfig.isLargeScreen) {
            return new BubbleStackView.RelativeStackPosition(stackPinnedEdge == StackPinnedEdge.LEFT, ((this.mScreenRect.height() / 2.0f) - (this.mBubbleSize / 2.0f)) / this.mScreenRect.height()).getAbsolutePositionInRegion(allowableStackPositionRegion);
        }
        return new BubbleStackView.RelativeStackPosition(stackPinnedEdge == StackPinnedEdge.LEFT, this.mContext.getResources().getDimensionPixelOffset(R.dimen.bubble_stack_starting_offset_y) / this.mPositionRect.height()).getAbsolutePositionInRegion(allowableStackPositionRegion);
    }

    public final int getTabletSidePadding() {
        return (this.mPositionRect.width() - ((int) (r4.width() * 0.85d))) / 2;
    }

    public final boolean isBubbleBarOnLeft() {
        return this.mBubbleBarLocation.isOnLeft(this.mDeviceConfig.isRtl);
    }

    public final boolean isStackOnLeft(PointF pointF) {
        if (pointF == null) {
            pointF = getRestingPosition();
        }
        return (this.mBubbleSize / 2) + ((int) pointF.x) < this.mScreenRect.width() / 2;
    }

    public final void setImeVisible(int i, boolean z) {
        this.mImeVisible = z;
        this.mImeHeight = z ? Math.max(i - this.mInsets.bottom, 0) : 0;
    }

    public final void setRestingPosition(PointF pointF) {
        PointF pointF2 = this.mRestingStackPosition;
        if (pointF2 == null) {
            this.mRestingStackPosition = new PointF(pointF);
        } else {
            pointF2.set(pointF);
        }
    }

    public final boolean showBubblesVertically() {
        DeviceConfig deviceConfig = this.mDeviceConfig;
        return deviceConfig.isLandscape || deviceConfig.isLargeScreen;
    }

    public final void update(DeviceConfig deviceConfig) {
        this.mDeviceConfig = deviceConfig;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_BUBBLES_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_BUBBLES, -5246433577405958017L, EnterpriseContainerCallback.CONTAINER_PACKAGE_UNINSTALL_FAILURE, Long.valueOf(this.mRotation), String.valueOf(deviceConfig.insets), Boolean.valueOf(deviceConfig.isLargeScreen), Boolean.valueOf(deviceConfig.isSmallTablet), Boolean.FALSE, String.valueOf(deviceConfig.windowBounds));
        }
        updateInternal(this.mRotation, deviceConfig.insets, deviceConfig.windowBounds);
    }

    public void updateInternal(int i, Insets insets, Rect rect) {
        Rect rect2;
        BubbleStackView.RelativeStackPosition relativeStackPosition = (this.mRestingStackPosition == null || (rect2 = this.mScreenRect) == null || rect2.equals(rect)) ? null : new BubbleStackView.RelativeStackPosition(getRestingPosition(), getAllowableStackPositionRegion(1));
        this.mRotation = i;
        this.mInsets = insets;
        this.mScreenRect = new Rect(rect);
        Rect rect3 = new Rect(rect);
        this.mPositionRect = rect3;
        int i2 = rect3.left;
        Insets insets2 = this.mInsets;
        rect3.left = i2 + insets2.left;
        rect3.top += insets2.top;
        rect3.right -= insets2.right;
        rect3.bottom -= insets2.bottom;
        Resources resources = this.mContext.getResources();
        this.mBubbleSize = resources.getDimensionPixelSize(R.dimen.bubble_size);
        this.mSpacingBetweenBubbles = resources.getDimensionPixelSize(R.dimen.bubble_spacing);
        this.mDefaultMaxBubbles = resources.getInteger(R.integer.bubbles_max_rendered);
        this.mExpandedViewPadding = resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_padding);
        this.mBubblePaddingTop = resources.getDimensionPixelSize(R.dimen.bubble_padding_top);
        this.mBubbleOffscreenAmount = resources.getDimensionPixelSize(R.dimen.bubble_stack_offscreen);
        this.mStackOffset = resources.getDimensionPixelSize(R.dimen.bubble_stack_offset);
        this.mBubbleElevation = resources.getDimensionPixelSize(R.dimen.bubble_elevation);
        this.mExpandedViewBubbleBarWidth = Math.min(resources.getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_width), this.mPositionRect.width() - (this.mExpandedViewPadding * 2));
        this.mBarExpViewDropTargetPaddingTop = resources.getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_drop_target_padding_top);
        this.mBarExpViewDropTargetPaddingBottom = resources.getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_drop_target_padding_bottom);
        this.mBarExpViewDropTargetPaddingHorizontal = resources.getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_drop_target_padding_horizontal);
        resources.getDimensionPixelSize(R.dimen.bubble_bar_drop_target_width);
        resources.getDimensionPixelSize(R.dimen.bubble_bar_drop_target_height);
        if (this.mDeviceConfig.isSmallTablet) {
            this.mExpandedViewLargeScreenWidth = (int) (rect.width() * 0.72f);
        } else {
            this.mExpandedViewLargeScreenWidth = Math.min(resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_largescreen_width), rect.width() - (resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_largescreen_landscape_padding) * 2));
        }
        DeviceConfig deviceConfig = this.mDeviceConfig;
        if (!deviceConfig.isLargeScreen) {
            int i3 = this.mExpandedViewPadding;
            this.mExpandedViewLargeScreenInsetClosestEdge = i3;
            this.mExpandedViewLargeScreenInsetFurthestEdge = i3;
        } else if (deviceConfig.isSmallTablet) {
            int width = (rect.width() - this.mExpandedViewLargeScreenWidth) / 2;
            this.mExpandedViewLargeScreenInsetClosestEdge = width;
            this.mExpandedViewLargeScreenInsetFurthestEdge = width;
        } else {
            this.mExpandedViewLargeScreenInsetClosestEdge = resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_largescreen_landscape_padding);
            this.mExpandedViewLargeScreenInsetFurthestEdge = (rect.width() - this.mExpandedViewLargeScreenInsetClosestEdge) - this.mExpandedViewLargeScreenWidth;
        }
        this.mOverflowWidth = resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_overflow_width);
        this.mPointerWidth = resources.getDimensionPixelSize(R.dimen.bubble_pointer_width);
        this.mPointerHeight = resources.getDimensionPixelSize(R.dimen.bubble_pointer_height);
        this.mPointerMargin = resources.getDimensionPixelSize(R.dimen.bubble_pointer_margin);
        this.mPointerOverlap = resources.getDimensionPixelSize(R.dimen.bubble_pointer_overlap);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.bubble_manage_button_height);
        this.mManageButtonHeight = dimensionPixelSize;
        this.mManageButtonHeightIncludingMargins = (resources.getDimensionPixelSize(R.dimen.bubble_manage_button_margin) * 2) + dimensionPixelSize;
        this.mExpandedViewMinHeight = resources.getDimensionPixelSize(R.dimen.bubble_expanded_default_height);
        this.mOverflowHeight = resources.getDimensionPixelSize(R.dimen.bubble_overflow_height);
        this.mBubbleOverflowLandscapeHeight = resources.getDimensionPixelSize(R.dimen.bubble_overflow_landscape_height);
        this.mBubbleOverflowMarginBottom = resources.getDimensionPixelSize(R.dimen.bubble_overflow_margin_bottom);
        this.mMinimumFlyoutWidthLargeScreen = resources.getDimensionPixelSize(R.dimen.bubbles_flyout_min_width_large_screen);
        this.mMaxBubbles = this.mDefaultMaxBubbles;
        if (relativeStackPosition != null) {
            this.mRestingStackPosition = relativeStackPosition.getAbsolutePositionInRegion(getAllowableStackPositionRegion(1));
        }
    }

    public BubblePositioner(Context context, DeviceConfig deviceConfig) {
        this.mRotation = 0;
        this.mBubbleBarLocation = BubbleBarLocation.DEFAULT;
        this.mContext = context;
        this.mDeviceConfig = deviceConfig;
        update(deviceConfig);
    }
}
