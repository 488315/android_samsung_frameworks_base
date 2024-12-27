package com.android.server.wm;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.SystemClock;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ClipRectAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.LocalAnimationAdapter;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowChangeAnimationSpec implements LocalAnimationAdapter.AnimationSpec {
    public final Animation mAnimation;
    public final boolean mIsThumbnail;
    public final ThreadLocal mThreadLocalTmps = ThreadLocal.withInitial(new WindowChangeAnimationSpec$$ExternalSyntheticLambda0());
    public final Rect mTmpRect = new Rect();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TmpValues {
        public final Transformation mTransformation = new Transformation();
        public final float[] mFloats = new float[9];
        public final float[] mVecs = new float[4];
    }

    public WindowChangeAnimationSpec(Rect rect, Rect rect2, DisplayInfo displayInfo, float f, boolean z) {
        Rect rect3 = new Rect(rect);
        Rect rect4 = new Rect(rect2);
        this.mIsThumbnail = z;
        long j = (int) (f * 336.0f);
        boolean z2 = (rect4.height() + (rect4.width() - rect3.width())) - rect3.height() >= 0;
        long j2 = (long) (j * 0.7f);
        float width = ((rect3.width() * 0.7f) / rect4.width()) + 0.3f;
        float height = ((rect3.height() * 0.7f) / rect4.height()) + 0.3f;
        if (z) {
            AnimationSet animationSet = new AnimationSet(true);
            AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, FullScreenMagnificationGestureHandler.MAX_SCALE);
            alphaAnimation.setDuration(j2);
            if (!z2) {
                alphaAnimation.setStartOffset(j - j2);
            }
            animationSet.addAnimation(alphaAnimation);
            float f2 = 1.0f / width;
            float f3 = 1.0f / height;
            ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f2, f3, f3);
            scaleAnimation.setDuration(j);
            animationSet.addAnimation(scaleAnimation);
            this.mAnimation = animationSet;
            animationSet.initialize(rect3.width(), rect3.height(), rect4.width(), rect4.height());
            return;
        }
        AnimationSet animationSet2 = new AnimationSet(true);
        ScaleAnimation scaleAnimation2 = new ScaleAnimation(width, 1.0f, height, 1.0f);
        scaleAnimation2.setDuration(j2);
        if (!z2) {
            scaleAnimation2.setStartOffset(j - j2);
        }
        animationSet2.addAnimation(scaleAnimation2);
        TranslateAnimation translateAnimation = new TranslateAnimation(rect3.left, rect4.left, rect3.top, rect4.top);
        translateAnimation.setDuration(j);
        animationSet2.addAnimation(translateAnimation);
        Rect rect5 = new Rect(rect3);
        Rect rect6 = new Rect(rect4);
        rect5.offsetTo(0, 0);
        rect6.offsetTo(0, 0);
        ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect5, rect6);
        clipRectAnimation.setDuration(j);
        animationSet2.addAnimation(clipRectAnimation);
        this.mAnimation = animationSet2;
        animationSet2.initialize(rect3.width(), rect3.height(), displayInfo.appWidth, displayInfo.appHeight);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final void apply(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, long j) {
        TmpValues tmpValues = (TmpValues) this.mThreadLocalTmps.get();
        if (this.mIsThumbnail) {
            this.mAnimation.getTransformation(j, tmpValues.mTransformation);
            transaction.setMatrix(surfaceControl, tmpValues.mTransformation.getMatrix(), tmpValues.mFloats);
            transaction.setAlpha(surfaceControl, tmpValues.mTransformation.getAlpha());
            return;
        }
        this.mAnimation.getTransformation(j, tmpValues.mTransformation);
        Matrix matrix = tmpValues.mTransformation.getMatrix();
        transaction.setMatrix(surfaceControl, matrix, tmpValues.mFloats);
        float[] fArr = tmpValues.mVecs;
        fArr[2] = 0.0f;
        fArr[1] = 0.0f;
        fArr[3] = 1.0f;
        fArr[0] = 1.0f;
        matrix.mapVectors(fArr);
        fArr[0] = 1.0f / fArr[0];
        fArr[3] = 1.0f / fArr[3];
        Rect clipRect = tmpValues.mTransformation.getClipRect();
        Rect rect = this.mTmpRect;
        float f = clipRect.left;
        float f2 = fArr[0];
        rect.left = (int) ((f * f2) + 0.5f);
        rect.right = (int) ((clipRect.right * f2) + 0.5f);
        float f3 = clipRect.top;
        float f4 = fArr[3];
        rect.top = (int) ((f3 * f4) + 0.5f);
        rect.bottom = (int) ((clipRect.bottom * f4) + 0.5f);
        transaction.setWindowCrop(surfaceControl, rect);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final long calculateStatusBarTransitionStartTime() {
        long uptimeMillis = SystemClock.uptimeMillis();
        return Math.max(uptimeMillis, (((long) (this.mAnimation.getDuration() * 0.99f)) + uptimeMillis) - 120);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final boolean canSkipFirstFrame() {
        return false;
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final void dump(PrintWriter printWriter, String str) {
        printWriter.print(str);
        printWriter.println(this.mAnimation.getDuration());
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final void dumpDebugInner(ProtoOutputStream protoOutputStream) {
        long start = protoOutputStream.start(1146756268033L);
        protoOutputStream.write(1138166333441L, this.mAnimation.toString());
        protoOutputStream.end(start);
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final long getDuration() {
        return this.mAnimation.getDuration();
    }

    @Override // com.android.server.wm.LocalAnimationAdapter.AnimationSpec
    public final boolean getShowWallpaper() {
        return false;
    }
}
