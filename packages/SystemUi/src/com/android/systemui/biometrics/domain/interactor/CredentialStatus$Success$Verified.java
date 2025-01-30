package com.android.systemui.biometrics.domain.interactor;

import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import java.util.Arrays;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        return PathParser$$ExternalSyntheticOutline0.m29m("Verified(hat=", Arrays.toString(this.hat), ")");
    }
}
