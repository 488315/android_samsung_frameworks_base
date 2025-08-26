package androidx.fragment.app;

import androidx.fragment.app.DefaultSpecialEffectsController;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$ObjectRef;

/* loaded from: classes.dex */
public final /* synthetic */ class DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                Function0 function0 = (Function0) ((Ref$ObjectRef) obj).element;
                if (function0 != null) {
                    function0.invoke();
                    break;
                }
                break;
            case 1:
                FragmentTransition.setViewVisibility(4, (ArrayList) obj);
                break;
            default:
                DefaultSpecialEffectsController.TransitionEffect transitionEffect = (DefaultSpecialEffectsController.TransitionEffect) obj;
                Iterator it = transitionEffect.transitionInfos.iterator();
                while (it.hasNext()) {
                    ((DefaultSpecialEffectsController.TransitionInfo) it.next()).operation.completeEffect(transitionEffect);
                }
                break;
        }
    }

    public /* synthetic */ DefaultSpecialEffectsController$TransitionEffect$$ExternalSyntheticLambda0(ArrayList arrayList) {
        this.$r8$classId = 1;
        this.f$0 = arrayList;
    }
}
