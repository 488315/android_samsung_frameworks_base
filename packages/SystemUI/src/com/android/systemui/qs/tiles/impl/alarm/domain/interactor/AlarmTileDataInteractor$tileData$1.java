package com.android.systemui.qs.tiles.impl.alarm.domain.interactor;

import android.app.AlarmManager;
import com.android.systemui.qs.tiles.impl.alarm.domain.model.AlarmTileModel;
import com.android.systemui.statusbar.policy.NextAlarmController;
import com.android.systemui.statusbar.policy.NextAlarmControllerImpl;
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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class AlarmTileDataInteractor$tileData$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AlarmTileDataInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AlarmTileDataInteractor$tileData$1(AlarmTileDataInteractor alarmTileDataInteractor, Continuation continuation) {
        super(2, continuation);
        this.this$0 = alarmTileDataInteractor;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AlarmTileDataInteractor$tileData$1 alarmTileDataInteractor$tileData$1 = new AlarmTileDataInteractor$tileData$1(this.this$0, continuation);
        alarmTileDataInteractor$tileData$1.L$0 = obj;
        return alarmTileDataInteractor$tileData$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AlarmTileDataInteractor$tileData$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final AlarmTileDataInteractor alarmTileDataInteractor = this.this$0;
            final NextAlarmController.NextAlarmChangeCallback nextAlarmChangeCallback = new NextAlarmController.NextAlarmChangeCallback() { // from class: com.android.systemui.qs.tiles.impl.alarm.domain.interactor.AlarmTileDataInteractor$tileData$1$alarmCallback$1
                @Override // com.android.systemui.statusbar.policy.NextAlarmController.NextAlarmChangeCallback
                public final void onNextAlarmChanged(AlarmManager.AlarmClockInfo alarmClockInfo) {
                    ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(alarmClockInfo == null ? AlarmTileModel.NoAlarmSet.INSTANCE : new AlarmTileModel.NextAlarmSet(AlarmTileDataInteractor.this.dateFormatUtil.is24HourFormat(), alarmClockInfo));
                }
            };
            ((NextAlarmControllerImpl) this.this$0.alarmController).addCallback(nextAlarmChangeCallback);
            final AlarmTileDataInteractor alarmTileDataInteractor2 = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.systemui.qs.tiles.impl.alarm.domain.interactor.AlarmTileDataInteractor$tileData$1.1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ((NextAlarmControllerImpl) AlarmTileDataInteractor.this.alarmController).removeCallback(nextAlarmChangeCallback);
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
