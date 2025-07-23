package com.google.common.util.concurrent;

import com.google.common.collect.ForwardingObject;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
