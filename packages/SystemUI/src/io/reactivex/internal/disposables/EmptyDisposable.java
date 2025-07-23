package io.reactivex.internal.disposables;

import io.reactivex.internal.fuseable.QueueDisposable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public enum EmptyDisposable implements QueueDisposable {
    INSTANCE,
    /* JADX INFO: Fake field, exist only in values array */
    NEVER;

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean isEmpty() {
        return true;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final boolean offer(Object obj) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final Object poll() {
        return null;
    }

    @Override // io.reactivex.internal.fuseable.QueueDisposable
    public final int requestFusion() {
        return 2;
    }

    @Override // io.reactivex.internal.fuseable.SimpleQueue
    public final void clear() {
    }

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
    }
}
