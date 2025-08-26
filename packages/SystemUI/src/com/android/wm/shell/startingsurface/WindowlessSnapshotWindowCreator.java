package com.android.wm.shell.startingsurface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.wm.shell.startingsurface.WindowlessSnapshotWindowCreator;

/* loaded from: classes3.dex */
public class WindowlessSnapshotWindowCreator {
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final SplashscreenContentDrawer mSplashscreenContentDrawer;
    public final StartingSurfaceDrawer.StartingWindowRecordManager mStartingWindowRecordManager;
    public final TransactionPool mTransactionPool;

    public class SnapshotWindowRecord extends StartingSurfaceDrawer.SnapshotRecord {
        public SurfaceControl mChildSurface;
        public final boolean mHasImeSurface;
        public SurfaceControl mRootSurface;
        public SurfaceControlViewHost mViewHost;

        public SnapshotWindowRecord(SurfaceControlViewHost surfaceControlViewHost, SurfaceControl surfaceControl, SurfaceControl surfaceControl2, int i, boolean z, int i2, ShellExecutor shellExecutor, int i3, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
            super(i2, shellExecutor, i3, startingWindowRecordManager);
            this.mViewHost = surfaceControlViewHost;
            this.mRootSurface = surfaceControl;
            this.mChildSurface = surfaceControl2;
            this.mBGColor = i;
            this.mHasImeSurface = z;
        }

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.SnapshotRecord
        public final boolean hasImeSurface() {
            return this.mHasImeSurface;
        }

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.SnapshotRecord
        public final void removeImmediately() {
            super.removeImmediately();
            final ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            valueAnimatorOfFloat.setDuration(233L);
            final SurfaceControl.Transaction transactionAcquire = WindowlessSnapshotWindowCreator.this.mTransactionPool.acquire();
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.startingsurface.WindowlessSnapshotWindowCreator$SnapshotWindowRecord$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    WindowlessSnapshotWindowCreator.SnapshotWindowRecord snapshotWindowRecord = this.f$0;
                    ValueAnimator valueAnimator2 = valueAnimatorOfFloat;
                    SurfaceControl.Transaction transaction = transactionAcquire;
                    SurfaceControl surfaceControl = snapshotWindowRecord.mChildSurface;
                    if (surfaceControl == null || !surfaceControl.isValid()) {
                        valueAnimator2.cancel();
                    } else {
                        transaction.setAlpha(snapshotWindowRecord.mChildSurface, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                        transaction.apply();
                    }
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.startingsurface.WindowlessSnapshotWindowCreator.SnapshotWindowRecord.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    WindowlessSnapshotWindowCreator.this.mTransactionPool.release(transactionAcquire);
                    SnapshotWindowRecord snapshotWindowRecord = SnapshotWindowRecord.this;
                    if (snapshotWindowRecord.mChildSurface != null) {
                        SurfaceControl.Transaction transactionAcquire2 = WindowlessSnapshotWindowCreator.this.mTransactionPool.acquire();
                        transactionAcquire2.remove(SnapshotWindowRecord.this.mChildSurface).apply();
                        WindowlessSnapshotWindowCreator.this.mTransactionPool.release(transactionAcquire2);
                        SnapshotWindowRecord.this.mChildSurface = null;
                    }
                    SurfaceControl surfaceControl = SnapshotWindowRecord.this.mRootSurface;
                    if (surfaceControl != null && surfaceControl.isValid()) {
                        SnapshotWindowRecord.this.mRootSurface.release();
                    }
                    SnapshotWindowRecord snapshotWindowRecord2 = SnapshotWindowRecord.this;
                    snapshotWindowRecord2.mRootSurface = null;
                    SurfaceControlViewHost surfaceControlViewHost = snapshotWindowRecord2.mViewHost;
                    if (surfaceControlViewHost != null) {
                        surfaceControlViewHost.release();
                        SnapshotWindowRecord.this.mViewHost = null;
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SurfaceControl surfaceControl = SnapshotWindowRecord.this.mChildSurface;
                    if (surfaceControl == null || !surfaceControl.isValid()) {
                        valueAnimatorOfFloat.cancel();
                    }
                }
            });
            valueAnimatorOfFloat.start();
        }
    }

    public WindowlessSnapshotWindowCreator(StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager, Context context, DisplayManager displayManager, SplashscreenContentDrawer splashscreenContentDrawer, TransactionPool transactionPool) {
        this.mStartingWindowRecordManager = startingWindowRecordManager;
        this.mContext = context;
        this.mDisplayManager = displayManager;
        this.mSplashscreenContentDrawer = splashscreenContentDrawer;
        this.mTransactionPool = transactionPool;
    }
}
