package com.android.systemui.volume.domain.startable;

import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.internal.logging.UiEventLogger;
import com.android.settingslib.volume.domain.interactor.AudioModeInteractor;
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

/* loaded from: classes3.dex */
public final class AudioModeLoggerStartable {
    public final AudioModeInteractor audioModeInteractor;
    public final CoroutineScope scope;
    public final UiEventLogger uiEventLogger;

    /* renamed from: com.android.systemui.volume.domain.startable.AudioModeLoggerStartable$start$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AudioModeLoggerStartable.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Flow flowDistinctUntilChanged = FlowKt.distinctUntilChanged(AudioModeLoggerStartable.this.audioModeInteractor.isOngoingCall);
                final AudioModeLoggerStartable audioModeLoggerStartable = AudioModeLoggerStartable.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.volume.domain.startable.AudioModeLoggerStartable.start.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        audioModeLoggerStartable.uiEventLogger.log(((Boolean) obj2).booleanValue() ? VolumePanelUiEvent.VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_CALLING : VolumePanelUiEvent.VOLUME_PANEL_AUDIO_MODE_CHANGE_TO_NORMAL);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowDistinctUntilChanged.collect(flowCollector, this) == coroutineSingletons) {
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

    public AudioModeLoggerStartable(CoroutineScope coroutineScope, UiEventLogger uiEventLogger, AudioModeInteractor audioModeInteractor) {
        this.scope = coroutineScope;
        this.uiEventLogger = uiEventLogger;
        this.audioModeInteractor = audioModeInteractor;
    }

    public final void start() {
        CoroutineTracingKt.launchTraced$default(this.scope, null, null, new AnonymousClass1(null), 7);
    }
}
