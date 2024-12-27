package com.android.server.wm;

import java.util.ArrayList;

class WindowList extends ArrayList {
    public final void addFirst(Object obj) {
        add(0, obj);
    }

    public final Object peekLast() {
        if (size() > 0) {
            return get(size() - 1);
        }
        return null;
    }
}
