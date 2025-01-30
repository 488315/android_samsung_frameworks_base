package io.reactivex.internal.fuseable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public interface SimpleQueue {
    void clear();

    boolean isEmpty();

    boolean offer(Object obj);

    Object poll();
}
