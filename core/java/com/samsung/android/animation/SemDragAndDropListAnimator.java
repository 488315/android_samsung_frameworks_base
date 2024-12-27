package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.internal.R;

@Deprecated
public class SemDragAndDropListAnimator extends SemAbsDragAndDropAnimator {
    private static final String TAG = "SemDragAndDropListAnimator";
    private int mDragViewRoundCorner;
    private SemDragAndDropAnimationCore.ItemAnimationListener mItemAnimationListener;
    private AdapterView.OnItemLongClickListener mItemLongClickListener;
    private ListView mListView;
    SparseIntArray mNonMovableItems;
    private final int mScrollBarSize;

    public SemDragAndDropListAnimator(Context context, ListView listview) {
        super(context, listview);
        this.mNonMovableItems = new SparseIntArray();
        this.mScrollBarSize = 10;
        this.mDragViewRoundCorner = 0;
        this.mListView = listview;
        initListeners();
        this.mDndAnimationCore.setAnimationListener(this.mItemAnimationListener);
        this.mListView.setDndListAnimator(this);
        this.mListView.setOnItemLongClickListener(this.mItemLongClickListener);
    }

    private void initListeners() {
        this.mItemAnimationListener =
                new SemDragAndDropAnimationCore.ItemAnimationListener() { // from class:
                    // com.samsung.android.animation.SemDragAndDropListAnimator.1
                    @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimationListener
                    public void onItemAnimatorEnd() {
                        if (SemDragAndDropListAnimator.this.mListItemSelectionAnimating) {
                            SemDragAndDropListAnimator.this.mListItemSelectionAnimating = false;
                            return;
                        }
                        if (SemDragAndDropListAnimator.this.mDropDonePending) {
                            SemDragAndDropListAnimator.this.mDropDonePending = false;
                            if (SemDragAndDropListAnimator.this.mDndController != null) {
                                Log.d(
                                        SemDragAndDropListAnimator.TAG,
                                        "initListeners : onItemAnimatorEnd :"
                                            + " mDndController.dropDone #1 , mFirstDragPos = "
                                                + SemDragAndDropListAnimator.this.mFirstDragPos
                                                + ", mDragPos = "
                                                + SemDragAndDropListAnimator.this.mDragPos);
                                SemDragAndDropListAnimator.this.mDndController.dropDone(
                                        SemDragAndDropListAnimator.this.mFirstDragPos,
                                        SemDragAndDropListAnimator.this.mDragPos);
                                SemDragAndDropListAnimator.this.speakDragReleaseForAccessibility(
                                        SemDragAndDropListAnimator.this.mDragPos);
                            }
                            SemDragAndDropListAnimator.this.mItemAnimator.removeAll();
                            SemDragAndDropListAnimator.this.resetDndPositionValues();
                            if (SemDragAndDropListAnimator.this.mDndListener != null) {
                                Log.d(
                                        SemDragAndDropListAnimator.TAG,
                                        "initListeners : onItemAnimatorEnd :"
                                            + " dndListener.onDragAndDropEnd() #1");
                                SemDragAndDropListAnimator.this.mDndListener.onDragAndDropEnd();
                            }
                            SemDragAndDropListAnimator.this.mListView.setEnabled(true);
                        }
                    }
                };
        this.mItemLongClickListener =
                new AdapterView
                        .OnItemLongClickListener() { // from class:
                                                     // com.samsung.android.animation.SemDragAndDropListAnimator.2
                    @Override // android.widget.AdapterView.OnItemLongClickListener
                    public boolean onItemLongClick(
                            AdapterView<?> parent, View view, int position, long id) {
                        return SemDragAndDropListAnimator.this.initDragIfNecessary(position);
                    }
                };
    }

    public void setDragAndDropController(
            SemAbsDragAndDropAnimator.DragAndDropController dndController) {
        if (dndController == null) {
            this.mDndController = null;
        } else if (this.mListView.getHeaderViewsCount() == 0
                && this.mListView.getFooterViewsCount() == 0) {
            this.mDndController = dndController;
        } else {
            this.mDndController = new HeaderFooterDndController(dndController);
        }
    }

    private boolean checkStartDnd(int x, int y, int itemPosition) {
        if (this.mListView.semIsLongPressTriggeredByKey()) {
            Log.d(TAG, "checkStartDnd : LongPress is triggered by key, return false");
            return false;
        }
        if (!checkDndGrabHandle(x, y, itemPosition)) {
            return false;
        }
        Log.d(TAG, "checkStartDnd : canDrag #1 itemPosition = " + itemPosition);
        boolean canDrag = this.mDndController.canDrag(itemPosition);
        if (!canDrag) {
            speakNotDraggableForAccessibility(itemPosition);
        }
        return canDrag;
    }

    private boolean checkDndGrabHandle(int x, int y, int itemPosition) {
        if (activatedByLongPress()) {
            return true;
        }
        Rect childRect = new Rect();
        View v = this.mListView.getChildAt(itemPosition - this.mListView.getFirstVisiblePosition());
        v.getHitRect(childRect);
        getDragGrabHandleHitRect(childRect, this.mTempRect);
        return this.mTempRect.contains(x, y);
    }

    public boolean startDrag() {
        if (this.mTempEvent == null) {
            return false;
        }
        int position =
                this.mListView.pointToPosition(
                        (int) this.mTempEvent.getX(), (int) this.mTempEvent.getY());
        return initDragIfNecessary(position);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a4 A[ADDED_TO_REGION, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            int r0 = r7.getAction()
            r1 = 1
            r2 = 0
            switch(r0) {
                case 0: goto L32;
                case 1: goto L24;
                case 2: goto Lb;
                case 3: goto L24;
                default: goto L9;
            }
        L9:
            goto La4
        Lb:
            boolean r3 = r6.isDraggable()
            if (r3 == 0) goto La4
            int r3 = r6.mDndTouchMode
            if (r3 != r1) goto La4
            android.widget.ListView r3 = r6.mListView
            int r3 = r3.getCount()
            if (r3 <= r1) goto La4
            boolean r3 = r6.activatedByLongPress()
            if (r3 == 0) goto La4
            return r1
        L24:
            boolean r1 = r6.isDraggable()
            if (r1 == 0) goto La4
            int r1 = r6.mDndTouchMode
            if (r1 == 0) goto La4
            r6.onTouchUpCancel(r7)
            goto La4
        L32:
            android.widget.ListView r3 = r6.mListView
            boolean r3 = r3.isEnabled()
            if (r3 != 0) goto L3b
            return r2
        L3b:
            android.view.MotionEvent r3 = r6.mTempEvent
            if (r3 == 0) goto L44
            android.view.MotionEvent r3 = r6.mTempEvent
            r3.recycle()
        L44:
            android.view.MotionEvent r3 = android.view.MotionEvent.obtain(r7)
            r6.mTempEvent = r3
            int r3 = r7.getPointerId(r2)
            r6.mActivePointerId = r3
            float r3 = r7.getX()
            int r3 = (int) r3
            r6.mDndTouchX = r3
            float r3 = r7.getY()
            int r3 = (int) r3
            r6.mDndTouchY = r3
            int r3 = r6.mDndTouchY
            r6.mFirstTouchY = r3
            boolean r3 = r6.isDraggable()
            if (r3 == 0) goto La4
            android.widget.ListView r3 = r6.mListView
            int r3 = r3.getCount()
            if (r3 <= r1) goto La4
            android.widget.ListView r3 = r6.mListView
            int r4 = r6.mDndTouchX
            int r5 = r6.mDndTouchY
            int r3 = r3.pointToPosition(r4, r5)
            r4 = -1
            if (r3 != r4) goto L7e
            return r2
        L7e:
            boolean r4 = r6.activatedByLongPress()
            if (r4 == 0) goto L85
            return r2
        L85:
            if (r3 < 0) goto La0
            android.widget.ListView r4 = r6.mListView
            int r4 = r4.getCount()
            if (r3 >= r4) goto La0
            int r4 = r6.mDndTouchX
            int r5 = r6.mDndTouchY
            boolean r4 = r6.checkStartDnd(r4, r5, r3)
            if (r4 == 0) goto La0
            boolean r4 = r6.initDrag(r3)
            if (r4 == 0) goto La3
            return r1
        La0:
            r6.resetDndState()
        La3:
        La4:
            return r2
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.animation.SemDragAndDropListAnimator.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public AdapterView.OnItemLongClickListener getDragAndDropOnItemLongClickListener() {
        return this.mItemLongClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initDragIfNecessary(int position) {
        if (isDraggable() && activatedByLongPress() && this.mListView.getCount() > 1) {
            if (position >= 0
                    && position < this.mListView.getCount()
                    && checkStartDnd(this.mDndTouchX, this.mDndTouchY, position)) {
                return initDrag(position);
            }
            resetDndState();
            return false;
        }
        return false;
    }

    private boolean initDrag(int itemPosition) {
        this.mDragView =
                this.mListView.getChildAt(itemPosition - this.mListView.getFirstVisiblePosition());
        if (this.mDragView == null) {
            return false;
        }
        this.mListView.setEnableHoverDrawable(false);
        this.mDndTouchMode = 1;
        this.mFirstDragPos = itemPosition;
        this.mDragPos = this.mFirstDragPos;
        this.mDragView.getHitRect(this.mDragViewRect);
        speakDragStartForAccessibility(itemPosition);
        if (!this.mUserSetDragItemBitmap) {
            if (this.mDragViewBitmap != null) {
                this.mDragViewBitmap.recycle();
            }
            this.mDragViewRoundCorner = this.mDragView.semGetRoundedCorners();
            if (this.mDragViewRoundCorner != 0) {
                this.mDragView.semSetRoundedCorners(0);
                this.mDragView.invalidate();
            }
            TypedValue typedValue = new TypedValue();
            int strokecolor = 29406;
            int strokeWidth =
                    Math.round(
                            this.mListView.getContext().getResources().getDisplayMetrics().density);
            boolean isValid =
                    this.mDragView
                            .getContext()
                            .getTheme()
                            .resolveAttribute(16843828, typedValue, true);
            if (!isValid) {
                this.mDragView
                        .getContext()
                        .getTheme()
                        .resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue, true);
                boolean isDeviceDefaultLight = typedValue.data == 0;
                if (!isDeviceDefaultLight) {
                    strokecolor = 4100607;
                }
            } else {
                strokecolor = typedValue.data;
            }
            this.mDragViewBitmap =
                    SemAnimatorUtils.getBitmapDrawableFromView(this.mDragView).getBitmap();
            Canvas canvas = new Canvas(this.mDragViewBitmap);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(strokecolor);
            paint.setStrokeWidth(strokeWidth);
            Rect r =
                    new Rect(
                            0,
                            0,
                            this.mDragViewBitmap.getWidth() - 1,
                            this.mDragViewBitmap.getHeight() - 1);
            canvas.drawRect(r, paint);
        }
        setDragViewAlpha(255);
        if (this.mDragViewBitmap != null) {
            this.mDndTouchOffsetY = this.mDndTouchY - this.mDragViewRect.top;
        }
        startSelectHighlightingAnimation(this.mDragView);
        if (this.mDndListener != null) {
            Log.d(TAG, "dndListener.OnDragAndDropStart() initDrag");
            this.mDndListener.onDragAndDropStart();
        }
        this.mListView.invalidate();
        return true;
    }

    private void startSelectHighlightingAnimation(View child) {
        Rect hitRect = new Rect();
        child.getHitRect(hitRect);
        this.mListItemSelectionAnimating = true;
        this.mScaleUpAndDownAnimation =
                new SemDragAndDropAnimationCore.ItemSelectHighlightingAnimation(hitRect);
        this.mScaleUpAndDownAnimation.setStartAndDuration(0);
        this.mItemAnimator.putItemAnimation(this.mFirstDragPos, this.mScaleUpAndDownAnimation);
        this.mItemAnimator.start();
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0038, code lost:

       return true;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r7) {
        /*
            r6 = this;
            boolean r0 = r6.isDraggable()
            r1 = 0
            if (r0 == 0) goto L39
            int r0 = r6.mDndTouchMode
            if (r0 != 0) goto Lc
            goto L39
        Lc:
            int r0 = r7.getAction()
            r2 = r0 & 255(0xff, float:3.57E-43)
            r3 = 1
            switch(r2) {
                case 0: goto L37;
                case 1: goto L33;
                case 2: goto L2f;
                case 3: goto L33;
                case 4: goto L16;
                case 5: goto L16;
                case 6: goto L17;
                default: goto L16;
            }
        L16:
            goto L38
        L17:
            r2 = 65280(0xff00, float:9.1477E-41)
            r2 = r2 & r0
            int r2 = r2 >> 8
            int r4 = r7.getPointerId(r2)
            int r5 = r6.mActivePointerId
            if (r4 != r5) goto L38
            if (r2 != 0) goto L28
            r1 = r3
        L28:
            int r5 = r7.getPointerId(r1)
            r6.mActivePointerId = r5
            goto L38
        L2f:
            r6.onTouchMove(r7)
            goto L38
        L33:
            r6.onTouchUpCancel(r7)
            goto L38
        L37:
        L38:
            return r3
        L39:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.animation.SemDragAndDropListAnimator.onTouchEvent(android.view.MotionEvent):boolean");
    }

    private void onTouchMove(MotionEvent event) {
        int pointerIndex = event.findPointerIndex(this.mActivePointerId);
        if (pointerIndex == -1) {
            pointerIndex = 0;
            this.mActivePointerId = event.getPointerId(0);
        }
        this.mDndTouchX = (int) event.getX(pointerIndex);
        this.mDndTouchY = (int) event.getY(pointerIndex);
        if (this.mDndTouchY
                > (this.mListView.getBottom() - this.mListView.getPaddingBottom())
                        - this.mListView.getTop()) {
            this.mDndTouchY =
                    (this.mListView.getBottom() - this.mListView.getPaddingBottom())
                            - this.mListView.getTop();
        } else if (this.mDndTouchY < this.mListView.getPaddingTop()) {
            this.mDndTouchY = this.mListView.getPaddingTop();
        }
        if (this.mScaleUpAndDownAnimation != null && !this.mScaleUpAndDownAnimation.isFinished()) {
            int distance = Math.abs(this.mDndTouchY - this.mFirstTouchY);
            if (distance > 15.0f) {
                this.mListItemSelectionAnimating = false;
            }
        }
        this.mDndTouchMode = 2;
        boolean needScroll = false;
        int top = this.mListView.getPaddingTop();
        View temp = this.mListView.getChildAt(0);
        if (temp != null) {
            top += temp.getHeight() / 2;
        }
        int bottom =
                (this.mListView.getBottom() - this.mListView.getPaddingBottom())
                        - this.mListView.getTop();
        View temp2 = this.mListView.getChildAt(this.mListView.getChildCount() - 1);
        if (temp2 != null) {
            bottom -= temp2.getHeight() / 2;
        }
        if (this.mDndTouchY > bottom || this.mDndTouchY < top) {
            needScroll = true;
            if (this.mDndAutoScrollMode == 0) {
                this.mListView.postOnAnimationDelayed(this.mAutoScrollRunnable, 150L);
            }
            if (this.mDndTouchY > bottom) {
                this.mDndAutoScrollMode = 2;
            }
            if (this.mDndTouchY < top) {
                this.mDndAutoScrollMode = 1;
            }
        }
        if (!needScroll) {
            this.mDndAutoScrollMode = 0;
        }
        if (this.mDndAutoScrollMode == 0) {
            this.mListView.removeCallbacks(this.mAutoScrollRunnable);
        }
        reorderIfNeeded();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void reorderIfNeeded() {
        int prevDestPosition = this.mDragPos;
        int adjustedY =
                (this.mDndTouchY - this.mDndTouchOffsetY) + (this.mDragViewRect.height() / 2);
        int dragPos = findDragItemPosition(adjustedY);
        Log.d(
                TAG,
                "reorderIfNeeded : canDrop #1 mFirstDragPos = "
                        + this.mFirstDragPos
                        + ", dragPos = "
                        + dragPos);
        if (dragPos != -1 && this.mDndController.canDrop(this.mFirstDragPos, dragPos)) {
            this.mDragPos = dragPos;
        }
        if (prevDestPosition != this.mDragPos && !this.mListItemSelectionAnimating) {
            recalculateOffset(prevDestPosition, this.mDragPos);
            this.mItemAnimator.start();
        }
        if (prevDestPosition != this.mDragPos || this.mDragViewBitmap != null) {
            this.mListView.invalidate();
        }
    }

    private void onTouchUpCancel(MotionEvent event) {
        int distance;
        this.mActivePointerId = -1;
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (this.mDndTouchMode == 1) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(
                        TAG,
                        "dndListener.onDragAndDropEnd() onTouchUpCancel DND_TOUCH_STATUS_START #2");
                this.mDndListener.onDragAndDropEnd();
            }
        }
        if (this.mDndTouchMode != 2) {
            return;
        }
        if (this.mListView.getChildCount() == 0) {
            resetDndState();
            return;
        }
        View dragView = this.mListView.getChildAt(this.mFirstDragPos - firstVisiblePosition);
        View dropView = this.mListView.getChildAt(this.mDragPos - firstVisiblePosition);
        if (dragView == null || dropView == null) {
            int offsetY = this.mDndTouchY;
            int draggedBitmapTop = offsetY - this.mDndTouchOffsetY;
            boolean dropViewVisible = dropView != null;
            if (dropViewVisible) {
                distance = dropView.getTop() - draggedBitmapTop;
            } else if (this.mDragPos < firstVisiblePosition) {
                distance =
                        -((draggedBitmapTop - this.mListView.getChildAt(0).getTop())
                                + this.mDragViewRect.height());
            } else {
                distance =
                        this.mListView.getChildAt(this.mListView.getChildCount() - 1).getBottom()
                                - draggedBitmapTop;
            }
            Log.v(TAG, "dndListener.onTouchUp() dragView == null, distance=" + distance);
            ValueAnimator va = ValueAnimator.ofInt(0, distance);
            va.addUpdateListener(
                    new ValueAnimator
                            .AnimatorUpdateListener() { // from class:
                                                        // com.samsung.android.animation.SemDragAndDropListAnimator.3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator animator) {
                            SemDragAndDropListAnimator.this.mDragViewBitmapTranslateY =
                                    ((Integer) animator.getAnimatedValue()).intValue();
                            SemDragAndDropListAnimator.this.mListView.invalidate();
                        }
                    });
            va.addListener(
                    new AnimatorListenerAdapter() { // from class:
                        // com.samsung.android.animation.SemDragAndDropListAnimator.4
                        @Override // android.animation.AnimatorListenerAdapter,
                        // android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator anim) {
                            if (SemDragAndDropListAnimator.this.mFirstDragPos
                                    != SemDragAndDropListAnimator.this.mDragPos) {
                                Log.d(
                                        SemDragAndDropListAnimator.TAG,
                                        "onTouchUpCancel : onAnimationEnd : mDndController.dropDone"
                                            + " #2 , mFirstDragPos = "
                                                + SemDragAndDropListAnimator.this.mFirstDragPos
                                                + ", mDragPos = "
                                                + SemDragAndDropListAnimator.this.mDragPos);
                                SemDragAndDropListAnimator.this.mDndController.dropDone(
                                        SemDragAndDropListAnimator.this.mFirstDragPos,
                                        SemDragAndDropListAnimator.this.mDragPos);
                                SemDragAndDropListAnimator.this.speakDragReleaseForAccessibility(
                                        SemDragAndDropListAnimator.this.mDragPos);
                            }
                            SemDragAndDropListAnimator.this.mItemAnimator.removeAll();
                            SemDragAndDropListAnimator.this.resetDndState();
                            if (SemDragAndDropListAnimator.this.mDndListener != null) {
                                Log.d(
                                        SemDragAndDropListAnimator.TAG,
                                        "dndListener.onDragAndDropEnd() from onAnimationEnd() #3");
                                SemDragAndDropListAnimator.this.mDndListener.onDragAndDropEnd();
                            }
                        }
                    });
            va.setDuration(210L);
            va.setInterpolator(SINE_IN_OUT_70);
            va.start();
        } else if (this.mListItemSelectionAnimating) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(TAG, "dndListener.onDragAndDropEnd() mListItemSelectionAnimating is true #4");
                this.mDndListener.onDragAndDropEnd();
            }
        } else {
            int offsetY2 = dropView.getTop() - dragView.getTop();
            int deltaY = dropView.getTop() - (this.mDndTouchY - this.mDndTouchOffsetY);
            SemDragAndDropAnimationCore.TranslateItemAnimation t =
                    new SemDragAndDropAnimationCore.TranslateItemAnimation();
            t.translate(0, 0, offsetY2, deltaY);
            t.setStartAndDuration(0.7f);
            this.mItemAnimator.putItemAnimation(this.mFirstDragPos, t);
            this.mItemAnimator.start();
            this.mRetainFirstDragViewPos = this.mFirstDragPos - firstVisiblePosition;
            this.mListView.setEnabled(false);
            this.mDropDonePending = true;
            resetDndTouchValuesAndBitmap();
            Log.d(TAG, "onTouchUp() start last animation");
        }
        this.mDndAutoScrollMode = 0;
        this.mListView.removeCallbacks(this.mAutoScrollRunnable);
        this.mListView.invalidate();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void resetDndTouchValuesAndBitmap() {
        super.resetDndTouchValuesAndBitmap();
        this.mNonMovableItems.clear();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void resetDndPositionValues() {
        super.resetDndPositionValues();
        this.mListView.setEnableHoverDrawable(true);
    }

    private int findDragItemPosition(int y) {
        int childCount = this.mListView.getChildCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                View v = this.mListView.getChildAt(i);
                v.getHitRect(this.mTempRect);
                if (this.mTempRect.contains(this.mTempRect.centerX(), y)) {
                    return i + firstVisiblePosition;
                }
            }
            return -1;
        }
        return -1;
    }

    private int findMovedItemPosition(int y) {
        int childCount = this.mListView.getChildCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                if (i != this.mFirstDragPos - firstVisiblePosition) {
                    View v = this.mListView.getChildAt(i);
                    v.getHitRect(this.mTempRect);
                    SemDragAndDropAnimationCore.ItemAnimation ia =
                            this.mItemAnimator.getItemAnimation(i + firstVisiblePosition);
                    if (ia instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
                        int yPosAdjust =
                                ((SemDragAndDropAnimationCore.TranslateItemAnimation) ia)
                                        .getDestOffsetY();
                        if (this.mTempRect.contains(this.mTempRect.centerX(), y - yPosAdjust)) {
                            return i + firstVisiblePosition;
                        }
                    } else {
                        continue;
                    }
                }
            }
            return -1;
        }
        return -1;
    }

    private void addReturningTranslation(int position) {
        SemDragAndDropAnimationCore.ItemAnimation ia =
                this.mItemAnimator.getItemAnimation(position);
        if (!(ia instanceof SemDragAndDropAnimationCore.TranslateItemAnimation)) {
            return;
        }
        SemDragAndDropAnimationCore.TranslateItemAnimation t =
                (SemDragAndDropAnimationCore.TranslateItemAnimation) ia;
        int yCurrentOffset = (int) t.getCurrentTranslateY();
        t.translate(0, 0, 0, -yCurrentOffset);
        t.setStartAndDuration(t.getProgress());
    }

    private void addNewTranslation(int position, int totalHeight) {
        SemDragAndDropAnimationCore.TranslateItemAnimation t;
        SemDragAndDropAnimationCore.ItemAnimation a = this.mItemAnimator.getItemAnimation(position);
        if (a instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            t = (SemDragAndDropAnimationCore.TranslateItemAnimation) a;
        } else {
            t = new SemDragAndDropAnimationCore.TranslateItemAnimation();
        }
        int yCurrentOffset = 0;
        if (!t.isFinished()) {
            yCurrentOffset = (int) t.getCurrentTranslateY();
        }
        t.translate(0, 0, totalHeight, totalHeight - yCurrentOffset);
        if (!t.isFinished()) {
            t.setStartAndDuration(t.getProgress());
        } else {
            t.setStartAndDuration(0);
        }
        this.mItemAnimator.putItemAnimation(position, t);
    }

    private void updateRoundCorner(int pos) {
        int childCorner;
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        View child = this.mListView.getChildAt(pos - firstVisiblePosition);
        if (child != null
                && this.mDragViewRoundCorner != (childCorner = child.semGetRoundedCorners())) {
            child.semSetRoundedCorners(this.mDragViewRoundCorner);
            this.mDragViewRoundCorner = childCorner;
            child.invalidate();
        }
    }

    private void recalculateOffset(int prevPos, int newPos) {
        View fixedView;
        int dividerHeight = this.mListView.getDividerHeight();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        int childHeight = this.mDragViewRect.height() + dividerHeight;
        if (newPos > prevPos) {
            for (int i = prevPos + 1; i <= newPos; i++) {
                if (i > this.mFirstDragPos) {
                    Log.d(
                            TAG,
                            "recalculateOffset : canDrop #2 mFirstDragPos = "
                                    + this.mFirstDragPos
                                    + ", i = "
                                    + i);
                    if (this.mDndController.canDrop(this.mFirstDragPos, i)) {
                        int totalHeight = childHeight;
                        int currentIdx = i;
                        while (true) {
                            currentIdx--;
                            if (this.mNonMovableItems.indexOfKey(currentIdx) < 0) {
                                break;
                            } else {
                                totalHeight += this.mNonMovableItems.get(currentIdx);
                            }
                        }
                        updateRoundCorner(i);
                        addNewTranslation(i, -totalHeight);
                    } else {
                        View fixedView2 = this.mListView.getChildAt(i - firstVisiblePosition);
                        if (fixedView2 != null) {
                            this.mNonMovableItems.put(i, fixedView2.getHeight() + dividerHeight);
                        }
                    }
                } else {
                    View child = this.mListView.getChildAt(i - firstVisiblePosition);
                    if (child == null) {
                        Log.e(TAG, "recalculateOffset('dragging down') no such item, i=" + i);
                    } else {
                        int centerY = SemAnimatorUtils.getViewCenterY(child);
                        int movedPos = findMovedItemPosition(centerY);
                        updateRoundCorner(movedPos);
                        addReturningTranslation(movedPos);
                    }
                }
            }
            return;
        }
        for (int i2 = prevPos - 1; i2 >= newPos; i2--) {
            if (i2 < this.mFirstDragPos) {
                Log.d(
                        TAG,
                        "recalculateOffset : canDrop #3 mFirstDragPos = "
                                + this.mFirstDragPos
                                + ", i = "
                                + i2);
                if (this.mDndController.canDrop(this.mFirstDragPos, i2)) {
                    int totalHeight2 = childHeight;
                    int currentIdx2 = i2;
                    while (true) {
                        currentIdx2++;
                        if (this.mNonMovableItems.indexOfKey(currentIdx2) < 0) {
                            break;
                        } else {
                            totalHeight2 += this.mNonMovableItems.get(currentIdx2);
                        }
                    }
                    updateRoundCorner(i2);
                    addNewTranslation(i2, totalHeight2);
                } else if (this.mNonMovableItems.get(i2, -1) == -1
                        && (fixedView = this.mListView.getChildAt(i2 - firstVisiblePosition))
                                != null) {
                    this.mNonMovableItems.put(i2, fixedView.getHeight() + dividerHeight);
                }
            } else {
                View child2 = this.mListView.getChildAt(i2 - firstVisiblePosition);
                if (child2 == null) {
                    Log.e(TAG, "recalculateOffset('dragging up') no such item, i=" + i2);
                } else {
                    int centerY2 = SemAnimatorUtils.getViewCenterY(child2);
                    int movedPos2 = findMovedItemPosition(centerY2);
                    updateRoundCorner(movedPos2);
                    addReturningTranslation(movedPos2);
                }
            }
        }
    }

    private void getDragGrabHandleHitRect(Rect childRect, Rect outGrabHandleRect) {
        if (this.mDragGrabHandleDrawable != null) {
            int drawableWidth = this.mDragGrabHandleDrawable.getIntrinsicWidth();
            int drawableHeight = this.mDragGrabHandleDrawable.getIntrinsicHeight();
            boolean isLayoutRtl = this.mListView.isLayoutRtl();
            if (isLayoutRtl) {
                childRect.left += this.mDragGrabHandlePadding.right;
                childRect.top += this.mDragGrabHandlePadding.top;
                childRect.right -= this.mDragGrabHandlePadding.left;
                childRect.bottom += this.mDragGrabHandlePadding.bottom;
                childRect.left += 10;
                childRect.right += 10;
                Gravity.apply(
                        this.mDragGrabHandlePosGravity,
                        drawableWidth,
                        drawableHeight,
                        childRect,
                        outGrabHandleRect,
                        1);
                return;
            }
            childRect.left += this.mDragGrabHandlePadding.left;
            childRect.top += this.mDragGrabHandlePadding.top;
            childRect.right += this.mDragGrabHandlePadding.right;
            childRect.bottom += this.mDragGrabHandlePadding.bottom;
            childRect.left -= 10;
            childRect.right -= 10;
            Gravity.apply(
                    this.mDragGrabHandlePosGravity,
                    drawableWidth,
                    drawableHeight,
                    childRect,
                    outGrabHandleRect,
                    0);
        }
    }

    private void drawDragHandle(
            Canvas canvas, Rect childRect, boolean isDraggedItem, boolean isAllowDragItem) {
        Log.d(TAG, "drawDragHandle : isAllowDragItem = " + isAllowDragItem);
        if (this.mDragGrabHandleDrawable != null && isAllowDragItem) {
            getDragGrabHandleHitRect(childRect, this.mTempRect);
            this.mDragGrabHandleDrawable.setBounds(this.mTempRect);
            this.mDragGrabHandleDrawable.setState(
                    isDraggedItem ? PRESSED_STATE_SET : EMPTY_STATE_SET);
            Log.d(TAG, "drawDragHandle : call mDragGrabHandleDrawable.draw.. ");
            this.mDragGrabHandleDrawable.draw(canvas);
            return;
        }
        Log.d(TAG, "drawDragHandle : not draw drageGrabHandle~~!! ");
    }

    public boolean preDrawChild(Canvas canvas, View child, long drawingTime) {
        int index = this.mListView.indexOfChild(child);
        int pos = this.mListView.getFirstVisiblePosition() + index;
        if (isDraggable()
                && pos == this.mFirstDragPos
                && !this.mDropDonePending
                && !this.mListItemSelectionAnimating) {
            return false;
        }
        SemDragAndDropAnimationCore.ItemAnimation ia = this.mItemAnimator.getItemAnimation(pos);
        this.mCanvasSaveCount = 0;
        if (ia != null) {
            ia.getTransformation(this.mTempTrans);
            this.mCanvasSaveCount = canvas.save();
            canvas.concat(this.mTempTrans.getMatrix());
            return true;
        }
        return true;
    }

    public void postDrawChild(Canvas canvas, View child, long drawingTime) {
        Log.d(TAG, "postDrawChild : call drawDragHandlerIfNeeded");
        drawDragHandlerIfNeeded(canvas, child, drawingTime);
        if (this.mCanvasSaveCount > 0) {
            canvas.restoreToCount(this.mCanvasSaveCount);
        }
    }

    private void drawDragHandlerIfNeeded(Canvas canvas, View child, long drawingTime) {
        if (isDraggable()) {
            int index = this.mListView.indexOfChild(child);
            int pos = this.mListView.getFirstVisiblePosition() + index;
            if (this.mListView.getAdapter().isEnabled(pos) && !isHeaderOrFooterViewPos(pos)) {
                child.getHitRect(this.mTempRect);
                Log.d(TAG, "drawDragHandlerIfNeeded : canDrag #2 pos = " + pos);
                drawDragHandle(canvas, this.mTempRect, false, this.mDndController.canDrag(pos));
            }
        }
    }

    private boolean isHeaderOrFooterViewPos(int pos) {
        if (pos < this.mListView.getHeaderViewsCount()
                || pos >= this.mListView.getCount() - this.mListView.getFooterViewsCount()) {
            return true;
        }
        return false;
    }

    public void dispatchDraw(Canvas canvas) {
        if (isDraggable() && this.mDragViewBitmap != null) {
            int draggedItemX = this.mListView.getPaddingLeft();
            int draggedItemY = this.mDndTouchY - this.mDndTouchOffsetY;
            if (this.mListItemSelectionAnimating || this.mDragViewBitmap.isRecycled()) {
                return;
            }
            canvas.drawBitmap(
                    this.mDragViewBitmap,
                    draggedItemX,
                    this.mDragViewBitmapTranslateY + draggedItemY,
                    this.mDragViewBitmapPaint);
            if ((this.mDragGrabHandlePosGravity & 5) == 5) {
                this.mTempRect.left = -this.mListView.getPaddingRight();
            } else {
                this.mTempRect.left = this.mListView.getPaddingLeft();
            }
            this.mTempRect.top = this.mDragViewBitmapTranslateY + draggedItemY;
            this.mTempRect.bottom = this.mTempRect.top + this.mDragViewRect.height();
            this.mTempRect.right = this.mTempRect.left + this.mListView.getWidth();
            drawDragHandle(canvas, this.mTempRect, true, true);
        }
    }

    private class HeaderFooterDndController
            implements SemAbsDragAndDropAnimator.DragAndDropController {
        private final SemAbsDragAndDropAnimator.DragAndDropController mWrappedController;

        HeaderFooterDndController(
                SemAbsDragAndDropAnimator.DragAndDropController wrappedController) {
            this.mWrappedController = wrappedController;
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public boolean canDrag(int startPos) {
            if (this.mWrappedController == null
                    || startPos < SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()
                    || startPos
                            >= SemDragAndDropListAnimator.this.mListView.getCount()
                                    - SemDragAndDropListAnimator.this.mListView
                                            .getFooterViewsCount()) {
                return false;
            }
            Log.d(
                    SemDragAndDropListAnimator.TAG,
                    "HeaderFooterDndController : canDrag #3 mListView.getHeaderViewsCount() = "
                            + SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount());
            return this.mWrappedController.canDrag(
                    startPos - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount());
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public boolean canDrop(int startPos, int destPos) {
            if (this.mWrappedController == null
                    || destPos < SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()
                    || destPos
                            >= SemDragAndDropListAnimator.this.mListView.getCount()
                                    - SemDragAndDropListAnimator.this.mListView
                                            .getFooterViewsCount()) {
                return false;
            }
            Log.d(
                    SemDragAndDropListAnimator.TAG,
                    "HeaderFooterDndController : canDrop #4 startPos -"
                        + " mListView.getHeaderViewsCount() = "
                            + (startPos
                                    - SemDragAndDropListAnimator.this.mListView
                                            .getHeaderViewsCount())
                            + ", destPos = "
                            + destPos);
            return this.mWrappedController.canDrop(
                    startPos - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount(),
                    destPos - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount());
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public void dropDone(int startPos, int destPos) {
            if (this.mWrappedController != null) {
                if (startPos < SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()) {
                    startPos = SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount();
                } else if (startPos
                        > SemDragAndDropListAnimator.this.mListView.getCount()
                                - SemDragAndDropListAnimator.this.mListView.getFooterViewsCount()) {
                    startPos =
                            (SemDragAndDropListAnimator.this.mListView.getCount()
                                            - SemDragAndDropListAnimator.this.mListView
                                                    .getFooterViewsCount())
                                    - 1;
                }
                if (destPos < SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()) {
                    destPos = SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount();
                } else if (destPos
                        >= SemDragAndDropListAnimator.this.mListView.getCount()
                                - SemDragAndDropListAnimator.this.mListView.getFooterViewsCount()) {
                    destPos =
                            (SemDragAndDropListAnimator.this.mListView.getCount()
                                            - SemDragAndDropListAnimator.this.mListView
                                                    .getFooterViewsCount())
                                    - 1;
                }
                Log.d(
                        SemDragAndDropListAnimator.TAG,
                        "HeaderFooterDndController : dropDone : mWrappedController.dropDone #3");
                Log.d(
                        SemDragAndDropListAnimator.TAG,
                        "HeaderFooterDndController : dropDone : startPos -"
                            + " mListView.getHeaderViewsCount() = "
                                + (startPos
                                        - SemDragAndDropListAnimator.this.mListView
                                                .getHeaderViewsCount()));
                Log.d(
                        SemDragAndDropListAnimator.TAG,
                        "HeaderFooterDndController : dropDone : destPos -"
                            + " mListView.getHeaderViewsCount() = "
                                + (destPos
                                        - SemDragAndDropListAnimator.this.mListView
                                                .getHeaderViewsCount()));
                this.mWrappedController.dropDone(
                        startPos - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount(),
                        destPos - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount());
            }
        }
    }
}
