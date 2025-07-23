package com.android.systemui.statusbar.phone.ongoingcall.domain.interactor;

import com.android.systemui.activity.data.repository.ActivityManagerRepositoryImpl;
import com.android.systemui.log.core.Logger;
import com.android.systemui.statusbar.notification.shared.ActiveNotificationModel;
import com.android.systemui.statusbar.phone.ongoingcall.shared.model.OngoingCallModel;
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
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class OngoingCallInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ OngoingCallInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public OngoingCallInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, OngoingCallInteractor ongoingCallInteractor) {
        super(3, continuation);
        this.this$0 = ongoingCallInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        OngoingCallInteractor$special$$inlined$flatMapLatest$1 ongoingCallInteractor$special$$inlined$flatMapLatest$1 = new OngoingCallInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        ongoingCallInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        ongoingCallInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return ongoingCallInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__ZipKt$combine$$inlined$unsafeFlow$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            ActiveNotificationModel activeNotificationModel = (ActiveNotificationModel) this.L$1;
            OngoingCallInteractor ongoingCallInteractor = this.this$0;
            Logger logger = ongoingCallInteractor.logger;
            if (activeNotificationModel == null) {
                Logger.d$default(logger, "No active call notification - hiding chip", null, 2, null);
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(OngoingCallModel.NoCall.INSTANCE);
            } else {
                flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(activeNotificationModel), ((ActivityManagerRepositoryImpl) ongoingCallInteractor.activityManagerRepository).createIsAppVisibleFlow(activeNotificationModel.uid, logger, OngoingCallInteractor.TAG), new OngoingCallInteractor$createOngoingCallStateFlow$1(ongoingCallInteractor, null));
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__ZipKt$combine$$inlined$unsafeFlow$1, this) == coroutineSingletons) {
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
