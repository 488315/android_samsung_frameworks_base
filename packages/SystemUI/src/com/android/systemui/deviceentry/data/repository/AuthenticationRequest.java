package com.android.systemui.deviceentry.data.repository;

import com.android.systemui.deviceentry.shared.FaceAuthUiEvent;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class AuthenticationRequest {
    public final boolean fallbackToDetection;
    public final FaceAuthUiEvent uiEvent;

    public AuthenticationRequest(FaceAuthUiEvent faceAuthUiEvent, boolean z) {
        this.uiEvent = faceAuthUiEvent;
        this.fallbackToDetection = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AuthenticationRequest)) {
            return false;
        }
        AuthenticationRequest authenticationRequest = (AuthenticationRequest) obj;
        return this.uiEvent == authenticationRequest.uiEvent && this.fallbackToDetection == authenticationRequest.fallbackToDetection;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.fallbackToDetection) + (this.uiEvent.hashCode() * 31);
    }

    public final String toString() {
        return "AuthenticationRequest(uiEvent=" + this.uiEvent + ", fallbackToDetection=" + this.fallbackToDetection + ")";
    }
}
