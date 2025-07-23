package com.android.systemui.common.ui.domain.interactor;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class ConfigurationInteractorImpl$directionalDimensionPixelSize$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ int $originLayoutDirection;
    /* synthetic */ int I$0;
    /* synthetic */ int I$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfigurationInteractorImpl$directionalDimensionPixelSize$1(int i, Continuation continuation) {
        super(3, continuation);
        this.$originLayoutDirection = i;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        int intValue = ((Number) obj).intValue();
        int intValue2 = ((Number) obj2).intValue();
        ConfigurationInteractorImpl$directionalDimensionPixelSize$1 configurationInteractorImpl$directionalDimensionPixelSize$1 = new ConfigurationInteractorImpl$directionalDimensionPixelSize$1(this.$originLayoutDirection, (Continuation) obj3);
        configurationInteractorImpl$directionalDimensionPixelSize$1.I$0 = intValue;
        configurationInteractorImpl$directionalDimensionPixelSize$1.I$1 = intValue2;
        return configurationInteractorImpl$directionalDimensionPixelSize$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        int i = this.I$0;
        if (this.$originLayoutDirection != this.I$1) {
            i = -i;
        }
        return new Integer(i);
    }
}
