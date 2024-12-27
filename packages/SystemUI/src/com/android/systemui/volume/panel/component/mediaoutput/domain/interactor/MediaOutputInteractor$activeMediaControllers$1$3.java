package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.flow.FlowCollector;

final class MediaOutputInteractor$activeMediaControllers$1$3 extends SuspendLambda implements Function2 {
    final /* synthetic */ List<MediaController> $activeSessions;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputInteractor$activeMediaControllers$1$3(List<MediaController> list, Continuation continuation) {
        super(2, continuation);
        this.$activeSessions = list;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaOutputInteractor$activeMediaControllers$1$3 mediaOutputInteractor$activeMediaControllers$1$3 = new MediaOutputInteractor$activeMediaControllers$1$3(this.$activeSessions, continuation);
        mediaOutputInteractor$activeMediaControllers$1$3.L$0 = obj;
        return mediaOutputInteractor$activeMediaControllers$1$3;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaOutputInteractor$activeMediaControllers$1$3) create((FlowCollector) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            List<MediaController> list = this.$activeSessions;
            this.label = 1;
            if (flowCollector.emit(list, this) == coroutineSingletons) {
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
