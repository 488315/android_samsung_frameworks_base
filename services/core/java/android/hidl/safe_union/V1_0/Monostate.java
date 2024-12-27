package android.hidl.safe_union.V1_0;

import java.util.Objects;

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
