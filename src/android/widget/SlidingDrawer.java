package android.widget;

import android.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;

@Deprecated
/* loaded from: classes5.dex */
public class SlidingDrawer extends ViewGroup {
    private static final int ANIMATION_FRAME_DURATION = 16;
    private static final int COLLAPSED_FULL_CLOSED = -10002;
    private static final int EXPANDED_FULL_OPEN = -10001;
    private static final float MAXIMUM_ACCELERATION = 2000.0f;
    private static final float MAXIMUM_MAJOR_VELOCITY = 200.0f;
    private static final float MAXIMUM_MINOR_VELOCITY = 150.0f;
    private static final float MAXIMUM_TAP_VELOCITY = 100.0f;
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;
    private static final int TAP_THRESHOLD = 6;
    private static final int VELOCITY_UNITS = 1000;
    private boolean mAllowSingleTap;
    private boolean mAnimateOnClick;
    private float mAnimatedAcceleration;
    private float mAnimatedVelocity;
    private boolean mAnimating;
    private long mAnimationLastTime;
    private float mAnimationPosition;
    private int mBottomOffset;
    private View mContent;
    private final int mContentId;
    private long mCurrentAnimationTime;
    private boolean mExpanded;
    private final Rect mFrame;
    private View mHandle;
    private int mHandleHeight;
    private final int mHandleId;
    private int mHandleWidth;
    private final Rect mInvalidate;
    private boolean mLocked;
    private final int mMaximumAcceleration;
    private final int mMaximumMajorVelocity;
    private final int mMaximumMinorVelocity;
    private final int mMaximumTapVelocity;
    private OnDrawerCloseListener mOnDrawerCloseListener;
    private OnDrawerOpenListener mOnDrawerOpenListener;
    private OnDrawerScrollListener mOnDrawerScrollListener;
    private final Runnable mSlidingRunnable;
    private final int mTapThreshold;
    private int mTopOffset;
    private int mTouchDelta;
    private boolean mTracking;
    private VelocityTracker mVelocityTracker;
    private final int mVelocityUnits;
    private boolean mVertical;

    public interface OnDrawerCloseListener {
        void onDrawerClosed();
    }

    public interface OnDrawerOpenListener {
        void onDrawerOpened();
    }

    public interface OnDrawerScrollListener {
        void onScrollEnded();

        void onScrollStarted();
    }

    public SlidingDrawer(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SlidingDrawer(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SlidingDrawer(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mFrame = new Rect();
        this.mInvalidate = new Rect();
        this.mSlidingRunnable = new Runnable() { // from class: android.widget.SlidingDrawer.1
            @Override // java.lang.Runnable
            public void run() {
                SlidingDrawer.this.doAnimation();
            }
        };
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SlidingDrawer, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.SlidingDrawer, attributeSet, obtainStyledAttributes, i, i2);
        this.mVertical = obtainStyledAttributes.getInt(0, 1) == 1;
        this.mBottomOffset = (int) obtainStyledAttributes.getDimension(1, 0.0f);
        this.mTopOffset = (int) obtainStyledAttributes.getDimension(2, 0.0f);
        this.mAllowSingleTap = obtainStyledAttributes.getBoolean(3, true);
        this.mAnimateOnClick = obtainStyledAttributes.getBoolean(6, true);
        int resourceId = obtainStyledAttributes.getResourceId(4, 0);
        if (resourceId == 0) {
            throw new IllegalArgumentException("The handle attribute is required and must refer to a valid child.");
        }
        int resourceId2 = obtainStyledAttributes.getResourceId(5, 0);
        if (resourceId2 == 0) {
            throw new IllegalArgumentException("The content attribute is required and must refer to a valid child.");
        }
        if (resourceId == resourceId2) {
            throw new IllegalArgumentException("The content and handle attributes must refer to different children.");
        }
        this.mHandleId = resourceId;
        this.mContentId = resourceId2;
        float f = getResources().getDisplayMetrics().density;
        this.mTapThreshold = (int) ((6.0f * f) + 0.5f);
        this.mMaximumTapVelocity = (int) ((100.0f * f) + 0.5f);
        this.mMaximumMinorVelocity = (int) ((MAXIMUM_MINOR_VELOCITY * f) + 0.5f);
        this.mMaximumMajorVelocity = (int) ((200.0f * f) + 0.5f);
        this.mMaximumAcceleration = (int) ((MAXIMUM_ACCELERATION * f) + 0.5f);
        this.mVelocityUnits = (int) ((f * 1000.0f) + 0.5f);
        obtainStyledAttributes.recycle();
        setAlwaysDrawnWithCacheEnabled(false);
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        View findViewById = findViewById(this.mHandleId);
        this.mHandle = findViewById;
        if (findViewById == null) {
            throw new IllegalArgumentException("The handle attribute is must refer to an existing child.");
        }
        findViewById.setOnClickListener(new DrawerToggler());
        View findViewById2 = findViewById(this.mContentId);
        this.mContent = findViewById2;
        if (findViewById2 == null) {
            throw new IllegalArgumentException("The content attribute is must refer to an existing child.");
        }
        findViewById2.setVisibility(8);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode == 0 || mode2 == 0) {
            throw new RuntimeException("SlidingDrawer cannot have UNSPECIFIED dimensions");
        }
        View view = this.mHandle;
        measureChild(view, i, i2);
        if (this.mVertical) {
            this.mContent.measure(View.MeasureSpec.makeMeasureSpec(size, 1073741824), View.MeasureSpec.makeMeasureSpec((size2 - view.getMeasuredHeight()) - this.mTopOffset, 1073741824));
        } else {
            this.mContent.measure(View.MeasureSpec.makeMeasureSpec((size - view.getMeasuredWidth()) - this.mTopOffset, 1073741824), View.MeasureSpec.makeMeasureSpec(size2, 1073741824));
        }
        setMeasuredDimension(size, size2);
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void dispatchDraw(Canvas canvas) {
        long drawingTime = getDrawingTime();
        View view = this.mHandle;
        boolean z = this.mVertical;
        drawChild(canvas, view, drawingTime);
        if (this.mTracking || this.mAnimating) {
            Bitmap drawingCache = this.mContent.getDrawingCache();
            if (drawingCache != null) {
                if (z) {
                    canvas.drawBitmap(drawingCache, 0.0f, view.getBottom(), (Paint) null);
                    return;
                } else {
                    canvas.drawBitmap(drawingCache, view.getRight(), 0.0f, (Paint) null);
                    return;
                }
            }
            canvas.save();
            canvas.translate(z ? 0.0f : view.getLeft() - this.mTopOffset, z ? view.getTop() - this.mTopOffset : 0.0f);
            drawChild(canvas, this.mContent, drawingTime);
            canvas.restore();
            return;
        }
        if (this.mExpanded) {
            drawChild(canvas, this.mContent, drawingTime);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        if (this.mTracking) {
            return;
        }
        int i7 = i3 - i;
        int i8 = i4 - i2;
        View view = this.mHandle;
        int measuredWidth = view.getMeasuredWidth();
        int measuredHeight = view.getMeasuredHeight();
        View view2 = this.mContent;
        if (this.mVertical) {
            i5 = (i7 - measuredWidth) / 2;
            i6 = this.mExpanded ? this.mTopOffset : (i8 - measuredHeight) + this.mBottomOffset;
            view2.layout(0, this.mTopOffset + measuredHeight, view2.getMeasuredWidth(), this.mTopOffset + measuredHeight + view2.getMeasuredHeight());
        } else {
            i5 = this.mExpanded ? this.mTopOffset : (i7 - measuredWidth) + this.mBottomOffset;
            i6 = (i8 - measuredHeight) / 2;
            int i9 = this.mTopOffset;
            view2.layout(i9 + measuredWidth, 0, i9 + measuredWidth + view2.getMeasuredWidth(), view2.getMeasuredHeight());
        }
        view.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
        this.mHandleHeight = view.getHeight();
        this.mHandleWidth = view.getWidth();
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        if (this.mLocked) {
            return false;
        }
        int action = motionEvent.getAction();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Rect rect = this.mFrame;
        View view = this.mHandle;
        view.getHitRect(rect);
        if (!this.mTracking && !rect.contains((int) x, (int) y)) {
            return false;
        }
        if (action == 0) {
            this.mTracking = true;
            view.setPressed(true);
            prepareContent();
            OnDrawerScrollListener onDrawerScrollListener = this.mOnDrawerScrollListener;
            if (onDrawerScrollListener != null) {
                onDrawerScrollListener.onScrollStarted();
            }
            if (this.mVertical) {
                int top = this.mHandle.getTop();
                this.mTouchDelta = ((int) y) - top;
                prepareTracking(top);
            } else {
                int left = this.mHandle.getLeft();
                this.mTouchDelta = ((int) x) - left;
                prepareTracking(left);
            }
            this.mVelocityTracker.addMovement(motionEvent);
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:12:0x001a, code lost:
    
        if (r0 != 3) goto L85;
     */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r12) {
        /*
            Method dump skipped, instructions count: 272
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SlidingDrawer.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void animateClose(int i, boolean z) {
        prepareTracking(i);
        performFling(i, this.mMaximumAcceleration, true, z);
    }

    private void animateOpen(int i, boolean z) {
        prepareTracking(i);
        performFling(i, -this.mMaximumAcceleration, true, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:32:0x0061, code lost:
    
        if (r5 > (-r3.mMaximumMajorVelocity)) goto L33;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void performFling(int r4, float r5, boolean r6, boolean r7) {
        /*
            r3 = this;
            float r0 = (float) r4
            r3.mAnimationPosition = r0
            r3.mAnimatedVelocity = r5
            boolean r0 = r3.mExpanded
            r1 = 0
            if (r0 == 0) goto L41
            if (r6 != 0) goto L35
            int r6 = r3.mMaximumMajorVelocity
            float r0 = (float) r6
            int r0 = (r5 > r0 ? 1 : (r5 == r0 ? 0 : -1))
            if (r0 > 0) goto L35
            int r0 = r3.mTopOffset
            boolean r2 = r3.mVertical
            if (r2 == 0) goto L1c
            int r2 = r3.mHandleHeight
            goto L1e
        L1c:
            int r2 = r3.mHandleWidth
        L1e:
            int r0 = r0 + r2
            if (r4 <= r0) goto L28
            int r4 = -r6
            float r4 = (float) r4
            int r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r4 <= 0) goto L28
            goto L35
        L28:
            int r4 = r3.mMaximumAcceleration
            int r4 = -r4
            float r4 = (float) r4
            r3.mAnimatedAcceleration = r4
            int r4 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r4 <= 0) goto L7b
            r3.mAnimatedVelocity = r1
            goto L7b
        L35:
            int r4 = r3.mMaximumAcceleration
            float r4 = (float) r4
            r3.mAnimatedAcceleration = r4
            int r4 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r4 >= 0) goto L7b
            r3.mAnimatedVelocity = r1
            goto L7b
        L41:
            if (r6 != 0) goto L6f
            int r6 = r3.mMaximumMajorVelocity
            float r6 = (float) r6
            int r6 = (r5 > r6 ? 1 : (r5 == r6 ? 0 : -1))
            if (r6 > 0) goto L63
            boolean r6 = r3.mVertical
            if (r6 == 0) goto L53
            int r6 = r3.getHeight()
            goto L57
        L53:
            int r6 = r3.getWidth()
        L57:
            int r6 = r6 / 2
            if (r4 <= r6) goto L6f
            int r4 = r3.mMaximumMajorVelocity
            int r4 = -r4
            float r4 = (float) r4
            int r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r4 <= 0) goto L6f
        L63:
            int r4 = r3.mMaximumAcceleration
            float r4 = (float) r4
            r3.mAnimatedAcceleration = r4
            int r4 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r4 >= 0) goto L7b
            r3.mAnimatedVelocity = r1
            goto L7b
        L6f:
            int r4 = r3.mMaximumAcceleration
            int r4 = -r4
            float r4 = (float) r4
            r3.mAnimatedAcceleration = r4
            int r4 = (r5 > r1 ? 1 : (r5 == r1 ? 0 : -1))
            if (r4 <= 0) goto L7b
            r3.mAnimatedVelocity = r1
        L7b:
            long r4 = android.os.SystemClock.uptimeMillis()
            r3.mAnimationLastTime = r4
            r0 = 16
            long r4 = r4 + r0
            r3.mCurrentAnimationTime = r4
            r4 = 1
            r3.mAnimating = r4
            java.lang.Runnable r4 = r3.mSlidingRunnable
            r3.removeCallbacks(r4)
            java.lang.Runnable r4 = r3.mSlidingRunnable
            r3.postDelayed(r4, r0)
            r3.stopTracking(r7)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SlidingDrawer.performFling(int, float, boolean, boolean):void");
    }

    private void prepareTracking(int i) {
        int width;
        int i2;
        this.mTracking = true;
        this.mVelocityTracker = VelocityTracker.obtain();
        if (!this.mExpanded) {
            this.mAnimatedAcceleration = this.mMaximumAcceleration;
            this.mAnimatedVelocity = this.mMaximumMajorVelocity;
            int i3 = this.mBottomOffset;
            if (this.mVertical) {
                width = getHeight();
                i2 = this.mHandleHeight;
            } else {
                width = getWidth();
                i2 = this.mHandleWidth;
            }
            float f = i3 + (width - i2);
            this.mAnimationPosition = f;
            moveHandle((int) f);
            this.mAnimating = true;
            removeCallbacks(this.mSlidingRunnable);
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mAnimationLastTime = uptimeMillis;
            this.mCurrentAnimationTime = uptimeMillis + 16;
            this.mAnimating = true;
            return;
        }
        if (this.mAnimating) {
            this.mAnimating = false;
            removeCallbacks(this.mSlidingRunnable);
        }
        moveHandle(i);
    }

    private void moveHandle(int i) {
        View view = this.mHandle;
        if (this.mVertical) {
            if (i == -10001) {
                view.offsetTopAndBottom(this.mTopOffset - view.getTop());
                invalidate();
                return;
            }
            if (i == -10002) {
                view.offsetTopAndBottom((((this.mBottomOffset + this.mBottom) - this.mTop) - this.mHandleHeight) - view.getTop());
                invalidate();
                return;
            }
            int top = view.getTop();
            int i2 = i - top;
            int i3 = this.mTopOffset;
            if (i < i3) {
                i2 = i3 - top;
            } else if (i2 > (((this.mBottomOffset + this.mBottom) - this.mTop) - this.mHandleHeight) - top) {
                i2 = (((this.mBottomOffset + this.mBottom) - this.mTop) - this.mHandleHeight) - top;
            }
            view.offsetTopAndBottom(i2);
            Rect rect = this.mFrame;
            Rect rect2 = this.mInvalidate;
            view.getHitRect(rect);
            rect2.set(rect);
            rect2.union(rect.left, rect.top - i2, rect.right, rect.bottom - i2);
            rect2.union(0, rect.bottom - i2, getWidth(), (rect.bottom - i2) + this.mContent.getHeight());
            invalidate(rect2);
            return;
        }
        if (i == -10001) {
            view.offsetLeftAndRight(this.mTopOffset - view.getLeft());
            invalidate();
            return;
        }
        if (i == -10002) {
            view.offsetLeftAndRight((((this.mBottomOffset + this.mRight) - this.mLeft) - this.mHandleWidth) - view.getLeft());
            invalidate();
            return;
        }
        int left = view.getLeft();
        int i4 = i - left;
        int i5 = this.mTopOffset;
        if (i < i5) {
            i4 = i5 - left;
        } else if (i4 > (((this.mBottomOffset + this.mRight) - this.mLeft) - this.mHandleWidth) - left) {
            i4 = (((this.mBottomOffset + this.mRight) - this.mLeft) - this.mHandleWidth) - left;
        }
        view.offsetLeftAndRight(i4);
        Rect rect3 = this.mFrame;
        Rect rect4 = this.mInvalidate;
        view.getHitRect(rect3);
        rect4.set(rect3);
        rect4.union(rect3.left - i4, rect3.top, rect3.right - i4, rect3.bottom);
        rect4.union(rect3.right - i4, 0, (rect3.right - i4) + this.mContent.getWidth(), getHeight());
        invalidate(rect4);
    }

    private void prepareContent() {
        if (this.mAnimating) {
            return;
        }
        View view = this.mContent;
        if (view.isLayoutRequested()) {
            if (this.mVertical) {
                int i = this.mHandleHeight;
                view.measure(View.MeasureSpec.makeMeasureSpec(this.mRight - this.mLeft, 1073741824), View.MeasureSpec.makeMeasureSpec(((this.mBottom - this.mTop) - i) - this.mTopOffset, 1073741824));
                view.layout(0, this.mTopOffset + i, view.getMeasuredWidth(), this.mTopOffset + i + view.getMeasuredHeight());
            } else {
                int width = this.mHandle.getWidth();
                view.measure(View.MeasureSpec.makeMeasureSpec(((this.mRight - this.mLeft) - width) - this.mTopOffset, 1073741824), View.MeasureSpec.makeMeasureSpec(this.mBottom - this.mTop, 1073741824));
                int i2 = this.mTopOffset;
                view.layout(width + i2, 0, i2 + width + view.getMeasuredWidth(), view.getMeasuredHeight());
            }
        }
        view.getViewTreeObserver().dispatchOnPreDraw();
        if (!view.isHardwareAccelerated()) {
            view.buildDrawingCache();
        }
        view.setVisibility(8);
    }

    private void stopTracking(boolean z) {
        OnDrawerScrollListener onDrawerScrollListener;
        this.mHandle.setPressed(false);
        this.mTracking = false;
        if (z && (onDrawerScrollListener = this.mOnDrawerScrollListener) != null) {
            onDrawerScrollListener.onScrollEnded();
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void doAnimation() {
        if (this.mAnimating) {
            incrementAnimation();
            if (this.mAnimationPosition >= (this.mBottomOffset + (this.mVertical ? getHeight() : getWidth())) - 1) {
                this.mAnimating = false;
                closeDrawer();
                return;
            }
            float f = this.mAnimationPosition;
            if (f < this.mTopOffset) {
                this.mAnimating = false;
                openDrawer();
            } else {
                moveHandle((int) f);
                this.mCurrentAnimationTime += 16;
                postDelayed(this.mSlidingRunnable, 16L);
            }
        }
    }

    private void incrementAnimation() {
        long uptimeMillis = SystemClock.uptimeMillis();
        float f = (uptimeMillis - this.mAnimationLastTime) / 1000.0f;
        float f2 = this.mAnimationPosition;
        float f3 = this.mAnimatedVelocity;
        float f4 = this.mAnimatedAcceleration;
        this.mAnimationPosition = f2 + (f3 * f) + (0.5f * f4 * f * f);
        this.mAnimatedVelocity = f3 + (f4 * f);
        this.mAnimationLastTime = uptimeMillis;
    }

    public void toggle() {
        if (!this.mExpanded) {
            openDrawer();
        } else {
            closeDrawer();
        }
        invalidate();
        requestLayout();
    }

    public void animateToggle() {
        if (!this.mExpanded) {
            animateOpen();
        } else {
            animateClose();
        }
    }

    public void open() {
        openDrawer();
        invalidate();
        requestLayout();
        sendAccessibilityEvent(32);
    }

    public void close() {
        closeDrawer();
        invalidate();
        requestLayout();
    }

    public void animateClose() {
        prepareContent();
        OnDrawerScrollListener onDrawerScrollListener = this.mOnDrawerScrollListener;
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollStarted();
        }
        animateClose(this.mVertical ? this.mHandle.getTop() : this.mHandle.getLeft(), false);
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollEnded();
        }
    }

    public void animateOpen() {
        prepareContent();
        OnDrawerScrollListener onDrawerScrollListener = this.mOnDrawerScrollListener;
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollStarted();
        }
        animateOpen(this.mVertical ? this.mHandle.getTop() : this.mHandle.getLeft(), false);
        sendAccessibilityEvent(32);
        if (onDrawerScrollListener != null) {
            onDrawerScrollListener.onScrollEnded();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public CharSequence getAccessibilityClassName() {
        return SlidingDrawer.class.getName();
    }

    private void closeDrawer() {
        moveHandle(-10002);
        this.mContent.setVisibility(8);
        this.mContent.destroyDrawingCache();
        if (this.mExpanded) {
            this.mExpanded = false;
            OnDrawerCloseListener onDrawerCloseListener = this.mOnDrawerCloseListener;
            if (onDrawerCloseListener != null) {
                onDrawerCloseListener.onDrawerClosed();
            }
        }
    }

    private void openDrawer() {
        moveHandle(-10001);
        this.mContent.setVisibility(0);
        if (this.mExpanded) {
            return;
        }
        this.mExpanded = true;
        OnDrawerOpenListener onDrawerOpenListener = this.mOnDrawerOpenListener;
        if (onDrawerOpenListener != null) {
            onDrawerOpenListener.onDrawerOpened();
        }
    }

    public void setOnDrawerOpenListener(OnDrawerOpenListener onDrawerOpenListener) {
        this.mOnDrawerOpenListener = onDrawerOpenListener;
    }

    public void setOnDrawerCloseListener(OnDrawerCloseListener onDrawerCloseListener) {
        this.mOnDrawerCloseListener = onDrawerCloseListener;
    }

    public void setOnDrawerScrollListener(OnDrawerScrollListener onDrawerScrollListener) {
        this.mOnDrawerScrollListener = onDrawerScrollListener;
    }

    public View getHandle() {
        return this.mHandle;
    }

    public View getContent() {
        return this.mContent;
    }

    public void unlock() {
        this.mLocked = false;
    }

    public void lock() {
        this.mLocked = true;
    }

    public boolean isOpened() {
        return this.mExpanded;
    }

    public boolean isMoving() {
        return this.mTracking || this.mAnimating;
    }

    private class DrawerToggler implements View.OnClickListener {
        private DrawerToggler() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (SlidingDrawer.this.mLocked) {
                return;
            }
            if (SlidingDrawer.this.mAnimateOnClick) {
                SlidingDrawer.this.animateToggle();
            } else {
                SlidingDrawer.this.toggle();
            }
        }
    }
}
