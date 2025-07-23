package com.android.systemui.statusbar.phone.ongoingcall.domain.interactor;

import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OngoingCallInteractor$isGestureListeningEnabled$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ OngoingCallInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingCallInteractor$isGestureListeningEnabled$1(OngoingCallInteractor ongoingCallInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = ongoingCallInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean booleanValue = ((Boolean) obj2).booleanValue();
        boolean booleanValue2 = ((Boolean) obj3).booleanValue();
        OngoingCallInteractor$isGestureListeningEnabled$1 ongoingCallInteractor$isGestureListeningEnabled$1 = new OngoingCallInteractor$isGestureListeningEnabled$1(this.this$0, (Continuation) obj4);
        ongoingCallInteractor$isGestureListeningEnabled$1.L$0 = (OngoingCallModel) obj;
        ongoingCallInteractor$isGestureListeningEnabled$1.Z$0 = booleanValue;
        ongoingCallInteractor$isGestureListeningEnabled$1.Z$1 = booleanValue2;
        return ongoingCallInteractor$isGestureListeningEnabled$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        OngoingCallModel ongoingCallModel = (OngoingCallModel) this.L$0;
        boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        OngoingCallInteractor ongoingCallInteractor = this.this$0;
        String str = OngoingCallInteractor.TAG;
        ongoingCallInteractor.getClass();
        return Boolean.valueOf((ongoingCallModel instanceof OngoingCallModel.InCall) && !((OngoingCallModel.InCall) ongoingCallModel).isAppVisible && !z2 && z);
    }
}
