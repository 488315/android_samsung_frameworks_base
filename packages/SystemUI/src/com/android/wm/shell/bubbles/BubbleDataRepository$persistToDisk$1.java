package com.android.wm.shell.bubbles;

import android.util.SparseArray;
import com.android.wm.shell.bubbles.storage.BubbleEntity;
import java.util.List;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
final class BubbleDataRepository$persistToDisk$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SparseArray<List<BubbleEntity>> $entitiesByUser;
    final /* synthetic */ Job $prev;
    int label;
    final /* synthetic */ BubbleDataRepository this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BubbleDataRepository$persistToDisk$1(Job job, BubbleDataRepository bubbleDataRepository, SparseArray<List<BubbleEntity>> sparseArray, Continuation continuation) {
        super(2, continuation);
        this.$prev = job;
        this.this$0 = bubbleDataRepository;
        this.$entitiesByUser = sparseArray;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new BubbleDataRepository$persistToDisk$1(this.$prev, this.this$0, this.$entitiesByUser, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((BubbleDataRepository$persistToDisk$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0032, code lost:
    
        if (kotlinx.coroutines.YieldKt.yield(r4) == r0) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0034, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0029, code lost:
    
        if (kotlinx.coroutines.JobKt.cancelAndJoin(r5, r4) == r0) goto L17;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r5) {
        /*
            r4 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r4.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L1c
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r5)
            goto L35
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L2c
        L1c:
            kotlin.ResultKt.throwOnFailure(r5)
            kotlinx.coroutines.Job r5 = r4.$prev
            if (r5 == 0) goto L2c
            r4.label = r3
            java.lang.Object r5 = kotlinx.coroutines.JobKt.cancelAndJoin(r5, r4)
            if (r5 != r0) goto L2c
            goto L34
        L2c:
            r4.label = r2
            java.lang.Object r5 = kotlinx.coroutines.YieldKt.yield(r4)
            if (r5 != r0) goto L35
        L34:
            return r0
        L35:
            com.android.wm.shell.bubbles.BubbleDataRepository r5 = r4.this$0
            com.android.wm.shell.bubbles.storage.BubblePersistentRepository r5 = r5.persistentRepository
            android.util.SparseArray<java.util.List<com.android.wm.shell.bubbles.storage.BubbleEntity>> r4 = r4.$entitiesByUser
            android.util.AtomicFile r0 = r5.bubbleFile
            monitor-enter(r0)
            android.util.AtomicFile r1 = r5.bubbleFile     // Catch: java.lang.Throwable -> L51 java.io.IOException -> L64
            java.io.FileOutputStream r1 = r1.startWrite()     // Catch: java.lang.Throwable -> L51 java.io.IOException -> L64
            r1.getClass()     // Catch: java.lang.Throwable -> L51 java.io.IOException -> L64
            com.android.wm.shell.bubbles.storage.BubbleXmlHelperKt.writeXml(r1, r4)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            android.util.AtomicFile r4 = r5.bubbleFile     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            r4.finishWrite(r1)     // Catch: java.lang.Throwable -> L51 java.lang.Exception -> L53
            monitor-exit(r0)
            goto L6d
        L51:
            r4 = move-exception
            goto L70
        L53:
            r4 = move-exception
            java.lang.String r2 = "BubblePersistentRepository"
            java.lang.String r3 = "Failed to save bubble file, restoring backup"
            android.util.Log.e(r2, r3, r4)     // Catch: java.lang.Throwable -> L51
            android.util.AtomicFile r4 = r5.bubbleFile     // Catch: java.lang.Throwable -> L51
            r4.failWrite(r1)     // Catch: java.lang.Throwable -> L51
            kotlin.Unit r4 = kotlin.Unit.INSTANCE     // Catch: java.lang.Throwable -> L51
            monitor-exit(r0)
            goto L6d
        L64:
            r4 = move-exception
            java.lang.String r5 = "BubblePersistentRepository"
            java.lang.String r1 = "Failed to save bubble file"
            android.util.Log.e(r5, r1, r4)     // Catch: java.lang.Throwable -> L51
            monitor-exit(r0)
        L6d:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        L70:
            monitor-exit(r0)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.bubbles.BubbleDataRepository$persistToDisk$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
