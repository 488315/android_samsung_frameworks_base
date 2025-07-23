package com.android.systemui.statusbar.connectivity;

import android.telephony.ServiceState;
import android.telephony.SignalStrength;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
