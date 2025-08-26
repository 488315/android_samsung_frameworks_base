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
import com.samsung.android.animation.SemAbsAddDeleteAnimator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

@Deprecated
/* loaded from: classes6.dex */
public class SemAddDeleteListAnimator extends SemAbsAddDeleteAnimator {
    private static String TAG = "SemAddDeleteListAnimator";
    private ListView mListView;
    private OnAddDeleteListener mOnAddDeleteListener;
    LinkedHashMap<Long, SemAbsAddDeleteAnimator.ViewInfo> mOldViewCache = new LinkedHashMap<>();
    LinkedHashMap<Long, SemAbsAddDeleteAnimator.ViewInfo> mOldHeaderFooterViewCache = new LinkedHashMap<>();
    private boolean mInsertPending = false;
    private boolean mDeletePending = false;
    private boolean mInsertDeletePending = false;
    private boolean mIsInsertDelete = false;
    private final int EXTRA_ANIM_TIMEOUT_DUTAION = 100;
    private final Handler mHandler = new Handler();
    private final Runnable mAniTimeoutRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.1
        @Override // java.lang.Runnable
        public void run() {
            Log.i(SemAddDeleteListAnimator.TAG, "mAniTimeoutRunnable.run");
            if (SemAddDeleteListAnimator.this.mGhostViewSnapshots.size() > 0) {
                Iterator<SemAbsAddDeleteAnimator.ViewInfo> it = SemAddDeleteListAnimator.this.mGhostViewSnapshots.iterator();
                while (it.hasNext()) {
                    it.next().recycleBitmap();
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

    public SemAddDeleteListAnimator(Context context, ListView listView) {
        this.mListView = listView;
        listView.setAddDeleteListAnimator(this);
        this.mHostView = listView;
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
        OnAddDeleteListener onAddDeleteListener = this.mOnAddDeleteListener;
        if (onAddDeleteListener != null) {
            onAddDeleteListener.onDelete();
        }
        deleteFromAdapterCompleted();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setDeletePending(ArrayList<Integer> arrayList) {
        if (arrayList.size() == 0) {
            return;
        }
        prepareDelete(arrayList);
        OnAddDeleteListener onAddDeleteListener = this.mOnAddDeleteListener;
        if (onAddDeleteListener != null) {
            onAddDeleteListener.onDelete();
        }
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void deleteFromAdapterCompleted() {
        if (!this.mDeletePending) {
            throw new SemAbsAddDeleteAnimator.SetDeletePendingIsNotCalledBefore();
        }
        this.mDeletePending = false;
        this.mListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.2
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteListAnimator.this.mListView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteListAnimator.this.mDeleteRunnable == null) {
                    return true;
                }
                SemAddDeleteListAnimator.this.mDeleteRunnable.run();
                SemAddDeleteListAnimator.this.mDeleteRunnable = null;
                return true;
            }
        });
    }

    private void prepareDelete(ArrayList<Integer> arrayList) {
        this.mDeletePending = true;
        final ArrayList arrayList2 = new ArrayList(arrayList);
        ensureAdapterAndListener();
        Collections.sort(arrayList2);
        final HashSet hashSet = new HashSet(arrayList2);
        final int childCount = this.mListView.getChildCount();
        final int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        final ListAdapter adapter = this.mListView.getAdapter();
        capturePreAnimationViewCoordinates();
        this.mDeleteRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.3
            @Override // java.lang.Runnable
            public void run() {
                int width;
                int childMaxHeight;
                int left;
                int top;
                float top2;
                ListView listView;
                int i;
                float f;
                int i2;
                SemAbsAddDeleteAnimator.ViewInfo viewInfoRemove;
                int i3;
                int i4;
                float top3;
                int i5;
                ListView listView2 = SemAddDeleteListAnimator.this.mListView;
                int childCount2 = listView2.getChildCount();
                int firstVisiblePosition2 = listView2.getFirstVisiblePosition();
                int lastVisiblePosition = listView2.getLastVisiblePosition();
                int headerViewsCount = listView2.getHeaderViewsCount();
                int footerViewsCount = listView2.getFooterViewsCount();
                int count = adapter.getCount();
                ArrayList arrayList3 = new ArrayList();
                if (childCount2 > headerViewsCount) {
                    childMaxHeight = SemAddDeleteListAnimator.this.getChildMaxHeight() + listView2.getDividerHeight();
                    left = listView2.getChildAt(headerViewsCount).getLeft();
                    width = listView2.getChildAt(headerViewsCount).getWidth();
                } else {
                    width = listView2.getWidth();
                    childMaxHeight = 0;
                    left = 0;
                }
                int i6 = firstVisiblePosition - firstVisiblePosition2;
                boolean z = true;
                int i7 = lastVisiblePosition + 1 + (childCount - childCount2);
                int i8 = i6;
                boolean z2 = true;
                int i9 = 0;
                while (i9 < childCount2) {
                    boolean z3 = z;
                    View childAt = listView2.getChildAt(i9);
                    int i10 = i9 + firstVisiblePosition2;
                    int i11 = firstVisiblePosition2;
                    long itemId = adapter.getItemId(i10);
                    float top4 = childAt.getTop();
                    if (itemId == -1) {
                        if (i10 < headerViewsCount) {
                            f = top4;
                            i5 = i10 + 1;
                            i2 = i7;
                        } else {
                            f = top4;
                            i2 = i7;
                            if (i10 >= count - footerViewsCount) {
                                i5 = -(((i10 + footerViewsCount) - count) + 1);
                            }
                            viewInfoRemove = SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(itemId));
                        }
                        itemId = i5;
                        viewInfoRemove = SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(itemId));
                    } else {
                        f = top4;
                        i2 = i7;
                        viewInfoRemove = SemAddDeleteListAnimator.this.mOldViewCache.remove(Long.valueOf(itemId));
                    }
                    if (viewInfoRemove != null) {
                        viewInfoRemove.recycleBitmap();
                        if (viewInfoRemove.top == f) {
                            i7 = i2;
                            z2 = false;
                            i9++;
                            z = z3;
                            firstVisiblePosition2 = i11;
                        } else {
                            top3 = viewInfoRemove.top - f;
                            i4 = i2;
                            z2 = false;
                        }
                    } else {
                        if (i8 <= 0 || !z2) {
                            i3 = i2 - i10;
                            i4 = i2 + 1;
                        } else {
                            i3 = -i6;
                            i8--;
                            i4 = i2;
                        }
                        top3 = (childAt.getTop() + (i3 * childMaxHeight)) - f;
                    }
                    arrayList3.add(SemAddDeleteListAnimator.this.getTranslateAnim(childAt, 0.0f, top3));
                    i7 = i4;
                    i9++;
                    z = z3;
                    firstVisiblePosition2 = i11;
                }
                int i12 = firstVisiblePosition2;
                boolean z4 = z;
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it = SemAddDeleteListAnimator.this.mOldViewCache.entrySet().iterator();
                boolean z5 = false;
                while (it.hasNext()) {
                    SemAbsAddDeleteAnimator.ViewInfo value = it.next().getValue();
                    SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(value);
                    Rect rect = new Rect(left, value.top, left + width, value.bottom);
                    int newPosition = SemAddDeleteListAnimator.this.getNewPosition(value.oldPosition, arrayList2);
                    boolean zContains = hashSet.contains(Integer.valueOf(value.oldPosition));
                    int i13 = newPosition - i12;
                    if (i13 < 0 || i13 >= childCount2) {
                        if (childCount2 == 0) {
                            top = listView2.getPaddingTop();
                        } else {
                            top = listView2.getChildAt(0).getTop();
                        }
                        top2 = (top - value.top) - ((-i13) * childMaxHeight);
                    } else {
                        top2 = listView2.getChildAt(i13).getTop() - value.top;
                    }
                    Rect rect2 = new Rect(rect);
                    rect2.offset(0, (int) top2);
                    if (zContains) {
                        int iWidth = (int) (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f) * rect2.width());
                        int iHeight = (int) (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f) * rect2.height());
                        listView = listView2;
                        i = childCount2;
                        rect2 = new Rect(rect2.left + iWidth, rect2.top + iHeight, rect2.right - iWidth, rect2.bottom - iHeight);
                    } else {
                        listView = listView2;
                        i = childCount2;
                    }
                    PropertyValuesHolder propertyValuesHolderOfObject = PropertyValuesHolder.ofObject("bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, rect2);
                    PropertyValuesHolder propertyValuesHolderOfInt = PropertyValuesHolder.ofInt("alpha", 255, 0);
                    BitmapDrawable bitmapDrawable = value.viewSnapshot;
                    PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[2];
                    propertyValuesHolderArr[0] = propertyValuesHolderOfObject;
                    propertyValuesHolderArr[z4 ? 1 : 0] = propertyValuesHolderOfInt;
                    ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, propertyValuesHolderArr);
                    if (!z5) {
                        objectAnimatorOfPropertyValuesHolder.addUpdateListener(SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                        z5 = z4 ? 1 : 0;
                    }
                    arrayList3.add(objectAnimatorOfPropertyValuesHolder);
                    listView2 = listView;
                    childCount2 = i;
                }
                SemAddDeleteListAnimator.this.mOldViewCache.clear();
                SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.clear();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList3);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.3.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationStart #1");
                        if (SemAddDeleteListAnimator.this.mListView.isPressed()) {
                            SemAddDeleteListAnimator.this.mListView.setPressed(false);
                        }
                        SemAddDeleteListAnimator.this.mListView.setEnabled(false);
                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteListAnimator.this.mOnAddDeleteListener.onAnimationStart(false);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationEnd #1");
                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                        if (SemAddDeleteListAnimator.this.mGhostViewSnapshots.size() > 0) {
                            Iterator<SemAbsAddDeleteAnimator.ViewInfo> it2 = SemAddDeleteListAnimator.this.mGhostViewSnapshots.iterator();
                            while (it2.hasNext()) {
                                it2.next().recycleBitmap();
                            }
                        }
                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteListAnimator.this.mListView.invalidate();
                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteListAnimator.this.mOnAddDeleteListener.onAnimationEnd(false);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationCancel #1");
                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                        if (SemAddDeleteListAnimator.this.mGhostViewSnapshots.size() > 0) {
                            Iterator<SemAbsAddDeleteAnimator.ViewInfo> it2 = SemAddDeleteListAnimator.this.mGhostViewSnapshots.iterator();
                            while (it2.hasNext()) {
                                it2.next().recycleBitmap();
                            }
                        }
                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteListAnimator.this.mListView.invalidate();
                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                    }
                });
                animatorSet.setInterpolator(SemAbsAddDeleteAnimator.DELETE_INTERPOLATOR);
                animatorSet.setDuration(SemAddDeleteListAnimator.this.mTranslationDuration);
                animatorSet.start();
                Log.i(SemAddDeleteListAnimator.TAG, "postDelayed #1 mAniTimeoutRunnable delay = " + (SemAddDeleteListAnimator.this.mTranslationDuration + 100));
                SemAddDeleteListAnimator.this.mHandler.postDelayed(SemAddDeleteListAnimator.this.mAniTimeoutRunnable, (long) (SemAddDeleteListAnimator.this.mTranslationDuration + 100));
            }
        };
    }

    private void capturePreAnimationViewCoordinates() {
        ListView listView;
        int i;
        ListView listView2 = this.mListView;
        ListAdapter adapter = listView2.getAdapter();
        int childCount = listView2.getChildCount();
        int firstVisiblePosition = listView2.getFirstVisiblePosition();
        int count = adapter.getCount();
        int headerViewsCount = listView2.getHeaderViewsCount();
        int footerViewsCount = listView2.getFooterViewsCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = listView2.getChildAt(i2);
            int i3 = i2 + firstVisiblePosition;
            long itemId = adapter.getItemId(i3);
            if (childAt.getHeight() == 0 || childAt.getWidth() == 0) {
                listView = listView2;
                Log.e(TAG, "setDelete() child's one of dimensions is 0, i=" + i2);
            } else {
                long j = itemId;
                BitmapDrawable bitmapDrawableFromView = SemAnimatorUtils.getBitmapDrawableFromView(childAt);
                if (j == -1) {
                    if (i3 < headerViewsCount) {
                        i = i3 + 1;
                    } else {
                        if (i3 >= count - footerViewsCount) {
                            i = -(((i3 + footerViewsCount) - count) + 1);
                        }
                        listView = listView2;
                        this.mOldHeaderFooterViewCache.put(Long.valueOf(j), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i3, 0, childAt.getTop(), 0, childAt.getBottom()));
                    }
                    j = i;
                    listView = listView2;
                    this.mOldHeaderFooterViewCache.put(Long.valueOf(j), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i3, 0, childAt.getTop(), 0, childAt.getBottom()));
                } else {
                    listView = listView2;
                    this.mOldViewCache.put(Long.valueOf(j), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i3, 0, childAt.getTop(), 0, childAt.getBottom()));
                }
            }
            i2++;
            listView2 = listView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getChildMaxHeight() {
        int height;
        int childCount = this.mListView.getChildCount();
        int count = this.mListView.getAdapter().getCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            int i3 = i2 + firstVisiblePosition;
            if (i3 >= this.mListView.getHeaderViewsCount() && i3 < count - this.mListView.getFooterViewsCount() && (height = this.mListView.getChildAt(i2).getHeight()) > i) {
                i = height;
            }
        }
        return i;
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setInsert(ArrayList<Integer> arrayList) {
        if (arrayList.size() == 0) {
            return;
        }
        prepareInsert(arrayList);
        OnAddDeleteListener onAddDeleteListener = this.mOnAddDeleteListener;
        if (onAddDeleteListener != null) {
            onAddDeleteListener.onAdd();
        }
        insertIntoAdapterCompleted();
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void setInsertPending(ArrayList<Integer> arrayList) {
        if (arrayList.size() == 0) {
            return;
        }
        prepareInsert(arrayList);
        OnAddDeleteListener onAddDeleteListener = this.mOnAddDeleteListener;
        if (onAddDeleteListener != null) {
            onAddDeleteListener.onAdd();
        }
    }

    @Override // com.samsung.android.animation.SemAbsAddDeleteAnimator
    public void insertIntoAdapterCompleted() {
        if (!this.mInsertPending) {
            throw new SemAbsAddDeleteAnimator.SetInsertPendingIsNotCalledBefore();
        }
        this.mInsertPending = false;
        this.mListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.4
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteListAnimator.this.mListView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteListAnimator.this.mInsertRunnable == null) {
                    return true;
                }
                SemAddDeleteListAnimator.this.mInsertRunnable.run();
                SemAddDeleteListAnimator.this.mInsertRunnable = null;
                return true;
            }
        });
    }

    private void prepareInsert(ArrayList<Integer> arrayList) {
        int i;
        int i2 = 1;
        this.mInsertPending = true;
        ensureAdapterAndListener();
        final ArrayList arrayList2 = new ArrayList(arrayList);
        Collections.sort(arrayList2);
        final HashSet hashSet = new HashSet(arrayList2);
        ListView listView = this.mListView;
        final ListAdapter adapter = listView.getAdapter();
        int childCount = listView.getChildCount();
        int count = adapter.getCount();
        int firstVisiblePosition = listView.getFirstVisiblePosition();
        int footerViewsCount = listView.getFooterViewsCount();
        int i3 = 0;
        while (i3 < childCount) {
            int i4 = i3 + firstVisiblePosition;
            View childAt = listView.getChildAt(i3);
            long itemId = adapter.getItemId(i4);
            if (childAt.getHeight() == 0 || childAt.getWidth() == 0) {
                String str = TAG;
                StringBuilder sb = new StringBuilder("setInsert() child's one of dimensions is 0, i=");
                i3 = i3;
                sb.append(i3);
                Log.e(str, sb.toString());
            } else {
                BitmapDrawable bitmapDrawableFromView = SemAnimatorUtils.getBitmapDrawableFromView(childAt);
                if (itemId != -1) {
                    i = i3;
                    this.mOldViewCache.put(Long.valueOf(itemId), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i4, 0, childAt.getTop(), 0, childAt.getBottom()));
                } else if (i4 >= count - footerViewsCount) {
                    i = i3;
                    this.mOldHeaderFooterViewCache.put(Long.valueOf(-(((i4 + footerViewsCount) - count) + i2)), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i4, 0, childAt.getTop(), 0, childAt.getBottom()));
                }
                i3 = i;
            }
            i3++;
            i2 = 1;
        }
        final HashMap map = new HashMap();
        for (int i5 = 0; i5 < arrayList2.size(); i5++) {
            Integer num = (Integer) arrayList2.get(i5);
            View childAt2 = listView.getChildAt((num.intValue() - i5) - firstVisiblePosition);
            if (childAt2 != null) {
                map.put(num, Integer.valueOf(childAt2.getTop()));
            }
        }
        this.mInsertRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.5
            @Override // java.lang.Runnable
            public void run() {
                int width;
                int left;
                int childMaxHeight;
                int top;
                int i6;
                ObjectAnimator translateAnim;
                ListView listView2 = SemAddDeleteListAnimator.this.mListView;
                int firstVisiblePosition2 = listView2.getFirstVisiblePosition();
                int headerViewsCount = listView2.getHeaderViewsCount();
                int footerViewsCount2 = listView2.getFooterViewsCount();
                int childCount2 = listView2.getChildCount();
                int count2 = adapter.getCount();
                ArrayList arrayList3 = new ArrayList();
                if (childCount2 > headerViewsCount) {
                    childMaxHeight = SemAddDeleteListAnimator.this.getChildMaxHeight() + listView2.getDividerHeight();
                    left = listView2.getChildAt(headerViewsCount).getLeft();
                    width = listView2.getChildAt(0).getWidth();
                } else {
                    width = listView2.getWidth();
                    left = 0;
                    childMaxHeight = 0;
                }
                int i7 = 0;
                while (i7 < childCount2) {
                    int i8 = i7 + firstVisiblePosition2;
                    long itemId2 = adapter.getItemId(i8);
                    View childAt3 = listView2.getChildAt(i7);
                    float top2 = childAt3.getTop();
                    int i9 = firstVisiblePosition2;
                    if (itemId2 == -1) {
                        SemAbsAddDeleteAnimator.ViewInfo viewInfoRemove = SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(-(((i8 + footerViewsCount2) - count2) + 1)));
                        if (viewInfoRemove == null) {
                            Log.e(SemAddDeleteListAnimator.TAG, "AFTER header/footer SOMETHING WENT WRONG, in the new layout, header/footer is appearing that was not present before!");
                        } else {
                            viewInfoRemove.recycleBitmap();
                            if (viewInfoRemove.top == top2) {
                                Log.e(SemAddDeleteListAnimator.TAG, "AFTER header/footer something strange is happening, the coordinates are same after layout, viewInfo.top=" + viewInfoRemove.top + ", newY=" + top2);
                            } else {
                                arrayList3.add(SemAddDeleteListAnimator.this.getTranslateAnim(childAt3, 0.0f, viewInfoRemove.top - top2));
                            }
                        }
                        i6 = footerViewsCount2;
                    } else {
                        i6 = footerViewsCount2;
                        Integer num2 = (Integer) map.remove(Integer.valueOf(i8));
                        SemAbsAddDeleteAnimator.ViewInfo viewInfoRemove2 = SemAddDeleteListAnimator.this.mOldViewCache.remove(Long.valueOf(itemId2));
                        if (viewInfoRemove2 != null) {
                            viewInfoRemove2.recycleBitmap();
                            if (viewInfoRemove2.top != top2) {
                                arrayList3.add(SemAddDeleteListAnimator.this.getTranslateAnim(childAt3, 0.0f, viewInfoRemove2.top - top2));
                            }
                        } else if (num2 != null) {
                            arrayList3.add(SemAddDeleteListAnimator.this.getInsertTranslateAlphaScaleAnim(childAt3, 0.0f, num2.intValue() - top2));
                        } else {
                            float top3 = (childAt3.getTop() - ((i8 - (i8 - SemAddDeleteListAnimator.this.getShiftCount(i8, arrayList2))) * childMaxHeight)) - top2;
                            if (hashSet.contains(Integer.valueOf(i8))) {
                                translateAnim = SemAddDeleteListAnimator.this.getInsertTranslateAlphaScaleAnim(childAt3, 0.0f, top3);
                            } else {
                                translateAnim = SemAddDeleteListAnimator.this.getTranslateAnim(childAt3, 0.0f, top3);
                            }
                            arrayList3.add(translateAnim);
                        }
                    }
                    i7++;
                    firstVisiblePosition2 = i9;
                    footerViewsCount2 = i6;
                }
                map.clear();
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it = SemAddDeleteListAnimator.this.mOldViewCache.entrySet().iterator();
                int lastVisiblePosition = listView2.getLastVisiblePosition();
                boolean z = false;
                while (it.hasNext()) {
                    int i10 = lastVisiblePosition + 1;
                    if (arrayList2.contains(Integer.valueOf(i10))) {
                        lastVisiblePosition = i10;
                    } else {
                        SemAbsAddDeleteAnimator.ViewInfo value = it.next().getValue();
                        int newPositionForInsert = SemAddDeleteListAnimator.this.getNewPositionForInsert(value.oldPosition, arrayList2);
                        if (newPositionForInsert < listView2.getFirstVisiblePosition()) {
                            top = listView2.getChildAt(0).getTop() - ((listView2.getFirstVisiblePosition() - newPositionForInsert) * childMaxHeight);
                        } else {
                            top = value.top + ((i10 - value.oldPosition) * childMaxHeight);
                            lastVisiblePosition = i10;
                        }
                        Rect rect = new Rect(left, value.top, left + width, value.bottom);
                        Rect rect2 = new Rect(left, top, rect.width() + left, rect.height() + top);
                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(value);
                        ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(value.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, rect2);
                        arrayList3.add(objectAnimatorOfObject);
                        if (!z) {
                            objectAnimatorOfObject.addUpdateListener(SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                            z = true;
                        }
                    }
                }
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it2 = SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.entrySet().iterator();
                while (it2.hasNext()) {
                    SemAbsAddDeleteAnimator.ViewInfo value2 = it2.next().getValue();
                    int size = value2.top + (arrayList2.size() * childMaxHeight);
                    Rect rect3 = new Rect(left, value2.top, left + width, value2.bottom);
                    Rect rect4 = new Rect(left, size, rect3.width() + left, rect3.height() + size);
                    SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(value2);
                    ObjectAnimator objectAnimatorOfObject2 = ObjectAnimator.ofObject(value2.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect3, rect4);
                    if (!z) {
                        objectAnimatorOfObject2.addUpdateListener(SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                    }
                    arrayList3.add(objectAnimatorOfObject2);
                }
                SemAddDeleteListAnimator.this.mOldViewCache.clear();
                SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.clear();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList3);
                animatorSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.5.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationStart #2");
                        if (SemAddDeleteListAnimator.this.mListView.isPressed()) {
                            SemAddDeleteListAnimator.this.mListView.setPressed(false);
                        }
                        SemAddDeleteListAnimator.this.mListView.setEnabled(false);
                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteListAnimator.this.mOnAddDeleteListener.onAnimationStart(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationEnd #2");
                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteListAnimator.this.mListView.invalidate();
                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteListAnimator.this.mOnAddDeleteListener.onAnimationEnd(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationCancel #2");
                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteListAnimator.this.mListView.invalidate();
                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                    }
                });
                animatorSet.setDuration(SemAddDeleteListAnimator.this.mTranslationDuration);
                animatorSet.start();
                Log.i(SemAddDeleteListAnimator.TAG, "postDelayed #2 mAniTimeoutRunnable delay = " + (SemAddDeleteListAnimator.this.mTranslationDuration + 100));
                SemAddDeleteListAnimator.this.mHandler.postDelayed(SemAddDeleteListAnimator.this.mAniTimeoutRunnable, (long) (SemAddDeleteListAnimator.this.mTranslationDuration + 100));
            }
        };
    }

    public void setInsertDelete(ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2) {
        if (arrayList.size() == 0 && arrayList2.size() == 0) {
            return;
        }
        if (arrayList2.size() == 0) {
            prepareInsert(arrayList);
            OnAddDeleteListener onAddDeleteListener = this.mOnAddDeleteListener;
            if (onAddDeleteListener != null) {
                onAddDeleteListener.onAdd();
            }
            insertIntoAdapterCompleted();
            return;
        }
        if (arrayList.size() == 0) {
            prepareDelete(arrayList2);
            OnAddDeleteListener onAddDeleteListener2 = this.mOnAddDeleteListener;
            if (onAddDeleteListener2 != null) {
                onAddDeleteListener2.onDelete();
            }
            deleteFromAdapterCompleted();
            return;
        }
        prepareInsertDelete(arrayList, arrayList2);
        this.mIsInsertDelete = true;
        OnAddDeleteListener onAddDeleteListener3 = this.mOnAddDeleteListener;
        if (onAddDeleteListener3 != null) {
            onAddDeleteListener3.onDelete();
            this.mOnAddDeleteListener.onAdd();
        }
        this.mIsInsertDelete = false;
        insertDeleteFromAdapterCompleted();
    }

    public void setInsertDeletePending(ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2) {
        if (arrayList.size() == 0 && arrayList2.size() == 0) {
            return;
        }
        if (arrayList2.size() == 0) {
            prepareInsert(arrayList);
            OnAddDeleteListener onAddDeleteListener = this.mOnAddDeleteListener;
            if (onAddDeleteListener != null) {
                onAddDeleteListener.onAdd();
                return;
            }
            return;
        }
        if (arrayList.size() == 0) {
            prepareDelete(arrayList2);
            OnAddDeleteListener onAddDeleteListener2 = this.mOnAddDeleteListener;
            if (onAddDeleteListener2 != null) {
                onAddDeleteListener2.onDelete();
                return;
            }
            return;
        }
        prepareInsertDelete(arrayList, arrayList2);
        this.mIsInsertDelete = true;
        OnAddDeleteListener onAddDeleteListener3 = this.mOnAddDeleteListener;
        if (onAddDeleteListener3 != null) {
            onAddDeleteListener3.onDelete();
            this.mOnAddDeleteListener.onAdd();
        }
        this.mIsInsertDelete = false;
    }

    public void insertDeleteFromAdapterCompleted() {
        if (!this.mInsertDeletePending) {
            throw new SemAbsAddDeleteAnimator.SetDeletePendingIsNotCalledBefore();
        }
        this.mInsertDeletePending = false;
        this.mListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.6
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteListAnimator.this.mListView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteListAnimator.this.mInsertDeleteRunnable == null) {
                    return true;
                }
                SemAddDeleteListAnimator.this.mInsertDeleteRunnable.run();
                SemAddDeleteListAnimator.this.mInsertDeleteRunnable = null;
                return true;
            }
        });
    }

    private void prepareInsertDelete(ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2) {
        boolean z;
        int i;
        int i2;
        int i3;
        boolean z2 = true;
        this.mInsertDeletePending = true;
        ensureAdapterAndListener();
        final ArrayList arrayList3 = new ArrayList(arrayList);
        Collections.sort(arrayList3);
        final HashSet hashSet = new HashSet(arrayList3);
        final ArrayList arrayList4 = new ArrayList(arrayList2);
        Collections.sort(arrayList4);
        final HashSet hashSet2 = new HashSet(arrayList4);
        final ListAdapter adapter = this.mListView.getAdapter();
        final int childCount = this.mListView.getChildCount();
        int count = adapter.getCount();
        int firstVisiblePosition = this.mListView.getFirstVisiblePosition();
        int headerViewsCount = this.mListView.getHeaderViewsCount();
        int footerViewsCount = this.mListView.getFooterViewsCount();
        int i4 = 0;
        while (i4 < childCount) {
            int i5 = i4 + firstVisiblePosition;
            View childAt = this.mListView.getChildAt(i4);
            long itemId = adapter.getItemId(i5);
            if (childAt.getHeight() == 0 || childAt.getWidth() == 0) {
                z = z2;
                String str = TAG;
                StringBuilder sb = new StringBuilder("setInsert() child's one of dimensions is 0, i=");
                i = i4;
                sb.append(i);
                Log.e(str, sb.toString());
            } else {
                BitmapDrawable bitmapDrawableFromView = SemAnimatorUtils.getBitmapDrawableFromView(childAt);
                if (itemId == -1) {
                    if (i5 < headerViewsCount) {
                        z = z2;
                        i3 = i5 + 1;
                        i2 = i4;
                    } else {
                        z = z2;
                        i2 = i4;
                        if (i5 >= count - footerViewsCount) {
                            i3 = -(((i5 + footerViewsCount) - count) + 1);
                        }
                        this.mOldHeaderFooterViewCache.put(Long.valueOf(itemId), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i5, 0, childAt.getTop(), 0, childAt.getBottom()));
                    }
                    itemId = i3;
                    this.mOldHeaderFooterViewCache.put(Long.valueOf(itemId), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i5, 0, childAt.getTop(), 0, childAt.getBottom()));
                } else {
                    z = z2;
                    i2 = i4;
                    this.mOldViewCache.put(Long.valueOf(itemId), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i5, 0, childAt.getTop(), 0, childAt.getBottom()));
                }
                i = i2;
            }
            i4 = i + 1;
            z2 = z;
        }
        final HashMap map = new HashMap();
        for (int i6 = 0; i6 < arrayList3.size(); i6++) {
            Integer num = (Integer) arrayList3.get(i6);
            int iIntValue = num.intValue() - i6;
            for (int i7 = 0; i7 < arrayList4.size(); i7++) {
                if (((Integer) arrayList4.get(i7)).intValue() <= iIntValue) {
                    iIntValue++;
                }
            }
            View childAt2 = this.mListView.getChildAt(iIntValue - firstVisiblePosition);
            if (childAt2 != null) {
                map.put(num, Integer.valueOf(childAt2.getTop()));
            }
        }
        this.mInsertDeleteRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.7
            /* JADX WARN: Removed duplicated region for block: B:18:0x009c  */
            /* JADX WARN: Removed duplicated region for block: B:19:0x00a7  */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public void run() {
                int width;
                int childMaxHeight;
                int left;
                float top;
                int top2;
                ListView listView;
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it;
                boolean z3;
                ObjectAnimator objectAnimatorOfObject;
                int top3;
                int i8;
                int i9;
                long j;
                SemAbsAddDeleteAnimator.ViewInfo viewInfoRemove;
                ListView listView2 = SemAddDeleteListAnimator.this.mListView;
                int firstVisiblePosition2 = listView2.getFirstVisiblePosition();
                int lastVisiblePosition = listView2.getLastVisiblePosition();
                int headerViewsCount2 = listView2.getHeaderViewsCount();
                int footerViewsCount2 = listView2.getFooterViewsCount();
                int childCount2 = listView2.getChildCount();
                int count2 = adapter.getCount();
                ArrayList arrayList5 = new ArrayList();
                if (childCount2 > headerViewsCount2) {
                    childMaxHeight = SemAddDeleteListAnimator.this.getChildMaxHeight() + listView2.getDividerHeight();
                    left = listView2.getChildAt(headerViewsCount2).getLeft();
                    width = listView2.getChildAt(0).getWidth();
                } else {
                    width = listView2.getWidth();
                    childMaxHeight = 0;
                    left = 0;
                }
                boolean z4 = true;
                int i10 = lastVisiblePosition + 1 + (childCount - childCount2);
                int i11 = firstVisiblePosition2;
                int i12 = 0;
                boolean z5 = true;
                while (i12 < childCount2) {
                    boolean z6 = z4;
                    int i13 = i12 + firstVisiblePosition2;
                    long itemId2 = adapter.getItemId(i13);
                    View childAt3 = listView2.getChildAt(i12);
                    int i14 = firstVisiblePosition2;
                    float top4 = childAt3.getTop();
                    int i15 = i10;
                    if (itemId2 == -1) {
                        if (i13 < headerViewsCount2) {
                            i8 = headerViewsCount2;
                            j = i13 + 1;
                        } else {
                            i8 = headerViewsCount2;
                            if (i13 >= count2 - footerViewsCount2) {
                                j = -(((i13 + footerViewsCount2) - count2) + 1);
                            }
                            viewInfoRemove = SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(itemId2));
                            if (viewInfoRemove != null) {
                                Log.e(SemAddDeleteListAnimator.TAG, "AFTER header/footer SOMETHING WENT WRONG, in the new layout, header/footer is appearing that was not present before!");
                            } else {
                                viewInfoRemove.recycleBitmap();
                                if (viewInfoRemove.top == top4) {
                                    Log.e(SemAddDeleteListAnimator.TAG, "AFTER header/footer something strange is happening, the coordinates are same after layout, viewInfo.top=" + viewInfoRemove.top + ", newY=" + top4);
                                } else {
                                    arrayList5.add(SemAddDeleteListAnimator.this.getTranslateAnim(childAt3, 0.0f, viewInfoRemove.top - top4));
                                }
                            }
                        }
                        itemId2 = j;
                        viewInfoRemove = SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(itemId2));
                        if (viewInfoRemove != null) {
                        }
                    } else {
                        i8 = headerViewsCount2;
                        Integer num2 = (Integer) map.remove(Integer.valueOf(i13));
                        SemAbsAddDeleteAnimator.ViewInfo viewInfoRemove2 = SemAddDeleteListAnimator.this.mOldViewCache.remove(Long.valueOf(itemId2));
                        if (viewInfoRemove2 != null) {
                            viewInfoRemove2.recycleBitmap();
                            if (viewInfoRemove2.top != top4) {
                                arrayList5.add(SemAddDeleteListAnimator.this.getTranslateAnim(childAt3, 0.0f, viewInfoRemove2.top - top4));
                            }
                            i10 = i15;
                            z5 = false;
                        } else if (num2 != null) {
                            arrayList5.add(SemAddDeleteListAnimator.this.getInsertTranslateAlphaScaleAnim(childAt3, 0.0f, num2.intValue() - top4));
                        } else if (hashSet.contains(Integer.valueOf(i13))) {
                            arrayList5.add(SemAddDeleteListAnimator.this.getInsertTranslateAlphaScaleAnim(childAt3, 0.0f, (childAt3.getTop() - ((i13 - (i13 - SemAddDeleteListAnimator.this.getShiftCount(i13, arrayList3, arrayList4))) * childMaxHeight)) - top4));
                        } else {
                            if (i11 <= 0 || !z5) {
                                i9 = i15 - i13;
                                i10 = i15 + 1;
                            } else {
                                i9 = -SemAddDeleteListAnimator.this.getShiftCount(i13, arrayList3, arrayList4);
                                i11--;
                                i10 = i15;
                            }
                            arrayList5.add(SemAddDeleteListAnimator.this.getTranslateAnim(childAt3, 0.0f, (childAt3.getTop() + (i9 * childMaxHeight)) - top4));
                        }
                        i12++;
                        z4 = z6;
                        firstVisiblePosition2 = i14;
                        headerViewsCount2 = i8;
                    }
                    i10 = i15;
                    i12++;
                    z4 = z6;
                    firstVisiblePosition2 = i14;
                    headerViewsCount2 = i8;
                }
                int i16 = firstVisiblePosition2;
                boolean z7 = z4;
                map.clear();
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it2 = SemAddDeleteListAnimator.this.mOldViewCache.entrySet().iterator();
                boolean z8 = false;
                while (it2.hasNext()) {
                    SemAbsAddDeleteAnimator.ViewInfo value = it2.next().getValue();
                    SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(value);
                    Rect rect = new Rect(left, value.top, left + width, value.bottom);
                    int newPosition = SemAddDeleteListAnimator.this.getNewPosition(value.oldPosition, arrayList3, arrayList4);
                    boolean zContains = hashSet2.contains(Integer.valueOf(value.oldPosition));
                    int i17 = newPosition - i16;
                    if (i17 < 0) {
                        if (childCount2 == 0) {
                            top3 = listView2.getPaddingTop();
                        } else {
                            top3 = listView2.getChildAt(0).getTop();
                        }
                        top = (top3 - value.top) - ((-i17) * childMaxHeight);
                    } else if (i17 >= childCount2) {
                        int i18 = childCount2 - 1;
                        if (listView2.getChildAt(i18) == null) {
                            top2 = 0 - value.top;
                        } else {
                            top2 = listView2.getChildAt(i18).getTop() - value.top;
                        }
                        top = top2 + (((i17 - childCount2) + 1) * childMaxHeight);
                    } else {
                        top = listView2.getChildAt(i17).getTop() - value.top;
                    }
                    Rect rect2 = new Rect(rect);
                    rect2.offset(0, (int) top);
                    if (zContains) {
                        int iWidth = (int) (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f) * rect2.width());
                        int iHeight = (int) (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f) * rect2.height());
                        listView = listView2;
                        it = it2;
                        z3 = z8;
                        PropertyValuesHolder propertyValuesHolderOfObject = PropertyValuesHolder.ofObject("bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, new Rect(rect2.left + iWidth, rect2.top + iHeight, rect2.right - iWidth, rect2.bottom - iHeight));
                        PropertyValuesHolder propertyValuesHolderOfInt = PropertyValuesHolder.ofInt("alpha", 255, 0);
                        BitmapDrawable bitmapDrawable = value.viewSnapshot;
                        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[2];
                        propertyValuesHolderArr[0] = propertyValuesHolderOfObject;
                        propertyValuesHolderArr[z7 ? 1 : 0] = propertyValuesHolderOfInt;
                        objectAnimatorOfObject = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, propertyValuesHolderArr);
                    } else {
                        listView = listView2;
                        it = it2;
                        z3 = z8;
                        objectAnimatorOfObject = ObjectAnimator.ofObject(value.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, rect2);
                    }
                    if (z3) {
                        z8 = z3;
                    } else {
                        objectAnimatorOfObject.addUpdateListener(SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                        z8 = z7 ? 1 : 0;
                    }
                    arrayList5.add(objectAnimatorOfObject);
                    listView2 = listView;
                    it2 = it;
                }
                boolean z9 = z8;
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it3 = SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.entrySet().iterator();
                while (it3.hasNext()) {
                    SemAbsAddDeleteAnimator.ViewInfo value2 = it3.next().getValue();
                    int size = value2.top + (arrayList3.size() * childMaxHeight);
                    Rect rect3 = new Rect(left, value2.top, left + width, value2.bottom);
                    Rect rect4 = new Rect(left, size, rect3.width() + left, rect3.height() + size);
                    SemAddDeleteListAnimator.this.mGhostViewSnapshots.add(value2);
                    ObjectAnimator objectAnimatorOfObject2 = ObjectAnimator.ofObject(value2.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect3, rect4);
                    if (!z9) {
                        objectAnimatorOfObject2.addUpdateListener(SemAddDeleteListAnimator.this.mBitmapUpdateListener);
                    }
                    arrayList5.add(objectAnimatorOfObject2);
                }
                SemAddDeleteListAnimator.this.mOldViewCache.clear();
                SemAddDeleteListAnimator.this.mOldHeaderFooterViewCache.clear();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList5);
                animatorSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAddDeleteListAnimator.7.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationStart #3");
                        if (SemAddDeleteListAnimator.this.mListView.isPressed()) {
                            SemAddDeleteListAnimator.this.mListView.setPressed(false);
                        }
                        SemAddDeleteListAnimator.this.mListView.setEnabled(false);
                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteListAnimator.this.mOnAddDeleteListener.onAnimationStart(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationEnd #3");
                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteListAnimator.this.mListView.invalidate();
                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                        if (SemAddDeleteListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteListAnimator.this.mOnAddDeleteListener.onAnimationEnd(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Log.i(SemAddDeleteListAnimator.TAG, "onAnimationCancel #3");
                        SemAddDeleteListAnimator.this.mHandler.removeCallbacks(SemAddDeleteListAnimator.this.mAniTimeoutRunnable);
                        SemAddDeleteListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteListAnimator.this.mListView.invalidate();
                        SemAddDeleteListAnimator.this.mListView.setEnabled(true);
                    }
                });
                animatorSet.setDuration(SemAddDeleteListAnimator.this.mTranslationDuration);
                animatorSet.start();
                Log.i(SemAddDeleteListAnimator.TAG, "postDelayed #3 mAniTimeoutRunnable delay = " + (SemAddDeleteListAnimator.this.mTranslationDuration + 100));
                SemAddDeleteListAnimator.this.mHandler.postDelayed(SemAddDeleteListAnimator.this.mAniTimeoutRunnable, (long) (SemAddDeleteListAnimator.this.mTranslationDuration + 100));
            }
        };
    }

    private void ensureAdapterAndListener() {
        ListAdapter adapter = this.mListView.getAdapter();
        if (adapter == null) {
            throw new IllegalStateException("Adapter need to be set before performing add/delete operations.");
        }
        if (!adapter.hasStableIds()) {
            throw new IllegalStateException("SemAddDeleteListAnimator requires an adapter that has stable ids");
        }
        if (this.mOnAddDeleteListener == null) {
            throw new IllegalStateException("OnAddDeleteListener need to be supplied before performing add/delete operations");
        }
    }

    public boolean isInsertDeleting() {
        return this.mIsInsertDelete;
    }
}
