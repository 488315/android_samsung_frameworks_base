package com.android.systemui.biometrics.ui.viewmodel;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.shared.model.BiometricModality;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class PromptAuthState {
    public final BiometricModality authenticatedModality;
    public final long delay;
    public final boolean isAuthenticated;
    public final boolean needsUserConfirmation;
    public boolean wasConfirmed;

    public PromptAuthState(boolean z, BiometricModality biometricModality, boolean z2, long j) {
        this.isAuthenticated = z;
        this.authenticatedModality = biometricModality;
        this.needsUserConfirmation = z2;
        this.delay = j;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PromptAuthState)) {
            return false;
        }
        PromptAuthState promptAuthState = (PromptAuthState) obj;
        return this.isAuthenticated == promptAuthState.isAuthenticated && this.authenticatedModality == promptAuthState.authenticatedModality && this.needsUserConfirmation == promptAuthState.needsUserConfirmation && this.delay == promptAuthState.delay;
    }

    public final int hashCode() {
        return Long.hashCode(this.delay) + TransitionData$$ExternalSyntheticOutline0.m((this.authenticatedModality.hashCode() + (Boolean.hashCode(this.isAuthenticated) * 31)) * 31, 31, this.needsUserConfirmation);
    }

    public final String toString() {
        return "PromptAuthState(isAuthenticated=" + this.isAuthenticated + ", authenticatedModality=" + this.authenticatedModality + ", needsUserConfirmation=" + this.needsUserConfirmation + ", delay=" + this.delay + ")";
    }

    public /* synthetic */ PromptAuthState(boolean z, BiometricModality biometricModality, boolean z2, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? BiometricModality.None : biometricModality, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? 0L : j);
    }
}
