package androidx.recyclerview.widget;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/* loaded from: classes.dex */
final class FastScroller extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    int mAnimationState;
    private final Runnable mHideRunnable;
    float mHorizontalDragX;
    int mHorizontalThumbCenterX;
    private final StateListDrawable mHorizontalThumbDrawable;
    private final int mHorizontalThumbHeight;
    int mHorizontalThumbWidth;
    private final Drawable mHorizontalTrackDrawable;
    private final int mHorizontalTrackHeight;
    private final int mMargin;
    private RecyclerView mRecyclerView;
    private final int mScrollbarMinimumRange;
    final ValueAnimator mShowHideAnimator;
    float mVerticalDragY;
    int mVerticalThumbCenterY;
    final StateListDrawable mVerticalThumbDrawable;
    int mVerticalThumbHeight;
    private final int mVerticalThumbWidth;
    final Drawable mVerticalTrackDrawable;
    private final int mVerticalTrackWidth;
    private static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    private static final int[] EMPTY_STATE_SET = new int[0];
    private int mRecyclerViewWidth = 0;
    private int mRecyclerViewHeight = 0;
    private boolean mNeedVerticalScrollbar = false;
    private boolean mNeedHorizontalScrollbar = false;
    private int mState = 0;
    private int mDragState = 0;
    private final int[] mVerticalRange = new int[2];
    private final int[] mHorizontalRange = new int[2];

    private class AnimatorListener extends AnimatorListenerAdapter {
        private boolean mCanceled = false;

        AnimatorListener() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationCancel(Animator animator) {
            this.mCanceled = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public final void onAnimationEnd(Animator animator) {
            if (this.mCanceled) {
                this.mCanceled = false;
                return;
            }
            if (((Float) FastScroller.this.mShowHideAnimator.getAnimatedValue()).floatValue() == 0.0f) {
                FastScroller fastScroller = FastScroller.this;
                fastScroller.mAnimationState = 0;
                fastScroller.setState(0);
            } else {
                FastScroller fastScroller2 = FastScroller.this;
                fastScroller2.mAnimationState = 2;
                fastScroller2.requestRedraw();
            }
        }
    }

    private class AnimatorUpdater implements ValueAnimator.AnimatorUpdateListener {
        AnimatorUpdater() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            FastScroller.this.mVerticalThumbDrawable.setAlpha(floatValue);
            FastScroller.this.mVerticalTrackDrawable.setAlpha(floatValue);
            FastScroller.this.requestRedraw();
        }
    }

    FastScroller(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i, int i2, int i3) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mShowHideAnimator = ofFloat;
        this.mAnimationState = 0;
        Runnable runnable = new Runnable() { // from class: androidx.recyclerview.widget.FastScroller.1
            @Override // java.lang.Runnable
            public final void run() {
                FastScroller fastScroller = FastScroller.this;
                int i4 = fastScroller.mAnimationState;
                ValueAnimator valueAnimator = fastScroller.mShowHideAnimator;
                if (i4 == 1) {
                    valueAnimator.cancel();
                } else if (i4 != 2) {
                    return;
                }
                fastScroller.mAnimationState = 3;
                valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
                valueAnimator.setDuration(500);
                valueAnimator.start();
            }
        };
        this.mHideRunnable = runnable;
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: androidx.recyclerview.widget.FastScroller.2
        };
        this.mVerticalThumbDrawable = stateListDrawable;
        this.mVerticalTrackDrawable = drawable;
        this.mHorizontalThumbDrawable = stateListDrawable2;
        this.mHorizontalTrackDrawable = drawable2;
        this.mVerticalThumbWidth = Math.max(i, stateListDrawable.getIntrinsicWidth());
        this.mVerticalTrackWidth = Math.max(i, drawable.getIntrinsicWidth());
        this.mHorizontalThumbHeight = Math.max(i, stateListDrawable2.getIntrinsicWidth());
        this.mHorizontalTrackHeight = Math.max(i, drawable2.getIntrinsicWidth());
        this.mScrollbarMinimumRange = i2;
        this.mMargin = i3;
        stateListDrawable.setAlpha(255);
        drawable.setAlpha(255);
        ofFloat.addListener(new AnimatorListener());
        ofFloat.addUpdateListener(new AnimatorUpdater());
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView2.mLayout;
            if (layoutManager != null) {
                layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
            }
            recyclerView2.mItemDecorations.remove(this);
            if (recyclerView2.mItemDecorations.isEmpty()) {
                recyclerView2.setWillNotDraw(recyclerView2.getOverScrollMode() == 2);
            }
            recyclerView2.markItemDecorInsetsDirty();
            recyclerView2.requestLayout();
            this.mRecyclerView.removeOnItemTouchListener(this);
            this.mRecyclerView.removeOnScrollListener(onScrollListener);
            this.mRecyclerView.removeCallbacks(runnable);
        }
        this.mRecyclerView = recyclerView;
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager2 = recyclerView.mLayout;
            if (layoutManager2 != null) {
                layoutManager2.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
            }
            if (recyclerView.mItemDecorations.isEmpty()) {
                recyclerView.setWillNotDraw(false);
            }
            recyclerView.mItemDecorations.add(this);
            recyclerView.markItemDecorInsetsDirty();
            recyclerView.requestLayout();
            this.mRecyclerView.addOnItemTouchListener(this);
            this.mRecyclerView.addOnScrollListener(onScrollListener);
        }
    }

    final boolean isPointInsideHorizontalThumb(float f, float f2) {
        if (f2 >= this.mRecyclerViewHeight - this.mHorizontalThumbHeight) {
            int i = this.mHorizontalThumbCenterX;
            int i2 = this.mHorizontalThumbWidth;
            if (f >= i - (i2 / 2) && f <= (i2 / 2) + i) {
                return true;
            }
        }
        return false;
    }

    final boolean isPointInsideVerticalThumb(float f, float f2) {
        boolean z = ViewCompat.getLayoutDirection(this.mRecyclerView) == 1;
        int i = this.mVerticalThumbWidth;
        if (z) {
            if (f > i) {
                return false;
            }
        } else if (f < this.mRecyclerViewWidth - i) {
            return false;
        }
        int i2 = this.mVerticalThumbCenterY;
        int i3 = this.mVerticalThumbHeight / 2;
        return f2 >= ((float) (i2 - i3)) && f2 <= ((float) (i3 + i2));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDrawOver(Canvas canvas) {
        if (this.mRecyclerViewWidth != this.mRecyclerView.getWidth() || this.mRecyclerViewHeight != this.mRecyclerView.getHeight()) {
            this.mRecyclerViewWidth = this.mRecyclerView.getWidth();
            this.mRecyclerViewHeight = this.mRecyclerView.getHeight();
            setState(0);
            return;
        }
        if (this.mAnimationState != 0) {
            if (this.mNeedVerticalScrollbar) {
                int i = this.mRecyclerViewWidth;
                int i2 = this.mVerticalThumbWidth;
                int i3 = i - i2;
                int i4 = this.mVerticalThumbCenterY;
                int i5 = this.mVerticalThumbHeight;
                int i6 = i4 - (i5 / 2);
                StateListDrawable stateListDrawable = this.mVerticalThumbDrawable;
                stateListDrawable.setBounds(0, 0, i2, i5);
                int i7 = this.mRecyclerViewHeight;
                int i8 = this.mVerticalTrackWidth;
                Drawable drawable = this.mVerticalTrackDrawable;
                drawable.setBounds(0, 0, i8, i7);
                if (ViewCompat.getLayoutDirection(this.mRecyclerView) == 1) {
                    drawable.draw(canvas);
                    canvas.translate(i2, i6);
                    canvas.scale(-1.0f, 1.0f);
                    stateListDrawable.draw(canvas);
                    canvas.scale(-1.0f, 1.0f);
                    canvas.translate(-i2, -i6);
                } else {
                    canvas.translate(i3, 0.0f);
                    drawable.draw(canvas);
                    canvas.translate(0.0f, i6);
                    stateListDrawable.draw(canvas);
                    canvas.translate(-i3, -i6);
                }
            }
            if (this.mNeedHorizontalScrollbar) {
                int i9 = this.mRecyclerViewHeight;
                int i10 = this.mHorizontalThumbHeight;
                int i11 = i9 - i10;
                int i12 = this.mHorizontalThumbCenterX;
                int i13 = this.mHorizontalThumbWidth;
                int i14 = i12 - (i13 / 2);
                StateListDrawable stateListDrawable2 = this.mHorizontalThumbDrawable;
                stateListDrawable2.setBounds(0, 0, i13, i10);
                int i15 = this.mRecyclerViewWidth;
                int i16 = this.mHorizontalTrackHeight;
                Drawable drawable2 = this.mHorizontalTrackDrawable;
                drawable2.setBounds(0, 0, i15, i16);
                canvas.translate(0.0f, i11);
                drawable2.draw(canvas);
                canvas.translate(i14, 0.0f);
                stateListDrawable2.draw(canvas);
                canvas.translate(-i14, -i11);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = this.mState;
        if (i == 1) {
            boolean isPointInsideVerticalThumb = isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean isPointInsideHorizontalThumb = isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() == 0 && (isPointInsideVerticalThumb || isPointInsideHorizontalThumb)) {
                if (isPointInsideHorizontalThumb) {
                    this.mDragState = 1;
                    this.mHorizontalDragX = (int) motionEvent.getX();
                } else if (isPointInsideVerticalThumb) {
                    this.mDragState = 2;
                    this.mVerticalDragY = (int) motionEvent.getY();
                }
                setState(2);
                return true;
            }
        } else if (i == 2) {
            return true;
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x00bb, code lost:
    
        if (r9 >= 0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0112, code lost:
    
        if (r5 >= 0) goto L49;
     */
    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTouchEvent(MotionEvent motionEvent) {
        int i;
        int i2;
        if (this.mState == 0) {
            return;
        }
        if (motionEvent.getAction() == 0) {
            boolean isPointInsideVerticalThumb = isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean isPointInsideHorizontalThumb = isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            if (isPointInsideVerticalThumb || isPointInsideHorizontalThumb) {
                if (isPointInsideHorizontalThumb) {
                    this.mDragState = 1;
                    this.mHorizontalDragX = (int) motionEvent.getX();
                } else if (isPointInsideVerticalThumb) {
                    this.mDragState = 2;
                    this.mVerticalDragY = (int) motionEvent.getY();
                }
                setState(2);
                return;
            }
            return;
        }
        if (motionEvent.getAction() == 1 && this.mState == 2) {
            this.mVerticalDragY = 0.0f;
            this.mHorizontalDragX = 0.0f;
            setState(1);
            this.mDragState = 0;
            return;
        }
        if (motionEvent.getAction() == 2 && this.mState == 2) {
            show();
            int i3 = this.mDragState;
            int i4 = this.mMargin;
            if (i3 == 1) {
                float x = motionEvent.getX();
                int[] iArr = this.mHorizontalRange;
                iArr[0] = i4;
                int i5 = this.mRecyclerViewWidth - i4;
                iArr[1] = i5;
                float max = Math.max(i4, Math.min(i5, x));
                if (Math.abs(this.mHorizontalThumbCenterX - max) >= 2.0f) {
                    float f = this.mHorizontalDragX;
                    int computeHorizontalScrollRange = this.mRecyclerView.computeHorizontalScrollRange();
                    int computeHorizontalScrollOffset = this.mRecyclerView.computeHorizontalScrollOffset();
                    int i6 = this.mRecyclerViewWidth;
                    int i7 = iArr[1] - iArr[0];
                    if (i7 != 0) {
                        int i8 = computeHorizontalScrollRange - i6;
                        i2 = (int) (((max - f) / i7) * i8);
                        int i9 = computeHorizontalScrollOffset + i2;
                        if (i9 < i8) {
                        }
                    }
                    i2 = 0;
                    if (i2 != 0) {
                        this.mRecyclerView.scrollBy(i2, 0);
                    }
                    this.mHorizontalDragX = max;
                }
            }
            if (this.mDragState == 2) {
                float y = motionEvent.getY();
                int[] iArr2 = this.mVerticalRange;
                iArr2[0] = i4;
                int i10 = this.mRecyclerViewHeight - i4;
                iArr2[1] = i10;
                float max2 = Math.max(i4, Math.min(i10, y));
                if (Math.abs(this.mVerticalThumbCenterY - max2) < 2.0f) {
                    return;
                }
                float f2 = this.mVerticalDragY;
                int computeVerticalScrollRange = this.mRecyclerView.computeVerticalScrollRange();
                int computeVerticalScrollOffset = this.mRecyclerView.computeVerticalScrollOffset();
                int i11 = this.mRecyclerViewHeight;
                int i12 = iArr2[1] - iArr2[0];
                if (i12 != 0) {
                    int i13 = computeVerticalScrollRange - i11;
                    i = (int) (((max2 - f2) / i12) * i13);
                    int i14 = computeVerticalScrollOffset + i;
                    if (i14 < i13) {
                    }
                }
                i = 0;
                if (i != 0) {
                    this.mRecyclerView.scrollBy(0, i);
                }
                this.mVerticalDragY = max2;
            }
        }
    }

    final void requestRedraw() {
        this.mRecyclerView.invalidate();
    }

    final void setState(int i) {
        Runnable runnable = this.mHideRunnable;
        StateListDrawable stateListDrawable = this.mVerticalThumbDrawable;
        if (i == 2 && this.mState != 2) {
            stateListDrawable.setState(PRESSED_STATE_SET);
            this.mRecyclerView.removeCallbacks(runnable);
        }
        if (i == 0) {
            requestRedraw();
        } else {
            show();
        }
        if (this.mState == 2 && i != 2) {
            stateListDrawable.setState(EMPTY_STATE_SET);
            this.mRecyclerView.removeCallbacks(runnable);
            this.mRecyclerView.postDelayed(runnable, 1200);
        } else if (i == 1) {
            this.mRecyclerView.removeCallbacks(runnable);
            this.mRecyclerView.postDelayed(runnable, 1500);
        }
        this.mState = i;
    }

    public final void show() {
        int i = this.mAnimationState;
        ValueAnimator valueAnimator = this.mShowHideAnimator;
        if (i != 0) {
            if (i != 3) {
                return;
            } else {
                valueAnimator.cancel();
            }
        }
        this.mAnimationState = 1;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        valueAnimator.setDuration(500L);
        valueAnimator.setStartDelay(0L);
        valueAnimator.start();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final void onRequestDisallowInterceptTouchEvent() {
    }
}
