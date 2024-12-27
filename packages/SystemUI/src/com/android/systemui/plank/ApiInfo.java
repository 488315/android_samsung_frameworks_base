package com.android.systemui.plank;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
