package androidx.datastore.core;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DataStoreImpl$transformAndWrite$2 extends SuspendLambda implements Function1 {
    final /* synthetic */ CoroutineContext $callerContext;
    final /* synthetic */ Function2 $transform;
    Object L$0;
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$transformAndWrite$2(DataStoreImpl dataStoreImpl, CoroutineContext coroutineContext, Function2 function2, Continuation continuation) {
        super(1, continuation);
        this.this$0 = dataStoreImpl;
        this.$callerContext = coroutineContext;
        this.$transform = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new DataStoreImpl$transformAndWrite$2(this.this$0, this.$callerContext, this.$transform, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((DataStoreImpl$transformAndWrite$2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x004c, code lost:
    
        if (r9 == r0) goto L29;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0034, code lost:
    
        if (r9 == r0) goto L29;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r9) {
        /*
            r8 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r8.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L29
            if (r1 == r4) goto L25
            if (r1 == r3) goto L1d
            if (r1 != r2) goto L15
            java.lang.Object r8 = r8.L$0
            kotlin.ResultKt.throwOnFailure(r9)
            return r8
        L15:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L1d:
            java.lang.Object r1 = r8.L$0
            androidx.datastore.core.Data r1 = (androidx.datastore.core.Data) r1
            kotlin.ResultKt.throwOnFailure(r9)
            goto L4f
        L25:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L37
        L29:
            kotlin.ResultKt.throwOnFailure(r9)
            androidx.datastore.core.DataStoreImpl r9 = r8.this$0
            r8.label = r4
            java.lang.Object r9 = androidx.datastore.core.DataStoreImpl.access$readDataOrHandleCorruption(r9, r4, r8)
            if (r9 != r0) goto L37
            goto L71
        L37:
            r1 = r9
            androidx.datastore.core.Data r1 = (androidx.datastore.core.Data) r1
            kotlin.coroutines.CoroutineContext r9 = r8.$callerContext
            androidx.datastore.core.DataStoreImpl$transformAndWrite$2$newData$1 r5 = new androidx.datastore.core.DataStoreImpl$transformAndWrite$2$newData$1
            kotlin.jvm.functions.Function2 r6 = r8.$transform
            r7 = 0
            r5.<init>(r6, r1, r7)
            r8.L$0 = r1
            r8.label = r3
            java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r9, r5, r8)
            if (r9 != r0) goto L4f
            goto L71
        L4f:
            java.lang.Object r3 = r1.value
            if (r3 == 0) goto L58
            int r3 = r3.hashCode()
            goto L59
        L58:
            r3 = 0
        L59:
            int r5 = r1.hashCode
            if (r3 != r5) goto L73
            java.lang.Object r1 = r1.value
            boolean r1 = kotlin.jvm.internal.Intrinsics.areEqual(r1, r9)
            if (r1 != 0) goto L72
            androidx.datastore.core.DataStoreImpl r1 = r8.this$0
            r8.L$0 = r9
            r8.label = r2
            java.lang.Object r8 = r1.writeData$datastore_core_release(r9, r4, r8)
            if (r8 != r0) goto L72
        L71:
            return r0
        L72:
            return r9
        L73:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "Data in DataStore was mutated but DataStore is only compatible with Immutable types."
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$transformAndWrite$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
