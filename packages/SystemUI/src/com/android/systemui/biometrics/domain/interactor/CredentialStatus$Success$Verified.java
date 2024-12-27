package com.android.systemui.biometrics.domain.interactor;

import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
