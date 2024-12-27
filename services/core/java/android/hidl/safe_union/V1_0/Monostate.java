package android.hidl.safe_union.V1_0;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class Monostate {
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != Monostate.class) {
            return false;
        }
        return true;
    }

    public final int hashCode() {
        return Objects.hash(new Object[0]);
    }

    public final String toString() {
        return "{}";
    }
}
