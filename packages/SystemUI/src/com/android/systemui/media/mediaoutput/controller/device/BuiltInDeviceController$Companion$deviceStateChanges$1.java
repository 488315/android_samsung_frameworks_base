package com.android.systemui.media.mediaoutput.controller.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import com.android.systemui.media.mediaoutput.ext.AudioDeviceInfoExt;
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
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                    boolean isWiredHeadsetOn = ((AudioManager) pair.getSecond()).isWiredHeadsetOn();
                    for (AudioDeviceInfo audioDeviceInfo : audioDeviceInfoArr) {
                        AudioDeviceInfoExt.INSTANCE.getClass();
                        if (AudioDeviceInfoExt.isValidDeviceTypeForMedia(audioDeviceInfo, isWiredHeadsetOn)) {
                            BuildersKt.launch$default(r6, null, null, new BuiltInDeviceController$Companion$deviceStateChanges$1$updateDevices$1(producerScope, pair, null), 3);
                            return;
                        }
                    }
                }
            };
            final Pair<Context, AudioManager> pair2 = this.$this_deviceStateChanges;
            final ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$receiver$1
                /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
                /* JADX WARN: Removed duplicated region for block: B:52:0x00db  */
                @Override // android.content.BroadcastReceiver
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onReceive(android.content.Context r10, android.content.Intent r11) {
                    /*
                        Method dump skipped, instructions count: 270
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.device.BuiltInDeviceController$Companion$deviceStateChanges$1$receiver$1.onReceive(android.content.Context, android.content.Intent):void");
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
                    Pair pair4 = Pair.this;
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
