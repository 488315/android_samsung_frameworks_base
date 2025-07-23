package com.samsung.sesl.compose.foundation;

import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.State;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
final class BasicSwitchKt$SeslBasicSwitch$4$2$1$2$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ State<Function1> $animateFraction$delegate;
    final /* synthetic */ boolean $checked;
    final /* synthetic */ MutableState<Boolean> $needUpdateFraction$delegate;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BasicSwitchKt$SeslBasicSwitch$4$2$1$2$1(boolean z, MutableState<Boolean> mutableState, State<? extends Function1> state, Continuation continuation) {
        super(2, continuation);
        this.$checked = z;
        this.$needUpdateFraction$delegate = mutableState;
        this.$animateFraction$delegate = state;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BasicSwitchKt$SeslBasicSwitch$4$2$1$2$1(this.$checked, this.$needUpdateFraction$delegate, this.$animateFraction$delegate, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BasicSwitchKt$SeslBasicSwitch$4$2$1$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            this.label = 1;
            if (DelayKt.delay(100L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        if (((Boolean) this.$needUpdateFraction$delegate.getValue()).booleanValue()) {
            ((Function1) this.$animateFraction$delegate.getValue()).mo779invoke(new Float(this.$checked ? 1.0f : 0.0f));
        }
        return Unit.INSTANCE;
    }
}
