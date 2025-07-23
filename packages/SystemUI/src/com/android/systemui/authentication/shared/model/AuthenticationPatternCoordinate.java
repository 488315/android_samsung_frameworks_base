package com.android.systemui.authentication.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AuthenticationPatternCoordinate {
    public final int x;
    public final int y;

    public AuthenticationPatternCoordinate(int i, int i2) {
        this.x = i;
        this.y = i2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthenticationPatternCoordinate)) {
            return false;
        }
        AuthenticationPatternCoordinate authenticationPatternCoordinate = (AuthenticationPatternCoordinate) obj;
        return this.x == authenticationPatternCoordinate.x && this.y == authenticationPatternCoordinate.y;
    }

    public final int hashCode() {
        return Integer.hashCode(this.y) + (Integer.hashCode(this.x) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("AuthenticationPatternCoordinate(x=");
        sb.append(this.x);
        sb.append(", y=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.y, ")", sb);
    }
}
