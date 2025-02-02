package androidx.fragment.app;

import android.util.Log;
import androidx.fragment.app.DefaultSpecialEffectsController;
import androidx.fragment.app.SpecialEffectsController;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DefaultSpecialEffectsController$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ DefaultSpecialEffectsController.TransitionInfo f$0;
    public final /* synthetic */ SpecialEffectsController.Operation f$1;

    public /* synthetic */ DefaultSpecialEffectsController$$ExternalSyntheticLambda1(DefaultSpecialEffectsController.TransitionInfo transitionInfo, SpecialEffectsController.Operation operation) {
        this.f$0 = transitionInfo;
        this.f$1 = operation;
    }

    @Override // java.lang.Runnable
    public final void run() {
        DefaultSpecialEffectsController.TransitionInfo transitionInfo = this.f$0;
        SpecialEffectsController.Operation operation = this.f$1;
        Intrinsics.checkNotNullParameter(transitionInfo, "$transitionInfo");
        Intrinsics.checkNotNullParameter(operation, "$operation");
        transitionInfo.completeSpecialEffect();
        if (FragmentManager.isLoggingEnabled(2)) {
            Log.v("FragmentManager", "Transition for operation " + operation + " has completed");
        }
    }
}
