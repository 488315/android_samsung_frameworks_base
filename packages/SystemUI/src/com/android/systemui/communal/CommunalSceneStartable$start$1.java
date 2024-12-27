package com.android.systemui.communal;

import com.android.systemui.keyguard.shared.model.TransitionStep;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReferenceImpl;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final /* synthetic */ class CommunalSceneStartable$start$1 extends FunctionReferenceImpl implements Function2 {
    public CommunalSceneStartable$start$1(Object obj) {
        super(2, obj, CommunalSceneStartable.class, "determineSceneAfterTransition", "determineSceneAfterTransition(Lcom/android/systemui/keyguard/shared/model/TransitionStep;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", 0);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return CommunalSceneStartable.access$determineSceneAfterTransition((CommunalSceneStartable) this.receiver, (TransitionStep) obj, (Continuation) obj2);
    }
}
