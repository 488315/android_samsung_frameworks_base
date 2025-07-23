package com.android.systemui.keyguard.data.quickaffordance;

import androidx.lifecycle.LiveData;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class MuteQuickAffordanceConfig$asFlow$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ LiveData $this_asFlow;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MuteQuickAffordanceConfig$asFlow$1(LiveData liveData, Continuation continuation) {
        super(2, continuation);
        this.$this_asFlow = liveData;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MuteQuickAffordanceConfig$asFlow$1 muteQuickAffordanceConfig$asFlow$1 = new MuteQuickAffordanceConfig$asFlow$1(this.$this_asFlow, continuation);
        muteQuickAffordanceConfig$asFlow$1.L$0 = obj;
        return muteQuickAffordanceConfig$asFlow$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MuteQuickAffordanceConfig$asFlow$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0060, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r4, r5) == r0) goto L16;
     */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r6) {
        /*
            r5 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r5.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L24
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r6)
            goto L63
        L10:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L18:
            java.lang.Object r1 = r5.L$1
            androidx.lifecycle.Observer r1 = (androidx.lifecycle.Observer) r1
            java.lang.Object r3 = r5.L$0
            kotlinx.coroutines.channels.ProducerScope r3 = (kotlinx.coroutines.channels.ProducerScope) r3
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4e
        L24:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.Object r6 = r5.L$0
            kotlinx.coroutines.channels.ProducerScope r6 = (kotlinx.coroutines.channels.ProducerScope) r6
            com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$asFlow$1$observer$1 r1 = new com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$asFlow$1$observer$1
            r1.<init>()
            androidx.lifecycle.LiveData r4 = r5.$this_asFlow
            r4.observeForever(r1)
            androidx.lifecycle.LiveData r4 = r5.$this_asFlow
            java.lang.Object r4 = r4.getValue()
            r5.L$0 = r6
            r5.L$1 = r1
            r5.label = r3
            r3 = r6
            kotlinx.coroutines.channels.ChannelCoroutine r3 = (kotlinx.coroutines.channels.ChannelCoroutine) r3
            kotlinx.coroutines.channels.Channel r3 = r3._channel
            java.lang.Object r3 = r3.send(r4, r5)
            if (r3 != r0) goto L4d
            goto L62
        L4d:
            r3 = r6
        L4e:
            androidx.lifecycle.LiveData r6 = r5.$this_asFlow
            com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$asFlow$1$$ExternalSyntheticLambda0 r4 = new com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$asFlow$1$$ExternalSyntheticLambda0
            r4.<init>()
            r6 = 0
            r5.L$0 = r6
            r5.L$1 = r6
            r5.label = r2
            java.lang.Object r5 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r3, r4, r5)
            if (r5 != r0) goto L63
        L62:
            return r0
        L63:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.quickaffordance.MuteQuickAffordanceConfig$asFlow$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
