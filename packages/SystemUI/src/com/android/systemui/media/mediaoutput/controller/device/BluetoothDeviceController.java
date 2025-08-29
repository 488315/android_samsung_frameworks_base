package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothA2dp;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ContentResolver;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.settingslib.bluetooth.A2dpProfile;
import com.android.settingslib.bluetooth.CachedBluetoothDevice;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothProfileManager;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DeviceUtils;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioDeviceExt;
import com.android.systemui.media.mediaoutput.entity.BluetoothDevice;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.android.systemui.media.mediaoutput.ext.CachedBluetoothDeviceExtKt;
import com.android.systemui.media.mediaoutput.ext.MultiSequenceString;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptySet;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class BluetoothDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Lazy bluetoothAdapter$delegate;
    public final ContentResolver cr;
    public final boolean isDualAudioSupported;
    public final LocalBluetoothManager localBluetoothManager;
    public boolean musicShareEventLogged;
    public StandaloneCoroutine updateJob;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return BluetoothDeviceController.this.new AnonymousClass1(continuation);
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
                LocalBluetoothManager localBluetoothManager = BluetoothDeviceController.this.localBluetoothManager;
                if (localBluetoothManager != null) {
                    BluetoothDeviceController.Companion.getClass();
                    Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new BluetoothDeviceController$Companion$connectedDeviceChanges$1(localBluetoothManager, null)), -1, 2);
                    if (flowBuffer$default != null) {
                        MediaOutputConst.INSTANCE.getClass();
                        Flow flowM3481debounceHG0u8IE = FlowKt.m3481debounceHG0u8IE(flowBuffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                        if (flowM3481debounceHG0u8IE != null) {
                            final BluetoothDeviceController bluetoothDeviceController = BluetoothDeviceController.this;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController.1.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) throws Throwable {
                                    Companion companion = BluetoothDeviceController.Companion;
                                    Object objUpdateDevices = bluetoothDeviceController.updateDevices((List) obj2, false, continuation);
                                    return objUpdateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? objUpdateDevices : Unit.INSTANCE;
                                }
                            };
                            this.label = 1;
                            if (flowM3481debounceHG0u8IE.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$deselect$1, reason: invalid class name and case insensitive filesystem */
    final class C09381 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        int label;
        /* synthetic */ Object result;

        public C09381(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return BluetoothDeviceController.this.deselect(null, this);
        }
    }

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$updateDevices$1, reason: invalid class name and case insensitive filesystem */
    final class C09391 extends ContinuationImpl {
        Object L$0;
        Object L$1;
        Object L$2;
        int label;
        /* synthetic */ Object result;

        public C09391(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            BluetoothDeviceController bluetoothDeviceController = BluetoothDeviceController.this;
            Companion companion = BluetoothDeviceController.Companion;
            return bluetoothDeviceController.updateDevices(null, false, this);
        }
    }

    public BluetoothDeviceController(ContentResolver contentResolver, AudioManager audioManager, LocalBluetoothManager localBluetoothManager) {
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        BluetoothAdapter bluetoothAdapter;
        this.cr = contentResolver;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        boolean zSemIsDualPlaySupported = false;
        if (localBluetoothManager != null && (localBluetoothProfileManager = localBluetoothManager.mProfileManager) != null && (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) != null && (bluetoothAdapter = a2dpProfile.mBluetoothAdapter) != null) {
            zSemIsDualPlaySupported = bluetoothAdapter.semIsDualPlaySupported();
        }
        this.isDualAudioSupported = zSemIsDualPlaySupported;
        this.bluetoothAdapter$delegate = LazyKt__LazyJVMKt.lazy(new BluetoothDeviceController$$ExternalSyntheticLambda0());
        Log.d("BluetoothDeviceController", "init() - isDualAudioSupported = " + zSemIsDualPlaySupported);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
        CachedBluetoothDevice cachedBluetoothDevice;
        Log.d("BluetoothDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        BluetoothDevice bluetoothDevice = null;
        bluetoothDevice = null;
        bluetoothDevice = null;
        if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice2 = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice;
            CachedBluetoothDevice cachedBluetoothDevice2 = bluetoothDevice2.cachedBluetoothDevice;
            if (cachedBluetoothDevice2 == null) {
                cachedBluetoothDevice2 = null;
            }
            if (cachedBluetoothDevice2.isConnectedA2dpDevice()) {
                CachedBluetoothDevice cachedBluetoothDevice3 = bluetoothDevice2.cachedBluetoothDevice;
                bluetoothDevice = (cachedBluetoothDevice3 != null ? cachedBluetoothDevice3 : null).mDevice;
            } else {
                this.audioManager.semSetFineVolume(3, i, 0);
            }
        } else if ((audioDevice instanceof MusicShareDevice) && (cachedBluetoothDevice = ((MusicShareDevice) audioDevice).cachedBluetoothDevice) != null) {
            bluetoothDevice = cachedBluetoothDevice.mDevice;
        }
        if (bluetoothDevice != null) {
            this.audioManager.semSetFineVolume(bluetoothDevice, 3, i, 0);
            DeviceUtils deviceUtils = DeviceUtils.INSTANCE;
            ContentResolver contentResolver = this.cr;
            boolean needEarProtect = audioDevice.getNeedEarProtect();
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BluetoothDeviceController$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    BluetoothDeviceController bluetoothDeviceController = this.f$0;
                    LocalBluetoothManager localBluetoothManager = bluetoothDeviceController.localBluetoothManager;
                    if (localBluetoothManager != null) {
                        StandaloneCoroutine standaloneCoroutine = bluetoothDeviceController.updateJob;
                        if (standaloneCoroutine != null) {
                            standaloneCoroutine.cancel(null);
                        }
                        bluetoothDeviceController.updateJob = BuildersKt.launch$default(bluetoothDeviceController.getControllerScope(), null, null, new BluetoothDeviceController$adjustVolume$2$1$1$1(bluetoothDeviceController, localBluetoothManager, null), 3);
                    }
                    return Unit.INSTANCE;
                }
            };
            deviceUtils.getClass();
            DeviceUtils.checkVolumeLimiter(contentResolver, needEarProtect, i, function0);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit cancel(AudioDevice audioDevice) {
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        if (audioDevice instanceof MusicShareDevice) {
            LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
            if (localBluetoothManager != null && (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) != null && (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) != null) {
                List connectedDevices = audioCastProfile.getConnectedDevices();
                ArrayList arrayList = new ArrayList();
                for (Object obj : connectedDevices) {
                    if (((SemBluetoothCastDevice) obj).getConnectionState() == 2) {
                        arrayList.add(obj);
                    }
                }
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj2 = arrayList.get(i);
                    i++;
                    SemBluetoothCastDevice semBluetoothCastDevice = (SemBluetoothCastDevice) obj2;
                    Log.d(audioCastProfile.TAG, "disconnectGuest");
                    SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                    if (semBluetoothAudioCast != null) {
                        semBluetoothAudioCast.disconnectGuest(semBluetoothCastDevice);
                    }
                }
            }
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.EndMusicShareHost.INSTANCE);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        super.close();
        Log.d("BluetoothDeviceController", "close()");
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object deselect(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        C09381 c09381;
        Object next;
        BluetoothA2dp bluetoothA2dp;
        if (continuationImpl instanceof C09381) {
            c09381 = (C09381) continuationImpl;
            int i = c09381.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                c09381.label = i - Integer.MIN_VALUE;
            } else {
                c09381 = new C09381(continuationImpl);
            }
        }
        Object obj = c09381.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = c09381.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj);
            Log.d("BluetoothDeviceController", "deselect() - " + audioDevice);
            if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
                com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice;
                List list = bluetoothDevice.activeDevices;
                if (list != null) {
                    Iterator it = list.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            next = null;
                            break;
                        }
                        next = it.next();
                        if (!Intrinsics.areEqual(((com.android.systemui.media.mediaoutput.entity.BluetoothDevice) next).id, bluetoothDevice.id)) {
                            break;
                        }
                    }
                    com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice2 = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) next;
                    if (bluetoothDevice2 != null) {
                        Log.i("BluetoothDeviceController", "deselect(" + audioDevice + ") - " + bluetoothDevice2);
                        c09381.L$0 = this;
                        c09381.L$1 = bluetoothDevice2;
                        c09381.label = 1;
                        if (transfer(bluetoothDevice2, c09381) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                }
            }
            return Unit.INSTANCE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        this = (BluetoothDeviceController) c09381.L$0;
        ResultKt.throwOnFailure(obj);
        LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
        if (localBluetoothManager != null) {
            A2dpProfile a2dpProfile = localBluetoothManager.mProfileManager.mA2dpProfile;
            if (a2dpProfile != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                bluetoothA2dp.setDualPlayMode(false);
            }
            BuildersKt.launch$default(this.getControllerScope(), null, null, new BluetoothDeviceController$deselect$4$1(this, localBluetoothManager, null), 3);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit select(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        LocalBluetoothManager localBluetoothManager;
        BluetoothA2dp bluetoothA2dp;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "BluetoothDeviceController");
        if ((audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) && (localBluetoothManager = this.localBluetoothManager) != null) {
            A2dpProfile a2dpProfile = localBluetoothManager.mProfileManager.mA2dpProfile;
            if (a2dpProfile != null && (bluetoothA2dp = a2dpProfile.mService) != null) {
                bluetoothA2dp.setDualPlayMode(true);
            }
            BuildersKt.launch$default(getControllerScope(), null, null, new BluetoothDeviceController$select$2$1(this, localBluetoothManager, null), 3);
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005e  */
    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        Object obj;
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "BluetoothDeviceController");
        Object obj2 = null;
        if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
            com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice;
            AudioDeviceInfo audioDeviceInfo = bluetoothDevice.audioDeviceInfo;
            if (audioDeviceInfo != null) {
                AudioManager audioManager = this.audioManager;
                Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
                AudioDeviceInfo[] devices = audioManager.getDevices(2);
                ArrayList arrayList = new ArrayList();
                for (AudioDeviceInfo audioDeviceInfo2 : devices) {
                    if (audioDeviceInfo2.semGetInternalType() == audioDeviceInfo.semGetInternalType()) {
                        arrayList.add(audioDeviceInfo2);
                    }
                }
                int size = arrayList.size();
                int i = 0;
                while (true) {
                    if (i >= size) {
                        obj = null;
                        break;
                    }
                    obj = arrayList.get(i);
                    i++;
                    if (Intrinsics.areEqual(((AudioDeviceInfo) obj).semGetAddress(), audioDeviceInfo.semGetAddress())) {
                        break;
                    }
                }
                Object obj3 = (AudioDeviceInfo) obj;
                if (obj3 != null) {
                    obj2 = obj3;
                } else {
                    Object obj4 = bluetoothDevice.cachedBluetoothDevice;
                    if (obj4 != null) {
                        obj2 = obj4;
                    }
                }
            }
        } else if (audioDevice instanceof MusicShareDevice) {
            obj2 = ((MusicShareDevice) audioDevice).audioDeviceInfo;
        }
        if (obj2 != null) {
            if (obj2 instanceof AudioDeviceInfo) {
                AudioManagerExtKt.setDeviceForced(this.audioManager, (AudioDeviceInfo) obj2);
            } else if (obj2 instanceof CachedBluetoothDevice) {
                ((BluetoothAdapter) this.bluetoothAdapter$delegate.getValue()).setActiveDevice(((CachedBluetoothDevice) obj2).mDevice, 0);
            }
        }
        return Unit.INSTANCE;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0125  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0141 A[LOOP:7: B:56:0x013b->B:58:0x0141, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x01aa  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x020c  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0018  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x020f  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x023c  */
    /* JADX WARN: Type inference failed for: r0v5, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r11v25, types: [com.android.systemui.media.mediaoutput.entity.BluetoothDevice] */
    /* JADX WARN: Type inference failed for: r11v27, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r11v34, types: [com.android.systemui.media.mediaoutput.entity.MusicShareDevice] */
    /* JADX WARN: Type inference failed for: r5v27, types: [com.android.systemui.media.mediaoutput.entity.BluetoothDevice] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object updateDevices(List list, boolean z, Continuation continuation) throws Throwable {
        C09391 c09391;
        Set set;
        int size;
        int i;
        int i2;
        int size2;
        int i3;
        ArrayList arrayList;
        int size3;
        int i4;
        ArrayList arrayList2;
        boolean z2;
        MusicShareDevice musicShareDevice;
        int iSemGetFineVolume;
        ?? bluetoothDevice;
        LocalBluetoothProfileManager localBluetoothProfileManager;
        A2dpProfile a2dpProfile;
        BluetoothA2dp bluetoothA2dp;
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        List connectedDevices;
        BluetoothDeviceController bluetoothDeviceController = this;
        int i5 = 1;
        if (continuation instanceof C09391) {
            c09391 = (C09391) continuation;
            int i6 = c09391.label;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                c09391.label = i6 - Integer.MIN_VALUE;
            } else {
                c09391 = bluetoothDeviceController.new C09391(continuation);
            }
        }
        Object obj = c09391.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i7 = c09391.label;
        if (i7 == 0) {
            ResultKt.throwOnFailure(obj);
            int i8 = 2;
            AudioDeviceInfo[] devices = bluetoothDeviceController.audioManager.getDevices(2);
            ArrayList arrayList3 = new ArrayList();
            for (AudioDeviceInfo audioDeviceInfo : devices) {
                AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
                audioDeviceInfo.getClass();
                boolean zIsWiredHeadsetOn = bluetoothDeviceController.audioManager.isWiredHeadsetOn();
                audioDeviceInfoExt.getClass();
                if (AudioDeviceInfoExt.isValidDeviceTypeForMedia(audioDeviceInfo, zIsWiredHeadsetOn)) {
                    arrayList3.add(audioDeviceInfo);
                }
            }
            ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList3, 10));
            int size4 = arrayList3.size();
            int i9 = 0;
            while (i9 < size4) {
                Object obj2 = arrayList3.get(i9);
                i9++;
                arrayList4.add(((AudioDeviceInfo) obj2).getAddress());
            }
            ArrayList arrayList5 = new ArrayList();
            int size5 = arrayList4.size();
            int i10 = 0;
            while (i10 < size5) {
                Object obj3 = arrayList4.get(i10);
                i10++;
                String str = (String) obj3;
                str.getClass();
                if (!StringsKt__StringsKt.isBlank(str)) {
                    arrayList5.add(obj3);
                }
            }
            Set set2 = CollectionsKt___CollectionsKt.toSet(arrayList5);
            LocalBluetoothManager localBluetoothManager = bluetoothDeviceController.localBluetoothManager;
            if (localBluetoothManager == null || (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) == null || (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) == null || (connectedDevices = audioCastProfile.getConnectedDevices()) == null) {
                set = EmptySet.INSTANCE;
                ArrayList arrayList6 = new ArrayList();
                for (Object obj4 : list) {
                    if (CachedBluetoothDeviceExtKt.isConnectedWithMembers((CachedBluetoothDevice) obj4)) {
                        arrayList6.add(obj4);
                    }
                }
                size = arrayList6.size();
                i = 0;
                while (i < size) {
                    Object obj5 = arrayList6.get(i);
                    i += i5;
                    final CachedBluetoothDevice cachedBluetoothDevice = (CachedBluetoothDevice) obj5;
                    int i11 = i8;
                    Log.d("BluetoothDeviceController", "\t" + cachedBluetoothDevice);
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("\t", CollectionsKt___CollectionsKt.joinToString$default(Arrays.asList(Integer.valueOf(i5), Integer.valueOf(i11), 21, 22), null, null, null, new Function1() { // from class: com.android.systemui.media.mediaoutput.ext.CachedBluetoothDeviceExtKt$$ExternalSyntheticLambda0
                        @Override // kotlin.jvm.functions.Function1
                        /* renamed from: invoke */
                        public final Object mo781invoke(Object obj6) {
                            int iIntValue = ((Integer) obj6).intValue();
                            return iIntValue + " = " + cachedBluetoothDevice.isActiveDevice(iIntValue);
                        }
                    }, 31), "BluetoothDeviceController");
                    i8 = i11;
                    i5 = i5;
                }
                i2 = i5;
                int i12 = i8;
                ArrayList arrayList7 = new ArrayList();
                size2 = arrayList6.size();
                i3 = 0;
                while (i3 < size2) {
                    Object obj6 = arrayList6.get(i3);
                    i3++;
                    CachedBluetoothDevice cachedBluetoothDevice2 = (CachedBluetoothDevice) obj6;
                    if (!CollectionsKt___CollectionsKt.intersect(CachedBluetoothDeviceExtKt.getAllAddresses(cachedBluetoothDevice2), set2).isEmpty() || cachedBluetoothDevice2.isConnectedA2dpDevice() || cachedBluetoothDevice2.isConnectedLeAudioDevice() || cachedBluetoothDevice2.isConnectedHearingAidDevice()) {
                        arrayList7.add(obj6);
                    }
                }
                ArrayList arrayList8 = new ArrayList(arrayList7);
                Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
                arrayList = new ArrayList();
                size3 = arrayList8.size();
                i4 = 0;
                while (i4 < size3) {
                    Object obj7 = arrayList8.get(i4);
                    i4++;
                    if (((CachedBluetoothDevice) obj7).isConnectedA2dpDevice()) {
                        arrayList.add(obj7);
                    }
                }
                if (arrayList.size() <= i2) {
                    arrayList = null;
                }
                if (arrayList == null) {
                    AudioManager audioManager = bluetoothDeviceController.audioManager;
                    Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
                    if (audioManager.semGetCurrentDeviceType() != 8) {
                        arrayList = null;
                    }
                    if (arrayList != null) {
                        ref$BooleanRef.element = bluetoothDeviceController.isDualAudioSupported;
                        Companion.getClass();
                        boolean zSemIsDualPlayMode = (localBluetoothManager == null || (localBluetoothProfileManager = localBluetoothManager.mProfileManager) == null || (a2dpProfile = localBluetoothProfileManager.mA2dpProfile) == null || (bluetoothA2dp = a2dpProfile.mService) == null) ? false : bluetoothA2dp.semIsDualPlayMode();
                        ArrayList arrayList9 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList8, 10));
                        int size6 = arrayList8.size();
                        int i13 = 0;
                        while (i13 < size6) {
                            Object obj8 = arrayList8.get(i13);
                            i13++;
                            CachedBluetoothDevice cachedBluetoothDevice3 = (CachedBluetoothDevice) obj8;
                            if ((set.contains(cachedBluetoothDevice3.mDevice.getAddress()) ? cachedBluetoothDevice3 : null) != null) {
                                MusicShareDevice.Companion companion = MusicShareDevice.Companion;
                                AudioManager audioManager2 = bluetoothDeviceController.audioManager;
                                Lazy lazy2 = AudioManagerExtKt.mediaStrategy$delegate;
                                int iSemGetCurrentDeviceType = audioManager2.semGetCurrentDeviceType();
                                companion.getClass();
                                String address = cachedBluetoothDevice3.mDevice.getAddress();
                                String name = cachedBluetoothDevice3.getName();
                                String connectionSummary = cachedBluetoothDevice3.getConnectionSummary();
                                TintDrawablePainter.Companion companion2 = TintDrawablePainter.Companion;
                                Drawable iconDrawable = cachedBluetoothDevice3.getIconDrawable(false);
                                companion2.getClass();
                                bluetoothDevice = new MusicShareDevice(address, name, connectionSummary, TintDrawablePainter.Companion.toConverter(iconDrawable), null, 0, 0, CachedBluetoothDeviceExtKt.isActiveDeviceWithMembers(cachedBluetoothDevice3, iSemGetCurrentDeviceType) ? State.SELECTED : State.CONNECTED, false, false, false, 1904, null);
                                bluetoothDevice.controllerType = ControllerType.Bluetooth;
                                bluetoothDevice.cachedBluetoothDevice = cachedBluetoothDevice3;
                            } else {
                                BluetoothDevice.Companion companion3 = com.android.systemui.media.mediaoutput.entity.BluetoothDevice.Companion;
                                AudioManager audioManager3 = bluetoothDeviceController.audioManager;
                                Lazy lazy3 = AudioManagerExtKt.mediaStrategy$delegate;
                                int iSemGetCurrentDeviceType2 = audioManager3.semGetCurrentDeviceType();
                                companion3.getClass();
                                String address2 = cachedBluetoothDevice3.mDevice.getAddress();
                                String name2 = cachedBluetoothDevice3.getName();
                                TintDrawablePainter.Companion companion4 = TintDrawablePainter.Companion;
                                Drawable iconDrawable2 = cachedBluetoothDevice3.getIconDrawable(false);
                                companion4.getClass();
                                bluetoothDevice = new com.android.systemui.media.mediaoutput.entity.BluetoothDevice(address2, name2, null, TintDrawablePainter.Companion.toConverter(iconDrawable2), null, 0, 0, CachedBluetoothDeviceExtKt.isActiveDeviceWithMembers(cachedBluetoothDevice3, iSemGetCurrentDeviceType2) ? State.SELECTED : State.CONNECTED, false, false, z, 884, null);
                                bluetoothDevice.cachedBluetoothDevice = cachedBluetoothDevice3;
                            }
                            arrayList9.add(bluetoothDevice);
                        }
                        arrayList2 = new ArrayList();
                        int size7 = arrayList9.size();
                        int i14 = 0;
                        while (i14 < size7) {
                            Object obj9 = arrayList9.get(i14);
                            i14++;
                            AudioDevice audioDevice = (AudioDevice) obj9;
                            if (audioDevice instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
                                com.android.systemui.media.mediaoutput.entity.BluetoothDevice bluetoothDevice2 = (com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice;
                                CachedBluetoothDevice cachedBluetoothDevice4 = bluetoothDevice2.cachedBluetoothDevice;
                                if (cachedBluetoothDevice4 == null) {
                                    cachedBluetoothDevice4 = null;
                                }
                                boolean zIsConnectedA2dpDevice = cachedBluetoothDevice4.isConnectedA2dpDevice();
                                if (zIsConnectedA2dpDevice) {
                                    AudioManager audioManager4 = bluetoothDeviceController.audioManager;
                                    CachedBluetoothDevice cachedBluetoothDevice5 = bluetoothDevice2.cachedBluetoothDevice;
                                    if (cachedBluetoothDevice5 == null) {
                                        cachedBluetoothDevice5 = null;
                                    }
                                    z2 = zSemIsDualPlayMode;
                                    iSemGetFineVolume = audioManager4.semGetFineVolume(cachedBluetoothDevice5.mDevice, 3);
                                } else {
                                    z2 = zSemIsDualPlayMode;
                                    iSemGetFineVolume = bluetoothDeviceController.audioManager.semGetFineVolume(3);
                                }
                                int i15 = iSemGetFineVolume;
                                CachedBluetoothDevice cachedBluetoothDevice6 = bluetoothDevice2.cachedBluetoothDevice;
                                if (cachedBluetoothDevice6 == null) {
                                    cachedBluetoothDevice6 = null;
                                }
                                ?? Copy$default = com.android.systemui.media.mediaoutput.entity.BluetoothDevice.copy$default(bluetoothDevice2, CachedBluetoothDeviceExtKt.getBatteryDescription(cachedBluetoothDevice6), null, i15, (z2 && zIsConnectedA2dpDevice) ? State.SELECTED : bluetoothDevice2.state, ref$BooleanRef.element && zIsConnectedA2dpDevice, 1627);
                                Copy$default.deepCopy((com.android.systemui.media.mediaoutput.entity.BluetoothDevice) audioDevice);
                                musicShareDevice = Copy$default;
                            } else {
                                z2 = zSemIsDualPlayMode;
                                if (audioDevice instanceof MusicShareDevice) {
                                    CharSequence[] charSequenceArr = new CharSequence[i12];
                                    MusicShareDevice musicShareDevice2 = (MusicShareDevice) audioDevice;
                                    CachedBluetoothDevice cachedBluetoothDevice7 = musicShareDevice2.cachedBluetoothDevice;
                                    charSequenceArr[0] = cachedBluetoothDevice7 != null ? CachedBluetoothDeviceExtKt.getBatteryDescription(cachedBluetoothDevice7) : null;
                                    charSequenceArr[1] = musicShareDevice2.description;
                                    MultiSequenceString multiSequenceString = new MultiSequenceString(ArraysKt___ArraysKt.filterNotNull(charSequenceArr), "\n");
                                    AudioManager audioManager5 = bluetoothDeviceController.audioManager;
                                    CachedBluetoothDevice cachedBluetoothDevice8 = musicShareDevice2.cachedBluetoothDevice;
                                    MusicShareDevice musicShareDeviceCopy$default = MusicShareDevice.copy$default(musicShareDevice2, multiSequenceString, audioManager5.semGetFineVolume(cachedBluetoothDevice8 != null ? cachedBluetoothDevice8.mDevice : null, 3), z2 ? State.SELECTED : musicShareDevice2.state, false, 1883);
                                    musicShareDeviceCopy$default.deepCopy((MusicShareDevice) audioDevice);
                                    musicShareDevice = musicShareDeviceCopy$default;
                                } else {
                                    musicShareDevice = null;
                                }
                            }
                            if (musicShareDevice != null) {
                                arrayList2.add(musicShareDevice);
                            }
                            zSemIsDualPlayMode = z2;
                            i12 = 2;
                        }
                        if (zSemIsDualPlayMode) {
                            ArrayList arrayList10 = new ArrayList();
                            int size8 = arrayList2.size();
                            int i16 = 0;
                            while (i16 < size8) {
                                Object obj10 = arrayList2.get(i16);
                                i16++;
                                if (obj10 instanceof com.android.systemui.media.mediaoutput.entity.BluetoothDevice) {
                                    arrayList10.add(obj10);
                                }
                            }
                            ArrayList arrayList11 = new ArrayList();
                            int size9 = arrayList10.size();
                            int i17 = 0;
                            while (i17 < size9) {
                                Object obj11 = arrayList10.get(i17);
                                i17++;
                                AudioDeviceExt.INSTANCE.getClass();
                                if (AudioDeviceExt.isActive((com.android.systemui.media.mediaoutput.entity.BluetoothDevice) obj11)) {
                                    arrayList11.add(obj11);
                                }
                            }
                            int size10 = arrayList11.size();
                            int i18 = 0;
                            while (i18 < size10) {
                                Object obj12 = arrayList11.get(i18);
                                i18++;
                                ((com.android.systemui.media.mediaoutput.entity.BluetoothDevice) obj12).activeDevices = arrayList11;
                            }
                        }
                        int size11 = arrayList2.size();
                        int i19 = 0;
                        while (i19 < size11) {
                            Object obj13 = arrayList2.get(i19);
                            i19++;
                            Log.d("BluetoothDeviceController", "\t" + obj13);
                        }
                        SharedFlowImpl sharedFlowImpl = bluetoothDeviceController.devicesFlow;
                        c09391.L$0 = bluetoothDeviceController;
                        c09391.L$1 = arrayList2;
                        c09391.L$2 = arrayList2;
                        c09391.label = 1;
                        if (sharedFlowImpl.emit(arrayList2, c09391) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                }
            } else {
                ArrayList arrayList12 = new ArrayList();
                for (Object obj14 : connectedDevices) {
                    if (((SemBluetoothCastDevice) obj14).getLocalDeviceRole() == 2) {
                        arrayList12.add(obj14);
                    }
                }
                ArrayList arrayList13 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList12, 10));
                int size12 = arrayList12.size();
                int i20 = 0;
                while (i20 < size12) {
                    Object obj15 = arrayList12.get(i20);
                    i20++;
                    arrayList13.add(((SemBluetoothCastDevice) obj15).getAddress());
                }
                set = CollectionsKt___CollectionsKt.toSet(arrayList13);
                if (set == null) {
                }
                ArrayList arrayList62 = new ArrayList();
                while (r11.hasNext()) {
                }
                size = arrayList62.size();
                i = 0;
                while (i < size) {
                }
                i2 = i5;
                int i122 = i8;
                ArrayList arrayList72 = new ArrayList();
                size2 = arrayList62.size();
                i3 = 0;
                while (i3 < size2) {
                }
                ArrayList arrayList82 = new ArrayList(arrayList72);
                Ref$BooleanRef ref$BooleanRef2 = new Ref$BooleanRef();
                arrayList = new ArrayList();
                size3 = arrayList82.size();
                i4 = 0;
                while (i4 < size3) {
                }
                if (arrayList.size() <= i2) {
                }
                if (arrayList == null) {
                }
            }
        } else {
            if (i7 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r0 = (List) c09391.L$2;
            BluetoothDeviceController bluetoothDeviceController2 = (BluetoothDeviceController) c09391.L$0;
            ResultKt.throwOnFailure(obj);
            arrayList2 = r0;
            bluetoothDeviceController = bluetoothDeviceController2;
        }
        if (bluetoothDeviceController.musicShareEventLogged) {
            arrayList2 = null;
        }
        if (arrayList2 != null) {
            ArrayList arrayList14 = new ArrayList();
            for (Object obj16 : arrayList2) {
                if (obj16 instanceof MusicShareDevice) {
                    arrayList14.add(obj16);
                }
            }
            MusicShareDevice musicShareDevice3 = (MusicShareDevice) CollectionsKt___CollectionsKt.firstOrNull((List) arrayList14);
            if (musicShareDevice3 != null) {
                AudioDeviceExt.INSTANCE.getClass();
                if ((AudioDeviceExt.isConnected(musicShareDevice3) ? musicShareDevice3 : null) != null) {
                    MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.MusicShareOnMyDevice.INSTANCE);
                    bluetoothDeviceController.musicShareEventLogged = true;
                }
            }
        }
        return Unit.INSTANCE;
    }
}
