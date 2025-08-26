package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import com.samsung.android.animation.SemAbsDragAndDropAnimator;
import com.samsung.android.animation.SemDragAndDropAnimationCore;
import java.util.HashSet;

@Deprecated
/* loaded from: classes6.dex */
public class SemDragAndDropGridAnimator extends SemAbsDragAndDropAnimator {
    private static final String TAG = "SemDragAndDropGridAnimator";
    private GridView mGridView;
    private SemDragAndDropAnimationCore.ItemAnimationListener mItemAnimationListener;
    private int mItemHeight;
    private int mItemWidth;
    HashSet<Integer> mNonMovableItems;
    private AdapterView.OnItemLongClickListener mOnItemLongClickListener;

    public SemDragAndDropGridAnimator(Context context, GridView gridView) {
        super(context, gridView);
        this.mNonMovableItems = new HashSet<>();
        this.mGridView = gridView;
        gridView.setDndGridAnimator(this);
        this.mItemWidth = Integer.MIN_VALUE;
        this.mItemHeight = Integer.MIN_VALUE;
        initListeners();
        this.mDndAnimationCore.setAnimationListener(this.mItemAnimationListener);
        this.mGridView.setOnItemLongClickListener(this.mOnItemLongClickListener);
        this.mGridView.setSelector(17170445);
    }

    private void initListeners() {
        this.mItemAnimationListener = new SemDragAndDropAnimationCore.ItemAnimationListener() { // from class: com.samsung.android.animation.SemDragAndDropGridAnimator.1
            @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimationListener
            public void onItemAnimatorEnd() {
                if (SemDragAndDropGridAnimator.this.mListItemSelectionAnimating) {
                    SemDragAndDropGridAnimator.this.mListItemSelectionAnimating = false;
                    SemDragAndDropGridAnimator.this.updateDragViewBitmap();
                    return;
                }
                if (SemDragAndDropGridAnimator.this.mDropDonePending) {
                    SemDragAndDropGridAnimator.this.mDropDonePending = false;
                    if (SemDragAndDropGridAnimator.this.mDragPos != SemDragAndDropGridAnimator.this.mFirstDragPos) {
                        SemDragAndDropGridAnimator.this.mDndController.dropDone(SemDragAndDropGridAnimator.this.mFirstDragPos, SemDragAndDropGridAnimator.this.mDragPos);
                        SemDragAndDropGridAnimator semDragAndDropGridAnimator = SemDragAndDropGridAnimator.this;
                        semDragAndDropGridAnimator.speakDragReleaseForAccessibility(semDragAndDropGridAnimator.mDragPos);
                    }
                    SemDragAndDropGridAnimator.this.mItemAnimator.removeAll();
                    SemDragAndDropGridAnimator.this.resetDndPositionValues();
                    if (SemDragAndDropGridAnimator.this.mDndListener != null) {
                        Log.d(SemDragAndDropGridAnimator.TAG, "dndListener.onDragAndDropEnd() from onAllAnimationsFinished()");
                        SemDragAndDropGridAnimator.this.mDndListener.onDragAndDropEnd();
                    }
                    SemDragAndDropGridAnimator.this.mGridView.setEnabled(true);
                }
            }
        };
        this.mOnItemLongClickListener = new AdapterView.OnItemLongClickListener() { // from class: com.samsung.android.animation.SemDragAndDropGridAnimator.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                return SemDragAndDropGridAnimator.this.initDragIfNecessary(i);
            }
        };
    }

    public void setDragAndDropController(SemAbsDragAndDropAnimator.DragAndDropController dragAndDropController) {
        this.mDndController = dragAndDropController;
    }

    private boolean checkStartDnd(int i, int i2, int i3) {
        if (!checkDndGrabHandle(i, i2, i3)) {
            return false;
        }
        boolean zCanDrag = this.mDndController.canDrag(i3);
        if (!zCanDrag) {
            speakNotDraggableForAccessibility(i3);
        }
        return zCanDrag;
    }

    private boolean checkDndGrabHandle(int i, int i2, int i3) {
        if (activatedByLongPress()) {
            return true;
        }
        Rect rect = new Rect();
        GridView gridView = this.mGridView;
        gridView.getChildAt(i3 - gridView.getFirstVisiblePosition()).getHitRect(rect);
        getDragGrabHandleHitRect(rect, this.mTempRect);
        return this.mTempRect.contains(i, i2);
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x002b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int iPointToPosition;
        int action = motionEvent.getAction();
        if (action != 0) {
            if (action == 1) {
                if (isDraggable() && this.mDndTouchMode != 0) {
                    onTouchUpCancel(motionEvent);
                }
            } else if (action != 2) {
                if (action == 3) {
                }
            } else if (isDraggable() && this.mDndTouchMode == 1 && this.mGridView.getCount() > 1 && activatedByLongPress()) {
                return true;
            }
        } else {
            if (!this.mGridView.isEnabled()) {
                return false;
            }
            this.mFirstTouchX = this.mDndTouchX;
            this.mFirstTouchY = this.mDndTouchY;
            this.mDndTouchX = (int) motionEvent.getX();
            this.mDndTouchY = (int) motionEvent.getY();
            if (!isDraggable() || this.mGridView.getCount() <= 1 || (iPointToPosition = this.mGridView.pointToPosition(this.mDndTouchX, this.mDndTouchY)) == -1 || activatedByLongPress()) {
                return false;
            }
            if (iPointToPosition >= 0 && iPointToPosition < this.mGridView.getCount() && checkStartDnd(this.mDndTouchX, this.mDndTouchY, iPointToPosition)) {
                if (initDrag(iPointToPosition)) {
                    return true;
                }
            } else {
                resetDndState();
            }
        }
        return false;
    }

    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
        return this.mOnItemLongClickListener.onItemLongClick(adapterView, view, i, j);
    }

    public AdapterView.OnItemLongClickListener getDragAndDropOnItemLongClickListener() {
        return this.mOnItemLongClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initDragIfNecessary(int i) {
        if (!isDraggable() || !activatedByLongPress() || this.mGridView.getCount() <= 1) {
            return false;
        }
        if (i >= 0 && i < this.mGridView.getCount() && checkStartDnd(this.mDndTouchX, this.mDndTouchY, i)) {
            return initDrag(i);
        }
        resetDndState();
        return false;
    }

    private boolean initDrag(int i) {
        findMovingArrage();
        GridView gridView = this.mGridView;
        this.mDragView = gridView.getChildAt(i - gridView.getFirstVisiblePosition());
        if (this.mDragView == null) {
            return false;
        }
        this.mGridView.setEnableHoverDrawable(false);
        this.mDndTouchMode = 2;
        this.mFirstDragPos = i;
        this.mDragPos = this.mFirstDragPos;
        this.mDragView.setPressed(false);
        this.mDragView.getHitRect(this.mDragViewRect);
        speakDragStartForAccessibility(i);
        if (this.mDragViewBitmap != null) {
            this.mDragViewBitmap.recycle();
        }
        updateDragViewBitmap();
        setDragViewAlpha(this.mDragViewBitmapAlpha);
        if (this.mDragViewBitmap != null) {
            this.mDndTouchOffsetX = this.mDndTouchX - this.mDragViewRect.left;
            this.mDndTouchOffsetY = this.mDndTouchY - this.mDragViewRect.top;
        }
        startSelectHighlightingAnimation(this.mDragView);
        if (this.mDndListener != null) {
            Log.d(TAG, "dndListener.OnDragAndDropStart()");
            this.mDndListener.onDragAndDropStart();
        }
        this.mGridView.invalidate();
        return true;
    }

    private void startSelectHighlightingAnimation(View view) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        this.mListItemSelectionAnimating = true;
        this.mScaleUpAndDownAnimation = new SemDragAndDropAnimationCore.ItemSelectHighlightingAnimation(rect);
        this.mScaleUpAndDownAnimation.setStartAndDuration(0);
        this.mItemAnimator.putItemAnimation(this.mFirstDragPos, this.mScaleUpAndDownAnimation);
        this.mItemAnimator.start();
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isDraggable() || this.mDndTouchMode == 0) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 1) {
            onTouchUpCancel(motionEvent);
        } else if (action == 2) {
            onTouchMove(motionEvent);
        } else if (action == 3) {
        }
        return true;
    }

    private void onTouchUpCancel(MotionEvent motionEvent) {
        final int left;
        int height;
        final int iHeight;
        if (this.mDndTouchMode == 1) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(TAG, "dndListener.onDragAndDropEnd() DND_TOUCH_STATUS_START");
                this.mDndListener.onDragAndDropEnd();
            }
        }
        if (this.mDndTouchMode != 2) {
            return;
        }
        int firstVisiblePosition = this.mGridView.getFirstVisiblePosition();
        int childCount = this.mGridView.getChildCount();
        View childAt = this.mGridView.getChildAt(this.mFirstDragPos - firstVisiblePosition);
        View childAt2 = this.mGridView.getChildAt(this.mDragPos - firstVisiblePosition);
        if (childAt == null || childAt2 == null) {
            int i = this.mDndTouchX - this.mDndTouchOffsetX;
            int i2 = this.mDndTouchY - this.mDndTouchOffsetY;
            if (childAt2 != null) {
                left = childAt2.getLeft() - i;
                height = childAt2.getTop();
            } else {
                int numColumns = this.mGridView.getNumColumns();
                if (childCount < numColumns) {
                    Log.e(TAG, "Child cound (" + this.mGridView.getChildCount() + ") is smaller than column count (" + numColumns + NavigationBarInflaterView.KEY_CODE_END);
                    resetDndState();
                    return;
                }
                if (this.mDragPos < firstVisiblePosition) {
                    left = this.mGridView.getChildAt(this.mDragPos % numColumns).getLeft() - i;
                    iHeight = ((-this.mGridView.getPaddingTop()) - i2) - this.mDragViewRect.height();
                    Log.v(TAG, "dndListener.onTouchUp() dragView == null, distanceX=" + left + ", distanceY=" + iHeight);
                    ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                    valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemDragAndDropGridAnimator.3
                        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            SemDragAndDropGridAnimator.this.mDragViewBitmapTranslateX = (int) (left * valueAnimator.getAnimatedFraction());
                            SemDragAndDropGridAnimator.this.mDragViewBitmapTranslateY = (int) (iHeight * valueAnimator.getAnimatedFraction());
                            SemDragAndDropGridAnimator.this.mGridView.invalidate();
                        }
                    });
                    valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemDragAndDropGridAnimator.4
                        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                        public void onAnimationEnd(Animator animator) {
                            if (SemDragAndDropGridAnimator.this.mFirstDragPos != SemDragAndDropGridAnimator.this.mDragPos) {
                                SemDragAndDropGridAnimator.this.mDndController.dropDone(SemDragAndDropGridAnimator.this.mFirstDragPos, SemDragAndDropGridAnimator.this.mDragPos);
                                SemDragAndDropGridAnimator semDragAndDropGridAnimator = SemDragAndDropGridAnimator.this;
                                semDragAndDropGridAnimator.speakDragReleaseForAccessibility(semDragAndDropGridAnimator.mDragPos);
                            }
                            SemDragAndDropGridAnimator.this.mItemAnimator.removeAll();
                            SemDragAndDropGridAnimator.this.resetDndState();
                            if (SemDragAndDropGridAnimator.this.mDndListener != null) {
                                Log.d(SemDragAndDropGridAnimator.TAG, "dndListener.onDragAndDropEnd() from AnimationEnd");
                                SemDragAndDropGridAnimator.this.mDndListener.onDragAndDropEnd();
                            }
                        }
                    });
                    valueAnimatorOfFloat.setDuration(210L);
                    valueAnimatorOfFloat.setInterpolator(SINE_IN_OUT_70);
                    valueAnimatorOfFloat.start();
                } else {
                    left = this.mGridView.getChildAt((this.mGridView.getChildCount() + (this.mDragPos % numColumns)) - numColumns).getLeft() - i;
                    height = this.mGridView.getHeight();
                }
            }
            iHeight = height - i2;
            Log.v(TAG, "dndListener.onTouchUp() dragView == null, distanceX=" + left + ", distanceY=" + iHeight);
            ValueAnimator valueAnimatorOfFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
            valueAnimatorOfFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemDragAndDropGridAnimator.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SemDragAndDropGridAnimator.this.mDragViewBitmapTranslateX = (int) (left * valueAnimator.getAnimatedFraction());
                    SemDragAndDropGridAnimator.this.mDragViewBitmapTranslateY = (int) (iHeight * valueAnimator.getAnimatedFraction());
                    SemDragAndDropGridAnimator.this.mGridView.invalidate();
                }
            });
            valueAnimatorOfFloat2.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemDragAndDropGridAnimator.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (SemDragAndDropGridAnimator.this.mFirstDragPos != SemDragAndDropGridAnimator.this.mDragPos) {
                        SemDragAndDropGridAnimator.this.mDndController.dropDone(SemDragAndDropGridAnimator.this.mFirstDragPos, SemDragAndDropGridAnimator.this.mDragPos);
                        SemDragAndDropGridAnimator semDragAndDropGridAnimator = SemDragAndDropGridAnimator.this;
                        semDragAndDropGridAnimator.speakDragReleaseForAccessibility(semDragAndDropGridAnimator.mDragPos);
                    }
                    SemDragAndDropGridAnimator.this.mItemAnimator.removeAll();
                    SemDragAndDropGridAnimator.this.resetDndState();
                    if (SemDragAndDropGridAnimator.this.mDndListener != null) {
                        Log.d(SemDragAndDropGridAnimator.TAG, "dndListener.onDragAndDropEnd() from AnimationEnd");
                        SemDragAndDropGridAnimator.this.mDndListener.onDragAndDropEnd();
                    }
                }
            });
            valueAnimatorOfFloat2.setDuration(210L);
            valueAnimatorOfFloat2.setInterpolator(SINE_IN_OUT_70);
            valueAnimatorOfFloat2.start();
        } else if (this.mListItemSelectionAnimating) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(TAG, "dndListener.onDragAndDropEnd() mListItemSelectionAnimating is true");
                this.mDndListener.onDragAndDropEnd();
            }
        } else {
            int left2 = childAt2.getLeft() - childAt.getLeft();
            int top = childAt2.getTop() - childAt.getTop();
            int left3 = childAt2.getLeft() - (this.mDndTouchX - this.mDndTouchOffsetX);
            int top2 = childAt2.getTop() - (this.mDndTouchY - this.mDndTouchOffsetY);
            SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation = new SemDragAndDropAnimationCore.TranslateItemAnimation();
            translateItemAnimation.translate(left2, left3, top, top2);
            translateItemAnimation.setStartAndDuration(0.7f);
            this.mItemAnimator.putItemAnimation(this.mFirstDragPos, translateItemAnimation);
            this.mItemAnimator.start();
            this.mRetainFirstDragViewPos = this.mFirstDragPos - firstVisiblePosition;
            this.mGridView.setEnabled(false);
            this.mDropDonePending = true;
            resetDndTouchValuesAndBitmap();
        }
        this.mGridView.removeCallbacks(this.mAutoScrollRunnable);
        this.mGridView.invalidate();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void resetDndTouchValuesAndBitmap() {
        super.resetDndTouchValuesAndBitmap();
        this.mItemWidth = Integer.MIN_VALUE;
        this.mItemHeight = Integer.MIN_VALUE;
        this.mNonMovableItems.clear();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void resetDndPositionValues() {
        super.resetDndPositionValues();
        this.mGridView.setEnableHoverDrawable(true);
    }

    private void onTouchMove(MotionEvent motionEvent) {
        this.mDndTouchX = (int) motionEvent.getX();
        this.mDndTouchY = (int) motionEvent.getY();
        if (this.mListItemSelectionAnimating && this.mScaleUpAndDownAnimation != null && !this.mScaleUpAndDownAnimation.isFinished() && Math.max(Math.abs(this.mDndTouchX - this.mFirstTouchX), Math.abs(this.mDndTouchY - this.mFirstTouchY)) > 15.0f) {
            this.mListItemSelectionAnimating = false;
            updateDragViewBitmap();
        }
        int paddingTop = this.mGridView.getPaddingTop();
        View childAt = this.mGridView.getChildAt(0);
        if (childAt != null) {
            paddingTop += childAt.getHeight() / 2;
        }
        int bottom = (this.mGridView.getBottom() - this.mGridView.getPaddingBottom()) - this.mGridView.getTop();
        GridView gridView = this.mGridView;
        View childAt2 = gridView.getChildAt(gridView.getChildCount() - 1);
        if (childAt2 != null) {
            bottom -= childAt2.getHeight() / 2;
        }
        if (this.mDndTouchY > bottom || this.mDndTouchY < paddingTop) {
            if (this.mDndAutoScrollMode == 0) {
                this.mGridView.postOnAnimationDelayed(this.mAutoScrollRunnable, 150L);
            }
            if (this.mDndTouchY > bottom) {
                this.mDndAutoScrollMode = 2;
            }
            if (this.mDndTouchY < paddingTop) {
                this.mDndAutoScrollMode = 1;
            }
        } else {
            this.mDndAutoScrollMode = 0;
        }
        if (this.mDndAutoScrollMode == 0) {
            this.mGridView.removeCallbacks(this.mAutoScrollRunnable);
        }
        reorderIfNeeded();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void reorderIfNeeded() {
        int i = this.mDragPos;
        int iPointToPosition = this.mGridView.pointToPosition((this.mDndTouchX - this.mDndTouchOffsetX) + (this.mDragViewRect.width() / 2), (this.mDndTouchY - this.mDndTouchOffsetY) + (this.mDragViewRect.height() / 2));
        if (iPointToPosition != -1) {
            if (this.mDndController.canDrop(this.mFirstDragPos, iPointToPosition)) {
                this.mDragPos = iPointToPosition;
            } else if (this.mDragPos > iPointToPosition) {
                while (true) {
                    iPointToPosition++;
                    if (iPointToPosition >= this.mDragPos) {
                        break;
                    } else if (this.mDndController.canDrop(this.mFirstDragPos, iPointToPosition)) {
                        this.mDragPos = iPointToPosition;
                        break;
                    }
                }
            } else {
                while (true) {
                    iPointToPosition--;
                    if (iPointToPosition <= this.mDragPos) {
                        break;
                    } else if (this.mDndController.canDrop(this.mFirstDragPos, iPointToPosition)) {
                        this.mDragPos = iPointToPosition;
                        break;
                    }
                }
            }
        }
        if (i != this.mDragPos) {
            this.mListItemSelectionAnimating = false;
            recalculateOffset(i, this.mDragPos);
            this.mItemAnimator.start();
        }
        if (i == this.mDragPos && this.mDragViewBitmap == null) {
            return;
        }
        this.mGridView.invalidate();
    }

    private int findMovedItemIndex(View view) {
        int destOffsetX;
        int destOffsetY;
        int viewCenterX = SemAnimatorUtils.getViewCenterX(view);
        int viewCenterY = SemAnimatorUtils.getViewCenterY(view);
        int childCount = this.mGridView.getChildCount();
        int firstVisiblePosition = this.mGridView.getFirstVisiblePosition();
        if (childCount <= 0) {
            return -1;
        }
        for (int i = childCount - 1; i >= 0; i--) {
            this.mGridView.getChildAt(i).getHitRect(this.mTempRect);
            int i2 = i + firstVisiblePosition;
            SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i2);
            if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
                SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation = (SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation;
                destOffsetX = translateItemAnimation.getDestOffsetX();
                destOffsetY = translateItemAnimation.getDestOffsetY();
            } else {
                destOffsetX = 0;
                destOffsetY = 0;
            }
            if (i != this.mFirstDragPos - firstVisiblePosition && this.mTempRect.contains(viewCenterX - destOffsetX, viewCenterY - destOffsetY)) {
                return i2;
            }
        }
        return -1;
    }

    private void findMovingArrage() {
        if (this.mGridView.getCount() >= 2) {
            View childAt = this.mGridView.getChildAt(0);
            View childAt2 = this.mGridView.getChildAt(1);
            if (childAt == null || childAt2 == null) {
                return;
            }
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            childAt.getHitRect(rect);
            childAt2.getHitRect(rect2);
            this.mItemWidth = Math.abs(rect2.left - rect.left);
        } else {
            this.mItemWidth = 0;
        }
        if (this.mGridView.getCount() > this.mGridView.getNumColumns()) {
            View childAt3 = this.mGridView.getChildAt(0);
            GridView gridView = this.mGridView;
            View childAt4 = gridView.getChildAt(gridView.getNumColumns());
            if (childAt3 == null || childAt4 == null) {
                return;
            }
            Rect rect3 = new Rect();
            Rect rect4 = new Rect();
            childAt3.getHitRect(rect3);
            childAt4.getHitRect(rect4);
            this.mItemHeight = Math.abs(rect4.top - rect3.top);
            return;
        }
        this.mItemHeight = 0;
    }

    private void recalculateOffset(int i, int i2) {
        int i3;
        int i4;
        int firstVisiblePosition = this.mGridView.getFirstVisiblePosition();
        int numColumns = this.mGridView.getNumColumns();
        boolean zIsLayoutRtl = this.mGridView.isLayoutRtl();
        if (i2 <= i) {
            for (int i5 = i - 1; i5 >= i2; i5--) {
                if (i5 < this.mFirstDragPos) {
                    if (this.mDndController.canDrop(this.mFirstDragPos, i5)) {
                        int i6 = i5 + 1;
                        int i7 = 0;
                        for (int i8 = i6; this.mNonMovableItems.contains(Integer.valueOf(i8)); i8++) {
                            i7++;
                        }
                        int i9 = i6 + i7;
                        int i10 = (i9 / numColumns) - (i5 / numColumns);
                        int i11 = (i9 % numColumns) - (i5 % numColumns);
                        if (zIsLayoutRtl) {
                            i3 = i11 * this.mItemWidth * (-1);
                        } else {
                            i3 = i11 * this.mItemWidth;
                        }
                        addNewTranslation(i5, i3, i10 * this.mItemHeight);
                    } else {
                        this.mNonMovableItems.add(Integer.valueOf(i5));
                    }
                } else {
                    View childAt = this.mGridView.getChildAt(i5 - firstVisiblePosition);
                    if (childAt == null) {
                        Log.e(TAG, "recalculateOffset('dragging up') no such item, i=" + i5);
                    } else {
                        addReturningTranslation(findMovedItemIndex(childAt));
                    }
                }
            }
            return;
        }
        while (true) {
            i++;
            if (i > i2) {
                return;
            }
            if (i > this.mFirstDragPos) {
                if (this.mDndController.canDrop(this.mFirstDragPos, i)) {
                    int i12 = i - 1;
                    int i13 = 0;
                    for (int i14 = i12; this.mNonMovableItems.contains(Integer.valueOf(i14)); i14--) {
                        i13++;
                    }
                    int i15 = i12 - i13;
                    int i16 = (i15 / numColumns) - (i / numColumns);
                    int i17 = (i15 % numColumns) - (i % numColumns);
                    if (zIsLayoutRtl) {
                        i4 = i17 * this.mItemWidth * (-1);
                    } else {
                        i4 = i17 * this.mItemWidth;
                    }
                    addNewTranslation(i, i4, i16 * this.mItemHeight);
                } else {
                    this.mNonMovableItems.add(Integer.valueOf(i));
                }
            } else {
                View childAt2 = this.mGridView.getChildAt(i - firstVisiblePosition);
                if (childAt2 == null) {
                    Log.e(TAG, "recalculateOffset('dragging down') no such item, i=" + i);
                } else {
                    addReturningTranslation(findMovedItemIndex(childAt2));
                }
            }
        }
    }

    private void addNewTranslation(int i, int i2, int i3) {
        SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation;
        int currentTranslateX;
        int currentTranslateY;
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i);
        if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            translateItemAnimation = (SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation;
        } else {
            translateItemAnimation = new SemDragAndDropAnimationCore.TranslateItemAnimation();
        }
        int destOffsetX = translateItemAnimation.getDestOffsetX();
        int destOffsetY = translateItemAnimation.getDestOffsetY();
        if (translateItemAnimation.isFinished()) {
            currentTranslateX = 0;
            currentTranslateY = 0;
        } else {
            currentTranslateX = (int) translateItemAnimation.getCurrentTranslateX();
            currentTranslateY = (int) translateItemAnimation.getCurrentTranslateY();
        }
        if (!translateItemAnimation.isFinished()) {
            translateItemAnimation.setStartAndDuration(translateItemAnimation.getProgress());
        } else {
            translateItemAnimation.setStartAndDuration(0);
        }
        int i4 = i2 + destOffsetX;
        int i5 = i3 + destOffsetY;
        translateItemAnimation.translate(i4, i4 - currentTranslateX, i5, i5 - currentTranslateY);
        this.mItemAnimator.putItemAnimation(i, translateItemAnimation);
    }

    private void addReturningTranslation(int i) {
        SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation;
        int currentTranslateX;
        int currentTranslateY;
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i);
        if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            translateItemAnimation = (SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation;
            currentTranslateX = (int) translateItemAnimation.getCurrentTranslateX();
            currentTranslateY = (int) translateItemAnimation.getCurrentTranslateY();
        } else {
            translateItemAnimation = new SemDragAndDropAnimationCore.TranslateItemAnimation();
            currentTranslateX = 0;
            currentTranslateY = 0;
        }
        translateItemAnimation.translate(0, -currentTranslateX, 0, -currentTranslateY);
        translateItemAnimation.setStartAndDuration(translateItemAnimation.getProgress());
        this.mItemAnimator.putItemAnimation(i, translateItemAnimation);
    }

    private void getDragGrabHandleHitRect(Rect rect, Rect rect2) {
        if (this.mDragGrabHandleDrawable != null) {
            int intrinsicWidth = this.mDragGrabHandleDrawable.getIntrinsicWidth();
            int intrinsicHeight = this.mDragGrabHandleDrawable.getIntrinsicHeight();
            if (this.mGridView.isLayoutRtl()) {
                rect.left += this.mDragGrabHandlePadding.right;
                rect.top += this.mDragGrabHandlePadding.top;
                rect.right -= this.mDragGrabHandlePadding.left;
                rect.bottom += this.mDragGrabHandlePadding.bottom;
                Gravity.apply(this.mDragGrabHandlePosGravity, intrinsicWidth, intrinsicHeight, rect, rect2, 1);
                return;
            }
            rect.left += this.mDragGrabHandlePadding.left;
            rect.top += this.mDragGrabHandlePadding.top;
            rect.right += this.mDragGrabHandlePadding.right;
            rect.bottom += this.mDragGrabHandlePadding.bottom;
            Gravity.apply(this.mDragGrabHandlePosGravity, intrinsicWidth, intrinsicHeight, rect, rect2, 0);
        }
    }

    private void drawDragHandle(Canvas canvas, Rect rect, boolean z, boolean z2) {
        if (this.mDragGrabHandleDrawable == null || !z2) {
            return;
        }
        getDragGrabHandleHitRect(rect, this.mTempRect);
        this.mDragGrabHandleDrawable.setBounds(this.mTempRect);
        this.mDragGrabHandleDrawable.setState(z ? PRESSED_STATE_SET : EMPTY_STATE_SET);
        this.mDragGrabHandleDrawable.setAlpha(this.mDragHandleAlpha);
        this.mDragGrabHandleDrawable.draw(canvas);
    }

    public boolean preDrawChild(Canvas canvas, View view, long j) {
        int iIndexOfChild = this.mGridView.indexOfChild(view) + this.mGridView.getFirstVisiblePosition();
        if (isDraggable() && iIndexOfChild == this.mFirstDragPos && !this.mDropDonePending && !this.mListItemSelectionAnimating) {
            return false;
        }
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(iIndexOfChild);
        this.mCanvasSaveCount = 0;
        if (itemAnimation == null) {
            return true;
        }
        itemAnimation.getTransformation(this.mTempTrans);
        this.mCanvasSaveCount = canvas.save();
        canvas.concat(this.mTempTrans.getMatrix());
        return true;
    }

    public void postDrawChild(Canvas canvas, View view, long j) {
        drawDragHandlerIfNeeded(canvas, view, j);
        if (this.mCanvasSaveCount > 0) {
            canvas.restoreToCount(this.mCanvasSaveCount);
        }
    }

    private void drawDragHandlerIfNeeded(Canvas canvas, View view, long j) {
        if (isDraggable()) {
            int iIndexOfChild = this.mGridView.indexOfChild(view) + this.mGridView.getFirstVisiblePosition();
            if (this.mGridView.getAdapter().isEnabled(iIndexOfChild)) {
                view.getHitRect(this.mTempRect);
                drawDragHandle(canvas, this.mTempRect, false, this.mDndController.canDrag(iIndexOfChild));
            }
        }
    }

    public void dispatchDraw(Canvas canvas) {
        if (!isDraggable() || this.mDragViewBitmap == null || this.mListItemSelectionAnimating) {
            return;
        }
        int i = this.mDndTouchX - this.mDndTouchOffsetX;
        int i2 = this.mDndTouchY - this.mDndTouchOffsetY;
        canvas.drawBitmap(this.mDragViewBitmap, this.mDragViewBitmapTranslateX + i, this.mDragViewBitmapTranslateY + i2, this.mDragViewBitmapPaint);
        this.mTempRect.left = i + this.mDragViewBitmapTranslateX;
        this.mTempRect.top = i2 + this.mDragViewBitmapTranslateY;
        this.mTempRect.bottom = this.mTempRect.top + this.mDragViewRect.height();
        this.mTempRect.right = this.mTempRect.left + this.mDragViewRect.width();
        drawDragHandle(canvas, this.mTempRect, true, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateDragViewBitmap() {
        if (this.mDragView != null) {
            this.mDragViewBitmap = SemAnimatorUtils.getBitmapDrawableFromView(this.mDragView).getBitmap();
        }
    }
}
