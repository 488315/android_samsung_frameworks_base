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

/* loaded from: classes2.dex */
public abstract class KairosNetworkKt {
    public static RootKairosNetwork launchKairosNetwork$default(CoroutineScope coroutineScope) {
        CoroutineContext coroutineContextNewCoroutineContext = CoroutineContextKt.newCoroutineContext(coroutineScope, EmptyCoroutineContext.INSTANCE);
        ContextScope contextScopeCoroutineScope = CoroutineScopeKt.CoroutineScope(coroutineContextNewCoroutineContext.plus(new JobImpl((Job) coroutineContextNewCoroutineContext.get(Job.Key))));
        Network network = new Network(contextScopeCoroutineScope);
        BuildersKt.launch$default(contextScopeCoroutineScope, new CoroutineName("launchKairosNetwork scheduler"), null, new KairosNetworkKt$launchKairosNetwork$1(network, null), 2);
        return new RootKairosNetwork(network, contextScopeCoroutineScope, JobKt.getJob(contextScopeCoroutineScope.coroutineContext));
    }
}
