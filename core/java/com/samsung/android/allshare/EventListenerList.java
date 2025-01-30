package com.samsung.android.allshare;

import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: IAppControlAPI.java */
/* loaded from: classes5.dex */
class EventListenerList extends CopyOnWriteArrayList<Object> {
    private static final long serialVersionUID = -5961702296714063231L;

    EventListenerList() {
    }

    @Override // java.util.concurrent.CopyOnWriteArrayList, java.util.List, java.util.Collection
    public boolean add(Object obj) {
        if (indexOf(obj) >= 0) {
            return false;
        }
        return super.add(obj);
    }
}
