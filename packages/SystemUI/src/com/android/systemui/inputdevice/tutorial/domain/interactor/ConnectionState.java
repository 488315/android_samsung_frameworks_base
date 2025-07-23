package com.android.systemui.inputdevice.tutorial.domain.interactor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
