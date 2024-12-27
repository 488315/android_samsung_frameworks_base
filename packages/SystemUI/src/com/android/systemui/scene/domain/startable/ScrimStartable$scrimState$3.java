package com.android.systemui.scene.domain.startable;

import com.android.systemui.scene.domain.startable.ScrimStartable;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

final class ScrimStartable$scrimState$3 extends SuspendLambda implements Function2 {
    /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ ScrimStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScrimStartable$scrimState$3(ScrimStartable scrimStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = scrimStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ScrimStartable$scrimState$3 scrimStartable$scrimState$3 = new ScrimStartable$scrimState$3(this.this$0, continuation);
        scrimStartable$scrimState$3.L$0 = obj;
        return scrimStartable$scrimState$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ScrimStartable$scrimState$3) create((ScrimStartable.Model) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        if (((ScrimStartable.Model) this.L$0) != null) {
            this.this$0.scrimController.mExpansionAffectsAlpha = !r2.unlocking;
        }
        return Unit.INSTANCE;
    }
}
