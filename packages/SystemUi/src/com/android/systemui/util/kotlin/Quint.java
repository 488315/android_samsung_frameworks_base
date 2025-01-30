package com.android.systemui.util.kotlin;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class Quint {
    public final Object fifth;
    public final Object first;
    public final Object fourth;
    public final Object second;
    public final Object third;

    public Quint(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        this.first = obj;
        this.second = obj2;
        this.third = obj3;
        this.fourth = obj4;
        this.fifth = obj5;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Quint)) {
            return false;
        }
        Quint quint = (Quint) obj;
        return Intrinsics.areEqual(this.first, quint.first) && Intrinsics.areEqual(this.second, quint.second) && Intrinsics.areEqual(this.third, quint.third) && Intrinsics.areEqual(this.fourth, quint.fourth) && Intrinsics.areEqual(this.fifth, quint.fifth);
    }

    public final int hashCode() {
        Object obj = this.first;
        int hashCode = (obj == null ? 0 : obj.hashCode()) * 31;
        Object obj2 = this.second;
        int hashCode2 = (hashCode + (obj2 == null ? 0 : obj2.hashCode())) * 31;
        Object obj3 = this.third;
        int hashCode3 = (hashCode2 + (obj3 == null ? 0 : obj3.hashCode())) * 31;
        Object obj4 = this.fourth;
        int hashCode4 = (hashCode3 + (obj4 == null ? 0 : obj4.hashCode())) * 31;
        Object obj5 = this.fifth;
        return hashCode4 + (obj5 != null ? obj5.hashCode() : 0);
    }

    public final String toString() {
        return "Quint(first=" + this.first + ", second=" + this.second + ", third=" + this.third + ", fourth=" + this.fourth + ", fifth=" + this.fifth + ")";
    }
}
