package androidx.fragment.app;

import android.view.View;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.SpecialEffectsController;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DefaultSpecialEffectsController$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ DefaultSpecialEffectsController$$ExternalSyntheticLambda0(DefaultSpecialEffectsController defaultSpecialEffectsController, View view, DefaultSpecialEffectsController.AnimationInfo animationInfo) {
        this.f$2 = defaultSpecialEffectsController;
        this.f$0 = view;
        this.f$1 = animationInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DefaultSpecialEffectsController.$r8$lambda$Q6FgD4jZH_jdeVxJxVn553yFXpk((List) this.f$0, (SpecialEffectsController.Operation) this.f$1, (DefaultSpecialEffectsController) this.f$2);
                break;
            default:
                DefaultSpecialEffectsController this$0 = (DefaultSpecialEffectsController) this.f$2;
                View view = (View) this.f$0;
                DefaultSpecialEffectsController.AnimationInfo animationInfo = (DefaultSpecialEffectsController.AnimationInfo) this.f$1;
                Intrinsics.checkNotNullParameter(this$0, "this$0");
                Intrinsics.checkNotNullParameter(animationInfo, "$animationInfo");
                this$0.getContainer().endViewTransition(view);
                animationInfo.completeSpecialEffect();
                break;
        }
    }

    public /* synthetic */ DefaultSpecialEffectsController$$ExternalSyntheticLambda0(Object obj, SpecialEffectsController.Operation operation, Object obj2) {
        this.f$0 = obj;
        this.f$1 = operation;
        this.f$2 = obj2;
    }
}
