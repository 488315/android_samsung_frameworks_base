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
import com.samsung.android.animation.SemAbsAddDeleteAnimator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes6.dex */
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

    public SemAddDeleteGridAnimator(Context context, GridView gridView) {
        this.mGridView = gridView;
        gridView.setAddDeleteGridAnimator(this);
        this.mHostView = gridView;
    }

    public void setOnAddDeleteListener(OnAddDeleteListener onAddDeleteListener) {
        this.mOnAddDeleteListener = onAddDeleteListener;
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setDelete(ArrayList<Integer> arrayList) {
        if (arrayList.size() == 0) {
            return;
        }
        prepareDelete(arrayList);
        this.mOnAddDeleteListener.onDelete();
        deleteFromAdapterCompleted();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setDeletePending(ArrayList<Integer> arrayList) {
        if (arrayList.size() == 0) {
            return;
        }
        prepareDelete(arrayList);
        this.mOnAddDeleteListener.onDelete();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void deleteFromAdapterCompleted() {
        if (!this.mDeletePending) {
            throw new SemAbsAddDeleteAnimator.SetDeletePendingIsNotCalledBefore();
        }
        this.mDeletePending = false;
        this.mGridView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteGridAnimator.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteGridAnimator.this.mGridView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteGridAnimator.this.mDeleteRunnable == null) {
                    return true;
                }
                SemAddDeleteGridAnimator.this.mDeleteRunnable.run();
                SemAddDeleteGridAnimator.this.mDeleteRunnable = null;
                return true;
            }
        });
    }

    private void prepareDelete(ArrayList<Integer> arrayList) {
        this.mDeletePending = true;
        ensureAdapterAndListener();
        final ArrayList arrayList2 = new ArrayList(arrayList);
        Collections.sort(arrayList2);
        final HashSet hashSet = new HashSet(arrayList2);
        final GridView gridView = this.mGridView;
        final ListAdapter adapter = gridView.getAdapter();
        int childCount = gridView.getChildCount();
        final int firstVisiblePosition = gridView.getFirstVisiblePosition();
        final int lastVisiblePosition = gridView.getLastVisiblePosition();
        for (int i = 0; i < childCount; i++) {
            View childAt = gridView.getChildAt(i);
            int i2 = i + firstVisiblePosition;
            this.mOldViewCache.put(Long.valueOf(adapter.getItemId(i2)), new SemAbsAddDeleteAnimator.ViewInfo(SemAnimatorUtils.getBitmapDrawableFromView(childAt), i2, childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom()));
        }
        final int height = gridView.getChildAt(0).getHeight();
        this.mDeleteRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteGridAnimator.2
            @Override // java.lang.Runnable
            public void run() {
                int i3;
                int paddingLeft;
                float f;
                float f2;
                int i4;
                int i5;
                int i6;
                int left;
                float top;
                boolean z;
                float f3;
                ArrayList arrayList3 = new ArrayList();
                int childCount2 = gridView.getChildCount();
                int firstVisiblePosition2 = gridView.getFirstVisiblePosition();
                int numColumns = gridView.getNumColumns();
                if (childCount2 > numColumns) {
                    i3 = gridView.getChildAt(numColumns).getTop() - gridView.getChildAt(0).getTop();
                } else {
                    i3 = height;
                }
                int i7 = firstVisiblePosition - firstVisiblePosition2;
                int i8 = lastVisiblePosition;
                int i9 = 0;
                int i10 = i7;
                boolean z2 = true;
                while (i9 < childCount2) {
                    View childAt2 = gridView.getChildAt(i9);
                    int i11 = i9 + firstVisiblePosition2;
                    SemAbsAddDeleteAnimator.ViewInfo remove = SemAddDeleteGridAnimator.this.mOldViewCache.remove(Long.valueOf(adapter.getItemId(i11)));
                    float left2 = childAt2.getLeft();
                    float top2 = childAt2.getTop();
                    if (remove != null) {
                        remove.recycleBitmap();
                        if (remove.left == left2 && remove.top == top2) {
                            i4 = firstVisiblePosition2;
                            i6 = i3;
                            z2 = false;
                            i9++;
                            firstVisiblePosition2 = i4;
                            i3 = i6;
                        } else {
                            f3 = remove.left - left2;
                            top = remove.top - top2;
                            i4 = firstVisiblePosition2;
                            i6 = i3;
                            z = false;
                        }
                    } else {
                        if (i10 <= 0 || !z2) {
                            i4 = firstVisiblePosition2;
                            i8 = SemAddDeleteGridAnimator.this.getNextAppearingViewPosition(hashSet, i8);
                            i5 = i8;
                        } else {
                            i5 = i11 - i7;
                            i10--;
                            i4 = firstVisiblePosition2;
                        }
                        int i12 = i3;
                        int floor = ((int) Math.floor(i5 / numColumns)) - (i11 / numColumns);
                        int i13 = i5 % numColumns;
                        if (i13 < 0) {
                            i13 += numColumns;
                        }
                        if (childCount2 > i13) {
                            left = gridView.getChildAt(i13).getLeft();
                            i6 = i12;
                        } else {
                            i6 = i12;
                            left = gridView.getChildAt(0).getLeft() + (i13 * gridView.getChildAt(0).getWidth());
                        }
                        top = (childAt2.getTop() + (floor * i6)) - top2;
                        z = z2;
                        f3 = left - left2;
                    }
                    arrayList3.add(SemAddDeleteGridAnimator.this.getTranslateAnim(childAt2, f3, top));
                    z2 = z;
                    i9++;
                    firstVisiblePosition2 = i4;
                    i3 = i6;
                }
                int i14 = firstVisiblePosition2;
                int i15 = i3;
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it = SemAddDeleteGridAnimator.this.mOldViewCache.entrySet().iterator();
                boolean z3 = false;
                while (it.hasNext()) {
                    SemAbsAddDeleteAnimator.ViewInfo value = it.next().getValue();
                    SemAddDeleteGridAnimator.this.mGhostViewSnapshots.add(value);
                    Rect rect = new Rect(value.left, value.top, value.right, value.bottom);
                    int newPosition = SemAddDeleteGridAnimator.this.getNewPosition(value.oldPosition, arrayList2);
                    boolean contains = hashSet.contains(Integer.valueOf(value.oldPosition));
                    int i16 = newPosition - i14;
                    if (i16 < 0 || i16 >= childCount2) {
                        int i17 = newPosition % numColumns;
                        if (childCount2 > i17) {
                            paddingLeft = gridView.getChildAt(i17).getLeft();
                        } else {
                            paddingLeft = gridView.getPaddingLeft();
                        }
                        float f4 = paddingLeft;
                        f = value.top - (((value.oldPosition / numColumns) - (newPosition / numColumns)) * i15);
                        f2 = f4;
                    } else {
                        f2 = gridView.getChildAt(i16).getLeft();
                        f = gridView.getChildAt(i16).getTop();
                    }
                    Rect rect2 = new Rect(rect);
                    rect2.offset((int) (f2 - value.left), (int) (f - value.top));
                    if (contains) {
                        int width = (int) (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f) * rect2.width());
                        int height2 = (int) (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f) * rect2.height());
                        rect2 = new Rect(rect2.left + width, rect2.top + height2, rect2.right - width, rect2.bottom - height2);
                    }
                    ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(value.viewSnapshot, PropertyValuesHolder.ofObject("bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, rect2), PropertyValuesHolder.ofInt("alpha", 255, 0));
                    if (!z3) {
                        ofPropertyValuesHolder.addUpdateListener(SemAddDeleteGridAnimator.this.mBitmapUpdateListener);
                        z3 = true;
                    }
                    arrayList3.add(ofPropertyValuesHolder);
                }
                SemAddDeleteGridAnimator.this.mOldViewCache.clear();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList3);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAddDeleteGridAnimator.2.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(false);
                        if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteGridAnimator.this.mOnAddDeleteListener.onAnimationStart(false);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteGridAnimator.this.mGridView.invalidate();
                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                        if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteGridAnimator.this.mOnAddDeleteListener.onAnimationEnd(false);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Log.d(SemAddDeleteGridAnimator.TAG, "onAnimationCancel #1");
                        SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteGridAnimator.this.mGridView.invalidate();
                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                    }
                });
                animatorSet.setInterpolator(SemAbsAddDeleteAnimator.DELETE_INTERPOLATOR);
                animatorSet.setDuration(SemAddDeleteGridAnimator.this.mTranslationDuration);
                animatorSet.start();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getNextAppearingViewPosition(HashSet<Integer> hashSet, int i) {
        do {
            i++;
        } while (hashSet.contains(Integer.valueOf(i)));
        return i;
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setInsert(ArrayList<Integer> arrayList) {
        if (arrayList.size() == 0) {
            return;
        }
        prepareInsert(arrayList);
        this.mOnAddDeleteListener.onAdd();
        insertIntoAdapterCompleted();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setInsertPending(ArrayList<Integer> arrayList) {
        if (arrayList.size() == 0) {
            return;
        }
        prepareInsert(arrayList);
        this.mOnAddDeleteListener.onAdd();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void insertIntoAdapterCompleted() {
        if (!this.mInsertPending) {
            throw new SemAbsAddDeleteAnimator.SetInsertPendingIsNotCalledBefore();
        }
        this.mInsertPending = false;
        this.mGridView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteGridAnimator.3
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteGridAnimator.this.mGridView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteGridAnimator.this.mInsertRunnable == null) {
                    return true;
                }
                SemAddDeleteGridAnimator.this.mInsertRunnable.run();
                SemAddDeleteGridAnimator.this.mInsertRunnable = null;
                return true;
            }
        });
    }

    private void prepareInsert(final ArrayList<Integer> arrayList) {
        boolean z = true;
        this.mInsertPending = true;
        ensureAdapterAndListener();
        Collections.sort(arrayList);
        final HashSet hashSet = new HashSet(arrayList);
        GridView gridView = this.mGridView;
        final ListAdapter adapter = gridView.getAdapter();
        int childCount = gridView.getChildCount();
        int firstVisiblePosition = gridView.getFirstVisiblePosition();
        int i = 0;
        while (i < childCount) {
            View childAt = gridView.getChildAt(i);
            int i2 = i + firstVisiblePosition;
            this.mOldViewCache.put(Long.valueOf(adapter.getItemId(i2)), new SemAbsAddDeleteAnimator.ViewInfo(SemAnimatorUtils.getBitmapDrawableFromView(childAt), i2, childAt.getLeft(), childAt.getTop(), childAt.getRight(), childAt.getBottom()));
            i++;
            z = z;
        }
        boolean z2 = z;
        final HashMap hashMap = new HashMap();
        for (int i3 = 0; i3 < arrayList.size(); i3++) {
            Integer num = arrayList.get(i3);
            View childAt2 = gridView.getChildAt((num.intValue() - i3) - firstVisiblePosition);
            if (childAt2 != null) {
                float left = childAt2.getLeft();
                float top = childAt2.getTop();
                float[] fArr = new float[2];
                fArr[0] = left;
                fArr[z2 ? 1 : 0] = top;
                hashMap.put(num, fArr);
            }
        }
        this.mInsertRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteGridAnimator.4
            @Override // java.lang.Runnable
            public void run() {
                ObjectAnimator translateAnim;
                GridView gridView2 = SemAddDeleteGridAnimator.this.mGridView;
                int firstVisiblePosition2 = gridView2.getFirstVisiblePosition();
                int childCount2 = gridView2.getChildCount();
                ArrayList arrayList2 = new ArrayList();
                int numColumns = gridView2.getNumColumns();
                boolean z3 = false;
                int top2 = childCount2 > numColumns ? gridView2.getChildAt(numColumns).getTop() - gridView2.getChildAt(0).getTop() : 0;
                int i4 = 0;
                while (i4 < childCount2) {
                    int i5 = i4 + firstVisiblePosition2;
                    long itemId = adapter.getItemId(i5);
                    View childAt3 = gridView2.getChildAt(i4);
                    float[] fArr2 = (float[]) hashMap.get(Integer.valueOf(i5));
                    float left2 = childAt3.getLeft();
                    boolean z4 = z3;
                    float top3 = childAt3.getTop();
                    SemAbsAddDeleteAnimator.ViewInfo remove = SemAddDeleteGridAnimator.this.mOldViewCache.remove(Long.valueOf(itemId));
                    if (remove != null) {
                        remove.recycleBitmap();
                        if (remove.left != left2 || remove.top != top3) {
                            arrayList2.add(SemAddDeleteGridAnimator.this.getTranslateAnim(childAt3, remove.left - left2, remove.top - top3));
                        }
                    } else if (fArr2 != null) {
                        arrayList2.add(SemAddDeleteGridAnimator.this.getInsertTranslateAlphaScaleAnim(childAt3, fArr2[z4 ? 1 : 0] - left2, fArr2[1] - top3));
                    } else {
                        int shiftCount = (i5 / numColumns) - ((i5 - SemAddDeleteGridAnimator.this.getShiftCount(i5, arrayList)) / numColumns);
                        float left3 = gridView2.getChildAt(r9 % numColumns).getLeft() - left2;
                        float top4 = (childAt3.getTop() - (shiftCount * top2)) - top3;
                        if (hashSet.contains(Integer.valueOf(i5))) {
                            translateAnim = SemAddDeleteGridAnimator.this.getInsertTranslateAlphaScaleAnim(childAt3, left3, top4);
                        } else {
                            translateAnim = SemAddDeleteGridAnimator.this.getTranslateAnim(childAt3, left3, top4);
                        }
                        arrayList2.add(translateAnim);
                    }
                    i4++;
                    z3 = z4 ? 1 : 0;
                }
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it = SemAddDeleteGridAnimator.this.mOldViewCache.entrySet().iterator();
                int lastVisiblePosition = gridView2.getLastVisiblePosition();
                while (it.hasNext()) {
                    lastVisiblePosition++;
                    if (!arrayList.contains(Integer.valueOf(lastVisiblePosition))) {
                        SemAbsAddDeleteAnimator.ViewInfo value = it.next().getValue();
                        int i6 = (lastVisiblePosition / numColumns) - (value.oldPosition / numColumns);
                        float left4 = gridView2.getChildAt(lastVisiblePosition % numColumns).getLeft();
                        float f = value.top + (i6 * top2);
                        Rect rect = new Rect(value.left, value.top, value.right, value.bottom);
                        int i7 = (int) f;
                        Rect rect2 = new Rect((int) left4, i7, (int) (left4 + rect.width()), rect.height() + i7);
                        SemAddDeleteGridAnimator.this.mGhostViewSnapshots.add(value);
                        ObjectAnimator ofObject = ObjectAnimator.ofObject(value.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, rect2);
                        arrayList2.add(ofObject);
                        if (!z3) {
                            ofObject.addUpdateListener(SemAddDeleteGridAnimator.this.mBitmapUpdateListener);
                            z3 = true;
                        }
                    }
                }
                SemAddDeleteGridAnimator.this.mOldViewCache.clear();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList2);
                animatorSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAddDeleteGridAnimator.4.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(false);
                        if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteGridAnimator.this.mOnAddDeleteListener.onAnimationStart(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteGridAnimator.this.mGridView.invalidate();
                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                        if (SemAddDeleteGridAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteGridAnimator.this.mOnAddDeleteListener.onAnimationEnd(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Log.d(SemAddDeleteGridAnimator.TAG, "onAnimationCancel #2");
                        SemAddDeleteGridAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteGridAnimator.this.mGridView.invalidate();
                        SemAddDeleteGridAnimator.this.mGridView.setEnabled(true);
                    }
                });
                animatorSet.setDuration(SemAddDeleteGridAnimator.this.mTranslationDuration);
                animatorSet.start();
            }
        };
    }

    private void ensureAdapterAndListener() {
        ListAdapter adapter = this.mGridView.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("Adapter need to be set before performing add/delete operations.");
        }
        if (!adapter.hasStableIds()) {
            throw new IllegalStateException("SemAddDeleteGridAnimator requires an adapter that has stable ids");
        }
        if (this.mOnAddDeleteListener == null) {
            throw new IllegalStateException("OnAddDeleteListener need to be supplied before performing add/delete operations");
        }
    }
}
