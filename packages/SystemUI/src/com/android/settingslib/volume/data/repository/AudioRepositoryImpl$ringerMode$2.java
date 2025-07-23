package com.android.settingslib.volume.data.repository;

import com.android.settingslib.volume.shared.model.RingerMode;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class AudioRepositoryImpl$ringerMode$2 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AudioRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioRepositoryImpl$ringerMode$2(AudioRepositoryImpl audioRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioRepositoryImpl$ringerMode$2 audioRepositoryImpl$ringerMode$2 = new AudioRepositoryImpl$ringerMode$2(this.this$0, continuation);
        audioRepositoryImpl$ringerMode$2.L$0 = obj;
        return audioRepositoryImpl$ringerMode$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioRepositoryImpl$ringerMode$2) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            int ringerModeInternal = this.this$0.audioManager.getRingerModeInternal();
            RingerMode.m991constructorimpl(ringerModeInternal);
            RingerMode m990boximpl = RingerMode.m990boximpl(ringerModeInternal);
            this.label = 1;
            if (flowCollector.emit(m990boximpl, this) == coroutineSingletons) {
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
