package com.android.keyguard;

import java.util.Objects;

public final class TrustGrantFlags {
    public final int mFlags;

    public TrustGrantFlags(int i) {
        this.mFlags = i;
    }

    public final boolean dismissKeyguardRequested() {
        return (this.mFlags & 2) != 0;
    }

    public final boolean equals(Object obj) {
        return (obj instanceof TrustGrantFlags) && ((TrustGrantFlags) obj).mFlags == this.mFlags;
    }

    public final int hashCode() {
        return Objects.hash(Integer.valueOf(this.mFlags));
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("[");
        int i = this.mFlags;
        sb.append(i);
        sb.append("]=");
        if ((i & 1) != 0) {
            sb.append("initiatedByUser|");
        }
        if (dismissKeyguardRequested()) {
            sb.append("dismissKeyguard|");
        }
        if ((i & 4) != 0) {
            sb.append("temporaryAndRenewable|");
        }
        if ((i & 8) != 0) {
            sb.append("displayMessage|");
        }
        return sb.toString();
    }
}
