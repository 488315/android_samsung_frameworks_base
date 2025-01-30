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
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.SemHorizontalListView;
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
  LinkedHashMap<Long, SemAbsAddDeleteAnimator.ViewInfo> mOldHeaderFooterViewCache =
      new LinkedHashMap<>();
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
    listview.setAddDeleteListAnimator(this);
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
    this.mHorizontalListView
        .getViewTreeObserver()
        .addOnPreDrawListener(
            new ViewTreeObserver
                .OnPreDrawListener() { // from class:
                                       // com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.1
              @Override // android.view.ViewTreeObserver.OnPreDrawListener
              public boolean onPreDraw() {
                SemAddDeleteHorizontalListAnimator.this
                    .mHorizontalListView
                    .getViewTreeObserver()
                    .removeOnPreDrawListener(this);
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
    SemHorizontalListView semHorizontalListView = this.mHorizontalListView;
    if (semHorizontalListView.getChildAt(semHorizontalListView.getHeaderViewsCount()) != null) {
      SemHorizontalListView semHorizontalListView2 = this.mHorizontalListView;
      int defaultHeight2 =
          semHorizontalListView2
              .getChildAt(semHorizontalListView2.getHeaderViewsCount())
              .getHeight();
      SemHorizontalListView semHorizontalListView3 = this.mHorizontalListView;
      defaultHeight = defaultHeight2;
      defaultTop =
          semHorizontalListView3.getChildAt(semHorizontalListView3.getHeaderViewsCount()).getTop();
    } else {
      int defaultHeight3 = this.mHorizontalListView.getHeight();
      defaultHeight = defaultHeight3;
      defaultTop = 0;
    }
    capturePreAnimationViewCoordinates();
    final int i = defaultTop;
    final int i2 = defaultHeight;
    this.mDeleteRunnable =
        new Runnable() { // from class:
                         // com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.2
          /* JADX WARN: Removed duplicated region for block: B:52:0x020b  */
          /* JADX WARN: Removed duplicated region for block: B:55:0x0275  */
          /* JADX WARN: Removed duplicated region for block: B:58:0x027d A[SYNTHETIC] */
          /* JADX WARN: Removed duplicated region for block: B:59:0x0248  */
          @Override // java.lang.Runnable
          /*
              Code decompiled incorrectly, please refer to instructions dump.
          */
          public void run() {
            int top;
            int height;
            int top2;
            int childCountAfter;
            SemHorizontalListView listview;
            int adapterCount;
            int childCountAfter2;
            float translationX;
            SemHorizontalListView listview2;
            float translationX2;
            int singleItemWidth;
            float firstChildLeft;
            float translationX3;
            int headerViewsCount;
            int top3;
            int height2;
            SemAbsAddDeleteAnimator.ViewInfo viewInfo;
            int rowShift;
            int oldX;
            float translationX4;
            SemHorizontalListView listview3 =
                SemAddDeleteHorizontalListAnimator.this.mHorizontalListView;
            int childCountAfter3 = listview3.getChildCount();
            int firstVisiblePosAfter = listview3.getFirstVisiblePosition();
            int lastVisiblePosAfter = listview3.getLastVisiblePosition();
            int headerViewsCount2 = listview3.getHeaderViewsCount();
            int footerViewsCount = listview3.getFooterViewsCount();
            int adapterCount2 = adapter.getCount();
            float translationX5 = 0.0f;
            ArrayList<Animator> animations = new ArrayList<>();
            int singleItemWidth2 = 0;
            if (childCountAfter3 > headerViewsCount2) {
              singleItemWidth2 =
                  SemAddDeleteHorizontalListAnimator.this.getChildMaxWidth()
                      + listview3.getDividerHeight();
              top = listview3.getChildAt(headerViewsCount2).getTop();
              height = listview3.getChildAt(headerViewsCount2).getHeight();
            } else {
              top = i;
              height = i2;
            }
            boolean newItemsComingFromLeft = true;
            int newItemsComingFromLeftCount = firstVisiblePosBefore - firstVisiblePosAfter;
            int newItemsFromLeftRemaining = newItemsComingFromLeftCount;
            int i3 = lastVisiblePosAfter + 1;
            int lastVisiblePosAfter2 = childCountBefore;
            int newlyAppearingViewOldPositionFromRight =
                i3 + (lastVisiblePosAfter2 - childCountAfter3);
            int i4 = 0;
            while (i4 < childCountAfter3) {
              float translationX6 = translationX5;
              View child = listview3.getChildAt(i4);
              SemHorizontalListView listview4 = listview3;
              int position = i4 + firstVisiblePosAfter;
              int childCountAfter4 = childCountAfter3;
              long itemId = adapter.getItemId(position);
              float newX = child.getLeft();
              if (itemId == -1) {
                if (position < headerViewsCount2) {
                  headerViewsCount = headerViewsCount2;
                  top3 = top;
                  height2 = height;
                  itemId = position + 1;
                } else {
                  headerViewsCount = headerViewsCount2;
                  top3 = top;
                  height2 = height;
                  if (position >= adapterCount2 - footerViewsCount) {
                    int footerId = ((position + footerViewsCount) - adapterCount2) + 1;
                    itemId = -footerId;
                  }
                }
                viewInfo =
                    SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.remove(
                        Long.valueOf(itemId));
              } else {
                headerViewsCount = headerViewsCount2;
                top3 = top;
                height2 = height;
                viewInfo =
                    SemAddDeleteHorizontalListAnimator.this.mOldViewCache.remove(
                        Long.valueOf(itemId));
              }
              if (viewInfo != null) {
                viewInfo.recycleBitmap();
                newItemsComingFromLeft = false;
                if (viewInfo.left == newX) {
                  translationX5 = translationX6;
                  i4++;
                  listview3 = listview4;
                  childCountAfter3 = childCountAfter4;
                  headerViewsCount2 = headerViewsCount;
                  top = top3;
                  height = height2;
                } else {
                  translationX4 = viewInfo.left - newX;
                }
              } else {
                if (newItemsFromLeftRemaining > 0 && newItemsComingFromLeft) {
                  rowShift = -newItemsComingFromLeftCount;
                  newItemsFromLeftRemaining--;
                } else {
                  rowShift = newlyAppearingViewOldPositionFromRight - position;
                  newlyAppearingViewOldPositionFromRight++;
                }
                if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                  oldX = child.getLeft() - (rowShift * singleItemWidth2);
                } else {
                  int oldX2 = child.getLeft();
                  oldX = oldX2 + (rowShift * singleItemWidth2);
                }
                translationX4 = oldX - newX;
              }
              animations.add(
                  SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(
                      child, translationX4, 0.0f));
              translationX5 = translationX4;
              i4++;
              listview3 = listview4;
              childCountAfter3 = childCountAfter4;
              headerViewsCount2 = headerViewsCount;
              top = top3;
              height = height2;
            }
            SemHorizontalListView listview5 = listview3;
            int childCountAfter5 = childCountAfter3;
            int top4 = top;
            int height3 = height;
            Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator =
                SemAddDeleteHorizontalListAnimator.this.mOldViewCache.entrySet().iterator();
            boolean updateListenerAdded = false;
            while (entrySetIterator.hasNext()) {
              Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> oldViewCoordinate =
                  entrySetIterator.next();
              SemAbsAddDeleteAnimator.ViewInfo viewInfo2 = oldViewCoordinate.getValue();
              SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.add(viewInfo2);
              Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator2 =
                  entrySetIterator;
              int footerViewsCount2 = footerViewsCount;
              int footerViewsCount3 = top4;
              Rect startValue =
                  new Rect(viewInfo2.left, footerViewsCount3, viewInfo2.right, top4 + height3);
              int newPosition =
                  SemAddDeleteHorizontalListAnimator.this.getNewPosition(
                      viewInfo2.oldPosition, deletedItems);
              boolean isDeletedItem =
                  deletedItemPosHash.contains(Integer.valueOf(viewInfo2.oldPosition));
              int destinationViewIndex = newPosition - firstVisiblePosAfter;
              int firstVisiblePosAfter2 = firstVisiblePosAfter;
              if (destinationViewIndex >= 0) {
                childCountAfter = childCountAfter5;
                if (destinationViewIndex >= childCountAfter) {
                  top2 = footerViewsCount3;
                  listview = listview5;
                  adapterCount = adapterCount2;
                } else {
                  listview = listview5;
                  top2 = footerViewsCount3;
                  int top5 = listview.getChildAt(destinationViewIndex).getLeft();
                  float referenceX = top5;
                  adapterCount = adapterCount2;
                  childCountAfter2 = childCountAfter;
                  translationX = referenceX - viewInfo2.left;
                  Rect endValue = new Rect(startValue);
                  endValue.offset((int) translationX, 0);
                  if (isDeletedItem) {
                    listview2 = listview;
                    translationX2 = translationX;
                    singleItemWidth = singleItemWidth2;
                  } else {
                    int horizOffset =
                        (int)
                            (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f)
                                * endValue.width());
                    listview2 = listview;
                    int vertOffset =
                        (int)
                            (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f)
                                * endValue.height());
                    translationX2 = translationX;
                    int i5 = endValue.left + horizOffset;
                    singleItemWidth = singleItemWidth2;
                    int singleItemWidth3 = endValue.top;
                    endValue =
                        new Rect(
                            i5,
                            singleItemWidth3 + vertOffset,
                            endValue.right - horizOffset,
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
                    anim.addUpdateListener(
                        SemAddDeleteHorizontalListAnimator.this.mBitmapUpdateListener);
                    updateListenerAdded = true;
                  }
                  animations.add(anim);
                  entrySetIterator = entrySetIterator2;
                  adapterCount2 = adapterCount;
                  footerViewsCount = footerViewsCount2;
                  firstVisiblePosAfter = firstVisiblePosAfter2;
                  top4 = top2;
                  childCountAfter5 = childCountAfter2;
                  listview5 = listview2;
                  singleItemWidth2 = singleItemWidth;
                }
              } else {
                top2 = footerViewsCount3;
                childCountAfter = childCountAfter5;
                listview = listview5;
                adapterCount = adapterCount2;
              }
              if (childCountAfter == 0) {
                if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                  childCountAfter2 = childCountAfter;
                  firstChildLeft = listview.getWidth() - (viewInfo2.right - viewInfo2.left);
                } else {
                  childCountAfter2 = childCountAfter;
                  firstChildLeft = listview.getPaddingLeft();
                }
              } else {
                childCountAfter2 = childCountAfter;
                firstChildLeft = listview.getChildAt(0).getLeft();
              }
              float translationX7 = firstChildLeft - viewInfo2.left;
              if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                translationX3 = translationX7 + ((-destinationViewIndex) * singleItemWidth2);
              } else {
                translationX3 = translationX7 - ((-destinationViewIndex) * singleItemWidth2);
              }
              translationX = translationX3;
              Rect endValue2 = new Rect(startValue);
              endValue2.offset((int) translationX, 0);
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
              adapterCount2 = adapterCount;
              footerViewsCount = footerViewsCount2;
              firstVisiblePosAfter = firstVisiblePosAfter2;
              top4 = top2;
              childCountAfter5 = childCountAfter2;
              listview5 = listview2;
              singleItemWidth2 = singleItemWidth;
            }
            SemAddDeleteHorizontalListAnimator.this.mOldViewCache.clear();
            SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.clear();
            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(animations);
            animSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                                                // com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.2.1
                  @Override // android.animation.AnimatorListenerAdapter,
                            // android.animation.Animator.AnimatorListener
                  public void onAnimationStart(Animator animation) {
                    SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(false);
                    if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                      SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationStart(
                          false);
                    }
                  }

                  @Override // android.animation.AnimatorListenerAdapter,
                            // android.animation.Animator.AnimatorListener
                  public void onAnimationEnd(Animator animation) {
                    SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                    SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                    SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                    if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                      SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationEnd(
                          false);
                    }
                  }

                  @Override // android.animation.AnimatorListenerAdapter,
                            // android.animation.Animator.AnimatorListener
                  public void onAnimationCancel(Animator animation) {
                    Log.m94d(SemAddDeleteHorizontalListAnimator.TAG, "onAnimationCancel #1");
                    SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                    SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                    SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                  }
                });
            animSet.setInterpolator(SemAbsAddDeleteAnimator.DELETE_INTERPOLATOR);
            animSet.setDuration(SemAddDeleteHorizontalListAnimator.this.mTranslationDuration);
            animSet.start();
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
          this.mOldHeaderFooterViewCache.put(
              Long.valueOf(itemId),
              new SemAbsAddDeleteAnimator.ViewInfo(
                  snapshot, position, child.getLeft(), 0, child.getRight(), 0));
        } else {
          listview = listview2;
          adapter = adapter2;
          this.mOldViewCache.put(
              Long.valueOf(itemId2),
              new SemAbsAddDeleteAnimator.ViewInfo(
                  snapshot, i + firstVisiblePosBefore, child.getLeft(), 0, child.getRight(), 0));
        }
        i++;
        listview2 = listview;
        adapter2 = adapter;
      }
      Log.m96e(TAG, "setDelete() child's one of dimensions is 0, i = " + i);
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
      if (pos >= this.mHorizontalListView.getHeaderViewsCount()
          && pos < adapterCount - this.mHorizontalListView.getFooterViewsCount()
          && (width = this.mHorizontalListView.getChildAt(i).getWidth()) > childWidth) {
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
    this.mHorizontalListView
        .getViewTreeObserver()
        .addOnPreDrawListener(
            new ViewTreeObserver
                .OnPreDrawListener() { // from class:
                                       // com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.3
              @Override // android.view.ViewTreeObserver.OnPreDrawListener
              public boolean onPreDraw() {
                SemAddDeleteHorizontalListAnimator.this
                    .mHorizontalListView
                    .getViewTreeObserver()
                    .removeOnPreDrawListener(this);
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
            this.mOldHeaderFooterViewCache.put(
                Long.valueOf(-footerId),
                new SemAbsAddDeleteAnimator.ViewInfo(
                    snapshot, position, child.getLeft(), 0, child.getRight(), 0));
          }
        } else {
          childCount = childCount2;
          adapterCount = adapterCount2;
          this.mOldViewCache.put(
              Long.valueOf(itemId),
              new SemAbsAddDeleteAnimator.ViewInfo(
                  snapshot, position, child.getLeft(), 0, child.getRight(), 0));
        }
        i2++;
        childCount2 = childCount;
        adapterCount2 = adapterCount;
        i = 1;
      }
      Log.m96e(TAG, "setInsert() child's one of dimensions is 0, i = " + i2);
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
            Integer.valueOf(insertedItemPos), Integer.valueOf(refView.getLeft()));
      }
    }
    this.mInsertRunnable =
        new Runnable() { // from class:
          // com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.4
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
            SemHorizontalListView listview2 =
                SemAddDeleteHorizontalListAnimator.this.mHorizontalListView;
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
                SemAbsAddDeleteAnimator.ViewInfo viewInfo =
                    SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.remove(
                        Long.valueOf(-footerId2));
                if (viewInfo == null) {
                  Log.m96e(
                      SemAddDeleteHorizontalListAnimator.TAG,
                      "AFTER header/footer SOMETHING WENT WRONG, in the new layout, header/footer"
                          + " is appearing that was not present before!");
                } else {
                  viewInfo.recycleBitmap();
                  if (viewInfo.left == newX2) {
                    Log.m96e(
                        SemAddDeleteHorizontalListAnimator.TAG,
                        "AFTER header/footer something strange is happening, the coordinates are"
                            + " same after layout, viewInfo.left="
                            + viewInfo.left
                            + ", newX="
                            + newX2);
                  } else {
                    float translationX3 = viewInfo.left - newX2;
                    ObjectAnimator anim2 =
                        SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(
                            child2, translationX3, 0.0f);
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
                Integer startPos =
                    (Integer) upcomingViewsStartCoords.remove(Integer.valueOf(position2));
                SemAbsAddDeleteAnimator.ViewInfo viewInfo2 =
                    SemAddDeleteHorizontalListAnimator.this.mOldViewCache.remove(
                        Long.valueOf(itemId2));
                if (viewInfo2 != null) {
                  viewInfo2.recycleBitmap();
                  if (viewInfo2.left != newX2) {
                    translationX = viewInfo2.left - newX2;
                    ObjectAnimator anim3 =
                        SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(
                            child2, translationX, 0.0f);
                    animations.add(anim3);
                    firstVisiblePos2 = firstVisiblePos3;
                  }
                  firstVisiblePos2 = firstVisiblePos3;
                  translationX = translationX2;
                } else if (startPos != null) {
                  translationX = startPos.intValue() - newX2;
                  ObjectAnimator anim4 =
                      SemAddDeleteHorizontalListAnimator.this.getInsertTranslateAlphaScaleAnim(
                          child2, translationX, 0.0f);
                  animations.add(anim4);
                  firstVisiblePos2 = firstVisiblePos3;
                } else {
                  int currentPos = i3 + firstVisiblePos3;
                  int shiftCount =
                      SemAddDeleteHorizontalListAnimator.this.getShiftCount(
                          currentPos, insertedItems);
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
                    anim =
                        SemAddDeleteHorizontalListAnimator.this.getInsertTranslateAlphaScaleAnim(
                            child2, translationX4, 0.0f);
                  } else {
                    anim =
                        SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(
                            child2, translationX4, 0.0f);
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
            Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator =
                SemAddDeleteHorizontalListAnimator.this.mOldViewCache.entrySet().iterator();
            int lastVisiblePosition = listview2.getLastVisiblePosition();
            boolean updateListenerAdded = false;
            int currentPos3 = lastVisiblePosition;
            while (entrySetIterator.hasNext()) {
              currentPos3++;
              if (!insertedItems.contains(Integer.valueOf(currentPos3))) {
                Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> viewEntry =
                    entrySetIterator.next();
                SemAbsAddDeleteAnimator.ViewInfo viewinfo = viewEntry.getValue();
                int newPosition =
                    SemAddDeleteHorizontalListAnimator.this.getNewPositionForInsert(
                        viewinfo.oldPosition, insertedItems);
                if (newPosition < listview2.getFirstVisiblePosition()) {
                  currentPos3--;
                  int rowShift2 = listview2.getFirstVisiblePosition() - newPosition;
                  int childLeft =
                      top2 != 0 ? listview2.getChildAt(0).getLeft() : listview2.getLeft();
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
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> entrySetIterator2 =
                    entrySetIterator;
                int lastVisiblePosition2 = lastVisiblePosition;
                int childCount3 = top2;
                int childCount4 = currentPos2;
                Rect oldViewBounds =
                    new Rect(viewinfo.left, childCount4, viewinfo.right, currentPos2 + height3);
                Rect newViewBounds =
                    new Rect(
                        newX,
                        childCount4,
                        oldViewBounds.width() + newX,
                        oldViewBounds.height() + childCount4);
                SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.add(viewinfo);
                int currentPos4 = currentPos3;
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
                      SemAddDeleteHorizontalListAnimator.this.mBitmapUpdateListener);
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
            for (Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo> viewEntry2 :
                SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.entrySet()) {
              SemAbsAddDeleteAnimator.ViewInfo viewinfo2 = viewEntry2.getValue();
              int newX3 = viewinfo2.left + (insertedItems.size() * singleItemWidth);
              Rect oldViewBounds2 =
                  new Rect(viewinfo2.left, childCount5, viewinfo2.right, childCount5 + height3);
              Rect newViewBounds2 =
                  new Rect(
                      newX3,
                      childCount5,
                      oldViewBounds2.width() + newX3,
                      oldViewBounds2.height() + childCount5);
              SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.add(viewinfo2);
              ObjectAnimator animBounds2 =
                  ObjectAnimator.ofObject(
                      viewinfo2.viewSnapshot,
                      "bounds",
                      SemAnimatorUtils.BOUNDS_EVALUATOR,
                      oldViewBounds2,
                      newViewBounds2);
              if (!updateListenerAdded) {
                animBounds2.addUpdateListener(
                    SemAddDeleteHorizontalListAnimator.this.mBitmapUpdateListener);
              }
              animations.add(animBounds2);
            }
            SemAddDeleteHorizontalListAnimator.this.mOldViewCache.clear();
            SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.clear();
            AnimatorSet animSet = new AnimatorSet();
            animSet.playTogether(animations);
            animSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
            animSet.addListener(
                new AnimatorListenerAdapter() { // from class:
                  // com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.4.1
                  @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
                  public void onAnimationStart(Animator animation) {
                    SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(false);
                    if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                      SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationStart(
                          true);
                    }
                  }

                  @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
                  public void onAnimationEnd(Animator animation) {
                    SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                    SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                    SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                    if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                      SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationEnd(
                          true);
                    }
                  }

                  @Override // android.animation.AnimatorListenerAdapter,
                  // android.animation.Animator.AnimatorListener
                  public void onAnimationCancel(Animator animation) {
                    Log.m94d(SemAddDeleteHorizontalListAnimator.TAG, "onAnimationCancel #2");
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
      throw new IllegalStateException(
          "Adapter need to be set before performing add/delete operations.");
    }
    if (!adapter.hasStableIds()) {
      throw new IllegalStateException(
          "TwAddDeleteListAnimator requires an adapter that has stable ids");
    }
    if (this.mOnAddDeleteListener == null) {
      throw new IllegalStateException(
          "OnAddDeleteListener need to be supplied before performing add/delete operations");
    }
  }
}
