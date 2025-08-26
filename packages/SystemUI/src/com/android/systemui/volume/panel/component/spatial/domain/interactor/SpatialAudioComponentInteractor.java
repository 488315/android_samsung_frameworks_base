package com.android.systemui.volume.panel.component.spatial.domain.interactor;

import android.media.AudioDeviceAttributes;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.media.data.repository.SpatializerRepository;
import com.android.settingslib.media.data.repository.SpatializerRepositoryImpl;
import com.android.settingslib.media.domain.interactor.SpatializerInteractor;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.systemui.volume.domain.interactor.AudioOutputInteractor;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioAvailabilityModel;
import com.android.systemui.volume.panel.component.spatial.domain.model.SpatialAudioEnabledModel;
import java.util.Iterator;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;

/* loaded from: classes3.dex */
public final class SpatialAudioComponentInteractor {
    public final AudioRepository audioRepository;
    public final CoroutineContext backgroundCoroutineContext;
    public final SharedFlowImpl changes;
    public final ReadonlyStateFlow currentAudioDeviceAttributes;
    public final ReadonlyStateFlow isAvailable;
    public final ReadonlyStateFlow isEnabled;
    public final SpatializerInteractor spatializerInteractor;
    public static final Companion Companion = new Companion(null);
    public static final AudioDeviceAttributes builtinSpeaker = new AudioDeviceAttributes(2, 2, "");
    public static final Set audioProfiles = ArraysKt___ArraysKt.toSet(new Integer[]{2, 22, 21});

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$setEnabled$1, reason: invalid class name */
    final class AnonymousClass1 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return SpatialAudioComponentInteractor.this.setEnabled(null, this);
        }
    }

    public SpatialAudioComponentInteractor(AudioOutputInteractor audioOutputInteractor, SpatializerInteractor spatializerInteractor, AudioRepository audioRepository, CoroutineContext coroutineContext, CoroutineScope coroutineScope) {
        this.spatializerInteractor = spatializerInteractor;
        this.audioRepository = audioRepository;
        this.backgroundCoroutineContext = coroutineContext;
        SharedFlowImpl sharedFlowImplMutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this.changes = sharedFlowImplMutableSharedFlow$default;
        final ReadonlyStateFlow readonlyStateFlow = audioOutputInteractor.currentAudioDevice;
        ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ SpatialAudioComponentInteractor this$0;

                /* renamed from: com.android.systemui.volume.panel.component.spatial.domain.interactor.SpatialAudioComponentInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, SpatialAudioComponentInteractor spatialAudioComponentInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = spatialAudioComponentInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x005a, code lost:
                
                    if (r6.emit(r8, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
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
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        Object objAccess$getAudioDeviceAttributes = SpatialAudioComponentInteractor.access$getAudioDeviceAttributes(this.this$0, (AudioOutputDevice) obj, anonymousClass1);
                        if (objAccess$getAudioDeviceAttributes != coroutineSingletons) {
                            obj2 = objAccess$getAudioDeviceAttributes;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = readonlyStateFlow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), builtinSpeaker);
        this.currentAudioDeviceAttributes = readonlyStateFlowStateIn;
        FlowKt__ZipKt$combine$$inlined$unsafeFlow$1 flowKt__ZipKt$combine$$inlined$unsafeFlow$1 = new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(readonlyStateFlowStateIn, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SpatialAudioComponentInteractor$isAvailable$1(null), sharedFlowImplMutableSharedFlow$default), new SpatialAudioComponentInteractor$isAvailable$2(this, null));
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(flowKt__ZipKt$combine$$inlined$unsafeFlow$1, coroutineScope, startedEagerly, SpatialAudioAvailabilityModel.Unavailable.INSTANCE);
        this.isAvailable = readonlyStateFlowStateIn2;
        this.isEnabled = FlowKt.stateIn(FlowKt.combine(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new SpatialAudioComponentInteractor$isEnabled$1(null), sharedFlowImplMutableSharedFlow$default), readonlyStateFlowStateIn, readonlyStateFlowStateIn2, new SpatialAudioComponentInteractor$isEnabled$2(this, null)), coroutineScope, startedEagerly, SpatialAudioEnabledModel.Unknown.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$getAudioDeviceAttributes(SpatialAudioComponentInteractor spatialAudioComponentInteractor, AudioOutputDevice audioOutputDevice, ContinuationImpl continuationImpl) throws Throwable {
        SpatialAudioComponentInteractor$getAudioDeviceAttributes$1 spatialAudioComponentInteractor$getAudioDeviceAttributes$1;
        spatialAudioComponentInteractor.getClass();
        if (continuationImpl instanceof SpatialAudioComponentInteractor$getAudioDeviceAttributes$1) {
            spatialAudioComponentInteractor$getAudioDeviceAttributes$1 = (SpatialAudioComponentInteractor$getAudioDeviceAttributes$1) continuationImpl;
            int i = spatialAudioComponentInteractor$getAudioDeviceAttributes$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                spatialAudioComponentInteractor$getAudioDeviceAttributes$1.label = i - Integer.MIN_VALUE;
            } else {
                spatialAudioComponentInteractor$getAudioDeviceAttributes$1 = new SpatialAudioComponentInteractor$getAudioDeviceAttributes$1(spatialAudioComponentInteractor, continuationImpl);
            }
        }
        Object objIsSpatialAudioAvailableForDevice = spatialAudioComponentInteractor$getAudioDeviceAttributes$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = spatialAudioComponentInteractor$getAudioDeviceAttributes$1.label;
        Object obj = null;
        if (i2 != 0) {
            if (i2 == 1) {
                ResultKt.throwOnFailure(objIsSpatialAudioAvailableForDevice);
                return objIsSpatialAudioAvailableForDevice;
            }
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            Object next = spatialAudioComponentInteractor$getAudioDeviceAttributes$1.L$2;
            Iterator it = (Iterator) spatialAudioComponentInteractor$getAudioDeviceAttributes$1.L$1;
            SpatialAudioComponentInteractor spatialAudioComponentInteractor2 = (SpatialAudioComponentInteractor) spatialAudioComponentInteractor$getAudioDeviceAttributes$1.L$0;
            ResultKt.throwOnFailure(objIsSpatialAudioAvailableForDevice);
            do {
                if (((Boolean) objIsSpatialAudioAvailableForDevice).booleanValue()) {
                    obj = next;
                } else if (it.hasNext()) {
                    next = it.next();
                    SpatializerInteractor spatializerInteractor = spatialAudioComponentInteractor2.spatializerInteractor;
                    spatialAudioComponentInteractor$getAudioDeviceAttributes$1.L$0 = spatialAudioComponentInteractor2;
                    spatialAudioComponentInteractor$getAudioDeviceAttributes$1.L$1 = it;
                    spatialAudioComponentInteractor$getAudioDeviceAttributes$1.L$2 = next;
                    spatialAudioComponentInteractor$getAudioDeviceAttributes$1.label = 2;
                    objIsSpatialAudioAvailableForDevice = ((SpatializerRepositoryImpl) spatializerInteractor.repository).isSpatialAudioAvailableForDevice((AudioDeviceAttributes) next, spatialAudioComponentInteractor$getAudioDeviceAttributes$1);
                }
                return (AudioDeviceAttributes) obj;
            } while (objIsSpatialAudioAvailableForDevice != coroutineSingletons);
        }
        ResultKt.throwOnFailure(objIsSpatialAudioAvailableForDevice);
        if (audioOutputDevice instanceof AudioOutputDevice.BuiltIn) {
            return builtinSpeaker;
        }
        if (!(audioOutputDevice instanceof AudioOutputDevice.Bluetooth)) {
            if ((audioOutputDevice instanceof AudioOutputDevice.Wired) || (audioOutputDevice instanceof AudioOutputDevice.Remote)) {
                return null;
            }
            if (audioOutputDevice instanceof AudioOutputDevice.Unknown) {
                return builtinSpeaker;
            }
            if (audioOutputDevice instanceof AudioOutputDevice.Unavailable) {
                return builtinSpeaker;
            }
            throw new NoWhenBranchMatchedException();
        }
        CachedBluetoothDevice cachedBluetoothDevice = ((AudioOutputDevice.Bluetooth) audioOutputDevice).cachedBluetoothDevice;
        spatialAudioComponentInteractor$getAudioDeviceAttributes$1.label = 1;
        Object objWithContext = BuildersKt.withContext(spatialAudioComponentInteractor.backgroundCoroutineContext, new SpatialAudioComponentInteractor$getAudioDeviceAttributesByBluetoothProfile$2(cachedBluetoothDevice, spatialAudioComponentInteractor, null), spatialAudioComponentInteractor$getAudioDeviceAttributes$1);
        if (objWithContext != coroutineSingletons) {
            return objWithContext;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:41:0x00b6, code lost:
    
        if (r7.emit(r8, r0) != r1) goto L43;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object setEnabled(SpatialAudioEnabledModel spatialAudioEnabledModel, ContinuationImpl continuationImpl) throws Throwable {
        AnonymousClass1 anonymousClass1;
        AudioDeviceAttributes audioDeviceAttributes;
        Object objRemoveSpatialAudioCompatibleDevice;
        if (continuationImpl instanceof AnonymousClass1) {
            anonymousClass1 = (AnonymousClass1) continuationImpl;
            int i = anonymousClass1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                anonymousClass1.label = i - Integer.MIN_VALUE;
            } else {
                anonymousClass1 = new AnonymousClass1(continuationImpl);
            }
        }
        Object obj = anonymousClass1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = anonymousClass1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            audioDeviceAttributes = (AudioDeviceAttributes) this.currentAudioDeviceAttributes.$$delegate_0.getValue();
            if (audioDeviceAttributes == null) {
                return Unit.INSTANCE;
            }
            boolean z = spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.SpatialAudioEnabled;
            anonymousClass1.L$0 = this;
            anonymousClass1.L$1 = spatialAudioEnabledModel;
            anonymousClass1.L$2 = audioDeviceAttributes;
            anonymousClass1.label = 1;
            SpatializerRepository spatializerRepository = this.spatializerInteractor.repository;
            if (z) {
                objRemoveSpatialAudioCompatibleDevice = ((SpatializerRepositoryImpl) spatializerRepository).addSpatialAudioCompatibleDevice(audioDeviceAttributes, anonymousClass1);
                if (objRemoveSpatialAudioCompatibleDevice != coroutineSingletons) {
                    objRemoveSpatialAudioCompatibleDevice = Unit.INSTANCE;
                }
            } else {
                objRemoveSpatialAudioCompatibleDevice = ((SpatializerRepositoryImpl) spatializerRepository).removeSpatialAudioCompatibleDevice(audioDeviceAttributes, anonymousClass1);
                if (objRemoveSpatialAudioCompatibleDevice != coroutineSingletons) {
                    objRemoveSpatialAudioCompatibleDevice = Unit.INSTANCE;
                }
            }
            if (objRemoveSpatialAudioCompatibleDevice != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            AudioDeviceAttributes audioDeviceAttributes2 = (AudioDeviceAttributes) anonymousClass1.L$2;
            spatialAudioEnabledModel = (SpatialAudioEnabledModel) anonymousClass1.L$1;
            SpatialAudioComponentInteractor spatialAudioComponentInteractor = (SpatialAudioComponentInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            audioDeviceAttributes = audioDeviceAttributes2;
            this = spatialAudioComponentInteractor;
        } else {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            this = (SpatialAudioComponentInteractor) anonymousClass1.L$0;
            ResultKt.throwOnFailure(obj);
            SharedFlowImpl sharedFlowImpl = this.changes;
            Unit unit = Unit.INSTANCE;
            anonymousClass1.L$0 = null;
            anonymousClass1.label = 3;
        }
        SpatializerInteractor spatializerInteractor = this.spatializerInteractor;
        anonymousClass1.L$0 = this;
        anonymousClass1.L$1 = null;
        anonymousClass1.L$2 = null;
        anonymousClass1.label = 2;
        Object headTrackingEnabled = ((SpatializerRepositoryImpl) spatializerInteractor.repository).setHeadTrackingEnabled(audioDeviceAttributes, spatialAudioEnabledModel instanceof SpatialAudioEnabledModel.HeadTrackingEnabled, anonymousClass1);
        if (headTrackingEnabled != coroutineSingletons) {
            headTrackingEnabled = Unit.INSTANCE;
        }
        if (headTrackingEnabled != coroutineSingletons) {
            SharedFlowImpl sharedFlowImpl2 = this.changes;
            Unit unit2 = Unit.INSTANCE;
            anonymousClass1.L$0 = null;
            anonymousClass1.label = 3;
        }
        return coroutineSingletons;
    }
}
