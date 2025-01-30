package com.android.systemui.biometrics;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2", m277f = "UdfpsKeyguardViewControllerLegacy.kt", m278l = {262}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
public final class UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UdfpsKeyguardViewControllerLegacy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy, Continuation<? super UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2> continuation) {
        super(2, continuation);
        this.this$0 = udfpsKeyguardViewControllerLegacy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
            FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = udfpsKeyguardViewControllerLegacy.primaryBouncerInteractor.bouncerExpansion;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$listenForBouncerExpansion$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    float floatValue = ((Number) obj2).floatValue();
                    UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy2 = UdfpsKeyguardViewControllerLegacy.this;
                    udfpsKeyguardViewControllerLegacy2.inputBouncerExpansion = floatValue;
                    udfpsKeyguardViewControllerLegacy2.updateAlpha();
                    udfpsKeyguardViewControllerLegacy2.updatePauseAuth();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flowKt__ZipKt$combine$$inlined$unsafeFlow$1.collect(flowCollector, this) == coroutineSingletons) {
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
