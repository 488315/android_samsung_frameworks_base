package com.android.systemui.statusbar.phone.ongoingcall;

import com.android.systemui.statusbar.data.repository.StatusBarModeRepositoryImpl;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

final class OngoingCallController$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ OngoingCallController this$0;

    public OngoingCallController$start$1(OngoingCallController ongoingCallController, Continuation continuation) {
        super(2, continuation);
        this.this$0 = ongoingCallController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new OngoingCallController$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((OngoingCallController$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final OngoingCallController ongoingCallController = this.this$0;
            ReadonlyStateFlow readonlyStateFlow = ((StatusBarModeRepositoryImpl) ongoingCallController.statusBarModeRepository).defaultDisplay.isInFullscreenMode;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.phone.ongoingcall.OngoingCallController$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    boolean booleanValue = ((Boolean) obj2).booleanValue();
                    OngoingCallController ongoingCallController2 = OngoingCallController.this;
                    ongoingCallController2.isFullscreen = booleanValue;
                    ongoingCallController2.updateChipClickListener();
                    ongoingCallController2.updateGestureListening();
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlyStateFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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
