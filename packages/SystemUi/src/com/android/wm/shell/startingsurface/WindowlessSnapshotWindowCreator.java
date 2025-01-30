package com.android.wm.shell.startingsurface;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.TransactionPool;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;
import com.android.wm.shell.startingsurface.WindowlessSnapshotWindowCreator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WindowlessSnapshotWindowCreator {
    public final Context mContext;
    public final DisplayManager mDisplayManager;
    public final SplashscreenContentDrawer mSplashscreenContentDrawer;
    public final StartingSurfaceDrawer.StartingWindowRecordManager mStartingWindowRecordManager;
    public final TransactionPool mTransactionPool;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SnapshotWindowRecord extends StartingSurfaceDrawer.SnapshotRecord {
        public SurfaceControl mChildSurface;
        public final boolean mHasImeSurface;
        public SurfaceControlViewHost mViewHost;

        public SnapshotWindowRecord(SurfaceControlViewHost surfaceControlViewHost, SurfaceControl surfaceControl, int i, boolean z, int i2, ShellExecutor shellExecutor, int i3, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager) {
            super(i2, shellExecutor, i3, startingWindowRecordManager);
            this.mViewHost = surfaceControlViewHost;
            this.mChildSurface = surfaceControl;
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
            final ValueAnimator ofFloat = ValueAnimator.ofFloat(1.0f, 0.0f);
            ofFloat.setDuration(233L);
            final SurfaceControl.Transaction acquire = WindowlessSnapshotWindowCreator.this.mTransactionPool.acquire();
            ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.wm.shell.startingsurface.WindowlessSnapshotWindowCreator$SnapshotWindowRecord$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    WindowlessSnapshotWindowCreator.SnapshotWindowRecord snapshotWindowRecord = WindowlessSnapshotWindowCreator.SnapshotWindowRecord.this;
                    ValueAnimator valueAnimator2 = ofFloat;
                    SurfaceControl.Transaction transaction = acquire;
                    SurfaceControl surfaceControl = snapshotWindowRecord.mChildSurface;
                    if (surfaceControl == null || !surfaceControl.isValid()) {
                        valueAnimator2.cancel();
                    } else {
                        transaction.setAlpha(snapshotWindowRecord.mChildSurface, ((Float) valueAnimator.getAnimatedValue()).floatValue());
                        transaction.apply();
                    }
                }
            });
            ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.wm.shell.startingsurface.WindowlessSnapshotWindowCreator.SnapshotWindowRecord.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    WindowlessSnapshotWindowCreator.this.mTransactionPool.release(acquire);
                    SnapshotWindowRecord snapshotWindowRecord = SnapshotWindowRecord.this;
                    if (snapshotWindowRecord.mChildSurface != null) {
                        SurfaceControl.Transaction acquire2 = WindowlessSnapshotWindowCreator.this.mTransactionPool.acquire();
                        acquire2.remove(SnapshotWindowRecord.this.mChildSurface).apply();
                        WindowlessSnapshotWindowCreator.this.mTransactionPool.release(acquire2);
                        SnapshotWindowRecord.this.mChildSurface = null;
                    }
                    SurfaceControlViewHost surfaceControlViewHost = SnapshotWindowRecord.this.mViewHost;
                    if (surfaceControlViewHost != null) {
                        surfaceControlViewHost.release();
                        SnapshotWindowRecord.this.mViewHost = null;
                    }
                }

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator) {
                    SurfaceControl surfaceControl = SnapshotWindowRecord.this.mChildSurface;
                    if (surfaceControl == null || !surfaceControl.isValid()) {
                        ofFloat.cancel();
                    }
                }
            });
            ofFloat.start();
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
