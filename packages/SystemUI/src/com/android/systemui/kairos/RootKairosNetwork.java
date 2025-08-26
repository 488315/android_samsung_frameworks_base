package com.android.systemui.kairos;

import com.android.systemui.kairos.internal.Network;
import java.util.concurrent.CancellationException;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.ChildHandle;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobSupport;

/* loaded from: classes2.dex */
public final class RootKairosNetwork implements Job, KairosNetwork {
    public final /* synthetic */ Job $$delegate_0;
    public final /* synthetic */ LocalNetwork $$delegate_1;

    public RootKairosNetwork(Network network, CoroutineScope coroutineScope, Job job) {
        this.$$delegate_0 = job;
        this.$$delegate_1 = new LocalNetwork(network, coroutineScope, EventsKt.emptyEvents);
    }

    @Override // com.android.systemui.kairos.KairosNetwork
    public final Object activateSpec(Function1 function1, Continuation continuation) {
        return this.$$delegate_1.activateSpec(function1, continuation);
    }

    @Override // kotlinx.coroutines.Job
    public final ChildHandle attachChild(JobSupport jobSupport) {
        return this.$$delegate_0.attachChild(jobSupport);
    }

    @Override // kotlinx.coroutines.Job
    public final void cancel(CancellationException cancellationException) {
        this.$$delegate_0.cancel(cancellationException);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final Object fold(Object obj, Function2 function2) {
        return this.$$delegate_0.fold(obj, function2);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext.Element get(CoroutineContext.Key key) {
        return this.$$delegate_0.get(key);
    }

    @Override // kotlinx.coroutines.Job
    public final CancellationException getCancellationException() {
        return this.$$delegate_0.getCancellationException();
    }

    @Override // kotlin.coroutines.CoroutineContext.Element
    public final CoroutineContext.Key getKey() {
        return this.$$delegate_0.getKey();
    }

    @Override // kotlinx.coroutines.Job
    public final DisposableHandle invokeOnCompletion(Function1 function1) {
        return this.$$delegate_0.invokeOnCompletion(function1);
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isActive() {
        return this.$$delegate_0.isActive();
    }

    @Override // kotlinx.coroutines.Job
    public final boolean isCancelled$1() {
        return this.$$delegate_0.isCancelled$1();
    }

    @Override // kotlinx.coroutines.Job
    public final Object join(ContinuationImpl continuationImpl) {
        return this.$$delegate_0.join(continuationImpl);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext minusKey(CoroutineContext.Key key) {
        return this.$$delegate_0.minusKey(key);
    }

    @Override // kotlin.coroutines.CoroutineContext
    public final CoroutineContext plus(CoroutineContext coroutineContext) {
        return this.$$delegate_0.plus(coroutineContext);
    }

    @Override // kotlinx.coroutines.Job
    public final boolean start() {
        return this.$$delegate_0.start();
    }

    @Override // com.android.systemui.kairos.KairosNetwork
    public final Object transact(Function1 function1, Continuation continuation) {
        return this.$$delegate_1.transact(function1, continuation);
    }

    @Override // kotlinx.coroutines.Job
    public final DisposableHandle invokeOnCompletion(boolean z, boolean z2, Function1 function1) {
        return this.$$delegate_0.invokeOnCompletion(z, z2, function1);
    }
}
