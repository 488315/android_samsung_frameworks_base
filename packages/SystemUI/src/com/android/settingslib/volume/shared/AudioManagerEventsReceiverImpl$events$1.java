package com.android.settingslib.volume.shared;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.Iterator;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes.dex */
final class AudioManagerEventsReceiverImpl$events$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ AudioManagerEventsReceiverImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AudioManagerEventsReceiverImpl$events$1(AudioManagerEventsReceiverImpl audioManagerEventsReceiverImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = audioManagerEventsReceiverImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        AudioManagerEventsReceiverImpl$events$1 audioManagerEventsReceiverImpl$events$1 = new AudioManagerEventsReceiverImpl$events$1(this.this$0, continuation);
        audioManagerEventsReceiverImpl$events$1.L$0 = obj;
        return audioManagerEventsReceiverImpl$events$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((AudioManagerEventsReceiverImpl$events$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [android.content.BroadcastReceiver, com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$events$1$receiver$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new BroadcastReceiver() { // from class: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$events$1$receiver$1
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context, Intent intent) {
                    ProducerScope producerScope2 = producerScope;
                    BuildersKt.launch$default(producerScope2, null, null, new AudioManagerEventsReceiverImpl$events$1$receiver$1$onReceive$1(producerScope2, intent, null), 3);
                }
            };
            Context context = this.this$0.context;
            IntentFilter intentFilter = new IntentFilter();
            this.this$0.getClass();
            Iterator it = ArraysKt___ArraysKt.toSet(new String[]{"android.media.STREAM_MUTE_CHANGED_ACTION", "android.media.MASTER_MUTE_CHANGED_ACTION", "android.media.VOLUME_CHANGED_ACTION", "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", "android.media.STREAM_DEVICES_CHANGED_ACTION", "android.media.VOLUME_CHANGED_ACTION"}).iterator();
            while (it.hasNext()) {
                intentFilter.addAction((String) it.next());
            }
            Unit unit = Unit.INSTANCE;
            context.registerReceiver(r1, intentFilter);
            final AudioManagerEventsReceiverImpl audioManagerEventsReceiverImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$events$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    audioManagerEventsReceiverImpl.context.unregisterReceiver(r1);
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
