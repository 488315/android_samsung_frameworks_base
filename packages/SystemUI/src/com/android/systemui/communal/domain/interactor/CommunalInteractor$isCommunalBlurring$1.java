package com.android.systemui.communal.domain.interactor;

import com.android.systemui.communal.shared.model.CommunalBackgroundType;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* loaded from: classes2.dex */
final class CommunalInteractor$isCommunalBlurring$1 extends SuspendLambda implements Function3 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    int label;

    public CommunalInteractor$isCommunalBlurring$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        CommunalInteractor$isCommunalBlurring$1 communalInteractor$isCommunalBlurring$1 = new CommunalInteractor$isCommunalBlurring$1((Continuation) obj3);
        communalInteractor$isCommunalBlurring$1.Z$0 = zBooleanValue;
        communalInteractor$isCommunalBlurring$1.L$0 = (CommunalBackgroundType) obj2;
        communalInteractor$isCommunalBlurring$1.invokeSuspend(Unit.INSTANCE);
        return Boolean.FALSE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        if (z) {
            CommunalBackgroundType communalBackgroundType = CommunalBackgroundType.STATIC;
        }
        return false;
    }
}
