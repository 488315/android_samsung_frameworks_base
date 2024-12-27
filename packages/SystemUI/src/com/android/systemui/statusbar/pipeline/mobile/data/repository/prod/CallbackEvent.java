package com.android.systemui.statusbar.pipeline.mobile.data.repository.prod;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import android.telephony.TelephonyDisplayInfo;
import android.telephony.satellite.NtnSignalStrength;
import android.telephony.satellite.SemSatelliteServiceState;
import android.telephony.satellite.SemSatelliteSignalStrength;
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.runtime.Anchor$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

public interface CallbackEvent {

    public final class OnCallStateChanged implements CallbackEvent {
        public final int callState;

        public OnCallStateChanged(int i) {
            this.callState = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnCallStateChanged) && this.callState == ((OnCallStateChanged) obj).callState;
        }

        public final int hashCode() {
            return Integer.hashCode(this.callState);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.callState, ")", new StringBuilder("OnCallStateChanged(callState="));
        }
    }

    public final class OnCarrierNetworkChange implements CallbackEvent {
        public final boolean active;

        public OnCarrierNetworkChange(boolean z) {
            this.active = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnCarrierNetworkChange) && this.active == ((OnCarrierNetworkChange) obj).active;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.active);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnCarrierNetworkChange(active="), this.active, ")");
        }
    }

    public final class OnCarrierRoamingNtnModeChanged implements CallbackEvent {
        public final boolean active;

        public OnCarrierRoamingNtnModeChanged(boolean z) {
            this.active = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnCarrierRoamingNtnModeChanged) && this.active == ((OnCarrierRoamingNtnModeChanged) obj).active;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.active);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnCarrierRoamingNtnModeChanged(active="), this.active, ")");
        }
    }

    public final class OnCarrierRoamingNtnSignalStrengthChanged implements CallbackEvent {
        public final NtnSignalStrength signalStrength;

        public OnCarrierRoamingNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength) {
            this.signalStrength = ntnSignalStrength;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnCarrierRoamingNtnSignalStrengthChanged) && Intrinsics.areEqual(this.signalStrength, ((OnCarrierRoamingNtnSignalStrengthChanged) obj).signalStrength);
        }

        public final int hashCode() {
            return this.signalStrength.hashCode();
        }

        public final String toString() {
            return "OnCarrierRoamingNtnSignalStrengthChanged(signalStrength=" + this.signalStrength + ")";
        }
    }

    public final class OnDataActivity implements CallbackEvent {
        public final int direction;

        public OnDataActivity(int i) {
            this.direction = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnDataActivity) && this.direction == ((OnDataActivity) obj).direction;
        }

        public final int hashCode() {
            return Integer.hashCode(this.direction);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.direction, ")", new StringBuilder("OnDataActivity(direction="));
        }
    }

    public final class OnDataConnectionStateChanged implements CallbackEvent {
        public final int dataState;

        public OnDataConnectionStateChanged(int i) {
            this.dataState = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnDataConnectionStateChanged) && this.dataState == ((OnDataConnectionStateChanged) obj).dataState;
        }

        public final int hashCode() {
            return Integer.hashCode(this.dataState);
        }

        public final String toString() {
            return Anchor$$ExternalSyntheticOutline0.m(this.dataState, ")", new StringBuilder("OnDataConnectionStateChanged(dataState="));
        }
    }

    public final class OnDataEnabledChanged implements CallbackEvent {
        public final boolean enabled;

        public OnDataEnabledChanged(boolean z) {
            this.enabled = z;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnDataEnabledChanged) && this.enabled == ((OnDataEnabledChanged) obj).enabled;
        }

        public final int hashCode() {
            return Boolean.hashCode(this.enabled);
        }

        public final String toString() {
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(new StringBuilder("OnDataEnabledChanged(enabled="), this.enabled, ")");
        }
    }

    public final class OnDisplayInfoChanged implements CallbackEvent {
        public final TelephonyDisplayInfo telephonyDisplayInfo;

        public OnDisplayInfoChanged(TelephonyDisplayInfo telephonyDisplayInfo) {
            this.telephonyDisplayInfo = telephonyDisplayInfo;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnDisplayInfoChanged) && Intrinsics.areEqual(this.telephonyDisplayInfo, ((OnDisplayInfoChanged) obj).telephonyDisplayInfo);
        }

        public final int hashCode() {
            return this.telephonyDisplayInfo.hashCode();
        }

        public final String toString() {
            return "OnDisplayInfoChanged(telephonyDisplayInfo=" + this.telephonyDisplayInfo + ")";
        }
    }

    public final class OnServiceStateChanged implements CallbackEvent {
        public final ServiceState serviceState;

        public OnServiceStateChanged(ServiceState serviceState) {
            this.serviceState = serviceState;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnServiceStateChanged) && Intrinsics.areEqual(this.serviceState, ((OnServiceStateChanged) obj).serviceState);
        }

        public final int hashCode() {
            return this.serviceState.hashCode();
        }

        public final String toString() {
            return "OnServiceStateChanged(serviceState=" + this.serviceState + ")";
        }
    }

    public final class OnSignalStrengthChanged implements CallbackEvent {
        public final SignalStrength signalStrength;

        public OnSignalStrengthChanged(SignalStrength signalStrength) {
            this.signalStrength = signalStrength;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof OnSignalStrengthChanged) && Intrinsics.areEqual(this.signalStrength, ((OnSignalStrengthChanged) obj).signalStrength);
        }

        public final int hashCode() {
            return this.signalStrength.hashCode();
        }

        public final String toString() {
            return "OnSignalStrengthChanged(signalStrength=" + this.signalStrength + ")";
        }
    }

    public final class onSemSatelliteServiceStateChanged implements CallbackEvent {
        public final SemSatelliteServiceState serviceState;

        public onSemSatelliteServiceStateChanged(SemSatelliteServiceState semSatelliteServiceState) {
            this.serviceState = semSatelliteServiceState;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof onSemSatelliteServiceStateChanged) && Intrinsics.areEqual(this.serviceState, ((onSemSatelliteServiceStateChanged) obj).serviceState);
        }

        public final int hashCode() {
            return this.serviceState.hashCode();
        }

        public final String toString() {
            return "onSemSatelliteServiceStateChanged(serviceState=" + this.serviceState + ")";
        }
    }

    public final class onSemSatelliteSignalStrengthChanged implements CallbackEvent {
        public final SemSatelliteSignalStrength signalStrength;

        public onSemSatelliteSignalStrengthChanged(SemSatelliteSignalStrength semSatelliteSignalStrength) {
            this.signalStrength = semSatelliteSignalStrength;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof onSemSatelliteSignalStrengthChanged) && Intrinsics.areEqual(this.signalStrength, ((onSemSatelliteSignalStrengthChanged) obj).signalStrength);
        }

        public final int hashCode() {
            return this.signalStrength.hashCode();
        }

        public final String toString() {
            return "onSemSatelliteSignalStrengthChanged(signalStrength=" + this.signalStrength + ")";
        }
    }
}
