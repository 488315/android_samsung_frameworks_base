package com.android.systemui.keyguard.bouncer.domain.interactor;

import android.util.Log;
import com.android.systemui.keyguard.bouncer.shared.model.BouncerMessageModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.keyguard.bouncer.domain.interactor.BouncerMessageAuditLogger$collectAndLog$1", m277f = "BouncerMessageAuditLogger.kt", m278l = {58}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class BouncerMessageAuditLogger$collectAndLog$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $context;
    final /* synthetic */ Flow $flow;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerMessageAuditLogger$collectAndLog$1(Flow flow, String str, Continuation<? super BouncerMessageAuditLogger$collectAndLog$1> continuation) {
        super(2, continuation);
        this.$flow = flow;
        this.$context = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BouncerMessageAuditLogger$collectAndLog$1(this.$flow, this.$context, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BouncerMessageAuditLogger$collectAndLog$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow flow = this.$flow;
            final String str = this.$context;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.bouncer.domain.interactor.BouncerMessageAuditLogger$collectAndLog$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    Log.d(BouncerMessageAuditLoggerKt.TAG, str + ((BouncerMessageModel) obj2));
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow.collect(flowCollector, this) == coroutineSingletons) {
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
