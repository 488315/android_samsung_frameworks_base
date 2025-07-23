package com.android.systemui.statusbar.phone.ongoingactivity;

import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HistoryDumpList extends ConcurrentLinkedQueue<String> {
    private final int MAX_SIZE;

    public HistoryDumpList(int i) {
        this.MAX_SIZE = i;
    }

    @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean contains(Object obj) {
        if (obj == null ? true : obj instanceof String) {
            return super.contains((String) obj);
        }
        return false;
    }

    @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.AbstractCollection, java.util.Collection
    public final /* bridge */ boolean remove(Object obj) {
        if (obj == null ? true : obj instanceof String) {
            return super.remove((String) obj);
        }
        return false;
    }

    @Override // java.util.concurrent.ConcurrentLinkedQueue, java.util.AbstractQueue, java.util.AbstractCollection, java.util.Collection, java.util.Queue
    public final boolean add(String str) {
        if (super.size() > this.MAX_SIZE) {
            poll();
        }
        return offer(new SimpleDateFormat("MM-dd HH:mm:ss.SSS").format(Long.valueOf(System.currentTimeMillis())) + " | " + str);
    }
}
