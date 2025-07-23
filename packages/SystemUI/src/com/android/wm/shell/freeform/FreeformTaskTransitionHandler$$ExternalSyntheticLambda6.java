package com.android.wm.shell.freeform;

import android.animation.Animator;
import android.animation.ValueAnimator;
import com.android.wm.shell.freeform.FreeformTaskTransitionHandler;
import java.util.ArrayList;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformTaskTransitionHandler$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ ArrayList f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ FreeformTaskTransitionHandler$$ExternalSyntheticLambda6(ArrayList arrayList, Animator animator, FreeformTaskTransitionHandler$$ExternalSyntheticLambda1 freeformTaskTransitionHandler$$ExternalSyntheticLambda1) {
        this.f$0 = arrayList;
        this.f$1 = animator;
        this.f$2 = freeformTaskTransitionHandler$$ExternalSyntheticLambda1;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ArrayList arrayList = this.f$0;
                Animator animator = (Animator) this.f$1;
                FreeformTaskTransitionHandler$$ExternalSyntheticLambda1 freeformTaskTransitionHandler$$ExternalSyntheticLambda1 = (FreeformTaskTransitionHandler$$ExternalSyntheticLambda1) this.f$2;
                arrayList.remove(animator);
                freeformTaskTransitionHandler$$ExternalSyntheticLambda1.run();
                break;
            default:
                ArrayList arrayList2 = this.f$0;
                ValueAnimator valueAnimator = (ValueAnimator) this.f$1;
                Runnable runnable = (Runnable) this.f$2;
                int i = FreeformTaskTransitionHandler.AnonymousClass1.$r8$clinit;
                arrayList2.remove(valueAnimator);
                runnable.run();
                break;
        }
    }

    public /* synthetic */ FreeformTaskTransitionHandler$$ExternalSyntheticLambda6(ArrayList arrayList, ValueAnimator valueAnimator, Runnable runnable) {
        this.f$0 = arrayList;
        this.f$1 = valueAnimator;
        this.f$2 = runnable;
    }
}
