package com.android.wm.shell.pip2.phone.transition;

import android.content.Context;
import android.graphics.Rect;
import android.view.SurfaceControl;
import com.android.wm.shell.pip2.animation.PipExpandAnimator;
import com.android.wm.shell.pip2.phone.transition.PipExpandHandler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipExpandHandler$$ExternalSyntheticLambda0 implements PipExpandHandler.PipExpandAnimatorSupplier {
    @Override // com.android.wm.shell.pip2.phone.transition.PipExpandHandler.PipExpandAnimatorSupplier
    public final PipExpandAnimator get(Context context, SurfaceControl surfaceControl, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Rect rect, Rect rect2, Rect rect3, Rect rect4, int i) {
        return new PipExpandAnimator(context, surfaceControl, transaction, transaction2, rect, rect2, rect3, rect4, i);
    }
}
