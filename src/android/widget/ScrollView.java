package android.widget;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RenderNode;
import android.graphics.drawable.Drawable;
import android.media.TtmlUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.Settings;
import android.text.MultiSelection;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.StateSet;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.HapticScrollFeedbackProvider;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.SoundEffectConstants;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import android.view.flags.Flags;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.DifferentialMotionFlingHelper;
import android.widget.FrameLayout;
import com.android.internal.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class ScrollView extends FrameLayout {
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final float FLING_DESTRETCH_FACTOR = 4.0f;
    private static final int HOVERSCROLL_DOWN = 2;
    private static final int HOVERSCROLL_HEIGHT_BOTTOM_DP = 25;
    private static final int HOVERSCROLL_HEIGHT_TOP_DP = 25;
    private static final int HOVERSCROLL_UP = 1;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final int MSG_HOVERSCROLL_MOVE = 1;
    public static final int SEM_GO_TO_TOP_BUTTON_STYLE_BLACK = 1;
    public static final int SEM_GO_TO_TOP_BUTTON_STYLE_WHITE = 0;
    private static final String TAG = "ScrollView";
    static final Interpolator sLinearInterpolator = new LinearInterpolator();
    private int GO_TO_TOP_HIDE;
    private final int GTP_STATE_MAINTAINED;
    private final int GTP_STATE_NONE;
    private final int GTP_STATE_PRESSED;
    private final int GTP_STATE_SHOWN;
    private int HOVERSCROLL_DELAY;
    private float HOVERSCROLL_SPEED;
    private final int ON_ABSORB_VELOCITY;
    private final int SWITCH_CONTROL_FLING;
    private final float SWITCH_CONTROL_SCROLL_MAX_DURATION;
    private final float SWITCH_CONTROL_SCROLL_MIN_DURATION;
    private int mActivePointerId;
    private float mAutoscrollDuration;
    private float mAutoscrollDurationGap;
    private View mChildToScrollTo;
    private DifferentialMotionFlingHelper mDifferentialMotionFlingHelper;
    public EdgeEffect mEdgeGlowBottom;
    public EdgeEffect mEdgeGlowTop;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private boolean mFillViewport;
    private StrictMode.Span mFlingStrictSpan;
    private final Runnable mGoToTopEdgeEffectRunnable;
    private int mGoToTopElevation;
    private int mGoToTopGap;
    private RenderNode mGoToTopRenderNode;
    private int mGoToTopWH;
    private HapticScrollFeedbackProvider mHapticScrollFeedbackProvider;
    private boolean mHoverAreaEnter;
    private int mHoverBottomAreaHeight;
    private HoverScrollHandler mHoverHandler;
    private long mHoverRecognitionCurrentTime;
    private long mHoverRecognitionDurationTime;
    private long mHoverRecognitionStartTime;
    private int mHoverScrollDirection;
    private boolean mHoverScrollEnable;
    private int mHoverScrollSpeed;
    private long mHoverScrollStartTime;
    private boolean mHoverScrollStateChanged;
    private long mHoverScrollTimeInterval;
    private int mHoverTopAreaHeight;
    private boolean mIgnoreDelaychildPrerssed;
    private boolean mIsBeingDragged;
    private boolean mIsGoToTopShown;
    private boolean mIsHoverOverscrolled;
    private boolean mIsLayoutDirty;
    private int mLastMotionY;
    private long mLastScroll;
    private boolean mLinear;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedsHoverScroll;
    private int mNestedYOffset;
    private Outline mOutline;
    private int mOverflingDistance;
    private int mOverscrollDistance;
    private boolean mPreviousTextViewScroll;
    private SavedState mSavedState;
    private final int[] mScrollConsumed;
    private final int[] mScrollOffset;
    private StrictMode.Span mScrollStrictSpan;
    private OverScroller mScroller;
    private final Runnable mSemAutoHide;
    private boolean mSemEnableGoToTop;
    private Bitmap mSemGoToTopBitmap;
    private ValueAnimator mSemGoToTopFadeInAnimator;
    private final Runnable mSemGoToTopFadeInRunnable;
    private ValueAnimator mSemGoToTopFadeOutAnimator;
    private final Runnable mSemGoToTopFadeOutRunnable;
    private Drawable mSemGoToTopImage;
    private int mSemGoToTopLastState;
    private Drawable mSemGoToTopLightImage;
    private boolean mSemGoToTopPressed;
    private final Rect mSemGoToTopRect;
    private int mSemGoToTopState;
    private boolean mSizeChange;
    private boolean mSmoothScrollingEnabled;
    private final Rect mTempRect;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    private static int clamp(int i, int i2, int i3) {
        if (i2 >= i3 || i < 0) {
            return 0;
        }
        return i2 + i > i3 ? i3 - i2 : i;
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onStartNestedScroll(View view, View view2, int i) {
        return (i & 2) != 0;
    }

    public void updateCustomEdgeGlow(Drawable drawable, Drawable drawable2) {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ScrollView> {
        private int mFillViewportId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mFillViewportId = propertyMapper.mapBoolean("fillViewport", 16843130);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ScrollView scrollView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mFillViewportId, scrollView.isFillViewport());
        }
    }

    public void setIgnoreDelaychildPrerssedState(boolean z) {
        this.mIgnoreDelaychildPrerssed = z;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void setTouchSlop(int i) {
        this.mTouchSlop = i;
    }

    public ScrollView(Context context) {
        this(context, null);
    }

    public ScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842880);
    }

    public ScrollView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new Rect();
        this.mIsLayoutDirty = true;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mLinear = false;
        this.ON_ABSORB_VELOCITY = 10000;
        this.mSmoothScrollingEnabled = true;
        this.mSemEnableGoToTop = false;
        this.mSizeChange = false;
        this.mSemGoToTopRect = new Rect();
        this.mOutline = new Outline();
        this.GTP_STATE_NONE = 0;
        this.GTP_STATE_SHOWN = 1;
        this.GTP_STATE_PRESSED = 2;
        this.GTP_STATE_MAINTAINED = 3;
        this.mSemGoToTopState = 0;
        this.mSemGoToTopLastState = 0;
        this.mSemGoToTopPressed = false;
        this.mIsGoToTopShown = false;
        this.mActivePointerId = -1;
        this.mScrollOffset = new int[2];
        this.mScrollConsumed = new int[2];
        this.mScrollStrictSpan = null;
        this.mFlingStrictSpan = null;
        this.mHoverTopAreaHeight = 0;
        this.mHoverBottomAreaHeight = 0;
        this.mHoverRecognitionDurationTime = 0L;
        this.mHoverRecognitionCurrentTime = 0L;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mHoverScrollStartTime = 0L;
        this.mHoverScrollDirection = -1;
        this.mIsHoverOverscrolled = false;
        this.mIgnoreDelaychildPrerssed = false;
        this.mPreviousTextViewScroll = false;
        this.mHoverScrollEnable = true;
        this.mHoverScrollStateChanged = false;
        this.mHoverAreaEnter = false;
        this.HOVERSCROLL_SPEED = 800.0f;
        this.HOVERSCROLL_DELAY = 15;
        this.mNeedsHoverScroll = false;
        this.SWITCH_CONTROL_FLING = 4000;
        this.SWITCH_CONTROL_SCROLL_MIN_DURATION = 0.6f;
        this.SWITCH_CONTROL_SCROLL_MAX_DURATION = 17.1f;
        this.mAutoscrollDurationGap = 1.178f;
        this.mGoToTopEdgeEffectRunnable = new Runnable() { // from class: android.widget.ScrollView.2
            @Override // java.lang.Runnable
            public void run() {
                ScrollView.this.mEdgeGlowTop.onAbsorb(10000);
                ScrollView.this.invalidate();
            }
        };
        this.mHoverScrollSpeed = 0;
        this.GO_TO_TOP_HIDE = 2500;
        this.mSemGoToTopFadeOutRunnable = new Runnable() { // from class: android.widget.ScrollView.3
            @Override // java.lang.Runnable
            public void run() {
                ScrollView.this.semPlayGoToTopFadeOut();
            }
        };
        this.mSemGoToTopFadeInRunnable = new Runnable() { // from class: android.widget.ScrollView.4
            @Override // java.lang.Runnable
            public void run() {
                ScrollView.this.semPlayGoToTopFadeIn();
            }
        };
        this.mSemAutoHide = new Runnable() { // from class: android.widget.ScrollView.5
            @Override // java.lang.Runnable
            public void run() {
                ScrollView.this.semSetupGoToTop(0);
            }
        };
        this.mEdgeGlowTop = new EdgeEffect(context, attributeSet);
        this.mEdgeGlowBottom = new EdgeEffect(context, attributeSet);
        this.mEdgeGlowTop.semSetHostView(this, true);
        this.mEdgeGlowBottom.semSetHostView(this, true);
        initScrollView();
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ScrollView, attributeSet, obtainStyledAttributes, i, i2);
        setFillViewport(obtainStyledAttributes.getBoolean(0, false));
        obtainStyledAttributes.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            setRevealOnFocusHint(false);
        }
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return !this.mIgnoreDelaychildPrerssed;
    }

    @Override // android.view.View
    protected float getTopFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (this.mScrollY < verticalFadingEdgeLength) {
            return this.mScrollY / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    @Override // android.view.View
    protected float getBottomFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        int bottom = (getChildAt(0).getBottom() - this.mScrollY) - (getHeight() - this.mPaddingBottom);
        if (bottom < verticalFadingEdgeLength) {
            return bottom / verticalFadingEdgeLength;
        }
        return 1.0f;
    }

    public void setEdgeEffectColor(int i) {
        setTopEdgeEffectColor(i);
        setBottomEdgeEffectColor(i);
    }

    public void setBottomEdgeEffectColor(int i) {
        this.mEdgeGlowBottom.setColor(i);
    }

    public void setTopEdgeEffectColor(int i) {
        this.mEdgeGlowTop.setColor(i);
    }

    public int getTopEdgeEffectColor() {
        return this.mEdgeGlowTop.getColor();
    }

    public int getBottomEdgeEffectColor() {
        return this.mEdgeGlowBottom.getColor();
    }

    public int getMaxScrollAmount() {
        return (int) ((this.mBottom - this.mTop) * 0.5f);
    }

    private void initScrollView() {
        this.mScroller = new OverScroller(getContext());
        setFocusable(true);
        setDescendantFocusability(262144);
        setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(this.mContext);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mOverscrollDistance = viewConfiguration.getScaledOverscrollDistance();
        this.mOverflingDistance = viewConfiguration.getScaledOverflingDistance();
        this.mVerticalScrollFactor = viewConfiguration.getScaledVerticalScrollFactor();
        initGoToTop();
    }

    private void initGoToTop() {
        this.mSemGoToTopRect.setEmpty();
        Resources resources = getResources();
        TypedValue typedValue = new TypedValue();
        if (this.mContext.getTheme().resolveAttribute(R.attr.semGoToTopStyle, typedValue, true)) {
            this.mSemGoToTopLightImage = this.mContext.getResources().getDrawable(typedValue.resourceId);
        }
        this.mGoToTopWH = resources.getDimensionPixelSize(R.dimen.sem_go_to_top_scrollableview_size);
        this.mGoToTopGap = resources.getDimensionPixelSize(R.dimen.sem_go_to_top_scrollableview_gap);
        this.mGoToTopElevation = resources.getDimensionPixelSize(R.dimen.sem_go_to_top_elevation);
        Log.d(TAG, "initGoToTop");
        if (this.mSemGoToTopState != 0) {
            this.mSemGoToTopImage.setBounds(0, 0, 0, 0);
        }
        this.mSemGoToTopState = 0;
        this.mSemGoToTopLastState = 0;
        removeCallbacks(this.mSemAutoHide);
        removeCallbacks(this.mSemGoToTopFadeInRunnable);
        removeCallbacks(this.mSemGoToTopFadeOutRunnable);
    }

    @Override // android.view.ViewGroup
    public void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("ScrollView can host only one direct child");
        }
        super.addView(view, i, layoutParams);
    }

    private boolean canScroll() {
        View childAt = getChildAt(0);
        if (childAt != null) {
            if (getHeight() < childAt.getHeight() + this.mPaddingTop + this.mPaddingBottom) {
                return true;
            }
        }
        return false;
    }

    private boolean canScrollUp() {
        View childAt = getChildAt(0);
        if (childAt != null) {
            if (getHeight() <= childAt.getHeight() + this.mPaddingTop + this.mPaddingBottom) {
                return true;
            }
        }
        return false;
    }

    private boolean canScrollDown() {
        View childAt = getChildAt(0);
        return childAt != null && this.mScrollY > ((childAt.getHeight() + this.mPaddingTop) + this.mPaddingBottom) - getHeight();
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    public void setFillViewport(boolean z) {
        if (z != this.mFillViewport) {
            this.mFillViewport = z;
            requestLayout();
        }
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    public void setSmoothScrollingEnabled(boolean z) {
        this.mSmoothScrollingEnabled = z;
    }

    @Override // android.widget.FrameLayout, android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        int i4;
        int i5;
        super.onMeasure(i, i2);
        if (this.mFillViewport && View.MeasureSpec.getMode(i2) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            int i6 = getContext().getApplicationInfo().targetSdkVersion;
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (i6 >= 23) {
                i3 = this.mPaddingLeft + this.mPaddingRight + layoutParams.leftMargin + layoutParams.rightMargin;
                i4 = this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin;
                i5 = layoutParams.bottomMargin;
            } else {
                i3 = this.mPaddingLeft + this.mPaddingRight;
                i4 = this.mPaddingTop;
                i5 = this.mPaddingBottom;
            }
            int measuredHeight = getMeasuredHeight() - (i4 + i5);
            if (childAt.getMeasuredHeight() < measuredHeight) {
                childAt.measure(getChildMeasureSpec(i, i3, layoutParams.width), View.MeasureSpec.makeMeasureSpec(measuredHeight, 1073741824));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int childCount = getChildCount();
        int scrollRange = getScrollRange();
        boolean isNeedToScroll = MultiSelection.isNeedToScroll();
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        if (this.mHoverTopAreaHeight <= 0 || this.mHoverBottomAreaHeight <= 0) {
            this.mHoverTopAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
            this.mHoverBottomAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        }
        int height = childCount != 0 ? getHeight() : 0;
        boolean z = motionEvent.getToolType(0) == 2;
        int action = motionEvent.getAction();
        if (action == 0) {
            this.mSemGoToTopPressed = false;
            if (semIsSupportGotoTop() && this.mSemGoToTopState != 2 && this.mSemGoToTopRect.contains(x, y)) {
                semSetupGoToTop(2);
                this.mSemGoToTopPressed = true;
                this.mSemGoToTopImage.setHotspot(x, y);
                this.mSemGoToTopImage.setState(new int[]{16842919, 16842910, 16842913});
                return true;
            }
        } else if (action != 1) {
            if (action != 2) {
                if (action == 3) {
                    if (semIsSupportGotoTop() && this.mSemGoToTopState != 0) {
                        this.mSemGoToTopImage.setState(StateSet.NOTHING);
                    }
                    this.mSemGoToTopPressed = false;
                }
            } else if (semIsSupportGotoTop() && this.mSemGoToTopState == 2) {
                if (!this.mSemGoToTopRect.contains(x, y)) {
                    this.mSemGoToTopState = 1;
                    this.mSemGoToTopImage.setState(StateSet.NOTHING);
                    semAutoHide();
                }
                return true;
            }
        } else {
            if (semIsSupportGotoTop() && this.mSemGoToTopState == 2) {
                if (canScrollUp()) {
                    post(new Runnable() { // from class: android.widget.ScrollView.1
                        @Override // java.lang.Runnable
                        public void run() {
                            ScrollView.this.smoothScrollTo(0, 0);
                        }
                    });
                    postDelayed(this.mGoToTopEdgeEffectRunnable, 150L);
                }
                this.mSemGoToTopState = 1;
                semAutoHide();
                this.mSemGoToTopImage.setState(StateSet.NOTHING);
                playSoundEffect(0);
                return true;
            }
            this.mSemGoToTopPressed = false;
        }
        if ((y > this.mHoverTopAreaHeight && y < height - this.mHoverBottomAreaHeight) || x <= 0 || x > getRight() || scrollRange == 0 || !z || motionEvent.getButtonState() != 32) {
            if (this.mHoverHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
            }
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mHoverAreaEnter = false;
            this.mIsHoverOverscrolled = false;
            return super.dispatchTouchEvent(motionEvent);
        }
        if (!this.mHoverAreaEnter) {
            this.mHoverScrollStartTime = System.currentTimeMillis();
        }
        switch (action) {
            case 211:
                if (semIsSupportGotoTop() && this.mSemGoToTopState != 2 && this.mSemGoToTopRect.contains(x, y)) {
                    semSetupGoToTop(2);
                    this.mSemGoToTopImage.setHotspot(x, y);
                    this.mSemGoToTopImage.setState(new int[]{16842919, 16842910, 16842913});
                    return true;
                }
                break;
            case 212:
                if (semIsSupportGotoTop() && this.mSemGoToTopState == 2) {
                    Log.d(TAG, "pen up false GOTOTOP");
                    if (canScrollUp()) {
                        smoothScrollTo(0, 0);
                        this.mEdgeGlowTop.onAbsorb(10000);
                        invalidate();
                    }
                    semSetupGoToTop(0);
                    this.mSemGoToTopImage.setState(StateSet.NOTHING);
                    return true;
                }
                if (this.mHoverHandler.hasMessages(1)) {
                    this.mHoverHandler.removeMessages(1);
                }
                this.mHoverRecognitionStartTime = 0L;
                this.mHoverScrollStartTime = 0L;
                this.mIsHoverOverscrolled = false;
                this.mHoverAreaEnter = false;
                return super.dispatchTouchEvent(motionEvent);
            case 213:
                if (semIsSupportGotoTop() && this.mSemGoToTopState == 2 && !this.mSemGoToTopRect.contains(x, y)) {
                    this.mSemGoToTopState = 1;
                    this.mSemGoToTopImage.setState(StateSet.NOTHING);
                    return true;
                }
                if (isNeedToScroll) {
                    if (y >= 0 && y <= this.mHoverTopAreaHeight) {
                        if (!this.mHoverAreaEnter) {
                            this.mHoverAreaEnter = true;
                            this.mHoverScrollStartTime = System.currentTimeMillis();
                        }
                        if (!this.mHoverHandler.hasMessages(1)) {
                            this.mHoverRecognitionStartTime = System.currentTimeMillis();
                            this.mHoverScrollDirection = 2;
                            this.mHoverHandler.sendEmptyMessage(1);
                        }
                    } else if (y >= height - this.mHoverBottomAreaHeight && y <= height) {
                        if (!this.mHoverAreaEnter) {
                            this.mHoverAreaEnter = true;
                            this.mHoverScrollStartTime = System.currentTimeMillis();
                        }
                        if (!this.mHoverHandler.hasMessages(1)) {
                            this.mHoverRecognitionStartTime = System.currentTimeMillis();
                            this.mHoverScrollDirection = 1;
                            this.mHoverHandler.sendEmptyMessage(1);
                        }
                    } else {
                        this.mHoverScrollStartTime = 0L;
                        this.mHoverRecognitionStartTime = 0L;
                        this.mHoverAreaEnter = false;
                        if (this.mHoverHandler.hasMessages(1)) {
                            this.mHoverHandler.removeMessages(1);
                        }
                        this.mIsHoverOverscrolled = false;
                    }
                } else if (this.mPreviousTextViewScroll && this.mHoverHandler.hasMessages(1)) {
                    this.mHoverHandler.removeMessages(1);
                }
                this.mPreviousTextViewScroll = isNeedToScroll;
                break;
                break;
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    private boolean semIsTalkBackIsRunning() {
        AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
        if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
            return false;
        }
        return accessibilityManager.semIsAccessibilityServiceEnabled(32) || accessibilityManager.semIsAccessibilityServiceEnabled(16) || accessibilityManager.semIsAccessibilityServiceEnabled(64);
    }

    private boolean semIsSupportGotoTop() {
        return this.mSemEnableGoToTop && !semIsTalkBackIsRunning();
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        boolean fullScroll;
        boolean fullScroll2;
        this.mTempRect.setEmpty();
        if (!canScroll()) {
            if (isFocused() && keyEvent.getKeyCode() != 4 && keyEvent.getKeyCode() != 111) {
                View findFocus = findFocus();
                if (findFocus == this) {
                    findFocus = null;
                }
                View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, 130);
                if (findNextFocus != null && findNextFocus != this && findNextFocus.requestFocus(130)) {
                    return true;
                }
            }
            return false;
        }
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 19) {
                if (!keyEvent.isAltPressed()) {
                    fullScroll = arrowScroll(33);
                } else {
                    fullScroll = fullScroll(33);
                }
                if (fullScroll) {
                    playSoundEffect(SoundEffectConstants.getContantForFocusDirection(33));
                }
                return fullScroll;
            }
            if (keyCode == 20) {
                if (!keyEvent.isAltPressed()) {
                    fullScroll2 = arrowScroll(130);
                } else {
                    fullScroll2 = fullScroll(130);
                }
                if (fullScroll2) {
                    playSoundEffect(SoundEffectConstants.getContantForFocusDirection(130));
                }
                return fullScroll2;
            }
            if (keyCode == 62) {
                return pageScroll(keyEvent.isShiftPressed() ? 33 : 130);
            }
            if (keyCode == 92) {
                return pageScroll(33);
            }
            if (keyCode == 93) {
                return pageScroll(130);
            }
            if (keyCode == 122) {
                return fullScroll(33);
            }
            if (keyCode == 123) {
                return fullScroll(130);
            }
        }
        return false;
    }

    private boolean inChild(int i, int i2) {
        if (getChildCount() > 0) {
            int i3 = this.mScrollY;
            View childAt = getChildAt(0);
            if (i2 >= childAt.getTop() - i3 && i2 < childAt.getBottom() - i3 && i >= childAt.getLeft() && i < childAt.getRight()) {
                return true;
            }
        }
        return false;
    }

    private void initOrResetVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            velocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void initDifferentialFlingHelperIfNotExists() {
        if (this.mDifferentialMotionFlingHelper == null) {
            this.mDifferentialMotionFlingHelper = new DifferentialMotionFlingHelper(this.mContext, new DifferentialFlingTarget());
        }
    }

    private void initHapticScrollFeedbackProviderIfNotExists() {
        if (this.mHapticScrollFeedbackProvider == null) {
            this.mHapticScrollFeedbackProvider = new HapticScrollFeedbackProvider(this);
        }
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (z) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        boolean z = true;
        if ((action == 2 && this.mIsBeingDragged) || super.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        if (getScrollY() == 0 && !canScrollVertically(1)) {
            return false;
        }
        int i = action & 255;
        if (i == 0) {
            int y = (int) motionEvent.getY();
            if (!inChild((int) motionEvent.getX(), y)) {
                this.mIsBeingDragged = false;
                recycleVelocityTracker();
            } else {
                this.mLastMotionY = y;
                this.mActivePointerId = motionEvent.getPointerId(0);
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mScroller.computeScrollOffset();
                if (Flags.viewVelocityApi()) {
                    setFrameContentVelocity(Math.abs(this.mScroller.getCurrVelocity()));
                }
                if (this.mScroller.isFinished() && this.mEdgeGlowBottom.isFinished() && this.mEdgeGlowTop.isFinished()) {
                    z = false;
                }
                this.mIsBeingDragged = z;
                if (!this.mEdgeGlowTop.isFinished()) {
                    this.mEdgeGlowTop.onPullDistance(0.0f, motionEvent.getX() / getWidth());
                }
                if (!this.mEdgeGlowBottom.isFinished()) {
                    this.mEdgeGlowBottom.onPullDistance(0.0f, 1.0f - (motionEvent.getX() / getWidth()));
                }
                if (this.mIsBeingDragged && this.mScrollStrictSpan == null) {
                    this.mScrollStrictSpan = StrictMode.enterCriticalSpan("ScrollView-scroll");
                }
                startNestedScroll(2);
            }
        } else {
            if (i != 1) {
                if (i == 2) {
                    int i2 = this.mActivePointerId;
                    if (i2 != -1) {
                        int findPointerIndex = motionEvent.findPointerIndex(i2);
                        if (findPointerIndex == -1) {
                            Log.e(TAG, "Invalid pointerId=" + i2 + " in onInterceptTouchEvent");
                        } else {
                            int y2 = (int) motionEvent.getY(findPointerIndex);
                            if (Math.abs(y2 - this.mLastMotionY) > this.mTouchSlop && (2 & getNestedScrollAxes()) == 0) {
                                this.mIsBeingDragged = true;
                                this.mLastMotionY = y2;
                                initVelocityTrackerIfNotExists();
                                this.mVelocityTracker.addMovement(motionEvent);
                                this.mNestedYOffset = 0;
                                if (this.mScrollStrictSpan == null) {
                                    this.mScrollStrictSpan = StrictMode.enterCriticalSpan("ScrollView-scroll");
                                }
                                ViewParent parent = getParent();
                                if (parent != null) {
                                    parent.requestDisallowInterceptTouchEvent(true);
                                }
                            }
                        }
                    }
                } else if (i != 3) {
                    if (i == 6) {
                        onSecondaryPointerUp(motionEvent);
                    }
                }
            }
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            recycleVelocityTracker();
            if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
                postInvalidateOnAnimation();
            }
            stopNestedScroll();
        }
        return this.mIsBeingDragged;
    }

    private boolean shouldDisplayEdgeEffects() {
        return getOverScrollMode() != 2;
    }

    public boolean isLockScreenMode() {
        Context context = this.mContext;
        Context context2 = this.mContext;
        return ((KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode();
    }

    @Deprecated
    public void semSetHoverScrollMode(boolean z) {
        this.mHoverScrollEnable = z;
        this.mHoverScrollStateChanged = true;
    }

    public void setHoverScrollSpeed(int i) {
        this.HOVERSCROLL_SPEED = i + 23;
    }

    public void setHoverScrollDelay(int i) {
        this.HOVERSCROLL_DELAY = i;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (!isHoveringUIEnabled()) {
            return super.dispatchHoverEvent(motionEvent);
        }
        int action = motionEvent.getAction();
        if (action == 9 || this.mHoverScrollStateChanged) {
            int toolType = motionEvent.getToolType(0);
            this.mNeedsHoverScroll = true;
            this.mHoverScrollStateChanged = false;
            if (!this.mHoverScrollEnable) {
                this.mNeedsHoverScroll = false;
            }
            if (this.mNeedsHoverScroll && toolType == 2 && Settings.System.getInt(this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0) != 1) {
                this.mNeedsHoverScroll = false;
            }
            if (this.mNeedsHoverScroll && toolType == 3) {
                this.mNeedsHoverScroll = false;
            }
        }
        if (!this.mNeedsHoverScroll) {
            return super.dispatchHoverEvent(motionEvent);
        }
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        int childCount = getChildCount();
        int scrollRange = getScrollRange();
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        if (this.mHoverTopAreaHeight <= 0 || this.mHoverBottomAreaHeight <= 0) {
            this.mHoverTopAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
            this.mHoverBottomAreaHeight = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        }
        int height = childCount != 0 ? getHeight() : 0;
        boolean z = motionEvent.getToolType(0) == 2;
        if ((y > this.mHoverTopAreaHeight && y < height - this.mHoverBottomAreaHeight) || x <= 0 || x > getRight() || scrollRange == 0 || ((y >= 0 && y <= this.mHoverTopAreaHeight && this.mScrollY <= 0 && this.mIsHoverOverscrolled) || ((y >= height - this.mHoverBottomAreaHeight && y <= height && this.mScrollY >= scrollRange && this.mIsHoverOverscrolled) || ((z && motionEvent.getButtonState() == 32) || !z || isLockScreenMode() || (this.mSemEnableGoToTop && this.mSemGoToTopState != 0 && this.mSemGoToTopRect.contains(x, y)))))) {
            if (this.mHoverHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
                showPointerIcon(motionEvent, 20001);
            }
            if ((y > this.mHoverTopAreaHeight && y < height - this.mHoverBottomAreaHeight) || x <= 0 || x > getRight()) {
                this.mIsHoverOverscrolled = false;
            }
            if (this.mHoverAreaEnter || this.mHoverScrollStartTime != 0) {
                showPointerIcon(motionEvent, 20001);
            }
            this.mHoverRecognitionStartTime = 0L;
            this.mHoverScrollStartTime = 0L;
            this.mHoverAreaEnter = false;
            return super.dispatchHoverEvent(motionEvent);
        }
        if (!this.mHoverAreaEnter) {
            this.mHoverScrollStartTime = System.currentTimeMillis();
        }
        if (action != 7) {
            if (action == 9) {
                this.mHoverAreaEnter = true;
                if (y >= 0 && y <= this.mHoverTopAreaHeight) {
                    if (!this.mHoverHandler.hasMessages(1)) {
                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                        showPointerIcon(motionEvent, 20011);
                        this.mHoverScrollDirection = 2;
                        this.mHoverHandler.sendEmptyMessage(1);
                    }
                } else if (y >= height - this.mHoverBottomAreaHeight && y <= height && !this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    showPointerIcon(motionEvent, 20015);
                    this.mHoverScrollDirection = 1;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (action == 10) {
                if (this.mHoverHandler.hasMessages(1)) {
                    this.mHoverHandler.removeMessages(1);
                }
                showPointerIcon(motionEvent, 20001);
                this.mHoverRecognitionStartTime = 0L;
                this.mHoverScrollStartTime = 0L;
                this.mIsHoverOverscrolled = false;
                this.mHoverAreaEnter = false;
                return super.dispatchHoverEvent(motionEvent);
            }
        } else {
            if (!this.mHoverAreaEnter) {
                this.mHoverAreaEnter = true;
                motionEvent.setAction(10);
                return super.dispatchHoverEvent(motionEvent);
            }
            if (y >= 0 && y <= this.mHoverTopAreaHeight) {
                if (!this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 1) {
                        showPointerIcon(motionEvent, 20011);
                    }
                    this.mHoverScrollDirection = 2;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (y >= height - this.mHoverBottomAreaHeight && y <= height && !this.mHoverHandler.hasMessages(1)) {
                this.mHoverRecognitionStartTime = System.currentTimeMillis();
                if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 2) {
                    showPointerIcon(motionEvent, 20015);
                }
                this.mHoverScrollDirection = 1;
                this.mHoverHandler.sendEmptyMessage(1);
            }
        }
        return true;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        ViewParent parent;
        boolean z;
        int round;
        initVelocityTrackerIfNotExists();
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        boolean z2 = false;
        if (actionMasked == 0) {
            this.mNestedYOffset = 0;
        }
        obtain.offsetLocation(0.0f, this.mNestedYOffset);
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (findPointerIndex == -1) {
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        Log.e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                    } else {
                        int y = (int) motionEvent.getY(findPointerIndex);
                        int i = this.mLastMotionY - y;
                        if (dispatchNestedPreScroll(0, i, this.mScrollConsumed, this.mScrollOffset)) {
                            i -= this.mScrollConsumed[1];
                            obtain.offsetLocation(0.0f, this.mScrollOffset[1]);
                            this.mNestedYOffset += this.mScrollOffset[1];
                        }
                        if (!this.mIsBeingDragged && Math.abs(i) > this.mTouchSlop) {
                            ViewParent parent2 = getParent();
                            if (parent2 != null) {
                                parent2.requestDisallowInterceptTouchEvent(true);
                            }
                            this.mIsBeingDragged = true;
                            if (i > 0) {
                                i -= this.mTouchSlop;
                            } else {
                                i += this.mTouchSlop;
                            }
                        }
                        if (this.mIsBeingDragged) {
                            this.mLastMotionY = y - this.mScrollOffset[1];
                            int i2 = this.mScrollY;
                            int scrollRange = getScrollRange();
                            int overScrollMode = getOverScrollMode();
                            boolean z3 = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0);
                            float x = motionEvent.getX(findPointerIndex) / getWidth();
                            if (z3) {
                                if (i < 0 && this.mEdgeGlowBottom.getDistance() != 0.0f) {
                                    round = Math.round(getHeight() * this.mEdgeGlowBottom.onPullDistance(i / getHeight(), 1.0f - x));
                                } else {
                                    round = (i <= 0 || this.mEdgeGlowTop.getDistance() == 0.0f) ? 0 : Math.round((-getHeight()) * this.mEdgeGlowTop.onPullDistance((-i) / getHeight(), x));
                                }
                                i -= round;
                            }
                            int i3 = i;
                            z = false;
                            overScrollBy(0, i3, 0, this.mScrollY, 0, scrollRange, 0, this.mOverscrollDistance, true);
                            int i4 = this.mScrollY - i2;
                            if (dispatchNestedScroll(0, i4, 0, i3 - i4, this.mScrollOffset)) {
                                int i5 = this.mLastMotionY;
                                int i6 = this.mScrollOffset[1];
                                this.mLastMotionY = i5 - i6;
                                obtain.offsetLocation(0.0f, i6);
                                this.mNestedYOffset += this.mScrollOffset[1];
                            } else if (!this.mSemGoToTopPressed && z3) {
                                float f = i3;
                                if (f != 0.0f) {
                                    int i7 = i2 + i3;
                                    if (i7 < 0) {
                                        this.mEdgeGlowTop.onPullDistance((-i3) / getHeight(), x);
                                        if (!this.mEdgeGlowBottom.isFinished()) {
                                            this.mEdgeGlowBottom.onRelease();
                                        }
                                        z2 = true;
                                    } else if (i7 > scrollRange) {
                                        this.mEdgeGlowBottom.onPullDistance(f / getHeight(), 1.0f - x);
                                        semShowGoToTop();
                                        if (!this.mEdgeGlowTop.isFinished()) {
                                            this.mEdgeGlowTop.onRelease();
                                        }
                                        z2 = false;
                                        z = true;
                                    } else {
                                        z2 = false;
                                    }
                                    if (shouldDisplayEdgeEffects() && (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished())) {
                                        postInvalidateOnAnimation();
                                    }
                                    i = i3;
                                }
                            }
                            i = i3;
                            z2 = false;
                        } else {
                            z = false;
                        }
                        if (Flags.enableScrollFeedbackForTouch()) {
                            if (z2 || z) {
                                initHapticScrollFeedbackProviderIfNotExists();
                                this.mHapticScrollFeedbackProvider.onScrollLimit(obtain.getDeviceId(), obtain.getSource(), 1, z2);
                            } else if (Math.abs(i) != 0) {
                                initHapticScrollFeedbackProviderIfNotExists();
                                this.mHapticScrollFeedbackProvider.onScrollProgress(obtain.getDeviceId(), obtain.getSource(), 1, i);
                            }
                        }
                    }
                } else if (actionMasked != 3) {
                    if (actionMasked == 5) {
                        int actionIndex = motionEvent.getActionIndex();
                        this.mLastMotionY = (int) motionEvent.getY(actionIndex);
                        this.mActivePointerId = motionEvent.getPointerId(actionIndex);
                    } else if (actionMasked == 6) {
                        onSecondaryPointerUp(motionEvent);
                        int i8 = this.mActivePointerId;
                        if (i8 == -1 || motionEvent.findPointerIndex(i8) < 0) {
                            Log.e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                        } else {
                            this.mLastMotionY = (int) motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId));
                        }
                    }
                } else if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
                        postInvalidateOnAnimation();
                    }
                    this.mActivePointerId = -1;
                    endDrag();
                }
            } else if (this.mIsBeingDragged) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int yVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
                if (Math.abs(yVelocity) > this.mMinimumVelocity) {
                    flingWithNestedDispatch(-yVelocity);
                } else if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
                    postInvalidateOnAnimation();
                }
                this.mActivePointerId = -1;
                endDrag();
                velocityTracker.clear();
            }
        } else {
            if (getChildCount() == 0) {
                return false;
            }
            if (!this.mScroller.isFinished() && (parent = getParent()) != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                StrictMode.Span span = this.mFlingStrictSpan;
                if (span != null) {
                    span.finish();
                    this.mFlingStrictSpan = null;
                }
            }
            this.mLastMotionY = (int) motionEvent.getY();
            this.mActivePointerId = motionEvent.getPointerId(0);
            startNestedScroll(2);
        }
        VelocityTracker velocityTracker2 = this.mVelocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.addMovement(obtain);
        }
        obtain.recycle();
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mLastMotionY = (int) motionEvent.getY(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        int i;
        int i2;
        if (motionEvent.getAction() == 8) {
            if (motionEvent.isFromSource(2)) {
                i = 9;
            } else {
                i = motionEvent.isFromSource(4194304) ? 26 : -1;
            }
            float axisValue = i == -1 ? 0.0f : motionEvent.getAxisValue(i);
            int round = Math.round(this.mVerticalScrollFactor * axisValue);
            if (round != 0) {
                startNestedScroll(2);
                int i3 = 0;
                if (!dispatchNestedPreScroll(0, -round, null, null)) {
                    int scrollRange = getScrollRange();
                    int i4 = this.mScrollY;
                    int i5 = i4 - round;
                    int overScrollMode = getOverScrollMode();
                    boolean z = !motionEvent.isFromSource(8194) && (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0));
                    if (i5 < 0) {
                        if (z) {
                            this.mEdgeGlowTop.onPullDistance((-i5) / getHeight(), 0.5f);
                            this.mEdgeGlowTop.onRelease();
                            invalidate();
                            i2 = 1;
                        } else {
                            i2 = 0;
                        }
                    } else if (i5 > scrollRange) {
                        if (z) {
                            this.mEdgeGlowBottom.onPullDistance((i5 - scrollRange) / getHeight(), 0.5f);
                            this.mEdgeGlowBottom.onRelease();
                            invalidate();
                            i3 = 1;
                        }
                        int i6 = i3;
                        i3 = scrollRange;
                        i2 = i6;
                    } else {
                        i2 = 0;
                        i3 = i5;
                    }
                    if (i3 != i4) {
                        semShowGoToTop();
                        super.scrollTo(this.mScrollX, i3);
                        return true;
                    }
                    if (z) {
                        if (axisValue > 0.0f && this.mScrollY <= 0) {
                            this.mEdgeGlowTop.setSize(getWidth(), getHeight());
                            this.mEdgeGlowTop.onAbsorb(10000);
                            if (!this.mEdgeGlowBottom.isFinished()) {
                                this.mEdgeGlowBottom.onRelease();
                            }
                        } else if (axisValue < 0.0f && !canScrollDown()) {
                            this.mEdgeGlowBottom.setSize(getWidth(), getHeight());
                            this.mEdgeGlowBottom.onAbsorb(10000);
                            semShowGoToTop();
                            if (!this.mEdgeGlowTop.isFinished()) {
                                this.mEdgeGlowTop.onRelease();
                            }
                        }
                        if (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished()) {
                            invalidate();
                        }
                    }
                    if (i2 != 0) {
                        return true;
                    }
                }
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (this.mScrollY != i2) {
            semShowGoToTop();
        }
        if (!this.mScroller.isFinished()) {
            int i3 = this.mScrollX;
            int i4 = this.mScrollY;
            this.mScrollX = i;
            this.mScrollY = i2;
            invalidateParentIfNeeded();
            onScrollChanged(this.mScrollX, this.mScrollY, i3, i4);
            if (z2) {
                this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange());
            }
        } else {
            super.scrollTo(i, i2);
        }
        awakenScrollBars();
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        int i2;
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        if (bundle != null) {
            this.mAutoscrollDurationGap = 16.5f / (bundle.getInt("auto_scroll_speed_level_count", 15) - 1);
            i2 = bundle.getInt("auto_scroll_speed_level", 8) - 1;
        } else {
            i2 = 7;
        }
        if (i != 4096) {
            if (i != 8192) {
                if (i == 4194304) {
                    if (!canScroll()) {
                        return false;
                    }
                    float f = 17.1f - (this.mAutoscrollDurationGap * i2);
                    this.mAutoscrollDuration = f;
                    autoScrollWithDuration(f);
                    return true;
                }
                if (i == 8388608) {
                    if (!canScroll()) {
                        return false;
                    }
                    OverScroller overScroller = this.mScroller;
                    if (overScroller != null && !overScroller.isFinished()) {
                        this.mScroller.abortAnimation();
                    }
                    this.mLinear = false;
                    OverScroller overScroller2 = this.mScroller;
                    if (overScroller2 != null) {
                        overScroller2.setInterpolator(null);
                    }
                    return true;
                }
                if (i != 16908344) {
                    if (i != 16908346) {
                        if (i == 67108864) {
                            Log.d(TAG, "SEM_ACTION_AUTOSCROLL_TOP");
                            if (!canScroll()) {
                                return false;
                            }
                            smoothScrollToWithDuration(0, 0, 0);
                            return true;
                        }
                        if (i == 268435456) {
                            if (!canScroll()) {
                                return false;
                            }
                            float f2 = this.mAutoscrollDuration;
                            if (f2 > 0.6f) {
                                this.mAutoscrollDuration = f2 - this.mAutoscrollDurationGap;
                            }
                            autoScrollWithDuration(this.mAutoscrollDuration);
                            return true;
                        }
                        if (i != 536870912 || !canScroll()) {
                            return false;
                        }
                        float f3 = this.mAutoscrollDuration;
                        if (f3 < 17.1f) {
                            this.mAutoscrollDuration = f3 + this.mAutoscrollDurationGap;
                        }
                        autoScrollWithDuration(this.mAutoscrollDuration);
                        return true;
                    }
                }
            }
            int max = Math.max(this.mScrollY - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop), 0);
            if (max == this.mScrollY) {
                return false;
            }
            smoothScrollTo(0, max);
            return true;
        }
        int min = Math.min(this.mScrollY + ((getHeight() - this.mPaddingBottom) - this.mPaddingTop), getScrollRange());
        if (min == this.mScrollY) {
            return false;
        }
        smoothScrollTo(0, min);
        return true;
    }

    private void autoScrollWithDuration(float f) {
        int scrollRange = (int) (f * (getScrollRange() - this.mScrollY));
        this.mLinear = true;
        Log.d(TAG, "autoScrollWithDuration() duration = " + scrollRange);
        smoothScrollByWithDuration(0, getScrollRange(), scrollRange);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ScrollView.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        int scrollRange;
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        if (isEnabled() && (scrollRange = getScrollRange()) > 0) {
            accessibilityNodeInfo.setScrollable(true);
            if (this.mScrollY > 0) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
            }
            if (this.mScrollY < scrollRange) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
            }
        }
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.SEM_ACTION_AUTOSCROLL_ON);
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.SEM_ACTION_AUTOSCROLL_OFF);
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(getScrollRange() > 0);
        accessibilityEvent.setMaxScrollX(this.mScrollX);
        accessibilityEvent.setMaxScrollY(getScrollRange());
    }

    private int getScrollRange() {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getHeight() - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop));
        }
        return 0;
    }

    private View findFocusableViewInBounds(boolean z, int i, int i2) {
        ArrayList<View> focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            View view2 = focusables.get(i3);
            int top = view2.getTop();
            int bottom = view2.getBottom();
            if (i < bottom && top < i2) {
                boolean z3 = i < top && bottom < i2;
                if (view == null) {
                    view = view2;
                    z2 = z3;
                } else {
                    boolean z4 = (z && top < view.getTop()) || (!z && bottom > view.getBottom());
                    if (z2) {
                        if (z3) {
                            if (!z4) {
                            }
                            view = view2;
                        }
                    } else if (z3) {
                        view = view2;
                        z2 = true;
                    } else {
                        if (!z4) {
                        }
                        view = view2;
                    }
                }
            }
        }
        return view;
    }

    public boolean pageScroll(int i) {
        boolean z = i == 130;
        int height = getHeight();
        if (z) {
            this.mTempRect.top = getScrollY() + height;
            int childCount = getChildCount();
            if (childCount > 0) {
                View childAt = getChildAt(childCount - 1);
                if (this.mTempRect.top + height > childAt.getBottom()) {
                    this.mTempRect.top = childAt.getBottom() - height;
                }
            }
        } else {
            this.mTempRect.top = getScrollY() - height;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        Rect rect = this.mTempRect;
        rect.bottom = rect.top + height;
        return scrollAndFocus(i, this.mTempRect.top, this.mTempRect.bottom);
    }

    public boolean fullScroll(int i) {
        int childCount;
        boolean z = i == 130;
        int height = getHeight();
        this.mTempRect.top = 0;
        this.mTempRect.bottom = height;
        if (z && (childCount = getChildCount()) > 0) {
            this.mTempRect.bottom = getChildAt(childCount - 1).getBottom() + this.mPaddingBottom;
            Rect rect = this.mTempRect;
            rect.top = rect.bottom - height;
        }
        return scrollAndFocus(i, this.mTempRect.top, this.mTempRect.bottom);
    }

    private boolean scrollAndFocus(int i, int i2, int i3) {
        int height = getHeight();
        int scrollY = getScrollY();
        int i4 = height + scrollY;
        boolean z = false;
        boolean z2 = i == 33;
        View findFocusableViewInBounds = findFocusableViewInBounds(z2, i2, i3);
        if (findFocusableViewInBounds == null) {
            findFocusableViewInBounds = this;
        }
        if (i2 < scrollY || i3 > i4) {
            doScrollY(z2 ? i2 - scrollY : i3 - i4);
            z = true;
        }
        if (findFocusableViewInBounds != findFocus()) {
            findFocusableViewInBounds.requestFocus(i);
        }
        return z;
    }

    public boolean arrowScroll(int i) {
        int bottom;
        View findFocus = findFocus();
        if (findFocus == this) {
            findFocus = null;
        }
        View findNextFocus = FocusFinder.getInstance().findNextFocus(this, findFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (findNextFocus != null && isWithinDeltaOfScreen(findNextFocus, maxScrollAmount, getHeight())) {
            findNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(findNextFocus, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            doScrollY(computeScrollDeltaToGetChildRectOnScreen);
            if (this.mSemEnableGoToTop && canScrollUp() && this.mSemGoToTopState != 2 && computeScrollDeltaToGetChildRectOnScreen != 0) {
                semSetupGoToTop(1);
                semAutoHide();
            }
            findNextFocus.requestFocus(i);
        } else {
            if (i == 33 && getScrollY() < maxScrollAmount) {
                maxScrollAmount = getScrollY();
            } else if (i == 130 && getChildCount() > 0 && (bottom = getChildAt(0).getBottom() - ((getScrollY() + getHeight()) - this.mPaddingBottom)) < maxScrollAmount) {
                maxScrollAmount = bottom;
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            semShowGoToTop();
            if (i != 130) {
                maxScrollAmount = -maxScrollAmount;
            }
            doScrollY(maxScrollAmount);
        }
        if (findFocus != null && findFocus.isFocused() && isOffScreen(findFocus)) {
            int descendantFocusability = getDescendantFocusability();
            setDescendantFocusability(131072);
            requestFocus();
            setDescendantFocusability(descendantFocusability);
        }
        return true;
    }

    private boolean isOffScreen(View view) {
        return !isWithinDeltaOfScreen(view, 0, getHeight());
    }

    private boolean isWithinDeltaOfScreen(View view, int i, int i2) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.bottom + i >= getScrollY() && this.mTempRect.top - i <= getScrollY() + i2;
    }

    private void doScrollY(int i) {
        if (i != 0) {
            if (this.mSmoothScrollingEnabled) {
                smoothScrollBy(0, i);
            } else {
                scrollBy(0, i);
            }
        }
    }

    public final void smoothScrollBy(int i, int i2) {
        if (getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
            int max = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop));
            int i3 = this.mScrollY;
            this.mScroller.startScroll(this.mScrollX, i3, 0, Math.max(0, Math.min(i2 + i3, max)) - i3);
            postInvalidateOnAnimation();
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                StrictMode.Span span = this.mFlingStrictSpan;
                if (span != null) {
                    span.finish();
                    this.mFlingStrictSpan = null;
                }
            }
            scrollBy(i, i2);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollByWithDuration(int i, int i2, int i3) {
        if (getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
            int max = Math.max(0, getChildAt(0).getHeight() - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop));
            int i4 = this.mScrollY;
            int max2 = Math.max(0, Math.min(i2 + i4, max)) - i4;
            this.mScroller.setInterpolator(this.mLinear ? sLinearInterpolator : null);
            this.mScroller.startScroll(this.mScrollX, i4, 0, max2, i3);
            postInvalidateOnAnimation();
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
                StrictMode.Span span = this.mFlingStrictSpan;
                if (span != null) {
                    span.finish();
                    this.mFlingStrictSpan = null;
                }
            }
            scrollBy(i, i2);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollTo(int i, int i2) {
        smoothScrollBy(i - this.mScrollX, i2 - this.mScrollY);
    }

    public final void smoothScrollToWithDuration(int i, int i2, int i3) {
        smoothScrollByWithDuration(i - this.mScrollX, i2 - this.mScrollY, i3);
    }

    @Override // android.view.View
    protected int computeVerticalScrollRange() {
        int childCount = getChildCount();
        int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
        if (childCount == 0) {
            return height;
        }
        int bottom = getChildAt(0).getBottom();
        int i = this.mScrollY;
        int max = Math.max(0, bottom - height);
        return i < 0 ? bottom - i : i > max ? bottom + (i - max) : bottom;
    }

    @Override // android.view.View
    protected int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override // android.view.ViewGroup
    protected void measureChild(View view, int i, int i2) {
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight, view.getLayoutParams().width), View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i2) - (this.mPaddingTop + this.mPaddingBottom)), 0));
    }

    @Override // android.view.ViewGroup
    protected void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(getChildMeasureSpec(i, this.mPaddingLeft + this.mPaddingRight + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + i2, marginLayoutParams.width), View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i3) - ((((this.mPaddingTop + this.mPaddingBottom) + marginLayoutParams.topMargin) + marginLayoutParams.bottomMargin) + i4)), 0));
    }

    @Override // android.view.View
    public void computeScroll() {
        ScrollView scrollView;
        if (this.mScroller.computeScrollOffset()) {
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int consumeFlingInStretch = consumeFlingInStretch(currY - i2);
            if (i == currX && consumeFlingInStretch == 0) {
                scrollView = this;
            } else {
                int scrollRange = getScrollRange();
                int overScrollMode = getOverScrollMode();
                boolean z = true;
                if (overScrollMode != 0 && (overScrollMode != 1 || scrollRange <= 0)) {
                    z = false;
                }
                boolean z2 = z;
                scrollView = this;
                scrollView.overScrollBy(currX - i, consumeFlingInStretch, i, i2, 0, scrollRange, 0, this.mOverflingDistance, false);
                scrollView.onScrollChanged(scrollView.mScrollX, scrollView.mScrollY, i, i2);
                if (z2 && consumeFlingInStretch != 0) {
                    if (currY < 0 && i2 >= 0) {
                        scrollView.mEdgeGlowTop.onAbsorb((int) scrollView.mScroller.getCurrVelocity());
                    } else if (currY > scrollRange && i2 <= scrollRange) {
                        scrollView.mEdgeGlowBottom.onAbsorb((int) scrollView.mScroller.getCurrVelocity());
                    }
                }
            }
            if (!scrollView.awakenScrollBars()) {
                scrollView.postInvalidateOnAnimation();
            }
            if (Flags.viewVelocityApi()) {
                scrollView.setFrameContentVelocity(Math.abs(scrollView.mScroller.getCurrVelocity()));
                return;
            }
            return;
        }
        StrictMode.Span span = this.mFlingStrictSpan;
        if (span != null) {
            span.finish();
            this.mFlingStrictSpan = null;
        }
    }

    private int consumeFlingInStretch(int i) {
        EdgeEffect edgeEffect;
        EdgeEffect edgeEffect2;
        int scrollY = getScrollY();
        if (scrollY < 0 || scrollY > getScrollRange()) {
            return i;
        }
        if (i > 0 && (edgeEffect2 = this.mEdgeGlowTop) != null && edgeEffect2.getDistance() != 0.0f) {
            int round = Math.round(((-r1) / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowTop.onPullDistance(((-i) * FLING_DESTRETCH_FACTOR) / getHeight(), 0.5f));
            this.mEdgeGlowTop.onRelease();
            if (round != i) {
                this.mEdgeGlowTop.finish();
            }
            return i - round;
        }
        if (i >= 0 || (edgeEffect = this.mEdgeGlowBottom) == null || edgeEffect.getDistance() == 0.0f) {
            return i;
        }
        float height = getHeight();
        int round2 = Math.round((height / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowBottom.onPullDistance((i * FLING_DESTRETCH_FACTOR) / height, 0.5f));
        this.mEdgeGlowBottom.onRelease();
        if (round2 != i) {
            this.mEdgeGlowBottom.finish();
        }
        return i - round2;
    }

    public void scrollToDescendant(View view) {
        if (!this.mIsLayoutDirty && view != null) {
            view.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(view, this.mTempRect);
            int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
            if (computeScrollDeltaToGetChildRectOnScreen != 0) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
                return;
            }
            return;
        }
        this.mChildToScrollTo = view;
    }

    private boolean scrollToChildRect(Rect rect, boolean z) {
        int computeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z2 = computeScrollDeltaToGetChildRectOnScreen != 0;
        if (z2) {
            if (z) {
                scrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
                return z2;
            }
            smoothScrollBy(0, computeScrollDeltaToGetChildRectOnScreen);
        }
        return z2;
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int height = getHeight();
        int scrollY = getScrollY();
        int i3 = scrollY + height;
        int verticalFadingEdgeLength = getVerticalFadingEdgeLength();
        if (rect.top > 0) {
            scrollY += verticalFadingEdgeLength;
        }
        if (rect.bottom < getChildAt(0).getHeight()) {
            i3 -= verticalFadingEdgeLength;
        }
        if (rect.bottom > i3 && rect.top > scrollY) {
            if (rect.height() > height) {
                i2 = rect.top - scrollY;
            } else {
                i2 = rect.bottom - i3;
            }
            return Math.min(i2, getChildAt(0).getBottom() - i3);
        }
        if (rect.top >= scrollY || rect.bottom >= i3) {
            return 0;
        }
        if (rect.height() > height) {
            i = 0 - (i3 - rect.bottom);
        } else {
            i = 0 - (scrollY - rect.top);
        }
        return Math.max(i, -getScrollY());
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (view2 != null && view2.getRevealOnFocusHint()) {
            if (!this.mIsLayoutDirty) {
                scrollToDescendant(view2);
            } else {
                this.mChildToScrollTo = view2;
            }
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        View findNextFocusFromRect;
        if (i == 2) {
            i = 130;
        } else if (i == 1) {
            i = 33;
        }
        if (rect == null) {
            findNextFocusFromRect = FocusFinder.getInstance().findNextFocus(this, null, i);
        } else {
            findNextFocusFromRect = FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        }
        if (findNextFocusFromRect == null || isOffScreen(findNextFocusFromRect)) {
            return false;
        }
        return findNextFocusFromRect.requestFocus(i, rect);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean z) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return scrollToChildRect(rect, z);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        StrictMode.Span span = this.mScrollStrictSpan;
        if (span != null) {
            span.finish();
            this.mScrollStrictSpan = null;
        }
        StrictMode.Span span2 = this.mFlingStrictSpan;
        if (span2 != null) {
            span2.finish();
            this.mFlingStrictSpan = null;
        }
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        this.mIsLayoutDirty = false;
        View view = this.mChildToScrollTo;
        if (view != null && isViewDescendantOf(view, this)) {
            scrollToDescendant(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!isLaidOut()) {
            SavedState savedState = this.mSavedState;
            if (savedState != null) {
                this.mScrollY = savedState.scrollPosition;
                this.mSavedState = null;
            }
            int max = Math.max(0, (getChildCount() > 0 ? getChildAt(0).getMeasuredHeight() : 0) - (((i4 - i2) - this.mPaddingBottom) - this.mPaddingTop));
            if (this.mScrollY > max) {
                this.mScrollY = max;
            } else if (this.mScrollY < 0) {
                this.mScrollY = 0;
            }
        }
        if (z) {
            Log.d(TAG, " onsize change changed ");
            this.mSizeChange = true;
            semSetupGoToTop(3);
            semAutoHide();
        }
        scrollTo(this.mScrollX, this.mScrollY);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View findFocus = findFocus();
        if (findFocus == null || this == findFocus || !isWithinDeltaOfScreen(findFocus, 0, i4)) {
            return;
        }
        findFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(findFocus, this.mTempRect);
        doScrollY(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    }

    private static boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && isViewDescendantOf((View) parent, view2);
    }

    public void fling(int i) {
        if (getChildCount() > 0) {
            int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
            this.mScroller.fling(this.mScrollX, this.mScrollY, 0, i, 0, 0, 0, Math.max(0, getChildAt(0).getHeight() - height), 0, height / 2);
            if (Flags.viewVelocityApi()) {
                setFrameContentVelocity(Math.abs(this.mScroller.getCurrVelocity()));
            }
            if (this.mFlingStrictSpan == null) {
                this.mFlingStrictSpan = StrictMode.enterCriticalSpan("ScrollView-fling");
            }
            postInvalidateOnAnimation();
        }
    }

    private void flingWithoutAcc(int i) {
        if (getChildCount() > 0) {
            int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
            this.mScroller.fling(this.mScrollX, this.mScrollY, 0, i, 0, 0, 0, Math.max(0, getChildAt(0).getHeight() - height), 0, height / 2, true);
            if (this.mFlingStrictSpan == null) {
                this.mFlingStrictSpan = StrictMode.enterCriticalSpan("ScrollView-fling");
            }
            postInvalidateOnAnimation();
        }
    }

    private void flingWithNestedDispatch(int i) {
        boolean z = (this.mScrollY > 0 || i > 0) && (this.mScrollY < getScrollRange() || i < 0);
        float f = i;
        if (dispatchNestedPreFling(0.0f, f)) {
            return;
        }
        boolean dispatchNestedFling = dispatchNestedFling(0.0f, f, z);
        if (z) {
            fling(i);
            return;
        }
        if (dispatchNestedFling) {
            return;
        }
        if (!this.mEdgeGlowTop.isFinished()) {
            int i2 = -i;
            if (shouldAbsorb(this.mEdgeGlowTop, i2)) {
                this.mEdgeGlowTop.onAbsorb(i2);
                return;
            } else {
                fling(i);
                return;
            }
        }
        if (this.mEdgeGlowBottom.isFinished()) {
            return;
        }
        if (shouldAbsorb(this.mEdgeGlowBottom, i)) {
            this.mEdgeGlowBottom.onAbsorb(i);
        } else {
            fling(i);
        }
    }

    private boolean shouldAbsorb(EdgeEffect edgeEffect, int i) {
        if (i > 0) {
            return true;
        }
        return ((float) this.mScroller.getSplineFlingDistance(-i)) < edgeEffect.getDistance() * ((float) getHeight());
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        recycleVelocityTracker();
        if (shouldDisplayEdgeEffects()) {
            this.mEdgeGlowTop.onRelease();
            this.mEdgeGlowBottom.onRelease();
        }
        StrictMode.Span span = this.mScrollStrictSpan;
        if (span != null) {
            span.finish();
            this.mScrollStrictSpan = null;
        }
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            int clamp = clamp(i, (getWidth() - this.mPaddingRight) - this.mPaddingLeft, childAt.getWidth());
            int clamp2 = clamp(i2, (getHeight() - this.mPaddingBottom) - this.mPaddingTop, childAt.getHeight());
            if (clamp == this.mScrollX && clamp2 == this.mScrollY) {
                return;
            }
            semShowGoToTop();
            super.scrollTo(clamp, clamp2);
        }
    }

    @Override // android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return this.mSemGoToTopImage == drawable || super.verifyDrawable(drawable);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScrollAccepted(View view, View view2, int i) {
        super.onNestedScrollAccepted(view, view2, i);
        startNestedScroll(2);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onStopNestedScroll(View view) {
        super.onStopNestedScroll(view);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void onNestedScroll(View view, int i, int i2, int i3, int i4) {
        int i5 = this.mScrollY;
        scrollBy(0, i4);
        int i6 = this.mScrollY - i5;
        dispatchNestedScroll(0, i6, 0, i4 - i6, null);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean onNestedFling(View view, float f, float f2, boolean z) {
        if (z) {
            return false;
        }
        flingWithNestedDispatch((int) f2);
        return true;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        int width;
        int height;
        float f;
        float f2;
        int width2;
        int height2;
        float f3;
        float f4;
        super.draw(canvas);
        if (shouldDisplayEdgeEffects()) {
            int i = this.mScrollY;
            boolean clipToPadding = getClipToPadding();
            if (!this.mEdgeGlowTop.isFinished()) {
                int save = canvas.save();
                if (clipToPadding) {
                    width2 = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                    height2 = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                    f3 = this.mPaddingLeft;
                    f4 = this.mPaddingTop;
                } else {
                    width2 = getWidth();
                    height2 = getHeight();
                    f3 = 0.0f;
                    f4 = 0.0f;
                }
                canvas.translate(f3, Math.min(0, i) + f4);
                this.mEdgeGlowTop.setSize(width2, height2);
                if (this.mEdgeGlowTop.draw(canvas)) {
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(save);
            }
            if (!this.mEdgeGlowBottom.isFinished()) {
                int save2 = canvas.save();
                if (clipToPadding) {
                    width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                    height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                    f = this.mPaddingLeft;
                    f2 = this.mPaddingTop;
                } else {
                    width = getWidth();
                    height = getHeight();
                    f = 0.0f;
                    f2 = 0.0f;
                }
                canvas.translate((-width) + f, Math.max(getScrollRange(), i) + height + f2);
                canvas.rotate(180.0f, width, 0.0f);
                this.mEdgeGlowBottom.setSize(width, height);
                if (this.mEdgeGlowBottom.draw(canvas)) {
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(save2);
            }
        }
        if (this.mSemEnableGoToTop) {
            drawGoToTop(canvas);
        }
    }

    private void drawGoToTop(Canvas canvas) {
        int i = this.mScrollY;
        int save = canvas.save();
        canvas.translate(0.0f, i);
        if (i == 0 && this.mSemGoToTopState != 0) {
            post(this.mSemGoToTopFadeOutRunnable);
        }
        if (!this.mSemGoToTopRect.isEmpty()) {
            if (canvas.isHardwareAccelerated()) {
                canvas.enableZ();
                float alpha = this.mSemGoToTopImage.getAlpha() / 255.0f;
                RecordingCanvas beginRecording = this.mGoToTopRenderNode.beginRecording();
                this.mOutline.setAlpha(alpha);
                this.mGoToTopRenderNode.setOutline(this.mOutline);
                this.mGoToTopRenderNode.setAlpha(alpha);
                beginRecording.drawBitmap(this.mSemGoToTopBitmap, 0.0f, 0.0f, (Paint) null);
                canvas.drawRenderNode(this.mGoToTopRenderNode);
                this.mGoToTopRenderNode.endRecording();
                canvas.disableZ();
            } else {
                this.mSemGoToTopImage.draw(canvas);
            }
        }
        canvas.restoreToCount(save);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (this.mContext.getApplicationInfo().targetSdkVersion <= 18 || !(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mSavedState = savedState;
        requestLayout();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        if (this.mContext.getApplicationInfo().targetSdkVersion <= 18) {
            return super.onSaveInstanceState();
        }
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = this.mScrollY;
        return savedState;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("fillViewport", this.mFillViewport);
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.ScrollView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int scrollPosition;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.scrollPosition);
        }

        public String toString() {
            return "ScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.scrollPosition + "}";
        }
    }

    private static class HoverScrollHandler extends Handler {
        private final WeakReference<ScrollView> mScrollView;

        HoverScrollHandler(ScrollView scrollView) {
            this.mScrollView = new WeakReference<>(scrollView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            ScrollView scrollView = this.mScrollView.get();
            if (scrollView != null) {
                scrollView.handleMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message message) {
        int i;
        if (message.what != 1) {
            return;
        }
        int scrollRange = getScrollRange();
        long currentTimeMillis = System.currentTimeMillis();
        this.mHoverRecognitionCurrentTime = currentTimeMillis;
        this.mHoverRecognitionDurationTime = (currentTimeMillis - this.mHoverRecognitionStartTime) / 1000;
        if (currentTimeMillis - this.mHoverScrollStartTime < this.mHoverScrollTimeInterval) {
            return;
        }
        int applyDimension = (int) (TypedValue.applyDimension(1, this.HOVERSCROLL_SPEED, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        this.mHoverScrollSpeed = applyDimension;
        long j = this.mHoverRecognitionDurationTime;
        if (j > 2 && j < 4) {
            this.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.1d));
        } else if (j >= 4 && j < 5) {
            this.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.2d));
        } else if (j >= 5) {
            this.mHoverScrollSpeed = applyDimension + ((int) (applyDimension * 0.3d));
        }
        if (this.mHoverScrollDirection == 2) {
            i = this.mHoverScrollSpeed * (-1);
        } else {
            i = this.mHoverScrollSpeed;
        }
        if (i < 0 && this.mScrollY > 0) {
            flingWithoutAcc(i);
            this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
            return;
        }
        if (i > 0 && this.mScrollY < scrollRange) {
            flingWithoutAcc(i);
            this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
            return;
        }
        int overScrollMode = getOverScrollMode();
        boolean z = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0);
        if (z && !this.mIsHoverOverscrolled) {
            int i2 = this.mHoverScrollDirection;
            if (i2 == 2) {
                this.mEdgeGlowTop.setSize((getWidth() - this.mPaddingLeft) - this.mPaddingRight, getHeight());
                this.mEdgeGlowTop.onAbsorb(10000);
                if (!this.mEdgeGlowBottom.isFinished()) {
                    this.mEdgeGlowBottom.onRelease();
                }
            } else if (i2 == 1) {
                this.mEdgeGlowBottom.setSize((getWidth() - this.mPaddingLeft) - this.mPaddingRight, getHeight());
                this.mEdgeGlowBottom.onAbsorb(10000);
                semShowGoToTop();
                if (!this.mEdgeGlowTop.isFinished()) {
                    this.mEdgeGlowTop.onRelease();
                }
            }
            if (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished()) {
                invalidate();
            }
            this.mIsHoverOverscrolled = true;
        }
        if (z || this.mIsHoverOverscrolled) {
            return;
        }
        this.mIsHoverOverscrolled = true;
    }

    private void showPointerIcon(MotionEvent motionEvent, int i) {
        if (i == 20011 || i == 20015) {
            i = semGetRotatePointerIcon(i);
        }
        semSetPointerIcon(motionEvent.getToolType(0), i == 20001 ? null : PointerIcon.getSystemIcon(this.mContext, i));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semSetupGoToTop(int i) {
        int i2;
        int i3;
        if (this.mSemEnableGoToTop) {
            removeCallbacks(this.mSemAutoHide);
            if (i == 3) {
                i = canScrollUp() ? this.mSemGoToTopLastState : 0;
            }
            if (i != 2) {
                this.mSemGoToTopImage.setState(StateSet.NOTHING);
            }
            if (!this.mIsGoToTopShown && i == 0 && this.mSemGoToTopLastState != 0) {
                post(this.mSemGoToTopFadeOutRunnable);
            }
            this.mSemGoToTopState = i;
            int width = getWidth();
            int height = getHeight();
            int i4 = this.mPaddingLeft + (((width - this.mPaddingLeft) - this.mPaddingRight) / 2);
            int[] iArr = {0, 0};
            getLocationInWindow(iArr);
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int rotation = ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
            boolean z = rotation == 1 || rotation == 3;
            Rect rect = new Rect();
            getWindowVisibleDisplayFrame(rect);
            int i5 = z ? rect.left : 0;
            int i6 = z ? rect.right : displayMetrics.widthPixels;
            int i7 = iArr[0];
            if (i7 < i5 && (i3 = -i7) > this.mPaddingLeft) {
                i4 += (i3 - this.mPaddingLeft) / 2;
            }
            int i8 = iArr[0];
            if (i8 + width > i6 && (i2 = (i8 + width) - displayMetrics.widthPixels) > this.mPaddingRight) {
                i4 -= (i2 - this.mPaddingRight) / 2;
            }
            int i9 = this.mSemGoToTopState;
            if (i9 != 0) {
                if (i9 == 1 || i9 == 2) {
                    Rect rect2 = this.mSemGoToTopRect;
                    int i10 = this.mGoToTopWH;
                    int i11 = this.mGoToTopGap;
                    rect2.set(i4 - (i10 / 2), (height - i10) - i11, i4 + (i10 / 2), height - i11);
                }
            } else if (this.mIsGoToTopShown) {
                this.mSemGoToTopRect.setEmpty();
            }
            this.mSemGoToTopImage.setBounds(this.mSemGoToTopRect);
            if (this.mIsGoToTopShown) {
                this.mIsGoToTopShown = false;
            }
            if (i == 1 && (this.mSemGoToTopLastState == 0 || this.mSizeChange)) {
                post(this.mSemGoToTopFadeInRunnable);
            }
            this.mSizeChange = false;
            this.mSemGoToTopLastState = this.mSemGoToTopState;
            this.mOutline.setOval(0, 0, this.mSemGoToTopRect.width(), this.mSemGoToTopRect.height());
            this.mGoToTopRenderNode.setPosition(this.mSemGoToTopRect);
            this.mGoToTopRenderNode.setClipToBounds(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semPlayGoToTopFadeOut() {
        if (this.mSemGoToTopFadeOutAnimator.isRunning()) {
            return;
        }
        this.mSemGoToTopFadeOutAnimator.setIntValues(this.mSemGoToTopImage.getAlpha(), 0);
        this.mSemGoToTopFadeOutAnimator.start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void semPlayGoToTopFadeIn() {
        if (this.mSemGoToTopFadeInAnimator.isRunning()) {
            return;
        }
        this.mSemGoToTopFadeInAnimator.setIntValues(this.mSemGoToTopImage.getAlpha(), 255);
        this.mSemGoToTopFadeInAnimator.start();
    }

    public void semSetGoToTopEnabled(boolean z) {
        semSetGoToTopEnabled(z, 1);
    }

    public void semSetGoToTopEnabled(boolean z, int i) {
        Drawable drawable;
        if (i == 0) {
            drawable = this.mSemGoToTopLightImage;
        } else {
            drawable = this.mContext.getResources().getDrawable(R.drawable.sem_list_go_to_top_dark);
        }
        this.mSemGoToTopImage = drawable;
        if (drawable != null) {
            this.mSemEnableGoToTop = z;
            if (drawable.getAlpha() != 255) {
                this.mSemGoToTopImage.setAlpha(255);
            }
            this.mSemGoToTopBitmap = drawableToBitmap(this.mSemGoToTopImage);
            this.mSemGoToTopImage.setAlpha(0);
            if (z) {
                this.mSemGoToTopImage.setCallback(this);
            } else {
                this.mSemGoToTopImage.setCallback(null);
            }
            ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
            this.mSemGoToTopFadeInAnimator = ofInt;
            ofInt.setDuration(333L);
            this.mSemGoToTopFadeInAnimator.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
            this.mSemGoToTopFadeInAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.ScrollView.6
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ScrollView.this.mSemGoToTopImage.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    ScrollView.this.invalidate();
                }
            });
            ValueAnimator ofInt2 = ValueAnimator.ofInt(255, 0);
            this.mSemGoToTopFadeOutAnimator = ofInt2;
            ofInt2.setDuration(333L);
            this.mSemGoToTopFadeOutAnimator.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
            this.mSemGoToTopFadeOutAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.ScrollView.7
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    ScrollView.this.mSemGoToTopImage.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                    ScrollView.this.invalidate();
                }
            });
            this.mSemGoToTopFadeOutAnimator.addListener(new Animator.AnimatorListener() { // from class: android.widget.ScrollView.8
                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationCancel(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationRepeat(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                }

                @Override // android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    ScrollView.this.mIsGoToTopShown = true;
                    ScrollView.this.semSetupGoToTop(0);
                }
            });
            RenderNode renderNode = new RenderNode("goToTop");
            this.mGoToTopRenderNode = renderNode;
            renderNode.setElevation(this.mGoToTopElevation);
        }
    }

    void semAutoHide() {
        if (this.mSemEnableGoToTop) {
            removeCallbacks(this.mSemAutoHide);
            postDelayed(this.mSemAutoHide, this.GO_TO_TOP_HIDE);
        }
    }

    private void semShowGoToTop() {
        if (this.mSemEnableGoToTop && this.mSemGoToTopState != 2 && canScrollUp()) {
            semSetupGoToTop(1);
            semAutoHide();
        }
    }

    private int semGetRotatePointerIcon(int i) {
        float rotation = getRotation() + semGetParentViewRotation();
        boolean z = i == 20011;
        boolean z2 = rotation < 0.0f;
        int i2 = (((int) ((rotation + (z2 ? -45 : 45)) / 90.0f)) + (z ? 0 : z2 ? -2 : 2)) % 4;
        if (z2 && i2 != 0) {
            i2 += 4;
        }
        int abs = Math.abs(i2);
        if (abs == 0) {
            return 20011;
        }
        if (abs == 1) {
            return 20013;
        }
        if (abs != 2) {
            return abs != 3 ? 20001 : 20017;
        }
        return 20015;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.widget.ScrollView] */
    /* JADX WARN: Type inference failed for: r2v1, types: [android.view.View] */
    /* JADX WARN: Type inference failed for: r2v3, types: [android.view.View] */
    private float semGetParentViewRotation() {
        while (this.getParent() instanceof View) {
            this = (View) this.getParent();
            if (this.getRotation() != 0.0f) {
                return this.getRotation();
            }
        }
        return 0.0f;
    }

    @Deprecated
    public void semSetSmoothScrollEnabled(boolean z) {
        this.mScroller.semSetSmoothScrollEnabled(z);
    }

    private class DifferentialFlingTarget implements DifferentialMotionFlingHelper.DifferentialMotionFlingTarget {
        private DifferentialFlingTarget() {
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public boolean startDifferentialMotionFling(float f) {
            stopDifferentialMotionFling();
            ScrollView.this.fling((int) f);
            return true;
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public void stopDifferentialMotionFling() {
            ScrollView.this.mScroller.abortAnimation();
        }

        @Override // android.widget.DifferentialMotionFlingHelper.DifferentialMotionFlingTarget
        public float getScaledScrollFactor() {
            return -ScrollView.this.mVerticalScrollFactor;
        }
    }
}
