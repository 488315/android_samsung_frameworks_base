package com.android.p038wm.shell.naturalswitching;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.FrameLayout;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.constraintlayout.core.widgets.analyzer.DependencyGraph$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.android.systemui.R;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class NonDragTargetView extends FrameLayout {
    public final Rect mContainingBounds;
    public int mCornerRadius;
    public View mDimView;
    public final Rect mDisplayBounds;
    public int mDividerSize;
    public final Rect mDragTargetBounds;
    public int mDragTargetWindowingMode;
    public int mDropSide;
    public NonDragTarget mDropTarget;
    public int mHalfTarget;
    public boolean mIsFloatingDragTarget;
    public boolean mIsInitialExpanded;
    public ViewGroup mMainView;
    public int mNaturalSwitchingMode;
    public final SparseArray mNonTargets;
    public Runnable mOnDrawCallback;
    public final NonDragTargetView$$ExternalSyntheticLambda0 mOnDrawListener;
    public int mPushRegion;
    public final SparseArray mPushRegions;
    public boolean mPushed;
    public final ArraySet mPushedNonTargets;
    public int mQuarterTarget;
    public int mScaleDeltaSize;
    public NonDragTarget mShrunkTarget;
    public SplitScreenController mSplitScreenController;
    public final Rect mStableRect;
    public NonDragTarget mSwapTarget;
    public TaskVisibility mTaskVisibility;
    public final Rect mTmpRect;
    public WindowManager mWm;

    /* JADX WARN: Type inference failed for: r2v3, types: [com.android.wm.shell.naturalswitching.NonDragTargetView$$ExternalSyntheticLambda0] */
    public NonDragTargetView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDropSide = 1;
        this.mIsInitialExpanded = false;
        this.mQuarterTarget = 0;
        this.mHalfTarget = 0;
        this.mContainingBounds = new Rect();
        this.mStableRect = new Rect();
        this.mDisplayBounds = new Rect();
        this.mDragTargetBounds = new Rect();
        this.mTmpRect = new Rect();
        this.mDropTarget = null;
        this.mShrunkTarget = null;
        this.mSwapTarget = null;
        this.mPushRegions = new SparseArray();
        this.mPushedNonTargets = new ArraySet();
        this.mPushed = false;
        this.mPushRegion = 0;
        this.mOnDrawCallback = null;
        this.mOnDrawListener = new ViewTreeObserver.OnDrawListener() { // from class: com.android.wm.shell.naturalswitching.NonDragTargetView$$ExternalSyntheticLambda0
            @Override // android.view.ViewTreeObserver.OnDrawListener
            public final void onDraw() {
                NonDragTargetView nonDragTargetView = NonDragTargetView.this;
                Runnable runnable = nonDragTargetView.mOnDrawCallback;
                if (runnable != null) {
                    runnable.run();
                    nonDragTargetView.mOnDrawCallback = null;
                }
            }
        };
        this.mNonTargets = new SparseArray();
    }

    public final NonDragTarget createNonDragTarget() {
        NonDragTarget nonDragTarget = (NonDragTarget) LayoutInflater.from(((FrameLayout) this).mContext).inflate(R.layout.ns_non_drag_target, (ViewGroup) null);
        this.mMainView.addView(nonDragTarget);
        return nonDragTarget;
    }

    public final Rect getCenterFreeformBounds() {
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.ns_drop_freeform_width);
        int dimensionPixelSize2 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.ns_drop_freeform_height);
        Rect rect = new Rect();
        int width = ((this.mDisplayBounds.width() - dimensionPixelSize) / 2) + rect.left;
        int height = (this.mDisplayBounds.height() - dimensionPixelSize2) / 2;
        rect.set(width, height, dimensionPixelSize + width, dimensionPixelSize2 + height);
        return rect;
    }

    public final ArrayList getPolygonTouchRegion(int i, Rect rect) {
        if (!CoreRune.MW_NATURAL_SWITCHING_PIP || this.mDragTargetWindowingMode != 2) {
            Rect centerFreeformBounds = getCenterFreeformBounds();
            ArrayList arrayList = new ArrayList();
            if (i == 16) {
                arrayList.add(new PointF(rect.left, rect.top));
                arrayList.add(new PointF(rect.right, rect.top));
                arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
                arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
            } else if (i == 32) {
                arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
                arrayList.add(new PointF(rect.right, rect.top));
                arrayList.add(new PointF(rect.right, rect.bottom));
                arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.bottom));
            } else if (i == 8) {
                arrayList.add(new PointF(rect.left, rect.top));
                arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
                arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.bottom));
                arrayList.add(new PointF(rect.left, rect.bottom));
            } else if (i == 64) {
                arrayList.add(new PointF(centerFreeformBounds.left, rect.top));
                arrayList.add(new PointF(centerFreeformBounds.right, rect.top));
                arrayList.add(new PointF(rect.right, rect.bottom));
                arrayList.add(new PointF(rect.left, rect.bottom));
            }
            return arrayList;
        }
        ArrayList arrayList2 = new ArrayList();
        PointF pointF = new PointF(this.mDisplayBounds.width() / 2, this.mDisplayBounds.height() / 2);
        if (i == 16) {
            arrayList2.add(new PointF(rect.left, rect.top));
            arrayList2.add(new PointF(rect.right, rect.top));
            arrayList2.add(pointF);
            arrayList2.add(pointF);
        } else if (i == 32) {
            arrayList2.add(pointF);
            arrayList2.add(new PointF(rect.right, rect.top));
            arrayList2.add(new PointF(rect.right, rect.bottom));
            arrayList2.add(pointF);
        } else if (i == 8) {
            arrayList2.add(new PointF(rect.left, rect.top));
            arrayList2.add(pointF);
            arrayList2.add(pointF);
            arrayList2.add(new PointF(rect.left, rect.bottom));
        } else if (i == 64) {
            arrayList2.add(pointF);
            arrayList2.add(pointF);
            arrayList2.add(new PointF(rect.right, rect.bottom));
            arrayList2.add(new PointF(rect.left, rect.bottom));
        }
        return arrayList2;
    }

    public final int getReverseWindowingMode(int i, boolean z) {
        if (this.mTaskVisibility.isTwoUp()) {
            if (i == 3) {
                return 4;
            }
            return i == 4 ? 3 : 0;
        }
        if (this.mTaskVisibility.isMultiSplit() && isQuarter(i)) {
            return z ? this.mQuarterTarget : this.mHalfTarget;
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0082  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x00a3  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Rect getShrinkBounds(NonDragTarget nonDragTarget) {
        boolean z;
        int width;
        int height;
        int i;
        int i2;
        int i3;
        int i4;
        Rect rect = new Rect();
        if (nonDragTarget.mAnimator != null) {
            rect.set(nonDragTarget.mEndBounds);
        } else {
            nonDragTarget.getCurrentLayoutBounds(rect);
        }
        boolean z2 = true;
        if (!this.mIsFloatingDragTarget && !this.mDragTargetBounds.isEmpty()) {
            if (this.mTaskVisibility.isMultiSplit()) {
                z = !isQuarter(this.mDragTargetWindowingMode);
                if (z) {
                    width = this.mDragTargetBounds.width() + this.mDividerSize;
                    height = this.mDragTargetBounds.height();
                    i = this.mDividerSize;
                } else {
                    int width2 = this.mContainingBounds.width();
                    int i5 = this.mDividerSize;
                    width = AbsActionBarView$$ExternalSyntheticOutline0.m8m(width2, i5, 2, i5);
                    int height2 = this.mContainingBounds.height();
                    i = this.mDividerSize;
                    height = (height2 - i) / 2;
                }
                int i6 = height + i;
                i2 = this.mDropSide;
                if (i2 != 2) {
                    rect.left = this.mContainingBounds.left + width;
                } else if (i2 == 4) {
                    rect.top = this.mContainingBounds.top + i6;
                } else if (i2 == 8) {
                    rect.right = this.mContainingBounds.right - width;
                } else if (i2 == 16) {
                    rect.bottom = this.mContainingBounds.bottom - i6;
                }
                return rect;
            }
            if (this.mTaskVisibility.isTwoUp() && (this.mSplitScreenController.isVerticalDivision() ? !((i3 = this.mDropSide) == 4 || i3 == 16) : !((i4 = this.mDropSide) != 4 && i4 != 16))) {
                z2 = false;
            }
        }
        z = z2;
        if (z) {
        }
        int i62 = height + i;
        i2 = this.mDropSide;
        if (i2 != 2) {
        }
        return rect;
    }

    public final NonDragTarget getTargetUnderPoint(int i, int i2) {
        if (this.mDropSide == 16) {
            i2 = this.mStableRect.bottom - 1;
        }
        int size = this.mNonTargets.size();
        while (true) {
            size--;
            if (size < 0) {
                return null;
            }
            NonDragTarget nonDragTarget = (NonDragTarget) this.mNonTargets.valueAt(size);
            if (nonDragTarget.mAnimator == null) {
                if (!this.mPushed) {
                    ArrayList arrayList = nonDragTarget.mPolygon;
                    int i3 = 0;
                    if (arrayList != null && arrayList.size() >= 4) {
                        ArrayList arrayList2 = nonDragTarget.mPolygon;
                        if (arrayList2 != null && arrayList2.size() >= 4) {
                            int size2 = nonDragTarget.mPolygon.size();
                            int i4 = size2 - 1;
                            int i5 = 0;
                            while (i3 < size2) {
                                PointF pointF = (PointF) nonDragTarget.mPolygon.get(i3);
                                PointF pointF2 = (PointF) nonDragTarget.mPolygon.get(i4);
                                float f = pointF.y;
                                float f2 = i2;
                                if ((f < f2 && pointF2.y >= f2) || (pointF2.y < f2 && f >= f2)) {
                                    float f3 = pointF.x;
                                    if (DependencyGraph$$ExternalSyntheticOutline0.m20m(pointF2.x, f3, (f2 - f) / (pointF2.y - f), f3) <= i) {
                                        i5 ^= 1;
                                    }
                                }
                                int i6 = i3;
                                i3++;
                                i4 = i6;
                            }
                            i3 = i5;
                        }
                        if (i3 != 0) {
                            return nonDragTarget;
                        }
                    } else {
                        nonDragTarget.getCurrentLayoutBounds(this.mTmpRect);
                        if (this.mTmpRect.contains(i, i2)) {
                            return nonDragTarget;
                        }
                    }
                } else if (nonDragTarget.mBaseBounds.contains(i, i2)) {
                    return nonDragTarget;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0144, code lost:
    
        if ((r11.mDragTargetWindowingMode == 1) != false) goto L55;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void init(int i, int i2, TaskVisibility taskVisibility, SplitScreenController splitScreenController) {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        this.mMainView = (ViewGroup) findViewById(R.id.main);
        this.mWm = (WindowManager) getContext().getSystemService("window");
        this.mTaskVisibility = taskVisibility;
        taskVisibility.mDisplayLayout.getStableBounds(this.mStableRect, true);
        TaskVisibility taskVisibility2 = this.mTaskVisibility;
        Rect rect = this.mDisplayBounds;
        DisplayLayout displayLayout = taskVisibility2.mDisplayLayout;
        rect.set(0, 0, displayLayout.mWidth, displayLayout.mHeight);
        if (CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT) {
            this.mContainingBounds.set(this.mStableRect);
        } else {
            this.mContainingBounds.set(this.mDisplayBounds);
        }
        this.mDragTargetWindowingMode = i;
        this.mIsFloatingDragTarget = NaturalSwitchingLayout.isFloating(i);
        this.mNaturalSwitchingMode = i2;
        this.mSplitScreenController = splitScreenController;
        View view = new View(((FrameLayout) this).mContext);
        this.mDimView = view;
        this.mMainView.addView(view);
        int color = ((FrameLayout) this).mContext.getResources().getColor(17171493, null);
        this.mDimView.setBackgroundColor(Color.argb(Math.round(Color.alpha(color) * 0.9f), Color.red(color), Color.green(color), Color.blue(color)));
        this.mDimView.setVisibility(4);
        if (this.mTaskVisibility.isMultiSplit()) {
            if (isQuarter(this.mDragTargetWindowingMode)) {
                int i3 = this.mDragTargetWindowingMode;
                if (i3 != 12) {
                    this.mQuarterTarget = 12;
                    this.mHalfTarget = i3 == 3 ? 4 : 3;
                } else if (this.mSplitScreenController.getCellHostStageType() == 0) {
                    this.mQuarterTarget = 3;
                    this.mHalfTarget = 4;
                } else {
                    this.mQuarterTarget = 4;
                    this.mHalfTarget = 3;
                }
            } else {
                this.mHalfTarget = this.mDragTargetWindowingMode;
            }
        }
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
            dimensionPixelSize = getResources().getDimensionPixelSize(android.R.dimen.notification_big_picture_max_width_low_ram);
            dimensionPixelSize2 = CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? getResources().getDimensionPixelSize(android.R.dimen.notification_big_picture_max_width) : getResources().getDimensionPixelSize(android.R.dimen.notification_big_picture_max_height_low_ram);
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(android.R.dimen.dropdownitem_text_padding_left);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(android.R.dimen.dropdownitem_icon_width);
        }
        this.mDividerSize = dimensionPixelSize - (dimensionPixelSize2 * 2);
        this.mScaleDeltaSize = getResources().getDimensionPixelSize(R.dimen.natural_switching_scale_delta);
        this.mCornerRadius = getResources().getDimensionPixelOffset(android.R.dimen.text_view_start_margin);
        if (!this.mTaskVisibility.isTaskVisible(13)) {
            if (CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN) {
            }
            SparseArray sparseArray = this.mTaskVisibility.mRunningTaskInfo;
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) sparseArray.valueAt(size);
                int keyAt = sparseArray.keyAt(size);
                if (runningTaskInfo.isVisible() && keyAt != this.mDragTargetWindowingMode && !NaturalSwitchingLayout.isFloating(keyAt)) {
                    NonDragTarget createNonDragTarget = createNonDragTarget();
                    createNonDragTarget.init(this, runningTaskInfo.taskId, keyAt, runningTaskInfo.configuration.windowConfiguration.getBounds(), runningTaskInfo.configuration.windowConfiguration.getStagePosition());
                    if (this.mNaturalSwitchingMode == 1) {
                        createNonDragTarget.setThumbnail();
                    } else {
                        createNonDragTarget.setDropTargetView();
                    }
                    this.mNonTargets.put(keyAt, createNonDragTarget);
                }
            }
            this.mDragTargetBounds.set(this.mTaskVisibility.getTaskBounds(this.mDragTargetWindowingMode));
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(2016, 1049368, -2);
            layoutParams.privateFlags |= 16;
            layoutParams.width = this.mDisplayBounds.width();
            layoutParams.height = this.mDisplayBounds.height();
            layoutParams.y = 0;
            layoutParams.x = 0;
            layoutParams.gravity = 8388659;
            layoutParams.setFitInsetsTypes(0);
            layoutParams.setTitle("NS:NonDragTargetView");
            this.mWm.addView(this, layoutParams);
        }
        if (!CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT || MultiWindowUtils.isInSubDisplay(((FrameLayout) this).mContext)) {
            NonDragTarget createNonDragTarget2 = createNonDragTarget();
            NonDragTarget createNonDragTarget3 = createNonDragTarget();
            Rect rect2 = new Rect();
            Rect rect3 = new Rect();
            this.mSplitScreenController.getStageBounds(rect2, rect3);
            if (this.mSplitScreenController.isVerticalDivision()) {
                Rect rect4 = this.mDisplayBounds;
                int i4 = rect4.top;
                rect3.top = i4;
                rect2.top = i4;
                int i5 = rect4.bottom;
                rect3.bottom = i5;
                rect2.bottom = i5;
                rect2.left = rect4.left;
                rect3.right = rect4.right;
            } else {
                Rect rect5 = this.mDisplayBounds;
                rect2.top = rect5.top;
                rect3.bottom = rect5.bottom;
            }
            createNonDragTarget2.init(this, 0, 13, rect2, 0);
            createNonDragTarget2.setDropTargetView();
            this.mNonTargets.put(3, createNonDragTarget2);
            createNonDragTarget3.init(this, 0, 13, rect3, 1);
            createNonDragTarget3.setDropTargetView();
            this.mNonTargets.put(4, createNonDragTarget3);
            this.mDragTargetBounds.set(this.mTaskVisibility.getTaskBounds(this.mDragTargetWindowingMode));
        } else {
            NonDragTarget createNonDragTarget4 = createNonDragTarget();
            NonDragTarget createNonDragTarget5 = createNonDragTarget();
            NonDragTarget createNonDragTarget6 = createNonDragTarget();
            NonDragTarget createNonDragTarget7 = createNonDragTarget();
            int width = this.mDisplayBounds.width();
            int height = this.mDisplayBounds.height();
            Rect stableInsets = this.mTaskVisibility.mDisplayLayout.stableInsets(true);
            int i6 = width - stableInsets.right;
            int i7 = stableInsets.left;
            int m8m = AbsActionBarView$$ExternalSyntheticOutline0.m8m(i6, i7, 2, i7);
            if (this.mSplitScreenController.isSplitScreenFeasible(true)) {
                int i8 = height / 2;
                Rect rect6 = new Rect(stableInsets.left, 0, width - stableInsets.right, i8);
                createNonDragTarget4.init(this, rect6, getPolygonTouchRegion(16, rect6), 16);
                createNonDragTarget4.setDropTargetView();
                this.mNonTargets.put(6, createNonDragTarget4);
                Rect rect7 = new Rect(stableInsets.left, i8, width - stableInsets.right, height);
                createNonDragTarget5.init(this, rect7, getPolygonTouchRegion(64, rect7), 64);
                createNonDragTarget5.setDropTargetView();
                this.mNonTargets.put(7, createNonDragTarget5);
            }
            if (this.mSplitScreenController.isSplitScreenFeasible(false)) {
                Rect rect8 = new Rect(stableInsets.left, 0, m8m, height);
                createNonDragTarget6.init(this, rect8, getPolygonTouchRegion(8, rect8), 8);
                createNonDragTarget6.setDropTargetView();
                this.mNonTargets.put(8, createNonDragTarget6);
                Rect rect9 = new Rect(m8m, 0, width - stableInsets.right, height);
                createNonDragTarget7.init(this, rect9, getPolygonTouchRegion(32, rect9), 32);
                createNonDragTarget7.setDropTargetView();
                this.mNonTargets.put(9, createNonDragTarget7);
            }
        }
        WindowManager.LayoutParams layoutParams2 = new WindowManager.LayoutParams(2016, 1049368, -2);
        layoutParams2.privateFlags |= 16;
        layoutParams2.width = this.mDisplayBounds.width();
        layoutParams2.height = this.mDisplayBounds.height();
        layoutParams2.y = 0;
        layoutParams2.x = 0;
        layoutParams2.gravity = 8388659;
        layoutParams2.setFitInsetsTypes(0);
        layoutParams2.setTitle("NS:NonDragTargetView");
        this.mWm.addView(this, layoutParams2);
    }

    public final void initPushRegion(Rect rect) {
        int i = (int) ((((FrameLayout) this).mContext.getResources().getDisplayMetrics().density * 45.0f) + 0.5f);
        Rect dividerBounds = (isQuarter(this.mDragTargetWindowingMode) || this.mIsFloatingDragTarget) ? this.mSplitScreenController.getDividerBounds() : this.mSplitScreenController.getCellDividerBounds();
        if (isNonTargetsHorizontal()) {
            if (this.mDragTargetBounds.width() < rect.width()) {
                rect = this.mDragTargetBounds;
            }
            int width = rect.width() / 2;
            int i2 = dividerBounds.top - i;
            int i3 = dividerBounds.bottom + i;
            this.mPushRegions.put(1, new Rect(this.mDisplayBounds.left, i2, this.mStableRect.left + width, i3));
            this.mPushRegions.put(3, new Rect(this.mStableRect.right - width, i2, this.mDisplayBounds.right, i3));
            return;
        }
        if (this.mDragTargetBounds.height() < rect.height()) {
            rect = this.mDragTargetBounds;
        }
        int height = rect.height();
        int i4 = dividerBounds.left - i;
        int i5 = dividerBounds.right + i;
        this.mPushRegions.put(2, new Rect(i4, this.mDisplayBounds.top, i5, this.mStableRect.top + 120));
        this.mPushRegions.put(4, new Rect(i4, this.mStableRect.bottom - height, i5, this.mDisplayBounds.bottom));
    }

    public final boolean isNonTargetsHorizontal() {
        if (this.mIsFloatingDragTarget) {
            return !this.mSplitScreenController.isVerticalDivision();
        }
        if (this.mTaskVisibility.isMultiSplit()) {
            return !this.mSplitScreenController.isVerticalDivision() ? isQuarter(this.mDragTargetWindowingMode) : this.mDragTargetWindowingMode == this.mHalfTarget;
        }
        return false;
    }

    public final boolean isQuarter(int i) {
        if (this.mIsFloatingDragTarget) {
            return false;
        }
        if (i == 12) {
            return true;
        }
        return (i == 3 && this.mSplitScreenController.getCellHostStageType() == 0) || (i == 4 && this.mSplitScreenController.getCellHostStageType() == 1);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
    }

    public final void startTransition(boolean z) {
        if (this.mNaturalSwitchingMode == 1) {
            for (int size = this.mNonTargets.size() - 1; size >= 0; size--) {
                final NonDragTarget nonDragTarget = (NonDragTarget) this.mNonTargets.valueAt(size);
                ValueAnimator valueAnimator = nonDragTarget.mTransitAnimator;
                if (valueAnimator != null) {
                    valueAnimator.end();
                }
                if (z) {
                    nonDragTarget.startOutlineInsetsAnimationIfNeeded();
                } else {
                    nonDragTarget.mAnimatingExit = true;
                }
                nonDragTarget.mTransitAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                final float scaleX = nonDragTarget.mView.getScaleX();
                final float scaleY = nonDragTarget.mView.getScaleY();
                float f = 1.0f;
                final float f2 = z ? nonDragTarget.mDownScale.x : 1.0f;
                if (z) {
                    f = nonDragTarget.mDownScale.y;
                }
                final float f3 = f;
                nonDragTarget.mTransitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.8
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        float floatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        float f4 = scaleX;
                        float m20m = DependencyGraph$$ExternalSyntheticOutline0.m20m(f2, f4, floatValue, f4);
                        float f5 = scaleY;
                        float m20m2 = DependencyGraph$$ExternalSyntheticOutline0.m20m(f3, f5, floatValue, f5);
                        NonDragTarget.this.mView.setScaleX(m20m);
                        NonDragTarget.this.mView.setScaleY(m20m2);
                        NonDragTarget.this.mBlurView.setScaleX(m20m);
                        NonDragTarget.this.mBlurView.setScaleY(m20m2);
                        NonDragTarget.this.mView.invalidateOutline();
                        NonDragTarget.this.mBlurView.invalidateOutline();
                    }
                });
                nonDragTarget.mTransitAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.9
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        super.onAnimationEnd(animator);
                        NonDragTarget nonDragTarget2 = NonDragTarget.this;
                        nonDragTarget2.mTransitAnimator = null;
                        if (nonDragTarget2.mAnimatingExit) {
                            nonDragTarget2.mAnimatingExit = false;
                        }
                    }
                });
                nonDragTarget.mTransitAnimator.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                nonDragTarget.mTransitAnimator.setDuration(150L);
                nonDragTarget.mTransitAnimator.start();
            }
        }
    }

    public final void swapOrShrinkNonTarget(int i, int i2, int i3) {
        undoNonTarget();
        NonDragTarget targetUnderPoint = getTargetUnderPoint(i, i2);
        if (this.mPushedNonTargets.contains(targetUnderPoint)) {
            this.mPushedNonTargets.remove(targetUnderPoint);
            this.mPushed = false;
        }
        if (targetUnderPoint != null) {
            if (!this.mTaskVisibility.isMultiSplit()) {
                Rect shrinkBounds = getShrinkBounds(targetUnderPoint);
                this.mShrunkTarget = targetUnderPoint;
                targetUnderPoint.animate(shrinkBounds);
                return;
            }
            if (!isQuarter(this.mDragTargetWindowingMode)) {
                if (i3 != 32) {
                    this.mSwapTarget = targetUnderPoint;
                    targetUnderPoint.bringToFront();
                    targetUnderPoint.animate(this.mDragTargetBounds);
                    return;
                }
                return;
            }
            if (isQuarter(targetUnderPoint.mNsWindowingMode)) {
                Rect shrinkBounds2 = getShrinkBounds(targetUnderPoint);
                this.mShrunkTarget = targetUnderPoint;
                targetUnderPoint.animate(shrinkBounds2);
                return;
            }
            if ((!this.mSplitScreenController.isVerticalDivision() && (i3 == 2 || i3 == 8)) || (this.mSplitScreenController.isVerticalDivision() && (i3 == 4 || i3 == 16))) {
                this.mShrunkTarget = targetUnderPoint;
                targetUnderPoint.animate(getShrinkBounds(targetUnderPoint));
                return;
            }
            this.mSwapTarget = targetUnderPoint;
            targetUnderPoint.animate(this.mDragTargetBounds);
            targetUnderPoint.bringToFront();
            NonDragTarget nonDragTarget = (NonDragTarget) this.mNonTargets.get(getReverseWindowingMode(this.mDragTargetWindowingMode, true));
            if (nonDragTarget != null) {
                nonDragTarget.animate(this.mTaskVisibility.getTaskBounds(nonDragTarget.mNsWindowingMode));
                if (this.mPushedNonTargets.contains(nonDragTarget)) {
                    this.mPushedNonTargets.remove(nonDragTarget);
                }
            }
        }
    }

    public final void undoNonTarget() {
        NonDragTarget nonDragTarget;
        NonDragTarget nonDragTarget2 = this.mSwapTarget;
        if (nonDragTarget2 == null) {
            NonDragTarget nonDragTarget3 = this.mShrunkTarget;
            if (nonDragTarget3 != null) {
                nonDragTarget3.animate(nonDragTarget3.mBaseBounds);
                this.mShrunkTarget = null;
                return;
            }
            return;
        }
        nonDragTarget2.animate(nonDragTarget2.mBaseBounds);
        if (isQuarter(this.mDragTargetWindowingMode) && this.mSwapTarget.mNsWindowingMode == this.mHalfTarget && (nonDragTarget = (NonDragTarget) this.mNonTargets.get(getReverseWindowingMode(this.mDragTargetWindowingMode, true))) != null) {
            nonDragTarget.animate(nonDragTarget.mBaseBounds);
            if (this.mPushedNonTargets.contains(nonDragTarget)) {
                this.mPushedNonTargets.remove(nonDragTarget);
            }
        }
        this.mSwapTarget = null;
    }
}
