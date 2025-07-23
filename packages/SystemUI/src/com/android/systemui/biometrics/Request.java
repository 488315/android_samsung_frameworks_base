package com.android.systemui.biometrics;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        return ReorderTile$$ExternalSyntheticOutline0.m(this.displayId, ")", new StringBuilder("Request(displayId="));
    }
}
