package com.android.systemui.biometrics.p003ui.viewmodel;

import com.android.systemui.biometrics.domain.model.BiometricModality;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class PromptAuthState {
    public final BiometricModality authenticatedModality;
    public final long delay;
    public final boolean isAuthenticated;
    public final boolean needsUserConfirmation;

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

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.isAuthenticated;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int hashCode = (this.authenticatedModality.hashCode() + (i * 31)) * 31;
        boolean z2 = this.needsUserConfirmation;
        return Long.hashCode(this.delay) + ((hashCode + (z2 ? 1 : z2 ? 1 : 0)) * 31);
    }

    public final String toString() {
        return "PromptAuthState(isAuthenticated=" + this.isAuthenticated + ", authenticatedModality=" + this.authenticatedModality + ", needsUserConfirmation=" + this.needsUserConfirmation + ", delay=" + this.delay + ")";
    }

    public /* synthetic */ PromptAuthState(boolean z, BiometricModality biometricModality, boolean z2, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(z, (i & 2) != 0 ? BiometricModality.None : biometricModality, (i & 4) != 0 ? false : z2, (i & 8) != 0 ? 0L : j);
    }
}
