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
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FastScroller extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    public int mAnimationState;
    public final AnonymousClass1 mHideRunnable;
    public float mHorizontalDragX;
    public int mHorizontalThumbCenterX;
    public final StateListDrawable mHorizontalThumbDrawable;
    public final int mHorizontalThumbHeight;
    public int mHorizontalThumbWidth;
    public final Drawable mHorizontalTrackDrawable;
    public final int mHorizontalTrackHeight;
    public final int mMargin;
    public final AnonymousClass2 mOnScrollListener;
    public final RecyclerView mRecyclerView;
    public final int mScrollbarMinimumRange;
    public final ValueAnimator mShowHideAnimator;
    public float mVerticalDragY;
    public int mVerticalThumbCenterY;
    public final StateListDrawable mVerticalThumbDrawable;
    public int mVerticalThumbHeight;
    public final int mVerticalThumbWidth;
    public final Drawable mVerticalTrackDrawable;
    public final int mVerticalTrackWidth;
    public static final int[] PRESSED_STATE_SET = {R.attr.state_pressed};
    public static final int[] EMPTY_STATE_SET = new int[0];
    public int mRecyclerViewWidth = 0;
    public int mRecyclerViewHeight = 0;
    public boolean mNeedVerticalScrollbar = false;
    public boolean mNeedHorizontalScrollbar = false;
    public int mState = 0;
    public int mDragState = 0;
    public final int[] mVerticalRange = new int[2];
    public final int[] mHorizontalRange = new int[2];

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AnimatorListener extends AnimatorListenerAdapter {
        public boolean mCanceled = false;

        public AnimatorListener() {
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
                fastScroller2.mRecyclerView.invalidate();
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AnimatorUpdater implements ValueAnimator.AnimatorUpdateListener {
        public AnimatorUpdater() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
            int floatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            FastScroller.this.mVerticalThumbDrawable.setAlpha(floatValue);
            FastScroller.this.mVerticalTrackDrawable.setAlpha(floatValue);
            FastScroller.this.mRecyclerView.invalidate();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [androidx.recyclerview.widget.FastScroller$1, java.lang.Runnable] */
    /* JADX WARN: Type inference failed for: r2v2, types: [androidx.recyclerview.widget.FastScroller$2, androidx.recyclerview.widget.RecyclerView$OnScrollListener, java.lang.Object] */
    public FastScroller(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i, int i2, int i3) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        this.mShowHideAnimator = ofFloat;
        this.mAnimationState = 0;
        ?? r0 = new Runnable() { // from class: androidx.recyclerview.widget.FastScroller.1
            @Override // java.lang.Runnable
            public final void run() {
                FastScroller fastScroller = FastScroller.this;
                int i4 = fastScroller.mAnimationState;
                if (i4 == 1) {
                    fastScroller.mShowHideAnimator.cancel();
                } else if (i4 != 2) {
                    return;
                }
                fastScroller.mAnimationState = 3;
                ValueAnimator valueAnimator = fastScroller.mShowHideAnimator;
                valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
                fastScroller.mShowHideAnimator.setDuration(500);
                fastScroller.mShowHideAnimator.start();
            }
        };
        this.mHideRunnable = r0;
        ?? r2 = new RecyclerView.OnScrollListener() { // from class: androidx.recyclerview.widget.FastScroller.2
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public final void onScrolled(RecyclerView recyclerView2, int i4, int i5) {
                int computeHorizontalScrollOffset = recyclerView2.computeHorizontalScrollOffset();
                int computeVerticalScrollOffset = recyclerView2.computeVerticalScrollOffset();
                FastScroller fastScroller = FastScroller.this;
                int computeVerticalScrollRange = fastScroller.mRecyclerView.computeVerticalScrollRange();
                int i6 = fastScroller.mRecyclerViewHeight;
                int i7 = computeVerticalScrollRange - i6;
                int i8 = fastScroller.mScrollbarMinimumRange;
                fastScroller.mNeedVerticalScrollbar = i7 > 0 && i6 >= i8;
                int computeHorizontalScrollRange = fastScroller.mRecyclerView.computeHorizontalScrollRange();
                int i9 = fastScroller.mRecyclerViewWidth;
                boolean z = computeHorizontalScrollRange - i9 > 0 && i9 >= i8;
                fastScroller.mNeedHorizontalScrollbar = z;
                boolean z2 = fastScroller.mNeedVerticalScrollbar;
                if (!z2 && !z) {
                    if (fastScroller.mState != 0) {
                        fastScroller.setState(0);
                        return;
                    }
                    return;
                }
                if (z2) {
                    float f = i6;
                    fastScroller.mVerticalThumbCenterY = (int) ((((f / 2.0f) + computeVerticalScrollOffset) * f) / computeVerticalScrollRange);
                    fastScroller.mVerticalThumbHeight = Math.min(i6, (i6 * i6) / computeVerticalScrollRange);
                }
                if (fastScroller.mNeedHorizontalScrollbar) {
                    float f2 = computeHorizontalScrollOffset;
                    float f3 = i9;
                    fastScroller.mHorizontalThumbCenterX = (int) ((((f3 / 2.0f) + f2) * f3) / computeHorizontalScrollRange);
                    fastScroller.mHorizontalThumbWidth = Math.min(i9, (i9 * i9) / computeHorizontalScrollRange);
                }
                int i10 = fastScroller.mState;
                if (i10 == 0 || i10 == 1) {
                    fastScroller.setState(1);
                }
            }
        };
        this.mOnScrollListener = r2;
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
            recyclerView2.removeItemDecoration(this);
            RecyclerView recyclerView3 = this.mRecyclerView;
            recyclerView3.mOnItemTouchListeners.remove(this);
            if (recyclerView3.mInterceptingOnItemTouchListener == this) {
                recyclerView3.mInterceptingOnItemTouchListener = null;
            }
            List list = this.mRecyclerView.mScrollListeners;
            if (list != null) {
                ((ArrayList) list).remove((Object) r2);
            }
            this.mRecyclerView.removeCallbacks(r0);
        }
        this.mRecyclerView = recyclerView;
        if (recyclerView != null) {
            recyclerView.addItemDecoration(this);
            this.mRecyclerView.mOnItemTouchListeners.add(this);
            this.mRecyclerView.addOnScrollListener(r2);
        }
    }

    public static int scrollTo(float f, float f2, int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[1] - iArr[0];
        if (i4 != 0) {
            int i5 = i - i3;
            int i6 = (int) (((f2 - f) / i4) * i5);
            int i7 = i2 + i6;
            if (i7 < i5 && i7 >= 0) {
                return i6;
            }
        }
        return 0;
    }

    public final boolean isPointInsideHorizontalThumb(float f, float f2) {
        if (f2 < this.mRecyclerViewHeight - this.mHorizontalThumbHeight) {
            return false;
        }
        int i = this.mHorizontalThumbCenterX;
        int i2 = this.mHorizontalThumbWidth;
        return f >= ((float) (i - (i2 / 2))) && f <= ((float) ((i2 / 2) + i));
    }

    public final boolean isPointInsideVerticalThumb(float f, float f2) {
        RecyclerView recyclerView = this.mRecyclerView;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        boolean z = recyclerView.getLayoutDirection() == 1;
        int i = this.mVerticalThumbWidth;
        if (!z ? f >= this.mRecyclerViewWidth - i : f <= i) {
            int i2 = this.mVerticalThumbCenterY;
            int i3 = this.mVerticalThumbHeight / 2;
            if (f2 >= i2 - i3 && f2 <= i3 + i2) {
                return true;
            }
        }
        return false;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public final void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int i = this.mRecyclerViewWidth;
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (i != recyclerView2.getWidth() || this.mRecyclerViewHeight != recyclerView2.getHeight()) {
            this.mRecyclerViewWidth = recyclerView2.getWidth();
            this.mRecyclerViewHeight = recyclerView2.getHeight();
            setState(0);
            return;
        }
        if (this.mAnimationState != 0) {
            if (this.mNeedVerticalScrollbar) {
                int i2 = this.mRecyclerViewWidth;
                int i3 = this.mVerticalThumbWidth;
                int i4 = i2 - i3;
                int i5 = this.mVerticalThumbCenterY;
                int i6 = this.mVerticalThumbHeight;
                int i7 = i5 - (i6 / 2);
                this.mVerticalThumbDrawable.setBounds(0, 0, i3, i6);
                this.mVerticalTrackDrawable.setBounds(0, 0, this.mVerticalTrackWidth, this.mRecyclerViewHeight);
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                if (recyclerView2.getLayoutDirection() == 1) {
                    this.mVerticalTrackDrawable.draw(canvas);
                    canvas.translate(i3, i7);
                    canvas.scale(-1.0f, 1.0f);
                    this.mVerticalThumbDrawable.draw(canvas);
                    canvas.scale(-1.0f, 1.0f);
                    canvas.translate(-i3, -i7);
                } else {
                    canvas.translate(i4, 0.0f);
                    this.mVerticalTrackDrawable.draw(canvas);
                    canvas.translate(0.0f, i7);
                    this.mVerticalThumbDrawable.draw(canvas);
                    canvas.translate(-i4, -i7);
                }
            }
            if (this.mNeedHorizontalScrollbar) {
                int i8 = this.mRecyclerViewHeight;
                int i9 = this.mHorizontalThumbHeight;
                int i10 = this.mHorizontalThumbCenterX;
                int i11 = this.mHorizontalThumbWidth;
                this.mHorizontalThumbDrawable.setBounds(0, 0, i11, i9);
                this.mHorizontalTrackDrawable.setBounds(0, 0, this.mRecyclerViewWidth, this.mHorizontalTrackHeight);
                canvas.translate(0.0f, i8 - i9);
                this.mHorizontalTrackDrawable.draw(canvas);
                canvas.translate(i10 - (i11 / 2), 0.0f);
                this.mHorizontalThumbDrawable.draw(canvas);
                canvas.translate(-r2, -r9);
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        int i = this.mState;
        if (i != 1) {
            return i == 2;
        }
        boolean isPointInsideVerticalThumb = isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
        boolean isPointInsideHorizontalThumb = isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
        if (motionEvent.getAction() != 0) {
            return false;
        }
        if (!isPointInsideVerticalThumb && !isPointInsideHorizontalThumb) {
            return false;
        }
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

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final void onTouchEvent(MotionEvent motionEvent) {
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
            int i = this.mDragState;
            int i2 = this.mMargin;
            if (i == 1) {
                float x = motionEvent.getX();
                int[] iArr = this.mHorizontalRange;
                iArr[0] = i2;
                int i3 = this.mRecyclerViewWidth - i2;
                iArr[1] = i3;
                float max = Math.max(i2, Math.min(i3, x));
                if (Math.abs(this.mHorizontalThumbCenterX - max) >= 2.0f) {
                    int scrollTo = scrollTo(this.mHorizontalDragX, max, iArr, this.mRecyclerView.computeHorizontalScrollRange(), this.mRecyclerView.computeHorizontalScrollOffset(), this.mRecyclerViewWidth);
                    if (scrollTo != 0) {
                        this.mRecyclerView.scrollBy(scrollTo, 0);
                    }
                    this.mHorizontalDragX = max;
                }
            }
            if (this.mDragState == 2) {
                float y = motionEvent.getY();
                int[] iArr2 = this.mVerticalRange;
                iArr2[0] = i2;
                int i4 = this.mRecyclerViewHeight - i2;
                iArr2[1] = i4;
                float max2 = Math.max(i2, Math.min(i4, y));
                if (Math.abs(this.mVerticalThumbCenterY - max2) < 2.0f) {
                    return;
                }
                int scrollTo2 = scrollTo(this.mVerticalDragY, max2, iArr2, this.mRecyclerView.computeVerticalScrollRange(), this.mRecyclerView.computeVerticalScrollOffset(), this.mRecyclerViewHeight);
                if (scrollTo2 != 0) {
                    this.mRecyclerView.scrollBy(0, scrollTo2);
                }
                this.mVerticalDragY = max2;
            }
        }
    }

    public final void setState(int i) {
        AnonymousClass1 anonymousClass1 = this.mHideRunnable;
        if (i == 2 && this.mState != 2) {
            this.mVerticalThumbDrawable.setState(PRESSED_STATE_SET);
            this.mRecyclerView.removeCallbacks(anonymousClass1);
        }
        if (i == 0) {
            this.mRecyclerView.invalidate();
        } else {
            show();
        }
        if (this.mState == 2 && i != 2) {
            this.mVerticalThumbDrawable.setState(EMPTY_STATE_SET);
            this.mRecyclerView.removeCallbacks(anonymousClass1);
            this.mRecyclerView.postDelayed(anonymousClass1, 1200);
        } else if (i == 1) {
            this.mRecyclerView.removeCallbacks(anonymousClass1);
            this.mRecyclerView.postDelayed(anonymousClass1, 1500);
        }
        this.mState = i;
    }

    public final void show() {
        int i = this.mAnimationState;
        if (i != 0) {
            if (i != 3) {
                return;
            } else {
                this.mShowHideAnimator.cancel();
            }
        }
        this.mAnimationState = 1;
        ValueAnimator valueAnimator = this.mShowHideAnimator;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        this.mShowHideAnimator.setDuration(500L);
        this.mShowHideAnimator.setStartDelay(0L);
        this.mShowHideAnimator.start();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.OnItemTouchListener
    public final void onRequestDisallowInterceptTouchEvent(boolean z) {
    }
}
