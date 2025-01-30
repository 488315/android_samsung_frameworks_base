package com.android.systemui.keyguard.shared.model;

import android.hardware.face.FaceManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SuccessAuthenticationStatus extends AuthenticationStatus {
    public final FaceManager.AuthenticationResult successResult;

    public SuccessAuthenticationStatus(FaceManager.AuthenticationResult authenticationResult) {
        super(null);
        this.successResult = authenticationResult;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof SuccessAuthenticationStatus) && Intrinsics.areEqual(this.successResult, ((SuccessAuthenticationStatus) obj).successResult);
    }

    public final int hashCode() {
        return this.successResult.hashCode();
    }

    public final String toString() {
        return "SuccessAuthenticationStatus(successResult=" + this.successResult + ")";
    }
}
