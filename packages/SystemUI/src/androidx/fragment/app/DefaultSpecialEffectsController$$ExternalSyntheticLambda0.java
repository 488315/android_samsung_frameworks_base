package androidx.fragment.app;

import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.SpecialEffectsController;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class DefaultSpecialEffectsController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ DefaultSpecialEffectsController$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DefaultSpecialEffectsController defaultSpecialEffectsController = (DefaultSpecialEffectsController) this.f$0;
                SpecialEffectsController.Operation operation = (SpecialEffectsController.Operation) this.f$1;
                int i = DefaultSpecialEffectsController.$r8$clinit;
                defaultSpecialEffectsController.applyContainerChangesToOperation$fragment_release(operation);
                break;
            default:
                DefaultSpecialEffectsController.TransitionEffect transitionEffect = (DefaultSpecialEffectsController.TransitionEffect) this.f$0;
                ViewGroup viewGroup = (ViewGroup) this.f$1;
                Iterator it = transitionEffect.transitionInfos.iterator();
                while (it.hasNext()) {
                    SpecialEffectsController.Operation operation2 = ((DefaultSpecialEffectsController.TransitionInfo) it.next()).operation;
                    View view = operation2.fragment.mView;
                    if (view != null) {
                        operation2.finalState.applyState(view, viewGroup);
                    }
                }
                break;
        }
    }
}
