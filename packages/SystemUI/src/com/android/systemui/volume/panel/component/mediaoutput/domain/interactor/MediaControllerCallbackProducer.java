package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Bundle;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

/* loaded from: classes3.dex */
public final class MediaControllerCallbackProducer extends MediaController.Callback {
    public final ProducerScope producingScope;

    /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaControllerCallbackProducer$send$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ MediaControllerChangeModel $change;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(MediaControllerChangeModel mediaControllerChangeModel, Continuation continuation) {
            super(2, continuation);
            this.$change = mediaControllerChangeModel;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaControllerCallbackProducer.this.new AnonymousClass1(this.$change, continuation);
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
                SendChannel sendChannel = MediaControllerCallbackProducer.this.producingScope;
                MediaControllerChangeModel mediaControllerChangeModel = this.$change;
                this.label = 1;
                if (((ChannelCoroutine) sendChannel)._channel.send(mediaControllerChangeModel, this) == coroutineSingletons) {
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

    public MediaControllerCallbackProducer(ProducerScope producerScope) {
        this.producingScope = producerScope;
    }

    @Override // android.media.session.MediaController.Callback
    public final void onAudioInfoChanged(MediaController.PlaybackInfo playbackInfo) {
        send(new MediaControllerChangeModel.AudioInfoChanged(playbackInfo));
    }

    @Override // android.media.session.MediaController.Callback
    public final void onExtrasChanged(Bundle bundle) {
        send(new MediaControllerChangeModel.ExtrasChanged(bundle));
    }

    @Override // android.media.session.MediaController.Callback
    public final void onMetadataChanged(MediaMetadata mediaMetadata) {
        send(new MediaControllerChangeModel.MetadataChanged(mediaMetadata));
    }

    @Override // android.media.session.MediaController.Callback
    public final void onPlaybackStateChanged(PlaybackState playbackState) {
        send(new MediaControllerChangeModel.PlaybackStateChanged(playbackState));
    }

    @Override // android.media.session.MediaController.Callback
    public final void onQueueChanged(List list) {
        send(new MediaControllerChangeModel.QueueChanged(list));
    }

    @Override // android.media.session.MediaController.Callback
    public final void onQueueTitleChanged(CharSequence charSequence) {
        send(new MediaControllerChangeModel.QueueTitleChanged(charSequence));
    }

    @Override // android.media.session.MediaController.Callback
    public final void onSessionDestroyed() {
        send(MediaControllerChangeModel.SessionDestroyed.INSTANCE);
    }

    @Override // android.media.session.MediaController.Callback
    public final void onSessionEvent(String str, Bundle bundle) {
        send(new MediaControllerChangeModel.SessionEvent(str, bundle));
    }

    public final void send(MediaControllerChangeModel mediaControllerChangeModel) {
        CoroutineTracingKt.launchTraced$default(this.producingScope, null, null, new AnonymousClass1(mediaControllerChangeModel, null), 7);
    }
}
