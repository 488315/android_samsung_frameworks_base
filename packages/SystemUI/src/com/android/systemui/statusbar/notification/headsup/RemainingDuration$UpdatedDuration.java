package com.android.systemui.statusbar.notification.headsup;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RemainingDuration$UpdatedDuration {
    public final int duration;

    public RemainingDuration$UpdatedDuration(int i) {
        this.duration = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof RemainingDuration$UpdatedDuration) && this.duration == ((RemainingDuration$UpdatedDuration) obj).duration;
    }

    public final int hashCode() {
        return Integer.hashCode(this.duration);
    }

    public final String toString() {
        return ReorderTile$$ExternalSyntheticOutline0.m(this.duration, ")", new StringBuilder("UpdatedDuration(duration="));
    }
}
