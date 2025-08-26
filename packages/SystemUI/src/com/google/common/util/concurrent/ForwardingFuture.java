package com.google.common.util.concurrent;

import com.google.common.collect.ForwardingObject;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* loaded from: classes4.dex */
public abstract class ForwardingFuture extends ForwardingObject implements Future {
    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        return delegate().cancel(z);
    }

    @Override // com.google.common.collect.ForwardingObject
    public abstract ListenableFuture delegate();

    @Override // java.util.concurrent.Future
    public final Object get() {
        return delegate().get();
    }

    @Override // java.util.concurrent.Future
    public final boolean isCancelled() {
        return delegate().isCancelled();
    }

    @Override // java.util.concurrent.Future
    public final boolean isDone() {
        return delegate().isDone();
    }

    @Override // java.util.concurrent.Future
    public final Object get(long j, TimeUnit timeUnit) {
        return delegate().get(j, timeUnit);
    }
}
