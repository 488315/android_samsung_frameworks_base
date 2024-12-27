package com.android.systemui.util.kotlin;

import kotlin.jvm.internal.Intrinsics;

public final class Septuple<A, B, C, D, E, F, G> {
    public static final int $stable = 0;
    private final E fifth;
    private final A first;
    private final D fourth;
    private final B second;
    private final G seventh;
    private final F sixth;
    private final C third;

    public Septuple(A a, B b, C c, D d, E e, F f, G g) {
        this.first = a;
        this.second = b;
        this.third = c;
        this.fourth = d;
        this.fifth = e;
        this.sixth = f;
        this.seventh = g;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Septuple copy$default(Septuple septuple, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, int i, Object obj8) {
        A a = obj;
        if ((i & 1) != 0) {
            a = septuple.first;
        }
        B b = obj2;
        if ((i & 2) != 0) {
            b = septuple.second;
        }
        B b2 = b;
        C c = obj3;
        if ((i & 4) != 0) {
            c = septuple.third;
        }
        C c2 = c;
        D d = obj4;
        if ((i & 8) != 0) {
            d = septuple.fourth;
        }
        D d2 = d;
        E e = obj5;
        if ((i & 16) != 0) {
            e = septuple.fifth;
        }
        E e2 = e;
        F f = obj6;
        if ((i & 32) != 0) {
            f = septuple.sixth;
        }
        F f2 = f;
        G g = obj7;
        if ((i & 64) != 0) {
            g = septuple.seventh;
        }
        return septuple.copy(a, b2, c2, d2, e2, f2, g);
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

    public final E component5() {
        return this.fifth;
    }

    public final F component6() {
        return this.sixth;
    }

    public final G component7() {
        return this.seventh;
    }

    public final Septuple<A, B, C, D, E, F, G> copy(A a, B b, C c, D d, E e, F f, G g) {
        return new Septuple<>(a, b, c, d, e, f, g);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Septuple)) {
            return false;
        }
        Septuple septuple = (Septuple) obj;
        return Intrinsics.areEqual(this.first, septuple.first) && Intrinsics.areEqual(this.second, septuple.second) && Intrinsics.areEqual(this.third, septuple.third) && Intrinsics.areEqual(this.fourth, septuple.fourth) && Intrinsics.areEqual(this.fifth, septuple.fifth) && Intrinsics.areEqual(this.sixth, septuple.sixth) && Intrinsics.areEqual(this.seventh, septuple.seventh);
    }

    public final E getFifth() {
        return this.fifth;
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

    public final G getSeventh() {
        return this.seventh;
    }

    public final F getSixth() {
        return this.sixth;
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
        int hashCode4 = (hashCode3 + (d == null ? 0 : d.hashCode())) * 31;
        E e = this.fifth;
        int hashCode5 = (hashCode4 + (e == null ? 0 : e.hashCode())) * 31;
        F f = this.sixth;
        int hashCode6 = (hashCode5 + (f == null ? 0 : f.hashCode())) * 31;
        G g = this.seventh;
        return hashCode6 + (g != null ? g.hashCode() : 0);
    }

    public String toString() {
        return "Septuple(first=" + this.first + ", second=" + this.second + ", third=" + this.third + ", fourth=" + this.fourth + ", fifth=" + this.fifth + ", sixth=" + this.sixth + ", seventh=" + this.seventh + ")";
    }
}
