package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.Transformation;
import com.samsung.android.animation.SemDragAndDropAnimationCore;

@Deprecated
/* loaded from: classes6.dex */
public abstract class SemAbsDragAndDropAnimator {
    static final int BITMAP_ALPHA = 179;
    static final int DND_AUTO_SCROLL_DELTA_VALUE = 7;
    static final int DND_AUTO_SCROLL_END = 2;
    static final int DND_AUTO_SCROLL_FRAME_DELAY = 10;
    static final int DND_AUTO_SCROLL_NONE = 0;
    static final int DND_AUTO_SCROLL_START = 1;
    static final int DND_TOUCH_STATUS_MOVING = 2;
    static final int DND_TOUCH_STATUS_NON = 0;
    static final int DND_TOUCH_STATUS_START = 1;
    static final float DRAGGING_RELEASE_ANIM_DURATION_MULTIPLICATOR = 0.7f;
    static final int DRAG_HANDLE_FADE_DURATION = 200;
    static final int INVALID_POINTER_ID = -1;
    static final float SCALEUPDOWNANIM_RESISTANCE = 15.0f;
    private static final String TAG = "SemAbsDragAndDropAnimator";
    int mAutoScrollBottomDelta;
    SemDragAutoScrollListener mAutoScrollListener;
    AutoScrollRunnable mAutoScrollRunnable;
    int mAutoScrollTopDelta;
    Context mContext;
    private final float mDensity;
    SemDragAndDropAnimationCore mDndAnimationCore;
    int mDndAutoScrollMode;
    DragAndDropController mDndController;
    DragAndDropListener mDndListener;
    boolean mDndMode;
    int mDndTouchMode;
    int mDndTouchOffsetX;
    int mDndTouchOffsetY;
    int mDndTouchX;
    int mDndTouchY;
    Drawable mDragGrabHandleDrawable;
    Rect mDragGrabHandlePadding;
    int mDragGrabHandlePosGravity;
    int mDragPos;
    View mDragView;
    Bitmap mDragViewBitmap;
    int mDragViewBitmapAlpha;
    Paint mDragViewBitmapPaint;
    Rect mDragViewRect;
    int mFirstDragPos;
    int mFirstTouchX;
    int mFirstTouchY;
    SemDragAndDropAnimationCore.ItemAnimator mItemAnimator;
    SemDragAndDropAnimationCore.ItemSelectHighlightingAnimation mScaleUpAndDownAnimation;
    MotionEvent mTempEvent;
    private View mView;
    static int[] PRESSED_STATE_SET = {16842919};
    static int[] EMPTY_STATE_SET = new int[0];
    static final PathInterpolator SINE_IN_OUT_70 = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
    static final Interpolator FADE_IN_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
    static final Interpolator FADE_OUT_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
    int mActivePointerId = -1;
    int mDragHandleAlpha = 255;
    boolean mListItemSelectionAnimating = false;
    boolean mUserSetDragItemBitmap = false;
    boolean mDropDonePending = false;
    int mRetainFirstDragViewPos = -1;
    Rect mTempRect = new Rect();
    Transformation mTempTrans = new Transformation();
    int mDragViewBitmapTranslateX = 0;
    int mDragViewBitmapTranslateY = 0;
    int mCanvasSaveCount = 0;

    public interface DragAndDropController {
        boolean canDrag(int i);

        boolean canDrop(int i, int i2);

        void dropDone(int i, int i2);
    }

    public interface DragAndDropListener {
        void onDragAndDropEnd();

        void onDragAndDropStart();
    }

    public interface SemDragAutoScrollListener {
        void onAutoScroll(int i);
    }

    abstract void reorderIfNeeded();

    void speakDragReleaseForAccessibility(int i) {
    }

    void speakNotDraggableForAccessibility(int i) {
    }

    public SemAbsDragAndDropAnimator(Context context, View view) {
        if (context == null || view == null) {
            throw new RuntimeException("SemDragAndDropGridAnimator constructor arguments cannot be null");
        }
        this.mContext = context;
        this.mView = view;
        SemDragAndDropAnimationCore semDragAndDropAnimationCore = new SemDragAndDropAnimationCore(view);
        this.mDndAnimationCore = semDragAndDropAnimationCore;
        this.mItemAnimator = semDragAndDropAnimationCore.itemAnimator;
        this.mDndMode = false;
        this.mFirstDragPos = -1;
        this.mDragPos = -1;
        this.mDndTouchX = Integer.MIN_VALUE;
        this.mDndTouchY = Integer.MIN_VALUE;
        this.mDndTouchOffsetX = Integer.MIN_VALUE;
        this.mDndTouchOffsetY = Integer.MIN_VALUE;
        this.mDndTouchMode = 0;
        float f = this.mContext.getResources().getDisplayMetrics().density;
        this.mDensity = f;
        this.mDragView = null;
        this.mDragViewRect = new Rect();
        this.mDragViewBitmapPaint = new Paint();
        this.mDragViewBitmapAlpha = 179;
        this.mDragGrabHandleDrawable = null;
        this.mDragGrabHandlePosGravity = 21;
        this.mDragGrabHandlePadding = new Rect();
        this.mAutoScrollRunnable = new AutoScrollRunnable();
        this.mAutoScrollTopDelta = (int) (7.0f * f);
        this.mAutoScrollBottomDelta = (int) (f * (-7.0f));
    }

    @Deprecated
    public boolean isDraggable() {
        return this.mDndMode;
    }

    public void setDraggable(boolean z) {
        Interpolator interpolator;
        if (this.mDndController == null) {
            throw new RuntimeException("You must specify dndController to activate Drag&Drop.");
        }
        if (!this.mView.isAttachedToWindow() || this.mDragGrabHandleDrawable == null) {
            setDndModeInternal(z);
            return;
        }
        final boolean z2 = this.mDndMode;
        if (z2 != z) {
            if (!z2) {
                setDndModeInternal(true);
                this.mDragHandleAlpha = 0;
            }
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat.setDuration(200L);
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemAbsDragAndDropAnimator.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    float animatedFraction = valueAnimator.getAnimatedFraction();
                    if (z2) {
                        SemAbsDragAndDropAnimator.this.mDragHandleAlpha = (int) ((1.0f - animatedFraction) * 255.0f);
                    } else {
                        SemAbsDragAndDropAnimator.this.mDragHandleAlpha = (int) (animatedFraction * 255.0f);
                    }
                    SemAbsDragAndDropAnimator.this.mView.invalidate();
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAbsDragAndDropAnimator.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animator) throws Resources.NotFoundException {
                    SemAbsDragAndDropAnimator.this.mView.setEnabled(false);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) throws Resources.NotFoundException {
                    if (z2) {
                        SemAbsDragAndDropAnimator.this.setDndModeInternal(false);
                    }
                    SemAbsDragAndDropAnimator.this.mDragHandleAlpha = 255;
                    SemAbsDragAndDropAnimator.this.mView.setEnabled(true);
                }
            });
            if (z2) {
                interpolator = FADE_OUT_INTERPOLATOR;
            } else {
                interpolator = FADE_IN_INTERPOLATOR;
            }
            valueAnimatorOfFloat.setInterpolator(interpolator);
            valueAnimatorOfFloat.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDndModeInternal(boolean z) {
        this.mDndMode = z;
        if (!z) {
            this.mItemAnimator.removeAll();
            resetDndState();
        }
        this.mView.invalidate();
    }

    public View getDragView() {
        if (isDraggable()) {
            return this.mDragView;
        }
        return null;
    }

    public void setDragItemBitmap(Bitmap bitmap) {
        if (isDraggable()) {
            Bitmap bitmap2 = this.mDragViewBitmap;
            if (bitmap2 != null) {
                bitmap2.recycle();
            }
            this.mDragViewBitmap = bitmap;
            this.mUserSetDragItemBitmap = true;
        }
    }

    public void setDragViewAlpha(int i) {
        Paint paint = this.mDragViewBitmapPaint;
        if (paint != null) {
            this.mDragViewBitmapAlpha = i;
            paint.setAlpha(i);
        }
    }

    public void setDragAndDropEventListener(DragAndDropListener dragAndDropListener) {
        this.mDndListener = dragAndDropListener;
    }

    public void setAutoScrollListener(SemDragAutoScrollListener semDragAutoScrollListener) {
        this.mAutoScrollListener = semDragAutoScrollListener;
    }

    public void setDragGrabHandleDrawable(int i) {
        setDragGrabHandleDrawable(this.mContext.getResources().getDrawable(i));
    }

    public void setDragGrabHandleDrawable(Drawable drawable) {
        this.mDragGrabHandleDrawable = drawable;
    }

    public void setDragGrabHandlePositionGravity(int i) {
        this.mDragGrabHandlePosGravity = i;
    }

    public void setDragGrabHandlePadding(int i, int i2, int i3, int i4) {
        if (this.mDragGrabHandleDrawable != null) {
            this.mDragGrabHandlePadding.left = i;
            this.mDragGrabHandlePadding.top = i2;
            this.mDragGrabHandlePadding.right = i3;
            this.mDragGrabHandlePadding.bottom = i4;
        }
    }

    public int getDragGrabHandlePaddingLeft() {
        if (this.mDragGrabHandleDrawable != null) {
            return this.mDragGrabHandlePadding.left;
        }
        return Integer.MIN_VALUE;
    }

    public int getDragGrabHandlePaddingTop() {
        if (this.mDragGrabHandleDrawable != null) {
            return this.mDragGrabHandlePadding.top;
        }
        return Integer.MIN_VALUE;
    }

    public int getDragGrabHandlePaddingRight() {
        if (this.mDragGrabHandleDrawable != null) {
            return this.mDragGrabHandlePadding.right;
        }
        return Integer.MIN_VALUE;
    }

    public int getDragGrabHandlePaddingBottom() {
        if (this.mDragGrabHandleDrawable != null) {
            return this.mDragGrabHandlePadding.bottom;
        }
        return Integer.MIN_VALUE;
    }

    void resetDndState() {
        resetDndTouchValuesAndBitmap();
        resetDndPositionValues();
    }

    void resetDndTouchValuesAndBitmap() {
        this.mDndTouchMode = 0;
        this.mDndTouchX = Integer.MIN_VALUE;
        this.mDndTouchY = Integer.MIN_VALUE;
        this.mFirstTouchX = Integer.MIN_VALUE;
        this.mFirstTouchY = Integer.MIN_VALUE;
        this.mDragViewBitmapTranslateX = 0;
        this.mDragViewBitmapTranslateY = 0;
        this.mDragView = null;
        Bitmap bitmap = this.mDragViewBitmap;
        if (bitmap != null) {
            bitmap.recycle();
            this.mDragViewBitmap = null;
        }
        this.mDndAutoScrollMode = 0;
        this.mView.removeCallbacks(this.mAutoScrollRunnable);
    }

    void resetDndPositionValues() {
        this.mFirstDragPos = -1;
        this.mDragPos = -1;
        this.mRetainFirstDragViewPos = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class AutoScrollRunnable implements Runnable {
        private AutoScrollRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SemAbsDragAndDropAnimator.this.mListItemSelectionAnimating = false;
            int i = SemAbsDragAndDropAnimator.this.mDndAutoScrollMode == 1 ? SemAbsDragAndDropAnimator.this.mAutoScrollTopDelta : 0;
            if (SemAbsDragAndDropAnimator.this.mDndAutoScrollMode == 2) {
                i = SemAbsDragAndDropAnimator.this.mAutoScrollBottomDelta;
            }
            if (i != 0 && SemAbsDragAndDropAnimator.this.mAutoScrollListener != null) {
                SemAbsDragAndDropAnimator.this.mAutoScrollListener.onAutoScroll(i);
            }
            SemAbsDragAndDropAnimator.this.reorderIfNeeded();
            if (SemAbsDragAndDropAnimator.this.mDndAutoScrollMode != 0) {
                SemAbsDragAndDropAnimator.this.mView.postOnAnimationDelayed(this, 10L);
            }
        }
    }

    public int getChildDrawingOrder(int i, int i2) {
        int i3 = this.mRetainFirstDragViewPos;
        if (i3 != -1) {
            if (i2 == i3) {
                return i - 1;
            }
            int i4 = i - 1;
            if (i2 == i4) {
                return i3 <= i4 ? i3 : i4;
            }
        }
        return i2;
    }

    boolean activatedByLongPress() {
        return this.mDragGrabHandleDrawable == null || SemAnimatorUtils.isTalkBackEnabled(this.mContext);
    }

    public void speakDescriptionForAccessibility() {
        if (SemAnimatorUtils.isTalkBackEnabled(this.mContext) && this.mView.getVisibility() == 0) {
            isDraggable();
        }
    }

    void speakDragStartForAccessibility(int i) {
        this.mView.clearAccessibilityFocus();
    }
}
