package androidx.datastore.core;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class FileReadScope$readData$2 extends SuspendLambda implements Function1 {
    Object L$0;
    int label;
    final /* synthetic */ FileReadScope this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FileReadScope$readData$2(FileReadScope fileReadScope, Continuation continuation) {
        super(1, continuation);
        this.this$0 = fileReadScope;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Continuation continuation) {
        return new FileReadScope$readData$2(this.this$0, continuation);
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        return ((FileReadScope$readData$2) create((Continuation) obj)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0041, code lost:
    
        if (r7 == r0) goto L34;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [int] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.io.Closeable] */
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
            r4 = 0
            if (r1 == 0) goto L29
            if (r1 == r3) goto L1f
            if (r1 != r2) goto L17
            java.lang.Object r6 = r6.L$0
            java.io.Closeable r6 = (java.io.Closeable) r6
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L15
            goto L73
        L15:
            r7 = move-exception
            goto L7b
        L17:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L1f:
            java.lang.Object r1 = r6.L$0
            java.io.Closeable r1 = (java.io.Closeable) r1
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: java.lang.Throwable -> L27
            goto L44
        L27:
            r7 = move-exception
            goto L48
        L29:
            kotlin.ResultKt.throwOnFailure(r7)
            java.io.FileInputStream r1 = new java.io.FileInputStream     // Catch: java.io.FileNotFoundException -> L4e
            androidx.datastore.core.FileReadScope r7 = r6.this$0     // Catch: java.io.FileNotFoundException -> L4e
            java.io.File r7 = r7.file     // Catch: java.io.FileNotFoundException -> L4e
            r1.<init>(r7)     // Catch: java.io.FileNotFoundException -> L4e
            androidx.datastore.core.FileReadScope r7 = r6.this$0     // Catch: java.io.FileNotFoundException -> L4e
            androidx.datastore.core.Serializer r7 = r7.serializer     // Catch: java.lang.Throwable -> L27
            r6.L$0 = r1     // Catch: java.lang.Throwable -> L27
            r6.label = r3     // Catch: java.lang.Throwable -> L27
            java.lang.Object r7 = r7.readFrom(r1)     // Catch: java.lang.Throwable -> L27
            if (r7 != r0) goto L44
            goto L6f
        L44:
            kotlin.io.CloseableKt.closeFinally(r1, r4)     // Catch: java.io.FileNotFoundException -> L4e
            return r7
        L48:
            throw r7     // Catch: java.lang.Throwable -> L49
        L49:
            r3 = move-exception
            kotlin.io.CloseableKt.closeFinally(r1, r7)     // Catch: java.io.FileNotFoundException -> L4e
            throw r3     // Catch: java.io.FileNotFoundException -> L4e
        L4e:
            androidx.datastore.core.FileReadScope r7 = r6.this$0
            java.io.File r7 = r7.file
            boolean r7 = r7.exists()
            if (r7 == 0) goto L81
            java.io.FileInputStream r7 = new java.io.FileInputStream
            androidx.datastore.core.FileReadScope r1 = r6.this$0
            java.io.File r1 = r1.file
            r7.<init>(r1)
            androidx.datastore.core.FileReadScope r1 = r6.this$0
            androidx.datastore.core.Serializer r1 = r1.serializer     // Catch: java.lang.Throwable -> L77
            r6.L$0 = r7     // Catch: java.lang.Throwable -> L77
            r6.label = r2     // Catch: java.lang.Throwable -> L77
            java.lang.Object r6 = r1.readFrom(r7)     // Catch: java.lang.Throwable -> L77
            if (r6 != r0) goto L70
        L6f:
            return r0
        L70:
            r5 = r7
            r7 = r6
            r6 = r5
        L73:
            kotlin.io.CloseableKt.closeFinally(r6, r4)
            goto L89
        L77:
            r6 = move-exception
            r5 = r7
            r7 = r6
            r6 = r5
        L7b:
            throw r7     // Catch: java.lang.Throwable -> L7c
        L7c:
            r0 = move-exception
            kotlin.io.CloseableKt.closeFinally(r6, r7)
            throw r0
        L81:
            androidx.datastore.core.FileReadScope r6 = r6.this$0
            androidx.datastore.core.Serializer r6 = r6.serializer
            java.lang.Object r7 = r6.getDefaultValue()
        L89:
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.FileReadScope$readData$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
