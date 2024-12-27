package com.android.systemui.media.mediaoutput.viewmodel;

import android.media.session.MediaController;
import android.media.session.MediaSessionManager;
import android.util.Log;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.SendChannel;

final class MediaSessionViewModel$Companion$activeMediaChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ MediaSessionManager $this_activeMediaChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaSessionViewModel$Companion$activeMediaChanges$1(MediaSessionManager mediaSessionManager, Continuation continuation) {
        super(2, continuation);
        this.$this_activeMediaChanges = mediaSessionManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaSessionViewModel$Companion$activeMediaChanges$1 mediaSessionViewModel$Companion$activeMediaChanges$1 = new MediaSessionViewModel$Companion$activeMediaChanges$1(this.$this_activeMediaChanges, continuation);
        mediaSessionViewModel$Companion$activeMediaChanges$1.L$0 = obj;
        return mediaSessionViewModel$Companion$activeMediaChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaSessionViewModel$Companion$activeMediaChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        final ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope2 = (ProducerScope) this.L$0;
            EmptyList emptyList = EmptyList.INSTANCE;
            this.L$0 = producerScope2;
            this.label = 1;
            Object send = ((ChannelCoroutine) producerScope2)._channel.send(emptyList, this);
            producerScope = producerScope2;
            if (send == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            ProducerScope producerScope3 = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
            producerScope = producerScope3;
        }
        final MediaSessionManager.OnActiveSessionsChangedListener onActiveSessionsChangedListener = new MediaSessionManager.OnActiveSessionsChangedListener() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1$listener$1

            /* renamed from: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1$listener$1$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements Function2 {
                final /* synthetic */ ProducerScope $$this$callbackFlow;
                final /* synthetic */ List<MediaController> $it;
                int label;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(ProducerScope producerScope, List<MediaController> list, Continuation continuation) {
                    super(2, continuation);
                    this.$$this$callbackFlow = producerScope;
                    this.$it = list;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Continuation create(Object obj, Continuation continuation) {
                    return new AnonymousClass1(this.$$this$callbackFlow, this.$it, continuation);
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
                        SendChannel sendChannel = this.$$this$callbackFlow;
                        Object obj2 = this.$it;
                        if (obj2 == null) {
                            obj2 = EmptyList.INSTANCE;
                        }
                        this.label = 1;
                        if (((ChannelCoroutine) sendChannel)._channel.send(obj2, this) == coroutineSingletons) {
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

            @Override // android.media.session.MediaSessionManager.OnActiveSessionsChangedListener
            public final void onActiveSessionsChanged(List list) {
                Log.d("MediaSessionViewModel", "onActiveSessionsChanged() - " + list);
                ProducerScope producerScope4 = ProducerScope.this;
                BuildersKt.launch$default(producerScope4, null, null, new AnonymousClass1(producerScope4, list, null), 3);
            }
        };
        this.$this_activeMediaChanges.addOnActiveSessionsChangedListener(onActiveSessionsChangedListener, null);
        final MediaSessionManager mediaSessionManager = this.$this_activeMediaChanges;
        Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaSessionViewModel$Companion$activeMediaChanges$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.d("MediaSessionViewModel", "removeOnActiveSessionsChangedListener");
                mediaSessionManager.removeOnActiveSessionsChangedListener(onActiveSessionsChangedListener);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
