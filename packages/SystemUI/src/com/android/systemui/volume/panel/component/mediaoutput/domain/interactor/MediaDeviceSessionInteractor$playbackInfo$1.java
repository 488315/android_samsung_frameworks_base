package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class MediaDeviceSessionInteractor$playbackInfo$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MediaDeviceSessionInteractor$playbackInfo$1(Continuation continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaDeviceSessionInteractor$playbackInfo$1 mediaDeviceSessionInteractor$playbackInfo$1 = new MediaDeviceSessionInteractor$playbackInfo$1((Continuation) obj3);
        mediaDeviceSessionInteractor$playbackInfo$1.L$0 = (FlowCollector) obj;
        mediaDeviceSessionInteractor$playbackInfo$1.L$1 = (MediaController) obj2;
        return mediaDeviceSessionInteractor$playbackInfo$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            MediaControllerChangeModel.AudioInfoChanged audioInfoChanged = new MediaControllerChangeModel.AudioInfoChanged(((MediaController) this.L$1).getPlaybackInfo());
            this.L$0 = null;
            this.label = 1;
            if (flowCollector.emit(audioInfoChanged, this) == coroutineSingletons) {
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
