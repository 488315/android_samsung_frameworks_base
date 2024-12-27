package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class SemAddDeleteGridAnimator extends SemAbsAddDeleteAnimator {
    private static final String TAG = "SemAddDeleteGridAnimator";
    private GridView mGridView;
    private OnAddDeleteListener mOnAddDeleteListener;
    LinkedHashMap<Long, SemAbsAddDeleteAnimator.ViewInfo> mOldViewCache = new LinkedHashMap<>();
    private boolean mInsertPending = false;
    private boolean mDeletePending = false;

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

    public SemAddDeleteGridAnimator(Context context, GridView gridview) {
        this.mGridView = gridview;
        this.mGridView.setAddDeleteGridAnimator(this);
        this.mHostView = gridview;
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
        this.mOnAddDeleteListener.onDelete();
        deleteFromAdapterCompleted();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setDeletePending(ArrayList<Integer> deletingItemPositions) {
        if (deletingItemPositions.size() == 0) {
            return;
        }
        prepareDelete(deletingItemPositions);
        this.mOnAddDeleteListener.onDelete();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void deleteFromAdapterCompleted() {
        if (!this.mDeletePending) {
            throw new SemAbsAddDeleteAnimator.SetDeletePendingIsNotCalledBefore();
        }
        this.mDeletePending = false;
        this.mGridView
                .getViewTreeObserver()
                .addOnPreDrawListener(
                        new ViewTreeObserver
                                .OnPreDrawListener() { // from class:
                                                       // com.samsung.android.animation.SemAddDeleteGridAnimator.1
                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public boolean onPreDraw() {
                                SemAddDeleteGridAnimator.this
                                        .mGridView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                if (SemAddDeleteGridAnimator.this.mDeleteRunnable != null) {
                                    SemAddDeleteGridAnimator.this.mDeleteRunnable.run();
                                    SemAddDeleteGridAnimator.this.mDeleteRunnable = null;
                                    return true;
                                }
                                return true;
                            }
                        });
    }

    private void prepareDelete(ArrayList<Integer> deletingItemPositions) {
        this.mDeletePending = true;
        ensureAdapterAndListener();
        final ArrayList<Integer> deletedItems = new ArrayList<>(deletingItemPositions);
        Collections.sort(deletedItems);
        final HashSet<Integer> deletedItemPosHash = new HashSet<>(deletedItems);
        final GridView gridView = this.mGridView;
        final ListAdapter adapter = gridView.getAdapter();
        int childCountBefore = gridView.getChildCount();
        final int firstVisiblePosBefore = gridView.getFirstVisiblePosition();
        final int lastVisiblePosBefore = gridView.getLastVisiblePosition();
        for (int i = 0; i < childCountBefore; i++) {
            View child = gridView.getChildAt(i);
            long itemId = adapter.getItemId(i + firstVisiblePosBefore);
            this.mOldViewCache.put(
                    Long.valueOf(itemId),
                    new SemAbsAddDeleteAnimator.ViewInfo(
                            SemAnimatorUtils.getBitmapDrawableFromView(child),
                            i + firstVisiblePosBefore,
                            child.getLeft(),
                            child.getTop(),
                            child.getRight(),
                            child.getBottom()));
        }
        final int singleRowHeightBefore = gridView.getChildAt(0).getHeight();
        this.mDeleteRunnable =
                new Runnable() { // from class:
                    // com.samsung.android.animation.SemAddDeleteGridAnimator.2
                    /* JADX WARN: Removed duplicated region for block: B:44:0x01ed  */
                    /* JADX WARN: Removed duplicated region for block: B:47:0x025b  */
                    /* JADX WARN: Removed duplicated region for block: B:50:0x0263 A[SYNTHETIC] */
                    /* JADX WARN: Removed duplicated region for block: B:51:0x022a  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public void run() {
                        /*
                            Method dump skipped, instructions count: 674
                            To view this dump change 'Code comments level' option to 'DEBUG'
                        */
                        throw new UnsupportedOperationException(
                                "Method not decompiled:"
                                    + " com.samsung.android.animation.SemAddDeleteGridAnimator.AnonymousClass2.run():void");
                    }
                };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNextAppearingViewPosition(
            HashSet<Integer> deletedItems, int lastNewlyAppearingViewPosition) {
        int index = lastNewlyAppearingViewPosition + 1;
        while (deletedItems.contains(Integer.valueOf(index))) {
            index++;
        }
        return index;
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setInsert(ArrayList<Integer> insertedItemPositions) {
        if (insertedItemPositions.size() == 0) {
            return;
        }
        prepareInsert(insertedItemPositions);
        this.mOnAddDeleteListener.onAdd();
        insertIntoAdapterCompleted();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setInsertPending(ArrayList<Integer> insertedItemPositions) {
        if (insertedItemPositions.size() == 0) {
            return;
        }
        prepareInsert(insertedItemPositions);
        this.mOnAddDeleteListener.onAdd();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void insertIntoAdapterCompleted() {
        if (!this.mInsertPending) {
            throw new SemAbsAddDeleteAnimator.SetInsertPendingIsNotCalledBefore();
        }
        this.mInsertPending = false;
        this.mGridView
                .getViewTreeObserver()
                .addOnPreDrawListener(
                        new ViewTreeObserver
                                .OnPreDrawListener() { // from class:
                                                       // com.samsung.android.animation.SemAddDeleteGridAnimator.3
                            @Override // android.view.ViewTreeObserver.OnPreDrawListener
                            public boolean onPreDraw() {
                                SemAddDeleteGridAnimator.this
                                        .mGridView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                if (SemAddDeleteGridAnimator.this.mInsertRunnable != null) {
                                    SemAddDeleteGridAnimator.this.mInsertRunnable.run();
                                    SemAddDeleteGridAnimator.this.mInsertRunnable = null;
                                    return true;
                                }
                                return true;
                            }
                        });
    }

    private void prepareInsert(final ArrayList<Integer> insertedItemPositions) {
        this.mInsertPending = true;
        ensureAdapterAndListener();
        Collections.sort(insertedItemPositions);
        final HashSet<Integer> insertedItemPosHash = new HashSet<>(insertedItemPositions);
        GridView gridView = this.mGridView;
        final ListAdapter adapter = gridView.getAdapter();
        int childCount = gridView.getChildCount();
        int firstVisiblePos = gridView.getFirstVisiblePosition();
        for (int i = 0; i < childCount; i++) {
            View child = gridView.getChildAt(i);
            long itemId = adapter.getItemId(i + firstVisiblePos);
            this.mOldViewCache.put(
                    Long.valueOf(itemId),
                    new SemAbsAddDeleteAnimator.ViewInfo(
                            SemAnimatorUtils.getBitmapDrawableFromView(child),
                            i + firstVisiblePos,
                            child.getLeft(),
                            child.getTop(),
                            child.getRight(),
                            child.getBottom()));
        }
        final HashMap<Integer, float[]> upcomingViewsStartCoords = new HashMap<>();
        for (int j = 0; j < insertedItemPositions.size(); j++) {
            int insertedItemPos = insertedItemPositions.get(j).intValue();
            int itemAtStartPos = insertedItemPos - j;
            View refView = gridView.getChildAt(itemAtStartPos - firstVisiblePos);
            if (refView != null) {
                upcomingViewsStartCoords.put(
                        Integer.valueOf(insertedItemPos),
                        new float[] {refView.getLeft(), refView.getTop()});
            }
        }
        this.mInsertRunnable =
                new Runnable() { // from class:
                                 // com.samsung.android.animation.SemAddDeleteGridAnimator.4
                    @Override // java.lang.Runnable
                    public void run() {
                        int firstVisiblePos2;
                        ObjectAnimator anim;
                        GridView gridView2 = SemAddDeleteGridAnimator.this.mGridView;
                        int firstVisiblePos3 = gridView2.getFirstVisiblePosition();
                        int childCount2 = gridView2.getChildCount();
                        float translationX = 0.0f;
                        float translationY = 0.0f;
                        ArrayList<Animator> animations = new ArrayList<>();
                        int numColumns = gridView2.getNumColumns();
                        int singleRowHeight = 0;
                        if (childCount2 > numColumns) {
                            singleRowHeight =
                                    gridView2.getChildAt(numColumns).getTop()
                                            - gridView2.getChildAt(0).getTop();
                        }
                        int i2 = 0;
                        while (i2 < childCount2) {
                            long itemId2 = adapter.getItemId(i2 + firstVisiblePos3);
                            View child2 = gridView2.getChildAt(i2);
                            float[] startPos =
                                    (float[])
                                            upcomingViewsStartCoords.get(
                                                    Integer.valueOf(i2 + firstVisiblePos3));
                            float newX = child2.getLeft();
                            float newY = child2.getTop();
                            int childCount3 = childCount2;
                            float translationX2 = translationX;
                            SemAbsAddDeleteAnimator.ViewInfo viewInfo =
                                    SemAddDeleteGridAnimator.this.mOldViewCache.remove(
                                            Long.valueOf(itemId2));
                            if (viewInfo != null) {
                                viewInfo.recycleBitmap();
                                if (viewInfo.left == newX && viewInfo.top == newY) {
                                    firstVisiblePos2 = firstVisiblePos3;
                                    translationX = translationX2;
                                } else {
                                    translationX = viewInfo.left - newX;
                                    translationY = viewInfo.top - newY;
                                    ObjectAnimator anim2 =
                                            SemAddDeleteGridAnimator.this.getTranslateAnim(
                                                    child2, translationX, translationY);
                                    animations.add(anim2);
                                    firstVisiblePos2 = firstVisiblePos3;
                                }
                            } else if (startPos != null) {
                                translationX = startPos[0] - newX;
                                translationY = startPos[1] - newY;
                                ObjectAnimator anim3 =
                                        SemAddDeleteGridAnimator.this
                                                .getInsertTranslateAlphaScaleAnim(
                                                        child2, translationX, translationY);
                                animations.add(anim3);
                                firstVisiblePos2 = firstVisiblePos3;
                            } else {
                                int currentPos = i2 + firstVisiblePos3;
                                int shiftCount =
                                        SemAddDeleteGridAnimator.this.getShiftCount(
                                                currentPos, insertedItemPositions);
                                int oldPos = currentPos - shiftCount;
                                int oldPosRowId = oldPos / numColumns;
                                int newPosRowId = currentPos / numColumns;
                                int rowShift = newPosRowId - oldPosRowId;
                                firstVisiblePos2 = firstVisiblePos3;
                                int firstVisiblePos4 = oldPos % numColumns;
                                int oldX = gridView2.getChildAt(firstVisiblePos4).getLeft();
                                int shiftCount2 = child2.getTop() - (rowShift * singleRowHeight);
                                float translationX3 = oldX - newX;
                                float translationY2 = shiftCount2 - newY;
                                if (insertedItemPosHash.contains(Integer.valueOf(currentPos))) {
                                    anim =
                                            SemAddDeleteGridAnimator.this
                                                    .getInsertTranslateAlphaScaleAnim(
                                                            child2, translationX3, translationY2);
                                } else {
                                    anim =
                                            SemAddDeleteGridAnimator.this.getTranslateAnim(
                                                    child2, translationX3, translationY2);
                                }
                                animations.add(anim);
                                translationX = translationX3;
                                translationY = translationY2;
                            }
                            i2++;
                            childCount2 = childCount3;
                            firstVisiblePos3 = firstVisiblePos2;
                        }
                        Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>>
                                entrySetIterator =
                                        SemAddDeleteGridAnimator.this
                                                .mOldViewCache
                                                .entrySet()
                                                .iterator();
                        int lastVisiblePosition = gridView2.getLastVisiblePosition();
                        int currentPos2 = lastVisiblePosition;
                        boolean updateListenerAdded = false;
                        while (entrySetIterator.hasNext()) {
                            currentPos2++;
                            if (!insertedItemPositions.contains(Integer.valueOf(currentPos2))) {
                                Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> viewEntry =
                                        entrySetIterator.next();
                                SemAbsAddDeleteAnimator.ViewInfo viewinfo = viewEntry.getValue();
                                int oldPosRowId2 = viewinfo.oldPosition / numColumns;
                                int newPosRowId2 = currentPos2 / numColumns;
                                int rowShift2 = newPosRowId2 - oldPosRowId2;
                                float newX2 =
                                        gridView2.getChildAt(currentPos2 % numColumns).getLeft();
                                float newY2 = viewinfo.top + (rowShift2 * singleRowHeight);
                                GridView gridView3 = gridView2;
                                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>>
                                        entrySetIterator2 = entrySetIterator;
                                int i3 = viewinfo.left;
                                int lastVisiblePosition2 = lastVisiblePosition;
                                int lastVisiblePosition3 = viewinfo.top;
                                int currentPos3 = viewinfo.right;
                                int numColumns2 = numColumns;
                                Rect oldViewBounds =
                                        new Rect(
                                                i3,
                                                lastVisiblePosition3,
                                                currentPos3,
                                                viewinfo.bottom);
                                int singleRowHeight2 = singleRowHeight;
                                int singleRowHeight3 = (int) newY2;
                                Rect newViewBounds =
                                        new Rect(
                                                (int) newX2,
                                                (int) newY2,
                                                (int) (oldViewBounds.width() + newX2),
                                                singleRowHeight3 + oldViewBounds.height());
                                SemAddDeleteGridAnimator.this.mGhostViewSnapshots.add(viewinfo);
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
                                            SemAddDeleteGridAnimator.this.mBitmapUpdateListener);
                                    updateListenerAdded = true;
                                }
                                gridView2 = gridView3;
                                entrySetIterator = entrySetIterator2;
                                lastVisiblePosition = lastVisiblePosition2;
                                currentPos2 = currentPos2;
                                numColumns = numColumns2;
                                singleRowHeight = singleRowHeight2;
                            }
                        }
                        SemAddDeleteGridAnimator.this.mOldViewCache.clear();
                        AnimatorSet animSet = new AnimatorSet();
                        animSet.playTogether(animations);
                        animSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
                        animSet.addListener(
                                new AnimatorListenerAdapter() { // from class:
                                                                // com.samsung.android.animation.SemAddDeleteGridAnimator.4.1
                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationStart(Animator animation) {
                                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(false);
                                        if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener
                                                != null) {
                                            SemAddDeleteGridAnimator.this.mOnAddDeleteListener
                                                    .onAnimationStart(true);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationEnd(Animator animation) {
                                        SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                                        SemAddDeleteGridAnimator.this.mGridView.invalidate();
                                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                                        if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener
                                                != null) {
                                            SemAddDeleteGridAnimator.this.mOnAddDeleteListener
                                                    .onAnimationEnd(true);
                                        }
                                    }

                                    @Override // android.animation.AnimatorListenerAdapter,
                                              // android.animation.Animator.AnimatorListener
                                    public void onAnimationCancel(Animator animation) {
                                        Log.d(SemAddDeleteGridAnimator.TAG, "onAnimationCancel #2");
                                        SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                                        SemAddDeleteGridAnimator.this.mGridView.invalidate();
                                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                                    }
                                });
                        animSet.setDuration(SemAddDeleteGridAnimator.this.mTranslationDuration);
                        animSet.start();
                    }
                };
    }

    private void ensureAdapterAndListener() {
        ListAdapter adapter = this.mGridView.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException(
                    "Adapter need to be set before performing add/delete operations.");
        }
        if (!adapter.hasStableIds()) {
            throw new IllegalStateException(
                    "SemAddDeleteGridAnimator requires an adapter that has stable ids");
        }
        if (this.mOnAddDeleteListener == null) {
            throw new IllegalStateException(
                    "OnAddDeleteListener need to be supplied before performing add/delete"
                        + " operations");
        }
    }
}
