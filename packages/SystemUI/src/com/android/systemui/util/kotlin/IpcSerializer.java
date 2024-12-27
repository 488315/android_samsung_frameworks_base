package com.android.systemui.util.kotlin;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.Channel;
import kotlinx.coroutines.channels.ChannelKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class IpcSerializer {
    public static final int $stable = 8;
    private final Channel channel = ChannelKt.Channel$default(0, null, null, 7);

    /* JADX WARN: Removed duplicated region for block: B:13:0x0051 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x007e  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:18:0x007b -> B:11:0x0047). Please report as a decompilation issue!!! */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object process(kotlin.coroutines.Continuation r7) {
        /*
            r6 = this;
            boolean r0 = r7 instanceof com.android.systemui.util.kotlin.IpcSerializer$process$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.util.kotlin.IpcSerializer$process$1 r0 = (com.android.systemui.util.kotlin.IpcSerializer$process$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.IpcSerializer$process$1 r0 = new com.android.systemui.util.kotlin.IpcSerializer$process$1
            r0.<init>(r6, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3e
            if (r2 == r4) goto L36
            if (r2 != r3) goto L2e
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r6 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L47
        L2e:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L36:
            java.lang.Object r6 = r0.L$0
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r6 = (kotlinx.coroutines.channels.BufferedChannel.BufferedChannelIterator) r6
            kotlin.ResultKt.throwOnFailure(r7)
            goto L52
        L3e:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlinx.coroutines.channels.Channel r6 = r6.channel
            kotlinx.coroutines.channels.BufferedChannel$BufferedChannelIterator r6 = r6.iterator()
        L47:
            r0.L$0 = r6
            r0.label = r4
            java.lang.Object r7 = r6.hasNext(r0)
            if (r7 != r1) goto L52
            return r1
        L52:
            java.lang.Boolean r7 = (java.lang.Boolean) r7
            boolean r7 = r7.booleanValue()
            if (r7 == 0) goto L7e
            java.lang.Object r7 = r6.next()
            kotlin.Pair r7 = (kotlin.Pair) r7
            java.lang.Object r2 = r7.component1()
            kotlinx.coroutines.CompletableDeferred r2 = (kotlinx.coroutines.CompletableDeferred) r2
            java.lang.Object r7 = r7.component2()
            kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            kotlinx.coroutines.CompletableDeferredImpl r2 = (kotlinx.coroutines.CompletableDeferredImpl) r2
            r2.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r5)
            r0.L$0 = r6
            r0.label = r3
            java.lang.Object r7 = r7.join(r0)
            if (r7 != r1) goto L47
            return r1
        L7e:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "Unexpected end of serialization channel"
            java.lang.String r7 = r7.toString()
            r6.<init>(r7)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.IpcSerializer.process(kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x00a4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0096 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final <R> java.lang.Object runSerialized(kotlin.jvm.functions.Function1 r10, kotlin.coroutines.Continuation r11) {
        /*
            r9 = this;
            boolean r0 = r11 instanceof com.android.systemui.util.kotlin.IpcSerializer$runSerialized$1
            if (r0 == 0) goto L13
            r0 = r11
            com.android.systemui.util.kotlin.IpcSerializer$runSerialized$1 r0 = (com.android.systemui.util.kotlin.IpcSerializer$runSerialized$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.util.kotlin.IpcSerializer$runSerialized$1 r0 = new com.android.systemui.util.kotlin.IpcSerializer$runSerialized$1
            r0.<init>(r9, r11)
        L18:
            java.lang.Object r11 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 3
            r5 = 2
            r6 = 1
            if (r2 == 0) goto L57
            if (r2 == r6) goto L47
            if (r2 == r5) goto L3b
            if (r2 != r4) goto L33
            java.lang.Object r9 = r0.L$0
            kotlinx.coroutines.CompletableDeferred r9 = (kotlinx.coroutines.CompletableDeferred) r9
            kotlin.ResultKt.throwOnFailure(r11)
            goto La5
        L33:
            java.lang.IllegalStateException r9 = new java.lang.IllegalStateException
            java.lang.String r10 = "call to 'resume' before 'invoke' with coroutine"
            r9.<init>(r10)
            throw r9
        L3b:
            java.lang.Object r9 = r0.L$1
            kotlinx.coroutines.CompletableDeferred r9 = (kotlinx.coroutines.CompletableDeferred) r9
            java.lang.Object r10 = r0.L$0
            kotlin.jvm.functions.Function1 r10 = (kotlin.jvm.functions.Function1) r10
            kotlin.ResultKt.throwOnFailure(r11)
            goto L98
        L47:
            java.lang.Object r9 = r0.L$2
            kotlinx.coroutines.CompletableDeferred r9 = (kotlinx.coroutines.CompletableDeferred) r9
            java.lang.Object r10 = r0.L$1
            kotlinx.coroutines.CompletableDeferred r10 = (kotlinx.coroutines.CompletableDeferred) r10
            java.lang.Object r2 = r0.L$0
            kotlin.jvm.functions.Function1 r2 = (kotlin.jvm.functions.Function1) r2
            kotlin.ResultKt.throwOnFailure(r11)
            goto L86
        L57:
            kotlin.ResultKt.throwOnFailure(r11)
            kotlin.Unit r11 = kotlin.Unit.INSTANCE
            kotlinx.coroutines.CompletableDeferredImpl r2 = new kotlinx.coroutines.CompletableDeferredImpl
            r2.<init>(r3)
            r2.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r11)
            kotlinx.coroutines.CompletableDeferredImpl r7 = new kotlinx.coroutines.CompletableDeferredImpl
            r7.<init>(r3)
            r7.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r11)
            kotlinx.coroutines.channels.Channel r9 = r9.channel
            kotlin.Pair r11 = new kotlin.Pair
            r11.<init>(r2, r7)
            r0.L$0 = r10
            r0.L$1 = r2
            r0.L$2 = r7
            r0.label = r6
            java.lang.Object r9 = r9.send(r11, r0)
            if (r9 != r1) goto L82
            return r1
        L82:
            r9 = r7
            r8 = r2
            r2 = r10
            r10 = r8
        L86:
            r0.L$0 = r2
            r0.L$1 = r9
            r0.L$2 = r3
            r0.label = r5
            kotlinx.coroutines.CompletableDeferredImpl r10 = (kotlinx.coroutines.CompletableDeferredImpl) r10
            java.lang.Object r10 = r10.awaitInternal(r0)
            if (r10 != r1) goto L97
            return r1
        L97:
            r10 = r2
        L98:
            r0.L$0 = r9
            r0.L$1 = r3
            r0.label = r4
            java.lang.Object r11 = r10.invoke(r0)
            if (r11 != r1) goto La5
            return r1
        La5:
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            kotlinx.coroutines.CompletableDeferredImpl r9 = (kotlinx.coroutines.CompletableDeferredImpl) r9
            r9.makeCompleting$external__kotlinx_coroutines__linux_glibc_common__kotlinx_coroutines_host(r10)
            return r11
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.util.kotlin.IpcSerializer.runSerialized(kotlin.jvm.functions.Function1, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final <R> R runSerializedBlocking(Function1 function1) {
        return (R) BuildersKt.runBlocking(EmptyCoroutineContext.INSTANCE, new IpcSerializer$runSerializedBlocking$1(this, function1, null));
    }
}
