package com.android.server.utils.quota;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class Category {
    public static final Category SINGLE_CATEGORY = new Category("SINGLE");
    public final int mHash;
    public final String mName;

    public Category(String str) {
        this.mName = str;
        this.mHash = str.hashCode();
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Category)) {
            return false;
        }
        return this.mName.equals(((Category) obj).mName);
    }

    public final int hashCode() {
        return this.mHash;
    }

    public final String toString() {
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(new StringBuilder("Category{"), this.mName, "}");
    }
}
