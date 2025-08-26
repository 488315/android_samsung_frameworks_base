package com.android.systemui.volume.domain.interactor;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceInfo;
import android.provider.DeviceConfig;
import android.util.Log;
import com.android.settingslib.bluetooth.BluetoothUtils;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.media.BluetoothMediaDevice;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.media.PhoneMediaDevice;
import com.android.settingslib.volume.data.repository.AudioRepository;
import com.android.settingslib.volume.data.repository.AudioRepositoryImpl;
import com.android.systemui.R;
import com.android.systemui.volume.domain.model.AudioOutputDevice;
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
import kotlinx.coroutines.flow.ReadonlyStateFlow;

/* loaded from: classes3.dex */
public final class AudioOutputInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ AudioRepository $audioRepository$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AudioOutputInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioOutputInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, AudioRepository audioRepository, AudioOutputInteractor audioOutputInteractor) {
        super(3, continuation);
        this.$audioRepository$inlined = audioRepository;
        this.this$0 = audioOutputInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AudioOutputInteractor$special$$inlined$flatMapLatest$1 audioOutputInteractor$special$$inlined$flatMapLatest$1 = new AudioOutputInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.$audioRepository$inlined, this.this$0);
        audioOutputInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        audioOutputInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return audioOutputInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            if (((Boolean) this.L$1).booleanValue()) {
                final ReadonlyStateFlow communicationDevice = ((AudioRepositoryImpl) this.$audioRepository$inlined).getCommunicationDevice();
                final AudioOutputInteractor audioOutputInteractor = this.this$0;
                flow = new Flow() { // from class: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$1

                    /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AudioOutputInteractor this$0;

                        /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, AudioOutputInteractor audioOutputInteractor) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = audioOutputInteractor;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:32:0x00a3  */
                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            Object wired;
                            LocalBluetoothManager localBluetoothManager;
                            BluetoothAdapter bluetoothAdapter;
                            Drawable drawable;
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
                                AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo) obj;
                                if (audioDeviceInfo != null) {
                                    AudioOutputInteractor audioOutputInteractor = this.this$0;
                                    audioOutputInteractor.getClass();
                                    boolean zCheckBluetoothAddress = BluetoothAdapter.checkBluetoothAddress(audioDeviceInfo.getAddress());
                                    DeviceIconInteractor deviceIconInteractor = audioOutputInteractor.deviceIconInteractor;
                                    if (!zCheckBluetoothAddress || (localBluetoothManager = audioOutputInteractor.localBluetoothManager) == null || (bluetoothAdapter = audioOutputInteractor.bluetoothAdapter) == null) {
                                        wired = audioDeviceInfo.getAddress().length() > 0 ? new AudioOutputDevice.Wired(audioDeviceInfo.getProductName().toString(), deviceIconInteractor.context.getDrawable(deviceIconInteractor.iconUtil.getIconResIdFromAudioDeviceType(audioDeviceInfo.getType()))) : new AudioOutputDevice.BuiltIn(PhoneMediaDevice.getMediaTransferThisDeviceName(audioOutputInteractor.context), deviceIconInteractor.context.getDrawable(deviceIconInteractor.iconUtil.getIconResIdFromAudioDeviceType(audioDeviceInfo.getType())));
                                    } else {
                                        CachedBluetoothDevice cachedBluetoothDeviceFindDevice = localBluetoothManager.mCachedDeviceManager.findDevice(bluetoothAdapter.getRemoteDevice(audioDeviceInfo.getAddress()));
                                        if (cachedBluetoothDeviceFindDevice != null) {
                                            String name = cachedBluetoothDeviceFindDevice.getName();
                                            deviceIconInteractor.getClass();
                                            BluetoothDevice bluetoothDevice = cachedBluetoothDeviceFindDevice.mDevice;
                                            boolean z = BluetoothUtils.DEBUG;
                                            if (DeviceConfig.getBoolean("settings_ui", "bt_advanced_header_enabled", true)) {
                                                if (BluetoothUtils.getBooleanMetaData(bluetoothDevice)) {
                                                    Log.d("BluetoothUtils", "isAdvancedDetailsHeader: untetheredHeadset is true");
                                                    drawable = deviceIconInteractor.context.getDrawable(R.drawable.ic_earbuds_advanced);
                                                }
                                                wired = new AudioOutputDevice.Bluetooth(name, drawable, cachedBluetoothDeviceFindDevice);
                                            } else {
                                                Log.d("BluetoothUtils", "isAdvancedDetailsHeader: advancedEnabled is false");
                                            }
                                            drawable = (Drawable) BluetoothUtils.getBtClassDrawableWithDescription(deviceIconInteractor.context, cachedBluetoothDeviceFindDevice).first;
                                            wired = new AudioOutputDevice.Bluetooth(name, drawable, cachedBluetoothDeviceFindDevice);
                                        }
                                    }
                                } else {
                                    wired = null;
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(wired, anonymousClass1) == coroutineSingletons) {
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
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = communicationDevice.collect(new AnonymousClass2(flowCollector2, audioOutputInteractor), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            } else {
                final AudioOutputInteractor audioOutputInteractor2 = this.this$0;
                final Flow flow2 = audioOutputInteractor2.mediaOutputInteractor.currentConnectedDevice;
                flow = new Flow() { // from class: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2

                    /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ AudioOutputInteractor this$0;

                        /* renamed from: com.android.systemui.volume.domain.interactor.AudioOutputInteractor$currentAudioDevice$lambda$2$$inlined$map$2$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, AudioOutputInteractor audioOutputInteractor) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = audioOutputInteractor;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(Object obj, Continuation continuation) {
                            AnonymousClass1 anonymousClass1;
                            Object wired;
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
                                MediaDevice mediaDevice = (MediaDevice) obj;
                                if (mediaDevice != null) {
                                    this.this$0.getClass();
                                    if (mediaDevice instanceof BluetoothMediaDevice) {
                                        BluetoothMediaDevice bluetoothMediaDevice = (BluetoothMediaDevice) mediaDevice;
                                        wired = new AudioOutputDevice.Bluetooth(bluetoothMediaDevice.mCachedDevice.getName(), bluetoothMediaDevice.getIcon(), bluetoothMediaDevice.mCachedDevice);
                                    } else {
                                        wired = (mediaDevice.getDeviceType() == 3 || mediaDevice.getDeviceType() == 2) ? new AudioOutputDevice.Wired(mediaDevice.getName(), mediaDevice.getIcon()) : mediaDevice.getDeviceType() == 6 ? new AudioOutputDevice.Remote(mediaDevice.getName(), mediaDevice.getIcon()) : new AudioOutputDevice.BuiltIn(mediaDevice.getName(), mediaDevice.getIcon());
                                    }
                                } else {
                                    wired = null;
                                }
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(wired, anonymousClass1) == coroutineSingletons) {
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
                    public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                        Object objCollect = flow2.collect(new AnonymousClass2(flowCollector2, audioOutputInteractor2), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
            }
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
