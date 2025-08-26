package com.android.systemui.communal.posturing.domain.interactor;

import com.android.systemui.communal.posturing.shared.model.PosturedState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class PosturingInteractor$postured$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public PosturingInteractor$postured$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PosturingInteractor$postured$1 posturingInteractor$postured$1 = new PosturingInteractor$postured$1((Continuation) obj3);
        posturingInteractor$postured$1.L$0 = (PosturedState) obj;
        posturingInteractor$postured$1.L$1 = (PosturedState) obj2;
        return posturingInteractor$postured$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        PosturedState posturedState = (PosturedState) this.L$0;
        Boolean boolAsBoolean = PosturingInteractorKt.asBoolean((PosturedState) this.L$1);
        return Boolean.valueOf((boolAsBoolean == null && (boolAsBoolean = PosturingInteractorKt.asBoolean(posturedState)) == null) ? false : boolAsBoolean.booleanValue());
    }
}
