package com.android.systemui.keyboard.backlight.domain.interactor;

import com.android.systemui.keyboard.data.repository.KeyboardRepositoryImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

public final class KeyboardBacklightInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ KeyboardBacklightInteractor this$0;

    public KeyboardBacklightInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, KeyboardBacklightInteractor keyboardBacklightInteractor) {
        super(3, continuation);
        this.this$0 = keyboardBacklightInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyboardBacklightInteractor$special$$inlined$flatMapLatest$1 keyboardBacklightInteractor$special$$inlined$flatMapLatest$1 = new KeyboardBacklightInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        keyboardBacklightInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyboardBacklightInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyboardBacklightInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = ((Boolean) this.L$1).booleanValue() ? ((KeyboardRepositoryImpl) this.this$0.keyboardRepository).backlight : new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, flowCollector) == coroutineSingletons) {
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
