package androidx.fragment.app;

import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.SpecialEffectsController;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SpecialEffectsController.Operation f$0;
    public final /* synthetic */ DefaultSpecialEffectsController.TransitionEffect f$1;

    public /* synthetic */ DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda1(SpecialEffectsController.Operation operation, DefaultSpecialEffectsController.TransitionEffect transitionEffect, int i) {
        this.$r8$classId = i;
        this.f$0 = operation;
        this.f$1 = transitionEffect;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                SpecialEffectsController.Operation operation = this.f$0;
                DefaultSpecialEffectsController.TransitionEffect transitionEffect = this.f$1;
                if (FragmentManager.isLoggingEnabled(2)) {
                    Objects.toString(operation);
                }
                operation.completeEffect(transitionEffect);
                break;
            default:
                SpecialEffectsController.Operation operation2 = this.f$0;
                DefaultSpecialEffectsController.TransitionEffect transitionEffect2 = this.f$1;
                if (FragmentManager.isLoggingEnabled(2)) {
                    Objects.toString(operation2);
                }
                operation2.completeEffect(transitionEffect2);
                break;
        }
    }
}
