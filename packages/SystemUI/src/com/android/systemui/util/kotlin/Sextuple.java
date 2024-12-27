package com.android.systemui.util.kotlin;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class Sextuple<A, B, C, D, E, F> {
    public static final int $stable = 0;
    private final E fifth;
    private final A first;
    private final D fourth;
    private final B second;
    private final F sixth;
    private final C third;

    public Sextuple(A a, B b, C c, D d, E e, F f) {
        this.first = a;
        this.second = b;
        this.third = c;
        this.fourth = d;
        this.fifth = e;
        this.sixth = f;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ Sextuple copy$default(Sextuple sextuple, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, int i, Object obj7) {
        A a = obj;
        if ((i & 1) != 0) {
            a = sextuple.first;
        }
        B b = obj2;
        if ((i & 2) != 0) {
            b = sextuple.second;
        }
        B b2 = b;
        C c = obj3;
        if ((i & 4) != 0) {
            c = sextuple.third;
        }
        C c2 = c;
        D d = obj4;
        if ((i & 8) != 0) {
            d = sextuple.fourth;
        }
        D d2 = d;
        E e = obj5;
        if ((i & 16) != 0) {
            e = sextuple.fifth;
        }
        E e2 = e;
        F f = obj6;
        if ((i & 32) != 0) {
            f = sextuple.sixth;
        }
        return sextuple.copy(a, b2, c2, d2, e2, f);
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

    public final Sextuple<A, B, C, D, E, F> copy(A a, B b, C c, D d, E e, F f) {
        return new Sextuple<>(a, b, c, d, e, f);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Sextuple)) {
            return false;
        }
        Sextuple sextuple = (Sextuple) obj;
        return Intrinsics.areEqual(this.first, sextuple.first) && Intrinsics.areEqual(this.second, sextuple.second) && Intrinsics.areEqual(this.third, sextuple.third) && Intrinsics.areEqual(this.fourth, sextuple.fourth) && Intrinsics.areEqual(this.fifth, sextuple.fifth) && Intrinsics.areEqual(this.sixth, sextuple.sixth);
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
        return hashCode5 + (f != null ? f.hashCode() : 0);
    }

    public String toString() {
        return "Sextuple(first=" + this.first + ", second=" + this.second + ", third=" + this.third + ", fourth=" + this.fourth + ", fifth=" + this.fifth + ", sixth=" + this.sixth + ")";
    }
}
