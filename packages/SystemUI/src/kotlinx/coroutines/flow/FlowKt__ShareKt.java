package kotlinx.coroutines.flow;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract /* synthetic */ class FlowKt__ShareKt {
    /* JADX WARN: Code restructure failed: missing block: B:19:0x002e, code lost:
    
        if (r5 == 0) goto L19;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final kotlinx.coroutines.flow.SharingConfig configureSharing$FlowKt__ShareKt(kotlinx.coroutines.flow.Flow r7, int r8) {
        /*
            kotlinx.coroutines.channels.Channel$Factory r0 = kotlinx.coroutines.channels.Channel.Factory
            r0.getClass()
            int r0 = kotlinx.coroutines.channels.Channel.Factory.CHANNEL_DEFAULT_CAPACITY
            if (r8 >= r0) goto La
            goto Lb
        La:
            r0 = r8
        Lb:
            int r0 = r0 - r8
            boolean r1 = r7 instanceof kotlinx.coroutines.flow.internal.ChannelFlow
            if (r1 == 0) goto L3b
            r1 = r7
            kotlinx.coroutines.flow.internal.ChannelFlow r1 = (kotlinx.coroutines.flow.internal.ChannelFlow) r1
            kotlinx.coroutines.flow.Flow r2 = r1.dropChannelOperators()
            if (r2 == 0) goto L3b
            kotlinx.coroutines.flow.SharingConfig r7 = new kotlinx.coroutines.flow.SharingConfig
            r3 = -3
            kotlinx.coroutines.channels.BufferOverflow r4 = r1.onBufferOverflow
            int r5 = r1.capacity
            if (r5 == r3) goto L29
            r3 = -2
            if (r5 == r3) goto L29
            if (r5 == 0) goto L29
            r0 = r5
            goto L35
        L29:
            kotlinx.coroutines.channels.BufferOverflow r3 = kotlinx.coroutines.channels.BufferOverflow.SUSPEND
            r6 = 0
            if (r4 != r3) goto L32
            if (r5 != 0) goto L35
        L30:
            r0 = r6
            goto L35
        L32:
            if (r8 != 0) goto L30
            r0 = 1
        L35:
            kotlin.coroutines.CoroutineContext r8 = r1.context
            r7.<init>(r2, r0, r4, r8)
            return r7
        L3b:
            kotlinx.coroutines.flow.SharingConfig r8 = new kotlinx.coroutines.flow.SharingConfig
            kotlinx.coroutines.channels.BufferOverflow r1 = kotlinx.coroutines.channels.BufferOverflow.SUSPEND
            kotlin.coroutines.EmptyCoroutineContext r2 = kotlin.coroutines.EmptyCoroutineContext.INSTANCE
            r8.<init>(r7, r0, r1, r2)
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__ShareKt.configureSharing$FlowKt__ShareKt(kotlinx.coroutines.flow.Flow, int):kotlinx.coroutines.flow.SharingConfig");
    }
}
