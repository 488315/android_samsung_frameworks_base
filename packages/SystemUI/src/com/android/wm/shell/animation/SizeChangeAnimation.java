package com.android.wm.shell.animation;

import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.SurfaceControl;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ClipRectAnimation;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import com.android.wm.shell.bubbles.bar.BubbleBarExpandedView;
import com.android.wm.shell.shared.animation.Interpolators;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class SizeChangeAnimation {
    public final Animation mAnimation;
    public final ValueAnimator mAnimator;
    public final Animation mSnapshotAnim;
    public final float[] mTmpFloats;
    public final Matrix mTmpMatrix;
    public final Rect mTmpRect;
    public final Transformation mTmpTransform;
    public final float[] mTmpVecs;

    public SizeChangeAnimation(Rect rect, Rect rect2) {
        this(rect, rect2, 1.0f, 0.7f);
    }

    public final void apply(BubbleBarExpandedView bubbleBarExpandedView, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, SurfaceControl surfaceControl2, float f) {
        long j = (long) (f * 1000.0f);
        this.mSnapshotAnim.getTransformation(j, this.mTmpTransform);
        transaction.setMatrix(surfaceControl2, this.mTmpTransform.getMatrix(), this.mTmpFloats);
        transaction.setAlpha(surfaceControl2, this.mTmpTransform.getAlpha());
        this.mAnimation.getTransformation(j, this.mTmpTransform);
        this.mTmpMatrix.set(this.mTmpTransform.getMatrix());
        this.mTmpMatrix.preTranslate(-bubbleBarExpandedView.getTranslationX(), -bubbleBarExpandedView.getTranslationY());
        this.mTmpMatrix.postTranslate(bubbleBarExpandedView.getTranslationX(), bubbleBarExpandedView.getTranslationY());
        bubbleBarExpandedView.setAnimationMatrix(this.mTmpMatrix);
        Rect rect = this.mTmpRect;
        Transformation transformation = this.mTmpTransform;
        float[] fArr = this.mTmpVecs;
        fArr[2] = 0.0f;
        fArr[1] = 0.0f;
        fArr[3] = 1.0f;
        fArr[0] = 1.0f;
        transformation.getMatrix().mapVectors(fArr);
        fArr[0] = 1.0f / fArr[0];
        fArr[3] = 1.0f / fArr[3];
        Rect clipRect = transformation.getClipRect();
        float f2 = clipRect.left;
        float f3 = fArr[0];
        rect.left = (int) ((f2 * f3) + 0.5f);
        rect.right = (int) ((clipRect.right * f3) + 0.5f);
        float f4 = clipRect.top;
        float f5 = fArr[3];
        rect.top = (int) ((f4 * f5) + 0.5f);
        rect.bottom = (int) ((clipRect.bottom * f5) + 0.5f);
        transaction.setCrop(surfaceControl, this.mTmpRect);
        bubbleBarExpandedView.setClipBounds(this.mTmpRect);
        bubbleBarExpandedView.getViewRootImpl().applyTransactionOnDraw(transaction);
    }

    public SizeChangeAnimation(Rect rect, Rect rect2, float f, float f2) {
        this.mTmpRect = new Rect();
        this.mTmpTransform = new Transformation();
        this.mTmpMatrix = new Matrix();
        this.mTmpFloats = new float[9];
        this.mTmpVecs = new float[4];
        this.mAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        boolean z = (rect2.height() + (rect2.width() - rect.width())) - rect.height() >= 0;
        long j = (long) (1000.0f * f2);
        float f3 = 1.0f - f2;
        float height = ((rect.height() * f2) / rect2.height()) + f3;
        AnimationSet animationSet = new AnimationSet(true);
        Interpolator interpolator = Interpolators.LINEAR;
        animationSet.setInterpolator(interpolator);
        ScaleAnimation scaleAnimation = new ScaleAnimation(((rect.width() * f2) / rect2.width()) + f3, 1.0f, height, 1.0f);
        scaleAnimation.setDuration(j);
        long j2 = !z ? 1000 - j : 0L;
        scaleAnimation.setStartOffset(j2);
        animationSet.addAnimation(scaleAnimation);
        if (f != 1.0f) {
            ScaleAnimation scaleAnimation2 = new ScaleAnimation(f, 1.0f, f, 1.0f);
            scaleAnimation2.setDuration(j);
            scaleAnimation2.setStartOffset(j2);
            animationSet.addAnimation(scaleAnimation2);
        }
        TranslateAnimation translateAnimation = new TranslateAnimation(rect.left, rect2.left, rect.top, rect2.top);
        translateAnimation.setDuration(1000L);
        animationSet.addAnimation(translateAnimation);
        Rect rect3 = new Rect(rect);
        rect3.scale(f);
        Rect rect4 = new Rect(rect2);
        rect3.offsetTo(0, 0);
        rect4.offsetTo(0, 0);
        ClipRectAnimation clipRectAnimation = new ClipRectAnimation(rect3, rect4);
        clipRectAnimation.setDuration(1000L);
        animationSet.addAnimation(clipRectAnimation);
        animationSet.initialize(rect.width(), rect.height(), rect2.width(), rect2.height());
        this.mAnimation = animationSet;
        boolean z2 = (rect2.height() + (rect2.width() - rect.width())) - rect.height() >= 0;
        float width = 1.0f / (((rect.width() * f2) / rect2.width()) + f3);
        float height2 = 1.0f / (((rect.height() * f2) / rect2.height()) + f3);
        AnimationSet animationSet2 = new AnimationSet(true);
        animationSet2.setInterpolator(interpolator);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(j);
        if (!z2) {
            alphaAnimation.setStartOffset(1000 - j);
        }
        animationSet2.addAnimation(alphaAnimation);
        ScaleAnimation scaleAnimation3 = new ScaleAnimation(width, width, height2, height2);
        scaleAnimation3.setDuration(1000L);
        animationSet2.addAnimation(scaleAnimation3);
        animationSet2.initialize(rect.width(), rect.height(), rect2.width(), rect2.height());
        this.mSnapshotAnim = animationSet2;
    }
}
