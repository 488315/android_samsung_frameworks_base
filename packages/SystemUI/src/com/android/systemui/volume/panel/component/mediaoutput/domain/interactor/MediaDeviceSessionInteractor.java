package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import android.media.session.PlaybackState;
import com.android.settingslib.volume.data.repository.MediaControllerRepository;
import com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import java.util.Collection;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$2;

/* loaded from: classes3.dex */
public final class MediaDeviceSessionInteractor {
    public final CoroutineContext backgroundCoroutineContext;
    public final MediaControllerInteractor mediaControllerInteractor;
    public final MediaControllerRepository mediaControllerRepository;

    /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            AnonymousClass1 anonymousClass1 = new AnonymousClass1((Continuation) obj3);
            anonymousClass1.L$0 = (FlowCollector) obj;
            anonymousClass1.L$1 = (MediaController) obj2;
            return anonymousClass1.invokeSuspend(Unit.INSTANCE);
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

    /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$1, reason: invalid class name and case insensitive filesystem */
    final class C11891 extends SuspendLambda implements Function3 {
        private /* synthetic */ Object L$0;
        /* synthetic */ Object L$1;
        int label;

        public C11891(Continuation continuation) {
            super(3, continuation);
        }

        @Override // kotlin.jvm.functions.Function3
        public final Object invoke(Object obj, Object obj2, Object obj3) {
            C11891 c11891 = new C11891((Continuation) obj3);
            c11891.L$0 = (FlowCollector) obj;
            c11891.L$1 = (MediaController) obj2;
            return c11891.invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                FlowCollector flowCollector = (FlowCollector) this.L$0;
                MediaControllerChangeModel.PlaybackStateChanged playbackStateChanged = new MediaControllerChangeModel.PlaybackStateChanged(((MediaController) this.L$1).getPlaybackState());
                this.L$0 = null;
                this.label = 1;
                if (flowCollector.emit(playbackStateChanged, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$setSessionVolume$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ MediaDeviceSession $mediaDeviceSession;
        final /* synthetic */ int $volume;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(MediaDeviceSession mediaDeviceSession, int i, Continuation continuation) {
            super(2, continuation);
            this.$mediaDeviceSession = mediaDeviceSession;
            this.$volume = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaDeviceSessionInteractor.this.new AnonymousClass2(this.$mediaDeviceSession, this.$volume, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            MediaDeviceSessionInteractor mediaDeviceSessionInteractor = MediaDeviceSessionInteractor.this;
            MediaController mediaControllerAccess$findControllerForSession = MediaDeviceSessionInteractor.access$findControllerForSession(mediaDeviceSessionInteractor, (Collection) ((MediaControllerRepositoryImpl) mediaDeviceSessionInteractor.mediaControllerRepository).activeSessions.$$delegate_0.getValue(), this.$mediaDeviceSession);
            boolean z = false;
            if (mediaControllerAccess$findControllerForSession != null) {
                mediaControllerAccess$findControllerForSession.setVolumeTo(this.$volume, 0);
                z = true;
            }
            return Boolean.valueOf(z);
        }
    }

    public MediaDeviceSessionInteractor(CoroutineContext coroutineContext, MediaControllerInteractor mediaControllerInteractor, MediaControllerRepository mediaControllerRepository) {
        this.backgroundCoroutineContext = coroutineContext;
        this.mediaControllerInteractor = mediaControllerInteractor;
        this.mediaControllerRepository = mediaControllerRepository;
    }

    public static final MediaController access$findControllerForSession(MediaDeviceSessionInteractor mediaDeviceSessionInteractor, Collection collection, MediaDeviceSession mediaDeviceSession) {
        Object next;
        mediaDeviceSessionInteractor.getClass();
        Iterator it = collection.iterator();
        while (true) {
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (Intrinsics.areEqual(((MediaController) next).getSessionToken(), mediaDeviceSession.sessionToken)) {
                break;
            }
        }
        return (MediaController) next;
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1] */
    public final MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1 playbackInfo(MediaDeviceSession mediaDeviceSession) {
        final FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 flowKt__TransformKt$filterIsInstance$$inlined$filter$2 = new FlowKt__TransformKt$filterIsInstance$$inlined$filter$2(FlowKt.flowOn(FlowKt.transformLatest(((MediaControllerRepositoryImpl) this.mediaControllerRepository).activeSessions, new MediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1(null, this, mediaDeviceSession, new AnonymousClass1(null))), this.backgroundCoroutineContext), Reflection.getOrCreateKotlinClass(MediaControllerChangeModel.AudioInfoChanged.class));
        return new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1

            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        MediaController.PlaybackInfo playbackInfo = ((MediaControllerChangeModel.AudioInfoChanged) obj).info;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(playbackInfo, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$filterIsInstance$$inlined$filter$2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1] */
    public final MediaDeviceSessionInteractor$playbackState$$inlined$map$1 playbackState(MediaDeviceSession mediaDeviceSession) {
        final FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 flowKt__TransformKt$filterIsInstance$$inlined$filter$2 = new FlowKt__TransformKt$filterIsInstance$$inlined$filter$2(FlowKt.flowOn(FlowKt.transformLatest(((MediaControllerRepositoryImpl) this.mediaControllerRepository).activeSessions, new MediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1(null, this, mediaDeviceSession, new C11891(null))), this.backgroundCoroutineContext), Reflection.getOrCreateKotlinClass(MediaControllerChangeModel.PlaybackStateChanged.class));
        return new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1

            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    if (continuation instanceof AnonymousClass1) {
                        anonymousClass1 = (AnonymousClass1) continuation;
                        int i = anonymousClass1.label;
                        if ((i & Integer.MIN_VALUE) != 0) {
                            anonymousClass1.label = i - Integer.MIN_VALUE;
                        } else {
                            anonymousClass1 = new AnonymousClass1(continuation);
                        }
                    }
                    Object obj2 = anonymousClass1.result;
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                    int i2 = anonymousClass1.label;
                    if (i2 == 0) {
                        ResultKt.throwOnFailure(obj2);
                        PlaybackState playbackState = ((MediaControllerChangeModel.PlaybackStateChanged) obj).state;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(playbackState, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowKt__TransformKt$filterIsInstance$$inlined$filter$2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    public final Object setSessionVolume(MediaDeviceSession mediaDeviceSession, int i, Continuation continuation) {
        if (!mediaDeviceSession.canAdjustVolume) {
            return Boolean.FALSE;
        }
        return BuildersKt.withContext(this.backgroundCoroutineContext, new AnonymousClass2(mediaDeviceSession, i, null), continuation);
    }
}
