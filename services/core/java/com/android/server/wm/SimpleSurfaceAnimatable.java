package com.android.server.wm;

import android.view.SurfaceControl;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/* loaded from: classes3.dex */
public class SimpleSurfaceAnimatable implements SurfaceAnimator.Animatable {
  public final Supplier mAnimationLeashFactory;
  public final SurfaceControl mAnimationLeashParent;
  public final Runnable mCommitTransactionRunnable;
  public final int mHeight;
  public final Consumer mOnAnimationFinished;
  public final BiConsumer mOnAnimationLeashCreated;
  public final Consumer mOnAnimationLeashLost;
  public final SurfaceControl mParentSurfaceControl;
  public final Supplier mPendingTransaction;
  public final boolean mShouldDeferAnimationFinish;
  public final SurfaceControl mSurfaceControl;
  public final Supplier mSyncTransaction;
  public final int mWidth;

  public SimpleSurfaceAnimatable(Builder builder) {
    this.mWidth = builder.mWidth;
    this.mHeight = builder.mHeight;
    this.mShouldDeferAnimationFinish = builder.mShouldDeferAnimationFinish;
    this.mAnimationLeashParent = builder.mAnimationLeashParent;
    this.mSurfaceControl = builder.mSurfaceControl;
    this.mParentSurfaceControl = builder.mParentSurfaceControl;
    this.mCommitTransactionRunnable = builder.mCommitTransactionRunnable;
    this.mAnimationLeashFactory = builder.mAnimationLeashFactory;
    this.mOnAnimationLeashCreated = builder.mOnAnimationLeashCreated;
    this.mOnAnimationLeashLost = builder.mOnAnimationLeashLost;
    this.mSyncTransaction = builder.mSyncTransactionSupplier;
    this.mPendingTransaction = builder.mPendingTransactionSupplier;
    this.mOnAnimationFinished = builder.mOnAnimationFinished;
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public SurfaceControl.Transaction getSyncTransaction() {
    return (SurfaceControl.Transaction) this.mSyncTransaction.get();
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public void commitPendingTransaction() {
    this.mCommitTransactionRunnable.run();
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public void onAnimationLeashCreated(
      SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
    BiConsumer biConsumer = this.mOnAnimationLeashCreated;
    if (biConsumer != null) {
      biConsumer.accept(transaction, surfaceControl);
    }
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public void onAnimationLeashLost(SurfaceControl.Transaction transaction) {
    Consumer consumer = this.mOnAnimationLeashLost;
    if (consumer != null) {
      consumer.accept(transaction);
    }
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public SurfaceControl.Builder makeAnimationLeash() {
    return (SurfaceControl.Builder) this.mAnimationLeashFactory.get();
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public SurfaceControl getAnimationLeashParent() {
    return this.mAnimationLeashParent;
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public SurfaceControl getSurfaceControl() {
    return this.mSurfaceControl;
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public SurfaceControl getParentSurfaceControl() {
    return this.mParentSurfaceControl;
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public int getSurfaceWidth() {
    return this.mWidth;
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public int getSurfaceHeight() {
    return this.mHeight;
  }

  @Override // com.android.server.wm.SurfaceAnimator.Animatable
  public boolean shouldDeferAnimationFinish(Runnable runnable) {
    Consumer consumer = this.mOnAnimationFinished;
    if (consumer != null) {
      consumer.accept(runnable);
    }
    return this.mShouldDeferAnimationFinish;
  }

  public class Builder {
    public Supplier mAnimationLeashFactory;
    public Runnable mCommitTransactionRunnable;
    public Supplier mPendingTransactionSupplier;
    public Supplier mSyncTransactionSupplier;
    public int mWidth = -1;
    public int mHeight = -1;
    public boolean mShouldDeferAnimationFinish = false;
    public SurfaceControl mAnimationLeashParent = null;
    public SurfaceControl mSurfaceControl = null;
    public SurfaceControl mParentSurfaceControl = null;
    public BiConsumer mOnAnimationLeashCreated = null;
    public Consumer mOnAnimationLeashLost = null;
    public Consumer mOnAnimationFinished = null;

    public Builder setCommitTransactionRunnable(Runnable runnable) {
      this.mCommitTransactionRunnable = runnable;
      return this;
    }

    public Builder setSyncTransactionSupplier(Supplier supplier) {
      this.mSyncTransactionSupplier = supplier;
      return this;
    }

    public Builder setPendingTransactionSupplier(Supplier supplier) {
      this.mPendingTransactionSupplier = supplier;
      return this;
    }

    public Builder setAnimationLeashSupplier(Supplier supplier) {
      this.mAnimationLeashFactory = supplier;
      return this;
    }

    public Builder setAnimationLeashParent(SurfaceControl surfaceControl) {
      this.mAnimationLeashParent = surfaceControl;
      return this;
    }

    public Builder setSurfaceControl(SurfaceControl surfaceControl) {
      this.mSurfaceControl = surfaceControl;
      return this;
    }

    public Builder setParentSurfaceControl(SurfaceControl surfaceControl) {
      this.mParentSurfaceControl = surfaceControl;
      return this;
    }

    public Builder setWidth(int i) {
      this.mWidth = i;
      return this;
    }

    public Builder setHeight(int i) {
      this.mHeight = i;
      return this;
    }

    public SurfaceAnimator.Animatable build() {
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
      if (this.mSurfaceControl == null) {
        throw new IllegalArgumentException("mSurfaceControl cannot be null");
      }
      return new SimpleSurfaceAnimatable(this);
    }
  }
}
