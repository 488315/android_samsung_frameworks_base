package com.android.server.wm;

import java.util.ArrayList;

/* loaded from: classes3.dex */
class WindowList extends ArrayList {
    public void addFirst(Object obj) {
        add(0, obj);
    }

    public Object peekLast() {
        if (size() > 0) {
            return get(size() - 1);
        }
        return null;
    }

    public Object peekFirst() {
        if (size() > 0) {
            return get(0);
        }
        return null;
    }
}
