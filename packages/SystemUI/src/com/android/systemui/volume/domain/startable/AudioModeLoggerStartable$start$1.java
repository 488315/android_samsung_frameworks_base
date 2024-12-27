package com.android.systemui.volume.domain.startable;

import com.android.systemui.volume.panel.ui.VolumePanelUiEvent;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

final class AudioModeLoggerStartable$start$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ AudioModeLoggerStartable this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioModeLoggerStartable$start$1(AudioModeLoggerStartable audioModeLoggerStartable, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioModeLoggerStartable;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new AudioModeLoggerStartable$start$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioModeLoggerStartable$start$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            Flow distinctUntilChanged = FlowKt.distinctUntilChanged(this.this$0.audioModeInteractor.isOngoingCall);
            final AudioModeLoggerStartable audioModeLoggerStartable = this.this$0;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.volume.domain.startable.AudioModeLoggerStartable$start$1.1
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    AudioModeLoggerStartable.this.uiEventLogger.log(((Boolean) obj2).booleanValue() ? VolumePanelUiEvent.VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_CALLING : VolumePanelUiEvent.VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_NORMAL);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (distinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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
