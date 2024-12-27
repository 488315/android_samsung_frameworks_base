package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Deprecated
public class SemAddDeleteListAnimator extends SemAbsAddDeleteAnimator {
    private static String TAG = "SemAddDeleteListAnimator";
    private ListView mListView;
    private OnAddDeleteListener mOnAddDeleteListener;
    LinkedHashMap<Long, SemAbsAddDeleteAnimator.ViewInfo> mOldViewCache = new LinkedHashMap<>();
    LinkedHashMap<Long, SemAbsAddDeleteAnimator.ViewInfo> mOldHeaderFooterViewCache =
            new LinkedHashMap<>();
    private boolean mInsertPending = false;
    private boolean mDeletePending = false;
    private boolean mInsertDeletePending = false;
    private boolean mIsInsertDelete = false;
    private final int EXTRA_ANIM_TIMEOUT_DUTAION = 100;
    private final Handler mHandler = new Handler();
    private final Runnable mAniTimeoutRunnable =
            new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.1
                @Override // java.lang.Runnable
                public void run() {
                    Log.i(SemAddDeleteListAnimator.TAG, "mAniTimeoutRunnable.run");
                    if (SemAddDeleteListAnimator.this.mGhostViewSnapshots.size() > 0) {
                        Iterator<SemAbsAddDeleteAnimator.ViewInfo> it =
                                SemAddDeleteListAnimator.this.mGhostViewSnapshots.iterator();
                        while (it.hasNext()) {
                            SemAbsAddDeleteAnimator.ViewInfo vInfo = it.next();
                            vInfo.recycleBitmap();
                        }
                    }
                    SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                    SemAddDeleteListAnimator.this.mListView.invalidate();
                    SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                }
            };

    public interface OnAddDeleteListener {
        void onAdd();

        void onAnimationEnd(boolean z);

        void onAnimationStart(boolean z);

        void onDelete();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public /* bridge */ /* synthetic */ void draw(Canvas canvas) {
        super.draw(canvas);
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public /* bridge */ /* synthetic */ void setTransitionDuration(int i) {
        super.setTransitionDuration(i);
    }

    public SemAddDeleteListAnimator(Context context, ListView listview) {
        this.mListView = listview;
        this.mListView.setAddDeleteListAnimator(this);
        this.mHostView = listview;
    }

    public void setOnAddDeleteListener(OnAddDeleteListener onAddDeleteListener) {
        this.mOnAddDeleteListener = onAddDeleteListener;
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setDelete(ArrayList<Integer> deletingItemPositions) {
        if (deletingItemPositions.size() == 0) {
            return;
        }
        prepareDelete(deletingItemPositions);
        if (this.mOnAddDeleteListener != null) {
            this.mOnAddDeleteListener.onDelete();
        }
        deleteFromAdapterCompleted();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setDeletePending(ArrayList<Integer> deletingItemPositions) {
        if (deletingItemPositions.size() == 0) {
            return;
        }
        prepareDelete(deletingItemPositions);
        if (this.mOnAddDeleteListener != null) {
            this.mOnAddDeleteListener.onDelete();
        }
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void deleteFromAdapterCompleted() {
        if (!this.mDeletePending) {
            throw new SemAbsAddDeleteAnimator.SetDeletePendingIsNotCalledBefore();
        }
        this.mDeletePending = false;
        this.mListView
                .getViewTreeObserver()
                .addOnPreDrawListener(
                        new ViewTreeObserver
                                .OnPreDrawListener() { // from class:
                                                       // com.samsung.android.animation.SemAddDeleteListAnimator.2
                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public boolean onPreDraw() {
                                SemAddDeleteListAnimator.this
                                        .mListView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                if (SemAddDeleteListAnimator.this.mDeleteRunnable != null) {
                                    SemAddDeleteListAnimator.this.mDeleteRunnable.run();
                                    SemAddDeleteListAnimator.this.mDeleteRunnable = null;
                                    return true;
                                }
                                return true;
                            }
                        });
    }

    private void prepareDelete(ArrayList<Integer> deletingItemPositions) {
        this.mDeletePending = true;
        final ArrayList<Integer> deletedItems = new ArrayList<>(deletingItemPositions);
        ensureAdapterAndListener();
        Collections.sort(deletedItems);
        final HashSet<Integer> deletedItemPosHash = new HashSet<>(deletedItems);
        final int childCountBefore = this.mListView.getChildCount();
        final int firstVisiblePosBefore = this.mListView.getFirstVisiblePosition();
        final ListAdapter adapter = this.mListView.getAdapter();
        capturePreAnimationViewCoordinates();
        this.mDeleteRunnable =
                new Runnable() { // from class:
                    // com.samsung.android.animation.SemAddDeleteListAnimator.3
                    /* JADX WARN: Removed duplicated region for block: B:44:0x01c8  */
                    /* JADX WARN: Removed duplicated region for block: B:47:0x0235  */
                    /* JADX WARN: Removed duplicated region for block: B:50:0x023d A[SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:51:0x0205  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void run() {
                        /*
                            Method dump skipped, instructions count: 702
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.animation.SemAddDeleteListAnimator.AnonymousClass3.run():void");
                    }
                };
    }

    private void capturePreAnimationViewCoordinates() {
        ListView listview;
        long itemId;
        ListView listview2 = this.mListView;
        ListAdapter adapter = listview2.getAdapter();
        int childCountBefore = listview2.getChildCount();
        int firstVisiblePosBefore = listview2.getFirstVisiblePosition();
        int adapterCount = adapter.getCount();
        int headerViewsCount = listview2.getHeaderViewsCount();
        int footerViewsCount = listview2.getFooterViewsCount();
        int i = 0;
        while (i < childCountBefore) {
            View child = listview2.getChildAt(i);
            int position = i + firstVisiblePosBefore;
            long itemId2 = adapter.getItemId(position);
            if (child.getHeight() == 0) {
                listview = listview2;
            } else if (child.getWidth() == 0) {
                listview = listview2;
            } else {
                BitmapDrawable snapshot = SemAnimatorUtils.getBitmapDrawableFromView(child);
                if (itemId2 == -1) {
                    if (position < headerViewsCount) {
                        itemId = position + 1;
                    } else if (position < adapterCount - footerViewsCount) {
                        itemId = itemId2;
                    } else {
                        int footerId = ((position + footerViewsCount) - adapterCount) + 1;
                        itemId = -footerId;
                    }
                    listview = listview2;
                    this.mOldHeaderFooterViewCache.put(
                            Long.valueOf(itemId),
                            new SemAbsAddDeleteAnimator.ViewInfo(
                                    snapshot, position, 0, child.getTop(), 0, child.getBottom()));
                } else {
                    listview = listview2;
                    this.mOldViewCache.put(
                            Long.valueOf(itemId2),
                            new SemAbsAddDeleteAnimator.ViewInfo(
                                    snapshot,
                                    i + firstVisiblePosBefore,
                                    0,
                                    child.getTop(),
                                    0,
                                    child.getBottom()));
                }
                i++;
                listview2 = listview;
            }
            Log.e(TAG, "setDelete() child's one of dimensions is 0, i=" + i);
            i++;
            listview2 = listview;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getChildMaxHeight() {
        int height;
        int childCount = this.mListView.getChildCount();
        int adapterCount = this.mListView.getAdapter().getCount();
        int firstVisiblePos = this.mListView.getFirstVisiblePosition();
        int childHeight = 0;
        for (int i = 0; i < childCount; i++) {
            int pos = i + firstVisiblePos;
            if (pos >= this.mListView.getHeaderViewsCount()
                    && pos < adapterCount - this.mListView.getFooterViewsCount()
                    && (height = this.mListView.getChildAt(i).getHeight()) > childHeight) {
                childHeight = height;
            }
        }
        return childHeight;
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setInsert(ArrayList<Integer> insertedItemPositions) {
        if (insertedItemPositions.size() == 0) {
            return;
        }
        prepareInsert(insertedItemPositions);
        if (this.mOnAddDeleteListener != null) {
            this.mOnAddDeleteListener.onAdd();
        }
        insertIntoAdapterCompleted();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setInsertPending(ArrayList<Integer> insertedItemPositions) {
        if (insertedItemPositions.size() == 0) {
            return;
        }
        prepareInsert(insertedItemPositions);
        if (this.mOnAddDeleteListener != null) {
            this.mOnAddDeleteListener.onAdd();
        }
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void insertIntoAdapterCompleted() {
        if (!this.mInsertPending) {
            throw new SemAbsAddDeleteAnimator.SetInsertPendingIsNotCalledBefore();
        }
        this.mInsertPending = false;
        this.mListView
                .getViewTreeObserver()
                .addOnPreDrawListener(
                        new ViewTreeObserver
                                .OnPreDrawListener() { // from class:
                                                       // com.samsung.android.animation.SemAddDeleteListAnimator.4
                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public boolean onPreDraw() {
                                SemAddDeleteListAnimator.this
                                        .mListView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                if (SemAddDeleteListAnimator.this.mInsertRunnable != null) {
                                    SemAddDeleteListAnimator.this.mInsertRunnable.run();
                                    SemAddDeleteListAnimator.this.mInsertRunnable = null;
                                    return true;
                                }
                                return true;
                            }
                        });
    }

    private void prepareInsert(ArrayList<Integer> insertedItemPositions) {
        int childCount;
        int adapterCount;
        int i = 1;
        this.mInsertPending = true;
        ensureAdapterAndListener();
        final ArrayList<Integer> insertedItems = new ArrayList<>(insertedItemPositions);
        Collections.sort(insertedItems);
        final HashSet<Integer> insertedItemPosHash = new HashSet<>(insertedItems);
        ListView listview = this.mListView;
        final ListAdapter adapter = listview.getAdapter();
        int childCount2 = listview.getChildCount();
        int adapterCount2 = adapter.getCount();
        int firstVisiblePos = listview.getFirstVisiblePosition();
        int footerViewsCount = listview.getFooterViewsCount();
        int i2 = 0;
        while (i2 < childCount2) {
            int position = i2 + firstVisiblePos;
            View child = listview.getChildAt(i2);
            long itemId = adapter.getItemId(position);
            if (child.getHeight() == 0) {
                childCount = childCount2;
                adapterCount = adapterCount2;
            } else if (child.getWidth() == 0) {
                childCount = childCount2;
                adapterCount = adapterCount2;
            } else {
                BitmapDrawable snapshot = SemAnimatorUtils.getBitmapDrawableFromView(child);
                if (itemId == -1) {
                    if (position < adapterCount2 - footerViewsCount) {
                        childCount = childCount2;
                        adapterCount = adapterCount2;
                    } else {
                        int footerId = ((position + footerViewsCount) - adapterCount2) + i;
                        childCount = childCount2;
                        adapterCount = adapterCount2;
                        this.mOldHeaderFooterViewCache.put(
                                Long.valueOf(-footerId),
                                new SemAbsAddDeleteAnimator.ViewInfo(
                                        snapshot,
                                        position,
                                        0,
                                        child.getTop(),
                                        0,
                                        child.getBottom()));
                    }
                } else {
                    childCount = childCount2;
                    adapterCount = adapterCount2;
                    this.mOldViewCache.put(
                            Long.valueOf(itemId),
                            new SemAbsAddDeleteAnimator.ViewInfo(
                                    snapshot, position, 0, child.getTop(), 0, child.getBottom()));
                }
                i2++;
                childCount2 = childCount;
                adapterCount2 = adapterCount;
                i = 1;
            }
            Log.e(TAG, "setInsert() child's one of dimensions is 0, i=" + i2);
            i2++;
            childCount2 = childCount;
            adapterCount2 = adapterCount;
            i = 1;
        }
        final HashMap<Integer, Integer> upcomingViewsStartCoords = new HashMap<>();
        for (int j = 0; j < insertedItems.size(); j++) {
            int insertedItemPos = insertedItems.get(j).intValue();
            int itemAtStartPos = insertedItemPos - j;
            View refView = listview.getChildAt(itemAtStartPos - firstVisiblePos);
            if (refView != null) {
                upcomingViewsStartCoords.put(
                        Integer.valueOf(insertedItemPos), Integer.valueOf(refView.getTop()));
            }
        }
        this.mInsertRunnable =
                new Runnable() { // from class:
                    // com.samsung.android.animation.SemAddDeleteListAnimator.5
                    @Override // java.lang.Runnable
                    public void run() {
                        int width;
                        int newY;
                        int footerViewsCount2;
                        int adapterCount3;
                        int left;
                        int width2;
                        int firstVisiblePos2;
                        ObjectAnimator anim;
                        ListView listview2 = SemAddDeleteListAnimator.this.mListView;
                        int firstVisiblePos3 = listview2.getFirstVisiblePosition();
                        int headerViewsCount = listview2.getHeaderViewsCount();
                        int footerViewsCount3 = listview2.getFooterViewsCount();
                        int childCount3 = listview2.getChildCount();
                        int adapterCount4 = adapter.getCount();
                        ArrayList<Animator> animations = new ArrayList<>();
                        int singleItemHeight = 0;
                        int left2 = 0;
                        if (childCount3 > headerViewsCount) {
                            singleItemHeight =
                                    SemAddDeleteListAnimator.this.getChildMaxHeight()
                                            + listview2.getDividerHeight();
                            left2 = listview2.getChildAt(headerViewsCount).getLeft();
                            width = listview2.getChildAt(0).getWidth();
                        } else {
                            width = listview2.getWidth();
                        }
                        int i3 = 0;
                        while (i3 < childCount3) {
                            int position2 = i3 + firstVisiblePos3;
                            long itemId2 = adapter.getItemId(position2);
                            View child2 = listview2.getChildAt(i3);
                            int headerViewsCount2 = headerViewsCount;
                            float newY2 = child2.getTop();
                            int childCount4 = childCount3;
                            if (itemId2 == -1) {
                                footerViewsCount2 = footerViewsCount3;
                                long footerId2 =
                                        ((position2 + footerViewsCount3) - adapterCount4) + 1;
                                adapterCount3 = adapterCount4;
                                left = left2;
                                width2 = width;
                                SemAbsAddDeleteAnimator.ViewInfo viewInfo =
                                        SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache
                                                .remove(Long.valueOf(-footerId2));
                                if (viewInfo == null) {
                                    Log.e(
                                            SemAddDeleteListAnimator.TAG,
                                            "AFTER header/footer SOMETHING WENT WRONG, in the new"
                                                + " layout, header/footer is appearing that was not"
                                                + " present before!");
                                } else {
                                    viewInfo.recycleBitmap();
                                    if (viewInfo.top == newY2) {
                                        Log.e(
                                                SemAddDeleteListAnimator.TAG,
                                                "AFTER header/footer something strange is"
                                                    + " happening, the coordinates are same after"
                                                    + " layout, viewInfo.top="
                                                        + viewInfo.top
                                                        + ", newY="
                                                        + newY2);
                                    } else {
                                        ObjectAnimator anim2 =
                                                SemAddDeleteListAnimator.this.getTranslateAnim(
                                                        child2, 0.0f, viewInfo.top - newY2);
                                        animations.add(anim2);
                                        firstVisiblePos2 = firstVisiblePos3;
                                        i3++;
                                        headerViewsCount = headerViewsCount2;
                                        footerViewsCount3 = footerViewsCount2;
                                        childCount3 = childCount4;
                                        adapterCount4 = adapterCount3;
                                        left2 = left;
                                        width = width2;
                                        firstVisiblePos3 = firstVisiblePos2;
                                    }
                                }
                                firstVisiblePos2 = firstVisiblePos3;
                                i3++;
                                headerViewsCount = headerViewsCount2;
                                footerViewsCount3 = footerViewsCount2;
                                childCount3 = childCount4;
                                adapterCount4 = adapterCount3;
                                left2 = left;
                                width = width2;
                                firstVisiblePos3 = firstVisiblePos2;
                            } else {
                                footerViewsCount2 = footerViewsCount3;
                                adapterCount3 = adapterCount4;
                                left = left2;
                                width2 = width;
                                Integer startPos =
                                        (Integer)
                                                upcomingViewsStartCoords.remove(
                                                        Integer.valueOf(position2));
                                SemAbsAddDeleteAnimator.ViewInfo viewInfo2 =
                                        SemAddDeleteListAnimator.this.mOldViewCache.remove(
                                                Long.valueOf(itemId2));
                                if (viewInfo2 != null) {
                                    viewInfo2.recycleBitmap();
                                    if (viewInfo2.top != newY2) {
                                        ObjectAnimator anim3 =
                                                SemAddDeleteListAnimator.this.getTranslateAnim(
                                                        child2, 0.0f, viewInfo2.top - newY2);
                                        animations.add(anim3);
                                        firstVisiblePos2 = firstVisiblePos3;
                                    }
                                    firstVisiblePos2 = firstVisiblePos3;
                                } else if (startPos != null) {
                                    ObjectAnimator anim4 =
                                            SemAddDeleteListAnimator.this
                                                    .getInsertTranslateAlphaScaleAnim(
                                                            child2,
                                                            0.0f,
                                                            startPos.intValue() - newY2);
                                    animations.add(anim4);
                                    firstVisiblePos2 = firstVisiblePos3;
                                } else {
                                    int currentPos = i3 + firstVisiblePos3;
                                    int shiftCount =
                                            SemAddDeleteListAnimator.this.getShiftCount(
                                                    currentPos, insertedItems);
                                    int oldPos = currentPos - shiftCount;
                                    int rowShift = currentPos - oldPos;
                                    firstVisiblePos2 = firstVisiblePos3;
                                    int firstVisiblePos4 =
                                            child2.getTop() - (rowShift * singleItemHeight);
                                    float translationY = firstVisiblePos4 - newY2;
                                    if (insertedItemPosHash.contains(Integer.valueOf(currentPos))) {
                                        anim =
                                                SemAddDeleteListAnimator.this
                                                        .getInsertTranslateAlphaScaleAnim(
                                                                child2, 0.0f, translationY);
                                    } else {
                                        anim =
                                                SemAddDeleteListAnimator.this.getTranslateAnim(
                                                        child2, 0.0f, translationY);
                                    }
                                    animations.add(anim);
                                }
                                i3++;
                                headerViewsCount = headerViewsCount2;
                                footerViewsCount3 = footerViewsCount2;
                                childCount3 = childCount4;
                                adapterCount4 = adapterCount3;
                                left2 = left;
                                width = width2;
                                firstVisiblePos3 = firstVisiblePos2;
                            }
                        }
                        int left3 = left2;
                        int width3 = width;
                        upcomingViewsStartCoords.clear();
                        Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>>
                                entrySetIterator =
                                        SemAddDeleteListAnimator.this
                                                .mOldViewCache
                                                .entrySet()
                                                .iterator();
                        int lastVisiblePosition = listview2.getLastVisiblePosition();
                        boolean updateListenerAdded = false;
                        int currentPos2 = lastVisiblePosition;
                        while (entrySetIterator.hasNext()) {
                            currentPos2++;
                            if (!insertedItems.contains(Integer.valueOf(currentPos2))) {
                                Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> viewEntry =
                                        entrySetIterator.next();
                                SemAbsAddDeleteAnimator.ViewInfo viewinfo = viewEntry.getValue();
                                int newPosition =
                                        SemAddDeleteListAnimator.this.getNewPositionForInsert(
                                                viewinfo.oldPosition, insertedItems);
                                if (newPosition < listview2.getFirstVisiblePosition()) {
                                    currentPos2--;
                                    int rowShift2 =
                                            listview2.getFirstVisiblePosition() - newPosition;
                                    newY =
                                            listview2.getChildAt(0).getTop()
                                                    - (rowShift2 * singleItemHeight);
                                } else {
                                    int rowShift3 = currentPos2 - viewinfo.oldPosition;
                                    newY = viewinfo.top + (rowShift3 * singleItemHeight);
                                }
                                ListView listview3 = listview2;
                                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>>
                                        entrySetIterator2 = entrySetIterator;
                                int lastVisiblePosition2 = lastVisiblePosition;
                                int currentPos3 = currentPos2;
                                int currentPos4 = left3;
                                Rect oldViewBounds =
                                        new Rect(
                                                currentPos4,
                                                viewinfo.top,
                                                left3 + width3,
                                                viewinfo.bottom);
                                Rect newViewBounds =
                                        new Rect(
                                                currentPos4,
                                                newY,
                                                oldViewBounds.width() + currentPos4,
                                                oldViewBounds.height() + newY);
                                SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(viewinfo);
                                ObjectAnimator animBounds =
                                        ObjectAnimator.ofObject(
                                                viewinfo.viewSnapshot,
                                                "bounds",
                                                SemAnimatorUtils.BOUNDS_EVALUATOR,
                                                oldViewBounds,
                                                newViewBounds);
                                animations.add(animBounds);
                                if (!updateListenerAdded) {
                                    animBounds.addUpdateListener(
                                            SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                                    updateListenerAdded = true;
                                }
                                left3 = currentPos4;
                                listview2 = listview3;
                                entrySetIterator = entrySetIterator2;
                                lastVisiblePosition = lastVisiblePosition2;
                                currentPos2 = currentPos3;
                            }
                        }
                        int currentPos5 = left3;
                        for (Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> viewEntry2 :
                                SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache
                                        .entrySet()) {
                            SemAbsAddDeleteAnimator.ViewInfo viewinfo2 = viewEntry2.getValue();
                            int newY3 = viewinfo2.top + (insertedItems.size() * singleItemHeight);
                            Rect oldViewBounds2 =
                                    new Rect(
                                            currentPos5,
                                            viewinfo2.top,
                                            currentPos5 + width3,
                                            viewinfo2.bottom);
                            Rect newViewBounds2 =
                                    new Rect(
                                            currentPos5,
                                            newY3,
                                            oldViewBounds2.width() + currentPos5,
                                            oldViewBounds2.height() + newY3);
                            SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(viewinfo2);
                            ObjectAnimator animBounds2 =
                                    ObjectAnimator.ofObject(
                                            viewinfo2.viewSnapshot,
                                            "bounds",
                                            SemAnimatorUtils.BOUNDS_EVALUATOR,
                                            oldViewBounds2,
                                            newViewBounds2);
                            if (!updateListenerAdded) {
                                animBounds2.addUpdateListener(
                                        SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                            }
                            animations.add(animBounds2);
                        }
                        SemAddDeleteListAnimator.this.mOldViewCache.clear();
                        SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.clear();
                        AnimatorSet animSet = new AnimatorSet();
                        animSet.playTogether(animations);
                        animSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
                        animSet.addListener(
                                new AnimatorListenerAdapter() { // from class:
                                    // com.samsung.android.animation.SemAddDeleteListAnimator.5.1
                                    @Override // android.animation.AnimatorListenerAdapter,
                                    // android.animation.Animator.AnimatorListener
                                    public void onAnimationStart(Animator animation) {
                                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationStart #2");
                                        if (SemAddDeleteListAnimator.this.mListView.isPressed()) {
                                            SemAddDeleteListAnimator.this.mListView.setPressed(
                                                    false);
                                        }
                                        SemAddDeleteListAnimator.this.mListView.setEnabled(false);
                                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener
                                                != null) {
                                            SemAddDeleteListAnimator.this.mOnAddDeleteListener
                                                    .onAnimationStart(true);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter,
                                    // android.animation.Animator.AnimatorListener
                                    public void onAnimationEnd(Animator animation) {
                                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationEnd #2");
                                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(
                                                SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                                        SemAddDeleteListAnimator.this.mListView.invalidate();
                                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener
                                                != null) {
                                            SemAddDeleteListAnimator.this.mOnAddDeleteListener
                                                    .onAnimationEnd(true);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter,
                                    // android.animation.Animator.AnimatorListener
                                    public void onAnimationCancel(Animator animation) {
                                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationCancel #2");
                                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(
                                                SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                                        SemAddDeleteListAnimator.this.mListView.invalidate();
                                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                                    }
                                });
                        animSet.setDuration(SemAddDeleteListAnimator.this.mTranslationDuration);
                        animSet.start();
                        Log.i(
                                SemAddDeleteListAnimator.TAG,
                                "postDelayed #2 mAniTimeoutRunnable delay = "
                                        + (SemAddDeleteListAnimator.this.mTranslationDuration
                                                + 100));
                        SemAddDeleteListAnimator.this.mHandler.postDelayed(
                                SemAddDeleteListAnimator.this.mAniTimeoutRunnable,
                                SemAddDeleteListAnimator.this.mTranslationDuration + 100);
                    }
                };
    }

    public void setInsertDelete(
            ArrayList<Integer> insertedItemPositions, ArrayList<Integer> deletingItemPositions) {
        if (insertedItemPositions.size() == 0 && deletingItemPositions.size() == 0) {
            return;
        }
        if (deletingItemPositions.size() == 0) {
            prepareInsert(insertedItemPositions);
            if (this.mOnAddDeleteListener != null) {
                this.mOnAddDeleteListener.onAdd();
            }
            insertIntoAdapterCompleted();
            return;
        }
        if (insertedItemPositions.size() == 0) {
            prepareDelete(deletingItemPositions);
            if (this.mOnAddDeleteListener != null) {
                this.mOnAddDeleteListener.onDelete();
            }
            deleteFromAdapterCompleted();
            return;
        }
        prepareInsertDelete(insertedItemPositions, deletingItemPositions);
        this.mIsInsertDelete = true;
        if (this.mOnAddDeleteListener != null) {
            this.mOnAddDeleteListener.onDelete();
            this.mOnAddDeleteListener.onAdd();
        }
        this.mIsInsertDelete = false;
        insertDeleteFromAdapterCompleted();
    }

    public void setInsertDeletePending(
            ArrayList<Integer> insertedItemPositions, ArrayList<Integer> deletingItemPositions) {
        if (insertedItemPositions.size() == 0 && deletingItemPositions.size() == 0) {
            return;
        }
        if (deletingItemPositions.size() == 0) {
            prepareInsert(insertedItemPositions);
            if (this.mOnAddDeleteListener != null) {
                this.mOnAddDeleteListener.onAdd();
                return;
            }
            return;
        }
        if (insertedItemPositions.size() == 0) {
            prepareDelete(deletingItemPositions);
            if (this.mOnAddDeleteListener != null) {
                this.mOnAddDeleteListener.onDelete();
                return;
            }
            return;
        }
        prepareInsertDelete(insertedItemPositions, deletingItemPositions);
        this.mIsInsertDelete = true;
        if (this.mOnAddDeleteListener != null) {
            this.mOnAddDeleteListener.onDelete();
            this.mOnAddDeleteListener.onAdd();
        }
        this.mIsInsertDelete = false;
    }

    public void insertDeleteFromAdapterCompleted() {
        if (!this.mInsertDeletePending) {
            throw new SemAbsAddDeleteAnimator.SetDeletePendingIsNotCalledBefore();
        }
        this.mInsertDeletePending = false;
        this.mListView
                .getViewTreeObserver()
                .addOnPreDrawListener(
                        new ViewTreeObserver
                                .OnPreDrawListener() { // from class:
                                                       // com.samsung.android.animation.SemAddDeleteListAnimator.6
                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public boolean onPreDraw() {
                                SemAddDeleteListAnimator.this
                                        .mListView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                if (SemAddDeleteListAnimator.this.mInsertDeleteRunnable != null) {
                                    SemAddDeleteListAnimator.this.mInsertDeleteRunnable.run();
                                    SemAddDeleteListAnimator.this.mInsertDeleteRunnable = null;
                                    return true;
                                }
                                return true;
                            }
                        });
    }

    private void prepareInsertDelete(
            ArrayList<Integer> insertedItemPositions, ArrayList<Integer> deletingItemPositions) {
        int i;
        int i2;
        int i3;
        int position;
        this.mInsertDeletePending = true;
        ensureAdapterAndListener();
        final ArrayList<Integer> insertedItems = new ArrayList<>(insertedItemPositions);
        Collections.sort(insertedItems);
        final HashSet<Integer> insertedItemPosHash = new HashSet<>(insertedItems);
        final ArrayList<Integer> deletedItems = new ArrayList<>(deletingItemPositions);
        Collections.sort(deletedItems);
        final HashSet<Integer> deletedItemPosHash = new HashSet<>(deletedItems);
        final ListAdapter adapter = this.mListView.getAdapter();
        final int childCountBefore = this.mListView.getChildCount();
        int adapterCount = adapter.getCount();
        int firstVisiblePosBefore = this.mListView.getFirstVisiblePosition();
        int headerViewsCount = this.mListView.getHeaderViewsCount();
        int footerViewsCount = this.mListView.getFooterViewsCount();
        int i4 = 0;
        while (i4 < childCountBefore) {
            int position2 = i4 + firstVisiblePosBefore;
            View child = this.mListView.getChildAt(i4);
            long itemId = adapter.getItemId(position2);
            if (child.getHeight() == 0) {
                i = i4;
            } else if (child.getWidth() == 0) {
                i = i4;
            } else {
                BitmapDrawable snapshot = SemAnimatorUtils.getBitmapDrawableFromView(child);
                if (itemId != -1) {
                    this.mOldViewCache.put(
                            Long.valueOf(itemId),
                            new SemAbsAddDeleteAnimator.ViewInfo(
                                    snapshot, position2, 0, child.getTop(), 0, child.getBottom()));
                    i2 = i4;
                } else {
                    if (position2 < headerViewsCount) {
                        i3 = i4;
                        itemId = position2 + 1;
                        position = position2;
                    } else {
                        i3 = i4;
                        if (position2 < adapterCount - footerViewsCount) {
                            position = position2;
                        } else {
                            int footerId = ((position2 + footerViewsCount) - adapterCount) + 1;
                            position = position2;
                            itemId = -footerId;
                        }
                    }
                    this.mOldHeaderFooterViewCache.put(
                            Long.valueOf(itemId),
                            new SemAbsAddDeleteAnimator.ViewInfo(
                                    snapshot, position, 0, child.getTop(), 0, child.getBottom()));
                    i2 = i3;
                }
                i4 = i2 + 1;
            }
            i2 = i;
            Log.e(TAG, "setInsert() child's one of dimensions is 0, i=" + i2);
            i4 = i2 + 1;
        }
        final HashMap<Integer, Integer> upcomingViewsStartCoords = new HashMap<>();
        for (int j = 0; j < insertedItems.size(); j++) {
            int insertedItemPos = insertedItems.get(j).intValue();
            int itemAtStartPos = insertedItemPos - j;
            for (int k = 0; k < deletedItems.size(); k++) {
                if (deletedItems.get(k).intValue() <= itemAtStartPos) {
                    itemAtStartPos++;
                }
            }
            View refView = this.mListView.getChildAt(itemAtStartPos - firstVisiblePosBefore);
            if (refView != null) {
                upcomingViewsStartCoords.put(
                        Integer.valueOf(insertedItemPos), Integer.valueOf(refView.getTop()));
            }
        }
        this.mInsertDeleteRunnable =
                new Runnable() { // from class:
                    // com.samsung.android.animation.SemAddDeleteListAnimator.7
                    @Override // java.lang.Runnable
                    public void run() {
                        int width;
                        int firstVisiblePos;
                        ListView listview;
                        float translationY;
                        float translationY2;
                        float translationY3;
                        boolean newItemsComingFromTop;
                        ObjectAnimator anim;
                        float firstChildTop;
                        int footerViewsCount2;
                        int left;
                        int width2;
                        int headerViewsCount2;
                        int adapterCount2;
                        int rowShift;
                        int headerViewsCount3;
                        ListView listview2 = SemAddDeleteListAnimator.this.mListView;
                        int firstVisiblePos2 = listview2.getFirstVisiblePosition();
                        int lastVisiblePos = listview2.getLastVisiblePosition();
                        int headerViewsCount4 = listview2.getHeaderViewsCount();
                        int footerViewsCount3 = listview2.getFooterViewsCount();
                        int childCount = listview2.getChildCount();
                        int adapterCount3 = adapter.getCount();
                        float translationY4 = 0.0f;
                        ArrayList<Animator> animations = new ArrayList<>();
                        int singleItemHeight = 0;
                        int left2 = 0;
                        if (childCount > headerViewsCount4) {
                            singleItemHeight =
                                    SemAddDeleteListAnimator.this.getChildMaxHeight()
                                            + listview2.getDividerHeight();
                            left2 = listview2.getChildAt(headerViewsCount4).getLeft();
                            width = listview2.getChildAt(0).getWidth();
                        } else {
                            width = listview2.getWidth();
                        }
                        boolean newItemsComingFromTop2 = true;
                        int newItemsComingFromTopCount = firstVisiblePos2;
                        int newItemsFromTopRemaining = newItemsComingFromTopCount;
                        int newlyAppearingViewOldPositionFromBottom =
                                lastVisiblePos + 1 + (childCountBefore - childCount);
                        int i5 = 0;
                        while (i5 < childCount) {
                            int lastVisiblePos2 = lastVisiblePos;
                            int lastVisiblePos3 = i5 + firstVisiblePos2;
                            float translationY5 = translationY4;
                            long itemId2 = adapter.getItemId(lastVisiblePos3);
                            View child2 = listview2.getChildAt(i5);
                            int newItemsComingFromTopCount2 = newItemsComingFromTopCount;
                            float newY = child2.getTop();
                            ListView listview3 = listview2;
                            if (itemId2 == -1) {
                                if (lastVisiblePos3 < headerViewsCount4) {
                                    left = left2;
                                    width2 = width;
                                    headerViewsCount3 = headerViewsCount4;
                                    footerViewsCount2 = footerViewsCount3;
                                    itemId2 = lastVisiblePos3 + 1;
                                } else {
                                    left = left2;
                                    width2 = width;
                                    if (lastVisiblePos3 < adapterCount3 - footerViewsCount3) {
                                        headerViewsCount3 = headerViewsCount4;
                                        footerViewsCount2 = footerViewsCount3;
                                    } else {
                                        long footerId2 =
                                                ((lastVisiblePos3 + footerViewsCount3)
                                                                - adapterCount3)
                                                        + 1;
                                        headerViewsCount3 = headerViewsCount4;
                                        footerViewsCount2 = footerViewsCount3;
                                        itemId2 = -footerId2;
                                    }
                                }
                                SemAbsAddDeleteAnimator.ViewInfo viewInfo =
                                        SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache
                                                .remove(Long.valueOf(itemId2));
                                if (viewInfo == null) {
                                    Log.e(
                                            SemAddDeleteListAnimator.TAG,
                                            "AFTER header/footer SOMETHING WENT WRONG, in the new"
                                                + " layout, header/footer is appearing that was not"
                                                + " present before!");
                                    headerViewsCount2 = headerViewsCount3;
                                    adapterCount2 = adapterCount3;
                                } else {
                                    viewInfo.recycleBitmap();
                                    if (viewInfo.top == newY) {
                                        Log.e(
                                                SemAddDeleteListAnimator.TAG,
                                                "AFTER header/footer something strange is"
                                                    + " happening, the coordinates are same after"
                                                    + " layout, viewInfo.top="
                                                        + viewInfo.top
                                                        + ", newY="
                                                        + newY);
                                        headerViewsCount2 = headerViewsCount3;
                                        adapterCount2 = adapterCount3;
                                    } else {
                                        float translationY6 = viewInfo.top - newY;
                                        ObjectAnimator anim2 =
                                                SemAddDeleteListAnimator.this.getTranslateAnim(
                                                        child2, 0.0f, translationY6);
                                        animations.add(anim2);
                                        headerViewsCount2 = headerViewsCount3;
                                        translationY4 = translationY6;
                                        adapterCount2 = adapterCount3;
                                        i5++;
                                        lastVisiblePos = lastVisiblePos2;
                                        headerViewsCount4 = headerViewsCount2;
                                        newItemsComingFromTopCount = newItemsComingFromTopCount2;
                                        listview2 = listview3;
                                        left2 = left;
                                        width = width2;
                                        footerViewsCount3 = footerViewsCount2;
                                        adapterCount3 = adapterCount2;
                                    }
                                }
                                translationY4 = translationY5;
                                i5++;
                                lastVisiblePos = lastVisiblePos2;
                                headerViewsCount4 = headerViewsCount2;
                                newItemsComingFromTopCount = newItemsComingFromTopCount2;
                                listview2 = listview3;
                                left2 = left;
                                width = width2;
                                footerViewsCount3 = footerViewsCount2;
                                adapterCount3 = adapterCount2;
                            } else {
                                int headerViewsCount5 = headerViewsCount4;
                                footerViewsCount2 = footerViewsCount3;
                                left = left2;
                                width2 = width;
                                Integer startPos =
                                        (Integer)
                                                upcomingViewsStartCoords.remove(
                                                        Integer.valueOf(lastVisiblePos3));
                                SemAbsAddDeleteAnimator.ViewInfo viewInfo2 =
                                        SemAddDeleteListAnimator.this.mOldViewCache.remove(
                                                Long.valueOf(itemId2));
                                if (viewInfo2 != null) {
                                    viewInfo2.recycleBitmap();
                                    if (viewInfo2.top == newY) {
                                        headerViewsCount2 = headerViewsCount5;
                                        adapterCount2 = adapterCount3;
                                        newItemsComingFromTop2 = false;
                                        translationY4 = translationY5;
                                    } else {
                                        float translationY7 = viewInfo2.top - newY;
                                        headerViewsCount2 = headerViewsCount5;
                                        ObjectAnimator anim3 =
                                                SemAddDeleteListAnimator.this.getTranslateAnim(
                                                        child2, 0.0f, translationY7);
                                        animations.add(anim3);
                                        adapterCount2 = adapterCount3;
                                        newItemsComingFromTop2 = false;
                                        translationY4 = translationY7;
                                    }
                                } else {
                                    headerViewsCount2 = headerViewsCount5;
                                    if (startPos != null) {
                                        float translationY8 = startPos.intValue() - newY;
                                        ObjectAnimator anim4 =
                                                SemAddDeleteListAnimator.this
                                                        .getInsertTranslateAlphaScaleAnim(
                                                                child2, 0.0f, translationY8);
                                        animations.add(anim4);
                                        translationY4 = translationY8;
                                        adapterCount2 = adapterCount3;
                                    } else {
                                        int currentPos = i5 + firstVisiblePos2;
                                        if (insertedItemPosHash.contains(
                                                Integer.valueOf(currentPos))) {
                                            int shiftCount =
                                                    SemAddDeleteListAnimator.this.getShiftCount(
                                                            currentPos,
                                                            insertedItems,
                                                            deletedItems);
                                            int oldPos = currentPos - shiftCount;
                                            int rowShiftInsert = currentPos - oldPos;
                                            int shiftCount2 =
                                                    child2.getTop()
                                                            - (rowShiftInsert * singleItemHeight);
                                            float translationYInsert = shiftCount2 - newY;
                                            adapterCount2 = adapterCount3;
                                            ObjectAnimator anim5 =
                                                    SemAddDeleteListAnimator.this
                                                            .getInsertTranslateAlphaScaleAnim(
                                                                    child2,
                                                                    0.0f,
                                                                    translationYInsert);
                                            animations.add(anim5);
                                            translationY4 = translationY5;
                                        } else {
                                            adapterCount2 = adapterCount3;
                                            if (newItemsFromTopRemaining > 0
                                                    && newItemsComingFromTop2) {
                                                rowShift =
                                                        -SemAddDeleteListAnimator.this
                                                                .getShiftCount(
                                                                        currentPos,
                                                                        insertedItems,
                                                                        deletedItems);
                                                newItemsFromTopRemaining--;
                                            } else {
                                                rowShift =
                                                        newlyAppearingViewOldPositionFromBottom
                                                                - lastVisiblePos3;
                                                newlyAppearingViewOldPositionFromBottom++;
                                            }
                                            int oldY =
                                                    child2.getTop() + (rowShift * singleItemHeight);
                                            float translationY9 = oldY - newY;
                                            ObjectAnimator anim6 =
                                                    SemAddDeleteListAnimator.this.getTranslateAnim(
                                                            child2, 0.0f, translationY9);
                                            animations.add(anim6);
                                            translationY4 = translationY9;
                                        }
                                    }
                                }
                                i5++;
                                lastVisiblePos = lastVisiblePos2;
                                headerViewsCount4 = headerViewsCount2;
                                newItemsComingFromTopCount = newItemsComingFromTopCount2;
                                listview2 = listview3;
                                left2 = left;
                                width = width2;
                                footerViewsCount3 = footerViewsCount2;
                                adapterCount3 = adapterCount2;
                            }
                        }
                        ListView listview4 = listview2;
                        int left3 = left2;
                        int width3 = width;
                        upcomingViewsStartCoords.clear();
                        Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>>
                                entrySetIterator =
                                        SemAddDeleteListAnimator.this
                                                .mOldViewCache
                                                .entrySet()
                                                .iterator();
                        boolean updateListenerAdded = false;
                        while (entrySetIterator.hasNext()) {
                            Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> oldViewCoordinate =
                                    entrySetIterator.next();
                            SemAbsAddDeleteAnimator.ViewInfo viewInfo3 =
                                    oldViewCoordinate.getValue();
                            SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(viewInfo3);
                            Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>>
                                    entrySetIterator2 = entrySetIterator;
                            int left4 = left3;
                            Rect startValue =
                                    new Rect(
                                            left4, viewInfo3.top, left3 + width3, viewInfo3.bottom);
                            int newPosition =
                                    SemAddDeleteListAnimator.this.getNewPosition(
                                            viewInfo3.oldPosition, insertedItems, deletedItems);
                            boolean isDeletedItem =
                                    deletedItemPosHash.contains(
                                            Integer.valueOf(viewInfo3.oldPosition));
                            int destinationViewIndex = newPosition - firstVisiblePos2;
                            if (destinationViewIndex < 0) {
                                if (childCount == 0) {
                                    firstVisiblePos = firstVisiblePos2;
                                    firstChildTop = listview4.getPaddingTop();
                                    listview = listview4;
                                } else {
                                    firstVisiblePos = firstVisiblePos2;
                                    listview = listview4;
                                    firstChildTop = listview.getChildAt(0).getTop();
                                }
                                int newPosition2 = viewInfo3.top;
                                float translationY10 = firstChildTop - newPosition2;
                                translationY =
                                        translationY10
                                                - ((-destinationViewIndex) * singleItemHeight);
                            } else {
                                firstVisiblePos = firstVisiblePos2;
                                listview = listview4;
                                if (destinationViewIndex >= childCount) {
                                    if (listview.getChildAt(childCount - 1) == null) {
                                        translationY2 = 0 - viewInfo3.top;
                                    } else {
                                        translationY2 =
                                                listview.getChildAt(childCount - 1).getTop()
                                                        - viewInfo3.top;
                                    }
                                    translationY =
                                            translationY2
                                                    + (((destinationViewIndex - childCount) + 1)
                                                            * singleItemHeight);
                                } else {
                                    float referenceY =
                                            listview.getChildAt(destinationViewIndex).getTop();
                                    translationY = referenceY - viewInfo3.top;
                                }
                            }
                            Rect endValue = new Rect(startValue);
                            int childCount2 = childCount;
                            endValue.offset(0, (int) translationY);
                            if (isDeletedItem) {
                                int horizOffset =
                                        (int)
                                                (((1.0f
                                                                        - SemAbsAddDeleteAnimator
                                                                                .START_SCALE_FACTOR)
                                                                / 2.0f)
                                                        * endValue.width());
                                translationY3 = translationY;
                                int vertOffset =
                                        (int)
                                                (((1.0f
                                                                        - SemAbsAddDeleteAnimator
                                                                                .START_SCALE_FACTOR)
                                                                / 2.0f)
                                                        * endValue.height());
                                newItemsComingFromTop = newItemsComingFromTop2;
                                PropertyValuesHolder pvhBounds =
                                        PropertyValuesHolder.ofObject(
                                                "bounds",
                                                SemAnimatorUtils.BOUNDS_EVALUATOR,
                                                startValue,
                                                new Rect(
                                                        endValue.left + horizOffset,
                                                        endValue.top + vertOffset,
                                                        endValue.right - horizOffset,
                                                        endValue.bottom - vertOffset));
                                PropertyValuesHolder pvhAlpha =
                                        PropertyValuesHolder.ofInt("alpha", 255, 0);
                                anim =
                                        ObjectAnimator.ofPropertyValuesHolder(
                                                viewInfo3.viewSnapshot, pvhBounds, pvhAlpha);
                            } else {
                                translationY3 = translationY;
                                newItemsComingFromTop = newItemsComingFromTop2;
                                anim =
                                        ObjectAnimator.ofObject(
                                                viewInfo3.viewSnapshot,
                                                "bounds",
                                                SemAnimatorUtils.BOUNDS_EVALUATOR,
                                                startValue,
                                                endValue);
                            }
                            if (!updateListenerAdded) {
                                anim.addUpdateListener(
                                        SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                                updateListenerAdded = true;
                            }
                            animations.add(anim);
                            firstVisiblePos2 = firstVisiblePos;
                            childCount = childCount2;
                            newItemsComingFromTop2 = newItemsComingFromTop;
                            left3 = left4;
                            listview4 = listview;
                            entrySetIterator = entrySetIterator2;
                        }
                        int left5 = left3;
                        for (Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> viewEntry :
                                SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache
                                        .entrySet()) {
                            SemAbsAddDeleteAnimator.ViewInfo viewinfo = viewEntry.getValue();
                            int newY2 = viewinfo.top + (insertedItems.size() * singleItemHeight);
                            Rect oldViewBounds =
                                    new Rect(left5, viewinfo.top, left5 + width3, viewinfo.bottom);
                            Rect newViewBounds =
                                    new Rect(
                                            left5,
                                            newY2,
                                            oldViewBounds.width() + left5,
                                            oldViewBounds.height() + newY2);
                            SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(viewinfo);
                            int left6 = left5;
                            ObjectAnimator animBounds =
                                    ObjectAnimator.ofObject(
                                            viewinfo.viewSnapshot,
                                            "bounds",
                                            SemAnimatorUtils.BOUNDS_EVALUATOR,
                                            oldViewBounds,
                                            newViewBounds);
                            if (!updateListenerAdded) {
                                animBounds.addUpdateListener(
                                        SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                            }
                            animations.add(animBounds);
                            left5 = left6;
                        }
                        SemAddDeleteListAnimator.this.mOldViewCache.clear();
                        SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.clear();
                        AnimatorSet animSet = new AnimatorSet();
                        animSet.playTogether(animations);
                        animSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
                        animSet.addListener(
                                new AnimatorListenerAdapter() { // from class:
                                    // com.samsung.android.animation.SemAddDeleteListAnimator.7.1
                                    @Override // android.animation.AnimatorListenerAdapter,
                                    // android.animation.Animator.AnimatorListener
                                    public void onAnimationStart(Animator animation) {
                                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationStart #3");
                                        if (SemAddDeleteListAnimator.this.mListView.isPressed()) {
                                            SemAddDeleteListAnimator.this.mListView.setPressed(
                                                    false);
                                        }
                                        SemAddDeleteListAnimator.this.mListView.setEnabled(false);
                                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener
                                                != null) {
                                            SemAddDeleteListAnimator.this.mOnAddDeleteListener
                                                    .onAnimationStart(true);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter,
                                    // android.animation.Animator.AnimatorListener
                                    public void onAnimationEnd(Animator animation) {
                                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationEnd #3");
                                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(
                                                SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                                        SemAddDeleteListAnimator.this.mListView.invalidate();
                                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener
                                                != null) {
                                            SemAddDeleteListAnimator.this.mOnAddDeleteListener
                                                    .onAnimationEnd(true);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter,
                                    // android.animation.Animator.AnimatorListener
                                    public void onAnimationCancel(Animator animation) {
                                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationCancel #3");
                                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(
                                                SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                                        SemAddDeleteListAnimator.this.mListView.invalidate();
                                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                                    }
                                });
                        animSet.setDuration(SemAddDeleteListAnimator.this.mTranslationDuration);
                        animSet.start();
                        Log.i(
                                SemAddDeleteListAnimator.TAG,
                                "postDelayed #3 mAniTimeoutRunnable delay = "
                                        + (SemAddDeleteListAnimator.this.mTranslationDuration
                                                + 100));
                        SemAddDeleteListAnimator.this.mHandler.postDelayed(
                                SemAddDeleteListAnimator.this.mAniTimeoutRunnable,
                                SemAddDeleteListAnimator.this.mTranslationDuration + 100);
                    }
                };
    }

    private void ensureAdapterAndListener() {
        ListAdapter adapter = this.mListView.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException(
                    "Adapter need to be set before performing add/delete operations.");
        }
        if (!adapter.hasStableIds()) {
            throw new IllegalStateException(
                    "SemAddDeleteListAnimator requires an adapter that has stable ids");
        }
        if (this.mOnAddDeleteListener == null) {
            throw new IllegalStateException(
                    "OnAddDeleteListener need to be supplied before performing add/delete"
                        + " operations");
        }
    }

    public boolean isInsertDeleting() {
        return this.mIsInsertDelete;
    }
}
