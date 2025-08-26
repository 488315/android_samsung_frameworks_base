package com.android.systemui.lifecycle;

import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
final class ActivatableKt$rememberActivated$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Activatable $instance;
    final /* synthetic */ String $traceName;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ActivatableKt$rememberActivated$1$1(String str, Activatable activatable, Continuation continuation) {
        super(2, continuation);
        this.$traceName = str;
        this.$instance = activatable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ActivatableKt$rememberActivated$1$1 activatableKt$rememberActivated$1$1 = new ActivatableKt$rememberActivated$1$1(this.$traceName, this.$instance, continuation);
        activatableKt$rememberActivated$1$1.L$0 = obj;
        return activatableKt$rememberActivated$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((ActivatableKt$rememberActivated$1$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Activatable activatable = this.$instance;
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
