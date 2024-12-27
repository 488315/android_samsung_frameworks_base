package com.android.systemui.volume.domain.interactor;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.data.repository.AudioSharingRepository;
import com.android.settingslib.volume.data.repository.AudioSharingRepositoryImpl;
import com.android.settingslib.volume.domain.interactor.AudioModeInteractor;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class AudioOutputInteractor {
    public final BluetoothAdapter bluetoothAdapter;
    public final Context context;
    public final ReadonlyStateFlow currentAudioDevice;
    public final DeviceIconInteractor deviceIconInteractor;
    public final FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2 isInAudioSharing;
    public final LocalBluetoothManager localBluetoothManager;
    public final MediaOutputInteractor mediaOutputInteractor;

    public AudioOutputInteractor(Context context, AudioRepository audioRepository, AudioModeInteractor audioModeInteractor, CoroutineScope coroutineScope, CoroutineContext coroutineContext, LocalBluetoothManager localBluetoothManager, BluetoothAdapter bluetoothAdapter, DeviceIconInteractor deviceIconInteractor, MediaOutputInteractor mediaOutputInteractor, AudioSharingRepository audioSharingRepository) {
        this.context = context;
        this.localBluetoothManager = localBluetoothManager;
        this.bluetoothAdapter = bluetoothAdapter;
        this.deviceIconInteractor = deviceIconInteractor;
        this.mediaOutputInteractor = mediaOutputInteractor;
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(audioModeInteractor.isOngoingCall, new AudioOutputInteractor$special$$inlined$flatMapLatest$1(null, audioRepository, this));
        Flow flowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L43
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.volume.domain.model.AudioOutputDevice r5 = (com.android.systemui.volume.domain.model.AudioOutputDevice) r5
                        if (r5 != 0) goto L38
                        com.android.systemui.volume.domain.model.AudioOutputDevice$Unknown r5 = com.android.systemui.volume.domain.model.AudioOutputDevice.Unknown.INSTANCE
                    L38:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L43
                        return r1
                    L43:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineContext);
        SharingStarted.Companion.getClass();
        this.currentAudioDevice = FlowKt.stateIn(flowOn, coroutineScope, SharingStarted.Companion.Eagerly, AudioOutputDevice.Unknown.INSTANCE);
        this.isInAudioSharing = ((AudioSharingRepositoryImpl) audioSharingRepository).inAudioSharing;
    }
}
