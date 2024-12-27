package com.android.systemui.accessibility.data.repository;

import android.os.UserHandle;
import com.android.systemui.accessibility.data.model.NightDisplayState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class NightDisplayRepository$nightDisplayState$1$4 extends SuspendLambda implements Function2 {
    final /* synthetic */ UserHandle $user;
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ NightDisplayRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NightDisplayRepository$nightDisplayState$1$4(NightDisplayRepository nightDisplayRepository, UserHandle userHandle, Continuation continuation) {
        super(2, continuation);
        this.this$0 = nightDisplayRepository;
        this.$user = userHandle;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        NightDisplayRepository$nightDisplayState$1$4 nightDisplayRepository$nightDisplayState$1$4 = new NightDisplayRepository$nightDisplayState$1$4(this.this$0, this.$user, continuation);
        nightDisplayRepository$nightDisplayState$1$4.L$0 = obj;
        return nightDisplayRepository$nightDisplayState$1$4;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((NightDisplayRepository$nightDisplayState$1$4) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            NightDisplayRepository nightDisplayRepository = this.this$0;
            UserHandle userHandle = this.$user;
            int i2 = NightDisplayRepository.$r8$clinit;
            NightDisplayState initialState = nightDisplayRepository.initialState(userHandle);
            this.label = 1;
            if (flowCollector.emit(initialState, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
