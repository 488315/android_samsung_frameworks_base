package androidx.compose.runtime;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class OpaqueKey {
    public final String key;

    public OpaqueKey(String str) {
        this.key = str;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof OpaqueKey) && Intrinsics.areEqual(this.key, ((OpaqueKey) obj).key);
    }

    public final int hashCode() {
        return this.key.hashCode();
    }

    public final String toString() {
        return OpaqueKey$$ExternalSyntheticOutline0.m(new StringBuilder("OpaqueKey(key="), this.key, ')');
    }
}
