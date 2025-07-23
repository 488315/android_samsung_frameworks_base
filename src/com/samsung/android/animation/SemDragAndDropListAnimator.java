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
import com.samsung.android.animation.SemAbsDragAndDropAnimator;
import com.samsung.android.animation.SemDragAndDropAnimationCore;

@Deprecated
/* loaded from: classes6.dex */
public class SemDragAndDropListAnimator extends SemAbsDragAndDropAnimator {
    private static final String TAG = "SemDragAndDropListAnimator";
    private int mDragViewRoundCorner;
    private SemDragAndDropAnimationCore.ItemAnimationListener mItemAnimationListener;
    private AdapterView.OnItemLongClickListener mItemLongClickListener;
    private ListView mListView;
    SparseIntArray mNonMovableItems;
    private final int mScrollBarSize;

    public SemDragAndDropListAnimator(Context context, ListView listView) {
        super(context, listView);
        this.mNonMovableItems = new SparseIntArray();
        this.mScrollBarSize = 10;
        this.mDragViewRoundCorner = 0;
        this.mListView = listView;
        initListeners();
        this.mDndAnimationCore.setAnimationListener(this.mItemAnimationListener);
        this.mListView.setDndListAnimator(this);
        this.mListView.setOnItemLongClickListener(this.mItemLongClickListener);
    }

    private void initListeners() {
        this.mItemAnimationListener = new SemDragAndDropAnimationCore.ItemAnimationListener() { // from class: com.samsung.android.animation.SemDragAndDropListAnimator.1
            @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimationListener
            public void onItemAnimatorEnd() {
                if (SemDragAndDropListAnimator.this.mListItemSelectionAnimating) {
                    SemDragAndDropListAnimator.this.mListItemSelectionAnimating = false;
                    return;
                }
                if (SemDragAndDropListAnimator.this.mDropDonePending) {
                    SemDragAndDropListAnimator.this.mDropDonePending = false;
                    if (SemDragAndDropListAnimator.this.mDndController != null) {
                        Log.d(SemDragAndDropListAnimator.TAG, "initListeners : onItemAnimatorEnd : mDndController.dropDone #1 , mFirstDragPos = " + SemDragAndDropListAnimator.this.mFirstDragPos + ", mDragPos = " + SemDragAndDropListAnimator.this.mDragPos);
                        SemDragAndDropListAnimator.this.mDndController.dropDone(SemDragAndDropListAnimator.this.mFirstDragPos, SemDragAndDropListAnimator.this.mDragPos);
                        SemDragAndDropListAnimator semDragAndDropListAnimator = SemDragAndDropListAnimator.this;
                        semDragAndDropListAnimator.speakDragReleaseForAccessibility(semDragAndDropListAnimator.mDragPos);
                    }
                    SemDragAndDropListAnimator.this.mItemAnimator.removeAll();
                    SemDragAndDropListAnimator.this.resetDndPositionValues();
                    if (SemDragAndDropListAnimator.this.mDndListener != null) {
                        Log.d(SemDragAndDropListAnimator.TAG, "initListeners : onItemAnimatorEnd : dndListener.onDragAndDropEnd() #1");
                        SemDragAndDropListAnimator.this.mDndListener.onDragAndDropEnd();
                    }
                    SemDragAndDropListAnimator.this.mListView.setEnabled(true);
                }
            }
        };
        this.mItemLongClickListener = new AdapterView.OnItemLongClickListener() { // from class: com.samsung.android.animation.SemDragAndDropListAnimator.2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long j) {
                return SemDragAndDropListAnimator.this.initDragIfNecessary(i);
            }
        };
    }

    public void setDragAndDropController(SemAbsDragAndDropAnimator.DragAndDropController dragAndDropController) {
        if (dragAndDropController == null) {
            this.mDndController = null;
        } else if (this.mListView.getHeaderViewsCount() == 0 && this.mListView.getFooterViewsCount() == 0) {
            this.mDndController = dragAndDropController;
        } else {
            this.mDndController = new HeaderFooterDndController(dragAndDropController);
        }
    }

    private boolean checkStartDnd(int i, int i2, int i3) {
        if (this.mListView.semIsLongPressTriggeredByKey()) {
            Log.d(TAG, "checkStartDnd : LongPress is triggered by key, return false");
            return false;
        }
        if (!checkDndGrabHandle(i, i2, i3)) {
            return false;
        }
        Log.d(TAG, "checkStartDnd : canDrag #1 itemPosition = " + i3);
        boolean canDrag = this.mDndController.canDrag(i3);
        if (!canDrag) {
            speakNotDraggableForAccessibility(i3);
        }
        return canDrag;
    }

    private boolean checkDndGrabHandle(int i, int i2, int i3) {
        if (activatedByLongPress()) {
            return true;
        }
        Rect rect = new Rect();
        ListView listView = this.mListView;
        listView.getChildAt(i3 - listView.getFirstVisiblePosition()).getHitRect(rect);
        getDragGrabHandleHitRect(rect, this.mTempRect);
        return this.mTempRect.contains(i, i2);
    }

    public boolean startDrag() {
        if (this.mTempEvent == null) {
            return false;
        }
        return initDragIfNecessary(this.mListView.pointToPosition((int) this.mTempEvent.getX(), (int) this.mTempEvent.getY()));
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x000e, code lost:
    
        if (r0 != 3) goto L49;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onInterceptTouchEvent(android.view.MotionEvent r5) {
        /*
            r4 = this;
            int r0 = r5.getAction()
            r1 = 0
            r2 = 1
            if (r0 == 0) goto L39
            if (r0 == r2) goto L2b
            r3 = 2
            if (r0 == r3) goto L12
            r2 = 3
            if (r0 == r2) goto L2b
            goto Laa
        L12:
            boolean r5 = r4.isDraggable()
            if (r5 == 0) goto Laa
            int r5 = r4.mDndTouchMode
            if (r5 != r2) goto Laa
            android.widget.ListView r5 = r4.mListView
            int r5 = r5.getCount()
            if (r5 <= r2) goto Laa
            boolean r4 = r4.activatedByLongPress()
            if (r4 == 0) goto Laa
            return r2
        L2b:
            boolean r0 = r4.isDraggable()
            if (r0 == 0) goto Laa
            int r0 = r4.mDndTouchMode
            if (r0 == 0) goto Laa
            r4.onTouchUpCancel(r5)
            goto Laa
        L39:
            android.widget.ListView r0 = r4.mListView
            boolean r0 = r0.isEnabled()
            if (r0 != 0) goto L42
            return r1
        L42:
            android.view.MotionEvent r0 = r4.mTempEvent
            if (r0 == 0) goto L4b
            android.view.MotionEvent r0 = r4.mTempEvent
            r0.recycle()
        L4b:
            android.view.MotionEvent r0 = android.view.MotionEvent.obtain(r5)
            r4.mTempEvent = r0
            int r0 = r5.getPointerId(r1)
            r4.mActivePointerId = r0
            float r0 = r5.getX()
            int r0 = (int) r0
            r4.mDndTouchX = r0
            float r5 = r5.getY()
            int r5 = (int) r5
            r4.mDndTouchY = r5
            int r5 = r4.mDndTouchY
            r4.mFirstTouchY = r5
            boolean r5 = r4.isDraggable()
            if (r5 == 0) goto Laa
            android.widget.ListView r5 = r4.mListView
            int r5 = r5.getCount()
            if (r5 <= r2) goto Laa
            android.widget.ListView r5 = r4.mListView
            int r0 = r4.mDndTouchX
            int r3 = r4.mDndTouchY
            int r5 = r5.pointToPosition(r0, r3)
            r0 = -1
            if (r5 != r0) goto L85
            return r1
        L85:
            boolean r0 = r4.activatedByLongPress()
            if (r0 == 0) goto L8c
            return r1
        L8c:
            if (r5 < 0) goto La7
            android.widget.ListView r0 = r4.mListView
            int r0 = r0.getCount()
            if (r5 >= r0) goto La7
            int r0 = r4.mDndTouchX
            int r3 = r4.mDndTouchY
            boolean r0 = r4.checkStartDnd(r0, r3, r5)
            if (r0 == 0) goto La7
            boolean r4 = r4.initDrag(r5)
            if (r4 == 0) goto Laa
            return r2
        La7:
            r4.resetDndState()
        Laa:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.animation.SemDragAndDropListAnimator.onInterceptTouchEvent(android.view.MotionEvent):boolean");
    }

    public AdapterView.OnItemLongClickListener getDragAndDropOnItemLongClickListener() {
        return this.mItemLongClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean initDragIfNecessary(int i) {
        if (!isDraggable() || !activatedByLongPress() || this.mListView.getCount() <= 1) {
            return false;
        }
        if (i >= 0 && i < this.mListView.getCount() && checkStartDnd(this.mDndTouchX, this.mDndTouchY, i)) {
            return initDrag(i);
        }
        resetDndState();
        return false;
    }

    private boolean initDrag(int i) {
        int i2;
        ListView listView = this.mListView;
        this.mDragView = listView.getChildAt(i - listView.getFirstVisiblePosition());
        if (this.mDragView == null) {
            return false;
        }
        this.mListView.setEnableHoverDrawable(false);
        this.mDndTouchMode = 1;
        this.mFirstDragPos = i;
        this.mDragPos = this.mFirstDragPos;
        this.mDragView.getHitRect(this.mDragViewRect);
        speakDragStartForAccessibility(i);
        if (!this.mUserSetDragItemBitmap) {
            if (this.mDragViewBitmap != null) {
                this.mDragViewBitmap.recycle();
            }
            int semGetRoundedCorners = this.mDragView.semGetRoundedCorners();
            this.mDragViewRoundCorner = semGetRoundedCorners;
            if (semGetRoundedCorners != 0) {
                this.mDragView.semSetRoundedCorners(0);
                this.mDragView.invalidate();
            }
            TypedValue typedValue = new TypedValue();
            int round = Math.round(this.mListView.getContext().getResources().getDisplayMetrics().density);
            if (this.mDragView.getContext().getTheme().resolveAttribute(16843828, typedValue, true)) {
                i2 = typedValue.data;
            } else {
                this.mDragView.getContext().getTheme().resolveAttribute(R.attr.parentIsDeviceDefaultDark, typedValue, true);
                i2 = typedValue.data == 0 ? 29406 : 4100607;
            }
            this.mDragViewBitmap = SemAnimatorUtils.getBitmapDrawableFromView(this.mDragView).getBitmap();
            Canvas canvas = new Canvas(this.mDragViewBitmap);
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(i2);
            paint.setStrokeWidth(round);
            canvas.drawRect(new Rect(0, 0, this.mDragViewBitmap.getWidth() - 1, this.mDragViewBitmap.getHeight() - 1), paint);
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

    private void startSelectHighlightingAnimation(View view) {
        Rect rect = new Rect();
        view.getHitRect(rect);
        this.mListItemSelectionAnimating = true;
        this.mScaleUpAndDownAnimation = new SemDragAndDropAnimationCore.ItemSelectHighlightingAnimation(rect);
        this.mScaleUpAndDownAnimation.setStartAndDuration(0);
        this.mItemAnimator.putItemAnimation(this.mFirstDragPos, this.mScaleUpAndDownAnimation);
        this.mItemAnimator.start();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!isDraggable() || this.mDndTouchMode == 0) {
            return false;
        }
        int action = motionEvent.getAction();
        int i = action & 255;
        if (i != 1) {
            if (i == 2) {
                onTouchMove(motionEvent);
            } else if (i != 3) {
                if (i == 6) {
                    int i2 = (action & 65280) >> 8;
                    if (motionEvent.getPointerId(i2) == this.mActivePointerId) {
                        this.mActivePointerId = motionEvent.getPointerId(i2 == 0 ? 1 : 0);
                    }
                }
            }
            return true;
        }
        onTouchUpCancel(motionEvent);
        return true;
    }

    private void onTouchMove(MotionEvent motionEvent) {
        int findPointerIndex = motionEvent.findPointerIndex(this.mActivePointerId);
        if (findPointerIndex == -1) {
            this.mActivePointerId = motionEvent.getPointerId(0);
            findPointerIndex = 0;
        }
        this.mDndTouchX = (int) motionEvent.getX(findPointerIndex);
        this.mDndTouchY = (int) motionEvent.getY(findPointerIndex);
        if (this.mDndTouchY > (this.mListView.getBottom() - this.mListView.getPaddingBottom()) - this.mListView.getTop()) {
            this.mDndTouchY = (this.mListView.getBottom() - this.mListView.getPaddingBottom()) - this.mListView.getTop();
        } else if (this.mDndTouchY < this.mListView.getPaddingTop()) {
            this.mDndTouchY = this.mListView.getPaddingTop();
        }
        if (this.mScaleUpAndDownAnimation != null && !this.mScaleUpAndDownAnimation.isFinished() && Math.abs(this.mDndTouchY - this.mFirstTouchY) > 15.0f) {
            this.mListItemSelectionAnimating = false;
        }
        this.mDndTouchMode = 2;
        int paddingTop = this.mListView.getPaddingTop();
        View childAt = this.mListView.getChildAt(0);
        if (childAt != null) {
            paddingTop += childAt.getHeight() / 2;
        }
        int bottom = (this.mListView.getBottom() - this.mListView.getPaddingBottom()) - this.mListView.getTop();
        ListView listView = this.mListView;
        View childAt2 = listView.getChildAt(listView.getChildCount() - 1);
        if (childAt2 != null) {
            bottom -= childAt2.getHeight() / 2;
        }
        if (this.mDndTouchY > bottom || this.mDndTouchY < paddingTop) {
            if (this.mDndAutoScrollMode == 0) {
                this.mListView.postOnAnimationDelayed(this.mAutoScrollRunnable, 150L);
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
            this.mListView.removeCallbacks(this.mAutoScrollRunnable);
        }
        reorderIfNeeded();
    }

    @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator
    void reorderIfNeeded() {
        int i = this.mDragPos;
        int findDragItemPosition = findDragItemPosition((this.mDndTouchY - this.mDndTouchOffsetY) + (this.mDragViewRect.height() / 2));
        Log.d(TAG, "reorderIfNeeded : canDrop #1 mFirstDragPos = " + this.mFirstDragPos + ", dragPos = " + findDragItemPosition);
        if (findDragItemPosition != -1 && this.mDndController.canDrop(this.mFirstDragPos, findDragItemPosition)) {
            this.mDragPos = findDragItemPosition;
        }
        if (i != this.mDragPos && !this.mListItemSelectionAnimating) {
            recalculateOffset(i, this.mDragPos);
            this.mItemAnimator.start();
        }
        if (i == this.mDragPos && this.mDragViewBitmap == null) {
            return;
        }
        this.mListView.invalidate();
    }

    private void onTouchUpCancel(MotionEvent motionEvent) {
        int bottom;
        int i;
        this.mActivePointerId = -1;
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (this.mDndTouchMode == 1) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(TAG, "dndListener.onDragAndDropEnd() onTouchUpCancel DND_TOUCH_STATUS_START #2");
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
        View childAt = this.mListView.getChildAt(this.mFirstDragPos - firstVisiblePosition);
        View childAt2 = this.mListView.getChildAt(this.mDragPos - firstVisiblePosition);
        if (childAt == null || childAt2 == null) {
            int i2 = this.mDndTouchY - this.mDndTouchOffsetY;
            if (childAt2 != null) {
                bottom = childAt2.getTop();
            } else if (this.mDragPos < firstVisiblePosition) {
                i = -((i2 - this.mListView.getChildAt(0).getTop()) + this.mDragViewRect.height());
                Log.v(TAG, "dndListener.onTouchUp() dragView == null, distance=" + i);
                ValueAnimator ofInt = ValueAnimator.ofInt(0, i);
                ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemDragAndDropListAnimator.3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        SemDragAndDropListAnimator.this.mDragViewBitmapTranslateY = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        SemDragAndDropListAnimator.this.mListView.invalidate();
                    }
                });
                ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemDragAndDropListAnimator.4
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        if (SemDragAndDropListAnimator.this.mFirstDragPos != SemDragAndDropListAnimator.this.mDragPos) {
                            Log.d(SemDragAndDropListAnimator.TAG, "onTouchUpCancel : onAnimationEnd : mDndController.dropDone #2 , mFirstDragPos = " + SemDragAndDropListAnimator.this.mFirstDragPos + ", mDragPos = " + SemDragAndDropListAnimator.this.mDragPos);
                            SemDragAndDropListAnimator.this.mDndController.dropDone(SemDragAndDropListAnimator.this.mFirstDragPos, SemDragAndDropListAnimator.this.mDragPos);
                            SemDragAndDropListAnimator semDragAndDropListAnimator = SemDragAndDropListAnimator.this;
                            semDragAndDropListAnimator.speakDragReleaseForAccessibility(semDragAndDropListAnimator.mDragPos);
                        }
                        SemDragAndDropListAnimator.this.mItemAnimator.removeAll();
                        SemDragAndDropListAnimator.this.resetDndState();
                        if (SemDragAndDropListAnimator.this.mDndListener != null) {
                            Log.d(SemDragAndDropListAnimator.TAG, "dndListener.onDragAndDropEnd() from onAnimationEnd() #3");
                            SemDragAndDropListAnimator.this.mDndListener.onDragAndDropEnd();
                        }
                    }
                });
                ofInt.setDuration(210L);
                ofInt.setInterpolator(SINE_IN_OUT_70);
                ofInt.start();
            } else {
                ListView listView = this.mListView;
                bottom = listView.getChildAt(listView.getChildCount() - 1).getBottom();
            }
            i = bottom - i2;
            Log.v(TAG, "dndListener.onTouchUp() dragView == null, distance=" + i);
            ValueAnimator ofInt2 = ValueAnimator.ofInt(0, i);
            ofInt2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemDragAndDropListAnimator.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SemDragAndDropListAnimator.this.mDragViewBitmapTranslateY = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                    SemDragAndDropListAnimator.this.mListView.invalidate();
                }
            });
            ofInt2.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemDragAndDropListAnimator.4
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (SemDragAndDropListAnimator.this.mFirstDragPos != SemDragAndDropListAnimator.this.mDragPos) {
                        Log.d(SemDragAndDropListAnimator.TAG, "onTouchUpCancel : onAnimationEnd : mDndController.dropDone #2 , mFirstDragPos = " + SemDragAndDropListAnimator.this.mFirstDragPos + ", mDragPos = " + SemDragAndDropListAnimator.this.mDragPos);
                        SemDragAndDropListAnimator.this.mDndController.dropDone(SemDragAndDropListAnimator.this.mFirstDragPos, SemDragAndDropListAnimator.this.mDragPos);
                        SemDragAndDropListAnimator semDragAndDropListAnimator = SemDragAndDropListAnimator.this;
                        semDragAndDropListAnimator.speakDragReleaseForAccessibility(semDragAndDropListAnimator.mDragPos);
                    }
                    SemDragAndDropListAnimator.this.mItemAnimator.removeAll();
                    SemDragAndDropListAnimator.this.resetDndState();
                    if (SemDragAndDropListAnimator.this.mDndListener != null) {
                        Log.d(SemDragAndDropListAnimator.TAG, "dndListener.onDragAndDropEnd() from onAnimationEnd() #3");
                        SemDragAndDropListAnimator.this.mDndListener.onDragAndDropEnd();
                    }
                }
            });
            ofInt2.setDuration(210L);
            ofInt2.setInterpolator(SINE_IN_OUT_70);
            ofInt2.start();
        } else if (this.mListItemSelectionAnimating) {
            resetDndState();
            if (this.mDndListener != null) {
                Log.d(TAG, "dndListener.onDragAndDropEnd() mListItemSelectionAnimating is true #4");
                this.mDndListener.onDragAndDropEnd();
            }
        } else {
            int top = childAt2.getTop() - childAt.getTop();
            int top2 = childAt2.getTop() - (this.mDndTouchY - this.mDndTouchOffsetY);
            SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation = new SemDragAndDropAnimationCore.TranslateItemAnimation();
            translateItemAnimation.translate(0, 0, top, top2);
            translateItemAnimation.setStartAndDuration(0.7f);
            this.mItemAnimator.putItemAnimation(this.mFirstDragPos, translateItemAnimation);
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

    private int findDragItemPosition(int i) {
        int childCount = this.mListView.getChildCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (childCount <= 0) {
            return -1;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            this.mListView.getChildAt(i2).getHitRect(this.mTempRect);
            if (this.mTempRect.contains(this.mTempRect.centerX(), i)) {
                return i2 + firstVisiblePosition;
            }
        }
        return -1;
    }

    private int findMovedItemPosition(int i) {
        int childCount = this.mListView.getChildCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        if (childCount <= 0) {
            return -1;
        }
        for (int i2 = 0; i2 < childCount; i2++) {
            if (i2 != this.mFirstDragPos - firstVisiblePosition) {
                this.mListView.getChildAt(i2).getHitRect(this.mTempRect);
                int i3 = i2 + firstVisiblePosition;
                SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i3);
                if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
                    if (this.mTempRect.contains(this.mTempRect.centerX(), i - ((SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation).getDestOffsetY())) {
                        return i3;
                    }
                } else {
                    continue;
                }
            }
        }
        return -1;
    }

    private void addReturningTranslation(int i) {
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i);
        if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation = (SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation;
            translateItemAnimation.translate(0, 0, 0, -((int) translateItemAnimation.getCurrentTranslateY()));
            translateItemAnimation.setStartAndDuration(translateItemAnimation.getProgress());
        }
    }

    private void addNewTranslation(int i, int i2) {
        SemDragAndDropAnimationCore.TranslateItemAnimation translateItemAnimation;
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(i);
        if (itemAnimation instanceof SemDragAndDropAnimationCore.TranslateItemAnimation) {
            translateItemAnimation = (SemDragAndDropAnimationCore.TranslateItemAnimation) itemAnimation;
        } else {
            translateItemAnimation = new SemDragAndDropAnimationCore.TranslateItemAnimation();
        }
        translateItemAnimation.translate(0, 0, i2, i2 - (!translateItemAnimation.isFinished() ? (int) translateItemAnimation.getCurrentTranslateY() : 0));
        if (!translateItemAnimation.isFinished()) {
            translateItemAnimation.setStartAndDuration(translateItemAnimation.getProgress());
        } else {
            translateItemAnimation.setStartAndDuration(0);
        }
        this.mItemAnimator.putItemAnimation(i, translateItemAnimation);
    }

    private void updateRoundCorner(int i) {
        int semGetRoundedCorners;
        int i2;
        View childAt = this.mListView.getChildAt(i - this.mListView.getFirstVisiblePosition());
        if (childAt == null || (i2 = this.mDragViewRoundCorner) == (semGetRoundedCorners = childAt.semGetRoundedCorners())) {
            return;
        }
        childAt.semSetRoundedCorners(i2);
        this.mDragViewRoundCorner = semGetRoundedCorners;
        childAt.invalidate();
    }

    private void recalculateOffset(int i, int i2) {
        View childAt;
        int dividerHeight = this.mListView.getDividerHeight();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        int height = this.mDragViewRect.height() + dividerHeight;
        if (i2 <= i) {
            for (int i3 = i - 1; i3 >= i2; i3--) {
                if (i3 < this.mFirstDragPos) {
                    Log.d(TAG, "recalculateOffset : canDrop #3 mFirstDragPos = " + this.mFirstDragPos + ", i = " + i3);
                    if (this.mDndController.canDrop(this.mFirstDragPos, i3)) {
                        int i4 = i3;
                        int i5 = height;
                        while (true) {
                            i4++;
                            if (this.mNonMovableItems.indexOfKey(i4) < 0) {
                                break;
                            } else {
                                i5 += this.mNonMovableItems.get(i4);
                            }
                        }
                        updateRoundCorner(i3);
                        addNewTranslation(i3, i5);
                    } else if (this.mNonMovableItems.get(i3, -1) == -1 && (childAt = this.mListView.getChildAt(i3 - firstVisiblePosition)) != null) {
                        this.mNonMovableItems.put(i3, childAt.getHeight() + dividerHeight);
                    }
                } else {
                    View childAt2 = this.mListView.getChildAt(i3 - firstVisiblePosition);
                    if (childAt2 == null) {
                        Log.e(TAG, "recalculateOffset('dragging up') no such item, i=" + i3);
                    } else {
                        int findMovedItemPosition = findMovedItemPosition(SemAnimatorUtils.getViewCenterY(childAt2));
                        updateRoundCorner(findMovedItemPosition);
                        addReturningTranslation(findMovedItemPosition);
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
                Log.d(TAG, "recalculateOffset : canDrop #2 mFirstDragPos = " + this.mFirstDragPos + ", i = " + i);
                if (this.mDndController.canDrop(this.mFirstDragPos, i)) {
                    int i6 = i;
                    int i7 = height;
                    while (true) {
                        i6--;
                        if (this.mNonMovableItems.indexOfKey(i6) < 0) {
                            break;
                        } else {
                            i7 += this.mNonMovableItems.get(i6);
                        }
                    }
                    updateRoundCorner(i);
                    addNewTranslation(i, -i7);
                } else {
                    View childAt3 = this.mListView.getChildAt(i - firstVisiblePosition);
                    if (childAt3 != null) {
                        this.mNonMovableItems.put(i, childAt3.getHeight() + dividerHeight);
                    }
                }
            } else {
                View childAt4 = this.mListView.getChildAt(i - firstVisiblePosition);
                if (childAt4 == null) {
                    Log.e(TAG, "recalculateOffset('dragging down') no such item, i=" + i);
                } else {
                    int findMovedItemPosition2 = findMovedItemPosition(SemAnimatorUtils.getViewCenterY(childAt4));
                    updateRoundCorner(findMovedItemPosition2);
                    addReturningTranslation(findMovedItemPosition2);
                }
            }
        }
    }

    private void getDragGrabHandleHitRect(Rect rect, Rect rect2) {
        if (this.mDragGrabHandleDrawable != null) {
            int intrinsicWidth = this.mDragGrabHandleDrawable.getIntrinsicWidth();
            int intrinsicHeight = this.mDragGrabHandleDrawable.getIntrinsicHeight();
            if (this.mListView.isLayoutRtl()) {
                rect.left += this.mDragGrabHandlePadding.right;
                rect.top += this.mDragGrabHandlePadding.top;
                rect.right -= this.mDragGrabHandlePadding.left;
                rect.bottom += this.mDragGrabHandlePadding.bottom;
                rect.left += 10;
                rect.right += 10;
                Gravity.apply(this.mDragGrabHandlePosGravity, intrinsicWidth, intrinsicHeight, rect, rect2, 1);
                return;
            }
            rect.left += this.mDragGrabHandlePadding.left;
            rect.top += this.mDragGrabHandlePadding.top;
            rect.right += this.mDragGrabHandlePadding.right;
            rect.bottom += this.mDragGrabHandlePadding.bottom;
            rect.left -= 10;
            rect.right -= 10;
            Gravity.apply(this.mDragGrabHandlePosGravity, intrinsicWidth, intrinsicHeight, rect, rect2, 0);
        }
    }

    private void drawDragHandle(Canvas canvas, Rect rect, boolean z, boolean z2) {
        Log.d(TAG, "drawDragHandle : isAllowDragItem = " + z2);
        if (this.mDragGrabHandleDrawable != null && z2) {
            getDragGrabHandleHitRect(rect, this.mTempRect);
            this.mDragGrabHandleDrawable.setBounds(this.mTempRect);
            this.mDragGrabHandleDrawable.setState(z ? PRESSED_STATE_SET : EMPTY_STATE_SET);
            Log.d(TAG, "drawDragHandle : call mDragGrabHandleDrawable.draw.. ");
            this.mDragGrabHandleDrawable.draw(canvas);
            return;
        }
        Log.d(TAG, "drawDragHandle : not draw drageGrabHandle~~!! ");
    }

    public boolean preDrawChild(Canvas canvas, View view, long j) {
        int indexOfChild = this.mListView.indexOfChild(view) + this.mListView.getFirstVisiblePosition();
        if (isDraggable() && indexOfChild == this.mFirstDragPos && !this.mDropDonePending && !this.mListItemSelectionAnimating) {
            return false;
        }
        SemDragAndDropAnimationCore.ItemAnimation itemAnimation = this.mItemAnimator.getItemAnimation(indexOfChild);
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
        Log.d(TAG, "postDrawChild : call drawDragHandlerIfNeeded");
        drawDragHandlerIfNeeded(canvas, view, j);
        if (this.mCanvasSaveCount > 0) {
            canvas.restoreToCount(this.mCanvasSaveCount);
        }
    }

    private void drawDragHandlerIfNeeded(Canvas canvas, View view, long j) {
        if (isDraggable()) {
            int indexOfChild = this.mListView.indexOfChild(view) + this.mListView.getFirstVisiblePosition();
            if (!this.mListView.getAdapter().isEnabled(indexOfChild) || isHeaderOrFooterViewPos(indexOfChild)) {
                return;
            }
            view.getHitRect(this.mTempRect);
            Log.d(TAG, "drawDragHandlerIfNeeded : canDrag #2 pos = " + indexOfChild);
            drawDragHandle(canvas, this.mTempRect, false, this.mDndController.canDrag(indexOfChild));
        }
    }

    private boolean isHeaderOrFooterViewPos(int i) {
        return i < this.mListView.getHeaderViewsCount() || i >= this.mListView.getCount() - this.mListView.getFooterViewsCount();
    }

    public void dispatchDraw(Canvas canvas) {
        if (!isDraggable() || this.mDragViewBitmap == null) {
            return;
        }
        int paddingLeft = this.mListView.getPaddingLeft();
        int i = this.mDndTouchY - this.mDndTouchOffsetY;
        if (this.mListItemSelectionAnimating || this.mDragViewBitmap.isRecycled()) {
            return;
        }
        canvas.drawBitmap(this.mDragViewBitmap, paddingLeft, this.mDragViewBitmapTranslateY + i, this.mDragViewBitmapPaint);
        if ((this.mDragGrabHandlePosGravity & 5) == 5) {
            this.mTempRect.left = -this.mListView.getPaddingRight();
        } else {
            this.mTempRect.left = this.mListView.getPaddingLeft();
        }
        this.mTempRect.top = i + this.mDragViewBitmapTranslateY;
        this.mTempRect.bottom = this.mTempRect.top + this.mDragViewRect.height();
        this.mTempRect.right = this.mTempRect.left + this.mListView.getWidth();
        drawDragHandle(canvas, this.mTempRect, true, true);
    }

    private class HeaderFooterDndController implements SemAbsDragAndDropAnimator.DragAndDropController {
        private final SemAbsDragAndDropAnimator.DragAndDropController mWrappedController;

        HeaderFooterDndController(SemAbsDragAndDropAnimator.DragAndDropController dragAndDropController) {
            this.mWrappedController = dragAndDropController;
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public boolean canDrag(int i) {
            if (this.mWrappedController == null || i < SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount() || i >= SemDragAndDropListAnimator.this.mListView.getCount() - SemDragAndDropListAnimator.this.mListView.getFooterViewsCount()) {
                return false;
            }
            Log.d(SemDragAndDropListAnimator.TAG, "HeaderFooterDndController : canDrag #3 mListView.getHeaderViewsCount() = " + SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount());
            return this.mWrappedController.canDrag(i - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount());
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public boolean canDrop(int i, int i2) {
            if (this.mWrappedController == null || i2 < SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount() || i2 >= SemDragAndDropListAnimator.this.mListView.getCount() - SemDragAndDropListAnimator.this.mListView.getFooterViewsCount()) {
                return false;
            }
            Log.d(SemDragAndDropListAnimator.TAG, "HeaderFooterDndController : canDrop #4 startPos - mListView.getHeaderViewsCount() = " + (i - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()) + ", destPos = " + i2);
            return this.mWrappedController.canDrop(i - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount(), i2 - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount());
        }

        @Override // com.samsung.android.animation.SemAbsDragAndDropAnimator.DragAndDropController
        public void dropDone(int i, int i2) {
            if (this.mWrappedController != null) {
                if (i < SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()) {
                    i = SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount();
                } else if (i > SemDragAndDropListAnimator.this.mListView.getCount() - SemDragAndDropListAnimator.this.mListView.getFooterViewsCount()) {
                    i = (SemDragAndDropListAnimator.this.mListView.getCount() - SemDragAndDropListAnimator.this.mListView.getFooterViewsCount()) - 1;
                }
                if (i2 < SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()) {
                    i2 = SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount();
                } else if (i2 >= SemDragAndDropListAnimator.this.mListView.getCount() - SemDragAndDropListAnimator.this.mListView.getFooterViewsCount()) {
                    i2 = (SemDragAndDropListAnimator.this.mListView.getCount() - SemDragAndDropListAnimator.this.mListView.getFooterViewsCount()) - 1;
                }
                Log.d(SemDragAndDropListAnimator.TAG, "HeaderFooterDndController : dropDone : mWrappedController.dropDone #3");
                Log.d(SemDragAndDropListAnimator.TAG, "HeaderFooterDndController : dropDone : startPos - mListView.getHeaderViewsCount() = " + (i - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()));
                Log.d(SemDragAndDropListAnimator.TAG, "HeaderFooterDndController : dropDone : destPos - mListView.getHeaderViewsCount() = " + (i2 - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount()));
                this.mWrappedController.dropDone(i - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount(), i2 - SemDragAndDropListAnimator.this.mListView.getHeaderViewsCount());
            }
        }
    }
}
