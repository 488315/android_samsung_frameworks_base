package com.android.server.wm;

import android.os.SystemClock;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class LocalAnimationAdapter implements AnimationAdapter {
    public final SurfaceAnimationRunner mAnimator;
    public final AnimationSpec mSpec;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface AnimationSpec {
        void apply(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j);

        default WindowAnimationSpec asWindowAnimationSpec() {
            return null;
        }

        default long calculateStatusBarTransitionStartTime() {
            return SystemClock.uptimeMillis();
        }

        default boolean canSkipFirstFrame() {
            return false;
        }

        void dump(PrintWriter printWriter, String str);

        void dumpDebugInner(ProtoOutputStream protoOutputStream);

        default int getBackgroundColor() {
            return 0;
        }

        long getDuration();

        default float getFraction(float f) {
            float duration = getDuration();
            if (duration > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                return f / duration;
            }
            return 1.0f;
        }

        default boolean getShowBackground() {
            return false;
        }

        default boolean getShowWallpaper() {
            return false;
        }
    }

    public LocalAnimationAdapter(
            AnimationSpec animationSpec, SurfaceAnimationRunner surfaceAnimationRunner) {
        this.mSpec = animationSpec;
        this.mAnimator = surfaceAnimationRunner;
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void dump(PrintWriter printWriter, String str) {
        this.mSpec.dump(printWriter, str);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void dumpDebug$1(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268033L);
        AnimationSpec animationSpec = this.mSpec;
        animationSpec.getClass();
        long start2 = protoOutputStream.start(1146756268033L);
        animationSpec.dumpDebugInner(protoOutputStream);
        protoOutputStream.end(start2);
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final int getBackgroundColor() {
        return this.mSpec.getBackgroundColor();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final long getDurationHint() {
        return this.mSpec.getDuration();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final boolean getShowBackground() {
        return this.mSpec.getShowBackground();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final boolean getShowWallpaper() {
        return this.mSpec.getShowWallpaper();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final long getStatusBarTransitionsStartTime() {
        return this.mSpec.calculateStatusBarTransitionStartTime();
    }

    @Override // com.android.server.wm.AnimationAdapter
    public final void onAnimationCancelled(SurfaceControl surfaceControl) {
        this.mAnimator.onAnimationCancelled(surfaceControl);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.wm.LocalAnimationAdapter$$ExternalSyntheticLambda0] */
    @Override // com.android.server.wm.AnimationAdapter
    public void startAnimation(
            SurfaceControl surfaceControl,
            SurfaceControl.Transaction transaction,
            final int i,
            final SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback) {
        this.mAnimator.startAnimation(
                this.mSpec,
                surfaceControl,
                transaction,
                new Runnable() { // from class:
                                 // com.android.server.wm.LocalAnimationAdapter$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        LocalAnimationAdapter localAnimationAdapter = LocalAnimationAdapter.this;
                        SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback2 =
                                onAnimationFinishedCallback;
                        int i2 = i;
                        localAnimationAdapter.getClass();
                        onAnimationFinishedCallback2.onAnimationFinished(i2, localAnimationAdapter);
                    }
                });
    }
}
