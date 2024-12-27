package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.SemHorizontalListView;
import com.samsung.android.animation.SemAbsAddDeleteAnimator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes5.dex */
public class SemAddDeleteHorizontalListAnimator extends SemAbsAddDeleteAnimator {
    private static String TAG = "SemAddDeleteHListAnimator";
    private SemHorizontalListView mHorizontalListView;
    private OnAddDeleteListener mOnAddDeleteListener;
    LinkedHashMap<Long, SemAbsAddDeleteAnimator.ViewInfo> mOldViewCache = new LinkedHashMap<>();
    LinkedHashMap<Long, SemAbsAddDeleteAnimator.ViewInfo> mOldHeaderFooterViewCache = new LinkedHashMap<>();
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

    public SemAddDeleteHorizontalListAnimator(Context context, SemHorizontalListView listview) {
        this.mHorizontalListView = listview;
        this.mHorizontalListView.setAddDeleteListAnimator(this);
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
        this.mHorizontalListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteHorizontalListAnimator.this.mDeleteRunnable != null) {
                    SemAddDeleteHorizontalListAnimator.this.mDeleteRunnable.run();
                    SemAddDeleteHorizontalListAnimator.this.mDeleteRunnable = null;
                    return true;
                }
                return true;
            }
        });
    }

    private void prepareDelete(ArrayList<Integer> deletingItemPositions) {
        int defaultHeight;
        int defaultTop;
        this.mDeletePending = true;
        final ArrayList<Integer> deletedItems = new ArrayList<>(deletingItemPositions);
        ensureAdapterAndListener();
        Collections.sort(deletedItems);
        final HashSet<Integer> deletedItemPosHash = new HashSet<>(deletedItems);
        final int childCountBefore = this.mHorizontalListView.getChildCount();
        final int firstVisiblePosBefore = this.mHorizontalListView.getFirstVisiblePosition();
        final ListAdapter adapter = this.mHorizontalListView.getAdapter();
        if (this.mHorizontalListView.getChildAt(this.mHorizontalListView.getHeaderViewsCount()) != null) {
            int defaultHeight2 = this.mHorizontalListView.getChildAt(this.mHorizontalListView.getHeaderViewsCount()).getHeight();
            defaultHeight = defaultHeight2;
            defaultTop = this.mHorizontalListView.getChildAt(this.mHorizontalListView.getHeaderViewsCount()).getTop();
        } else {
            int defaultHeight3 = this.mHorizontalListView.getHeight();
            defaultHeight = defaultHeight3;
            defaultTop = 0;
        }
        capturePreAnimationViewCoordinates();
        final int i = defaultTop;
        final int i2 = defaultHeight;
        this.mDeleteRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.2
            /* JADX WARN: Removed duplicated region for block: B:53:0x020d  */
            /* JADX WARN: Removed duplicated region for block: B:56:0x027a  */
            /* JADX WARN: Removed duplicated region for block: B:59:0x0282 A[SYNTHETIC] */
            /* JADX WARN: Removed duplicated region for block: B:60:0x024a  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public void run() {
                /*
                    Method dump skipped, instructions count: 716
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.AnonymousClass2.run():void");
            }
        };
    }

    private void capturePreAnimationViewCoordinates() {
        SemHorizontalListView listview;
        ListAdapter adapter;
        long itemId;
        SemHorizontalListView listview2 = this.mHorizontalListView;
        ListAdapter adapter2 = listview2.getAdapter();
        int childCountBefore = listview2.getChildCount();
        int firstVisiblePosBefore = listview2.getFirstVisiblePosition();
        int adapterCount = adapter2.getCount();
        int headerViewsCount = listview2.getHeaderViewsCount();
        int footerViewsCount = listview2.getFooterViewsCount();
        int i = 0;
        while (i < childCountBefore) {
            View child = listview2.getChildAt(i);
            int position = i + firstVisiblePosBefore;
            long itemId2 = adapter2.getItemId(position);
            if (child.getHeight() == 0) {
                listview = listview2;
                adapter = adapter2;
            } else if (child.getWidth() == 0) {
                listview = listview2;
                adapter = adapter2;
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
                    adapter = adapter2;
                    this.mOldHeaderFooterViewCache.put(Long.valueOf(itemId), new SemAbsAddDeleteAnimator.ViewInfo(snapshot, position, child.getLeft(), 0, child.getRight(), 0));
                } else {
                    listview = listview2;
                    adapter = adapter2;
                    this.mOldViewCache.put(Long.valueOf(itemId2), new SemAbsAddDeleteAnimator.ViewInfo(snapshot, i + firstVisiblePosBefore, child.getLeft(), 0, child.getRight(), 0));
                }
                i++;
                listview2 = listview;
                adapter2 = adapter;
            }
            Log.e(TAG, "setDelete() child's one of dimensions is 0, i = " + i);
            i++;
            listview2 = listview;
            adapter2 = adapter;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getChildMaxWidth() {
        int width;
        int childCount = this.mHorizontalListView.getChildCount();
        int adapterCount = this.mHorizontalListView.getAdapter().getCount();
        int firstVisiblePos = this.mHorizontalListView.getFirstVisiblePosition();
        int childWidth = 0;
        for (int i = 0; i < childCount; i++) {
            int pos = i + firstVisiblePos;
            if (pos >= this.mHorizontalListView.getHeaderViewsCount() && pos < adapterCount - this.mHorizontalListView.getFooterViewsCount() && (width = this.mHorizontalListView.getChildAt(i).getWidth()) > childWidth) {
                childWidth = width;
            }
        }
        return childWidth;
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
        this.mHorizontalListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.3
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteHorizontalListAnimator.this.mInsertRunnable != null) {
                    SemAddDeleteHorizontalListAnimator.this.mInsertRunnable.run();
                    SemAddDeleteHorizontalListAnimator.this.mInsertRunnable = null;
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
        SemHorizontalListView listview = this.mHorizontalListView;
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
                        this.mOldHeaderFooterViewCache.put(Long.valueOf(-footerId), new SemAbsAddDeleteAnimator.ViewInfo(snapshot, position, child.getLeft(), 0, child.getRight(), 0));
                    }
                } else {
                    childCount = childCount2;
                    adapterCount = adapterCount2;
                    this.mOldViewCache.put(Long.valueOf(itemId), new SemAbsAddDeleteAnimator.ViewInfo(snapshot, position, child.getLeft(), 0, child.getRight(), 0));
                }
                i2++;
                childCount2 = childCount;
                adapterCount2 = adapterCount;
                i = 1;
            }
            Log.e(TAG, "setInsert() child's one of dimensions is 0, i = " + i2);
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
                upcomingViewsStartCoords.put(Integer.valueOf(insertedItemPos), Integer.valueOf(refView.getLeft()));
            }
        }
        this.mInsertRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.4
            @Override // java.lang.Runnable
            public void run() {
                int height;
                int newX;
                int footerViewsCount2;
                int adapterCount3;
                int top;
                int height2;
                int firstVisiblePos2;
                int oldX;
                ObjectAnimator anim;
                SemHorizontalListView listview2 = SemAddDeleteHorizontalListAnimator.this.mHorizontalListView;
                int firstVisiblePos3 = listview2.getFirstVisiblePosition();
                int headerViewsCount = listview2.getHeaderViewsCount();
                int footerViewsCount3 = listview2.getFooterViewsCount();
                int top2 = listview2.getChildCount();
                int adapterCount4 = adapter.getCount();
                float translationX = 0.0f;
                ArrayList<Animator> animations = new ArrayList<>();
                int singleItemWidth = 0;
                int top3 = 0;
                if (top2 > headerViewsCount) {
                    singleItemWidth = SemAddDeleteHorizontalListAnimator.this.getChildMaxWidth();
                    top3 = listview2.getChildAt(headerViewsCount).getTop();
                    height = listview2.getChildAt(0).getHeight();
                } else {
                    height = listview2.getHeight();
                }
                int i3 = 0;
                while (i3 < top2) {
                    int position2 = i3 + firstVisiblePos3;
                    long itemId2 = adapter.getItemId(position2);
                    View child2 = listview2.getChildAt(i3);
                    int headerViewsCount2 = headerViewsCount;
                    float newX2 = child2.getLeft();
                    float translationX2 = translationX;
                    if (itemId2 == -1) {
                        adapterCount3 = adapterCount4;
                        long footerId2 = ((position2 + footerViewsCount3) - adapterCount4) + 1;
                        footerViewsCount2 = footerViewsCount3;
                        top = top3;
                        height2 = height;
                        SemAbsAddDeleteAnimator.ViewInfo viewInfo = SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(-footerId2));
                        if (viewInfo == null) {
                            Log.e(SemAddDeleteHorizontalListAnimator.TAG, "AFTER header/footer SOMETHING WENT WRONG, in the new layout, header/footer is appearing that was not present before!");
                        } else {
                            viewInfo.recycleBitmap();
                            if (viewInfo.left == newX2) {
                                Log.e(SemAddDeleteHorizontalListAnimator.TAG, "AFTER header/footer something strange is happening, the coordinates are same after layout, viewInfo.left=" + viewInfo.left + ", newX=" + newX2);
                            } else {
                                float translationX3 = viewInfo.left - newX2;
                                ObjectAnimator anim2 = SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(child2, translationX3, 0.0f);
                                animations.add(anim2);
                                firstVisiblePos2 = firstVisiblePos3;
                                translationX = translationX3;
                                i3++;
                                headerViewsCount = headerViewsCount2;
                                adapterCount4 = adapterCount3;
                                footerViewsCount3 = footerViewsCount2;
                                top3 = top;
                                height = height2;
                                firstVisiblePos3 = firstVisiblePos2;
                            }
                        }
                        firstVisiblePos2 = firstVisiblePos3;
                        translationX = translationX2;
                        i3++;
                        headerViewsCount = headerViewsCount2;
                        adapterCount4 = adapterCount3;
                        footerViewsCount3 = footerViewsCount2;
                        top3 = top;
                        height = height2;
                        firstVisiblePos3 = firstVisiblePos2;
                    } else {
                        footerViewsCount2 = footerViewsCount3;
                        adapterCount3 = adapterCount4;
                        top = top3;
                        height2 = height;
                        Integer startPos = (Integer) upcomingViewsStartCoords.remove(Integer.valueOf(position2));
                        SemAbsAddDeleteAnimator.ViewInfo viewInfo2 = SemAddDeleteHorizontalListAnimator.this.mOldViewCache.remove(Long.valueOf(itemId2));
                        if (viewInfo2 != null) {
                            viewInfo2.recycleBitmap();
                            if (viewInfo2.left != newX2) {
                                translationX = viewInfo2.left - newX2;
                                ObjectAnimator anim3 = SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(child2, translationX, 0.0f);
                                animations.add(anim3);
                                firstVisiblePos2 = firstVisiblePos3;
                            }
                            firstVisiblePos2 = firstVisiblePos3;
                            translationX = translationX2;
                        } else if (startPos != null) {
                            translationX = startPos.intValue() - newX2;
                            ObjectAnimator anim4 = SemAddDeleteHorizontalListAnimator.this.getInsertTranslateAlphaScaleAnim(child2, translationX, 0.0f);
                            animations.add(anim4);
                            firstVisiblePos2 = firstVisiblePos3;
                        } else {
                            int currentPos = i3 + firstVisiblePos3;
                            int shiftCount = SemAddDeleteHorizontalListAnimator.this.getShiftCount(currentPos, insertedItems);
                            int oldPos = currentPos - shiftCount;
                            int rowShift = currentPos - oldPos;
                            firstVisiblePos2 = firstVisiblePos3;
                            if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                                oldX = child2.getLeft() + (rowShift * singleItemWidth);
                            } else {
                                int oldX2 = child2.getLeft();
                                oldX = oldX2 - (rowShift * singleItemWidth);
                            }
                            float translationX4 = oldX - newX2;
                            if (insertedItemPosHash.contains(Integer.valueOf(currentPos))) {
                                anim = SemAddDeleteHorizontalListAnimator.this.getInsertTranslateAlphaScaleAnim(child2, translationX4, 0.0f);
                            } else {
                                anim = SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(child2, translationX4, 0.0f);
                            }
                            animations.add(anim);
                            translationX = translationX4;
                        }
                        i3++;
                        headerViewsCount = headerViewsCount2;
                        adapterCount4 = adapterCount3;
                        footerViewsCount3 = footerViewsCount2;
                        top3 = top;
                        height = height2;
                        firstVisiblePos3 = firstVisiblePos2;
                    }
                }
                int currentPos2 = top3;
                int height3 = height;
                upcomingViewsStartCoords.clear();
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator = SemAddDeleteHorizontalListAnimator.this.mOldViewCache.entrySet().iterator();
                int lastVisiblePosition = listview2.getLastVisiblePosition();
                boolean updateListenerAdded = false;
                int currentPos3 = lastVisiblePosition;
                while (entrySetIterator.hasNext()) {
                    currentPos3++;
                    if (!insertedItems.contains(Integer.valueOf(currentPos3))) {
                        Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> viewEntry = entrySetIterator.next();
                        SemAbsAddDeleteAnimator.ViewInfo viewinfo = viewEntry.getValue();
                        int newPosition = SemAddDeleteHorizontalListAnimator.this.getNewPositionForInsert(viewinfo.oldPosition, insertedItems);
                        if (newPosition < listview2.getFirstVisiblePosition()) {
                            currentPos3--;
                            int rowShift2 = listview2.getFirstVisiblePosition() - newPosition;
                            int childLeft = top2 != 0 ? listview2.getChildAt(0).getLeft() : listview2.getLeft();
                            if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                                newX = (rowShift2 * singleItemWidth) + childLeft;
                            } else {
                                newX = childLeft - (rowShift2 * singleItemWidth);
                            }
                        } else {
                            int rowShift3 = currentPos3 - viewinfo.oldPosition;
                            if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                                newX = viewinfo.left - (rowShift3 * singleItemWidth);
                            } else {
                                newX = viewinfo.left + (rowShift3 * singleItemWidth);
                            }
                        }
                        SemHorizontalListView listview3 = listview2;
                        Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator2 = entrySetIterator;
                        int lastVisiblePosition2 = lastVisiblePosition;
                        int childCount3 = top2;
                        int childCount4 = currentPos2;
                        Rect oldViewBounds = new Rect(viewinfo.left, childCount4, viewinfo.right, currentPos2 + height3);
                        Rect newViewBounds = new Rect(newX, childCount4, oldViewBounds.width() + newX, oldViewBounds.height() + childCount4);
                        SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.add(viewinfo);
                        int currentPos4 = currentPos3;
                        ObjectAnimator animBounds = ObjectAnimator.ofObject(viewinfo.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, oldViewBounds, newViewBounds);
                        animations.add(animBounds);
                        if (!updateListenerAdded) {
                            animBounds.addUpdateListener(SemAddDeleteHorizontalListAnimator.this.mBitmapUpdateListener);
                            updateListenerAdded = true;
                        }
                        listview2 = listview3;
                        entrySetIterator = entrySetIterator2;
                        lastVisiblePosition = lastVisiblePosition2;
                        currentPos3 = currentPos4;
                        currentPos2 = childCount4;
                        top2 = childCount3;
                    }
                }
                int childCount5 = currentPos2;
                for (Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> viewEntry2 : SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.entrySet()) {
                    SemAbsAddDeleteAnimator.ViewInfo viewinfo2 = viewEntry2.getValue();
                    int newX3 = viewinfo2.left + (insertedItems.size() * singleItemWidth);
                    Rect oldViewBounds2 = new Rect(viewinfo2.left, childCount5, viewinfo2.right, childCount5 + height3);
                    Rect newViewBounds2 = new Rect(newX3, childCount5, oldViewBounds2.width() + newX3, oldViewBounds2.height() + childCount5);
                    SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.add(viewinfo2);
                    ObjectAnimator animBounds2 = ObjectAnimator.ofObject(viewinfo2.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, oldViewBounds2, newViewBounds2);
                    if (!updateListenerAdded) {
                        animBounds2.addUpdateListener(SemAddDeleteHorizontalListAnimator.this.mBitmapUpdateListener);
                    }
                    animations.add(animBounds2);
                }
                SemAddDeleteHorizontalListAnimator.this.mOldViewCache.clear();
                SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.clear();
                AnimatorSet animSet = new AnimatorSet();
                animSet.playTogether(animations);
                animSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
                animSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.4.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animation) {
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(false);
                        if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationStart(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animation) {
                        SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                        if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationEnd(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animation) {
                        Log.d(SemAddDeleteHorizontalListAnimator.TAG, "onAnimationCancel #2");
                        SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                    }
                });
                animSet.setDuration(SemAddDeleteHorizontalListAnimator.this.mTranslationDuration);
                animSet.start();
            }
        };
    }

    private void ensureAdapterAndListener() {
        ListAdapter adapter = this.mHorizontalListView.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("Adapter need to be set before performing add/delete operations.");
        }
        if (!adapter.hasStableIds()) {
            throw new IllegalStateException("TwAddDeleteListAnimator requires an adapter that has stable ids");
        }
        if (this.mOnAddDeleteListener == null) {
            throw new IllegalStateException("OnAddDeleteListener need to be supplied before performing add/delete operations");
        }
    }
}
