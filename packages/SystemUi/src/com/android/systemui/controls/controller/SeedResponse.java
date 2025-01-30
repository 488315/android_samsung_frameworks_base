package com.android.systemui.controls.controller;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = this.packageName.hashCode() * 31;
        boolean z = this.accepted;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "SeedResponse(packageName=" + this.packageName + ", accepted=" + this.accepted + ")";
    }
}
