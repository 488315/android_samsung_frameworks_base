package com.android.server.wm;

import android.view.SurfaceControl;

import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class SimpleSurfaceAnimatable implements SurfaceAnimator.Animatable {
    public final Supplier mAnimationLeashFactory;
    public final SurfaceControl mAnimationLeashParent;
    public final Runnable mCommitTransactionRunnable;
    public final int mHeight;
    public final SurfaceControl mParentSurfaceControl;
    public final Supplier mPendingTransaction;
    public final SurfaceControl mSurfaceControl;
    public final Supplier mSyncTransaction;
    public final int mWidth;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Builder {
        public Supplier mAnimationLeashFactory;
        public SurfaceControl mAnimationLeashParent;
        public Runnable mCommitTransactionRunnable;
        public int mHeight;
        public SurfaceControl mParentSurfaceControl;
        public Supplier mPendingTransactionSupplier;
        public SurfaceControl mSurfaceControl;
        public Supplier mSyncTransactionSupplier;
        public int mWidth;

        public final SimpleSurfaceAnimatable build() {
            if (this.mSyncTransactionSupplier == null) {
                throw new IllegalArgumentException("mSyncTransactionSupplier cannot be null");
            }
            if (this.mPendingTransactionSupplier == null) {
                throw new IllegalArgumentException("mPendingTransactionSupplier cannot be null");
            }
            if (this.mAnimationLeashFactory == null) {
                throw new IllegalArgumentException("mAnimationLeashFactory cannot be null");
            }
            if (this.mCommitTransactionRunnable == null) {
                throw new IllegalArgumentException("mCommitTransactionRunnable cannot be null");
            }
            if (this.mSurfaceControl != null) {
                return new SimpleSurfaceAnimatable(this);
            }
            throw new IllegalArgumentException("mSurfaceControl cannot be null");
        }
    }

    public SimpleSurfaceAnimatable(Builder builder) {
        this.mWidth = builder.mWidth;
        this.mHeight = builder.mHeight;
        this.mAnimationLeashParent = builder.mAnimationLeashParent;
        this.mSurfaceControl = builder.mSurfaceControl;
        this.mParentSurfaceControl = builder.mParentSurfaceControl;
        this.mCommitTransactionRunnable = builder.mCommitTransactionRunnable;
        this.mAnimationLeashFactory = builder.mAnimationLeashFactory;
        this.mSyncTransaction = builder.mSyncTransactionSupplier;
        this.mPendingTransaction = builder.mPendingTransactionSupplier;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final void commitPendingTransaction() {
        this.mCommitTransactionRunnable.run();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl getAnimationLeashParent() {
        return this.mAnimationLeashParent;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl getParentSurfaceControl() {
        return this.mParentSurfaceControl;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl getSurfaceControl() {
        return this.mSurfaceControl;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final int getSurfaceHeight() {
        return this.mHeight;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final int getSurfaceWidth() {
        return this.mWidth;
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl.Transaction getSyncTransaction() {
        return (SurfaceControl.Transaction) this.mSyncTransaction.get();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final SurfaceControl.Builder makeAnimationLeash() {
        return (SurfaceControl.Builder) this.mAnimationLeashFactory.get();
    }

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashCreated(
            SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {}

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final void onAnimationLeashLost(SurfaceControl.Transaction transaction) {}

    @Override // com.android.server.wm.SurfaceAnimator.Animatable
    public final boolean shouldDeferAnimationFinish(Runnable runnable) {
        return false;
    }
}
