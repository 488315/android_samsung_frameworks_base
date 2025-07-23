package com.android.systemui.util.composable.kairos;

import androidx.compose.runtime.MutableState;
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
final class ActivatedKairosSpecKt$ActivatedKairosSpec$1$1$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MutableState<Object> $state$delegate;
    final /* synthetic */ Object $uninit;
    final /* synthetic */ Object $v;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivatedKairosSpecKt$ActivatedKairosSpec$1$1$1$1(Object obj, MutableState<Object> mutableState, Object obj2, Continuation continuation) {
        super(2, continuation);
        this.$v = obj;
        this.$state$delegate = mutableState;
        this.$uninit = obj2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Unit invokeSuspend$lambda$0(Object obj, MutableState mutableState) {
        mutableState.setValue(obj);
        return Unit.INSTANCE;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new ActivatedKairosSpecKt$ActivatedKairosSpec$1$1$1$1(this.$v, this.$state$delegate, this.$uninit, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(KairosCoroutineScope kairosCoroutineScope, Continuation continuation) {
        return ((ActivatedKairosSpecKt$ActivatedKairosSpec$1$1$1$1) create(kairosCoroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.$state$delegate.setValue(this.$v);
            final Object obj2 = this.$uninit;
            final MutableState<Object> mutableState = this.$state$delegate;
            Function0 function0 = new Function0() { // from class: com.android.systemui.util.composable.kairos.ActivatedKairosSpecKt$ActivatedKairosSpec$1$1$1$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Unit invokeSuspend$lambda$0;
                    invokeSuspend$lambda$0 = ActivatedKairosSpecKt$ActivatedKairosSpec$1$1$1$1.invokeSuspend$lambda$0(obj2, mutableState);
                    return invokeSuspend$lambda$0;
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
