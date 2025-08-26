package com.android.wm.shell.pip2;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.Choreographer;
import android.view.SurfaceControl;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class PipSurfaceTransactionHelper {
    public final int mCornerRadius;
    public final int mShadowRadius;
    public final Matrix mTmpTransform = new Matrix();
    public final float[] mTmpFloat9 = new float[9];
    public final Rect mTmpDestinationRect = new Rect();

    public interface SurfaceControlTransactionFactory {
    }

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
