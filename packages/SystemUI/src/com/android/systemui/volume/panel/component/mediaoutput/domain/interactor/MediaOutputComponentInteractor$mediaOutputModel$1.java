package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.content.Context;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaOutputComponentModel;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.SessionWithPlaybackState;
import com.android.systemui.volume.panel.shared.model.ResultKt$filterData$$inlined$map$1;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;

/* loaded from: classes3.dex */
final class MediaOutputComponentInteractor$mediaOutputModel$1 extends SuspendLambda implements Function4 {
    /* synthetic */ Object L$0;
    /* synthetic */ boolean Z$0;
    /* synthetic */ boolean Z$1;
    int label;
    final /* synthetic */ MediaOutputComponentInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaOutputComponentInteractor$mediaOutputModel$1(MediaOutputComponentInteractor mediaOutputComponentInteractor, Continuation continuation) {
        super(4, continuation);
        this.this$0 = mediaOutputComponentInteractor;
    }

    @Override // kotlin.jvm.functions.Function4
    public final Object invoke(Object obj, Object obj2, Object obj3, Object obj4) {
        boolean zBooleanValue = ((Boolean) obj).booleanValue();
        boolean zBooleanValue2 = ((Boolean) obj2).booleanValue();
        MediaOutputComponentInteractor$mediaOutputModel$1 mediaOutputComponentInteractor$mediaOutputModel$1 = new MediaOutputComponentInteractor$mediaOutputModel$1(this.this$0, (Continuation) obj4);
        mediaOutputComponentInteractor$mediaOutputModel$1.Z$0 = zBooleanValue;
        mediaOutputComponentInteractor$mediaOutputModel$1.Z$1 = zBooleanValue2;
        mediaOutputComponentInteractor$mediaOutputModel$1.L$0 = (AudioOutputDevice) obj3;
        return mediaOutputComponentInteractor$mediaOutputModel$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        final boolean z = this.Z$0;
        boolean z2 = this.Z$1;
        final AudioOutputDevice audioOutputDevice = (AudioOutputDevice) this.L$0;
        if (z2) {
            Context context = this.this$0.context;
            return new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(new MediaOutputComponentModel.Calling(audioOutputDevice, z, false));
        }
        final ResultKt$filterData$$inlined$map$1 resultKt$filterData$$inlined$map$1FilterData = com.android.systemui.volume.panel.shared.model.ResultKt.filterData(this.this$0.sessionWithPlaybackState);
        return new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1

            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ AudioOutputDevice $currentAudioDevice$inlined;
                public final /* synthetic */ boolean $isInAudioSharing$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$mediaOutputModel$1$invokeSuspend$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AudioOutputDevice audioOutputDevice, boolean z) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$currentAudioDevice$inlined = audioOutputDevice;
                    this.$isInAudioSharing$inlined = z;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object mediaSession;
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
                        SessionWithPlaybackState sessionWithPlaybackState = (SessionWithPlaybackState) obj;
                        boolean z = false;
                        if (sessionWithPlaybackState == null) {
                            AudioOutputDevice audioOutputDevice = this.$currentAudioDevice$inlined;
                            boolean z2 = this.$isInAudioSharing$inlined;
                            if (!z2 && !(audioOutputDevice instanceof AudioOutputDevice.Unknown)) {
                                z = true;
                            }
                            mediaSession = new MediaOutputComponentModel.Idle(audioOutputDevice, z2, z);
                        } else {
                            AudioOutputDevice audioOutputDevice2 = this.$currentAudioDevice$inlined;
                            boolean z3 = this.$isInAudioSharing$inlined;
                            mediaSession = new MediaOutputComponentModel.MediaSession(sessionWithPlaybackState.session, sessionWithPlaybackState.isPlaybackActive, audioOutputDevice2, z3, (z3 || (audioOutputDevice2 instanceof AudioOutputDevice.Unknown)) ? false : true);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mediaSession, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = resultKt$filterData$$inlined$map$1FilterData.collect(new AnonymousClass2(flowCollector, audioOutputDevice, z), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }
}
