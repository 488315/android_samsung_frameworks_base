package com.android.systemui.wallet.controller;

import androidx.picker3.widget.SeslColorSpectrumView$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.wallet.controller.WalletContextualLocationsService$onBind$1", m277f = "WalletContextualLocationsService.kt", m278l = {42}, m279m = "invokeSuspend")
/* loaded from: classes2.dex */
final class WalletContextualLocationsService$onBind$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ WalletContextualLocationsService this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WalletContextualLocationsService$onBind$1(WalletContextualLocationsService walletContextualLocationsService, Continuation<? super WalletContextualLocationsService$onBind$1> continuation) {
        super(2, continuation);
        this.this$0 = walletContextualLocationsService;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new WalletContextualLocationsService$onBind$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WalletContextualLocationsService$onBind$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final WalletContextualLocationsService walletContextualLocationsService = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = walletContextualLocationsService.controller.allWalletCards;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.wallet.controller.WalletContextualLocationsService$onBind$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    SeslColorSpectrumView$$ExternalSyntheticOutline0.m43m("Number of cards registered ", ((List) obj2).size(), "WalletContextualLocationsService");
                    WalletContextualLocationsService.this.getClass();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
