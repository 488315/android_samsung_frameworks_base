package com.android.p038wm.shell.transition;

import android.animation.ValueAnimator;
import android.view.SurfaceControl;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class DefaultTransitionHandler$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Runnable f$2;

    public /* synthetic */ DefaultTransitionHandler$$ExternalSyntheticLambda3(Object obj, Object obj2, Runnable runnable, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = runnable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DefaultTransitionHandler defaultTransitionHandler = (DefaultTransitionHandler) this.f$0;
                SurfaceControl surfaceControl = (SurfaceControl) this.f$1;
                Runnable runnable = this.f$2;
                SurfaceControl.Transaction acquire = defaultTransitionHandler.mTransactionPool.acquire();
                if (surfaceControl.isValid()) {
                    acquire.remove(surfaceControl);
                    acquire.apply();
                }
                runnable.run();
                break;
            default:
                ArrayList arrayList = (ArrayList) this.f$0;
                ValueAnimator valueAnimator = (ValueAnimator) this.f$1;
                Runnable runnable2 = this.f$2;
                arrayList.remove(valueAnimator);
                runnable2.run();
                break;
        }
    }
}
