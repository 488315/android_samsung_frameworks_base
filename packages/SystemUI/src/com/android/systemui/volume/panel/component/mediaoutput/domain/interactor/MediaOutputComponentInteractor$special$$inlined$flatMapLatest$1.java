package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.PlaybackState;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.SessionWithPlaybackState;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
public final class MediaOutputComponentInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaOutputComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputComponentInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, MediaOutputComponentInteractor mediaOutputComponentInteractor) {
        super(3, continuation);
        this.this$0 = mediaOutputComponentInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaOutputComponentInteractor$special$$inlined$flatMapLatest$1 mediaOutputComponentInteractor$special$$inlined$flatMapLatest$1 = new MediaOutputComponentInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        mediaOutputComponentInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        mediaOutputComponentInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return mediaOutputComponentInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final MediaDeviceSession mediaDeviceSession = (MediaDeviceSession) this.L$1;
            if (mediaDeviceSession == null) {
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
            } else {
                final MediaDeviceSessionInteractor$playbackState$$inlined$map$1 mediaDeviceSessionInteractor$playbackState$$inlined$map$1PlaybackState = this.this$0.mediaDeviceSessionInteractor.playbackState(mediaDeviceSession);
                flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 = new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$sessionWithPlaybackState$lambda$2$$inlined$mapNotNull$1

                    /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$sessionWithPlaybackState$lambda$2$$inlined$mapNotNull$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ MediaDeviceSession $session$inlined;
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$sessionWithPlaybackState$lambda$2$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, MediaDeviceSession mediaDeviceSession) {
                            this.$this_unsafeFlow = flowCollector;
                            this.$session$inlined = mediaDeviceSession;
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
                                PlaybackState playbackState = (PlaybackState) obj;
                                SessionWithPlaybackState sessionWithPlaybackState = playbackState != null ? new SessionWithPlaybackState(this.$session$inlined, playbackState.isActive()) : null;
                                if (sessionWithPlaybackState != null) {
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(sessionWithPlaybackState, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
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
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = mediaDeviceSessionInteractor$playbackState$$inlined$map$1PlaybackState.collect(new AnonymousClass2(flowCollector2, mediaDeviceSession), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2, this) == coroutineSingletons) {
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
