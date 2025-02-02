package androidx.customview.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class ViewDragHelper {
    private static final Interpolator sInterpolator = new InterpolatorC01101();
    private final Callback mCallback;
    private View mCapturedView;
    private final int mDefaultEdgeSize;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private final float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private final OverScroller mScroller;
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;
    private int mActivePointerId = -1;
    private final Runnable mSetIdleRunnable = new Runnable() { // from class: androidx.customview.widget.ViewDragHelper.2
        @Override // java.lang.Runnable
        public final void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };

    /* renamed from: androidx.customview.widget.ViewDragHelper$1 */
    final class InterpolatorC01101 implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    }

    public static abstract class Callback {
        public abstract int clampViewPositionHorizontal(View view, int i);

        public abstract int clampViewPositionVertical(View view);

        public abstract int getViewHorizontalDragRange(View view);

        public abstract void onEdgeDragStarted(int i, int i2);

        public abstract void onEdgeTouched();

        public abstract void onViewCaptured(View view);

        public abstract void onViewDragStateChanged(int i);

        public abstract void onViewPositionChanged(View view, int i);

        public abstract void onViewReleased(View view, float f);

        public abstract boolean tryCaptureView(View view);
    }

    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        if (viewGroup == null) {
            throw new NullPointerException("Parent view may not be null");
        }
        this.mParentView = viewGroup;
        this.mCallback = callback;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        int i = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
        this.mDefaultEdgeSize = i;
        this.mEdgeSize = i;
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(context, sInterpolator);
    }

    private boolean checkNewEdgeDrag(float f, float f2, int i, int i2) {
        float abs = Math.abs(f);
        float abs2 = Math.abs(f2);
        if ((this.mInitialEdgesTouched[i] & i2) != i2 || (this.mTrackingEdges & i2) == 0 || (this.mEdgeDragsLocked[i] & i2) == i2 || (this.mEdgeDragsInProgress[i] & i2) == i2) {
            return false;
        }
        int i3 = this.mTouchSlop;
        if (abs <= i3 && abs2 <= i3) {
            return false;
        }
        if (abs < abs2 * 0.5f) {
            this.mCallback.getClass();
        }
        return (this.mEdgeDragsInProgress[i] & i2) == 0 && abs > ((float) this.mTouchSlop);
    }

    private boolean checkTouchSlop(View view, float f) {
        if (view == null) {
            return false;
        }
        return (this.mCallback.getViewHorizontalDragRange(view) > 0) && Math.abs(f) > ((float) this.mTouchSlop);
    }

    private void clearMotionHistory(int i) {
        float[] fArr = this.mInitialMotionX;
        if (fArr != null) {
            int i2 = this.mPointersDown;
            int i3 = 1 << i;
            if ((i2 & i3) != 0) {
                fArr[i] = 0.0f;
                this.mInitialMotionY[i] = 0.0f;
                this.mLastMotionX[i] = 0.0f;
                this.mLastMotionY[i] = 0.0f;
                this.mInitialEdgesTouched[i] = 0;
                this.mEdgeDragsInProgress[i] = 0;
                this.mEdgeDragsLocked[i] = 0;
                this.mPointersDown = (~i3) & i2;
            }
        }
    }

    private int computeAxisDuration(int i, int i2, int i3) {
        if (i == 0) {
            return 0;
        }
        float width = this.mParentView.getWidth() / 2;
        float sin = (((float) Math.sin((Math.min(1.0f, Math.abs(i) / r3) - 0.5f) * 0.47123894f)) * width) + width;
        int abs = Math.abs(i2);
        return Math.min(abs > 0 ? Math.round(Math.abs(sin / abs) * 1000.0f) * 4 : (int) (((Math.abs(i) / i3) + 1.0f) * 256.0f), 600);
    }

    public static ViewDragHelper create(ViewGroup viewGroup, Callback callback) {
        ViewDragHelper viewDragHelper = new ViewDragHelper(viewGroup.getContext(), viewGroup, callback);
        viewDragHelper.mTouchSlop = (int) (1.0f * viewDragHelper.mTouchSlop);
        return viewDragHelper;
    }

    private boolean forceSettleCapturedViewAt(int i, int i2, int i3, int i4) {
        float f;
        float f2;
        float f3;
        float f4;
        int left = this.mCapturedView.getLeft();
        int top = this.mCapturedView.getTop();
        int i5 = i - left;
        int i6 = i2 - top;
        OverScroller overScroller = this.mScroller;
        if (i5 == 0 && i6 == 0) {
            overScroller.abortAnimation();
            setDragState(0);
            return false;
        }
        View view = this.mCapturedView;
        int i7 = (int) this.mMinVelocity;
        int i8 = (int) this.mMaxVelocity;
        int abs = Math.abs(i3);
        if (abs < i7) {
            i3 = 0;
        } else if (abs > i8) {
            i3 = i3 > 0 ? i8 : -i8;
        }
        int i9 = (int) this.mMinVelocity;
        int abs2 = Math.abs(i4);
        if (abs2 < i9) {
            i4 = 0;
        } else if (abs2 > i8) {
            i4 = i4 > 0 ? i8 : -i8;
        }
        int abs3 = Math.abs(i5);
        int abs4 = Math.abs(i6);
        int abs5 = Math.abs(i3);
        int abs6 = Math.abs(i4);
        int i10 = abs5 + abs6;
        int i11 = abs3 + abs4;
        if (i3 != 0) {
            f = abs5;
            f2 = i10;
        } else {
            f = abs3;
            f2 = i11;
        }
        float f5 = f / f2;
        if (i4 != 0) {
            f3 = abs6;
            f4 = i10;
        } else {
            f3 = abs4;
            f4 = i11;
        }
        overScroller.startScroll(left, top, i5, i6, (int) ((computeAxisDuration(i6, i4, 0) * (f3 / f4)) + (computeAxisDuration(i5, i3, this.mCallback.getViewHorizontalDragRange(view)) * f5)));
        setDragState(2);
        return true;
    }

    private void releaseViewForPointerUp() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        float f = this.mMaxVelocity;
        velocityTracker.computeCurrentVelocity(1000, f);
        float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
        float f2 = this.mMinVelocity;
        float abs = Math.abs(xVelocity);
        if (abs < f2) {
            xVelocity = 0.0f;
        } else if (abs > f) {
            xVelocity = xVelocity > 0.0f ? f : -f;
        }
        float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        float f3 = this.mMinVelocity;
        float abs2 = Math.abs(yVelocity);
        if (abs2 >= f3) {
            int i = (abs2 > f ? 1 : (abs2 == f ? 0 : -1));
        }
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, xVelocity);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* JADX WARN: Type inference failed for: r0v4, types: [int] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7 */
    /* JADX WARN: Type inference failed for: r2v1, types: [androidx.customview.widget.ViewDragHelper$Callback] */
    private void reportNewEdgeDrags(int i, float f, float f2) {
        boolean checkNewEdgeDrag = checkNewEdgeDrag(f, f2, i, 1);
        boolean z = checkNewEdgeDrag;
        if (checkNewEdgeDrag(f2, f, i, 4)) {
            z = (checkNewEdgeDrag ? 1 : 0) | 4;
        }
        boolean z2 = z;
        if (checkNewEdgeDrag(f, f2, i, 2)) {
            z2 = (z ? 1 : 0) | 2;
        }
        ?? r0 = z2;
        if (checkNewEdgeDrag(f2, f, i, 8)) {
            r0 = (z2 ? 1 : 0) | 8;
        }
        if (r0 != 0) {
            int[] iArr = this.mEdgeDragsInProgress;
            iArr[i] = iArr[i] | r0;
            this.mCallback.onEdgeDragStarted(r0, i);
        }
    }

    private void saveInitialMotion(int i, float f, float f2) {
        float[] fArr = this.mInitialMotionX;
        if (fArr == null || fArr.length <= i) {
            int i2 = i + 1;
            float[] fArr2 = new float[i2];
            float[] fArr3 = new float[i2];
            float[] fArr4 = new float[i2];
            float[] fArr5 = new float[i2];
            int[] iArr = new int[i2];
            int[] iArr2 = new int[i2];
            int[] iArr3 = new int[i2];
            if (fArr != null) {
                System.arraycopy(fArr, 0, fArr2, 0, fArr.length);
                float[] fArr6 = this.mInitialMotionY;
                System.arraycopy(fArr6, 0, fArr3, 0, fArr6.length);
                float[] fArr7 = this.mLastMotionX;
                System.arraycopy(fArr7, 0, fArr4, 0, fArr7.length);
                float[] fArr8 = this.mLastMotionY;
                System.arraycopy(fArr8, 0, fArr5, 0, fArr8.length);
                int[] iArr4 = this.mInitialEdgesTouched;
                System.arraycopy(iArr4, 0, iArr, 0, iArr4.length);
                int[] iArr5 = this.mEdgeDragsInProgress;
                System.arraycopy(iArr5, 0, iArr2, 0, iArr5.length);
                int[] iArr6 = this.mEdgeDragsLocked;
                System.arraycopy(iArr6, 0, iArr3, 0, iArr6.length);
            }
            this.mInitialMotionX = fArr2;
            this.mInitialMotionY = fArr3;
            this.mLastMotionX = fArr4;
            this.mLastMotionY = fArr5;
            this.mInitialEdgesTouched = iArr;
            this.mEdgeDragsInProgress = iArr2;
            this.mEdgeDragsLocked = iArr3;
        }
        float[] fArr9 = this.mInitialMotionX;
        this.mLastMotionX[i] = f;
        fArr9[i] = f;
        float[] fArr10 = this.mInitialMotionY;
        this.mLastMotionY[i] = f2;
        fArr10[i] = f2;
        int[] iArr7 = this.mInitialEdgesTouched;
        int i3 = (int) f;
        int i4 = (int) f2;
        ViewGroup viewGroup = this.mParentView;
        int i5 = i3 < viewGroup.getLeft() + this.mEdgeSize ? 1 : 0;
        if (i4 < viewGroup.getTop() + this.mEdgeSize) {
            i5 |= 4;
        }
        if (i3 > viewGroup.getRight() - this.mEdgeSize) {
            i5 |= 2;
        }
        if (i4 > viewGroup.getBottom() - this.mEdgeSize) {
            i5 |= 8;
        }
        iArr7[i] = i5;
        this.mPointersDown = (1 << i) | this.mPointersDown;
    }

    private void saveLastMotion(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = motionEvent.getPointerId(i);
            if ((this.mPointersDown & (1 << pointerId)) != 0) {
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                this.mLastMotionX[pointerId] = x;
                this.mLastMotionY[pointerId] = y;
            }
        }
    }

    public final void cancel() {
        this.mActivePointerId = -1;
        float[] fArr = this.mInitialMotionX;
        if (fArr != null) {
            Arrays.fill(fArr, 0.0f);
            Arrays.fill(this.mInitialMotionY, 0.0f);
            Arrays.fill(this.mLastMotionX, 0.0f);
            Arrays.fill(this.mLastMotionY, 0.0f);
            Arrays.fill(this.mInitialEdgesTouched, 0);
            Arrays.fill(this.mEdgeDragsInProgress, 0);
            Arrays.fill(this.mEdgeDragsLocked, 0);
            this.mPointersDown = 0;
        }
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    public final void captureChildView(View view, int i) {
        ViewParent parent = view.getParent();
        ViewGroup viewGroup = this.mParentView;
        if (parent != viewGroup) {
            throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + viewGroup + ")");
        }
        this.mCapturedView = view;
        this.mActivePointerId = i;
        this.mCallback.onViewCaptured(view);
        setDragState(1);
    }

    public final boolean continueSettling() {
        if (this.mDragState == 2) {
            OverScroller overScroller = this.mScroller;
            boolean computeScrollOffset = overScroller.computeScrollOffset();
            int currX = overScroller.getCurrX();
            int currY = overScroller.getCurrY();
            int left = currX - this.mCapturedView.getLeft();
            int top = currY - this.mCapturedView.getTop();
            if (left != 0) {
                View view = this.mCapturedView;
                int i = ViewCompat.$r8$clinit;
                view.offsetLeftAndRight(left);
            }
            if (top != 0) {
                View view2 = this.mCapturedView;
                int i2 = ViewCompat.$r8$clinit;
                view2.offsetTopAndBottom(top);
            }
            if (left != 0 || top != 0) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, currX);
            }
            if (computeScrollOffset && currX == overScroller.getFinalX() && currY == overScroller.getFinalY()) {
                overScroller.abortAnimation();
                computeScrollOffset = false;
            }
            if (!computeScrollOffset) {
                this.mParentView.post(this.mSetIdleRunnable);
            }
        }
        return this.mDragState == 2;
    }

    public final View findTopChildUnder(int i, int i2) {
        ViewGroup viewGroup = this.mParentView;
        for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
            this.mCallback.getClass();
            View childAt = viewGroup.getChildAt(childCount);
            if (i >= childAt.getLeft() && i < childAt.getRight() && i2 >= childAt.getTop() && i2 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    public final View getCapturedView() {
        return this.mCapturedView;
    }

    public final int getDefaultEdgeSize() {
        return this.mDefaultEdgeSize;
    }

    public final int getEdgeSize() {
        return this.mEdgeSize;
    }

    public final int getTouchSlop() {
        return this.mTouchSlop;
    }

    public final int getViewDragState() {
        return this.mDragState;
    }

    public final void processTouchEvent(MotionEvent motionEvent) {
        int findPointerIndex;
        int i;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        Callback callback = this.mCallback;
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int pointerId = motionEvent.getPointerId(0);
            View findTopChildUnder = findTopChildUnder((int) x, (int) y);
            saveInitialMotion(pointerId, x, y);
            tryCaptureViewForDrag(findTopChildUnder, pointerId);
            if ((this.mTrackingEdges & this.mInitialEdgesTouched[pointerId]) != 0) {
                callback.onEdgeTouched();
                return;
            }
            return;
        }
        if (actionMasked == 1) {
            if (this.mDragState == 1) {
                releaseViewForPointerUp();
            }
            cancel();
            return;
        }
        if (actionMasked == 2) {
            if (this.mDragState == 1) {
                int i2 = this.mActivePointerId;
                if (((this.mPointersDown & (1 << i2)) != 0 ? 1 : 0) == 0 || (findPointerIndex = motionEvent.findPointerIndex(i2)) == -1) {
                    return;
                }
                float x2 = motionEvent.getX(findPointerIndex);
                float y2 = motionEvent.getY(findPointerIndex);
                float[] fArr = this.mLastMotionX;
                int i3 = this.mActivePointerId;
                int i4 = (int) (x2 - fArr[i3]);
                int i5 = (int) (y2 - this.mLastMotionY[i3]);
                int left = this.mCapturedView.getLeft() + i4;
                this.mCapturedView.getTop();
                int left2 = this.mCapturedView.getLeft();
                int top = this.mCapturedView.getTop();
                if (i4 != 0) {
                    left = callback.clampViewPositionHorizontal(this.mCapturedView, left);
                    this.mCapturedView.offsetLeftAndRight(left - left2);
                }
                if (i5 != 0) {
                    int clampViewPositionVertical = callback.clampViewPositionVertical(this.mCapturedView);
                    View view = this.mCapturedView;
                    int i6 = clampViewPositionVertical - top;
                    int i7 = ViewCompat.$r8$clinit;
                    view.offsetTopAndBottom(i6);
                }
                if (i4 != 0 || i5 != 0) {
                    callback.onViewPositionChanged(this.mCapturedView, left);
                }
            } else {
                int pointerCount = motionEvent.getPointerCount();
                for (int i8 = 0; i8 < pointerCount; i8++) {
                    int pointerId2 = motionEvent.getPointerId(i8);
                    if ((this.mPointersDown & (1 << pointerId2)) != 0) {
                        float x3 = motionEvent.getX(i8);
                        float y3 = motionEvent.getY(i8);
                        float f = x3 - this.mInitialMotionX[pointerId2];
                        reportNewEdgeDrags(pointerId2, f, y3 - this.mInitialMotionY[pointerId2]);
                        if (this.mDragState == 1) {
                            break;
                        }
                        View findTopChildUnder2 = findTopChildUnder((int) x3, (int) y3);
                        if (checkTouchSlop(findTopChildUnder2, f) && tryCaptureViewForDrag(findTopChildUnder2, pointerId2)) {
                            break;
                        }
                    }
                }
            }
            saveLastMotion(motionEvent);
            return;
        }
        if (actionMasked == 3) {
            if (this.mDragState == 1) {
                this.mReleaseInProgress = true;
                callback.onViewReleased(this.mCapturedView, 0.0f);
                this.mReleaseInProgress = false;
                if (this.mDragState == 1) {
                    setDragState(0);
                }
            }
            cancel();
            return;
        }
        if (actionMasked != 5) {
            if (actionMasked != 6) {
                return;
            }
            int pointerId3 = motionEvent.getPointerId(actionIndex);
            if (this.mDragState == 1 && pointerId3 == this.mActivePointerId) {
                int pointerCount2 = motionEvent.getPointerCount();
                while (true) {
                    if (r3 >= pointerCount2) {
                        i = -1;
                        break;
                    }
                    int pointerId4 = motionEvent.getPointerId(r3);
                    if (pointerId4 != this.mActivePointerId) {
                        View findTopChildUnder3 = findTopChildUnder((int) motionEvent.getX(r3), (int) motionEvent.getY(r3));
                        View view2 = this.mCapturedView;
                        if (findTopChildUnder3 == view2 && tryCaptureViewForDrag(view2, pointerId4)) {
                            i = this.mActivePointerId;
                            break;
                        }
                    }
                    r3++;
                }
                if (i == -1) {
                    releaseViewForPointerUp();
                }
            }
            clearMotionHistory(pointerId3);
            return;
        }
        int pointerId5 = motionEvent.getPointerId(actionIndex);
        float x4 = motionEvent.getX(actionIndex);
        float y4 = motionEvent.getY(actionIndex);
        saveInitialMotion(pointerId5, x4, y4);
        if (this.mDragState == 0) {
            tryCaptureViewForDrag(findTopChildUnder((int) x4, (int) y4), pointerId5);
            if ((this.mTrackingEdges & this.mInitialEdgesTouched[pointerId5]) != 0) {
                callback.onEdgeTouched();
                return;
            }
            return;
        }
        int i9 = (int) x4;
        int i10 = (int) y4;
        View view3 = this.mCapturedView;
        if (view3 != null && i9 >= view3.getLeft() && i9 < view3.getRight() && i10 >= view3.getTop() && i10 < view3.getBottom()) {
            r3 = 1;
        }
        if (r3 != 0) {
            tryCaptureViewForDrag(this.mCapturedView, pointerId5);
        }
    }

    final void setDragState(int i) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != i) {
            this.mDragState = i;
            this.mCallback.onViewDragStateChanged(i);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    public final void setEdgeSize(int i) {
        this.mEdgeSize = i;
    }

    public final void setEdgeTrackingEnabled(int i) {
        this.mTrackingEdges = i;
    }

    public final void setMinVelocity(float f) {
        this.mMinVelocity = f;
    }

    public final void settleCapturedViewAt(int i, int i2) {
        if (!this.mReleaseInProgress) {
            throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
        }
        forceSettleCapturedViewAt(i, i2, (int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId));
    }

    public final boolean shouldInterceptTouchEvent(MotionEvent motionEvent) {
        View findTopChildUnder;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        Callback callback = this.mCallback;
        if (actionMasked != 0) {
            if (actionMasked != 1) {
                if (actionMasked != 2) {
                    if (actionMasked != 3) {
                        if (actionMasked == 5) {
                            int pointerId = motionEvent.getPointerId(actionIndex);
                            float x = motionEvent.getX(actionIndex);
                            float y = motionEvent.getY(actionIndex);
                            saveInitialMotion(pointerId, x, y);
                            int i = this.mDragState;
                            if (i == 0) {
                                if ((this.mInitialEdgesTouched[pointerId] & this.mTrackingEdges) != 0) {
                                    callback.onEdgeTouched();
                                }
                            } else if (i == 2 && (findTopChildUnder = findTopChildUnder((int) x, (int) y)) == this.mCapturedView) {
                                tryCaptureViewForDrag(findTopChildUnder, pointerId);
                            }
                        } else if (actionMasked == 6) {
                            clearMotionHistory(motionEvent.getPointerId(actionIndex));
                        }
                    }
                } else if (this.mInitialMotionX != null && this.mInitialMotionY != null) {
                    int pointerCount = motionEvent.getPointerCount();
                    for (int i2 = 0; i2 < pointerCount; i2++) {
                        int pointerId2 = motionEvent.getPointerId(i2);
                        if ((this.mPointersDown & (1 << pointerId2)) != 0) {
                            float x2 = motionEvent.getX(i2);
                            float y2 = motionEvent.getY(i2);
                            float f = x2 - this.mInitialMotionX[pointerId2];
                            float f2 = y2 - this.mInitialMotionY[pointerId2];
                            View findTopChildUnder2 = findTopChildUnder((int) x2, (int) y2);
                            boolean checkTouchSlop = checkTouchSlop(findTopChildUnder2, f);
                            if (checkTouchSlop) {
                                int left = findTopChildUnder2.getLeft();
                                int clampViewPositionHorizontal = callback.clampViewPositionHorizontal(findTopChildUnder2, ((int) f) + left);
                                findTopChildUnder2.getTop();
                                callback.clampViewPositionVertical(findTopChildUnder2);
                                int viewHorizontalDragRange = callback.getViewHorizontalDragRange(findTopChildUnder2);
                                if (viewHorizontalDragRange == 0) {
                                    break;
                                }
                                if (viewHorizontalDragRange > 0 && clampViewPositionHorizontal == left) {
                                    break;
                                }
                            }
                            reportNewEdgeDrags(pointerId2, f, f2);
                            if (this.mDragState == 1) {
                                break;
                            }
                            if (checkTouchSlop && tryCaptureViewForDrag(findTopChildUnder2, pointerId2)) {
                                break;
                            }
                        }
                    }
                    saveLastMotion(motionEvent);
                }
            }
            cancel();
        } else {
            float x3 = motionEvent.getX();
            float y3 = motionEvent.getY();
            int pointerId3 = motionEvent.getPointerId(0);
            saveInitialMotion(pointerId3, x3, y3);
            View findTopChildUnder3 = findTopChildUnder((int) x3, (int) y3);
            if (findTopChildUnder3 == this.mCapturedView && this.mDragState == 2) {
                tryCaptureViewForDrag(findTopChildUnder3, pointerId3);
            }
            if ((this.mInitialEdgesTouched[pointerId3] & this.mTrackingEdges) != 0) {
                callback.onEdgeTouched();
            }
        }
        return this.mDragState == 1;
    }

    public final boolean smoothSlideViewTo(View view, int i, int i2) {
        this.mCapturedView = view;
        this.mActivePointerId = -1;
        boolean forceSettleCapturedViewAt = forceSettleCapturedViewAt(i, i2, 0, 0);
        if (!forceSettleCapturedViewAt && this.mDragState == 0 && this.mCapturedView != null) {
            this.mCapturedView = null;
        }
        return forceSettleCapturedViewAt;
    }

    final boolean tryCaptureViewForDrag(View view, int i) {
        if (view == this.mCapturedView && this.mActivePointerId == i) {
            return true;
        }
        if (view == null || !this.mCallback.tryCaptureView(view)) {
            return false;
        }
        this.mActivePointerId = i;
        captureChildView(view, i);
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x004b A[LOOP:0: B:6:0x0008->B:23:0x004b, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkTouchSlop() {
        boolean z;
        float[] fArr;
        float[] fArr2;
        float[] fArr3;
        float[] fArr4 = this.mInitialMotionX;
        if (fArr4 == null) {
            return false;
        }
        int length = fArr4.length;
        for (int i = 0; i < length; i++) {
            if ((this.mPointersDown & (1 << i)) != 0) {
                float[] fArr5 = this.mInitialMotionX;
                if (fArr5 != null && (fArr = this.mInitialMotionY) != null && (fArr2 = this.mLastMotionX) != null && (fArr3 = this.mLastMotionY) != null) {
                    float f = fArr2[i] - fArr5[i];
                    float f2 = fArr3[i] - fArr[i];
                    float f3 = (f2 * f2) + (f * f);
                    int i2 = this.mTouchSlop;
                    if (f3 > i2 * i2) {
                        z = true;
                        if (!z) {
                            return true;
                        }
                    }
                } else {
                    Log.w("ViewDragHelper", "Inconsistent pointer event stream: pointer is down, but there is no initial motion recorded. Is something intercepting or modifying events?");
                }
            }
            z = false;
            if (!z) {
            }
        }
        return false;
    }
}
