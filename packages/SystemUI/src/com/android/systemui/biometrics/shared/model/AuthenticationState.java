package com.android.systemui.biometrics.shared.model;

import android.hardware.biometrics.BiometricSourceType;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.core.KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public interface AuthenticationState {

    public final class Acquired implements AuthenticationState {
        public final int acquiredInfo;
        public final BiometricSourceType biometricSourceType;
        public final AuthenticationReason requestReason;

        public Acquired(BiometricSourceType biometricSourceType, AuthenticationReason authenticationReason, int i) {
            this.biometricSourceType = biometricSourceType;
            this.requestReason = authenticationReason;
            this.acquiredInfo = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Acquired)) {
                return false;
            }
            Acquired acquired = (Acquired) obj;
            return this.biometricSourceType == acquired.biometricSourceType && Intrinsics.areEqual(this.requestReason, acquired.requestReason) && this.acquiredInfo == acquired.acquiredInfo;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final BiometricSourceType getBiometricSourceType() {
            return this.biometricSourceType;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final AuthenticationReason getRequestReason() {
            return this.requestReason;
        }

        public final int hashCode() {
            return Integer.hashCode(this.acquiredInfo) + ((this.requestReason.hashCode() + (this.biometricSourceType.hashCode() * 31)) * 31);
        }

        public final String toString() {
            BiometricSourceType biometricSourceType = this.biometricSourceType;
            StringBuilder sb = new StringBuilder("Acquired(biometricSourceType=");
            sb.append(biometricSourceType);
            sb.append(", requestReason=");
            sb.append(this.requestReason);
            sb.append(", acquiredInfo=");
            return Anchor$$ExternalSyntheticOutline0.m(this.acquiredInfo, ")", sb);
        }
    }

    public final class Error implements AuthenticationState {
        public final BiometricSourceType biometricSourceType;
        public final int errCode;
        public final String errString;
        public final AuthenticationReason requestReason;

        public Error(BiometricSourceType biometricSourceType, String str, int i, AuthenticationReason authenticationReason) {
            this.biometricSourceType = biometricSourceType;
            this.errString = str;
            this.errCode = i;
            this.requestReason = authenticationReason;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Error)) {
                return false;
            }
            Error error = (Error) obj;
            return this.biometricSourceType == error.biometricSourceType && Intrinsics.areEqual(this.errString, error.errString) && this.errCode == error.errCode && Intrinsics.areEqual(this.requestReason, error.requestReason);
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final BiometricSourceType getBiometricSourceType() {
            return this.biometricSourceType;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final AuthenticationReason getRequestReason() {
            return this.requestReason;
        }

        public final int hashCode() {
            int hashCode = this.biometricSourceType.hashCode() * 31;
            String str = this.errString;
            return this.requestReason.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.errCode, (hashCode + (str == null ? 0 : str.hashCode())) * 31, 31);
        }

        public final String toString() {
            return "Error(biometricSourceType=" + this.biometricSourceType + ", errString=" + this.errString + ", errCode=" + this.errCode + ", requestReason=" + this.requestReason + ")";
        }
    }

    public final class Failed implements AuthenticationState {
        public final BiometricSourceType biometricSourceType;
        public final AuthenticationReason requestReason;
        public final int userId;

        public Failed(BiometricSourceType biometricSourceType, AuthenticationReason authenticationReason, int i) {
            this.biometricSourceType = biometricSourceType;
            this.requestReason = authenticationReason;
            this.userId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Failed)) {
                return false;
            }
            Failed failed = (Failed) obj;
            return this.biometricSourceType == failed.biometricSourceType && Intrinsics.areEqual(this.requestReason, failed.requestReason) && this.userId == failed.userId;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final BiometricSourceType getBiometricSourceType() {
            return this.biometricSourceType;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final AuthenticationReason getRequestReason() {
            return this.requestReason;
        }

        public final int hashCode() {
            return Integer.hashCode(this.userId) + ((this.requestReason.hashCode() + (this.biometricSourceType.hashCode() * 31)) * 31);
        }

        public final String toString() {
            BiometricSourceType biometricSourceType = this.biometricSourceType;
            StringBuilder sb = new StringBuilder("Failed(biometricSourceType=");
            sb.append(biometricSourceType);
            sb.append(", requestReason=");
            sb.append(this.requestReason);
            sb.append(", userId=");
            return Anchor$$ExternalSyntheticOutline0.m(this.userId, ")", sb);
        }
    }

    public final class Help implements AuthenticationState {
        public final BiometricSourceType biometricSourceType;
        public final int helpCode;
        public final String helpString;
        public final AuthenticationReason requestReason;

        public Help(BiometricSourceType biometricSourceType, String str, int i, AuthenticationReason authenticationReason) {
            this.biometricSourceType = biometricSourceType;
            this.helpString = str;
            this.helpCode = i;
            this.requestReason = authenticationReason;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Help)) {
                return false;
            }
            Help help = (Help) obj;
            return this.biometricSourceType == help.biometricSourceType && Intrinsics.areEqual(this.helpString, help.helpString) && this.helpCode == help.helpCode && Intrinsics.areEqual(this.requestReason, help.requestReason);
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final BiometricSourceType getBiometricSourceType() {
            return this.biometricSourceType;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final AuthenticationReason getRequestReason() {
            return this.requestReason;
        }

        public final int hashCode() {
            int hashCode = this.biometricSourceType.hashCode() * 31;
            String str = this.helpString;
            return this.requestReason.hashCode() + KeyframesSpec$KeyframeEntity$$ExternalSyntheticOutline0.m(this.helpCode, (hashCode + (str == null ? 0 : str.hashCode())) * 31, 31);
        }

        public final String toString() {
            return "Help(biometricSourceType=" + this.biometricSourceType + ", helpString=" + this.helpString + ", helpCode=" + this.helpCode + ", requestReason=" + this.requestReason + ")";
        }
    }

    public final class Started implements AuthenticationState {
        public final BiometricSourceType biometricSourceType;
        public final AuthenticationReason requestReason;

        public Started(BiometricSourceType biometricSourceType, AuthenticationReason authenticationReason) {
            this.biometricSourceType = biometricSourceType;
            this.requestReason = authenticationReason;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Started)) {
                return false;
            }
            Started started = (Started) obj;
            return this.biometricSourceType == started.biometricSourceType && Intrinsics.areEqual(this.requestReason, started.requestReason);
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final BiometricSourceType getBiometricSourceType() {
            return this.biometricSourceType;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final AuthenticationReason getRequestReason() {
            return this.requestReason;
        }

        public final int hashCode() {
            return this.requestReason.hashCode() + (this.biometricSourceType.hashCode() * 31);
        }

        public final String toString() {
            return "Started(biometricSourceType=" + this.biometricSourceType + ", requestReason=" + this.requestReason + ")";
        }
    }

    public final class Stopped implements AuthenticationState {
        public final BiometricSourceType biometricSourceType;
        public final AuthenticationReason requestReason;

        public Stopped(BiometricSourceType biometricSourceType, AuthenticationReason authenticationReason) {
            this.biometricSourceType = biometricSourceType;
            this.requestReason = authenticationReason;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Stopped)) {
                return false;
            }
            Stopped stopped = (Stopped) obj;
            return this.biometricSourceType == stopped.biometricSourceType && Intrinsics.areEqual(this.requestReason, stopped.requestReason);
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final BiometricSourceType getBiometricSourceType() {
            return this.biometricSourceType;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final AuthenticationReason getRequestReason() {
            return this.requestReason;
        }

        public final int hashCode() {
            return this.requestReason.hashCode() + (this.biometricSourceType.hashCode() * 31);
        }

        public final String toString() {
            return "Stopped(biometricSourceType=" + this.biometricSourceType + ", requestReason=" + this.requestReason + ")";
        }
    }

    public final class Succeeded implements AuthenticationState {
        public final BiometricSourceType biometricSourceType;
        public final boolean isStrongBiometric;
        public final AuthenticationReason requestReason;
        public final int userId;

        public Succeeded(BiometricSourceType biometricSourceType, boolean z, AuthenticationReason authenticationReason, int i) {
            this.biometricSourceType = biometricSourceType;
            this.isStrongBiometric = z;
            this.requestReason = authenticationReason;
            this.userId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Succeeded)) {
                return false;
            }
            Succeeded succeeded = (Succeeded) obj;
            return this.biometricSourceType == succeeded.biometricSourceType && this.isStrongBiometric == succeeded.isStrongBiometric && Intrinsics.areEqual(this.requestReason, succeeded.requestReason) && this.userId == succeeded.userId;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final BiometricSourceType getBiometricSourceType() {
            return this.biometricSourceType;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final AuthenticationReason getRequestReason() {
            return this.requestReason;
        }

        public final int hashCode() {
            return Integer.hashCode(this.userId) + ((this.requestReason.hashCode() + TransitionData$$ExternalSyntheticOutline0.m(this.biometricSourceType.hashCode() * 31, 31, this.isStrongBiometric)) * 31);
        }

        public final String toString() {
            return "Succeeded(biometricSourceType=" + this.biometricSourceType + ", isStrongBiometric=" + this.isStrongBiometric + ", requestReason=" + this.requestReason + ", userId=" + this.userId + ")";
        }
    }

    BiometricSourceType getBiometricSourceType();

    AuthenticationReason getRequestReason();

    public final class Idle implements AuthenticationState {
        public final BiometricSourceType biometricSourceType;
        public final AuthenticationReason requestReason;

        public Idle(BiometricSourceType biometricSourceType, AuthenticationReason authenticationReason) {
            this.biometricSourceType = biometricSourceType;
            this.requestReason = authenticationReason;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Idle)) {
                return false;
            }
            Idle idle = (Idle) obj;
            return this.biometricSourceType == idle.biometricSourceType && Intrinsics.areEqual(this.requestReason, idle.requestReason);
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final BiometricSourceType getBiometricSourceType() {
            return this.biometricSourceType;
        }

        @Override // com.android.systemui.biometrics.shared.model.AuthenticationState
        public final AuthenticationReason getRequestReason() {
            return this.requestReason;
        }

        public final int hashCode() {
            BiometricSourceType biometricSourceType = this.biometricSourceType;
            return this.requestReason.hashCode() + ((biometricSourceType == null ? 0 : biometricSourceType.hashCode()) * 31);
        }

        public final String toString() {
            return "Idle(biometricSourceType=" + this.biometricSourceType + ", requestReason=" + this.requestReason + ")";
        }

        public /* synthetic */ Idle(BiometricSourceType biometricSourceType, AuthenticationReason authenticationReason, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? null : biometricSourceType, authenticationReason);
        }
    }
}
