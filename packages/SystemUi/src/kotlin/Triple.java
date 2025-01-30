package kotlin;

import java.io.Serializable;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class Triple<A, B, C> implements Serializable {
    private final A first;
    private final B second;
    private final C third;

    public Triple(A a, B b, C c) {
        this.first = a;
        this.second = b;
        this.third = c;
    }

    public final Object component1() {
        return this.first;
    }

    public final Object component2() {
        return this.second;
    }

    public final Object component3() {
        return this.third;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Triple)) {
            return false;
        }
        Triple triple = (Triple) obj;
        return Intrinsics.areEqual(this.first, triple.first) && Intrinsics.areEqual(this.second, triple.second) && Intrinsics.areEqual(this.third, triple.third);
    }

    public final Object getFirst() {
        return this.first;
    }

    public final Object getSecond() {
        return this.second;
    }

    public final Object getThird() {
        return this.third;
    }

    public final int hashCode() {
        A a = this.first;
        int hashCode = (a == null ? 0 : a.hashCode()) * 31;
        B b = this.second;
        int hashCode2 = (hashCode + (b == null ? 0 : b.hashCode())) * 31;
        C c = this.third;
        return hashCode2 + (c != null ? c.hashCode() : 0);
    }

    public final String toString() {
        return "(" + this.first + ", " + this.second + ", " + this.third + ')';
    }
}
