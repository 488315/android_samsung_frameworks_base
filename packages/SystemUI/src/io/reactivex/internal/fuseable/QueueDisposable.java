package io.reactivex.internal.fuseable;

import io.reactivex.disposables.Disposable;

/* loaded from: classes4.dex */
public interface QueueDisposable extends Disposable, SimpleQueue {
    int requestFusion();
}
