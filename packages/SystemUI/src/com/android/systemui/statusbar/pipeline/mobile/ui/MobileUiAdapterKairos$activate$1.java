package com.android.systemui.statusbar.pipeline.mobile.ui;

import com.android.systemui.kairos.BuildScopeKt;
import com.android.systemui.kairos.KairosCoroutineScope;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class MobileUiAdapterKairos$activate$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ MobileUiAdapterKairos this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MobileUiAdapterKairos$activate$1(MobileUiAdapterKairos mobileUiAdapterKairos, Continuation continuation) {
        super(2, continuation);
        this.this$0 = mobileUiAdapterKairos;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new MobileUiAdapterKairos$activate$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MobileUiAdapterKairos$activate$1) create((KairosCoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final MobileUiAdapterKairos mobileUiAdapterKairos = this.this$0;
            mobileUiAdapterKairos.isCollecting = true;
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.mobile.ui.MobileUiAdapterKairos$activate$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    MobileUiAdapterKairos.this.isCollecting = false;
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (BuildScopeKt.awaitClose(function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        throw new KotlinNothingValueException();
    }
}
