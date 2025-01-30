package com.android.systemui.biometrics;

import com.samsung.android.knox.custom.IKnoxCustomManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2", m277f = "UdfpsKeyguardViewControllerLegacy.kt", m278l = {IKnoxCustomManager.Stub.TRANSACTION_setHardKeyIntentBroadcastExternal}, m279m = "invokeSuspend")
/* renamed from: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2 */
/* loaded from: classes.dex */
public final class C1077xb116dad8 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ UdfpsKeyguardViewControllerLegacy this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C1077xb116dad8(UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy, Continuation<? super C1077xb116dad8> continuation) {
        super(2, continuation);
        this.this$0 = udfpsKeyguardViewControllerLegacy;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new C1077xb116dad8(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((C1077xb116dad8) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final UdfpsKeyguardViewControllerLegacy udfpsKeyguardViewControllerLegacy = this.this$0;
            StateFlow stateFlow = udfpsKeyguardViewControllerLegacy.alternateBouncerInteractor.isVisible;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.UdfpsKeyguardViewControllerLegacy$listenForAlternateBouncerVisibility$2.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    UdfpsKeyguardViewControllerLegacy.access$showUdfpsBouncer(UdfpsKeyguardViewControllerLegacy.this, ((Boolean) obj2).booleanValue());
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (stateFlow.collect(flowCollector, this) == coroutineSingletons) {
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
