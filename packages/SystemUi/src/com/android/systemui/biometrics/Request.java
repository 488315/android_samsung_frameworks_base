package com.android.systemui.biometrics;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("Request(displayId="), this.displayId, ")");
    }
}
