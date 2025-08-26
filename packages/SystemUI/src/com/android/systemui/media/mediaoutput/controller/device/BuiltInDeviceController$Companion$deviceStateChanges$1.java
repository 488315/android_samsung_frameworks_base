package com.android.systemui.media.mediaoutput.controller.device;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
import com.android.systemui.media.mediaoutput.ext.BundleExtKt;
import java.util.ArrayList;
import java.util.Set;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class BuiltInDeviceController$Companion$deviceStateChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Pair<Context, AudioManager> $this_deviceStateChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public BuiltInDeviceController$Companion$deviceStateChanges$1(Pair<? extends Context, ? extends AudioManager> pair, Continuation continuation) {
        super(2, continuation);
        this.$this_deviceStateChanges = pair;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        BuiltInDeviceController$Companion$deviceStateChanges$1 builtInDeviceController$Companion$deviceStateChanges$1 = new BuiltInDeviceController$Companion$deviceStateChanges$1(this.$this_deviceStateChanges, continuation);
        builtInDeviceController$Companion$deviceStateChanges$1.L$0 = obj;
        return builtInDeviceController$Companion$deviceStateChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BuiltInDeviceController$Companion$deviceStateChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.AudioDeviceCallback, com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$callback$1] */
    /* JADX WARN: Type inference failed for: r3v1, types: [android.content.BroadcastReceiver, com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final Pair<Context, AudioManager> pair = this.$this_deviceStateChanges;
            final ?? r1 = new AudioDeviceCallback() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$callback$1
                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
                    ArrayList arrayList = new ArrayList(audioDeviceInfoArr.length);
                    for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                        AudioDeviceInfoExt.INSTANCE.getClass();
                        arrayList.add(AudioDeviceInfoExt.toLogText(audioDeviceInfo));
                    }
                    Log.d("BuiltInDeviceController", "onAudioDevicesAdded() - " + arrayList);
                    updateListForValidMedia(audioDeviceInfoArr);
                }

                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
                    ArrayList arrayList = new ArrayList(audioDeviceInfoArr.length);
                    int i2 = 0;
                    for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                        AudioDeviceInfoExt.INSTANCE.getClass();
                        arrayList.add(AudioDeviceInfoExt.toLogText(audioDeviceInfo));
                    }
                    Log.d("BuiltInDeviceController", "onAudioDevicesRemoved() - " + arrayList);
                    ArrayList arrayList2 = new ArrayList(audioDeviceInfoArr.length);
                    for (AudioDeviceInfo audioDeviceInfo2 : audioDeviceInfoArr) {
                        arrayList2.add(audioDeviceInfo2.getAddress());
                    }
                    Set set = CollectionsKt___CollectionsKt.toSet(arrayList2);
                    AudioDeviceInfo[] devices = ((AudioManager) pair.getSecond()).getDevices(2);
                    ArrayList arrayList3 = new ArrayList();
                    for (AudioDeviceInfo audioDeviceInfo3 : devices) {
                        if (audioDeviceInfo3.getType() != 7) {
                            arrayList3.add(audioDeviceInfo3);
                        }
                    }
                    if (!arrayList3.isEmpty()) {
                        int size = arrayList3.size();
                        while (true) {
                            if (i2 >= size) {
                                break;
                            }
                            Object obj2 = arrayList3.get(i2);
                            i2++;
                            if (set.contains(((AudioDeviceInfo) obj2).getAddress())) {
                                arrayList3 = null;
                                break;
                            }
                        }
                    }
                    if (arrayList3 != null) {
                        updateListForValidMedia(audioDeviceInfoArr);
                    }
                }

                public final void updateListForValidMedia(AudioDeviceInfo[] audioDeviceInfoArr) {
                    boolean zIsWiredHeadsetOn = ((AudioManager) pair.getSecond()).isWiredHeadsetOn();
                    for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                        AudioDeviceInfoExt.INSTANCE.getClass();
                        if (AudioDeviceInfoExt.isValidDeviceTypeForMedia(audioDeviceInfo, zIsWiredHeadsetOn)) {
                            ProducerScope producerScope2 = producerScope;
                            BuildersKt.launch$default(producerScope2, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope2, pair, null), 3);
                            return;
                        }
                    }
                }
            };
            final Pair<Context, AudioManager> pair2 = this.$this_deviceStateChanges;
            final ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$receiver$1
                /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
                /* JADX WARN: Removed duplicated region for block: B:51:0x00db  */
                @Override // android.content.BroadcastReceiver
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    ArrayList arrayList = null;
                    Log.d("BuiltInDeviceController", "onReceive() - " + intent + ", " + (extras != null ? BundleExtKt.getSerialize(extras) : null));
                    String action = intent.getAction();
                    if (action != null) {
                        switch (action.hashCode()) {
                            case -1966727609:
                                if (!action.equals("android.samsung.media.action.AUDIO_MODE")) {
                                }
                                ProducerScope producerScope2 = producerScope;
                                BuildersKt.launch$default(producerScope2, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope2, pair2, null), 3);
                                break;
                            case -1940635523:
                                if (!action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                                }
                                if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) != 3) {
                                    ProducerScope producerScope3 = producerScope;
                                    BuildersKt.launch$default(producerScope3, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope3, pair2, null), 3);
                                    break;
                                }
                                break;
                            case -1315844839:
                                if (!action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                                }
                                if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) != 3) {
                                }
                                break;
                            case -805245182:
                                if (!action.equals("android.intent.action.MULTISOUND_STATE_CHANGE")) {
                                }
                                ProducerScope producerScope22 = producerScope;
                                BuildersKt.launch$default(producerScope22, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope22, pair2, null), 3);
                                break;
                            case 487423555:
                                if (action.equals("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED")) {
                                    BluetoothDevice bluetoothDevice = (BluetoothDevice) intent.getParcelableExtra("android.bluetooth.device.extra.DEVICE", BluetoothDevice.class);
                                    if (bluetoothDevice != null) {
                                        AudioDeviceInfo[] devices = ((AudioManager) pair2.getSecond()).getDevices(2);
                                        ArrayList arrayList2 = new ArrayList();
                                        int i2 = 0;
                                        for (AudioDeviceInfo audioDeviceInfo : devices) {
                                            if (audioDeviceInfo.getType() != 7) {
                                                arrayList2.add(audioDeviceInfo);
                                            }
                                        }
                                        if (!arrayList2.isEmpty()) {
                                            int size = arrayList2.size();
                                            while (true) {
                                                if (i2 < size) {
                                                    Object obj2 = arrayList2.get(i2);
                                                    i2++;
                                                    if (Intrinsics.areEqual(((AudioDeviceInfo) obj2).getAddress(), bluetoothDevice.getAddress())) {
                                                        arrayList = arrayList2;
                                                    }
                                                }
                                            }
                                        }
                                        if (arrayList != null) {
                                            ProducerScope producerScope4 = producerScope;
                                            BuildersKt.launch$default(producerScope4, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope4, pair2, null), 3);
                                            break;
                                        } else {
                                            Log.d("BuiltInDeviceController", "onReceive() - device not exist in AudioManager");
                                            break;
                                        }
                                    } else {
                                        Log.d("BuiltInDeviceController", "onReceive() - extra device is null");
                                        break;
                                    }
                                }
                                break;
                            case 1920758225:
                                if (!action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                                }
                                ProducerScope producerScope222 = producerScope;
                                BuildersKt.launch$default(producerScope222, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope222, pair2, null), 3);
                                break;
                        }
                    }
                }
            };
            ((AudioManager) this.$this_deviceStateChanges.getSecond()).registerAudioDeviceCallback(r1, null);
            Context context = (Context) this.$this_deviceStateChanges.getFirst();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.MULTISOUND_STATE_CHANGE");
            intentFilter.addAction("android.bluetooth.a2dp.profile.action.ACTIVE_DEVICE_CHANGED");
            intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            intentFilter.addAction("android.samsung.media.action.AUDIO_MODE");
            intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            Unit unit = Unit.INSTANCE;
            context.registerReceiver(r3, intentFilter);
            final Pair<Context, AudioManager> pair3 = this.$this_deviceStateChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("BuiltInDeviceController", "unregister");
                    Pair pair4 = pair3;
                    ((AudioManager) pair4.getSecond()).unregisterAudioDeviceCallback(r1);
                    ((Context) pair4.getFirst()).unregisterReceiver(r3);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
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
