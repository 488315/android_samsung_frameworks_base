package com.android.systemui.screenshot.scroll;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.util.MathUtils;
import android.widget.ImageView;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import com.android.systemui.screenshot.LegacyScreenshotController$$ExternalSyntheticLambda19;
import com.android.systemui.screenshot.ScreenshotShelfViewProxy;
import com.android.systemui.screenshot.scroll.CropView;
import com.android.systemui.screenshot.scroll.ScrollCaptureController;
import com.android.systemui.screenshot.ui.ScreenshotAnimationController;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class LongScreenshotActivity$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LongScreenshotActivity f$0;
    public final /* synthetic */ float f$1;
    public final /* synthetic */ float f$2;

    public /* synthetic */ LongScreenshotActivity$$ExternalSyntheticLambda4(LongScreenshotActivity longScreenshotActivity, float f, float f2, int i) {
        this.$r8$classId = i;
        this.f$0 = longScreenshotActivity;
        this.f$1 = f;
        this.f$2 = f2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = 1;
        int i2 = 2;
        switch (this.$r8$classId) {
            case 0:
                LongScreenshotActivity longScreenshotActivity = this.f$0;
                float f = this.f$1;
                float f2 = this.f$2;
                int i3 = LongScreenshotActivity.$r8$clinit;
                longScreenshotActivity.updateImageDimensions();
                longScreenshotActivity.mEnterTransitionView.post(new LongScreenshotActivity$$ExternalSyntheticLambda4(longScreenshotActivity, f, f2, i));
                break;
            case 1:
                LongScreenshotActivity longScreenshotActivity2 = this.f$0;
                float f3 = this.f$1;
                float f4 = this.f$2;
                int i4 = LongScreenshotActivity.$r8$clinit;
                final Rect rect = new Rect();
                longScreenshotActivity2.mEnterTransitionView.getBoundsOnScreen(rect);
                ScrollCaptureExecutor$executeBatchScrollCapture$1$1$1$1 scrollCaptureExecutor$executeBatchScrollCapture$1$1$1$1 = (ScrollCaptureExecutor$executeBatchScrollCapture$1$1$1$1) longScreenshotActivity2.mLongScreenshotHolder.mTransitionDestinationCallback.getAndSet(null);
                final LongScreenshotActivity$$ExternalSyntheticLambda4 longScreenshotActivity$$ExternalSyntheticLambda4 = new LongScreenshotActivity$$ExternalSyntheticLambda4(longScreenshotActivity2, f3, f4, i2);
                final ScreenshotShelfViewProxy screenshotShelfViewProxy = ((LegacyScreenshotController$$ExternalSyntheticLambda19) scrollCaptureExecutor$executeBatchScrollCapture$1$1$1$1.$transition).f$0;
                final ScreenshotAnimationController screenshotAnimationController = screenshotShelfViewProxy.animationController;
                screenshotAnimationController.getClass();
                AnimatorSet animatorSet = new AnimatorSet();
                ValueAnimator ofFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$runLongScreenshotTransition$1
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ScreenshotAnimationController.this.scrollingScrim.setAlpha(1 - valueAnimator.getAnimatedFraction());
                    }
                });
                screenshotAnimationController.scrollTransitionPreview.setVisibility(0);
                ImageView imageView = screenshotAnimationController.scrollTransitionPreview;
                ScrollCaptureController.LongScreenshot longScreenshot = scrollCaptureExecutor$executeBatchScrollCapture$1$1$1$1.$it;
                imageView.setImageBitmap(longScreenshot.toBitmap());
                final float x = screenshotAnimationController.scrollTransitionPreview.getX();
                final float y = screenshotAnimationController.scrollTransitionPreview.getY();
                int[] locationOnScreen = screenshotAnimationController.scrollTransitionPreview.getLocationOnScreen();
                rect.offset(((int) x) - locationOnScreen[0], ((int) y) - locationOnScreen[1]);
                screenshotAnimationController.scrollTransitionPreview.setPivotX(0.0f);
                screenshotAnimationController.scrollTransitionPreview.setPivotY(0.0f);
                screenshotAnimationController.scrollTransitionPreview.setAlpha(1.0f);
                float width = screenshotAnimationController.scrollTransitionPreview.getWidth();
                ImageTileSet imageTileSet = longScreenshot.mImageTileSet;
                float width2 = width / imageTileSet.getWidth();
                Matrix matrix = new Matrix();
                matrix.setScale(width2, width2);
                matrix.postTranslate(imageTileSet.mRegion.getBounds().left * width2, imageTileSet.getTop() * width2);
                screenshotAnimationController.scrollTransitionPreview.setImageMatrix(matrix);
                final float width3 = rect.width() / screenshotAnimationController.scrollTransitionPreview.getWidth();
                ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.0f, 1.0f);
                ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$runLongScreenshotTransition$2
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        float animatedFraction = valueAnimator.getAnimatedFraction();
                        float lerp = MathUtils.lerp(1.0f, width3, animatedFraction);
                        screenshotAnimationController.scrollTransitionPreview.setScaleX(lerp);
                        screenshotAnimationController.scrollTransitionPreview.setScaleY(lerp);
                        screenshotAnimationController.scrollTransitionPreview.setX(MathUtils.lerp(x, rect.left, animatedFraction));
                        screenshotAnimationController.scrollTransitionPreview.setY(MathUtils.lerp(y, rect.top, animatedFraction));
                    }
                });
                ValueAnimator ofFloat3 = ValueAnimator.ofFloat(1.0f, 0.0f);
                ofFloat3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$runLongScreenshotTransition$3
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ScreenshotAnimationController.this.scrollTransitionPreview.setAlpha(1 - valueAnimator.getAnimatedFraction());
                    }
                });
                ofFloat2.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ui.ScreenshotAnimationController$runLongScreenshotTransition$$inlined$doOnEnd$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        longScreenshotActivity$$ExternalSyntheticLambda4.run();
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                    }
                });
                animatorSet.play(ofFloat2).with(ofFloat).before(ofFloat3);
                screenshotAnimationController.animator = animatorSet;
                animatorSet.addListener(new Animator.AnimatorListener() { // from class: com.android.systemui.screenshot.ScreenshotShelfViewProxy$startLongScreenshotTransition$$inlined$doOnEnd$1
                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        ScreenshotShelfViewProxy.ScreenshotViewCallback screenshotViewCallback = ScreenshotShelfViewProxy.this.callbacks;
                        if (screenshotViewCallback != null) {
                            screenshotViewCallback.onDismiss();
                        }
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationCancel(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationRepeat(Animator animator) {
                    }

                    @Override // android.animation.Animator.AnimatorListener
                    public final void onAnimationStart(Animator animator) {
                    }
                });
                animatorSet.start();
                break;
            default:
                LongScreenshotActivity longScreenshotActivity3 = this.f$0;
                float f5 = this.f$1;
                float f6 = this.f$2;
                longScreenshotActivity3.mPreview.animate().alpha(1.0f);
                longScreenshotActivity3.mCropView.setBoundaryPosition(f5, CropView.CropBoundary.TOP);
                longScreenshotActivity3.mCropView.setBoundaryPosition(f6, CropView.CropBoundary.BOTTOM);
                final CropView cropView = longScreenshotActivity3.mCropView;
                cropView.mEntranceInterpolation = 0.0f;
                ValueAnimator valueAnimator = new ValueAnimator();
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.screenshot.scroll.CropView$$ExternalSyntheticLambda0
                    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                    public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                        CropView cropView2 = CropView.this;
                        int i5 = CropView.$r8$clinit;
                        cropView2.getClass();
                        cropView2.mEntranceInterpolation = valueAnimator2.getAnimatedFraction();
                        cropView2.invalidate();
                    }
                });
                valueAnimator.setFloatValues(0.0f, 1.0f);
                valueAnimator.setDuration(750L);
                valueAnimator.setInterpolator(new FastOutSlowInInterpolator());
                valueAnimator.start();
                longScreenshotActivity3.mCropView.setVisibility(0);
                longScreenshotActivity3.setButtonsEnabled(true);
                break;
        }
    }
}
