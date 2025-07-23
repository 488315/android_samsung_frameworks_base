package com.android.settingslib.media.session;

import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import androidx.concurrent.futures.DirectExecutor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class MediaSessionManagerExtKt$defaultRemoteSessionChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaSessionManager $this_defaultRemoteSessionChanged;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionManagerExtKt$defaultRemoteSessionChanged$1(MediaSessionManager mediaSessionManager, Continuation continuation) {
        super(2, continuation);
        this.$this_defaultRemoteSessionChanged = mediaSessionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaSessionManagerExtKt$defaultRemoteSessionChanged$1 mediaSessionManagerExtKt$defaultRemoteSessionChanged$1 = new MediaSessionManagerExtKt$defaultRemoteSessionChanged$1(this.$this_defaultRemoteSessionChanged, continuation);
        mediaSessionManagerExtKt$defaultRemoteSessionChanged$1.L$0 = obj;
        return mediaSessionManagerExtKt$defaultRemoteSessionChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionManagerExtKt$defaultRemoteSessionChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            MediaSessionManager.RemoteSessionCallback remoteSessionCallback = new MediaSessionManager.RemoteSessionCallback() { // from class: com.android.settingslib.media.session.MediaSessionManagerExtKt$defaultRemoteSessionChanged$1$callback$1
                public final void onDefaultRemoteSessionChanged(MediaSession.Token token) {
                    ProducerScope producerScope2 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope2, null, null, new MediaSessionManagerExtKt$defaultRemoteSessionChanged$1$callback$1$onDefaultRemoteSessionChanged$1(producerScope2, token, null), 3);
                }

                public final void onVolumeChanged(MediaSession.Token token, int i2) {
                }
            };
            this.$this_defaultRemoteSessionChanged.registerRemoteSessionCallback(DirectExecutor.INSTANCE, remoteSessionCallback);
            MediaSessionManagerExtKt$activeMediaChanges$1$$ExternalSyntheticLambda0 mediaSessionManagerExtKt$activeMediaChanges$1$$ExternalSyntheticLambda0 = new MediaSessionManagerExtKt$activeMediaChanges$1$$ExternalSyntheticLambda0(this.$this_defaultRemoteSessionChanged, remoteSessionCallback, 1);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, mediaSessionManagerExtKt$activeMediaChanges$1$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
