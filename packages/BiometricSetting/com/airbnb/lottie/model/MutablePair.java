package com.airbnb.lottie.model;

import androidx.core.util.Pair;

/* loaded from: classes.dex */
public final class MutablePair<T> {
    T first;
    T second;

    public final boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        F f = pair.first;
        Object obj2 = this.first;
        if (!(f == obj2 || (f != 0 && f.equals(obj2)))) {
            return false;
        }
        Object obj3 = this.second;
        S s = pair.second;
        return s == obj3 || (s != 0 && s.equals(obj3));
    }

    public final int hashCode() {
        T t = this.first;
        int hashCode = t == null ? 0 : t.hashCode();
        T t2 = this.second;
        return hashCode ^ (t2 != null ? t2.hashCode() : 0);
    }

    public final void set(T t, T t2) {
        this.first = t;
        this.second = t2;
    }

    public final String toString() {
        return "Pair{" + String.valueOf(this.first) + " " + String.valueOf(this.second) + "}";
    }
}
