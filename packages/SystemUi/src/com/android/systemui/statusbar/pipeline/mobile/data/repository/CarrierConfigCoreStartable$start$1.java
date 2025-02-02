package com.android.systemui.statusbar.pipeline.mobile.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.statusbar.pipeline.mobile.data.repository.CarrierConfigCoreStartable$start$1", m277f = "CarrierConfigCoreStartable.kt", m278l = {37}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class CarrierConfigCoreStartable$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ CarrierConfigCoreStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public CarrierConfigCoreStartable$start$1(CarrierConfigCoreStartable carrierConfigCoreStartable, Continuation<? super CarrierConfigCoreStartable$start$1> continuation) {
        super(2, continuation);
        this.this$0 = carrierConfigCoreStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new CarrierConfigCoreStartable$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((CarrierConfigCoreStartable$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            CarrierConfigRepository carrierConfigRepository = this.this$0.carrierConfigRepository;
            this.label = 1;
            if (carrierConfigRepository.startObservingCarrierConfigUpdates(this) == coroutineSingletons) {
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
