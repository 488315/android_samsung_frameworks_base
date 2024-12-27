package com.android.systemui.media.mediaoutput.ext;

import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ResourceString implements CharSequence {
    public final List args;
    public final int resId;

    public ResourceString(int i, List<? extends Object> list) {
        this.resId = i;
        this.args = list;
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ char charAt(int i) {
        return '!';
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResourceString)) {
            return false;
        }
        ResourceString resourceString = (ResourceString) obj;
        return this.resId == resourceString.resId && Intrinsics.areEqual(this.args, resourceString.args);
    }

    public final int hashCode() {
        int hashCode = Integer.hashCode(this.resId) * 31;
        List list = this.args;
        return hashCode + (list == null ? 0 : list.hashCode());
    }

    @Override // java.lang.CharSequence
    public final /* bridge */ int length() {
        return 1;
    }

    @Override // java.lang.CharSequence
    public final CharSequence subSequence(int i, int i2) {
        return "";
    }

    @Override // java.lang.CharSequence
    public final String toString() {
        return "ResourceString(resId=" + this.resId + ", args=" + this.args + ")";
    }

    public /* synthetic */ ResourceString(int i, List list, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : list);
    }
}
