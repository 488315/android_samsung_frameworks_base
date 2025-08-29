package com.android.systemui.media.mediaoutput.controller.device;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import androidx.datastore.core.DataStore;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.systemui.media.mediaoutput.analytics.MoSaLogging;
import com.android.systemui.media.mediaoutput.analytics.SaCustom;
import com.android.systemui.media.mediaoutput.analytics.SaEvent;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt;
import com.android.systemui.media.mediaoutput.common.DataStoreDebugLabsExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.common.DataStoreExt;
import com.android.systemui.media.mediaoutput.common.DataStoreExt$special$$inlined$map$4;
import com.android.systemui.media.mediaoutput.common.MediaOutputConst;
import com.android.systemui.media.mediaoutput.compose.ext.TintDrawablePainter;
import com.android.systemui.media.mediaoutput.entity.AudioDevice;
import com.android.systemui.media.mediaoutput.entity.AudioDeviceExt;
import com.android.systemui.media.mediaoutput.entity.MusicShareDevice;
import com.android.systemui.media.mediaoutput.entity.State;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import com.android.systemui.media.mediaoutput.ext.AudioManagerExtKt;
import com.samsung.android.bluetooth.SemBluetoothAudioCast;
import com.samsung.android.bluetooth.SemBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.AudioCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDevice;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.CachedBluetoothCastDeviceManager;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastAdapter;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfile;
import com.samsung.android.settingslib.bluetooth.bluetoothcast.LocalBluetoothCastProfileManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt__MathJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.StandaloneCoroutine;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.SharedFlowImpl;

/* loaded from: classes2.dex */
public final class MusicShareDeviceController extends DeviceController {
    public static final Companion Companion = new Companion(null);
    public final AudioManager audioManager;
    public final Context context;
    public final DataStore dataStore;
    public boolean isShowMusicShareEnabled = true;
    public boolean isSupportSelectableBudsTogether;
    public boolean isTriggeredBudsTogether;
    public final LocalBluetoothManager localBluetoothManager;
    public int musicShareDeviceCount;
    public StandaloneCoroutine updateJob;

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MusicShareDeviceController.this.new AnonymousClass1(continuation);
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
                DataStoreExt dataStoreExt = DataStoreExt.INSTANCE;
                DataStore dataStore = MusicShareDeviceController.this.dataStore;
                dataStoreExt.getClass();
                DataStoreExt$special$$inlined$map$4 dataStoreExt$special$$inlined$map$4 = new DataStoreExt$special$$inlined$map$4(dataStore.getData());
                final MusicShareDeviceController musicShareDeviceController = MusicShareDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.1.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        LocalBluetoothCastAdapter localBluetoothCastAdapter;
                        boolean zBooleanValue = ((Boolean) obj2).booleanValue();
                        MusicShareDeviceController musicShareDeviceController2 = musicShareDeviceController;
                        musicShareDeviceController2.isShowMusicShareEnabled = zBooleanValue;
                        LocalBluetoothManager localBluetoothManager = musicShareDeviceController2.localBluetoothManager;
                        if (localBluetoothManager != null && (localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter) != null) {
                            if (zBooleanValue) {
                                localBluetoothCastAdapter.startDiscovery();
                            } else {
                                localBluetoothCastAdapter.cancelDiscovery();
                            }
                        }
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreExt$special$$inlined$map$4.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass2(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MusicShareDeviceController.this.new AnonymousClass2(continuation);
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
                DataStoreDebugLabsExt dataStoreDebugLabsExt = DataStoreDebugLabsExt.INSTANCE;
                DataStore dataStore = MusicShareDeviceController.this.dataStore;
                dataStoreDebugLabsExt.getClass();
                DataStoreDebugLabsExt$special$$inlined$map$4 dataStoreDebugLabsExt$special$$inlined$map$4 = new DataStoreDebugLabsExt$special$$inlined$map$4(dataStore.getData());
                final MusicShareDeviceController musicShareDeviceController = MusicShareDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.2.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) {
                        musicShareDeviceController.isSupportSelectableBudsTogether = ((Boolean) obj2).booleanValue();
                        return Unit.INSTANCE;
                    }
                };
                this.label = 1;
                if (dataStoreDebugLabsExt$special$$inlined$map$4.collect(flowCollector, this) == coroutineSingletons) {
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$3, reason: invalid class name */
    final class AnonymousClass3 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass3(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MusicShareDeviceController.this.new AnonymousClass3(continuation);
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
                Companion companion = MusicShareDeviceController.Companion;
                MusicShareDeviceController musicShareDeviceController = MusicShareDeviceController.this;
                AudioManager audioManager = musicShareDeviceController.audioManager;
                Context context = musicShareDeviceController.context;
                companion.getClass();
                Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MusicShareDeviceController$Companion$castDeviceChanges$1(audioManager, context, null)), -1, 2);
                MediaOutputConst.INSTANCE.getClass();
                Flow flowM3481debounceHG0u8IE = FlowKt.m3481debounceHG0u8IE(flowBuffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                final MusicShareDeviceController musicShareDeviceController2 = MusicShareDeviceController.this;
                FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.3.1
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    public final Object emit(Object obj2, Continuation continuation) throws Resources.NotFoundException {
                        LocalBluetoothCastAdapter localBluetoothCastAdapter;
                        MusicShareDeviceController musicShareDeviceController3 = musicShareDeviceController2;
                        LocalBluetoothManager localBluetoothManager = musicShareDeviceController3.localBluetoothManager;
                        if (localBluetoothManager != null && (localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter) != null) {
                            if (musicShareDeviceController3.isShowMusicShareEnabled) {
                                localBluetoothCastAdapter.startDiscovery();
                            } else {
                                localBluetoothCastAdapter.cancelDiscovery();
                            }
                        }
                        Object objAccess$updateDevices = MusicShareDeviceController.access$updateDevices(musicShareDeviceController3, continuation);
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

    /* renamed from: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$4, reason: invalid class name */
    final class AnonymousClass4 extends SuspendLambda implements Function2 {
        int label;

        public AnonymousClass4(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MusicShareDeviceController.this.new AnonymousClass4(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass4) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                LocalBluetoothManager localBluetoothManager = MusicShareDeviceController.this.localBluetoothManager;
                if (localBluetoothManager != null) {
                    MusicShareDeviceController.Companion.getClass();
                    Flow flowBuffer$default = FlowKt.buffer$default(FlowKt.callbackFlow(new MusicShareDeviceController$Companion$castEventChanges$1(localBluetoothManager, null)), -1, 2);
                    if (flowBuffer$default != null) {
                        MediaOutputConst.INSTANCE.getClass();
                        Flow flowM3481debounceHG0u8IE = FlowKt.m3481debounceHG0u8IE(flowBuffer$default, MediaOutputConst.AUDIO_PATH_DEBOUNCE_TIMEOUT);
                        if (flowM3481debounceHG0u8IE != null) {
                            final MusicShareDeviceController musicShareDeviceController = MusicShareDeviceController.this;
                            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController.4.1
                                @Override // kotlinx.coroutines.flow.FlowCollector
                                public final Object emit(Object obj2, Continuation continuation) throws Resources.NotFoundException {
                                    Object objAccess$updateDevices = MusicShareDeviceController.access$updateDevices(musicShareDeviceController, continuation);
                                    return objAccess$updateDevices == CoroutineSingletons.COROUTINE_SUSPENDED ? objAccess$updateDevices : Unit.INSTANCE;
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

    public MusicShareDeviceController(Context context, AudioManager audioManager, LocalBluetoothManager localBluetoothManager, DataStore dataStore) {
        this.context = context;
        this.audioManager = audioManager;
        this.localBluetoothManager = localBluetoothManager;
        this.dataStore = dataStore;
        Log.d("MusicShareDeviceController", "init()");
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass1(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass2(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass3(null), 3);
        BuildersKt.launch$default(getControllerScope(), null, null, new AnonymousClass4(null), 3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Type inference failed for: r0v9, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r1v22, types: [java.lang.Object] */
    /* JADX WARN: Type inference failed for: r7v12, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r7v3, types: [kotlin.collections.EmptyList] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$updateDevices(MusicShareDeviceController musicShareDeviceController, Continuation continuation) throws Resources.NotFoundException {
        MusicShareDeviceController$updateDevices$1 musicShareDeviceController$updateDevices$1;
        Object obj;
        ?? arrayList;
        ArrayList arrayList2;
        int audioSharingDeviceVolume;
        State state;
        State state2;
        State state3;
        int castProfileConnectionState;
        CachedBluetoothCastDeviceManager cachedBluetoothCastDeviceManager;
        MusicShareDevice musicShareDevice;
        MusicShareDeviceController musicShareDeviceController2 = musicShareDeviceController;
        musicShareDeviceController2.getClass();
        if (continuation instanceof MusicShareDeviceController$updateDevices$1) {
            musicShareDeviceController$updateDevices$1 = (MusicShareDeviceController$updateDevices$1) continuation;
            int i = musicShareDeviceController$updateDevices$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                musicShareDeviceController$updateDevices$1.label = i - Integer.MIN_VALUE;
            } else {
                musicShareDeviceController$updateDevices$1 = new MusicShareDeviceController$updateDevices$1(musicShareDeviceController2, continuation);
            }
        }
        Object obj2 = musicShareDeviceController$updateDevices$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = musicShareDeviceController$updateDevices$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(obj2);
            AudioDeviceInfo[] devices = musicShareDeviceController2.audioManager.getDevices(2);
            ArrayList arrayList3 = new ArrayList();
            int i3 = 0;
            for (AudioDeviceInfo audioDeviceInfo : devices) {
                if (audioDeviceInfo.getType() == 25) {
                    arrayList3.add(audioDeviceInfo);
                }
            }
            int size = arrayList3.size();
            int i4 = 0;
            while (true) {
                if (i4 >= size) {
                    obj = null;
                    break;
                }
                obj = arrayList3.get(i4);
                i4++;
                AudioDeviceInfo audioDeviceInfo2 = (AudioDeviceInfo) obj;
                if (Intrinsics.areEqual(audioDeviceInfo2.getAddress(), "0") || audioDeviceInfo2.getAddress().startsWith("hsp:")) {
                    break;
                }
            }
            AudioDeviceInfo audioDeviceInfo3 = (AudioDeviceInfo) obj;
            boolean z = musicShareDeviceController2.isShowMusicShareEnabled;
            LocalBluetoothManager localBluetoothManager = musicShareDeviceController2.localBluetoothManager;
            if (localBluetoothManager == null || (cachedBluetoothCastDeviceManager = localBluetoothManager.mCachedCastDeviceManager) == null) {
                arrayList = EmptyList.INSTANCE;
            } else {
                Collection cachedCastDevicesCopy = cachedBluetoothCastDeviceManager.getCachedCastDevicesCopy();
                arrayList = new ArrayList();
                ArrayList arrayList4 = (ArrayList) cachedCastDevicesCopy;
                int size2 = arrayList4.size();
                int i5 = 0;
                while (i5 < size2) {
                    Object obj3 = arrayList4.get(i5);
                    i5++;
                    if (((CachedBluetoothCastDevice) obj3).isConnected() || z) {
                        arrayList.add(obj3);
                    }
                }
            }
            List<CachedBluetoothCastDevice> listSortedWith = CollectionsKt___CollectionsKt.sortedWith((Iterable) arrayList, new Comparator() { // from class: com.android.systemui.media.mediaoutput.controller.device.MusicShareDeviceController$updateDevices$$inlined$sortedByDescending$1
                @Override // java.util.Comparator
                public final int compare(Object obj4, Object obj5) {
                    CachedBluetoothCastDevice cachedBluetoothCastDevice = (CachedBluetoothCastDevice) obj5;
                    CachedBluetoothCastDevice cachedBluetoothCastDevice2 = (CachedBluetoothCastDevice) obj4;
                    return ComparisonsKt__ComparisonsKt.compareValues(Long.valueOf(cachedBluetoothCastDevice.isConnected() ? Long.MAX_VALUE : cachedBluetoothCastDevice.getConnectionTimeStamp()), Long.valueOf(cachedBluetoothCastDevice2.isConnected() ? Long.MAX_VALUE : cachedBluetoothCastDevice2.getConnectionTimeStamp()));
                }
            });
            arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listSortedWith, 10));
            for (CachedBluetoothCastDevice cachedBluetoothCastDevice : listSortedWith) {
                MusicShareDevice.Companion companion = MusicShareDevice.Companion;
                cachedBluetoothCastDevice.getClass();
                Iterator it = cachedBluetoothCastDevice.mCastProfiles.iterator();
                while (true) {
                    audioSharingDeviceVolume = -1;
                    if (!it.hasNext()) {
                        break;
                    }
                    LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) it.next();
                    if (localBluetoothCastProfile != null) {
                        SemBluetoothCastDevice semBluetoothCastDevice = cachedBluetoothCastDevice.mCastDevice;
                        AudioCastProfile audioCastProfile = (AudioCastProfile) localBluetoothCastProfile;
                        Log.d(audioCastProfile.TAG, "getAudioSharingDeviceVolume");
                        SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                        if (semBluetoothAudioCast != null) {
                            audioSharingDeviceVolume = semBluetoothAudioCast.getAudioSharingDeviceVolume(semBluetoothCastDevice);
                        }
                    }
                }
                int i6 = audioSharingDeviceVolume * 10;
                if (audioDeviceInfo3 != null) {
                    AudioDeviceInfoExt audioDeviceInfoExt = AudioDeviceInfoExt.INSTANCE;
                    AudioManager audioManager = musicShareDeviceController2.audioManager;
                    audioDeviceInfoExt.getClass();
                    int type = audioDeviceInfo3.getType();
                    Lazy lazy = AudioManagerExtKt.mediaStrategy$delegate;
                    state = audioManager.semGetCurrentDeviceType() == type ? State.SELECTED : State.CONNECTED;
                }
                companion.getClass();
                String strM = AbstractResolvableFuture$$ExternalSyntheticOutline0.m(cachedBluetoothCastDevice.mCastDevice.getPeerAddress(), "/", cachedBluetoothCastDevice.mCastDevice.getAddress());
                String name = cachedBluetoothCastDevice.getName();
                String connectionSummary = cachedBluetoothCastDevice.getConnectionSummary();
                TintDrawablePainter.Companion companion2 = TintDrawablePainter.Companion;
                Drawable btCastDrawable = cachedBluetoothCastDevice.getBtCastDrawable();
                companion2.getClass();
                TintDrawablePainter converter = TintDrawablePainter.Companion.toConverter(btCastDrawable);
                if (cachedBluetoothCastDevice.isConnected()) {
                    state3 = state;
                } else {
                    List castProfiles = cachedBluetoothCastDevice.getCastProfiles();
                    for (int i7 = 0; i7 < castProfiles.size(); i7++) {
                        LocalBluetoothCastProfile localBluetoothCastProfile2 = (LocalBluetoothCastProfile) castProfiles.get(i7);
                        if (localBluetoothCastProfile2 != null && ((castProfileConnectionState = cachedBluetoothCastDevice.getCastProfileConnectionState(localBluetoothCastProfile2)) == 1 || castProfileConnectionState == 3)) {
                            state2 = State.CONNECTING;
                            break;
                        }
                    }
                    state2 = State.DISCONNECTED;
                    state3 = state2;
                }
                MusicShareDevice musicShareDevice2 = new MusicShareDevice(strM, name, connectionSummary, converter, null, i6, 0, state3, false, (cachedBluetoothCastDevice.isConnected() && state == State.SELECTED) ? false : true, cachedBluetoothCastDevice.isConnected(), 336, null);
                musicShareDevice2.cachedBluetoothCastDevice = cachedBluetoothCastDevice;
                musicShareDevice2.audioDeviceInfo = audioDeviceInfo3;
                arrayList2.add(musicShareDevice2);
            }
            int size3 = arrayList2.size();
            while (i3 < size3) {
                Object obj4 = arrayList2.get(i3);
                i3++;
                MusicShareDevice musicShareDevice3 = (MusicShareDevice) obj4;
                CachedBluetoothCastDevice cachedBluetoothCastDevice2 = musicShareDevice3.cachedBluetoothCastDevice;
                Log.d("MusicShareDeviceController", "\t" + musicShareDevice3 + " - " + (cachedBluetoothCastDevice2 != null ? new Long(cachedBluetoothCastDevice2.getConnectionTimeStamp()) : null));
            }
            SharedFlowImpl sharedFlowImpl = musicShareDeviceController2.devicesFlow;
            musicShareDeviceController$updateDevices$1.L$0 = musicShareDeviceController2;
            musicShareDeviceController$updateDevices$1.L$1 = arrayList2;
            musicShareDeviceController$updateDevices$1.L$2 = arrayList2;
            musicShareDeviceController$updateDevices$1.label = 1;
            if (sharedFlowImpl.emit(arrayList2, musicShareDeviceController$updateDevices$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ?? r0 = (List) musicShareDeviceController$updateDevices$1.L$2;
            MusicShareDeviceController musicShareDeviceController3 = (MusicShareDeviceController) musicShareDeviceController$updateDevices$1.L$0;
            ResultKt.throwOnFailure(obj2);
            arrayList2 = r0;
            musicShareDeviceController2 = musicShareDeviceController3;
        }
        if (musicShareDeviceController2.musicShareDeviceCount == arrayList2.size()) {
            arrayList2 = null;
        }
        if (arrayList2 != null) {
            musicShareDeviceController2.musicShareDeviceCount = arrayList2.size();
            MoSaLogging moSaLogging = MoSaLogging.INSTANCE;
            SaEvent.SearchMusicShare searchMusicShare = SaEvent.SearchMusicShare.INSTANCE;
            SaCustom[] saCustomArr = {new SaCustom.Value(musicShareDeviceController2.musicShareDeviceCount > 0 ? "y" : "n")};
            moSaLogging.getClass();
            MoSaLogging.send(searchMusicShare, saCustomArr);
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            if (arrayList2 != null) {
                MoSaLogging.send(SaEvent.NumberOfMusicShareDevice.INSTANCE, new SaCustom.Number(musicShareDeviceController2.musicShareDeviceCount));
                Iterator it2 = arrayList2.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        musicShareDevice = null;
                        break;
                    }
                    ?? next = it2.next();
                    AudioDeviceExt.INSTANCE.getClass();
                    if (AudioDeviceExt.isConnected((MusicShareDevice) next)) {
                        musicShareDevice = next;
                        break;
                    }
                }
                if (musicShareDevice != null) {
                    MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.SharedDevicesWithMusicShare.INSTANCE);
                }
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit adjustVolume(AudioDevice audioDevice, int i) {
        Log.d("MusicShareDeviceController", "adjustVolume() - " + audioDevice + " - " + i);
        if (audioDevice instanceof MusicShareDevice) {
            MusicShareDevice musicShareDevice = (MusicShareDevice) audioDevice;
            AudioDeviceInfo audioDeviceInfo = musicShareDevice.audioDeviceInfo;
            if (isAudioSharingEnabled()) {
                audioDeviceInfo = null;
            }
            if (audioDeviceInfo != null) {
                this.audioManager.semSetFineVolume(3, i, 0);
            } else {
                CachedBluetoothCastDevice cachedBluetoothCastDevice = musicShareDevice.cachedBluetoothCastDevice;
                if (cachedBluetoothCastDevice != null) {
                    int iRoundToInt = MathKt__MathJVMKt.roundToInt(i / 10);
                    Iterator it = cachedBluetoothCastDevice.mCastProfiles.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) it.next();
                        if (localBluetoothCastProfile != null) {
                            SemBluetoothCastDevice semBluetoothCastDevice = cachedBluetoothCastDevice.mCastDevice;
                            AudioCastProfile audioCastProfile = (AudioCastProfile) localBluetoothCastProfile;
                            Log.d(audioCastProfile.TAG, "setAudioSharingDeviceVolume");
                            SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                            if (semBluetoothAudioCast != null) {
                                semBluetoothAudioCast.setAudioSharingDeviceVolume(semBluetoothCastDevice, iRoundToInt);
                            }
                        }
                    }
                }
            }
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit cancel(AudioDevice audioDevice) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("cancel() - ", audioDevice, "MusicShareDeviceController");
        if (audioDevice instanceof MusicShareDevice) {
            CachedBluetoothCastDevice cachedBluetoothCastDevice = ((MusicShareDevice) audioDevice).cachedBluetoothCastDevice;
            if (cachedBluetoothCastDevice != null) {
                cachedBluetoothCastDevice.disconnect();
            }
            MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.EndMusicShareClient.INSTANCE);
        }
        return Unit.INSTANCE;
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final void close() {
        LocalBluetoothCastAdapter localBluetoothCastAdapter;
        super.close();
        Log.d("MusicShareDeviceController", "close()");
        LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothCastAdapter = localBluetoothManager.mLocalCastAdapter) == null) {
            return;
        }
        localBluetoothCastAdapter.cancelDiscovery();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Object deselect(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("deselect() - ", audioDevice, "MusicShareDeviceController");
        if (audioDevice instanceof MusicShareDevice) {
            setAudioSharingEnabled(false);
        }
        return Unit.INSTANCE;
    }

    public final boolean isAudioSharingEnabled() {
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) == null || (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) == null) {
            return false;
        }
        Log.d(audioCastProfile.TAG, "isAudioSharingEnabled");
        SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
        if (semBluetoothAudioCast == null) {
            return false;
        }
        return semBluetoothAudioCast.isAudioSharingEnabled();
    }

    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    public final Unit select(AudioDevice audioDevice, ContinuationImpl continuationImpl) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("select() - ", audioDevice, "MusicShareDeviceController");
        if (audioDevice instanceof MusicShareDevice) {
            setAudioSharingEnabled(true);
        }
        return Unit.INSTANCE;
    }

    public final void setAudioSharingEnabled(boolean z) {
        LocalBluetoothCastProfileManager localBluetoothCastProfileManager;
        AudioCastProfile audioCastProfile;
        LocalBluetoothManager localBluetoothManager = this.localBluetoothManager;
        if (localBluetoothManager == null || (localBluetoothCastProfileManager = localBluetoothManager.mLocalCastProfileManager) == null || (audioCastProfile = localBluetoothCastProfileManager.mAudioCastProfile) == null) {
            return;
        }
        Log.d(audioCastProfile.TAG, "setAudioSharingEnabled");
        SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
        if (semBluetoothAudioCast == null) {
            return;
        }
        semBluetoothAudioCast.setAudioSharingEnabled(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    @Override // com.android.systemui.media.mediaoutput.controller.device.DeviceController
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object transfer(AudioDevice audioDevice, Continuation continuation) {
        AudioMirroringDeviceController$$ExternalSyntheticOutline0.m("transfer() - ", audioDevice, "MusicShareDeviceController");
        if (audioDevice instanceof MusicShareDevice) {
            MusicShareDevice musicShareDevice = (MusicShareDevice) audioDevice;
            CachedBluetoothCastDevice cachedBluetoothCastDevice = musicShareDevice.cachedBluetoothCastDevice;
            if (cachedBluetoothCastDevice == null) {
                AudioDeviceInfo audioDeviceInfo = musicShareDevice.audioDeviceInfo;
                if (audioDeviceInfo != null) {
                    CachedBluetoothCastDevice cachedBluetoothCastDevice2 = musicShareDevice.cachedBluetoothCastDevice;
                    if (cachedBluetoothCastDevice2 != null) {
                        Iterator it = cachedBluetoothCastDevice2.mCastProfiles.iterator();
                        while (it.hasNext()) {
                            LocalBluetoothCastProfile localBluetoothCastProfile = (LocalBluetoothCastProfile) it.next();
                            if (localBluetoothCastProfile != null) {
                                SemBluetoothCastDevice semBluetoothCastDevice = cachedBluetoothCastDevice2.mCastDevice;
                                AudioCastProfile audioCastProfile = (AudioCastProfile) localBluetoothCastProfile;
                                Log.d(audioCastProfile.TAG, "setActiveDevice");
                                SemBluetoothAudioCast semBluetoothAudioCast = audioCastProfile.mService;
                                if (semBluetoothAudioCast != null) {
                                    semBluetoothAudioCast.setActiveDevice(semBluetoothCastDevice);
                                }
                            }
                        }
                    }
                    this.audioManager.semSetDeviceForced(audioDeviceInfo.semGetInternalType(), isAudioSharingEnabled() ? audioDeviceInfo.semGetAddress() : "0");
                }
                MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.PlayMusicShare.INSTANCE);
            } else {
                if (cachedBluetoothCastDevice.isConnected()) {
                    cachedBluetoothCastDevice = null;
                }
                if (cachedBluetoothCastDevice != null) {
                    this.isTriggeredBudsTogether = true;
                    StandaloneCoroutine standaloneCoroutine = this.updateJob;
                    if (standaloneCoroutine != null) {
                        standaloneCoroutine.cancel(null);
                    }
                    this.updateJob = BuildersKt.launch$default(getControllerScope(), null, null, new MusicShareDeviceController$transfer$3$1(this, null), 3);
                    cachedBluetoothCastDevice.connect();
                }
                MoSaLogging.send$default(MoSaLogging.INSTANCE, SaEvent.PlayMusicShare.INSTANCE);
            }
        }
        return Unit.INSTANCE;
    }
}
