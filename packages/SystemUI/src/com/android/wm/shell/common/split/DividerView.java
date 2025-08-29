package com.android.wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Property;
import android.view.GestureDetector;
import android.view.InsetsController;
import android.view.InsetsSource;
import android.view.InsetsState;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.SurfaceControlViewHost;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import androidx.appcompat.widget.AbsActionBarView$$ExternalSyntheticOutline0;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.common.split.DividerResizeController;
import com.android.wm.shell.common.split.DividerResizeLayout;
import com.android.wm.shell.common.split.DividerSnapAlgorithm;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* loaded from: classes3.dex */
public class DividerView extends FrameLayout implements View.OnTouchListener {
    public static final AnonymousClass1 DIVIDER_HEIGHT_PROPERTY = new AnonymousClass1(Integer.class, "height");
    public static final AnonymousClass4 DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
    public static final AnonymousClass3 DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY;
    public final AnonymousClass2 mAnimatorListener;
    public View mBackground;
    public final Rect mBackgroundRect;
    public DividerRoundedCorner mCorners;
    public FrameLayout mDividerBar;
    public final Rect mDividerBounds;
    public DividerPanel mDividerPanel;
    public DividerResizeController mDividerResizeController;
    public GestureDetector mGestureDetector;
    public final Handler mH;
    public DividerHandleView mHandle;
    public final AnonymousClass5 mHandleDelegate;
    public final View.OnHoverListener mHandleHoverListener;
    public int mHandleRegionHeight;
    public int mHandleRegionWidth;
    public boolean mHideHandle;
    public final InputManager mInputManager;
    public boolean mInteractive;
    public final boolean mIsCellDivider;
    public final AnonymousClass11 mMouseOut;
    public final AnonymousClass9 mMouseOutAnimatorListener;
    public final AnimatorSet mMouseOutAnimatorSet;
    public final ValueAnimator mMouseOutRoundedCornerAnimator;
    public final AnonymousClass10 mMouseOver;
    public final AnonymousClass8 mMouseOverAnimatorListener;
    public final AnimatorSet mMouseOverAnimatorSet;
    public int mMouseOverBgScaleSize;
    public final ValueAnimator mMouseOverRoundedCornerAnimator;
    public boolean mMoving;
    public final AnonymousClass12 mMultiSplitHandleDelegate;
    public boolean mNeedUpdateCursorWhenMoving;
    public final AnonymousClass7 mRoundedCornerUpdateListener;
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

    /* renamed from: com.android.wm.shell.common.split.DividerView$1, reason: invalid class name */
    public class AnonymousClass1 extends Property {
        public AnonymousClass1(Class cls, String str) {
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

    public class DoubleTapListener extends GestureDetector.SimpleOnGestureListener {
        public /* synthetic */ DoubleTapListener(DividerView dividerView, int i) {
            this();
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTap(MotionEvent motionEvent) {
            SplitLayout splitLayout = DividerView.this.mSplitLayout;
            if (splitLayout == null) {
                return true;
            }
            AnimatorSet animatorSet = splitLayout.mSwapAnimator;
            if (animatorSet != null && animatorSet.isRunning()) {
                return true;
            }
            ((StageCoordinator) splitLayout.mSplitLayoutHandler).switchSplitPosition(SystemUIAnalytics.DT_COVER_SCREEN_OFF_DOUBLE_TAP);
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public final boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        private DoubleTapListener() {
        }
    }

    public static /* synthetic */ void $r8$lambda$YGxiu2asQy_xuW4RhQgo25UjOYI(DividerView dividerView, MotionEvent motionEvent) {
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
            dividerView.mH.removeCallbacks(dividerView.mMouseOver);
            dividerView.mH.removeCallbacks(dividerView.mMouseOut);
            dividerView.mH.postDelayed(dividerView.mMouseOver, 100L);
        } else {
            if (action != 10) {
                return;
            }
            dividerView.mNeedUpdateCursorWhenMoving = true;
            dividerView.mH.removeCallbacks(dividerView.mMouseOver);
            dividerView.mH.removeCallbacks(dividerView.mMouseOut);
            dividerView.mH.post(dividerView.mMouseOut);
        }
    }

    /* renamed from: -$$Nest$msendTalkBackFeedback, reason: not valid java name */
    public static void m3238$$Nest$msendTalkBackFeedback(DividerView dividerView, int i) {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(((FrameLayout) dividerView).mContext);
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            String string = ((FrameLayout) dividerView).mContext.getString(i);
            accessibilityEventObtain.getText().clear();
            accessibilityEventObtain.getText().add(string);
            accessibilityManager.sendAccessibilityEvent(accessibilityEventObtain);
        }
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.wm.shell.common.split.DividerView$3] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.wm.shell.common.split.DividerView$4] */
    static {
        Class<Integer> cls = Integer.class;
        DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY = new Property(cls, "width") { // from class: com.android.wm.shell.common.split.DividerView.3
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
        DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY = new Property(cls, "height") { // from class: com.android.wm.shell.common.split.DividerView.4
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

    /* JADX WARN: Type inference failed for: r2v10, types: [com.android.wm.shell.common.split.DividerView$5] */
    /* JADX WARN: Type inference failed for: r2v11, types: [com.android.wm.shell.common.split.DividerView$7] */
    /* JADX WARN: Type inference failed for: r2v12, types: [com.android.wm.shell.common.split.DividerView$8] */
    /* JADX WARN: Type inference failed for: r2v13, types: [com.android.wm.shell.common.split.DividerView$9] */
    /* JADX WARN: Type inference failed for: r2v14, types: [com.android.wm.shell.common.split.DividerView$10] */
    /* JADX WARN: Type inference failed for: r2v15, types: [com.android.wm.shell.common.split.DividerView$11] */
    /* JADX WARN: Type inference failed for: r2v17, types: [com.android.wm.shell.common.split.DividerView$12] */
    /* JADX WARN: Type inference failed for: r2v9, types: [com.android.wm.shell.common.split.DividerView$2] */
    public DividerView(Context context) {
        super(context);
        new Paint();
        this.mBackgroundRect = new Rect();
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mSetTouchRegion = true;
        this.mMouseOverAnimatorSet = new AnimatorSet();
        this.mMouseOutAnimatorSet = new AnimatorSet();
        this.mMouseOverRoundedCornerAnimator = new ValueAnimator();
        this.mMouseOutRoundedCornerAnimator = new ValueAnimator();
        this.mH = new Handler();
        this.mTouching = false;
        this.mNeedUpdateCursorWhenMoving = true;
        this.mDividerBounds = new Rect();
        this.mTempRect = new Rect();
        this.mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.2
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
            /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                DividerSnapAlgorithm cellSnapAlgorithm;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    cellSnapAlgorithm = dividerView.mIsCellDivider ? dividerView.mSplitLayout.getCellSnapAlgorithm() : DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                }
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                if (DividerView.this.mSplitLayout.mIsLeftRightSplit) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_full)));
                    DividerSnapAlgorithm.SnapTarget snapTarget = cellSnapAlgorithm.mFirstSplitTarget;
                    DividerSnapAlgorithm.SnapTarget snapTarget2 = cellSnapAlgorithm.mMiddleTarget;
                    if (snapTarget != snapTarget2) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_70)));
                    }
                    if (cellSnapAlgorithm.mTargets.size() - 2 > 1) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_50)));
                    }
                    if (cellSnapAlgorithm.mLastSplitTarget != snapTarget2) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_30)));
                    }
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_right_full)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_swap_apps, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_swap_horizontal)));
                    return;
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_full)));
                DividerSnapAlgorithm.SnapTarget snapTarget3 = cellSnapAlgorithm.mFirstSplitTarget;
                DividerSnapAlgorithm.SnapTarget snapTarget4 = cellSnapAlgorithm.mMiddleTarget;
                if (snapTarget3 != snapTarget4) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_70)));
                }
                if (cellSnapAlgorithm.mTargets.size() - 2 > 1) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_50)));
                }
                if (cellSnapAlgorithm.mLastSplitTarget != snapTarget4) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_30)));
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_bottom_full)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_swap_apps, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_swap_vertical)));
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0030  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x00d8  */
            /* JADX WARN: Removed duplicated region for block: B:55:0x00f1  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                DividerSnapAlgorithm cellSnapAlgorithm;
                DividerSnapAlgorithm.SnapTarget snapTarget;
                DividerSnapAlgorithm.SnapTarget snapTarget2;
                if (i == R.id.action_swap_apps) {
                    SplitLayout splitLayout = DividerView.this.mSplitLayout;
                    AnimatorSet animatorSet = splitLayout.mSwapAnimator;
                    if (animatorSet != null && animatorSet.isRunning()) {
                        return true;
                    }
                    ((StageCoordinator) splitLayout.mSplitLayoutHandler).switchSplitPosition(SystemUIAnalytics.DT_COVER_SCREEN_OFF_DOUBLE_TAP);
                    return true;
                }
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    cellSnapAlgorithm = dividerView.mIsCellDivider ? dividerView.mSplitLayout.getCellSnapAlgorithm() : DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                }
                if (i != 16) {
                    if (i == R.id.action_move_tl_full) {
                        snapTarget = cellSnapAlgorithm.mDismissEndTarget;
                        DividerView dividerView2 = DividerView.this;
                        if (dividerView2.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView2, R.string.accessibility_action_divider_left_full);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView2, R.string.accessibility_action_divider_top_full);
                        }
                    } else if (i == R.id.action_move_tl_70) {
                        snapTarget = cellSnapAlgorithm.mLastSplitTarget;
                        DividerView dividerView3 = DividerView.this;
                        if (dividerView3.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView3, R.string.accessibility_action_divider_left_70);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView3, R.string.accessibility_action_divider_top_70);
                        }
                    } else if (i == R.id.action_move_tl_50) {
                        snapTarget = cellSnapAlgorithm.mMiddleTarget;
                        DividerView dividerView4 = DividerView.this;
                        if (dividerView4.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView4, R.string.accessibility_action_divider_left_50);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView4, R.string.accessibility_action_divider_top_50);
                        }
                    } else if (i == R.id.action_move_tl_30) {
                        snapTarget = cellSnapAlgorithm.mFirstSplitTarget;
                        DividerView dividerView5 = DividerView.this;
                        if (dividerView5.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView5, R.string.accessibility_action_divider_left_30);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView5, R.string.accessibility_action_divider_top_30);
                        }
                    } else if (i == R.id.action_move_rb_full) {
                        snapTarget = cellSnapAlgorithm.mDismissStartTarget;
                        DividerView dividerView6 = DividerView.this;
                        if (dividerView6.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView6, R.string.accessibility_action_divider_right_full);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView6, R.string.accessibility_action_divider_bottom_full);
                        }
                    }
                    snapTarget2 = snapTarget;
                    if (snapTarget2 != null) {
                        return super.performAccessibilityAction(view, i, bundle);
                    }
                    SplitLayout splitLayout2 = DividerView.this.mSplitLayout;
                    splitLayout2.snapToTarget(splitLayout2.mDividerPosition, snapTarget2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, Interpolators.FAST_OUT_SLOW_IN, false);
                    SplitLayout splitLayout3 = DividerView.this.mSplitLayout;
                    splitLayout3.updateDividerBounds(splitLayout3.mDividerPosition, true, false);
                    return true;
                }
                DividerView.this.openDividerPanelIfNeeded();
                snapTarget = null;
                snapTarget2 = snapTarget;
                if (snapTarget2 != null) {
                }
            }
        };
        this.mRoundedCornerUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerView.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DividerView.this.mCorners.mDividerWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                DividerView.this.mCorners.invalidate();
            }
        };
        this.mMouseOverAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) throws Resources.NotFoundException {
                DividerView dividerView = DividerView.this;
                AnonymousClass1 anonymousClass1 = DividerView.DIVIDER_HEIGHT_PROPERTY;
                dividerView.updateBackgroundColor(true);
                DividerView.this.mCorners.mDividerBarBackground.setColor(0);
            }
        };
        this.mMouseOutAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
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
                DividerView.this.mCorners.mDividerBarBackground.setColor(0);
            }
        };
        this.mMouseOver = new Runnable() { // from class: com.android.wm.shell.common.split.DividerView.10
            /* JADX WARN: Removed duplicated region for block: B:16:0x0034  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                Property property;
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mCorners;
                dividerRoundedCorner.mDividerWidth = dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
                DividerView dividerView = DividerView.this;
                if (!dividerView.mIsCellDivider) {
                    property = dividerView.mSplitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                } else if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                    SplitLayout splitLayout = dividerView.mSplitLayout;
                    property = splitLayout.mParallelMultiSplit ? splitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY : !dividerView.mSplitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                }
                int i = dividerView.mSplitLayout.mDividerSize;
                ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, i, dividerView.mMouseOverBgScaleSize + i);
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.0f, 1.3f);
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.0f, 1.3f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOverRoundedCornerAnimator;
                int i2 = dividerView2.mCorners.mDividerWidth;
                valueAnimator.setIntValues(i2, dividerView2.mMouseOverBgScaleSize + i2);
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOverRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView dividerView4 = DividerView.this;
                dividerView4.mMouseOverAnimatorSet.playTogether(objectAnimatorOfInt, objectAnimatorOfFloat, objectAnimatorOfFloat2, dividerView4.mMouseOverRoundedCornerAnimator);
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
                DividerView dividerView = DividerView.this;
                SplitLayout splitLayout = dividerView.mSplitLayout;
                Property property = !splitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                int i = splitLayout.mDividerSize;
                ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, dividerView.mMouseOverBgScaleSize + i, i);
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.3f, 1.0f);
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.3f, 1.0f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOutRoundedCornerAnimator;
                DividerRoundedCorner dividerRoundedCorner = dividerView2.mCorners;
                valueAnimator.setIntValues(dividerRoundedCorner.mDividerWidth, dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width));
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOutRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView.this.mMouseOutAnimatorSet.playTogether(objectAnimatorOfInt, objectAnimatorOfFloat, objectAnimatorOfFloat2);
                DividerView.this.mMouseOutAnimatorSet.setDuration(200L);
                DividerView.this.mMouseOutAnimatorSet.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                DividerView dividerView4 = DividerView.this;
                dividerView4.mMouseOutAnimatorSet.addListener(dividerView4.mMouseOutAnimatorListener);
                DividerView.this.mMouseOutAnimatorSet.start();
            }
        };
        this.mHandleHoverListener = new View.OnHoverListener() { // from class: com.android.wm.shell.common.split.DividerView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                DividerView.$r8$lambda$YGxiu2asQy_xuW4RhQgo25UjOYI(this.f$0, motionEvent);
                return false;
            }
        };
        this.mMultiSplitHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.wm.shell.common.split.DividerView.12
            /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                DividerSnapAlgorithm cellSnapAlgorithm;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    cellSnapAlgorithm = dividerView.mIsCellDivider ? dividerView.mSplitLayout.getCellSnapAlgorithm() : DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                }
                boolean zIsVerticalDivision = DividerView.this.isVerticalDivision();
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(zIsVerticalDivision ? R.string.accessibility_action_divider_left_full : R.string.accessibility_action_divider_top_full)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(zIsVerticalDivision ? R.string.accessibility_action_divider_right_full : R.string.accessibility_action_divider_bottom_full)));
                if (cellSnapAlgorithm.mTargets.size() == 3 || DividerView.this.mIsCellDivider) {
                    return;
                }
                int i = zIsVerticalDivision ? R.string.accessibility_action_divider_left_percent : R.string.accessibility_action_divider_top_percent;
                if (cellSnapAlgorithm.getTargetMinimalRatio() == 30) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(i, 70)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(i, 50)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(i, 30)));
                } else if (cellSnapAlgorithm.getTargetMinimalRatio() == 40) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_60, ((FrameLayout) DividerView.this).mContext.getString(i, 60)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(i, 50)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_40, ((FrameLayout) DividerView.this).mContext.getString(i, 40)));
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:35:0x005b  */
            /* JADX WARN: Removed duplicated region for block: B:47:0x00aa  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0011  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                DividerSnapAlgorithm cellSnapAlgorithm;
                DividerSnapAlgorithm.SnapTarget snapTarget;
                DividerSnapAlgorithm.SnapTarget snapTarget2;
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    cellSnapAlgorithm = dividerView.mIsCellDivider ? dividerView.mSplitLayout.getCellSnapAlgorithm() : DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                }
                if (i != 16) {
                    if (i == R.id.action_move_tl_full) {
                        snapTarget = cellSnapAlgorithm.mDismissEndTarget;
                    } else if (i == R.id.action_move_tl_70 || i == R.id.action_move_tl_60) {
                        snapTarget = cellSnapAlgorithm.mLastSplitTarget;
                    } else if (i == R.id.action_move_tl_50) {
                        snapTarget = cellSnapAlgorithm.mMiddleTarget;
                    } else if (i == R.id.action_move_tl_30 || i == R.id.action_move_tl_40) {
                        snapTarget = cellSnapAlgorithm.mFirstSplitTarget;
                    } else if (i == R.id.action_move_rb_full) {
                        snapTarget = cellSnapAlgorithm.mDismissStartTarget;
                    }
                    snapTarget2 = snapTarget;
                    if (snapTarget2 != null) {
                        return super.performAccessibilityAction(view, i, bundle);
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
                                SplitLayout splitLayout = dividerView2.mSplitLayout;
                                splitLayout.snapToTarget(splitLayout.mCellDividerPosition, snapTarget2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, Interpolators.FAST_OUT_SLOW_IN, false);
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
                    DividerView.this.mSplitLayout.updateDividerBounds(snapTarget2.position, true, false);
                    SplitLayout splitLayout2 = DividerView.this.mSplitLayout;
                    splitLayout2.snapToTarget(splitLayout2.mDividerPosition, snapTarget2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, Interpolators.FAST_OUT_SLOW_IN, false);
                    return true;
                }
                DividerView.this.openDividerPanelIfNeeded();
                snapTarget2 = null;
                if (snapTarget2 != null) {
                }
            }
        };
    }

    public final int getCurrentPosition() {
        return (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) ? this.mSplitLayout.mCellDividerPosition : this.mSplitLayout.mDividerPosition;
    }

    public final boolean isVerticalDivision() {
        SplitLayout splitLayout;
        return (!CoreRune.MW_MULTI_SPLIT_DIVIDER || (splitLayout = this.mSplitLayout) == null) ? getResources().getConfiguration().orientation == 2 : (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) ? (CoreRune.MW_PARALLEL_MULTI_SPLIT && splitLayout.mParallelMultiSplit) ? splitLayout.isVerticalDivision() : !splitLayout.isVerticalDivision() : splitLayout.isVerticalDivision();
    }

    @Override // android.view.View
    public final void onFinishInflate() throws Resources.NotFoundException {
        super.onFinishInflate();
        this.mDividerBar = (FrameLayout) findViewById(R.id.divider_bar);
        this.mHandle = (DividerHandleView) findViewById(R.id.docked_divider_handle);
        this.mCorners = (DividerRoundedCorner) findViewById(R.id.docked_divider_rounded_corner);
        this.mBackground = findViewById(R.id.docked_divider_background);
        updateBackgroundColor(false);
        this.mHandle.setOnTouchListener(this);
        this.mMouseOverBgScaleSize = getResources().getDimensionPixelSize(R.dimen.split_divider_handle_mouse_over_scale_size);
        this.mHandle.setOnHoverListener(this.mHandleHoverListener);
        this.mTouchElevation = getResources().getDimensionPixelSize(R.dimen.docked_stack_divider_lift_elevation);
        new GestureDetector(getContext(), new DoubleTapListener(this, 0));
        this.mInteractive = true;
        this.mHideHandle = false;
        setOnTouchListener(this);
        if (CoreRune.MW_MULTI_SPLIT_ACCESSIBILITY) {
            this.mHandle.setAccessibilityDelegate(this.mMultiSplitHandleDelegate);
        } else {
            this.mHandle.setAccessibilityDelegate(this.mHandleDelegate);
        }
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        if (!DeviceConfig.getBoolean("systemui", "cursor_hover_states_enabled", false)) {
            return false;
        }
        if (motionEvent.getAction() == 9) {
            setHovering();
            return true;
        }
        if (motionEvent.getAction() != 10) {
            return false;
        }
        releaseHovering();
        return true;
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
            for (int iSourceSize = insetsState.sourceSize() - 1; iSourceSize >= 0; iSourceSize--) {
                InsetsSource insetsSourceSourceAt = insetsState.sourceAt(iSourceSize);
                if (insetsSourceSourceAt.getType() == WindowInsets.Type.navigationBars() && insetsSourceSourceAt.hasFlags(2)) {
                    Rect rect2 = this.mTempRect;
                    rect2.inset(insetsSourceSourceAt.calculateVisibleInsets(rect2));
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
            ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(this, DIVIDER_HEIGHT_PROPERTY, this.mDividerBounds.height(), this.mTempRect.height());
            objectAnimatorOfInt.setInterpolator(InsetsController.RESIZE_INTERPOLATOR);
            objectAnimatorOfInt.setDuration(300L);
            objectAnimatorOfInt.addListener(this.mAnimatorListener);
            objectAnimatorOfInt.start();
        } else {
            DIVIDER_HEIGHT_PROPERTY.set(this, Integer.valueOf(this.mTempRect.height()));
            this.mSetTouchRegion = true;
        }
        this.mDividerBounds.set(this.mTempRect);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        super.onLayout(z, i, i2, i3, i4);
        if (this.mSetTouchRegion) {
            int iWidth = (this.mDividerBounds.width() - this.mHandleRegionWidth) / 2;
            int iHeight = this.mDividerBounds.height();
            int i5 = this.mHandleRegionHeight;
            int i6 = (iHeight - i5) / 2;
            this.mTempRect.set(iWidth, i6, this.mHandleRegionWidth + iWidth, i5 + i6);
            this.mSplitWindowManager.setTouchRegion(this.mTempRect);
            this.mSetTouchRegion = false;
        }
        if (z) {
            boolean z2 = this.mSplitLayout.mIsLeftRightSplit;
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
                z2 = !z2;
            }
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
            int width = z2 ? (getWidth() - dimensionPixelSize) / 2 : 0;
            int height = z2 ? 0 : (getHeight() - dimensionPixelSize) / 2;
            this.mBackgroundRect.set(width, height, z2 ? width + dimensionPixelSize : getWidth(), z2 ? getHeight() : height + dimensionPixelSize);
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x001c  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        int i2 = 20007;
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                SplitLayout splitLayout = this.mSplitLayout;
                if (splitLayout.mParallelMultiSplit) {
                    if (splitLayout.mIsLeftRightSplit) {
                    }
                } else if (!this.mSplitLayout.mIsLeftRightSplit) {
                }
            }
        } else if (this.mSplitLayout.mIsLeftRightSplit) {
            i2 = 20006;
        }
        return PointerIcon.getSystemIcon(getContext(), i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:125:0x0355  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x036a  */
    /* JADX WARN: Removed duplicated region for block: B:146:0x0387  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x038b  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0392  */
    /* JADX WARN: Removed duplicated region for block: B:158:0x03b1  */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03fa  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00ff  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0213  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0272  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean onTouch(View view, MotionEvent motionEvent) throws Resources.NotFoundException {
        DividerResizeController.ResizeAlgorithm resizeAlgorithm;
        int i;
        int iMin;
        int i2;
        int size;
        boolean z;
        ImageView imageView;
        int i3;
        boolean z2;
        DividerView dividerView;
        int iWidth;
        int iHeight;
        DividerView dividerView2;
        if (this.mSplitLayout == null || !this.mInteractive) {
            return false;
        }
        this.mGestureDetector.onTouchEvent(motionEvent);
        motionEvent.setLocation(motionEvent.getRawX(), motionEvent.getRawY());
        int action = motionEvent.getAction() & 255;
        boolean zIsVerticalDivision = CoreRune.MW_MULTI_SPLIT_DIVIDER ? isVerticalDivision() : this.mSplitLayout.mIsLeftRightSplit;
        int x = (int) (zIsVerticalDivision ? motionEvent.getX() : motionEvent.getY());
        if (action != 0 && this.mVelocityTracker == null) {
            return false;
        }
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    this.mVelocityTracker.addMovement(motionEvent);
                    if (!this.mMoving && Math.abs(x - this.mStartPos) > this.mTouchSlop) {
                        DividerResizeController dividerResizeController = this.mDividerResizeController;
                        SplitLayout splitLayout = this.mSplitLayout;
                        if (!dividerResizeController.mResizingRequested) {
                            DividerResizeController.USE_GUIDE_VIEW_EFFECTS = dividerResizeController.mUseGuideViewByMultiStar;
                            dividerResizeController.mStageCoordinator.closeHandleMenuIfNeeded();
                            dividerResizeController.mResizingRequested = true;
                            dividerResizeController.mSplitLayout = splitLayout;
                            dividerResizeController.mDividerView = this;
                            dividerResizeController.mDividerSize = splitLayout.mDividerSize;
                            dividerResizeController.mIsHorizontalDivision = !isVerticalDivision();
                            dividerResizeController.mCurrentDividerPosition = dividerResizeController.mDividerView.getCurrentPosition();
                            dividerResizeController.mDefaultHandleMoveThreshold = dividerResizeController.mContext.getResources().getDimensionPixelSize(R.dimen.mw_divider_handle_move_threshold_default);
                            dividerResizeController.mDividerResizeLayout = (DividerResizeLayout) dividerResizeController.mLayoutInflater.inflate(R.layout.divider_resize_layout, (ViewGroup) null);
                            boolean z3 = CoreRune.MW_MULTI_SPLIT_NATURAL_RESIZING;
                            if (z3) {
                                boolean zIsMultiSplitActive = dividerResizeController.mStageCoordinator.isMultiSplitActive();
                                dividerResizeController.mIsMultiSplitActive = zIsMultiSplitActive;
                                if (zIsMultiSplitActive) {
                                    dividerResizeController.mHalfSplitStageType = dividerResizeController.mStageCoordinator.getCellHostStageType() == 0 ? 1 : 0;
                                }
                            }
                            DividerResizeController.ResizeAlgorithm resizeAlgorithm2 = dividerResizeController.mResizeAlgorithm;
                            resizeAlgorithm2.getClass();
                            DividerResizeController dividerResizeController2 = DividerResizeController.this;
                            resizeAlgorithm2.mDividerSnapAlgorithm = (z3 && (dividerView2 = dividerResizeController2.mDividerView) != null && dividerView2.mIsCellDivider) ? dividerResizeController2.mSplitLayout.getCellSnapAlgorithm() : dividerResizeController2.mSplitLayout.mDividerSnapAlgorithm;
                            if (!CoreRune.MW_PARALLEL_MULTI_SPLIT || ((dividerView = dividerResizeController2.mDividerView) != null && dividerView.mIsCellDivider)) {
                                i3 = 2;
                                resizeAlgorithm2.mDisplaySize = !dividerResizeController2.mIsHorizontalDivision ? dividerResizeController2.mSplitLayout.getRootBounds().height() : dividerResizeController2.mSplitLayout.getRootBounds().width();
                                DividerSnapAlgorithm dividerSnapAlgorithm = resizeAlgorithm2.mDividerSnapAlgorithm;
                                resizeAlgorithm2.mFirstSplitTargetPosition = dividerSnapAlgorithm.mFirstSplitTarget.position;
                                resizeAlgorithm2.mMiddleTargetPosition = dividerSnapAlgorithm.mMiddleTarget.position;
                                resizeAlgorithm2.mLastSplitTargetPosition = dividerSnapAlgorithm.mLastSplitTarget.position;
                                resizeAlgorithm2.mDismissStartThreshold = ((resizeAlgorithm2.mFirstSplitTargetPosition - resizeAlgorithm2.mDividerSnapAlgorithm.getStartInset()) / 2) + dividerSnapAlgorithm.getStartInset();
                                int i4 = resizeAlgorithm2.mDividerSnapAlgorithm.mDismissEndTarget.position;
                                resizeAlgorithm2.mDismissEndTargetPosition = i4;
                                resizeAlgorithm2.mDismissEndThreshold = i4 - ((i4 - resizeAlgorithm2.mLastSplitTargetPosition) / 2);
                                if (z3 || !dividerResizeController2.mIsMultiSplitActive) {
                                    int i5 = (int) (r2.getStashStartTarget().position * 0.7f);
                                    resizeAlgorithm2.mDismissStartThreshold = resizeAlgorithm2.mDividerSnapAlgorithm.getStashStartTarget().position - i5;
                                    resizeAlgorithm2.mDismissEndThreshold = resizeAlgorithm2.mDividerSnapAlgorithm.getStashEndTarget().position + i5;
                                    int i6 = resizeAlgorithm2.mFirstSplitTargetPosition;
                                    resizeAlgorithm2.mSplitStashStartThreshold = i6 - ((i6 - resizeAlgorithm2.mDismissStartThreshold) / 2);
                                    int i7 = resizeAlgorithm2.mLastSplitTargetPosition;
                                    resizeAlgorithm2.mSplitStashEndThreshold = AbsActionBarView$$ExternalSyntheticOutline0.m(resizeAlgorithm2.mDismissEndTargetPosition, i7, i3, i7);
                                    resizeAlgorithm2.mSplitStashStartPosition = resizeAlgorithm2.mDividerSnapAlgorithm.getStashStartTarget().position;
                                    resizeAlgorithm2.mSplitStashEndPosition = resizeAlgorithm2.mDividerSnapAlgorithm.getStashEndTarget().position;
                                } else {
                                    resizeAlgorithm2.mSplitStashStartThreshold = 0;
                                    resizeAlgorithm2.mSplitStashEndThreshold = 0;
                                    resizeAlgorithm2.mSplitStashStartPosition = 0;
                                    resizeAlgorithm2.mSplitStashEndPosition = 0;
                                }
                                resizeAlgorithm2.mFirstFadeOutPosition = resizeAlgorithm2.mDismissStartThreshold + ((int) ((resizeAlgorithm2.mFirstSplitTargetPosition - r2) * 0.625f));
                                resizeAlgorithm2.mLastFadeOutPosition = resizeAlgorithm2.mDismissEndThreshold - ((int) ((r2 - resizeAlgorithm2.mLastSplitTargetPosition) * 0.625f));
                                Log.d("DividerResizeController", "ResizeAlgorithm_init: " + resizeAlgorithm2);
                                dividerResizeController.mDividerResizeLayout.init(this, dividerResizeController.mSplitLayout, dividerResizeController.mStageCoordinator, resizeAlgorithm2);
                                z2 = true;
                            } else {
                                SplitLayout splitLayout2 = dividerResizeController2.mSplitLayout;
                                if (splitLayout2.mParallelMultiSplit) {
                                    Rect rect = new Rect(splitLayout2.getDisplayStableInsets(splitLayout2.mContext));
                                    Rect rect2 = new Rect();
                                    i3 = 2;
                                    CellUtil.getCellAndHostArea(splitLayout2.mCellStageWindowConfigPosition, splitLayout2.getTopLeftBounds(), splitLayout2.getBottomRightBounds(), splitLayout2.mHostAndCellArea, splitLayout2.isVerticalDivision());
                                    rect2.set(splitLayout2.getTopLeftBounds().equals(splitLayout2.mHostAndCellArea) ? splitLayout2.getBottomRightBounds() : splitLayout2.getTopLeftBounds());
                                    if (splitLayout2.isVerticalDivision()) {
                                        if (splitLayout2.getTopLeftBounds().equals(rect2)) {
                                            rect2.left -= rect.left;
                                        } else {
                                            rect2.right += rect.right;
                                        }
                                        rect2.top -= rect.top;
                                        rect2.bottom += rect.bottom;
                                    } else {
                                        if (splitLayout2.getTopLeftBounds().equals(rect2)) {
                                            rect2.top -= rect.top;
                                        } else {
                                            rect2.bottom += rect.bottom;
                                        }
                                        rect2.left -= rect.left;
                                        rect2.right += rect.right;
                                    }
                                    if (splitLayout2.getTopLeftBounds().equals(splitLayout2.mHostAndCellArea)) {
                                        if (splitLayout2.isVerticalDivision()) {
                                            int i8 = splitLayout2.mCellDividerPosition;
                                            rect.left = i8;
                                            rect2.left = i8;
                                        } else {
                                            int i9 = splitLayout2.mCellDividerPosition;
                                            rect.top = i9;
                                            rect2.top = i9;
                                        }
                                        iWidth = splitLayout2.mRootBounds.width();
                                        iHeight = splitLayout2.mRootBounds.height();
                                    } else {
                                        if (splitLayout2.isVerticalDivision()) {
                                            rect2.right = splitLayout2.mCellDividerPosition;
                                        } else {
                                            rect2.bottom = splitLayout2.mCellDividerPosition;
                                        }
                                        iWidth = rect2.width();
                                        iHeight = rect2.height();
                                    }
                                    resizeAlgorithm2.mDividerSnapAlgorithm = new DividerSnapAlgorithm(splitLayout2.mContext.getResources(), iWidth, iHeight, splitLayout2.mDividerSize, splitLayout2.mIsLeftRightSplit, rect, splitLayout2.mPinnedTaskbarInsets.toRect(), DockedDividerUtils.getDockSide(rect2, splitLayout2.mRootBounds), false, true, true, true);
                                }
                                resizeAlgorithm2.mDisplaySize = !dividerResizeController2.mIsHorizontalDivision ? dividerResizeController2.mSplitLayout.getRootBounds().height() : dividerResizeController2.mSplitLayout.getRootBounds().width();
                                DividerSnapAlgorithm dividerSnapAlgorithm2 = resizeAlgorithm2.mDividerSnapAlgorithm;
                                resizeAlgorithm2.mFirstSplitTargetPosition = dividerSnapAlgorithm2.mFirstSplitTarget.position;
                                resizeAlgorithm2.mMiddleTargetPosition = dividerSnapAlgorithm2.mMiddleTarget.position;
                                resizeAlgorithm2.mLastSplitTargetPosition = dividerSnapAlgorithm2.mLastSplitTarget.position;
                                resizeAlgorithm2.mDismissStartThreshold = ((resizeAlgorithm2.mFirstSplitTargetPosition - resizeAlgorithm2.mDividerSnapAlgorithm.getStartInset()) / 2) + dividerSnapAlgorithm2.getStartInset();
                                int i42 = resizeAlgorithm2.mDividerSnapAlgorithm.mDismissEndTarget.position;
                                resizeAlgorithm2.mDismissEndTargetPosition = i42;
                                resizeAlgorithm2.mDismissEndThreshold = i42 - ((i42 - resizeAlgorithm2.mLastSplitTargetPosition) / 2);
                                if (z3) {
                                    int i52 = (int) (r2.getStashStartTarget().position * 0.7f);
                                    resizeAlgorithm2.mDismissStartThreshold = resizeAlgorithm2.mDividerSnapAlgorithm.getStashStartTarget().position - i52;
                                    resizeAlgorithm2.mDismissEndThreshold = resizeAlgorithm2.mDividerSnapAlgorithm.getStashEndTarget().position + i52;
                                    int i62 = resizeAlgorithm2.mFirstSplitTargetPosition;
                                    resizeAlgorithm2.mSplitStashStartThreshold = i62 - ((i62 - resizeAlgorithm2.mDismissStartThreshold) / 2);
                                    int i72 = resizeAlgorithm2.mLastSplitTargetPosition;
                                    resizeAlgorithm2.mSplitStashEndThreshold = AbsActionBarView$$ExternalSyntheticOutline0.m(resizeAlgorithm2.mDismissEndTargetPosition, i72, i3, i72);
                                    resizeAlgorithm2.mSplitStashStartPosition = resizeAlgorithm2.mDividerSnapAlgorithm.getStashStartTarget().position;
                                    resizeAlgorithm2.mSplitStashEndPosition = resizeAlgorithm2.mDividerSnapAlgorithm.getStashEndTarget().position;
                                    resizeAlgorithm2.mFirstFadeOutPosition = resizeAlgorithm2.mDismissStartThreshold + ((int) ((resizeAlgorithm2.mFirstSplitTargetPosition - r2) * 0.625f));
                                    resizeAlgorithm2.mLastFadeOutPosition = resizeAlgorithm2.mDismissEndThreshold - ((int) ((r2 - resizeAlgorithm2.mLastSplitTargetPosition) * 0.625f));
                                    Log.d("DividerResizeController", "ResizeAlgorithm_init: " + resizeAlgorithm2);
                                    dividerResizeController.mDividerResizeLayout.init(this, dividerResizeController.mSplitLayout, dividerResizeController.mStageCoordinator, resizeAlgorithm2);
                                    z2 = true;
                                }
                            }
                            return true;
                        }
                        Log.w("DividerResizeController", "startResizing: failed, already resizing state!");
                        z2 = true;
                        this.mMoving = z2;
                    }
                    if (this.mMoving) {
                        if (motionEvent.getToolType(0) == 3 && this.mNeedUpdateCursorWhenMoving) {
                            updateCursorType();
                            this.mNeedUpdateCursorWhenMoving = false;
                        }
                        int currentPosition = (getCurrentPosition() + x) - this.mStartPos;
                        DividerResizeController dividerResizeController3 = this.mDividerResizeController;
                        if (dividerResizeController3.mResizingRequested && !dividerResizeController3.mIsFinishing) {
                            if (dividerResizeController3.mIsResizing) {
                                resizeAlgorithm = dividerResizeController3.mResizeAlgorithm;
                                DividerResizeController.ResizeAlgorithm.m3236$$Nest$mupdate(resizeAlgorithm, currentPosition);
                                if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                                }
                                iMin = resizeAlgorithm.mTouchPosition;
                                i2 = resizeAlgorithm.mFirstSplitTargetPosition;
                                if (iMin >= i2) {
                                    if (iMin >= resizeAlgorithm.mDismissStartThreshold) {
                                    }
                                    DividerResizeController dividerResizeController4 = DividerResizeController.this;
                                    if (!z) {
                                    }
                                    DividerResizeLayout dividerResizeLayout = dividerResizeController3.mDividerResizeLayout;
                                    int i10 = resizeAlgorithm.mSplitDismissSide;
                                    dividerResizeLayout.setAlpha(1.0f);
                                    while (size >= 0) {
                                    }
                                }
                            } else if (Math.abs(dividerResizeController3.mCurrentDividerPosition - currentPosition) > dividerResizeController3.mDefaultHandleMoveThreshold) {
                                dividerResizeController3.mIsResizing = true;
                                ListPopupWindow$$ExternalSyntheticOutline0.m(currentPosition, "validateMoveEvent: start move divider, pos=", "DividerResizeController");
                                if (CoreRune.MW_SA_LOGGING && motionEvent.getToolType(0) == 3) {
                                    CoreSaLogger.logForAdvanced("1000", "From Mouse snapping");
                                }
                                resizeAlgorithm = dividerResizeController3.mResizeAlgorithm;
                                DividerResizeController.ResizeAlgorithm.m3236$$Nest$mupdate(resizeAlgorithm, currentPosition);
                                if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS) {
                                    i = resizeAlgorithm.mTouchPosition;
                                    int i11 = resizeAlgorithm.mDismissStartThreshold;
                                    if ((i < i11) || i > (i11 = resizeAlgorithm.mDismissEndThreshold)) {
                                        i = i11;
                                    }
                                } else {
                                    i = resizeAlgorithm.isInStartStashZone() ? resizeAlgorithm.mSplitStashStartPosition : resizeAlgorithm.isInEndStashZone() ? resizeAlgorithm.mSplitStashEndPosition : resizeAlgorithm.mTouchPosition;
                                }
                                iMin = resizeAlgorithm.mTouchPosition;
                                i2 = resizeAlgorithm.mFirstSplitTargetPosition;
                                if (iMin >= i2 || iMin > resizeAlgorithm.mLastSplitTargetPosition) {
                                    boolean z4 = iMin >= resizeAlgorithm.mDismissStartThreshold;
                                    DividerResizeController dividerResizeController42 = DividerResizeController.this;
                                    iMin = !z4 ? (CoreRune.MW_PARALLEL_MULTI_SPLIT && dividerResizeController42.mSplitLayout.mParallelMultiSplit) ? resizeAlgorithm.mDividerSnapAlgorithm.mDismissStartTarget.position : Math.min(-dividerResizeController42.mDividerSize, resizeAlgorithm.mDividerSnapAlgorithm.mDismissStartTarget.position) : iMin > resizeAlgorithm.mDismissEndThreshold ? (CoreRune.MW_PARALLEL_MULTI_SPLIT && dividerResizeController42.mSplitLayout.mParallelMultiSplit) ? resizeAlgorithm.mDividerSnapAlgorithm.mDismissEndTarget.position : Math.max(resizeAlgorithm.mDisplaySize, resizeAlgorithm.mDividerSnapAlgorithm.mDismissEndTarget.position) : (iMin >= i2 && iMin <= (i2 = resizeAlgorithm.mLastSplitTargetPosition)) ? resizeAlgorithm.mDividerSnapAlgorithm.calculateSnapTarget(iMin, false).position : i2;
                                }
                                DividerResizeLayout dividerResizeLayout2 = dividerResizeController3.mDividerResizeLayout;
                                int i102 = resizeAlgorithm.mSplitDismissSide;
                                dividerResizeLayout2.setAlpha(1.0f);
                                for (size = dividerResizeLayout2.mResizeTargets.size() - 1; size >= 0; size--) {
                                    final DividerResizeLayout.DividerResizeTarget dividerResizeTarget = (DividerResizeLayout.DividerResizeTarget) dividerResizeLayout2.mResizeTargets.valueAt(size);
                                    if (dividerResizeTarget != null) {
                                        if (i102 == 0) {
                                            dividerResizeTarget.calculateBoundsForPosition(i, dividerResizeTarget.mTmpBounds);
                                            dividerResizeTarget.mEndBounds.set(dividerResizeTarget.mTmpBounds);
                                            z = true;
                                            dividerResizeTarget.mShouldPlayHaptic = true;
                                        } else {
                                            z = true;
                                            dividerResizeTarget.calculateBoundsForPosition(iMin, dividerResizeTarget.mTmpBounds);
                                            dividerResizeTarget.mEndBounds.set(dividerResizeTarget.mTmpBounds);
                                        }
                                        boolean z5 = dividerResizeTarget.mIsResizing;
                                        DividerResizeLayout dividerResizeLayout3 = DividerResizeLayout.this;
                                        if (!z5) {
                                            dividerResizeTarget.mIsResizing = z;
                                            dividerResizeTarget.mView.setAlpha(1.0f);
                                            dividerResizeTarget.mView.setVisibility(0);
                                            dividerResizeTarget.mBlurView.setVisibility(0);
                                            if (DividerResizeController.USE_GUIDE_VIEW_EFFECTS && (imageView = dividerResizeLayout3.mGuideBarView) != null) {
                                                imageView.setVisibility(0);
                                            }
                                            dividerResizeTarget.startBoundsAnimation(dividerResizeTarget.mEndBounds, true, 300L);
                                            ValueAnimator valueAnimator = dividerResizeTarget.mBlurAnimator;
                                            if (valueAnimator != null) {
                                                valueAnimator.end();
                                            }
                                            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                                            dividerResizeTarget.mBlurAnimator = valueAnimatorOfFloat;
                                            valueAnimatorOfFloat.addUpdateListener(new DividerResizeLayout$$ExternalSyntheticLambda3(dividerResizeTarget, 1));
                                            dividerResizeTarget.mBlurAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerResizeLayout.DividerResizeTarget.2
                                                public AnonymousClass2() {
                                                }

                                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                                public final void onAnimationEnd(Animator animator) {
                                                    super.onAnimationEnd(animator);
                                                    DividerResizeTarget dividerResizeTarget2 = DividerResizeTarget.this;
                                                    dividerResizeTarget2.mBlurAnimator = null;
                                                    dividerResizeTarget2.mView.setAlpha(0.0f);
                                                    DividerResizeLayout dividerResizeLayout4 = DividerResizeLayout.this;
                                                    float[] fArr = DividerResizeLayout.BLUR_PRESET;
                                                    dividerResizeLayout4.postFinishRunnableIfPossible("onAnimationFinished", false);
                                                }
                                            });
                                            dividerResizeTarget.mBlurAnimator.setInterpolator(DividerResizeLayout.SINE_OUT_60);
                                            dividerResizeTarget.mBlurAnimator.setDuration(100L);
                                            dividerResizeTarget.mBlurAnimator.start();
                                            dividerResizeTarget.startOutlineInsetsAnimation(true);
                                        }
                                        if (dividerResizeTarget.mSplitDismissSide != i102) {
                                            dividerResizeTarget.mSplitDismissSide = i102;
                                            dividerResizeTarget.startOutlineInsetsAnimation(i102 == 0);
                                            dividerResizeTarget.startBoundsAnimation(dividerResizeTarget.mTmpBounds, false, 400L);
                                        }
                                        if (dividerResizeTarget.mBoundsAnimator == null) {
                                            dividerResizeTarget.updateViewBounds(dividerResizeTarget.mTmpBounds);
                                        }
                                        if (dividerResizeTarget.mShouldPlayHaptic && i102 != 0) {
                                            dividerResizeTarget.mShouldPlayHaptic = false;
                                            dividerResizeLayout3.performHapticFeedback(0);
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
                if (action != 3) {
                    return true;
                }
            }
            releaseTouching();
            if (motionEvent.getToolType(0) == 3) {
                this.mNeedUpdateCursorWhenMoving = true;
            }
            this.mVelocityTracker.addMovement(motionEvent);
            this.mVelocityTracker.computeCurrentVelocity(1000);
            if (zIsVerticalDivision) {
                this.mVelocityTracker.getXVelocity();
            } else {
                this.mVelocityTracker.getYVelocity();
            }
            this.mDividerResizeController.finishResizing((((CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) ? this.mSplitLayout.mCellDividerPosition : this.mSplitLayout.mDividerPosition) + x) - this.mStartPos);
            this.mMoving = false;
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.mVelocityTracker = null;
            }
            return true;
        }
        VelocityTracker velocityTrackerObtain = VelocityTracker.obtain();
        this.mVelocityTracker = velocityTrackerObtain;
        velocityTrackerObtain.addMovement(motionEvent);
        setSlippery(false);
        this.mHandle.getClass();
        this.mHandle.animate().setInterpolator(Interpolators.TOUCH_RESPONSE).setDuration(150L).translationZ(this.mTouchElevation).start();
        this.mTouching = true;
        this.mStartPos = x;
        this.mMoving = false;
        SplitLayout splitLayout3 = this.mSplitLayout;
        splitLayout3.mInteractionJankMonitor.begin(splitLayout3.getDividerLeash(), splitLayout3.mContext, splitLayout3.mHandler, 52);
        if (motionEvent.getToolType(0) == 3) {
            updateCursorType();
        }
        performHapticFeedback(0);
        return true;
    }

    public final void openDividerPanelIfNeeded() {
        if (this.mDividerPanel.isSupportPanelOpenPolicy()) {
            if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || !this.mIsCellDivider) {
                this.mSplitLayout.mStageCoordinator.closeHandleMenuIfNeeded();
                this.mDividerPanel.updateDividerPanel();
            } else if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                SplitLayout splitLayout = this.mSplitLayout;
                if (splitLayout.mParallelMultiSplit) {
                    splitLayout.mStageCoordinator.closeHandleMenuIfNeeded();
                    this.mDividerPanel.updateDividerPanel();
                }
            }
        }
    }

    public void releaseHovering() {
        this.mHandle.setHovering(false);
        this.mHandle.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(200L).translationZ(0.0f).start();
    }

    public final void releaseTouching() {
        setSlippery(true);
        this.mHandle.getClass();
        this.mHandle.animate().setInterpolator(Interpolators.FAST_OUT_SLOW_IN).setDuration(200L).translationZ(0.0f).start();
        this.mTouching = false;
    }

    public void setHovering() {
        this.mHandle.setHovering(true);
        this.mHandle.animate().setInterpolator(Interpolators.TOUCH_RESPONSE).setDuration(150L).translationZ(this.mTouchElevation).start();
    }

    public final void setInteractive(String str, boolean z, boolean z2) {
        if (z == this.mInteractive) {
            return;
        }
        int i = 0;
        if (ProtoLogImpl_1771455215.Cache.WM_SHELL_SPLIT_SCREEN_enabled[0]) {
            ProtoLogImpl_1771455215.d(ShellProtoLogGroup.WM_SHELL_SPLIT_SCREEN, 6072005070485667526L, 12, z ? "interactive" : "non-interactive", Boolean.valueOf(z2), str);
        }
        this.mInteractive = z;
        this.mHideHandle = z2;
        if (!z && z2 && this.mMoving) {
            SplitLayout splitLayout = this.mSplitLayout;
            final int i2 = splitLayout.mDividerPosition;
            splitLayout.flingDividerPosition(0, i2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, Interpolators.FAST_OUT_SLOW_IN, new Runnable() { // from class: com.android.wm.shell.common.split.DividerView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    DividerView dividerView = this.f$0;
                    dividerView.mSplitLayout.setDividePosition(i2, null, true);
                }
            });
            this.mMoving = false;
        }
        releaseTouching();
        DividerHandleView dividerHandleView = this.mHandle;
        if (!this.mInteractive && this.mHideHandle) {
            i = 4;
        }
        dividerHandleView.setVisibility(i);
    }

    public final void setSlippery(boolean z) {
        if (this.mViewHost == null) {
            return;
        }
        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) getLayoutParams();
        int i = layoutParams.flags;
        if (((i & VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS) != 0) == z) {
            return;
        }
        if (z) {
            layoutParams.flags = i | VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS;
        } else {
            layoutParams.flags = (-536870913) & i;
        }
        this.mViewHost.relayout(layoutParams);
    }

    public final void updateBackgroundColor(boolean z) throws Resources.NotFoundException {
        if (CoreRune.MW_MULTI_SPLIT && !z) {
            this.mBackground.setBackgroundColor(0);
        } else {
            this.mBackground.setBackgroundColor(getContext().getResources().getColor(17171593, null));
        }
    }

    public final void updateCursorType() {
        InputManager inputManager = this.mInputManager;
        if (inputManager != null) {
            inputManager.setPointerIconType(!this.mSplitLayout.mIsLeftRightSplit ? 10122 : 10123);
        }
    }

    public DividerView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
    }

    public DividerView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    /* JADX WARN: Type inference failed for: r4v10, types: [com.android.wm.shell.common.split.DividerView$5] */
    /* JADX WARN: Type inference failed for: r4v11, types: [com.android.wm.shell.common.split.DividerView$7] */
    /* JADX WARN: Type inference failed for: r4v12, types: [com.android.wm.shell.common.split.DividerView$8] */
    /* JADX WARN: Type inference failed for: r4v13, types: [com.android.wm.shell.common.split.DividerView$9] */
    /* JADX WARN: Type inference failed for: r4v14, types: [com.android.wm.shell.common.split.DividerView$10] */
    /* JADX WARN: Type inference failed for: r4v15, types: [com.android.wm.shell.common.split.DividerView$11] */
    /* JADX WARN: Type inference failed for: r4v17, types: [com.android.wm.shell.common.split.DividerView$12] */
    /* JADX WARN: Type inference failed for: r4v9, types: [com.android.wm.shell.common.split.DividerView$2] */
    public DividerView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        new Paint();
        this.mBackgroundRect = new Rect();
        this.mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        this.mSetTouchRegion = true;
        this.mMouseOverAnimatorSet = new AnimatorSet();
        this.mMouseOutAnimatorSet = new AnimatorSet();
        this.mMouseOverRoundedCornerAnimator = new ValueAnimator();
        this.mMouseOutRoundedCornerAnimator = new ValueAnimator();
        this.mH = new Handler();
        this.mTouching = false;
        this.mNeedUpdateCursorWhenMoving = true;
        this.mDividerBounds = new Rect();
        this.mTempRect = new Rect();
        this.mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.2
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
            /* JADX WARN: Removed duplicated region for block: B:7:0x0014  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                DividerSnapAlgorithm cellSnapAlgorithm;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    cellSnapAlgorithm = dividerView.mIsCellDivider ? dividerView.mSplitLayout.getCellSnapAlgorithm() : DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                }
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                if (DividerView.this.mSplitLayout.mIsLeftRightSplit) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_full)));
                    DividerSnapAlgorithm.SnapTarget snapTarget = cellSnapAlgorithm.mFirstSplitTarget;
                    DividerSnapAlgorithm.SnapTarget snapTarget2 = cellSnapAlgorithm.mMiddleTarget;
                    if (snapTarget != snapTarget2) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_70)));
                    }
                    if (cellSnapAlgorithm.mTargets.size() - 2 > 1) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_50)));
                    }
                    if (cellSnapAlgorithm.mLastSplitTarget != snapTarget2) {
                        accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_left_30)));
                    }
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_right_full)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_swap_apps, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_swap_horizontal)));
                    return;
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_full)));
                DividerSnapAlgorithm.SnapTarget snapTarget3 = cellSnapAlgorithm.mFirstSplitTarget;
                DividerSnapAlgorithm.SnapTarget snapTarget4 = cellSnapAlgorithm.mMiddleTarget;
                if (snapTarget3 != snapTarget4) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_70)));
                }
                if (cellSnapAlgorithm.mTargets.size() - 2 > 1) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_50)));
                }
                if (cellSnapAlgorithm.mLastSplitTarget != snapTarget4) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_top_30)));
                }
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_bottom_full)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_swap_apps, ((FrameLayout) DividerView.this).mContext.getString(R.string.accessibility_action_divider_swap_vertical)));
            }

            /* JADX WARN: Removed duplicated region for block: B:16:0x0030  */
            /* JADX WARN: Removed duplicated region for block: B:53:0x00d8  */
            /* JADX WARN: Removed duplicated region for block: B:55:0x00f1  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                DividerSnapAlgorithm cellSnapAlgorithm;
                DividerSnapAlgorithm.SnapTarget snapTarget;
                DividerSnapAlgorithm.SnapTarget snapTarget2;
                if (i3 == R.id.action_swap_apps) {
                    SplitLayout splitLayout = DividerView.this.mSplitLayout;
                    AnimatorSet animatorSet = splitLayout.mSwapAnimator;
                    if (animatorSet != null && animatorSet.isRunning()) {
                        return true;
                    }
                    ((StageCoordinator) splitLayout.mSplitLayoutHandler).switchSplitPosition(SystemUIAnalytics.DT_COVER_SCREEN_OFF_DOUBLE_TAP);
                    return true;
                }
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    cellSnapAlgorithm = dividerView.mIsCellDivider ? dividerView.mSplitLayout.getCellSnapAlgorithm() : DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                }
                if (i3 != 16) {
                    if (i3 == R.id.action_move_tl_full) {
                        snapTarget = cellSnapAlgorithm.mDismissEndTarget;
                        DividerView dividerView2 = DividerView.this;
                        if (dividerView2.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView2, R.string.accessibility_action_divider_left_full);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView2, R.string.accessibility_action_divider_top_full);
                        }
                    } else if (i3 == R.id.action_move_tl_70) {
                        snapTarget = cellSnapAlgorithm.mLastSplitTarget;
                        DividerView dividerView3 = DividerView.this;
                        if (dividerView3.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView3, R.string.accessibility_action_divider_left_70);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView3, R.string.accessibility_action_divider_top_70);
                        }
                    } else if (i3 == R.id.action_move_tl_50) {
                        snapTarget = cellSnapAlgorithm.mMiddleTarget;
                        DividerView dividerView4 = DividerView.this;
                        if (dividerView4.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView4, R.string.accessibility_action_divider_left_50);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView4, R.string.accessibility_action_divider_top_50);
                        }
                    } else if (i3 == R.id.action_move_tl_30) {
                        snapTarget = cellSnapAlgorithm.mFirstSplitTarget;
                        DividerView dividerView5 = DividerView.this;
                        if (dividerView5.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView5, R.string.accessibility_action_divider_left_30);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView5, R.string.accessibility_action_divider_top_30);
                        }
                    } else if (i3 == R.id.action_move_rb_full) {
                        snapTarget = cellSnapAlgorithm.mDismissStartTarget;
                        DividerView dividerView6 = DividerView.this;
                        if (dividerView6.mSplitLayout.mIsLeftRightSplit) {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView6, R.string.accessibility_action_divider_right_full);
                        } else {
                            DividerView.m3238$$Nest$msendTalkBackFeedback(dividerView6, R.string.accessibility_action_divider_bottom_full);
                        }
                    }
                    snapTarget2 = snapTarget;
                    if (snapTarget2 != null) {
                        return super.performAccessibilityAction(view, i3, bundle);
                    }
                    SplitLayout splitLayout2 = DividerView.this.mSplitLayout;
                    splitLayout2.snapToTarget(splitLayout2.mDividerPosition, snapTarget2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, Interpolators.FAST_OUT_SLOW_IN, false);
                    SplitLayout splitLayout3 = DividerView.this.mSplitLayout;
                    splitLayout3.updateDividerBounds(splitLayout3.mDividerPosition, true, false);
                    return true;
                }
                DividerView.this.openDividerPanelIfNeeded();
                snapTarget = null;
                snapTarget2 = snapTarget;
                if (snapTarget2 != null) {
                }
            }
        };
        this.mRoundedCornerUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.common.split.DividerView.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                DividerView.this.mCorners.mDividerWidth = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                DividerView.this.mCorners.invalidate();
            }
        };
        this.mMouseOverAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.8
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationStart(Animator animator) throws Resources.NotFoundException {
                DividerView dividerView = DividerView.this;
                AnonymousClass1 anonymousClass1 = DividerView.DIVIDER_HEIGHT_PROPERTY;
                dividerView.updateBackgroundColor(true);
                DividerView.this.mCorners.mDividerBarBackground.setColor(0);
            }
        };
        this.mMouseOutAnimatorListener = new AnimatorListenerAdapter() { // from class: com.android.wm.shell.common.split.DividerView.9
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
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
                DividerView.this.mCorners.mDividerBarBackground.setColor(0);
            }
        };
        this.mMouseOver = new Runnable() { // from class: com.android.wm.shell.common.split.DividerView.10
            /* JADX WARN: Removed duplicated region for block: B:16:0x0034  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                Property property;
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mCorners;
                dividerRoundedCorner.mDividerWidth = dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
                DividerView dividerView = DividerView.this;
                if (!dividerView.mIsCellDivider) {
                    property = dividerView.mSplitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                } else if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                    SplitLayout splitLayout = dividerView.mSplitLayout;
                    property = splitLayout.mParallelMultiSplit ? splitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY : !dividerView.mSplitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                }
                int i3 = dividerView.mSplitLayout.mDividerSize;
                ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, i3, dividerView.mMouseOverBgScaleSize + i3);
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.0f, 1.3f);
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.0f, 1.3f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOverRoundedCornerAnimator;
                int i22 = dividerView2.mCorners.mDividerWidth;
                valueAnimator.setIntValues(i22, dividerView2.mMouseOverBgScaleSize + i22);
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOverRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView dividerView4 = DividerView.this;
                dividerView4.mMouseOverAnimatorSet.playTogether(objectAnimatorOfInt, objectAnimatorOfFloat, objectAnimatorOfFloat2, dividerView4.mMouseOverRoundedCornerAnimator);
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
                DividerView dividerView = DividerView.this;
                SplitLayout splitLayout = dividerView.mSplitLayout;
                Property property = !splitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                int i3 = splitLayout.mDividerSize;
                ObjectAnimator objectAnimatorOfInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, dividerView.mMouseOverBgScaleSize + i3, i3);
                ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.3f, 1.0f);
                ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.3f, 1.0f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOutRoundedCornerAnimator;
                DividerRoundedCorner dividerRoundedCorner = dividerView2.mCorners;
                valueAnimator.setIntValues(dividerRoundedCorner.mDividerWidth, dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width));
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOutRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView.this.mMouseOutAnimatorSet.playTogether(objectAnimatorOfInt, objectAnimatorOfFloat, objectAnimatorOfFloat2);
                DividerView.this.mMouseOutAnimatorSet.setDuration(200L);
                DividerView.this.mMouseOutAnimatorSet.setInterpolator(InterpolatorUtils.SINE_OUT_60);
                DividerView dividerView4 = DividerView.this;
                dividerView4.mMouseOutAnimatorSet.addListener(dividerView4.mMouseOutAnimatorListener);
                DividerView.this.mMouseOutAnimatorSet.start();
            }
        };
        this.mHandleHoverListener = new View.OnHoverListener() { // from class: com.android.wm.shell.common.split.DividerView$$ExternalSyntheticLambda0
            @Override // android.view.View.OnHoverListener
            public final boolean onHover(View view, MotionEvent motionEvent) {
                DividerView.$r8$lambda$YGxiu2asQy_xuW4RhQgo25UjOYI(this.f$0, motionEvent);
                return false;
            }
        };
        this.mMultiSplitHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.wm.shell.common.split.DividerView.12
            /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                DividerSnapAlgorithm cellSnapAlgorithm;
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    cellSnapAlgorithm = dividerView.mIsCellDivider ? dividerView.mSplitLayout.getCellSnapAlgorithm() : DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                }
                boolean zIsVerticalDivision = DividerView.this.isVerticalDivision();
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_full, ((FrameLayout) DividerView.this).mContext.getString(zIsVerticalDivision ? R.string.accessibility_action_divider_left_full : R.string.accessibility_action_divider_top_full)));
                accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_rb_full, ((FrameLayout) DividerView.this).mContext.getString(zIsVerticalDivision ? R.string.accessibility_action_divider_right_full : R.string.accessibility_action_divider_bottom_full)));
                if (cellSnapAlgorithm.mTargets.size() == 3 || DividerView.this.mIsCellDivider) {
                    return;
                }
                int i3 = zIsVerticalDivision ? R.string.accessibility_action_divider_left_percent : R.string.accessibility_action_divider_top_percent;
                if (cellSnapAlgorithm.getTargetMinimalRatio() == 30) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_70, ((FrameLayout) DividerView.this).mContext.getString(i3, 70)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(i3, 50)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_30, ((FrameLayout) DividerView.this).mContext.getString(i3, 30)));
                } else if (cellSnapAlgorithm.getTargetMinimalRatio() == 40) {
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_60, ((FrameLayout) DividerView.this).mContext.getString(i3, 60)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_50, ((FrameLayout) DividerView.this).mContext.getString(i3, 50)));
                    accessibilityNodeInfo.addAction(new AccessibilityNodeInfo.AccessibilityAction(R.id.action_move_tl_40, ((FrameLayout) DividerView.this).mContext.getString(i3, 40)));
                }
            }

            /* JADX WARN: Removed duplicated region for block: B:35:0x005b  */
            /* JADX WARN: Removed duplicated region for block: B:47:0x00aa  */
            /* JADX WARN: Removed duplicated region for block: B:7:0x0011  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                DividerSnapAlgorithm cellSnapAlgorithm;
                DividerSnapAlgorithm.SnapTarget snapTarget;
                DividerSnapAlgorithm.SnapTarget snapTarget2;
                if (CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM) {
                    DividerView dividerView = DividerView.this;
                    cellSnapAlgorithm = dividerView.mIsCellDivider ? dividerView.mSplitLayout.getCellSnapAlgorithm() : DividerView.this.mSplitLayout.mDividerSnapAlgorithm;
                }
                if (i3 != 16) {
                    if (i3 == R.id.action_move_tl_full) {
                        snapTarget = cellSnapAlgorithm.mDismissEndTarget;
                    } else if (i3 == R.id.action_move_tl_70 || i3 == R.id.action_move_tl_60) {
                        snapTarget = cellSnapAlgorithm.mLastSplitTarget;
                    } else if (i3 == R.id.action_move_tl_50) {
                        snapTarget = cellSnapAlgorithm.mMiddleTarget;
                    } else if (i3 == R.id.action_move_tl_30 || i3 == R.id.action_move_tl_40) {
                        snapTarget = cellSnapAlgorithm.mFirstSplitTarget;
                    } else if (i3 == R.id.action_move_rb_full) {
                        snapTarget = cellSnapAlgorithm.mDismissStartTarget;
                    }
                    snapTarget2 = snapTarget;
                    if (snapTarget2 != null) {
                        return super.performAccessibilityAction(view, i3, bundle);
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
                                SplitLayout splitLayout = dividerView2.mSplitLayout;
                                splitLayout.snapToTarget(splitLayout.mCellDividerPosition, snapTarget2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, Interpolators.FAST_OUT_SLOW_IN, false);
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
                    DividerView.this.mSplitLayout.updateDividerBounds(snapTarget2.position, true, false);
                    SplitLayout splitLayout2 = DividerView.this.mSplitLayout;
                    splitLayout2.snapToTarget(splitLayout2.mDividerPosition, snapTarget2, IKnoxCustomManager.Stub.TRANSACTION_addDexURLShortcutExtend, Interpolators.FAST_OUT_SLOW_IN, false);
                    return true;
                }
                DividerView.this.openDividerPanelIfNeeded();
                snapTarget2 = null;
                if (snapTarget2 != null) {
                }
            }
        };
        TypedArray typedArrayObtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, com.android.wm.shell.R.styleable.DividerView, 0, 0);
        try {
            this.mIsCellDivider = typedArrayObtainStyledAttributes.getBoolean(3, false);
            typedArrayObtainStyledAttributes.recycle();
            this.mInputManager = (InputManager) context.getSystemService(InputManager.class);
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }
}
