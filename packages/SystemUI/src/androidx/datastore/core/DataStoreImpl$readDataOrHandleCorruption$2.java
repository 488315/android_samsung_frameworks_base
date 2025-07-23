package androidx.datastore.core;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class DataStoreImpl$readDataOrHandleCorruption$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ int $preLockVersion;
    Object L$0;
    /* synthetic */ boolean Z$0;
    int label;
    final /* synthetic */ DataStoreImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataStoreImpl$readDataOrHandleCorruption$2(DataStoreImpl dataStoreImpl, int i, Continuation continuation) {
        super(2, continuation);
        this.this$0 = dataStoreImpl;
        this.$preLockVersion = i;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DataStoreImpl$readDataOrHandleCorruption$2 dataStoreImpl$readDataOrHandleCorruption$2 = new DataStoreImpl$readDataOrHandleCorruption$2(this.this$0, this.$preLockVersion, continuation);
        dataStoreImpl$readDataOrHandleCorruption$2.Z$0 = ((Boolean) obj).booleanValue();
        return dataStoreImpl$readDataOrHandleCorruption$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Boolean bool = (Boolean) obj;
        bool.booleanValue();
        return ((DataStoreImpl$readDataOrHandleCorruption$2) create(bool, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0031, code lost:
    
        if (r6 == r0) goto L16;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x005e  */
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
            if (r1 == 0) goto L20
            if (r1 == r3) goto L1a
            if (r1 != r2) goto L12
            java.lang.Object r5 = r5.L$0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L4e
        L12:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L1a:
            boolean r1 = r5.Z$0
            kotlin.ResultKt.throwOnFailure(r6)
            goto L34
        L20:
            kotlin.ResultKt.throwOnFailure(r6)
            boolean r1 = r5.Z$0
            androidx.datastore.core.DataStoreImpl r6 = r5.this$0
            r5.Z$0 = r1
            r5.label = r3
            int r3 = androidx.datastore.core.DataStoreImpl.$r8$clinit
            java.lang.Object r6 = r6.readDataFromFileOrDefault(r5)
            if (r6 != r0) goto L34
            goto L4a
        L34:
            if (r1 == 0) goto L55
            androidx.datastore.core.DataStoreImpl r1 = r5.this$0
            int r3 = androidx.datastore.core.DataStoreImpl.$r8$clinit
            androidx.datastore.core.InterProcessCoordinator r1 = r1.getCoordinator()
            r5.L$0 = r6
            r5.label = r2
            androidx.datastore.core.SingleProcessCoordinator r1 = (androidx.datastore.core.SingleProcessCoordinator) r1
            java.lang.Object r5 = r1.getVersion()
            if (r5 != r0) goto L4b
        L4a:
            return r0
        L4b:
            r4 = r6
            r6 = r5
            r5 = r4
        L4e:
            java.lang.Number r6 = (java.lang.Number) r6
            int r6 = r6.intValue()
            goto L5a
        L55:
            int r5 = r5.$preLockVersion
            r4 = r6
            r6 = r5
            r5 = r4
        L5a:
            androidx.datastore.core.Data r0 = new androidx.datastore.core.Data
            if (r5 == 0) goto L63
            int r1 = r5.hashCode()
            goto L64
        L63:
            r1 = 0
        L64:
            r0.<init>(r5, r1, r6)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$readDataOrHandleCorruption$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
