package com.android.wm.shell.common.split;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.DeviceConfig;
import android.util.AttributeSet;
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
import com.android.internal.protolog.ProtoLogImpl_1771455215;
import com.android.systemui.R;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.animation.Interpolators;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.splitscreen.StageTaskListener;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.util.InterpolatorUtils;
import com.samsung.systemui.splugins.volume.VolumePanelValues;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static void m3222$$Nest$msendTalkBackFeedback(DividerView dividerView, int i) {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(((FrameLayout) dividerView).mContext);
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain(NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT);
            String string = ((FrameLayout) dividerView).mContext.getString(i);
            obtain.getText().clear();
            obtain.getText().add(string);
            accessibilityManager.sendAccessibilityEvent(obtain);
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
            /* JADX WARN: Removed duplicated region for block: B:20:0x00cd  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x003a  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onInitializeAccessibilityNodeInfo(android.view.View r11, android.view.accessibility.AccessibilityNodeInfo r12) {
                /*
                    Method dump skipped, instructions count: 352
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.AnonymousClass5.onInitializeAccessibilityNodeInfo(android.view.View, android.view.accessibility.AccessibilityNodeInfo):void");
            }

            /* JADX WARN: Removed duplicated region for block: B:18:0x003a  */
            /* JADX WARN: Removed duplicated region for block: B:22:0x00d8  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x00f1  */
            /* JADX WARN: Removed duplicated region for block: B:26:0x0041  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean performAccessibilityAction(android.view.View r9, int r10, android.os.Bundle r11) {
                /*
                    Method dump skipped, instructions count: 246
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.AnonymousClass5.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
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
            public final void onAnimationStart(Animator animator) {
                DividerView dividerView = DividerView.this;
                AnonymousClass1 anonymousClass1 = DividerView.DIVIDER_HEIGHT_PROPERTY;
                dividerView.updateBackgroundColor(true);
                DividerView.this.mCorners.mDividerBarBackground.setColor(0);
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
                DividerView.this.mCorners.mDividerBarBackground.setColor(0);
            }
        };
        this.mMouseOver = new Runnable() { // from class: com.android.wm.shell.common.split.DividerView.10
            @Override // java.lang.Runnable
            public final void run() {
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mCorners;
                dividerRoundedCorner.mDividerWidth = dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
                DividerView dividerView = DividerView.this;
                Property property = dividerView.mIsCellDivider ? !dividerView.mSplitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY : dividerView.mSplitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                int i = dividerView.mSplitLayout.mDividerSize;
                ObjectAnimator ofInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, i, dividerView.mMouseOverBgScaleSize + i);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.0f, 1.3f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.0f, 1.3f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOverRoundedCornerAnimator;
                int i2 = dividerView2.mCorners.mDividerWidth;
                valueAnimator.setIntValues(i2, dividerView2.mMouseOverBgScaleSize + i2);
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
                DividerView dividerView = DividerView.this;
                SplitLayout splitLayout = dividerView.mSplitLayout;
                Property property = !splitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                int i = splitLayout.mDividerSize;
                ObjectAnimator ofInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, dividerView.mMouseOverBgScaleSize + i, i);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.3f, 1.0f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.3f, 1.0f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOutRoundedCornerAnimator;
                DividerRoundedCorner dividerRoundedCorner = dividerView2.mCorners;
                valueAnimator.setIntValues(dividerRoundedCorner.mDividerWidth, dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width));
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOutRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView.this.mMouseOutAnimatorSet.playTogether(ofInt, ofFloat, ofFloat2);
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
                DividerView.$r8$lambda$YGxiu2asQy_xuW4RhQgo25UjOYI(DividerView.this, motionEvent);
                return false;
            }
        };
        this.mMultiSplitHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.wm.shell.common.split.DividerView.12
            /* JADX WARN: Removed duplicated region for block: B:11:0x0045  */
            /* JADX WARN: Removed duplicated region for block: B:17:0x0076  */
            /* JADX WARN: Removed duplicated region for block: B:20:0x008a  */
            /* JADX WARN: Removed duplicated region for block: B:23:0x00e1  */
            /* JADX WARN: Removed duplicated region for block: B:28:0x007a  */
            /* JADX WARN: Removed duplicated region for block: B:31:0x0049  */
            /* JADX WARN: Removed duplicated region for block: B:32:0x002b  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x0027  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onInitializeAccessibilityNodeInfo(android.view.View r7, android.view.accessibility.AccessibilityNodeInfo r8) {
                /*
                    Method dump skipped, instructions count: 320
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.AnonymousClass12.onInitializeAccessibilityNodeInfo(android.view.View, android.view.accessibility.AccessibilityNodeInfo):void");
            }

            /* JADX WARN: Removed duplicated region for block: B:11:0x005b  */
            /* JADX WARN: Removed duplicated region for block: B:24:0x00aa  */
            /* JADX WARN: Removed duplicated region for block: B:26:0x0022  */
            /* JADX WARN: Removed duplicated region for block: B:8:0x001c  */
            @Override // android.view.View.AccessibilityDelegate
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final boolean performAccessibilityAction(android.view.View r10, int r11, android.os.Bundle r12) {
                /*
                    r9 = this;
                    boolean r0 = com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM
                    if (r0 == 0) goto L11
                    com.android.wm.shell.common.split.DividerView r0 = com.android.wm.shell.common.split.DividerView.this
                    boolean r1 = r0.mIsCellDivider
                    if (r1 == 0) goto L11
                    com.android.wm.shell.common.split.SplitLayout r0 = r0.mSplitLayout
                    com.android.wm.shell.common.split.DividerSnapAlgorithm r0 = r0.getCellSnapAlgorithm()
                    goto L17
                L11:
                    com.android.wm.shell.common.split.DividerView r0 = com.android.wm.shell.common.split.DividerView.this
                    com.android.wm.shell.common.split.SplitLayout r0 = r0.mSplitLayout
                    com.android.wm.shell.common.split.DividerSnapAlgorithm r0 = r0.mDividerSnapAlgorithm
                L17:
                    r1 = 16
                    r2 = 0
                    if (r11 != r1) goto L22
                    com.android.wm.shell.common.split.DividerView r0 = com.android.wm.shell.common.split.DividerView.this
                    r0.openDividerPanelIfNeeded()
                    goto L51
                L22:
                    r1 = 2131361942(0x7f0a0096, float:1.834365E38)
                    if (r11 != r1) goto L2b
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mDismissEndTarget
                L29:
                    r5 = r0
                    goto L59
                L2b:
                    r1 = 2131361941(0x7f0a0095, float:1.8343649E38)
                    if (r11 == r1) goto L56
                    r1 = 2131361940(0x7f0a0094, float:1.8343647E38)
                    if (r11 != r1) goto L36
                    goto L56
                L36:
                    r1 = 2131361939(0x7f0a0093, float:1.8343644E38)
                    if (r11 != r1) goto L3e
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mMiddleTarget
                    goto L29
                L3e:
                    r1 = 2131361937(0x7f0a0091, float:1.834364E38)
                    if (r11 == r1) goto L53
                    r1 = 2131361938(0x7f0a0092, float:1.8343642E38)
                    if (r11 != r1) goto L49
                    goto L53
                L49:
                    r1 = 2131361936(0x7f0a0090, float:1.8343638E38)
                    if (r11 != r1) goto L51
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mDismissStartTarget
                    goto L29
                L51:
                    r5 = r2
                    goto L59
                L53:
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mFirstSplitTarget
                    goto L29
                L56:
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mLastSplitTarget
                    goto L29
                L59:
                    if (r5 == 0) goto Laa
                    boolean r10 = com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_ACCESSIBILITY
                    r11 = 0
                    r12 = 1
                    if (r10 == 0) goto L92
                    com.android.wm.shell.common.split.DividerView r10 = com.android.wm.shell.common.split.DividerView.this
                    boolean r0 = r10.mIsCellDivider
                    if (r0 == 0) goto L92
                    com.android.wm.shell.common.split.DividerResizeController r0 = r10.mDividerResizeController
                    boolean r1 = r0.mResizingRequested
                    if (r1 == 0) goto L91
                    r0.mDividerView = r10
                    r0.mResizingRequested = r12
                    r0.mIsResizing = r12
                    boolean r0 = r0.mUseGuideViewByMultiStar
                    com.android.wm.shell.common.split.DividerResizeController.USE_GUIDE_VIEW_EFFECTS = r0
                    com.android.wm.shell.common.split.SplitLayout r3 = r10.mSplitLayout
                    int r4 = r3.mCellDividerPosition
                    android.view.animation.Interpolator r7 = com.android.wm.shell.shared.animation.Interpolators.FAST_OUT_SLOW_IN
                    r8 = 0
                    r6 = 250(0xfa, float:3.5E-43)
                    r3.snapToTarget(r4, r5, r6, r7, r8)
                    com.android.wm.shell.common.split.DividerView r9 = com.android.wm.shell.common.split.DividerView.this
                    com.android.wm.shell.common.split.DividerResizeController r9 = r9.mDividerResizeController
                    boolean r10 = r9.mIsResizing
                    if (r10 == 0) goto L91
                    r9.mDividerView = r2
                    r9.mResizingRequested = r11
                    r9.mIsResizing = r11
                L91:
                    return r12
                L92:
                    com.android.wm.shell.common.split.DividerView r10 = com.android.wm.shell.common.split.DividerView.this
                    com.android.wm.shell.common.split.SplitLayout r10 = r10.mSplitLayout
                    int r0 = r5.position
                    r10.updateDividerBounds(r0, r12, r11)
                    com.android.wm.shell.common.split.DividerView r9 = com.android.wm.shell.common.split.DividerView.this
                    com.android.wm.shell.common.split.SplitLayout r3 = r9.mSplitLayout
                    int r4 = r3.mDividerPosition
                    android.view.animation.Interpolator r7 = com.android.wm.shell.shared.animation.Interpolators.FAST_OUT_SLOW_IN
                    r8 = 0
                    r6 = 250(0xfa, float:3.5E-43)
                    r3.snapToTarget(r4, r5, r6, r7, r8)
                    return r12
                Laa:
                    boolean r9 = super.performAccessibilityAction(r10, r11, r12)
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.AnonymousClass12.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
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
    public final void onFinishInflate() {
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
            for (int sourceSize = insetsState.sourceSize() - 1; sourceSize >= 0; sourceSize--) {
                InsetsSource sourceAt = insetsState.sourceAt(sourceSize);
                if (sourceAt.getType() == WindowInsets.Type.navigationBars() && sourceAt.hasFlags(2)) {
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
            int width = (this.mDividerBounds.width() - this.mHandleRegionWidth) / 2;
            int height = this.mDividerBounds.height();
            int i5 = this.mHandleRegionHeight;
            int i6 = (height - i5) / 2;
            this.mTempRect.set(width, i6, this.mHandleRegionWidth + width, i5 + i6);
            this.mSplitWindowManager.setTouchRegion(this.mTempRect);
            this.mSetTouchRegion = false;
        }
        if (z) {
            boolean z2 = this.mSplitLayout.mIsLeftRightSplit;
            if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && this.mIsCellDivider) {
                z2 = !z2;
            }
            int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.split_divider_bar_width);
            int width2 = z2 ? (getWidth() - dimensionPixelSize) / 2 : 0;
            int height2 = z2 ? 0 : (getHeight() - dimensionPixelSize) / 2;
            this.mBackgroundRect.set(width2, height2, z2 ? width2 + dimensionPixelSize : getWidth(), z2 ? getHeight() : height2 + dimensionPixelSize);
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

    @Override // android.view.ViewGroup, android.view.View
    public final PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int i) {
        int i2 = 20006;
        if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || !this.mIsCellDivider ? !this.mSplitLayout.mIsLeftRightSplit : this.mSplitLayout.mIsLeftRightSplit) {
            i2 = 20007;
        }
        return PointerIcon.getSystemIcon(getContext(), i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:78:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0229  */
    @Override // android.view.View.OnTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean onTouch(android.view.View r30, android.view.MotionEvent r31) {
        /*
            Method dump skipped, instructions count: 1284
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.onTouch(android.view.View, android.view.MotionEvent):boolean");
    }

    public final void openDividerPanelIfNeeded() {
        if (this.mDividerPanel.isSupportPanelOpenPolicy()) {
            if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || !this.mIsCellDivider) {
                StageCoordinator stageCoordinator = this.mSplitLayout.mStageCoordinator;
                StageTaskListener stageTaskListener = stageCoordinator.mMainStage;
                if (!stageTaskListener.isFocused()) {
                    stageTaskListener = stageCoordinator.mSideStage;
                }
                stageTaskListener.getClass();
                this.mDividerPanel.updateDividerPanel();
                return;
            }
            if (CoreRune.MW_PARALLEL_MULTI_SPLIT) {
                SplitLayout splitLayout = this.mSplitLayout;
                if (splitLayout.mParallelMultiSplit) {
                    StageCoordinator stageCoordinator2 = splitLayout.mStageCoordinator;
                    StageTaskListener stageTaskListener2 = stageCoordinator2.mMainStage;
                    if (!stageTaskListener2.isFocused()) {
                        stageTaskListener2 = stageCoordinator2.mSideStage;
                    }
                    stageTaskListener2.getClass();
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
                    DividerView dividerView = DividerView.this;
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

    public final void updateBackgroundColor(boolean z) {
        if (CoreRune.MW_MULTI_SPLIT && !z) {
            this.mBackground.setBackgroundColor(0);
        } else {
            this.mBackground.setBackgroundColor(getContext().getResources().getColor(17171594, null));
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
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                /*
                    Method dump skipped, instructions count: 352
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.AnonymousClass5.onInitializeAccessibilityNodeInfo(android.view.View, android.view.accessibility.AccessibilityNodeInfo):void");
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                /*
                    Method dump skipped, instructions count: 246
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.AnonymousClass5.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
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
            public final void onAnimationStart(Animator animator) {
                DividerView dividerView = DividerView.this;
                AnonymousClass1 anonymousClass1 = DividerView.DIVIDER_HEIGHT_PROPERTY;
                dividerView.updateBackgroundColor(true);
                DividerView.this.mCorners.mDividerBarBackground.setColor(0);
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
                DividerView.this.mCorners.mDividerBarBackground.setColor(0);
            }
        };
        this.mMouseOver = new Runnable() { // from class: com.android.wm.shell.common.split.DividerView.10
            @Override // java.lang.Runnable
            public final void run() {
                DividerRoundedCorner dividerRoundedCorner = DividerView.this.mCorners;
                dividerRoundedCorner.mDividerWidth = dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width);
                DividerView dividerView = DividerView.this;
                Property property = dividerView.mIsCellDivider ? !dividerView.mSplitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY : dividerView.mSplitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                int i3 = dividerView.mSplitLayout.mDividerSize;
                ObjectAnimator ofInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, i3, dividerView.mMouseOverBgScaleSize + i3);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.0f, 1.3f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.0f, 1.3f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOverRoundedCornerAnimator;
                int i22 = dividerView2.mCorners.mDividerWidth;
                valueAnimator.setIntValues(i22, dividerView2.mMouseOverBgScaleSize + i22);
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
                DividerView dividerView = DividerView.this;
                SplitLayout splitLayout = dividerView.mSplitLayout;
                Property property = !splitLayout.mIsLeftRightSplit ? DividerView.DIVIDER_MOUSE_OVER_BG_WIDTH_PROPERTY : DividerView.DIVIDER_MOUSE_OVER_BG_HEIGHT_PROPERTY;
                int i3 = splitLayout.mDividerSize;
                ObjectAnimator ofInt = ObjectAnimator.ofInt(dividerView, (Property<DividerView, Integer>) property, dividerView.mMouseOverBgScaleSize + i3, i3);
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleX", 1.3f, 1.0f);
                ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(DividerView.this.mHandle, "scaleY", 1.3f, 1.0f);
                DividerView dividerView2 = DividerView.this;
                ValueAnimator valueAnimator = dividerView2.mMouseOutRoundedCornerAnimator;
                DividerRoundedCorner dividerRoundedCorner = dividerView2.mCorners;
                valueAnimator.setIntValues(dividerRoundedCorner.mDividerWidth, dividerRoundedCorner.getResources().getDimensionPixelSize(CoreRune.MW_MULTI_SPLIT_DIVIDER_SIZE_FOLD ? R.dimen.split_divider_bar_width_fold : R.dimen.split_divider_bar_width));
                DividerView dividerView3 = DividerView.this;
                dividerView3.mMouseOutRoundedCornerAnimator.addUpdateListener(dividerView3.mRoundedCornerUpdateListener);
                DividerView.this.mMouseOutAnimatorSet.playTogether(ofInt, ofFloat, ofFloat2);
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
                DividerView.$r8$lambda$YGxiu2asQy_xuW4RhQgo25UjOYI(DividerView.this, motionEvent);
                return false;
            }
        };
        this.mMultiSplitHandleDelegate = new View.AccessibilityDelegate() { // from class: com.android.wm.shell.common.split.DividerView.12
            @Override // android.view.View.AccessibilityDelegate
            public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                /*
                    Method dump skipped, instructions count: 320
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.AnonymousClass12.onInitializeAccessibilityNodeInfo(android.view.View, android.view.accessibility.AccessibilityNodeInfo):void");
            }

            @Override // android.view.View.AccessibilityDelegate
            public final boolean performAccessibilityAction(View view, int i3, Bundle bundle) {
                /*
                    this = this;
                    boolean r0 = com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_SNAP_ALGORITHM
                    if (r0 == 0) goto L11
                    com.android.wm.shell.common.split.DividerView r0 = com.android.wm.shell.common.split.DividerView.this
                    boolean r1 = r0.mIsCellDivider
                    if (r1 == 0) goto L11
                    com.android.wm.shell.common.split.SplitLayout r0 = r0.mSplitLayout
                    com.android.wm.shell.common.split.DividerSnapAlgorithm r0 = r0.getCellSnapAlgorithm()
                    goto L17
                L11:
                    com.android.wm.shell.common.split.DividerView r0 = com.android.wm.shell.common.split.DividerView.this
                    com.android.wm.shell.common.split.SplitLayout r0 = r0.mSplitLayout
                    com.android.wm.shell.common.split.DividerSnapAlgorithm r0 = r0.mDividerSnapAlgorithm
                L17:
                    r1 = 16
                    r2 = 0
                    if (r11 != r1) goto L22
                    com.android.wm.shell.common.split.DividerView r0 = com.android.wm.shell.common.split.DividerView.this
                    r0.openDividerPanelIfNeeded()
                    goto L51
                L22:
                    r1 = 2131361942(0x7f0a0096, float:1.834365E38)
                    if (r11 != r1) goto L2b
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mDismissEndTarget
                L29:
                    r5 = r0
                    goto L59
                L2b:
                    r1 = 2131361941(0x7f0a0095, float:1.8343649E38)
                    if (r11 == r1) goto L56
                    r1 = 2131361940(0x7f0a0094, float:1.8343647E38)
                    if (r11 != r1) goto L36
                    goto L56
                L36:
                    r1 = 2131361939(0x7f0a0093, float:1.8343644E38)
                    if (r11 != r1) goto L3e
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mMiddleTarget
                    goto L29
                L3e:
                    r1 = 2131361937(0x7f0a0091, float:1.834364E38)
                    if (r11 == r1) goto L53
                    r1 = 2131361938(0x7f0a0092, float:1.8343642E38)
                    if (r11 != r1) goto L49
                    goto L53
                L49:
                    r1 = 2131361936(0x7f0a0090, float:1.8343638E38)
                    if (r11 != r1) goto L51
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mDismissStartTarget
                    goto L29
                L51:
                    r5 = r2
                    goto L59
                L53:
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mFirstSplitTarget
                    goto L29
                L56:
                    com.android.wm.shell.common.split.DividerSnapAlgorithm$SnapTarget r0 = r0.mLastSplitTarget
                    goto L29
                L59:
                    if (r5 == 0) goto Laa
                    boolean r10 = com.samsung.android.rune.CoreRune.MW_MULTI_SPLIT_ACCESSIBILITY
                    r11 = 0
                    r12 = 1
                    if (r10 == 0) goto L92
                    com.android.wm.shell.common.split.DividerView r10 = com.android.wm.shell.common.split.DividerView.this
                    boolean r0 = r10.mIsCellDivider
                    if (r0 == 0) goto L92
                    com.android.wm.shell.common.split.DividerResizeController r0 = r10.mDividerResizeController
                    boolean r1 = r0.mResizingRequested
                    if (r1 == 0) goto L91
                    r0.mDividerView = r10
                    r0.mResizingRequested = r12
                    r0.mIsResizing = r12
                    boolean r0 = r0.mUseGuideViewByMultiStar
                    com.android.wm.shell.common.split.DividerResizeController.USE_GUIDE_VIEW_EFFECTS = r0
                    com.android.wm.shell.common.split.SplitLayout r3 = r10.mSplitLayout
                    int r4 = r3.mCellDividerPosition
                    android.view.animation.Interpolator r7 = com.android.wm.shell.shared.animation.Interpolators.FAST_OUT_SLOW_IN
                    r8 = 0
                    r6 = 250(0xfa, float:3.5E-43)
                    r3.snapToTarget(r4, r5, r6, r7, r8)
                    com.android.wm.shell.common.split.DividerView r9 = com.android.wm.shell.common.split.DividerView.this
                    com.android.wm.shell.common.split.DividerResizeController r9 = r9.mDividerResizeController
                    boolean r10 = r9.mIsResizing
                    if (r10 == 0) goto L91
                    r9.mDividerView = r2
                    r9.mResizingRequested = r11
                    r9.mIsResizing = r11
                L91:
                    return r12
                L92:
                    com.android.wm.shell.common.split.DividerView r10 = com.android.wm.shell.common.split.DividerView.this
                    com.android.wm.shell.common.split.SplitLayout r10 = r10.mSplitLayout
                    int r0 = r5.position
                    r10.updateDividerBounds(r0, r12, r11)
                    com.android.wm.shell.common.split.DividerView r9 = com.android.wm.shell.common.split.DividerView.this
                    com.android.wm.shell.common.split.SplitLayout r3 = r9.mSplitLayout
                    int r4 = r3.mDividerPosition
                    android.view.animation.Interpolator r7 = com.android.wm.shell.shared.animation.Interpolators.FAST_OUT_SLOW_IN
                    r8 = 0
                    r6 = 250(0xfa, float:3.5E-43)
                    r3.snapToTarget(r4, r5, r6, r7, r8)
                    return r12
                Laa:
                    boolean r9 = super.performAccessibilityAction(r10, r11, r12)
                    return r9
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.common.split.DividerView.AnonymousClass12.performAccessibilityAction(android.view.View, int, android.os.Bundle):boolean");
            }
        };
        TypedArray obtainStyledAttributes = getContext().getTheme().obtainStyledAttributes(attributeSet, com.android.wm.shell.R.styleable.DividerView, 0, 0);
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
