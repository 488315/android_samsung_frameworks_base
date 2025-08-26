package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.content.Context;
import com.android.settingslib.volume.domain.interactor.AudioModeInteractor;
import com.android.systemui.volume.domain.interactor.AudioOutputInteractor;
import com.android.systemui.volume.domain.interactor.AudioSharingInteractor;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.shared.model.Result;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import com.android.systemui.volume.panel.shared.model.ResultKt$wrapInResult$$inlined$map$1;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* loaded from: classes3.dex */
public final class MediaOutputComponentInteractor {
    public final Context context;
    public final MediaOutputComponentInteractor$special$$inlined$filter$1 currentAudioDevice;
    public final MediaDeviceSessionInteractor mediaDeviceSessionInteractor;
    public final ReadonlyStateFlow mediaOutputModel;
    public final ReadonlyStateFlow sessionWithPlaybackState;

    /* JADX WARN: Type inference failed for: r5v1, types: [com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$special$$inlined$filter$1, kotlinx.coroutines.flow.Flow] */
    public MediaOutputComponentInteractor(Context context, CoroutineScope coroutineScope, MediaDeviceSessionInteractor mediaDeviceSessionInteractor, AudioOutputInteractor audioOutputInteractor, AudioModeInteractor audioModeInteractor, MediaOutputInteractor mediaOutputInteractor, AudioSharingInteractor audioSharingInteractor) {
        this.context = context;
        this.mediaDeviceSessionInteractor = mediaDeviceSessionInteractor;
        ResultKt$wrapInResult$$inlined$map$1 resultKt$wrapInResult$$inlined$map$1 = new ResultKt$wrapInResult$$inlined$map$1(FlowKt.transformLatest(ResultKt.filterData(mediaOutputInteractor.defaultActiveMediaSession), new MediaOutputComponentInteractor$special$$inlined$flatMapLatest$1(null, this)));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        this.sessionWithPlaybackState = FlowKt.stateIn(resultKt$wrapInResult$$inlined$map$1, coroutineScope, startedEagerly, new Result.Loading());
        final ReadonlyStateFlow readonlyStateFlow = audioOutputInteractor.currentAudioDevice;
        ?? r5 = new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$special$$inlined$filter$1

            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputComponentInteractor$special$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        kotlin.ResultKt.throwOnFailure(obj2);
                        if (!(((AudioOutputDevice) obj) instanceof AudioOutputDevice.Unavailable)) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    } else {
                        if (i2 != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        kotlin.ResultKt.throwOnFailure(obj2);
                    }
                    return Unit.INSTANCE;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.currentAudioDevice = r5;
        this.mediaOutputModel = FlowKt.stateIn(new ResultKt$wrapInResult$$inlined$map$1(FlowKt.transformLatest(FlowKt.combine(audioSharingInteractor.isInAudioSharing(), audioModeInteractor.isOngoingCall, r5, new MediaOutputComponentInteractor$mediaOutputModel$1(this, null)), new MediaOutputComponentInteractor$special$$inlined$flatMapLatest$2(null))), coroutineScope, startedEagerly, new Result.Loading());
    }
}
