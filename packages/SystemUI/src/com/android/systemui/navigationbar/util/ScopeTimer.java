package com.android.systemui.navigationbar.util;

import kotlin.jvm.functions.Function0;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Job;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScopeTimer {
    public Job job;
    public final CoroutineScope scope;

    public ScopeTimer(CoroutineScope coroutineScope) {
        this.scope = coroutineScope;
    }

    public final void cancel() {
        Job job = this.job;
        if (job == null || !job.isActive()) {
            return;
        }
        Job job2 = this.job;
        if (job2 != null) {
            job2.cancel(null);
        }
        this.job = null;
    }

    public final void start(long j, Function0 function0) {
        cancel();
        this.job = BuildersKt.launch$default(this.scope, null, null, new ScopeTimer$start$1(j, function0, this, null), 3);
    }
}
