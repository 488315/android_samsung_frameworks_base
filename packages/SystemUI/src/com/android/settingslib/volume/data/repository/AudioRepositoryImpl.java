package com.android.settingslib.volume.data.repository;

import android.content.ContentResolver;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;
import com.android.settingslib.volume.data.model.VolumeControllerEvent;
import com.android.settingslib.volume.shared.AudioLogger;
import com.android.settingslib.volume.shared.AudioManagerEventsReceiver;
import com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl;
import com.android.settingslib.volume.shared.model.AudioManagerEvent;
import com.android.settingslib.volume.shared.model.AudioStream;
import com.android.settingslib.volume.shared.model.AudioStreamModel;
import com.android.settingslib.volume.shared.model.RingerMode;
import com.android.settingslib.volume.shared.model.StreamAudioManagerEvent;
import java.util.Map;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedWhileSubscribed;

/* loaded from: classes.dex */
public final class AudioRepositoryImpl implements AudioRepository {
    public final AudioManager audioManager;
    public final AudioManagerEventsReceiver audioManagerEventsReceiver;
    public final CoroutineContext backgroundCoroutineContext;
    public final ContentResolver contentResolver;
    public final CoroutineScope coroutineScope;
    public final AudioLogger logger;
    public final ReadonlyStateFlow mode;
    public final ReadonlyStateFlow ringerMode;
    public final Map streamSettingNames;
    public final ProducingVolumeController volumeController;
    public final Flow volumeControllerEvents;

    /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getBluetoothAudioDeviceCategory$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $bluetoothAddress;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(String str, Continuation continuation) {
            super(2, continuation);
            this.$bluetoothAddress = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return AudioRepositoryImpl.this.new AnonymousClass2(this.$bluetoothAddress, continuation);
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
            return new Integer(AudioRepositoryImpl.this.audioManager.getBluetoothAudioDeviceCategory(this.$bluetoothAddress));
        }
    }

    public AudioRepositoryImpl(AudioManagerEventsReceiver audioManagerEventsReceiver, AudioManager audioManager, ContentResolver contentResolver, CoroutineContext coroutineContext, CoroutineScope coroutineScope, AudioLogger audioLogger, boolean z) {
        this.audioManagerEventsReceiver = audioManagerEventsReceiver;
        this.audioManager = audioManager;
        this.contentResolver = contentResolver;
        this.backgroundCoroutineContext = coroutineContext;
        this.coroutineScope = coroutineScope;
        this.logger = audioLogger;
        ProducingVolumeController producingVolumeController = new ProducingVolumeController();
        this.volumeController = producingVolumeController;
        AudioStream.m991constructorimpl(0);
        Pair pair = new Pair(AudioStream.m990boximpl(0), "volume_voice");
        AudioStream.m991constructorimpl(1);
        Pair pair2 = new Pair(AudioStream.m990boximpl(1), "volume_system");
        AudioStream.m991constructorimpl(2);
        Pair pair3 = new Pair(AudioStream.m990boximpl(2), "volume_ring");
        AudioStream.m991constructorimpl(3);
        Pair pair4 = new Pair(AudioStream.m990boximpl(3), "volume_music");
        AudioStream.m991constructorimpl(4);
        Pair pair5 = new Pair(AudioStream.m990boximpl(4), "volume_alarm");
        AudioStream.m991constructorimpl(5);
        Pair pair6 = new Pair(AudioStream.m990boximpl(5), "volume_notification");
        AudioStream.m991constructorimpl(6);
        Pair pair7 = new Pair(AudioStream.m990boximpl(6), "volume_bluetooth_sco");
        AudioStream.m991constructorimpl(10);
        Pair pair8 = new Pair(AudioStream.m990boximpl(10), "volume_a11y");
        AudioStream.m991constructorimpl(11);
        this.streamSettingNames = MapsKt__MapsKt.mapOf(pair, pair2, pair3, pair4, pair5, pair6, pair7, pair8, new Pair(AudioStream.m990boximpl(11), "volume_assistant"));
        this.volumeControllerEvents = z ? producingVolumeController.events : EmptyFlow.INSTANCE;
        Flow flowFlowOn = FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AudioRepositoryImpl$mode$2(this, null), FlowKt.callbackFlow(new AudioRepositoryImpl$mode$1(this, null))), coroutineContext);
        SharingStarted.Companion companion = SharingStarted.Companion;
        this.mode = FlowKt.stateIn(flowFlowOn, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), Integer.valueOf(audioManager.getMode()));
        final FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 flowKt__TransformKt$filterIsInstance$$inlined$filter$2 = new FlowKt__TransformKt$filterIsInstance$$inlined$filter$2(((AudioManagerEventsReceiverImpl) audioManagerEventsReceiver).events, Reflection.getOrCreateKotlinClass(AudioManagerEvent.InternalRingerModeChanged.class));
        Flow flowFlowOn2 = FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AudioRepositoryImpl$ringerMode$2(this, null), new Flow() { // from class: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$special$$inlined$map$1

            /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AudioRepositoryImpl this$0;

                /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AudioRepositoryImpl audioRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = audioRepositoryImpl;
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
                        int ringerModeInternal = this.this$0.audioManager.getRingerModeInternal();
                        RingerMode.m993constructorimpl(ringerModeInternal);
                        RingerMode ringerModeM992boximpl = RingerMode.m992boximpl(ringerModeInternal);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(ringerModeM992boximpl, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__TransformKt$filterIsInstance$$inlined$filter$2.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), coroutineContext);
        StartedWhileSubscribed startedWhileSubscribedWhileSubscribed$default = SharingStarted.Companion.WhileSubscribed$default(companion, 3);
        int ringerModeInternal = audioManager.getRingerModeInternal();
        RingerMode.m993constructorimpl(ringerModeInternal);
        this.ringerMode = FlowKt.stateIn(flowFlowOn2, coroutineScope, startedWhileSubscribedWhileSubscribed$default, RingerMode.m992boximpl(ringerModeInternal));
    }

    /* renamed from: access$getCurrentAudioStream-tLTdkI8, reason: not valid java name */
    public static final AudioStreamModel m979access$getCurrentAudioStreamtLTdkI8(AudioRepositoryImpl audioRepositoryImpl, int i) {
        int streamMinVolume;
        audioRepositoryImpl.getClass();
        try {
            streamMinVolume = audioRepositoryImpl.audioManager.getStreamMinVolume(i);
        } catch (IllegalArgumentException unused) {
            streamMinVolume = audioRepositoryImpl.audioManager.getStreamMinVolume(0);
        }
        return new AudioStreamModel(i, audioRepositoryImpl.audioManager.getStreamVolume(i), streamMinVolume, audioRepositoryImpl.audioManager.getStreamMaxVolume(i), audioRepositoryImpl.audioManager.isStreamMutableByUi(i), audioRepositoryImpl.audioManager.isStreamAffectedByRingerMode(i), audioRepositoryImpl.audioManager.isStreamMute(i), null);
    }

    /* renamed from: getAudioStream-tLTdkI8, reason: not valid java name */
    public final Flow m980getAudioStreamtLTdkI8(final int i) {
        final ReadonlySharedFlow readonlySharedFlow = ((AudioManagerEventsReceiverImpl) this.audioManagerEventsReceiver).events;
        Flow flow = new Flow() { // from class: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$filter$1

            /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $audioStream$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$audioStream$inlined = i;
                }

                /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
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
                        AudioManagerEvent audioManagerEvent = (AudioManagerEvent) obj;
                        if (audioManagerEvent instanceof StreamAudioManagerEvent) {
                            int iMo989getAudioStream2ffMKO0 = ((StreamAudioManagerEvent) audioManagerEvent).mo989getAudioStream2ffMKO0();
                            AudioStream.Companion companion = AudioStream.Companion;
                            if (iMo989getAudioStream2ffMKO0 == this.$audioStream$inlined) {
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlySharedFlow.collect(new AnonymousClass2(flowCollector, i), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        String str = (String) this.streamSettingNames.get(AudioStream.m990boximpl(i));
        Uri uriFor = str != null ? Settings.System.getUriFor(str) : null;
        Flow flowCallbackFlow = uriFor == null ? EmptyFlow.INSTANCE : FlowKt.callbackFlow(new AudioRepositoryImpl$volumeSettingChanges$1(this, uriFor, null));
        final Flow flow2 = this.volumeControllerEvents;
        final Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.merge(flow, flowCallbackFlow, new Flow() { // from class: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$filter$2

            /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$filter$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$filter$2$2$1, reason: invalid class name */
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
                        ResultKt.throwOnFailure(obj2);
                        if (((VolumeControllerEvent) obj) instanceof VolumeControllerEvent.VolumeChanged) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow2.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), -1, 2);
        return FlowKt.flowOn(new FlowKt__TransformKt$onEach$$inlined$unsafeTransform$1(FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AudioRepositoryImpl$getAudioStream$4(this, i, null), new Flow() { // from class: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$map$1

            /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ int $audioStream$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AudioRepositoryImpl this$0;

                /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$getAudioStream-tLTdkI8$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AudioRepositoryImpl audioRepositoryImpl, int i) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = audioRepositoryImpl;
                    this.$audioStream$inlined = i;
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
                        AudioStreamModel audioStreamModelM979access$getCurrentAudioStreamtLTdkI8 = AudioRepositoryImpl.m979access$getCurrentAudioStreamtLTdkI8(this.this$0, this.$audioStream$inlined);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(audioStreamModelM979access$getCurrentAudioStreamtLTdkI8, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowBuffer$default.collect(new AnonymousClass2(flowCollector, this, i), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        })), new AudioRepositoryImpl$getAudioStream$5(this, i, null)), this.backgroundCoroutineContext);
    }

    public final Object getBluetoothAudioDeviceCategory(String str, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundCoroutineContext, new AnonymousClass2(str, null), continuation);
    }

    public final ReadonlyStateFlow getCommunicationDevice() {
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.callbackFlow(new AudioRepositoryImpl$communicationDevice$1(this, null)));
        return FlowKt.stateIn(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AudioRepositoryImpl$communicationDevice$3(this, null), new Flow() { // from class: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$special$$inlined$map$2

            /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AudioRepositoryImpl this$0;

                /* renamed from: com.android.settingslib.volume.data.repository.AudioRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AudioRepositoryImpl audioRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = audioRepositoryImpl;
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
                        AudioDeviceInfo communicationDevice = this.this$0.audioManager.getCommunicationDevice();
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(communicationDevice, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), this.backgroundCoroutineContext), this.coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), this.audioManager.getCommunicationDevice());
    }

    /* renamed from: getLastAudibleVolume-VrMivd8, reason: not valid java name */
    public final Object m981getLastAudibleVolumeVrMivd8(int i, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundCoroutineContext, new AudioRepositoryImpl$getLastAudibleVolume$2(this, i, null), continuation);
    }

    /* renamed from: setMuted-ZdW0WiI, reason: not valid java name */
    public final Object m982setMutedZdW0WiI(int i, boolean z, Continuation continuation) {
        return BuildersKt.withContext(this.backgroundCoroutineContext, new AudioRepositoryImpl$setMuted$2(z, this, i, null), continuation);
    }

    /* renamed from: setRingerModeInternal-2JRsiQU, reason: not valid java name */
    public final Object m983setRingerModeInternal2JRsiQU(int i, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundCoroutineContext, new AudioRepositoryImpl$setRingerModeInternal$2(this, i, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }

    /* renamed from: setVolume-ZdW0WiI, reason: not valid java name */
    public final Object m984setVolumeZdW0WiI(int i, int i2, Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundCoroutineContext, new AudioRepositoryImpl$setVolume$2(this, i, i2, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
