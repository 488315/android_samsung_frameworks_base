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
import kotlinx.coroutines.flow.StateFlow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
@DebugMetadata(m276c = "com.android.systemui.biometrics.SideFpsController$listenForAlternateBouncerVisibility$1", m277f = "SideFpsController.kt", m278l = {179}, m279m = "invokeSuspend")
/* loaded from: classes.dex */
final class SideFpsController$listenForAlternateBouncerVisibility$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ SideFpsController this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SideFpsController$listenForAlternateBouncerVisibility$1(SideFpsController sideFpsController, Continuation<? super SideFpsController$listenForAlternateBouncerVisibility$1> continuation) {
        super(2, continuation);
        this.this$0 = sideFpsController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new SideFpsController$listenForAlternateBouncerVisibility$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SideFpsController$listenForAlternateBouncerVisibility$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final SideFpsController sideFpsController = this.this$0;
            StateFlow stateFlow = sideFpsController.alternateBouncerInteractor.isVisible;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.biometrics.SideFpsController$listenForAlternateBouncerVisibility$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    SideFpsController sideFpsController2 = SideFpsController.this;
                    if (booleanValue) {
                        sideFpsController2.show(SideFpsUiRequestSource.ALTERNATE_BOUNCER, 4);
                    } else {
                        sideFpsController2.hide(SideFpsUiRequestSource.ALTERNATE_BOUNCER);
                    }
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
