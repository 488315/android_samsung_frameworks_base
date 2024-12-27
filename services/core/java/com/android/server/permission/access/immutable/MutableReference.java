package com.android.server.permission.access.immutable;

import com.android.server.permission.jarjar.kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MutableReference {
    public Immutable immutable;
    public Immutable mutable;

    public MutableReference(Immutable immutable, Immutable immutable2) {
        this.immutable = immutable;
        this.mutable = immutable2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!MutableReference.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull("null cannot be cast to non-null type com.android.server.permission.access.immutable.MutableReference<*, *>", obj);
        return Intrinsics.areEqual(this.immutable, ((MutableReference) obj).immutable);
    }

    public final int hashCode() {
        return this.immutable.hashCode();
    }

    public final Immutable mutate() {
        Immutable immutable = this.mutable;
        if (immutable != null) {
            return immutable;
        }
        Immutable immutable2 = (Immutable) this.immutable.toMutable();
        this.immutable = immutable2;
        this.mutable = immutable2;
        return immutable2;
    }

    public final MutableReference toImmutable() {
        return new MutableReference(this.immutable, null);
    }
}
