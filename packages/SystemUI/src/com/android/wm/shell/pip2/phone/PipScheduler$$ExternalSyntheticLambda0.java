package com.android.wm.shell.pip2.phone;

import android.content.Context;
import android.view.SurfaceControl;
import com.android.wm.shell.pip2.animation.PipAlphaAnimator;
import com.android.wm.shell.pip2.phone.PipScheduler;

/* loaded from: classes3.dex */
public final /* synthetic */ class PipScheduler$$ExternalSyntheticLambda0 implements PipScheduler.PipAlphaAnimatorSupplier {
    @Override // com.android.wm.shell.pip2.phone.PipScheduler.PipAlphaAnimatorSupplier
    public final PipAlphaAnimator get(Context context, SurfaceControl surfaceControl) {
        return new PipAlphaAnimator(context, surfaceControl, null, null, 1);
    }
}
