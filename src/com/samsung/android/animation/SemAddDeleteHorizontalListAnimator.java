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
import com.samsung.android.animation.SemAbsAddDeleteAnimator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/* loaded from: classes6.dex */
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

    public SemAddDeleteHorizontalListAnimator(Context context, SemHorizontalListView semHorizontalListView) {
        this.mHorizontalListView = semHorizontalListView;
        semHorizontalListView.setAddDeleteListAnimator(this);
        this.mHostView = semHorizontalListView;
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
        this.mHorizontalListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteHorizontalListAnimator.this.mDeleteRunnable == null) {
                    return true;
                }
                SemAddDeleteHorizontalListAnimator.this.mDeleteRunnable.run();
                SemAddDeleteHorizontalListAnimator.this.mDeleteRunnable = null;
                return true;
            }
        });
    }

    private void prepareDelete(ArrayList<Integer> arrayList) {
        int height;
        int i;
        this.mDeletePending = true;
        final ArrayList arrayList2 = new ArrayList(arrayList);
        ensureAdapterAndListener();
        Collections.sort(arrayList2);
        final HashSet hashSet = new HashSet(arrayList2);
        final int childCount = this.mHorizontalListView.getChildCount();
        final int firstVisiblePosition = this.mHorizontalListView.getFirstVisiblePosition();
        final ListAdapter adapter = this.mHorizontalListView.getAdapter();
        SemHorizontalListView semHorizontalListView = this.mHorizontalListView;
        if (semHorizontalListView.getChildAt(semHorizontalListView.getHeaderViewsCount()) != null) {
            SemHorizontalListView semHorizontalListView2 = this.mHorizontalListView;
            height = semHorizontalListView2.getChildAt(semHorizontalListView2.getHeaderViewsCount()).getHeight();
            SemHorizontalListView semHorizontalListView3 = this.mHorizontalListView;
            i = semHorizontalListView3.getChildAt(semHorizontalListView3.getHeaderViewsCount()).getTop();
        } else {
            height = this.mHorizontalListView.getHeight();
            i = 0;
        }
        final int i2 = height;
        final int i3 = i;
        capturePreAnimationViewCoordinates();
        this.mDeleteRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.2
            @Override // java.lang.Runnable
            public void run() {
                int i4;
                int i5;
                int i6;
                int left;
                float f;
                SemHorizontalListView semHorizontalListView4;
                int i7;
                float f2;
                int i8;
                SemAbsAddDeleteAnimator.ViewInfo remove;
                int i9;
                int i10;
                int left2;
                float f3;
                int i11;
                SemHorizontalListView semHorizontalListView5 = SemAddDeleteHorizontalListAnimator.this.mHorizontalListView;
                int childCount2 = semHorizontalListView5.getChildCount();
                int firstVisiblePosition2 = semHorizontalListView5.getFirstVisiblePosition();
                int lastVisiblePosition = semHorizontalListView5.getLastVisiblePosition();
                int headerViewsCount = semHorizontalListView5.getHeaderViewsCount();
                int footerViewsCount = semHorizontalListView5.getFooterViewsCount();
                int count = adapter.getCount();
                ArrayList arrayList3 = new ArrayList();
                if (childCount2 > headerViewsCount) {
                    i6 = SemAddDeleteHorizontalListAnimator.this.getChildMaxWidth() + semHorizontalListView5.getDividerHeight();
                    i4 = semHorizontalListView5.getChildAt(headerViewsCount).getTop();
                    i5 = semHorizontalListView5.getChildAt(headerViewsCount).getHeight();
                } else {
                    i4 = i3;
                    i5 = i2;
                    i6 = 0;
                }
                int i12 = firstVisiblePosition - firstVisiblePosition2;
                boolean z = true;
                int i13 = lastVisiblePosition + 1 + (childCount - childCount2);
                int i14 = i12;
                boolean z2 = true;
                int i15 = 0;
                while (i15 < childCount2) {
                    boolean z3 = z;
                    View childAt = semHorizontalListView5.getChildAt(i15);
                    int i16 = i15 + firstVisiblePosition2;
                    int i17 = firstVisiblePosition2;
                    long itemId = adapter.getItemId(i16);
                    float left3 = childAt.getLeft();
                    if (itemId == -1) {
                        if (i16 < headerViewsCount) {
                            f2 = left3;
                            i11 = i16 + 1;
                            i8 = i13;
                        } else {
                            f2 = left3;
                            i8 = i13;
                            if (i16 >= count - footerViewsCount) {
                                i11 = -(((i16 + footerViewsCount) - count) + 1);
                            }
                            remove = SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(itemId));
                        }
                        itemId = i11;
                        remove = SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(itemId));
                    } else {
                        f2 = left3;
                        i8 = i13;
                        remove = SemAddDeleteHorizontalListAnimator.this.mOldViewCache.remove(Long.valueOf(itemId));
                    }
                    if (remove != null) {
                        remove.recycleBitmap();
                        if (remove.left == f2) {
                            i13 = i8;
                            z2 = false;
                            i15++;
                            z = z3;
                            firstVisiblePosition2 = i17;
                        } else {
                            f3 = remove.left - f2;
                            i10 = i8;
                            z2 = false;
                        }
                    } else {
                        if (i14 <= 0 || !z2) {
                            i9 = i8 - i16;
                            i10 = i8 + 1;
                        } else {
                            i9 = -i12;
                            i14--;
                            i10 = i8;
                        }
                        if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                            left2 = childAt.getLeft() - (i9 * i6);
                        } else {
                            left2 = childAt.getLeft() + (i9 * i6);
                        }
                        f3 = left2 - f2;
                    }
                    arrayList3.add(SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(childAt, f3, 0.0f));
                    i13 = i10;
                    i15++;
                    z = z3;
                    firstVisiblePosition2 = i17;
                }
                int i18 = firstVisiblePosition2;
                boolean z4 = z;
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it = SemAddDeleteHorizontalListAnimator.this.mOldViewCache.entrySet().iterator();
                boolean z5 = false;
                while (it.hasNext()) {
                    SemAbsAddDeleteAnimator.ViewInfo value = it.next().getValue();
                    SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.add(value);
                    Rect rect = new Rect(value.left, i4, value.right, i4 + i5);
                    int newPosition = SemAddDeleteHorizontalListAnimator.this.getNewPosition(value.oldPosition, arrayList2);
                    boolean contains = hashSet.contains(Integer.valueOf(value.oldPosition));
                    int i19 = newPosition - i18;
                    if (i19 < 0 || i19 >= childCount2) {
                        if (childCount2 == 0) {
                            if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                                left = semHorizontalListView5.getWidth() - (value.right - value.left);
                            } else {
                                left = semHorizontalListView5.getPaddingLeft();
                            }
                        } else {
                            left = semHorizontalListView5.getChildAt(0).getLeft();
                        }
                        float f4 = left - value.left;
                        f = SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl() ? f4 + ((-i19) * i6) : f4 - ((-i19) * i6);
                    } else {
                        f = semHorizontalListView5.getChildAt(i19).getLeft() - value.left;
                    }
                    Rect rect2 = new Rect(rect);
                    rect2.offset((int) f, 0);
                    if (contains) {
                        int width = (int) (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f) * rect2.width());
                        int height2 = (int) (((1.0f - SemAbsAddDeleteAnimator.START_SCALE_FACTOR) / 2.0f) * rect2.height());
                        semHorizontalListView4 = semHorizontalListView5;
                        i7 = childCount2;
                        rect2 = new Rect(rect2.left + width, rect2.top + height2, rect2.right - width, rect2.bottom - height2);
                    } else {
                        semHorizontalListView4 = semHorizontalListView5;
                        i7 = childCount2;
                    }
                    PropertyValuesHolder ofObject = PropertyValuesHolder.ofObject("bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, rect2);
                    PropertyValuesHolder ofInt = PropertyValuesHolder.ofInt("alpha", 255, 0);
                    BitmapDrawable bitmapDrawable = value.viewSnapshot;
                    PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[2];
                    propertyValuesHolderArr[0] = ofObject;
                    propertyValuesHolderArr[z4 ? 1 : 0] = ofInt;
                    ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, propertyValuesHolderArr);
                    if (!z5) {
                        ofPropertyValuesHolder.addUpdateListener(SemAddDeleteHorizontalListAnimator.this.mBitmapUpdateListener);
                        z5 = z4 ? 1 : 0;
                    }
                    arrayList3.add(ofPropertyValuesHolder);
                    semHorizontalListView5 = semHorizontalListView4;
                    childCount2 = i7;
                }
                SemAddDeleteHorizontalListAnimator.this.mOldViewCache.clear();
                SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.clear();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList3);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.2.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(false);
                        if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationStart(false);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                        if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationEnd(false);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Log.d(SemAddDeleteHorizontalListAnimator.TAG, "onAnimationCancel #1");
                        SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                    }
                });
                animatorSet.setInterpolator(SemAbsAddDeleteAnimator.DELETE_INTERPOLATOR);
                animatorSet.setDuration(SemAddDeleteHorizontalListAnimator.this.mTranslationDuration);
                animatorSet.start();
            }
        };
    }

    private void capturePreAnimationViewCoordinates() {
        SemHorizontalListView semHorizontalListView;
        int i;
        SemHorizontalListView semHorizontalListView2 = this.mHorizontalListView;
        ListAdapter adapter = semHorizontalListView2.getAdapter();
        int childCount = semHorizontalListView2.getChildCount();
        int firstVisiblePosition = semHorizontalListView2.getFirstVisiblePosition();
        int count = adapter.getCount();
        int headerViewsCount = semHorizontalListView2.getHeaderViewsCount();
        int footerViewsCount = semHorizontalListView2.getFooterViewsCount();
        int i2 = 0;
        while (i2 < childCount) {
            View childAt = semHorizontalListView2.getChildAt(i2);
            int i3 = i2 + firstVisiblePosition;
            long itemId = adapter.getItemId(i3);
            if (childAt.getHeight() == 0 || childAt.getWidth() == 0) {
                semHorizontalListView = semHorizontalListView2;
                Log.e(TAG, "setDelete() child's one of dimensions is 0, i = " + i2);
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
                        semHorizontalListView = semHorizontalListView2;
                        this.mOldHeaderFooterViewCache.put(Long.valueOf(j), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i3, childAt.getLeft(), 0, childAt.getRight(), 0));
                    }
                    j = i;
                    semHorizontalListView = semHorizontalListView2;
                    this.mOldHeaderFooterViewCache.put(Long.valueOf(j), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i3, childAt.getLeft(), 0, childAt.getRight(), 0));
                } else {
                    semHorizontalListView = semHorizontalListView2;
                    this.mOldViewCache.put(Long.valueOf(j), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i3, childAt.getLeft(), 0, childAt.getRight(), 0));
                }
            }
            i2++;
            semHorizontalListView2 = semHorizontalListView;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getChildMaxWidth() {
        int width;
        int childCount = this.mHorizontalListView.getChildCount();
        int count = this.mHorizontalListView.getAdapter().getCount();
        int firstVisiblePosition = this.mHorizontalListView.getFirstVisiblePosition();
        int i = 0;
        for (int i2 = 0; i2 < childCount; i2++) {
            int i3 = i2 + firstVisiblePosition;
            if (i3 >= this.mHorizontalListView.getHeaderViewsCount() && i3 < count - this.mHorizontalListView.getFooterViewsCount() && (width = this.mHorizontalListView.getChildAt(i2).getWidth()) > i) {
                i = width;
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
        this.mHorizontalListView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.3
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (SemAddDeleteHorizontalListAnimator.this.mInsertRunnable == null) {
                    return true;
                }
                SemAddDeleteHorizontalListAnimator.this.mInsertRunnable.run();
                SemAddDeleteHorizontalListAnimator.this.mInsertRunnable = null;
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
        SemHorizontalListView semHorizontalListView = this.mHorizontalListView;
        final ListAdapter adapter = semHorizontalListView.getAdapter();
        int childCount = semHorizontalListView.getChildCount();
        int count = adapter.getCount();
        int firstVisiblePosition = semHorizontalListView.getFirstVisiblePosition();
        int footerViewsCount = semHorizontalListView.getFooterViewsCount();
        int i3 = 0;
        while (i3 < childCount) {
            int i4 = i3 + firstVisiblePosition;
            View childAt = semHorizontalListView.getChildAt(i3);
            long itemId = adapter.getItemId(i4);
            if (childAt.getHeight() == 0 || childAt.getWidth() == 0) {
                String str = TAG;
                StringBuilder sb = new StringBuilder("setInsert() child's one of dimensions is 0, i = ");
                i3 = i3;
                sb.append(i3);
                Log.e(str, sb.toString());
            } else {
                BitmapDrawable bitmapDrawableFromView = SemAnimatorUtils.getBitmapDrawableFromView(childAt);
                if (itemId != -1) {
                    i = i3;
                    this.mOldViewCache.put(Long.valueOf(itemId), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i4, childAt.getLeft(), 0, childAt.getRight(), 0));
                } else if (i4 >= count - footerViewsCount) {
                    i = i3;
                    this.mOldHeaderFooterViewCache.put(Long.valueOf(-(((i4 + footerViewsCount) - count) + i2)), new SemAbsAddDeleteAnimator.ViewInfo(bitmapDrawableFromView, i4, childAt.getLeft(), 0, childAt.getRight(), 0));
                }
                i3 = i;
            }
            i3++;
            i2 = 1;
        }
        final HashMap hashMap = new HashMap();
        for (int i5 = 0; i5 < arrayList2.size(); i5++) {
            Integer num = (Integer) arrayList2.get(i5);
            View childAt2 = semHorizontalListView.getChildAt((num.intValue() - i5) - firstVisiblePosition);
            if (childAt2 != null) {
                hashMap.put(num, Integer.valueOf(childAt2.getLeft()));
            }
        }
        this.mInsertRunnable = new Runnable() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.4
            @Override // java.lang.Runnable
            public void run() {
                int height;
                int i6;
                int i7;
                int i8;
                int left;
                int i9;
                int left2;
                ObjectAnimator translateAnim;
                SemHorizontalListView semHorizontalListView2 = SemAddDeleteHorizontalListAnimator.this.mHorizontalListView;
                int firstVisiblePosition2 = semHorizontalListView2.getFirstVisiblePosition();
                int headerViewsCount = semHorizontalListView2.getHeaderViewsCount();
                int footerViewsCount2 = semHorizontalListView2.getFooterViewsCount();
                int childCount2 = semHorizontalListView2.getChildCount();
                int count2 = adapter.getCount();
                ArrayList arrayList3 = new ArrayList();
                if (childCount2 > headerViewsCount) {
                    i7 = SemAddDeleteHorizontalListAnimator.this.getChildMaxWidth();
                    i6 = semHorizontalListView2.getChildAt(headerViewsCount).getTop();
                    height = semHorizontalListView2.getChildAt(0).getHeight();
                } else {
                    height = semHorizontalListView2.getHeight();
                    i6 = 0;
                    i7 = 0;
                }
                int i10 = 0;
                while (i10 < childCount2) {
                    int i11 = i10 + firstVisiblePosition2;
                    long itemId2 = adapter.getItemId(i11);
                    View childAt3 = semHorizontalListView2.getChildAt(i10);
                    float left3 = childAt3.getLeft();
                    int i12 = firstVisiblePosition2;
                    if (itemId2 == -1) {
                        SemAbsAddDeleteAnimator.ViewInfo remove = SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.remove(Long.valueOf(-(((i11 + footerViewsCount2) - count2) + 1)));
                        if (remove == null) {
                            Log.e(SemAddDeleteHorizontalListAnimator.TAG, "AFTER header/footer SOMETHING WENT WRONG, in the new layout, header/footer is appearing that was not present before!");
                        } else {
                            remove.recycleBitmap();
                            if (remove.left == left3) {
                                Log.e(SemAddDeleteHorizontalListAnimator.TAG, "AFTER header/footer something strange is happening, the coordinates are same after layout, viewInfo.left=" + remove.left + ", newX=" + left3);
                            } else {
                                arrayList3.add(SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(childAt3, remove.left - left3, 0.0f));
                            }
                        }
                        i9 = footerViewsCount2;
                    } else {
                        i9 = footerViewsCount2;
                        Integer num2 = (Integer) hashMap.remove(Integer.valueOf(i11));
                        SemAbsAddDeleteAnimator.ViewInfo remove2 = SemAddDeleteHorizontalListAnimator.this.mOldViewCache.remove(Long.valueOf(itemId2));
                        if (remove2 != null) {
                            remove2.recycleBitmap();
                            if (remove2.left != left3) {
                                arrayList3.add(SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(childAt3, remove2.left - left3, 0.0f));
                            }
                        } else if (num2 != null) {
                            arrayList3.add(SemAddDeleteHorizontalListAnimator.this.getInsertTranslateAlphaScaleAnim(childAt3, num2.intValue() - left3, 0.0f));
                        } else {
                            int shiftCount = i11 - (i11 - SemAddDeleteHorizontalListAnimator.this.getShiftCount(i11, arrayList2));
                            if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                                left2 = childAt3.getLeft() + (shiftCount * i7);
                            } else {
                                left2 = childAt3.getLeft() - (shiftCount * i7);
                            }
                            float f = left2 - left3;
                            if (hashSet.contains(Integer.valueOf(i11))) {
                                translateAnim = SemAddDeleteHorizontalListAnimator.this.getInsertTranslateAlphaScaleAnim(childAt3, f, 0.0f);
                            } else {
                                translateAnim = SemAddDeleteHorizontalListAnimator.this.getTranslateAnim(childAt3, f, 0.0f);
                            }
                            arrayList3.add(translateAnim);
                        }
                    }
                    i10++;
                    firstVisiblePosition2 = i12;
                    footerViewsCount2 = i9;
                }
                hashMap.clear();
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it = SemAddDeleteHorizontalListAnimator.this.mOldViewCache.entrySet().iterator();
                int lastVisiblePosition = semHorizontalListView2.getLastVisiblePosition();
                boolean z = false;
                while (it.hasNext()) {
                    int i13 = lastVisiblePosition + 1;
                    if (arrayList2.contains(Integer.valueOf(i13))) {
                        lastVisiblePosition = i13;
                    } else {
                        SemAbsAddDeleteAnimator.ViewInfo value = it.next().getValue();
                        int newPositionForInsert = SemAddDeleteHorizontalListAnimator.this.getNewPositionForInsert(value.oldPosition, arrayList2);
                        if (newPositionForInsert < semHorizontalListView2.getFirstVisiblePosition()) {
                            int firstVisiblePosition3 = semHorizontalListView2.getFirstVisiblePosition() - newPositionForInsert;
                            if (childCount2 != 0) {
                                left = semHorizontalListView2.getChildAt(0).getLeft();
                            } else {
                                left = semHorizontalListView2.getLeft();
                            }
                            i8 = SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl() ? left + (firstVisiblePosition3 * i7) : left - (firstVisiblePosition3 * i7);
                        } else {
                            int i14 = i13 - value.oldPosition;
                            if (SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.isLayoutRtl()) {
                                i8 = value.left - (i14 * i7);
                            } else {
                                i8 = value.left + (i14 * i7);
                            }
                            lastVisiblePosition = i13;
                        }
                        SemHorizontalListView semHorizontalListView3 = semHorizontalListView2;
                        Rect rect = new Rect(value.left, i6, value.right, i6 + height);
                        Rect rect2 = new Rect(i8, i6, rect.width() + i8, rect.height() + i6);
                        SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.add(value);
                        ObjectAnimator ofObject = ObjectAnimator.ofObject(value.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect, rect2);
                        arrayList3.add(ofObject);
                        if (!z) {
                            ofObject.addUpdateListener(SemAddDeleteHorizontalListAnimator.this.mBitmapUpdateListener);
                            z = true;
                        }
                        semHorizontalListView2 = semHorizontalListView3;
                    }
                }
                Iterator<Map.Entry<Long, SemAbsAddDeleteAnimator.ViewInfo>> it2 = SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.entrySet().iterator();
                while (it2.hasNext()) {
                    SemAbsAddDeleteAnimator.ViewInfo value2 = it2.next().getValue();
                    int size = value2.left + (arrayList2.size() * i7);
                    Rect rect3 = new Rect(value2.left, i6, value2.right, i6 + height);
                    Rect rect4 = new Rect(size, i6, rect3.width() + size, rect3.height() + i6);
                    SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.add(value2);
                    ObjectAnimator ofObject2 = ObjectAnimator.ofObject(value2.viewSnapshot, "bounds", SemAnimatorUtils.BOUNDS_EVALUATOR, rect3, rect4);
                    if (!z) {
                        ofObject2.addUpdateListener(SemAddDeleteHorizontalListAnimator.this.mBitmapUpdateListener);
                    }
                    arrayList3.add(ofObject2);
                }
                SemAddDeleteHorizontalListAnimator.this.mOldViewCache.clear();
                SemAddDeleteHorizontalListAnimator.this.mOldHeaderFooterViewCache.clear();
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(arrayList3);
                animatorSet.setInterpolator(SemAbsAddDeleteAnimator.INSERT_INTERPOLATOR);
                animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.samsung.android.animation.SemAddDeleteHorizontalListAnimator.4.1
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationStart(Animator animator) {
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(false);
                        if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationStart(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationEnd(Animator animator) {
                        SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                        if (SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener != null) {
                            SemAddDeleteHorizontalListAnimator.this.mOnAddDeleteListener.onAnimationEnd(true);
                        }
                    }

                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public void onAnimationCancel(Animator animator) {
                        Log.d(SemAddDeleteHorizontalListAnimator.TAG, "onAnimationCancel #2");
                        SemAddDeleteHorizontalListAnimator.this.mGhostViewSnapshots.clear();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.invalidate();
                        SemAddDeleteHorizontalListAnimator.this.mHorizontalListView.setEnabled(true);
                    }
                });
                animatorSet.setDuration(SemAddDeleteHorizontalListAnimator.this.mTranslationDuration);
                animatorSet.start();
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
