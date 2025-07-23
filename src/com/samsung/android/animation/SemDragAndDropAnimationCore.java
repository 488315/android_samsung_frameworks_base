package com.samsung.android.animation;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.view.animation.Transformation;

/* loaded from: classes6.dex */
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

    void setAnimationListener(ItemAnimationListener itemAnimationListener) {
        this.mItemAnimationListener = itemAnimationListener;
    }

    static abstract class ItemAnimation {
        int mDuration;
        float mProgress;
        long mStartTime;

        abstract void getTransformation(Transformation transformation);

        ItemAnimation() {
        }

        void computeAnimation(long j) {
            float f = (j - this.mStartTime) / this.mDuration;
            this.mProgress = f;
            if (f > 1.0f) {
                this.mProgress = 1.0f;
            }
        }

        boolean isFinished() {
            return this.mStartTime + ((long) this.mDuration) <= SystemClock.uptimeMillis();
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

        void translate(int i, int i2, int i3, int i4) {
            this.mOffsetXDest = i;
            this.mDeltaX = i2;
            this.mOffsetYDest = i3;
            this.mDeltaY = i4;
        }

        @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimation
        void getTransformation(Transformation transformation) {
            transformation.setTransformationType(2);
            Matrix matrix = transformation.getMatrix();
            matrix.reset();
            float interpolation = 1.0f - this.mInterpolator.getInterpolation(this.mProgress);
            matrix.setTranslate(this.mOffsetXDest - (this.mDeltaX * interpolation), this.mOffsetYDest - (this.mDeltaY * interpolation));
        }

        int getDestOffsetY() {
            return this.mOffsetYDest;
        }

        int getDestOffsetX() {
            return this.mOffsetXDest;
        }

        float getCurrentTranslateX() {
            return this.mOffsetXDest - (this.mDeltaX * (1.0f - this.mInterpolator.getInterpolation(this.mProgress)));
        }

        float getCurrentTranslateY() {
            return this.mOffsetYDest - (this.mDeltaY * (1.0f - this.mInterpolator.getInterpolation(this.mProgress)));
        }

        void setStartAndDuration(int i) {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mDuration = i;
            if (i == 0) {
                this.mDuration = 300;
            }
        }

        void setStartAndDuration(float f) {
            setStartAndDuration(Math.round(f * 300.0f));
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

        ItemSelectHighlightingAnimation(Rect rect) {
            this.mPivotX = rect.exactCenterX();
            this.mPivotY = rect.exactCenterY();
        }

        void setScaleUpParameters(float f, float f2, float f3, float f4, float f5, float f6) {
            this.mFromX = f;
            this.mToX = f2;
            this.mFromY = f3;
            this.mToY = f4;
            this.mPivotX = f5;
            this.mPivotY = f6;
        }

        @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimation
        void getTransformation(Transformation transformation) {
            transformation.setTransformationType(2);
            Matrix matrix = transformation.getMatrix();
            matrix.reset();
            if (this.mProgress > 1.0f) {
                this.mProgress = 1.0f;
            }
            float interpolation = this.mInterpolator.getInterpolation(this.mProgress);
            float f = this.mFromX;
            float f2 = (f == 1.0f && this.mToX == 1.0f) ? 1.0f : f + ((this.mToX - f) * interpolation);
            float f3 = this.mFromY;
            matrix.setScale(f2, (f3 == 1.0f && this.mToY == 1.0f) ? 1.0f : ((this.mToY - f3) * interpolation) + f3, this.mPivotX, this.mPivotY);
        }

        private void switchToScaleDown() {
            float f = this.mFromX;
            this.mFromX = this.mToX;
            this.mToX = f;
            float f2 = this.mFromY;
            this.mFromY = this.mToY;
            this.mToY = f2;
        }

        @Override // com.samsung.android.animation.SemDragAndDropAnimationCore.ItemAnimation
        void computeAnimation(long j) {
            super.computeAnimation(j);
            if (this.mProgress <= 0.5f || this.mHalfOfAnimationPassed) {
                return;
            }
            switchToScaleDown();
            this.mHalfOfAnimationPassed = true;
        }

        void setStartAndDuration(int i) {
            this.mStartTime = SystemClock.uptimeMillis();
            this.mDuration = i;
            if (i == 0) {
                this.mDuration = 150;
            }
        }
    }

    class ItemAnimator implements Runnable {
        private SparseArray<ItemAnimation> mAnimations = new SparseArray<>();
        private boolean mIsAnimating;

        ItemAnimator() {
        }

        ItemAnimation getItemAnimation(int i) {
            return this.mAnimations.get(i, null);
        }

        void putItemAnimation(int i, ItemAnimation itemAnimation) {
            this.mAnimations.put(i, itemAnimation);
        }

        void removeItemAnimation(int i) {
            this.mAnimations.delete(i);
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
            long uptimeMillis = SystemClock.uptimeMillis();
            boolean z = true;
            for (int size = this.mAnimations.size() - 1; size >= 0; size--) {
                ItemAnimation itemAnimation = this.mAnimations.get(this.mAnimations.keyAt(size), null);
                if (itemAnimation != null) {
                    itemAnimation.computeAnimation(uptimeMillis);
                    z &= itemAnimation.isFinished();
                }
            }
            SemDragAndDropAnimationCore.this.mView.invalidate();
            if (!z) {
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
