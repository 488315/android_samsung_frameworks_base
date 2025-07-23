package androidx.datastore.core;

import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.sync.Mutex;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DataStoreImpl$InitDataStore$doRun$initData$1$api$1 {
    public final /* synthetic */ Ref$ObjectRef $currentData;
    public final /* synthetic */ Ref$BooleanRef $initializationComplete;
    public final /* synthetic */ Mutex $updateLock;
    public final /* synthetic */ DataStoreImpl this$0;

    public DataStoreImpl$InitDataStore$doRun$initData$1$api$1(Mutex mutex, Ref$BooleanRef ref$BooleanRef, Ref$ObjectRef<Object> ref$ObjectRef, DataStoreImpl dataStoreImpl) {
        this.$updateLock = mutex;
        this.$initializationComplete = ref$BooleanRef;
        this.$currentData = ref$ObjectRef;
        this.this$0 = dataStoreImpl;
    }

    /* JADX WARN: Code restructure failed: missing block: B:50:0x008f, code lost:
    
        if (r10.lock(r0) == r1) goto L39;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b6 A[Catch: all -> 0x0054, TRY_LEAVE, TryCatch #1 {all -> 0x0054, blocks: (B:27:0x0050, B:28:0x00ae, B:30:0x00b6), top: B:26:0x0050 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x0096 A[Catch: all -> 0x00d2, TRY_LEAVE, TryCatch #0 {all -> 0x00d2, blocks: (B:40:0x0092, B:42:0x0096, B:45:0x00d5, B:46:0x00dc), top: B:39:0x0092 }] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00d5 A[Catch: all -> 0x00d2, TRY_ENTER, TryCatch #0 {all -> 0x00d2, blocks: (B:40:0x0092, B:42:0x0096, B:45:0x00d5, B:46:0x00dc), top: B:39:0x0092 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object updateData(kotlin.jvm.functions.Function2 r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            Method dump skipped, instructions count: 225
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreImpl$InitDataStore$doRun$initData$1$api$1.updateData(kotlin.jvm.functions.Function2, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
