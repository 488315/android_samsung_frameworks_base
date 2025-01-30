package com.samsung.android.animation;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.p009os.SystemClock;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.Transformation;

/* loaded from: classes5.dex */
class SemDragAndDropAnimationCore {
    static final int SELECT_HIGHLIGHT_ANIM_DURATION = 150;
    private static final String TAG = "SemDragAndDropAnimationCore";
    static final int TRANSLATE_ITEM_ANIM_DURATION = 300;
    ItemAnimator itemAnimator = new ItemAnimator();
    private ItemAnimationListener mItemAnimationListener;
    private View mView;

    interface ItemAnimationListener {
        void onItemAnimatorEnd();
    }

    SemDragAndDropAnimationCore(View view) {
        this.mView = view;
    }

    void setAnimationListener(ItemAnimationListener l) {
        this.mItemAnimationListener = l;
    }

    static abstract class ItemAnimation {
        int mDuration;
        float mProgress;
        long mStartTime;

        abstract void getTransformation(Transformation transformation);

        ItemAnimation() {
        }

        void computeAnimation(long curUpTime) {
            long elapsed = curUpTime - this.mStartTime;
            float f = elapsed / this.mDuration;
            this.mProgress = f;
            if (f > 1.0f) {
                this.mProgress = 1.0f;
            }
        }

        boolean isFinished() {
            long currentTime = SystemClock.uptimeMillis();
            return this.mStartTime + ((long) this.mDuration) <= currentTime;
        }

        int getDuration() {
            return this.mDuration;
        }

        float getProgress() {
            return this.mProgress;
        }
    }

    static class TranslateItemAnimation extends ItemAnimation {
        private int mDeltaX;
        private int mDeltaY;
        private Interpolator mInterpolator = new PathInterpolator(0.33f, 0.0f, 0.3f, 1.0f);
        private int mOffsetXDest;
        private int mOffsetYDest;

        TranslateItemAnimation() {
        }

        void translate(int offsetDestX, int deltaX, int offsetDestY, int deltaY) {
            this.mOffsetXDest = offsetDestX;
            this.mDeltaX = deltaX;
            this.mOffsetYDest = offsetDestY;
            this.mDeltaY = deltaY;
        }

        @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimation
        void getTransformation(Transformation outTransform) {
            outTransform.setTransformationType(2);
            Matrix m = outTransform.getMatrix();
            m.reset();
            float interpolatedProgress = this.mInterpolator.getInterpolation(this.mProgress);
            float translateX = this.mOffsetXDest - (this.mDeltaX * (1.0f - interpolatedProgress));
            float translateY = this.mOffsetYDest - (this.mDeltaY * (1.0f - interpolatedProgress));
            m.setTranslate(translateX, translateY);
        }

        int getDestOffsetY() {
            return this.mOffsetYDest;
        }

        int getDestOffsetX() {
            return this.mOffsetXDest;
        }

        float getCurrentTranslateX() {
            float interpolatedProgress = this.mInterpolator.getInterpolation(this.mProgress);
            return this.mOffsetXDest - (this.mDeltaX * (1.0f - interpolatedProgress));
        }

        float getCurrentTranslateY() {
            float interpolatedProgress = this.mInterpolator.getInterpolation(this.mProgress);
            return this.mOffsetYDest - (this.mDeltaY * (1.0f - interpolatedProgress));
        }

        void setStartAndDuration(int duration) {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mDuration = duration;
            if (duration == 0) {
                this.mDuration = 300;
            }
        }

        void setStartAndDuration(float durationMultiplicator) {
            int duration = Math.round(300.0f * durationMultiplicator);
            setStartAndDuration(duration);
        }
    }

    static class ItemSelectHighlightingAnimation extends ItemAnimation {
        private static final float DEFAULT_FROM_X = 1.0f;
        private static final float DEFAULT_FROM_Y = 1.0f;
        private static final float DEFAULT_TO_X = 1.08f;
        private static final float DEFAULT_TO_Y = 1.08f;
        private float mPivotX;
        private float mPivotY;
        private boolean mHalfOfAnimationPassed = false;
        private Interpolator mInterpolator = new AccelerateDecelerateInterpolator();
        private float mFromX = 1.0f;
        private float mToX = 1.08f;
        private float mFromY = 1.0f;
        private float mToY = 1.08f;

        ItemSelectHighlightingAnimation(Rect childHitRect) {
            this.mPivotX = childHitRect.exactCenterX();
            this.mPivotY = childHitRect.exactCenterY();
        }

        void setScaleUpParameters(float fromX, float toX, float fromY, float toY, float pivotX, float pivotY) {
            this.mFromX = fromX;
            this.mToX = toX;
            this.mFromY = fromY;
            this.mToY = toY;
            this.mPivotX = pivotX;
            this.mPivotY = pivotY;
        }

        @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimation
        void getTransformation(Transformation outTransform) {
            outTransform.setTransformationType(2);
            Matrix m = outTransform.getMatrix();
            m.reset();
            if (this.mProgress > 1.0f) {
                this.mProgress = 1.0f;
            }
            float interpolatedProgress = this.mInterpolator.getInterpolation(this.mProgress);
            float sx = 1.0f;
            float sy = 1.0f;
            float f = this.mFromX;
            if (f != 1.0f || this.mToX != 1.0f) {
                sx = f + ((this.mToX - f) * interpolatedProgress);
            }
            float f2 = this.mFromY;
            if (f2 != 1.0f || this.mToY != 1.0f) {
                sy = f2 + ((this.mToY - f2) * interpolatedProgress);
            }
            m.setScale(sx, sy, this.mPivotX, this.mPivotY);
        }

        private void switchToScaleDown() {
            float temp = this.mFromX;
            this.mFromX = this.mToX;
            this.mToX = temp;
            float temp2 = this.mFromY;
            this.mFromY = this.mToY;
            this.mToY = temp2;
        }

        @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimation
        void computeAnimation(long curUpTime) {
            super.computeAnimation(curUpTime);
            if (this.mProgress > 0.5f && !this.mHalfOfAnimationPassed) {
                switchToScaleDown();
                this.mHalfOfAnimationPassed = true;
            }
        }

        void setStartAndDuration(int duration) {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mDuration = duration;
            if (duration == 0) {
                this.mDuration = 150;
            }
        }
    }

    class ItemAnimator implements Runnable {
        private SparseArray<ItemAnimation> mAnimations = new SparseArray<>();
        private boolean mIsAnimating;

        ItemAnimator() {
        }

        ItemAnimation getItemAnimation(int position) {
            return this.mAnimations.get(position, null);
        }

        void putItemAnimation(int position, ItemAnimation a) {
            this.mAnimations.put(position, a);
        }

        void removeItemAnimation(int position) {
            this.mAnimations.delete(position);
        }

        void removeAll() {
            this.mAnimations.clear();
        }

        void start() {
            this.mIsAnimating = true;
            SemDragAndDropAnimationCore.this.mView.removeCallbacks(this);
            run();
        }

        @Override // java.lang.Runnable
        public void run() {
            long curTime = SystemClock.uptimeMillis();
            boolean allFinished = true;
            int size = this.mAnimations.size();
            for (int i = size - 1; i >= 0; i--) {
                int position = this.mAnimations.keyAt(i);
                ItemAnimation ia = this.mAnimations.get(position, null);
                if (ia != null) {
                    ia.computeAnimation(curTime);
                    boolean finished = ia.isFinished();
                    allFinished &= finished;
                }
            }
            SemDragAndDropAnimationCore.this.mView.invalidate();
            if (!allFinished) {
                SemDragAndDropAnimationCore.this.mView.postOnAnimation(this);
            } else if (this.mIsAnimating) {
                this.mIsAnimating = false;
                if (SemDragAndDropAnimationCore.this.mItemAnimationListener != null) {
                    SemDragAndDropAnimationCore.this.mItemAnimationListener.onItemAnimatorEnd();
                }
            }
        }
    }
}
