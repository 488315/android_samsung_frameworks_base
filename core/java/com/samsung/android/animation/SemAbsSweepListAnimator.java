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

/* loaded from: classes5.dex */
abstract class SemAbsSweepListAnimator {
    private static final float COSINE_VALUE_THESHOLD = 0.57f;
    private static final boolean DEBUGGABLE = false;
    private static final boolean DEBUGGABLE_LOW = true;
    private static final int DIRECTION_LEFT_TO_RIGHT = 0;
    private static final int DIRECTION_RIGHT_TO_LEFT = 1;
    protected static final int MOVE_DURATION = 150;
    protected static final int SWIPE_DURATION = 600;
    private static final String TAG = "SemAbsSweepListAnimator";
    private float downX;
    private float downY;
    protected float mDownX;
    protected int mForegroundViewResId;
    protected ListView mListView;
    protected VelocityTracker mVelocityTracker;
    protected View mViewToRemoveFg;
    private float upX;
    private float upY;
    private static int INVALID_POINTER_ID = -1;
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

    private View findTouchedView(MotionEvent event) {
        int position = this.mListView.pointToPosition((int) event.getX(), (int) event.getY());
        int firstPosition = this.mListView.getFirstVisiblePosition();
        int wantedChild = position - firstPosition;
        View itemView = this.mListView.getChildAt(wantedChild);
        if (itemView == null) {
            return null;
        }
        if (itemView instanceof ViewGroup) {
            View foregroundView = itemView.findViewById(this.mForegroundViewResId);
            return foregroundView;
        }
        return itemView;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0034 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (isTouchEventSkipped()) {
            return false;
        }
        addVelocityTracker(event);
        switch (event.getAction()) {
            case 0:
                this.mDownX = event.getX();
                handleTouchDownEvent(event);
                return false;
            case 1:
            case 6:
                handleTouchUpEvent(event);
            case 2:
                float xDiff = calculateDistanceX(event);
                return xDiff > ((float) this.mScaledTouchSlop);
            case 3:
                handleTouchCancelEvent(event);
        }
    }

    private boolean handleTouchDownEvent(MotionEvent event) {
        if (this.mListView == null) {
            return false;
        }
        View findTouchedView = findTouchedView(event);
        this.mForegroundView = findTouchedView;
        if (findTouchedView == null) {
            return false;
        }
        int positionForView = this.mListView.getPositionForView(findTouchedView);
        this.mCurrentPosition = positionForView;
        if (positionForView == -1 || positionForView < this.mListView.getFirstVisiblePosition() || this.mCurrentPosition > this.mListView.getLastVisiblePosition()) {
            return false;
        }
        this.downX = event.getX();
        this.downY = event.getY();
        onActionDown(event);
        return true;
    }

    private boolean handleTouchMoveEvent(MotionEvent event) {
        ListView listView = this.mListView;
        if (listView != null && (this.mCurrentPosition < listView.getFirstVisiblePosition() || this.mCurrentPosition > this.mListView.getLastVisiblePosition())) {
            return false;
        }
        ListView listView2 = this.mListView;
        if (listView2 != null) {
            boolean fastScrollOpened = listView2.mSemFastScrollEffectState;
            boolean isScrollStateIdle = this.mListView.semGetLastScrollState() == 0;
            if (fastScrollOpened || !isScrollStateIdle) {
                return false;
            }
        }
        this.upX = event.getX();
        float y = event.getY();
        this.upY = y;
        float deltaX = this.downX - this.upX;
        float deltaY = this.downY - y;
        double sqrtValue = Math.sqrt(Math.abs(deltaX * deltaX) + Math.abs(deltaY * deltaY));
        double cosineValue = Math.cos(Math.abs(deltaX / sqrtValue));
        if (this.mSwiping) {
            onActionMove(event, this.mForegroundView, this.mCurrentPosition);
            trackSweepDistanceAndDirection(event);
            return true;
        }
        if (cosineValue >= 0.5699999928474426d) {
            return false;
        }
        onActionMove(event, this.mForegroundView, this.mCurrentPosition);
        return true;
    }

    private void handleTouchUpEvent(MotionEvent event) {
        int actionIndex = event.getActionIndex();
        int currentPointerId = event.getPointerId(actionIndex);
        if (currentPointerId == this.mActivePointerId && this.mItemPressed) {
            boolean isSweepPattern = sweepPatternIsIndeedFling(this.mVelocityTracker.getXVelocity());
            onActionUp(event, this.mForegroundView, this.mCurrentPosition, isSweepPattern);
        } else {
            Log.m94d(TAG, "handleTouchUpEvent : event.getAction() = " + event.getAction() + ", currentPointerId = " + currentPointerId + ", mActivePointerId = " + this.mActivePointerId + ", mItemPressed = " + this.mItemPressed + ", mSwiping = " + this.mSwiping);
            if (this.mItemPressed && this.mSwiping) {
                Log.m94d(TAG, "handleTouchUpEvent : call onActionCancel to remove remaining sweepBitmap");
                onActionCancel(event, this.mForegroundView, this.mCurrentPosition);
            }
        }
        this.mCurrentPosition = -1;
        initSweepDistanceVariables();
    }

    private boolean handleTouchCancelEvent(MotionEvent event) {
        onActionCancel(event, this.mForegroundView, this.mCurrentPosition);
        initSweepDistanceVariables();
        if (!this.mSwiping) {
            return false;
        }
        this.mSwiping = false;
        this.mListView.removePendingCallbacks();
        return true;
    }

    private boolean isTouchEventSkipped() {
        if ((this.mSwiping && this.mSwipingPosition != this.mCurrentPosition) || !this.mListView.isEnabled()) {
            return true;
        }
        return false;
    }

    private void addVelocityTracker(MotionEvent event) {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
        this.mVelocityTracker.addMovement(event);
    }

    private float calculateDistanceX(MotionEvent event) {
        float diff = Math.abs(this.mDownX - event.getX());
        return diff;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isTouchEventSkipped()) {
            return false;
        }
        addVelocityTracker(event);
        switch (event.getAction()) {
            case 0:
            case 4:
            case 5:
            default:
                return false;
            case 1:
            case 6:
                handleTouchUpEvent(event);
                return false;
            case 2:
                boolean touchConsumed = handleTouchMoveEvent(event);
                return touchConsumed;
            case 3:
                boolean touchConsumed2 = handleTouchCancelEvent(event);
                return touchConsumed2;
        }
    }

    private void initSweepDistanceVariables() {
        this.mSweepLeftDistance = 0.0f;
        this.mSweepRightDistance = 0.0f;
        this.mSweepPrevPosX = -1.0f;
        this.mSweepDirection = -1;
    }

    private String getCurrentSweepDirection(int sweepDirection) {
        switch (sweepDirection) {
            case 0:
                return "Left to Right";
            case 1:
                return "Right to Left";
            default:
                return "No direction";
        }
    }

    private void trackSweepDistanceAndDirection(MotionEvent event) {
        if (this.mSweepPrevPosX == -1.0f) {
            Log.m94d(TAG, "trackSweepDistanceAndDirection : first calling trackSweepDistanceAndDirection");
            Log.m94d(TAG, "trackSweepDistanceAndDirection : mSweepPrevPosX is set to mDownX, mSweepPrevPosX = " + this.mDownX);
            this.mSweepPrevPosX = this.mDownX;
        }
        float f = this.mSweepPrevPosX;
        if (f != -1.0f) {
            if (f > event.getX()) {
                Log.m94d(TAG, "trackSweepDistanceAndDirection : sweep to left");
                this.mSweepDirection = 1;
                this.mSweepLeftDistance += this.mSweepPrevPosX - event.getX();
            } else if (this.mSweepPrevPosX < event.getX()) {
                Log.m94d(TAG, "trackSweepDistanceAndDirection : sweep to right");
                this.mSweepDirection = 0;
                this.mSweepRightDistance += event.getX() - this.mSweepPrevPosX;
            }
        }
        int i = this.mPrevSweepDirection;
        if (i != -1 && i != this.mSweepDirection) {
            Log.m94d(TAG, "trackSweepDistanceAndDirection : SweepDirection is changed");
            Log.m94d(TAG, "trackSweepDistanceAndDirection : changed direction = " + getCurrentSweepDirection(this.mSweepDirection));
            int i2 = this.mSweepDirection;
            if (i2 == 1) {
                Log.m94d(TAG, "trackSweepDistanceAndDirection : Set mSweepRightDistance = 0");
                this.mSweepRightDistance = 0.0f;
            } else if (i2 == 0) {
                Log.m94d(TAG, "trackSweepDistanceAndDirection : Set mSweepLeftDistance = 0");
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
            Log.m94d(TAG, "trackSweepDistanceAndDirection : Clear velocityTracker");
        }
        this.mPrevSweepDirection = this.mSweepDirection;
        this.mSweepPrevPosX = event.getX();
    }

    private boolean sweepPatternIsIndeedFling(float velocity) {
        Log.m94d(TAG, "***** Start sweepPatternIsIndeedFling *****");
        Log.m94d(TAG, "sweepPatternIsIndeedFling : velocity =" + velocity);
        Log.m94d(TAG, "sweepPatternIsIndeedFling : mSweepRightDistance = " + this.mSweepRightDistance);
        Log.m94d(TAG, "sweepPatternIsIndeedFling : mSweepLeftDistance = " + this.mSweepLeftDistance);
        int minimalDistanceThreshold = this.mScaledTouchSlop * 2;
        Log.m94d(TAG, "sweepPatternIsIndeedFling : minimalDistanceThreshold = " + minimalDistanceThreshold);
        if ((velocity > 0.0f && this.mSweepRightDistance < minimalDistanceThreshold) || (velocity < 0.0f && this.mSweepLeftDistance < minimalDistanceThreshold)) {
            Log.m94d(TAG, "sweepPatternIsIndeedFling : SweepDistance is less than minDistance, return false #1");
            Log.m94d(TAG, "***** End sweepPatternIsIndeedFling *****");
            return false;
        }
        Log.m94d(TAG, "sweepPatternIsIndeedFling : return true #2");
        Log.m94d(TAG, "***** End sweepPatternIsIndeedFling *****");
        return true;
    }

    protected float getAdjustedVelocityX(float[] mHistoricalVelocities) {
        if (this.mHistoricalVelocityIndex == 0) {
            return 0.0f;
        }
        float totalVelocity = 0.0f;
        int totalWeight = 0;
        int i = 0;
        while (true) {
            int i2 = HISTORICAL_VELOCITY_COUNT;
            if (i < i2) {
                float vel = mHistoricalVelocities[((this.mHistoricalVelocityIndex - 1) + i) % i2];
                if (vel != 0.0f) {
                    totalVelocity += 1 * vel;
                    totalWeight++;
                }
                i++;
            } else {
                return totalVelocity / totalWeight;
            }
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

    protected void showForeground(View viewForeground) {
        if (viewForeground == null) {
            return;
        }
        viewForeground.setAlpha(1.0f);
        viewForeground.setTranslationX(0.0f);
        viewForeground.setVisibility(0);
    }

    protected BitmapDrawable getBitmapDrawableFromView(View view) {
        Bitmap b = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(b);
        view.draw(canvas);
        BitmapDrawable bd = new BitmapDrawable(this.mListView.getResources(), b);
        bd.setBounds(new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        return bd;
    }
}
