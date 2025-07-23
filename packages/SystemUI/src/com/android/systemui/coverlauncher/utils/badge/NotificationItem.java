package com.android.systemui.coverlauncher.utils.badge;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
