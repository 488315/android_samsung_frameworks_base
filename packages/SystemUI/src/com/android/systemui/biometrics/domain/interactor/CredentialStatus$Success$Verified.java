package com.android.systemui.biometrics.domain.interactor;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class CredentialStatus$Success$Verified implements CredentialStatus {
    public final byte[] hat;

    public CredentialStatus$Success$Verified(byte[] bArr) {
        this.hat = bArr;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CredentialStatus$Success$Verified) && Intrinsics.areEqual(this.hat, ((CredentialStatus$Success$Verified) obj).hat);
    }

    public final int hashCode() {
        return Arrays.hashCode(this.hat);
    }

    public final String toString() {
        return ContentInViewNode$Request$$ExternalSyntheticOutline0.m("Verified(hat=", Arrays.toString(this.hat), ")");
    }
}
