package com.android.wm.shell.pip;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Choreographer;
import android.view.SurfaceControl;
import com.android.systemui.R;
import com.android.wm.shell.transition.Transitions;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipSurfaceTransactionHelper {
    public int mCornerRadius;
    public int mShadowRadius;
    public final Matrix mTmpTransform = new Matrix();
    public final float[] mTmpFloat9 = new float[9];
    public final RectF mTmpSourceRectF = new RectF();
    public final RectF mTmpDestinationRectF = new RectF();
    public final Rect mTmpDestinationRect = new Rect();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SurfaceControlTransactionFactory {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class VsyncSurfaceControlTransactionFactory implements SurfaceControlTransactionFactory {
        public final SurfaceControl.Transaction getTransaction() {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
            return transaction;
        }
    }

    public PipSurfaceTransactionHelper(Context context) {
        onDensityOrFontScaleChanged(context);
    }

    public final void cropAndPosition(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.setWindowCrop(surfaceControl, rect.width(), rect.height()).setPosition(surfaceControl, rect.left, rect.top);
    }

    public final void onDensityOrFontScaleChanged(Context context) {
        boolean z = CoreRune.MW_PIP_DISABLE_ROUNDED_CORNER;
        this.mCornerRadius = z ? 0 : context.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
        this.mShadowRadius = z ? 0 : context.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
    }

    public final void resetScale(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.setMatrix(surfaceControl, Matrix.IDENTITY_MATRIX, this.mTmpFloat9).setPosition(surfaceControl, rect.left, rect.top);
    }

    public final void rotateAndScaleWithCrop(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, float f, float f2, float f3, boolean z, boolean z2, Rect rect4) {
        int i;
        this.mTmpDestinationRect.set(rect);
        this.mTmpDestinationRect.inset(rect3);
        int width = this.mTmpDestinationRect.width();
        int height = this.mTmpDestinationRect.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        float f4 = width <= height ? width2 / width : height2 / height;
        Rect rect5 = this.mTmpDestinationRect;
        if (!z || rect4 == null) {
            boolean z3 = Transitions.SHELL_TRANSITIONS_ROTATION;
            int i2 = z3 ? height2 : width2;
            if (!z3) {
                width2 = height2;
            }
            rect5.set(0, 0, i2, width2);
            rect5.scale(1.0f / f4);
            rect5.offset(rect3.left, rect3.top);
            if (z) {
                f2 -= rect3.left * f4;
                i = rect3.top;
            } else if (z2) {
                f2 -= rect3.top * f4;
                f3 += rect3.left * f4;
            } else {
                f2 += rect3.top * f4;
                i = rect3.left;
            }
            f3 -= i * f4;
        } else {
            rect5.set(rect4);
        }
        this.mTmpTransform.setScale(f4, f4);
        this.mTmpTransform.postRotate(f);
        this.mTmpTransform.postTranslate(f2, f3);
        transaction.setMatrix(surfaceControl, this.mTmpTransform, this.mTmpFloat9).setCrop(surfaceControl, rect5);
    }

    public final void round(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, boolean z) {
        transaction.setCornerRadius(surfaceControl, z ? this.mCornerRadius : 0.0f);
    }

    public final void scale(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, RectF rectF, float f, boolean z) {
        this.mTmpSourceRectF.set(rect);
        if (z) {
            this.mTmpSourceRectF.offsetTo(0.0f, 0.0f);
        }
        this.mTmpDestinationRectF.set(rectF);
        this.mTmpTransform.setRectToRect(this.mTmpSourceRectF, this.mTmpDestinationRectF, Matrix.ScaleToFit.FILL);
        this.mTmpTransform.postRotate(f, this.mTmpDestinationRectF.centerX(), this.mTmpDestinationRectF.centerY());
        transaction.setMatrix(surfaceControl, this.mTmpTransform, this.mTmpFloat9);
    }

    public final void shadow(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, boolean z) {
        transaction.setShadowRadius(surfaceControl, z ? this.mShadowRadius : 0.0f);
    }

    public final void round(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2) {
        transaction.setCornerRadius(surfaceControl, this.mCornerRadius * ((float) (Math.hypot(rect.width(), rect.height()) / Math.hypot(rect2.width(), rect2.height()))));
    }
}
