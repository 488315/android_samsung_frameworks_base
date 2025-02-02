package androidx.core.util;

import java.util.Objects;

/* loaded from: classes.dex */
public final class Pair<F, S> {
    public final F first;
    public final S second;

    public final boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        return Objects.equals(pair.first, this.first) && Objects.equals(pair.second, this.second);
    }

    public final int hashCode() {
        F f = this.first;
        int hashCode = f == null ? 0 : f.hashCode();
        S s = this.second;
        return hashCode ^ (s != null ? s.hashCode() : 0);
    }

    public final String toString() {
        return "Pair{" + this.first + " " + this.second + "}";
    }
}
