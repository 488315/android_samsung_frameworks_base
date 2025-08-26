package android.widget;

import android.R;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.TtmlUtils;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
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
import android.view.ViewRootImpl;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.AnimationUtils;
import android.view.flags.Flags;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import android.widget.FrameLayout;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class HorizontalScrollView extends FrameLayout {
    private static final int ANIMATED_SCROLL_GAP = 250;
    private static final float FLING_DESTRETCH_FACTOR = 4.0f;
    private static final int HOVERSCROLL_LEFT = 1;
    private static final int HOVERSCROLL_RIGHT = 2;
    private static final int HOVERSCROLL_WIDTH_DP = 25;
    private static final int INVALID_POINTER = -1;
    private static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final int MSG_HOVERSCROLL_MOVE = 1;
    private static final int MSG_TIMEOUT = 4;
    private static final String TAG = "HorizontalScrollView";
    private static final int TIMEOUT_DELAY = 100;
    private int HOVERSCROLL_DELAY;
    private int HOVERSCROLL_SPEED;
    private final int ON_ABSORB_VELOCITY;
    private int mActivePointerId;
    private View mChildToScrollTo;
    public EdgeEffect mEdgeGlowLeft;
    public EdgeEffect mEdgeGlowRight;

    @ViewDebug.ExportedProperty(category = TtmlUtils.TAG_LAYOUT)
    private boolean mFillViewport;
    private float mHorizontalScrollFactor;
    private boolean mHoverAreaEnter;
    private int mHoverAreaWidth;
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
    private boolean mIsBeingDragged;
    private boolean mIsHoverOverscrolled;
    private boolean mIsLayoutDirty;
    private boolean mIsSetOpenTheme;
    private boolean mIsThemeDeviceDefaultFamily;
    private int mLastMotionX;
    private long mLastScroll;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private boolean mNeedsHoverScroll;
    private int mOverflingDistance;
    private int mOverscrollDistance;
    private SavedState mSavedState;
    private OverScroller mScroller;
    private boolean mSmoothScrollingEnabled;
    private final Rect mTempRect;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;

    private static int clamp(int i, int i2, int i3) {
        if (i2 >= i3 || i < 0) {
            return 0;
        }
        return i2 + i > i3 ? i3 - i2 : i;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public void updateCustomEdgeGlow(Drawable drawable, Drawable drawable2) {
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<HorizontalScrollView> {
        private int mFillViewportId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mFillViewportId = propertyMapper.mapBoolean("fillViewport", 16843130);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(HorizontalScrollView horizontalScrollView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readBoolean(this.mFillViewportId, horizontalScrollView.isFillViewport());
        }
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public void setTouchSlop(int i) {
        this.mTouchSlop = i;
    }

    private void hidden_setTouchSlop(int i) {
        setTouchSlop(i);
    }

    public HorizontalScrollView(Context context) {
        this(context, null);
    }

    public HorizontalScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843603);
    }

    public HorizontalScrollView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public HorizontalScrollView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mTempRect = new Rect();
        this.mIsLayoutDirty = true;
        this.mChildToScrollTo = null;
        this.mIsBeingDragged = false;
        this.mSmoothScrollingEnabled = true;
        this.mActivePointerId = -1;
        this.ON_ABSORB_VELOCITY = 10000;
        this.mHoverAreaWidth = 0;
        this.mHoverRecognitionDurationTime = 0L;
        this.mHoverRecognitionCurrentTime = 0L;
        this.mHoverRecognitionStartTime = 0L;
        this.mHoverScrollTimeInterval = 300L;
        this.mHoverScrollStartTime = 0L;
        this.mHoverScrollDirection = -1;
        this.mIsHoverOverscrolled = false;
        this.mHoverScrollEnable = true;
        this.mHoverScrollStateChanged = false;
        this.mHoverAreaEnter = false;
        this.HOVERSCROLL_SPEED = 15;
        this.HOVERSCROLL_DELAY = 15;
        this.mNeedsHoverScroll = false;
        this.mHoverScrollSpeed = 0;
        this.mEdgeGlowLeft = new EdgeEffect(context, attributeSet);
        this.mEdgeGlowRight = new EdgeEffect(context, attributeSet);
        this.mEdgeGlowLeft.semSetHostView(this, false);
        this.mEdgeGlowRight.semSetHostView(this, false);
        initScrollView();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.HorizontalScrollView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.HorizontalScrollView, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        setFillViewport(typedArrayObtainStyledAttributes.getBoolean(0, false));
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(com.android.internal.R.attr.parentIsDeviceDefault, typedValue, true);
        this.mIsThemeDeviceDefaultFamily = typedValue.data != 0;
        boolean z = this.mIsThemeDeviceDefaultFamily && Settings.System.getString(context.getContentResolver(), "current_sec_active_themepackage") != null && context.getResources().getAssets().getSamsungThemeOverlays().size() > 0;
        this.mIsSetOpenTheme = z;
        if (z) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, com.android.internal.R.styleable.View, i, i2);
            if (typedArrayObtainStyledAttributes2.getResourceId(13, 0) == 17304057) {
                setBackground(context.getDrawable(com.android.internal.R.drawable.tw_action_bar_background_stacked));
            }
            typedArrayObtainStyledAttributes2.recycle();
        }
        typedArrayObtainStyledAttributes.recycle();
        if (context.getResources().getConfiguration().uiMode == 6) {
            setRevealOnFocusHint(false);
        }
    }

    @Override // android.view.View
    protected float getLeftFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (this.mScrollX < horizontalFadingEdgeLength) {
            return this.mScrollX / horizontalFadingEdgeLength;
        }
        return 1.0f;
    }

    @Override // android.view.View
    protected float getRightFadingEdgeStrength() {
        if (getChildCount() == 0) {
            return 0.0f;
        }
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        int right = (getChildAt(0).getRight() - this.mScrollX) - (getWidth() - this.mPaddingRight);
        if (right < horizontalFadingEdgeLength) {
            return right / horizontalFadingEdgeLength;
        }
        return 1.0f;
    }

    public void setEdgeEffectColor(int i) {
        setLeftEdgeEffectColor(i);
        setRightEdgeEffectColor(i);
    }

    public void setRightEdgeEffectColor(int i) {
        this.mEdgeGlowRight.setColor(i);
    }

    public void setLeftEdgeEffectColor(int i) {
        this.mEdgeGlowLeft.setColor(i);
    }

    public int getLeftEdgeEffectColor() {
        return this.mEdgeGlowLeft.getColor();
    }

    public int getRightEdgeEffectColor() {
        return this.mEdgeGlowRight.getColor();
    }

    public int getMaxScrollAmount() {
        return (int) ((this.mRight - this.mLeft) * 0.5f);
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
        this.mHorizontalScrollFactor = viewConfiguration.getScaledHorizontalScrollFactor();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
    }

    @Override // android.view.ViewGroup
    public void addView(View view) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(view);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(view, i);
    }

    @Override // android.view.ViewGroup, android.view.ViewManager
    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(view, layoutParams);
    }

    @Override // android.view.ViewGroup
    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (getChildCount() > 0) {
            throw new IllegalStateException("HorizontalScrollView can host only one direct child");
        }
        super.addView(view, i, layoutParams);
    }

    private boolean canScroll() {
        View childAt = getChildAt(0);
        if (childAt != null) {
            if (getWidth() < childAt.getWidth() + this.mPaddingLeft + this.mPaddingRight) {
                return true;
            }
        }
        return false;
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
        if (this.mFillViewport && View.MeasureSpec.getMode(i) != 0 && getChildCount() > 0) {
            View childAt = getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) childAt.getLayoutParams();
            if (getContext().getApplicationInfo().targetSdkVersion >= 23) {
                i3 = this.mPaddingLeft + this.mPaddingRight + layoutParams.leftMargin + layoutParams.rightMargin;
                i4 = this.mPaddingTop + this.mPaddingBottom + layoutParams.topMargin;
                i5 = layoutParams.bottomMargin;
            } else {
                i3 = this.mPaddingLeft + this.mPaddingRight;
                i4 = this.mPaddingTop;
                i5 = this.mPaddingBottom;
            }
            int i6 = i4 + i5;
            int measuredWidth = getMeasuredWidth() - i3;
            if (childAt.getMeasuredWidth() < measuredWidth) {
                childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), getChildMeasureSpec(i2, i6, layoutParams.height));
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        return super.dispatchKeyEvent(keyEvent) || executeKeyEvent(keyEvent);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) throws Resources.NotFoundException {
        boolean zFullScroll;
        boolean zFullScroll2;
        this.mTempRect.setEmpty();
        boolean z = false;
        if (!canScroll()) {
            if (isFocused()) {
                View viewFindFocus = findFocus();
                if (viewFindFocus == this) {
                    viewFindFocus = null;
                }
                View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, 66);
                if (viewFindNextFocus != null && viewFindNextFocus != this && viewFindNextFocus.requestFocus(66)) {
                    return true;
                }
            }
            return false;
        }
        if (keyEvent.getAction() == 0) {
            int keyCode = keyEvent.getKeyCode();
            if (keyCode == 21) {
                if (!keyEvent.isAltPressed()) {
                    zFullScroll = arrowScroll(17);
                } else {
                    zFullScroll = fullScroll(17);
                }
                z = zFullScroll;
                i = 17;
            } else if (keyCode != 22) {
                if (keyCode == 62) {
                    pageScroll(keyEvent.isShiftPressed() ? 17 : 66);
                }
                i = 0;
            } else {
                if (!keyEvent.isAltPressed()) {
                    zFullScroll2 = arrowScroll(66);
                } else {
                    zFullScroll2 = fullScroll(66);
                }
                z = zFullScroll2;
            }
            if (z) {
                playSoundEffect(SoundEffectConstants.getContantForFocusDirection(i));
            }
        }
        return z;
    }

    private boolean inChild(int i, int i2) {
        if (getChildCount() > 0) {
            int i3 = this.mScrollX;
            View childAt = getChildAt(0);
            if (i2 >= childAt.getTop() && i2 < childAt.getBottom() && i >= childAt.getLeft() - i3 && i < childAt.getRight() - i3) {
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

    /* JADX WARN: Removed duplicated region for block: B:38:0x009f  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if ((action == 2 && this.mIsBeingDragged) || super.onInterceptTouchEvent(motionEvent)) {
            return true;
        }
        int i = action & 255;
        if (i == 0) {
            int x = (int) motionEvent.getX();
            if (!inChild(x, (int) motionEvent.getY())) {
                this.mIsBeingDragged = false;
                recycleVelocityTracker();
            } else {
                this.mLastMotionX = x;
                this.mActivePointerId = motionEvent.getPointerId(0);
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(motionEvent);
                this.mIsBeingDragged = !(this.mScroller.isFinished() && this.mEdgeGlowLeft.isFinished() && this.mEdgeGlowRight.isFinished()) && canScroll();
                if (!this.mEdgeGlowLeft.isFinished()) {
                    this.mEdgeGlowLeft.onPullDistance(0.0f, 1.0f - (motionEvent.getY() / getHeight()));
                }
                if (!this.mEdgeGlowRight.isFinished()) {
                    this.mEdgeGlowRight.onPullDistance(0.0f, motionEvent.getY() / getHeight());
                }
            }
        } else if (i == 1) {
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, getScrollRange(), 0, 0)) {
                postInvalidateOnAnimation();
            }
        } else if (i == 2) {
            int i2 = this.mActivePointerId;
            if (i2 != -1) {
                int iFindPointerIndex = motionEvent.findPointerIndex(i2);
                if (iFindPointerIndex == -1) {
                    Log.e(TAG, "Invalid pointerId=" + i2 + " in onInterceptTouchEvent");
                } else {
                    int x2 = (int) motionEvent.getX(iFindPointerIndex);
                    if (Math.abs(x2 - this.mLastMotionX) > this.mTouchSlop) {
                        this.mIsBeingDragged = true;
                        this.mLastMotionX = x2;
                        initVelocityTrackerIfNotExists();
                        this.mVelocityTracker.addMovement(motionEvent);
                        if (this.mParent != null) {
                            this.mParent.requestDisallowInterceptTouchEvent(true);
                        }
                    }
                }
            }
        } else if (i != 3) {
            if (i == 5) {
                int actionIndex = motionEvent.getActionIndex();
                this.mLastMotionX = (int) motionEvent.getX(actionIndex);
                this.mActivePointerId = motionEvent.getPointerId(actionIndex);
            } else if (i == 6) {
                onSecondaryPointerUp(motionEvent);
                int iFindPointerIndex2 = motionEvent.findPointerIndex(this.mActivePointerId);
                if (iFindPointerIndex2 < 0) {
                    return true;
                }
                this.mLastMotionX = (int) motionEvent.getX(iFindPointerIndex2);
            }
        }
        return this.mIsBeingDragged;
    }

    public boolean isLockScreenMode() {
        Context context = this.mContext;
        Context context2 = this.mContext;
        return ((KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE)).inKeyguardRestrictedInputMode();
    }

    public void semSetHoverScrollMode(boolean z) {
        this.mHoverScrollEnable = z;
        this.mHoverScrollStateChanged = true;
    }

    public void setHoverScrollSpeed(int i) {
        this.HOVERSCROLL_SPEED = i;
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
        int childCount = getChildCount();
        int scrollRange = getScrollRange();
        if (this.mHoverAreaWidth <= 0) {
            this.mHoverAreaWidth = (int) (TypedValue.applyDimension(1, 25.0f, this.mContext.getResources().getDisplayMetrics()) + 0.5f);
        }
        int width = childCount != 0 ? getWidth() - this.mPaddingBottom : 0;
        boolean z = motionEvent.getToolType(0) == 2;
        if (this.mHoverHandler == null) {
            this.mHoverHandler = new HoverScrollHandler(this);
        }
        int i = this.mHoverAreaWidth;
        if ((x > i && x < width - i) || scrollRange == 0 || ((x >= 0 && x <= i && this.mScrollX <= 0 && this.mIsHoverOverscrolled) || ((x >= width - this.mHoverAreaWidth && x <= width && this.mScrollX >= scrollRange && this.mIsHoverOverscrolled) || ((z && motionEvent.getButtonState() == 32) || !z || isLockScreenMode())))) {
            if (this.mHoverHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
                showPointerIcon(motionEvent, 20001);
            }
            int i2 = this.mHoverAreaWidth;
            if (x > i2 && x < width - i2) {
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
        if (action == 7) {
            resetTimeout();
        }
        if (action != 7) {
            if (action == 9) {
                this.mHoverAreaEnter = true;
                if (x >= 0 && x <= this.mHoverAreaWidth) {
                    if (!this.mHoverHandler.hasMessages(1)) {
                        this.mHoverRecognitionStartTime = System.currentTimeMillis();
                        showPointerIcon(motionEvent, 20017);
                        this.mHoverScrollDirection = 1;
                        this.mHoverHandler.sendEmptyMessage(1);
                    }
                } else if (x >= width - this.mHoverAreaWidth && x <= width && !this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    showPointerIcon(motionEvent, 20013);
                    this.mHoverScrollDirection = 2;
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
            if (x >= 0 && x <= this.mHoverAreaWidth) {
                if (!this.mHoverHandler.hasMessages(1)) {
                    this.mHoverRecognitionStartTime = System.currentTimeMillis();
                    if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 2) {
                        showPointerIcon(motionEvent, 20017);
                    }
                    this.mHoverScrollDirection = 1;
                    this.mHoverHandler.sendEmptyMessage(1);
                }
            } else if (x >= width - this.mHoverAreaWidth && x <= width && !this.mHoverHandler.hasMessages(1)) {
                this.mHoverRecognitionStartTime = System.currentTimeMillis();
                if (!this.mIsHoverOverscrolled || this.mHoverScrollDirection == 1) {
                    showPointerIcon(motionEvent, 20013);
                }
                this.mHoverScrollDirection = 2;
                this.mHoverHandler.sendEmptyMessage(1);
            }
        }
        return true;
    }

    private void resetTimeout() {
        HoverScrollHandler hoverScrollHandler = this.mHoverHandler;
        if (hoverScrollHandler != null) {
            if (hoverScrollHandler.hasMessages(4)) {
                this.mHoverHandler.removeMessages(4);
            }
            this.mHoverHandler.sendEmptyMessageDelayed(4, 100L);
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        ViewParent parent;
        initVelocityTrackerIfNotExists();
        this.mVelocityTracker.addMovement(motionEvent);
        int action = motionEvent.getAction() & 255;
        int iRound = 0;
        if (action != 0) {
            if (action != 1) {
                if (action == 2) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                    if (iFindPointerIndex == -1) {
                        this.mActivePointerId = motionEvent.getPointerId(0);
                        Log.e(TAG, "Invalid pointerId=" + this.mActivePointerId + " in onTouchEvent");
                    } else {
                        int x = (int) motionEvent.getX(iFindPointerIndex);
                        int i = this.mLastMotionX - x;
                        if (!this.mIsBeingDragged && Math.abs(i) > this.mTouchSlop) {
                            ViewParent parent2 = getParent();
                            if (parent2 != null) {
                                parent2.requestDisallowInterceptTouchEvent(true);
                            }
                            this.mIsBeingDragged = true;
                            i = i > 0 ? i - this.mTouchSlop : i + this.mTouchSlop;
                        }
                        if (this.mIsBeingDragged) {
                            this.mLastMotionX = x;
                            int i2 = this.mScrollX;
                            int scrollRange = getScrollRange();
                            int overScrollMode = getOverScrollMode();
                            boolean z = overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0);
                            float y = motionEvent.getY(iFindPointerIndex) / getHeight();
                            if (z) {
                                if (i < 0 && this.mEdgeGlowRight.getDistance() != 0.0f) {
                                    iRound = Math.round(getWidth() * this.mEdgeGlowRight.onPullDistance(i / getWidth(), y));
                                } else if (i > 0 && this.mEdgeGlowLeft.getDistance() != 0.0f) {
                                    iRound = Math.round((-getWidth()) * this.mEdgeGlowLeft.onPullDistance((-i) / getWidth(), 1.0f - y));
                                }
                                i -= iRound;
                            }
                            int i3 = i;
                            overScrollBy(i3, 0, this.mScrollX, 0, scrollRange, 0, this.mOverscrollDistance, 0, true);
                            if (z) {
                                float f = i3;
                                if (f != 0.0f) {
                                    int i4 = i2 + i3;
                                    if (i4 < 0) {
                                        this.mEdgeGlowLeft.onPullDistance((-i3) / getWidth(), 1.0f - y);
                                        if (!this.mEdgeGlowRight.isFinished()) {
                                            this.mEdgeGlowRight.onRelease();
                                        }
                                    } else if (i4 > scrollRange) {
                                        this.mEdgeGlowRight.onPullDistance(f / getWidth(), y);
                                        if (!this.mEdgeGlowLeft.isFinished()) {
                                            this.mEdgeGlowLeft.onRelease();
                                        }
                                    }
                                    if (shouldDisplayEdgeEffects() && (!this.mEdgeGlowLeft.isFinished() || !this.mEdgeGlowRight.isFinished())) {
                                        postInvalidateOnAnimation();
                                    }
                                }
                            }
                        }
                    }
                } else if (action != 3) {
                    if (action == 6) {
                        onSecondaryPointerUp(motionEvent);
                    }
                } else if (this.mIsBeingDragged && getChildCount() > 0) {
                    if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, getScrollRange(), 0, 0)) {
                        postInvalidateOnAnimation();
                    }
                    this.mActivePointerId = -1;
                    this.mIsBeingDragged = false;
                    recycleVelocityTracker();
                    if (shouldDisplayEdgeEffects()) {
                        this.mEdgeGlowLeft.onRelease();
                        this.mEdgeGlowRight.onRelease();
                    }
                }
            } else if (this.mIsBeingDragged) {
                VelocityTracker velocityTracker = this.mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
                int xVelocity = (int) velocityTracker.getXVelocity(this.mActivePointerId);
                if (getChildCount() > 0) {
                    if (Math.abs(xVelocity) > this.mMinimumVelocity) {
                        fling(-xVelocity);
                    } else if (this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, getScrollRange(), 0, 0)) {
                        postInvalidateOnAnimation();
                    }
                }
                this.mActivePointerId = -1;
                this.mIsBeingDragged = false;
                recycleVelocityTracker();
                if (shouldDisplayEdgeEffects()) {
                    this.mEdgeGlowLeft.onRelease();
                    this.mEdgeGlowRight.onRelease();
                }
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
            }
            this.mLastMotionX = (int) motionEvent.getX();
            this.mActivePointerId = motionEvent.getPointerId(0);
        }
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int action = (motionEvent.getAction() & 65280) >> 8;
        if (motionEvent.getPointerId(action) == this.mActivePointerId) {
            int i = action == 0 ? 1 : 0;
            this.mLastMotionX = (int) motionEvent.getX(i);
            this.mActivePointerId = motionEvent.getPointerId(i);
            VelocityTracker velocityTracker = this.mVelocityTracker;
            if (velocityTracker != null) {
                velocityTracker.clear();
            }
        }
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        float axisValue;
        boolean z;
        if (motionEvent.getAction() == 8 && !this.mIsBeingDragged) {
            if (motionEvent.isFromSource(2)) {
                if ((motionEvent.getMetaState() & 1) != 0) {
                    axisValue = -motionEvent.getAxisValue(9);
                } else {
                    axisValue = motionEvent.getAxisValue(10);
                }
            } else {
                axisValue = motionEvent.isFromSource(4194304) ? motionEvent.getAxisValue(26) : 0.0f;
            }
            ViewRootImpl viewRootImpl = getViewRootImpl();
            if (viewRootImpl != null && viewRootImpl.isDesktopMode() && motionEvent.getAxisValue(9) != 0.0f) {
                axisValue = -motionEvent.getAxisValue(9);
            }
            int iRound = Math.round(axisValue * this.mHorizontalScrollFactor);
            if (iRound != 0) {
                int scrollRange = getScrollRange();
                int i = this.mScrollX;
                int i2 = iRound + i;
                int overScrollMode = getOverScrollMode();
                boolean z2 = false;
                boolean z3 = !motionEvent.isFromSource(8194) && (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0));
                if (i2 < 0) {
                    if (z3) {
                        this.mEdgeGlowLeft.onPullDistance((-i2) / getWidth(), 0.5f);
                        this.mEdgeGlowLeft.onRelease();
                        invalidate();
                        z = true;
                    } else {
                        z = false;
                    }
                    scrollRange = 0;
                    z2 = z;
                } else if (i2 <= scrollRange) {
                    scrollRange = i2;
                } else if (z3) {
                    this.mEdgeGlowRight.onPullDistance((i2 - scrollRange) / getWidth(), 0.5f);
                    this.mEdgeGlowRight.onRelease();
                    invalidate();
                    z2 = true;
                }
                if (scrollRange != i) {
                    super.scrollTo(scrollRange, this.mScrollY);
                    return true;
                }
                if (z2) {
                    return true;
                }
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    @Override // android.view.View
    protected void onOverScrolled(int i, int i2, boolean z, boolean z2) {
        if (!this.mScroller.isFinished()) {
            int i3 = this.mScrollX;
            int i4 = this.mScrollY;
            this.mScrollX = i;
            this.mScrollY = i2;
            invalidateParentIfNeeded();
            onScrollChanged(this.mScrollX, this.mScrollY, i3, i4);
            if (z) {
                this.mScroller.springBack(this.mScrollX, this.mScrollY, 0, getScrollRange(), 0, 0);
            }
        } else {
            super.scrollTo(i, i2);
        }
        awakenScrollBars();
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (i != 4096) {
            if (i == 8192 || i == 16908345) {
                if (!isEnabled()) {
                    return false;
                }
                int iMax = Math.max(0, this.mScrollX - ((getWidth() - this.mPaddingLeft) - this.mPaddingRight));
                if (iMax == this.mScrollX) {
                    return false;
                }
                smoothScrollTo(iMax, 0);
                return true;
            }
            if (i != 16908347) {
                return false;
            }
        }
        if (!isEnabled()) {
            return false;
        }
        int iMin = Math.min(this.mScrollX + ((getWidth() - this.mPaddingLeft) - this.mPaddingRight), getScrollRange());
        if (iMin == this.mScrollX) {
            return false;
        }
        smoothScrollTo(iMin, 0);
        return true;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return HorizontalScrollView.class.getName();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        int scrollRange = getScrollRange();
        if (scrollRange > 0) {
            accessibilityNodeInfo.setScrollable(true);
            if (isEnabled() && this.mScrollX > 0) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_LEFT);
            }
            if (!isEnabled() || this.mScrollX >= scrollRange) {
                return;
            }
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_RIGHT);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setScrollable(getScrollRange() > 0);
        accessibilityEvent.setMaxScrollX(getScrollRange());
        accessibilityEvent.setMaxScrollY(this.mScrollY);
    }

    private int getScrollRange() {
        if (getChildCount() > 0) {
            return Math.max(0, getChildAt(0).getWidth() - ((getWidth() - this.mPaddingLeft) - this.mPaddingRight));
        }
        return 0;
    }

    private View findFocusableViewInMyBounds(boolean z, int i, View view) {
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength() / 2;
        int i2 = i + horizontalFadingEdgeLength;
        int width = (i + getWidth()) - horizontalFadingEdgeLength;
        return (view == null || view.getLeft() >= width || view.getRight() <= i2) ? findFocusableViewInBounds(z, i2, width) : view;
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private View findFocusableViewInBounds(boolean z, int i, int i2) {
        ArrayList<View> focusables = getFocusables(2);
        int size = focusables.size();
        View view = null;
        boolean z2 = false;
        for (int i3 = 0; i3 < size; i3++) {
            View view2 = focusables.get(i3);
            int left = view2.getLeft();
            int right = view2.getRight();
            if (i < right && left < i2) {
                boolean z3 = i < left && right < i2;
                if (view == null) {
                    view = view2;
                    z2 = z3;
                } else {
                    boolean z4 = (z && left < view.getLeft()) || (!z && right > view.getRight());
                    if (z2) {
                        if (z3 && z4) {
                            view = view2;
                        }
                    } else if (z3) {
                        view = view2;
                        z2 = true;
                    } else if (z4) {
                    }
                }
            }
        }
        return view;
    }

    public boolean pageScroll(int i) {
        boolean z = i == 66;
        int width = getWidth();
        if (z) {
            this.mTempRect.left = getScrollX() + width;
            if (getChildCount() > 0) {
                View childAt = getChildAt(0);
                if (this.mTempRect.left + width > childAt.getRight()) {
                    this.mTempRect.left = childAt.getRight() - width;
                }
            }
        } else {
            this.mTempRect.left = getScrollX() - width;
            if (this.mTempRect.left < 0) {
                this.mTempRect.left = 0;
            }
        }
        Rect rect = this.mTempRect;
        rect.right = rect.left + width;
        return scrollAndFocus(i, this.mTempRect.left, this.mTempRect.right);
    }

    public boolean fullScroll(int i) {
        boolean z = i == 66;
        int width = getWidth();
        this.mTempRect.left = 0;
        this.mTempRect.right = width;
        if (z && getChildCount() > 0) {
            this.mTempRect.right = getChildAt(0).getRight();
            Rect rect = this.mTempRect;
            rect.left = rect.right - width;
        }
        return scrollAndFocus(i, this.mTempRect.left, this.mTempRect.right);
    }

    private boolean scrollAndFocus(int i, int i2, int i3) throws Resources.NotFoundException {
        int width = getWidth();
        int scrollX = getScrollX();
        int i4 = width + scrollX;
        boolean z = false;
        boolean z2 = i == 17;
        View viewFindFocusableViewInBounds = findFocusableViewInBounds(z2, i2, i3);
        if (viewFindFocusableViewInBounds == null) {
            viewFindFocusableViewInBounds = this;
        }
        if (i2 < scrollX || i3 > i4) {
            doScrollX(z2 ? i2 - scrollX : i3 - i4);
            z = true;
        }
        if (viewFindFocusableViewInBounds != findFocus()) {
            viewFindFocusableViewInBounds.requestFocus(i);
        }
        return z;
    }

    public boolean arrowScroll(int i) throws Resources.NotFoundException {
        int right;
        View viewFindFocus = findFocus();
        if (viewFindFocus == this) {
            viewFindFocus = null;
        }
        View viewFindNextFocus = FocusFinder.getInstance().findNextFocus(this, viewFindFocus, i);
        int maxScrollAmount = getMaxScrollAmount();
        if (viewFindNextFocus != null && isWithinDeltaOfScreen(viewFindNextFocus, maxScrollAmount)) {
            viewFindNextFocus.getDrawingRect(this.mTempRect);
            offsetDescendantRectToMyCoords(viewFindNextFocus, this.mTempRect);
            doScrollX(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            viewFindNextFocus.requestFocus(i);
        } else {
            if (i == 17 && getScrollX() < maxScrollAmount) {
                maxScrollAmount = getScrollX();
            } else if (i == 66 && getChildCount() > 0 && (right = getChildAt(0).getRight() - (getScrollX() + getWidth())) < maxScrollAmount) {
                maxScrollAmount = right;
            }
            if (maxScrollAmount == 0) {
                return false;
            }
            if (i != 66) {
                maxScrollAmount = -maxScrollAmount;
            }
            doScrollX(maxScrollAmount);
        }
        if (viewFindFocus == null || !viewFindFocus.isFocused() || !isOffScreen(viewFindFocus)) {
            return true;
        }
        int descendantFocusability = getDescendantFocusability();
        setDescendantFocusability(131072);
        requestFocus();
        setDescendantFocusability(descendantFocusability);
        return true;
    }

    private boolean isOffScreen(View view) {
        return !isWithinDeltaOfScreen(view, 0);
    }

    private boolean isWithinDeltaOfScreen(View view, int i) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        return this.mTempRect.right + i >= getScrollX() && this.mTempRect.left - i <= getScrollX() + getWidth();
    }

    private void doScrollX(int i) {
        if (i != 0) {
            if (this.mSmoothScrollingEnabled) {
                smoothScrollBy(i, 0);
            } else {
                scrollBy(i, 0);
            }
        }
    }

    public final void smoothScrollBy(int i, int i2) {
        if (getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250) {
            int iMax = Math.max(0, getChildAt(0).getWidth() - ((getWidth() - this.mPaddingRight) - this.mPaddingLeft));
            int i3 = this.mScrollX;
            this.mScroller.startScroll(i3, this.mScrollY, Math.max(0, Math.min(i + i3, iMax)) - i3, 0);
            postInvalidateOnAnimation();
        } else {
            if (!this.mScroller.isFinished()) {
                this.mScroller.abortAnimation();
            }
            scrollBy(i, i2);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public final void smoothScrollTo(int i, int i2) {
        smoothScrollBy(i - this.mScrollX, i2 - this.mScrollY);
    }

    @Override // android.view.View
    protected int computeHorizontalScrollRange() {
        int childCount = getChildCount();
        int width = (getWidth() - this.mPaddingLeft) - this.mPaddingRight;
        if (childCount == 0) {
            return width;
        }
        int right = getChildAt(0).getRight();
        int i = this.mScrollX;
        int iMax = Math.max(0, right - width);
        return i < 0 ? right - i : i > iMax ? right + (i - iMax) : right;
    }

    @Override // android.view.View
    protected int computeHorizontalScrollOffset() {
        return Math.max(0, super.computeHorizontalScrollOffset());
    }

    @Override // android.view.ViewGroup
    protected void measureChild(View view, int i, int i2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i) - (this.mPaddingLeft + this.mPaddingRight)), 0), getChildMeasureSpec(i2, this.mPaddingTop + this.mPaddingBottom, layoutParams.height));
    }

    @Override // android.view.ViewGroup
    protected void measureChildWithMargins(View view, int i, int i2, int i3, int i4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        view.measure(View.MeasureSpec.makeSafeMeasureSpec(Math.max(0, View.MeasureSpec.getSize(i) - ((((this.mPaddingLeft + this.mPaddingRight) + marginLayoutParams.leftMargin) + marginLayoutParams.rightMargin) + i2)), 0), getChildMeasureSpec(i3, this.mPaddingTop + this.mPaddingBottom + marginLayoutParams.topMargin + marginLayoutParams.bottomMargin + i4, marginLayoutParams.height));
    }

    @Override // android.view.View
    public void computeScroll() {
        HorizontalScrollView horizontalScrollView;
        if (this.mScroller.computeScrollOffset()) {
            int i = this.mScrollX;
            int i2 = this.mScrollY;
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int iConsumeFlingInStretch = consumeFlingInStretch(currX - i);
            if (iConsumeFlingInStretch == 0 && i2 == currY) {
                horizontalScrollView = this;
            } else {
                int scrollRange = getScrollRange();
                int overScrollMode = getOverScrollMode();
                boolean z = true;
                if (overScrollMode != 0 && (overScrollMode != 1 || scrollRange <= 0)) {
                    z = false;
                }
                boolean z2 = z;
                horizontalScrollView = this;
                horizontalScrollView.overScrollBy(iConsumeFlingInStretch, currY - i2, i, i2, scrollRange, 0, this.mOverflingDistance, 0, false);
                horizontalScrollView.onScrollChanged(horizontalScrollView.mScrollX, horizontalScrollView.mScrollY, i, i2);
                if (z2 && iConsumeFlingInStretch != 0) {
                    if (currX < 0 && i >= 0) {
                        horizontalScrollView.mEdgeGlowLeft.onAbsorb((int) horizontalScrollView.mScroller.getCurrVelocity());
                    } else if (currX > scrollRange && i <= scrollRange) {
                        horizontalScrollView.mEdgeGlowRight.onAbsorb((int) horizontalScrollView.mScroller.getCurrVelocity());
                    }
                }
            }
            if (!horizontalScrollView.awakenScrollBars()) {
                horizontalScrollView.postInvalidateOnAnimation();
            }
            if (Flags.viewVelocityApi()) {
                horizontalScrollView.setFrameContentVelocity(Math.abs(horizontalScrollView.mScroller.getCurrVelocity()));
            }
        }
    }

    private int consumeFlingInStretch(int i) {
        EdgeEffect edgeEffect;
        EdgeEffect edgeEffect2;
        int scrollX = getScrollX();
        if (scrollX < 0 || scrollX > getScrollRange()) {
            return i;
        }
        if (i > 0 && (edgeEffect2 = this.mEdgeGlowLeft) != null && edgeEffect2.getDistance() != 0.0f) {
            int iRound = Math.round(((-r1) / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowLeft.onPullDistance(((-i) * FLING_DESTRETCH_FACTOR) / getWidth(), 0.5f));
            if (iRound != i) {
                this.mEdgeGlowLeft.finish();
            }
            return i - iRound;
        }
        if (i >= 0 || (edgeEffect = this.mEdgeGlowRight) == null || edgeEffect.getDistance() == 0.0f) {
            return i;
        }
        float width = getWidth();
        int iRound2 = Math.round((width / FLING_DESTRETCH_FACTOR) * this.mEdgeGlowRight.onPullDistance((i * FLING_DESTRETCH_FACTOR) / width, 0.5f));
        if (iRound2 != i) {
            this.mEdgeGlowRight.finish();
        }
        return i - iRound2;
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(view, this.mTempRect);
        int iComputeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (iComputeScrollDeltaToGetChildRectOnScreen != 0) {
            scrollBy(iComputeScrollDeltaToGetChildRectOnScreen, 0);
        }
    }

    private boolean scrollToChildRect(Rect rect, boolean z) {
        int iComputeScrollDeltaToGetChildRectOnScreen = computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean z2 = iComputeScrollDeltaToGetChildRectOnScreen != 0;
        if (z2) {
            if (z) {
                scrollBy(iComputeScrollDeltaToGetChildRectOnScreen, 0);
                return z2;
            }
            smoothScrollBy(iComputeScrollDeltaToGetChildRectOnScreen, 0);
        }
        return z2;
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int i;
        int i2;
        if (getChildCount() == 0) {
            return 0;
        }
        int width = getWidth();
        int scrollX = getScrollX();
        int i3 = scrollX + width;
        int horizontalFadingEdgeLength = getHorizontalFadingEdgeLength();
        if (rect.left > 0) {
            scrollX += horizontalFadingEdgeLength;
        }
        if (rect.right < getChildAt(0).getWidth()) {
            i3 -= horizontalFadingEdgeLength;
        }
        if (rect.right > i3 && rect.left > scrollX) {
            if (rect.width() > width) {
                i2 = rect.left - scrollX;
            } else {
                i2 = rect.right - i3;
            }
            return Math.min(i2, getChildAt(0).getRight() - i3);
        }
        if (rect.left >= scrollX || rect.right >= i3) {
            return 0;
        }
        if (rect.width() > width) {
            i = 0 - (i3 - rect.right);
        } else {
            i = 0 - (scrollX - rect.left);
        }
        return Math.max(i, -getScrollX());
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view, View view2) {
        if (view2 != null && view2.getRevealOnFocusHint()) {
            if (!this.mIsLayoutDirty) {
                scrollToChild(view2);
            } else {
                this.mChildToScrollTo = view2;
            }
        }
        super.requestChildFocus(view, view2);
    }

    @Override // android.view.ViewGroup
    protected boolean onRequestFocusInDescendants(int i, Rect rect) {
        View viewFindNextFocusFromRect;
        if (i == 2) {
            i = 66;
        } else if (i == 1) {
            i = 17;
        }
        if (rect == null) {
            viewFindNextFocusFromRect = FocusFinder.getInstance().findNextFocus(this, null, i);
        } else {
            viewFindNextFocusFromRect = FocusFinder.getInstance().findNextFocusFromRect(this, rect, i);
        }
        if (viewFindNextFocusFromRect == null || isOffScreen(viewFindNextFocusFromRect)) {
            return false;
        }
        return viewFindNextFocusFromRect.requestFocus(i, rect);
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

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int measuredWidth;
        int i5;
        int i6;
        if (getChildCount() > 0) {
            measuredWidth = getChildAt(0).getMeasuredWidth();
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getChildAt(0).getLayoutParams();
            i5 = layoutParams.leftMargin + layoutParams.rightMargin;
        } else {
            measuredWidth = 0;
            i5 = 0;
        }
        int i7 = i3 - i;
        layoutChildren(i, i2, i3, i4, measuredWidth > ((i7 - getPaddingLeftWithForeground()) - getPaddingRightWithForeground()) - i5);
        this.mIsLayoutDirty = false;
        View view = this.mChildToScrollTo;
        if (view != null && isViewDescendantOf(view, this)) {
            scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!isLaidOut()) {
            int iMax = Math.max(0, measuredWidth - ((i7 - this.mPaddingLeft) - this.mPaddingRight));
            if (this.mSavedState != null) {
                if (isLayoutRtl()) {
                    i6 = iMax - this.mSavedState.scrollOffsetFromStart;
                } else {
                    i6 = this.mSavedState.scrollOffsetFromStart;
                }
                this.mScrollX = i6;
                this.mSavedState = null;
            } else if (isLayoutRtl()) {
                this.mScrollX = iMax - this.mScrollX;
            }
            if (this.mScrollX > iMax) {
                this.mScrollX = iMax;
            } else if (this.mScrollX < 0) {
                this.mScrollX = 0;
            }
        }
        scrollTo(this.mScrollX, this.mScrollY);
    }

    @Override // android.view.View
    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        View viewFindFocus = findFocus();
        if (viewFindFocus == null || this == viewFindFocus || !isWithinDeltaOfScreen(viewFindFocus, this.mRight - this.mLeft)) {
            return;
        }
        viewFindFocus.getDrawingRect(this.mTempRect);
        offsetDescendantRectToMyCoords(viewFindFocus, this.mTempRect);
        doScrollX(computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    }

    private static boolean isViewDescendantOf(View view, View view2) {
        if (view == view2) {
            return true;
        }
        Object parent = view.getParent();
        return (parent instanceof ViewGroup) && isViewDescendantOf((View) parent, view2);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void fling(int i) throws Resources.NotFoundException {
        if (getChildCount() > 0) {
            int width = (getWidth() - this.mPaddingRight) - this.mPaddingLeft;
            int iMax = Math.max(0, (getChildAt(0).getRight() - this.mPaddingLeft) - width);
            if (this.mScrollX == 0 && !this.mEdgeGlowLeft.isFinished()) {
                int i2 = -i;
                if (shouldAbsorb(this.mEdgeGlowLeft, i2)) {
                    this.mEdgeGlowLeft.onAbsorb(i2);
                }
            } else if (this.mScrollX == iMax && !this.mEdgeGlowRight.isFinished() && shouldAbsorb(this.mEdgeGlowRight, i)) {
                this.mEdgeGlowRight.onAbsorb(i);
            } else {
                this.mScroller.fling(this.mScrollX, this.mScrollY, i, 0, 0, iMax, 0, 0, width / 2, 0);
                if (Flags.viewVelocityApi()) {
                    setFrameContentVelocity(Math.abs(this.mScroller.getCurrVelocity()));
                }
                boolean z = i > 0;
                View viewFindFocus = findFocus();
                View viewFindFocusableViewInMyBounds = findFocusableViewInMyBounds(z, this.mScroller.getFinalX(), viewFindFocus);
                if (viewFindFocusableViewInMyBounds == null) {
                    viewFindFocusableViewInMyBounds = this;
                }
                if (viewFindFocusableViewInMyBounds != viewFindFocus) {
                    viewFindFocusableViewInMyBounds.requestFocus(z ? 66 : 17);
                }
            }
            postInvalidateOnAnimation();
        }
    }

    private boolean shouldAbsorb(EdgeEffect edgeEffect, int i) {
        if (i > 0) {
            return true;
        }
        return ((float) this.mScroller.getSplineFlingDistance(-i)) < edgeEffect.getDistance() * ((float) getWidth());
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        if (getChildCount() > 0) {
            View childAt = getChildAt(0);
            int iClamp = clamp(i, (getWidth() - this.mPaddingRight) - this.mPaddingLeft, childAt.getWidth());
            int iClamp2 = clamp(i2, (getHeight() - this.mPaddingBottom) - this.mPaddingTop, childAt.getHeight());
            if (iClamp == this.mScrollX && iClamp2 == this.mScrollY) {
                return;
            }
            super.scrollTo(iClamp, iClamp2);
        }
    }

    private boolean shouldDisplayEdgeEffects() {
        return getOverScrollMode() != 2;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (shouldDisplayEdgeEffects()) {
            int i = this.mScrollX;
            if (!this.mEdgeGlowLeft.isFinished()) {
                int iSave = canvas.save();
                int height = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
                canvas.rotate(270.0f);
                canvas.translate((-height) - this.mPaddingTop, Math.min(0, i));
                this.mEdgeGlowLeft.setSize(height, getWidth());
                if (this.mEdgeGlowLeft.draw(canvas)) {
                    postInvalidateOnAnimation();
                }
                canvas.restoreToCount(iSave);
            }
            if (this.mEdgeGlowRight.isFinished()) {
                return;
            }
            int iSave2 = canvas.save();
            int width = getWidth();
            int height2 = (getHeight() - this.mPaddingTop) - this.mPaddingBottom;
            canvas.rotate(90.0f);
            canvas.translate(this.mPaddingTop, -(Math.max(getScrollRange(), i) + width));
            this.mEdgeGlowRight.setSize(height2, width);
            if (this.mEdgeGlowRight.draw(canvas)) {
                postInvalidateOnAnimation();
            }
            canvas.restoreToCount(iSave2);
        }
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
        boolean zIsLayoutRtl = isLayoutRtl();
        int i = this.mScrollX;
        if (zIsLayoutRtl) {
            i = -i;
        }
        savedState.scrollOffsetFromStart = i;
        return savedState;
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) throws Resources.NotFoundException, IOException {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("layout:fillViewPort", this.mFillViewport);
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.HorizontalScrollView.SavedState.1
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
        public int scrollOffsetFromStart;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public SavedState(Parcel parcel) {
            super(parcel);
            this.scrollOffsetFromStart = parcel.readInt();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.scrollOffsetFromStart);
        }

        public String toString() {
            return "HorizontalScrollView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " scrollPosition=" + this.scrollOffsetFromStart + "}";
        }
    }

    private static class HoverScrollHandler extends Handler {
        private final WeakReference<HorizontalScrollView> mScrollView;

        HoverScrollHandler(HorizontalScrollView horizontalScrollView) {
            this.mScrollView = new WeakReference<>(horizontalScrollView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            HorizontalScrollView horizontalScrollView = this.mScrollView.get();
            if (horizontalScrollView != null) {
                horizontalScrollView.handleMessage(message);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleMessage(Message message) {
        HoverScrollHandler hoverScrollHandler;
        int i = message.what;
        if (i != 1) {
            if (i == 4 && (hoverScrollHandler = this.mHoverHandler) != null && hoverScrollHandler.hasMessages(1)) {
                this.mHoverHandler.removeMessages(1);
                return;
            }
            return;
        }
        int scrollRange = getScrollRange();
        long jCurrentTimeMillis = System.currentTimeMillis();
        this.mHoverRecognitionCurrentTime = jCurrentTimeMillis;
        long j = (jCurrentTimeMillis - this.mHoverRecognitionStartTime) / 1000;
        this.mHoverRecognitionDurationTime = j;
        if (jCurrentTimeMillis - this.mHoverScrollStartTime < this.mHoverScrollTimeInterval) {
            return;
        }
        if (j == 3) {
            this.mHoverScrollSpeed = this.HOVERSCROLL_SPEED + 2;
        } else if (j == 4) {
            this.mHoverScrollSpeed = this.HOVERSCROLL_SPEED + 4;
        } else if (j >= 5) {
            this.mHoverScrollSpeed = this.HOVERSCROLL_SPEED + 6;
        } else {
            this.mHoverScrollSpeed = this.HOVERSCROLL_SPEED;
        }
        int i2 = this.mHoverScrollDirection == 1 ? -this.mHoverScrollSpeed : this.mHoverScrollSpeed;
        boolean z = false;
        if ((i2 < 0 && this.mScrollX > 0) || (i2 > 0 && this.mScrollX < scrollRange)) {
            scrollBy(i2, 0);
            this.mHoverHandler.sendEmptyMessageDelayed(1, this.HOVERSCROLL_DELAY);
            return;
        }
        int overScrollMode = getOverScrollMode();
        if (overScrollMode == 0 || (overScrollMode == 1 && scrollRange > 0)) {
            z = true;
        }
        if (z && !this.mIsHoverOverscrolled) {
            if (this.mEdgeGlowLeft != null) {
                int i3 = this.mHoverScrollDirection;
                if (i3 == 1) {
                    this.mEdgeGlowLeft.setSize((getHeight() - this.mPaddingTop) - this.mPaddingBottom, getWidth());
                    this.mEdgeGlowLeft.onAbsorb(10000);
                    if (!this.mEdgeGlowRight.isFinished()) {
                        this.mEdgeGlowRight.onRelease();
                    }
                } else if (i3 == 2) {
                    this.mEdgeGlowRight.setSize((getHeight() - this.mPaddingTop) - this.mPaddingBottom, getWidth());
                    this.mEdgeGlowRight.onAbsorb(10000);
                    if (!this.mEdgeGlowLeft.isFinished()) {
                        this.mEdgeGlowLeft.onRelease();
                    }
                }
            }
            EdgeEffect edgeEffect = this.mEdgeGlowLeft;
            if (edgeEffect != null && (!edgeEffect.isFinished() || !this.mEdgeGlowRight.isFinished())) {
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
        if (i == 20017 || i == 20013) {
            i = semGetRotatePointerIcon(i);
        }
        semSetPointerIcon(motionEvent.getToolType(0), i == 20001 ? null : PointerIcon.getSystemIcon(this.mContext, i));
    }

    private int semGetRotatePointerIcon(int i) {
        float rotation = getRotation() + semGetParentViewRotation();
        boolean z = i == 20013;
        boolean z2 = rotation < 0.0f;
        int i2 = (((int) ((rotation + (z2 ? -45 : 45)) / 90.0f)) + (z ? 0 : z2 ? -2 : 2)) % 4;
        if (z2 && i2 != 0) {
            i2 += 4;
        }
        int iAbs = Math.abs(i2);
        if (iAbs == 0) {
            return 20013;
        }
        if (iAbs == 1) {
            return 20015;
        }
        if (iAbs != 2) {
            return iAbs != 3 ? 20001 : 20011;
        }
        return 20017;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v0, types: [android.widget.HorizontalScrollView] */
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
}
