package com.android.systemui.biometrics;

import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class Request {
    public final int displayId;

    public Request(int i) {
        this.displayId = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof Request) && this.displayId == ((Request) obj).displayId;
    }

    public final int hashCode() {
        return Integer.hashCode(this.displayId);
    }

    public final String toString() {
        return Anchor$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("Request(displayId="));
    }
}
