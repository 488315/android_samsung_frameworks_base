package com.android.server.wm;

import java.util.ArrayList;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
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
