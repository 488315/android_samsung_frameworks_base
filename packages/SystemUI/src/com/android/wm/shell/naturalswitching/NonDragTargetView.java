package com.android.wm.shell.naturalswitching;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Resources;
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
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import com.android.systemui.R;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public class NonDragTargetView extends FrameLayout {
    public ShellExecutor mBackgroundExecutor;
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
    public NaturalSwitchingLayout$$ExternalSyntheticLambda5 mOnDrawCallback;
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
    public final Rect mToSwapTargetBounds;
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
        this.mToSwapTargetBounds = new Rect();
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
                NonDragTargetView nonDragTargetView = this.f$0;
                NaturalSwitchingLayout$$ExternalSyntheticLambda5 naturalSwitchingLayout$$ExternalSyntheticLambda5 = nonDragTargetView.mOnDrawCallback;
                if (naturalSwitchingLayout$$ExternalSyntheticLambda5 != null) {
                    naturalSwitchingLayout$$ExternalSyntheticLambda5.run();
                    nonDragTargetView.mOnDrawCallback = null;
                }
            }
        };
        this.mNonTargets = new SparseArray();
    }

    public final NonDragTarget createNonDragTarget() {
        NonDragTarget nonDragTarget = (NonDragTarget) LayoutInflater.from(((FrameLayout) this).mContext).inflate(R.layout.ns_non_drag_target, (ViewGroup) null);
        nonDragTarget.mBackgroundExecutor = this.mBackgroundExecutor;
        this.mMainView.addView(nonDragTarget);
        return nonDragTarget;
    }

    public final Rect getCenterFreeformBounds() throws Resources.NotFoundException {
        int dimensionPixelSize = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.ns_drop_freeform_width);
        int dimensionPixelSize2 = ((FrameLayout) this).mContext.getResources().getDimensionPixelSize(R.dimen.ns_drop_freeform_height);
        Rect rect = new Rect();
        int iWidth = ((this.mDisplayBounds.width() - dimensionPixelSize) / 2) + rect.left;
        int iHeight = (this.mDisplayBounds.height() - dimensionPixelSize2) / 2;
        rect.set(iWidth, iHeight, dimensionPixelSize + iWidth, dimensionPixelSize2 + iHeight);
        return rect;
    }

    public final ArrayList getPolygonTouchRegion(int i, Rect rect) throws Resources.NotFoundException {
        if (!CoreRune.MW_NATURAL_SWITCHING_PIP || this.mDragTargetWindowingMode != 2) {
            Rect centerFreeformBounds = getCenterFreeformBounds();
            ArrayList arrayList = new ArrayList();
            if (i == 16) {
                arrayList.add(new PointF(rect.left, rect.top));
                arrayList.add(new PointF(rect.right, rect.top));
                arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
                arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
                return arrayList;
            }
            if (i == 32) {
                arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.top));
                arrayList.add(new PointF(rect.right, rect.top));
                arrayList.add(new PointF(rect.right, rect.bottom));
                arrayList.add(new PointF(centerFreeformBounds.right, centerFreeformBounds.bottom));
                return arrayList;
            }
            if (i == 8) {
                arrayList.add(new PointF(rect.left, rect.top));
                arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.top));
                arrayList.add(new PointF(centerFreeformBounds.left, centerFreeformBounds.bottom));
                arrayList.add(new PointF(rect.left, rect.bottom));
                return arrayList;
            }
            if (i == 64) {
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
            return arrayList2;
        }
        if (i == 32) {
            arrayList2.add(pointF);
            arrayList2.add(new PointF(rect.right, rect.top));
            arrayList2.add(new PointF(rect.right, rect.bottom));
            arrayList2.add(pointF);
            return arrayList2;
        }
        if (i == 8) {
            arrayList2.add(new PointF(rect.left, rect.top));
            arrayList2.add(pointF);
            arrayList2.add(pointF);
            arrayList2.add(new PointF(rect.left, rect.bottom));
            return arrayList2;
        }
        if (i == 64) {
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

    public final Rect getShrinkBounds(NonDragTarget nonDragTarget) {
        int iWidth;
        int iHeight;
        int i;
        int i2;
        Rect rect = new Rect();
        if (nonDragTarget.mAnimator != null) {
            rect.set(nonDragTarget.mEndBounds);
        } else {
            nonDragTarget.getCurrentLayoutBounds(rect);
        }
        boolean zIsQuarter = true;
        if (!this.mIsFloatingDragTarget && !this.mDragTargetBounds.isEmpty()) {
            if (this.mTaskVisibility.isMultiSplit()) {
                zIsQuarter = true ^ isQuarter(this.mDragTargetWindowingMode);
            } else if (this.mTaskVisibility.isTwoUp() && (this.mSplitScreenController.isVerticalDivision() ? !((i = this.mDropSide) == 4 || i == 16) : !((i2 = this.mDropSide) != 4 && i2 != 16))) {
                zIsQuarter = false;
            }
        }
        if (zIsQuarter) {
            int iWidth2 = this.mContainingBounds.width();
            int i3 = this.mDividerSize;
            iWidth = AbsActionBarView$$ExternalSyntheticOutline0.m(iWidth2, i3, 2, i3);
            int iHeight2 = this.mContainingBounds.height();
            int i4 = this.mDividerSize;
            iHeight = AbsActionBarView$$ExternalSyntheticOutline0.m(iHeight2, i4, 2, i4);
        } else {
            iWidth = this.mDragTargetBounds.width() + this.mDividerSize;
            iHeight = this.mDragTargetBounds.height() + this.mDividerSize;
        }
        int i5 = this.mDropSide;
        if (i5 == 2) {
            rect.left = this.mContainingBounds.left + iWidth;
            return rect;
        }
        if (i5 == 4) {
            rect.top = this.mContainingBounds.top + iHeight;
            return rect;
        }
        if (i5 == 8) {
            rect.right = this.mContainingBounds.right - iWidth;
            return rect;
        }
        if (i5 != 16) {
            return rect;
        }
        rect.bottom = this.mContainingBounds.bottom - iHeight;
        return rect;
    }

    public final NonDragTarget getTargetUnderPoint(int i, int i2) {
        if (this.mDropSide == 16) {
            i2 = this.mStableRect.bottom - 1;
        }
        for (int size = this.mNonTargets.size() - 1; size >= 0; size--) {
            NonDragTarget nonDragTarget = (NonDragTarget) this.mNonTargets.valueAt(size);
            if (nonDragTarget.mAnimator == null) {
                if (!this.mPushed) {
                    ArrayList arrayList = nonDragTarget.mPolygon;
                    if (arrayList == null || arrayList.size() < 4) {
                        nonDragTarget.getCurrentLayoutBounds(this.mTmpRect);
                        if (this.mTmpRect.contains(i, i2)) {
                            return nonDragTarget;
                        }
                    } else {
                        ArrayList arrayList2 = nonDragTarget.mPolygon;
                        int i3 = 0;
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
                                    if (DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(pointF2.x, f3, (f2 - f) / (pointF2.y - f), f3) <= i) {
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
                    }
                } else if (nonDragTarget.mBaseBounds.contains(i, i2)) {
                    return nonDragTarget;
                }
            }
        }
        return null;
    }

    public final void init(int i, TaskVisibility taskVisibility, int i2, SplitScreenController splitScreenController, ShellExecutor shellExecutor) throws Resources.NotFoundException {
        int dimensionPixelSize;
        int dimensionPixelSize2;
        this.mMainView = (ViewGroup) findViewById(R.id.main);
        this.mWm = (WindowManager) getContext().getSystemService("window");
        this.mTaskVisibility = taskVisibility;
        taskVisibility.mDisplayLayout.getStableBounds(this.mStableRect, true);
        this.mTaskVisibility.mDisplayLayout.getDisplayBounds(this.mDisplayBounds);
        this.mBackgroundExecutor = shellExecutor;
        boolean z = CoreRune.MW_NATURAL_SWITCHING_MULTI_SPLIT;
        if (z) {
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
        int color = ((FrameLayout) this).mContext.getResources().getColor(17171593, null);
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
            dimensionPixelSize = getResources().getDimensionPixelSize(android.R.dimen.secondary_rounded_corner_radius_bottom_adjustment);
            dimensionPixelSize2 = CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? getResources().getDimensionPixelSize(android.R.dimen.secondary_rounded_corner_radius_bottom) : getResources().getDimensionPixelSize(android.R.dimen.secondary_rounded_corner_radius_adjustment);
        } else {
            dimensionPixelSize = getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_25);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(android.R.dimen.indeterminate_progress_alpha_24);
        }
        this.mDividerSize = dimensionPixelSize - (dimensionPixelSize2 * 2);
        this.mScaleDeltaSize = getResources().getDimensionPixelSize(R.dimen.natural_switching_scale_delta);
        this.mCornerRadius = getResources().getDimensionPixelOffset(17105925);
        if (!this.mTaskVisibility.isTaskVisible(13) && (!CoreRune.MW_NATURAL_SWITCHING_FULLSCREEN || this.mDragTargetWindowingMode != 1)) {
            SparseArray sparseArray = this.mTaskVisibility.mRunningTaskInfo;
            for (int size = sparseArray.size() - 1; size >= 0; size--) {
                ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) sparseArray.valueAt(size);
                int iKeyAt = sparseArray.keyAt(size);
                if (runningTaskInfo.isVisible() && iKeyAt != this.mDragTargetWindowingMode && !NaturalSwitchingLayout.isFloating(iKeyAt)) {
                    NonDragTarget nonDragTargetCreateNonDragTarget = createNonDragTarget();
                    nonDragTargetCreateNonDragTarget.init(this, runningTaskInfo.taskId, iKeyAt, runningTaskInfo.configuration.windowConfiguration.getBounds(), runningTaskInfo.configuration.windowConfiguration.getStagePosition());
                    if (this.mNaturalSwitchingMode == 1) {
                        nonDragTargetCreateNonDragTarget.setThumbnail();
                    } else {
                        nonDragTargetCreateNonDragTarget.setDropTargetView();
                    }
                    this.mNonTargets.put(iKeyAt, nonDragTargetCreateNonDragTarget);
                }
            }
            this.mDragTargetBounds.set(this.mTaskVisibility.getTaskBounds(this.mDragTargetWindowingMode));
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitScreenController.isParallelMultiSplit()) {
                this.mToSwapTargetBounds.set(this.mDragTargetBounds);
            }
        } else if (!z || MultiWindowUtils.isInSubDisplay(((FrameLayout) this).mContext)) {
            NonDragTarget nonDragTargetCreateNonDragTarget2 = createNonDragTarget();
            NonDragTarget nonDragTargetCreateNonDragTarget3 = createNonDragTarget();
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            this.mSplitScreenController.getStageBounds(rect, rect2);
            if (this.mSplitScreenController.isVerticalDivision()) {
                Rect rect3 = this.mDisplayBounds;
                int i4 = rect3.top;
                rect2.top = i4;
                rect.top = i4;
                int i5 = rect3.bottom;
                rect2.bottom = i5;
                rect.bottom = i5;
                rect.left = rect3.left;
                rect2.right = rect3.right;
            } else {
                Rect rect4 = this.mDisplayBounds;
                rect.top = rect4.top;
                rect2.bottom = rect4.bottom;
            }
            nonDragTargetCreateNonDragTarget2.init(this, 0, 13, rect, 0);
            nonDragTargetCreateNonDragTarget2.setDropTargetView();
            this.mNonTargets.put(3, nonDragTargetCreateNonDragTarget2);
            nonDragTargetCreateNonDragTarget3.init(this, 0, 13, rect2, 1);
            nonDragTargetCreateNonDragTarget3.setDropTargetView();
            this.mNonTargets.put(4, nonDragTargetCreateNonDragTarget3);
            this.mDragTargetBounds.set(this.mTaskVisibility.getTaskBounds(this.mDragTargetWindowingMode));
        } else {
            NonDragTarget nonDragTargetCreateNonDragTarget4 = createNonDragTarget();
            NonDragTarget nonDragTargetCreateNonDragTarget5 = createNonDragTarget();
            NonDragTarget nonDragTargetCreateNonDragTarget6 = createNonDragTarget();
            NonDragTarget nonDragTargetCreateNonDragTarget7 = createNonDragTarget();
            int iWidth = this.mDisplayBounds.width();
            int iHeight = this.mDisplayBounds.height();
            Rect rectStableInsets = this.mTaskVisibility.mDisplayLayout.stableInsets(true);
            int i6 = iWidth - rectStableInsets.right;
            int i7 = rectStableInsets.left;
            int iM = AbsActionBarView$$ExternalSyntheticOutline0.m(i6, i7, 2, i7);
            if (this.mSplitScreenController.isSplitScreenFeasible(true)) {
                int i8 = iHeight / 2;
                Rect rect5 = new Rect(rectStableInsets.left, 0, iWidth - rectStableInsets.right, i8);
                ArrayList polygonTouchRegion = getPolygonTouchRegion(16, rect5);
                nonDragTargetCreateNonDragTarget4.init(this, 0, 13, rect5, 16);
                nonDragTargetCreateNonDragTarget4.mPolygon.addAll(polygonTouchRegion);
                nonDragTargetCreateNonDragTarget4.setDropTargetView();
                this.mNonTargets.put(6, nonDragTargetCreateNonDragTarget4);
                Rect rect6 = new Rect(rectStableInsets.left, i8, iWidth - rectStableInsets.right, iHeight);
                ArrayList polygonTouchRegion2 = getPolygonTouchRegion(64, rect6);
                nonDragTargetCreateNonDragTarget5.init(this, 0, 13, rect6, 64);
                nonDragTargetCreateNonDragTarget5.mPolygon.addAll(polygonTouchRegion2);
                nonDragTargetCreateNonDragTarget5.setDropTargetView();
                this.mNonTargets.put(7, nonDragTargetCreateNonDragTarget5);
            }
            if (this.mSplitScreenController.isSplitScreenFeasible(false)) {
                Rect rect7 = new Rect(rectStableInsets.left, 0, iM, iHeight);
                ArrayList polygonTouchRegion3 = getPolygonTouchRegion(8, rect7);
                nonDragTargetCreateNonDragTarget6.init(this, 0, 13, rect7, 8);
                nonDragTargetCreateNonDragTarget6.mPolygon.addAll(polygonTouchRegion3);
                nonDragTargetCreateNonDragTarget6.setDropTargetView();
                this.mNonTargets.put(8, nonDragTargetCreateNonDragTarget6);
                Rect rect8 = new Rect(iM, 0, iWidth - rectStableInsets.right, iHeight);
                ArrayList polygonTouchRegion4 = getPolygonTouchRegion(32, rect8);
                nonDragTargetCreateNonDragTarget7.init(this, 0, 13, rect8, 32);
                nonDragTargetCreateNonDragTarget7.mPolygon.addAll(polygonTouchRegion4);
                nonDragTargetCreateNonDragTarget7.setDropTargetView();
                this.mNonTargets.put(9, nonDragTargetCreateNonDragTarget7);
            }
        }
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

    public final void initPushRegion(Rect rect) {
        int i = (int) ((((FrameLayout) this).mContext.getResources().getDisplayMetrics().density * 45.0f) + 0.5f);
        Rect dividerBounds = (isQuarter(this.mDragTargetWindowingMode) || this.mIsFloatingDragTarget) ? this.mSplitScreenController.getDividerBounds() : this.mSplitScreenController.getCellDividerBounds();
        if (isNonTargetsHorizontal()) {
            if (this.mDragTargetBounds.width() < rect.width()) {
                rect = this.mDragTargetBounds;
            }
            int iWidth = rect.width() / 2;
            int i2 = dividerBounds.top - i;
            int i3 = dividerBounds.bottom + i;
            this.mPushRegions.put(1, new Rect(this.mDisplayBounds.left, i2, this.mStableRect.left + iWidth, i3));
            this.mPushRegions.put(3, new Rect(this.mStableRect.right - iWidth, i2, this.mDisplayBounds.right, i3));
            return;
        }
        if (this.mDragTargetBounds.height() < rect.height()) {
            rect = this.mDragTargetBounds;
        }
        int iHeight = rect.height();
        int i4 = dividerBounds.left - i;
        int i5 = dividerBounds.right + i;
        this.mPushRegions.put(2, new Rect(i4, this.mDisplayBounds.top, i5, this.mStableRect.top + 120));
        this.mPushRegions.put(4, new Rect(i4, this.mStableRect.bottom - iHeight, i5, this.mDisplayBounds.bottom));
    }

    public final boolean isNonTargetsHorizontal() {
        if (this.mIsFloatingDragTarget) {
            return !this.mSplitScreenController.isVerticalDivision();
        }
        if (this.mTaskVisibility.isMultiSplit()) {
            if (!this.mSplitScreenController.isVerticalDivision()) {
                return isQuarter(this.mDragTargetWindowingMode);
            }
            if (this.mDragTargetWindowingMode == this.mHalfTarget) {
                return true;
            }
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
                float f = 1.0f;
                nonDragTarget.mTransitAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
                final float scaleX = nonDragTarget.mView.getScaleX();
                final float scaleY = nonDragTarget.mView.getScaleY();
                final float f2 = z ? nonDragTarget.mDownScale.x : 1.0f;
                if (z) {
                    f = nonDragTarget.mDownScale.y;
                }
                final float f3 = f;
                nonDragTarget.mTransitAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.8
                    public final /* synthetic */ float val$fromScaleX;
                    public final /* synthetic */ float val$fromScaleY;
                    public final /* synthetic */ float val$toScaleX;
                    public final /* synthetic */ float val$toScaleY;

                    public AnonymousClass8(final float scaleX2, final float f22, final float scaleY2, final float f32) {
                        f = scaleX2;
                        f = f22;
                        f = scaleY2;
                        f = f32;
                    }

                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        float fFloatValue = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                        float f4 = f;
                        float fM$1 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f, f4, fFloatValue, f4);
                        float f5 = f;
                        float fM$12 = DrawerArrowDrawable$$ExternalSyntheticOutline0.m$1(f, f5, fFloatValue, f5);
                        NonDragTarget.this.mView.setScaleX(fM$1);
                        NonDragTarget.this.mView.setScaleY(fM$12);
                        NonDragTarget.this.mBlurView.setScaleX(fM$1);
                        NonDragTarget.this.mBlurView.setScaleY(fM$12);
                        NonDragTarget.this.mView.invalidateOutline();
                        NonDragTarget.this.mBlurView.invalidateOutline();
                    }
                });
                nonDragTarget.mTransitAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.naturalswitching.NonDragTarget.9
                    public AnonymousClass9() {
                    }

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
        if (CoreRune.MW_PARALLEL_MULTI_SPLIT && this.mSplitScreenController.isParallelMultiSplit()) {
            NonDragTarget targetUnderPoint = getTargetUnderPoint(i, i2);
            if (targetUnderPoint == null) {
                undoNonTarget();
                return;
            }
            if (this.mDragTargetBounds.contains(i, i2)) {
                targetUnderPoint.animate(targetUnderPoint.mBaseBounds);
                this.mSwapTarget = null;
                this.mShrunkTarget = null;
                this.mToSwapTargetBounds.set(this.mDragTargetBounds);
                return;
            }
            this.mSwapTarget = targetUnderPoint;
            Rect rect = new Rect();
            targetUnderPoint.getCurrentLayoutBounds(rect);
            targetUnderPoint.animate(this.mToSwapTargetBounds);
            this.mToSwapTargetBounds.set(rect);
            return;
        }
        undoNonTarget();
        NonDragTarget targetUnderPoint2 = getTargetUnderPoint(i, i2);
        if (this.mPushedNonTargets.contains(targetUnderPoint2)) {
            this.mPushedNonTargets.remove(targetUnderPoint2);
            this.mPushed = false;
        }
        if (targetUnderPoint2 != null) {
            if (!this.mTaskVisibility.isMultiSplit()) {
                Rect shrinkBounds = getShrinkBounds(targetUnderPoint2);
                this.mShrunkTarget = targetUnderPoint2;
                targetUnderPoint2.animate(shrinkBounds);
                return;
            }
            if (!isQuarter(this.mDragTargetWindowingMode)) {
                if (i3 != 32) {
                    this.mSwapTarget = targetUnderPoint2;
                    targetUnderPoint2.bringToFront();
                    targetUnderPoint2.animate(this.mDragTargetBounds);
                    return;
                }
                return;
            }
            if (isQuarter(targetUnderPoint2.mNsWindowingMode)) {
                Rect shrinkBounds2 = getShrinkBounds(targetUnderPoint2);
                this.mShrunkTarget = targetUnderPoint2;
                targetUnderPoint2.animate(shrinkBounds2);
                return;
            }
            if ((!this.mSplitScreenController.isVerticalDivision() && (i3 == 2 || i3 == 8)) || (this.mSplitScreenController.isVerticalDivision() && (i3 == 4 || i3 == 16))) {
                this.mShrunkTarget = targetUnderPoint2;
                targetUnderPoint2.animate(getShrinkBounds(targetUnderPoint2));
                return;
            }
            this.mSwapTarget = targetUnderPoint2;
            targetUnderPoint2.animate(this.mDragTargetBounds);
            targetUnderPoint2.bringToFront();
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
