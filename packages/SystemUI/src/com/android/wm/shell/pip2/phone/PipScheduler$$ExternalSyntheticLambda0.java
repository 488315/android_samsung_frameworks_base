package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.view.SurfaceControl;
import com.android.wm.shell.pip2.animation.PipAlphaAnimator;
import com.android.wm.shell.pip2.phone.PipScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class PipScheduler$$ExternalSyntheticLambda0 implements PipScheduler.PipAlphaAnimatorSupplier {
    @Override // com.android.wm.shell.pip2.phone.PipScheduler.PipAlphaAnimatorSupplier
    public final PipAlphaAnimator get(Context context, SurfaceControl surfaceControl) {
        return new PipAlphaAnimator(context, surfaceControl, null, null, 1);
    }
}
