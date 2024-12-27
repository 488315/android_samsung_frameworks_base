package com.android.systemui.media.mediaoutput.controller.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.util.Log;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
final class WifiDisplayDeviceController$Companion$activeDeviceChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Pair<Context, AudioManager> $this_activeDeviceChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public WifiDisplayDeviceController$Companion$activeDeviceChanges$1(Pair<? extends Context, ? extends AudioManager> pair, Continuation continuation) {
        super(2, continuation);
        this.$this_activeDeviceChanges = pair;
    }

    public static final Job invokeSuspend$updateDevices(ProducerScope producerScope) {
        return BuildersKt.launch$default(producerScope, null, null, new WifiDisplayDeviceController$Companion$activeDeviceChanges$1$updateDevices$1(producerScope, null), 3);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        WifiDisplayDeviceController$Companion$activeDeviceChanges$1 wifiDisplayDeviceController$Companion$activeDeviceChanges$1 = new WifiDisplayDeviceController$Companion$activeDeviceChanges$1(this.$this_activeDeviceChanges, continuation);
        wifiDisplayDeviceController$Companion$activeDeviceChanges$1.L$0 = obj;
        return wifiDisplayDeviceController$Companion$activeDeviceChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((WifiDisplayDeviceController$Companion$activeDeviceChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.media.AudioDeviceCallback, com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeDeviceChanges$1$callback$1] */
    /* JADX WARN: Type inference failed for: r3v3, types: [android.content.BroadcastReceiver, com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeDeviceChanges$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new AudioDeviceCallback() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeDeviceChanges$1$callback$1
                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesAdded(AudioDeviceInfo[] audioDeviceInfoArr) {
                    WifiDisplayDeviceController$Companion$activeDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this);
                }

                @Override // android.media.AudioDeviceCallback
                public final void onAudioDevicesRemoved(AudioDeviceInfo[] audioDeviceInfoArr) {
                    WifiDisplayDeviceController$Companion$activeDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this);
                }
            };
            ((AudioManager) this.$this_activeDeviceChanges.getSecond()).registerAudioDeviceCallback(r1, null);
            final ?? r3 = new BroadcastReceiver() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeDeviceChanges$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Log.d("WifiDisplayDeviceController", "onReceive() - " + intent);
                    String action = intent.getAction();
                    if (action != null) {
                        int hashCode = action.hashCode();
                        if (hashCode != -1940635523) {
                            if (hashCode != -1315844839) {
                                if (hashCode != 1920758225 || !action.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                                    return;
                                }
                            } else if (!action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                                return;
                            }
                        } else if (!action.equals("android.media.VOLUME_CHANGED_ACTION")) {
                            return;
                        }
                        if (intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1) == 3) {
                            WifiDisplayDeviceController$Companion$activeDeviceChanges$1.invokeSuspend$updateDevices(ProducerScope.this);
                        }
                    }
                }
            };
            invokeSuspend$updateDevices(producerScope);
            Context context = (Context) this.$this_activeDeviceChanges.getFirst();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.media.STREAM_DEVICES_CHANGED_ACTION");
            intentFilter.addAction("android.media.VOLUME_CHANGED_ACTION");
            intentFilter.addAction("android.media.STREAM_MUTE_CHANGED_ACTION");
            Unit unit = Unit.INSTANCE;
            context.registerReceiver(r3, intentFilter);
            final Pair<Context, AudioManager> pair = this.$this_activeDeviceChanges;
            Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.controller.device.WifiDisplayDeviceController$Companion$activeDeviceChanges$1.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Log.d("WifiDisplayDeviceController", "unregisterReceiver");
                    ((Context) pair.getFirst()).unregisterReceiver(r3);
                    ((AudioManager) pair.getSecond()).unregisterAudioDeviceCallback(r1);
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
