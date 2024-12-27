package com.android.systemui.util.kotlin;

import kotlin.jvm.internal.Intrinsics;

public final class Quint<A, B, C, D, E> {
    public static final int $stable = 0;
    private final E fifth;
    private final A first;
    private final D fourth;
    private final B second;
    private final C third;

    public Quint(A a, B b, C c, D d, E e) {
        this.first = a;
        this.second = b;
        this.third = c;
        this.fourth = d;
        this.fifth = e;
    }

    public static /* synthetic */ Quint copy$default(Quint quint, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, int i, Object obj6) {
        A a = obj;
        if ((i & 1) != 0) {
            a = quint.first;
        }
        B b = obj2;
        if ((i & 2) != 0) {
            b = quint.second;
        }
        B b2 = b;
        C c = obj3;
        if ((i & 4) != 0) {
            c = quint.third;
        }
        C c2 = c;
        D d = obj4;
        if ((i & 8) != 0) {
            d = quint.fourth;
        }
        D d2 = d;
        E e = obj5;
        if ((i & 16) != 0) {
            e = quint.fifth;
        }
        return quint.copy(a, b2, c2, d2, e);
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

    public final Quint<A, B, C, D, E> copy(A a, B b, C c, D d, E e) {
        return new Quint<>(a, b, c, d, e);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Quint)) {
            return false;
        }
        Quint quint = (Quint) obj;
        return Intrinsics.areEqual(this.first, quint.first) && Intrinsics.areEqual(this.second, quint.second) && Intrinsics.areEqual(this.third, quint.third) && Intrinsics.areEqual(this.fourth, quint.fourth) && Intrinsics.areEqual(this.fifth, quint.fifth);
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
        return hashCode4 + (e != null ? e.hashCode() : 0);
    }

    public String toString() {
        return "Quint(first=" + this.first + ", second=" + this.second + ", third=" + this.third + ", fourth=" + this.fourth + ", fifth=" + this.fifth + ")";
    }
}
