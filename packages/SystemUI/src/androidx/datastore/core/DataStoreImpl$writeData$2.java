package androidx.datastore.core;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$IntRef;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DataStoreImpl$writeData$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Object $newData;
    final /* synthetic */ Ref$IntRef $newVersion;
    final /* synthetic */ boolean $updateCache;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$writeData$2(Ref$IntRef ref$IntRef, DataStoreImpl dataStoreImpl, Object obj, boolean z, Continuation continuation) {
        super(2, continuation);
        this.$newVersion = ref$IntRef;
        this.this$0 = dataStoreImpl;
        this.$newData = obj;
        this.$updateCache = z;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DataStoreImpl$writeData$2 dataStoreImpl$writeData$2 = new DataStoreImpl$writeData$2(this.$newVersion, this.this$0, this.$newData, this.$updateCache, continuation);
        dataStoreImpl$writeData$2.L$0 = obj;
        return dataStoreImpl$writeData$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DataStoreImpl$writeData$2) create((WriteScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:20:0x0064, code lost:
    
        if (r3.writeData(r7, r6) == r0) goto L16;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L24
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r7)
            goto L67
        L10:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L18:
            java.lang.Object r1 = r6.L$1
            kotlin.jvm.internal.Ref$IntRef r1 = (kotlin.jvm.internal.Ref$IntRef) r1
            java.lang.Object r3 = r6.L$0
            androidx.datastore.core.WriteScope r3 = (androidx.datastore.core.WriteScope) r3
            kotlin.ResultKt.throwOnFailure(r7)
            goto L4f
        L24:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            androidx.datastore.core.WriteScope r7 = (androidx.datastore.core.WriteScope) r7
            kotlin.jvm.internal.Ref$IntRef r1 = r6.$newVersion
            androidx.datastore.core.DataStoreImpl r4 = r6.this$0
            int r5 = androidx.datastore.core.DataStoreImpl.$r8$clinit
            androidx.datastore.core.InterProcessCoordinator r4 = r4.getCoordinator()
            r6.L$0 = r7
            r6.L$1 = r1
            r6.label = r3
            androidx.datastore.core.SingleProcessCoordinator r4 = (androidx.datastore.core.SingleProcessCoordinator) r4
            androidx.datastore.core.AtomicInt r3 = r4.version
            java.util.concurrent.atomic.AtomicInteger r3 = r3.delegate
            int r3 = r3.incrementAndGet()
            java.lang.Integer r4 = new java.lang.Integer
            r4.<init>(r3)
            if (r4 != r0) goto L4d
            goto L66
        L4d:
            r3 = r7
            r7 = r4
        L4f:
            java.lang.Number r7 = (java.lang.Number) r7
            int r7 = r7.intValue()
            r1.element = r7
            java.lang.Object r7 = r6.$newData
            r1 = 0
            r6.L$0 = r1
            r6.L$1 = r1
            r6.label = r2
            java.lang.Object r7 = r3.writeData(r7, r6)
            if (r7 != r0) goto L67
        L66:
            return r0
        L67:
            boolean r7 = r6.$updateCache
            if (r7 == 0) goto L85
            androidx.datastore.core.DataStoreImpl r7 = r6.this$0
            androidx.datastore.core.DataStoreInMemoryCache r7 = r7.inMemoryCache
            androidx.datastore.core.Data r0 = new androidx.datastore.core.Data
            java.lang.Object r1 = r6.$newData
            if (r1 == 0) goto L7a
            int r2 = r1.hashCode()
            goto L7b
        L7a:
            r2 = 0
        L7b:
            kotlin.jvm.internal.Ref$IntRef r6 = r6.$newVersion
            int r6 = r6.element
            r0.<init>(r1, r2, r6)
            r7.tryUpdate(r0)
        L85:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$writeData$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
