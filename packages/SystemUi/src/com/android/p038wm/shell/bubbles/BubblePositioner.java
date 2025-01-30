package com.android.p038wm.shell.bubbles;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Insets;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.UserHandle;
import android.util.Log;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;
import androidx.appcompat.widget.MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.QpShellRune;
import com.android.p038wm.shell.bubbles.BubbleStackView;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubblePositioner {
    public int mBubbleBarSize;
    public int mBubbleOffscreenAmount;
    public int mBubblePaddingTop;
    public int mBubbleSize;
    public final Context mContext;
    public int mDefaultMaxBubbles;
    public int mExpandedViewLargeScreenWidth;
    public int mExpandedViewMinHeight;
    public int mExpandedViewPadding;
    public int mImeHeight;
    public boolean mImeVisible;
    public Insets mInsets;
    public int mManageButtonHeight;
    public int mMaxBubbles;
    public int mOverflowHeight;
    public int mPointerHeight;
    public int mPointerMargin;
    public int mPointerOverlap;
    public int mPointerWidth;
    public Rect mPositionRect;
    public PointF mRestingStackPosition;
    public Rect mScreenRect;
    public boolean mShowingInBubbleBar;
    public int mSpacingBetweenBubbles;
    public int mStackOffset;
    public final WindowManager mWindowManager;
    public int mRotation = 0;
    public final int[] mPaddings = new int[4];

    public BubblePositioner(Context context, WindowManager windowManager) {
        new PointF();
        this.mContext = context;
        this.mWindowManager = windowManager;
        update();
    }

    public final RectF getAllowableStackPositionRegion(int i) {
        RectF rectF = new RectF(this.mPositionRect);
        int i2 = this.mImeVisible ? this.mImeHeight : 0;
        int i3 = i > 1 ? this.mBubblePaddingTop + this.mStackOffset : this.mBubblePaddingTop;
        rectF.left = rectF.left - (-this.mBubbleOffscreenAmount);
        rectF.top += this.mBubblePaddingTop;
        float f = rectF.right;
        int i4 = this.mBubbleSize;
        rectF.right = f + (r3 - i4);
        rectF.bottom -= (i2 + i3) + i4;
        return rectF;
    }

    public final PointF getDefaultStartPosition() {
        BubbleStackView.RelativeStackPosition relativeStackPosition = new BubbleStackView.RelativeStackPosition(MenuPopupWindow$MenuDropDownListView$$ExternalSyntheticOutline0.m11m(this.mContext) != 1, r0.getResources().getDimensionPixelOffset(R.dimen.bubble_stack_starting_offset_y) / this.mPositionRect.height());
        RectF allowableStackPositionRegion = getAllowableStackPositionRegion(1);
        return new PointF(relativeStackPosition.mOnLeft ? allowableStackPositionRegion.left : allowableStackPositionRegion.right, (allowableStackPositionRegion.height() * relativeStackPosition.mVerticalOffsetPercent) + allowableStackPositionRegion.top);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x004b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final PointF getExpandedBubbleXY(int i, BubbleStackView.StackViewState stackViewState) {
        int i2;
        int i3;
        int i4;
        float f;
        float f2;
        float f3;
        int i5;
        int i6;
        int i7 = this.mBubbleSize;
        int i8 = i7 / 2;
        int i9 = i7 / 4;
        if (showBubblesVertically()) {
            if (stackViewState.numberOfBubbles != 2) {
                f = ((this.mBubbleSize + this.mSpacingBetweenBubbles) * i) - i9;
                int i10 = stackViewState.numberOfBubbles;
                float centerY = (showBubblesVertically() ? this.mPositionRect.centerY() : this.mPositionRect.centerX()) - ((((i10 - 1) * this.mSpacingBetweenBubbles) + (this.mBubbleSize * i10)) / 2.0f);
                if (showBubblesVertically()) {
                    f3 = centerY + f;
                    boolean z = QpShellRune.NOTI_BUBBLE_STYLE_TABLET;
                    int tabletSidePadding = z ? (getTabletSidePadding() + this.mPositionRect.left) - this.mBubbleSize : this.mPositionRect.left;
                    if (z) {
                        i5 = this.mPositionRect.right;
                        i6 = getTabletSidePadding();
                    } else {
                        i5 = this.mPositionRect.right;
                        i6 = this.mBubbleSize;
                    }
                    int i11 = i5 - i6;
                    if (!z) {
                        int i12 = -this.mBubbleOffscreenAmount;
                        tabletSidePadding -= i12;
                        i11 += i12;
                    }
                    f2 = stackViewState.onLeft ? tabletSidePadding : i11;
                } else {
                    f2 = f + centerY;
                    f3 = this.mPositionRect.top + this.mBubblePaddingTop;
                }
                if (showBubblesVertically() || !this.mImeVisible) {
                    return new PointF(f2, f3);
                }
                float f4 = this.mPositionRect.top + this.mExpandedViewPadding;
                if (showBubblesVertically()) {
                    int i13 = (this.mImeVisible ? this.mImeHeight : 0) + this.mInsets.bottom;
                    int i14 = this.mSpacingBetweenBubbles;
                    float f5 = this.mScreenRect.bottom - ((i14 * 2) + i13);
                    int i15 = stackViewState.numberOfBubbles;
                    float f6 = ((i15 - 1) * i14) + (this.mBubbleSize * i15);
                    float centerY2 = this.mPositionRect.centerY();
                    float f7 = f6 / 2.0f;
                    float f8 = centerY2 + f7;
                    float f9 = centerY2 - f7;
                    if (f8 > f5) {
                        float f10 = f9 - (f8 - f5);
                        float max = Math.max(f10, f4);
                        if (f10 < f4) {
                            int i16 = stackViewState.numberOfBubbles - 1;
                            float f11 = ((i16 - 1) * this.mSpacingBetweenBubbles) + (this.mBubbleSize * i16);
                            float centerY3 = showBubblesVertically() ? this.mPositionRect.centerY() : this.mPositionRect.centerX();
                            float f12 = f11 / 2.0f;
                            f9 = (centerY3 - f12) - ((centerY3 + f12) - f5);
                        } else {
                            f9 = max;
                        }
                    }
                    int i17 = stackViewState.selectedIndex;
                    int i18 = this.mBubbleSize + this.mSpacingBetweenBubbles;
                    if ((i17 * i18) + f9 >= f4) {
                        f4 = f9;
                    }
                    f4 += i18 * i;
                }
                return new PointF(f2, f4);
            }
            i3 = this.mBubbleSize;
            i4 = this.mSpacingBetweenBubbles;
        } else {
            if (stackViewState.numberOfBubbles != 2) {
                i2 = (this.mBubbleSize + this.mSpacingBetweenBubbles) * i;
                f = i2;
                int i102 = stackViewState.numberOfBubbles;
                float centerY4 = (showBubblesVertically() ? this.mPositionRect.centerY() : this.mPositionRect.centerX()) - ((((i102 - 1) * this.mSpacingBetweenBubbles) + (this.mBubbleSize * i102)) / 2.0f);
                if (showBubblesVertically()) {
                }
                if (showBubblesVertically()) {
                }
                return new PointF(f2, f3);
            }
            i3 = this.mBubbleSize;
            i4 = this.mSpacingBetweenBubbles;
        }
        i2 = ((i3 + i4) * i) + i8;
        f = i2;
        int i1022 = stackViewState.numberOfBubbles;
        float centerY42 = (showBubblesVertically() ? this.mPositionRect.centerY() : this.mPositionRect.centerX()) - ((((i1022 - 1) * this.mSpacingBetweenBubbles) + (this.mBubbleSize * i1022)) / 2.0f);
        if (showBubblesVertically()) {
        }
        if (showBubblesVertically()) {
        }
        return new PointF(f2, f3);
    }

    public final int[] getExpandedViewContainerPadding(boolean z) {
        int i = this.mPointerHeight - this.mPointerOverlap;
        Insets insets = this.mInsets;
        int i2 = insets.left;
        int i3 = this.mExpandedViewPadding;
        int i4 = i2 + i3;
        int i5 = insets.right + i3;
        if (showBubblesVertically()) {
            if (z) {
                i4 += this.mBubbleSize - i;
                i5 = (int) (i5 + 0.0f);
            } else {
                i5 += this.mBubbleSize - i;
                i4 = (int) (i4 + 0.0f);
            }
        }
        int[] iArr = this.mPaddings;
        iArr[0] = i4;
        iArr[1] = showBubblesVertically() ? 0 : this.mPointerMargin;
        iArr[2] = i5;
        iArr[3] = 0;
        if (QpShellRune.NOTI_BUBBLE_STYLE_TABLET) {
            int tabletSidePadding = getTabletSidePadding();
            iArr[2] = tabletSidePadding;
            iArr[0] = tabletSidePadding;
        }
        return iArr;
    }

    public final float getExpandedViewHeight(BubbleViewProvider bubbleViewProvider) {
        float f;
        int i = 0;
        boolean z = bubbleViewProvider == null || "Overflow".equals(bubbleViewProvider.getKey());
        if (z && showBubblesVertically()) {
            return -1.0f;
        }
        if (z) {
            f = this.mOverflowHeight;
        } else {
            Bubble bubble = (Bubble) bubbleViewProvider;
            int i2 = bubble.mDesiredHeightResId;
            boolean z2 = i2 != 0;
            Context context = this.mContext;
            if (z2) {
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
        if (max > getMaxExpandedViewHeight()) {
            return -1.0f;
        }
        return max;
    }

    public final float getExpandedViewY(BubbleViewProvider bubbleViewProvider, float f) {
        boolean z = bubbleViewProvider == null || "Overflow".equals(bubbleViewProvider.getKey());
        float expandedViewHeight = getExpandedViewHeight(bubbleViewProvider);
        float expandedViewYTopAligned = getExpandedViewYTopAligned();
        if (!showBubblesVertically() || expandedViewHeight == -1.0f) {
            return expandedViewYTopAligned;
        }
        int i = z ? this.mExpandedViewPadding : this.mManageButtonHeight;
        float pointerPosition = getPointerPosition(f);
        float f2 = expandedViewHeight / 2.0f;
        float f3 = pointerPosition + f2 + i;
        float f4 = pointerPosition - f2;
        Rect rect = this.mPositionRect;
        int i2 = rect.top;
        return (f4 <= ((float) i2) || ((float) rect.bottom) <= f3) ? f4 <= ((float) i2) ? expandedViewYTopAligned : ((rect.bottom - i) - expandedViewHeight) - this.mPointerWidth : (pointerPosition - this.mPointerWidth) - f2;
    }

    public final float getExpandedViewYTopAligned() {
        int i = this.mPositionRect.top;
        if (showBubblesVertically()) {
            return (((-this.mPointerWidth) + this.mExpandedViewPadding) * (QpShellRune.NOTI_BUBBLE_STYLE_TABLET ? -1 : 1)) + i;
        }
        return i + this.mBubbleSize + this.mPointerMargin;
    }

    public final int getMaxExpandedViewHeight() {
        int expandedViewYTopAligned = ((int) getExpandedViewYTopAligned()) - 0;
        return (((this.mPositionRect.height() - expandedViewYTopAligned) - (showBubblesVertically() ? 0 : this.mPointerHeight)) - (showBubblesVertically() ? this.mPointerWidth : this.mPointerHeight + this.mPointerMargin)) - this.mExpandedViewPadding;
    }

    public final float getPointerPosition(float f) {
        int i = this.mBubbleSize;
        return showBubblesVertically() ? (this.mBubbleSize / 2.0f) + f : ((((int) Math.round(Math.sqrt((((i * i) * 0.6597222f) * 4.0f) / 3.141592653589793d))) / 2.0f) + f) - this.mPointerWidth;
    }

    public final PointF getRestingPosition() {
        PointF pointF = this.mRestingStackPosition;
        return pointF == null ? getDefaultStartPosition() : pointF;
    }

    public final int getTabletSidePadding() {
        return (this.mPositionRect.width() - ((int) (r4.width() * 0.85d))) / 2;
    }

    public final boolean isLandscape() {
        return this.mContext.getResources().getConfiguration().orientation == 2;
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
        return isLandscape();
    }

    public final void update() {
        WindowMetrics currentWindowMetrics = this.mWindowManager.getCurrentWindowMetrics();
        if (currentWindowMetrics == null) {
            return;
        }
        Insets insetsIgnoringVisibility = currentWindowMetrics.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars() | WindowInsets.Type.statusBars() | WindowInsets.Type.displayCutout());
        Rect bounds = currentWindowMetrics.getBounds();
        this.mContext.getResources().getConfiguration();
        Log.w("Bubbles", "update positioner: rotation: " + this.mRotation + " insets: " + insetsIgnoringVisibility + " isLargeScreen: false isSmallTablet: false showingInBubbleBar: " + this.mShowingInBubbleBar + " bounds: " + bounds);
        updateInternal(this.mRotation, insetsIgnoringVisibility, bounds);
    }

    public void updateInternal(int i, Insets insets, Rect rect) {
        this.mRotation = i;
        this.mInsets = insets;
        this.mScreenRect = new Rect(rect);
        Rect rect2 = new Rect(rect);
        this.mPositionRect = rect2;
        int i2 = rect2.left;
        Insets insets2 = this.mInsets;
        rect2.left = i2 + insets2.left;
        rect2.top += insets2.top;
        rect2.right -= insets2.right;
        rect2.bottom -= insets2.bottom;
        Resources resources = this.mContext.getResources();
        this.mBubbleSize = resources.getDimensionPixelSize(R.dimen.bubble_size);
        this.mSpacingBetweenBubbles = resources.getDimensionPixelSize(R.dimen.bubble_spacing);
        this.mDefaultMaxBubbles = resources.getInteger(R.integer.bubbles_max_rendered);
        int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_padding);
        this.mExpandedViewPadding = dimensionPixelSize;
        int i3 = dimensionPixelSize / 2;
        this.mBubblePaddingTop = resources.getDimensionPixelSize(R.dimen.bubble_padding_top);
        this.mBubbleOffscreenAmount = resources.getDimensionPixelSize(R.dimen.bubble_stack_offscreen);
        this.mStackOffset = resources.getDimensionPixelSize(R.dimen.bubble_stack_offset);
        this.mBubbleBarSize = resources.getDimensionPixelSize(R.dimen.bubblebar_size);
        if (this.mShowingInBubbleBar) {
            this.mExpandedViewLargeScreenWidth = (int) (isLandscape() ? rect.width() * 0.4f : rect.width() * 0.7f);
        } else {
            this.mExpandedViewLargeScreenWidth = (int) (isLandscape() ? rect.width() * 0.48f : rect.width() * 0.7f);
        }
        resources.getDimensionPixelSize(R.dimen.bubble_expanded_view_overflow_width);
        this.mPointerWidth = resources.getDimensionPixelSize(R.dimen.bubble_pointer_width);
        this.mPointerHeight = resources.getDimensionPixelSize(R.dimen.bubble_pointer_height);
        this.mPointerMargin = resources.getDimensionPixelSize(R.dimen.bubble_pointer_margin);
        this.mPointerOverlap = resources.getDimensionPixelSize(R.dimen.bubble_pointer_overlap);
        this.mManageButtonHeight = resources.getDimensionPixelSize(R.dimen.bubble_manage_button_total_height);
        this.mExpandedViewMinHeight = resources.getDimensionPixelSize(R.dimen.bubble_expanded_default_height);
        this.mOverflowHeight = resources.getDimensionPixelSize(R.dimen.bubble_overflow_height);
        resources.getDimensionPixelSize(R.dimen.bubbles_flyout_min_width_large_screen);
        this.mMaxBubbles = this.mDefaultMaxBubbles;
    }
}
