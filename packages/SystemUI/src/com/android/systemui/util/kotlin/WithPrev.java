package com.android.systemui.util.kotlin;

import kotlin.jvm.internal.Intrinsics;

public final class WithPrev<S, T extends S> {
    public static final int $stable = 0;
    private final T newValue;
    private final S previousValue;

    public WithPrev(S s, T t) {
        this.previousValue = s;
        this.newValue = t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ WithPrev copy$default(WithPrev withPrev, Object obj, Object obj2, int i, Object obj3) {
        if ((i & 1) != 0) {
            obj = withPrev.previousValue;
        }
        if ((i & 2) != 0) {
            obj2 = withPrev.newValue;
        }
        return withPrev.copy(obj, obj2);
    }

    public final S component1() {
        return this.previousValue;
    }

    public final T component2() {
        return this.newValue;
    }

    public final WithPrev<S, T> copy(S s, T t) {
        return new WithPrev<>(s, t);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof WithPrev)) {
            return false;
        }
        WithPrev withPrev = (WithPrev) obj;
        return Intrinsics.areEqual(this.previousValue, withPrev.previousValue) && Intrinsics.areEqual(this.newValue, withPrev.newValue);
    }

    public final T getNewValue() {
        return this.newValue;
    }

    public final S getPreviousValue() {
        return this.previousValue;
    }

    public int hashCode() {
        S s = this.previousValue;
        int hashCode = (s == null ? 0 : s.hashCode()) * 31;
        T t = this.newValue;
        return hashCode + (t != null ? t.hashCode() : 0);
    }

    public String toString() {
        return "WithPrev(previousValue=" + this.previousValue + ", newValue=" + this.newValue + ")";
    }
}
