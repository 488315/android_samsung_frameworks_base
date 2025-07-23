package com.android.settingslib.bluetooth;

import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LocalBluetoothManagerExtKt$headsetAudioModeChanges$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LocalBluetoothManager $this_headsetAudioModeChanges;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalBluetoothManagerExtKt$headsetAudioModeChanges$1(LocalBluetoothManager localBluetoothManager, Continuation continuation) {
        super(2, continuation);
        this.$this_headsetAudioModeChanges = localBluetoothManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LocalBluetoothManagerExtKt$headsetAudioModeChanges$1 localBluetoothManagerExtKt$headsetAudioModeChanges$1 = new LocalBluetoothManagerExtKt$headsetAudioModeChanges$1(this.$this_headsetAudioModeChanges, continuation);
        localBluetoothManagerExtKt$headsetAudioModeChanges$1.L$0 = obj;
        return localBluetoothManagerExtKt$headsetAudioModeChanges$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LocalBluetoothManagerExtKt$headsetAudioModeChanges$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.bluetooth.BluetoothCallback, com.android.settingslib.bluetooth.LocalBluetoothManagerExtKt$headsetAudioModeChanges$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new BluetoothCallback() { // from class: com.android.settingslib.bluetooth.LocalBluetoothManagerExtKt$headsetAudioModeChanges$1$callback$1
                @Override // com.android.settingslib.bluetooth.BluetoothCallback
                public final void onAudioModeChanged() {
                    ProducerScope producerScope2 = ProducerScope.this;
                    BuildersKt.launch$default(producerScope2, null, null, new LocalBluetoothManagerExtKt$headsetAudioModeChanges$1$callback$1$onAudioModeChanged$1(producerScope2, null), 3);
                }
            };
            this.$this_headsetAudioModeChanges.mEventManager.registerCallback(r1);
            final LocalBluetoothManager localBluetoothManager = this.$this_headsetAudioModeChanges;
            Function0 function0 = new Function0() { // from class: com.android.settingslib.bluetooth.LocalBluetoothManagerExtKt$headsetAudioModeChanges$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    LocalBluetoothManager.this.mEventManager.unregisterCallback(r1);
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
