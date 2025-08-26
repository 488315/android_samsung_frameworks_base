package com.android.systemui.inputdevice.tutorial.domain.interactor;

/* loaded from: classes2.dex */
public final class ConnectionState {
    public final boolean keyboardConnected;
    public final boolean touchpadConnected;

    public ConnectionState(boolean z, boolean z2) {
        this.keyboardConnected = z;
        this.touchpadConnected = z2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConnectionState)) {
            return false;
        }
        ConnectionState connectionState = (ConnectionState) obj;
        return this.keyboardConnected == connectionState.keyboardConnected && this.touchpadConnected == connectionState.touchpadConnected;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.touchpadConnected) + (Boolean.hashCode(this.keyboardConnected) * 31);
    }

    public final String toString() {
        return "ConnectionState(keyboardConnected=" + this.keyboardConnected + ", touchpadConnected=" + this.touchpadConnected + ")";
    }
}
