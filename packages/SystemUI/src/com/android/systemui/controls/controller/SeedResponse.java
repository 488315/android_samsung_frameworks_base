package com.android.systemui.controls.controller;

import kotlin.jvm.internal.Intrinsics;

public final class SeedResponse {
    public final boolean accepted;
    public final String packageName;

    public SeedResponse(String str, boolean z) {
        this.packageName = str;
        this.accepted = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SeedResponse)) {
            return false;
        }
        SeedResponse seedResponse = (SeedResponse) obj;
        return Intrinsics.areEqual(this.packageName, seedResponse.packageName) && this.accepted == seedResponse.accepted;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.accepted) + (this.packageName.hashCode() * 31);
    }

    public final String toString() {
        return "SeedResponse(packageName=" + this.packageName + ", accepted=" + this.accepted + ")";
    }
}
