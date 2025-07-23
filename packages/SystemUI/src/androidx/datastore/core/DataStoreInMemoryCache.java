package androidx.datastore.core;

import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DataStoreInMemoryCache {
    public final StateFlowImpl cachedValue = StateFlowKt.MutableStateFlow(UnInitialized.INSTANCE);

    public final State getCurrentState() {
        return (State) this.cachedValue.getValue();
    }

    /* JADX WARN: Code restructure failed: missing block: B:8:0x0021, code lost:
    
        if (r6.version > ((androidx.datastore.core.Data) r2).version) goto L17;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void tryUpdate(androidx.datastore.core.State r6) {
        /*
            r5 = this;
        L0:
            kotlinx.coroutines.flow.StateFlowImpl r0 = r5.cachedValue
            java.lang.Object r1 = r0.getValue()
            r2 = r1
            androidx.datastore.core.State r2 = (androidx.datastore.core.State) r2
            boolean r3 = r2 instanceof androidx.datastore.core.ReadException
            if (r3 != 0) goto L2f
            androidx.datastore.core.UnInitialized r3 = androidx.datastore.core.UnInitialized.INSTANCE
            boolean r3 = kotlin.jvm.internal.Intrinsics.areEqual(r2, r3)
            if (r3 == 0) goto L16
            goto L2f
        L16:
            boolean r3 = r2 instanceof androidx.datastore.core.Data
            if (r3 == 0) goto L24
            int r3 = r6.version
            r4 = r2
            androidx.datastore.core.Data r4 = (androidx.datastore.core.Data) r4
            int r4 = r4.version
            if (r3 <= r4) goto L30
            goto L2f
        L24:
            boolean r3 = r2 instanceof androidx.datastore.core.Final
            if (r3 == 0) goto L29
            goto L30
        L29:
            kotlin.NoWhenBranchMatchedException r5 = new kotlin.NoWhenBranchMatchedException
            r5.<init>()
            throw r5
        L2f:
            r2 = r6
        L30:
            boolean r0 = r0.compareAndSet(r1, r2)
            if (r0 == 0) goto L0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.datastore.core.DataStoreInMemoryCache.tryUpdate(androidx.datastore.core.State):void");
    }
}
