package com.android.wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.InsetsController;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.SurfaceControlViewHost;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.jank.InteractionJankMonitor;
import com.android.internal.policy.DividerSnapAlgorithm;
import com.android.wm.shell.C3767R;
import com.android.wm.shell.animation.Interpolators;
import com.android.wm.shell.common.split.DividerResizeController;
import com.android.wm.shell.common.split.DividerResizeLayout;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecoration;
import com.android.systemui.R;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.desktopsystemui.sharedlib.system.QuickStepContract;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DividerView extends FrameLayout implements View.OnTouchListener {
    public static final C38841 DIVIDER_HEIGHT_PROPERTY = new C38841(Integer.class, "height");
    public static final C38893 DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
    public static final C38882 DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY;
    public final C38904 mAnimatorListener;
    public View mBackground;
    public FrameLayout mDividerBar;
    public final Rect mDividerBounds;
    public DividerPanel mDividerPanel;
    public DividerResizeController mDividerResizeController;
    public DividerRoundedCorner mDividerRoundedCorner;
    public GestureDetector mGestureDetector;

    /* renamed from: mH */
    public final Handler f439mH;
    public DividerHandleView mHandle;
    public final C38915 mHandleDelegate;
    public final DividerView$$ExternalSyntheticLambda0 mHandleHoverListener;
    public final InputManager mInputManager;
    public boolean mInteractive;
    public final boolean mIsCellDivider;
    public final RunnableC388611 mMouseOut;
    public final C38959 mMouseOutAnimatorListener;
    public final AnimatorSet mMouseOutAnimatorSet;
    public final ValueAnimator mMouseOutRoundedCornerAnimator;
    public final RunnableC388510 mMouseOver;
    public final C38948 mMouseOverAnimatorListener;
    public final AnimatorSet mMouseOverAnimatorSet;
    public int mMouseOverBgScaleSize;
    public final ValueAnimator mMouseOverRoundedCornerAnimator;
    public boolean mMoving;
    public final C388712 mMultiSplitHandleDelegate;
    public boolean mNeedUpdateCursorWhenMoving;
    public final C38937 mRoundedCornerUpdateListener;
    public boolean mSetTouchRegion;
    public SplitLayout mSplitLayout;
    public SplitWindowManager mSplitWindowManager;
    public int mStartPos;
    public final Rect mTempRect;
    public int mTouchElevation;
    public final int mTouchSlop;
    public boolean mTouching;
    public VelocityTracker mVelocityTracker;
    public SurfaceControlViewHost mViewHost;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.wm.shell.common.split.DividerView$1 */
    public final class C38841 extends Property {
        public C38841(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public final Object get(Object obj) {
            return Integer.valueOf(((DividerView) obj).mDividerBar.getLayoutParams().height);
        }

        @Override // android.util.Property
        public final void set(Object obj, Object obj2) {
            DividerView dividerView = (DividerView) obj;
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) dividerView.mDividerBar.getLayoutParams();
            marginLayoutParams.height = ((Integer) obj2).intValue();
            dividerView.mDividerBar.setLayoutParams(marginLayoutParams);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        public /* synthetic */ DoubleTapListener(DividerView dividerView, int i) {
            this();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTap(MotionEvent motionEvent) {
            SplitLayout splitLayout = DividerView.this.mSplitLayout;
            if (splitLayout == null) {
                return true;
            }
            ((StageCoordinator) splitLayout.mSplitLayoutHandler).onDoubleTappedDivider();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        private DoubleTapListener() {
        }
    }

    public static /* synthetic */ void $r8$lambda$xjbJZubBp3a6wlufPhmpfDd1Ohw(DividerView dividerView, MotionEvent motionEvent) {
        dividerView.getClass();
        if (motionEvent.getToolType(0) != 3) {
            return;
        }
        int action = motionEvent.getAction();
        if (action == 7) {
            if (dividerView.mNeedUpdateCursorWhenMoving) {
                dividerView.updateCursorType();
                dividerView.mNeedUpdateCursorWhenMoving = false;
                return;
            }
            return;
        }
        if (action == 9) {
            dividerView.f439mH.removeCallbacks(dividerView.mMouseOver);
            dividerView.f439mH.removeCallbacks(dividerView.mMouseOut);
            dividerView.f439mH.postDelayed(dividerView.mMouseOver, 100L);
        } else {
            if (action != 10) {
                return;
            }
            dividerView.mNeedUpdateCursorWhenMoving = true;
            dividerView.f439mH.removeCallbacks(dividerView.mMouseOver);
            dividerView.f439mH.removeCallbacks(dividerView.mMouseOut);
            dividerView.f439mH.post(dividerView.mMouseOut);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.common.split.DividerView$2] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.common.split.DividerView$3] */
    static {
        Class<Integer> cls = Integer.class;
        DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY = new Property(cls, "width") { // from class: com.android.wm.shell.common.split.DividerView.2
            @Override // android.util.Property
            public final Object get(Object obj) {
                View view = ((DividerView) obj).mBackground;
                if (view != null) {
                    return Integer.valueOf(view.getLayoutParams().width);
                }
                return 0;
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                DividerView dividerView = (DividerView) obj;
                Integer num = (Integer) obj2;
                View view = dividerView.mBackground;
                if (view != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    marginLayoutParams.width = num.intValue();
                    dividerView.mBackground.setLayoutParams(marginLayoutParams);
                }
            }
        };
        DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY = new Property(cls, "height") { // from class: com.android.wm.shell.common.split.DividerView.3
            @Override // android.util.Property
            public final Object get(Object obj) {
                View view = ((DividerView) obj).mBackground;
                if (view != null) {
                    return Integer.valueOf(view.getLayoutParams().height);
                }
                return 0;
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                DividerView dividerView = (DividerView) obj;
                Integer num = (Integer) obj2;
                View view = dividerView.mBackground;
                if (view != null) {
                    ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                    marginLayoutParams.height = num.intValue();
                    dividerView.mBackground.setLayoutParams(marginLayoutParams);
                }
            }
        };
    }

    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.wm.shell.common.split.DividerView$8] */
    /* JADX WARN: Type inference failed for: r2v11, types: [com.android.wm.shell.common.split.DividerView$9] */
    /* JADX WARN: Type inference failed for: r2v12, types: [com.android.wm.shell.common.split.DividerView$10] */
    /* JADX WARN: Type inference failed for: r2v13, types: [com.android.wm.shell.common.split.DividerView$11] */
    /* JADX WARN: Type inference failed for: r2v14, types: [com.android.wm.shell.common.split.DividerView$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r2v15, types: [com.android.wm.shell.common.split.DividerView$12] */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.android.wm.shell.common.split.DividerView$4] */
    /* JADX WARN: Type inference failed for: r2v8, types: [com.android.wm.shell.common.split.DividerView$5] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.wm.shell.common.split.DividerView$7] */
    public DividerView(Context context) {
        super(context);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mSetTouchRegion = true;
        this.mMouseOverAnimatorSet = new AnimatorSet();
        this.mMouseOutAnimatorSet = new AnimatorSet();
        this.mMouseOverRoundedCornerAnimator = new ValueAnimator();
        this.mMouseOutRoundedCornerAnimator = new ValueAnimator();
        this.f439mH = new Handler();
        final int i = 0;
        this.mTouching = false;
        this.mNeedUpdateCursorWhenMoving = true;
        this.mDividerBounds = new Rect();
        this.mTempRect = new Rect();
        this.mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                DividerView.this.mSetTouchRegion = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DividerView.this.mSetTouchRegion = true;
            }
        };
        this.mHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.wm.shell.common.split.DividerView.5
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                DividerSnapAlgorithm dividerSnapAlgorithm = DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                if (DividerView.this.isLandscape()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_full)));
                    if (dividerSnapAlgorithm.isFirstSplitTargetAvailable()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_70)));
                    }
                    if (dividerSnapAlgorithm.showMiddleSplitTargetForAccessibility()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_50)));
                    }
                    if (dividerSnapAlgorithm.isLastSplitTargetAvailable()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_30)));
                    }
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_right_full)));
                    return;
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_full)));
                if (dividerSnapAlgorithm.isFirstSplitTargetAvailable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_70)));
                }
                if (dividerSnapAlgorithm.showMiddleSplitTargetForAccessibility()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_50)));
                }
                if (dividerSnapAlgorithm.isLastSplitTargetAvailable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_30)));
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_bottom_full)));
            }

            /* JADX WARN: Removed duplicated region for block: B:6:0x0043  */
            /* JADX WARN: Removed duplicated region for block: B:9:0x0058  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                DividerSnapAlgorithm.SnapTarget dismissStartTarget;
                DividerView dividerView = DividerView.this;
                DividerSnapAlgorithm dividerSnapAlgorithm = dividerView.mSplitLayout.mDividerSnapAlgorithm;
                if (i2 != 16) {
                    if (i2 == R.id.action_move_tl_full) {
                        dismissStartTarget = dividerSnapAlgorithm.getDismissEndTarget();
                    } else if (i2 == R.id.action_move_tl_70) {
                        dismissStartTarget = dividerSnapAlgorithm.getLastSplitTarget();
                    } else if (i2 == R.id.action_move_tl_50) {
                        dismissStartTarget = dividerSnapAlgorithm.getMiddleTarget();
                    } else if (i2 == R.id.action_move_tl_30) {
                        dismissStartTarget = dividerSnapAlgorithm.getFirstSplitTarget();
                    } else if (i2 == R.id.action_move_rb_full) {
                        dismissStartTarget = dividerSnapAlgorithm.getDismissStartTarget();
                    }
                    if (dismissStartTarget != null) {
                        return super.performAccessibilityAction(view, i2, bundle);
                    }
                    SplitLayout splitLayout = DividerView.this.mSplitLayout;
                    splitLayout.snapToTarget(splitLayout.mDividePosition, dismissStartTarget, false);
                    SplitLayout splitLayout2 = DividerView.this.mSplitLayout;
                    splitLayout2.updateDivideBounds(splitLayout2.mDividePosition);
                    return true;
                }
                dividerView.openDividerPanelIfNeeded();
                dismissStartTarget = null;
                if (dismissStartTarget != null) {
                }
            }
        };
        this.mRoundedCornerUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerView.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DividerView.this.mDividerRoundedCorner.mDividerWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                DividerView.this.mDividerRoundedCorner.invalidate();
            }
        };
        this.mMouseOverAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                DividerView dividerView = DividerView.this;
                C38841 c38841 = DividerView.DIVIDER_HEIGHT_PROPERTY;
                dividerView.updateBackgroundColor(true);
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mDividerRoundedCorner;
                dividerRoundedCorner.mDividerBarBackground.setColor(dividerRoundedCorner.getResources().getColor(17171493, null));
            }
        };
        this.mMouseOutAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DividerView dividerView = DividerView.this;
                if (dividerView.mTouching) {
                    dividerView.updateCursorType();
                } else {
                    InputManager inputManager = dividerView.mInputManager;
                    if (inputManager != null) {
                        inputManager.setPointerIconType(10121);
                    }
                }
                DividerView.this.updateBackgroundColor(false);
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mDividerRoundedCorner;
                dividerRoundedCorner.mDividerBarBackground.setColor(dividerRoundedCorner.getResources().getColor(17171493, null));
            }
        };
        this.mMouseOver = new Runnable() { // from class: com.android.wm.shell.common.split.DividerView.10
            @Override // java.lang.Runnable
            public final void run() {
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mDividerRoundedCorner;
                dividerRoundedCorner.mDividerWidth = dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
                Property property = DividerView.this.isVerticalDivision() ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                DividerView dividerView = DividerView.this;
                int i2 = dividerView.mSplitLayout.mDividerSize;
                ObjectAnimator ofInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, i2, dividerView.mMouseOverBgScaleSize + i2);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.0f, 1.3f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.0f, 1.3f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOverRoundedCornerAnimator;
                int i3 = dividerView2.mDividerRoundedCorner.mDividerWidth;
                valueAnimator.setIntValues(i3, dividerView2.mMouseOverBgScaleSize + i3);
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOverRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView dividerView4 = DividerView.this;
                dividerView4.mMouseOverAnimatorSet.playTogether(ofInt, ofFloat, ofFloat2, dividerView4.mMouseOverRoundedCornerAnimator);
                DividerView.this.mMouseOverAnimatorSet.setDuration(200L);
                DividerView.this.mMouseOverAnimatorSet.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                DividerView dividerView5 = DividerView.this;
                dividerView5.mMouseOverAnimatorSet.addListener(dividerView5.mMouseOverAnimatorListener);
                DividerView.this.mMouseOverAnimatorSet.start();
                DividerView.this.updateCursorType();
            }
        };
        this.mMouseOut = new Runnable() { // from class: com.android.wm.shell.common.split.DividerView.11
            @Override // java.lang.Runnable
            public final void run() {
                Property property = DividerView.this.isVerticalDivision() ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                DividerView dividerView = DividerView.this;
                int i2 = dividerView.mSplitLayout.mDividerSize;
                ObjectAnimator ofInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, dividerView.mMouseOverBgScaleSize + i2, i2);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.3f, 1.0f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.3f, 1.0f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOutRoundedCornerAnimator;
                DividerRoundedCorner dividerRoundedCorner = dividerView2.mDividerRoundedCorner;
                valueAnimator.setIntValues(dividerRoundedCorner.mDividerWidth, dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width));
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOutRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView dividerView4 = DividerView.this;
                dividerView4.mMouseOutAnimatorSet.playTogether(ofInt, ofFloat, ofFloat2, dividerView4.mMouseOutRoundedCornerAnimator);
                DividerView.this.mMouseOutAnimatorSet.setDuration(200L);
                DividerView.this.mMouseOutAnimatorSet.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                DividerView dividerView5 = DividerView.this;
                dividerView5.mMouseOutAnimatorSet.addListener(dividerView5.mMouseOutAnimatorListener);
                DividerView.this.mMouseOutAnimatorSet.start();
            }
        };
        this.mHandleHoverListener = new View.OnHoverListener(this) { // from class: com.android.wm.shell.common.split.DividerView$$ExternalSyntheticLambda0
            public final /* synthetic */ DividerView f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0003. Please report as an issue. */
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                switch (i) {
                }
                DividerView.$r8$lambda$xjbJZubBp3a6wlufPhmpfDd1Ohw(this.f$0, motionEvent);
                return false;
            }
        };
        this.mMultiSplitHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.wm.shell.common.split.DividerView.12
            /* JADX WARN: Removed duplicated region for block: B:11:0x0045  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x0049  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x002b  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                DividerSnapAlgorithm dividerSnapAlgorithm;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    if (dividerView.mIsCellDivider) {
                        dividerSnapAlgorithm = dividerView.mSplitLayout.getCellSnapAlgorithm();
                        boolean isVerticalDivision = DividerView.this.isVerticalDivision();
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(!isVerticalDivision ? R.string.accessibility_action_divider_left_full : R.string.accessibility_action_divider_top_full)));
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(!isVerticalDivision ? R.string.accessibility_action_divider_right_full : R.string.accessibility_action_divider_bottom_full)));
                        if (!dividerSnapAlgorithm.isMiddleTargetOnly() || DividerView.this.mIsCellDivider) {
                        }
                        int i2 = isVerticalDivision ? R.string.accessibility_action_divider_left_percent : R.string.accessibility_action_divider_top_percent;
                        if (dividerSnapAlgorithm.getTargetMinimalRatio() == 30) {
                            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(i2, 70)));
                            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(i2, 50)));
                            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(i2, 30)));
                            return;
                        } else {
                            if (dividerSnapAlgorithm.getTargetMinimalRatio() == 40) {
                                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_60, ((FrameLayout) DividerView.this).mContext.getString(i2, 60)));
                                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(i2, 50)));
                                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_40, ((FrameLayout) DividerView.this).mContext.getString(i2, 40)));
                                return;
                            }
                            return;
                        }
                    }
                }
                dividerSnapAlgorithm = DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                boolean isVerticalDivision2 = DividerView.this.isVerticalDivision();
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(!isVerticalDivision2 ? R.string.accessibility_action_divider_left_full : R.string.accessibility_action_divider_top_full)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(!isVerticalDivision2 ? R.string.accessibility_action_divider_right_full : R.string.accessibility_action_divider_bottom_full)));
                if (dividerSnapAlgorithm.isMiddleTargetOnly()) {
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:11:0x0063  */
            /* JADX WARN: Removed duplicated region for block: B:26:0x00ad  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x0022  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean performAccessibilityAction(View view, int i2, Bundle bundle) {
                DividerSnapAlgorithm dividerSnapAlgorithm;
                DividerSnapAlgorithm.SnapTarget lastSplitTarget;
                boolean z;
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    if (dividerView.mIsCellDivider) {
                        dividerSnapAlgorithm = dividerView.mSplitLayout.getCellSnapAlgorithm();
                        if (i2 == 16) {
                            if (i2 == R.id.action_move_tl_full) {
                                lastSplitTarget = dividerSnapAlgorithm.getDismissEndTarget();
                            } else if (i2 == R.id.action_move_tl_70 || i2 == R.id.action_move_tl_60) {
                                lastSplitTarget = dividerSnapAlgorithm.getLastSplitTarget();
                            } else if (i2 == R.id.action_move_tl_50) {
                                lastSplitTarget = dividerSnapAlgorithm.getMiddleTarget();
                            } else if (i2 == R.id.action_move_tl_30 || i2 == R.id.action_move_tl_40) {
                                lastSplitTarget = dividerSnapAlgorithm.getFirstSplitTarget();
                            } else if (i2 == R.id.action_move_rb_full) {
                                lastSplitTarget = dividerSnapAlgorithm.getDismissStartTarget();
                            }
                            if (lastSplitTarget == null) {
                                return super.performAccessibilityAction(view, i2, bundle);
                            }
                            if (CoreRune.MW_MULTI_SPLIT_ACCESSIBILITY) {
                                DividerView dividerView2 = DividerView.this;
                                if (dividerView2.mIsCellDivider) {
                                    DividerResizeController dividerResizeController = dividerView2.mDividerResizeController;
                                    if (dividerResizeController.mResizingRequested) {
                                        dividerResizeController.mDividerView = dividerView2;
                                        dividerResizeController.mResizingRequested = true;
                                        dividerResizeController.mIsResizing = true;
                                        DividerResizeController.USE_GUIDE_VIEW_EFFECTS = dividerResizeController.mUseGuideViewByMultiStar;
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    if (z) {
                                        SplitLayout splitLayout = dividerView2.mSplitLayout;
                                        splitLayout.snapToTarget(splitLayout.mCellDividePosition, lastSplitTarget, true);
                                        DividerResizeController dividerResizeController2 = DividerView.this.mDividerResizeController;
                                        if (dividerResizeController2.mIsResizing) {
                                            dividerResizeController2.mDividerView = null;
                                            dividerResizeController2.mResizingRequested = false;
                                            dividerResizeController2.mIsResizing = false;
                                        }
                                    }
                                    return true;
                                }
                            }
                            DividerView.this.mSplitLayout.updateDivideBounds(lastSplitTarget.position);
                            SplitLayout splitLayout2 = DividerView.this.mSplitLayout;
                            splitLayout2.snapToTarget(splitLayout2.mDividePosition, lastSplitTarget, false);
                            return true;
                        }
                        DividerView.this.openDividerPanelIfNeeded();
                        lastSplitTarget = null;
                        if (lastSplitTarget == null) {
                        }
                    }
                }
                dividerSnapAlgorithm = DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                if (i2 == 16) {
                }
                lastSplitTarget = null;
                if (lastSplitTarget == null) {
                }
            }
        };
    }

    public final int getCurrentPosition() {
        return (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) ? this.mSplitLayout.mCellDividePosition : this.mSplitLayout.mDividePosition;
    }

    public final boolean isLandscape() {
        return getResources().getConfiguration().orientation == 2;
    }

    public final boolean isVerticalDivision() {
        SplitLayout splitLayout;
        return (!CoreRune.MW_MULTI_SPLIT_DIVIDER || (splitLayout = this.mSplitLayout) == null) ? isLandscape() : (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) ? !splitLayout.isVerticalDivision() : splitLayout.isVerticalDivision();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDividerBar = (FrameLayout) findViewById(R.id.divider_bar);
        this.mHandle = (DividerHandleView) findViewById(R.id.docked_divider_handle);
        this.mBackground = findViewById(R.id.docked_divider_background);
        this.mDividerRoundedCorner = (DividerRoundedCorner) findViewById(R.id.divider_rounded_corner);
        updateBackgroundColor(false);
        this.mHandle.setOnTouchListener(this);
        this.mMouseOverBgScaleSize = getResources().getDimensionPixelSize(R.dimen.split_divider_handle_mouse_over_scale_size);
        this.mHandle.setOnHoverListener(this.mHandleHoverListener);
        this.mTouchElevation = getResources().getDimensionPixelSize(R.dimen.docked_stack_divider_lift_elevation);
        new GestureDetector(getContext(), new DoubleTapListener(this, 0));
        this.mInteractive = true;
        setOnTouchListener(this);
        if (CoreRune.MW_MULTI_SPLIT_ACCESSIBILITY) {
            this.mHandle.setAccessibilityDelegate(this.mMultiSplitHandleDelegate);
        } else {
            this.mHandle.setAccessibilityDelegate(this.mHandleDelegate);
        }
    }

    public final void onInsetsChanged(InsetsState insetsState, boolean z) {
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
            Rect rect = this.mTempRect;
            SplitLayout splitLayout = this.mSplitLayout;
            splitLayout.getClass();
            rect.set(new Rect(splitLayout.mCellDividerBounds));
        } else {
            this.mTempRect.set(this.mSplitLayout.mDividerBounds);
        }
        if (!insetsState.isSourceOrDefaultVisible(InsetsSource.ID_IME, WindowInsets.Type.ime())) {
            for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
                if (sourceAt.getType() == WindowInsets.Type.navigationBars() && sourceAt.insetsRoundedCornerFrame()) {
                    Rect rect2 = this.mTempRect;
                    rect2.inset(sourceAt.calculateVisibleInsets(rect2));
                }
            }
        }
        DividerResizeController dividerResizeController = this.mDividerResizeController;
        if (dividerResizeController != null && dividerResizeController.mIsResizing) {
            dividerResizeController.finishResizing(getCurrentPosition());
        }
        if (this.mTempRect.equals(this.mDividerBounds)) {
            return;
        }
        if (z) {
            ObjectAnimator ofInt = ObjectAnimator.ofInt(this, DIVIDER_HEIGHT_PROPERTY, this.mDividerBounds.height(), this.mTempRect.height());
            ofInt.setInterpolator(InsetsController.RESIZE_INTERPOLATOR);
            ofInt.setDuration(300L);
            ofInt.addListener(this.mAnimatorListener);
            ofInt.start();
        } else {
            DIVIDER_HEIGHT_PROPERTY.set(this, Integer.valueOf(this.mTempRect.height()));
            this.mSetTouchRegion = true;
        }
        this.mDividerBounds.set(this.mTempRect);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mSetTouchRegion) {
            this.mTempRect.set(this.mHandle.getLeft(), this.mHandle.getTop(), this.mHandle.getRight(), this.mHandle.getBottom());
            this.mSplitWindowManager.setTouchRegion(this.mTempRect);
            this.mSetTouchRegion = false;
        }
    }

    @Override // android.widget.FrameLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mBackground.getLayoutParams();
            if (isVerticalDivision()) {
                layoutParams.width = this.mSplitLayout.mDividerSize;
                layoutParams.height = -1;
                layoutParams.gravity = 1;
            } else {
                layoutParams.width = -1;
                layoutParams.height = this.mSplitLayout.mDividerSize;
                layoutParams.gravity = 16;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x01e6, code lost:
    
        if ((r14 > r1) != false) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x004b, code lost:
    
        if (r14 != 3) goto L159;
     */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01c4  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        int i;
        ImageView imageView;
        DividerSnapAlgorithm dividerSnapAlgorithm;
        int width;
        if (this.mSplitLayout == null || !this.mInteractive) {
            return false;
        }
        GestureDetector gestureDetector = this.mGestureDetector;
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }
        motionEvent.setLocation(motionEvent.getRawX(), motionEvent.getRawY());
        int action = motionEvent.getAction() & 255;
        boolean isVerticalDivision = CoreRune.MW_MULTI_SPLIT_DIVIDER ? isVerticalDivision() : isLandscape();
        int x = (int) (isVerticalDivision ? motionEvent.getX() : motionEvent.getY());
        if (action != 0 && this.mVelocityTracker == null) {
            return false;
        }
        int i2 = 1;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    this.mVelocityTracker.addMovement(motionEvent);
                    if (!this.mMoving && Math.abs(x - this.mStartPos) > this.mTouchSlop) {
                        DividerResizeController dividerResizeController = this.mDividerResizeController;
                        SplitLayout splitLayout = this.mSplitLayout;
                        if (dividerResizeController.mResizingRequested) {
                            Log.w("DividerResizeController", "startResizing: failed, already resizing state!");
                        } else {
                            DividerResizeController.USE_GUIDE_VIEW_EFFECTS = dividerResizeController.mUseGuideViewByMultiStar;
                            dividerResizeController.mResizingRequested = true;
                            dividerResizeController.mSplitLayout = splitLayout;
                            dividerResizeController.mDividerView = this;
                            dividerResizeController.mDividerSize = splitLayout.mDividerSize;
                            dividerResizeController.mIsHorizontalDivision = !isVerticalDivision();
                            dividerResizeController.mCurrentDividerPosition = dividerResizeController.mDividerView.getCurrentPosition();
                            dividerResizeController.mDefaultHandleMoveThreshold = dividerResizeController.mContext.getResources().getDimensionPixelSize(R.dimen.mw_divider_handle_move_threshold_default);
                            dividerResizeController.mDividerResizeLayout = (DividerResizeLayout) dividerResizeController.mLayoutInflater.inflate(R.layout.divider_resize_layout, (ViewGroup) null);
                            if (CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING && dividerResizeController.mStageCoordinator.isMultiSplitActive()) {
                                dividerResizeController.mHalfSplitStageType = dividerResizeController.mStageCoordinator.getCellHostStageType() == 0 ? 1 : 0;
                            }
                            DividerResizeController.ResizeAlgorithm resizeAlgorithm = dividerResizeController.mResizeAlgorithm;
                            resizeAlgorithm.getClass();
                            boolean z2 = CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING;
                            DividerResizeController dividerResizeController2 = DividerResizeController.this;
                            if (z2) {
                                DividerView dividerView = dividerResizeController2.mDividerView;
                                if (dividerView != null && dividerView.mIsCellDivider) {
                                    dividerSnapAlgorithm = dividerResizeController2.mSplitLayout.getCellSnapAlgorithm();
                                    resizeAlgorithm.mDividerSnapAlgorithm = dividerSnapAlgorithm;
                                    if (dividerResizeController2.mIsHorizontalDivision) {
                                        SplitLayout splitLayout2 = dividerResizeController2.mSplitLayout;
                                        splitLayout2.getClass();
                                        width = new Rect(splitLayout2.mRootBounds).width();
                                    } else {
                                        SplitLayout splitLayout3 = dividerResizeController2.mSplitLayout;
                                        splitLayout3.getClass();
                                        width = new Rect(splitLayout3.mRootBounds).height();
                                    }
                                    resizeAlgorithm.mDisplaySize = width;
                                    resizeAlgorithm.mFirstSplitTargetPosition = resizeAlgorithm.mDividerSnapAlgorithm.getFirstSplitTarget().position;
                                    resizeAlgorithm.mMiddleTargetPosition = resizeAlgorithm.mDividerSnapAlgorithm.getMiddleTarget().position;
                                    int i3 = resizeAlgorithm.mDividerSnapAlgorithm.getLastSplitTarget().position;
                                    resizeAlgorithm.mLastSplitTargetPosition = i3;
                                    int i4 = resizeAlgorithm.mFirstSplitTargetPosition / 2;
                                    resizeAlgorithm.mDismissStartThreshold = i4;
                                    int i5 = resizeAlgorithm.mDisplaySize;
                                    int i6 = i5 - ((i5 - i3) / 2);
                                    resizeAlgorithm.mDismissEndThreshold = i6;
                                    resizeAlgorithm.mFirstFadeOutPosition = i4 + ((int) ((r8 - i4) * 0.625f));
                                    resizeAlgorithm.mLastFadeOutPosition = i6 - ((int) ((i6 - i3) * 0.625f));
                                    Log.d("DividerResizeController", "ResizeAlgorithm_init: " + resizeAlgorithm);
                                    dividerResizeController.mDividerResizeLayout.init(this, dividerResizeController.mSplitLayout, dividerResizeController.mStageCoordinator, resizeAlgorithm);
                                }
                            }
                            dividerSnapAlgorithm = dividerResizeController2.mSplitLayout.mDividerSnapAlgorithm;
                            resizeAlgorithm.mDividerSnapAlgorithm = dividerSnapAlgorithm;
                            if (dividerResizeController2.mIsHorizontalDivision) {
                            }
                            resizeAlgorithm.mDisplaySize = width;
                            resizeAlgorithm.mFirstSplitTargetPosition = resizeAlgorithm.mDividerSnapAlgorithm.getFirstSplitTarget().position;
                            resizeAlgorithm.mMiddleTargetPosition = resizeAlgorithm.mDividerSnapAlgorithm.getMiddleTarget().position;
                            int i32 = resizeAlgorithm.mDividerSnapAlgorithm.getLastSplitTarget().position;
                            resizeAlgorithm.mLastSplitTargetPosition = i32;
                            int i42 = resizeAlgorithm.mFirstSplitTargetPosition / 2;
                            resizeAlgorithm.mDismissStartThreshold = i42;
                            int i52 = resizeAlgorithm.mDisplaySize;
                            int i62 = i52 - ((i52 - i32) / 2);
                            resizeAlgorithm.mDismissEndThreshold = i62;
                            resizeAlgorithm.mFirstFadeOutPosition = i42 + ((int) ((r8 - i42) * 0.625f));
                            resizeAlgorithm.mLastFadeOutPosition = i62 - ((int) ((i62 - i32) * 0.625f));
                            Log.d("DividerResizeController", "ResizeAlgorithm_init: " + resizeAlgorithm);
                            dividerResizeController.mDividerResizeLayout.init(this, dividerResizeController.mSplitLayout, dividerResizeController.mStageCoordinator, resizeAlgorithm);
                        }
                        this.mMoving = true;
                    }
                    if (this.mMoving) {
                        if (motionEvent.getToolType(0) == 3 && this.mNeedUpdateCursorWhenMoving) {
                            updateCursorType();
                            this.mNeedUpdateCursorWhenMoving = false;
                        }
                        int currentPosition = (getCurrentPosition() + x) - this.mStartPos;
                        DividerResizeController dividerResizeController3 = this.mDividerResizeController;
                        if (dividerResizeController3.mResizingRequested && !dividerResizeController3.mIsFinishing) {
                            if (!dividerResizeController3.mIsResizing) {
                                if (Math.abs(dividerResizeController3.mCurrentDividerPosition - currentPosition) > dividerResizeController3.mDefaultHandleMoveThreshold) {
                                    dividerResizeController3.mIsResizing = true;
                                    ListPopupWindow$$ExternalSyntheticOutline0.m10m("validateMoveEvent: start move divider, pos=", currentPosition, "DividerResizeController");
                                    if (CoreRune.MW_SA_LOGGING && motionEvent.getToolType(0) == 3) {
                                        CoreSaLogger.logForAdvanced("1000", "From Mouse snapping");
                                    }
                                } else {
                                    z = false;
                                    if (z) {
                                        DividerResizeController.ResizeAlgorithm resizeAlgorithm2 = dividerResizeController3.mResizeAlgorithm;
                                        DividerResizeController.ResizeAlgorithm.m2740$$Nest$mupdate(resizeAlgorithm2, currentPosition);
                                        if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                                            i = resizeAlgorithm2.getSnapTargetPosition();
                                        } else {
                                            i = resizeAlgorithm2.mTouchPosition;
                                            int i7 = resizeAlgorithm2.mDismissStartThreshold;
                                            if (!(i < i7)) {
                                                i7 = resizeAlgorithm2.mDismissEndThreshold;
                                            }
                                            i = i7;
                                        }
                                        int snapTargetPosition = resizeAlgorithm2.getSnapTargetPosition();
                                        DividerResizeLayout dividerResizeLayout = dividerResizeController3.mDividerResizeLayout;
                                        int i8 = resizeAlgorithm2.mSplitDismissSide;
                                        dividerResizeLayout.setAlpha(1.0f);
                                        for (int size = dividerResizeLayout.mResizeTargets.size() - 1; size >= 0; size--) {
                                            final DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) dividerResizeLayout.mResizeTargets.valueAt(size);
                                            if (dividerResizeTarget != null) {
                                                Rect rect = dividerResizeTarget.mEndBounds;
                                                Rect rect2 = dividerResizeTarget.mTmpBounds;
                                                if (i8 == 0) {
                                                    dividerResizeTarget.calculateBoundsForPosition(i, rect2);
                                                    rect.set(rect2);
                                                    dividerResizeTarget.mShouldPlayHaptic = true;
                                                } else {
                                                    dividerResizeTarget.calculateBoundsForPosition(snapTargetPosition, rect2);
                                                    rect.set(rect2);
                                                }
                                                boolean z3 = dividerResizeTarget.mIsResizing;
                                                DividerResizeLayout dividerResizeLayout2 = DividerResizeLayout.this;
                                                if (!z3) {
                                                    dividerResizeTarget.mIsResizing = true;
                                                    ImageView imageView2 = dividerResizeTarget.mView;
                                                    imageView2.setAlpha(1.0f);
                                                    imageView2.setVisibility(0);
                                                    dividerResizeTarget.mBlurView.setVisibility(0);
                                                    if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS && (imageView = dividerResizeLayout2.mGuideBarView) != null) {
                                                        imageView.setVisibility(0);
                                                    }
                                                    dividerResizeTarget.startBoundsAnimation(rect, true, 300L);
                                                    ValueAnimator valueAnimator = dividerResizeTarget.mBlurAnimator;
                                                    if (valueAnimator != null) {
                                                        valueAnimator.end();
                                                    }
                                                    ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                                                    dividerResizeTarget.mBlurAnimator = ofFloat;
                                                    ofFloat.addUpdateListener(new DividerResizeLayout$$ExternalSyntheticLambda3(dividerResizeTarget, i2));
                                                    dividerResizeTarget.mBlurAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.2
                                                        public C38782() {
                                                        }

                                                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                        public final void onAnimationEnd(Animator animator) {
                                                            super.onAnimationEnd(animator);
                                                            DividerResizeTarget dividerResizeTarget2 = DividerResizeTarget.this;
                                                            dividerResizeTarget2.mBlurAnimator = null;
                                                            dividerResizeTarget2.mView.setAlpha(0.0f);
                                                            DividerResizeLayout.m2741$$Nest$monAnimationFinished(DividerResizeLayout.this, "blurAnimator");
                                                        }
                                                    });
                                                    dividerResizeTarget.mBlurAnimator.setInterpolator(DividerResizeLayout.SINE_OUT_60);
                                                    dividerResizeTarget.mBlurAnimator.setDuration(100L);
                                                    dividerResizeTarget.mBlurAnimator.start();
                                                    dividerResizeTarget.startOutlineInsetsAnimation(true);
                                                }
                                                if (dividerResizeTarget.updateDismissSide(i8)) {
                                                    dividerResizeTarget.startOutlineInsetsAnimation(i8 == 0);
                                                    dividerResizeTarget.startBoundsAnimation(rect2, false, 400L);
                                                }
                                                if (!(dividerResizeTarget.mBoundsAnimator != null)) {
                                                    dividerResizeTarget.updateViewBounds(rect2);
                                                }
                                                if (dividerResizeTarget.mShouldPlayHaptic && i8 != 0) {
                                                    dividerResizeTarget.mShouldPlayHaptic = false;
                                                    dividerResizeLayout2.performHapticFeedback(0);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            z = true;
                            if (z) {
                            }
                        }
                    }
                }
            }
            releaseTouching();
            if (motionEvent.getToolType(0) == 3) {
                this.mNeedUpdateCursorWhenMoving = true;
            }
            this.mVelocityTracker.addMovement(motionEvent);
            this.mVelocityTracker.computeCurrentVelocity(1000);
            if (isVerticalDivision) {
                this.mVelocityTracker.getXVelocity();
            } else {
                this.mVelocityTracker.getYVelocity();
            }
            this.mDividerResizeController.finishResizing((((CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) ? this.mSplitLayout.mCellDividePosition : this.mSplitLayout.mDividePosition) + x) - this.mStartPos);
            this.mMoving = false;
        } else {
            VelocityTracker obtain = VelocityTracker.obtain();
            this.mVelocityTracker = obtain;
            obtain.addMovement(motionEvent);
            setSlippery(false);
            this.mHandle.getClass();
            this.mHandle.animate().setInterpolator(Interpolators.TOUCH_RESPONSE).setDuration(150L).translationZ(this.mTouchElevation).start();
            this.mTouching = true;
            this.mStartPos = x;
            this.mMoving = false;
            SplitLayout splitLayout4 = this.mSplitLayout;
            InteractionJankMonitor.Configuration.Builder withSurface = InteractionJankMonitor.Configuration.Builder.withSurface(52, splitLayout4.mContext, splitLayout4.getDividerLeash());
            if (!TextUtils.isEmpty(null)) {
                withSurface.setTag((String) null);
            }
            InteractionJankMonitor.getInstance().begin(withSurface);
            if (motionEvent.getToolType(0) == 3) {
                updateCursorType();
            }
            performHapticFeedback(0);
        }
        return true;
    }

    public final void openDividerPanelIfNeeded() {
        MultitaskingWindowDecoration multitaskingWindowDecoration;
        if (this.mDividerPanel.isSupportPanelOpenPolicy()) {
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
                return;
            }
            StageCoordinator stageCoordinator = this.mSplitLayout.mStageCoordinator;
            StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
            if (!stageTaskListener.isFocused()) {
                stageTaskListener = stageCoordinator.mSideStage;
            }
            if (stageTaskListener.mWindowDecorViewModel.isPresent()) {
                SparseArray sparseArray = ((MultitaskingWindowDecorViewModel) stageTaskListener.mWindowDecorViewModel.get()).mWindowDecorByTaskId;
                int size = sparseArray.size();
                while (true) {
                    size--;
                    if (size < 0) {
                        multitaskingWindowDecoration = null;
                        break;
                    }
                    multitaskingWindowDecoration = (MultitaskingWindowDecoration) sparseArray.valueAt(size);
                    if (multitaskingWindowDecoration != null && multitaskingWindowDecoration.mTaskInfo.isFocused) {
                        break;
                    }
                }
                if (multitaskingWindowDecoration != null) {
                    multitaskingWindowDecoration.closeHandleMenu(false);
                }
            }
            this.mDividerPanel.updateDividerPanel();
        }
    }

    public final void releaseTouching() {
        setSlippery(true);
        this.mHandle.getClass();
        this.mHandle.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(200L).translationZ(0.0f).start();
        this.mTouching = false;
    }

    public final void setSlippery(boolean z) {
        if (this.mViewHost == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) getLayoutParams();
        int i = layoutParams.flags;
        if (((i & QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT) != 0) == z) {
            return;
        }
        if (z) {
            layoutParams.flags = i | QuickStepContract.SYSUI_STATE_KNOX_HARD_KEY_INTENT;
        } else {
            layoutParams.flags = (-536870913) & i;
        }
        this.mViewHost.relayout(layoutParams);
    }

    public final void updateBackgroundColor(boolean z) {
        if (CoreRune.MW_MULTI_SPLIT && !z) {
            this.mBackground.setBackgroundColor(0);
        } else {
            this.mBackground.setBackgroundColor(getContext().getResources().getColor(17171493, null));
        }
    }

    public final void updateCursorType() {
        InputManager inputManager = this.mInputManager;
        if (inputManager != null) {
            inputManager.setPointerIconType(isVerticalDivision() ? 10122 : 10123);
        }
    }

    public DividerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DividerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.common.split.DividerView$4] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.wm.shell.common.split.DividerView$5] */
    /* JADX WARN: Type inference failed for: r0v4, types: [com.android.wm.shell.common.split.DividerView$7] */
    /* JADX WARN: Type inference failed for: r0v5, types: [com.android.wm.shell.common.split.DividerView$8] */
    /* JADX WARN: Type inference failed for: r0v6, types: [com.android.wm.shell.common.split.DividerView$9] */
    /* JADX WARN: Type inference failed for: r0v7, types: [com.android.wm.shell.common.split.DividerView$10] */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.android.wm.shell.common.split.DividerView$11] */
    /* JADX WARN: Type inference failed for: r0v9, types: [com.android.wm.shell.common.split.DividerView$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r4v5, types: [com.android.wm.shell.common.split.DividerView$12] */
    public DividerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        final int i3 = 1;
        this.mSetTouchRegion = true;
        this.mMouseOverAnimatorSet = new AnimatorSet();
        this.mMouseOutAnimatorSet = new AnimatorSet();
        this.mMouseOverRoundedCornerAnimator = new ValueAnimator();
        this.mMouseOutRoundedCornerAnimator = new ValueAnimator();
        this.f439mH = new Handler();
        this.mTouching = false;
        this.mNeedUpdateCursorWhenMoving = true;
        this.mDividerBounds = new Rect();
        this.mTempRect = new Rect();
        this.mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.4
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationCancel(Animator animator) {
                DividerView.this.mSetTouchRegion = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DividerView.this.mSetTouchRegion = true;
            }
        };
        this.mHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.wm.shell.common.split.DividerView.5
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                DividerSnapAlgorithm dividerSnapAlgorithm = DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                if (DividerView.this.isLandscape()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_full)));
                    if (dividerSnapAlgorithm.isFirstSplitTargetAvailable()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_70)));
                    }
                    if (dividerSnapAlgorithm.showMiddleSplitTargetForAccessibility()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_50)));
                    }
                    if (dividerSnapAlgorithm.isLastSplitTargetAvailable()) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_30)));
                    }
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_right_full)));
                    return;
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_full)));
                if (dividerSnapAlgorithm.isFirstSplitTargetAvailable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_70)));
                }
                if (dividerSnapAlgorithm.showMiddleSplitTargetForAccessibility()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_50)));
                }
                if (dividerSnapAlgorithm.isLastSplitTargetAvailable()) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_30)));
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_bottom_full)));
            }

            /* JADX WARN: Removed duplicated region for block: B:6:0x0043  */
            /* JADX WARN: Removed duplicated region for block: B:9:0x0058  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean performAccessibilityAction(View view, int i22, Bundle bundle) {
                DividerSnapAlgorithm.SnapTarget dismissStartTarget;
                DividerView dividerView = DividerView.this;
                DividerSnapAlgorithm dividerSnapAlgorithm = dividerView.mSplitLayout.mDividerSnapAlgorithm;
                if (i22 != 16) {
                    if (i22 == R.id.action_move_tl_full) {
                        dismissStartTarget = dividerSnapAlgorithm.getDismissEndTarget();
                    } else if (i22 == R.id.action_move_tl_70) {
                        dismissStartTarget = dividerSnapAlgorithm.getLastSplitTarget();
                    } else if (i22 == R.id.action_move_tl_50) {
                        dismissStartTarget = dividerSnapAlgorithm.getMiddleTarget();
                    } else if (i22 == R.id.action_move_tl_30) {
                        dismissStartTarget = dividerSnapAlgorithm.getFirstSplitTarget();
                    } else if (i22 == R.id.action_move_rb_full) {
                        dismissStartTarget = dividerSnapAlgorithm.getDismissStartTarget();
                    }
                    if (dismissStartTarget != null) {
                        return super.performAccessibilityAction(view, i22, bundle);
                    }
                    SplitLayout splitLayout = DividerView.this.mSplitLayout;
                    splitLayout.snapToTarget(splitLayout.mDividePosition, dismissStartTarget, false);
                    SplitLayout splitLayout2 = DividerView.this.mSplitLayout;
                    splitLayout2.updateDivideBounds(splitLayout2.mDividePosition);
                    return true;
                }
                dividerView.openDividerPanelIfNeeded();
                dismissStartTarget = null;
                if (dismissStartTarget != null) {
                }
            }
        };
        this.mRoundedCornerUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerView.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DividerView.this.mDividerRoundedCorner.mDividerWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                DividerView.this.mDividerRoundedCorner.invalidate();
            }
        };
        this.mMouseOverAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) {
                DividerView dividerView = DividerView.this;
                C38841 c38841 = DividerView.DIVIDER_HEIGHT_PROPERTY;
                dividerView.updateBackgroundColor(true);
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mDividerRoundedCorner;
                dividerRoundedCorner.mDividerBarBackground.setColor(dividerRoundedCorner.getResources().getColor(17171493, null));
            }
        };
        this.mMouseOutAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                DividerView dividerView = DividerView.this;
                if (dividerView.mTouching) {
                    dividerView.updateCursorType();
                } else {
                    InputManager inputManager = dividerView.mInputManager;
                    if (inputManager != null) {
                        inputManager.setPointerIconType(10121);
                    }
                }
                DividerView.this.updateBackgroundColor(false);
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mDividerRoundedCorner;
                dividerRoundedCorner.mDividerBarBackground.setColor(dividerRoundedCorner.getResources().getColor(17171493, null));
            }
        };
        this.mMouseOver = new Runnable() { // from class: com.android.wm.shell.common.split.DividerView.10
            @Override // java.lang.Runnable
            public final void run() {
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mDividerRoundedCorner;
                dividerRoundedCorner.mDividerWidth = dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
                Property property = DividerView.this.isVerticalDivision() ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                DividerView dividerView = DividerView.this;
                int i22 = dividerView.mSplitLayout.mDividerSize;
                ObjectAnimator ofInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, i22, dividerView.mMouseOverBgScaleSize + i22);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.0f, 1.3f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.0f, 1.3f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOverRoundedCornerAnimator;
                int i32 = dividerView2.mDividerRoundedCorner.mDividerWidth;
                valueAnimator.setIntValues(i32, dividerView2.mMouseOverBgScaleSize + i32);
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOverRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView dividerView4 = DividerView.this;
                dividerView4.mMouseOverAnimatorSet.playTogether(ofInt, ofFloat, ofFloat2, dividerView4.mMouseOverRoundedCornerAnimator);
                DividerView.this.mMouseOverAnimatorSet.setDuration(200L);
                DividerView.this.mMouseOverAnimatorSet.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                DividerView dividerView5 = DividerView.this;
                dividerView5.mMouseOverAnimatorSet.addListener(dividerView5.mMouseOverAnimatorListener);
                DividerView.this.mMouseOverAnimatorSet.start();
                DividerView.this.updateCursorType();
            }
        };
        this.mMouseOut = new Runnable() { // from class: com.android.wm.shell.common.split.DividerView.11
            @Override // java.lang.Runnable
            public final void run() {
                Property property = DividerView.this.isVerticalDivision() ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                DividerView dividerView = DividerView.this;
                int i22 = dividerView.mSplitLayout.mDividerSize;
                ObjectAnimator ofInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, dividerView.mMouseOverBgScaleSize + i22, i22);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.3f, 1.0f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.3f, 1.0f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOutRoundedCornerAnimator;
                DividerRoundedCorner dividerRoundedCorner = dividerView2.mDividerRoundedCorner;
                valueAnimator.setIntValues(dividerRoundedCorner.mDividerWidth, dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width));
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOutRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView dividerView4 = DividerView.this;
                dividerView4.mMouseOutAnimatorSet.playTogether(ofInt, ofFloat, ofFloat2, dividerView4.mMouseOutRoundedCornerAnimator);
                DividerView.this.mMouseOutAnimatorSet.setDuration(200L);
                DividerView.this.mMouseOutAnimatorSet.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                DividerView dividerView5 = DividerView.this;
                dividerView5.mMouseOutAnimatorSet.addListener(dividerView5.mMouseOutAnimatorListener);
                DividerView.this.mMouseOutAnimatorSet.start();
            }
        };
        this.mHandleHoverListener = new View.OnHoverListener(this) { // from class: com.android.wm.shell.common.split.DividerView$$ExternalSyntheticLambda0
            public final /* synthetic */ DividerView f$0;

            {
                this.f$0 = this;
            }

            /* JADX WARN: Failed to find 'out' block for switch in B:2:0x0003. Please report as an issue. */
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                switch (i3) {
                }
                DividerView.$r8$lambda$xjbJZubBp3a6wlufPhmpfDd1Ohw(this.f$0, motionEvent);
                return false;
            }
        };
        this.mMultiSplitHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.wm.shell.common.split.DividerView.12
            /* JADX WARN: Removed duplicated region for block: B:11:0x0045  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x0049  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x002b  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                DividerSnapAlgorithm dividerSnapAlgorithm;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    if (dividerView.mIsCellDivider) {
                        dividerSnapAlgorithm = dividerView.mSplitLayout.getCellSnapAlgorithm();
                        boolean isVerticalDivision2 = DividerView.this.isVerticalDivision();
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(!isVerticalDivision2 ? R.string.accessibility_action_divider_left_full : R.string.accessibility_action_divider_top_full)));
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(!isVerticalDivision2 ? R.string.accessibility_action_divider_right_full : R.string.accessibility_action_divider_bottom_full)));
                        if (!dividerSnapAlgorithm.isMiddleTargetOnly() || DividerView.this.mIsCellDivider) {
                        }
                        int i22 = isVerticalDivision2 ? R.string.accessibility_action_divider_left_percent : R.string.accessibility_action_divider_top_percent;
                        if (dividerSnapAlgorithm.getTargetMinimalRatio() == 30) {
                            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(i22, 70)));
                            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(i22, 50)));
                            accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(i22, 30)));
                            return;
                        } else {
                            if (dividerSnapAlgorithm.getTargetMinimalRatio() == 40) {
                                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_60, ((FrameLayout) DividerView.this).mContext.getString(i22, 60)));
                                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(i22, 50)));
                                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_40, ((FrameLayout) DividerView.this).mContext.getString(i22, 40)));
                                return;
                            }
                            return;
                        }
                    }
                }
                dividerSnapAlgorithm = DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                boolean isVerticalDivision22 = DividerView.this.isVerticalDivision();
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(!isVerticalDivision22 ? R.string.accessibility_action_divider_left_full : R.string.accessibility_action_divider_top_full)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(!isVerticalDivision22 ? R.string.accessibility_action_divider_right_full : R.string.accessibility_action_divider_bottom_full)));
                if (dividerSnapAlgorithm.isMiddleTargetOnly()) {
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:11:0x0063  */
            /* JADX WARN: Removed duplicated region for block: B:26:0x00ad  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x0022  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean performAccessibilityAction(View view, int i22, Bundle bundle) {
                DividerSnapAlgorithm dividerSnapAlgorithm;
                DividerSnapAlgorithm.SnapTarget lastSplitTarget;
                boolean z;
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    if (dividerView.mIsCellDivider) {
                        dividerSnapAlgorithm = dividerView.mSplitLayout.getCellSnapAlgorithm();
                        if (i22 == 16) {
                            if (i22 == R.id.action_move_tl_full) {
                                lastSplitTarget = dividerSnapAlgorithm.getDismissEndTarget();
                            } else if (i22 == R.id.action_move_tl_70 || i22 == R.id.action_move_tl_60) {
                                lastSplitTarget = dividerSnapAlgorithm.getLastSplitTarget();
                            } else if (i22 == R.id.action_move_tl_50) {
                                lastSplitTarget = dividerSnapAlgorithm.getMiddleTarget();
                            } else if (i22 == R.id.action_move_tl_30 || i22 == R.id.action_move_tl_40) {
                                lastSplitTarget = dividerSnapAlgorithm.getFirstSplitTarget();
                            } else if (i22 == R.id.action_move_rb_full) {
                                lastSplitTarget = dividerSnapAlgorithm.getDismissStartTarget();
                            }
                            if (lastSplitTarget == null) {
                                return super.performAccessibilityAction(view, i22, bundle);
                            }
                            if (CoreRune.MW_MULTI_SPLIT_ACCESSIBILITY) {
                                DividerView dividerView2 = DividerView.this;
                                if (dividerView2.mIsCellDivider) {
                                    DividerResizeController dividerResizeController = dividerView2.mDividerResizeController;
                                    if (dividerResizeController.mResizingRequested) {
                                        dividerResizeController.mDividerView = dividerView2;
                                        dividerResizeController.mResizingRequested = true;
                                        dividerResizeController.mIsResizing = true;
                                        DividerResizeController.USE_GUIDE_VIEW_EFFECTS = dividerResizeController.mUseGuideViewByMultiStar;
                                        z = true;
                                    } else {
                                        z = false;
                                    }
                                    if (z) {
                                        SplitLayout splitLayout = dividerView2.mSplitLayout;
                                        splitLayout.snapToTarget(splitLayout.mCellDividePosition, lastSplitTarget, true);
                                        DividerResizeController dividerResizeController2 = DividerView.this.mDividerResizeController;
                                        if (dividerResizeController2.mIsResizing) {
                                            dividerResizeController2.mDividerView = null;
                                            dividerResizeController2.mResizingRequested = false;
                                            dividerResizeController2.mIsResizing = false;
                                        }
                                    }
                                    return true;
                                }
                            }
                            DividerView.this.mSplitLayout.updateDivideBounds(lastSplitTarget.position);
                            SplitLayout splitLayout2 = DividerView.this.mSplitLayout;
                            splitLayout2.snapToTarget(splitLayout2.mDividePosition, lastSplitTarget, false);
                            return true;
                        }
                        DividerView.this.openDividerPanelIfNeeded();
                        lastSplitTarget = null;
                        if (lastSplitTarget == null) {
                        }
                    }
                }
                dividerSnapAlgorithm = DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                if (i22 == 16) {
                }
                lastSplitTarget = null;
                if (lastSplitTarget == null) {
                }
            }
        };
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, C3767R.styleable.DividerView, 0, 0);
        try {
            this.mIsCellDivider = obtainStyledAttributes.getBoolean(3, false);
            obtainStyledAttributes.recycle();
            this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
