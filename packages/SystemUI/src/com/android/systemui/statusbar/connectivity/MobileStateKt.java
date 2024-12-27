package com.android.systemui.statusbar.connectivity;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

public abstract class MobileStateKt {
    public static final String access$minLog(ServiceState serviceState) {
        int state = serviceState.getState();
        boolean isEmergencyOnly = serviceState.isEmergencyOnly();
        boolean roaming = serviceState.getRoaming();
        String operatorAlphaShort = serviceState.getOperatorAlphaShort();
        StringBuilder m = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("serviceState={state=", state, ",isEmergencyOnly=", isEmergencyOnly, ",roaming=");
        m.append(roaming);
        m.append(",operatorNameAlphaShort=");
        m.append(operatorAlphaShort);
        m.append("}");
        return m.toString();
    }

    public static final String access$minLog(SignalStrength signalStrength) {
        return "signalStrength={isGsm=" + signalStrength.isGsm() + ",level=" + signalStrength.getLevel() + "}";
    }
}
