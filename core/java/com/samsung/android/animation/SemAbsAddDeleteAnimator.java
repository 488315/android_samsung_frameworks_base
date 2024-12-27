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

abstract class SemAbsAddDeleteAnimator {
    Runnable mDeleteRunnable;
    View mHostView;
    Runnable mInsertDeleteRunnable;
    Runnable mInsertRunnable;
    static float START_SCALE_FACTOR = 0.95f;
    static Interpolator DELETE_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    static Interpolator INSERT_INTERPOLATOR = new PathInterpolator(0.33f, 0.0f, 0.67f, 1.0f);
    int mTranslationDuration = 300;
    Rect mBitmapUpdateBounds = new Rect();
    ArrayList<ViewInfo> mGhostViewSnapshots = new ArrayList<>();
    ValueAnimator.AnimatorUpdateListener mBitmapUpdateListener =
            new ValueAnimator
                    .AnimatorUpdateListener() { // from class:
                                                // com.samsung.android.animation.SemAbsAddDeleteAnimator.1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public void onAnimationUpdate(ValueAnimator anim) {
                    int ghostViewCount = SemAbsAddDeleteAnimator.this.mGhostViewSnapshots.size();
                    if (ghostViewCount == 0) {
                        return;
                    }
                    SemAbsAddDeleteAnimator.this.mBitmapUpdateBounds.setEmpty();
                    for (int i = 0; i < ghostViewCount; i++) {
                        ViewInfo vInfo = SemAbsAddDeleteAnimator.this.mGhostViewSnapshots.get(i);
                        SemAbsAddDeleteAnimator.this.mBitmapUpdateBounds.union(
                                vInfo.viewSnapshot.getBounds());
                    }
                    SemAbsAddDeleteAnimator.this.mHostView.invalidate(
                            SemAbsAddDeleteAnimator.this.mBitmapUpdateBounds);
                }
            };

    abstract void deleteFromAdapterCompleted();

    abstract void insertIntoAdapterCompleted();

    abstract void setDelete(ArrayList<Integer> arrayList);

    abstract void setDeletePending(ArrayList<Integer> arrayList);

    abstract void setInsert(ArrayList<Integer> arrayList);

    abstract void setInsertPending(ArrayList<Integer> arrayList);

    SemAbsAddDeleteAnimator() {}

    int getShiftCount(int currentPos, ArrayList<Integer> insertedItemPositions) {
        int shiftCount = 0;
        Iterator<Integer> it = insertedItemPositions.iterator();
        while (it.hasNext()) {
            int pos = it.next().intValue();
            if (pos >= currentPos) {
                break;
            }
            shiftCount++;
        }
        return shiftCount;
    }

    int getShiftCount(
            int currentPos,
            ArrayList<Integer> insertedItemPositions,
            ArrayList<Integer> deletingItemPositions) {
        int shiftCount = 0;
        Iterator<Integer> it = insertedItemPositions.iterator();
        while (it.hasNext()) {
            int pos = it.next().intValue();
            if (pos >= currentPos) {
                break;
            }
            shiftCount++;
        }
        Iterator<Integer> it2 = deletingItemPositions.iterator();
        while (it2.hasNext()) {
            int pos2 = it2.next().intValue();
            if (pos2 >= currentPos) {
                break;
            }
            shiftCount--;
        }
        return shiftCount;
    }

    int getNewPositionForInsert(int oldPosition, ArrayList<Integer> insertedItems) {
        int newPosition = oldPosition;
        Iterator<Integer> it = insertedItems.iterator();
        while (it.hasNext()) {
            int pos = it.next().intValue();
            if (pos > newPosition) {
                break;
            }
            newPosition++;
        }
        return newPosition;
    }

    int getNewPosition(int oldPosition, ArrayList<Integer> deletedItems) {
        int newPosition = oldPosition;
        Iterator<Integer> it = deletedItems.iterator();
        while (it.hasNext()) {
            int pos = it.next().intValue();
            if (pos >= oldPosition) {
                break;
            }
            newPosition--;
        }
        return newPosition;
    }

    int getNewPosition(
            int oldPosition, ArrayList<Integer> insertedItems, ArrayList<Integer> deletedItems) {
        int newPosition = oldPosition;
        Iterator<Integer> it = deletedItems.iterator();
        while (it.hasNext()) {
            int pos = it.next().intValue();
            if (pos >= oldPosition) {
                break;
            }
            newPosition--;
        }
        int i = 0;
        Iterator<Integer> it2 = insertedItems.iterator();
        while (it2.hasNext()) {
            int pos2 = it2.next().intValue();
            if (pos2 > oldPosition + i) {
                break;
            }
            newPosition++;
            i++;
        }
        return newPosition;
    }

    public void setTransitionDuration(int duration) {
        this.mTranslationDuration = duration;
    }

    PropertyValuesHolder getPropertyValuesHolder(Property<?, Float> property, float value) {
        return PropertyValuesHolder.ofFloat(property, value);
    }

    static class ViewInfo {
        int bottom;
        int left;
        int oldPosition;
        int right;
        int top;
        BitmapDrawable viewSnapshot;

        public ViewInfo(
                BitmapDrawable viewSnapshot,
                int oldPosition,
                int left,
                int top,
                int right,
                int bottom) {
            this.viewSnapshot = viewSnapshot;
            this.oldPosition = oldPosition;
            this.top = top;
            this.left = left;
            this.right = right;
            this.bottom = bottom;
        }

        public void recycleBitmap() {
            this.viewSnapshot.getBitmap().recycle();
        }
    }

    ObjectAnimator getInsertTranslateAlphaScaleAnim(
            View child, float translationX, float translationY) {
        child.setTranslationX(translationX);
        child.setTranslationY(translationY);
        child.setAlpha(0.0f);
        child.setScaleX(START_SCALE_FACTOR);
        child.setScaleY(START_SCALE_FACTOR);
        return ObjectAnimator.ofPropertyValuesHolder(
                child,
                getPropertyValuesHolder(View.TRANSLATION_X, 0.0f),
                getPropertyValuesHolder(View.TRANSLATION_Y, 0.0f),
                getPropertyValuesHolder(View.SCALE_X, 1.0f),
                getPropertyValuesHolder(View.SCALE_Y, 1.0f),
                getPropertyValuesHolder(View.ALPHA, 1.0f));
    }

    ObjectAnimator getTranslateAnim(View child, float translationX, float translationY) {
        child.setTranslationX(translationX);
        child.setTranslationY(translationY);
        return ObjectAnimator.ofPropertyValuesHolder(
                child,
                getPropertyValuesHolder(View.TRANSLATION_X, 0.0f),
                getPropertyValuesHolder(View.TRANSLATION_Y, 0.0f));
    }

    public void draw(Canvas canvas) {
        if (this.mGhostViewSnapshots.size() == 0) {
            return;
        }
        Iterator<ViewInfo> it = this.mGhostViewSnapshots.iterator();
        while (it.hasNext()) {
            ViewInfo vInfo = it.next();
            vInfo.viewSnapshot.draw(canvas);
        }
    }

    class SetDeletePendingIsNotCalledBefore extends RuntimeException {
        public SetDeletePendingIsNotCalledBefore() {
            super(
                    "setDeletePending() should be called prior to calling"
                        + " deleteFromAdapterCompleted()");
        }
    }

    class SetInsertPendingIsNotCalledBefore extends RuntimeException {
        public SetInsertPendingIsNotCalledBefore() {
            super(
                    "setInsertPending() should be called prior to calling"
                        + " insertFromAdapterCompleted()");
        }
    }
}
