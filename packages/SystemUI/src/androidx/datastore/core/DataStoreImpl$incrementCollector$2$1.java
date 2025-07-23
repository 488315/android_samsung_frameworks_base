package androidx.datastore.core;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DataStoreImpl$incrementCollector$2$1 extends SuspendLambda implements Function2 {
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$incrementCollector$2$1(DataStoreImpl dataStoreImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataStoreImpl$incrementCollector$2$1(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataStoreImpl$incrementCollector$2$1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0051, code lost:
    
        if (r5.collect(r1, r4) == r0) goto L18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x0053, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x0030, code lost:
    
        if (r5 == r0) goto L18;
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
            goto L54
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L33
        L1c:
            kotlin.ResultKt.throwOnFailure(r5)
            androidx.datastore.core.DataStoreImpl r5 = r4.this$0
            androidx.datastore.core.DataStoreImpl$InitDataStore r5 = r5.readAndInit
            r4.label = r3
            kotlinx.coroutines.CompletableDeferredImpl r5 = r5.didRun
            java.lang.Object r5 = r5.awaitInternal(r4)
            if (r5 != r0) goto L2e
            goto L30
        L2e:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
        L30:
            if (r5 != r0) goto L33
            goto L53
        L33:
            androidx.datastore.core.DataStoreImpl r5 = r4.this$0
            int r1 = androidx.datastore.core.DataStoreImpl.$r8$clinit
            androidx.datastore.core.InterProcessCoordinator r5 = r5.getCoordinator()
            androidx.datastore.core.SingleProcessCoordinator r5 = (androidx.datastore.core.SingleProcessCoordinator) r5
            kotlinx.coroutines.flow.SafeFlow r5 = r5.updateNotifications
            r1 = -1
            kotlinx.coroutines.flow.Flow r5 = kotlinx.coroutines.flow.FlowKt.buffer$default(r5, r1, r2)
            androidx.datastore.core.DataStoreImpl$incrementCollector$2$1$1 r1 = new androidx.datastore.core.DataStoreImpl$incrementCollector$2$1$1
            androidx.datastore.core.DataStoreImpl r3 = r4.this$0
            r1.<init>()
            r4.label = r2
            java.lang.Object r4 = r5.collect(r1, r4)
            if (r4 != r0) goto L54
        L53:
            return r0
        L54:
            kotlin.Unit r4 = kotlin.Unit.INSTANCE
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$incrementCollector$2$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
