package com.android.p038wm.shell.pip;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.Choreographer;
import android.view.SurfaceControl;
import com.android.p038wm.shell.transition.Transitions;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PipSurfaceTransactionHelper {
    public int mCornerRadius;
    public int mShadowRadius;
    public final Matrix mTmpTransform = new Matrix();
    public final float[] mTmpFloat9 = new float[9];
    public final RectF mTmpSourceRectF = new RectF();
    public final RectF mTmpDestinationRectF = new RectF();
    public final Rect mTmpDestinationRect = new Rect();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SurfaceControlTransactionFactory {
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class VsyncSurfaceControlTransactionFactory implements SurfaceControlTransactionFactory {
        public final SurfaceControl.Transaction getTransaction() {
            SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
            transaction.setFrameTimelineVsync(Choreographer.getInstance().getVsyncId());
            return transaction;
        }
    }

    public PipSurfaceTransactionHelper(Context context) {
        onDensityOrFontScaleChanged(context);
    }

    public final void crop(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.setWindowCrop(surfaceControl, rect.width(), rect.height()).setPosition(surfaceControl, rect.left, rect.top);
    }

    public final void onDensityOrFontScaleChanged(Context context) {
        this.mCornerRadius = CoreRune.MW_PIP_DISABLE_ROUND_CORNER ? 0 : context.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
        this.mShadowRadius = CoreRune.MW_PIP_DISABLE_ROUND_CORNER ? 0 : context.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
    }

    public final void resetScale(Rect rect, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.setMatrix(surfaceControl, Matrix.IDENTITY_MATRIX, this.mTmpFloat9).setPosition(surfaceControl, rect.left, rect.top);
    }

    public final void rotateAndScaleWithCrop(SurfaceControl.Transaction transaction, SurfaceControl surfaceControl, Rect rect, Rect rect2, Rect rect3, float f, float f2, float f3, boolean z, boolean z2) {
        float f4;
        int i;
        float f5;
        Rect rect4 = this.mTmpDestinationRect;
        rect4.set(rect);
        rect4.inset(rect3);
        int width = rect4.width();
        int height = rect4.height();
        int width2 = rect2.width();
        int height2 = rect2.height();
        float f6 = width <= height ? width2 / width : height2 / height;
        boolean z3 = Transitions.SHELL_TRANSITIONS_ROTATION;
        int i2 = z3 ? height2 : width2;
        if (!z3) {
            width2 = height2;
        }
        rect4.set(0, 0, i2, width2);
        rect4.scale(1.0f / f6);
        rect4.offset(rect3.left, rect3.top);
        if (z) {
            f4 = f2 - (rect3.left * f6);
            i = rect3.top;
        } else {
            if (z2) {
                f4 = f2 - (rect3.top * f6);
                f5 = (rect3.left * f6) + f3;
                Matrix matrix = this.mTmpTransform;
                matrix.setScale(f6, f6);
                matrix.postRotate(f);
                matrix.postTranslate(f4, f5);
                transaction.setMatrix(surfaceControl, matrix, this.mTmpFloat9).setCrop(surfaceControl, rect4);
            }
            f4 = f2 + (rect3.top * f6);
            i = rect3.left;
        }
        f5 = f3 - (i * f6);
        Matrix matrix2 = this.mTmpTransform;
        matrix2.setScale(f6, f6);
        matrix2.postRotate(f);
        matrix2.postTranslate(f4, f5);
        transaction.setMatrix(surfaceControl, matrix2, this.mTmpFloat9).setCrop(surfaceControl, rect4);
    }

    public final void round(SurfaceControl surfaceControl, boolean z, SurfaceControl.Transaction transaction) {
        transaction.setCornerRadius(surfaceControl, z ? this.mCornerRadius : 0.0f);
    }

    public final void scale(float f, Rect rect, Rect rect2, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        RectF rectF = this.mTmpSourceRectF;
        rectF.set(rect);
        rectF.offsetTo(0.0f, 0.0f);
        RectF rectF2 = this.mTmpDestinationRectF;
        rectF2.set(rect2);
        Matrix matrix = this.mTmpTransform;
        matrix.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.FILL);
        matrix.postRotate(f, rectF2.centerX(), rectF2.centerY());
        transaction.setMatrix(surfaceControl, matrix, this.mTmpFloat9);
    }

    public final void shadow(SurfaceControl surfaceControl, boolean z, SurfaceControl.Transaction transaction) {
        transaction.setShadowRadius(surfaceControl, z ? this.mShadowRadius : 0.0f);
    }

    public final void round(Rect rect, Rect rect2, SurfaceControl.Transaction transaction, SurfaceControl surfaceControl) {
        transaction.setCornerRadius(surfaceControl, this.mCornerRadius * ((float) (Math.hypot(rect.width(), rect.height()) / Math.hypot(rect2.width(), rect2.height()))));
    }
}
