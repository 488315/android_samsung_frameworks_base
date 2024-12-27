package com.android.server.location.contexthub;

import java.util.concurrent.ConcurrentLinkedDeque;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public class ConcurrentLinkedEvictingDeque extends ConcurrentLinkedDeque {
    private int mSize = 20;

    @Override // java.util.concurrent.ConcurrentLinkedDeque, java.util.AbstractCollection,
              // java.util.Collection, java.util.Deque, java.util.Queue
    public final boolean add(Object obj) {
        boolean add;
        synchronized (this) {
            try {
                if (size() == this.mSize) {
                    poll();
                }
                add = super.add(obj);
            } catch (Throwable th) {
                throw th;
            }
        }
        return add;
    }
}
