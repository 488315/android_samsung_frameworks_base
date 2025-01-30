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
/* loaded from: classes5.dex */
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

  public void setSweepAnimatorEnabled(boolean enabled) {
    this.mEnableSweep = enabled;
  }

  public boolean isSweepAnimatorEnabled() {
    return this.mEnableSweep;
  }

  public SemSweepListAnimator(Context context, ListView listView, int foregroundViewResId) {
    if (context == null) {
      throw new IllegalArgumentException("Context cannot be null");
    }
    if (foregroundViewResId <= 0) {
      throw new IllegalArgumentException("Resource ids should be positive integer");
    }
    this.mContext = context;
    this.mScaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    this.mForegroundViewResId = foregroundViewResId;
    this.mListView = listView;
    if (this.mListView != null) {
      this.mListView.setSweepListAnimator(this);
    }
  }

  public void setSweepAnimationType(int sweepAnimationType) {
    this.mSweepAnimationType = sweepAnimationType;
    switch (sweepAnimationType) {
      case 1:
        this.mSweepAnimationFilter = new SemSweepWaveFilter(this.mListView);
        break;
      case 2:
        this.mSweepAnimationFilter = new SemSweepTranslationFilter(this.mListView, this.mContext);
        break;
    }
  }

  public boolean isSwiping() {
    return this.mSwiping;
  }

  @Override // com.samsung.android.animation.SemAbsSweepListAnimator
  public void onActionDown(MotionEvent event) {
    this.mActivePointerId = event.getPointerId(0);
    this.mItemPressed = true;
    this.mDownX = event.getX();
  }

  @Override // com.samsung.android.animation.SemAbsSweepListAnimator
  public void onActionMove(MotionEvent event, View viewForeground, int position) {
    View v;
    SweepConfiguration sweepConfiguration;
    float x = event.getX();
    float deltaX = x - this.mDownX;
    float deltaXAbs = Math.abs(deltaX);
    if (this.mSwiping) {
      if (viewForeground == null || (sweepConfiguration = this.mCurrentSweepConfig) == null) {
        return;
      }
      if ((sweepConfiguration.allowLeftToRight && deltaX >= 0.0f)
          || (this.mCurrentSweepConfig.allowRightToLeft && deltaX <= 0.0f)) {
        this.mSweepAnimationFilter.doMoveAction(viewForeground, deltaX, position);
      } else if (Math.signum(this.mPreviousDeltaX) != Math.signum(deltaX)
          && this.mSweepAnimationType == 2) {
        viewForeground.setTranslationX(0.0f);
        viewForeground.setAlpha(1.0f);
        Rect bitmapDrawableBound = this.mSweepAnimationFilter.getBitmapDrawableBound();
        if (this.mListView != null && bitmapDrawableBound != null) {
          resetSweepAnimationFilter();
          this.mListView.invalidate(new Rect(bitmapDrawableBound));
        }
        this.mSkipActionUpAnimation = true;
      }
      this.mVelocityTracker.computeCurrentVelocity(VELOCITY_UNITS);
      float[] fArr = this.mHistoricalVelocities;
      int i = this.mHistoricalVelocityIndex;
      this.mHistoricalVelocityIndex = i + 1;
      fArr[i % HISTORICAL_VELOCITY_COUNT] = this.mVelocityTracker.getXVelocity();
      return;
    }
    if (deltaXAbs > this.mScaledTouchSlop) {
      this.mDownX = event.getX();
      this.mSwiping = true;
      this.mSwipingPosition = position;
      if (this.mListView != null) {
        this.mListView.requestDisallowInterceptTouchEvent(true);
        this.mListView.removePendingCallbacks();
      }
      this.mPreviousDeltaX = deltaX;
      if (this.mOnSweepListener != null && viewForeground != null) {
        Rect rect =
            new Rect(
                viewForeground.getLeft(),
                viewForeground.getTop(),
                viewForeground.getRight(),
                viewForeground.getBottom());
        SweepConfiguration onSweepStart = this.mOnSweepListener.onSweepStart(position, 0.0f, rect);
        this.mCurrentSweepConfig = onSweepStart;
        if (this.mSweepAnimationFilter != null && onSweepStart != null) {
          if ((onSweepStart.allowLeftToRight && deltaX >= 0.0f)
              || (this.mCurrentSweepConfig.allowRightToLeft && deltaX <= 0.0f)) {
            if (this.mCurrentSweepConfig.childIdForLocationHint != 0
                && viewForeground.findViewById(this.mCurrentSweepConfig.childIdForLocationHint)
                    != null) {
              View v2 =
                  viewForeground.findViewById(this.mCurrentSweepConfig.childIdForLocationHint);
              this.mForegroundView = v2;
              v = v2;
            } else {
              v = viewForeground;
            }
            View v3 = this.mListView;
            if (v3 != null) {
              Drawable listViewSelector = this.mListView.getSelector();
              this.mListView.setPressed(false);
              listViewSelector.jumpToCurrentState();
            }
            this.mSweepAnimationFilter.initAnimationFilter(
                v, deltaX, position, this.mOnSweepListener, this.mCurrentSweepConfig);
            return;
          }
          this.mSwiping = false;
          Log.m94d(TAG, "onActionMove : send onSweepEnd #4");
          this.mOnSweepListener.onSweepEnd(position, 0.0f);
          this.mSweepAnimationFilter.setForegroundView(viewForeground);
        }
      }
    }
  }

  @Override // com.samsung.android.animation.SemAbsSweepListAnimator
  public void onActionUp(
      MotionEvent event, final View viewForeground, final int position, boolean isSweepPattern) {
    boolean animationStarted = false;
    if (this.mSkipActionUpAnimation) {
      this.mSkipActionUpAnimation = false;
      this.mSwiping = false;
      this.mSwipingPosition = -1;
      this.mListView.setEnabled(true);
      resetTouchState();
      OnSweepListener onSweepListener = this.mOnSweepListener;
      if (onSweepListener != null) {
        onSweepListener.onSweepEnd(
            position, Math.signum(this.mSweepAnimationFilter.getEndXOfActionUpAnimator()));
        return;
      }
      return;
    }
    if (this.mSwiping) {
      if (viewForeground == null) {
        Log.m94d(TAG, "onActionUp : viewForeground = " + viewForeground);
        Log.m94d(TAG, "**** End onActionUp *****, return #1");
        return;
      }
      float x = event.getX();
      float x2 = this.mDownX;
      float deltaX = x - x2;
      final int width = viewForeground.getWidth();
      float adjustedVelocityX = getAdjustedVelocityX(this.mHistoricalVelocities);
      Log.m94d(TAG, "onActionUp : viewForeground = " + viewForeground);
      Log.m94d(TAG, "onActionUp : adjustedVelocityX = " + adjustedVelocityX);
      Log.m94d(TAG, "onActionUp : mScaledTouchSlop = " + this.mScaledTouchSlop);
      Log.m94d(TAG, "onActionUp : deltaX = " + deltaX);
      Log.m94d(TAG, "onActionUp : isSweepPattern = " + isSweepPattern);
      Log.m94d(TAG, "onActionUp : mSweepAnimationFilter = " + this.mSweepAnimationFilter);
      if (this.mSweepAnimationFilter == null) {
        Log.m94d(TAG, "onActionUp : mSweepAnimationFilter is null");
        Log.m94d(TAG, "**** End onActionUp *****, return #2");
        return;
      }
      Log.m94d(TAG, "onActionUp : create sweepAnimation.. #1");
      ValueAnimator animator =
          this.mSweepAnimationFilter.createActionUpAnimator(
              viewForeground, adjustedVelocityX, this.mScaledTouchSlop, deltaX, isSweepPattern);
      animator.addListener(
          new AnimatorListenerAdapter() { // from class:
            // com.samsung.android.animation.SemSweepListAnimator.1
            @Override // android.animation.AnimatorListenerAdapter,
            // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animation) {
              Log.m94d(SemSweepListAnimator.TAG, "animator : onAnimationStart");
            }

            @Override // android.animation.AnimatorListenerAdapter,
            // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animation) {
              if (SemSweepListAnimator.this.mSweepAnimationFilter != null
                  && !SemSweepListAnimator.this.mSweepAnimationFilter.isAnimationBack()
                  && SemSweepListAnimator.this.mSweepAnimationType == 2) {
                Log.m94d(
                    SemSweepListAnimator.TAG,
                    "onActionUp : animator : onAnimationEnd : prepare copy bitmap to animate fade.."
                        + " ");
                BitmapDrawable tempBd =
                    ((SemSweepTranslationFilter) SemSweepListAnimator.this.mSweepAnimationFilter)
                        .getSweepBitmapDrawable();
                if (tempBd == null || tempBd.getBitmap() == null) {
                  SemSweepListAnimator.this.resetSweepInfo();
                  if (SemSweepListAnimator.this.mOnSweepListener != null) {
                    Log.m94d(
                        SemSweepListAnimator.TAG,
                        "onActionUp : animator : onAnimationEnd : send onSweepEnd #1");
                    SemSweepListAnimator.this.mOnSweepListener.onSweepEnd(
                        position,
                        Math.signum(
                            SemSweepListAnimator.this.mSweepAnimationFilter
                                .getEndXOfActionUpAnimator()));
                  }
                  SemSweepListAnimator.this.resetSweepAnimationFilter();
                  Log.m94d(
                      SemSweepListAnimator.TAG,
                      "onActionUp : animator : onAnimationEnd : failed getBitmap() and so can not"
                          + " copy bitmap, return");
                  return;
                }
                final Bitmap copiedBitmap = tempBd.getBitmap().copy(Bitmap.Config.ARGB_8888, true);
                SemSweepListAnimator.this.mSweepBdToFade =
                    new BitmapDrawable(
                        SemSweepListAnimator.this.mContext.getResources(), copiedBitmap);
                SemSweepListAnimator.this.mSweepBdToFade.setBounds(tempBd.getBounds());
                if (SemSweepListAnimator.this.mSweepBdToFade != null) {
                  Log.m94d(SemSweepListAnimator.TAG, "animator : create fadeOut animator #2");
                  Log.m94d(
                      SemSweepListAnimator.TAG,
                      "animator : sweepBdToFade = " + SemSweepListAnimator.this.mSweepBdToFade);
                  ValueAnimator fadeOutAnimator = ValueAnimator.ofInt(255, 0);
                  fadeOutAnimator.setDuration(300L);
                  fadeOutAnimator.addUpdateListener(
                      new ValueAnimator.AnimatorUpdateListener() { // from class:
                        // com.samsung.android.animation.SemSweepListAnimator.1.1
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator anim) {
                          if (SemSweepListAnimator.this.mSweepBdToFade != null
                              && SemSweepListAnimator.this.mListView != null) {
                            int animatedValue = ((Integer) anim.getAnimatedValue()).intValue();
                            SemSweepListAnimator.this.mSweepBdToFade.setAlpha(animatedValue);
                            SemSweepListAnimator.this.mListView.invalidate(
                                SemSweepListAnimator.this.mSweepBdToFade.getBounds());
                          }
                        }
                      });
                  fadeOutAnimator.addListener(
                      new AnimatorListenerAdapter() { // from class:
                        // com.samsung.android.animation.SemSweepListAnimator.1.2
                        @Override // android.animation.AnimatorListenerAdapter,
                        // android.animation.Animator.AnimatorListener
                        public void onAnimationStart(Animator animation2) {
                          Log.m94d(SemSweepListAnimator.TAG, "fadeOutAnimator : onAnimationStart");
                        }

                        @Override // android.animation.AnimatorListenerAdapter,
                        // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animation2) {
                          Log.m94d(SemSweepListAnimator.TAG, "fadeOutAnimator : onAnimationEnd");
                          SemSweepListAnimator.this.resetSweepInfo();
                          if (SemSweepListAnimator.this.mOnSweepListener != null) {
                            Log.m94d(
                                SemSweepListAnimator.TAG,
                                "fadeOutAnimator : onAnimationEnd : send onSweepEnd #2");
                            SemSweepListAnimator.this.mOnSweepListener.onSweepEnd(
                                position,
                                Math.signum(
                                    SemSweepListAnimator.this.mSweepAnimationFilter
                                        .getEndXOfActionUpAnimator()));
                          }
                          if (SemSweepListAnimator.this.mSweepBdToFade != null) {
                            Bitmap b = SemSweepListAnimator.this.mSweepBdToFade.getBitmap();
                            if (b != null) {
                              Log.m94d(
                                  SemSweepListAnimator.TAG,
                                  "fadeOutAnimator : onAnimationEnd : recycle mSweepBdToFade");
                              b.recycle();
                            }
                            SemSweepListAnimator.this.mSweepBdToFade = null;
                          }
                          if (copiedBitmap != null) {
                            Log.m94d(
                                SemSweepListAnimator.TAG,
                                "fadeOutAnimator : onAnimationEnd : recycle copiedBitmap");
                            copiedBitmap.recycle();
                          }
                        }
                      });
                  Log.m94d(
                      SemSweepListAnimator.TAG,
                      "animator : onAnimationEnd : fadeOutAnimator.start()");
                  fadeOutAnimator.start();
                }
              } else {
                Log.m94d(
                    SemSweepListAnimator.TAG,
                    "animator : onAnimationEnd : Animation is back, call resetSweepInfo()");
                SemSweepListAnimator.this.resetSweepInfo();
                if (SemSweepListAnimator.this.mOnSweepListener != null) {
                  Log.m94d(
                      SemSweepListAnimator.TAG, "animator : onAnimationEnd : send onSweepEnd #3");
                  SemSweepListAnimator.this.mOnSweepListener.onSweepEnd(
                      position,
                      Math.signum(
                          SemSweepListAnimator.this.mSweepAnimationFilter
                              .getEndXOfActionUpAnimator()));
                }
              }
              Log.m94d(
                  SemSweepListAnimator.TAG,
                  "animator : onAnimationEnd : call resetSweepAnimationFilter ");
              SemSweepListAnimator.this.resetSweepAnimationFilter();
            }
          });
      if (this.mOnSweepListener != null) {
        animator.addUpdateListener(
            new ValueAnimator
                .AnimatorUpdateListener() { // from class:
                                            // com.samsung.android.animation.SemSweepListAnimator.2
              @Override // android.animation.ValueAnimator.AnimatorUpdateListener
              public void onAnimationUpdate(ValueAnimator anim) {
                float sweeprogress = -1.0f;
                if (SemSweepListAnimator.this.mSweepAnimationType == 2) {
                  sweeprogress = viewForeground.getTranslationX() / width;
                } else if (SemSweepListAnimator.this.mSweepAnimationType == 1) {
                  sweeprogress = ((Float) anim.getAnimatedValue()).floatValue();
                }
                SemSweepListAnimator.this.mSweepAnimationFilter.doUpActionWhenAnimationUpdate(
                    position, sweeprogress);
              }
            });
      }
      this.mListView.setEnabled(false);
      Log.m94d(TAG, "onActionUp : call animator.start()");
      animator.start();
      Drawable listViewSelector = this.mListView.getSelector();
      if ((listViewSelector instanceof StateListDrawable) && this.mSweepAnimationType == 2) {
        Drawable currentDrawable = ((StateListDrawable) listViewSelector).getCurrent();
        if (currentDrawable instanceof RippleDrawable) {
          ((RippleDrawable) currentDrawable).jumpToCurrentState();
        }
      }
      animationStarted = true;
    }
    resetTouchState();
    if (!this.mSwiping && !animationStarted) {
      resetSweepAnimationFilter();
    }
  }

  @Override // com.samsung.android.animation.SemAbsSweepListAnimator
  public void onActionCancel(MotionEvent event, View viewForeground, int position) {
    if (this.mSwiping && this.mOnSweepListener != null) {
      float x = event.getX();
      float deltaX = x - this.mDownX;
      float sweepProgress = deltaX / viewForeground.getWidth();
      Log.m94d(
          TAG, "onActionCancel : position = " + position + ", sweepProgress = " + sweepProgress);
      this.mOnSweepListener.onSweepEnd(position, sweepProgress);
    }
    showForeground(viewForeground);
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

  public void setOnSweepListener(OnSweepListener listener) {
    this.mOnSweepListener = listener;
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

    public SweepConfiguration(
        boolean allowLeftToRight, boolean allowRightToLeft, int childIdForLocationHint) {
      this.allowLeftToRight = allowLeftToRight;
      this.allowRightToLeft = allowRightToLeft;
      this.childIdForLocationHint = childIdForLocationHint;
    }

    public SweepConfiguration(boolean allowLeftToRight, boolean allowRightToLeft) {
      this(allowLeftToRight, allowRightToLeft, 0);
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
  public void setForegroundViewResId(int foregroundViewResId) {
    this.mForegroundViewResId = foregroundViewResId;
  }
}
