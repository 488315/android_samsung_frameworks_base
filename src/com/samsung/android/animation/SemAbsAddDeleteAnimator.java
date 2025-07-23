package com.samsung.android.animation;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.Property;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
abstract class SemAbsAddDeleteAnimator {
    static Interpolator DELETE_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    static Interpolator INSERT_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    static float START_SCALE_FACTOR = 0.95f;
    Runnable mDeleteRunnable;
    View mHostView;
    Runnable mInsertDeleteRunnable;
    Runnable mInsertRunnable;
    int mTranslationDuration = 300;
    Rect mBitmapUpdateBounds = new Rect();
    ArrayList<ViewInfo> mGhostViewSnapshots = new ArrayList<>();
    ValueAnimator.AnimatorUpdateListener mBitmapUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.samsung.android.animation.SemAbsAddDeleteAnimator.1
        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int size = SemAbsAddDeleteAnimator.this.mGhostViewSnapshots.size();
            if (size == 0) {
                return;
            }
            SemAbsAddDeleteAnimator.this.mBitmapUpdateBounds.setEmpty();
            for (int i = 0; i < size; i++) {
                SemAbsAddDeleteAnimator.this.mBitmapUpdateBounds.union(SemAbsAddDeleteAnimator.this.mGhostViewSnapshots.get(i).viewSnapshot.getBounds());
            }
            SemAbsAddDeleteAnimator.this.mHostView.invalidate(SemAbsAddDeleteAnimator.this.mBitmapUpdateBounds);
        }
    };

    abstract void deleteFromAdapterCompleted();

    abstract void insertIntoAdapterCompleted();

    abstract void setDelete(ArrayList<Integer> arrayList);

    abstract void setDeletePending(ArrayList<Integer> arrayList);

    abstract void setInsert(ArrayList<Integer> arrayList);

    abstract void setInsertPending(ArrayList<Integer> arrayList);

    SemAbsAddDeleteAnimator() {
    }

    int getShiftCount(int i, ArrayList<Integer> arrayList) {
        Iterator<Integer> it = arrayList.iterator();
        int i2 = 0;
        while (it.hasNext() && it.next().intValue() < i) {
            i2++;
        }
        return i2;
    }

    int getShiftCount(int i, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2) {
        Iterator<Integer> it = arrayList.iterator();
        int i2 = 0;
        while (it.hasNext() && it.next().intValue() < i) {
            i2++;
        }
        Iterator<Integer> it2 = arrayList2.iterator();
        while (it2.hasNext() && it2.next().intValue() < i) {
            i2--;
        }
        return i2;
    }

    int getNewPositionForInsert(int i, ArrayList<Integer> arrayList) {
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext() && it.next().intValue() <= i) {
            i++;
        }
        return i;
    }

    int getNewPosition(int i, ArrayList<Integer> arrayList) {
        Iterator<Integer> it = arrayList.iterator();
        int i2 = i;
        while (it.hasNext() && it.next().intValue() < i) {
            i2--;
        }
        return i2;
    }

    int getNewPosition(int i, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList2) {
        Iterator<Integer> it = arrayList2.iterator();
        int i2 = i;
        while (it.hasNext() && it.next().intValue() < i) {
            i2--;
        }
        Iterator<Integer> it2 = arrayList.iterator();
        for (int i3 = 0; it2.hasNext() && it2.next().intValue() <= i + i3; i3++) {
            i2++;
        }
        return i2;
    }

    public void setTransitionDuration(int i) {
        this.mTranslationDuration = i;
    }

    PropertyValuesHolder getPropertyValuesHolder(Property<?, Float> property, float f) {
        return PropertyValuesHolder.ofFloat(property, f);
    }

    static class ViewInfo {
        int bottom;
        int left;
        int oldPosition;
        int right;
        int top;
        BitmapDrawable viewSnapshot;

        public ViewInfo(BitmapDrawable bitmapDrawable, int i, int i2, int i3, int i4, int i5) {
            this.viewSnapshot = bitmapDrawable;
            this.oldPosition = i;
            this.top = i3;
            this.left = i2;
            this.right = i4;
            this.bottom = i5;
        }

        public void recycleBitmap() {
            this.viewSnapshot.getBitmap().recycle();
        }
    }

    ObjectAnimator getInsertTranslateAlphaScaleAnim(View view, float f, float f2) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        view.setAlpha(0.0f);
        view.setScaleX(START_SCALE_FACTOR);
        view.setScaleY(START_SCALE_FACTOR);
        return ObjectAnimator.ofPropertyValuesHolder(view, getPropertyValuesHolder(View.TRANSLATION_X, 0.0f), getPropertyValuesHolder(View.TRANSLATION_Y, 0.0f), getPropertyValuesHolder(View.SCALE_X, 1.0f), getPropertyValuesHolder(View.SCALE_Y, 1.0f), getPropertyValuesHolder(View.ALPHA, 1.0f));
    }

    ObjectAnimator getTranslateAnim(View view, float f, float f2) {
        view.setTranslationX(f);
        view.setTranslationY(f2);
        return ObjectAnimator.ofPropertyValuesHolder(view, getPropertyValuesHolder(View.TRANSLATION_X, 0.0f), getPropertyValuesHolder(View.TRANSLATION_Y, 0.0f));
    }

    public void draw(Canvas canvas) {
        if (this.mGhostViewSnapshots.size() == 0) {
            return;
        }
        Iterator<ViewInfo> it = this.mGhostViewSnapshots.iterator();
        while (it.hasNext()) {
            it.next().viewSnapshot.draw(canvas);
        }
    }

    class SetDeletePendingIsNotCalledBefore extends RuntimeException {
        public SetDeletePendingIsNotCalledBefore() {
            super("setDeletePending() should be called prior to calling deleteFromAdapterCompleted()");
        }
    }

    class SetInsertPendingIsNotCalledBefore extends RuntimeException {
        public SetInsertPendingIsNotCalledBefore() {
            super("setInsertPending() should be called prior to calling insertFromAdapterCompleted()");
        }
    }
}
