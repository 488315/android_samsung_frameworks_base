package io.reactivex.internal.fuseable;

/* loaded from: classes4.dex */
public interface SimpleQueue {
    void clear();

    boolean isEmpty();

    boolean offer(Object obj);

    Object poll();
}
