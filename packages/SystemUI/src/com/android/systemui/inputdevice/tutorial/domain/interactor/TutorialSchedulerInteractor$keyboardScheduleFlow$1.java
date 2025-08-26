package com.android.systemui.inputdevice.tutorial.domain.interactor;

import com.android.systemui.inputdevice.tutorial.data.repository.DeviceType;
import com.android.systemui.inputdevice.tutorial.data.repository.TutorialSchedulerRepository;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* loaded from: classes2.dex */
final class TutorialSchedulerInteractor$keyboardScheduleFlow$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ TutorialSchedulerInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TutorialSchedulerInteractor$keyboardScheduleFlow$1(TutorialSchedulerInteractor tutorialSchedulerInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = tutorialSchedulerInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        TutorialSchedulerInteractor$keyboardScheduleFlow$1 tutorialSchedulerInteractor$keyboardScheduleFlow$1 = new TutorialSchedulerInteractor$keyboardScheduleFlow$1(this.this$0, continuation);
        tutorialSchedulerInteractor$keyboardScheduleFlow$1.L$0 = obj;
        return tutorialSchedulerInteractor$keyboardScheduleFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((TutorialSchedulerInteractor$keyboardScheduleFlow$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0068, code lost:
    
        if (r1.emit(r8, r7) != r0) goto L24;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector2 = (FlowCollector) this.L$0;
            TutorialSchedulerRepository tutorialSchedulerRepository = this.this$0.repo;
            DeviceType deviceType = DeviceType.KEYBOARD;
            this.L$0 = flowCollector2;
            this.label = 1;
            Object objIsNotified = tutorialSchedulerRepository.isNotified(deviceType, this);
            if (objIsNotified != coroutineSingletons) {
                flowCollector = flowCollector2;
                obj = objIsNotified;
            }
            return coroutineSingletons;
        }
        if (i == 1) {
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        } else {
            if (i != 2) {
                if (i != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
            DeviceType deviceType2 = DeviceType.KEYBOARD;
            this.L$0 = null;
            this.label = 3;
        }
        if (!((Boolean) obj).booleanValue()) {
            TutorialSchedulerInteractor tutorialSchedulerInteractor = this.this$0;
            DeviceType deviceType3 = DeviceType.KEYBOARD;
            this.L$0 = flowCollector;
            this.label = 2;
            if (TutorialSchedulerInteractor.access$schedule(tutorialSchedulerInteractor, deviceType3, this) != coroutineSingletons) {
                DeviceType deviceType22 = DeviceType.KEYBOARD;
                this.L$0 = null;
                this.label = 3;
            }
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
