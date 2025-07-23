package com.android.systemui.biometrics.domain.model;

import android.support.v4.media.session.MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BiometricOperationInfo {
    public final long gatekeeperChallenge;

    public BiometricOperationInfo() {
        this(0L, 1, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof BiometricOperationInfo) && this.gatekeeperChallenge == ((BiometricOperationInfo) obj).gatekeeperChallenge;
    }

    public final int hashCode() {
        return Long.hashCode(this.gatekeeperChallenge);
    }

    public final String toString() {
        return MediaSessionCompat$QueueItem$$ExternalSyntheticOutline0.m(this.gatekeeperChallenge, ")", new StringBuilder("BiometricOperationInfo(gatekeeperChallenge="));
    }

    public BiometricOperationInfo(long j) {
        this.gatekeeperChallenge = j;
    }

    public /* synthetic */ BiometricOperationInfo(long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? -1L : j);
    }
}
