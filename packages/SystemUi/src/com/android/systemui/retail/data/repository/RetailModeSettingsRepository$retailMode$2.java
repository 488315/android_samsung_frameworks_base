package com.android.systemui.retail.data.repository;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.retail.data.repository.RetailModeSettingsRepository$retailMode$2", m277f = "RetailModeRepository.kt", m278l = {73}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
public final class RetailModeSettingsRepository$retailMode$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;

    public RetailModeSettingsRepository$retailMode$2(Continuation<? super RetailModeSettingsRepository$retailMode$2> continuation) {
        super(2, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        RetailModeSettingsRepository$retailMode$2 retailModeSettingsRepository$retailMode$2 = new RetailModeSettingsRepository$retailMode$2(continuation);
        retailModeSettingsRepository$retailMode$2.L$0 = obj;
        return retailModeSettingsRepository$retailMode$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((RetailModeSettingsRepository$retailMode$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Unit unit = Unit.INSTANCE;
            this.label = 1;
            if (flowCollector.emit(unit, this) == coroutineSingletons) {
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
