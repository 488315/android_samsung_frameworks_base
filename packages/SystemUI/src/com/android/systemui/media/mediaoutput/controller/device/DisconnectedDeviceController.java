package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothDevice;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.DisconnectedDevice;
import com.android.systemui.media.mediaoutput.ext.CachedBluetoothDeviceExtKt;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.SequencesKt___SequencesKt;
import kotlin.sequences.SequencesKt___SequencesKt$$ExternalSyntheticLambda2;
import kotlin.sequences.SequencesKt___SequencesKt$sortedWith$1;
import kotlin.sequences.TransformingSequence;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class DisconnectedDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final LocalBluetoothManager localBluetoothManager;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return DisconnectedDeviceController.this.new AnonymousClass1(continuation);
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
                LocalBluetoothManager localBluetoothManager = DisconnectedDeviceController.this.localBluetoothManager;
                if (localBluetoothManager != null) {
                    DisconnectedDeviceController.Companion.getClass();
                    Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new DisconnectedDeviceController$Companion$connectedDeviceChanges$1(localBluetoothManager, null)), -1, 2);
                    if (flowBuffer$default != null) {
                        MediaOutputConst.INSTANCE.getClass();
                        Flow flowM3482debounceHG0u8IE = FlowKt.m3482debounceHG0u8IE(flowBuffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                        if (flowM3482debounceHG0u8IE != null) {
                            final DisconnectedDeviceController disconnectedDeviceController = DisconnectedDeviceController.this;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController.1.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                                    Companion companion = DisconnectedDeviceController.Companion;
                                    Object objUpdateDevices$2 = disconnectedDeviceController.updateDevices$2((List) obj2, continuation);
                                    return objUpdateDevices$2 == CoroutineSingletons.COROUTINE_SUSPENDED ? objUpdateDevices$2 : Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (flowM3482debounceHG0u8IE.collect(flowCollector, this) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                        }
                    }
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

    public DisconnectedDeviceController(LocalBluetoothManager localBluetoothManager) {
        this.localBluetoothManager = localBluetoothManager;
        Log.d("DisconnectedDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("DisconnectedDeviceController", "close()");
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "DisconnectedDeviceController");
        if (audioDevice instanceof DisconnectedDevice) {
            CachedBluetoothDevice cachedBluetoothDevice = ((DisconnectedDevice) audioDevice).cachedBluetoothDevice;
            if (cachedBluetoothDevice == null) {
                cachedBluetoothDevice = null;
            }
            cachedBluetoothDevice.connect$1();
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0083  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00de A[LOOP:1: B:31:0x00d8->B:33:0x00de, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x011f A[LOOP:2: B:35:0x011d->B:36:0x011f, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0147 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r7v0, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r7v1, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r7v9, types: [java.util.ArrayList] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object updateDevices$2(List list, Continuation continuation) throws Throwable {
        DisconnectedDeviceController$updateDevices$1 disconnectedDeviceController$updateDevices$1;
        final ?? arrayList;
        ArrayList arrayList2;
        int size;
        SharedFlowImpl sharedFlowImpl;
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        if (continuation instanceof DisconnectedDeviceController$updateDevices$1) {
            disconnectedDeviceController$updateDevices$1 = (DisconnectedDeviceController$updateDevices$1) continuation;
            int i = disconnectedDeviceController$updateDevices$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                disconnectedDeviceController$updateDevices$1.label = i - Integer.MIN_VALUE;
            } else {
                disconnectedDeviceController$updateDevices$1 = new DisconnectedDeviceController$updateDevices$1(this, continuation);
            }
        }
        Object obj = disconnectedDeviceController$updateDevices$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = disconnectedDeviceController$updateDevices$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            int i3 = 0;
            LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
            if (localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) == null) {
                arrayList = EmptyList.INSTANCE;
                final int i4 = 0;
                final int i5 = 1;
                SequencesKt___SequencesKt$sortedWith$1 sequencesKt___SequencesKt$sortedWith$1 = new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filterNot(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj2;
                        switch (i4) {
                            case 0:
                                DisconnectedDeviceController.Companion companion = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(CachedBluetoothDeviceExtKt.isConnectedWithMembers(cachedBluetoothDevice));
                            case 1:
                                DisconnectedDeviceController.Companion companion2 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(cachedBluetoothDevice.mBondState == 12);
                            default:
                                DisconnectedDeviceController.Companion companion3 = DisconnectedDeviceController.Companion;
                                Log.d("DisconnectedDeviceController", "\t" + cachedBluetoothDevice);
                                return Unit.INSTANCE;
                        }
                    }
                }), new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        List list2 = arrayList;
                        DisconnectedDeviceController.Companion companion = DisconnectedDeviceController.Companion;
                        return Boolean.valueOf(list2.contains(((CachedBluetoothDevice) obj2).mDevice.getAddress()));
                    }
                }), new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj2;
                        switch (i5) {
                            case 0:
                                DisconnectedDeviceController.Companion companion = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(CachedBluetoothDeviceExtKt.isConnectedWithMembers(cachedBluetoothDevice));
                            case 1:
                                DisconnectedDeviceController.Companion companion2 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(cachedBluetoothDevice.mBondState == 12);
                            default:
                                DisconnectedDeviceController.Companion companion3 = DisconnectedDeviceController.Companion;
                                Log.d("DisconnectedDeviceController", "\t" + cachedBluetoothDevice);
                                return Unit.INSTANCE;
                        }
                    }
                }), new Comparator() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$updateDevices$$inlined$sortedByDescending$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj2, Object obj3) {
                        return ComparisonsKt__ComparisonsKt.compareValues(((CachedBluetoothDevice) obj3).mBondTimestamp, ((CachedBluetoothDevice) obj2).mBondTimestamp);
                    }
                });
                final int i6 = 2;
                List<CachedBluetoothDevice> list2 = SequencesKt___SequencesKt.toList(new TransformingSequence(sequencesKt___SequencesKt$sortedWith$1, new SequencesKt___SequencesKt$$ExternalSyntheticLambda2(new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj2;
                        switch (i6) {
                            case 0:
                                DisconnectedDeviceController.Companion companion = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(CachedBluetoothDeviceExtKt.isConnectedWithMembers(cachedBluetoothDevice));
                            case 1:
                                DisconnectedDeviceController.Companion companion2 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(cachedBluetoothDevice.mBondState == 12);
                            default:
                                DisconnectedDeviceController.Companion companion3 = DisconnectedDeviceController.Companion;
                                Log.d("DisconnectedDeviceController", "\t" + cachedBluetoothDevice);
                                return Unit.INSTANCE;
                        }
                    }
                })));
                arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
                for (CachedBluetoothDevice cachedBluetoothDevice : list2) {
                    DisconnectedDevice.Companion.getClass();
                    String address = cachedBluetoothDevice.mDevice.getAddress();
                    String name = cachedBluetoothDevice.getName();
                    String connectionSummary = cachedBluetoothDevice.getConnectionSummary();
                    TintDrawablePainter.Companion companion = TintDrawablePainter.Companion;
                    Drawable iconDrawable = cachedBluetoothDevice.getIconDrawable(false);
                    companion.getClass();
                    DisconnectedDevice disconnectedDevice = new DisconnectedDevice(address, name, connectionSummary, TintDrawablePainter.Companion.toConverter(iconDrawable), null, 0, 0, null, IKnoxCustomManager.Stub.TRANSACTION_getFavoriteApp, null);
                    disconnectedDevice.cachedBluetoothDevice = cachedBluetoothDevice;
                    arrayList2.add(disconnectedDevice);
                }
                size = arrayList2.size();
                while (i3 < size) {
                    Object obj2 = arrayList2.get(i3);
                    i3++;
                    Log.d("DisconnectedDeviceController", "\t" + ((DisconnectedDevice) obj2));
                }
                sharedFlowImpl = this.devicesFlow;
                disconnectedDeviceController$updateDevices$1.L$0 = arrayList2;
                disconnectedDeviceController$updateDevices$1.label = 1;
                if (sharedFlowImpl.emit(arrayList2, disconnectedDeviceController$updateDevices$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                int[] iArr = {0, 2, 1, 3};
                BluetoothA2dp bluetoothA2dp = a2dpProfile.mService;
                List<BluetoothDevice> arrayList3 = bluetoothA2dp == null ? new ArrayList<>(0) : bluetoothA2dp.getDevicesMatchingConnectionStates(iArr);
                if (arrayList3 != null) {
                    List<BluetoothDevice> list3 = arrayList3;
                    arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list3, 10));
                    Iterator it = list3.iterator();
                    while (it.hasNext()) {
                        arrayList.add(((BluetoothDevice) it.next()).getAddress());
                    }
                }
                final int i42 = 0;
                final int i52 = 1;
                SequencesKt___SequencesKt$sortedWith$1 sequencesKt___SequencesKt$sortedWith$12 = new SequencesKt___SequencesKt$sortedWith$1(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filter(SequencesKt___SequencesKt.filterNot(new CollectionsKt___CollectionsKt$asSequence$$inlined$Sequence$1(list), new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj22) {
                        CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj22;
                        switch (i42) {
                            case 0:
                                DisconnectedDeviceController.Companion companion2 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(CachedBluetoothDeviceExtKt.isConnectedWithMembers(cachedBluetoothDevice2));
                            case 1:
                                DisconnectedDeviceController.Companion companion22 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(cachedBluetoothDevice2.mBondState == 12);
                            default:
                                DisconnectedDeviceController.Companion companion3 = DisconnectedDeviceController.Companion;
                                Log.d("DisconnectedDeviceController", "\t" + cachedBluetoothDevice2);
                                return Unit.INSTANCE;
                        }
                    }
                }), new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$$ExternalSyntheticLambda1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj22) {
                        List list22 = arrayList;
                        DisconnectedDeviceController.Companion companion2 = DisconnectedDeviceController.Companion;
                        return Boolean.valueOf(list22.contains(((CachedBluetoothDevice) obj22).mDevice.getAddress()));
                    }
                }), new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj22) {
                        CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj22;
                        switch (i52) {
                            case 0:
                                DisconnectedDeviceController.Companion companion2 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(CachedBluetoothDeviceExtKt.isConnectedWithMembers(cachedBluetoothDevice2));
                            case 1:
                                DisconnectedDeviceController.Companion companion22 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(cachedBluetoothDevice2.mBondState == 12);
                            default:
                                DisconnectedDeviceController.Companion companion3 = DisconnectedDeviceController.Companion;
                                Log.d("DisconnectedDeviceController", "\t" + cachedBluetoothDevice2);
                                return Unit.INSTANCE;
                        }
                    }
                }), new Comparator() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$updateDevices$$inlined$sortedByDescending$1
                    @Override // java.util.Comparator
                    public final int compare(Object obj22, Object obj3) {
                        return ComparisonsKt__ComparisonsKt.compareValues(((CachedBluetoothDevice) obj3).mBondTimestamp, ((CachedBluetoothDevice) obj22).mBondTimestamp);
                    }
                });
                final int i62 = 2;
                List<CachedBluetoothDevice> list22 = SequencesKt___SequencesKt.toList(new TransformingSequence(sequencesKt___SequencesKt$sortedWith$12, new SequencesKt___SequencesKt$$ExternalSyntheticLambda2(new Function1() { // from class: com.android.systemui.media.mediaoutput.controller.device.DisconnectedDeviceController$$ExternalSyntheticLambda0
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj22) {
                        CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj22;
                        switch (i62) {
                            case 0:
                                DisconnectedDeviceController.Companion companion2 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(CachedBluetoothDeviceExtKt.isConnectedWithMembers(cachedBluetoothDevice2));
                            case 1:
                                DisconnectedDeviceController.Companion companion22 = DisconnectedDeviceController.Companion;
                                return Boolean.valueOf(cachedBluetoothDevice2.mBondState == 12);
                            default:
                                DisconnectedDeviceController.Companion companion3 = DisconnectedDeviceController.Companion;
                                Log.d("DisconnectedDeviceController", "\t" + cachedBluetoothDevice2);
                                return Unit.INSTANCE;
                        }
                    }
                })));
                arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list22, 10));
                while (r1.hasNext()) {
                }
                size = arrayList2.size();
                while (i3 < size) {
                }
                sharedFlowImpl = this.devicesFlow;
                disconnectedDeviceController$updateDevices$1.L$0 = arrayList2;
                disconnectedDeviceController$updateDevices$1.label = 1;
                if (sharedFlowImpl.emit(arrayList2, disconnectedDeviceController$updateDevices$1) == coroutineSingletons) {
                }
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
