package com.android.wm.shell.transition;

import android.animation.Animator;
import com.android.wm.shell.transition.MultiTaskingTransitionProvider;
import java.util.ArrayList;

/* loaded from: classes3.dex */
public final /* synthetic */ class MultiTaskingTransitionProvider$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                ArrayList arrayList = MultiTaskingTransitionProvider.sForceHidingAnimators;
                ((Animator) obj).cancel();
                break;
            default:
                ((MultiTaskingTransitionProvider.SurfaceValueAnimator) obj).start();
                break;
        }
    }
}
