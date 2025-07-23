package com.android.systemui.lifecycle;

import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class SysUiViewModelKt$rememberViewModel$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $instance;
    final /* synthetic */ String $traceName;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SysUiViewModelKt$rememberViewModel$1$1(String str, Object obj, Continuation continuation) {
        super(2, continuation);
        this.$traceName = str;
        this.$instance = obj;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SysUiViewModelKt$rememberViewModel$1$1 sysUiViewModelKt$rememberViewModel$1$1 = new SysUiViewModelKt$rememberViewModel$1$1(this.$traceName, this.$instance, continuation);
        sysUiViewModelKt$rememberViewModel$1$1.L$0 = obj;
        return sysUiViewModelKt$rememberViewModel$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SysUiViewModelKt$rememberViewModel$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Activatable activatable = (Activatable) this.$instance;
            this.label = 1;
            if (activatable.activate(this) == coroutineSingletons) {
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
