package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.core.os.BundleKt;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.compose.ext.ImageVectorConverterPainter;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.SmartMirroringDevice;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.icons.Icons;
import com.android.systemui.media.mediaoutput.icons.device.TvKt;
import com.google.gson.Gson;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class SmartMirroringDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final LocalBluetoothManager localBluetoothManager;
    public final Lazy smartMirroringClient$delegate;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioManager $audioManager;
        final /* synthetic */ Ref$BooleanRef $isInitialized;
        int label;
        final /* synthetic */ SmartMirroringDeviceController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(AudioManager audioManager, SmartMirroringDeviceController smartMirroringDeviceController, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
            super(2, continuation);
            this.$audioManager = audioManager;
            this.this$0 = smartMirroringDeviceController;
            this.$isInitialized = ref$BooleanRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass1(this.$audioManager, this.this$0, this.$isInitialized, continuation);
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
                Flow deviceChanged = AudioManagerExtKt.getDeviceChanged(this.$audioManager);
                final AudioManager audioManager = this.$audioManager;
                final SmartMirroringDeviceController smartMirroringDeviceController = this.this$0;
                final Ref$BooleanRef ref$BooleanRef = this.$isInitialized;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Number) obj2).longValue();
                        SmartMirroringDeviceController.access$_init_$updateScan(audioManager, smartMirroringDeviceController, ref$BooleanRef);
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (deviceChanged.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ AudioManager $audioManager;
        final /* synthetic */ Ref$BooleanRef $isInitialized;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(AudioManager audioManager, Ref$BooleanRef ref$BooleanRef, Continuation continuation) {
            super(2, continuation);
            this.$audioManager = audioManager;
            this.$isInitialized = ref$BooleanRef;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return SmartMirroringDeviceController.this.new AnonymousClass2(this.$audioManager, this.$isInitialized, continuation);
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
                SmartMirroringDeviceController smartMirroringDeviceController = SmartMirroringDeviceController.this;
                Companion companion = SmartMirroringDeviceController.Companion;
                final Flow flow = smartMirroringDeviceController.getSmartMirroringClient().connectionFlow;
                Flow flow2 = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1

                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                    public final class AnonymousClass2 implements FlowCollector {
                        public final /* synthetic */ FlowCollector $this_unsafeFlow;

                        /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                                if (((Boolean) obj).booleanValue()) {
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
                        Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                final SmartMirroringDeviceController smartMirroringDeviceController2 = SmartMirroringDeviceController.this;
                final AudioManager audioManager = this.$audioManager;
                final Ref$BooleanRef ref$BooleanRef = this.$isInitialized;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.2.2

                    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$2, reason: invalid class name and collision with other inner class name */
                    public final class C03492 implements FlowCollector {
                        public final /* synthetic */ SmartMirroringDeviceController this$0;

                        public C03492(SmartMirroringDeviceController smartMirroringDeviceController) {
                            this.this$0 = smartMirroringDeviceController;
                        }

                        /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
                        @Override // kotlinx.coroutines.flow.FlowCollector
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object emit(List list, Continuation continuation) {
                            SmartMirroringDeviceController$2$2$2$emit$1 smartMirroringDeviceController$2$2$2$emit$1;
                            int i;
                            String str;
                            Boolean booleanStrictOrNull;
                            String str2;
                            Boolean booleanStrictOrNull2;
                            if (continuation instanceof SmartMirroringDeviceController$2$2$2$emit$1) {
                                smartMirroringDeviceController$2$2$2$emit$1 = (SmartMirroringDeviceController$2$2$2$emit$1) continuation;
                                int i2 = smartMirroringDeviceController$2$2$2$emit$1.label;
                                if ((i2 & Integer.MIN_VALUE) != 0) {
                                    smartMirroringDeviceController$2$2$2$emit$1.label = i2 - Integer.MIN_VALUE;
                                } else {
                                    smartMirroringDeviceController$2$2$2$emit$1 = new SmartMirroringDeviceController$2$2$2$emit$1(this, continuation);
                                }
                            }
                            Object obj = smartMirroringDeviceController$2$2$2$emit$1.result;
                            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                            int i3 = smartMirroringDeviceController$2$2$2$emit$1.label;
                            if (i3 == 0) {
                                ResultKt.throwOnFailure(obj);
                                ArrayList arrayList = new ArrayList();
                                Iterator it = list.iterator();
                                while (true) {
                                    i = 0;
                                    zBooleanValue = false;
                                    zBooleanValue = false;
                                    boolean zBooleanValue = false;
                                    if (!it.hasNext()) {
                                        break;
                                    }
                                    Object next = it.next();
                                    Map map = ((DeviceInfo) next).details;
                                    if (map != null && (str2 = (String) map.get("KEY_IS_PREVIOUSLY_CONNECTED")) != null && (booleanStrictOrNull2 = StringsKt__StringsKt.toBooleanStrictOrNull(str2)) != null) {
                                        zBooleanValue = booleanStrictOrNull2.booleanValue();
                                    }
                                    if (zBooleanValue) {
                                        arrayList.add(next);
                                    }
                                }
                                ArrayList arrayList2 = new ArrayList();
                                int size = arrayList.size();
                                int i4 = 0;
                                while (i4 < size) {
                                    Object obj2 = arrayList.get(i4);
                                    i4++;
                                    Map map2 = ((DeviceInfo) obj2).details;
                                    if (!((map2 == null || (str = (String) map2.get("KEY_IS_NOW_CONNECTED")) == null || (booleanStrictOrNull = StringsKt__StringsKt.toBooleanStrictOrNull(str)) == null) ? false : booleanStrictOrNull.booleanValue())) {
                                        arrayList2.add(obj2);
                                    }
                                }
                                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                                int size2 = arrayList2.size();
                                while (i < size2) {
                                    Object obj3 = arrayList2.get(i);
                                    i++;
                                    DeviceInfo deviceInfo = (DeviceInfo) obj3;
                                    SmartMirroringDevice.Companion.getClass();
                                    StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(deviceInfo.name, "/");
                                    sbM.append(deviceInfo.key);
                                    String string = sbM.toString();
                                    ImageVectorConverterPainter.Companion companion = ImageVectorConverterPainter.Companion;
                                    Icons.Device device = Icons.Device.INSTANCE;
                                    ImageVector tv = TvKt.getTv();
                                    companion.getClass();
                                    SmartMirroringDevice smartMirroringDevice = new SmartMirroringDevice(string, deviceInfo.name, null, ImageVectorConverterPainter.Companion.toConverter(tv), null, 0, 0, null, false, 500, null);
                                    smartMirroringDevice.deviceInfo = deviceInfo;
                                    arrayList3.add(smartMirroringDevice);
                                }
                                SharedFlowImpl sharedFlowImpl = this.this$0.devicesFlow;
                                smartMirroringDeviceController$2$2$2$emit$1.L$0 = arrayList3;
                                smartMirroringDeviceController$2$2$2$emit$1.label = 1;
                                if (sharedFlowImpl.emit(arrayList3, smartMirroringDeviceController$2$2$2$emit$1) == coroutineSingletons) {
                                    return coroutineSingletons;
                                }
                            } else {
                                if (i3 != 1) {
                                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                                }
                                ResultKt.throwOnFailure(obj);
                            }
                            return Unit.INSTANCE;
                        }
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        ((Boolean) obj2).getClass();
                        AudioManager audioManager2 = audioManager;
                        Ref$BooleanRef ref$BooleanRef2 = ref$BooleanRef;
                        SmartMirroringDeviceController smartMirroringDeviceController3 = smartMirroringDeviceController2;
                        SmartMirroringDeviceController.access$_init_$updateScan(audioManager2, smartMirroringDeviceController3, ref$BooleanRef2);
                        SmartMirroringClient smartMirroringClient = smartMirroringDeviceController3.getSmartMirroringClient();
                        smartMirroringClient.getClass();
                        final CallbackFlowBuilder callbackFlowBuilderCallbackFlow = FlowKt.callbackFlow(new SmartMirroringClient$registerClient$1(smartMirroringClient, null));
                        Flow flow3 = new Flow() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1

                            /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1$2, reason: invalid class name */
                            public final class AnonymousClass2 implements FlowCollector {
                                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                                /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$2$2$emit$$inlined$map$1$2$1, reason: invalid class name */
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
                                        List list = (List) obj;
                                        Iterator it = list.iterator();
                                        while (it.hasNext()) {
                                            Log.d("SmartMirroringDeviceController", "\t" + ((DeviceInfo) it.next()));
                                        }
                                        anonymousClass1.label = 1;
                                        if (this.$this_unsafeFlow.emit(list, anonymousClass1) == coroutineSingletons) {
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
                            public final Object collect(FlowCollector flowCollector2, Continuation continuation2) {
                                Object objCollect = callbackFlowBuilderCallbackFlow.collect(new AnonymousClass2(flowCollector2), continuation2);
                                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                            }
                        };
                        MediaOutputConst.INSTANCE.getClass();
                        Object objCollect = FlowKt.m3482debounceHG0u8IE(flow3, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT).collect(new C03492(smartMirroringDeviceController3), continuation);
                        return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (flow2.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        final /* synthetic */ Context $context;
        int label;
        final /* synthetic */ SmartMirroringDeviceController this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass3(Context context, SmartMirroringDeviceController smartMirroringDeviceController, Continuation continuation) {
            super(2, continuation);
            this.$context = context;
            this.this$0 = smartMirroringDeviceController;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new AnonymousClass3(this.$context, this.this$0, continuation);
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
                Companion companion = SmartMirroringDeviceController.Companion;
                Context context = this.$context;
                companion.getClass();
                CallbackFlowBuilder callbackFlowBuilderCallbackFlow = FlowKt.callbackFlow(new SmartMirroringDeviceController$Companion$castDeviceStateChanges$1(context, null));
                final SmartMirroringDeviceController smartMirroringDeviceController = this.this$0;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        if (((Number) obj2).intValue() != 0) {
                            Companion companion2 = SmartMirroringDeviceController.Companion;
                            smartMirroringDeviceController.getSmartMirroringClient().stopScanMirroring();
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (callbackFlowBuilderCallbackFlow.collect(flowCollector, this) == coroutineSingletons) {
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

    public SmartMirroringDeviceController(Context context, AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        this.localBluetoothManager = localBluetoothManager;
        this.smartMirroringClient$delegate = LazyKt__LazyJVMKt.lazy(new SmartMirroringDeviceController$$ExternalSyntheticLambda0(0, context, audioManager));
        Log.d("SmartMirroringDeviceController", "init()");
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(audioManager, this, ref$BooleanRef, null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass2(audioManager, ref$BooleanRef, null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass3(context, this, null), 3);
    }

    public static final void access$_init_$updateScan(AudioManager audioManager, SmartMirroringDeviceController smartMirroringDeviceController, Ref$BooleanRef ref$BooleanRef) {
        List connectedDevices;
        Object failure;
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        AudioDeviceInfo[] devices = audioManager.getDevices(2);
        ArrayList arrayList = new ArrayList();
        for (AudioDeviceInfo audioDeviceInfo : devices) {
            if (audioDeviceInfo.getType() == 25) {
                arrayList.add(audioDeviceInfo);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (Intrinsics.areEqual(((AudioDeviceInfo) obj).getAddress(), "0")) {
                arrayList2.add(obj);
            }
        }
        LocalBluetoothManager localBluetoothManager = smartMirroringDeviceController.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) == null || (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) == null || (connectedDevices = audioCastProfile.getConnectedDevices()) == null) {
            connectedDevices = EmptyList.INSTANCE;
        }
        if (!arrayList2.isEmpty() || !connectedDevices.isEmpty()) {
            smartMirroringDeviceController.getSmartMirroringClient().stopScanMirroring();
        } else if (!ref$BooleanRef.element) {
            SmartMirroringClient smartMirroringClient = smartMirroringDeviceController.getSmartMirroringClient();
            smartMirroringClient.getClass();
            Log.d("SmartMirroringClient", "startScanMirroring()");
            Messenger messenger = smartMirroringClient.service;
            if (messenger != null) {
                AudioDeviceInfo[] devices2 = smartMirroringClient.audioManager.getDevices(2);
                int length = devices2.length;
                int i2 = 0;
                while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    if (devices2[i2].getType() == 25) {
                        messenger = null;
                        break;
                    }
                    i2++;
                }
                if (messenger != null) {
                    try {
                        int i3 = Result.$r8$clinit;
                        smartMirroringClient.isScanStarted = true;
                        messenger.send(Message.obtain(null, 3, 0, 0));
                        failure = Unit.INSTANCE;
                    } catch (Throwable th) {
                        int i4 = Result.$r8$clinit;
                        failure = new Result.Failure(th);
                    }
                    Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
                    if (thM3442exceptionOrNullimpl != null) {
                        thM3442exceptionOrNullimpl.printStackTrace();
                    }
                    Result.m3441boximpl(failure);
                }
            }
        }
        ref$BooleanRef.element = true;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit cancel(AudioDevice audioDevice) {
        Object failure;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "SmartMirroringDeviceController");
        if (audioDevice instanceof SmartMirroringDevice) {
            SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
            smartMirroringClient.getClass();
            Log.d("SmartMirroringClient", "disconnect()");
            Messenger messenger = smartMirroringClient.service;
            if (messenger != null) {
                try {
                    int i = Result.$r8$clinit;
                    messenger.send(Message.obtain(null, 6, 0, 0));
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
                if (thM3442exceptionOrNullimpl != null) {
                    thM3442exceptionOrNullimpl.printStackTrace();
                }
                Result.m3441boximpl(failure);
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("SmartMirroringDeviceController", "close()");
        SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
        smartMirroringClient.getClass();
        Log.d("SmartMirroringClient", "close()");
        smartMirroringClient.stopScanMirroring();
    }

    public final SmartMirroringClient getSmartMirroringClient() {
        return (SmartMirroringClient) this.smartMirroringClient$delegate.getValue();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        Object failure;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "SmartMirroringDeviceController");
        if (audioDevice instanceof SmartMirroringDevice) {
            SmartMirroringClient smartMirroringClient = getSmartMirroringClient();
            DeviceInfo deviceInfo = ((SmartMirroringDevice) audioDevice).deviceInfo;
            if (deviceInfo == null) {
                deviceInfo = null;
            }
            smartMirroringClient.getClass();
            Log.d("SmartMirroringClient", "connect() - " + deviceInfo);
            Messenger messenger = smartMirroringClient.service;
            if (messenger != null) {
                try {
                    int i = Result.$r8$clinit;
                    Message messageObtain = Message.obtain(null, 5, 0, 0);
                    messageObtain.setData(BundleKt.bundleOf(new Pair("deviceInfo", new Gson().toJson(deviceInfo))));
                    messenger.send(messageObtain);
                    failure = Unit.INSTANCE;
                } catch (Throwable th) {
                    int i2 = Result.$r8$clinit;
                    failure = new Result.Failure(th);
                }
                Throwable thM3442exceptionOrNullimpl = Result.m3442exceptionOrNullimpl(failure);
                if (thM3442exceptionOrNullimpl != null) {
                    thM3442exceptionOrNullimpl.printStackTrace();
                }
                Result.m3441boximpl(failure);
            }
        }
        return Unit.INSTANCE;
    }
}
