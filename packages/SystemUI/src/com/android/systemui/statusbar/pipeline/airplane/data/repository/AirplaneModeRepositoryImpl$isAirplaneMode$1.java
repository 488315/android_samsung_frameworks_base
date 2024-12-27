package com.android.systemui.statusbar.pipeline.airplane.data.repository;

import android.os.Handler;
import com.android.systemui.qs.SettingObserver;
import com.android.systemui.util.settings.GlobalSettings;
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

final class AirplaneModeRepositoryImpl$isAirplaneMode$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AirplaneModeRepositoryImpl this$0;

    public AirplaneModeRepositoryImpl$isAirplaneMode$1(AirplaneModeRepositoryImpl airplaneModeRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = airplaneModeRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AirplaneModeRepositoryImpl$isAirplaneMode$1 airplaneModeRepositoryImpl$isAirplaneMode$1 = new AirplaneModeRepositoryImpl$isAirplaneMode$1(this.this$0, continuation);
        airplaneModeRepositoryImpl$isAirplaneMode$1.L$0 = obj;
        return airplaneModeRepositoryImpl$isAirplaneMode$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AirplaneModeRepositoryImpl$isAirplaneMode$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            AirplaneModeRepositoryImpl airplaneModeRepositoryImpl = this.this$0;
            final GlobalSettings globalSettings = airplaneModeRepositoryImpl.globalSettings;
            final Handler handler = airplaneModeRepositoryImpl.bgHandler;
            final ?? r4 = new SettingObserver(globalSettings, handler) { // from class: com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl$isAirplaneMode$1$observer$1
                @Override // com.android.systemui.qs.SettingObserver
                public final void handleValueChanged(int i2, boolean z) {
                    ((ChannelCoroutine) ProducerScope.this).mo2552trySendJP2dKIU(Boolean.valueOf(i2 == 1));
                }
            };
            r4.setListening(true);
            ((ChannelCoroutine) producerScope).mo2552trySendJP2dKIU(Boolean.valueOf(r4.getValue() == 1));
            Function0 function0 = new Function0() { // from class: com.android.systemui.statusbar.pipeline.airplane.data.repository.AirplaneModeRepositoryImpl$isAirplaneMode$1.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    setListening(false);
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
