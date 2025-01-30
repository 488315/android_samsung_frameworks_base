package io.reactivex.internal.fuseable;

import io.reactivex.disposables.Disposable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface QueueDisposable extends Disposable, SimpleQueue {
    int requestFusion();
}
