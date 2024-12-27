package com.android.server.wm;

import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public interface AnimationAdapter {
    void dump(PrintWriter printWriter, String str);

    default void dumpDebug(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268035L);
        dumpDebug$1(protoOutputStream);
        protoOutputStream.end(start);
    }

    void dumpDebug$1(ProtoOutputStream protoOutputStream);

    default int getBackgroundColor() {
        return 0;
    }

    long getDurationHint();

    default boolean getShowBackground() {
        return false;
    }

    boolean getShowWallpaper();

    long getStatusBarTransitionsStartTime();

    void onAnimationCancelled(SurfaceControl surfaceControl);

    default boolean shouldDeferAnimationFinish() {
        return false;
    }

    void startAnimation(
            SurfaceControl surfaceControl,
            SurfaceControl.Transaction transaction,
            int i,
            SurfaceAnimator.OnAnimationFinishedCallback onAnimationFinishedCallback);
}
