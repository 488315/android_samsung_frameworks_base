package com.android.server.usage;

import android.util.ArrayMap;
import android.util.ArraySet;

public final class UserBroadcastEvents {
    public ArrayMap mBroadcastEvents;

    public final void clear(int i) {
        for (int size = this.mBroadcastEvents.size() - 1; size >= 0; size--) {
            ArraySet arraySet = (ArraySet) this.mBroadcastEvents.valueAt(size);
            for (int size2 = arraySet.size() - 1; size2 >= 0; size2--) {
                if (((BroadcastEvent) arraySet.valueAt(size2)).mSourceUid == i) {
                    arraySet.removeAt(size2);
                }
            }
        }
    }
}
