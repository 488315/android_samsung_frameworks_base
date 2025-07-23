package androidx.datastore.core;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DataStoreImpl$readState$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ boolean $requireLock;
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$readState$2(DataStoreImpl dataStoreImpl, boolean z, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
        this.$requireLock = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new DataStoreImpl$readState$2(this.this$0, this.$requireLock, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataStoreImpl$readState$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0049, code lost:
    
        if (r5 == r0) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:15:0x004b, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x003c, code lost:
    
        if (r5.readAndInitOrPropagateAndThrowFailure(r4) == r0) goto L20;
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
            goto L4c
        L10:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L18:
            kotlin.ResultKt.throwOnFailure(r5)     // Catch: java.lang.Throwable -> L4f
            goto L3f
        L1c:
            kotlin.ResultKt.throwOnFailure(r5)
            androidx.datastore.core.DataStoreImpl r5 = r4.this$0
            androidx.datastore.core.DataStoreInMemoryCache r5 = r5.inMemoryCache
            androidx.datastore.core.State r5 = r5.getCurrentState()
            boolean r5 = r5 instanceof androidx.datastore.core.Final
            if (r5 == 0) goto L34
            androidx.datastore.core.DataStoreImpl r4 = r4.this$0
            androidx.datastore.core.DataStoreInMemoryCache r4 = r4.inMemoryCache
            androidx.datastore.core.State r4 = r4.getCurrentState()
            return r4
        L34:
            androidx.datastore.core.DataStoreImpl r5 = r4.this$0     // Catch: java.lang.Throwable -> L4f
            r4.label = r3     // Catch: java.lang.Throwable -> L4f
            java.lang.Object r5 = r5.readAndInitOrPropagateAndThrowFailure(r4)     // Catch: java.lang.Throwable -> L4f
            if (r5 != r0) goto L3f
            goto L4b
        L3f:
            androidx.datastore.core.DataStoreImpl r5 = r4.this$0
            boolean r1 = r4.$requireLock
            r4.label = r2
            java.lang.Object r5 = androidx.datastore.core.DataStoreImpl.access$readDataAndUpdateCache(r5, r1, r4)
            if (r5 != r0) goto L4c
        L4b:
            return r0
        L4c:
            androidx.datastore.core.State r5 = (androidx.datastore.core.State) r5
            return r5
        L4f:
            r4 = move-exception
            androidx.datastore.core.ReadException r5 = new androidx.datastore.core.ReadException
            r0 = -1
            r5.<init>(r4, r0)
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$readState$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
