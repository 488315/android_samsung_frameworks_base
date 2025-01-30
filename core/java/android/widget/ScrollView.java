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
import android.view.Display;
import android.view.FocusFinder;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.C4337R;
import java.lang.ref.WeakReference;
import java.util.List;

/* loaded from: classes4.dex */
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

  public final class InspectionCompanion
      implements android.view.inspector.InspectionCompanion<ScrollView> {
    private int mFillViewportId;
    private boolean mPropertiesMapped = false;

    @Override // android.view.inspector.InspectionCompanion
    public void mapProperties(PropertyMapper propertyMapper) {
      this.mFillViewportId = propertyMapper.mapBoolean("fillViewport", 16843130);
      this.mPropertiesMapped = true;
    }

    @Override // android.view.inspector.InspectionCompanion
    public void readProperties(ScrollView node, PropertyReader propertyReader) {
      if (!this.mPropertiesMapped) {
        throw new InspectionCompanion.UninitializedPropertyMapException();
      }
      propertyReader.readBoolean(this.mFillViewportId, node.isFillViewport());
    }
  }

  public void setIgnoreDelaychildPrerssedState(boolean enable) {
    this.mIgnoreDelaychildPrerssed = enable;
  }

  public void updateCustomEdgeGlow(Drawable edgeeffectCustomEdge, Drawable edgeeffectCustomGlow) {}

  public int getTouchSlop() {
    return this.mTouchSlop;
  }

  public void setTouchSlop(int value) {
    this.mTouchSlop = value;
  }

  public ScrollView(Context context) {
    this(context, null);
  }

  public ScrollView(Context context, AttributeSet attrs) {
    this(context, attrs, 16842880);
  }

  public ScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
    this(context, attrs, defStyleAttr, 0);
  }

  public ScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
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
    this.mGoToTopEdgeEffectRunnable =
        new Runnable() { // from class: android.widget.ScrollView.2
          @Override // java.lang.Runnable
          public void run() {
            ScrollView.this.mEdgeGlowTop.onAbsorb(10000);
            ScrollView.this.invalidate();
          }
        };
    this.mHoverScrollSpeed = 0;
    this.GO_TO_TOP_HIDE = 2500;
    this.mSemGoToTopFadeOutRunnable =
        new Runnable() { // from class: android.widget.ScrollView.3
          @Override // java.lang.Runnable
          public void run() {
            ScrollView.this.semPlayGoToTopFadeOut();
          }
        };
    this.mSemGoToTopFadeInRunnable =
        new Runnable() { // from class: android.widget.ScrollView.4
          @Override // java.lang.Runnable
          public void run() {
            ScrollView.this.semPlayGoToTopFadeIn();
          }
        };
    this.mSemAutoHide =
        new Runnable() { // from class: android.widget.ScrollView.5
          @Override // java.lang.Runnable
          public void run() {
            ScrollView.this.semSetupGoToTop(0);
          }
        };
    this.mEdgeGlowTop = new EdgeEffect(context, attrs);
    this.mEdgeGlowBottom = new EdgeEffect(context, attrs);
    this.mEdgeGlowTop.semSetHostView(this, true);
    this.mEdgeGlowBottom.semSetHostView(this, true);
    initScrollView();
    TypedArray a =
        context.obtainStyledAttributes(
            attrs, C4337R.styleable.ScrollView, defStyleAttr, defStyleRes);
    saveAttributeDataForStyleable(
        context, C4337R.styleable.ScrollView, attrs, a, defStyleAttr, defStyleRes);
    setFillViewport(a.getBoolean(0, false));
    a.recycle();
    if (context.getResources().getConfiguration().uiMode == 6) {
      setRevealOnFocusHint(false);
    }
  }

  @Override // android.widget.FrameLayout, android.view.ViewGroup
  public boolean shouldDelayChildPressedState() {
    if (this.mIgnoreDelaychildPrerssed) {
      return false;
    }
    return true;
  }

  @Override // android.view.View
  protected float getTopFadingEdgeStrength() {
    if (getChildCount() == 0) {
      return 0.0f;
    }
    int length = getVerticalFadingEdgeLength();
    if (this.mScrollY < length) {
      return this.mScrollY / length;
    }
    return 1.0f;
  }

  @Override // android.view.View
  protected float getBottomFadingEdgeStrength() {
    if (getChildCount() == 0) {
      return 0.0f;
    }
    int length = getVerticalFadingEdgeLength();
    int bottomEdge = getHeight() - this.mPaddingBottom;
    int span = (getChildAt(0).getBottom() - this.mScrollY) - bottomEdge;
    if (span < length) {
      return span / length;
    }
    return 1.0f;
  }

  public void setEdgeEffectColor(int color) {
    setTopEdgeEffectColor(color);
    setBottomEdgeEffectColor(color);
  }

  public void setBottomEdgeEffectColor(int color) {
    this.mEdgeGlowBottom.setColor(color);
  }

  public void setTopEdgeEffectColor(int color) {
    this.mEdgeGlowTop.setColor(color);
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
    ViewConfiguration configuration = ViewConfiguration.get(this.mContext);
    this.mTouchSlop = configuration.getScaledTouchSlop();
    this.mMinimumVelocity = configuration.getScaledMinimumFlingVelocity();
    this.mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
    this.mOverscrollDistance = configuration.getScaledOverscrollDistance();
    this.mOverflingDistance = configuration.getScaledOverflingDistance();
    this.mVerticalScrollFactor = configuration.getScaledVerticalScrollFactor();
    initGoToTop();
  }

  private void initGoToTop() {
    this.mSemGoToTopRect.setEmpty();
    Resources res = getResources();
    TypedValue value = new TypedValue();
    boolean resolved =
        this.mContext.getTheme().resolveAttribute(C4337R.attr.semGoToTopStyle, value, true);
    if (resolved) {
      this.mSemGoToTopLightImage = this.mContext.getResources().getDrawable(value.resourceId);
    }
    this.mGoToTopWH = res.getDimensionPixelSize(C4337R.dimen.sem_go_to_top_scrollableview_size);
    this.mGoToTopGap = res.getDimensionPixelSize(C4337R.dimen.sem_go_to_top_scrollableview_gap);
    this.mGoToTopElevation = res.getDimensionPixelSize(C4337R.dimen.sem_go_to_top_elevation);
    Log.m94d(TAG, "initGoToTop");
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
  public void addView(View child) {
    if (getChildCount() > 0) {
      throw new IllegalStateException("ScrollView can host only one direct child");
    }
    super.addView(child);
  }

  @Override // android.view.ViewGroup
  public void addView(View child, int index) {
    if (getChildCount() > 0) {
      throw new IllegalStateException("ScrollView can host only one direct child");
    }
    super.addView(child, index);
  }

  @Override // android.view.ViewGroup, android.view.ViewManager
  public void addView(View child, ViewGroup.LayoutParams params) {
    if (getChildCount() > 0) {
      throw new IllegalStateException("ScrollView can host only one direct child");
    }
    super.addView(child, params);
  }

  @Override // android.view.ViewGroup
  public void addView(View child, int index, ViewGroup.LayoutParams params) {
    if (getChildCount() > 0) {
      throw new IllegalStateException("ScrollView can host only one direct child");
    }
    super.addView(child, index, params);
  }

  private boolean canScroll() {
    View child = getChildAt(0);
    if (child == null) {
      return false;
    }
    int childHeight = child.getHeight();
    return getHeight() < (this.mPaddingTop + childHeight) + this.mPaddingBottom;
  }

  private boolean canScrollUp() {
    View child = getChildAt(0);
    if (child == null) {
      return false;
    }
    int childHeight = child.getHeight();
    return getHeight() <= (this.mPaddingTop + childHeight) + this.mPaddingBottom;
  }

  private boolean canScrollDown() {
    View child = getChildAt(0);
    return child != null
        && this.mScrollY
            > ((child.getHeight() + this.mPaddingTop) + this.mPaddingBottom) - getHeight();
  }

  public boolean isFillViewport() {
    return this.mFillViewport;
  }

  public void setFillViewport(boolean fillViewport) {
    if (fillViewport != this.mFillViewport) {
      this.mFillViewport = fillViewport;
      requestLayout();
    }
  }

  public boolean isSmoothScrollingEnabled() {
    return this.mSmoothScrollingEnabled;
  }

  public void setSmoothScrollingEnabled(boolean smoothScrollingEnabled) {
    this.mSmoothScrollingEnabled = smoothScrollingEnabled;
  }

  @Override // android.widget.FrameLayout, android.view.View
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    int widthPadding;
    int heightPadding;
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    if (!this.mFillViewport) {
      return;
    }
    int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
    if (heightMode != 0 && getChildCount() > 0) {
      View child = getChildAt(0);
      int targetSdkVersion = getContext().getApplicationInfo().targetSdkVersion;
      FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) child.getLayoutParams();
      if (targetSdkVersion >= 23) {
        widthPadding = this.mPaddingLeft + this.mPaddingRight + lp.leftMargin + lp.rightMargin;
        heightPadding = this.mPaddingTop + this.mPaddingBottom + lp.topMargin + lp.bottomMargin;
      } else {
        int widthPadding2 = this.mPaddingLeft;
        widthPadding = widthPadding2 + this.mPaddingRight;
        heightPadding = this.mPaddingTop + this.mPaddingBottom;
      }
      int desiredHeight = getMeasuredHeight() - heightPadding;
      if (child.getMeasuredHeight() < desiredHeight) {
        int childWidthMeasureSpec = getChildMeasureSpec(widthMeasureSpec, widthPadding, lp.width);
        int childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(desiredHeight, 1073741824);
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
      }
    }
  }

  @Override // android.view.ViewGroup, android.view.View
  public boolean dispatchKeyEvent(KeyEvent event) {
    return super.dispatchKeyEvent(event) || executeKeyEvent(event);
  }

  @Override // android.view.ViewGroup, android.view.View
  public boolean dispatchTouchEvent(MotionEvent event) {
    int i;
    int i2;
    int i3;
    int x = (int) event.getX();
    int y = (int) event.getY();
    int childCount = getChildCount();
    int contentBottom = 0;
    int range = getScrollRange();
    boolean needToScroll = MultiSelection.isNeedToScroll();
    if (this.mHoverHandler == null) {
      this.mHoverHandler = new HoverScrollHandler(this);
    }
    if (this.mHoverTopAreaHeight <= 0 || this.mHoverBottomAreaHeight <= 0) {
      this.mHoverTopAreaHeight =
          (int)
              (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics())
                  + 0.5f);
      this.mHoverBottomAreaHeight =
          (int)
              (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics())
                  + 0.5f);
    }
    if (childCount != 0) {
      contentBottom = getHeight();
    }
    boolean isPossibleTooltype = event.getToolType(0) == 2;
    int action = event.getAction();
    switch (action) {
      case 0:
        this.mSemGoToTopPressed = false;
        if (semIsSupportGotoTop()
            && this.mSemGoToTopState != 2
            && this.mSemGoToTopRect.contains(x, y)) {
          semSetupGoToTop(2);
          this.mSemGoToTopPressed = true;
          this.mSemGoToTopImage.setHotspot(x, y);
          this.mSemGoToTopImage.setState(new int[] {16842919, 16842910, 16842913});
          return true;
        }
        break;
      case 1:
        if (semIsSupportGotoTop() && this.mSemGoToTopState == 2) {
          if (canScrollUp()) {
            post(
                new Runnable() { // from class: android.widget.ScrollView.1
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
        break;
      case 2:
        if (semIsSupportGotoTop() && this.mSemGoToTopState == 2) {
          if (!this.mSemGoToTopRect.contains(x, y)) {
            this.mSemGoToTopState = 1;
            this.mSemGoToTopImage.setState(StateSet.NOTHING);
            semAutoHide();
          }
          return true;
        }
        break;
      case 3:
        if (semIsSupportGotoTop() && this.mSemGoToTopState != 0) {
          this.mSemGoToTopImage.setState(StateSet.NOTHING);
        }
        this.mSemGoToTopPressed = false;
        break;
    }
    if ((y > this.mHoverTopAreaHeight && y < contentBottom - this.mHoverBottomAreaHeight)
        || x <= 0
        || x > getRight()
        || range == 0
        || !isPossibleTooltype
        || event.getButtonState() != 32) {
      if (this.mHoverHandler.hasMessages(1)) {
        this.mHoverHandler.removeMessages(1);
      }
      this.mHoverRecognitionStartTime = 0L;
      this.mHoverScrollStartTime = 0L;
      this.mHoverAreaEnter = false;
      this.mIsHoverOverscrolled = false;
      return super.dispatchTouchEvent(event);
    }
    if (!this.mHoverAreaEnter) {
      this.mHoverScrollStartTime = System.currentTimeMillis();
    }
    switch (action) {
      case 211:
        if (semIsSupportGotoTop()
            && this.mSemGoToTopState != 2
            && this.mSemGoToTopRect.contains(x, y)) {
          semSetupGoToTop(2);
          this.mSemGoToTopImage.setHotspot(x, y);
          this.mSemGoToTopImage.setState(new int[] {16842919, 16842910, 16842913});
          return true;
        }
        break;
      case 212:
        if (!semIsSupportGotoTop() || this.mSemGoToTopState != 2) {
          if (this.mHoverHandler.hasMessages(1)) {
            this.mHoverHandler.removeMessages(1);
          }
          this.mHoverRecognitionStartTime = 0L;
          this.mHoverScrollStartTime = 0L;
          this.mIsHoverOverscrolled = false;
          this.mHoverAreaEnter = false;
          return super.dispatchTouchEvent(event);
        }
        Log.m94d(TAG, "pen up false GOTOTOP");
        if (!canScrollUp()) {
          i = 0;
        } else {
          i = 0;
          smoothScrollTo(0, 0);
          this.mEdgeGlowTop.onAbsorb(10000);
          invalidate();
        }
        semSetupGoToTop(i);
        this.mSemGoToTopImage.setState(StateSet.NOTHING);
        return true;
      case 213:
        if (semIsSupportGotoTop()
            && this.mSemGoToTopState == 2
            && !this.mSemGoToTopRect.contains(x, y)) {
          this.mSemGoToTopState = 1;
          this.mSemGoToTopImage.setState(StateSet.NOTHING);
          return true;
        }
        if (needToScroll) {
          if (y >= 0 && y <= this.mHoverTopAreaHeight) {
            if (this.mHoverAreaEnter) {
              i3 = 1;
            } else {
              i3 = 1;
              this.mHoverAreaEnter = true;
              this.mHoverScrollStartTime = System.currentTimeMillis();
            }
            if (!this.mHoverHandler.hasMessages(i3)) {
              this.mHoverRecognitionStartTime = System.currentTimeMillis();
              this.mHoverScrollDirection = 2;
              this.mHoverHandler.sendEmptyMessage(i3);
            }
          } else if (y >= contentBottom - this.mHoverBottomAreaHeight && y <= contentBottom) {
            if (this.mHoverAreaEnter) {
              i2 = 1;
            } else {
              i2 = 1;
              this.mHoverAreaEnter = true;
              this.mHoverScrollStartTime = System.currentTimeMillis();
            }
            if (!this.mHoverHandler.hasMessages(i2)) {
              this.mHoverRecognitionStartTime = System.currentTimeMillis();
              this.mHoverScrollDirection = i2;
              this.mHoverHandler.sendEmptyMessage(i2);
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
        this.mPreviousTextViewScroll = needToScroll;
        break;
        break;
    }
    return super.dispatchTouchEvent(event);
  }

  private boolean semIsTalkBackIsRunning() {
    AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
    if (accessibilityManager == null || !accessibilityManager.isEnabled()) {
      return false;
    }
    boolean isRunning =
        accessibilityManager.semIsAccessibilityServiceEnabled(32)
            || accessibilityManager.semIsAccessibilityServiceEnabled(16)
            || accessibilityManager.semIsAccessibilityServiceEnabled(64);
    return isRunning;
  }

  private boolean semIsSupportGotoTop() {
    return this.mSemEnableGoToTop && !semIsTalkBackIsRunning();
  }

  public boolean executeKeyEvent(KeyEvent event) {
    boolean handled;
    boolean handled2;
    this.mTempRect.setEmpty();
    if (!canScroll()) {
      if (!isFocused() || event.getKeyCode() == 4 || event.getKeyCode() == 111) {
        return false;
      }
      View currentFocused = findFocus();
      if (currentFocused == this) {
        currentFocused = null;
      }
      View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, 130);
      return (nextFocused == null || nextFocused == this || !nextFocused.requestFocus(130))
          ? false
          : true;
    }
    if (event.getAction() != 0) {
      return false;
    }
    switch (event.getKeyCode()) {
      case 19:
        if (!event.isAltPressed()) {
          handled = arrowScroll(33);
        } else {
          handled = fullScroll(33);
        }
        if (handled) {
          playSoundEffect(SoundEffectConstants.getContantForFocusDirection(33));
          return handled;
        }
        return handled;
      case 20:
        if (!event.isAltPressed()) {
          handled2 = arrowScroll(130);
        } else {
          handled2 = fullScroll(130);
        }
        if (handled2) {
          playSoundEffect(SoundEffectConstants.getContantForFocusDirection(130));
          return handled2;
        }
        return handled2;
      case 62:
        pageScroll(event.isShiftPressed() ? 33 : 130);
        return false;
      case 92:
        boolean handled3 = pageScroll(33);
        return handled3;
      case 93:
        boolean handled4 = pageScroll(130);
        return handled4;
      case 122:
        boolean handled5 = fullScroll(33);
        return handled5;
      case 123:
        boolean handled6 = fullScroll(130);
        return handled6;
      default:
        return false;
    }
  }

  private boolean inChild(int x, int y) {
    if (getChildCount() <= 0) {
      return false;
    }
    int scrollY = this.mScrollY;
    View child = getChildAt(0);
    return y >= child.getTop() - scrollY
        && y < child.getBottom() - scrollY
        && x >= child.getLeft()
        && x < child.getRight();
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

  private void recycleVelocityTracker() {
    VelocityTracker velocityTracker = this.mVelocityTracker;
    if (velocityTracker != null) {
      velocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    if (disallowIntercept) {
      recycleVelocityTracker();
    }
    super.requestDisallowInterceptTouchEvent(disallowIntercept);
  }

  @Override // android.view.ViewGroup
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    int action = ev.getAction();
    boolean z = true;
    if ((action == 2 && this.mIsBeingDragged) || super.onInterceptTouchEvent(ev)) {
      return true;
    }
    if (getScrollY() == 0 && !canScrollVertically(1)) {
      return false;
    }
    switch (action & 255) {
      case 0:
        int y = (int) ev.getY();
        if (!inChild((int) ev.getX(), y)) {
          this.mIsBeingDragged = false;
          recycleVelocityTracker();
          break;
        } else {
          this.mLastMotionY = y;
          this.mActivePointerId = ev.getPointerId(0);
          initOrResetVelocityTracker();
          this.mVelocityTracker.addMovement(ev);
          this.mScroller.computeScrollOffset();
          if (this.mScroller.isFinished()
              && this.mEdgeGlowBottom.isFinished()
              && this.mEdgeGlowTop.isFinished()) {
            z = false;
          }
          this.mIsBeingDragged = z;
          if (!this.mEdgeGlowTop.isFinished()) {
            this.mEdgeGlowTop.onPullDistance(0.0f, ev.getX() / getWidth());
          }
          if (!this.mEdgeGlowBottom.isFinished()) {
            this.mEdgeGlowBottom.onPullDistance(0.0f, 1.0f - (ev.getX() / getWidth()));
          }
          if (this.mIsBeingDragged && this.mScrollStrictSpan == null) {
            this.mScrollStrictSpan = StrictMode.enterCriticalSpan("ScrollView-scroll");
          }
          startNestedScroll(2);
          break;
        }
      case 1:
      case 3:
        this.mIsBeingDragged = false;
        this.mActivePointerId = -1;
        recycleVelocityTracker();
        if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
          postInvalidateOnAnimation();
        }
        stopNestedScroll();
        break;
      case 2:
        int activePointerId = this.mActivePointerId;
        if (activePointerId != -1) {
          int pointerIndex = ev.findPointerIndex(activePointerId);
          if (pointerIndex == -1) {
            Log.m96e(TAG, "Invalid pointerId=" + activePointerId + " in onInterceptTouchEvent");
            break;
          } else {
            int y2 = (int) ev.getY(pointerIndex);
            int yDiff = Math.abs(y2 - this.mLastMotionY);
            if (yDiff > this.mTouchSlop && (2 & getNestedScrollAxes()) == 0) {
              this.mIsBeingDragged = true;
              this.mLastMotionY = y2;
              initVelocityTrackerIfNotExists();
              this.mVelocityTracker.addMovement(ev);
              this.mNestedYOffset = 0;
              if (this.mScrollStrictSpan == null) {
                this.mScrollStrictSpan = StrictMode.enterCriticalSpan("ScrollView-scroll");
              }
              ViewParent parent = getParent();
              if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
                break;
              }
            }
          }
        }
        break;
      case 6:
        onSecondaryPointerUp(ev);
        break;
    }
    return this.mIsBeingDragged;
  }

  private boolean shouldDisplayEdgeEffects() {
    return getOverScrollMode() != 2;
  }

  public boolean isLockScreenMode() {
    Context context = this.mContext;
    Context context2 = this.mContext;
    KeyguardManager keyguardManager =
        (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
    return keyguardManager.inKeyguardRestrictedInputMode();
  }

  @Deprecated
  public void semSetHoverScrollMode(boolean flag) {
    this.mHoverScrollEnable = flag;
    this.mHoverScrollStateChanged = true;
  }

  public void setHoverScrollSpeed(int hoverspeed) {
    this.HOVERSCROLL_SPEED = hoverspeed + 23;
  }

  public void setHoverScrollDelay(int hoverdelay) {
    this.HOVERSCROLL_DELAY = hoverdelay;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  @Override // android.view.ViewGroup, android.view.View
  protected boolean dispatchHoverEvent(MotionEvent ev) {
    if (!isHoveringUIEnabled()) {
      return super.dispatchHoverEvent(ev);
    }
    int action = ev.getAction();
    if (action == 9 || this.mHoverScrollStateChanged) {
      int toolType = ev.getToolType(0);
      this.mNeedsHoverScroll = true;
      this.mHoverScrollStateChanged = false;
      if (!this.mHoverScrollEnable) {
        this.mNeedsHoverScroll = false;
      }
      if (this.mNeedsHoverScroll && toolType == 2) {
        boolean isHoveringOn =
            Settings.System.getInt(
                    this.mContext.getContentResolver(), Settings.System.SEM_PEN_HOVERING, 0)
                == 1;
        if (!isHoveringOn) {
          this.mNeedsHoverScroll = false;
        }
      }
      boolean isHoveringOn2 = this.mNeedsHoverScroll;
      if (isHoveringOn2 && toolType == 3) {
        this.mNeedsHoverScroll = false;
      }
    }
    if (!this.mNeedsHoverScroll) {
      return super.dispatchHoverEvent(ev);
    }
    int x = (int) ev.getX();
    int y = (int) ev.getY();
    int childCount = getChildCount();
    int contentBottom = 0;
    int range = getScrollRange();
    if (this.mHoverHandler == null) {
      this.mHoverHandler = new HoverScrollHandler(this);
    }
    if (this.mHoverTopAreaHeight <= 0 || this.mHoverBottomAreaHeight <= 0) {
      this.mHoverTopAreaHeight =
          (int)
              (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics())
                  + 0.5f);
      this.mHoverBottomAreaHeight =
          (int)
              (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics())
                  + 0.5f);
    }
    if (childCount != 0) {
      contentBottom = getHeight();
    }
    boolean isPossibleTooltype = ev.getToolType(0) == 2;
    if ((y > this.mHoverTopAreaHeight && y < contentBottom - this.mHoverBottomAreaHeight)
        || x <= 0
        || x > getRight()
        || range == 0
        || ((y >= 0
                && y <= this.mHoverTopAreaHeight
                && this.mScrollY <= 0
                && this.mIsHoverOverscrolled)
            || ((y >= contentBottom - this.mHoverBottomAreaHeight
                    && y <= contentBottom
                    && this.mScrollY >= range
                    && this.mIsHoverOverscrolled)
                || ((isPossibleTooltype && ev.getButtonState() == 32)
                    || !isPossibleTooltype
                    || isLockScreenMode()
                    || (this.mSemEnableGoToTop
                        && this.mSemGoToTopState != 0
                        && this.mSemGoToTopRect.contains(x, y)))))) {
      if (this.mHoverHandler.hasMessages(1)) {
        this.mHoverHandler.removeMessages(1);
        showPointerIcon(ev, 20001);
      }
      if ((y > this.mHoverTopAreaHeight && y < contentBottom - this.mHoverBottomAreaHeight)
          || x <= 0
          || x > getRight()) {
        this.mIsHoverOverscrolled = false;
      }
      if (this.mHoverAreaEnter || this.mHoverScrollStartTime != 0) {
        showPointerIcon(ev, 20001);
      }
      this.mHoverRecognitionStartTime = 0L;
      this.mHoverScrollStartTime = 0L;
      this.mHoverAreaEnter = false;
      return super.dispatchHoverEvent(ev);
    }
    if (!this.mHoverAreaEnter) {
      this.mHoverScrollStartTime = System.currentTimeMillis();
    }
    switch (action) {
      case 7:
        if (!this.mHoverAreaEnter) {
          this.mHoverAreaEnter = true;
          ev.setAction(10);
          return super.dispatchHoverEvent(ev);
        }
        if (y >= 0 && y <= this.mHoverTopAreaHeight) {
          if (!this.mHoverHandler.hasMessages(1)) {
            this.mHoverRecognitionStartTime = System.currentTimeMillis();
            if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 1) {
              showPointerIcon(ev, 20011);
            }
            this.mHoverScrollDirection = 2;
            this.mHoverHandler.sendEmptyMessage(1);
          }
        } else if (y >= contentBottom - this.mHoverBottomAreaHeight
            && y <= contentBottom
            && !this.mHoverHandler.hasMessages(1)) {
          this.mHoverRecognitionStartTime = System.currentTimeMillis();
          if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 2) {
            showPointerIcon(ev, 20015);
          }
          this.mHoverScrollDirection = 1;
          this.mHoverHandler.sendEmptyMessage(1);
        }
        return true;
      case 8:
      default:
        return true;
      case 9:
        this.mHoverAreaEnter = true;
        if (y >= 0 && y <= this.mHoverTopAreaHeight) {
          if (!this.mHoverHandler.hasMessages(1)) {
            this.mHoverRecognitionStartTime = System.currentTimeMillis();
            showPointerIcon(ev, 20011);
            this.mHoverScrollDirection = 2;
            this.mHoverHandler.sendEmptyMessage(1);
          }
        } else if (y >= contentBottom - this.mHoverBottomAreaHeight
            && y <= contentBottom
            && !this.mHoverHandler.hasMessages(1)) {
          this.mHoverRecognitionStartTime = System.currentTimeMillis();
          showPointerIcon(ev, 20015);
          this.mHoverScrollDirection = 1;
          this.mHoverHandler.sendEmptyMessage(1);
        }
        return true;
      case 10:
        if (this.mHoverHandler.hasMessages(1)) {
          this.mHoverHandler.removeMessages(1);
        }
        showPointerIcon(ev, 20001);
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollStartTime = 0L;
        this.mIsHoverOverscrolled = false;
        this.mHoverAreaEnter = false;
        return super.dispatchHoverEvent(ev);
    }
  }

  @Override // android.view.View
  public boolean onTouchEvent(MotionEvent ev) {
    ViewParent parent;
    int deltaY;
    initVelocityTrackerIfNotExists();
    MotionEvent vtev = MotionEvent.obtain(ev);
    int actionMasked = ev.getActionMasked();
    boolean z = false;
    if (actionMasked == 0) {
      this.mNestedYOffset = 0;
    }
    vtev.offsetLocation(0.0f, this.mNestedYOffset);
    switch (actionMasked) {
      case 0:
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
        this.mLastMotionY = (int) ev.getY();
        this.mActivePointerId = ev.getPointerId(0);
        startNestedScroll(2);
        break;
      case 1:
        if (this.mIsBeingDragged) {
          VelocityTracker velocityTracker = this.mVelocityTracker;
          velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
          int initialVelocity = (int) velocityTracker.getYVelocity(this.mActivePointerId);
          if (Math.abs(initialVelocity) > this.mMinimumVelocity) {
            flingWithNestedDispatch(-initialVelocity);
          } else if (this.mScroller.springBack(
              this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
            postInvalidateOnAnimation();
          }
          this.mActivePointerId = -1;
          endDrag();
          velocityTracker.clear();
          break;
        }
        break;
      case 2:
        int activePointerIndex = ev.findPointerIndex(this.mActivePointerId);
        if (activePointerIndex == -1) {
          this.mActivePointerId = ev.getPointerId(0);
          Log.m96e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
          break;
        } else {
          int y = (int) ev.getY(activePointerIndex);
          int deltaY2 = this.mLastMotionY - y;
          if (dispatchNestedPreScroll(0, deltaY2, this.mScrollConsumed, this.mScrollOffset)) {
            deltaY2 -= this.mScrollConsumed[1];
            vtev.offsetLocation(0.0f, this.mScrollOffset[1]);
            this.mNestedYOffset += this.mScrollOffset[1];
          }
          if (!this.mIsBeingDragged && Math.abs(deltaY2) > this.mTouchSlop) {
            ViewParent parent2 = getParent();
            if (parent2 != null) {
              parent2.requestDisallowInterceptTouchEvent(true);
            }
            this.mIsBeingDragged = true;
            if (deltaY2 > 0) {
              deltaY2 -= this.mTouchSlop;
            } else {
              deltaY2 += this.mTouchSlop;
            }
          }
          if (!this.mIsBeingDragged) {
            break;
          } else {
            this.mLastMotionY = y - this.mScrollOffset[1];
            int oldY = this.mScrollY;
            int range = getScrollRange();
            int overscrollMode = getOverScrollMode();
            if (overscrollMode == 0 || (overscrollMode == 1 && range > 0)) {
              z = true;
            }
            boolean canOverscroll = z;
            float displacement = ev.getX(activePointerIndex) / getWidth();
            if (!canOverscroll) {
              deltaY = deltaY2;
            } else {
              int consumed = 0;
              if (deltaY2 < 0 && this.mEdgeGlowBottom.getDistance() != 0.0f) {
                consumed =
                    Math.round(
                        getHeight()
                            * this.mEdgeGlowBottom.onPullDistance(
                                deltaY2 / getHeight(), 1.0f - displacement));
              } else if (deltaY2 > 0 && this.mEdgeGlowTop.getDistance() != 0.0f) {
                consumed =
                    Math.round(
                        (-getHeight())
                            * this.mEdgeGlowTop.onPullDistance(
                                (-deltaY2) / getHeight(), displacement));
              }
              deltaY = deltaY2 - consumed;
            }
            overScrollBy(0, deltaY, 0, this.mScrollY, 0, range, 0, this.mOverscrollDistance, true);
            int scrolledDeltaY = this.mScrollY - oldY;
            int unconsumedY = deltaY - scrolledDeltaY;
            if (dispatchNestedScroll(0, scrolledDeltaY, 0, unconsumedY, this.mScrollOffset)) {
              int i = this.mLastMotionY;
              int i2 = this.mScrollOffset[1];
              this.mLastMotionY = i - i2;
              vtev.offsetLocation(0.0f, i2);
              this.mNestedYOffset += this.mScrollOffset[1];
              break;
            } else if (!this.mSemGoToTopPressed && canOverscroll && deltaY != 0.0f) {
              int pulledToY = oldY + deltaY;
              if (pulledToY < 0) {
                this.mEdgeGlowTop.onPullDistance((-deltaY) / getHeight(), displacement);
                if (!this.mEdgeGlowBottom.isFinished()) {
                  this.mEdgeGlowBottom.onRelease();
                }
              } else if (pulledToY > range) {
                this.mEdgeGlowBottom.onPullDistance(deltaY / getHeight(), 1.0f - displacement);
                semShowGoToTop();
                if (!this.mEdgeGlowTop.isFinished()) {
                  this.mEdgeGlowTop.onRelease();
                }
              }
              if (shouldDisplayEdgeEffects()
                  && (!this.mEdgeGlowTop.isFinished() || !this.mEdgeGlowBottom.isFinished())) {
                postInvalidateOnAnimation();
                break;
              }
            } else {
              break;
            }
          }
        }
        break;
      case 3:
        if (this.mIsBeingDragged && getChildCount() > 0) {
          if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange())) {
            postInvalidateOnAnimation();
          }
          this.mActivePointerId = -1;
          endDrag();
          break;
        }
        break;
      case 5:
        int index = ev.getActionIndex();
        this.mLastMotionY = (int) ev.getY(index);
        this.mActivePointerId = ev.getPointerId(index);
        break;
      case 6:
        onSecondaryPointerUp(ev);
        int i3 = this.mActivePointerId;
        if (i3 != -1 && ev.findPointerIndex(i3) >= 0) {
          this.mLastMotionY = (int) ev.getY(ev.findPointerIndex(this.mActivePointerId));
          break;
        } else {
          Log.m96e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
          break;
        }
    }
    VelocityTracker velocityTracker2 = this.mVelocityTracker;
    if (velocityTracker2 != null) {
      velocityTracker2.addMovement(vtev);
    }
    vtev.recycle();
    return true;
  }

  private void onSecondaryPointerUp(MotionEvent ev) {
    int pointerIndex = (ev.getAction() & 65280) >> 8;
    int pointerId = ev.getPointerId(pointerIndex);
    if (pointerId == this.mActivePointerId) {
      int newPointerIndex = pointerIndex == 0 ? 1 : 0;
      this.mLastMotionY = (int) ev.getY(newPointerIndex);
      this.mActivePointerId = ev.getPointerId(newPointerIndex);
      VelocityTracker velocityTracker = this.mVelocityTracker;
      if (velocityTracker != null) {
        velocityTracker.clear();
      }
    }
  }

  @Override // android.view.View
  public boolean onGenericMotionEvent(MotionEvent event) {
    float axisValue;
    switch (event.getAction()) {
      case 8:
        if (event.isFromSource(2)) {
          axisValue = event.getAxisValue(9);
        } else if (event.isFromSource(4194304)) {
          axisValue = event.getAxisValue(26);
        } else {
          axisValue = 0.0f;
        }
        int delta = Math.round(this.mVerticalScrollFactor * axisValue);
        if (delta != 0) {
          startNestedScroll(2);
          boolean canOverscroll = false;
          if (!dispatchNestedPreScroll(0, -delta, null, null)) {
            int range = getScrollRange();
            int oldScrollY = this.mScrollY;
            int newScrollY = oldScrollY - delta;
            int overscrollMode = getOverScrollMode();
            if (!event.isFromSource(8194)
                && (overscrollMode == 0 || (overscrollMode == 1 && range > 0))) {
              canOverscroll = true;
            }
            boolean absorbed = false;
            if (newScrollY < 0) {
              if (canOverscroll) {
                this.mEdgeGlowTop.onPullDistance((-newScrollY) / getHeight(), 0.5f);
                this.mEdgeGlowTop.onRelease();
                invalidate();
                absorbed = true;
              }
              newScrollY = 0;
            } else if (newScrollY > range) {
              if (canOverscroll) {
                this.mEdgeGlowBottom.onPullDistance((newScrollY - range) / getHeight(), 0.5f);
                this.mEdgeGlowBottom.onRelease();
                invalidate();
                absorbed = true;
              }
              newScrollY = range;
            }
            if (newScrollY != oldScrollY) {
              semShowGoToTop();
              super.scrollTo(this.mScrollX, newScrollY);
              return true;
            }
            if (canOverscroll) {
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
            if (absorbed) {
              return true;
            }
          }
        }
        break;
    }
    return super.onGenericMotionEvent(event);
  }

  @Override // android.view.View
  protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
    if (this.mScrollY != scrollY) {
      semShowGoToTop();
    }
    if (!this.mScroller.isFinished()) {
      int oldX = this.mScrollX;
      int oldY = this.mScrollY;
      this.mScrollX = scrollX;
      this.mScrollY = scrollY;
      invalidateParentIfNeeded();
      onScrollChanged(this.mScrollX, this.mScrollY, oldX, oldY);
      if (clampedY) {
        this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, 0, 0, getScrollRange());
      }
    } else {
      super.scrollTo(scrollX, scrollY);
    }
    awakenScrollBars();
  }

  @Override // android.view.View
  public boolean performAccessibilityActionInternal(int action, Bundle arguments) {
    if (super.performAccessibilityActionInternal(action, arguments)) {
      return true;
    }
    if (!isEnabled()) {
      return false;
    }
    int autoScrollSpeedLevel = 7;
    if (arguments != null) {
      int autoScrollSpeedLevelCount = arguments.getInt("auto_scroll_speed_level_count", 15);
      this.mAutoscrollDurationGap = 16.5f / (autoScrollSpeedLevelCount - 1);
      autoScrollSpeedLevel = arguments.getInt("auto_scroll_speed_level", 8) - 1;
    }
    switch (action) {
      case 4096:
      case 16908346:
        int viewportHeight = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
        int targetScrollY = Math.min(this.mScrollY + viewportHeight, getScrollRange());
        if (targetScrollY == this.mScrollY) {
          return false;
        }
        smoothScrollTo(0, targetScrollY);
        return true;
      case 8192:
      case 16908344:
        int viewportHeight2 = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
        int targetScrollY2 = Math.max(this.mScrollY - viewportHeight2, 0);
        if (targetScrollY2 == this.mScrollY) {
          return false;
        }
        smoothScrollTo(0, targetScrollY2);
        return true;
      case 4194304:
        if (!canScroll()) {
          return false;
        }
        float f = 17.1f - (this.mAutoscrollDurationGap * autoScrollSpeedLevel);
        this.mAutoscrollDuration = f;
        autoScrollWithDuration(f);
        return true;
      case 8388608:
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
      case 67108864:
        Log.m94d(TAG, "SEM_ACTION_AUTOSCROLL_TOP");
        if (!canScroll()) {
          return false;
        }
        smoothScrollToWithDuration(0, 0, 0);
        return true;
      case 268435456:
        if (!canScroll()) {
          return false;
        }
        float f2 = this.mAutoscrollDuration;
        if (f2 > 0.6f) {
          this.mAutoscrollDuration = f2 - this.mAutoscrollDurationGap;
        }
        autoScrollWithDuration(this.mAutoscrollDuration);
        return true;
      case 536870912:
        if (!canScroll()) {
          return false;
        }
        float f3 = this.mAutoscrollDuration;
        if (f3 < 17.1f) {
          this.mAutoscrollDuration = f3 + this.mAutoscrollDurationGap;
        }
        autoScrollWithDuration(this.mAutoscrollDuration);
        return true;
      default:
        return false;
    }
  }

  private void autoScrollWithDuration(float duration) {
    int tempdur = (int) ((getScrollRange() - this.mScrollY) * duration);
    this.mLinear = true;
    Log.m94d(TAG, "autoScrollWithDuration() duration = " + tempdur);
    smoothScrollByWithDuration(0, getScrollRange(), tempdur);
  }

  @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
  public CharSequence getAccessibilityClassName() {
    return ScrollView.class.getName();
  }

  @Override // android.view.ViewGroup, android.view.View
  public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo info) {
    int scrollRange;
    super.onInitializeAccessibilityNodeInfoInternal(info);
    if (isEnabled() && (scrollRange = getScrollRange()) > 0) {
      info.setScrollable(true);
      if (this.mScrollY > 0) {
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_UP);
      }
      if (this.mScrollY < scrollRange) {
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
        info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_DOWN);
      }
    }
    info.addAction(AccessibilityNodeInfo.AccessibilityAction.SEM_ACTION_AUTOSCROLL_ON);
    info.addAction(AccessibilityNodeInfo.AccessibilityAction.SEM_ACTION_AUTOSCROLL_OFF);
  }

  @Override // android.view.View
  public void onInitializeAccessibilityEventInternal(AccessibilityEvent event) {
    super.onInitializeAccessibilityEventInternal(event);
    boolean scrollable = getScrollRange() > 0;
    event.setScrollable(scrollable);
    event.setMaxScrollX(this.mScrollX);
    event.setMaxScrollY(getScrollRange());
  }

  private int getScrollRange() {
    if (getChildCount() <= 0) {
      return 0;
    }
    View child = getChildAt(0);
    int scrollRange =
        Math.max(0, child.getHeight() - ((getHeight() - this.mPaddingBottom) - this.mPaddingTop));
    return scrollRange;
  }

  private View findFocusableViewInBounds(boolean topFocus, int top, int bottom) {
    List<View> focusables = getFocusables(2);
    View focusCandidate = null;
    boolean foundFullyContainedFocusable = false;
    int count = focusables.size();
    for (int i = 0; i < count; i++) {
      View view = focusables.get(i);
      int viewTop = view.getTop();
      int viewBottom = view.getBottom();
      if (top < viewBottom && viewTop < bottom) {
        boolean viewIsCloserToBoundary = false;
        boolean viewIsFullyContained = top < viewTop && viewBottom < bottom;
        if (focusCandidate == null) {
          focusCandidate = view;
          foundFullyContainedFocusable = viewIsFullyContained;
        } else {
          if ((topFocus && viewTop < focusCandidate.getTop())
              || (!topFocus && viewBottom > focusCandidate.getBottom())) {
            viewIsCloserToBoundary = true;
          }
          if (foundFullyContainedFocusable) {
            if (viewIsFullyContained && viewIsCloserToBoundary) {
              focusCandidate = view;
            }
          } else if (viewIsFullyContained) {
            focusCandidate = view;
            foundFullyContainedFocusable = true;
          } else if (viewIsCloserToBoundary) {
            focusCandidate = view;
          }
        }
      }
    }
    return focusCandidate;
  }

  public boolean pageScroll(int direction) {
    boolean down = direction == 130;
    int height = getHeight();
    if (down) {
      this.mTempRect.top = getScrollY() + height;
      int count = getChildCount();
      if (count > 0) {
        View view = getChildAt(count - 1);
        if (this.mTempRect.top + height > view.getBottom()) {
          this.mTempRect.top = view.getBottom() - height;
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
    return scrollAndFocus(direction, this.mTempRect.top, this.mTempRect.bottom);
  }

  public boolean fullScroll(int direction) {
    int count;
    boolean down = direction == 130;
    int height = getHeight();
    this.mTempRect.top = 0;
    this.mTempRect.bottom = height;
    if (down && (count = getChildCount()) > 0) {
      View view = getChildAt(count - 1);
      this.mTempRect.bottom = view.getBottom() + this.mPaddingBottom;
      Rect rect = this.mTempRect;
      rect.top = rect.bottom - height;
    }
    return scrollAndFocus(direction, this.mTempRect.top, this.mTempRect.bottom);
  }

  private boolean scrollAndFocus(int direction, int top, int bottom) {
    boolean handled = true;
    int height = getHeight();
    int containerTop = getScrollY();
    int containerBottom = containerTop + height;
    boolean up = direction == 33;
    View newFocused = findFocusableViewInBounds(up, top, bottom);
    if (newFocused == null) {
      newFocused = this;
    }
    if (top >= containerTop && bottom <= containerBottom) {
      handled = false;
    } else {
      int delta = up ? top - containerTop : bottom - containerBottom;
      doScrollY(delta);
    }
    if (newFocused != findFocus()) {
      newFocused.requestFocus(direction);
    }
    return handled;
  }

  public boolean arrowScroll(int direction) {
    View currentFocused = findFocus();
    if (currentFocused == this) {
      currentFocused = null;
    }
    View nextFocused = FocusFinder.getInstance().findNextFocus(this, currentFocused, direction);
    int maxJump = getMaxScrollAmount();
    if (nextFocused != null && isWithinDeltaOfScreen(nextFocused, maxJump, getHeight())) {
      nextFocused.getDrawingRect(this.mTempRect);
      offsetDescendantRectToMyCoords(nextFocused, this.mTempRect);
      int scrollDelta = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
      doScrollY(scrollDelta);
      if (this.mSemEnableGoToTop
          && canScrollUp()
          && this.mSemGoToTopState != 2
          && scrollDelta != 0) {
        semSetupGoToTop(1);
        semAutoHide();
      }
      nextFocused.requestFocus(direction);
    } else {
      int scrollDelta2 = maxJump;
      if (direction == 33 && getScrollY() < scrollDelta2) {
        scrollDelta2 = getScrollY();
      } else if (direction == 130 && getChildCount() > 0) {
        int daBottom = getChildAt(0).getBottom();
        int screenBottom = (getScrollY() + getHeight()) - this.mPaddingBottom;
        if (daBottom - screenBottom < maxJump) {
          scrollDelta2 = daBottom - screenBottom;
        }
      }
      if (scrollDelta2 == 0) {
        return false;
      }
      semShowGoToTop();
      doScrollY(direction == 130 ? scrollDelta2 : -scrollDelta2);
    }
    if (currentFocused != null && currentFocused.isFocused() && isOffScreen(currentFocused)) {
      int descendantFocusability = getDescendantFocusability();
      setDescendantFocusability(131072);
      requestFocus();
      setDescendantFocusability(descendantFocusability);
    }
    return true;
  }

  private boolean isOffScreen(View descendant) {
    return !isWithinDeltaOfScreen(descendant, 0, getHeight());
  }

  private boolean isWithinDeltaOfScreen(View descendant, int delta, int height) {
    descendant.getDrawingRect(this.mTempRect);
    offsetDescendantRectToMyCoords(descendant, this.mTempRect);
    return this.mTempRect.bottom + delta >= getScrollY()
        && this.mTempRect.top - delta <= getScrollY() + height;
  }

  private void doScrollY(int delta) {
    if (delta != 0) {
      if (this.mSmoothScrollingEnabled) {
        smoothScrollBy(0, delta);
      } else {
        scrollBy(0, delta);
      }
    }
  }

  public final void smoothScrollBy(int dx, int dy) {
    if (getChildCount() == 0) {
      return;
    }
    long duration = AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll;
    if (duration > 250) {
      int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
      int bottom = getChildAt(0).getHeight();
      int maxY = Math.max(0, bottom - height);
      int scrollY = this.mScrollY;
      this.mScroller.startScroll(
          this.mScrollX, scrollY, 0, Math.max(0, Math.min(scrollY + dy, maxY)) - scrollY);
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
      scrollBy(dx, dy);
    }
    this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
  }

  public final void smoothScrollByWithDuration(int dx, int dy, int scrollDuration) {
    if (getChildCount() != 0) {
      long duration = AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll;
      if (duration > 250) {
        int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
        int bottom = getChildAt(0).getHeight();
        int maxY = Math.max(0, bottom - height);
        int scrollY = this.mScrollY;
        int dy2 = Math.max(0, Math.min(scrollY + dy, maxY)) - scrollY;
        this.mScroller.setInterpolator(this.mLinear ? sLinearInterpolator : null);
        this.mScroller.startScroll(this.mScrollX, scrollY, 0, dy2, scrollDuration);
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
        scrollBy(dx, dy);
      }
      this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }
  }

  public final void smoothScrollTo(int x, int y) {
    smoothScrollBy(x - this.mScrollX, y - this.mScrollY);
  }

  public final void smoothScrollToWithDuration(int x, int y, int scrollDuration) {
    smoothScrollByWithDuration(x - this.mScrollX, y - this.mScrollY, scrollDuration);
  }

  @Override // android.view.View
  protected int computeVerticalScrollRange() {
    int count = getChildCount();
    int contentHeight = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
    if (count == 0) {
      return contentHeight;
    }
    int scrollRange = getChildAt(0).getBottom();
    int scrollY = this.mScrollY;
    int overscrollBottom = Math.max(0, scrollRange - contentHeight);
    if (scrollY < 0) {
      return scrollRange - scrollY;
    }
    if (scrollY > overscrollBottom) {
      return scrollRange + (scrollY - overscrollBottom);
    }
    return scrollRange;
  }

  @Override // android.view.View
  protected int computeVerticalScrollOffset() {
    return Math.max(0, super.computeVerticalScrollOffset());
  }

  @Override // android.view.ViewGroup
  protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
    ViewGroup.LayoutParams lp = child.getLayoutParams();
    int childWidthMeasureSpec =
        getChildMeasureSpec(
            parentWidthMeasureSpec, this.mPaddingLeft + this.mPaddingRight, lp.width);
    int verticalPadding = this.mPaddingTop + this.mPaddingBottom;
    int childHeightMeasureSpec =
        View.MeasureSpec.makeSafeMeasureSpec(
            Math.max(0, View.MeasureSpec.getSize(parentHeightMeasureSpec) - verticalPadding), 0);
    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
  }

  @Override // android.view.ViewGroup
  protected void measureChildWithMargins(
      View child,
      int parentWidthMeasureSpec,
      int widthUsed,
      int parentHeightMeasureSpec,
      int heightUsed) {
    ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
    int childWidthMeasureSpec =
        getChildMeasureSpec(
            parentWidthMeasureSpec,
            this.mPaddingLeft + this.mPaddingRight + lp.leftMargin + lp.rightMargin + widthUsed,
            lp.width);
    int usedTotal =
        this.mPaddingTop + this.mPaddingBottom + lp.topMargin + lp.bottomMargin + heightUsed;
    int childHeightMeasureSpec =
        View.MeasureSpec.makeSafeMeasureSpec(
            Math.max(0, View.MeasureSpec.getSize(parentHeightMeasureSpec) - usedTotal), 0);
    child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
  }

  @Override // android.view.View
  public void computeScroll() {
    if (this.mScroller.computeScrollOffset()) {
      int oldX = this.mScrollX;
      int oldY = this.mScrollY;
      int x = this.mScroller.getCurrX();
      int y = this.mScroller.getCurrY();
      int deltaY = consumeFlingInStretch(y - oldY);
      if (oldX != x || deltaY != 0) {
        int range = getScrollRange();
        int overscrollMode = getOverScrollMode();
        boolean z = true;
        if (overscrollMode != 0 && (overscrollMode != 1 || range <= 0)) {
          z = false;
        }
        boolean canOverscroll = z;
        overScrollBy(x - oldX, deltaY, oldX, oldY, 0, range, 0, this.mOverflingDistance, false);
        onScrollChanged(this.mScrollX, this.mScrollY, oldX, oldY);
        if (canOverscroll && deltaY != 0) {
          if (y < 0 && oldY >= 0) {
            this.mEdgeGlowTop.onAbsorb((int) this.mScroller.getCurrVelocity());
          } else if (y > range && oldY <= range) {
            this.mEdgeGlowBottom.onAbsorb((int) this.mScroller.getCurrVelocity());
          }
        }
      }
      if (!awakenScrollBars()) {
        postInvalidateOnAnimation();
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

  private int consumeFlingInStretch(int unconsumed) {
    EdgeEffect edgeEffect;
    EdgeEffect edgeEffect2;
    if (unconsumed > 0
        && (edgeEffect2 = this.mEdgeGlowTop) != null
        && edgeEffect2.getDistance() != 0.0f) {
      int size = getHeight();
      float deltaDistance = ((-unconsumed) * FLING_DESTRETCH_FACTOR) / size;
      int consumed =
          Math.round(
              ((-size) / FLING_DESTRETCH_FACTOR)
                  * this.mEdgeGlowTop.onPullDistance(deltaDistance, 0.5f));
      this.mEdgeGlowTop.onRelease();
      if (consumed != unconsumed) {
        this.mEdgeGlowTop.finish();
      }
      return unconsumed - consumed;
    }
    if (unconsumed < 0
        && (edgeEffect = this.mEdgeGlowBottom) != null
        && edgeEffect.getDistance() != 0.0f) {
      int size2 = getHeight();
      float deltaDistance2 = (unconsumed * FLING_DESTRETCH_FACTOR) / size2;
      int consumed2 =
          Math.round(
              (size2 / FLING_DESTRETCH_FACTOR)
                  * this.mEdgeGlowBottom.onPullDistance(deltaDistance2, 0.5f));
      this.mEdgeGlowBottom.onRelease();
      if (consumed2 != unconsumed) {
        this.mEdgeGlowBottom.finish();
      }
      return unconsumed - consumed2;
    }
    return unconsumed;
  }

  public void scrollToDescendant(View child) {
    if (!this.mIsLayoutDirty && child != null) {
      child.getDrawingRect(this.mTempRect);
      offsetDescendantRectToMyCoords(child, this.mTempRect);
      int scrollDelta = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
      if (scrollDelta != 0) {
        scrollBy(0, scrollDelta);
        return;
      }
      return;
    }
    this.mChildToScrollTo = child;
  }

  private boolean scrollToChildRect(Rect rect, boolean immediate) {
    int delta = computeScrollDeltaToGetChildRectOnScreen(rect);
    boolean scroll = delta != 0;
    if (scroll) {
      if (immediate) {
        scrollBy(0, delta);
      } else {
        smoothScrollBy(0, delta);
      }
    }
    return scroll;
  }

  protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
    if (getChildCount() == 0) {
      return 0;
    }
    int height = getHeight();
    int screenTop = getScrollY();
    int screenBottom = screenTop + height;
    int fadingEdge = getVerticalFadingEdgeLength();
    if (rect.top > 0) {
      screenTop += fadingEdge;
    }
    if (rect.bottom < getChildAt(0).getHeight()) {
      screenBottom -= fadingEdge;
    }
    if (rect.bottom > screenBottom && rect.top > screenTop) {
      int scrollYDelta =
          rect.height() > height ? 0 + (rect.top - screenTop) : 0 + (rect.bottom - screenBottom);
      int bottom = getChildAt(0).getBottom();
      int distanceToBottom = bottom - screenBottom;
      return Math.min(scrollYDelta, distanceToBottom);
    }
    if (rect.top >= screenTop || rect.bottom >= screenBottom) {
      return 0;
    }
    int scrollYDelta2 =
        rect.height() > height ? 0 - (screenBottom - rect.bottom) : 0 - (screenTop - rect.top);
    return Math.max(scrollYDelta2, -getScrollY());
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public void requestChildFocus(View child, View focused) {
    if (focused != null && focused.getRevealOnFocusHint()) {
      if (!this.mIsLayoutDirty) {
        scrollToDescendant(focused);
      } else {
        this.mChildToScrollTo = focused;
      }
    }
    super.requestChildFocus(child, focused);
  }

  @Override // android.view.ViewGroup
  protected boolean onRequestFocusInDescendants(int direction, Rect previouslyFocusedRect) {
    View nextFocus;
    if (direction == 2) {
      direction = 130;
    } else if (direction == 1) {
      direction = 33;
    }
    if (previouslyFocusedRect == null) {
      nextFocus = FocusFinder.getInstance().findNextFocus(this, null, direction);
    } else {
      nextFocus =
          FocusFinder.getInstance().findNextFocusFromRect(this, previouslyFocusedRect, direction);
    }
    if (nextFocus == null || isOffScreen(nextFocus)) {
      return false;
    }
    return nextFocus.requestFocus(direction, previouslyFocusedRect);
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public boolean requestChildRectangleOnScreen(View child, Rect rectangle, boolean immediate) {
    rectangle.offset(child.getLeft() - child.getScrollX(), child.getTop() - child.getScrollY());
    return scrollToChildRect(rectangle, immediate);
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
  public void onWindowFocusChanged(boolean hasWindowFocus) {
    super.onWindowFocusChanged(hasWindowFocus);
  }

  @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
  protected void onLayout(boolean changed, int l, int t, int r, int b) {
    super.onLayout(changed, l, t, r, b);
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
      int childHeight = getChildCount() > 0 ? getChildAt(0).getMeasuredHeight() : 0;
      int scrollRange =
          Math.max(0, childHeight - (((b - t) - this.mPaddingBottom) - this.mPaddingTop));
      if (this.mScrollY > scrollRange) {
        this.mScrollY = scrollRange;
      } else if (this.mScrollY < 0) {
        this.mScrollY = 0;
      }
    }
    if (changed) {
      Log.m94d(TAG, " onsize change changed ");
      this.mSizeChange = true;
      semSetupGoToTop(3);
      semAutoHide();
    }
    scrollTo(this.mScrollX, this.mScrollY);
  }

  @Override // android.view.View
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    View currentFocused = findFocus();
    if (currentFocused != null
        && this != currentFocused
        && isWithinDeltaOfScreen(currentFocused, 0, oldh)) {
      currentFocused.getDrawingRect(this.mTempRect);
      offsetDescendantRectToMyCoords(currentFocused, this.mTempRect);
      int scrollDelta = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
      doScrollY(scrollDelta);
    }
  }

  private static boolean isViewDescendantOf(View child, View parent) {
    if (child == parent) {
      return true;
    }
    Object parent2 = child.getParent();
    return (parent2 instanceof ViewGroup) && isViewDescendantOf((View) parent2, parent);
  }

  public void fling(int velocityY) {
    if (getChildCount() > 0) {
      int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
      int bottom = getChildAt(0).getHeight();
      this.mScroller.fling(
          this.mScrollX,
          this.mScrollY,
          0,
          velocityY,
          0,
          0,
          0,
          Math.max(0, bottom - height),
          0,
          height / 2);
      if (this.mFlingStrictSpan == null) {
        this.mFlingStrictSpan = StrictMode.enterCriticalSpan("ScrollView-fling");
      }
      postInvalidateOnAnimation();
    }
  }

  private void flingWithoutAcc(int velocityY) {
    if (getChildCount() > 0) {
      int height = (getHeight() - this.mPaddingBottom) - this.mPaddingTop;
      int bottom = getChildAt(0).getHeight();
      this.mScroller.fling(
          this.mScrollX,
          this.mScrollY,
          0,
          velocityY,
          0,
          0,
          0,
          Math.max(0, bottom - height),
          0,
          height / 2,
          true);
      if (this.mFlingStrictSpan == null) {
        this.mFlingStrictSpan = StrictMode.enterCriticalSpan("ScrollView-fling");
      }
      postInvalidateOnAnimation();
    }
  }

  private void flingWithNestedDispatch(int velocityY) {
    boolean canFling =
        (this.mScrollY > 0 || velocityY > 0) && (this.mScrollY < getScrollRange() || velocityY < 0);
    if (!dispatchNestedPreFling(0.0f, velocityY)) {
      boolean consumed = dispatchNestedFling(0.0f, velocityY, canFling);
      if (canFling) {
        fling(velocityY);
        return;
      }
      if (!consumed) {
        if (!this.mEdgeGlowTop.isFinished()) {
          if (shouldAbsorb(this.mEdgeGlowTop, -velocityY)) {
            this.mEdgeGlowTop.onAbsorb(-velocityY);
            return;
          } else {
            fling(velocityY);
            return;
          }
        }
        if (!this.mEdgeGlowBottom.isFinished()) {
          if (shouldAbsorb(this.mEdgeGlowBottom, velocityY)) {
            this.mEdgeGlowBottom.onAbsorb(velocityY);
          } else {
            fling(velocityY);
          }
        }
      }
    }
  }

  private boolean shouldAbsorb(EdgeEffect edgeEffect, int velocity) {
    if (velocity > 0) {
      return true;
    }
    float distance = edgeEffect.getDistance() * getHeight();
    float flingDistance = (float) this.mScroller.getSplineFlingDistance(-velocity);
    return flingDistance < distance;
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
  public void scrollTo(int x, int y) {
    if (getChildCount() > 0) {
      View child = getChildAt(0);
      int x2 = clamp(x, (getWidth() - this.mPaddingRight) - this.mPaddingLeft, child.getWidth());
      int y2 = clamp(y, (getHeight() - this.mPaddingBottom) - this.mPaddingTop, child.getHeight());
      if (x2 != this.mScrollX || y2 != this.mScrollY) {
        semShowGoToTop();
        super.scrollTo(x2, y2);
      }
    }
  }

  @Override // android.view.View
  protected boolean verifyDrawable(Drawable dr) {
    return this.mSemGoToTopImage == dr || super.verifyDrawable(dr);
  }

  @Override // android.view.ViewGroup, android.view.View
  protected void onAttachedToWindow() {
    super.onAttachedToWindow();
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
    return (nestedScrollAxes & 2) != 0;
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public void onNestedScrollAccepted(View child, View target, int axes) {
    super.onNestedScrollAccepted(child, target, axes);
    startNestedScroll(2);
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public void onStopNestedScroll(View target) {
    super.onStopNestedScroll(target);
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public void onNestedScroll(
      View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    int oldScrollY = this.mScrollY;
    scrollBy(0, dyUnconsumed);
    int myConsumed = this.mScrollY - oldScrollY;
    int myUnconsumed = dyUnconsumed - myConsumed;
    dispatchNestedScroll(0, myConsumed, 0, myUnconsumed, null);
  }

  @Override // android.view.ViewGroup, android.view.ViewParent
  public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
    if (!consumed) {
      flingWithNestedDispatch((int) velocityY);
      return true;
    }
    return false;
  }

  @Override // android.view.View
  public void draw(Canvas canvas) {
    int width;
    int height;
    float translateX;
    float translateY;
    int width2;
    int height2;
    float translateX2;
    float translateY2;
    super.draw(canvas);
    if (shouldDisplayEdgeEffects()) {
      int scrollY = this.mScrollY;
      boolean clipToPadding = getClipToPadding();
      if (!this.mEdgeGlowTop.isFinished()) {
        int restoreCount = canvas.save();
        if (clipToPadding) {
          width2 = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
          height2 = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
          translateX2 = this.mPaddingLeft;
          translateY2 = this.mPaddingTop;
        } else {
          width2 = getWidth();
          height2 = getHeight();
          translateX2 = 0.0f;
          translateY2 = 0.0f;
        }
        canvas.translate(translateX2, Math.min(0, scrollY) + translateY2);
        this.mEdgeGlowTop.setSize(width2, height2);
        if (this.mEdgeGlowTop.draw(canvas)) {
          postInvalidateOnAnimation();
        }
        canvas.restoreToCount(restoreCount);
      }
      if (!this.mEdgeGlowBottom.isFinished()) {
        int restoreCount2 = canvas.save();
        if (clipToPadding) {
          width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
          height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
          translateX = this.mPaddingLeft;
          translateY = this.mPaddingTop;
        } else {
          width = getWidth();
          height = getHeight();
          translateX = 0.0f;
          translateY = 0.0f;
        }
        canvas.translate(
            (-width) + translateX, Math.max(getScrollRange(), scrollY) + height + translateY);
        canvas.rotate(180.0f, width, 0.0f);
        this.mEdgeGlowBottom.setSize(width, height);
        if (this.mEdgeGlowBottom.draw(canvas)) {
          postInvalidateOnAnimation();
        }
        canvas.restoreToCount(restoreCount2);
      }
    }
    if (this.mSemEnableGoToTop) {
      drawGoToTop(canvas);
    }
  }

  private void drawGoToTop(Canvas canvas) {
    int scrollY = this.mScrollY;
    int restoreCount = canvas.save();
    canvas.translate(0.0f, scrollY);
    if (scrollY == 0 && this.mSemGoToTopState != 0) {
      post(this.mSemGoToTopFadeOutRunnable);
    }
    if (!this.mSemGoToTopRect.isEmpty()) {
      if (canvas.isHardwareAccelerated()) {
        canvas.enableZ();
        float alpha = this.mSemGoToTopImage.getAlpha() / 255.0f;
        RecordingCanvas recordingCanvas = this.mGoToTopRenderNode.beginRecording();
        this.mOutline.setAlpha(alpha);
        this.mGoToTopRenderNode.setOutline(this.mOutline);
        this.mGoToTopRenderNode.setAlpha(alpha);
        recordingCanvas.drawBitmap(this.mSemGoToTopBitmap, 0.0f, 0.0f, (Paint) null);
        canvas.drawRenderNode(this.mGoToTopRenderNode);
        this.mGoToTopRenderNode.endRecording();
        canvas.disableZ();
      } else {
        this.mSemGoToTopImage.draw(canvas);
      }
    }
    canvas.restoreToCount(restoreCount);
  }

  private Bitmap drawableToBitmap(Drawable drawable) {
    Bitmap bitmap =
        Bitmap.createBitmap(
            drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);
    return bitmap;
  }

  private static int clamp(int n, int my, int child) {
    if (my >= child || n < 0) {
      return 0;
    }
    if (my + n > child) {
      return child - my;
    }
    return n;
  }

  /* JADX INFO: Access modifiers changed from: protected */
  @Override // android.view.View
  public void onRestoreInstanceState(Parcelable state) {
    if (this.mContext.getApplicationInfo().targetSdkVersion <= 18
        || !(state instanceof SavedState)) {
      super.onRestoreInstanceState(state);
      return;
    }
    SavedState ss = (SavedState) state;
    super.onRestoreInstanceState(ss.getSuperState());
    this.mSavedState = ss;
    requestLayout();
  }

  /* JADX INFO: Access modifiers changed from: protected */
  @Override // android.view.View
  public Parcelable onSaveInstanceState() {
    if (this.mContext.getApplicationInfo().targetSdkVersion <= 18) {
      return super.onSaveInstanceState();
    }
    Parcelable superState = super.onSaveInstanceState();
    SavedState ss = new SavedState(superState);
    ss.scrollPosition = this.mScrollY;
    return ss;
  }

  @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
  protected void encodeProperties(ViewHierarchyEncoder encoder) {
    super.encodeProperties(encoder);
    encoder.addProperty("fillViewport", this.mFillViewport);
  }

  static class SavedState extends View.BaseSavedState {
    public static final Parcelable.Creator<SavedState> CREATOR =
        new Parcelable.Creator<SavedState>() { // from class: android.widget.ScrollView.SavedState.1
          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
          }

          /* JADX WARN: Can't rename method to resolve collision */
          @Override // android.os.Parcelable.Creator
          public SavedState[] newArray(int size) {
            return new SavedState[size];
          }
        };
    public int scrollPosition;

    SavedState(Parcelable superState) {
      super(superState);
    }

    public SavedState(Parcel source) {
      super(source);
      this.scrollPosition = source.readInt();
    }

    @Override // android.view.View.BaseSavedState, android.view.AbsSavedState,
              // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
      super.writeToParcel(dest, flags);
      dest.writeInt(this.scrollPosition);
    }

    public String toString() {
      return "ScrollView.SavedState{"
          + Integer.toHexString(System.identityHashCode(this))
          + " scrollPosition="
          + this.scrollPosition
          + "}";
    }
  }

  private static class HoverScrollHandler extends Handler {
    private final WeakReference<ScrollView> mScrollView;

    HoverScrollHandler(ScrollView sv) {
      this.mScrollView = new WeakReference<>(sv);
    }

    @Override // android.os.Handler
    public void handleMessage(Message msg) {
      ScrollView sv = this.mScrollView.get();
      if (sv != null) {
        sv.handleMessage(msg);
      }
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void handleMessage(Message msg) {
    int offset;
    switch (msg.what) {
      case 1:
        int range = getScrollRange();
        long currentTimeMillis = System.currentTimeMillis();
        this.mHoverRecognitionCurrentTime = currentTimeMillis;
        this.mHoverRecognitionDurationTime =
            (currentTimeMillis - this.mHoverRecognitionStartTime) / 1000;
        if (currentTimeMillis - this.mHoverScrollStartTime >= this.mHoverScrollTimeInterval) {
          int applyDimension =
              (int)
                  (TypedValue.applyDimension(
                          1,
                          this.HOVERSCROLL_SPEED,
                          this.mContext.getResources().getDisplayMetrics())
                      + 0.5f);
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
            offset = this.mHoverScrollSpeed * (-1);
          } else {
            int offset2 = this.mHoverScrollSpeed;
            offset = offset2 * 1;
          }
          if (offset < 0 && this.mScrollY > 0) {
            flingWithoutAcc(offset);
            this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
            break;
          } else if (offset > 0 && this.mScrollY < range) {
            flingWithoutAcc(offset);
            this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
            break;
          } else {
            int overscrollMode = getOverScrollMode();
            boolean canOverscroll = overscrollMode == 0 || (overscrollMode == 1 && range > 0);
            if (canOverscroll && !this.mIsHoverOverscrolled) {
              int i = this.mHoverScrollDirection;
              if (i == 2) {
                int width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                this.mEdgeGlowTop.setSize(width, getHeight());
                this.mEdgeGlowTop.onAbsorb(10000);
                if (!this.mEdgeGlowBottom.isFinished()) {
                  this.mEdgeGlowBottom.onRelease();
                }
              } else if (i == 1) {
                int width2 = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
                this.mEdgeGlowBottom.setSize(width2, getHeight());
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
            if (!canOverscroll && !this.mIsHoverOverscrolled) {
              this.mIsHoverOverscrolled = true;
              break;
            }
          }
        }
        break;
    }
  }

  private void showPointerIcon(MotionEvent ev, int iconId) {
    if (iconId == 20011 || iconId == 20015) {
      iconId = semGetRotatePointerIcon(iconId);
    }
    InputDevice inputDevice = ev.getDevice();
    if (inputDevice != null) {
      inputDevice.setPointerType(iconId);
    } else {
      Log.m96e(TAG, "Failed to change PointerIcon to " + iconId);
    }
  }

  /* JADX INFO: Access modifiers changed from: private */
  public void semSetupGoToTop(int where) {
    int overlappedW;
    int overlappedW2;
    if (this.mSemEnableGoToTop) {
      removeCallbacks(this.mSemAutoHide);
      int where2 = where;
      if (where2 == 3) {
        where2 = canScrollUp() ? this.mSemGoToTopLastState : 0;
      }
      if (where2 != 2) {
        this.mSemGoToTopImage.setState(StateSet.NOTHING);
      }
      if (!this.mIsGoToTopShown && where2 == 0 && this.mSemGoToTopLastState != 0) {
        post(this.mSemGoToTopFadeOutRunnable);
      }
      this.mSemGoToTopState = where2;
      int w = getWidth();
      int h = getHeight();
      int contentW = (w - this.mPaddingLeft) - this.mPaddingRight;
      int centerX = this.mPaddingLeft + (contentW / 2);
      int[] locOnScr = {0, 0};
      getLocationInWindow(locOnScr);
      DisplayMetrics dm = getResources().getDisplayMetrics();
      Display display =
          ((WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE))
              .getDefaultDisplay();
      int rotate = display.getRotation();
      boolean isLandScape = rotate == 1 || rotate == 3;
      Rect displayFrame = new Rect();
      getWindowVisibleDisplayFrame(displayFrame);
      int left = isLandScape ? displayFrame.left : 0;
      int right = isLandScape ? displayFrame.right : dm.widthPixels;
      if (locOnScr[0] < left && (overlappedW2 = -locOnScr[0]) > this.mPaddingLeft) {
        centerX += (overlappedW2 - this.mPaddingLeft) / 2;
      }
      if (locOnScr[0] + w > right
          && (overlappedW = (locOnScr[0] + w) - dm.widthPixels) > this.mPaddingRight) {
        centerX -= (overlappedW - this.mPaddingRight) / 2;
      }
      switch (this.mSemGoToTopState) {
        case 0:
          if (this.mIsGoToTopShown) {
            this.mSemGoToTopRect.setEmpty();
            break;
          }
          break;
        case 1:
        case 2:
          Rect rect = this.mSemGoToTopRect;
          int i = this.mGoToTopWH;
          int w2 = this.mGoToTopGap;
          int contentW2 = (h - i) - w2;
          rect.set(centerX - (i / 2), contentW2, (i / 2) + centerX, h - w2);
          break;
      }
      this.mSemGoToTopImage.setBounds(this.mSemGoToTopRect);
      if (this.mIsGoToTopShown) {
        this.mIsGoToTopShown = false;
      }
      if (where2 == 1 && (this.mSemGoToTopLastState == 0 || this.mSizeChange)) {
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

  public void semSetGoToTopEnabled(boolean enabled) {
    semSetGoToTopEnabled(enabled, 1);
  }

  public void semSetGoToTopEnabled(boolean enabled, int buttonStyle) {
    Drawable drawable =
        buttonStyle == 0
            ? this.mSemGoToTopLightImage
            : this.mContext.getResources().getDrawable(C4337R.drawable.sem_list_go_to_top_dark);
    this.mSemGoToTopImage = drawable;
    if (drawable != null) {
      this.mSemEnableGoToTop = enabled;
      if (drawable.getAlpha() != 255) {
        this.mSemGoToTopImage.setAlpha(255);
      }
      this.mSemGoToTopBitmap = drawableToBitmap(this.mSemGoToTopImage);
      this.mSemGoToTopImage.setAlpha(0);
      if (enabled) {
        this.mSemGoToTopImage.setCallback(this);
      } else {
        this.mSemGoToTopImage.setCallback(null);
      }
      ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
      this.mSemGoToTopFadeInAnimator = ofInt;
      ofInt.setDuration(333L);
      this.mSemGoToTopFadeInAnimator.setInterpolator(new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
      this.mSemGoToTopFadeInAnimator.addUpdateListener(
          new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.ScrollView.6
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
              int value = ((Integer) animation.getAnimatedValue()).intValue();
              ScrollView.this.mSemGoToTopImage.setAlpha(value);
              ScrollView.this.invalidate();
            }
          });
      ValueAnimator ofInt2 = ValueAnimator.ofInt(255, 0);
      this.mSemGoToTopFadeOutAnimator = ofInt2;
      ofInt2.setDuration(333L);
      this.mSemGoToTopFadeOutAnimator.setInterpolator(
          new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f));
      this.mSemGoToTopFadeOutAnimator.addUpdateListener(
          new ValueAnimator.AnimatorUpdateListener() { // from class: android.widget.ScrollView.7
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
              int value = ((Integer) animation.getAnimatedValue()).intValue();
              ScrollView.this.mSemGoToTopImage.setAlpha(value);
              ScrollView.this.invalidate();
            }
          });
      this.mSemGoToTopFadeOutAnimator.addListener(
          new Animator.AnimatorListener() { // from class: android.widget.ScrollView.8
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {}

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
              ScrollView.this.mIsGoToTopShown = true;
              ScrollView.this.semSetupGoToTop(0);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animation) {}

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animation) {}
          });
      RenderNode renderNode = new RenderNode("goToTop");
      this.mGoToTopRenderNode = renderNode;
      renderNode.setElevation(this.mGoToTopElevation);
    }
  }

  void semAutoHide() {
    if (!this.mSemEnableGoToTop) {
      return;
    }
    removeCallbacks(this.mSemAutoHide);
    postDelayed(this.mSemAutoHide, this.GO_TO_TOP_HIDE);
  }

  private void semShowGoToTop() {
    if (this.mSemEnableGoToTop && this.mSemGoToTopState != 2 && canScrollUp()) {
      semSetupGoToTop(1);
      semAutoHide();
    }
  }

  private int semGetRotatePointerIcon(int iconId) {
    float rotation = getRotation() + semGetParentViewRotation();
    boolean scrollUp = iconId == 20011;
    boolean isNegative = rotation < 0.0f;
    float editedRotation = (isNegative ? -45 : 45) + rotation;
    int direction = (((int) (editedRotation / 90.0f)) + (scrollUp ? 0 : isNegative ? -2 : 2)) % 4;
    if (isNegative && direction != 0) {
      direction += 4;
    }
    switch (Math.abs(direction)) {
      case 0:
        return 20011;
      case 1:
        return 20013;
      case 2:
        return 20015;
      case 3:
        return 20017;
      default:
        return 20001;
    }
  }

  private float semGetParentViewRotation() {
    View parent = this;
    while (parent.getParent() instanceof View) {
      parent = (View) parent.getParent();
      if (parent.getRotation() != 0.0f) {
        return parent.getRotation();
      }
    }
    return 0.0f;
  }

  @Deprecated
  public void semSetSmoothScrollEnabled(boolean enabled) {
    this.mScroller.semSetSmoothScrollEnabled(enabled);
  }
}
