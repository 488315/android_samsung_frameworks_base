package com.android.systemui.coverlauncher.utils.badge;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
