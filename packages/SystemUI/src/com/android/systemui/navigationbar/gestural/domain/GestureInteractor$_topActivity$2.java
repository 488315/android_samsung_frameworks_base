package com.android.systemui.navigationbar.gestural.domain;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;

/* loaded from: classes2.dex */
final class GestureInteractor$_topActivity$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ GestureInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GestureInteractor$_topActivity$2(GestureInteractor gestureInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = gestureInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GestureInteractor$_topActivity$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GestureInteractor$_topActivity$2) create((Unit) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return obj;
        }
        ResultKt.throwOnFailure(obj);
        GestureInteractor gestureInteractor = this.this$0;
        this.label = 1;
        gestureInteractor.getClass();
        Object objWithContext = BuildersKt.withContext(gestureInteractor.backgroundCoroutineContext, new GestureInteractor$getTopActivity$2(gestureInteractor, null), this);
        return objWithContext == coroutineSingletons ? coroutineSingletons : objWithContext;
    }
}
