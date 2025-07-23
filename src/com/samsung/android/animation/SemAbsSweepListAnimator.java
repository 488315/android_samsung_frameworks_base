package com.samsung.android.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ListView;

/* loaded from: classes6.dex */
abstract class SemAbsSweepListAnimator {
    private static final float COSINE_VALUE_THESHOLD = 0.57f;
    private static final boolean DEBUGGABLE = false;
    private static final boolean DEBUGGABLE_LOW = true;
    private static final int DIRECTION_LEFT_TO_RIGHT = 0;
    private static final int DIRECTION_RIGHT_TO_LEFT = 1;
    private static int INVALID_POINTER_ID = -1;
    protected static final int MOVE_DURATION = 150;
    protected static final int SWIPE_DURATION = 600;
    private static final String TAG = "SemAbsSweepListAnimator";
    private float downX;
    private float downY;
    protected float mDownX;
    protected int mForegroundViewResId;
    protected ListView mListView;
    protected VelocityTracker mVelocityTracker;
    private float upX;
    private float upY;
    protected static Interpolator sAccelDecel = new AccelerateDecelerateInterpolator();
    protected static Interpolator sDecel = new DecelerateInterpolator();
    protected static int VELOCITY_UNITS = 500;
    protected static int HISTORICAL_VELOCITY_COUNT = 4;
    protected int mScaledTouchSlop = -1;
    protected boolean mSwiping = false;
    protected boolean mItemPressed = false;
    protected int mSwipingPosition = -1;
    protected float[] mHistoricalVelocities = new float[HISTORICAL_VELOCITY_COUNT];
    protected int mHistoricalVelocityIndex = 0;
    protected int mActivePointerId = INVALID_POINTER_ID;
    protected View mForegroundView = null;
    protected int mCurrentPosition = -1;
    private float mSweepLeftDistance = 0.0f;
    private float mSweepRightDistance = 0.0f;
    private float mSweepPrevPosX = -1.0f;
    private int mSweepDirection = -1;
    private int mPrevSweepDirection = -1;

    abstract void onActionCancel(MotionEvent motionEvent, View view, int i);

    abstract void onActionDown(MotionEvent motionEvent);

    abstract void onActionMove(MotionEvent motionEvent, View view, int i);

    abstract void onActionUp(MotionEvent motionEvent, View view, int i, boolean z);

    abstract void setForegroundViewResId(int i);

    SemAbsSweepListAnimator() {
    }

    private View findTouchedView(MotionEvent motionEvent) {
        View childAt = this.mListView.getChildAt(this.mListView.pointToPosition((int) motionEvent.getX(), (int) motionEvent.getY()) - this.mListView.getFirstVisiblePosition());
        if (childAt != null) {
            return childAt instanceof ViewGroup ? childAt.findViewById(this.mForegroundViewResId) : childAt;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x001b, code lost:
    
        if (r0 != 6) goto L21;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            boolean r0 = r4.isTouchEventSkipped()
            r1 = 0
            if (r0 == 0) goto L8
            return r1
        L8:
            r4.addVelocityTracker(r5)
            int r0 = r5.getAction()
            if (r0 == 0) goto L32
            r2 = 1
            if (r0 == r2) goto L2e
            r3 = 2
            if (r0 == r3) goto L22
            r2 = 3
            if (r0 == r2) goto L1e
            r2 = 6
            if (r0 == r2) goto L2e
            goto L31
        L1e:
            r4.handleTouchCancelEvent(r5)
            goto L31
        L22:
            float r5 = r4.calculateDistanceX(r5)
            int r4 = r4.mScaledTouchSlop
            float r4 = (float) r4
            int r4 = (r5 > r4 ? 1 : (r5 == r4 ? 0 : -1))
            if (r4 <= 0) goto L31
            return r2
        L2e:
            r4.handleTouchUpEvent(r5)
        L31:
            return r1
        L32:
            float r0 = r5.getX()
            r4.mDownX = r0
            r4.handleTouchDownEvent(r5)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.animation.SemAbsSweepListAnimator.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    private boolean handleTouchDownEvent(MotionEvent motionEvent) {
        if (this.mListView == null) {
            return false;
        }
        View findTouchedView = findTouchedView(motionEvent);
        this.mForegroundView = findTouchedView;
        if (findTouchedView == null) {
            return false;
        }
        int positionForView = this.mListView.getPositionForView(findTouchedView);
        this.mCurrentPosition = positionForView;
        if (positionForView == -1 || positionForView < this.mListView.getFirstVisiblePosition() || this.mCurrentPosition > this.mListView.getLastVisiblePosition()) {
            return false;
        }
        this.downX = motionEvent.getX();
        this.downY = motionEvent.getY();
        onActionDown(motionEvent);
        return true;
    }

    private boolean handleTouchMoveEvent(MotionEvent motionEvent) {
        ListView listView = this.mListView;
        if (listView != null && (this.mCurrentPosition < listView.getFirstVisiblePosition() || this.mCurrentPosition > this.mListView.getLastVisiblePosition())) {
            return false;
        }
        ListView listView2 = this.mListView;
        if (listView2 != null) {
            boolean z = listView2.mSemFastScrollEffectState;
            boolean z2 = this.mListView.semGetLastScrollState() == 0;
            if (z || !z2) {
                return false;
            }
        }
        this.upX = motionEvent.getX();
        float y = motionEvent.getY();
        this.upY = y;
        float f = this.downX - this.upX;
        float f2 = this.downY - y;
        double cos = Math.cos(Math.abs(f / Math.sqrt(Math.abs(f * f) + Math.abs(f2 * f2))));
        if (this.mSwiping) {
            onActionMove(motionEvent, this.mForegroundView, this.mCurrentPosition);
            trackSweepDistanceAndDirection(motionEvent);
            return true;
        }
        if (cos >= 0.5699999928474426d) {
            return false;
        }
        onActionMove(motionEvent, this.mForegroundView, this.mCurrentPosition);
        return true;
    }

    private void handleTouchUpEvent(MotionEvent motionEvent) {
        int pointerId = motionEvent.getPointerId(motionEvent.getActionIndex());
        if (pointerId == this.mActivePointerId && this.mItemPressed) {
            onActionUp(motionEvent, this.mForegroundView, this.mCurrentPosition, sweepPatternIsIndeedFling(this.mVelocityTracker.getXVelocity()));
        } else {
            Log.d(TAG, "handleTouchUpEvent : event.getAction() = " + motionEvent.getAction() + ", currentPointerId = " + pointerId + ", mActivePointerId = " + this.mActivePointerId + ", mItemPressed = " + this.mItemPressed + ", mSwiping = " + this.mSwiping);
            if (this.mItemPressed && this.mSwiping) {
                Log.d(TAG, "handleTouchUpEvent : call onActionCancel to remove remaining sweepBitmap");
                onActionCancel(motionEvent, this.mForegroundView, this.mCurrentPosition);
            }
        }
        this.mCurrentPosition = -1;
        initSweepDistanceVariables();
    }

    private boolean handleTouchCancelEvent(MotionEvent motionEvent) {
        onActionCancel(motionEvent, this.mForegroundView, this.mCurrentPosition);
        initSweepDistanceVariables();
        if (!this.mSwiping) {
            return false;
        }
        this.mSwiping = false;
        this.mListView.removePendingCallbacks();
        return true;
    }

    private boolean isTouchEventSkipped() {
        return (this.mSwiping && this.mSwipingPosition != this.mCurrentPosition) || !this.mListView.isEnabled();
    }

    private void addVelocityTracker(MotionEvent motionEvent) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(motionEvent);
    }

    private float calculateDistanceX(MotionEvent motionEvent) {
        return Math.abs(this.mDownX - motionEvent.getX());
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (isTouchEventSkipped()) {
            return false;
        }
        addVelocityTracker(motionEvent);
        int action = motionEvent.getAction();
        if (action != 1) {
            if (action == 2) {
                return handleTouchMoveEvent(motionEvent);
            }
            if (action == 3) {
                return handleTouchCancelEvent(motionEvent);
            }
            if (action != 6) {
                return false;
            }
        }
        handleTouchUpEvent(motionEvent);
        return false;
    }

    private void initSweepDistanceVariables() {
        this.mSweepLeftDistance = 0.0f;
        this.mSweepRightDistance = 0.0f;
        this.mSweepPrevPosX = -1.0f;
        this.mSweepDirection = -1;
    }

    private String getCurrentSweepDirection(int i) {
        if (i == 0) {
            return "Left to Right";
        }
        if (i == 1) {
            return "Right to Left";
        }
        return "No direction";
    }

    private void trackSweepDistanceAndDirection(MotionEvent motionEvent) {
        if (this.mSweepPrevPosX == -1.0f) {
            Log.d(TAG, "trackSweepDistanceAndDirection : first calling trackSweepDistanceAndDirection");
            Log.d(TAG, "trackSweepDistanceAndDirection : mSweepPrevPosX is set to mDownX, mSweepPrevPosX = " + this.mDownX);
            this.mSweepPrevPosX = this.mDownX;
        }
        float f = this.mSweepPrevPosX;
        if (f != -1.0f) {
            if (f > motionEvent.getX()) {
                Log.d(TAG, "trackSweepDistanceAndDirection : sweep to left");
                this.mSweepDirection = 1;
                this.mSweepLeftDistance += this.mSweepPrevPosX - motionEvent.getX();
            } else if (this.mSweepPrevPosX < motionEvent.getX()) {
                Log.d(TAG, "trackSweepDistanceAndDirection : sweep to right");
                this.mSweepDirection = 0;
                this.mSweepRightDistance += motionEvent.getX() - this.mSweepPrevPosX;
            }
        }
        int i = this.mPrevSweepDirection;
        if (i != -1 && i != this.mSweepDirection) {
            Log.d(TAG, "trackSweepDistanceAndDirection : SweepDirection is changed");
            Log.d(TAG, "trackSweepDistanceAndDirection : changed direction = " + getCurrentSweepDirection(this.mSweepDirection));
            int i2 = this.mSweepDirection;
            if (i2 == 1) {
                Log.d(TAG, "trackSweepDistanceAndDirection : Set mSweepRightDistance = 0");
                this.mSweepRightDistance = 0.0f;
            } else if (i2 == 0) {
                Log.d(TAG, "trackSweepDistanceAndDirection : Set mSweepLeftDistance = 0");
                this.mSweepLeftDistance = 0.0f;
            }
            this.mVelocityTracker.clear();
            int i3 = 0;
            while (true) {
                float[] fArr = this.mHistoricalVelocities;
                if (i3 >= fArr.length) {
                    break;
                }
                fArr[i3] = 0.0f;
                i3++;
            }
            this.mHistoricalVelocityIndex = 0;
            Log.d(TAG, "trackSweepDistanceAndDirection : Clear velocityTracker");
        }
        this.mPrevSweepDirection = this.mSweepDirection;
        this.mSweepPrevPosX = motionEvent.getX();
    }

    private boolean sweepPatternIsIndeedFling(float f) {
        Log.d(TAG, "***** Start sweepPatternIsIndeedFling *****");
        Log.d(TAG, "sweepPatternIsIndeedFling : velocity =" + f);
        Log.d(TAG, "sweepPatternIsIndeedFling : mSweepRightDistance = " + this.mSweepRightDistance);
        Log.d(TAG, "sweepPatternIsIndeedFling : mSweepLeftDistance = " + this.mSweepLeftDistance);
        int i = this.mScaledTouchSlop * 2;
        Log.d(TAG, "sweepPatternIsIndeedFling : minimalDistanceThreshold = " + i);
        if ((f > 0.0f && this.mSweepRightDistance < i) || (f < 0.0f && this.mSweepLeftDistance < i)) {
            Log.d(TAG, "sweepPatternIsIndeedFling : SweepDistance is less than minDistance, return false #1");
            Log.d(TAG, "***** End sweepPatternIsIndeedFling *****");
            return false;
        }
        Log.d(TAG, "sweepPatternIsIndeedFling : return true #2");
        Log.d(TAG, "***** End sweepPatternIsIndeedFling *****");
        return true;
    }

    protected float getAdjustedVelocityX(float[] fArr) {
        if (this.mHistoricalVelocityIndex == 0) {
            return 0.0f;
        }
        int i = 0;
        int i2 = 0;
        float f = 0.0f;
        while (true) {
            int i3 = HISTORICAL_VELOCITY_COUNT;
            if (i >= i3) {
                return f / i2;
            }
            float f2 = fArr[((this.mHistoricalVelocityIndex - 1) + i) % i3];
            if (f2 != 0.0f) {
                f += f2 * 1;
                i2++;
            }
            i++;
        }
    }

    protected void resetTouchState() {
        this.mItemPressed = false;
        this.mActivePointerId = INVALID_POINTER_ID;
        this.mVelocityTracker.recycle();
        this.mVelocityTracker = null;
        int i = 0;
        while (true) {
            float[] fArr = this.mHistoricalVelocities;
            if (i < fArr.length) {
                fArr[i] = 0.0f;
                i++;
            } else {
                this.mHistoricalVelocityIndex = 0;
                return;
            }
        }
    }

    protected void showForeground(View view) {
        if (view == null) {
            return;
        }
        view.setAlpha(1.0f);
        view.setTranslationX(0.0f);
        view.setVisibility(0);
    }

    protected BitmapDrawable getBitmapDrawableFromView(View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        BitmapDrawable bitmapDrawable = new BitmapDrawable(this.mListView.getResources(), createBitmap);
        bitmapDrawable.setBounds(new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        return bitmapDrawable;
    }
}
