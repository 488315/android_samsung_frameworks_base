package androidx.compose.ui.text.input;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class PlatformImeOptions {
    public final String privateImeOptions;

    public PlatformImeOptions() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof PlatformImeOptions) {
            return Intrinsics.areEqual(this.privateImeOptions, ((PlatformImeOptions) obj).privateImeOptions);
        }
        return false;
    }

    public final int hashCode() {
        String str = this.privateImeOptions;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public final String toString() {
        return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("PlatformImeOptions(privateImeOptions="), this.privateImeOptions, ')');
    }

    public PlatformImeOptions(String str) {
        this.privateImeOptions = str;
    }

    public /* synthetic */ PlatformImeOptions(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }
}
