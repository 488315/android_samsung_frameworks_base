package com.android.systemui.coverlauncher.utils.badge;

import java.io.Serializable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class NotificationItem implements Serializable {
    public int count;
    public String info;
    public String key;

    public NotificationItem(String str, String str2, int i) {
        this.key = str;
        this.info = str2;
        this.count = Math.max(1, i);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof NotificationItem) {
            return ((NotificationItem) obj).key.equals(this.key);
        }
        return false;
    }

    public final String toString() {
        return "key=" + this.key + ", count=" + this.count;
    }
}
