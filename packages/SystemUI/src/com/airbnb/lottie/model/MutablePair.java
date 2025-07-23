package com.airbnb.lottie.model;

import androidx.core.util.Pair;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MutablePair {
    public Object first;
    public Object second;

    public final boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        Object obj2 = pair.first;
        Object obj3 = this.first;
        if (obj2 != obj3 && (obj2 == null || !obj2.equals(obj3))) {
            return false;
        }
        Object obj4 = this.second;
        Object obj5 = pair.second;
        if (obj5 != obj4) {
            return obj5 != null && obj5.equals(obj4);
        }
        return true;
    }

    public final int hashCode() {
        Object obj = this.first;
        int hashCode = obj == null ? 0 : obj.hashCode();
        Object obj2 = this.second;
        return hashCode ^ (obj2 != null ? obj2.hashCode() : 0);
    }

    public final String toString() {
        return "Pair{" + this.first + " " + this.second + "}";
    }
}
