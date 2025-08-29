package com.android.systemui.qs.tiles.impl.alarm.domain.interactor;

import android.app.AlarmManager;
import android.os.UserHandle;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor;
import com.android.systemui.qs.tiles.impl.alarm.domain.model.AlarmTileModel;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
import com.android.systemui.util.time.DateFormatUtil;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes2.dex */
public final class AlarmTileDataInteractor implements QSTileDataInteractor {
    public final NextAlarmController alarmController;
    public final DateFormatUtil dateFormatUtil;

    /* renamed from: com.android.systemui.qs.tiles.impl.alarm.domain.interactor.AlarmTileDataInteractor$tileData$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        private /* synthetic */ Object L$0;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            AnonymousClass1 anonymousClass1 = AlarmTileDataInteractor.this.new AnonymousClass1(continuation);
            anonymousClass1.L$0 = obj;
            return anonymousClass1;
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.qs.tiles.impl.alarm.domain.interactor.AlarmTileDataInteractor$tileData$1$alarmCallback$1, java.lang.Object] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                final ProducerScope producerScope = (ProducerScope) this.L$0;
                final AlarmTileDataInteractor alarmTileDataInteractor = AlarmTileDataInteractor.this;
                final ?? r1 = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.qs.tiles.impl.alarm.domain.interactor.AlarmTileDataInteractor$tileData$1$alarmCallback$1
                    @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
                    public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                        ((ChannelCoroutine) producerScope).mo3475trySendJP2dKIU(alarmClockInfo == null ? AlarmTileModel.NoAlarmSet.INSTANCE : new AlarmTileModel.NextAlarmSet(alarmTileDataInteractor.dateFormatUtil.is24HourFormat(), alarmClockInfo));
                    }
                };
                ((NextAlarmControllerImpl) AlarmTileDataInteractor.this.alarmController).addCallback(r1);
                final AlarmTileDataInteractor alarmTileDataInteractor2 = AlarmTileDataInteractor.this;
                Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.alarm.domain.interactor.AlarmTileDataInteractor$tileData$1$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        ((NextAlarmControllerImpl) alarmTileDataInteractor2.alarmController).removeCallback(r1);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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

    public AlarmTileDataInteractor(NextAlarmController nextAlarmController, DateFormatUtil dateFormatUtil) {
        this.alarmController = nextAlarmController;
        this.dateFormatUtil = dateFormatUtil;
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileAvailabilityInteractor
    public final Flow availability(UserHandle userHandle) {
        return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(Boolean.TRUE);
    }

    @Override // com.android.systemui.qs.tiles.base.domain.interactor.QSTileDataInteractor
    public final Flow tileData(UserHandle userHandle, ReadonlyStateFlow readonlyStateFlow) {
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(null);
        conflatedCallbackFlow.getClass();
        return FlowConflatedKt.conflatedCallbackFlow(anonymousClass1);
    }
}
