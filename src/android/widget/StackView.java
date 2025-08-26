package android.widget;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.TableMaskFilter;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.RemotableViewMethod;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.animation.LinearInterpolator;
import android.widget.RemoteViews;
import com.android.internal.R;
import java.lang.ref.WeakReference;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class StackView extends AdapterViewAnimator {
    private static final int DEFAULT_ANIMATION_DURATION = 400;
    private static final int FRAME_PADDING = 4;
    private static final int GESTURE_NONE = 0;
    private static final int GESTURE_SLIDE_DOWN = 2;
    private static final int GESTURE_SLIDE_UP = 1;
    private static final int INVALID_POINTER = -1;
    private static final int ITEMS_SLIDE_DOWN = 1;
    private static final int ITEMS_SLIDE_UP = 0;
    private static final int MINIMUM_ANIMATION_DURATION = 50;
    private static final int MIN_TIME_BETWEEN_INTERACTION_AND_AUTOADVANCE = 5000;
    private static final long MIN_TIME_BETWEEN_SCROLLS = 100;
    private static final int NUM_ACTIVE_VIEWS = 5;
    private static final float PERSPECTIVE_SCALE_FACTOR = 0.0f;
    private static final float PERSPECTIVE_SHIFT_FACTOR_X = 0.1f;
    private static final float PERSPECTIVE_SHIFT_FACTOR_Y = 0.1f;
    private static final float SLIDE_UP_RATIO = 0.7f;
    private static final int STACK_RELAYOUT_DURATION = 100;
    private static final float SWIPE_THRESHOLD_RATIO = 0.2f;
    private static HolographicHelper sHolographicHelper;
    private final String TAG;
    private int mActivePointerId;
    private int mClickColor;
    private ImageView mClickFeedback;
    private boolean mClickFeedbackIsValid;
    private boolean mFirstLayoutHappened;
    private int mFramePadding;
    private ImageView mHighlight;
    private float mInitialX;
    private float mInitialY;
    private long mLastInteractionTime;
    private long mLastScrollTime;
    private int mMaximumVelocity;
    private float mNewPerspectiveShiftX;
    private float mNewPerspectiveShiftY;
    private float mPerspectiveShiftX;
    private float mPerspectiveShiftY;
    private int mResOutColor;
    private int mSlideAmount;
    private int mStackMode;
    private StackSlider mStackSlider;
    private int mSwipeGestureType;
    private int mSwipeThreshold;
    private final Rect mTouchRect;
    private int mTouchSlop;
    private boolean mTransitionIsSetup;
    private VelocityTracker mVelocityTracker;
    private int mYVelocity;
    private final Rect stackInvalidateRect;

    @Override // android.widget.AdapterViewAnimator
    void applyTransformForChildAtIndex(View view, int i) {
    }

    public StackView(Context context) {
        this(context, null);
    }

    public StackView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843838);
    }

    public StackView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public StackView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.TAG = "StackView";
        this.mTouchRect = new Rect();
        this.mYVelocity = 0;
        this.mSwipeGestureType = 0;
        this.mTransitionIsSetup = false;
        this.mClickFeedbackIsValid = false;
        this.mFirstLayoutHappened = false;
        this.mLastInteractionTime = 0L;
        this.stackInvalidateRect = new Rect();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.StackView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.StackView, attributeSet, typedArrayObtainStyledAttributes, i, i2);
        this.mResOutColor = typedArrayObtainStyledAttributes.getColor(1, 0);
        this.mClickColor = typedArrayObtainStyledAttributes.getColor(0, 0);
        typedArrayObtainStyledAttributes.recycle();
        initStackView();
    }

    private void initStackView() {
        configureViewAnimator(5, 1);
        setStaticTransformationsEnabled(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mActivePointerId = -1;
        ImageView imageView = new ImageView(getContext());
        this.mHighlight = imageView;
        imageView.setLayoutParams(new LayoutParams(this.mHighlight));
        addViewInLayout(this.mHighlight, -1, new LayoutParams(this.mHighlight));
        ImageView imageView2 = new ImageView(getContext());
        this.mClickFeedback = imageView2;
        imageView2.setLayoutParams(new LayoutParams(this.mClickFeedback));
        addViewInLayout(this.mClickFeedback, -1, new LayoutParams(this.mClickFeedback));
        this.mClickFeedback.setVisibility(4);
        this.mStackSlider = new StackSlider();
        if (sHolographicHelper == null) {
            sHolographicHelper = new HolographicHelper(this.mContext);
        }
        setClipChildren(false);
        setClipToPadding(false);
        this.mStackMode = 1;
        this.mWhichChild = -1;
        this.mFramePadding = (int) Math.ceil(this.mContext.getResources().getDisplayMetrics().density * 4.0f);
    }

    @Override // android.widget.AdapterViewAnimator
    void transformViewForTransition(int i, int i2, final View view, boolean z) {
        if (!z) {
            ((StackFrame) view).cancelSliderAnimator();
            view.setRotationX(0.0f);
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            layoutParams.setVerticalOffset(0);
            layoutParams.setHorizontalOffset(0);
        }
        if (i == -1 && i2 == getNumActiveViews() - 1) {
            transformViewAtIndex(i2, view, false);
            view.setVisibility(0);
            view.setAlpha(1.0f);
        } else if (i == 0 && i2 == 1) {
            StackFrame stackFrame = (StackFrame) view;
            stackFrame.cancelSliderAnimator();
            view.setVisibility(0);
            int iRound = Math.round(this.mStackSlider.getDurationForNeutralPosition(this.mYVelocity));
            StackSlider stackSlider = new StackSlider(this.mStackSlider);
            stackSlider.setView(view);
            if (z) {
                ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(stackSlider, PropertyValuesHolder.ofFloat("XProgress", 0.0f), PropertyValuesHolder.ofFloat("YProgress", 0.0f));
                objectAnimatorOfPropertyValuesHolder.setDuration(iRound);
                objectAnimatorOfPropertyValuesHolder.setInterpolator(new LinearInterpolator());
                stackFrame.setSliderAnimator(objectAnimatorOfPropertyValuesHolder);
                objectAnimatorOfPropertyValuesHolder.start();
            } else {
                stackSlider.setYProgress(0.0f);
                stackSlider.setXProgress(0.0f);
            }
        } else if (i == 1 && i2 == 0) {
            StackFrame stackFrame2 = (StackFrame) view;
            stackFrame2.cancelSliderAnimator();
            int iRound2 = Math.round(this.mStackSlider.getDurationForOffscreenPosition(this.mYVelocity));
            StackSlider stackSlider2 = new StackSlider(this.mStackSlider);
            stackSlider2.setView(view);
            if (z) {
                ObjectAnimator objectAnimatorOfPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(stackSlider2, PropertyValuesHolder.ofFloat("XProgress", 0.0f), PropertyValuesHolder.ofFloat("YProgress", 1.0f));
                objectAnimatorOfPropertyValuesHolder2.setDuration(iRound2);
                objectAnimatorOfPropertyValuesHolder2.setInterpolator(new LinearInterpolator());
                stackFrame2.setSliderAnimator(objectAnimatorOfPropertyValuesHolder2);
                objectAnimatorOfPropertyValuesHolder2.start();
            } else {
                stackSlider2.setYProgress(1.0f);
                stackSlider2.setXProgress(0.0f);
            }
        } else if (i2 == 0) {
            view.setAlpha(0.0f);
            view.setVisibility(4);
        } else if ((i == 0 || i == 1) && i2 > 1) {
            view.setVisibility(0);
            view.setAlpha(1.0f);
            view.setRotationX(0.0f);
            LayoutParams layoutParams2 = (LayoutParams) view.getLayoutParams();
            layoutParams2.setVerticalOffset(0);
            layoutParams2.setHorizontalOffset(0);
        } else if (i == -1) {
            view.setAlpha(1.0f);
            view.setVisibility(0);
        } else if (i2 == -1) {
            if (z) {
                postDelayed(new Runnable(this) { // from class: android.widget.StackView.1
                    @Override // java.lang.Runnable
                    public void run() {
                        view.setAlpha(0.0f);
                    }
                }, MIN_TIME_BETWEEN_SCROLLS);
            } else {
                view.setAlpha(0.0f);
            }
        }
        if (i2 != -1) {
            transformViewAtIndex(i2, view, z);
        }
    }

    private void transformViewAtIndex(int i, View view, boolean z) {
        float f = this.mPerspectiveShiftY;
        float f2 = this.mPerspectiveShiftX;
        if (this.mStackMode == 1) {
            int i2 = this.mMaxNumActiveViews - i;
            i = i2 - 1;
            if (i == this.mMaxNumActiveViews - 1) {
                i = i2 - 2;
            }
        } else {
            int i3 = i - 1;
            if (i3 >= 0) {
                i = i3;
            }
        }
        float f3 = (i * 1.0f) / (this.mMaxNumActiveViews - 2);
        float f4 = 1.0f - f3;
        float f5 = 1.0f - (0.0f * f4);
        float measuredHeight = (f3 * f) + ((f5 - 1.0f) * ((getMeasuredHeight() * 0.9f) / 2.0f));
        float measuredWidth = (f4 * f2) + ((1.0f - f5) * ((getMeasuredWidth() * 0.9f) / 2.0f));
        boolean z2 = view instanceof StackFrame;
        if (z2) {
            ((StackFrame) view).cancelTransformAnimator();
        }
        if (z) {
            ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(view, PropertyValuesHolder.ofFloat("scaleX", f5), PropertyValuesHolder.ofFloat("scaleY", f5), PropertyValuesHolder.ofFloat("translationY", measuredHeight), PropertyValuesHolder.ofFloat("translationX", measuredWidth));
            objectAnimatorOfPropertyValuesHolder.setDuration(MIN_TIME_BETWEEN_SCROLLS);
            if (z2) {
                ((StackFrame) view).setTransformAnimator(objectAnimatorOfPropertyValuesHolder);
            }
            objectAnimatorOfPropertyValuesHolder.start();
            return;
        }
        view.setTranslationX(measuredWidth);
        view.setTranslationY(measuredHeight);
        view.setScaleX(f5);
        view.setScaleY(f5);
    }

    private void setupStackSlider(View view, int i) {
        this.mStackSlider.setMode(i);
        if (view != null) {
            this.mHighlight.setImageBitmap(sHolographicHelper.createResOutline(view, this.mResOutColor));
            this.mHighlight.setRotation(view.getRotation());
            this.mHighlight.setTranslationY(view.getTranslationY());
            this.mHighlight.setTranslationX(view.getTranslationX());
            this.mHighlight.bringToFront();
            view.bringToFront();
            this.mStackSlider.setView(view);
            view.setVisibility(0);
        }
    }

    @Override // android.widget.AdapterViewAnimator
    @RemotableViewMethod
    public void showNext() throws Resources.NotFoundException {
        View viewAtRelativeIndex;
        if (this.mSwipeGestureType != 0) {
            return;
        }
        if (!this.mTransitionIsSetup && (viewAtRelativeIndex = getViewAtRelativeIndex(1)) != null) {
            setupStackSlider(viewAtRelativeIndex, 0);
            this.mStackSlider.setYProgress(0.0f);
            this.mStackSlider.setXProgress(0.0f);
        }
        super.showNext();
    }

    @Override // android.widget.AdapterViewAnimator
    @RemotableViewMethod
    public void showPrevious() throws Resources.NotFoundException {
        View viewAtRelativeIndex;
        if (this.mSwipeGestureType != 0) {
            return;
        }
        if (!this.mTransitionIsSetup && (viewAtRelativeIndex = getViewAtRelativeIndex(0)) != null) {
            setupStackSlider(viewAtRelativeIndex, 0);
            this.mStackSlider.setYProgress(1.0f);
            this.mStackSlider.setXProgress(0.0f);
        }
        super.showPrevious();
    }

    @Override // android.widget.AdapterViewAnimator
    void showOnly(int i, boolean z) throws Resources.NotFoundException {
        View view;
        super.showOnly(i, z);
        for (int i2 = this.mCurrentWindowEnd; i2 >= this.mCurrentWindowStart; i2--) {
            int iModulo = modulo(i2, getWindowSize());
            if (this.mViewsMap.get(Integer.valueOf(iModulo)) != null && (view = this.mViewsMap.get(Integer.valueOf(iModulo)).view) != null) {
                view.bringToFront();
            }
        }
        ImageView imageView = this.mHighlight;
        if (imageView != null) {
            imageView.bringToFront();
        }
        this.mTransitionIsSetup = false;
        this.mClickFeedbackIsValid = false;
    }

    void updateClickFeedback() {
        if (this.mClickFeedbackIsValid) {
            return;
        }
        View viewAtRelativeIndex = getViewAtRelativeIndex(1);
        if (viewAtRelativeIndex != null) {
            this.mClickFeedback.setImageBitmap(sHolographicHelper.createClickOutline(viewAtRelativeIndex, this.mClickColor));
            this.mClickFeedback.setTranslationX(viewAtRelativeIndex.getTranslationX());
            this.mClickFeedback.setTranslationY(viewAtRelativeIndex.getTranslationY());
        }
        this.mClickFeedbackIsValid = true;
    }

    @Override // android.widget.AdapterViewAnimator
    void showTapFeedback(View view) {
        updateClickFeedback();
        this.mClickFeedback.setVisibility(0);
        this.mClickFeedback.bringToFront();
        invalidate();
    }

    @Override // android.widget.AdapterViewAnimator
    void hideTapFeedback(View view) {
        this.mClickFeedback.setVisibility(4);
        invalidate();
    }

    private void updateChildTransforms() {
        for (int i = 0; i < getNumActiveViews(); i++) {
            View viewAtRelativeIndex = getViewAtRelativeIndex(i);
            if (viewAtRelativeIndex != null) {
                transformViewAtIndex(i, viewAtRelativeIndex, false);
            }
        }
    }

    private static class StackFrame extends FrameLayout {
        WeakReference<ObjectAnimator> sliderAnimator;
        WeakReference<ObjectAnimator> transformAnimator;

        public StackFrame(Context context) {
            super(context);
        }

        void setTransformAnimator(ObjectAnimator objectAnimator) {
            this.transformAnimator = new WeakReference<>(objectAnimator);
        }

        void setSliderAnimator(ObjectAnimator objectAnimator) {
            this.sliderAnimator = new WeakReference<>(objectAnimator);
        }

        boolean cancelTransformAnimator() {
            ObjectAnimator objectAnimator;
            WeakReference<ObjectAnimator> weakReference = this.transformAnimator;
            if (weakReference == null || (objectAnimator = weakReference.get()) == null) {
                return false;
            }
            objectAnimator.cancel();
            return true;
        }

        boolean cancelSliderAnimator() {
            ObjectAnimator objectAnimator;
            WeakReference<ObjectAnimator> weakReference = this.sliderAnimator;
            if (weakReference == null || (objectAnimator = weakReference.get()) == null) {
                return false;
            }
            objectAnimator.cancel();
            return true;
        }
    }

    @Override // android.widget.AdapterViewAnimator
    FrameLayout getFrameForChild() {
        StackFrame stackFrame = new StackFrame(this.mContext);
        int i = this.mFramePadding;
        stackFrame.setPadding(i, i, i, i);
        return stackFrame;
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        canvas.getClipBounds(this.stackInvalidateRect);
        int childCount = getChildCount();
        boolean z = false;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if ((layoutParams.horizontalOffset == 0 && layoutParams.verticalOffset == 0) || childAt.getAlpha() == 0.0f || childAt.getVisibility() != 0) {
                layoutParams.resetInvalidateRect();
            }
            Rect invalidateRect = layoutParams.getInvalidateRect();
            if (!invalidateRect.isEmpty()) {
                this.stackInvalidateRect.union(invalidateRect);
                z = true;
            }
        }
        if (z) {
            canvas.save();
            canvas.clipRectUnion(this.stackInvalidateRect);
            super.dispatchDraw(canvas);
            canvas.restore();
            return;
        }
        super.dispatchDraw(canvas);
    }

    private void onLayout() {
        if (!this.mFirstLayoutHappened) {
            this.mFirstLayoutHappened = true;
            updateChildTransforms();
        }
        int iRound = Math.round(getMeasuredHeight() * SLIDE_UP_RATIO);
        if (this.mSlideAmount != iRound) {
            this.mSlideAmount = iRound;
            this.mSwipeThreshold = Math.round(iRound * 0.2f);
        }
        if (Float.compare(this.mPerspectiveShiftY, this.mNewPerspectiveShiftY) == 0 && Float.compare(this.mPerspectiveShiftX, this.mNewPerspectiveShiftX) == 0) {
            return;
        }
        this.mPerspectiveShiftY = this.mNewPerspectiveShiftY;
        this.mPerspectiveShiftX = this.mNewPerspectiveShiftX;
        updateChildTransforms();
    }

    @Override // android.view.View
    public boolean onGenericMotionEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        if ((motionEvent.getSource() & 2) != 0 && motionEvent.getAction() == 8) {
            float axisValue = motionEvent.getAxisValue(9);
            if (axisValue < 0.0f) {
                pacedScroll(false);
                return true;
            }
            if (axisValue > 0.0f) {
                pacedScroll(true);
                return true;
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    private void pacedScroll(boolean z) throws Resources.NotFoundException {
        if (System.currentTimeMillis() - this.mLastScrollTime > MIN_TIME_BETWEEN_SCROLLS) {
            if (z) {
                showPrevious();
            } else {
                showNext();
            }
            this.mLastScrollTime = System.currentTimeMillis();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0036  */
    @Override // android.view.ViewGroup
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        int action = motionEvent.getAction() & 255;
        if (action != 0) {
            if (action == 1) {
                this.mActivePointerId = -1;
                this.mSwipeGestureType = 0;
            } else if (action == 2) {
                int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
                if (iFindPointerIndex == -1) {
                    Log.d("StackView", "Error: No data for our primary pointer.");
                    return false;
                }
                beginGestureIfNeeded(motionEvent.getY(iFindPointerIndex) - this.mInitialY);
            } else if (action != 3) {
                if (action == 6) {
                    onSecondaryPointerUp(motionEvent);
                }
            }
        } else if (this.mActivePointerId == -1) {
            this.mInitialX = motionEvent.getX();
            this.mInitialY = motionEvent.getY();
            this.mActivePointerId = motionEvent.getPointerId(0);
        }
        return this.mSwipeGestureType != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x0063  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void beginGestureIfNeeded(float f) {
        int i;
        int i2;
        if (((int) Math.abs(f)) <= this.mTouchSlop || this.mSwipeGestureType != 0) {
            return;
        }
        int i3 = 2;
        int i4 = f < 0.0f ? 1 : 2;
        cancelLongPress();
        requestDisallowInterceptTouchEvent(true);
        if (this.mAdapter == null) {
            return;
        }
        int count = getCount();
        int i5 = (this.mStackMode != 0 ? i4 != 2 : i4 == 2) ? 0 : 1;
        boolean z = this.mLoopViews && count == 1 && (((i2 = this.mStackMode) == 0 && i4 == 1) || (i2 == 1 && i4 == 2));
        boolean z2 = this.mLoopViews && count == 1 && (((i = this.mStackMode) == 1 && i4 == 1) || (i == 0 && i4 == 2));
        if (!this.mLoopViews || z2 || z) {
            if (this.mCurrentWindowStartUnbounded + i5 == -1 || z2) {
                i5++;
                i3 = 1;
            } else if (this.mCurrentWindowStartUnbounded + i5 != count - 1 && !z) {
                i3 = 0;
            }
        }
        this.mTransitionIsSetup = i3 == 0;
        View viewAtRelativeIndex = getViewAtRelativeIndex(i5);
        if (viewAtRelativeIndex == null) {
            return;
        }
        setupStackSlider(viewAtRelativeIndex, i3);
        this.mSwipeGestureType = i4;
        cancelHandleClick();
    }

    @Override // android.widget.AdapterViewAnimator, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) throws Resources.NotFoundException {
        super.onTouchEvent(motionEvent);
        int action = motionEvent.getAction();
        int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (iFindPointerIndex == -1) {
            Log.d("StackView", "Error: No data for our primary pointer.");
            return false;
        }
        float y = motionEvent.getY(iFindPointerIndex);
        float x = motionEvent.getX(iFindPointerIndex);
        float f = y - this.mInitialY;
        float f2 = x - this.mInitialX;
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        int i = action & 255;
        if (i == 1) {
            handlePointerUp(motionEvent);
        } else if (i == 2) {
            beginGestureIfNeeded(f);
            int i2 = this.mSlideAmount;
            float f3 = f2 / (i2 * 1.0f);
            int i3 = this.mSwipeGestureType;
            if (i3 == 2) {
                float f4 = ((f - (this.mTouchSlop * 1.0f)) / i2) * 1.0f;
                if (this.mStackMode == 1) {
                    f4 = 1.0f - f4;
                }
                this.mStackSlider.setYProgress(1.0f - f4);
                this.mStackSlider.setXProgress(f3);
                return true;
            }
            if (i3 == 1) {
                float f5 = ((-(f + (this.mTouchSlop * 1.0f))) / i2) * 1.0f;
                if (this.mStackMode == 1) {
                    f5 = 1.0f - f5;
                }
                this.mStackSlider.setYProgress(f5);
                this.mStackSlider.setXProgress(f3);
                return true;
            }
        } else if (i == 3) {
            this.mActivePointerId = -1;
            this.mSwipeGestureType = 0;
        } else if (i == 6) {
            onSecondaryPointerUp(motionEvent);
        }
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) throws Resources.NotFoundException {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mActivePointerId) {
            View viewAtRelativeIndex = getViewAtRelativeIndex(this.mSwipeGestureType == 2 ? 0 : 1);
            if (viewAtRelativeIndex == null) {
                return;
            }
            for (int i = 0; i < motionEvent.getPointerCount(); i++) {
                if (i != actionIndex) {
                    float x = motionEvent.getX(i);
                    float y = motionEvent.getY(i);
                    this.mTouchRect.set(viewAtRelativeIndex.getLeft(), viewAtRelativeIndex.getTop(), viewAtRelativeIndex.getRight(), viewAtRelativeIndex.getBottom());
                    if (this.mTouchRect.contains(Math.round(x), Math.round(y))) {
                        float x2 = motionEvent.getX(actionIndex);
                        this.mInitialY += y - motionEvent.getY(actionIndex);
                        this.mInitialX += x - x2;
                        this.mActivePointerId = motionEvent.getPointerId(i);
                        VelocityTracker velocityTracker = this.mVelocityTracker;
                        if (velocityTracker != null) {
                            velocityTracker.clear();
                            return;
                        }
                        return;
                    }
                }
            }
            handlePointerUp(motionEvent);
        }
    }

    private void handlePointerUp(MotionEvent motionEvent) throws Resources.NotFoundException {
        float f;
        int iRound;
        int iRound2;
        int y = (int) (motionEvent.getY(motionEvent.findPointerIndex(this.mActivePointerId)) - this.mInitialY);
        this.mLastInteractionTime = System.currentTimeMillis();
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.computeCurrentVelocity(1000, this.mMaximumVelocity);
            this.mYVelocity = (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        }
        VelocityTracker velocityTracker2 = this.mVelocityTracker;
        if (velocityTracker2 != null) {
            velocityTracker2.recycle();
            this.mVelocityTracker = null;
        }
        if (y > this.mSwipeThreshold && this.mSwipeGestureType == 2 && this.mStackSlider.mMode == 0) {
            this.mSwipeGestureType = 0;
            if (this.mStackMode == 0) {
                showPrevious();
            } else {
                showNext();
            }
            this.mHighlight.bringToFront();
        } else if (y < (-this.mSwipeThreshold) && this.mSwipeGestureType == 1 && this.mStackSlider.mMode == 0) {
            this.mSwipeGestureType = 0;
            if (this.mStackMode == 0) {
                showNext();
            } else {
                showPrevious();
            }
            this.mHighlight.bringToFront();
        } else {
            int i = this.mSwipeGestureType;
            if (i == 1) {
                int i2 = this.mStackMode;
                f = i2 != 1 ? 0.0f : 1.0f;
                if (i2 == 0 || this.mStackSlider.mMode != 0) {
                    iRound2 = Math.round(this.mStackSlider.getDurationForNeutralPosition());
                } else {
                    iRound2 = Math.round(this.mStackSlider.getDurationForOffscreenPosition());
                }
                ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(new StackSlider(this.mStackSlider), PropertyValuesHolder.ofFloat("XProgress", 0.0f), PropertyValuesHolder.ofFloat("YProgress", f));
                objectAnimatorOfPropertyValuesHolder.setDuration(iRound2);
                objectAnimatorOfPropertyValuesHolder.setInterpolator(new LinearInterpolator());
                objectAnimatorOfPropertyValuesHolder.start();
            } else if (i == 2) {
                int i3 = this.mStackMode;
                f = i3 == 1 ? 0.0f : 1.0f;
                if (i3 == 1 || this.mStackSlider.mMode != 0) {
                    iRound = Math.round(this.mStackSlider.getDurationForNeutralPosition());
                } else {
                    iRound = Math.round(this.mStackSlider.getDurationForOffscreenPosition());
                }
                ObjectAnimator objectAnimatorOfPropertyValuesHolder2 = ObjectAnimator.ofPropertyValuesHolder(new StackSlider(this.mStackSlider), PropertyValuesHolder.ofFloat("XProgress", 0.0f), PropertyValuesHolder.ofFloat("YProgress", f));
                objectAnimatorOfPropertyValuesHolder2.setDuration(iRound);
                objectAnimatorOfPropertyValuesHolder2.start();
            }
        }
        this.mActivePointerId = -1;
        this.mSwipeGestureType = 0;
    }

    private class StackSlider {
        static final int BEGINNING_OF_STACK_MODE = 1;
        static final int END_OF_STACK_MODE = 2;
        static final int NORMAL_MODE = 0;
        int mMode;
        View mView;
        float mXProgress;
        float mYProgress;

        private float rotationInterpolator(float f) {
            if (f < 0.2f) {
                return 0.0f;
            }
            return (f - 0.2f) / 0.8f;
        }

        private float viewAlphaInterpolator(float f) {
            if (f > 0.3f) {
                return (f - 0.3f) / StackView.SLIDE_UP_RATIO;
            }
            return 0.0f;
        }

        public StackSlider() {
            this.mMode = 0;
        }

        public StackSlider(StackSlider stackSlider) {
            this.mMode = 0;
            this.mView = stackSlider.mView;
            this.mYProgress = stackSlider.mYProgress;
            this.mXProgress = stackSlider.mXProgress;
            this.mMode = stackSlider.mMode;
        }

        private float cubic(float f) {
            return ((float) (Math.pow((f * 2.0f) - 1.0f, 3.0d) + 1.0d)) / 2.0f;
        }

        private float highlightAlphaInterpolator(float f) {
            float fCubic;
            if (f < 0.4f) {
                fCubic = cubic(f / 0.4f);
            } else {
                fCubic = cubic(1.0f - ((f - 0.4f) / 0.6f));
            }
            return fCubic * 0.85f;
        }

        void setView(View view) {
            this.mView = view;
        }

        public void setYProgress(float f) {
            float fMax = Math.max(0.0f, Math.min(1.0f, f));
            this.mYProgress = fMax;
            View view = this.mView;
            if (view == null) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) StackView.this.mHighlight.getLayoutParams();
            int i = StackView.this.mStackMode == 0 ? 1 : -1;
            if (Float.compare(0.0f, this.mYProgress) != 0 && Float.compare(1.0f, this.mYProgress) != 0) {
                if (this.mView.getLayerType() == 0) {
                    this.mView.setLayerType(2, null);
                }
            } else if (this.mView.getLayerType() != 0) {
                this.mView.setLayerType(0, null);
            }
            int i2 = this.mMode;
            if (i2 != 0) {
                if (i2 == 1) {
                    float f2 = (1.0f - fMax) * 0.2f;
                    float f3 = i * f2;
                    layoutParams.setVerticalOffset(Math.round(StackView.this.mSlideAmount * f3));
                    layoutParams2.setVerticalOffset(Math.round(f3 * StackView.this.mSlideAmount));
                    StackView.this.mHighlight.setAlpha(highlightAlphaInterpolator(f2));
                    return;
                }
                if (i2 != 2) {
                    return;
                }
                float f4 = fMax * 0.2f;
                float f5 = (-i) * f4;
                layoutParams.setVerticalOffset(Math.round(StackView.this.mSlideAmount * f5));
                layoutParams2.setVerticalOffset(Math.round(f5 * StackView.this.mSlideAmount));
                StackView.this.mHighlight.setAlpha(highlightAlphaInterpolator(f4));
                return;
            }
            float f6 = i;
            float f7 = (-fMax) * f6;
            layoutParams.setVerticalOffset(Math.round(StackView.this.mSlideAmount * f7));
            layoutParams2.setVerticalOffset(Math.round(f7 * StackView.this.mSlideAmount));
            StackView.this.mHighlight.setAlpha(highlightAlphaInterpolator(fMax));
            float fViewAlphaInterpolator = viewAlphaInterpolator(1.0f - fMax);
            if (this.mView.getAlpha() == 0.0f && fViewAlphaInterpolator != 0.0f && this.mView.getVisibility() != 0) {
                this.mView.setVisibility(0);
            } else if (fViewAlphaInterpolator == 0.0f && this.mView.getAlpha() != 0.0f && this.mView.getVisibility() == 0) {
                this.mView.setVisibility(4);
            }
            this.mView.setAlpha(fViewAlphaInterpolator);
            float f8 = f6 * 90.0f;
            this.mView.setRotationX(rotationInterpolator(fMax) * f8);
            StackView.this.mHighlight.setRotationX(f8 * rotationInterpolator(fMax));
        }

        public void setXProgress(float f) {
            float fMax = Math.max(-2.0f, Math.min(2.0f, f));
            this.mXProgress = fMax;
            View view = this.mView;
            if (view == null) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            LayoutParams layoutParams2 = (LayoutParams) StackView.this.mHighlight.getLayoutParams();
            float f2 = fMax * 0.2f;
            layoutParams.setHorizontalOffset(Math.round(StackView.this.mSlideAmount * f2));
            layoutParams2.setHorizontalOffset(Math.round(f2 * StackView.this.mSlideAmount));
        }

        void setMode(int i) {
            this.mMode = i;
        }

        float getDurationForNeutralPosition() {
            return getDuration(false, 0.0f);
        }

        float getDurationForOffscreenPosition() {
            return getDuration(true, 0.0f);
        }

        float getDurationForNeutralPosition(float f) {
            return getDuration(false, f);
        }

        float getDurationForOffscreenPosition(float f) {
            return getDuration(true, f);
        }

        private float getDuration(boolean z, float f) {
            float fAbs;
            View view = this.mView;
            if (view == null) {
                return 0.0f;
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            float fHypot = (float) Math.hypot(layoutParams.horizontalOffset, layoutParams.verticalOffset);
            float fHypot2 = (float) Math.hypot(StackView.this.mSlideAmount, StackView.this.mSlideAmount * 0.4f);
            if (fHypot > fHypot2) {
                fHypot = fHypot2;
            }
            if (f == 0.0f) {
                return (z ? 1.0f - (fHypot / fHypot2) : fHypot / fHypot2) * 400.0f;
            }
            if (z) {
                fAbs = fHypot / Math.abs(f);
            } else {
                fAbs = (fHypot2 - fHypot) / Math.abs(f);
            }
            return (fAbs < 50.0f || fAbs > 400.0f) ? getDuration(z, 0.0f) : fAbs;
        }

        public float getYProgress() {
            return this.mYProgress;
        }

        public float getXProgress() {
            return this.mXProgress;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // android.widget.AdapterViewAnimator
    public LayoutParams createOrReuseLayoutParams(View view) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            layoutParams2.setHorizontalOffset(0);
            layoutParams2.setVerticalOffset(0);
            layoutParams2.width = 0;
            layoutParams2.width = 0;
            return layoutParams2;
        }
        return new LayoutParams(view);
    }

    @Override // android.widget.AdapterViewAnimator, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) throws Resources.NotFoundException {
        checkForAndHandleDataChanged();
        int childCount = getChildCount();
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            int measuredWidth = this.mPaddingLeft + childAt.getMeasuredWidth();
            int measuredHeight = this.mPaddingTop + childAt.getMeasuredHeight();
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            childAt.layout(this.mPaddingLeft + layoutParams.horizontalOffset, this.mPaddingTop + layoutParams.verticalOffset, measuredWidth + layoutParams.horizontalOffset, measuredHeight + layoutParams.verticalOffset);
        }
        onLayout();
    }

    @Override // android.widget.AdapterViewAnimator, android.widget.Advanceable
    public void advance() throws Resources.NotFoundException {
        long jCurrentTimeMillis = System.currentTimeMillis() - this.mLastInteractionTime;
        if (this.mAdapter == null) {
            return;
        }
        if (!(getCount() == 1 && this.mLoopViews) && this.mSwipeGestureType == 0 && jCurrentTimeMillis > 5000) {
            showNext();
        }
    }

    private void measureChildren() {
        int childCount = getChildCount();
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float f = measuredWidth;
        int iRound = (Math.round(f * 0.9f) - this.mPaddingLeft) - this.mPaddingRight;
        float f2 = measuredHeight;
        int iRound2 = (Math.round(0.9f * f2) - this.mPaddingTop) - this.mPaddingBottom;
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = getChildAt(i3);
            childAt.measure(View.MeasureSpec.makeMeasureSpec(iRound, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(iRound2, Integer.MIN_VALUE));
            if (childAt != this.mHighlight && childAt != this.mClickFeedback) {
                int measuredWidth2 = childAt.getMeasuredWidth();
                int measuredHeight2 = childAt.getMeasuredHeight();
                if (measuredWidth2 > i) {
                    i = measuredWidth2;
                }
                if (measuredHeight2 > i2) {
                    i2 = measuredHeight2;
                }
            }
        }
        this.mNewPerspectiveShiftX = f * 0.1f;
        this.mNewPerspectiveShiftY = f2 * 0.1f;
        if (i > 0 && childCount > 0 && i < iRound) {
            this.mNewPerspectiveShiftX = measuredWidth - i;
        }
        if (i2 <= 0 || childCount <= 0 || i2 >= iRound2) {
            return;
        }
        this.mNewPerspectiveShiftY = measuredHeight - i2;
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x004f  */
    @Override // android.widget.AdapterViewAnimator, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void onMeasure(int i, int i2) {
        int iRound;
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        boolean z = (this.mReferenceChildWidth == -1 || this.mReferenceChildHeight == -1) ? false : true;
        if (mode2 == 0) {
            size2 = z ? Math.round(this.mReferenceChildHeight * 2.1111112f) + this.mPaddingTop + this.mPaddingBottom : 0;
        } else if (mode2 == Integer.MIN_VALUE) {
            if (z) {
                int iRound2 = Math.round(this.mReferenceChildHeight * 2.1111112f) + this.mPaddingTop + this.mPaddingBottom;
                size2 = iRound2 <= size2 ? iRound2 : size2 | 16777216;
            }
        }
        if (mode == 0) {
            if (z) {
                iRound = Math.round(this.mReferenceChildWidth * 2.1111112f) + this.mPaddingLeft + this.mPaddingRight;
                size = iRound;
            }
            size = 0;
        } else if (mode2 == Integer.MIN_VALUE) {
            if (z) {
                iRound = this.mReferenceChildWidth + this.mPaddingLeft + this.mPaddingRight;
                if (iRound > size) {
                    iRound = size | 16777216;
                }
                size = iRound;
            }
            size = 0;
        }
        setMeasuredDimension(size, size2);
        measureChildren();
    }

    @Override // android.widget.AdapterViewAnimator, android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return StackView.class.getName();
    }

    @Override // android.widget.AdapterView, android.view.ViewGroup, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setScrollable(getChildCount() > 1);
        if (isEnabled()) {
            if (getDisplayedChild() < getChildCount() - 1) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_FORWARD);
                if (this.mStackMode == 0) {
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN);
                } else {
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP);
                }
            }
            if (getDisplayedChild() > 0) {
                accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SCROLL_BACKWARD);
                if (this.mStackMode == 0) {
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_UP);
                } else {
                    accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_PAGE_DOWN);
                }
            }
        }
    }

    private boolean goForward() throws Resources.NotFoundException {
        if (getDisplayedChild() >= getChildCount() - 1) {
            return false;
        }
        showNext();
        return true;
    }

    private boolean goBackward() throws Resources.NotFoundException {
        if (getDisplayedChild() <= 0) {
            return false;
        }
        showPrevious();
        return true;
    }

    @Override // android.view.View
    public boolean performAccessibilityActionInternal(int i, Bundle bundle) {
        if (super.performAccessibilityActionInternal(i, bundle)) {
            return true;
        }
        if (!isEnabled()) {
            return false;
        }
        if (i == 4096) {
            return goForward();
        }
        if (i == 8192) {
            return goBackward();
        }
        switch (i) {
            case 16908358:
                if (this.mStackMode != 0) {
                    break;
                } else {
                    break;
                }
            case 16908359:
                if (this.mStackMode != 0) {
                    break;
                } else {
                    break;
                }
        }
        return false;
    }

    class LayoutParams extends ViewGroup.LayoutParams {
        private final Rect globalInvalidateRect;
        int horizontalOffset;
        private final Rect invalidateRect;
        private final RectF invalidateRectf;
        View mView;
        private final Rect parentRect;
        int verticalOffset;

        LayoutParams(View view) {
            super(0, 0);
            this.parentRect = new Rect();
            this.invalidateRect = new Rect();
            this.invalidateRectf = new RectF();
            this.globalInvalidateRect = new Rect();
            this.width = 0;
            this.height = 0;
            this.horizontalOffset = 0;
            this.verticalOffset = 0;
            this.mView = view;
        }

        LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.parentRect = new Rect();
            this.invalidateRect = new Rect();
            this.invalidateRectf = new RectF();
            this.globalInvalidateRect = new Rect();
            this.horizontalOffset = 0;
            this.verticalOffset = 0;
            this.width = 0;
            this.height = 0;
        }

        void invalidateGlobalRegion(View view, Rect rect) {
            this.globalInvalidateRect.set(rect);
            this.globalInvalidateRect.union(0, 0, StackView.this.getWidth(), StackView.this.getHeight());
            if (view.getParent() == null || !(view.getParent() instanceof View)) {
                return;
            }
            this.parentRect.set(0, 0, 0, 0);
            boolean z = true;
            while (view.getParent() != null && (view.getParent() instanceof View) && !this.parentRect.contains(this.globalInvalidateRect)) {
                if (!z) {
                    this.globalInvalidateRect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
                }
                view = (View) view.getParent();
                this.parentRect.set(view.getScrollX(), view.getScrollY(), view.getWidth() + view.getScrollX(), view.getHeight() + view.getScrollY());
                view.invalidate(this.globalInvalidateRect.left, this.globalInvalidateRect.top, this.globalInvalidateRect.right, this.globalInvalidateRect.bottom);
                z = false;
            }
            view.invalidate(this.globalInvalidateRect.left, this.globalInvalidateRect.top, this.globalInvalidateRect.right, this.globalInvalidateRect.bottom);
        }

        Rect getInvalidateRect() {
            return this.invalidateRect;
        }

        void resetInvalidateRect() {
            this.invalidateRect.set(0, 0, 0, 0);
        }

        public void setVerticalOffset(int i) {
            setOffsets(this.horizontalOffset, i);
        }

        public void setHorizontalOffset(int i) {
            setOffsets(i, this.verticalOffset);
        }

        public void setOffsets(int i, int i2) {
            int i3 = i - this.horizontalOffset;
            this.horizontalOffset = i;
            int i4 = i2 - this.verticalOffset;
            this.verticalOffset = i2;
            View view = this.mView;
            if (view != null) {
                view.requestLayout();
                this.invalidateRectf.set(Math.min(this.mView.getLeft() + i3, this.mView.getLeft()), Math.min(this.mView.getTop() + i4, this.mView.getTop()), Math.max(this.mView.getRight() + i3, this.mView.getRight()), Math.max(this.mView.getBottom() + i4, this.mView.getBottom()));
                float f = -this.invalidateRectf.left;
                float f2 = -this.invalidateRectf.top;
                this.invalidateRectf.offset(f, f2);
                this.mView.getMatrix().mapRect(this.invalidateRectf);
                this.invalidateRectf.offset(-f, -f2);
                this.invalidateRect.set((int) Math.floor(this.invalidateRectf.left), (int) Math.floor(this.invalidateRectf.top), (int) Math.ceil(this.invalidateRectf.right), (int) Math.ceil(this.invalidateRectf.bottom));
                invalidateGlobalRegion(this.mView, this.invalidateRect);
            }
        }
    }

    private static class HolographicHelper {
        private static final int CLICK_FEEDBACK = 1;
        private static final int RES_OUT = 0;
        private final Paint mBlurPaint;
        private final Canvas mCanvas;
        private float mDensity;
        private final Paint mErasePaint;
        private final Paint mHolographicPaint;
        private final Matrix mIdentityMatrix;
        private BlurMaskFilter mLargeBlurMaskFilter;
        private final Canvas mMaskCanvas;
        private BlurMaskFilter mSmallBlurMaskFilter;
        private final int[] mTmpXY;

        HolographicHelper(Context context) {
            Paint paint = new Paint();
            this.mHolographicPaint = paint;
            Paint paint2 = new Paint();
            this.mErasePaint = paint2;
            this.mBlurPaint = new Paint();
            this.mCanvas = new Canvas();
            this.mMaskCanvas = new Canvas();
            this.mTmpXY = new int[2];
            this.mIdentityMatrix = new Matrix();
            this.mDensity = context.getResources().getDisplayMetrics().density;
            paint.setFilterBitmap(true);
            paint.setMaskFilter(TableMaskFilter.CreateClipTable(0, 30));
            paint2.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            paint2.setFilterBitmap(true);
            this.mSmallBlurMaskFilter = new BlurMaskFilter(this.mDensity * 2.0f, BlurMaskFilter.Blur.NORMAL);
            this.mLargeBlurMaskFilter = new BlurMaskFilter(this.mDensity * 4.0f, BlurMaskFilter.Blur.NORMAL);
        }

        Bitmap createClickOutline(View view, int i) {
            return createOutline(view, 1, i);
        }

        Bitmap createResOutline(View view, int i) {
            return createOutline(view, 0, i);
        }

        Bitmap createOutline(View view, int i, int i2) {
            this.mHolographicPaint.setColor(i2);
            if (i == 0) {
                this.mBlurPaint.setMaskFilter(this.mSmallBlurMaskFilter);
            } else if (i == 1) {
                this.mBlurPaint.setMaskFilter(this.mLargeBlurMaskFilter);
            }
            if (view.getMeasuredWidth() == 0 || view.getMeasuredHeight() == 0) {
                return null;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view.getResources().getDisplayMetrics(), view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
            this.mCanvas.setBitmap(bitmapCreateBitmap);
            float rotationX = view.getRotationX();
            float rotation = view.getRotation();
            float translationY = view.getTranslationY();
            float translationX = view.getTranslationX();
            view.setRotationX(0.0f);
            view.setRotation(0.0f);
            view.setTranslationY(0.0f);
            view.setTranslationX(0.0f);
            view.draw(this.mCanvas);
            view.setRotationX(rotationX);
            view.setRotation(rotation);
            view.setTranslationY(translationY);
            view.setTranslationX(translationX);
            drawOutline(this.mCanvas, bitmapCreateBitmap);
            this.mCanvas.setBitmap(null);
            return bitmapCreateBitmap;
        }

        void drawOutline(Canvas canvas, Bitmap bitmap) {
            Bitmap bitmapExtractAlpha = bitmap.extractAlpha(this.mBlurPaint, this.mTmpXY);
            this.mMaskCanvas.setBitmap(bitmapExtractAlpha);
            this.mMaskCanvas.drawBitmap(bitmap, -r0[0], -r0[1], this.mErasePaint);
            canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            canvas.setMatrix(this.mIdentityMatrix);
            canvas.drawBitmap(bitmapExtractAlpha, r0[0], r0[1], this.mHolographicPaint);
            this.mMaskCanvas.setBitmap(null);
            bitmapExtractAlpha.recycle();
        }
    }
}
