package com.android.server.wm;

import android.content.Context;
import android.os.Debug;
import android.util.Slog;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class FadeAnimationController {
    public final Context mContext;
    public final DisplayContent mDisplayContent;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class FadeAnimationAdapter extends LocalAnimationAdapter {
        public final boolean mShow;

        public FadeAnimationAdapter(
                AnonymousClass1 anonymousClass1,
                SurfaceAnimationRunner surfaceAnimationRunner,
                boolean z) {
            super(anonymousClass1, surfaceAnimationRunner);
            this.mShow = z;
        }

        @Override // com.android.server.wm.AnimationAdapter
        public boolean shouldDeferAnimationFinish() {
            return !this.mShow;
        }
    }

    public FadeAnimationController(DisplayContent displayContent) {
        this.mDisplayContent = displayContent;
        this.mContext = displayContent.mWmService.mContext;
    }

    public FadeAnimationAdapter createAdapter(
            AnonymousClass1 anonymousClass1, boolean z, WindowToken windowToken) {
        return new FadeAnimationAdapter(
                anonymousClass1, windowToken.getSurfaceAnimationRunner(), z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.server.wm.FadeAnimationController$1] */
    public final void fadeWindowToken(
            boolean z,
            WindowToken windowToken,
            AsyncRotationController$$ExternalSyntheticLambda0
                    asyncRotationController$$ExternalSyntheticLambda0) {
        if (windowToken == null || windowToken.getParent() == null) {
            return;
        }
        final Animation fadeInAnimation = z ? getFadeInAnimation() : getFadeOutAnimation();
        FadeAnimationAdapter createAdapter =
                fadeInAnimation != null
                        ? createAdapter(
                                new LocalAnimationAdapter
                                        .AnimationSpec() { // from class:
                                                           // com.android.server.wm.FadeAnimationController.1
                                    public final Transformation mTransformation =
                                            new Transformation();

                                    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                                    public final void apply(
                                            SurfaceControl.Transaction transaction,
                                            SurfaceControl surfaceControl,
                                            long j) {
                                        this.mTransformation.clear();
                                        fadeInAnimation.getTransformation(j, this.mTransformation);
                                        transaction.setAlpha(
                                                surfaceControl, this.mTransformation.getAlpha());
                                    }

                                    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                                    public final void dump(PrintWriter printWriter, String str) {
                                        printWriter.print(str);
                                        printWriter.println(fadeInAnimation);
                                    }

                                    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                                    public final void dumpDebugInner(
                                            ProtoOutputStream protoOutputStream) {
                                        long start = protoOutputStream.start(1146756268033L);
                                        protoOutputStream.write(
                                                1138166333441L, fadeInAnimation.toString());
                                        protoOutputStream.end(start);
                                    }

                                    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                                    public final long getDuration() {
                                        return fadeInAnimation.getDuration();
                                    }

                                    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
                                    public final boolean getShowWallpaper() {
                                        return true;
                                    }
                                },
                                z,
                                windowToken)
                        : null;
        if (createAdapter == null) {
            return;
        }
        StringBuilder m =
                BatteryService$$ExternalSyntheticOutline0.m(
                        "fadeWindowToken, show=", ", animationType=", z);
        m.append(SurfaceAnimator.animationTypeToString(64));
        m.append(", topWindow=");
        m.append(windowToken.getTopChild());
        m.append(", token=");
        m.append(windowToken);
        m.append(", caller=");
        m.append(Debug.getCallers(5));
        Slog.d("WindowManager", m.toString());
        windowToken.startAnimation(
                windowToken.getPendingTransaction(),
                createAdapter,
                z,
                64,
                asyncRotationController$$ExternalSyntheticLambda0);
    }

    public abstract Animation getFadeInAnimation();

    public abstract Animation getFadeOutAnimation();
}
