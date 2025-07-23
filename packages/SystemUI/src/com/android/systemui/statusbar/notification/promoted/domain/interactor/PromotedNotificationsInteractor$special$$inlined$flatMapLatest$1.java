package com.android.systemui.statusbar.notification.promoted.domain.interactor;

import com.android.systemui.statusbar.chips.screenrecord.domain.model.ScreenRecordChipModel;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class PromotedNotificationsInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ PromotedNotificationsInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PromotedNotificationsInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, PromotedNotificationsInteractor promotedNotificationsInteractor) {
        super(3, continuation);
        this.this$0 = promotedNotificationsInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        PromotedNotificationsInteractor$special$$inlined$flatMapLatest$1 promotedNotificationsInteractor$special$$inlined$flatMapLatest$1 = new PromotedNotificationsInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        promotedNotificationsInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        promotedNotificationsInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return promotedNotificationsInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow distinctUntilChanged;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ScreenRecordChipModel screenRecordChipModel = (ScreenRecordChipModel) this.L$1;
            if (screenRecordChipModel instanceof ScreenRecordChipModel.DoingNothing) {
                distinctUntilChanged = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            } else if (screenRecordChipModel instanceof ScreenRecordChipModel.Starting) {
                distinctUntilChanged = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            } else {
                if (!(screenRecordChipModel instanceof ScreenRecordChipModel.Recording)) {
                    throw new NoWhenBranchMatchedException();
                }
                PromotedNotificationsInteractor promotedNotificationsInteractor = this.this$0;
                String str = ((ScreenRecordChipModel.Recording) screenRecordChipModel).hostPackage;
                if (str == null) {
                    promotedNotificationsInteractor.getClass();
                    distinctUntilChanged = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
                } else {
                    distinctUntilChanged = FlowKt.distinctUntilChanged(new PromotedNotificationsInteractor$createRecordingNotificationFlow$$inlined$map$1(promotedNotificationsInteractor.activeNotificationsInteractor.allRepresentativeNotifications, promotedNotificationsInteractor, str));
                }
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, distinctUntilChanged, this) == coroutineSingletons) {
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
