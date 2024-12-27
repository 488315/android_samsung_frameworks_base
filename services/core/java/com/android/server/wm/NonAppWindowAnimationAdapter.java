package com.android.server.wm;

import android.app.ActivityManager;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.proto.ProtoOutputStream;
import android.view.RemoteAnimationTarget;
import android.view.SurfaceControl;

import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;

import java.io.PrintWriter;

public final class NonAppWindowAnimationAdapter implements AnimationAdapter {
    public SurfaceControl mCapturedLeash;
    public SurfaceAnimator.OnAnimationFinishedCallback mCapturedLeashFinishCallback;
    public final long mDurationHint;
    public int mLastAnimationType;
    public final long mStatusBarTransitionDelay;
    public RemoteAnimationTarget mTarget;
    public final WindowContainer mWindowContainer;

    public NonAppWindowAnimationAdapter(WindowContainer windowContainer, long j, long j2) {
        this.mWindowContainer = windowContainer;
        this.mDurationHint = j;
        this.mStatusBarTransitionDelay = j2;
    }

    public static boolean shouldAttachNavBarToApp(
            WindowManagerService windowManagerService, DisplayContent displayContent, int i) {
        return (i == 8 || i == 10 || i == 12)
                && displayContent.mDisplayPolicy.shouldAttachNavBarToAppDuringTransition()
                && windowManagerService.mRecentsAnimationController == null
                && displayContent.getAsyncRotationController() == null;
    }

    public final RemoteAnimationTarget createRemoteAnimationTarget() {
        SurfaceControl surfaceControl = this.mCapturedLeash;
        Rect rect = new Rect();
        WindowContainer windowContainer = this.mWindowContainer;
        RemoteAnimationTarget remoteAnimationTarget =
                new RemoteAnimationTarget(
                        -1,
                        -1,
                        surfaceControl,
                        false,
                        rect,
                        (Rect) null,
                        windowContainer.getPrefixOrderIndex(),
                        windowContainer.getLastSurfacePosition(),
                        windowContainer.getBounds(),
                        (Rect) null,
                        windowContainer.getWindowConfiguration(),
                        true,
                        (SurfaceControl) null,
                        (Rect) null,
                        (ActivityManager.RunningTaskInfo) null,
                        false,
                        windowContainer.getWindowType());
        this.mTarget = remoteAnimationTarget;
        return remoteAnimationTarget;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.print("windowContainer=");
        printWriter.println(this.mWindowContainer);
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
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 705955074330737483L, 0, null, null);
        }
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void startAnimation(
            SurfaceControl surfaceControl,
            SurfaceControl.Transaction transaction,
            int i,
            SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_REMOTE_ANIMATIONS_enabled[0]) {
            ProtoLogImpl_54989576.d(
                    ProtoLogGroup.WM_DEBUG_REMOTE_ANIMATIONS, 3788905348567806832L, 0, null, null);
        }
        this.mCapturedLeash = surfaceControl;
        this.mCapturedLeashFinishCallback = onAnimationFinishedCallback;
        this.mLastAnimationType = i;
    }
}
