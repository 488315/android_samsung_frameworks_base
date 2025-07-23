package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.Network;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineContextKt;
import kotlinx.coroutines.CoroutineName;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobImpl;
import kotlinx.coroutines.JobKt;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class KairosNetworkKt {
    public static RootKairosNetwork launchKairosNetwork$default(CoroutineScope coroutineScope) {
        CoroutineContext newCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, EmptyCoroutineContext.INSTANCE);
        ContextScope CoroutineScope = CoroutineScopeKt.CoroutineScope(newCoroutineContext.plus(new JobImpl((Job) newCoroutineContext.get(Job.Key))));
        Network network = new Network(CoroutineScope);
        BuildersKt.launch$default(CoroutineScope, new CoroutineName("launchKairosNetwork scheduler"), null, new KairosNetworkKt$launchKairosNetwork$1(network, null), 2);
        return new RootKairosNetwork(network, CoroutineScope, JobKt.getJob(CoroutineScope.coroutineContext));
    }
}
