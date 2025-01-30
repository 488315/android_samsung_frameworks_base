package com.airbnb.lottie.model;

import androidx.core.util.Pair;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MutablePair {
    public Object first;
    public Object second;

    public final boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        Object obj2 = pair.first;
        Object obj3 = this.first;
        if (!(obj2 == obj3 || (obj2 != null && obj2.equals(obj3)))) {
            return false;
        }
        Object obj4 = this.second;
        Object obj5 = pair.second;
        return obj5 == obj4 || (obj5 != null && obj5.equals(obj4));
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
