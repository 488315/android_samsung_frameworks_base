package com.android.systemui.coverlauncher.utils.badge;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;

public final class NotificationItem implements Serializable {
    public int count;
    public String info;
    private String key;

    public NotificationItem(String str, String str2, int i) {
        this.key = str;
        this.info = str2;
        this.count = Math.max(1, i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof NotificationItem) {
            return Intrinsics.areEqual(((NotificationItem) obj).key, this.key);
        }
        return false;
    }

    public final String toString() {
        return "key=" + this.key + ", count=" + this.count;
    }
}
