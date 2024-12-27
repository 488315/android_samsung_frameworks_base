package com.android.server.location.contexthub;

import java.util.concurrent.ConcurrentLinkedDeque;

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
