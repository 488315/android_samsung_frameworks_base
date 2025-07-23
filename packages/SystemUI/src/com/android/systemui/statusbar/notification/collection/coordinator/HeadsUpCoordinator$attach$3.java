package com.android.systemui.statusbar.notification.collection.coordinator;

import com.android.systemui.statusbar.chips.notification.domain.interactor.StatusBarNotificationChipsInteractor;
import kotlin.KotlinNothingValueException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.ReadonlySharedFlow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class HeadsUpCoordinator$attach$3 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ HeadsUpCoordinator this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HeadsUpCoordinator$attach$3(HeadsUpCoordinator headsUpCoordinator, Continuation continuation) {
        super(2, continuation);
        this.this$0 = headsUpCoordinator;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new HeadsUpCoordinator$attach$3(this.this$0, continuation);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        StatusBarNotificationChipsInteractor statusBarNotificationChipsInteractor;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            statusBarNotificationChipsInteractor = this.this$0.statusBarNotificationChipsInteractor;
            ReadonlySharedFlow readonlySharedFlow = statusBarNotificationChipsInteractor.promotedNotificationChipTapEvent;
            final HeadsUpCoordinator headsUpCoordinator = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.HeadsUpCoordinator$attach$3.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(String str, Continuation continuation) {
                    HeadsUpCoordinator.this.onPromotedNotificationChipTapEvent(str);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (readonlySharedFlow.$$delegate_0.collect(flowCollector, this) == coroutineSingletons) {
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

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
        return ((HeadsUpCoordinator$attach$3) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }
}
