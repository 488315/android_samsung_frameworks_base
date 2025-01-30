package com.samsung.android.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
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

/* loaded from: classes5.dex */
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
    gridview.setAddDeleteGridAnimator(this);
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
        new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteGridAnimator.2
          /* JADX WARN: Removed duplicated region for block: B:44:0x01ed  */
          /* JADX WARN: Removed duplicated region for block: B:47:0x0257  */
          /* JADX WARN: Removed duplicated region for block: B:50:0x025f A[SYNTHETIC] */
          /* JADX WARN: Removed duplicated region for block: B:51:0x022a  */
          @Override // java.lang.Runnable
          /*
              Code decompiled incorrectly, please refer to instructions dump.
          */
          public void run() {
            int singleRowHeight;
            int firstVisiblePosAfter;
            int childCountAfter;
            float referenceY;
            float referenceX;
            int newItemsFromTopRemaining;
            int lastNewlyAppearingViewPosition;
            int i2;
            int oldPosition;
            int i3;
            int newItemsComingFromTopCount;
            int oldX;
            float translationY;
            float translationX;
            int currentPos;
            ArrayList<Animator> animations = new ArrayList<>();
            int childCountAfter2 = gridView.getChildCount();
            int firstVisiblePosAfter2 = gridView.getFirstVisiblePosition();
            int numColumns = gridView.getNumColumns();
            if (childCountAfter2 > numColumns) {
              singleRowHeight =
                  gridView.getChildAt(numColumns).getTop() - gridView.getChildAt(0).getTop();
            } else {
              singleRowHeight = singleRowHeightBefore;
            }
            int singleRowHeight2 = 1;
            int newItemsComingFromTopCount2 = firstVisiblePosBefore - firstVisiblePosAfter2;
            int newItemsFromTopRemaining2 = newItemsComingFromTopCount2;
            int lastNewlyAppearingViewPosition2 = lastVisiblePosBefore;
            int i4 = 0;
            while (i4 < childCountAfter2) {
              View child2 = gridView.getChildAt(i4);
              long itemId2 = adapter.getItemId(i4 + firstVisiblePosAfter2);
              SemAbsAddDeleteAnimator.ViewInfo viewInfo =
                  SemAddDeleteGridAnimator.this.mOldViewCache.remove(Long.valueOf(itemId2));
              float newX = child2.getLeft();
              float newY = child2.getTop();
              if (viewInfo != null) {
                viewInfo.recycleBitmap();
                currentPos = 0;
                if (viewInfo.left == newX && viewInfo.top == newY) {
                  newItemsComingFromTopCount = newItemsComingFromTopCount2;
                  i3 = i4;
                  singleRowHeight2 = currentPos;
                  i4 = i3 + 1;
                  newItemsComingFromTopCount2 = newItemsComingFromTopCount;
                } else {
                  translationX = viewInfo.left - newX;
                  translationY = viewInfo.top - newY;
                  newItemsComingFromTopCount = newItemsComingFromTopCount2;
                  i3 = i4;
                }
              } else {
                int currentPos2 = i4 + firstVisiblePosAfter2;
                if (newItemsFromTopRemaining2 > 0 && singleRowHeight2 != 0) {
                  newItemsFromTopRemaining2--;
                  i2 = singleRowHeight2;
                  oldPosition = currentPos2 - newItemsComingFromTopCount2;
                } else {
                  i2 = singleRowHeight2;
                  int oldPosition2 =
                      SemAddDeleteGridAnimator.this.getNextAppearingViewPosition(
                          deletedItemPosHash, lastNewlyAppearingViewPosition2);
                  lastNewlyAppearingViewPosition2 = oldPosition2;
                  oldPosition = oldPosition2;
                }
                int newItemsComingFromTopCount3 = newItemsComingFromTopCount2;
                int newItemsFromTopRemaining3 = newItemsFromTopRemaining2;
                int lastNewlyAppearingViewPosition3 = lastNewlyAppearingViewPosition2;
                i3 = i4;
                int oldPosRowId = (int) Math.floor(oldPosition / numColumns);
                int newPosRowId = currentPos2 / numColumns;
                int rowShift = oldPosRowId - newPosRowId;
                int refPosForLeftCoordinate = oldPosition % numColumns;
                if (refPosForLeftCoordinate < 0) {
                  refPosForLeftCoordinate += numColumns;
                }
                if (childCountAfter2 > refPosForLeftCoordinate) {
                  oldX = gridView.getChildAt(refPosForLeftCoordinate).getLeft();
                  newItemsComingFromTopCount = newItemsComingFromTopCount3;
                } else {
                  newItemsComingFromTopCount = newItemsComingFromTopCount3;
                  oldX =
                      gridView.getChildAt(0).getLeft()
                          + (gridView.getChildAt(0).getWidth() * refPosForLeftCoordinate);
                }
                int oldY = child2.getTop() + (rowShift * singleRowHeight);
                float translationX2 = oldX - newX;
                float translationX3 = oldY;
                translationY = translationX3 - newY;
                translationX = translationX2;
                newItemsFromTopRemaining2 = newItemsFromTopRemaining3;
                currentPos = i2;
                lastNewlyAppearingViewPosition2 = lastNewlyAppearingViewPosition3;
              }
              animations.add(
                  SemAddDeleteGridAnimator.this.getTranslateAnim(
                      child2, translationX, translationY));
              singleRowHeight2 = currentPos;
              i4 = i3 + 1;
              newItemsComingFromTopCount2 = newItemsComingFromTopCount;
            }
            Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator =
                SemAddDeleteGridAnimator.this.mOldViewCache.entrySet().iterator();
            boolean updateListenerAdded = false;
            while (entrySetIterator.hasNext()) {
              Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> oldViewCoordinate =
                  entrySetIterator.next();
              SemAbsAddDeleteAnimator.ViewInfo viewInfo2 = oldViewCoordinate.getValue();
              SemAddDeleteGridAnimator.this.mGhostViewSnapshots.add(viewInfo2);
              Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator2 =
                  entrySetIterator;
              Rect startValue =
                  new Rect(viewInfo2.left, viewInfo2.top, viewInfo2.right, viewInfo2.bottom);
              int newPosition =
                  SemAddDeleteGridAnimator.this.getNewPosition(viewInfo2.oldPosition, deletedItems);
              boolean isDeletedItem =
                  deletedItemPosHash.contains(Integer.valueOf(viewInfo2.oldPosition));
              int destinationViewIndex = newPosition - firstVisiblePosAfter2;
              if (destinationViewIndex < 0) {
                firstVisiblePosAfter = firstVisiblePosAfter2;
              } else if (destinationViewIndex >= childCountAfter2) {
                firstVisiblePosAfter = firstVisiblePosAfter2;
              } else {
                firstVisiblePosAfter = firstVisiblePosAfter2;
                referenceX = gridView.getChildAt(destinationViewIndex).getLeft();
                referenceY = gridView.getChildAt(destinationViewIndex).getTop();
                childCountAfter = childCountAfter2;
                float translationX4 = referenceX - viewInfo2.left;
                float translationY2 = referenceY - viewInfo2.top;
                int numColumns2 = numColumns;
                Rect endValue = new Rect(startValue);
                int singleRowHeight3 = singleRowHeight;
                int singleRowHeight4 = (int) translationX4;
                endValue.offset(singleRowHeight4, (int) translationY2);
                if (isDeletedItem) {
                  newItemsFromTopRemaining = newItemsFromTopRemaining2;
                  lastNewlyAppearingViewPosition = lastNewlyAppearingViewPosition2;
                } else {
                  int horizOffset =
                      (int)
                          (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f)
                              * endValue.width());
                  int vertOffset =
                      (int)
                          (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f)
                              * endValue.height());
                  int i5 = endValue.left + horizOffset;
                  newItemsFromTopRemaining = newItemsFromTopRemaining2;
                  int newItemsFromTopRemaining4 = endValue.top;
                  lastNewlyAppearingViewPosition = lastNewlyAppearingViewPosition2;
                  int lastNewlyAppearingViewPosition4 = endValue.right;
                  endValue =
                      new Rect(
                          i5,
                          newItemsFromTopRemaining4 + vertOffset,
                          lastNewlyAppearingViewPosition4 - horizOffset,
                          endValue.bottom - vertOffset);
                }
                PropertyValuesHolder pvhBounds =
                    PropertyValuesHolder.ofObject(
                        "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, startValue, endValue);
                PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofInt("alpha", 255, 0);
                ObjectAnimator anim =
                    ObjectAnimator.ofPropertyValuesHolder(
                        viewInfo2.viewSnapshot, pvhBounds, pvhAlpha);
                if (updateListenerAdded) {
                  anim.addUpdateListener(SemAddDeleteGridAnimator.this.mBitmapUpdateListener);
                  updateListenerAdded = true;
                }
                animations.add(anim);
                entrySetIterator = entrySetIterator2;
                firstVisiblePosAfter2 = firstVisiblePosAfter;
                childCountAfter2 = childCountAfter;
                numColumns = numColumns2;
                singleRowHeight = singleRowHeight3;
                newItemsFromTopRemaining2 = newItemsFromTopRemaining;
                lastNewlyAppearingViewPosition2 = lastNewlyAppearingViewPosition;
              }
              if (childCountAfter2 > newPosition % numColumns) {
                childCountAfter = childCountAfter2;
                referenceX = gridView.getChildAt(newPosition % numColumns).getLeft();
              } else {
                childCountAfter = childCountAfter2;
                referenceX = gridView.getPaddingLeft();
              }
              int rowShift2 = (viewInfo2.oldPosition / numColumns) - (newPosition / numColumns);
              referenceY = viewInfo2.top - (rowShift2 * singleRowHeight);
              float translationX42 = referenceX - viewInfo2.left;
              float translationY22 = referenceY - viewInfo2.top;
              int numColumns22 = numColumns;
              Rect endValue2 = new Rect(startValue);
              int singleRowHeight32 = singleRowHeight;
              int singleRowHeight42 = (int) translationX42;
              endValue2.offset(singleRowHeight42, (int) translationY22);
              if (isDeletedItem) {}
              PropertyValuesHolder pvhBounds2 =
                  PropertyValuesHolder.ofObject(
                      "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, startValue, endValue2);
              PropertyValuesHolder pvhAlpha2 = PropertyValuesHolder.ofInt("alpha", 255, 0);
              ObjectAnimator anim2 =
                  ObjectAnimator.ofPropertyValuesHolder(
                      viewInfo2.viewSnapshot, pvhBounds2, pvhAlpha2);
              if (updateListenerAdded) {}
              animations.add(anim2);
              entrySetIterator = entrySetIterator2;
              firstVisiblePosAfter2 = firstVisiblePosAfter;
              childCountAfter2 = childCountAfter;
              numColumns = numColumns22;
              singleRowHeight = singleRowHeight32;
              newItemsFromTopRemaining2 = newItemsFromTopRemaining;
              lastNewlyAppearingViewPosition2 = lastNewlyAppearingViewPosition;
            }
            SemAddDeleteGridAnimator.this.mOldViewCache.clear();
            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(animations);
            animSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.animation.SemAddDeleteGridAnimator.2.1
                  @Override // android.animation.AnimatorListenerAdapter,
                            // android.animation.Animator.AnimatorListener
                  public void onAnimationStart(Animator animation) {
                    SemAddDeleteGridAnimator.this.mGridView.setEnabled(false);
                    if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener != null) {
                      SemAddDeleteGridAnimator.this.mOnAddDeleteListener.onAnimationStart(false);
                    }
                  }

                  @Override // android.animation.AnimatorListenerAdapter,
                            // android.animation.Animator.AnimatorListener
                  public void onAnimationEnd(Animator animation) {
                    SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                    SemAddDeleteGridAnimator.this.mGridView.invalidate();
                    SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                    if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener != null) {
                      SemAddDeleteGridAnimator.this.mOnAddDeleteListener.onAnimationEnd(false);
                    }
                  }

                  @Override // android.animation.AnimatorListenerAdapter,
                            // android.animation.Animator.AnimatorListener
                  public void onAnimationCancel(Animator animation) {
                    Log.m94d(SemAddDeleteGridAnimator.TAG, "onAnimationCancel #1");
                    SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                    SemAddDeleteGridAnimator.this.mGridView.invalidate();
                    SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                  }
                });
            animSet.setInterpolator(SemAbsAddDeleteAnimator.DELETE_INTERPOLATOR);
            animSet.setDuration(SemAddDeleteGridAnimator.this.mTranslationDuration);
            animSet.start();
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
            Integer.valueOf(insertedItemPos), new float[] {refView.getLeft(), refView.getTop()});
      }
    }
    this.mInsertRunnable =
        new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteGridAnimator.4
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
                  gridView2.getChildAt(numColumns).getTop() - gridView2.getChildAt(0).getTop();
            }
            int i2 = 0;
            while (i2 < childCount2) {
              long itemId2 = adapter.getItemId(i2 + firstVisiblePos3);
              View child2 = gridView2.getChildAt(i2);
              float[] startPos =
                  (float[]) upcomingViewsStartCoords.get(Integer.valueOf(i2 + firstVisiblePos3));
              float newX = child2.getLeft();
              float newY = child2.getTop();
              int childCount3 = childCount2;
              float translationX2 = translationX;
              SemAbsAddDeleteAnimator.ViewInfo viewInfo =
                  SemAddDeleteGridAnimator.this.mOldViewCache.remove(Long.valueOf(itemId2));
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
                    SemAddDeleteGridAnimator.this.getInsertTranslateAlphaScaleAnim(
                        child2, translationX, translationY);
                animations.add(anim3);
                firstVisiblePos2 = firstVisiblePos3;
              } else {
                int currentPos = i2 + firstVisiblePos3;
                int shiftCount =
                    SemAddDeleteGridAnimator.this.getShiftCount(currentPos, insertedItemPositions);
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
                      SemAddDeleteGridAnimator.this.getInsertTranslateAlphaScaleAnim(
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
            Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator =
                SemAddDeleteGridAnimator.this.mOldViewCache.entrySet().iterator();
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
                float newX2 = gridView2.getChildAt(currentPos2 % numColumns).getLeft();
                float newY2 = viewinfo.top + (rowShift2 * singleRowHeight);
                GridView gridView3 = gridView2;
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator2 =
                    entrySetIterator;
                int i3 = viewinfo.left;
                int lastVisiblePosition2 = lastVisiblePosition;
                int lastVisiblePosition3 = viewinfo.top;
                int currentPos3 = viewinfo.right;
                int numColumns2 = numColumns;
                Rect oldViewBounds =
                    new Rect(i3, lastVisiblePosition3, currentPos3, viewinfo.bottom);
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
                  animBounds.addUpdateListener(SemAddDeleteGridAnimator.this.mBitmapUpdateListener);
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
                    if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener != null) {
                      SemAddDeleteGridAnimator.this.mOnAddDeleteListener.onAnimationStart(true);
                    }
                  }

                  @Override // android.animation.AnimatorListenerAdapter,
                            // android.animation.Animator.AnimatorListener
                  public void onAnimationEnd(Animator animation) {
                    SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                    SemAddDeleteGridAnimator.this.mGridView.invalidate();
                    SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                    if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener != null) {
                      SemAddDeleteGridAnimator.this.mOnAddDeleteListener.onAnimationEnd(true);
                    }
                  }

                  @Override // android.animation.AnimatorListenerAdapter,
                            // android.animation.Animator.AnimatorListener
                  public void onAnimationCancel(Animator animation) {
                    Log.m94d(SemAddDeleteGridAnimator.TAG, "onAnimationCancel #2");
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
          "OnAddDeleteListener need to be supplied before performing add/delete operations");
    }
  }
}
