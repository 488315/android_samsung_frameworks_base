package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

final class MediaDeviceSessionInteractor$stateChanges$1$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaController $controller;
    final /* synthetic */ Function3 $onStart;
    private /* synthetic */ Object L$0;
    int label;

    public MediaDeviceSessionInteractor$stateChanges$1$1(Function3 function3, MediaController mediaController, Continuation continuation) {
        super(2, continuation);
        this.$onStart = function3;
        this.$controller = mediaController;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaDeviceSessionInteractor$stateChanges$1$1 mediaDeviceSessionInteractor$stateChanges$1$1 = new MediaDeviceSessionInteractor$stateChanges$1$1(this.$onStart, this.$controller, continuation);
        mediaDeviceSessionInteractor$stateChanges$1$1.L$0 = obj;
        return mediaDeviceSessionInteractor$stateChanges$1$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaDeviceSessionInteractor$stateChanges$1$1) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Function3 function3 = this.$onStart;
            MediaController mediaController = this.$controller;
            this.label = 1;
            if (function3.invoke(flowCollector, mediaController, this) == coroutineSingletons) {
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
