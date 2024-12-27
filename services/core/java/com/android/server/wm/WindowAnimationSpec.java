package com.android.server.wm;

import android.graphics.Insets;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.proto.ProtoOutputStream;
import android.view.SurfaceControl;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import java.io.PrintWriter;

public final class WindowAnimationSpec implements LocalAnimationAdapter.AnimationSpec {
    public final Animation mAnimation;
    public final boolean mCanSkipFirstFrame;
    public final Point mPosition;
    public final Rect mRootTaskBounds;
    public final int mRootTaskClipMode;
    public final ThreadLocal mThreadLocalTmps;
    public final Rect mTmpRect;
    public final float mWindowCornerRadius;

    public final class TmpValues {
        public final Transformation transformation = new Transformation();
        public final float[] floats = new float[9];
    }

    public WindowAnimationSpec(
            Animation animation, Point point, Rect rect, boolean z, int i, float f) {
        Point point2 = new Point();
        this.mPosition = point2;
        this.mThreadLocalTmps =
                ThreadLocal.withInitial(new WindowAnimationSpec$$ExternalSyntheticLambda0());
        Rect rect2 = new Rect();
        this.mRootTaskBounds = rect2;
        this.mTmpRect = new Rect();
        this.mAnimation = animation;
        if (point != null) {
            point2.set(point.x, point.y);
        }
        this.mWindowCornerRadius = f;
        this.mCanSkipFirstFrame = z;
        this.mRootTaskClipMode = i;
        if (rect != null) {
            rect2.set(rect);
        }
    }

    public WindowAnimationSpec(Animation animation, Point point, boolean z, float f) {
        this(animation, point, null, z, 1, f);
    }

    public static float findInterpolationAdjustedTargetFraction(
            Interpolator interpolator, float f) {
        float f2 = 0.5f;
        for (float f3 = 0.25f; f3 >= 0.01f; f3 /= 2.0f) {
            f2 = interpolator.getInterpolation(f2) < f ? f2 + f3 : f2 - f3;
        }
        return f2;
    }

    public static TranslateAnimation findTranslateAnimation(Animation animation) {
        if (animation instanceof TranslateAnimation) {
            return (TranslateAnimation) animation;
        }
        if (!(animation instanceof AnimationSet)) {
            return null;
        }
        AnimationSet animationSet = (AnimationSet) animation;
        for (int i = 0; i < animationSet.getAnimations().size(); i++) {
            Animation animation2 = animationSet.getAnimations().get(i);
            if (animation2 instanceof TranslateAnimation) {
                return (TranslateAnimation) animation2;
            }
        }
        return null;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final void apply(
            SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j) {
        TmpValues tmpValues = (TmpValues) this.mThreadLocalTmps.get();
        tmpValues.transformation.clear();
        this.mAnimation.getTransformation(j, tmpValues.transformation);
        Matrix matrix = tmpValues.transformation.getMatrix();
        Point point = this.mPosition;
        matrix.postTranslate(point.x, point.y);
        transaction.setMatrix(
                surfaceControl, tmpValues.transformation.getMatrix(), tmpValues.floats);
        transaction.setAlpha(surfaceControl, tmpValues.transformation.getAlpha());
        if (this.mRootTaskClipMode != 1) {
            this.mTmpRect.set(this.mRootTaskBounds);
            if (tmpValues.transformation.hasClipRect()) {
                this.mTmpRect.intersect(tmpValues.transformation.getClipRect());
            }
            Transformation transformation = tmpValues.transformation;
            Rect rect = this.mTmpRect;
            Insets insets = transformation.getInsets();
            Insets insets2 = Insets.NONE;
            Insets min = Insets.min(insets, insets2);
            if (!min.equals(insets2)) {
                rect.inset(min);
            }
            transaction.setWindowCrop(surfaceControl, this.mTmpRect);
        } else {
            if (!tmpValues.transformation.hasClipRect()) {
                return;
            }
            Rect clipRect = tmpValues.transformation.getClipRect();
            Insets insets3 = tmpValues.transformation.getInsets();
            Insets insets4 = Insets.NONE;
            Insets min2 = Insets.min(insets3, insets4);
            if (!min2.equals(insets4)) {
                clipRect.inset(min2);
            }
            transaction.setWindowCrop(surfaceControl, clipRect);
        }
        if (this.mAnimation.hasRoundedCorners()) {
            float f = this.mWindowCornerRadius;
            if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                transaction.setCornerRadius(surfaceControl, f);
            }
        }
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final WindowAnimationSpec asWindowAnimationSpec() {
        return this;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final long calculateStatusBarTransitionStartTime() {
        long startOffset;
        long j;
        TranslateAnimation findTranslateAnimation = findTranslateAnimation(this.mAnimation);
        if (findTranslateAnimation == null) {
            return SystemClock.uptimeMillis();
        }
        if (findTranslateAnimation.isXAxisTransition()
                && findTranslateAnimation.isFullWidthTranslate()) {
            startOffset =
                    findTranslateAnimation.getStartOffset()
                            + SystemClock.uptimeMillis()
                            + ((long)
                                    (findTranslateAnimation.getDuration()
                                            * findInterpolationAdjustedTargetFraction(
                                                    findTranslateAnimation.getInterpolator(),
                                                    0.5f)));
            j = 60;
        } else {
            startOffset =
                    findTranslateAnimation.getStartOffset()
                            + SystemClock.uptimeMillis()
                            + ((long)
                                    (findTranslateAnimation.getDuration()
                                            * findInterpolationAdjustedTargetFraction(
                                                    findTranslateAnimation.getInterpolator(),
                                                    0.99f)));
            j = 120;
        }
        return startOffset - j;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final boolean canSkipFirstFrame() {
        return this.mCanSkipFirstFrame;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println(this.mAnimation);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final void dumpDebugInner(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1138166333441L, this.mAnimation.toString());
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final int getBackgroundColor() {
        return this.mAnimation.getBackdropColor();
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final long getDuration() {
        return this.mAnimation.computeDurationHint();
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final boolean getShowBackground() {
        return this.mAnimation.getShowBackdrop();
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final boolean getShowWallpaper() {
        return this.mAnimation.getShowWallpaper();
    }
}
