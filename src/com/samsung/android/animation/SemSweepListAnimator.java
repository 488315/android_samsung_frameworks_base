package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ListView;

@Deprecated
/* loaded from: classes6.dex */
public class SemSweepListAnimator extends SemAbsSweepListAnimator {
    private static final boolean DEBUGGABLE = false;
    private static final boolean DEBUGGABLE_LOW = true;
    private static final int FADEOUT_DURATION = 300;
    public static final int SWEEP_ANIMATION_TRANSLATION = 2;
    public static final int SWEEP_ANIMATION_WAVE = 1;
    private static final String TAG = "SemSweepListAnimator";
    private Context mContext;
    private OnSweepListener mOnSweepListener;
    private SemAbsSweepAnimationFilter mSweepAnimationFilter = null;
    private int mSweepAnimationType = -1;
    private SweepConfiguration mCurrentSweepConfig = null;
    private float mPreviousDeltaX = 0.0f;
    private boolean mSkipActionUpAnimation = false;
    private boolean mEnableSweep = true;
    private BitmapDrawable mSweepBdToFade = null;

    public interface OnSweepListener {
        void onSweep(int i, float f, Canvas canvas);

        void onSweepEnd(int i, float f);

        SweepConfiguration onSweepStart(int i, float f, Rect rect);
    }

    @Override // com.samsung.android.animation.SemAbsSweepListAnimator
    public /* bridge */ /* synthetic */ boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return super.onInterceptTouchEvent(motionEvent);
    }

    @Override // com.samsung.android.animation.SemAbsSweepListAnimator
    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent motionEvent) {
        return super.onTouchEvent(motionEvent);
    }

    public void setSweepAnimatorEnabled(boolean z) {
        this.mEnableSweep = z;
    }

    public boolean isSweepAnimatorEnabled() {
        return this.mEnableSweep;
    }

    public SemSweepListAnimator(Context context, ListView listView, int i) {
        if (context == null) {
            throw new IllegalArgumentException("Context cannot be null");
        }
        if (i <= 0) {
            throw new IllegalArgumentException("Resource ids should be positive integer");
        }
        this.mContext = context;
        this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.mForegroundViewResId = i;
        this.mListView = listView;
        if (this.mListView != null) {
            this.mListView.setSweepListAnimator(this);
        }
    }

    public void setSweepAnimationType(int i) {
        this.mSweepAnimationType = i;
        if (i == 1) {
            this.mSweepAnimationFilter = new SemSweepWaveFilter(this.mListView);
        } else {
            if (i != 2) {
                return;
            }
            this.mSweepAnimationFilter = new SemSweepTranslationFilter(this.mListView, this.mContext);
        }
    }

    public boolean isSwiping() {
        return this.mSwiping;
    }

    @Override // com.samsung.android.animation.SemAbsSweepListAnimator
    public void onActionDown(MotionEvent motionEvent) {
        this.mActivePointerId = motionEvent.getPointerId(0);
        this.mItemPressed = true;
        this.mDownX = motionEvent.getX();
    }

    @Override // com.samsung.android.animation.SemAbsSweepListAnimator
    public void onActionMove(MotionEvent motionEvent, View view, int i) {
        SweepConfiguration sweepConfiguration;
        float x = motionEvent.getX() - this.mDownX;
        float fAbs = Math.abs(x);
        if (this.mSwiping) {
            if (view == null || (sweepConfiguration = this.mCurrentSweepConfig) == null) {
                return;
            }
            if ((sweepConfiguration.allowLeftToRight && x >= 0.0f) || (this.mCurrentSweepConfig.allowRightToLeft && x <= 0.0f)) {
                this.mSweepAnimationFilter.doMoveAction(view, x, i);
            } else if (Math.signum(this.mPreviousDeltaX) != Math.signum(x) && this.mSweepAnimationType == 2) {
                view.setTranslationX(0.0f);
                view.setAlpha(1.0f);
                Rect bitmapDrawableBound = this.mSweepAnimationFilter.getBitmapDrawableBound();
                if (this.mListView != null && bitmapDrawableBound != null) {
                    resetSweepAnimationFilter();
                    this.mListView.invalidate(new Rect(bitmapDrawableBound));
                }
                this.mSkipActionUpAnimation = true;
            }
            this.mVelocityTracker.computeCurrentVelocity(VELOCITY_UNITS);
            float[] fArr = this.mHistoricalVelocities;
            int i2 = this.mHistoricalVelocityIndex;
            this.mHistoricalVelocityIndex = i2 + 1;
            fArr[i2 % HISTORICAL_VELOCITY_COUNT] = this.mVelocityTracker.getXVelocity();
            return;
        }
        if (fAbs > this.mScaledTouchSlop) {
            this.mDownX = motionEvent.getX();
            this.mSwiping = true;
            this.mSwipingPosition = i;
            if (this.mListView != null) {
                this.mListView.requestDisallowInterceptTouchEvent(true);
                this.mListView.removePendingCallbacks();
            }
            this.mPreviousDeltaX = x;
            if (this.mOnSweepListener == null || view == null) {
                return;
            }
            SweepConfiguration sweepConfigurationOnSweepStart = this.mOnSweepListener.onSweepStart(i, 0.0f, new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
            this.mCurrentSweepConfig = sweepConfigurationOnSweepStart;
            if (this.mSweepAnimationFilter == null || sweepConfigurationOnSweepStart == null) {
                return;
            }
            if ((sweepConfigurationOnSweepStart.allowLeftToRight && x >= 0.0f) || (this.mCurrentSweepConfig.allowRightToLeft && x <= 0.0f)) {
                if (this.mCurrentSweepConfig.childIdForLocationHint != 0 && view.findViewById(this.mCurrentSweepConfig.childIdForLocationHint) != null) {
                    view = view.findViewById(this.mCurrentSweepConfig.childIdForLocationHint);
                    this.mForegroundView = view;
                }
                View view2 = view;
                if (this.mListView != null) {
                    Drawable selector = this.mListView.getSelector();
                    this.mListView.setPressed(false);
                    selector.jumpToCurrentState();
                }
                this.mSweepAnimationFilter.initAnimationFilter(view2, x, i, this.mOnSweepListener, this.mCurrentSweepConfig);
                return;
            }
            this.mSwiping = false;
            Log.d(TAG, "onActionMove : send onSweepEnd #4");
            this.mOnSweepListener.onSweepEnd(i, 0.0f);
            this.mSweepAnimationFilter.setForegroundView(view);
        }
    }

    @Override // com.samsung.android.animation.SemAbsSweepListAnimator
    public void onActionUp(MotionEvent motionEvent, final View view, final int i, boolean z) {
        boolean z2 = true;
        if (this.mSkipActionUpAnimation) {
            this.mSkipActionUpAnimation = false;
            this.mSwiping = false;
            this.mSwipingPosition = -1;
            this.mListView.setEnabled(true);
            resetTouchState();
            OnSweepListener onSweepListener = this.mOnSweepListener;
            if (onSweepListener != null) {
                onSweepListener.onSweepEnd(i, Math.signum(this.mSweepAnimationFilter.getEndXOfActionUpAnimator()));
                return;
            }
            return;
        }
        if (!this.mSwiping) {
            z2 = false;
        } else {
            if (view == null) {
                Log.d(TAG, "onActionUp : viewForeground = " + view);
                Log.d(TAG, "**** End onActionUp *****, return #1");
                return;
            }
            float x = motionEvent.getX() - this.mDownX;
            final int width = view.getWidth();
            float adjustedVelocityX = getAdjustedVelocityX(this.mHistoricalVelocities);
            Log.d(TAG, "onActionUp : viewForeground = " + view);
            Log.d(TAG, "onActionUp : adjustedVelocityX = " + adjustedVelocityX);
            Log.d(TAG, "onActionUp : mScaledTouchSlop = " + this.mScaledTouchSlop);
            Log.d(TAG, "onActionUp : deltaX = " + x);
            Log.d(TAG, "onActionUp : isSweepPattern = " + z);
            Log.d(TAG, "onActionUp : mSweepAnimationFilter = " + this.mSweepAnimationFilter);
            if (this.mSweepAnimationFilter == null) {
                Log.d(TAG, "onActionUp : mSweepAnimationFilter is null");
                Log.d(TAG, "**** End onActionUp *****, return #2");
                return;
            }
            Log.d(TAG, "onActionUp : create sweepAnimation.. #1");
            ValueAnimator valueAnimatorCreateActionUpAnimator = this.mSweepAnimationFilter.createActionUpAnimator(view, adjustedVelocityX, this.mScaledTouchSlop, x, z);
            valueAnimatorCreateActionUpAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemSweepListAnimator.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) {
                    Log.d(SemSweepListAnimator.TAG, "animator : onAnimationStart");
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (SemSweepListAnimator.this.mSweepAnimationFilter != null && !SemSweepListAnimator.this.mSweepAnimationFilter.isAnimationBack() && SemSweepListAnimator.this.mSweepAnimationType == 2) {
                        Log.d(SemSweepListAnimator.TAG, "onActionUp : animator : onAnimationEnd : prepare copy bitmap to animate fade.. ");
                        BitmapDrawable sweepBitmapDrawable = ((SemSweepTranslationFilter) SemSweepListAnimator.this.mSweepAnimationFilter).getSweepBitmapDrawable();
                        if (sweepBitmapDrawable == null || sweepBitmapDrawable.getBitmap() == null) {
                            SemSweepListAnimator.this.resetSweepInfo();
                            if (SemSweepListAnimator.this.mOnSweepListener != null) {
                                Log.d(SemSweepListAnimator.TAG, "onActionUp : animator : onAnimationEnd : send onSweepEnd #1");
                                SemSweepListAnimator.this.mOnSweepListener.onSweepEnd(i, Math.signum(SemSweepListAnimator.this.mSweepAnimationFilter.getEndXOfActionUpAnimator()));
                            }
                            SemSweepListAnimator.this.resetSweepAnimationFilter();
                            Log.d(SemSweepListAnimator.TAG, "onActionUp : animator : onAnimationEnd : failed getBitmap() and so can not copy bitmap, return");
                            return;
                        }
                        final Bitmap bitmapCopy = sweepBitmapDrawable.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                        SemSweepListAnimator.this.mSweepBdToFade = new BitmapDrawable(SemSweepListAnimator.this.mContext.getResources(), bitmapCopy);
                        SemSweepListAnimator.this.mSweepBdToFade.setBounds(sweepBitmapDrawable.getBounds());
                        if (SemSweepListAnimator.this.mSweepBdToFade != null) {
                            Log.d(SemSweepListAnimator.TAG, "animator : create fadeOut animator #2");
                            Log.d(SemSweepListAnimator.TAG, "animator : sweepBdToFade = " + SemSweepListAnimator.this.mSweepBdToFade);
                            ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(255, 0);
                            valueAnimatorOfInt.setDuration(300L);
                            valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemSweepListAnimator.1.1
                                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                    if (SemSweepListAnimator.this.mSweepBdToFade == null || SemSweepListAnimator.this.mListView == null) {
                                        return;
                                    }
                                    SemSweepListAnimator.this.mSweepBdToFade.setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                                    SemSweepListAnimator.this.mListView.invalidate(SemSweepListAnimator.this.mSweepBdToFade.getBounds());
                                }
                            });
                            valueAnimatorOfInt.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemSweepListAnimator.1.2
                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationStart(Animator animator2) {
                                    Log.d(SemSweepListAnimator.TAG, "fadeOutAnimator : onAnimationStart");
                                }

                                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                                public void onAnimationEnd(Animator animator2) {
                                    Log.d(SemSweepListAnimator.TAG, "fadeOutAnimator : onAnimationEnd");
                                    SemSweepListAnimator.this.resetSweepInfo();
                                    if (SemSweepListAnimator.this.mOnSweepListener != null) {
                                        Log.d(SemSweepListAnimator.TAG, "fadeOutAnimator : onAnimationEnd : send onSweepEnd #2");
                                        SemSweepListAnimator.this.mOnSweepListener.onSweepEnd(i, Math.signum(SemSweepListAnimator.this.mSweepAnimationFilter.getEndXOfActionUpAnimator()));
                                    }
                                    if (SemSweepListAnimator.this.mSweepBdToFade != null) {
                                        Bitmap bitmap = SemSweepListAnimator.this.mSweepBdToFade.getBitmap();
                                        if (bitmap != null) {
                                            Log.d(SemSweepListAnimator.TAG, "fadeOutAnimator : onAnimationEnd : recycle mSweepBdToFade");
                                            bitmap.recycle();
                                        }
                                        SemSweepListAnimator.this.mSweepBdToFade = null;
                                    }
                                    if (bitmapCopy != null) {
                                        Log.d(SemSweepListAnimator.TAG, "fadeOutAnimator : onAnimationEnd : recycle copiedBitmap");
                                        bitmapCopy.recycle();
                                    }
                                }
                            });
                            Log.d(SemSweepListAnimator.TAG, "animator : onAnimationEnd : fadeOutAnimator.start()");
                            valueAnimatorOfInt.start();
                        }
                    } else {
                        Log.d(SemSweepListAnimator.TAG, "animator : onAnimationEnd : Animation is back, call resetSweepInfo()");
                        SemSweepListAnimator.this.resetSweepInfo();
                        if (SemSweepListAnimator.this.mOnSweepListener != null) {
                            Log.d(SemSweepListAnimator.TAG, "animator : onAnimationEnd : send onSweepEnd #3");
                            if (SemSweepListAnimator.this.mSweepAnimationFilter != null) {
                                SemSweepListAnimator.this.mOnSweepListener.onSweepEnd(i, Math.signum(SemSweepListAnimator.this.mSweepAnimationFilter.getEndXOfActionUpAnimator()));
                            }
                        }
                    }
                    Log.d(SemSweepListAnimator.TAG, "animator : onAnimationEnd : call resetSweepAnimationFilter ");
                    SemSweepListAnimator.this.resetSweepAnimationFilter();
                }
            });
            if (this.mOnSweepListener != null) {
                valueAnimatorCreateActionUpAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemSweepListAnimator.2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float fFloatValue;
                        if (SemSweepListAnimator.this.mSweepAnimationType == 2) {
                            fFloatValue = view.getTranslationX() / width;
                        } else {
                            fFloatValue = SemSweepListAnimator.this.mSweepAnimationType == 1 ? ((Float) valueAnimator.getAnimatedValue()).floatValue() : -1.0f;
                        }
                        SemSweepListAnimator.this.mSweepAnimationFilter.doUpActionWhenAnimationUpdate(i, fFloatValue);
                    }
                });
            }
            this.mListView.setEnabled(false);
            Log.d(TAG, "onActionUp : call animator.start()");
            valueAnimatorCreateActionUpAnimator.start();
            Drawable selector = this.mListView.getSelector();
            if ((selector instanceof StateListDrawable) && this.mSweepAnimationType == 2) {
                Drawable current = ((StateListDrawable) selector).getCurrent();
                if (current instanceof RippleDrawable) {
                    ((RippleDrawable) current).jumpToCurrentState();
                }
            }
        }
        resetTouchState();
        if (this.mSwiping || z2) {
            return;
        }
        resetSweepAnimationFilter();
    }

    @Override // com.samsung.android.animation.SemAbsSweepListAnimator
    public void onActionCancel(MotionEvent motionEvent, View view, int i) {
        if (this.mSwiping && this.mOnSweepListener != null) {
            float x = (motionEvent.getX() - this.mDownX) / view.getWidth();
            Log.d(TAG, "onActionCancel : position = " + i + ", sweepProgress = " + x);
            this.mOnSweepListener.onSweepEnd(i, x);
        }
        showForeground(view);
        this.mSwiping = false;
        resetTouchState();
        resetSweepAnimationFilter();
        this.mListView.setPressed(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetSweepInfo() {
        this.mSwiping = false;
        this.mSwipingPosition = -1;
        if (this.mListView != null) {
            this.mListView.setEnabled(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetSweepAnimationFilter() {
        SemAbsSweepAnimationFilter semAbsSweepAnimationFilter = this.mSweepAnimationFilter;
        if (semAbsSweepAnimationFilter != null) {
            semAbsSweepAnimationFilter.doRefresh();
        }
    }

    public void setOnSweepListener(OnSweepListener onSweepListener) {
        this.mOnSweepListener = onSweepListener;
    }

    public static class SweepConfiguration {
        public boolean allowLeftToRight;
        public boolean allowRightToLeft;
        public int backgroundColorLeftToRight;
        public int backgroundColorRightToLeft;
        public int childIdForLocationHint;
        public Drawable drawableLeftToRight;
        public int drawablePadding;
        public Drawable drawableRightToLeft;
        public String textLeftToRight;
        public String textRightToLeft;
        public float textSize;

        public SweepConfiguration(boolean z, boolean z2, int i) {
            this.allowLeftToRight = z;
            this.allowRightToLeft = z2;
            this.childIdForLocationHint = i;
        }

        public SweepConfiguration(boolean z, boolean z2) {
            this(z, z2, 0);
        }

        public SweepConfiguration() {
            this(true, true, 0);
        }
    }

    public void draw(Canvas canvas) {
        if (this.mSwiping) {
            this.mSweepAnimationFilter.draw(canvas);
        }
        BitmapDrawable bitmapDrawable = this.mSweepBdToFade;
        if (bitmapDrawable != null) {
            bitmapDrawable.draw(canvas);
        }
    }

    @Override // com.samsung.android.animation.SemAbsSweepListAnimator
    public void setForegroundViewResId(int i) {
        this.mForegroundViewResId = i;
    }
}
