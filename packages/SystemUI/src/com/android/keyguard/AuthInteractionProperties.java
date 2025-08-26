package com.android.keyguard;

import android.os.VibrationAttributes;
import com.google.android.msdl.domain.InteractionProperties;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class AuthInteractionProperties implements InteractionProperties {
    public final VibrationAttributes vibrationAttributes;

    /* JADX WARN: Multi-variable type inference failed */
    public AuthInteractionProperties() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AuthInteractionProperties) && Intrinsics.areEqual(this.vibrationAttributes, ((AuthInteractionProperties) obj).vibrationAttributes);
    }

    @Override // com.google.android.msdl.domain.InteractionProperties
    public final VibrationAttributes getVibrationAttributes() {
        return this.vibrationAttributes;
    }

    public final int hashCode() {
        return this.vibrationAttributes.hashCode();
    }

    public final String toString() {
        return "AuthInteractionProperties(vibrationAttributes=" + this.vibrationAttributes + ")";
    }

    public AuthInteractionProperties(VibrationAttributes vibrationAttributes) {
        this.vibrationAttributes = vibrationAttributes;
    }

    public /* synthetic */ AuthInteractionProperties(VibrationAttributes vibrationAttributes, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? VibrationAttributes.createForUsage(65) : vibrationAttributes);
    }
}
