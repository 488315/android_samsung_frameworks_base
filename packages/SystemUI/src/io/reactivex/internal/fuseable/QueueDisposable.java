package io.reactivex.internal.fuseable;

import io.reactivex.disposables.Disposable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public interface QueueDisposable extends Disposable, SimpleQueue {
    int requestFusion();
}
