package androidx.customview.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Arrays;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class ViewDragHelper {
    public static final AnonymousClass1 sInterpolator = new Interpolator() { // from class: androidx.customview.widget.ViewDragHelper.1
        @Override // android.animation.TimeInterpolator
        public final float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    };
    public final Callback mCallback;
    public View mCapturedView;
    public int mDragState;
    public int[] mEdgeDragsInProgress;
    public int[] mEdgeDragsLocked;
    public final int mEdgeSize;
    public int[] mInitialEdgesTouched;
    public float[] mInitialMotionX;
    public float[] mInitialMotionY;
    public float[] mLastMotionX;
    public float[] mLastMotionY;
    public final float mMaxVelocity;
    public final float mMinVelocity;
    public final ViewGroup mParentView;
    public int mPointersDown;
    public boolean mReleaseInProgress;
    public final OverScroller mScroller;
    public final int mTouchSlop;
    public VelocityTracker mVelocityTracker;
    public int mActivePointerId = -1;
    public final AnonymousClass2 mSetIdleRunnable = new Runnable() { // from class: androidx.customview.widget.ViewDragHelper.2
        @Override // java.lang.Runnable
        public final void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };
    public final boolean mIsUpdateOffsetLR = true;

    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.customview.widget.ViewDragHelper$2] */
    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Parent view may not be null");
        }
        if (callback == null) {
            throw new IllegalArgumentException("Callback may not be null");
        }
        this.mParentView = viewGroup;
        this.mCallback = callback;
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mEdgeSize = (int) ((context.getResources().getDisplayMetrics().density * 20.0f) + 0.5f);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaxVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(context, sInterpolator);
    }

    public static ViewDragHelper create(CoordinatorLayout coordinatorLayout, Callback callback) {
        return new ViewDragHelper(coordinatorLayout.getContext(), coordinatorLayout, callback);
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
        if (view.getParent() != this.mParentView) {
            throw new IllegalArgumentException("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (" + this.mParentView + ")");
        }
        this.mCapturedView = view;
        this.mActivePointerId = i;
        this.mCallback.onViewCaptured(view, i);
        setDragState(1);
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0044 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean checkTouchSlop(View view, float f, float f2) {
        if (view != null) {
            Callback callback = this.mCallback;
            boolean z = callback.getViewHorizontalDragRange(view) > 0;
            boolean z2 = callback.getViewVerticalDragRange() > 0;
            if (z && z2) {
                float f3 = (f2 * f2) + (f * f);
                int i = this.mTouchSlop;
                if (f3 > i * i) {
                }
            } else if (!z ? !(!z2 || Math.abs(f2) <= this.mTouchSlop) : Math.abs(f) > this.mTouchSlop) {
                return true;
            }
        }
        return false;
    }

    public final void clearMotionHistory(int i) {
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

    public final int computeAxisDuration(int i, int i2, int i3) {
        if (i == 0) {
            return 0;
        }
        float width = this.mParentView.getWidth() / 2;
        float fSin = (((float) Math.sin((Math.min(1.0f, Math.abs(i) / r3) - 0.5f) * 0.47123894f)) * width) + width;
        int iAbs = Math.abs(i2);
        return Math.min(iAbs > 0 ? Math.round(Math.abs(fSin / iAbs) * 1000.0f) * 4 : (int) (((Math.abs(i) / i3) + 1.0f) * 256.0f), VolteConstants.ErrorCode.BUSY_EVERYWHERE);
    }

    public final boolean continueSettling() {
        if (this.mDragState == 2) {
            boolean zComputeScrollOffset = this.mScroller.computeScrollOffset();
            int currX = this.mScroller.getCurrX();
            int currY = this.mScroller.getCurrY();
            int left = currX - this.mCapturedView.getLeft();
            int top = currY - this.mCapturedView.getTop();
            if (left != 0 && this.mIsUpdateOffsetLR) {
                View view = this.mCapturedView;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                view.offsetLeftAndRight(left);
            }
            if (top != 0) {
                View view2 = this.mCapturedView;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                view2.offsetTopAndBottom(top);
            }
            if (left != 0 || top != 0) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, currX, currY);
            }
            if (zComputeScrollOffset && currX == this.mScroller.getFinalX() && currY == this.mScroller.getFinalY()) {
                this.mScroller.abortAnimation();
                zComputeScrollOffset = false;
            }
            if (!zComputeScrollOffset) {
                this.mParentView.post(this.mSetIdleRunnable);
            }
        }
        return this.mDragState == 2;
    }

    public final View findTopChildUnder(int i, int i2) {
        for (int childCount = this.mParentView.getChildCount() - 1; childCount >= 0; childCount--) {
            ViewGroup viewGroup = this.mParentView;
            this.mCallback.getClass();
            View childAt = viewGroup.getChildAt(childCount);
            if (i >= childAt.getLeft() && i < childAt.getRight() && i2 >= childAt.getTop() && i2 < childAt.getBottom()) {
                return childAt;
            }
        }
        return null;
    }

    public final boolean forceSettleCapturedViewAt(int i, int i2, int i3, int i4) {
        float f;
        float f2;
        float f3;
        float f4;
        int left = this.mCapturedView.getLeft();
        int top = this.mCapturedView.getTop();
        int i5 = i - left;
        int i6 = i2 - top;
        if (i5 == 0 && i6 == 0) {
            this.mScroller.abortAnimation();
            setDragState(0);
            return false;
        }
        View view = this.mCapturedView;
        int i7 = (int) this.mMinVelocity;
        int i8 = (int) this.mMaxVelocity;
        int iAbs = Math.abs(i3);
        if (iAbs < i7) {
            i3 = 0;
        } else if (iAbs > i8) {
            i3 = i3 > 0 ? i8 : -i8;
        }
        int iAbs2 = Math.abs(i4);
        if (iAbs2 < i7) {
            i4 = 0;
        } else if (iAbs2 > i8) {
            i4 = i4 > 0 ? i8 : -i8;
        }
        int iAbs3 = Math.abs(i5);
        int iAbs4 = Math.abs(i6);
        int iAbs5 = Math.abs(i3);
        int iAbs6 = Math.abs(i4);
        int i9 = iAbs5 + iAbs6;
        int i10 = iAbs3 + iAbs4;
        if (i3 != 0) {
            f = iAbs5;
            f2 = i9;
        } else {
            f = iAbs3;
            f2 = i10;
        }
        float f5 = f / f2;
        if (i4 != 0) {
            f3 = iAbs6;
            f4 = i9;
        } else {
            f3 = iAbs4;
            f4 = i10;
        }
        float f6 = f3 / f4;
        Callback callback = this.mCallback;
        this.mScroller.startScroll(left, top, i5, i6, (int) ((computeAxisDuration(i6, i4, callback.getViewVerticalDragRange()) * f6) + (computeAxisDuration(i5, i3, callback.getViewHorizontalDragRange(view)) * f5)));
        setDragState(2);
        return true;
    }

    public final boolean isValidPointerForActionMove(int i) {
        if ((this.mPointersDown & (1 << i)) != 0 && i != -1) {
            return true;
        }
        Log.e("ViewDragHelper", "Ignoring pointerId=" + i + " because ACTION_DOWN was not received for this pointer before ACTION_MOVE. It likely happened because  ViewDragHelper did not receive all the events in the event stream.");
        return false;
    }

    public final void processTouchEvent(MotionEvent motionEvent) {
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
        int i2 = 0;
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int pointerId = motionEvent.getPointerId(0);
            View viewFindTopChildUnder = findTopChildUnder((int) x, (int) y);
            saveInitialMotion(x, y, pointerId);
            tryCaptureViewForDrag(viewFindTopChildUnder, pointerId);
            int i3 = this.mInitialEdgesTouched[pointerId];
            return;
        }
        if (actionMasked == 1) {
            if (this.mDragState == 1) {
                releaseViewForPointerUp();
            }
            cancel();
            return;
        }
        Callback callback = this.mCallback;
        if (actionMasked != 2) {
            if (actionMasked == 3) {
                if (this.mDragState == 1) {
                    this.mReleaseInProgress = true;
                    callback.onViewReleased(this.mCapturedView, 0.0f, 0.0f);
                    this.mReleaseInProgress = false;
                    if (this.mDragState == 1) {
                        setDragState(0);
                    }
                }
                cancel();
                return;
            }
            if (actionMasked == 5) {
                int pointerId2 = motionEvent.getPointerId(actionIndex);
                float x2 = motionEvent.getX(actionIndex);
                float y2 = motionEvent.getY(actionIndex);
                saveInitialMotion(x2, y2, pointerId2);
                if (this.mDragState == 0) {
                    tryCaptureViewForDrag(findTopChildUnder((int) x2, (int) y2), pointerId2);
                    int i4 = this.mInitialEdgesTouched[pointerId2];
                    return;
                }
                int i5 = (int) x2;
                int i6 = (int) y2;
                View view = this.mCapturedView;
                if (view != null && i5 >= view.getLeft() && i5 < view.getRight() && i6 >= view.getTop() && i6 < view.getBottom()) {
                    tryCaptureViewForDrag(this.mCapturedView, pointerId2);
                    return;
                }
                return;
            }
            if (actionMasked != 6) {
                return;
            }
            int pointerId3 = motionEvent.getPointerId(actionIndex);
            if (this.mDragState == 1 && pointerId3 == this.mActivePointerId) {
                int pointerCount = motionEvent.getPointerCount();
                while (true) {
                    if (i2 >= pointerCount) {
                        i = -1;
                        break;
                    }
                    int pointerId4 = motionEvent.getPointerId(i2);
                    if (pointerId4 != this.mActivePointerId) {
                        View viewFindTopChildUnder2 = findTopChildUnder((int) motionEvent.getX(i2), (int) motionEvent.getY(i2));
                        View view2 = this.mCapturedView;
                        if (viewFindTopChildUnder2 == view2 && tryCaptureViewForDrag(view2, pointerId4)) {
                            i = this.mActivePointerId;
                            break;
                        }
                    }
                    i2++;
                }
                if (i == -1) {
                    releaseViewForPointerUp();
                }
            }
            clearMotionHistory(pointerId3);
            return;
        }
        if (this.mDragState != 1) {
            int pointerCount2 = motionEvent.getPointerCount();
            while (i2 < pointerCount2) {
                int pointerId5 = motionEvent.getPointerId(i2);
                if (isValidPointerForActionMove(pointerId5)) {
                    float x3 = motionEvent.getX(i2);
                    float y3 = motionEvent.getY(i2);
                    float f = x3 - this.mInitialMotionX[pointerId5];
                    float f2 = y3 - this.mInitialMotionY[pointerId5];
                    Math.abs(f);
                    Math.abs(f2);
                    int i7 = this.mInitialEdgesTouched[pointerId5];
                    Math.abs(f2);
                    Math.abs(f);
                    int i8 = this.mInitialEdgesTouched[pointerId5];
                    Math.abs(f);
                    Math.abs(f2);
                    int i9 = this.mInitialEdgesTouched[pointerId5];
                    Math.abs(f2);
                    Math.abs(f);
                    int i10 = this.mInitialEdgesTouched[pointerId5];
                    if (this.mDragState != 1) {
                        View viewFindTopChildUnder3 = findTopChildUnder((int) x3, (int) y3);
                        if (checkTouchSlop(viewFindTopChildUnder3, f, f2) && tryCaptureViewForDrag(viewFindTopChildUnder3, pointerId5)) {
                            break;
                        }
                    } else {
                        break;
                    }
                }
                i2++;
            }
            saveLastMotion(motionEvent);
            return;
        }
        if (isValidPointerForActionMove(this.mActivePointerId)) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
            float x4 = motionEvent.getX(iFindPointerIndex);
            float y4 = motionEvent.getY(iFindPointerIndex);
            float[] fArr = this.mLastMotionX;
            int i11 = this.mActivePointerId;
            int i12 = (int) (x4 - fArr[i11]);
            int i13 = (int) (y4 - this.mLastMotionY[i11]);
            int left = this.mCapturedView.getLeft() + i12;
            int top = this.mCapturedView.getTop() + i13;
            int left2 = this.mCapturedView.getLeft();
            int top2 = this.mCapturedView.getTop();
            if (i12 != 0) {
                left = callback.clampViewPositionHorizontal(this.mCapturedView, left);
                if (this.mIsUpdateOffsetLR || this.mDragState != 2) {
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    this.mCapturedView.offsetLeftAndRight(left - left2);
                }
            }
            if (i13 != 0) {
                top = callback.clampViewPositionVertical(this.mCapturedView, top);
                View view3 = this.mCapturedView;
                int i14 = top - top2;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                view3.offsetTopAndBottom(i14);
            }
            if (i12 != 0 || i13 != 0) {
                callback.onViewPositionChanged(this.mCapturedView, left, top);
            }
            saveLastMotion(motionEvent);
        }
    }

    public final void releaseViewForPointerUp() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        float f = this.mMaxVelocity;
        velocityTracker.computeCurrentVelocity(1000, f);
        float xVelocity = this.mVelocityTracker.getXVelocity(this.mActivePointerId);
        float f2 = this.mMinVelocity;
        float fAbs = Math.abs(xVelocity);
        if (fAbs < f2) {
            xVelocity = 0.0f;
        } else if (fAbs > f) {
            xVelocity = xVelocity > 0.0f ? f : -f;
        }
        float yVelocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
        float fAbs2 = Math.abs(yVelocity);
        if (fAbs2 < f2) {
            f = 0.0f;
        } else if (fAbs2 <= f) {
            f = yVelocity;
        } else if (yVelocity <= 0.0f) {
            f = -f;
        }
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, xVelocity, f);
        this.mReleaseInProgress = false;
        if (this.mDragState == 1) {
            setDragState(0);
        }
    }

    public final void saveInitialMotion(float f, float f2, int i) {
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
        int left = this.mParentView.getLeft();
        int i5 = this.mEdgeSize;
        int i6 = i3 < left + i5 ? 1 : 0;
        if (i4 < this.mParentView.getTop() + i5) {
            i6 |= 4;
        }
        if (i3 > this.mParentView.getRight() - i5) {
            i6 |= 2;
        }
        if (i4 > this.mParentView.getBottom() - i5) {
            i6 |= 8;
        }
        iArr7[i] = i6;
        this.mPointersDown |= 1 << i;
    }

    public final void saveLastMotion(MotionEvent motionEvent) {
        int pointerCount = motionEvent.getPointerCount();
        for (int i = 0; i < pointerCount; i++) {
            int pointerId = motionEvent.getPointerId(i);
            if (isValidPointerForActionMove(pointerId)) {
                float x = motionEvent.getX(i);
                float y = motionEvent.getY(i);
                this.mLastMotionX[pointerId] = x;
                this.mLastMotionY[pointerId] = y;
            }
        }
    }

    public final void setDragState(int i) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState != i) {
            this.mDragState = i;
            this.mCallback.onViewDragStateChanged(i);
            if (this.mDragState == 0) {
                this.mCapturedView = null;
            }
        }
    }

    public final boolean settleCapturedViewAt(int i, int i2) {
        if (this.mReleaseInProgress) {
            return forceSettleCapturedViewAt(i, i2, (int) this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int) this.mVelocityTracker.getYVelocity(this.mActivePointerId));
        }
        throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
    }

    /* JADX WARN: Removed duplicated region for block: B:52:0x00d6  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0114  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean shouldInterceptTouchEvent(MotionEvent motionEvent) {
        View viewFindTopChildUnder;
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            cancel();
        }
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
        if (actionMasked == 0) {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            int pointerId = motionEvent.getPointerId(0);
            saveInitialMotion(x, y, pointerId);
            View viewFindTopChildUnder2 = findTopChildUnder((int) x, (int) y);
            if (viewFindTopChildUnder2 == this.mCapturedView && this.mDragState == 2) {
                tryCaptureViewForDrag(viewFindTopChildUnder2, pointerId);
            }
            int i = this.mInitialEdgesTouched[pointerId];
        } else if (actionMasked == 1) {
            cancel();
        } else if (actionMasked != 2) {
            if (actionMasked != 3) {
                if (actionMasked == 5) {
                    int pointerId2 = motionEvent.getPointerId(actionIndex);
                    float x2 = motionEvent.getX(actionIndex);
                    float y2 = motionEvent.getY(actionIndex);
                    saveInitialMotion(x2, y2, pointerId2);
                    int i2 = this.mDragState;
                    if (i2 == 0) {
                        int i3 = this.mInitialEdgesTouched[pointerId2];
                    } else if (i2 == 2 && (viewFindTopChildUnder = findTopChildUnder((int) x2, (int) y2)) == this.mCapturedView) {
                        tryCaptureViewForDrag(viewFindTopChildUnder, pointerId2);
                    }
                } else if (actionMasked == 6) {
                    clearMotionHistory(motionEvent.getPointerId(actionIndex));
                }
            }
        } else if (this.mInitialMotionX != null && this.mInitialMotionY != null) {
            int pointerCount = motionEvent.getPointerCount();
            for (int i4 = 0; i4 < pointerCount; i4++) {
                int pointerId3 = motionEvent.getPointerId(i4);
                if (isValidPointerForActionMove(pointerId3)) {
                    float x3 = motionEvent.getX(i4);
                    float y3 = motionEvent.getY(i4);
                    float f = x3 - this.mInitialMotionX[pointerId3];
                    float f2 = y3 - this.mInitialMotionY[pointerId3];
                    View viewFindTopChildUnder3 = findTopChildUnder((int) x3, (int) y3);
                    boolean z = viewFindTopChildUnder3 != null && checkTouchSlop(viewFindTopChildUnder3, f, f2);
                    if (z) {
                        int left = viewFindTopChildUnder3.getLeft();
                        Callback callback = this.mCallback;
                        int iClampViewPositionHorizontal = callback.clampViewPositionHorizontal(viewFindTopChildUnder3, ((int) f) + left);
                        int top = viewFindTopChildUnder3.getTop();
                        int iClampViewPositionVertical = callback.clampViewPositionVertical(viewFindTopChildUnder3, ((int) f2) + top);
                        int viewHorizontalDragRange = callback.getViewHorizontalDragRange(viewFindTopChildUnder3);
                        int viewVerticalDragRange = callback.getViewVerticalDragRange();
                        if ((viewHorizontalDragRange == 0 || (viewHorizontalDragRange > 0 && iClampViewPositionHorizontal == left)) && (viewVerticalDragRange == 0 || (viewVerticalDragRange > 0 && iClampViewPositionVertical == top))) {
                            break;
                        }
                        Math.abs(f);
                        Math.abs(f2);
                        int i5 = this.mInitialEdgesTouched[pointerId3];
                        Math.abs(f2);
                        Math.abs(f);
                        int i6 = this.mInitialEdgesTouched[pointerId3];
                        Math.abs(f);
                        Math.abs(f2);
                        int i7 = this.mInitialEdgesTouched[pointerId3];
                        Math.abs(f2);
                        Math.abs(f);
                        int i8 = this.mInitialEdgesTouched[pointerId3];
                        if (this.mDragState == 1 || (z && tryCaptureViewForDrag(viewFindTopChildUnder3, pointerId3))) {
                            break;
                        }
                    }
                }
            }
            saveLastMotion(motionEvent);
        }
        return this.mDragState == 1;
    }

    public final boolean tryCaptureViewForDrag(View view, int i) {
        if (view == this.mCapturedView && this.mActivePointerId == i) {
            return true;
        }
        if (view == null || !this.mCallback.tryCaptureView(view, i)) {
            return false;
        }
        this.mActivePointerId = i;
        captureChildView(view, i);
        return true;
    }

    public abstract class Callback {
        public int clampViewPositionHorizontal(View view, int i) {
            return 0;
        }

        public int clampViewPositionVertical(View view, int i) {
            return 0;
        }

        public int getViewHorizontalDragRange(View view) {
            return 0;
        }

        public int getViewVerticalDragRange() {
            return 0;
        }

        public abstract boolean tryCaptureView(View view, int i);

        public void onViewDragStateChanged(int i) {
        }

        public void onViewCaptured(View view, int i) {
        }

        public void onViewPositionChanged(View view, int i, int i2) {
        }

        public void onViewReleased(View view, float f, float f2) {
        }
    }
}
