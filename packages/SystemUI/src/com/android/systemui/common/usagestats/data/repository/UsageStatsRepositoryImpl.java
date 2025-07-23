package com.android.systemui.common.usagestats.data.repository;

import android.app.usage.UsageStatsManager;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class UsageStatsRepositoryImpl implements UsageStatsRepository {
    public final CoroutineContext bgContext;
    public final UsageStatsManager usageStatsManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public UsageStatsRepositoryImpl(CoroutineContext coroutineContext, UsageStatsManager usageStatsManager) {
        this.bgContext = coroutineContext;
        this.usageStatsManager = usageStatsManager;
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object queryActivityEvents(com.android.systemui.common.usagestats.data.model.UsageStatsQuery r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r4 = this;
            boolean r0 = r6 instanceof com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$1
            if (r0 == 0) goto L13
            r0 = r6
            com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$1 r0 = (com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$1 r0 = new com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$1
            r0.<init>(r4, r6)
        L18:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 != r3) goto L27
            kotlin.ResultKt.throwOnFailure(r6)
            return r6
        L27:
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2f:
            kotlin.ResultKt.throwOnFailure(r6)
            com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$2 r6 = new com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl$queryActivityEvents$2
            r2 = 0
            r6.<init>(r5, r4, r2)
            r0.label = r3
            kotlin.coroutines.CoroutineContext r4 = r4.bgContext
            java.lang.Object r4 = kotlinx.coroutines.BuildersKt.withContext(r4, r6, r0)
            if (r4 != r1) goto L43
            return r1
        L43:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.common.usagestats.data.repository.UsageStatsRepositoryImpl.queryActivityEvents(com.android.systemui.common.usagestats.data.model.UsageStatsQuery, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
