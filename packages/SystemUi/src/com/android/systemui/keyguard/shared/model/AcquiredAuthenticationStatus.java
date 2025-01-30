package com.android.systemui.keyguard.shared.model;

import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AcquiredAuthenticationStatus extends AuthenticationStatus {
    public final int acquiredInfo;

    public AcquiredAuthenticationStatus(int i) {
        super(null);
        this.acquiredInfo = i;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AcquiredAuthenticationStatus) && this.acquiredInfo == ((AcquiredAuthenticationStatus) obj).acquiredInfo;
    }

    public final int hashCode() {
        return Integer.hashCode(this.acquiredInfo);
    }

    public final String toString() {
        return ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder("AcquiredAuthenticationStatus(acquiredInfo="), this.acquiredInfo, ")");
    }
}
