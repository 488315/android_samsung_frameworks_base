package com.android.wm.shell.startingsurface;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.os.SystemClock;
import android.view.SurfaceControl;
import android.view.SurfaceControlViewHost;
import android.window.SplashScreenView;
import android.window.StartingWindowRemovalInfo;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransactionPool;
import com.android.wm.shell.startingsurface.StartingSurfaceDrawer;

/* loaded from: classes3.dex */
public class WindowlessSplashWindowCreator extends AbsSplashWindowCreator {
    public final TransactionPool mTransactionPool;

    public class SplashWindowRecord extends StartingSurfaceDrawer.StartingWindowRecord {
        public SurfaceControl mChildSurface;
        public final long mCreateTime;
        public final SplashScreenView mSplashView;
        public SurfaceControlViewHost mViewHost;

        public SplashWindowRecord(SurfaceControlViewHost surfaceControlViewHost, SplashScreenView splashScreenView, SurfaceControl surfaceControl, int i) {
            this.mViewHost = surfaceControlViewHost;
            this.mSplashView = splashScreenView;
            this.mChildSurface = surfaceControl;
            this.mBGColor = i;
            this.mCreateTime = SystemClock.uptimeMillis();
        }

        public final void release() {
            if (this.mChildSurface != null) {
                WindowlessSplashWindowCreator windowlessSplashWindowCreator = WindowlessSplashWindowCreator.this;
                SurfaceControl.Transaction transactionAcquire = windowlessSplashWindowCreator.mTransactionPool.acquire();
                transactionAcquire.remove(this.mChildSurface).apply();
                windowlessSplashWindowCreator.mTransactionPool.release(transactionAcquire);
                this.mChildSurface = null;
            }
            SurfaceControlViewHost surfaceControlViewHost = this.mViewHost;
            if (surfaceControlViewHost != null) {
                surfaceControlViewHost.release();
                this.mViewHost = null;
            }
        }

        @Override // com.android.wm.shell.startingsurface.StartingSurfaceDrawer.StartingWindowRecord
        public final boolean removeIfPossible(StartingWindowRemovalInfo startingWindowRemovalInfo, boolean z) {
            if (z) {
                release();
                return true;
            }
            WindowlessSplashWindowCreator.this.mSplashscreenContentDrawer.applyExitAnimation(this.mSplashView, startingWindowRemovalInfo.windowAnimationLeash, startingWindowRemovalInfo.mainFrame, new Runnable() { // from class: com.android.wm.shell.startingsurface.WindowlessSplashWindowCreator$SplashWindowRecord$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.release();
                }
            }, this.mCreateTime, 0.0f);
            return true;
        }
    }

    public WindowlessSplashWindowCreator(SplashscreenContentDrawer splashscreenContentDrawer, Context context, ShellExecutor shellExecutor, DisplayManager displayManager, StartingSurfaceDrawer.StartingWindowRecordManager startingWindowRecordManager, TransactionPool transactionPool) {
        super(splashscreenContentDrawer, context, shellExecutor, displayManager, startingWindowRecordManager);
        this.mTransactionPool = transactionPool;
    }
}
