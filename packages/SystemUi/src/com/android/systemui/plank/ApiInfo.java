package com.android.systemui.plank;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ApiInfo {
    public final String name;
    public final long timestamp;

    public ApiInfo(String str, long j) {
        this.name = str;
        this.timestamp = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ApiInfo)) {
            return false;
        }
        ApiInfo apiInfo = (ApiInfo) obj;
        return Intrinsics.areEqual(this.name, apiInfo.name) && this.timestamp == apiInfo.timestamp;
    }

    public final int hashCode() {
        return Long.hashCode(this.timestamp) + (this.name.hashCode() * 31);
    }

    public final String toString() {
        return "ApiInfo(name=" + this.name + ", timestamp=" + this.timestamp + ")";
    }
}
