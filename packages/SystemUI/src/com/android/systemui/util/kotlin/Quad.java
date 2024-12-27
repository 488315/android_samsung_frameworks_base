package com.android.systemui.util.kotlin;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class Quad<A, B, C, D> {
    public static final int $stable = 0;
    private final A first;
    private final D fourth;
    private final B second;
    private final C third;

    public Quad(A a, B b, C c, D d) {
        this.first = a;
        this.second = b;
        this.third = c;
        this.fourth = d;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Quad copy$default(Quad quad, Object obj, Object obj2, Object obj3, Object obj4, int i, Object obj5) {
        if ((i & 1) != 0) {
            obj = quad.first;
        }
        if ((i & 2) != 0) {
            obj2 = quad.second;
        }
        if ((i & 4) != 0) {
            obj3 = quad.third;
        }
        if ((i & 8) != 0) {
            obj4 = quad.fourth;
        }
        return quad.copy(obj, obj2, obj3, obj4);
    }

    public final A component1() {
        return this.first;
    }

    public final B component2() {
        return this.second;
    }

    public final C component3() {
        return this.third;
    }

    public final D component4() {
        return this.fourth;
    }

    public final Quad<A, B, C, D> copy(A a, B b, C c, D d) {
        return new Quad<>(a, b, c, d);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Quad)) {
            return false;
        }
        Quad quad = (Quad) obj;
        return Intrinsics.areEqual(this.first, quad.first) && Intrinsics.areEqual(this.second, quad.second) && Intrinsics.areEqual(this.third, quad.third) && Intrinsics.areEqual(this.fourth, quad.fourth);
    }

    public final A getFirst() {
        return this.first;
    }

    public final D getFourth() {
        return this.fourth;
    }

    public final B getSecond() {
        return this.second;
    }

    public final C getThird() {
        return this.third;
    }

    public int hashCode() {
        A a = this.first;
        int hashCode = (a == null ? 0 : a.hashCode()) * 31;
        B b = this.second;
        int hashCode2 = (hashCode + (b == null ? 0 : b.hashCode())) * 31;
        C c = this.third;
        int hashCode3 = (hashCode2 + (c == null ? 0 : c.hashCode())) * 31;
        D d = this.fourth;
        return hashCode3 + (d != null ? d.hashCode() : 0);
    }

    public String toString() {
        return "Quad(first=" + this.first + ", second=" + this.second + ", third=" + this.third + ", fourth=" + this.fourth + ")";
    }
}
