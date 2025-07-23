package com.android.wm.shell.pip2;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.Choreographer;
import android.view.SurfaceControl;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class PipSurfaceTransactionHelper {
    public final int mCornerRadius;
    public final int mShadowRadius;
    public final Matrix mTmpTransform = new Matrix();
    public final float[] mTmpFloat9 = new float[9];
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
        this.mCornerRadius = context.getResources().getDimensionPixelSize(R.dimen.pip_corner_radius);
        this.mShadowRadius = context.getResources().getDimensionPixelSize(R.dimen.pip_shadow_radius);
    }
}
