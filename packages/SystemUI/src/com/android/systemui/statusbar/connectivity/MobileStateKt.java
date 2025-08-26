package com.android.systemui.statusbar.connectivity;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* loaded from: classes3.dex */
public abstract class MobileStateKt {
    public static final String access$minLog(ServiceState serviceState) {
        int state = serviceState.getState();
        boolean zIsEmergencyOnly = serviceState.isEmergencyOnly();
        boolean roaming = serviceState.getRoaming();
        String operatorAlphaShort = serviceState.getOperatorAlphaShort();
        StringBuilder sbM = KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m("serviceState={state=", state, ",isEmergencyOnly=", zIsEmergencyOnly, ",roaming=");
        sbM.append(roaming);
        sbM.append(",operatorNameAlphaShort=");
        sbM.append(operatorAlphaShort);
        sbM.append("}");
        return sbM.toString();
    }

    public static final String access$minLog(SignalStrength signalStrength) {
        return "signalStrength={isGsm=" + signalStrength.isGsm() + ",level=" + signalStrength.getLevel() + "}";
    }
}
