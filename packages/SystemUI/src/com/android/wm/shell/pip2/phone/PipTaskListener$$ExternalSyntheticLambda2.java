package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.wm.shell.pip2.animation.PipResizeAnimator;
import com.android.wm.shell.pip2.phone.PipTaskListener;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipTaskListener$$ExternalSyntheticLambda2 implements PipTaskListener.PipResizeAnimatorSupplier {
    @Override // com.android.wm.shell.pip2.phone.PipTaskListener.PipResizeAnimatorSupplier
    public final PipResizeAnimator get(Context context, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Rect rect, Rect rect2, Rect rect3, int i) {
        return new PipResizeAnimator(context, surfaceControl, transaction, transaction2, rect, rect2, rect3, i, 0.0f);
    }
}
