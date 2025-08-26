package com.android.systemui.volume.domain.interactor;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.domain.interactor.AudioModeInteractor;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class AudioOutputInteractor {
    public final BluetoothAdapter bluetoothAdapter;
    public final Context context;
    public final ReadonlyStateFlow currentAudioDevice;
    public final DeviceIconInteractor deviceIconInteractor;
    public final LocalBluetoothManager localBluetoothManager;
    public final MediaOutputInteractor mediaOutputInteractor;

    public AudioOutputInteractor(Context context, AudioRepository audioRepository, AudioModeInteractor audioModeInteractor, CoroutineScope coroutineScope, CoroutineContext coroutineContext, LocalBluetoothManager localBluetoothManager, BluetoothAdapter bluetoothAdapter, DeviceIconInteractor deviceIconInteractor, MediaOutputInteractor mediaOutputInteractor) {
        this.context = context;
        this.localBluetoothManager = localBluetoothManager;
        this.bluetoothAdapter = bluetoothAdapter;
        this.deviceIconInteractor = deviceIconInteractor;
        this.mediaOutputInteractor = mediaOutputInteractor;
        final ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(audioModeInteractor.isOngoingCall, new AudioOutputInteractor$special$$inlined$flatMapLatest$1(null, audioRepository, this));
        Flow flowFlowOn = FlowKt.flowOn(new Flow() { // from class: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$special$$inlined$map$1

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
                        Object obj3 = (AudioOutputDevice) obj;
                        if (obj3 == null) {
                            obj3 = AudioOutputDevice.Unknown.INSTANCE;
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(obj3, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = channelFlowTransformLatestTransformLatest.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineContext);
        SharingStarted.Companion.getClass();
        this.currentAudioDevice = FlowKt.stateIn(flowFlowOn, coroutineScope, SharingStarted.Companion.Eagerly, AudioOutputDevice.Unavailable.INSTANCE);
    }
}
