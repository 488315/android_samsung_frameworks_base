package com.android.systemui.statusbar.phone.ongoingcall.domain.interactor;

import com.android.systemui.statusbar.gesture.SwipeStatusBarAwayGestureHandler;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class OngoingCallInteractor$start$3 extends SuspendLambda implements Function2 {
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ OngoingCallInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingCallInteractor$start$3(OngoingCallInteractor ongoingCallInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = ongoingCallInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        OngoingCallInteractor$start$3 ongoingCallInteractor$start$3 = new OngoingCallInteractor$start$3(this.this$0, continuation);
        ongoingCallInteractor$start$3.Z$0 = ((Boolean) obj).booleanValue();
        return ongoingCallInteractor$start$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((OngoingCallInteractor$start$3) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        boolean z = this.Z$0;
        final OngoingCallInteractor ongoingCallInteractor = this.this$0;
        SwipeStatusBarAwayGestureHandler swipeStatusBarAwayGestureHandler = ongoingCallInteractor.swipeStatusBarAwayGestureHandler;
        String str = OngoingCallInteractor.TAG;
        if (z) {
            swipeStatusBarAwayGestureHandler.addOnGestureDetectedCallback(str, new Function1() { // from class: com.android.systemui.statusbar.phone.ongoingcall.domain.interactor.OngoingCallInteractor$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    String str2 = OngoingCallInteractor.TAG;
                    OngoingCallInteractor.this.onStatusBarSwiped();
                    return Unit.INSTANCE;
                }
            });
        } else {
            swipeStatusBarAwayGestureHandler.removeOnGestureDetectedCallback(str);
        }
        return Unit.INSTANCE;
    }
}
