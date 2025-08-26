package com.android.systemui.media.mediaoutput.controller.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import com.android.systemui.media.mediaoutput.ext.BundleExtKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class SmartMirroringDeviceController$Companion$castDeviceStateChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Context $this_castDeviceStateChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SmartMirroringDeviceController$Companion$castDeviceStateChanges$1(Context context, Continuation continuation) {
        super(2, continuation);
        this.$this_castDeviceStateChanges = context;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        SmartMirroringDeviceController$Companion$castDeviceStateChanges$1 smartMirroringDeviceController$Companion$castDeviceStateChanges$1 = new SmartMirroringDeviceController$Companion$castDeviceStateChanges$1(this.$this_castDeviceStateChanges, continuation);
        smartMirroringDeviceController$Companion$castDeviceStateChanges$1.L$0 = obj;
        return smartMirroringDeviceController$Companion$castDeviceStateChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((SmartMirroringDeviceController$Companion$castDeviceStateChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.media.mediaoutput.controller.device.SmartMirroringDeviceController$Companion$castDeviceStateChanges$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    Bundle extras = intent.getExtras();
                    Log.d("SmartMirroringDeviceController", "onReceive() - " + intent + ", " + (extras != null ? BundleExtKt.getSerialize(extras) : null));
                    if (!intent.hasExtra("com.samsung.android.bluetooth.cast.extra.STATE")) {
                        intent = null;
                    }
                    if (intent != null) {
                        int intExtra = intent.getIntExtra("com.samsung.android.bluetooth.cast.extra.STATE", 0);
                        ProducerScope producerScope2 = producerScope;
                        BuildersKt.launch$default(producerScope2, null, null, new SmartMirroringDeviceController$Companion$castDeviceStateChanges$1$receiver$1$onReceive$2$1(producerScope2, intExtra, null), 3);
                    }
                }
            };
            this.$this_castDeviceStateChanges.registerReceiver(broadcastReceiver, new IntentFilter("com.samsung.android.bluetooth.audiocast.action.device.CONNECTION_STATE_CHANGED"));
            SmartMirroringDeviceController$$ExternalSyntheticLambda0 smartMirroringDeviceController$$ExternalSyntheticLambda0 = new SmartMirroringDeviceController$$ExternalSyntheticLambda0(1, this.$this_castDeviceStateChanges, broadcastReceiver);
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, smartMirroringDeviceController$$ExternalSyntheticLambda0, this) == coroutineSingletons) {
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
