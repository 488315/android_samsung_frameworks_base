package com.android.systemui;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.IndentingPrintWriter;
import android.view.View;
import androidx.core.graphics.ColorUtils;
import com.android.app.animation.Interpolators;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.settingslib.Utils;
import com.android.systemui.FaceScanningOverlay;
import com.android.systemui.ScreenDecorations;
import com.android.systemui.biometrics.AuthController;
import com.android.systemui.log.ScreenDecorationsLogger;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.util.DumpUtilsKt;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class FaceScanningOverlay extends ScreenDecorations.DisplayCutoutView {
    public static final Companion Companion = new Companion(null);
    public final AuthController authController;
    public ValueAnimator cameraProtectionAnimator;
    public int cameraProtectionColor;
    public int faceScanningAnimColor;
    public Runnable hideOverlayRunnable;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final ScreenDecorationsLogger logger;
    public final Executor mainExecutor;
    public AnimatorSet rimAnimator;
    public final Paint rimPaint;
    public float rimProgress;
    public final RectF rimRect;
    public boolean showScanningAnim;
    public final StatusBarStateController statusBarStateController;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public static final void access$scalePath(Companion companion, Path path, float f) {
            companion.getClass();
            Matrix matrix = new Matrix();
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            matrix.setScale(f, f, rectF.centerX(), rectF.centerY());
            path.transform(matrix);
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FaceScanningOverlay(Context context, int i, StatusBarStateController statusBarStateController, KeyguardUpdateMonitor keyguardUpdateMonitor, Executor executor, ScreenDecorationsLogger screenDecorationsLogger, AuthController authController) {
        super(context, i);
        this.statusBarStateController = statusBarStateController;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mainExecutor = executor;
        this.logger = screenDecorationsLogger;
        this.authController = authController;
        this.rimPaint = new Paint();
        this.rimProgress = 0.5f;
        this.rimRect = new RectF();
        this.cameraProtectionColor = -16777216;
        this.faceScanningAnimColor = Utils.getColorAttrDefaultColor(context, R.^attr-private.materialColorSecondary, 0);
        setVisibility(4);
    }

    public final ValueAnimator createRimDisappearAnimator(float f, long j, TimeInterpolator timeInterpolator) {
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.rimProgress, f);
        ofFloat.setDuration(j);
        ofFloat.setInterpolator(timeInterpolator);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$createRimDisappearAnimator$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                FaceScanningOverlay.Companion companion = FaceScanningOverlay.Companion;
                faceScanningOverlay.getClass();
                faceScanningOverlay.rimProgress = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                faceScanningOverlay.invalidate();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.FaceScanningOverlay$createRimDisappearAnimator$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                faceScanningOverlay.rimProgress = 0.5f;
                faceScanningOverlay.invalidate();
            }
        });
        return ofFloat;
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public final void drawCutoutProtection(Canvas canvas) {
        if (this.protectionRect.isEmpty()) {
            return;
        }
        if (this.rimProgress > 0.5f) {
            Path path = new Path(this.protectionPath);
            Companion.access$scalePath(Companion, path, this.rimProgress);
            this.rimPaint.setStyle(Paint.Style.FILL);
            int alpha = this.rimPaint.getAlpha();
            this.rimPaint.setColor(ColorUtils.blendARGB(this.statusBarStateController.getDozeAmount(), this.faceScanningAnimColor, -1));
            this.rimPaint.setAlpha(alpha);
            canvas.drawPath(path, this.rimPaint);
        }
        if (this.cameraProtectionProgress > 0.5f) {
            Path path2 = new Path(this.protectionPath);
            Companion.access$scalePath(Companion, path2, this.cameraProtectionProgress);
            this.paint.setStyle(Paint.Style.FILL);
            this.paint.setColor(this.cameraProtectionColor);
            canvas.drawPath(path2, this.paint);
        }
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public final void dump(PrintWriter printWriter) {
        IndentingPrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        asIndenting.increaseIndent();
        asIndenting.println("FaceScanningOverlay:");
        super.dump(asIndenting);
        asIndenting.println("rimProgress=" + this.rimProgress);
        asIndenting.println("rimRect=" + this.rimRect);
        asIndenting.println("this=" + this);
        asIndenting.decreaseIndent();
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public final void enableShowProtection(boolean z) {
        AnimatorSet animatorSet;
        boolean z2 = this.keyguardUpdateMonitor.isFaceDetectionRunning() || this.authController.isShowing();
        boolean isFaceAuthenticated = this.keyguardUpdateMonitor.getIsFaceAuthenticated();
        boolean z3 = z2 && z;
        if (z3 == this.showScanningAnim) {
            return;
        }
        this.logger.cameraProtectionShownOrHidden(z3, this.keyguardUpdateMonitor.isFaceDetectionRunning(), this.authController.isShowing(), isFaceAuthenticated, z, this.showScanningAnim);
        this.showScanningAnim = z3;
        updateProtectionBoundingPath();
        if (this.showScanningAnim) {
            setVisibility(0);
            requestLayout();
        }
        ValueAnimator valueAnimator = this.cameraProtectionAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
        ValueAnimator ofFloat = ValueAnimator.ofFloat(this.cameraProtectionProgress, z3 ? 1.0f : 0.5f);
        ofFloat.setStartDelay(this.showScanningAnim ? 0L : isFaceAuthenticated ? 400L : 200L);
        ofFloat.setDuration(this.showScanningAnim ? 250L : isFaceAuthenticated ? 500L : 300L);
        ofFloat.setInterpolator(this.showScanningAnim ? Interpolators.STANDARD_ACCELERATE : isFaceAuthenticated ? Interpolators.STANDARD : Interpolators.STANDARD_DECELERATE);
        ofFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$enableShowProtection$1$1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                FaceScanningOverlay.Companion companion = FaceScanningOverlay.Companion;
                faceScanningOverlay.getClass();
                faceScanningOverlay.cameraProtectionProgress = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                faceScanningOverlay.invalidate();
            }
        });
        ofFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.FaceScanningOverlay$enableShowProtection$1$2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                faceScanningOverlay.cameraProtectionAnimator = null;
                if (faceScanningOverlay.showScanningAnim) {
                    return;
                }
                faceScanningOverlay.setVisibility(4);
                Runnable runnable = faceScanningOverlay.hideOverlayRunnable;
                if (runnable != null) {
                    runnable.run();
                }
                faceScanningOverlay.hideOverlayRunnable = null;
                faceScanningOverlay.requestLayout();
            }
        });
        this.cameraProtectionAnimator = ofFloat;
        AnimatorSet animatorSet2 = this.rimAnimator;
        if (animatorSet2 != null) {
            animatorSet2.cancel();
        }
        if (this.showScanningAnim) {
            animatorSet = new AnimatorSet();
            ValueAnimator ofFloat2 = ValueAnimator.ofFloat(1.0f, 1.125f);
            ofFloat2.setDuration(250L);
            ofFloat2.setInterpolator(Interpolators.STANDARD_DECELERATE);
            ofFloat2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$createRimAppearAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                    FaceScanningOverlay.Companion companion = FaceScanningOverlay.Companion;
                    faceScanningOverlay.getClass();
                    faceScanningOverlay.rimProgress = ((Float) valueAnimator2.getAnimatedValue()).floatValue();
                    faceScanningOverlay.invalidate();
                }
            });
            animatorSet.playSequentially(this.cameraProtectionAnimator, ofFloat2);
        } else if (isFaceAuthenticated) {
            AnimatorSet animatorSet3 = new AnimatorSet();
            ValueAnimator ofInt = ValueAnimator.ofInt(255, 0);
            ofInt.setDuration(400L);
            ofInt.setInterpolator(Interpolators.LINEAR);
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.FaceScanningOverlay$createSuccessOpacityAnimator$1$1
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                    faceScanningOverlay.rimPaint.setAlpha(((Integer) valueAnimator2.getAnimatedValue()).intValue());
                    faceScanningOverlay.invalidate();
                }
            });
            ofInt.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.FaceScanningOverlay$createSuccessOpacityAnimator$1$2
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    FaceScanningOverlay.this.rimPaint.setAlpha(255);
                    FaceScanningOverlay.this.invalidate();
                }
            });
            animatorSet3.playTogether(createRimDisappearAnimator(1.25f, 400L, Interpolators.STANDARD_DECELERATE), ofInt);
            AnimatorSet animatorSet4 = new AnimatorSet();
            animatorSet4.playTogether(animatorSet3, this.cameraProtectionAnimator);
            animatorSet = animatorSet4;
        } else {
            animatorSet = new AnimatorSet();
            animatorSet.playTogether(createRimDisappearAnimator(1.0f, 200L, Interpolators.STANDARD), this.cameraProtectionAnimator);
        }
        this.rimAnimator = animatorSet;
        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.FaceScanningOverlay$enableShowProtection$2$1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public final void onAnimationEnd(Animator animator) {
                FaceScanningOverlay faceScanningOverlay = FaceScanningOverlay.this;
                faceScanningOverlay.rimAnimator = null;
                if (faceScanningOverlay.showScanningAnim) {
                    return;
                }
                faceScanningOverlay.requestLayout();
            }
        });
        AnimatorSet animatorSet5 = this.rimAnimator;
        if (animatorSet5 != null) {
            animatorSet5.start();
        }
    }

    @Override // com.android.systemui.ScreenDecorations.DisplayCutoutView, android.view.View
    public final void onMeasure(int i, int i2) {
        if (((ArrayList) this.mBounds).isEmpty()) {
            super.onMeasure(i, i2);
            return;
        }
        if (!this.showScanningAnim) {
            setMeasuredDimension(View.resolveSizeAndState(this.mBoundingRect.width(), i, 0), View.resolveSizeAndState(this.mBoundingRect.height(), i2, 0));
            return;
        }
        this.mTotalBounds.set(this.mBoundingRect);
        Rect rect = this.mTotalBounds;
        RectF rectF = this.rimRect;
        rect.union((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom);
        int resolveSizeAndState = View.resolveSizeAndState(this.mTotalBounds.width(), i, 0);
        int resolveSizeAndState2 = View.resolveSizeAndState(this.mTotalBounds.height(), i2, 0);
        this.logger.boundingRect(this.rimRect, "onMeasure: Face scanning animation");
        ScreenDecorationsLogger screenDecorationsLogger = this.logger;
        Rect rect2 = this.mBoundingRect;
        screenDecorationsLogger.getClass();
        screenDecorationsLogger.boundingRect(new RectF(rect2), "onMeasure: Display cutout view bounding rect");
        ScreenDecorationsLogger screenDecorationsLogger2 = this.logger;
        Rect rect3 = this.mTotalBounds;
        screenDecorationsLogger2.getClass();
        screenDecorationsLogger2.boundingRect(new RectF(rect3), "onMeasure: TotalBounds");
        this.logger.onMeasureDimensions(i, i2, resolveSizeAndState, resolveSizeAndState2);
        setMeasuredDimension(resolveSizeAndState, resolveSizeAndState2);
    }

    @Override // com.android.systemui.ScreenDecorations.DisplayCutoutView
    public final void setColor$1(int i) {
        this.cameraProtectionColor = i;
        invalidate();
    }

    @Override // com.android.systemui.DisplayCutoutBaseView
    public final void updateProtectionBoundingPath() {
        super.updateProtectionBoundingPath();
        this.rimRect.set(this.protectionRect);
        this.rimRect.scale(this.rimProgress);
    }
}
