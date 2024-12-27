package com.android.systemui.statusbar.connectivity;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
