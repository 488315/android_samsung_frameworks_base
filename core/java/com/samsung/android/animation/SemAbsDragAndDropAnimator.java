package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
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
/* loaded from: classes5.dex */
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

    public SemAbsDragAndDropAnimator(Context context, View view) {
        if (context == null || view == null) {
            throw new RuntimeException("SemDragAndDropGridAnimator constructor arguments cannot be null");
        }
        this.mContext = context;
        this.mView = view;
        this.mDndAnimationCore = new SemDragAndDropAnimationCore(view);
        this.mItemAnimator = this.mDndAnimationCore.itemAnimator;
        this.mDndMode = false;
        this.mFirstDragPos = -1;
        this.mDragPos = -1;
        this.mDndTouchX = Integer.MIN_VALUE;
        this.mDndTouchY = Integer.MIN_VALUE;
        this.mDndTouchOffsetX = Integer.MIN_VALUE;
        this.mDndTouchOffsetY = Integer.MIN_VALUE;
        this.mDndTouchMode = 0;
        this.mDensity = this.mContext.getResources().getDisplayMetrics().density;
        this.mDragView = null;
        this.mDragViewRect = new Rect();
        this.mDragViewBitmapPaint = new Paint();
        this.mDragViewBitmapAlpha = 179;
        this.mDragGrabHandleDrawable = null;
        this.mDragGrabHandlePosGravity = 21;
        this.mDragGrabHandlePadding = new Rect();
        this.mAutoScrollRunnable = new AutoScrollRunnable();
        this.mAutoScrollTopDelta = (int) (this.mDensity * 7.0f);
        this.mAutoScrollBottomDelta = (int) (this.mDensity * (-7.0f));
    }

    @Deprecated
    public boolean isDraggable() {
        return this.mDndMode;
    }

    public void setDraggable(boolean dndMode) {
        if (this.mDndController == null) {
            throw new RuntimeException("You must specify dndController to activate Drag&Drop.");
        }
        if (!this.mView.isAttachedToWindow() || this.mDragGrabHandleDrawable == null) {
            setDndModeInternal(dndMode);
            return;
        }
        if (this.mDndMode != dndMode) {
            final boolean fadeOut = this.mDndMode;
            if (!fadeOut) {
                setDndModeInternal(true);
                this.mDragHandleAlpha = 0;
            }
            ValueAnimator va = ValueAnimator.ofFloat(0.0f, 1.0f);
            va.setDuration(200L);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemAbsDragAndDropAnimator.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator animator) {
                    float fraction = animator.getAnimatedFraction();
                    if (fadeOut) {
                        SemAbsDragAndDropAnimator.this.mDragHandleAlpha = (int) ((1.0f - fraction) * 255.0f);
                    } else {
                        SemAbsDragAndDropAnimator.this.mDragHandleAlpha = (int) (255.0f * fraction);
                    }
                    SemAbsDragAndDropAnimator.this.mView.invalidate();
                }
            });
            va.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAbsDragAndDropAnimator.2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationStart(Animator animation) {
                    SemAbsDragAndDropAnimator.this.mView.setEnabled(false);
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animation) {
                    if (fadeOut) {
                        SemAbsDragAndDropAnimator.this.setDndModeInternal(false);
                    }
                    SemAbsDragAndDropAnimator.this.mDragHandleAlpha = 255;
                    SemAbsDragAndDropAnimator.this.mView.setEnabled(true);
                }
            });
            va.setInterpolator(fadeOut ? FADE_OUT_INTERPOLATOR : FADE_IN_INTERPOLATOR);
            va.start();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setDndModeInternal(boolean dndMode) {
        this.mDndMode = dndMode;
        if (!dndMode) {
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

    public void setDragItemBitmap(Bitmap item) {
        if (!isDraggable()) {
            return;
        }
        if (this.mDragViewBitmap != null) {
            this.mDragViewBitmap.recycle();
        }
        this.mDragViewBitmap = item;
        this.mUserSetDragItemBitmap = true;
    }

    public void setDragViewAlpha(int alpha) {
        if (this.mDragViewBitmapPaint != null) {
            this.mDragViewBitmapAlpha = alpha;
            this.mDragViewBitmapPaint.setAlpha(alpha);
        }
    }

    public void setDragAndDropEventListener(DragAndDropListener dndListener) {
        this.mDndListener = dndListener;
    }

    public void setAutoScrollListener(SemDragAutoScrollListener autoScrollListener) {
        this.mAutoScrollListener = autoScrollListener;
    }

    public void setDragGrabHandleDrawable(int resId) {
        setDragGrabHandleDrawable(this.mContext.getResources().getDrawable(resId));
    }

    public void setDragGrabHandleDrawable(Drawable d) {
        this.mDragGrabHandleDrawable = d;
    }

    public void setDragGrabHandlePositionGravity(int gravity) {
        this.mDragGrabHandlePosGravity = gravity;
    }

    public void setDragGrabHandlePadding(int paddingLeft, int paddingTop, int paddingRight, int paddingBottom) {
        if (this.mDragGrabHandleDrawable != null) {
            this.mDragGrabHandlePadding.left = paddingLeft;
            this.mDragGrabHandlePadding.top = paddingTop;
            this.mDragGrabHandlePadding.right = paddingRight;
            this.mDragGrabHandlePadding.bottom = paddingBottom;
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
        if (this.mDragViewBitmap != null) {
            this.mDragViewBitmap.recycle();
            this.mDragViewBitmap = null;
        }
        this.mDndAutoScrollMode = 0;
        this.mView.removeCallbacks(this.mAutoScrollRunnable);
    }

    void resetDndPositionValues() {
        this.mFirstDragPos = -1;
        this.mDragPos = this.mFirstDragPos;
        this.mRetainFirstDragViewPos = -1;
    }

    /* JADX INFO: Access modifiers changed from: private */
    class AutoScrollRunnable implements Runnable {
        private AutoScrollRunnable() {
        }

        @Override // java.lang.Runnable
        public void run() {
            SemAbsDragAndDropAnimator.this.mListItemSelectionAnimating = false;
            int delta = 0;
            if (SemAbsDragAndDropAnimator.this.mDndAutoScrollMode == 1) {
                delta = SemAbsDragAndDropAnimator.this.mAutoScrollTopDelta;
            }
            if (SemAbsDragAndDropAnimator.this.mDndAutoScrollMode == 2) {
                delta = SemAbsDragAndDropAnimator.this.mAutoScrollBottomDelta;
            }
            if (delta != 0 && SemAbsDragAndDropAnimator.this.mAutoScrollListener != null) {
                SemAbsDragAndDropAnimator.this.mAutoScrollListener.onAutoScroll(delta);
            }
            SemAbsDragAndDropAnimator.this.reorderIfNeeded();
            if (SemAbsDragAndDropAnimator.this.mDndAutoScrollMode != 0) {
                SemAbsDragAndDropAnimator.this.mView.postOnAnimationDelayed(this, 10L);
            }
        }
    }

    public int getChildDrawingOrder(int childCount, int i) {
        if (this.mRetainFirstDragViewPos != -1) {
            if (i == this.mRetainFirstDragViewPos) {
                return childCount - 1;
            }
            if (i == childCount - 1) {
                if (this.mRetainFirstDragViewPos <= childCount - 1) {
                    return this.mRetainFirstDragViewPos;
                }
                return childCount - 1;
            }
        }
        return i;
    }

    boolean activatedByLongPress() {
        return this.mDragGrabHandleDrawable == null || SemAnimatorUtils.isTalkBackEnabled(this.mContext);
    }

    public void speakDescriptionForAccessibility() {
        if (!SemAnimatorUtils.isTalkBackEnabled(this.mContext) || this.mView.getVisibility() != 0) {
            return;
        }
        isDraggable();
    }

    void speakDragReleaseForAccessibility(int itemPosition) {
    }

    void speakNotDraggableForAccessibility(int itemPosition) {
    }

    void speakDragStartForAccessibility(int itemPosition) {
        this.mView.clearAccessibilityFocus();
    }
}
