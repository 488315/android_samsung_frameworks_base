package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothA2dp;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplay;
import android.hardware.display.SemWifiDisplayStatus;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.datastore.core.DataStore;
import com.android.keyguard.ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$5;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.DexDevice;
import com.android.systemui.media.mediaoutput.entity.SmartViewDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.DisplayManagerExt;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.device.KimchiRefrigeratorKt;
import com.android.systemui.media.mediaoutput.icons.device.LaptopKt;
import com.android.systemui.media.mediaoutput.icons.device.LevelBoxKt;
import com.android.systemui.media.mediaoutput.icons.device.MobileDeviceKt;
import com.android.systemui.media.mediaoutput.icons.device.PcKt;
import com.android.systemui.media.mediaoutput.icons.device.RefrigeratorKt;
import com.android.systemui.media.mediaoutput.icons.device.SeroTvKt;
import com.android.systemui.media.mediaoutput.icons.device.SoundAccessoryKt;
import com.android.systemui.media.mediaoutput.icons.device.SoundBarKt;
import com.android.systemui.media.mediaoutput.icons.device.TabletKt;
import com.android.systemui.media.mediaoutput.icons.device.TvKt;
import com.android.systemui.volume.util.DisplayManagerWrapper;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.List;
import kotlin.Lazy;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.FilteringSequence;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda2;
import kotlin.sequences.TransformingSequence;
import kotlin.text.StringsKt__StringNumberConversionsKt;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class WifiDisplayDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final DataStore dataStore;
    public final DisplayManager displayManager;
    public final DisplayManagerWrapper displayManagerWrapper;
    public boolean isSupportDisplayDeviceVolumeControl;
    public final LocalBluetoothManager localBluetoothManager;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WifiDisplayDeviceController.this.new AnonymousClass1(continuation);
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
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = WifiDisplayDeviceController.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$5 dataStoreDebugLabsExt$special$$inlined$map$5 = new DataStoreDebugLabsExt$special$$inlined$map$5(dataStore.getData());
                final WifiDisplayDeviceController wifiDisplayDeviceController = WifiDisplayDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        WifiDisplayDeviceController wifiDisplayDeviceController2 = wifiDisplayDeviceController;
                        wifiDisplayDeviceController2.isSupportDisplayDeviceVolumeControl = zBooleanValue;
                        Object objAccess$updateDevices = WifiDisplayDeviceController.access$updateDevices(wifiDisplayDeviceController2, continuation);
                        return objAccess$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$updateDevices : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$5.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WifiDisplayDeviceController.this.new AnonymousClass2(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Companion companion = WifiDisplayDeviceController.Companion;
                WifiDisplayDeviceController wifiDisplayDeviceController = WifiDisplayDeviceController.this;
                Pair pair = new Pair(wifiDisplayDeviceController.context, wifiDisplayDeviceController.audioManager);
                companion.getClass();
                Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new WifiDisplayDeviceController$Companion$activeDeviceChanges$1(pair, null)), -1, 2);
                MediaOutputConst.INSTANCE.getClass();
                Flow flowM3481debounceHG0u8IE = FlowKt.m3481debounceHG0u8IE(flowBuffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                final WifiDisplayDeviceController wifiDisplayDeviceController2 = WifiDisplayDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object objAccess$updateDevices = WifiDisplayDeviceController.access$updateDevices(wifiDisplayDeviceController2, continuation);
                        return objAccess$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$updateDevices : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flowM3481debounceHG0u8IE.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return WifiDisplayDeviceController.this.new AnonymousClass3(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass3) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Companion companion = WifiDisplayDeviceController.Companion;
                DisplayManagerWrapper displayManagerWrapper = WifiDisplayDeviceController.this.displayManagerWrapper;
                companion.getClass();
                final Flow flowDebounce = FlowKt.debounce(FlowKt.buffer$default(FlowKt.callbackFlow(new WifiDisplayDeviceController$Companion$activeVolumeChanges$1(displayManagerWrapper, null)), -1, 2), 50L);
                final WifiDisplayDeviceController wifiDisplayDeviceController = WifiDisplayDeviceController.this;
                Flow flow = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1

                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;
                        public final /* synthetic */ WifiDisplayDeviceController this$0;

                        /* renamed from: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$3$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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

                        public AnonymousClass2(FlowCollector flowCollector, WifiDisplayDeviceController wifiDisplayDeviceController) {
                            this.$this_unsafeFlow = flowCollector;
                            this.this$0 = wifiDisplayDeviceController;
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
                                if (this.this$0.isSupportDisplayDeviceVolumeControl) {
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
                        Object objCollect = flowDebounce.collect(new AnonymousClass2(flowCollector, wifiDisplayDeviceController), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                final WifiDisplayDeviceController wifiDisplayDeviceController2 = WifiDisplayDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController.3.2
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        Object objAccess$updateDevices = WifiDisplayDeviceController.access$updateDevices(wifiDisplayDeviceController2, continuation);
                        return objAccess$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$updateDevices : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow.collect(flowCollector, this) == coroutineSingletons) {
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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public WifiDisplayDeviceController(Context context, AudioManager audioManager, DisplayManager displayManager, LocalBluetoothManager localBluetoothManager, DisplayManagerWrapper displayManagerWrapper, DataStore dataStore) {
        this.context = context;
        this.audioManager = audioManager;
        this.displayManager = displayManager;
        this.localBluetoothManager = localBluetoothManager;
        this.displayManagerWrapper = displayManagerWrapper;
        this.dataStore = dataStore;
        Log.d("WifiDisplayDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass3(null), 3);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$updateDevices(final WifiDisplayDeviceController wifiDisplayDeviceController, Continuation continuation) {
        WifiDisplayDeviceController$updateDevices$1 wifiDisplayDeviceController$updateDevices$1;
        wifiDisplayDeviceController.getClass();
        if (continuation instanceof WifiDisplayDeviceController$updateDevices$1) {
            wifiDisplayDeviceController$updateDevices$1 = (WifiDisplayDeviceController$updateDevices$1) continuation;
            int i = wifiDisplayDeviceController$updateDevices$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                wifiDisplayDeviceController$updateDevices$1.label = i - Integer.MIN_VALUE;
            } else {
                wifiDisplayDeviceController$updateDevices$1 = new WifiDisplayDeviceController$updateDevices$1(wifiDisplayDeviceController, continuation);
            }
        }
        Object obj = wifiDisplayDeviceController$updateDevices$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = wifiDisplayDeviceController$updateDevices$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            final int i3 = 0;
            FilteringSequence filteringSequenceFilter = SequencesKt___SequencesKt.filter(ArraysKt___ArraysKt.asSequence(wifiDisplayDeviceController.audioManager.getDevices(2)), new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i3) {
                        case 0:
                            WifiDisplayDeviceController.Companion companion = WifiDisplayDeviceController.Companion;
                            break;
                        case 1:
                            WifiDisplayDeviceController.Companion companion2 = WifiDisplayDeviceController.Companion;
                            break;
                        case 2:
                            AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo) obj2;
                            WifiDisplayDeviceController.Companion companion3 = WifiDisplayDeviceController.Companion;
                            AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
                            audioDeviceInfo.getClass();
                            audioDeviceInfoExt.getClass();
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("\t", AudioDeviceInfoExt.toLogText(audioDeviceInfo), "WifiDisplayDeviceController");
                            break;
                        default:
                            WifiDisplayDeviceController.Companion companion4 = WifiDisplayDeviceController.Companion;
                            AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("\t", (AudioDevice) obj2, "WifiDisplayDeviceController");
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
            final int i4 = 1;
            FilteringSequence filteringSequenceFilter2 = SequencesKt___SequencesKt.filter(filteringSequenceFilter, new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i4) {
                        case 0:
                            WifiDisplayDeviceController.Companion companion = WifiDisplayDeviceController.Companion;
                            break;
                        case 1:
                            WifiDisplayDeviceController.Companion companion2 = WifiDisplayDeviceController.Companion;
                            break;
                        case 2:
                            AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo) obj2;
                            WifiDisplayDeviceController.Companion companion3 = WifiDisplayDeviceController.Companion;
                            AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
                            audioDeviceInfo.getClass();
                            audioDeviceInfoExt.getClass();
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("\t", AudioDeviceInfoExt.toLogText(audioDeviceInfo), "WifiDisplayDeviceController");
                            break;
                        default:
                            WifiDisplayDeviceController.Companion companion4 = WifiDisplayDeviceController.Companion;
                            AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("\t", (AudioDevice) obj2, "WifiDisplayDeviceController");
                            break;
                    }
                    return Unit.INSTANCE;
                }
            });
            final int i5 = 2;
            FilteringSequence filteringSequenceMapNotNull = SequencesKt___SequencesKt.mapNotNull(new TransformingSequence(filteringSequenceFilter2, new SequencesKt___SequencesKt$$ExternalSyntheticLambda2(new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i5) {
                        case 0:
                            WifiDisplayDeviceController.Companion companion = WifiDisplayDeviceController.Companion;
                            break;
                        case 1:
                            WifiDisplayDeviceController.Companion companion2 = WifiDisplayDeviceController.Companion;
                            break;
                        case 2:
                            AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo) obj2;
                            WifiDisplayDeviceController.Companion companion3 = WifiDisplayDeviceController.Companion;
                            AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
                            audioDeviceInfo.getClass();
                            audioDeviceInfoExt.getClass();
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("\t", AudioDeviceInfoExt.toLogText(audioDeviceInfo), "WifiDisplayDeviceController");
                            break;
                        default:
                            WifiDisplayDeviceController.Companion companion4 = WifiDisplayDeviceController.Companion;
                            AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("\t", (AudioDevice) obj2, "WifiDisplayDeviceController");
                            break;
                    }
                    return Unit.INSTANCE;
                }
            })), new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda3
                /* JADX WARN: Removed duplicated region for block: B:16:0x0039  */
                /* JADX WARN: Removed duplicated region for block: B:28:0x0056  */
                /* JADX WARN: Removed duplicated region for block: B:64:0x00bc  */
                /* JADX WARN: Removed duplicated region for block: B:79:0x00e7  */
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object mo781invoke(Object obj2) {
                    int iIntValue;
                    String str;
                    Integer intOrNull;
                    String primaryDeviceType;
                    String strSubstring;
                    SemWifiDisplay semWifiDisplay;
                    SemWifiDisplay tv;
                    SemWifiDisplay smartViewDevice;
                    int connectedState;
                    SemWifiDisplay dexDevice;
                    SemWifiDisplay tv2;
                    String primaryDeviceType2;
                    List listSplit$default;
                    String str2;
                    AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo) obj2;
                    WifiDisplayDeviceController.Companion companion = WifiDisplayDeviceController.Companion;
                    DisplayManagerExt displayManagerExt = DisplayManagerExt.INSTANCE;
                    WifiDisplayDeviceController wifiDisplayDeviceController2 = this.f$0;
                    DisplayManager displayManager = wifiDisplayDeviceController2.displayManager;
                    displayManagerExt.getClass();
                    SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus = displayManager.semGetWifiDisplayStatus();
                    if (semWifiDisplayStatusSemGetWifiDisplayStatus == null) {
                        iIntValue = 0;
                    } else {
                        if (semWifiDisplayStatusSemGetWifiDisplayStatus.getActiveDisplayState() != 2) {
                            semWifiDisplayStatusSemGetWifiDisplayStatus = null;
                        }
                        if (semWifiDisplayStatusSemGetWifiDisplayStatus != null) {
                            int connectedState2 = semWifiDisplayStatusSemGetWifiDisplayStatus.getConnectedState();
                            Integer numValueOf = Integer.valueOf(connectedState2);
                            if (connectedState2 != 3 && connectedState2 != 2) {
                                numValueOf = null;
                            }
                            if (numValueOf != null) {
                                iIntValue = numValueOf.intValue();
                            }
                        }
                    }
                    if (iIntValue == 2) {
                        SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus2 = displayManager.semGetWifiDisplayStatus();
                        SemWifiDisplay activeDisplay = semWifiDisplayStatusSemGetWifiDisplayStatus2 != null ? semWifiDisplayStatusSemGetWifiDisplayStatus2.getActiveDisplay() : null;
                        if (activeDisplay != null && (deviceName = activeDisplay.getDeviceName()) != null) {
                            str = deviceName;
                        }
                    } else if (iIntValue != 3) {
                        SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus3 = displayManager.semGetWifiDisplayStatus();
                        SemWifiDisplay activeDisplay2 = semWifiDisplayStatusSemGetWifiDisplayStatus3 != null ? semWifiDisplayStatusSemGetWifiDisplayStatus3.getActiveDisplay() : null;
                        if (activeDisplay2 != null) {
                            String deviceName = activeDisplay2.getFriendlyDisplayName();
                            str = deviceName;
                        } else {
                            str = null;
                        }
                    } else {
                        str = "PC";
                    }
                    if (str == null) {
                        return null;
                    }
                    DisplayManager displayManager2 = wifiDisplayDeviceController2.displayManager;
                    SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus4 = displayManager2.semGetWifiDisplayStatus();
                    SemWifiDisplay activeDisplay3 = semWifiDisplayStatusSemGetWifiDisplayStatus4 != null ? semWifiDisplayStatusSemGetWifiDisplayStatus4.getActiveDisplay() : null;
                    if (activeDisplay3 == null || (primaryDeviceType2 = activeDisplay3.getPrimaryDeviceType()) == null) {
                        SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus5 = displayManager2.semGetWifiDisplayStatus();
                        SemWifiDisplay activeDisplay4 = semWifiDisplayStatusSemGetWifiDisplayStatus5 != null ? semWifiDisplayStatusSemGetWifiDisplayStatus5.getActiveDisplay() : null;
                        if (activeDisplay4 == null || (primaryDeviceType = activeDisplay4.getPrimaryDeviceType()) == null) {
                            intOrNull = null;
                        } else {
                            if (primaryDeviceType.length() < 4) {
                                primaryDeviceType = null;
                            }
                            if (primaryDeviceType != null && (strSubstring = primaryDeviceType.substring(0, 4)) != null) {
                                intOrNull = StringsKt__StringNumberConversionsKt.toIntOrNull(16, strSubstring);
                            }
                        }
                    } else {
                        if (primaryDeviceType2.length() <= 0) {
                            primaryDeviceType2 = null;
                        }
                        if (primaryDeviceType2 != null) {
                            if (!StringsKt__StringsKt.contains(primaryDeviceType2, "-", false)) {
                                primaryDeviceType2 = null;
                            }
                            if (primaryDeviceType2 == null || (listSplit$default = StringsKt__StringsKt.split$default(primaryDeviceType2, new String[]{"-"}, 0, 6)) == null || (str2 = (String) CollectionsKt___CollectionsKt.getOrNull(0, listSplit$default)) == null || (intOrNull = StringsKt__StringNumberConversionsKt.toIntOrNull(10, str2)) == null) {
                            }
                        }
                    }
                    if (intOrNull != null && intOrNull.intValue() == 1) {
                        Icons.Device device = Icons.Device.INSTANCE;
                        tv = (ImageVector) PcKt.Pc$delegate.getValue();
                        semWifiDisplay = null;
                    } else {
                        if (intOrNull == null) {
                            semWifiDisplay = null;
                        } else {
                            semWifiDisplay = null;
                            if (intOrNull.intValue() == 11) {
                                Icons.Device device2 = Icons.Device.INSTANCE;
                                tv = (ImageVector) LevelBoxKt.LevelBox$delegate.getValue();
                            }
                        }
                        if (intOrNull != null && intOrNull.intValue() == 12) {
                            Icons.Device device3 = Icons.Device.INSTANCE;
                            tv = (ImageVector) RefrigeratorKt.Refrigerator$delegate.getValue();
                        } else if (intOrNull != null && intOrNull.intValue() == 7) {
                            Icons.Device device4 = Icons.Device.INSTANCE;
                            tv = TvKt.getTv();
                        } else if (intOrNull != null && intOrNull.intValue() == 8) {
                            Icons.Device device5 = Icons.Device.INSTANCE;
                            tv = (ImageVector) SoundBarKt.SoundBar$delegate.getValue();
                        } else if (intOrNull != null && intOrNull.intValue() == 10) {
                            Icons.Device device6 = Icons.Device.INSTANCE;
                            tv = (ImageVector) MobileDeviceKt.MobileDevice$delegate.getValue();
                        } else {
                            tv = semWifiDisplay;
                        }
                    }
                    if (tv == null) {
                        SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus6 = displayManager2.semGetWifiDisplayStatus();
                        SemWifiDisplay activeDisplay5 = semWifiDisplayStatusSemGetWifiDisplayStatus6 != null ? semWifiDisplayStatusSemGetWifiDisplayStatus6.getActiveDisplay() : semWifiDisplay;
                        SemWifiDisplay semWifiDisplayValueOf = activeDisplay5 != null ? Integer.valueOf(activeDisplay5.getDeviceType()) : semWifiDisplay;
                        if (semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 4) {
                            Icons.Device device7 = Icons.Device.INSTANCE;
                            tv = (ImageVector) PcKt.Pc$delegate.getValue();
                        } else if (semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 22) {
                            Icons.Device device8 = Icons.Device.INSTANCE;
                            tv = (ImageVector) LevelBoxKt.LevelBox$delegate.getValue();
                        } else if (semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 9) {
                            Icons.Device device9 = Icons.Device.INSTANCE;
                            tv = (ImageVector) RefrigeratorKt.Refrigerator$delegate.getValue();
                        } else if ((semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 6) || ((semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 23) || (semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 8))) {
                            Icons.Device device10 = Icons.Device.INSTANCE;
                            tv = TvKt.getTv();
                        } else if (semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 7) {
                            Icons.Device device11 = Icons.Device.INSTANCE;
                            tv = (ImageVector) SoundBarKt.SoundBar$delegate.getValue();
                        } else if ((semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 1) || (semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 19)) {
                            Icons.Device device12 = Icons.Device.INSTANCE;
                            tv = (ImageVector) MobileDeviceKt.MobileDevice$delegate.getValue();
                        } else if (semWifiDisplayValueOf != null && semWifiDisplayValueOf.intValue() == 2) {
                            Icons.Device device13 = Icons.Device.INSTANCE;
                            tv = (ImageVector) TabletKt.Tablet$delegate.getValue();
                        } else {
                            tv = semWifiDisplay;
                        }
                    }
                    if (tv == null) {
                        if (StringsKt__StringsKt.contains(str, "The Sero", true)) {
                            Icons.Device device14 = Icons.Device.INSTANCE;
                            tv2 = (ImageVector) SeroTvKt.SeroTv$delegate.getValue();
                        } else if (StringsKt__StringsKt.contains(str, "TV", true) || StringsKt__StringsKt.contains(str, "BRAVIA", true)) {
                            Icons.Device device15 = Icons.Device.INSTANCE;
                            tv2 = TvKt.getTv();
                        } else if (StringsKt__StringsKt.contains(str, "Laptop", true)) {
                            Icons.Device device16 = Icons.Device.INSTANCE;
                            tv2 = (ImageVector) LaptopKt.Laptop$delegate.getValue();
                        } else if (StringsKt__StringsKt.contains(str, "PC", true) || StringsKt__StringsKt.contains(str, "SideSync", true)) {
                            Icons.Device device17 = Icons.Device.INSTANCE;
                            tv2 = (ImageVector) PcKt.Pc$delegate.getValue();
                        } else if (StringsKt__StringsKt.contains(str, "Tablet", true)) {
                            Icons.Device device18 = Icons.Device.INSTANCE;
                            tv2 = (ImageVector) TabletKt.Tablet$delegate.getValue();
                        } else if (StringsKt__StringsKt.contains(str, "KimchiRef", true)) {
                            Icons.Device device19 = Icons.Device.INSTANCE;
                            tv2 = (ImageVector) KimchiRefrigeratorKt.KimchiRefrigerator$delegate.getValue();
                        } else if (StringsKt__StringsKt.contains(str, "Refrigerator", true) || StringsKt__StringsKt.contains(str, "Fridge", true)) {
                            Icons.Device device20 = Icons.Device.INSTANCE;
                            tv2 = (ImageVector) RefrigeratorKt.Refrigerator$delegate.getValue();
                        } else if (StringsKt__StringsKt.contains(str, "Speaker", true)) {
                            Icons.Device device21 = Icons.Device.INSTANCE;
                            tv2 = (ImageVector) LevelBoxKt.LevelBox$delegate.getValue();
                        } else {
                            Icons.Device device22 = Icons.Device.INSTANCE;
                            tv2 = (ImageVector) SoundAccessoryKt.SoundAccessory$delegate.getValue();
                        }
                        tv = tv2;
                    }
                    boolean z = wifiDisplayDeviceController2.isSupportDisplayDeviceVolumeControl;
                    DisplayManagerWrapper displayManagerWrapper = wifiDisplayDeviceController2.displayManagerWrapper;
                    int iSemGetFineVolume = z ? displayManagerWrapper.displayCurrentVolume : wifiDisplayDeviceController2.audioManager.semGetFineVolume(3);
                    int displayMaxVolume = wifiDisplayDeviceController2.isSupportDisplayDeviceVolumeControl ? displayManagerWrapper.getDisplayMaxVolume() : 150;
                    AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
                    audioDeviceInfo.getClass();
                    AudioManager audioManager = wifiDisplayDeviceController2.audioManager;
                    audioDeviceInfoExt.getClass();
                    int type = audioDeviceInfo.getType();
                    Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
                    State state = audioManager.semGetCurrentDeviceType() == type ? State.SELECTED : State.CONNECTED;
                    SemWifiDisplay semWifiDisplaySemGetWifiDisplayStatus = wifiDisplayDeviceController2.displayManager.semGetWifiDisplayStatus();
                    if (semWifiDisplaySemGetWifiDisplayStatus != null) {
                        if (semWifiDisplaySemGetWifiDisplayStatus.getActiveDisplayState() != 2) {
                            semWifiDisplaySemGetWifiDisplayStatus = semWifiDisplay;
                        }
                        if (semWifiDisplaySemGetWifiDisplayStatus != null && ((connectedState = semWifiDisplaySemGetWifiDisplayStatus.getConnectedState()) == 3 || connectedState == 2)) {
                            DexDevice.Companion.getClass();
                            if (audioDeviceInfo.getType() == 25) {
                                String strM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo.getId(), "BuiltIn_");
                                ImageVectorConverterPainter.Companion.getClass();
                                dexDevice = new DexDevice(strM, str, null, ImageVectorConverterPainter.Companion.toConverter(tv), null, iSemGetFineVolume, displayMaxVolume, state, false, IKnoxCustomManager.Stub.TRANSACTION_setForcedDisplaySizeDensity, null);
                            } else {
                                dexDevice = semWifiDisplay;
                            }
                            if (dexDevice == null) {
                                return semWifiDisplay;
                            }
                            dexDevice.audioDeviceInfo = audioDeviceInfo;
                            return dexDevice;
                        }
                    }
                    int iSemGetAvailableDeviceMaskForQuickSoundPath = wifiDisplayDeviceController2.audioManager.semGetAvailableDeviceMaskForQuickSoundPath();
                    ConnectedDisplayKeyguardPresentation$$ExternalSyntheticOutline0.m(iSemGetAvailableDeviceMaskForQuickSoundPath, "semGetAvailableDeviceMaskForQuickSoundPath() - ", "AudioManagerExt");
                    if (iSemGetAvailableDeviceMaskForQuickSoundPath == 0) {
                        return semWifiDisplay;
                    }
                    SmartViewDevice.Companion.getClass();
                    if (audioDeviceInfo.getType() == 25) {
                        String strM2 = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(audioDeviceInfo.getId(), "BuiltIn_");
                        ImageVectorConverterPainter.Companion.getClass();
                        smartViewDevice = new SmartViewDevice(strM2, str, null, ImageVectorConverterPainter.Companion.toConverter(tv), null, iSemGetFineVolume, displayMaxVolume, state, false, false, 788, null);
                    } else {
                        smartViewDevice = semWifiDisplay;
                    }
                    if (smartViewDevice == null) {
                        return semWifiDisplay;
                    }
                    smartViewDevice.audioDeviceInfo = audioDeviceInfo;
                    return smartViewDevice;
                }
            });
            final int i6 = 3;
            List list = SequencesKt___SequencesKt.toList(new TransformingSequence(filteringSequenceMapNotNull, new SequencesKt___SequencesKt$$ExternalSyntheticLambda2(new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i6) {
                        case 0:
                            WifiDisplayDeviceController.Companion companion = WifiDisplayDeviceController.Companion;
                            break;
                        case 1:
                            WifiDisplayDeviceController.Companion companion2 = WifiDisplayDeviceController.Companion;
                            break;
                        case 2:
                            AudioDeviceInfo audioDeviceInfo = (AudioDeviceInfo) obj2;
                            WifiDisplayDeviceController.Companion companion3 = WifiDisplayDeviceController.Companion;
                            AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
                            audioDeviceInfo.getClass();
                            audioDeviceInfoExt.getClass();
                            MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("\t", AudioDeviceInfoExt.toLogText(audioDeviceInfo), "WifiDisplayDeviceController");
                            break;
                        default:
                            WifiDisplayDeviceController.Companion companion4 = WifiDisplayDeviceController.Companion;
                            AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("\t", (AudioDevice) obj2, "WifiDisplayDeviceController");
                            break;
                    }
                    return Unit.INSTANCE;
                }
            })));
            SharedFlowImpl sharedFlowImpl = wifiDisplayDeviceController.devicesFlow;
            wifiDisplayDeviceController$updateDevices$1.L$0 = list;
            wifiDisplayDeviceController$updateDevices$1.label = 1;
            if (sharedFlowImpl.emit(list, wifiDisplayDeviceController$updateDevices$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("WifiDisplayDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if ((audioDevice instanceof SmartViewDevice) || (audioDevice instanceof DexDevice)) {
            if (this.isSupportDisplayDeviceVolumeControl) {
                this.audioManager.adjustVolume(audioDevice.getVolume() < i ? 1 : -1, 0);
            } else {
                this.audioManager.semSetFineVolume(3, i, 0);
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit cancel(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "WifiDisplayDeviceController");
        if ((audioDevice instanceof SmartViewDevice) || (audioDevice instanceof DexDevice)) {
            this.displayManager.semDisconnectWifiDisplay();
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("WifiDisplayDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        AudioDeviceInfo audioDeviceInfo;
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        BluetoothA2dp bluetoothA2dp;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "WifiDisplayDeviceController");
        AudioDeviceInfo audioDeviceInfo2 = null;
        if (!(audioDevice instanceof SmartViewDevice) ? !(!(audioDevice instanceof DexDevice) || (audioDeviceInfo = ((DexDevice) audioDevice).audioDeviceInfo) == null) : (audioDeviceInfo = ((SmartViewDevice) audioDevice).audioDeviceInfo) != null) {
            audioDeviceInfo2 = audioDeviceInfo;
        }
        if (audioDeviceInfo2 != null) {
            AudioManagerExtKt.setDeviceForced(this.audioManager, audioDeviceInfo2);
            LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
            if (localBluetoothManager != null && (localBluetoothProfileManager = localBluetoothManager.mProfileManager) != null && (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                bluetoothA2dp.setDualPlayMode(false);
            }
        }
        return Unit.INSTANCE;
    }
}
