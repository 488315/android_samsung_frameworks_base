package com.android.server.wm;

import android.app.ActivityManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.proto.ProtoOutputStream;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WallpaperAnimationAdapter implements AnimationAdapter {
    public final Consumer mAnimationCanceledRunnable;
    public SurfaceControl mCapturedLeash;
    public SurfaceAnimator.OnAnimationFinishedCallback mCapturedLeashFinishCallback;
    public final long mDurationHint;
    public int mLastAnimationType;
    public final long mStatusBarTransitionDelay;
    public RemoteAnimationTarget mTarget;
    public final WallpaperWindowToken mWallpaperToken;

    public WallpaperAnimationAdapter(
            WallpaperWindowToken wallpaperWindowToken, long j, long j2, Consumer consumer) {
        this.mWallpaperToken = wallpaperWindowToken;
        this.mDurationHint = j;
        this.mStatusBarTransitionDelay = j2;
        this.mAnimationCanceledRunnable = consumer;
    }

    public static RemoteAnimationTarget[] startWallpaperAnimations(
            DisplayContent displayContent,
            final long j,
            final long j2,
            final Consumer consumer,
            final ArrayList arrayList) {
        if (displayContent.mWallpaperController.isWallpaperVisible()) {
            final ArrayList arrayList2 = new ArrayList();
            displayContent.forAllWallpaperWindows(
                    new Consumer() { // from class:
                                     // com.android.server.wm.WallpaperAnimationAdapter$$ExternalSyntheticLambda0
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj) {
                            long j3 = j;
                            long j4 = j2;
                            Consumer consumer2 = consumer;
                            ArrayList arrayList3 = arrayList2;
                            ArrayList arrayList4 = arrayList;
                            WallpaperWindowToken wallpaperWindowToken = (WallpaperWindowToken) obj;
                            WallpaperAnimationAdapter wallpaperAnimationAdapter =
                                    new WallpaperAnimationAdapter(
                                            wallpaperWindowToken, j3, j4, consumer2);
                            wallpaperWindowToken.startAnimation(
                                    wallpaperWindowToken.getPendingTransaction(),
                                    wallpaperAnimationAdapter,
                                    false,
                                    16);
                            RemoteAnimationTarget remoteAnimationTarget =
                                    new RemoteAnimationTarget(
                                            -1,
                                            -1,
                                            wallpaperAnimationAdapter.mCapturedLeash,
                                            false,
                                            (Rect) null,
                                            (Rect) null,
                                            wallpaperWindowToken.getPrefixOrderIndex(),
                                            new Point(),
                                            (Rect) null,
                                            (Rect) null,
                                            wallpaperWindowToken.getWindowConfiguration(),
                                            true,
                                            (SurfaceControl) null,
                                            (Rect) null,
                                            (ActivityManager.RunningTaskInfo) null,
                                            false);
                            wallpaperAnimationAdapter.mTarget = remoteAnimationTarget;
                            arrayList3.add(remoteAnimationTarget);
                            arrayList4.add(wallpaperAnimationAdapter);
                        }
                    });
            return (RemoteAnimationTarget[])
                    arrayList2.toArray(new RemoteAnimationTarget[arrayList2.size()]);
        }
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS,
                    1964980935866463086L,
                    0,
                    null,
                    String.valueOf(displayContent));
        }
        return new RemoteAnimationTarget[0];
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("token=");
        printWriter.println(this.mWallpaperToken);
        if (this.mTarget == null) {
            printWriter.print(str);
            printWriter.println("Target: null");
            return;
        }
        printWriter.print(str);
        printWriter.println("Target:");
        this.mTarget.dump(printWriter, str + "  ");
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void dumpDebug$1(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268034L);
        RemoteAnimationTarget remoteAnimationTarget = this.mTarget;
        if (remoteAnimationTarget != null) {
            remoteAnimationTarget.dumpDebug(protoOutputStream, 1146756268033L);
        }
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final long getDurationHint() {
        return this.mDurationHint;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final boolean getShowWallpaper() {
        return false;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final long getStatusBarTransitionsStartTime() {
        return SystemClock.uptimeMillis() + this.mStatusBarTransitionDelay;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void onAnimationCancelled(SurfaceControl surfaceControl) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 8030745595351281943L, 0, null, null);
        }
        this.mAnimationCanceledRunnable.accept(this);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void startAnimation(
            SurfaceControl surfaceControl,
            SurfaceControl.Transaction transaction,
            int i,
            SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 8131665298937888044L, 0, null, null);
        }
        transaction.setLayer(surfaceControl, this.mWallpaperToken.getPrefixOrderIndex());
        this.mCapturedLeash = surfaceControl;
        this.mCapturedLeashFinishCallback = onAnimationFinishedCallback;
        this.mLastAnimationType = i;
    }
}
