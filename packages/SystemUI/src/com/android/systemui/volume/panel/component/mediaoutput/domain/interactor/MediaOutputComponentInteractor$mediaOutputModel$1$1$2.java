package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.SessionWithPlaybackState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

final class MediaOutputComponentInteractor$mediaOutputModel$1$1$2 extends SuspendLambda implements Function3 {
    final /* synthetic */ boolean $isInAudioSharing;
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    public MediaOutputComponentInteractor$mediaOutputModel$1$1$2(boolean z, Continuation continuation) {
        super(3, continuation);
        this.$isInAudioSharing = z;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaOutputComponentInteractor$mediaOutputModel$1$1$2 mediaOutputComponentInteractor$mediaOutputModel$1$1$2 = new MediaOutputComponentInteractor$mediaOutputModel$1$1$2(this.$isInAudioSharing, (Continuation) obj3);
        mediaOutputComponentInteractor$mediaOutputModel$1$1$2.L$0 = (SessionWithPlaybackState) obj;
        mediaOutputComponentInteractor$mediaOutputModel$1$1$2.L$1 = (AudioOutputDevice) obj2;
        return mediaOutputComponentInteractor$mediaOutputModel$1$1$2.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        SessionWithPlaybackState sessionWithPlaybackState = (SessionWithPlaybackState) this.L$0;
        AudioOutputDevice audioOutputDevice = (AudioOutputDevice) this.L$1;
        if (sessionWithPlaybackState == null) {
            return new MediaOutputComponentModel.Idle(audioOutputDevice, this.$isInAudioSharing);
        }
        return new MediaOutputComponentModel.MediaSession(sessionWithPlaybackState.session, sessionWithPlaybackState.isPlaybackActive, audioOutputDevice, this.$isInAudioSharing);
    }
}
