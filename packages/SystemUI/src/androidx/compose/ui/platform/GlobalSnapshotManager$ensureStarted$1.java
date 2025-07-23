package androidx.compose.ui.platform;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.Channel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class GlobalSnapshotManager$ensureStarted$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ Channel $channel;
    Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public GlobalSnapshotManager$ensureStarted$1(Channel channel, Continuation continuation) {
        super(2, continuation);
        this.$channel = channel;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new GlobalSnapshotManager$ensureStarted$1(this.$channel, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((GlobalSnapshotManager$ensureStarted$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0035 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x0053 A[Catch: all -> 0x0015, TRY_LEAVE, TryCatch #0 {all -> 0x0015, blocks: (B:6:0x0011, B:7:0x0036, B:9:0x003e, B:10:0x0029, B:14:0x0053, B:21:0x0024), top: B:2:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:9:0x003e A[Catch: all -> 0x0015, TryCatch #0 {all -> 0x0015, blocks: (B:6:0x0011, B:7:0x0036, B:9:0x003e, B:10:0x0029, B:14:0x0053, B:21:0x0024), top: B:2:0x0005 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:11:0x0033 -> B:7:0x0036). Please report as a decompilation issue!!! */
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
            r2 = 1
            if (r1 == 0) goto L1f
            if (r1 != r2) goto L17
            java.lang.Object r1 = r5.L$1
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r1 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r1
            java.lang.Object r3 = r5.L$0
            kotlinx.coroutines.channels.ReceiveChannel r3 = (kotlinx.coroutines.channels.ReceiveChannel) r3
            kotlin.ResultKt.throwOnFailure(r6)     // Catch: java.lang.Throwable -> L15
            goto L36
        L15:
            r5 = move-exception
            goto L5c
        L17:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L1f:
            kotlin.ResultKt.throwOnFailure(r6)
            kotlinx.coroutines.channels.Channel r3 = r5.$channel
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r6 = r3.iterator()     // Catch: java.lang.Throwable -> L15
            r1 = r6
        L29:
            r5.L$0 = r3     // Catch: java.lang.Throwable -> L15
            r5.L$1 = r1     // Catch: java.lang.Throwable -> L15
            r5.label = r2     // Catch: java.lang.Throwable -> L15
            java.lang.Object r6 = r1.hasNext(r5)     // Catch: java.lang.Throwable -> L15
            if (r6 != r0) goto L36
            return r0
        L36:
            java.lang.Boolean r6 = (java.lang.Boolean) r6     // Catch: java.lang.Throwable -> L15
            boolean r6 = r6.booleanValue()     // Catch: java.lang.Throwable -> L15
            if (r6 == 0) goto L53
            java.lang.Object r6 = r1.next()     // Catch: java.lang.Throwable -> L15
            kotlin.Unit r6 = (kotlin.Unit) r6     // Catch: java.lang.Throwable -> L15
            java.util.concurrent.atomic.AtomicBoolean r6 = androidx.compose.ui.platform.GlobalSnapshotManager.sent     // Catch: java.lang.Throwable -> L15
            r4 = 0
            r6.set(r4)     // Catch: java.lang.Throwable -> L15
            androidx.compose.runtime.snapshots.Snapshot$Companion r6 = androidx.compose.runtime.snapshots.Snapshot.Companion     // Catch: java.lang.Throwable -> L15
            r6.getClass()     // Catch: java.lang.Throwable -> L15
            androidx.compose.runtime.snapshots.Snapshot.Companion.sendApplyNotifications()     // Catch: java.lang.Throwable -> L15
            goto L29
        L53:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L15
            r5 = 0
            r3.cancel(r5)
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        L5c:
            throw r5     // Catch: java.lang.Throwable -> L5d
        L5d:
            r6 = move-exception
            kotlinx.coroutines.channels.ChannelsKt.cancelConsumed(r3, r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.ui.platform.GlobalSnapshotManager$ensureStarted$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
